package de.herbert.view;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

import de.herbert.control.Control;

public class Annotation extends Component {

	private int hoverTime;
	private String text;
	private Component targetComponent;
	private float gap = 10f;
	private Font font;
	private Color bgColor = Color.yellow;
	private Color borderColor = Color.black;
	private Color textColor = Color.black;
	
	public Annotation(Component targetComponent, String text, int hoverTime) {
		super(new Rectangle(0,0,0,0));
		this.targetComponent = targetComponent;
		this.text = text;
		this.hoverTime = hoverTime;
		font = Control.getInstance().getContainer().getGraphics().getFont();
	}


	
	private int currentHoverTime;
	private boolean mouseOver;
	private boolean showAnnotation;
	
	@Override
	public void update(GameContainer container, int delta)
			throws SlickException {
		if(mouseOver) currentHoverTime+=delta;
		else currentHoverTime = 0;
		if(currentHoverTime >= hoverTime) show(container.getInput().getMouseX(), container.getInput().getMouseY());
		else showAnnotation = false;
		mouseOver = targetComponent.getBoundings().contains(container.getInput().getMouseX(), container.getInput().getMouseY());
	}
	
	private void show(float x, float y){
		if(!showAnnotation){
			boundings.setWidth(font.getWidth(text) + 2*gap);
			boundings.setHeight(font.getHeight(text) + 2*gap);
			boundings.setX(x);
			boundings.setY(y-boundings.getHeight());
		}
		showAnnotation = true;
	}
	
	@Override
	public void render(GameContainer container, Graphics g)
			throws SlickException {
		if(showAnnotation){
			g.setColor(bgColor);
			g.fillRoundRect(boundings.getX(), boundings.getY(), boundings.getWidth(), boundings.getHeight(), 5);
			g.setColor(borderColor);
			g.drawRoundRect(boundings.getX(), boundings.getY(), boundings.getWidth(), boundings.getHeight(), 5);
			
			g.setFont(font); g.setColor(textColor);
			g.drawString(text, boundings.getX() + gap, boundings.getY() + gap);
		}
	}

	public int getHoverTime() {
		return hoverTime;
	}

	public void setHoverTime(int hoverTime) {
		this.hoverTime = hoverTime;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Component getTargetComponent() {
		return targetComponent;
	}

	public void setTargetComponent(Component targetComponent) {
		this.targetComponent = targetComponent;
	}

	public float getGap() {
		return gap;
	}

	public void setGap(float gap) {
		this.gap = gap;
	}

	public Font getFont() {
		return font;
	}

	public void setFont(Font font) {
		this.font = font;
	}

	public Color getBgColor() {
		return bgColor;
	}

	public void setBgColor(Color bgColor) {
		this.bgColor = bgColor;
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
	
	
}
