package com.mebinskaria.art;

import javax.swing.JFrame;
import javax.swing.JLabel;

import com.mebinskaria.io.FileManager;
import com.mebinskaria.io.Screenshot;
import com.mebinskaria.main.DataModel;
import com.mebinskaria.main.DataModel.Mode;
import com.mebinskaria.main.Gallery;

public class ArtController {

	public void readPower(JLabel power) {
		try
		{
			int d = Integer.parseInt(power.getText());
			dataModel.setGuardPowerLevel(d);
		}
		catch(NumberFormatException e)
		{
			power.setText("ERROR");
		}
		
	}
	public void restartCanvas() {
		System.out.println("Erasing Canvas");
		dataModel.clearGuards();
		dataModel.clearWalls();
	}

	public void eraseGuards() {
		System.out.println("Erasing Guards");
		dataModel.clearGuards();
	}
	public void setCompute()
	{
			dataModel.compute();
	}
	
	public void saveFile()
	{
		fileManager.saveFile();
	}
	public void loadChooseFile(JFrame frame)
	{
		fileManager.chooseLoadFile(frame);
	}
	public void snapshot(Gallery gallery) {
		try {
			Screenshot.snap(gallery.getX(), gallery.getY(), gallery.getWidth() - 20, gallery.getHeight() - 10);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void setGuardRadialSpace(double value)
	{
		dataModel.setGuardRadialSpace(value);
	}

	public void setVisionPower(int visionPower) {
		dataModel.setGuardPowerLevel(visionPower);
	}
	
	public void setMode(Mode mode)
	{
		dataModel.setMode(mode);
	}
	
	
	private final DataModel dataModel = DataModel.getInstance();
	private final FileManager fileManager = FileManager.getInstance();
	
}
