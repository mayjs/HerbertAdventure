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
	
	public void draw(Graphics g, float x, float y){
		if(wrappedParts.size() == 0) wrappedParts = textParts;
		float textPosX = x, textPosY = y;
		for(Text t: wrappedParts){
			g.setColor(t.getColor());
			g.setFont(t.getSlickFont());
			String text = t.getText();
			String sub;
			int enterInd = -1;
			while((enterInd = text.indexOf("\\n")) > -1){
				sub = text.substring(0, enterInd);
				text = text.substring(enterInd + 1, text.length() - 1);
				g.drawString(sub, textPosX, textPosY);
				textPosY += g.getFont().getHeight(sub);
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
		for(Text t : wrappedParts){
			c = 0;
			
			height = Math.max(t.getSlickFont().getHeight(t.getText()), height);
			sub = t.getText();
			while((ind = sub.indexOf("\\n")) > -1){
				c++;
				sub = sub.substring(ind + 1, sub.length() - 1);
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
		boundings.setWidth(width);
		boundings.setHeight(finalHeight);
		return boundings;
	}

	public float getHeight() {
		return calcBoundings().getHeight();
	}
}
