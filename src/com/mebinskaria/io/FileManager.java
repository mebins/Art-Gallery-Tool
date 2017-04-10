package com.mebinskaria.io;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

import com.mebinskaria.art.Guard;
import com.mebinskaria.art.Wall;
import com.mebinskaria.main.DataModel;
import com.mebinskaria.main.Drawable;

public class FileManager {

	public static void saveFile() {

		Calendar now = Calendar.getInstance();
		File desktop = new File(System.getProperty("user.home"), "Desktop\\ArtGalleryExports\\Saves");
		File saveDir = new File(System.getProperty("user.home"),
				"Desktop\\ArtGalleryExports\\Saves\\" + Screenshot.formatter.format(now.getTime()) + ".txt");
		if (!desktop.exists()) {
			desktop.mkdirs();
		}
		try {
			FileWriter fw = new FileWriter(saveDir);
			BufferedWriter bw = new BufferedWriter(fw);
			for (Drawable e : DataModel.getGuards()) {
				bw.write(e.toString()+"\n");
			}
			for(Drawable e: DataModel.getWalls())
			{
				bw.write(e.toString()+"\n");
			}
			bw.close();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void chooseLoadFile(JFrame gallery) {
		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new java.io.File("."));
		chooser.setCurrentDirectory(new java.io.File("."));
		chooser.setSelectedFile(new File(""));
		chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		// chooser.setAcceptAllFileFilterUsed(false);
		if (chooser.showOpenDialog(gallery) == JFileChooser.OPEN_DIALOG) {
			loadFile(chooser.getSelectedFile());
		} else {
			// do when cancel
		}
	}
	private static ArrayList<Drawable> loadFile(File file) {
		ArrayList<Drawable> drawables = new ArrayList<>();
		try {
			Scanner sc = new Scanner(file);
			while (sc.hasNextLine()) {
				String[] data = sc.nextLine().split(":");
				switch(data[0])
				{
				case "W": DataModel.addWall(new Wall(Integer.parseInt(data[1]),Integer.parseInt(data[2]),Integer.parseInt(data[3]),Integer.parseInt(data[4])));
					break;
				case "G": DataModel.addGuard(new Guard(Integer.parseInt(data[1]),Integer.parseInt(data[2]),Integer.parseInt(data[3])));
				}
				
			}
			sc.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return drawables;
	}
}
