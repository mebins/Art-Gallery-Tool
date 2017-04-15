package com.mebinskaria.art;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Line2D;
import java.util.ArrayList;

import com.mebinskaria.main.DataModel;
import com.mebinskaria.main.Drawable;

@SuppressWarnings("serial")
public class Guard implements Drawable {

	/*
	 * Constructs the Guard Class
	 * 
	 * @param x Stores the X of the Guard.
	 * 
	 * @param y Stores the Y of the Guard.
	 * 
	 * @param color The number of the Color Picked from colorSelection.
	 * 
	 * @param n How many Walls the Guard can see through.
	 */
	public Guard(int x, int y, int n) {
		this.x = x;
		this.y = y;
		this.power = n;
		newColor = colors[colorSelection];
		if(++colorSelection >= colors.length) colorSelection = 0;
		compute = dataModel.isComputing();
	}
	/*
	 * Draws the guard and if computed will draw the area the guard can see.
	 * 
	 * @param g The Paint brush that draws the graphics
	 * 
	 * @param list The array of Walls that is used to see what the Guard can
	 * see.
	 */
	@Override
	public void render(Graphics g) {
		g.setColor(newColor);
		g.fillOval(x, y, CIRCLE_SIZE, CIRCLE_SIZE);
		if (compute) {
				for(Line e : lines)
				{
					e.render(g);
				}
			}
		g.setColor(newColor.brighter());
		g.fillOval(this.x, this.y, CIRCLE_SIZE, CIRCLE_SIZE);
		g.setColor(Color.BLACK);
		g.drawOval(this.x, this.y, CIRCLE_SIZE, CIRCLE_SIZE);
		g.setColor(newColor);
		}
	

	
	/*
	 * Function used to update the Guard.
	 */
	public void update() {
			compute = dataModel.isComputing();
			computation(dataModel.getGuardRadialSpace());
	}
	
	public void computation(double radialLevel) {
		if(radialLevel != oldRadialLevel)
		{
		ArrayList<Wall> walls = dataModel.getWalls();
		lines.clear();
		// for every wall
		int x = this.getX() + DISPLACEMENT;
		int y = this.getY() + DISPLACEMENT;
		int startX = x;
		int startY = y;
		int endX = 0;
		int endY = 0;
		double length = 4000;
		
		//360 degrees
		for (double theta = 0; theta < 2*Math.PI; theta += radialLevel) {
			ArrayList<Integer> optimizedList = new ArrayList<Integer>();
			boolean possibleIntersection = false;
			
			/*
			 * 1.Checks every line for a intersection
			 * 2.If there is a intersection checks exactly how long the line has to be
			 * 3.Draws the Line.
			 */
			for (int i = 0; i < walls.size(); i++) {
				Wall line = walls.get(i);
				endX = (int) (x + length * Math.sin(theta));
				endY = (int) (y + length * Math.cos(theta));
				if (Line2D.linesIntersect(startX, startY, endX, endY, line.getX(), line.getY(), line.getX2(),
						line.getY2())) {
					possibleIntersection = true;
					// optimizing length
					int optimizedLength = 1;
					int optimizedEndX = (int) (x + optimizedLength * Math.sin(theta));
					int optimizedEndY = (int) (y + optimizedLength * Math.cos(theta));

					while (!Line2D.linesIntersect(startX, startY, optimizedEndX, optimizedEndY, line.getX(),
							line.getY(), line.getX2(), line.getY2())) {
						optimizedLength++;
						optimizedEndX = (int) (x + optimizedLength * Math.sin(theta));
						optimizedEndY = (int) (y + optimizedLength * Math.cos(theta));
					}
					optimizedList.add(optimizedLength);
				}
			}
			//if there was a intersection, from 0 increment by 1 till collision.
			if (possibleIntersection) {
				for (int i = 0; i < power + 1; i++) {
					if (optimizedList.size() > 0) {
						int lowest = optimizedList.get(0);
						int lowestIndex = 0;
						for (int j = 1; j < optimizedList.size(); j++) {
							if (lowest > optimizedList.get(j)) {
								lowest = optimizedList.get(j);
								lowestIndex = j;
							}
						}
						optimizedList.remove(lowestIndex);

						int finalX = (int) (x + lowest * Math.sin(theta));
						int finalY = (int) (y + lowest * Math.cos(theta));
						lines.add(new Line(startX,startY,finalX,finalY));
					}
				}
			}
			
			oldRadialLevel = radialLevel;
		}
		}
	}

	
	public String toString()
	{
		return "G:"+this.x + ":"+this.y+":"+this.power;
	}
	
	/*
	 * returns X of the guard.
	 */
	public int getX() {
		return x;
	}

	/*
	 * @param x sets Guards x.
	 */
	public void setX(int x) {
		this.x = x;
	}

	public int getPower()
	{
		return power;
	}
	/*
	 * @returns Y of the guard.
	 */
	public int getY() {
		return y;
	}

	/*
	 * @param y sets Guards y.
	 */
	public void setY(int y) {
		this.y = y;
	}
	public boolean equals(Object target)
	{
		if(target instanceof Guard)
		{
			Guard other = (Guard)target;
			if(other.x == x)
			{
				if(other.y == y)
				{
					if(other.power == power)
					{
						if(other.newColor.equals(newColor)) return true;
					}
				}
			}
		}
		return false;
		
	}
	private ArrayList<Line> lines = new ArrayList<>();
	private int x;
	private int y;
	private double oldRadialLevel = -1; //optimizing code
	private final int CIRCLE_SIZE = 10;
	private final int DISPLACEMENT = CIRCLE_SIZE / 2; // used to center the line
	// to the guard
	private static int colorSelection = 0;
	private static boolean compute;
	private int  power = 0; // how many walls it can see through
	private final Color[] colors = {Color.blue,Color.green,Color.RED,Color.yellow,Color.CYAN,Color.PINK};
	private final Color newColor;
	
	private static final DataModel dataModel = DataModel.getInstance();
	

}
