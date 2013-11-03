package de.nrw.smims2013.adventure.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import de.nrw.smims2013.adventure.main.Main;
import de.nrw.smims2013.adventure.model.Item;
import de.nrw.smims2013.adventure.model.Point;

/**
 * Option Panel displays the options the player has on items.
 *
 */
public class OptionPanel extends JPanel
{	
	private static final long serialVersionUID = 1L;
	private int i;
	private AdventureFrame adventureFrame;
	
	/**
	 * Popups the OptionPanel
	 * @param pButton Array of Buttons to popup
	 * @param pPoint Point where to popup
	 */

	
	public void popUp(ItemButton[] pButton, Point pPoint, Item item)
	{
		this.setLayout(null);
		i=0;
		while(i<pButton.length)
		{
			pButton[i].setSize(120, 15);
			pButton[i].setLocation(5, (i*18+19));
			pButton[i].setFont(new Font("DialogInput", Font.BOLD, 10));		
			this.add(pButton[i]);
			i++;
		}
		
		this.setSize(130, 18*i+19);
		JLabel header=new JLabel();
		header.setText(item.getDisplayName());
		header.setSize(130, 16);
		header.setLocation(0, 0);
		header.setHorizontalAlignment(SwingConstants.CENTER);
		header.setVerticalAlignment(SwingConstants.CENTER);
		this.add(header);
		
		this.setLocation(pPoint.getX(),pPoint.getY()-(18*i+19));
		this.setVisible(true);
		
	}
	
	public void dispose()
	{
		this.removeAll();
		this.setVisible(false);
		adventureFrame.repaint();
	}
	public OptionPanel(AdventureFrame adventureFrame)
	{
		//this.setSize(70,80);
		this.adventureFrame = adventureFrame;
		this.setBackground(new Color(205, 133, 63, 230));
		this.setVisible(true);
	}
	
	/**
	 * Popup Option Panel when item is clicked.
	 * @param isInInventory true if Item is in Inventory
	 * @param point relative point that was clicked
	 * @param item Item that was clicked onto
	 */
	public void itemClicked(boolean isInInventory, Point point, Item item) {
		adventureFrame.getOptionPanel().dispose();
		List<ItemButton> buttons = new LinkedList<ItemButton>();
		ItemButton current = null;
		if (item == null)
			return;
		
		//Button to view the item
		current = new ItemButton("Ansehen", item, adventureFrame);
		current.addActionListener(view);
		buttons.add(current);
		
		if(!isInInventory && item.isGrapable()) {
			current = new ItemButton("Nehmen", item, adventureFrame);
			current.addActionListener(take);
			buttons.add(current);
		}
		
		if (item.isUsable()) {
			current = new ItemButton("Benutzen", item, adventureFrame);
			current.addActionListener(use);
			buttons.add(current);
		}
		if (isInInventory) {
			current = new ItemButton("Interagieren", item, adventureFrame);
			current.addActionListener(interact);
			buttons.add(current);
		}
		
		
		ItemButton[] buttonArray = buttons.toArray(new ItemButton[0]);
		
		this.popUp(buttonArray, point, item);
		adventureFrame.repaint();
	}
	
	ActionListener view = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			ItemButton bttn = (ItemButton)e.getSource();
			bttn.getAdventureFrame().getOptionPanel().dispose();
			bttn.getAdventureFrame().getDescriptionPanel().popUp(bttn.getItem());
		}
	};
	
	ActionListener take = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			ItemButton bttn = (ItemButton)e.getSource();
			Point playerPos = bttn.getAdventureFrame().getPlayer().getPosition();
			Point itemPos = bttn.getAdventureFrame().getPlayer().getScene().getItemPosition(bttn.getItem());
			
			if(Math.abs(playerPos.getX() - itemPos.getX()) <= 15){
				bttn.getAdventureFrame().getPlayer().getScene().remove(bttn.getItem());
				bttn.getAdventureFrame().getInventory().add(bttn.getItem());
				bttn.getAdventureFrame().repaint();
			}
			else{
				if(Main.DEBUG_ON) System.out.println("Zu weit weg.");
				bttn.getAdventureFrame().notification("Das ist zu weit weg.");
			}
			bttn.getAdventureFrame().getOptionPanel().dispose();
		}
	};
	
	ActionListener interact = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			ItemButton bttn = (ItemButton)e.getSource();
			bttn.getAdventureFrame().getScenePanel().setSelectedItem(bttn.getItem());
			bttn.getAdventureFrame().repaint();
			bttn.getAdventureFrame().getOptionPanel().dispose();
			if(bttn.getAdventureFrame().getInventory().contains(bttn.getItem()))
				bttn.getAdventureFrame().getInventoryPanel().select(bttn.getItem());
			
			bttn.getAdventureFrame().setCursorFromItem(bttn.getItem());
		}
	};
	
	ActionListener use = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			ItemButton bttn = (ItemButton)e.getSource();
			bttn.getItem().use();
			bttn.getAdventureFrame().repaint();
			bttn.getAdventureFrame().getOptionPanel().dispose();
		}
	};
}
