package de.herbert.view;

import java.awt.Font;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

import de.herbert.control.Control;
import de.herbert.parser.FormattedText;

public class FormattedTextPanel extends TextPanel{
	Font f;
	FormattedText text;
	
	public FormattedTextPanel(float x, float y, boolean closeOnClick, FormattedText text) {
		super(x, y, closeOnClick, Control.getInstance().getContainer());
		this.text = text;
		generateBoundings();
	}
	
	public void setFont(Font f){
		this.f = f;
	}
	
	public void setText(FormattedText text){
		this.text = text;
	}

	@Override
	public void render(GameContainer container, Graphics g)
			throws SlickException {
		super.render(container, g);
		text.draw(g, boundings.getX() + gap, boundings.getY() + gap);
	}
	
	@Override
	public void generateBoundings() {
		if(text == null) return;
		Rectangle b = text.getBoundings();
		boundings.setWidth(b.getWidth() + 2*gap);
		boundings.setHeight(b.getHeight() + 2*gap);
	}
	
	public static FormattedTextPanel popUp(float x, float y, FormattedText text, boolean autoClose){
		FormattedTextPanel panel = new FormattedTextPanel(x, y, autoClose, text);
		panel.popUp();
		return panel;
	}
}
