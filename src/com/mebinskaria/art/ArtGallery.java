package com.mebinskaria.art;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferStrategy;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

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

/**
 * 
 * COVERS SECTION 10 (One Thread)
 * @author Mebin Skaria Holds the View for Art Gallery Mode.
 */
public class ArtGallery extends Gallery implements Observer {

	private static final long serialVersionUID = 1L;

	/**
	 * @param width size of window.
	 * @param height height of window.
	 * @param frame the frame which is being changed(originally the menu).
	 */
	public ArtGallery(int width, int height, JFrame frame) {
		super("Art Gallery -  Beta", width, height, frame);
		this.setSize(new Dimension(width, height));
		new ArtTextView(width / 2, height / 2, (int)frame.getX() + this.getWidth(), (int)frame.getY());
		mm = new MouseManager(frame, this);
		frame.addMouseListener(mm);
		controller.addObserver(this);
		initialize();
		
	}

	/**
	 * Initializes (keeps the constructor more readable) before the Thread Loop.
	 */
	private void initialize() {
		try {
			getRadialLevelMethod = radialLevel.getClass().getMethod("getValue");
			System.out.println(getRadialLevelMethod.invoke(radialLevel));
		} catch (NoSuchMethodException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SecurityException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IllegalArgumentException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InvocationTargetException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		for (JButton b : buttons) {
			this.tools.add(b);
		}
		radialLevel.setInverted(true);
		// radialLevel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

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
		buttons[8]
				.addActionListener((ActionEvent event) -> controller.setVisionPower(Integer.parseInt(power.getText())));
		canvas.add(this);
		gallery.add(canvas, BorderLayout.CENTER);
		this.setFocusable(false);
		this.createBufferStrategy(3);
		bs = this.getBufferStrategy();
		g = bs.getDrawGraphics();
		gallery.pack();
		/*
		 * Runs 60 Frames Per Second
		 */

		t = new Timer(17, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tick();
				render(g);
			}
		});
		t.start();

	}
	
	public void update()
	{
		drawables = (ArrayList<Drawable>) dataModel.getDrawables();
		guards = (ArrayList<Guard>) dataModel.getGuards();
	
	}

	/**
	 * Updates the Gallery Logic Every Frame.
	 */
	public void tick() {
		oldSliderValue = newSliderValue;
		newSliderValue = (int) (2 * Math.PI / (radialLevel.getValue() / 100f)) + 1;
		if (oldSliderValue != newSliderValue) {
			radialLevelLabel.setText((int) (2 * Math.PI / (radialLevel.getValue() / 100f)) + 1 + " Radial Lines");
			controller.setGuardRadialSpace(radialLevel.getValue() / 100f);
		}
		
		if (!dataModel.isComputing() == computeButton) {
			if (dataModel.isComputing()) {
				buttons[4].setText("Computing : ON");
				computeButton = true;
			} else {
				buttons[4].setText("Computing : OFF");
				computeButton = false;
			}
		}

		Dimension canvasSize = this.getSize();
		Dimension frameSize = gallery.getSize();
		if (canvasSize.getWidth() != frameSize.getWidth()) {
			this.setSize((int) frameSize.getWidth(), (int) frameSize.getHeight());
		}
		if (canvasSize.getHeight() != frameSize.getHeight()) {
			this.setSize((int) frameSize.getWidth(), (int) frameSize.getHeight());
		}
		for (Drawable e : dataModel.getDrawables()) {
			e.update();
		}

		shouldRender = true;

	}

	/**
	 * Updates the Gallery Drawings every Frame.
	 */
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

			for (Drawable d : drawables) {
				d.render(g);
			}
			for (Guard e : guards) {
				g.setColor(Color.black);
				int x = e.getX();
				int y = e.getY();
				g.drawOval(x, y, circleSize, circleSize);
				g.setColor(e.getColor());
				g.fillOval(x, y, circleSize, circleSize);
				g.setColor(Color.BLACK);
				g.drawString(e.getPower() + "", x - 5, y);
			}
			if (dataModel.getX() != 1 && dataModel.getY() != -1) {
				g.setColor(Color.black);
				g.fillOval(dataModel.getX() - dataModel.getCircleDisplacement(), dataModel.getY() - dataModel.getCircleDisplacement(),
						dataModel.getCircleSize(), dataModel.getCircleSize());
			}
			g.dispose();
			bs.show();

			shouldRender = false;
		}
	}

	/**
	 * Checks if a full polygon has been drawn in the Gallery.
	 * 
	 * @return true if the polygon has been finished drawing.
	 */
	public boolean isPolygonDrawn() {
		return polygonDrawn;
	}

	/**
	 * 
	 * @param polygonDrawn
	 *            sets the polygon to be finished or unfinished.
	 */
	public void setPolygonDrawn(boolean polygonDrawn) {
		this.polygonDrawn = polygonDrawn;
	}

	/**
	 * 
	 * @return how many polygons there are.
	 */
	public int getPolygonIndex() {
		return polygonIndex;
	}

	public void setPolygonIndex(int polygonIndex) {
		this.polygonIndex = polygonIndex;
	}


	/**
	 * 
	 * @return current Vision power used to create for guards.
	 */
	public int getVisionPower() {
		return visionPower;
	}
	public ArrayList<Drawable> getDrawables()
	{
		return drawables;
	}
	public ArrayList<Guard> getGuards()
	{
		return guards;
	}

	private int newSliderValue = 1;
	private int oldSliderValue = 0;
	private int polygonIndex = 0;
	private int visionPower = 0;
	private boolean polygonDrawn = false;
	private boolean shouldRender = false;
	private boolean computeButton = false;

	private final JButton[] buttons = { new JButton("Restart"), new JButton("Erase Guards"), new JButton("Lines"),
			new JButton("Guards"), new JButton("Computing: OFF"), new JButton("Screenshot and Save JPEG"),
			new JButton("Save File"), new JButton("Load File"), new JButton("set Guard Vision") };
	private JTextField power = new JTextField("0");

	private final JSlider radialLevel = new JSlider(1, 100);
	private ArrayList<Drawable> drawables = new ArrayList<>();
	private ArrayList<Guard> guards = new ArrayList<>();
	private final JLabel radialLevelLabel = new JLabel();
	private final JPanel canvas = new JPanel();
	private Method getRadialLevelMethod;
	private MouseManager mm;

	private Graphics g;
	private BufferStrategy bs;
	private Timer t;
	private final ArtController controller = ArtController.getInstance();
	private final DataModel dataModel = DataModel.getInstance();
	private final int circleSize = dataModel.getCircleSize();
}
