package de.herbert.view;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

import de.herbert.parser.FormattedText;
import de.herbert.parser.Text;

public class FormattedTextButton extends Button{

	FormattedText text;
	
	public FormattedTextButton(Rectangle boundings, FormattedText text) {
		super(boundings);
		setStyle(ButtonStyle.BS_Default);
		this.text = text;
	}
	
	float textPosX = 0f;
	float textPosY = 0f;
	@Override
	public void render(GameContainer container, Graphics g)
			throws SlickException {
		super.render(container, g);
		text.draw(g, boundings.getX() + gap, boundings.getY() + gap);
	}
	
	public void setText(FormattedText text){
		this.text = text;
	}

}
