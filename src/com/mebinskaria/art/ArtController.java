package com.mebinskaria.art;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JLabel;

import com.mebinskaria.io.FileManager;
import com.mebinskaria.io.Screenshot;
import com.mebinskaria.main.DataModel;
import com.mebinskaria.main.DataModel.Mode;
import com.mebinskaria.main.Gallery;

/**
 * COVERS SECTION 8 (Singleton Pattern)
 * The Controller in the MVC Design, solely responsible for any change Occurring
 * in the Data Model.
 */
public class ArtController {

	/**
	 * Private Constructor because Singleton Pattern.
	 */
	private ArtController() {
	}

	/**
	 * 
	 * @return The Single instance of the ArtController.
	 */
	public static ArtController getInstance() {
		return instance;
	}

	/**
	 * 
	 * @param power
	 *            The JLabel the user will use to type in the amount of vision
	 *            the Guards can see.
	 */
	public void readPower(JLabel power) {
		try {
			int d = Integer.parseInt(power.getText());
			dataModel.setGuardPowerLevel(d);
		} catch (NumberFormatException e) {
			power.setText("ERROR");
		}

	}

	/**
	 * Restarts the Canvas, erases: guards, walls.
	 */
	public void restartCanvas() {
		System.out.println("Erasing Canvas");
		dataModel.clearGuards();
		dataModel.clearWalls();
		dataModel.setX(-1);
		dataModel.setY(-1);
		notifyObservers();
	}

	/**
	 * Sends command to DataModel and notifies all listeners.
	 * 
	 * @param list Collection of Wall Objects
	 */
	public void setWalls(List<Wall> list) {
		dataModel.setWalls(list);
		notifyObservers();
	}

	/**
	 * Sends command to DataModel and notifies all listeners.
	 * 
	 * @param g Guard Object to add to the Collection.
	 * 	 */
	public void addGuard(Guard g) {
		dataModel.addGuard(g);
		notifyObservers();
	}

	/**
	 * Sends command to DataModel and notifies all listeners. Erases all Guards.
	 */
	public void eraseGuards() {
		System.out.println("Erasing Guards");
		dataModel.clearGuards();
		notifyObservers();
	}

	/**
	 * Sets the Compute Mode On or Off, activating the Guards computation code
	 * (which is later cached).
	 */
	public void setCompute() {
		dataModel.compute();
	}

	/**
	 * Sends Command to the File Manager class to save the current state.
	 */
	public void saveFile() {
		fileManager.saveFile();
	}

	/**
	 * Chooses the File to load.
	 * 
	 * @param frame  which frame to make not focusable until a file is picked.
	 */
	public void loadChooseFile(JFrame frame) {
		fileManager.chooseLoadFile(frame);
		notifyObservers();
	}

	/**
	 * Takes a picture of the screen
	 * @param gallery which gallery to focus on
	 */
	public void snapshot(Gallery gallery) {
		try {
			Screenshot.snap(gallery.getX(), gallery.getY(), gallery.getWidth() - 20, gallery.getHeight() - 10);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param value The amount of space (in radian) per line)
	 */
	public void setGuardRadialSpace(double value) {
		dataModel.setGuardRadialSpace(value);
	}

	/**
	 * 
	 * @param visionPower how many walls the guards can see through.
	 */
	public void setVisionPower(int visionPower) {
		dataModel.setGuardPowerLevel(visionPower);

	}

	/**
	 * 
	 * @param mode which mode to draw in (Line, Guard, Etc)
	 */
	public void setMode(Mode mode) {
		dataModel.setMode(mode);

	}

	/**
	 * Updates the Model by the String
	 * 
	 * @param s The Objects to be created.
	 */
	public void readUpdate(String s) {
		Scanner sc = new Scanner(s);

		dataModel.clearGuards();
		dataModel.clearWalls();
		while (sc.hasNextLine()) {
			String input[] = sc.nextLine().split(":");
			if (input[0].equals("G")) {
				int[] dinput = { Integer.parseInt(input[1]), Integer.parseInt(input[2]), Integer.parseInt(input[3]) };
				dataModel.addGuard(new Guard(dinput[0], dinput[1], dinput[2]));
			}
			if (input[0].equals("W")) {
				int[] dinput = { Integer.parseInt(input[1]), Integer.parseInt(input[2]), Integer.parseInt(input[3]),
						Integer.parseInt(input[4]) };
				dataModel.addWall(new Wall(dinput[0], dinput[1], dinput[2], dinput[3]));
			}

		}
		sc.close();
		
		notifyObservers();

	}

	/**
	 * 
	 * @param observer Adds Objects to notify when something is updated by the controller that the objects need to know.
	 */
	public void addObserver(Observer observer) {
		observers.add(observer);
	}

	/**
	 * notifies all the listeners of this object.
	 */
	public void notifyObservers() {
		for (Observer o : observers) {
			o.update();
		}
	}
	private final static ArtController instance = new ArtController();
	private final ArrayList<Observer> observers = new ArrayList<>();
	private final DataModel dataModel = DataModel.getInstance();
	private final FileManager fileManager = FileManager.getInstance();
}
