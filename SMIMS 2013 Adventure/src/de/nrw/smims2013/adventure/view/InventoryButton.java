package de.nrw.smims2013.adventure.view;

import java.awt.Color;

import javax.swing.JButton;

public class InventoryButton extends JButton{

	private static final long serialVersionUID = 1L;
	private AdventureFrame adventureFrame;
	private int index;
	public static final Color BACKGROUND = new Color(10, 10, 10, 50);
	public static final Color SELECTED_BACKGROUND = new Color(50, 10, 10);
	
	public AdventureFrame getAdventureFrame() {
		return adventureFrame;
	}
	
	public int getIndex() {
		return index;
	}

	public InventoryButton(int index, AdventureFrame adventureFrame){
		super();
		setSize(80, 80);
		this.adventureFrame = adventureFrame;
		this.index=index;
		setBackground(BACKGROUND);
	}
	
	public void select(){
		setBackground(SELECTED_BACKGROUND);
	}
	
	public void unselect(){
		setBackground(BACKGROUND);
	}
}
