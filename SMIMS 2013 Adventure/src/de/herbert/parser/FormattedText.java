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
	Rectangle boundings = null;
	
	public FormattedText(Text text){
		addTextPart(text);
	}
	
	public FormattedText(Element e){
		wrappedParts = textParts = TextParser.getInstance().parseText(e).getTextParts();
	}
	
	public FormattedText(String text, Font f){
		textParts.add(new Text(text, f, Color.black));
		wrappedParts = textParts;
	}
	
	public FormattedText(String text){
		textParts.add(new Text(text, new Font("Verdana", 0, 12), Color.black));
		wrappedParts = textParts;
	}
	
	public FormattedText(List<Text> textParts){
		wrappedParts = this.textParts = textParts;
	}
	
	public int length(){
		int length = 0;
		for(Text l : wrappedParts)
			length += l.length();
		return length;
	}
	
	public List<Text> getTextParts(){
		return textParts;
	}
	
	public List<FormattedText> getLines(){
		List<FormattedText> lines = new LinkedList<FormattedText>();
		FormattedText lastLine = new FormattedText(Text.emptyText);
		for(Text part : wrappedParts){
			List<Text> parts = part.getLines();
			lastLine.addTextPart(parts.get(0));
			if(part.getCountOfLines()>1){
				lines.add(lastLine);
				lastLine = new FormattedText(Text.emptyText);
			}
			int i = 1;
			for(; i < parts.size() - 1; i++){
				lines.add(new FormattedText(parts.get(i)));
			}
			if(i > 1 || parts.size() == 2)
				lastLine = new FormattedText(parts.get(i));
		}
		lines.add(lastLine);
		return lines;
	}
	
	public void addTextPart(Text part){
		textParts.add(part);
		wrappedParts.add(part);
		boundings = null;	// need to be recalculated
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
		boundings = null;
	}
	
//	public void draw(Graphics g, float x, float y){
//		float textPosX = x, textPosY = y;
//		for(Text t: wrappedParts){
//			g.setColor(t.getColor());
//			g.setFont(t.getSlickFont());
//			String text = t.getText();
//			String sub;
//			int enterInd = -1;
//			while((enterInd = text.indexOf("\n")) > -1){
//				// draw line
//				sub = text.substring(0, enterInd);
//				if(enterInd >= text.length() - 1)text="";
//				else text = text.substring(enterInd + 1, text.length());
//				g.drawString(sub, textPosX, textPosY);
//				
//				// jump into next line
//				textPosY += g.getFont().getHeight(sub);
//				textPosX= x;
//			}
//			g.drawString(text, textPosX, textPosY);
//			textPosX += g.getFont().getWidth(text);
//		}
//	}
//	
	public void draw(Graphics g, float x, float y){
		float textX = x, textY = y;
		List<FormattedText> lines = getLines();
		
		if(lines.size() == 1 || (lines.size() == 2 && lines.get(1).length() == 0)){ // draw one line, index is 2 because getLines returns 2 if the line has a \n at the end
			FormattedText line = lines.get(0);
			float width = line.getWidth(), height = line.getHeight();
			for(Text text : line.getTextParts()){
				g.setColor(text.getColor());
				g.setFont(text.getSlickFont());
				g.drawString(text.getText(), textX, textY + height - text.getHeight());
				textX += text.getWidth();
			}
			return;
		}
		
		// more than one line
		for(FormattedText line : lines){
			// draw line
			line.draw(g, textX, textY);
			// jump into next line
			textY += line.getHeight();
		}
	}
	
	public void calcBoundings(){
		
		float width = 0f;
		float height = 0f;
		
		List<FormattedText> lines = getLines();
		
		if(lines.size() == 1){
			for(Text part : wrappedParts){
				height = height > part.getHeight() ? height : part.getHeight();
				width += part.getWidth();
			}
		}else{
			for(FormattedText text : lines){
				width = width > text.getBoundings().getWidth() ? width : text.getBoundings().getWidth();
				height += text.getBoundings().getHeight();
			}
		}
		boundings = new Rectangle(0, 0, width, height);
	}
	
//	public void calcBoundings(){
//		boundings = new Rectangle(0, 0, 0, 0);
//		float width = 0f;
//		float height = 0f;
//		float finalHeight = 0f;
//		int ind = 0;
//		int c = 0;
//		boolean b = true;
//		String sub;
//		float w = 0;
//		for(Text t : wrappedParts){
//			c = 0;
//			
//			height = Math.max(t.getSlickFont().getHeight(t.getText()), height);
//			sub = t.getText();
//			while((ind = sub.indexOf("\n")) > -1){
//				c++;
//				w = t.getSlickFont().getWidth(sub.substring(0, ind));
//				if(ind >= sub.length() - 1)sub="";
//				else sub = sub.substring(ind + 1, sub.length());
//				
//				if(b) width += w;
//				b =false;
//				width =  Math.max(width, w);
//				w=0;
//			}
//			if(c > 0)
//				finalHeight += c * height;
//			else
//				w += t.getSlickFont().getWidth(sub);
//			width =  Math.max(width, w);
//		}
//		finalHeight += height;
//		boundings.setWidth(width);
//		boundings.setHeight(finalHeight);
//	}

	public Rectangle getBoundings(){
		if(boundings == null) calcBoundings();
		return boundings;
	}
	
	public float getHeight() {
		return getBoundings().getHeight();
	}
	
	public float getWidth(){
		return getBoundings().getWidth();
	}
}
