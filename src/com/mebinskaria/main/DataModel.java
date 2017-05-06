package com.mebinskaria.main;

import java.util.ArrayList;
import java.util.List;

import com.mebinskaria.art.Guard;
import com.mebinskaria.art.Wall;

/**
 * 
 * COVERS SECTION 8 (Singleton Pattern)
 * COVERS SECTION 7 (Fully Encapsulated, Loose Coupling))
 * @author Mebin Skaria
 *
 */
public class DataModel {

	/**
	 * Private Constructor for Singleton Pattern
	 */
	private DataModel() {
	}

	/**
	 * 
	 * @return The Single instance of the DataModel.
	 */
	public static DataModel getInstance() {
		return dataModel;
	}

	/**
	 * 
	 * @author Mebin Skaria Chooses which mode the view is drawing in.
	 */
	public enum Mode {
		LINE, GUARD, NOTHING
	}

	/**
	 * 
	 * @return the current mode that has been set.
	 */
	public Mode getMode() {
		return mode;
	}

	/**
	 * 
	 * @param mode
	 *            the mode that the current mode will be set to.
	 */
	public void setMode(Mode mode) {
		this.mode = mode;
	}

	/**
	 * @return Returns the Circle Size that has been drawn.
	 */
	public int getCircleSize() {
		return CIRCLE_SIZE;
	}

	/**
	 * @return the Displacement to draw with.
	 */
	public int getCircleDisplacement() {
		return DISPLACEMENT;
	}

	/**
	 * 
	 * @param w the Wall Object to add to the List.
	 */
	public void addWall(Wall w) {
		walls.add(w);
	}

	/**
	 * 
	 * @param list sets the whole Wall Collection to this new List.
	 */
	public void setWalls(List<Wall> list) {
		walls = list;
	}

	/**
	 * @param g sets the whole guards Collection to this new List.
	 */
	public void setGuards(List<Guard> g) {
		guards = g;
	}

	/**
	 * 
	 * @return All Drawable Objects in the collections
	 */
	public List<Drawable> getDrawables() {
		List<Drawable> list = new ArrayList<>();
		list.addAll(guards);
		list.addAll(walls);
		return list;
	}

	/**
	 * 
	 * @return an ArrayList of all the Guards
	 */
	public ArrayList<Guard> getGuards() {
		return new ArrayList<Guard>(guards);
	}

	/**
	 * 
	 * @return an ArrayList of all the Walls
	 */
	public ArrayList<Wall> getWalls() {
		return new ArrayList<Wall>(walls);
	}

	/**
	 * 
	 * @param g adds the Guard Object to the collections.
	 */
	public void addGuard(Guard g) {
		guards.add(g);
	}

	/**
	 * Removes all Guards from the Collections.
	 */
	public void clearGuards() {
		guards.clear();
	}

	/**
	 * Removes all Walls from the Collections.
	 */
	public void clearWalls() {
		walls.clear();
	}

	/**
	 * 
	 * @return How much space there are in radian between each line.
	 */
	public double getGuardRadialSpace() {
		return guardRadialSpace;
	}

	/**
	 * 
	 * @param guardRadialSpace sets how much space there are in radian between each line.
	 */
	public void setGuardRadialSpace(double guardRadialSpace) {
		this.guardRadialSpace = guardRadialSpace;
	}

	/**
	 * 
	 * @return what level all new Guard Objects vision level will be.
	 */
	public int getGuardPowerLevel() {
		return guardPowerLevel;
	}

	/**
	 * 
	 * @param level set what level all new Guard Objects vision level will be.
	 */
	public void setGuardPowerLevel(int level) {
		guardPowerLevel = level;
	}

	/**
	 * 
	 * @return if the computing boolean is true or false.
	 */

	public boolean isComputing() {
		return computing;
	}

	/**
	 * Changes Boolean to false if it was true, or the opposite.
	 */
	public void compute() {
		if (!computing) {
			computing = true;
		} else {
			computing = false;
		}
	}
	
	/**
	 * 
	 * @return The first point the mouse clicked X Position.
	 */
	public int getX()
	{
		return x;
	}
	/**
	 * 
	 * @param x Where the first point the mouse clicked X Position
	 */
	public void setX(int x)
	{
		this.x = x;
	}
	
	/**
	 * 
	 * @return The first point the mouse clicked Y Position.
	 */
	public int getY()
	{
		return y;
	}
	/**
	 * 
	 * @param y The First point the mouse clicked X Position.
	 */
	public void setY(int y)
	{
		this.y = y;
	}

	private int x = -1;
	private int y = -1;
	private static DataModel dataModel = new DataModel();
	private List<Guard> guards = new ArrayList<>();
	private List<Wall> walls = new ArrayList<>();
	private final int CIRCLE_SIZE = 10;
	private final int DISPLACEMENT = CIRCLE_SIZE / 2; // used to center the line
	private double guardRadialSpace = 0;
	private int guardPowerLevel = 0;
	private boolean computing = false;
	private Mode mode = Mode.LINE;

}
