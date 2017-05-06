package com.mebinskaria.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;

import com.mebinskaria.art.Guard;
import com.mebinskaria.art.Wall;
import com.mebinskaria.main.DataModel;
import com.mebinskaria.main.DataModel.Mode;

public class DataModelTest {

	@Test
	public void test() {
		DataModel model = DataModel.getInstance();
		model.addGuard(new Guard(245,245,0));
		assertEquals(model.getGuards().get(0),new Guard(245,245,0));
		assertEquals(model.getDrawables().get(0),new Guard(245,245,0));
		model.addWall(new Wall(245,245,245,245));
		assertEquals(model.getWalls().get(0), new Wall(245,245,245,245));
		assertEquals(model.getDrawables().get(1),new Wall(245,245,245,245));
		model.clearGuards();
		assertEquals(model.getGuards().size(),0);
		assertEquals(model.getDrawables().size(),1);
		model.clearWalls();
		assertEquals(model.getWalls().size(),0);
		model.setGuardPowerLevel(2);
		model.setGuardRadialSpace(.2);
		model.setGuards(new ArrayList<Guard>(Arrays.asList(new Guard(24,24,2))));
		model.setWalls(new ArrayList<Wall>(Arrays.asList(new Wall(24,24,24,24))));
		model.setMode(Mode.GUARD);
		model.setX(24);
		model.setY(24);
		
		assertEquals(model.getGuardPowerLevel(),2);
		if(model.getGuardRadialSpace() != .2)
		{
			fail("");
		}
		assertEquals(model.getGuards(), new ArrayList<Guard>(Arrays.asList(new Guard(24,24,2))));
		assertEquals(model.getWalls(), new ArrayList<Wall>(Arrays.asList(new Wall(24,24,24,24))));
		assertEquals(model.getMode(),Mode.GUARD);
		assertEquals(model.getX(),24);
		assertEquals(model.getY(),24);
	}

}
