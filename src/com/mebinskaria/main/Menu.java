package com.mebinskaria.main;

import java.awt.Canvas;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JFrame;

import com.mebinskaria.art.ArtGallery;
import com.mebinskaria.light.LightGallery;

public class Menu extends Canvas {
	
	private static final long serialVersionUID = 1L;
	private JFrame menuFrame;
	Graphics g;
	Gallery gallery;
	private int width;
	private int height;
	private JButton buttons[] = {new JButton("Art Gallery"), new JButton("Light Gallery")};
	public Menu(String name, int width, int height)
	{
		this.width = width;
		this.height = height;
		menuFrame = new JFrame(name);
		menuFrame.setLocationRelativeTo(null);
		menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		menuFrame.setSize(width, height);
		menuFrame.setLayout(new FlowLayout());
		menuFrame.setVisible(true);
		initialize();
		menuFrame.pack();
		g = menuFrame.getGraphics();
	}
	private void initialize()
	{
		
		for(JButton b : buttons)
		{
			menuFrame.add(b);
		}
		buttons[0].addActionListener((ActionEvent event) -> createArtGallery());
		buttons[1].addActionListener((ActionEvent event) -> createLightGallery());
	}
	private void createArtGallery()
	{
		for(JButton e :  buttons)
		{
			menuFrame.remove(e);
		}
		
		gallery = new ArtGallery(width,height,menuFrame);
		
	}
	private void createLightGallery()
	{
		for(JButton e: buttons)
		{
			menuFrame.remove(e);
		}
		gallery = new LightGallery(width,height,menuFrame);
	}

}
