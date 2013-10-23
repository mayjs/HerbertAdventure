package de.herbert.view;

import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

import de.herbert.control.Control;

public class Textbox extends Component implements KeyListener{

	private Font font;
	private String text = "";
	private boolean focused;
	
	public Textbox(Rectangle boundings) {
		super(boundings);
		font = Control.getInstance().getContainer().getGraphics().getFont();
		Control.getInstance().getContainer().getInput().addKeyListener(this);
	}

	@Override
	public void update(GameContainer container, int delta)
			throws SlickException {
		Input i = container.getInput();
		if(i.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)){
			if(boundings.contains(i.getMouseX(), i.getMouseY())) focused = true;
			else focused = false;
		}
	}

	@Override
	public void render(GameContainer container, Graphics g)
			throws SlickException {
		font.drawString(boundings.getX(), boundings.getY(), text);
		if(focused){
			float x = boundings.getX() + font.getWidth(text) + 3;
			g.setLineWidth(3f);
			g.drawLine(x, boundings.getMinY(), x, boundings.getMaxY());
			g.resetLineWidth();;
		}
	}

	public Font getFont() {
		return font;
	}

	public void setFont(Font font) {
		this.font = font;
	}

	@Override
	public void inputEnded() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void inputStarted() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isAcceptingInput() {
		// TODO Auto-generated method stub
		return focused;
	}

	@Override
	public void setInput(Input input) {
		// TODO Auto-generated method stub
		input.addKeyListener(this);
	}

	@Override
	public void keyPressed(int key, char c) {
		System.out.println("CODE: " + key + " CHAR: " + c + " CHAR_INT: " + (int)c);
		if(Input.KEY_BACK == key && !text.isEmpty())
			text = text.substring(0, text.length()-1);
		else if((int)c >= 32)
			text += c;
	}

	@Override
	public void keyReleased(int key, char c) {
		// TODO Auto-generated method stub
		
	}	
}
