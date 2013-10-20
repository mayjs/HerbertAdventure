package de.herbert.control;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import de.herbert.view.View;

public class Control extends BasicGame {

	private View view;
	
	public Control(String title) {
		super(title);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void render(GameContainer container, Graphics g)
			throws SlickException {
		view.render(container, g);
		
	}

	@Override
	public void init(GameContainer container) throws SlickException {
		// TODO Auto-generated method stub
		view = new View(this);
	}

	@Override
	public void update(GameContainer container, int delta)
			throws SlickException {
		view.update(container, delta);
		
	}
	
	public static void main(String[] args) throws SlickException{
		AppGameContainer container = new AppGameContainer(new Control("Herbert"), 800, 600, false);
		
		container.start();
	}
}
