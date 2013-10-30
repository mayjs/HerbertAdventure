package de.herbert.parser;

import java.awt.Font;
import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;

public class Text implements Serializable{
	private static final long serialVersionUID = 1L;
	private static boolean useAntiAlaising = false;
	
	public static Text emptyText = new Text("", new Font("Verdana", 0, 12), Color.black);
	public static Map<Font, TrueTypeFont> loadedSlickFonts = new HashMap<Font, TrueTypeFont>();
	
	private String text;
	private Font font;
	private Color color;
	private org.newdawn.slick.Font slickFont;
	private float width, height;
	private List<Text> lines = null;
	
	public Text(String text, Font f, Color black) {
		super();
		this.text = text;
		this.font = f;
		this.slickFont = getTTF(f);
		this.color = black;
	}
	
	private Text(String text, Font f, Color black, org.newdawn.slick.Font slickFont){
		this.text = text;
		this.font =f;
		this.slickFont = getTTF(f);
		this.color = black;
	}
	
	public TrueTypeFont getTTF(Font f){
		TrueTypeFont ttf = loadedSlickFonts.get(f);
		if(ttf == null)	{
			ttf = new TrueTypeFont(f, useAntiAlaising);
			loadedSlickFonts.put(f, ttf);
		}
		return ttf;
	}
	
	public void append(String s){
		text += s;
		lines = null;
		calcWidthAndHeight();
	}
	
	public int length(){
		return text.length();
	}
	
	public Text sub(int index){
		return new Text(text.substring(index), font, color, slickFont);
	}
	
	public Text sub(int fromIndex, int toIndex){
		return new Text(text.substring(fromIndex, toIndex), font, color, slickFont);
	}
	
	public int indexOf(char c){
		return text.indexOf(c);
	}
	
	public int indexOf(String s){
		return text.indexOf(s);
	}
	
	public int indexOf(char c, int fromIndex){
		return text.indexOf(c, fromIndex);
	}
	
	public int indexOf(String s, int fromIndex){
		return text.indexOf(s, fromIndex);
	}
	
	public int lastIndexOf(char c){
		return text.lastIndexOf(c);
	}
	
	public int lastIndexOf(String s){
		return text.lastIndexOf(s);
	}
	
	public int lastIndexOf(char c, int fromIndex){
		return text.lastIndexOf(c, fromIndex);
	}
	
	public int lastIndexOf(String s, int fromIndex){
		return text.lastIndexOf(s, fromIndex);
	}
	
	public boolean contains(CharSequence s){
		return text.contains(s);
	}
	
	public float getWidth(){
		if(width == 0) width = slickFont.getWidth(text);
		return width;
	}
	
	public float getHeight(){
		if(height == 0) height = slickFont.getLineHeight();
		return height;
	}
	
	public float getWidth(int fromInd, int toInd){
		return slickFont.getWidth(sub(fromInd, toInd).getText());
	}
	
	/**
	 * Deletes all "\n" and replaces them with " ".
	 */
	public void eraseAllWraps(){
		text.replaceAll("\n", " ");
		lines = null; // they change and have to be calculated again
		calcWidthAndHeight();
	}
	
	public void calcWidthAndHeight(){
		float lineWidth = 0;
		width = 0;
		height = 0;
		List<Text> lines = getLines();
		for(Text line : lines){
			lineWidth = slickFont.getWidth(line.getText());
			width = lineWidth > width ? lineWidth : width;
			height = slickFont.getHeight(line.getText());
		}
	}
	
	public List<Text> getLines(){
		if(lines == null) calcLines();
		return lines;
	}
	
	public void calcLines(){
		int ind;
		lines = new LinkedList<Text>();
		String sub = getText();
		while((ind = sub.indexOf("\n")) > -1){
			lines.add(new Text(sub.substring(0, ind), font, color, slickFont));
			if(ind + 2 > sub.length())
				sub = "";
			else
				sub = sub.substring(ind + 1, sub.length());
		}
		lines.add(new Text(sub, font, color, slickFont));
	}
	
	public Text getLastLine(){
		List<Text> lines = getLines();
		if(lines.size() == 0) return null;
		return lines.get(lines.size() - 1);
	}
	
	public void wrapToWidth(float width){
		wrapToWidth(width, 0);
	}
	
	public void wrapToWidth(float width, float startWidth){
		List<Text> lines = getLines();
		text = "";
		int startInd = 0;
		int wrapInd = 0;
		for(Text line : lines){
			startInd = 0;
			while(startInd < line.length()){
				if(line.getWidth(startInd, line.length()) + startWidth > width){
					// find wrapInd, so that it is out of width
					wrapInd = startInd;//(int) (width/(line.getWidth() + startWidth) * text.length());
					while(line.getWidth(startInd, wrapInd) + startWidth <= width + 1)
						wrapInd++;
					
					// find last occurrence of " " before width
					while(wrapInd > startInd && line.getWidth(startInd, wrapInd) + startWidth >= width && wrapInd >= 0)
						wrapInd = line.lastIndexOf(" ", wrapInd) - 1;
					
					wrapInd+= 2;
					if(wrapInd <= startInd){	// the first word of the line is longer than width
						if(startWidth > 0){
							text += "\n";
						}else{
							int ind = line.indexOf(" ", startInd);
							if(ind < 0) {// line only consists of one word, it should have a "\n" at the end
								text += line.sub(startInd, line.length()).getText();
								startInd = line.length();
							}else {
								text += line.sub(startInd, ind).getText() + "\n";
								startInd = ind;
							}
						}
						
					}else{	// add word wrap
						text += line.sub(startInd, wrapInd).getText() + "\n";
						startInd = wrapInd;
					}
					
					startWidth = 0;
				}else{	// line doesn't need to be wrapped
					startWidth = 0;
					text += line.sub(startInd, line.length()).getText();
					if(line != getLastLine()) text+="\n";
					startInd = line.length();
				}
			}
		}
		this.lines = null; // need to be recalculated
		calcWidthAndHeight();
	}
	
	public int getCountOfLines(){
		return getLines().size();
	}
	
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
		lines = null;
		calcWidthAndHeight();
	}
	public Font getFont() {
		return font;
	}
	public org.newdawn.slick.Font getSlickFont(){
		return slickFont;
	}
	public void setFont(Font font) {
		this.font = font;
		this.slickFont = new TrueTypeFont(font, false);
	}
	public Color getColor() {
		return color;
	}
	public void setColor(Color color) {
		this.color = color;
	}	
}
