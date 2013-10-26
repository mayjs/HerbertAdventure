package de.herbert.view;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.MouseListener;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

import de.herbert.control.Control;

public class SelectorScrollList extends ScrollList implements MouseListener {
	
	protected Integer index;
	protected Color selectedBackground = Color.gray;
	
	public SelectorScrollList(Rectangle boundings) {
		super(boundings);
		Control.getInstance().getContainer().getInput().addMouseListener(this);
	}

	@Override
	public void render(GameContainer container, Graphics g) throws SlickException{
		g.setColor(backgroundColor);
		g.fill(boundings);
		g.setColor(borderColor);
		g.draw(boundings);
		
		scrollbar.render(container, g);
		
		Rectangle oldClip = g.getWorldClip();
		g.setWorldClip(boundings.getX(),boundings.getY(),boundings.getWidth() * 0.86f, boundings.getHeight());
		g.translate(0, -scrollbar.getValue()*(font.getLineHeight()+lineDistance));
		
		if(index != null) {
			g.setColor(selectedBackground);
			g.fill(new Rectangle(boundings.getX(),
					boundings.getY() + index*(font.getLineHeight() + lineDistance),
					boundings.getWidth()-scrollbar.getBoundings().getWidth(), 2*lineDistance+font.getLineHeight()));
		}
		
		for(int i = 0; i < entries.size(); i++){
			font.drawString(boundings.getX(), boundings.getY()+font.getLineHeight()*i+lineDistance*i,entries.get(i), textColor);
		}
		g.translate(0, scrollbar.getValue()*(font.getLineHeight()+lineDistance));
		g.setWorldClip(oldClip);
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
	public void setInput(Input arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(int button, int x, int y, int clickCount) {

		if(Input.MOUSE_LEFT_BUTTON == button && boundings.contains(x,y)){
			//Retranslate coordinates
			float retY = y + scrollbar.getValue()*(font.getLineHeight()+lineDistance);
			index = (int)((retY-boundings.getY())/(font.getLineHeight()+lineDistance));
		}
	}

	@Override
	public void mouseDragged(int arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(int arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(int arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(int arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseWheelMoved(int arg0) {
		// TODO Auto-generated method stub
		
	}

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

	public Color getSelectedBackground() {
		return selectedBackground;
	}

	public void setSelectedBackground(Color selectedBackground) {
		this.selectedBackground = selectedBackground;
	}

	public String getSelectedEntry(){
		if(index == null) return null;
		return entries.get(index);
	}
	
	public boolean isSelected(){
		return index!=null;
	}
	
	public void clearSelection(){
		index = null;
	}
}
