package com.mebinskaria.art;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferStrategy;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.Timer;

import com.mebinskaria.io.MouseManager;
import com.mebinskaria.main.DataModel;
import com.mebinskaria.main.DataModel.Mode;
import com.mebinskaria.main.Drawable;
import com.mebinskaria.main.Gallery;

public class ArtGallery extends Gallery {

	private static final long serialVersionUID = 1L;

	public ArtGallery(int width, int height, JFrame frame) {
		super("Art Gallery -  Beta", width, height, frame);
		this.setSize(new Dimension(width,height));
		initialize();
	}
	private JButton[] buttons = { new JButton("Restart"), new JButton("Erase Guards"), new JButton("Lines"),
			new JButton("Guards"), new JButton("Computing: OFF"), new JButton("Screenshot and Save JPEG"), new JButton("Save File"),
			new JButton("Load File"),new JButton("set Guard Vision") };
	private JTextField power = new JTextField("0");

	private JSlider radialLevel = new JSlider(1,100);
	
	private JLabel radialLevelLabel = new JLabel();
	private JPanel canvas = new JPanel();

	@Override
	public void initialize() {
		
		for (JButton b : buttons) {
			this.tools.add(b);
		}
		radialLevel.setInverted(true);
		
		//radialLevel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		tools.add(power);
		tools.add(radialLevel);
		tools.add(radialLevelLabel);
		
		buttons[0].addActionListener((ActionEvent event) -> controller.restartCanvas());
		buttons[1].addActionListener((ActionEvent event) -> controller.eraseGuards());
		buttons[2].addActionListener((ActionEvent event) -> controller.setMode(Mode.LINE));
		buttons[3].addActionListener((ActionEvent event) -> controller.setMode(Mode.GUARD));
		buttons[4].addActionListener((ActionEvent event) -> controller.setCompute());
		buttons[5].addActionListener((ActionEvent event) -> controller.snapshot(this));
		buttons[6].addActionListener((ActionEvent event) -> controller.saveFile());
		buttons[7].addActionListener((ActionEvent event) -> controller.loadChooseFile(gallery));
		buttons[8].addActionListener((ActionEvent event) -> controller.setVisionPower(Integer.parseInt(power.getText())));
		
		mm = new MouseManager(gallery, this);

		gallery.addMouseListener(mm);
		canvas.add(this);
		gallery.add(canvas,BorderLayout.CENTER);
		this.setFocusable(false);
		this.addMouseListener(mm);
		this.createBufferStrategy(3);
		bs = this.getBufferStrategy();
		g = bs.getDrawGraphics();
		gallery.pack();
		/*
		 * Runs 60 Frames Per Second
		 */
		t = new Timer(17, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				update();
				render(g);
			}
		});
		t.start();
	}
	@Override
	public void update() {
		
		oldSliderValue = newSliderValue;
		newSliderValue = (int)(2*Math.PI/(radialLevel.getValue()/100f)) +1;
		if(oldSliderValue != newSliderValue)
		{
			radialLevelLabel.setText((int)(2*Math.PI/(radialLevel.getValue()/100f)) +1 +" Radial Lines");
			controller.setGuardRadialSpace(radialLevel.getValue()/100f);
		}
		if(!dataModel.isComputing() == computeButton)
		{
			if(dataModel.isComputing())
			{
				buttons[4].setText("Computing : ON");
				computeButton = true;
			}
			else
			{
				buttons[4].setText("Computing : OFF");
				computeButton = false;
			}
		}
		
		Dimension canvasSize = this.getSize();
		Dimension frameSize = gallery.getSize();
		if(canvasSize.getWidth() != frameSize.getWidth())
		{
			this.setSize((int)frameSize.getWidth(), (int)frameSize.getHeight());
		}
		if(canvasSize.getHeight() != frameSize.getHeight())
		{
			this.setSize((int)frameSize.getWidth(), (int)frameSize.getHeight());
		}
		for(Drawable e : dataModel.getDrawables())
		{
			e.update();
		}
		shouldRender = true;
	}

	@Override
	public void render(Graphics g) {
		if (shouldRender) {
			// BufferStrategies
			bs = this.getBufferStrategy();
			if (bs == null) {
				createBufferStrategy(3);
				return;
			}
			g = bs.getDrawGraphics();

			// DRAW AREA
			g.setColor(Color.WHITE);
			g.clearRect(0, 0, getWidth(), getHeight());
			g.setColor(Color.BLACK);
			
			for(Drawable d : dataModel.getDrawables())
			{
				d.render(g);
			}
			g.dispose();
			bs.show();
			shouldRender = false;
		}
	}
	
	public boolean isPolygonDrawn() {
		return polygonDrawn;
	}

	public void setPolygonDrawn(boolean polygonDrawn) {
		this.polygonDrawn = polygonDrawn;
	}

	public int getPolygonIndex() {
		return polygonIndex;
	}

	public void setPolygonIndex(int polygonIndex) {
		this.polygonIndex = polygonIndex;
	}

	public int getX1() {
		return x1;
	}

	public void setX1(int x1) {
		this.x1 = x1;
	}

	public int getY1() {
		return y1;
	}

	public void setY1(int y1) {
		this.y1 = y1;
	}

	public int getVisionPower() {
		return visionPower;
	}

	
	private int newSliderValue = 1;
	private int oldSliderValue = 0;
	private int polygonIndex = 0;
	private int visionPower = 0;
	private int x1 = -1;
	private int y1 = -1;
	private boolean polygonDrawn = false;
	private boolean shouldRender = false;
	private boolean computeButton = false;
	private MouseManager mm;
	private Graphics g;
	private BufferStrategy bs;
	private Timer t;
	private final ArtController controller = new ArtController();
	private final DataModel dataModel = DataModel.getInstance();
}
