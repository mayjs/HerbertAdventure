package de.herbert.control;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.AppletGameContainer.Container;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import de.herbert.model.Model;
import de.herbert.view.View;

/**
 * The Controller class containing the main method and controlling everything else.
 *
 */
public class Control extends BasicGame {
	//Note: Shouldn't we call it Controller instead of Control?
	
	private static Control instance;
	
	private View view;
	private Model model;
	private GameContainer container;
	
	private Control(String title) {
		super(title);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Get existing instance of Control, if there is none, return newly created instance using the given title.
	 * @param title	title of the game
	 * @return	only existing instance of Control
	 */
	public static Control getInstance(String title) {
		if(instance == null)
			instance = new Control(title);
		return instance;
	}
	
	public static Control getInstance(){
		return getInstance("NULL INSTANCE");
	}

	@Override
	public void render(GameContainer container, Graphics g)
			throws SlickException {
		view.render(container, g);
		
	}

	@Override
	public void init(GameContainer container) throws SlickException {
		//TODO Auto-generated method stub
		this.container = container;
		model = new Model();
		//TODO Load Player
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
		AppGameContainer container = new AppGameContainer(Control.getInstance("Herbert"), 800, 600, false);
		container.start();
	}
}
