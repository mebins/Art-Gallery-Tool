package com.mebinskaria.art;

import java.awt.Color;
import java.awt.Graphics;

import com.mebinskaria.main.Drawable;

@SuppressWarnings("serial")
public class Wall implements Drawable{
	
	private int x;
	private int y;
	private int x2;
	private int y2;

	/*
	 * Constructor of Wall.
	 * @param x first point X.
	 * @param y first point Y.
	 * @param x2 second point X.
	 * @param y2 second point Y.
	 */
	public Wall(int x, int y, int x2, int y2)
	{
		this.x = x;
		this.y = y;
		this.x2 = x2;
		this.y2 = y2;
	}
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getX2() {
		return x2;
	}

	public void setX2(int x2) {
		this.x2 = x2;
	}

	public int getY2() {
		return y2;
	}

	public void setY2(int y2) {
		this.y2 = y2;
	}

	/*
	 * Draws the Walls
	 * @param g Paint brush to draw graphics
	 */
	public void render(Graphics g)
	{
		g.setColor(Color.BLACK);
		g.drawLine(x, y, x2, y2);
		g.drawLine(x+1, y, x2+1, y2);
		g.fillOval(x-DISPLACEMENT, y-DISPLACEMENT, CIRCLE_SIZE, CIRCLE_SIZE);
	}
	
	public void update()
	{
		
	}
	public String toString()
	{
		return "W:"+x+":" +y+":"+x2+":"+y2;
	}
	private final int CIRCLE_SIZE = 10;
	private final int DISPLACEMENT = CIRCLE_SIZE/2;
}
