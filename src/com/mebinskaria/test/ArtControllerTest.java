package com.mebinskaria.test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import com.mebinskaria.art.ArtController;
import com.mebinskaria.art.Guard;
import com.mebinskaria.art.Wall;
import com.mebinskaria.main.DataModel;
import com.mebinskaria.main.Drawable;

public class ArtControllerTest {

	@Test
	public void test() {

		DataModel model = DataModel.getInstance();
		ArtController controller = ArtController.getInstance();
		
		controller.readUpdate("G:245:245:0\nW:245:245:245:245\n");
		
		Guard correct = new Guard(245,245,0);
		Wall correctWall = new Wall(245,245,245,245);
		ArrayList<Drawable> drawables = (ArrayList<Drawable>) model.getDrawables();
		
		assertEquals(drawables.get(0),correct);
		assertEquals(drawables.get(1),correctWall);
		
		controller.eraseGuards();
		assertEquals(model.getGuards().size(), 0);
		controller.restartCanvas();
		assertEquals(model.getDrawables().size(), 0);
		
	}

}
