package com.mebinskaria.art;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.mebinskaria.main.DataModel;
import com.mebinskaria.main.Drawable;

/**
 * Holds View of Document Editor for DataModel
 * COVERS SECTION 8 (Decorator Pattern, Observer Pattern)
 * COVERS SECTION 7 (Polymorphism)
 * 
 * @author Mebin Skaria
 *
 */
public class ArtTextView implements Observer {

	/**
	 * 
	 * @param width the total width of the window.
	 * @param height the total height of the window.
	 * @param x x position the window will start at.
	 * @param y y position the window will start at.
	 */
	public ArtTextView(int width, int height, int x,int y) {
		frame = new JFrame("Art TextField");
		frame.setSize(width, height);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(true);
		frame.setVisible(true);
		frame.setLocation(x,y);
		
		textArea = new JTextArea();

		scrollBar = new JScrollPane(textArea);
		scrollBar.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		JButton apply = new JButton("Apply");

		apply.addActionListener((ActionEvent event) -> controller.readUpdate(textArea.getText()));
		frame.add(apply, BorderLayout.NORTH);
		frame.add(scrollBar, BorderLayout.CENTER);
		controller.addObserver(this);
	}

	/**
	 * Updates the Art Text View when notified
	 */
	public void update() {
		s = new String();
		List<Drawable> list = dataModel.getDrawables();
		for (int i = 0; i < list.size(); i++) {
			s += list.get(i) + "\n";
		}
		textArea.setText(s);

	}
	public String getData()
	{
		return s;
	}

	private final DataModel dataModel = DataModel.getInstance();
	private final ArtController controller = ArtController.getInstance();
	private JTextArea textArea;
	private JScrollPane scrollBar;
	private JFrame frame;
	private String s;
}
