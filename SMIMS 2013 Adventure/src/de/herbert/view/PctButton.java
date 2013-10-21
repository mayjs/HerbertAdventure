package de.herbert.view;

import java.net.URL;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

public class PctButton extends Button{
	private Image img;
	private float renderX, renderY;
	
	public PctButton(Rectangle boundings, Image image){
		super(boundings);
//		img = image;
//		float scale = (float)0.5;
//		if((img.getWidth() - getBoundings().getWidth()) > (img.getHeight() - getBoundings().getHeight())){
//			scale = (getBoundings().getWidth() - 2*GAP) / img.getWidth();
//		}else{
//			scale = (getBoundings().getHeight() - 2*GAP)/  img.getHeight();
//		}
//		img = img.getScaledCopy(scale);
		setImage(image);
	}
	
	public void setImage(Image pImg){
		img = pImg;
		if(img.getWidth() > boundings.getWidth() - 2*this.GAP || img.getHeight() > boundings.getHeight() - 2*this.GAP){
			float scale = 1f;
			if((img.getWidth() - getBoundings().getWidth()) > (img.getHeight() - getBoundings().getHeight())){
				scale = (getBoundings().getWidth() - 2*GAP) / img.getWidth();
			}else{
				scale = (getBoundings().getHeight() - 2*GAP)/  img.getHeight();
			}
			img = img.getScaledCopy(scale);
		}
		
		Rectangle imgBoundings = new Rectangle(boundings.getX()+GAP,boundings.getY()+GAP,img.getWidth(),img.getHeight());
		float dx = boundings.getCenterX() - imgBoundings.getCenterX();
		float dy = boundings.getCenterY() - imgBoundings.getCenterY();
		
		renderX = boundings.getX() + GAP + dx;
		renderY = boundings.getY() + GAP + dy;
	}
	
	public void update(GameContainer container, int delta)
		throws SlickException {
		super.update(container, delta);
	}
	
	public void render(GameContainer container, Graphics g)
			throws SlickException {
		// background is rendered in super.render(...)
		super.render(container, g);
//		g.drawImage(img, getBoundings().getX() + GAP, getBoundings().getY() + GAP);
		g.drawImage(img, renderX, renderY);
	}
}
