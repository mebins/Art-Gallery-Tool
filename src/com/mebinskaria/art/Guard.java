package com.mebinskaria.art;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Line2D;
import java.util.ArrayList;

import com.mebinskaria.main.DataModel;
import com.mebinskaria.main.Drawable;

@SuppressWarnings("serial")
/**
 * 
 * 
 * COVERS SECTION 7 (Full Encapsulation, Loose Coupling, Exhibits Polymorphism)
 * COVERS SECTION 9 (Equal Method, Serialization)
 * When computation is on (Calculates 360 Degrees of Lines (how many depends on
 * the radial space level) that collide with walls (depending on what level of
 * power the guard is at it will go through that many walls, default 0 power
 * level)).
 * 
 * @author Mebin Skaria
 *
 */
public class Guard implements Drawable {

	/**
	 * Constructs the Guard Class
	 * 
	 * @param x Stores the X of the Guard.
	 * 
	 * @param y Stores the Y of the Guard.
	 * 
	 * @param n How many Walls the Guard can see through.
	 */
	public Guard(int x, int y, int n) {
		this.x = x;
		this.y = y;
		this.power = n;
		guardColor = colors[colorSelection];
		if (++colorSelection >= colors.length)
			colorSelection = 0;
		compute = dataModel.isComputing();
	}

	/**
	 * Draws the guard and if computed will draw the area the guard can see.
	 * 
	 * @param g The Paint brush that draws the graphics
	 * 
	 */
	@Override
	public void render(Graphics g) {
		g.setColor(guardColor);
		if (compute) {
			for (Line e : lines) {
				e.render(g);
			}
		}
	}

	/**
	 * 
	 * @return the color of the guard.
	 */
	public Color getColor() {
		return guardColor;
	}

	/**
	 * Function used to update the Guard.
	 */
	public void update() {
		if (compute = dataModel.isComputing()) {
			computation(dataModel.getGuardRadialSpace());
		}

	}

	/**
	 * 
	 * @param radialLevel the radial space between each line in radian
	 */
	public void computation(double radialLevel) {

		if (radialLevel != oldRadialLevel) {
			ArrayList<Wall> walls = dataModel.getWalls();
			lines.clear();
			// for every wall
			int x = this.getX() + displacement;
			int y = this.getY() + displacement;
			int startX = x;
			int startY = y;
			int endX = 0;
			int endY = 0;
			double length = 4000;
			// 360 degrees
			for (double theta = 0; theta < 2 * Math.PI; theta += radialLevel) {
				ArrayList<Integer> optimizedList = new ArrayList<Integer>();
				boolean possibleIntersection = false;

				/*
				 * 1.Checks every line for a intersection 2.If there is a
				 * intersection checks exactly how long the line has to be
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
				// if there was a intersection, from 0 increment by 1 till
				// collision.
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
							lines.add(new Line(startX, startY, finalX, finalY));
						}
						
					}
				}

				oldRadialLevel = radialLevel;
			}
		}
	}

	/**
	 * @return a String in the appropriate format for the Guard
	 */
	public String toString() {
		return "G:" + this.x + ":" + this.y + ":" + this.power;
	}

	/**
	 * @return X of the guard.
	 */
	public int getX() {
		return x;
	}

	/**
	 * @param x sets Guards x.
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * 
	 * @return the guards vision to see through Walls.
	 */
	public int getPower() {
		return power;
	}

	/**
	 * @return Y of the guard.
	 */
	public int getY() {
		return y;
	}

	/**
	 * @param y sets Guards y.
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * reflexive, symmetric, transitive, and properly handling of null.
	 */
	public boolean equals(Object target) {
		if (target instanceof Guard) {
			Guard other = (Guard) target;
			if (other.x == x) {
				if (other.y == y) {
					if (other.power == power) {
							return true;
					}
				}
			}
		}
		return false;

	}
	public ArrayList<Line> getCache()
	{
		return lines;
	}

	private ArrayList<Line> lines = new ArrayList<>();
	private int x;
	private int y;
	private double oldRadialLevel = -1; // optimizing code

	// to the guard
	private static int colorSelection = 0;
	private boolean compute;
	private int power = 0; // how many walls it can see through
	private final Color[] colors = { Color.blue, Color.green, Color.RED, Color.yellow, Color.CYAN, Color.PINK };
	private final Color guardColor;
	private int displacement = dataModel.getCircleDisplacement();
	private static final DataModel dataModel = DataModel.getInstance();

}
