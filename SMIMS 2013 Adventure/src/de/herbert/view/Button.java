package de.herbert.view;

import java.io.File;
import java.io.InputStream;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.fills.GradientFill;
import org.newdawn.slick.geom.Rectangle;

public class Button extends Component {
	final int GAP = 20; // gap between border and image
	
	
	Color bgCol = new Color(0, 0, 0, 130);
	Color bgColMo = new Color(0, 0, 0, 160);
	Color bgColMd = new Color(100, 50, 0, 400);
	Color grCol = new Color(20,20,20,100);
	
	boolean mouseOver = false;
	boolean mouseDown = false;
	
	public Button(Rectangle boundings) {
		super(boundings);
	}

	@Override
	public void update(GameContainer container, int delta)
			throws SlickException {
		
		// Mouse
		int mouseX = container.getInput().getMouseX(),
				mouseY = container.getInput().getMouseY();
		
		if(mouseX >= boundings.getX() && mouseX <= boundings.getX() + boundings.getWidth() &&
			mouseY >= boundings.getY() && mouseY <= boundings.getY() + boundings.getHeight())
			mouseOver = true;
		else mouseOver = false;		
	}


	@Override
	public void render(GameContainer container, Graphics g)
			throws SlickException {
		Rectangle b = getBoundings();
		
		Color c = mouseOver? bgColMo : bgCol;
		if(container.getInput().isMouseButtonDown(0) && mouseOver) 
			c = bgColMd;
		
		g.setColor(Color.darkGray);
		g.drawRect(b.getX(), b.getY(), b.getWidth(), b.getHeight());
		//g.drawRoundRect(b.getX(), b.getY(), b.getWidth(), b.getHeight(), 10, 2);
		
		g.fill(b, new GradientFill(b.getX() + b.getWidth()/2, b.getY(), grCol, b.getX() + b.getWidth() / 2, b.getY() + b.getHeight(), c));

	}
	
	public Color getBgCol() {
		return bgCol;
	}


	public void setBgCol(Color bgCol) {
		this.bgCol = bgCol;
	}


	public Color getBgColMo() {
		return bgColMo;
	}


	public void setBgColMo(Color bgColMo) {
		this.bgColMo = bgColMo;
	}


	public Color getBgColMd() {
		return bgColMd;
	}


	public void setBgColMd(Color bgColMd) {
		this.bgColMd = bgColMd;
	}


	public Color getGrCol() {
		return grCol;
	}


	public void setGrCol(Color grCol) {
		this.grCol = grCol;
	}
}