package de.herbert.view;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

/**
 * Abstract definition for Components used in the View
 * @author Kemiren
 *
 */
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
	
	/**
	 * If false, the View will remove the Component in the next update
	 * @return If the control is still needed
	 */
	public boolean isStillUsed(){
		return true;
	}
}
