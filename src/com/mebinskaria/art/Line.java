package com.mebinskaria.art;

import java.awt.Graphics;

import com.mebinskaria.main.Drawable;

@SuppressWarnings("serial")
public class Line implements Drawable {
	private int x;
	private int y;
	private int x2;
	private int y2;
	
	public Line(int x, int y, int x2, int y2)
	{
		this.x = x;
		this.y = y;
		this.x2 = x2;
		this.y2 = y2;
	}
	
	public void render(Graphics g)
	{
		g.drawLine(x,y,x2,y2);
		
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

}
