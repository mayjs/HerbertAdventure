package de.herbert.view;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

import de.herbert.control.Control;

/**
 * Simple Textinput field.
 * It adjusts it's size on it's own, you can only give the minimal width and height
 * To determine the maximal width please use maxLength to set the maximum text length
 * @author Kemiren
 *
 */
public class Textbox extends Component implements KeyListener{

	private Font font;
	private String text = "", defaultText="";
	private boolean focused;
	private int maxLength = 20;
	
	private float minWidth, minHeight;
	private Color backgroundColor = Color.gray, borderColor = Color.white, textColor = Color.white;
	
	public Textbox(float x, float y, float minWidth, float minHeight) {
		super(new Rectangle(x, y, minWidth, minHeight));
		this.minWidth = minWidth; this.minHeight = minHeight;
		font = Control.getInstance().getContainer().getGraphics().getFont();
		Control.getInstance().getContainer().getInput().addKeyListener(this);
		
		this.adjustBoundings();
	}

	@Override
	public void update(GameContainer container, int delta)
			throws SlickException {
		//Check for focuse
		Input i = container.getInput();
		if(i.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)){
			if(boundings.contains(i.getMouseX(), i.getMouseY())) focused = true;
			else focused = false;
		}
	}

	@Override
	public void render(GameContainer container, Graphics g)
			throws SlickException {
		g.setColor(backgroundColor);
		g.fill(boundings);
		g.setColor(borderColor);
		g.draw(boundings);
		
		if(!text.isEmpty())
			font.drawString(boundings.getX(), boundings.getY(), text, textColor);
		else
			font.drawString(boundings.getX(), boundings.getY(), defaultText, textColor.darker(0.3f));
		
		if(focused){
			g.setColor(Color.white);
			float x = boundings.getX() + font.getWidth(text) + 3 + ((!text.isEmpty()&&text.charAt(text.length()-1) == ' ')?4:0);
			float yDown = Math.max(boundings.getMaxY(), boundings.getMinY() + font.getLineHeight());
			g.setLineWidth(3f);
			g.drawLine(x, boundings.getMinY(), x, yDown);
			g.setLineWidth(1);
		}
//		g.setColor(Color.green);
//		g.draw(boundings);
	}
	
	private void adjustBoundings(){
		boundings.setHeight(Math.max(font.getLineHeight(),minHeight));
		boundings.setWidth(Math.max((text.isEmpty()?font.getWidth(defaultText):font.getWidth(text))+7,minWidth));
	}
	
	public void setCurrentBoundingAsMinimal(){
		minWidth = boundings.getWidth();
		minHeight = boundings.getHeight();
	}

	public Font getFont() {
		return font;
	}

	public void setFont(Font font) {
		this.font = font;
	}

	@Override
	public void inputEnded() {
	}

	@Override
	public void inputStarted() {
	}

	@Override
	public boolean isAcceptingInput() {
		return focused;
	}

	@Override
	public void setInput(Input input) {
	}

	@Override
	public void keyPressed(int key, char c) {
		//System.out.println("CODE: " + key + " CHAR: " + c + " CHAR_INT: " + (int)c);
		if(Input.KEY_BACK == key && !text.isEmpty()){
			text = text.substring(0, text.length()-1);
			this.adjustBoundings();
		}
		else if(c >= 32 && text.length() < this.maxLength){ //only use printable characters
			text += c;
			this.adjustBoundings();
		}
	}

	@Override
	public void keyReleased(int key, char c) {
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
		this.adjustBoundings();
	}

	public int getMaxLength() {
		return maxLength;
	}

	public void setMaxLength(int maxLength) {
		this.maxLength = maxLength;
	}

	public float getMinWidth() {
		return minWidth;
	}

	public void setMinWidth(float minWidth) {
		this.minWidth = minWidth;
		this.adjustBoundings();
	}

	public float getMinHeight() {
		return minHeight;
	}

	public void setMinHeight(float minHeight) {
		this.minHeight = minHeight;
		this.adjustBoundings();
	}

	public boolean isFocused() {
		return focused;
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

	public String getDefaultText() {
		return defaultText;
	}

	public void setDefaultText(String defaultText) {
		this.defaultText = defaultText;
		this.adjustBoundings();
	}
}
