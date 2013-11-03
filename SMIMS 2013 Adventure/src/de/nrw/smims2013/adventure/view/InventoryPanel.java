package de.nrw.smims2013.adventure.view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import de.nrw.smims2013.adventure.model.Inventory;
import de.nrw.smims2013.adventure.model.Item;
import de.nrw.smims2013.adventure.model.Point;

public class InventoryPanel extends JPanel implements MouseListener{
	
	private static final long serialVersionUID = 1L;
	private JButton leftArrowBtn;
	private JButton rightArrowBtn;
	private InventoryButton[] visibleButtons;
	private AdventureFrame adventureFrame;
	private int offset = 0;
		
	public InventoryPanel(AdventureFrame pAdventureFrame){
		super();
				
		adventureFrame = pAdventureFrame; 
		
		// Groesse und Position werden festgelegt.
        setBackground(Color.BLUE);
        setSize(900, 100);
        setLocation(0, 600);
        setLayout(null);       
		
        // Buttons erstellen
        // linker Button
		leftArrowBtn = new JButton();
		leftArrowBtn.setSize(80,80);
		leftArrowBtn.setLocation(10,10); 
		leftArrowBtn.setBackground(InventoryButton.BACKGROUND);
		leftArrowBtn.setIcon(new ImageIcon(ImageLoader.getImage("leftArrow")));
		add(leftArrowBtn);
		leftArrowBtn.setVisible(true);		
		ActionListener left = new ActionListener() {
			@Override
			public void actionPerformed( ActionEvent e ) 
		    {		 		    	  
		    	 scrollLeft();          
		    }
		};
		leftArrowBtn.addActionListener(left);
		leftArrowBtn.addMouseListener(this);
		
		// InventarButton
		visibleButtons = new InventoryButton[7];		
		for(int i = 0; i < 7; i++){
			visibleButtons[i] = new InventoryButton(i, adventureFrame);
			visibleButtons[i].setLocation((110 + i*100), 10);
			visibleButtons[i].setDisabledIcon(new ImageIcon());
			visibleButtons[i].addActionListener(buttonClick);
			visibleButtons[i].addMouseListener(this);
			add(visibleButtons[i]);
		}
		
		// rechter Button
		rightArrowBtn = new JButton();
		rightArrowBtn.getLocation();
		rightArrowBtn.setSize(80,80);
		rightArrowBtn.setLocation(810,10); 
		rightArrowBtn.setBackground(InventoryButton.BACKGROUND);
		rightArrowBtn.setIcon(new ImageIcon(ImageLoader.getImage("rightArrow")));
		add(rightArrowBtn);
		rightArrowBtn.setVisible(true);	
		ActionListener right = new ActionListener() {
			@Override
			public void actionPerformed( ActionEvent e ) 
		    {		 		    	  
		    	 scrollRight();          
		    }
		};
		rightArrowBtn.addActionListener(right);
		rightArrowBtn.addMouseListener(this);
		
		JLabel background = new JLabel(new ImageIcon(ImageLoader.getImage("Inventar")));
        background.setSize(900, 100);
        add(background);
		
		this.reassignIcons();
	}
	
	public void scrollLeft(){
		offset -= offset>0? 1:0;
		this.reassignIcons();
	}
	
	public void scrollRight(){
		if(offset<this.getInventory().countItems()-1){
			offset++;
			this.reassignIcons();
		}
	}
	
	public void reassignIcons()
	{
		int i = offset;
		for( ; i < offset+7 && i<this.getInventory().countItems(); i++){
			this.visibleButtons[i-offset].setIcon(
					ImageLoader.getImageIcon(this.getInventory().get(i).getName()));
			this.visibleButtons[i-offset].setEnabled(true);
		}
		for(i=i-offset; i<7 ;i++){ //Remove Icons for unused buttons
			this.visibleButtons[i].setIcon(new ImageIcon());
			this.visibleButtons[i].setEnabled(false);
		}
		this.resetArrowButtonsEnabled();
		this.repaint();
		unselectAll();
		i=this.getInventory().getIndex(adventureFrame.getScenePanel().getSelectedItem());
		if(i>=0)
			visibleButtons[i-offset].select();
	}
	
	private void resetArrowButtonsEnabled(){
		leftArrowBtn.setEnabled(!(offset==0)&&this.getInventory().countItems() != 0);
		rightArrowBtn.setEnabled(!(offset==this.getInventory().countItems()-1)
				&&this.getInventory().countItems() != 0);
	}
	
	private Inventory getInventory() {
		return adventureFrame.getInventory();
	}
	
	public void unselectAll() {
		for(int i=0; i<visibleButtons.length; i++) {
			visibleButtons[i].unselect();
		}
	}
	
	public void select(Item item) {
		int i=this.getInventory().getIndex(item);
		visibleButtons[i-offset].select();
	}
	
	/**
	 * item in inventory was clicked
	 * @param nr
	 */
	public void inventoryItemClicked(int index, Point point) {
		Item item = this.getInventory().get(index + offset);
		Item selectedItem = adventureFrame.getScenePanel().getSelectedItem();
		
		
		point.setY(point.getY()+(int)this.getLocation().getY());
		
		if(selectedItem==null) {
			adventureFrame.getOptionPanel().itemClicked(true, point, item);
		}
		else {
			if(item.canInteract(selectedItem)) {
				item.interactWith(selectedItem);
				adventureFrame.getScenePanel().setSelectedItem(null);
				adventureFrame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				//this.unselectAll();
			}
			else {
				adventureFrame.notification("Das kann ich nicht machen.");
			}
		}
		//adventureFrame.repaint();
		reassignIcons();
	}
	
	//Action listener for the inventory buttons
	ActionListener buttonClick = new ActionListener() {
		//int x =-1;
		@Override
		public void actionPerformed(ActionEvent e) {
			InventoryButton bttn = (InventoryButton)e.getSource();
			bttn.getAdventureFrame().getDescriptionPanel().dispose();
			
			//Take the point the button itself has, and add the relative location of the mouse in the button
			Point p = new Point(((InventoryButton)e.getSource()).getLocation());
			p.setX(p.getX()+50);
			
			//call click where the index is transformed into an item
			bttn.getAdventureFrame().getInventoryPanel().inventoryItemClicked(bttn.getIndex(), p);			
		}
	};

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		this.reassignIcons();
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		this.reassignIcons();
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
