package com.mebinskaria.io;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

import com.mebinskaria.art.Guard;
import com.mebinskaria.art.Wall;
import com.mebinskaria.main.DataModel;
import com.mebinskaria.main.Drawable;

public class FileManager {


	public static FileManager getInstance()
	{
		return fileManager;
	}

	public void saveFile() {

		Calendar now = Calendar.getInstance();
		File desktop = new File(System.getProperty("user.home"), "Desktop\\ArtGalleryExports\\Saves");
		File saveDir = new File(System.getProperty("user.home"),
				"Desktop\\ArtGalleryExports\\Saves\\" + Screenshot.formatter.format(now.getTime()) + ".bin");
		File saveReport = new File(System.getProperty("user.home"),
				"Desktop\\ArtGalleryExports\\Saves\\Report " + Screenshot.formatter.format(now.getTime()) + ".txt");
		if (!desktop.exists()) {
			desktop.mkdirs();
		}
		/*
		 * Gives a Readable Data point of All Objects
		 */
		try {
			FileWriter fw = new FileWriter(saveReport);
			BufferedWriter bw = new BufferedWriter(fw);
			for(Drawable e: dataModel.getDrawables())
			{
				bw.write(e.toString());
				bw.newLine();
			}
			bw.close();
			fw.close();
			/*
			 * Creates Save bin file to restore All Objects
			 */
			ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(saveDir));
			os.writeObject(dataModel.getDrawables());
			os.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	public void chooseLoadFile(JFrame gallery) {
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
	@SuppressWarnings("unchecked")
	private void loadFile(File file) {
		ArrayList<Drawable> drawables = new ArrayList<>();
		try {
			ObjectInputStream os = new ObjectInputStream(new FileInputStream(file));
			Object temp = os.readObject();
			os.close();
			drawables = (ArrayList<Drawable>) temp;
			for(Drawable e : drawables)
			{
				if(e instanceof Guard)
				{	
					dataModel.addGuard((Guard) e);
				}
				if(e instanceof Wall)
				{
					dataModel.addWall((Wall) e);
				}
			}
			
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private static FileManager fileManager = new FileManager();
	private final DataModel dataModel = DataModel.getInstance();
}
