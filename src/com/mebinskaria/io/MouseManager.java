package com.mebinskaria.io;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JFrame;

import com.mebinskaria.art.ArtGallery;
import com.mebinskaria.art.Guard;
import com.mebinskaria.art.Wall;
import com.mebinskaria.main.DataModel;
import com.mebinskaria.main.Gallery;

public class MouseManager implements MouseListener {
	JFrame frame;
	Gallery gallery;

	public MouseManager(JFrame frame, Gallery gallery) {
		this.frame = frame;
		this.gallery = gallery;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		
	
		if (gallery instanceof ArtGallery) {
			ArtGallery ag = (ArtGallery) gallery;
			Point position = frame.getMousePosition();
			position.x -= WINDOWSLINECALIBRATIONX;
			position.y -= WINDOWSLINECALIBRATIONY;
			
			if (DataModel.getMode() == DataModel.Mode.LINE) {
				Wall firstWall;
				Wall lastWall;
				ArrayList<Wall> walls = DataModel.getWalls();
				if (walls.size() > 0 && !ag.isPolygonDrawn()) {
					lastWall = walls.get(walls.size() - 1);
					firstWall = walls.get(ag.getPolygonIndex());

					if (Math.abs(firstWall.getX() - position.getX()) <= 25
							&& Math.abs(firstWall.getY() - position.getY()) <= 25) {
						walls.add(new Wall((int) lastWall.getX2(), (int) lastWall.getY2(), (int) firstWall.getX(),
								(int) firstWall.getY()));
						ag.setPolygonDrawn(true);
						ag.setX1(-1);
						ag.setY1(-1);
					} else {
						walls.add(new Wall((int) lastWall.getX2(), (int) lastWall.getY2(), (int) position.getX(),
								(int) position.getY()));
					}
					DataModel.setWalls(walls);
				} else {
					
					int x1 = ag.getX1();
					int y1 = ag.getY1();
				
					if (x1 != -1 && y1 != -1) {
						walls.add(new Wall(x1, y1, (int) position.getX(), (int) position.getY()));
						x1 = -1;
						y1 = -1;
						ag.setPolygonIndex(walls.size() - 1);
						ag.setPolygonDrawn(false);
					} else {
						ag.setX1((int) position.getX());
						ag.setY1((int) position.getY());
					}
					DataModel.setWalls(walls);
				}
		
			}
			/*
			 * CALIBRATION NUMBERS FOR WINDOWS 10, 7 
			 */
			position.y -= WINDOWSGUARDCALIBRATIONY;
			position.x -= WINDOWSGUARDCALIBRATIONX;
			if (DataModel.getMode() == DataModel.Mode.GUARD) {
				DataModel.addGuard(new Guard((int) position.getX(), (int) position.getY(), DataModel.getGuardPowerLevel()));
			}
		}
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}
	
	private final int WINDOWSLINECALIBRATIONY = 35;
	private final int WINDOWSLINECALIBRATIONX = 11;
	private final int WINDOWSGUARDCALIBRATIONY = 10;
	private final int WINDOWSGUARDCALIBRATIONX = 7;

}
