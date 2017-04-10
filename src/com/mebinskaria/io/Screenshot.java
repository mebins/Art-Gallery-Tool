package com.mebinskaria.io;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.imageio.ImageIO;

public class Screenshot {
	public static SimpleDateFormat formatter = new SimpleDateFormat("dd mm yyyy hh mm ss a");
	
	/*
	 * @param positionX where the window.X is on the screen.
	 * @param positionY where the window.Y is on the screen.
	 * @param width of the snap, the width of the window.
	 * @param height of the snap, the height of the window.
	 */
	public static void snap(int positionX, int positionY, int width, int height) throws Exception
	{
		File desktop = new File(System.getProperty("user.home"), "Desktop\\ArtGalleryExports");
		desktop.mkdir();
		Calendar now = Calendar.getInstance();
		Robot robot = new Robot();
		BufferedImage screenShot = robot.createScreenCapture(new Rectangle(positionX+10, positionY, width,height));
		ImageIO.write(screenShot, "JPG", new File(System.getProperty("user.home"), "Desktop\\ArtGalleryExports\\"+formatter.format(now.getTime())+".jpg"));
	}
	
	
}
