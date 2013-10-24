package de.herbert.view;

import java.awt.Font;
import java.io.File;
import java.util.LinkedList;
import java.util.List;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.geom.Rectangle;
import org.w3c.dom.Element;

import de.herbert.control.Control;
import de.herbert.parser.FormattedText;
import de.herbert.parser.Text;
import de.herbert.parser.TextParser;

public class FormattedTextPanel extends TextPanel{
	Font f;
	FormattedText text;
	boolean autoSize;
	
	public FormattedTextPanel(float x, float y, boolean closeOnClick, FormattedText text) {
		super(x, y, closeOnClick, Control.getInstance().getContainer());
		this.text = text;
		generateBoundings();
		autoSize = true;
	}
	
	public FormattedTextPanel(float x, float y, boolean closeOnClick, FormattedText text, boolean autoSize) {
		super(x, y, closeOnClick, Control.getInstance().getContainer());
		this.text = text;
		this.autoSize = autoSize;
		generateBoundings();
		gap = 5;
	}
	
	public FormattedTextPanel(Rectangle boundings, boolean closeOnClick, FormattedText text) {
		super(boundings.getX(), boundings.getY(), closeOnClick, Control.getInstance().getContainer());
		this.text = text;
		autoSize = false;
		this.boundings = boundings;
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
		Rectangle b = text.calcBoundings();
		boundings.setWidth(b.getWidth() + 2*gap);
		boundings.setHeight(b.getHeight() + 2*gap);
	}
	
	public static FormattedTextPanel popUp(float x, float y, FormattedText text, boolean autoClose){
		FormattedTextPanel panel = new FormattedTextPanel(x, y, autoClose, text);
		panel.popUp();
		return panel;
	}
}
