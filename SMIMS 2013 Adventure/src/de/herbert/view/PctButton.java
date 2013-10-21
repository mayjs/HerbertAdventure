package de.herbert.view;

import java.net.URL;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

public class PctButton extends Button{
	Image img;
	
	public PctButton(Rectangle boundings, String imageUrl){
		super(boundings);
		try {
			img = new Image(imageUrl);
			float scale = (float)0.5;
			if((img.getWidth() - getBoundings().getWidth()) > (img.getHeight() - getBoundings().getHeight())){
				scale = (getBoundings().getWidth() - 2*GAP) / img.getWidth();
			}else{
				scale = (getBoundings().getHeight() - 2*GAP)/  img.getHeight();
			}
			img = img.getScaledCopy(scale);
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	
	public void update(GameContainer container, int delta)
		throws SlickException {
		super.update(container, delta);
	}
	
	public void render(GameContainer container, Graphics g)
			throws SlickException {
		// background is updated in super.render(...)
		super.render(container, g);
		g.drawImage(img, getBoundings().getX() + GAP, getBoundings().getY() + GAP);
	}
}
