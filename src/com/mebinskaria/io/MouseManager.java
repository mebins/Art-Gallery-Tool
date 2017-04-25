package com.mebinskaria.io;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JFrame;

import com.mebinskaria.art.ArtGallery;
import com.mebinskaria.art.Guard;
import com.mebinskaria.art.Wall;
import com.mebinskaria.main.DataModel;
import com.mebinskaria.main.DataModel.Mode;
import com.mebinskaria.main.Gallery;

public class MouseManager implements MouseListener {
	private JFrame frame;
	private Gallery gallery;

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

	public void mouseReset() {
		if (gallery instanceof ArtGallery) {
			ArtGallery ag = (ArtGallery) gallery;

			ag.setX1(-1);
			ag.setY1(-1);
		}
	}

	public void mouseRender(Graphics g) {
		if(shouldRender)
		{
			if (gallery instanceof ArtGallery) {
				ArtGallery ag = (ArtGallery) gallery;
				{
					if(ag.getX1() != 1 && ag.getY1() != -1)
					{
						g.setColor(Color.black);
						g.fillOval(ag.getX1()-DISPLACEMENT,ag.getY1()-DISPLACEMENT,CIRCLE_SIZE,CIRCLE_SIZE);
					}
				}
			}
		}
		
	}

	@Override
	public void mousePressed(MouseEvent e) {

		if (gallery instanceof ArtGallery) {
			ArtGallery ag = (ArtGallery) gallery;
			Point position = frame.getMousePosition();
			position.x -= WINDOWSLINECALIBRATIONX;
			position.y -= WINDOWSLINECALIBRATIONY;

			if (dataModel.getMode() == Mode.LINE) {
				Wall firstWall;
				Wall lastWall;
				ArrayList<Wall> walls = dataModel.getWalls();
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
					dataModel.setWalls(walls);
				} else {

					int x1 = ag.getX1();
					int y1 = ag.getY1();

					if (x1 != -1 && y1 != -1) {
						walls.add(new Wall(x1, y1, (int) position.getX(), (int) position.getY()));
						x1 = -1;
						y1 = -1;
						ag.setPolygonIndex(walls.size() - 1);
						ag.setPolygonDrawn(false);
						shouldRender = false;
					} else {
						ag.setX1((int) position.getX());
						ag.setY1((int) position.getY());
						shouldRender = true;
					}
					dataModel.setWalls(walls);
				}

			}
			/*
			 * CALIBRATION NUMBERS FOR WINDOWS (10, 7)
			 */
			position.y -= WINDOWSGUARDCALIBRATIONY;
			position.x -= WINDOWSGUARDCALIBRATIONX;
			if (dataModel.getMode() == Mode.GUARD) {
				dataModel.addGuard(
						new Guard((int) position.getX(), (int) position.getY(), dataModel.getGuardPowerLevel()));
			}
		}

	}

	
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}
	private boolean shouldRender = false;
	private final int WINDOWSLINECALIBRATIONY = 35;
	private final int WINDOWSLINECALIBRATIONX = 11;
	private final int WINDOWSGUARDCALIBRATIONY = 10;
	private final int WINDOWSGUARDCALIBRATIONX = 7;
	private final int CIRCLE_SIZE = 10;
	private final int DISPLACEMENT = CIRCLE_SIZE / 2;
	private final DataModel dataModel = DataModel.getInstance();
}
