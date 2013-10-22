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
	int GAP = 4; // gap between border and image
	
	final static char ID_CHAR_MIN = (char)65;
	final static char ID_CHAR_MAX = (char)90;
	static String uniqueIDRef = new String(new char[]{ID_CHAR_MIN - 1});
	
	private String uniqueID = null;
	private Map<ButtonListener, String> listeners = new HashMap<ButtonListener, String>();
	
	Color bgCol = Color.lightGray;
	Color bgColMo = Color.lightGray.darker();
	Color bgColMd = new Color(100, 50, 0, 400);
	Color grCol = new Color(30,30,30,90);
	Color bgColDis = new Color(0, 0, 0, 150);
	
	private boolean mouseOver = false;
	private boolean mouseDown = false;
	private boolean enabled = true;
	
	public Button(Rectangle boundings) {
		super(boundings);
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
		
		if(!enabled)return;
		//  Mouse
		int mouseX = container.getInput().getMouseX(),
				mouseY = container.getInput().getMouseY();
		
//		if(mouseX >= boundings.getX() && mouseX <= boundings.getX() + boundings.getWidth() &&
//			mouseY >= boundings.getY() && mouseY <= boundings.getY() + boundings.getHeight())
		if(boundings.contains(mouseX, mouseY))
			mouseOver = true;
		else mouseOver = false;
		
		if(container.getInput().isMouseButtonDown(0) && mouseOver) 
			mouseDown = true;
		else{
			if(mouseDown && mouseOver)
				fireButtonClicked();
			mouseDown = false;
		} 
	}


	@Override
	public void render(GameContainer container, Graphics g)
			throws SlickException {
		Rectangle b = getBoundings();
		
		Color c;
		if(enabled){
			c = mouseOver? bgColMo : bgCol;
			if(mouseDown) 
				c = bgColMd;
		}else{
			c = bgColDis;
		}
			g.setColor(Color.darkGray);
			g.drawRect(b.getX(), b.getY(), b.getWidth(), b.getHeight());
			g.fill(b, new GradientFill(b.getX() + b.getWidth()/2, b.getY(), grCol, b.getX() + b.getWidth() / 2, b.getY() + b.getHeight(), c));
		
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