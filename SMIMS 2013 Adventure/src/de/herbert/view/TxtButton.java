package de.herbert.view;

import java.awt.Font;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.geom.Rectangle;

public class TxtButton extends Button{
	
	Color txtColor = new Color(50, 60, 70);
	
	String text;
	Font font;
	TrueTypeFont ttf;
	
	public TxtButton(Rectangle boundings, String text){
		super(boundings);
		setText(text);
		setFont(new Font("Verdana", Font.BOLD, 20));
	}
	
	public TxtButton(Rectangle boundings, String text, Font font){
		super(boundings);
		setText(text);
		setFont(font);
	}
	
	public void update(GameContainer container, int delta) throws SlickException{
		super.update(container, delta);
	}
	
	public void render(GameContainer container, Graphics g)
			throws SlickException {
		super.render(container, g);
		ttf.drawString(getBoundings().getX(), getBoundings().getY(), text, txtColor);
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	public Font getFont(){
		return font;
	}
	
	
	public void setFont(Font font){
		this.font = font;
		ttf = new TrueTypeFont(font, true);
	}
}
