package de.herbert.parser;


import java.awt.Font;

import org.newdawn.slick.Color;






public class ColoredFont {
	Font font;
	Color color;
	public ColoredFont(Font f, Color color2) {
		super();
		this.font = f;
		this.color = color2;
	}
	
	public Font getFont() {
		return font;
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
	
	
}
