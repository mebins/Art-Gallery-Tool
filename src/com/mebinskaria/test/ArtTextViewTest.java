package com.mebinskaria.test;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;

import com.mebinskaria.art.ArtController;
import com.mebinskaria.art.ArtTextView;
import com.mebinskaria.art.Guard;
import com.mebinskaria.art.Wall;
import com.mebinskaria.main.DataModel;

public class ArtTextViewTest {

	@Test
	public void test() {
		
		
		DataModel model = DataModel.getInstance();
		ArtController controller = ArtController.getInstance();
		ArtTextView view = new ArtTextView(24,24,24,24);
		String s = view.getData();
		String result = null;
	
		assertEquals(s,result);
		result = new Guard(24,24,4).toString()+"\n";
		result += new Wall(24,24,24,24).toString() + "\n";
		controller.addGuard(new Guard(24,24,4));
		controller.setWalls(new ArrayList<Wall>(Arrays.asList(new Wall(24,24,24,24))));
		assertEquals(result,view.getData());
		
	}

}
