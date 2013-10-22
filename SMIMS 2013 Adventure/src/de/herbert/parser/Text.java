package de.herbert.parser;

import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;

import java.awt.Font;

public class Text {
	private String text;
	private Font font;
	private org.newdawn.slick.Font slickFont;
	
	public Text(String text, Font font, Color color) {
		super();
		this.text = text;
		this.font = font;
		this.slickFont = new TrueTypeFont(font, true);
		this.color = color;
	}
	
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public Font getFont() {
		return font;
	}
	public org.newdawn.slick.Font getSlickFont(){
		return slickFont;
	}
	public void setFont(Font font) {
		this.font = font;
	}
	public Color getColor() {
		return color;
	}
	public void setColor(Color color) {
		this.color = color;
	}
	private Color color;
	
	
}
