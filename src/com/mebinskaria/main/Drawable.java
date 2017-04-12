package com.mebinskaria.main;

import java.awt.Graphics;
import java.io.Serializable;

public interface Drawable extends Serializable {
	
	public void render(Graphics G);
	public void update(); 
	
}
