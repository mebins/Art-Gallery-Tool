package com.mebinskaria.main;

import java.awt.Graphics;
import java.io.Serializable;

/**
 * This Object can be drawn and saved through Object Serialization.
 * COVERS SECTION 9 (Serialization)
 * COVERS SECTION 7 (Exhibits Polymorphism)
 * COVERS COHEISION this is because all the methods make sense in context logically supporting a single purpose.
 * COVERS COMPLETENESS Allows all operations that are part of what the class represents, because all drawable objects are done through render and update in this program
 * COVERS CONVENIENCE Allows easy implementation of drawing of the objects and also serialization without another step.
 * COVERS CLARITY This Interface helps with clarity because all graphics can only be drawn in render and all logic updates will happen in the update method, seperating tasks, creating less buggy code.
 * MAKES CONSISTENCY for all objects that are drawn in the canvas to have same methods.
 * @author Mebin Skaria
 *
 */
public interface Drawable extends Serializable {
	
	/**
	 * 
	 * @param G What to draw every Frame.
	 */
	public void render(Graphics G);
	/**
	 * What to update every Frame.
	 */
	public void update(); 
	
}
