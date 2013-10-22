package de.herbert.view;

import java.awt.Font;
import java.io.File;
import java.util.LinkedList;
import java.util.List;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.w3c.dom.Element;

import de.herbert.control.Control;
import de.herbert.parser.Text;
import de.herbert.parser.TextParser;

public class TextPanel_ext extends TextPanel{
	Font f;
	List<Text> textList = new LinkedList<Text>();
	
	public TextPanel_ext(float x, float y, boolean closeOnClick, File f) {
		super(x, y, closeOnClick, Control.getInstance().getContainer());
		textList = TextParser.getInstance().parseText(f);
		generateBoundings();
	}
	
	public void setFont(Font f){
		this.f = f;
	}

	float textPos = 0f;
	@Override
	public void render(GameContainer container, Graphics g)
			throws SlickException {
		super.render(container, g);
		textPos = gap;
		for(Text t: textList){
			g.setColor(t.getColor());
			g.setFont(t.getSlickFont());
			String text = t.getText();
			g.drawString(text, boundings.getX() + textPos, boundings.getY() + gap);
			textPos += g.getFont().getWidth(text);
		}
	}
	
	@Override
	public void generateBoundings() {
		if(textList == null)return;
			float width = 0f;
			float height = 0f;
			for(Text t : textList){
				width +=  t.getSlickFont().getWidth(t.getText());
				height = Math.max(t.getSlickFont().getHeight(t.getText()), height);
			}
			boundings.setWidth(width + 2*gap);
			boundings.setHeight(height + 2*gap);
	}
	
	public static TextPanel_ext popUp(float x, float y, File f, boolean autoClose){
		TextPanel_ext panel = new TextPanel_ext(x, y, autoClose, f);
		panel.popUp();
		return panel;
	}
}
