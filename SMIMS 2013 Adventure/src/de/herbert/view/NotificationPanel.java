package de.herbert.view;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

import de.herbert.control.Control;

/**
 * Abstract base class for NotificationPanels.
 * A notification Panel should self determine its width and height in generateBoundings
 * Background rendering and closing on clicks are getting handled in super.update and super.render
 * @author Kemiren
 *
 */
public abstract class NotificationPanel extends Component {
	private boolean closeOnClick;
	private Color bgColor = Color.blue;
	private Color borderColor = Color.black;
	
	protected boolean inUse = true;
	
	public NotificationPanel(float x, float y, boolean closeOnClick) {
		super(new Rectangle(x,y,0,0));
		this.closeOnClick = closeOnClick;
		generateBoundings();
	}
	
	/**
	 * Override this to calculate the width and height of boundings
	 */
	public abstract void generateBoundings();
	
	
	@Override
	public void update(GameContainer container, int delta)
			throws SlickException {
		if(closeOnClick && container.getInput().isMousePressed(Input.MOUSE_LEFT_BUTTON)) close();
	}

	@Override
	public void render(GameContainer container, Graphics g)
			throws SlickException {
		g.setColor(bgColor);
		g.fillRoundRect(boundings.getX(), boundings.getY(), boundings.getWidth(), boundings.getHeight(), 5);
		g.setColor(borderColor);
		g.drawRoundRect(boundings.getX(), boundings.getY(), boundings.getWidth(), boundings.getHeight(), 5);
	}
	
	@Override
	public boolean isStillUsed(){
		return inUse;
	}
	
	/**
	 * PopUp this panel and add it to the View
	 */
	public void popUp(){
		inUse = true;
		Control.getInstance().getView().addComponent(this);
	}
	
	public void close(){
		inUse = false;
	}

	public Color getBgColor() {
		return bgColor;
	}

	public void setBgColor(Color bgColor) {
		this.bgColor = bgColor;
	}

	public Color getBorderColor() {
		return borderColor;
	}

	public void setBorderColor(Color borderColor) {
		this.borderColor = borderColor;
	}
	
	
}
