package de.herbert.view;

import java.io.File;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

import de.herbert.control.Control;

public class View implements ButtonListener{
	private Control control;
	
	private List<Component> components;
	
	public View(Control control){
		this.control = control;
		components = new LinkedList<Component>();
	}
	
	public void init(GameContainer container) throws SlickException {
		//Add components here!
		Component c = new TxtButton(new Rectangle(300, 100, 200, 200), "Hammer.¿?");
		components.add(c);
		

		Button b1 = new TxtButton(new Rectangle(50, 350, 20, 20), "1");
		b1.addButtonListener(this, "Button1");
		Button b2 = new TxtButton(new Rectangle(70, 350, 20, 20), "2");
		b2.addButtonListener(this);
		//Button tb = new TxtButton(new Rectangle(300, 100, 200, 200), "Hammer.¿?");
		//tb.addButtonListener(this);
		Button pctb = new PctButton(new Rectangle(50, 100, 200, 200), ImageLoader.getImage("Geld"));
		pctb.addButtonListener(this);
		//components.add(tb);
		components.add(pctb);
		components.add(b1);
		components.add(b2);
		
		// to test uniqueID generation: 
		for(int i = 0; i < 200; i++){
			Button b = new Button(new Rectangle(0, 0, 0, 0));
			System.out.println(b.getUniqueID());
		}
		Button b3 = new TxtButton(new Rectangle(50, 400, 100, 100), "idzeugs");
		b3.addButtonListener(this);
		components.add(b3);
		
		addAnnotation(c, "HAMMER!");
		
		components.add(new PctButton(new Rectangle(50, 100, 200, 200), ImageLoader.getImage("Geld")));
		
		TextPanel.popUp(100, 500, "Hallo Welt!\r\nDas ist ein Text.",true);
		
		try {
			TextPanel_ext.popUp(300, 500, new File(View.class.getResource("/SMIMS 2013 Adventure/src/de/herbert/parser/exapmle.xml").toURI()), false); // irgendwie findet der die Date nicht :(
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void update(GameContainer container, int delta)
			throws SlickException {
		for(Component c : components){
			if(!c.isStillUsed()){
				components.remove(c);
				continue;
			}
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

	@Override
	public void buttonClicked(String buttonId) {
		System.out.println("Button " + buttonId + " clicked.");
	}
	
	public void addComponent(Component component){
		components.add(component);
	}
	
	public void addAnnotation(Component c, String t){
		components.add(new Annotation(c,t,500));
	}
}
