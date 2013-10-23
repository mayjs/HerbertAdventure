package de.herbert.view;



import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

public class VerticalScrollbar extends Component {

	private float min, max=1, value;
	private Color lineColor = Color.darkGray, barColor = Color.gray;
	
	public VerticalScrollbar(Rectangle boundings) {
		super(boundings);
	}

	@Override
	public void update(GameContainer container, int delta)
			throws SlickException {
		Input i = container.getInput();
		if(i.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) && boundings.contains(i.getAbsoluteMouseX(), i.getAbsoluteMouseY())){
			value=(i.getAbsoluteMouseY()-boundings.getY())/boundings.getHeight()*(max-min);
		}

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
		return value;
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

	
}
