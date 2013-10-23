package de.herbert.view;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.fills.GradientFill;
import org.newdawn.slick.geom.Rectangle;

import de.herbert.model.Inventory;
import de.herbert.model.Item;

public class NotAnimatedInventoryPanel extends Component implements ButtonListener{
	PctButton[] buttons;
	float gap = 3; //gap between two buttons
	int offset = 0;
	private int countOfButtons;
	private Image defaultImage;
	private GradientFill bgFill;
	
	public NotAnimatedInventoryPanel(Rectangle boundings, int countOfButtons){
		super(boundings);
		
		this.countOfButtons = countOfButtons;
		bgFill = new GradientFill(boundings.getX(), boundings.getY(), Color.blue.darker(), boundings.getX() + boundings.getWidth()/2, boundings.getY(), Color.blue);
		
		try {
			defaultImage = new Image(0, 0);
		} catch (SlickException e) {
		}
		
		buttons = new PctButton[countOfButtons + 2];
		
		// calculate width and height of buttons (square)
		float height = boundings.getHeight() - 2*gap;
		float width = height;
		if((height + gap) * (countOfButtons + 2) + gap > boundings.getWidth())
			width = height = (boundings.getWidth() - gap) / (countOfButtons + 2) - gap;
		
		float buttonY = boundings.getY() + boundings.getHeight() / 2 - height / 2 ;
		// create Buttons
		buttons[0] = new PctButton(new Rectangle(boundings.getX() + gap, buttonY, width, height), ImageLoader.getImage("LeftArrow"));
		buttons[countOfButtons + 1] = new PctButton(new Rectangle(boundings.getX() + gap + (countOfButtons+1)*(width + gap), buttonY, width, height), ImageLoader.getImage("RightArrow"));
		buttons[0].setStyle(ButtonStyle.BS_Inventory);
		buttons[countOfButtons + 1].setStyle(ButtonStyle.BS_Inventory);
		buttons[0].addButtonListener(this, "LA");
		buttons[countOfButtons + 1].addButtonListener(this, "RA");
		PctButton button;
		for(int i = 1; i <= countOfButtons; i++){
			button = new PctButton(new Rectangle(boundings.getX() + gap + i*(width +gap), buttonY, width, height), ImageLoader.getImage("QuadratBlau"));
			button.addButtonListener(this, Integer.toString(i));
			button.setStyle(ButtonStyle.BS_Inventory);
			buttons[i] = button;
		}
		
		reassignIcons();
	}
	
	public void reassignIcons(){
		int i = offset;
		for(;i < offset + countOfButtons && i < getInventory().countItems(); i++){
			buttons[i - offset + 1].setImage(ImageLoader.getImage(getInventory().get(i).getName()));
			buttons[i - offset + 1].enable();
		}
		
		for(i = i - offset + 1;i <= countOfButtons; i++){
			buttons[i].disable();
			buttons[i].setImage(defaultImage);
		}
		if(offset > 0 && offset < getInventory().countItems())	buttons[0].enable(); else buttons[0].disable();
		if(getInventory().countItems() > 1 && offset < getInventory().countItems() - 1) buttons[countOfButtons + 1].enable(); else buttons[countOfButtons + 1].disable();
	}
	
	@Override
	public void update(GameContainer container, int delta)
		throws SlickException{
		for(PctButton button : buttons){
			button.update(container, delta);
		}
	}
	
	@Override
	public void render(GameContainer container, Graphics g)
		throws SlickException{
		g.fill(boundings, bgFill);
		g.setColor(Color.lightGray);
		g.drawRect(boundings.getX(), boundings.getY(), boundings.getWidth(), boundings.getHeight());
		for(PctButton button : buttons){
			button.render(container, g);
		}
	}

	@Override
	public void buttonClicked(String buttonId) {	
		if(buttonId.equals("LA")){ // leftArrow
			scrollLeft();
			return;
		}
		if(buttonId.equals("RA")){ //rightArrow
			scrollRight();
			return;
		}
		
		// TODO: Handle all other buttons ...
		int id = Integer.parseInt(buttonId);
		System.out.println("InventoryButton " + id + " (" + getInventory().get(id + offset - 1).getName() + ") clicked!");
	}
	
	private void scrollLeft(){
		if(offset > 0) offset--;
		reassignIcons();
	}
	
	private void scrollRight(){
		if(offset < getInventory().countItems() - 1) offset++;
		reassignIcons();
	}
	
	Inventory in;
	public Inventory getInventory(){
		// create a test inventory ...
		if(in == null){
			in = new Inventory();
			Item it = new Item();
			it.setName("hammer");
			in.add(it);
			it = new Item();
			it.setName("Tasse");
			in.add(it);
			it = new Item();
			it.setName("Karte");
			in.add(it);
		}
		return in;
	}
}
