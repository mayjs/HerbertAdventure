package de.herbert.view;

import java.util.LinkedList;
import java.util.List;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

import de.herbert.control.Control;

public class View {
	private Control control;
	
	private List<Component> components;
	
	public View(Control control){
		this.control = control;
		
		components = new LinkedList<Component>();
		
		//Add components here!
		//components.add(new SimpleComponent(new Rectangle(30,30,500,50)));
		
		components.add(new TxtButton(new Rectangle(300, 100, 200, 200), "Hammer.¿?"));
		components.add(new PctButton(new Rectangle(50, 100, 200, 200), ImageLoader.getImage("Geld")));
	}
	
	public void update(GameContainer container, int delta)
			throws SlickException {
		for(Component c : components){
			c.update(container, delta);
		}
	}
	
	public void render(GameContainer container, Graphics g)
			throws SlickException {
		g.setColor(Color.orange);
		g.fillRect(0, 0, 600, 600);
		for(Component c : components){
			c.render(container, g);
		}
	}
}
