package de.herbert.view;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

public class SimpleComponent extends Component {

	public SimpleComponent(Rectangle boundings) {
		super(boundings);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void update(GameContainer container, int delta)
			throws SlickException {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(GameContainer container, Graphics g)
			throws SlickException {
		g.fill(this.getBoundings());

	}

}
