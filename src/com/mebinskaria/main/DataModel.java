package com.mebinskaria.main;
import java.util.ArrayList;

import com.mebinskaria.art.Guard;
import com.mebinskaria.art.Wall;

public class DataModel {
	
	private static DataModel datamodel = new DataModel();
	
	public static DataModel getInstance()
	{
		return datamodel;
	}
	
	public enum Mode {
		LINE, GUARD, NOTHING
	}
	private ArrayList<Guard> guards = new ArrayList<>();
	private ArrayList<Wall> walls = new ArrayList<>();
	private final int CIRCLE_SIZE = 10;
	private final int DISPLACEMENT = CIRCLE_SIZE / 2; // used to center the line
	private double guardRadialSpace = 0;
	private int guardPowerLevel = 0;
	private boolean computing = false;
	
	private Mode mode = Mode.LINE;
	
	public Mode getMode()
	{
		return mode;
	}
	public void setMode(Mode mode)
	{
		this.mode = mode;
	}
	
	public int getCircleSize()
	{
		return CIRCLE_SIZE;
	}
	public int getCircleDisplacement()
	{
		return DISPLACEMENT;
	}
	public void addWall(Wall w)
	{
		walls.add(w);
	}
	public void setWalls(ArrayList<Wall> w)
	{
		walls = w;
	}
	public void setGuards(ArrayList<Guard> g)
	{
		guards = g;
	}
	public ArrayList<Drawable> getDrawables()
	{
		ArrayList<Drawable> list = new ArrayList<>();
		list.addAll(guards);
		list.addAll(walls);
		return list;
	}
	public void addGuard(Guard g)
	{
		guards.add(g);
	}
	public void clearGuards()
	{
		guards.clear();
	}
	public void clearWalls()
	{
		walls.clear();
	}
	
	public void compute()
	{	
		if(!computing)
		{
			computing = true;
		}
		else
		{
			computing = false;
		}
	}
	public double getGuardRadialSpace()
	{
		return guardRadialSpace;
	}
	public void setGuardRadialSpace(double guardRadialSpace)
	{
		this.guardRadialSpace = guardRadialSpace;
	}
	public int getGuardPowerLevel()
	{
		return guardPowerLevel;
	}
	public void setGuardPowerLevel(int level)
	{
		guardPowerLevel = level;
	}
	public boolean isComputing()
	{
		return computing;
	}
	public ArrayList<Guard> getGuards()
	{
		return new ArrayList<Guard>(guards);
	}
	public ArrayList<Wall> getWalls()
	{
		return new ArrayList<Wall>(walls);
	}
	

}
