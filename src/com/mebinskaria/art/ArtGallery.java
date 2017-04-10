package com.mebinskaria.art;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferStrategy;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.Timer;

import com.mebinskaria.io.FileManager;
import com.mebinskaria.io.MouseManager;
import com.mebinskaria.io.Screenshot;
import com.mebinskaria.main.DataModel;
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

	@Override
	public void initialize() {

		for (JButton b : buttons) {
			this.tools.add(b);
		}
		tools.add(power);

		buttons[0].addActionListener((ActionEvent event) -> restartCanvas());
		buttons[1].addActionListener((ActionEvent event) -> eraseGuards());
		buttons[2].addActionListener((ActionEvent event) -> DataModel.setMode(DataModel.Mode.LINE));
		buttons[3].addActionListener((ActionEvent event) -> DataModel.setMode(DataModel.Mode.GUARD));
		buttons[4].addActionListener((ActionEvent event) -> DataModel.compute(buttons[4]));
		buttons[5].addActionListener((ActionEvent event) -> snapshot());
		buttons[6].addActionListener((ActionEvent event) -> FileManager.saveFile());
		buttons[7].addActionListener((ActionEvent event) -> FileManager.chooseLoadFile(gallery));
		buttons[8].addActionListener((ActionEvent event) -> readPower());
		mm = new MouseManager(gallery, this);
		gallery.addMouseListener(mm);
		gallery.add(this);
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
		shouldRender = true;
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
		
		for(Guard e : DataModel.getGuards())
		{
			e.update();
		}
		
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
			
			for(Wall w : DataModel.getWalls())
			{
				w.render(g);
			}
			for(Guard e : DataModel.getGuards())
			{
				e.render(g);
			}
			g.dispose();
			bs.show();
			shouldRender = false;
		}
	}

	

	private void readPower() {
		System.out.println(power.getText());
		try
		{
			int d = Integer.parseInt(power.getText());
			DataModel.setGuardPowerLevel(d);
		}
		catch(NumberFormatException e)
		{
			power.setText("ERROR");
		}
		
	}

	private void restartCanvas() {
		System.out.println("Erasing Canvas");
		DataModel.clearGuards();
		DataModel.clearWalls();
	}

	private void eraseGuards() {
		System.out.println("Erasing Guards");
		DataModel.clearGuards();
	}

	private void snapshot() {
		try {
			Screenshot.snap(gallery.getX(), gallery.getY(), this.gallery.getWidth() - 20, gallery.getHeight() - 10);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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

	public void setVisionPower(int visionPower) {
		this.visionPower = visionPower;
	}


	private int polygonIndex = 0;
	private int visionPower = 0;
	private int x1 = -1;
	private int y1 = -1;
	private boolean polygonDrawn = false;
	private boolean shouldRender = false;
	private MouseManager mm;
	private Graphics g;
	private BufferStrategy bs;
	private Timer t;
	

}
