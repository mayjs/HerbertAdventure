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

public class InventoryPanel extends Component implements ButtonListener{
	PctButton leftArrow, rightArrow;
	PctButton[] buttons;
	PctButton b;
	float gap = 3; //gap between two buttons
	int offset = 0;
	float pos = 0f;
	float buttonWidth, buttonY;
	private int countOfButtons;
	private Image defaultImage;
	private GradientFill bgFill;
	
	public InventoryPanel(Rectangle boundings, int countOfButtons){
		super(boundings);
		
		this.countOfButtons = countOfButtons;
		bgFill = new GradientFill(boundings.getX(), boundings.getY(), Color.blue.darker(), boundings.getX() + boundings.getWidth()/2, boundings.getY(), Color.blue);
		
		try {
			defaultImage = new Image(0, 0);
		} catch (SlickException e) {
		}
		
		buttons = new PctButton[countOfButtons];
		
		// calculate width and height of buttons (square)
		buttonWidth = boundings.getHeight() - 2*gap;
		if((buttonWidth + gap) * (countOfButtons + 2) + gap > boundings.getWidth())
			buttonWidth = (boundings.getWidth() - gap) / (countOfButtons + 2) - gap;
		
		buttonY = boundings.getY() + boundings.getHeight() / 2 - buttonWidth / 2 ;
		// create Buttons
		rightArrow = new PctButton(new Rectangle(boundings.getX() + gap, buttonY, buttonWidth, buttonWidth), ImageLoader.getImage("LeftArrow"));
		leftArrow = new PctButton(new Rectangle(boundings.getX() + gap + (countOfButtons+1)*(buttonWidth + gap), buttonY, buttonWidth, buttonWidth), ImageLoader.getImage("RightArrow"));
		rightArrow.setStyle(ButtonStyle.BS_Inventory);
		leftArrow.setStyle(ButtonStyle.BS_Inventory);
		rightArrow.addButtonListener(this, "LA");
		leftArrow.addButtonListener(this, "RA");
		
		PctButton button;
		for(int i = 0; i < countOfButtons; i++){
			button = new PctButton(new Rectangle(boundings.getX() + gap + (i + 1)*(buttonWidth +gap), buttonY, buttonWidth, buttonWidth), ImageLoader.getImage("QuadratBlau"));
			button.addButtonListener(this, Integer.toString(i));
			button.setStyle(ButtonStyle.BS_Inventory);
			buttons[i] = button;
		}
		
		b = new PctButton(new Rectangle(20, 20, 20, 20), ImageLoader.getImage("QuadratBlau"));
		b.setStyle(ButtonStyle.BS_Inventory);
		reassignIcons();
	}
	
	public void reassignIcons(){
		int i = offset;
		for(;i < offset + countOfButtons && i < getInventory().countItems(); i++){
			buttons[i - offset].setImage(ImageLoader.getImage(getInventory().get(i).getName()));
			buttons[i - offset].enable();
		}
		
		for(i = i - offset;i < countOfButtons; i++){
			buttons[i].disable();
			buttons[i].setImage(defaultImage);
		}
		if(offset > 0 && offset < getInventory().countItems())	rightArrow.enable(); else rightArrow.disable();
		if(getInventory().countItems() > 1 && offset < getInventory().countItems() - 1) leftArrow.enable(); else leftArrow.disable();
	}
	
	public void calcButtonBoundings(){
		if(pos > 0){
			b.setBoundings(new Rectangle(boundings.getX() + buttonWidth + 2 * gap, buttonY + (buttonWidth - pos)/2, pos, pos));
			for(int i = 0; i < countOfButtons - 1; i++){
				buttons[i].setBoundings(new Rectangle(boundings.getX() + (buttonWidth + gap) * (i + 1) + gap + pos, buttonY, buttonWidth, buttonWidth));
			}
			buttons[countOfButtons - 1].setBoundings(new Rectangle(boundings.getX() + (buttonWidth + gap) * (countOfButtons) + gap + pos, buttonY + (pos)/2, buttonWidth - pos, buttonWidth - pos));
		}else if(pos < 0){
			buttons[0].setBoundings(new Rectangle(boundings.getX() + buttonWidth + 2 * gap, buttonY - pos/2, buttonWidth + pos, buttonWidth + pos));
			for(int i = 1; i < countOfButtons; i++){
				buttons[i].setBoundings(new Rectangle(boundings.getX() + (buttonWidth + gap) * (i + 1) + gap + pos, buttonY, buttonWidth, buttonWidth));
			}
			b.setBoundings(new Rectangle(boundings.getX() + (buttonWidth + gap) * (countOfButtons + 1) + pos, buttonY + (buttonWidth + pos)/2, -pos, - pos));
		}
	}
	
	@Override
	public void update(GameContainer container, int delta)
		throws SlickException{
		if(pos <= 0.5 && pos >= -0.5) pos = 0;
		if(pos > 0) pos -= delta * 0.5; else if(pos < 0) pos += delta * 0.5;
		calcButtonBoundings();
		rightArrow.update(container, delta);
		leftArrow.update(container, delta);
		b.update(container, delta);
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
		
		leftArrow.render(container, g);
		rightArrow.render(container, g);
		if(pos != 0) b.render(container, g);
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
		System.out.println("InventoryButton " + id + " (" + getInventory().get(id + offset).getName() + ") clicked!");
	}
	
	private void scrollLeft(){
		if(offset > 0) {offset--;
			pos = -buttonWidth;
			if(getInventory().countItems() <= offset + countOfButtons){
				b.setImage(defaultImage);
				b.disable();
			}else{
				b.setImage(ImageLoader.getImage(getInventory().get(offset + countOfButtons).getName()));
				b.enable();
			}
		}
		
		reassignIcons();
	}
	
	private void scrollRight(){
		if(offset < getInventory().countItems() - 1) offset++;
		reassignIcons();
		b.setImage(ImageLoader.getImage(getInventory().get(offset - 1).getName()));
		b.enable();
		pos = buttonWidth;
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
			it = new Item();
			it.setName("Geld");
			in.add(it);
			it = new Item();
			it.setName("Zigaretten");
			in.add(it);
			it = new Item();
			it.setName("Hausschluessel");
			in.add(it);
			it = new Item();
			it.setName("Fleisch");
			in.add(it);
			it = new Item();
			it.setName("FleischMitTabletten");
			in.add(it);
			it = new Item();
			it.setName("Karte");
			in.add(it);
		}
		return in;
	}
}
