package de.nrw.smims2013.adventure.view;

import java.awt.Color;

import javax.swing.JPanel;

public class MenuPanel extends JPanel 
{	
	
	private static final long serialVersionUID = 1L;
	private AdventureFrame adventureFrame;
	
	public MenuPanel(AdventureFrame pAdventureFrame)
	{
		adventureFrame = pAdventureFrame;
		setLayout(null);	
        setBackground(Color.BLACK);
        setSize(100, 100);
        setLocation(900, 600);
		createButton();
	}
	
	public void createButton()
	{
		MenuButton pauseButton = new MenuButton(adventureFrame);
		this.add(pauseButton);   
		
	}
}
