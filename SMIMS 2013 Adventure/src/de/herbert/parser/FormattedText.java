package de.herbert.parser;


import java.awt.Font;
import java.util.LinkedList;
import java.util.List;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;
import org.w3c.dom.Element;

public class FormattedText {
	private List<Text> textParts = new LinkedList<Text>();
	private List<Text> wrappedParts = new LinkedList<Text>();
	
	public FormattedText(Element e){
		textParts = TextParser.getInstance().parseText(e).getTextParts();
	}
	
	public FormattedText(String text, Font f){
		textParts.add(new Text(text, f, Color.black));
	}
	
	public FormattedText(String text){
		textParts.add(new Text(text, new Font("Verdana", 0, 12), Color.black));
	}
	
	public FormattedText(List<Text> textParts){
		this.textParts = textParts;
	}
	
	public List<Text> getTextParts(){
		return textParts;
	}
	
	public void addTextPart(Text part){
		textParts.add(part);
	}
	
	public void wrapToWidth(float width){
		float startWidth = 0;
		wrappedParts = textParts;
		for(Text part : wrappedParts){
			part.wrapToWidth(width, startWidth);
			if(part.getCountOfLines() > 1)
				startWidth = part.getLastLine().getWidth();
			else
				startWidth += part.getLastLine().getWidth();
		}
	}
	
	public void draw(Graphics g, float x, float y){
		if(wrappedParts.size() == 0) wrappedParts = textParts;
		float textPosX = x, textPosY = y;
		for(Text t: wrappedParts){
			g.setColor(t.getColor());
			g.setFont(t.getSlickFont());
			String text = t.getText();
			String sub;
			int enterInd = -1;
			while((enterInd = text.indexOf("\n")) > -1){
				// draw line
				sub = text.substring(0, enterInd);
				if(enterInd >= text.length() - 1)text="";
				else text = text.substring(enterInd + 1, text.length());
				g.drawString(sub, textPosX, textPosY);
				
				// jump into next line
				textPosY += g.getFont().getHeight(sub);
				textPosX= x;
			}
			g.drawString(text, textPosX, textPosY);
			textPosX += g.getFont().getWidth(text);
		}
	}
	
	public Rectangle calcBoundings(){
		if(wrappedParts.size() == 0) wrappedParts = textParts;
		Rectangle boundings = new Rectangle(0, 0, 0, 0);
		float width = 0f;
		float height = 0f;
		float finalHeight = 0f;
		int ind = 0;
		int c = 0;
		boolean b = true;
		String sub;
		float w = 0;
		for(Text t : wrappedParts){
			c = 0;
			
			height = Math.max(t.getSlickFont().getHeight(t.getText()), height);
			sub = t.getText();
			while((ind = sub.indexOf("\n")) > -1){
				c++;
				w = t.getSlickFont().getWidth(sub.substring(0, ind));
				if(ind >= sub.length() - 1)sub="";
				else sub = sub.substring(ind + 1, sub.length());
				
				if(b) width += w;
				b =false;
				width =  Math.max(width, w);
				w=0;
			}
			if(c > 0)
				finalHeight += c * height;
			else
				w += t.getSlickFont().getWidth(sub);
			width =  Math.max(width, w);
		}
		finalHeight += height;
		boundings.setWidth(width);
		boundings.setHeight(finalHeight);
		return boundings;
	}

	public float getHeight() {
		return calcBoundings().getHeight();
	}
	
	public float getWidth(){
		return calcBoundings().getWidth();
	}
}
