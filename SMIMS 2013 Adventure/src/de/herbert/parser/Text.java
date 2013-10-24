package de.herbert.parser;

import java.awt.Font;
import java.util.LinkedList;
import java.util.List;

import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;

public class Text {
	
	
	private String text;
	private Font font;
	private Color color;
	private org.newdawn.slick.Font slickFont;
	private float width, height;
	
	public Text(String text, Font f, Color black) {
		super();
		this.text = text;
		this.font = f;
		this.slickFont = new TrueTypeFont(f, true);
		this.color = black;
	}
	
	public void append(String s){
		text += "s";
	}
	
	public int length(){
		return text.length();
	}
	
	public Text sub(int index){
		return new Text(text.substring(index), font, color);
	}
	
	public Text sub(int fromIndex, int toIndex){
		return new Text(text.substring(fromIndex, toIndex), font, color);
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
		return width;
	}
	
	public float getHeight(){
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
		int ind;
		List<Text> lines = new LinkedList<Text>();
		String sub = getText();
		while((ind = sub.indexOf("\\n")) > -1){
			sub = sub.substring(ind + 2, sub.length()-1);
			lines.add(new Text(sub, font, color));
		}
		return lines;
	}
	
	public Text getLastLine(){
		List<Text> lines = getLines();
		return lines.get(lines.size() - 1);
	}
	
	public void wrapToWidth(float width){
		wrapToWidth(width, 0);
	}
	
	public void wrapToWidth(float width, float startWidth){
		List<Text> lines = getLines();
		int countOfWraps = 0;
		int curLength = 0;
		for(int i = 0; i < lines.size(); i++){
			Text curLine = lines.get(i);
			int wrapInd = lines.get(i).length() - 1;
			int ind = 0;
			while(curLine.getWidth(0, wrapInd) + startWidth > width){
				ind = curLine.lastIndexOf(" ", wrapInd);
				if(ind == -1) break;
				wrapInd = ind;
			}
			if(wrapInd > 0){
				text = text.substring(0, countOfWraps + wrapInd - 1 + curLength) + "\n" + text.substring(wrapInd + 1, text.length() - 1);
				countOfWraps++;
			}
			curLength += curLine.length();
		}
		calcWidthAndHeight();
	}
	
	private void wrapToWidth(float width, int startInd){
		
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
	
	
}
