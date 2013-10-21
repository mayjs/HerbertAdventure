package de.herbert.view;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

public class SimpleComponent extends Component {

	public SimpleComponent(Rectangle boundings) {
		super(boundings);
		// TODO Auto-generated constructor stub
	}

	float i = 0;
	boolean dir = false;
	@Override
	public void update(GameContainer container, int delta)
			throws SlickException {
		// TODO Auto-generated method stub
		if(dir) i += 1; else i -=2 ;
		if(i <= getBoundings().getX()) dir = true; 
		if(i >= this.getBoundings().getWidth() + getBoundings().getX()) dir= false;
	}

	
	@Override
	public void render(GameContainer container, Graphics g)
			throws SlickException {
		g.setColor(Color.green);
		g.fill(this.getBoundings());
		g.setColor(Color.red);
		g.drawLine(getBoundings().getX(), getBoundings().getY(), i, 45);
	}
}