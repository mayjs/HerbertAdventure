package de.herbert.view;


import java.util.LinkedList;
import java.util.List;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

import de.herbert.control.Control;

public class ScrollList extends Component {

	protected VerticalScrollbar scrollbar;
	protected List<String> entries = new LinkedList<String>();
	protected Color backgroundColor = Color.lightGray, borderColor = Color.gray, textColor = Color.white;
	protected Font font;
	protected float lineDistance = 1f;
	
	public ScrollList(Rectangle boundings) {
		super(boundings);
		scrollbar = new VerticalScrollbar(new Rectangle(boundings.getX() + boundings.getWidth() * 0.86f,boundings.getY(),boundings.getWidth()*0.14f,boundings.getHeight()));
		font = Control.getInstance().getContainer().getGraphics().getFont();
	}

	@Override
	public void update(GameContainer container, int delta)
			throws SlickException {
		scrollbar.update(container, delta);
	}

	@Override
	public void render(GameContainer container, Graphics g)
			throws SlickException {
		g.setColor(backgroundColor);
		g.fill(boundings);
		g.setColor(borderColor);
		g.draw(boundings);
		
		scrollbar.render(container, g);
		
		//Draw List Elements
		
		Rectangle oldClip = g.getWorldClip();
		g.setWorldClip(boundings.getX(),boundings.getY(),boundings.getWidth() * 0.86f, boundings.getHeight());
		g.translate(0, -scrollbar.getValue()*(font.getLineHeight()+lineDistance));
		for(int i = 0; i < entries.size(); i++){
			font.drawString(boundings.getX(), boundings.getY()+font.getLineHeight()*i+lineDistance*i,entries.get(i), textColor);
		}
		g.translate(0, 0);
		g.setWorldClip(oldClip);
	}
	
	private void adjustScrollBar(){
		float dispCap = this.boundings.getHeight()/(font.getLineHeight() + lineDistance);
		if((int)dispCap < entries.size()){
			scrollbar.unblock();
			scrollbar.setMax(entries.size()-(int)dispCap);
		}
		else
			scrollbar.block();
	}

	/**
	 * You can use this method to get the entries in the List.
	 * DO NOT MANIPULATE THE RETURNED LIST!
	 * @return A List of all Entries in this ScrollList
	 */
	public List<String> getEntries() {
		return entries;
	}

	public void setEntries(List<String> entries) {
		this.entries = entries;
		this.adjustScrollBar();
	}

	public Color getBackgroundColor() {
		return backgroundColor;
	}

	public void setBackgroundColor(Color backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	public Color getBorderColor() {
		return borderColor;
	}

	public void setBorderColor(Color borderColor) {
		this.borderColor = borderColor;
	}

	public Color getTextColor() {
		return textColor;
	}

	public void setTextColor(Color textColor) {
		this.textColor = textColor;
	}

	public Font getFont() {
		return font;
	}

	public void setFont(Font font) {
		this.font = font;
	}

	public float getLineDistance() {
		return lineDistance;
	}

	public void setLineDistance(float lineDistance) {
		this.lineDistance = lineDistance;
	}

	public VerticalScrollbar getScrollbar() {
		return scrollbar;
	}
	
	public void addEntry(String entry){
		entries.add(entry);
		this.adjustScrollBar();
	}
	public boolean removeEntry(String entry){
		boolean res = entries.remove(entry);
		this.adjustScrollBar();
		return res;
	}
	public String removeEntry(int i){
		String res = entries.remove(i);
		this.adjustScrollBar();
		return res;
	}
}
