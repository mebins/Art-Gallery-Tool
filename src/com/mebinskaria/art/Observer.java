package com.mebinskaria.art;

/**
 * 
 *  COVERS SECTION 7 (Exhibits Polymorphism)
 *  COVERS SECTION 8 (Observer Pattern)
 * This object can be attached to a object and be notified when something is changed.
 * @author Mebin Skaria
 *
 */
public interface Observer {
	
	/**
	 * What to update when something has changed.
	 */
	public void	update();

}
