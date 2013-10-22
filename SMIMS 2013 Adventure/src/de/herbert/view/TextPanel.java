package de.herbert.view;


import org.newdawn.slick.AppletGameContainer.Container;
import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import de.herbert.control.Control;

public class TextPanel extends Panel {

	private Font font;
	private String text = "";
	private float gap = 10f;
	
	public TextPanel(float x, float y, boolean closeOnClick, GameContainer container) {
		super(x, y, closeOnClick);
		font = container.getGraphics().getFont();
		generateBoundings();
	}

	@Override
	public void generateBoundings() {
		if(font != null){
			boundings.setWidth(font.getWidth(text) + 2*gap);
			boundings.setHeight(font.getHeight(text) + 2*gap);
		}
	}
	
	@Override
	public void render(GameContainer container, Graphics g)
			throws SlickException {
		super.render(container, g);
		g.setFont(font);
		g.setColor(Color.white);
		g.drawString(text, boundings.getX() + gap, boundings.getY() + gap);
	}

	public Font getFont() {
		return font;
	}

	public void setFont(Font font) {
		this.font = font;
		generateBoundings();
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
		generateBoundings();
	}

	public float getGap() {
		return gap;
	}

	public void setGap(float gap) {
		this.gap = gap;
		generateBoundings();
	}
	
	public static TextPanel popUp(float x, float y, Color bg, Color border, String text, boolean autoClose){
		TextPanel panel = new TextPanel(x,y,autoClose,Control.getInstance().getContainer());
		panel.setText(text);
		panel.setBgColor(bg);
		panel.setBorderColor(border);
		panel.popUp();
		return panel;
	}
	
	public static TextPanel popUp(float x, float y, String text, boolean autoClose){
		return popUp(x,y,Color.blue,Color.black,text,autoClose);
	}
}
