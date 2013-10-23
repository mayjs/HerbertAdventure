package de.herbert.view;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

public class ScrollList extends Component {

	private VerticalScrollbar scrollbar;
	
	public ScrollList(Rectangle boundings) {
		super(boundings);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void update(GameContainer container, int delta)
			throws SlickException {
		// TODO Auto-generated method stub
		scrollbar.update(container, delta);
	}

	@Override
	public void render(GameContainer container, Graphics g)
			throws SlickException {
		scrollbar.render(container, g);
		
		//Draw List Elements

	}

}
