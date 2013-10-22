package de.herbert.control;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.AppletGameContainer.Container;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import de.herbert.view.View;

public class Control extends BasicGame {

	private View view;
	private GameContainer container;
	
	private static Control instance;
	
	private Control(String title) {
		super(title);
		// TODO Auto-generated constructor stub
	}
	
	public static Control createInstance(String title){
		instance = new Control(title);
		return instance;
	}
	
	public static Control getInstance(){
		if(instance == null)
			createInstance("NULL INSTANCE");
		return instance;
	}

	@Override
	public void render(GameContainer container, Graphics g)
			throws SlickException {
		view.render(container, g);
		
	}

	@Override
	public void init(GameContainer container) throws SlickException {
		// TODO Auto-generated method stub
		this.container = container;
		view = new View(this);
		view.init(container);
	}

	@Override
	public void update(GameContainer container, int delta)
			throws SlickException {
		view.update(container, delta);
		
	}
	
	public GameContainer getContainer(){
		return container;
	}
	
	public View getView(){
		return view;
	}
	
	public static void main(String[] args) throws SlickException{
		AppGameContainer container = new AppGameContainer(Control.createInstance("Herbert"), 800, 600, false);
		
		container.start();
	}
}
