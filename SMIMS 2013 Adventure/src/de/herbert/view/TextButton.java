package de.herbert.view;

import java.awt.Font;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.geom.Rectangle;

public class TextButton extends Button{
	
	private Color txtColor = new Color(50, 60, 70);
	
	private String text;
	private Font font;
	private TrueTypeFont ttf;
	
	private float renderX, renderY;
	
	public TextButton(Rectangle boundings, String text){
		super(boundings);
		setFont(new Font("Verdana", Font.BOLD, 20));
		setText(text);
	}
	
	public TextButton(Rectangle boundings, String text, Font font){
		super(boundings);
		setFont(font);
		setText(text);
	}
	
	public void update(GameContainer container, int delta) throws SlickException{
		super.update(container, delta);
	}
	
	public void render(GameContainer container, Graphics g)
			throws SlickException {
		super.render(container, g);
		ttf.drawString(renderX, renderY, text, txtColor);
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
		Rectangle textBoundings = new Rectangle(boundings.getX()+gap,boundings.getY()+gap, ttf.getWidth(text), ttf.getHeight(text));
		float dx = boundings.getCenterX() - textBoundings.getCenterX();
		float dy = boundings.getCenterY() - textBoundings.getCenterY();
		
		renderX = boundings.getX() + gap + dx;
		renderY = boundings.getY() + gap + dy;
	}
	
	public Font getFont(){
		return font;
	}
	
	
	public void setFont(Font font){
		this.font = font;
		ttf = new TrueTypeFont(font, true);
	}
}
