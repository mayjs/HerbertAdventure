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

	float textPosX = 0f;
	float textPosY = 0f;
	@Override
	public void render(GameContainer container, Graphics g)
			throws SlickException {
		super.render(container, g);
		textPosX = gap;
		textPosY = gap;
		for(Text t: text.getTextParts()){
			g.setColor(t.getColor());
			g.setFont(t.getSlickFont());
			String text = t.getText();
			String sub;
			int enterInd = -1;
			while((enterInd = text.indexOf("\\n")) > -1){
				sub = text.substring(0, enterInd);
				text = text.substring(enterInd + 2, text.length() - 1);
				g.drawString(sub, boundings.getX() + textPosX, boundings.getY() + textPosY);
				textPosY += g.getFont().getHeight(sub);
			}
			g.drawString(text, boundings.getX() + textPosX, boundings.getY() + textPosY);
			textPosX += g.getFont().getWidth(text);
		}
	}
	
	@Override
	public void generateBoundings() {
		if(text == null )return;
			float width = 0f;
			float height = 0f;
			float finalHeight = 0f;
			int ind = 0;
			int c = 0;
			boolean b = true;
			String sub;
			for(Text t : text.getTextParts()){
				c = 0;
				
				height = Math.max(t.getSlickFont().getHeight(t.getText()), height);
				sub = t.getText();
				while((ind = sub.indexOf("\\n")) > -1){
					c++;
					sub = sub.substring(ind + 2, sub.length()-1);
					float w = t.getSlickFont().getWidth(sub);
					if(b) width += w;
					b =false;
					width =  w > width?w:width;
				}
				if(c > 0)
					finalHeight += c * height;
				else
					width += t.getSlickFont().getWidth(sub);
			}
			finalHeight += height;
			boundings.setWidth(width + 2*gap);
			boundings.setHeight(finalHeight + 2*gap);
	}
	
	public static FormattedTextPanel popUp(float x, float y, FormattedText text, boolean autoClose){
		FormattedTextPanel panel = new FormattedTextPanel(x, y, autoClose, text);
		panel.popUp();
		return panel;
	}
}
