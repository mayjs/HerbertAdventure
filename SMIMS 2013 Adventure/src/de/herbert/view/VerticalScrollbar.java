package de.herbert.view;



import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.MouseListener;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

import de.herbert.control.Control;

public class VerticalScrollbar extends Component implements MouseListener{

	private float min, max=1, value;
	private Color lineColor = Color.darkGray, barColor = Color.gray;
	private boolean mouseDown, blocked;
	
	public VerticalScrollbar(Rectangle boundings) {
		super(boundings);
		Control.getInstance().getContainer().getInput().addMouseListener(this);
	}

	@Override
	public void update(GameContainer container, int delta)
			throws SlickException {
//		Input i = container.getInput();
//		if(i.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) && boundings.contains(i.getAbsoluteMouseX(), i.getAbsoluteMouseY())){
//			value=(i.getAbsoluteMouseY()-boundings.getY())/boundings.getHeight()*(max-min);
//		}

	}

	@Override
	public void render(GameContainer container, Graphics g)
			throws SlickException {
		g.setColor(lineColor);
		g.drawLine(boundings.getCenterX(), boundings.getMinY(), boundings.getCenterX(), boundings.getMaxY());
		
		g.setLineWidth(3f); g.setColor(barColor);
		float y = max==min?0:boundings.getY() + (value-min)/(max-min)*boundings.getHeight();
		g.drawLine(boundings.getMinX(), y, boundings.getMaxX(), y);
		g.setLineWidth(1f);
	}

	public float getMin() {
		return min;
	}

	public void setMin(float min) {
		this.min = min;
	}

	public float getMax() {
		return max;
	}

	public void setMax(float max) {
		this.max = max;
	}

	public float getValue() {
		return blocked?min:value;
	}

	public void setValue(float value) {
		this.value = value;
	}

	public Color getLineColor() {
		return lineColor;
	}

	public void setLineColor(Color lineColor) {
		this.lineColor = lineColor;
	}

	public Color getBarColor() {
		return barColor;
	}

	public void setBarColor(Color barColor) {
		this.barColor = barColor;
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
		return true;
	}

	@Override
	public void setInput(Input input) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(int button, int x, int y, int clickCount) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(int oldx, int oldy, int newx, int newy) {
		if(mouseDown && !blocked){
			value=(newy-boundings.getY())/boundings.getHeight()*(max-min);
			if(value < min) value = min;
			if(value > max) value = max;
		}
	}

	@Override
	public void mouseMoved(int oldx, int oldy, int newx, int newy) {	
		
	}

	@Override
	public void mousePressed(int button, int x, int y) {
		mouseDown = boundings.contains(x, y) && button == Input.MOUSE_LEFT_BUTTON;
	}

	@Override
	public void mouseReleased(int button, int x, int y) {
		if(button == Input.MOUSE_LEFT_BUTTON) mouseDown = false;
	}

	@Override
	public void mouseWheelMoved(int change) {
		// TODO Auto-generated method stub
		
	}

	public void block(){
		blocked=true;
	}
	public void unblock(){
		blocked=false;
	}
}
