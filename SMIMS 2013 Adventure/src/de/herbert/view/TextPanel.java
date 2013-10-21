package de.herbert.view;


import org.newdawn.slick.AppletGameContainer.Container;
import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class TextPanel extends Panel {

	private Font font;
	private String text = "test";
	private float gap = 10f;
	
	public TextPanel(float x, float y, boolean closeOnClick, GameContainer container) {
		super(x, y, closeOnClick);
		font = container.getGraphics().getFont();
		generateBoundings();
	}

	@Override
	public void generateBoundings() {
		if(font != null){
			boundings.setWidth(font.getWidth(text) + 2*gap);
			boundings.setHeight(font.getHeight(text) + 2*gap);
		}
	}
	
	@Override
	public void render(GameContainer container, Graphics g)
			throws SlickException {
		super.render(container, g);
		g.setFont(font);
		g.setColor(Color.white);
		g.drawString(text, boundings.getX() + gap, boundings.getY() + gap);
	}

	public Font getFont() {
		return font;
	}

	public void setFont(Font font) {
		this.font = font;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public float getGap() {
		return gap;
	}

	public void setGap(float gap) {
		this.gap = gap;
	}
}
