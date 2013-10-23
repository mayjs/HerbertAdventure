package de.herbert.view;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

public class PctButton extends Button{
	private Image img;
	private Image scaledImg;
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
	
	public void setBoundings(Rectangle boundings){
		super.setBoundings(boundings);
		
		//if(Math.min(boundings.getWidth(), boundings.getHeight()) != Math.min(this.boundings.getWidth(), this.boundings.getHeight()))
			scaleImage();
	}
	
	public void setImage(Image pImg){
		img = pImg;
		scaleImage();
	}
	
	public void scaleImage(){
		if(img.getWidth() > boundings.getWidth() - 2*this.gap || img.getHeight() > boundings.getHeight() - 2*this.gap){
			float scale = 1f;
			if((img.getWidth() - getBoundings().getWidth()) > (img.getHeight() - getBoundings().getHeight())){
				scale = (getBoundings().getWidth() - 2*gap) / img.getWidth();
			}else{
				scale = (getBoundings().getHeight() - 2*gap)/  img.getHeight();
			}
			scaledImg = img.getScaledCopy(scale);
		}else{
			scaledImg = img;
		}
		
		Rectangle imgBoundings = new Rectangle(boundings.getX()+gap,boundings.getY()+gap,scaledImg.getWidth(),scaledImg.getHeight());
		float dx = boundings.getCenterX() - imgBoundings.getCenterX();
		float dy = boundings.getCenterY() - imgBoundings.getCenterY();
		
		renderX = boundings.getX() + gap + dx;
		renderY = boundings.getY() + gap + dy;
	}
	
	public void update(GameContainer container, int delta)
		throws SlickException {
		super.update(container, delta);
	}
	
	public void render(GameContainer container, Graphics g)
			throws SlickException {
		// background is rendered in super.render(...)
		super.render(container, g);
		
		// make image a little bit transparent when disabled
		if(!isEnabled())
			scaledImg.setAlpha(0.3f);
		else
			scaledImg.setAlpha(1);
		
			scaledImg.draw(renderX, renderY);
	}
}
