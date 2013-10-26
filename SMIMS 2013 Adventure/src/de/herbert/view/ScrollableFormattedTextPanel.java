package de.herbert.view;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.ShapeFill;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

import de.herbert.parser.FormattedText;

public class ScrollableFormattedTextPanel extends Component {
	float scrollbarWidth = 20;
	float gap = 5;
	FormattedText text;
	VerticalScrollbar scrollbar = new VerticalScrollbar(new Rectangle(0, 0, 0, 0));
	ShapeFill bgFill = ButtonStyle.BS_Default.getMouseOverFill(boundings);
	
	public ScrollableFormattedTextPanel(Rectangle boundings, FormattedText text) {
		super(boundings);
		setText(text);
		setBoundings(boundings);
	}
	
	public void setBoundings(Rectangle boundings){
		super.setBoundings(boundings);
		scrollbar.setBoundings(new Rectangle(boundings.getMaxX() - scrollbarWidth, boundings.getY(), scrollbarWidth, boundings.getHeight()));
		calcScrollbarValues();
	}
	
	public void setText(FormattedText text){
		this.text = text;
		calcScrollbarValues();
		text.wrapToWidth(boundings.getWidth() - 4 * gap - scrollbar.getBoundings().getWidth());
	}
	
	public void calcScrollbarValues(){
		scrollbar.setMax(text.getHeight() - boundings.getHeight() + 2*gap);
		if(scrollbar.getMax() < 0) scrollbar.setMax(0);
		scrollbar.setValue(0);
	}

	@Override
	public void update(GameContainer container, int delta)
			throws SlickException {
		if(scrollbar.getMax() > 0) scrollbar.update(container, delta);
	}

	@Override
	public void render(GameContainer container, Graphics g)
			throws SlickException {
		
		g.setColor(Color.lightGray);
		g.draw(boundings);
		g.fill(boundings, bgFill);
		if(scrollbar.getMax() > 0) scrollbar.render(container, g);
		
		Rectangle oldClip = g.getWorldClip();
		g.setWorldClip(boundings.getX(),boundings.getY(),boundings.getWidth() - scrollbar.getBoundings().getWidth(), boundings.getHeight());
		g.translate(0, -scrollbar.getValue());
		text.draw(g, boundings.getMinX() + gap, boundings.getMinY() + gap);
		g.translate(0, scrollbar.getValue());
		g.setWorldClip(oldClip);
		
		
	}

}
