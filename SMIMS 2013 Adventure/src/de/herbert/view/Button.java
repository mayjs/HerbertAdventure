package de.herbert.view;

import java.util.HashMap;
import java.util.Map;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.fills.GradientFill;
import org.newdawn.slick.geom.Rectangle;

public class Button extends Component {
	protected int gap = 4; // gap between border and image
	
	final static char ID_CHAR_MIN = (char)65;
	final static char ID_CHAR_MAX = (char)90;
	static String uniqueIDRef = new String(new char[]{ID_CHAR_MIN - 1});
	
	private String uniqueID = null;
	private Map<ButtonListener, String> listeners = new HashMap<ButtonListener, String>();
	
	ButtonStyle style;
	GradientFill currentFill;
	
	private boolean mouseOver = false;
	private boolean mouseDown = false;
	private boolean enabled = true;
	
	public Button(Rectangle boundings) {
		super(boundings);
		
		style = ButtonStyle.BS_Default;
		currentFill = style.getDefaultFill(boundings);
	}
	
	public String getUniqueID(){
		if(uniqueID == null) uniqueID = generateUniqueID();
		return uniqueID;
	}
	
	private static String generateUniqueID(){
		char[] chars = uniqueIDRef.toCharArray();
		int i = chars.length - 1;
		for(; i >= 0;){
			if(chars[i] < ID_CHAR_MAX){
				chars[i]++;
				break;
			}else
				chars[i] = ID_CHAR_MIN;
			;
			i --;
		}
		if(i < 0)
			uniqueIDRef = ID_CHAR_MIN + new String(chars);
		else
			uniqueIDRef = new String(chars);
		
		return uniqueIDRef;
	}

	@Override
	public void update(GameContainer container, int delta)
			throws SlickException {
		
		if(!enabled){
			currentFill = style.getDisabledFill(boundings);
			return;
		}
		//  Mouse
		int mouseX = container.getInput().getMouseX(),
				mouseY = container.getInput().getMouseY();
		
//		if(mouseX >= boundings.getX() && mouseX <= boundings.getX() + boundings.getWidth() &&
//			mouseY >= boundings.getY() && mouseY <= boundings.getY() + boundings.getHeight())
		if(boundings.contains(mouseX, mouseY)){
			mouseOver = true;
			currentFill = style.getMouseOverFill(boundings);
		}else{
			mouseOver = false;
			currentFill = style.getDefaultFill(boundings);
		}
		
		if(container.getInput().isMouseButtonDown(0) && mouseOver) {
			mouseDown = true;
			currentFill = style.getMousePressedFill(boundings);
		}else{
			if(mouseDown && mouseOver)
				fireButtonClicked();
			mouseDown = false;
		} 
	}


	@Override
	public void render(GameContainer container, Graphics g)
			throws SlickException {
		Rectangle b = getBoundings();
		g.setColor(Color.darkGray);
		g.drawRect(b.getX(), b.getY(), b.getWidth(), b.getHeight());
		g.fill(b, currentFill);
			
	}
	
	public String getListenerID(ButtonListener listener){
		return listeners.get(listener);
	}
	
	public void addButtonListener(ButtonListener listener, String id){
		listeners.put(listener, id);
	}
	
	public void addButtonListener(ButtonListener listener){
		addButtonListener(listener, getUniqueID());
	}
	
	private void fireButtonClicked(){
		for(ButtonListener listener : listeners.keySet())
			listener.buttonClicked(listeners.get(listener));
		
		// with thread:
//		Thread thread = new Thread(){
//			public void run(){
//				for(ButtonListener listener : listeners.keySet())
//					listener.buttonClicked(listeners.get(listener));
//				}
//			};
//		thread.start();
	}
	
	public void setStyle(ButtonStyle style){
		this.style = style;
	}
	
	public ButtonStyle getStyle(){
		return style;
	}
	
	public void enable(){
		enabled = true;
	}
	
	public void disable(){
		enabled = false;
	}
	
	public boolean isEnabled(){
		return enabled;
	}
}