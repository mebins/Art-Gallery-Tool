package com.mebinskaria.main;
import java.util.ArrayList;

import javax.swing.JButton;

import com.mebinskaria.art.Guard;
import com.mebinskaria.art.Wall;

public class DataModel {
	
	public enum Mode {
		LINE, GUARD, NOTHING
	}
	private static ArrayList<Guard> guards = new ArrayList<>();
	private static ArrayList<Wall> walls = new ArrayList<>();
	private static double guardRadialSpace = 0;
	private static int guardPowerLevel = 0;
	private static boolean computing = false;
	
	private static Mode mode = Mode.LINE;
	
	public static Mode getMode()
	{
		return mode;
	}
	public static void setMode(Mode mode)
	{
		DataModel.mode = mode;
	}
	public static void addWall(Wall w)
	{
		walls.add(w);
	}
	public static void setWalls(ArrayList<Wall> w)
	{
		walls = w;
	}
	public static void setGuards(ArrayList<Guard> g)
	{
		guards = g;
	}
	public static ArrayList<Drawable> getDrawables()
	{
		ArrayList<Drawable> list = new ArrayList<>();
		list.addAll(guards);
		list.addAll(walls);
		return list;
	}
	public static void addGuard(Guard g)
	{
		guards.add(g);
	}
	public static void clearGuards()
	{
		guards.clear();
	}
	public static void clearWalls()
	{
		walls.clear();
	}
	
	public static void compute(JButton button)
	{	
		if(!computing)
		{
			computing = true;
			button.setText("Computing: ON");

		}
		else
		{
			computing = false;
			button.setText("Computing: OFF");
		}
	}
	public static double getGuardRadialSpace()
	{
		return guardRadialSpace;
	}
	public static void setGuardRadialSpace(double guardRadialSpace)
	{
		DataModel.guardRadialSpace = guardRadialSpace;
	}
	public static int getGuardPowerLevel()
	{
		return guardPowerLevel;
	}
	public static void setGuardPowerLevel(int level)
	{
		guardPowerLevel = level;
	}
	public static boolean isComputing()
	{
		return computing;
	}
	public static ArrayList<Guard> getGuards()
	{
		return new ArrayList<Guard>(guards);
	}
	public static ArrayList<Wall> getWalls()
	{
		return new ArrayList<Wall>(walls);
	}
	

}
