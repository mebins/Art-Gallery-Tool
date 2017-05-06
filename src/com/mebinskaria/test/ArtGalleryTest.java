package com.mebinskaria.test;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JFrame;

import org.junit.Test;

import com.mebinskaria.art.ArtController;
import com.mebinskaria.art.ArtGallery;
import com.mebinskaria.art.Guard;
import com.mebinskaria.art.Wall;
import com.mebinskaria.main.DataModel;

public class ArtGalleryTest {

	@Test
	public void test() {
		ArtGallery gallery = new ArtGallery(24,24,new JFrame());
		ArtController controller = ArtController.getInstance();
		DataModel model = DataModel.getInstance();
		
		assertEquals(gallery.getDrawables(),model.getDrawables());
		controller.addGuard(new Guard(24,24,4));
		controller.setWalls(new ArrayList<Wall>(Arrays.asList(new Wall(24,24,24,24))));
		assertEquals(gallery.getDrawables(),model.getDrawables());
		
	}
	
	

}
