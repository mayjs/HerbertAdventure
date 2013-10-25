package de.herbert.view;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

import de.herbert.parser.FormattedText;

public class FormattedTextButton extends Button{

	FormattedText text;
	float renderX = 0f;
	float renderY = 0f;
	
	public FormattedTextButton(Rectangle boundings, FormattedText text) {
		super(boundings);
		setStyle(ButtonStyle.BS_Default);
		this.text = text;
	}
	
	public void setBoundings(Rectangle boundings){
		super.setBoundings(boundings);
		calcRenderCoords();
	}
	
	public void calcRenderCoords(){
		Rectangle imgBoundings = new Rectangle(boundings.getX()+gap,boundings.getY()+gap,text.getWidth(),text.getHeight());
		float dx = boundings.getCenterX() - imgBoundings.getCenterX();
		float dy = boundings.getCenterY() - imgBoundings.getCenterY();
		
		renderX = boundings.getX() + gap + dx;
		renderY = boundings.getY() + gap + dy;
	}
	
	
	@Override
	public void render(GameContainer container, Graphics g)
			throws SlickException {
		super.render(container, g);
		text.draw(g, renderX, renderY);
	}
	
	public void setText(FormattedText text){
		this.text = text;
		calcRenderCoords();
	}

}
