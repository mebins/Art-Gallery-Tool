package com.mebinskaria.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;

import com.mebinskaria.art.ArtController;
import com.mebinskaria.art.Guard;
import com.mebinskaria.art.Wall;
import com.mebinskaria.main.DataModel;

public class GuardTest {

	@Test
	public void test() {
		ArtController controller = ArtController.getInstance();
		DataModel model = DataModel.getInstance();
		Guard test = new Guard(24,24,4);
		
		if(test.equals(null))
		{
			fail("");
		}
		if(test.equals(new Guard(42,42,2)))
		{
			fail("");
		}
		if(!test.equals(new Guard(24,24,4)))
		{
			fail("");
		}
		controller.addGuard(test);
		controller.setWalls(new ArrayList<Wall>(Arrays.asList(new Wall(20,20,28,28),new Wall(28,28,50,50),new Wall(50,50,10,10),new Wall(10,10,20,20))));		
		test.computation(.2);
		assertEquals(test.getCache().size(),(int)((2*Math.PI/.2)+1)* 2 );
	}

}
