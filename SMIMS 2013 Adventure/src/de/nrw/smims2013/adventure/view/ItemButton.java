package de.nrw.smims2013.adventure.view;

import javax.swing.JButton;

import de.nrw.smims2013.adventure.model.Item;

/**
 * Button in OptionPanel for using items
 *
 */
public class ItemButton extends JButton {
	
	private AdventureFrame adventureFrame;

	private static final long serialVersionUID = 1237805442539057517L;
	private Item item;
	
	ItemButton(String name, Item item, AdventureFrame adventureFrame) {
		super(name);
		this.item = item;
		this.adventureFrame = adventureFrame;
	}
	
	public Item getItem() {
		return item;
	}
	
	public AdventureFrame getAdventureFrame() {
		return adventureFrame;
	}
}
