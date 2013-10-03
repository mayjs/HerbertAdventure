package de.nrw.smims2013.adventure.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class MenuButton extends JButton 
{	

	private static final long serialVersionUID = 1L;
	private AdventureFrame adventureFrame;
	private boolean triggered;
	
	public MenuButton(AdventureFrame pAdventureFrame)
	{
		triggered = false;
		adventureFrame = pAdventureFrame;
		setSize(100,100);
		setLocation(0,0); 
		ActionListener al = new ActionListener() {
		      public void actionPerformed( ActionEvent e ) 
		      {		 
		    	  	triggered = triggered ? false : true;
		    	  	if(triggered)
		    	  		adventureFrame.getScenePanel().stopWalking();
		    	    adventureFrame.getPausePanel().setVisible(triggered);
		    	    
		    	    }
		      };
		      this.addActionListener( al );
		setIcon(new ImageIcon(ImageLoader.getImage("PauseButton")));
	}
}
