package de.herbert.view;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

public abstract class Component {
	protected Rectangle boundings;
	
	public Component(Rectangle boundings){
		this.boundings = boundings;
	}
		
	public Rectangle getBoundings() {
		return boundings;
	}

	public void setBoundings(Rectangle boundings) {
		this.boundings = boundings;
	}


	public abstract void update(GameContainer container, int delta)
			throws SlickException;
	public abstract void render(GameContainer container, Graphics g)
			throws SlickException;
	
	public boolean isStillUsed(){
		return true;
	}
}
