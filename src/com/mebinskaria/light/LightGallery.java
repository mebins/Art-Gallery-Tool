package com.mebinskaria.light;

import java.awt.Graphics;

import javax.swing.JFrame;

import com.mebinskaria.main.Gallery;

/**
 * 
 *  COVERS SECTION 10 (Producer and Consumer Thread)
 * @author Mebin Skaria
 *
 */

public class LightGallery extends Gallery {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	final Processor processor = new Processor();
	Thread t1 = new Thread(new Runnable()
			{

				@Override
				public void run() {
					try {
						processor.produce();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
			});
	Thread t2 = new Thread(new Runnable()
	{

		@Override
		public void run() {
			try {
				processor.consume();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	});
	
	public LightGallery(int width, int height, JFrame frame) {
		super("Light Gallery - Alpha", width, height, frame);
		initialize();
	}

	
	private void initialize() {
		// TODO Auto-generated method stub
		
		t1.start();
		t2.start();
		
		
		try {
			t1.join();
			t2.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(Graphics g) {
		// TODO Auto-generated method stub
		
	}


}
