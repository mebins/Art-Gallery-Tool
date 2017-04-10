package com.mebinskaria.main;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Toolkit;

import javax.swing.JFrame;

public abstract class Gallery extends Canvas {
	
	/**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;
	public JFrame gallery;
	public JFrame tools;

	public Gallery(String name,int width,int height, JFrame frame)
	{
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		tools = new JFrame(name + " tools");
		tools.setSize(width*2, height/5);
		tools.setLocation((int)(screenSize.getWidth()-width*2)/2,0);
		tools.setVisible(true);
		tools.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gallery = frame; 
		gallery.setSize(width, height);
		gallery.setLocationRelativeTo(null);
		gallery.setName(name);
		gallery.setLocation((int)(screenSize.getWidth()-width*2)/2,height/5);
		gallery.setFocusable(false);
		gallery.setResizable(true);
		this.setSize(width,height);
		tools.setResizable(true);
		tools.setLayout(new FlowLayout());
		gallery.pack();
	}
	
	public abstract void initialize();
	public abstract void update();
	public abstract void render(Graphics g);

}
