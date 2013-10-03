package de.nrw.smims2013.adventure.test.TestMain;


import java.awt.Color;

import de.nrw.smims2013.adventure.model.Item;
import de.nrw.smims2013.adventure.model.Player;
import de.nrw.smims2013.adventure.parser.StoryParser;
import de.nrw.smims2013.adventure.view.AdventureFrame;
import de.nrw.smims2013.adventure.view.MenuPanel;
import de.nrw.smims2013.adventure.view.PausePanel;

public class InventoryTest {
	
	public static PausePanel pauseMenu;
	
	public static void main(String[] args){
		String s = System.getProperty("user.dir") + "/src/de/nrw/smims2013/adventure/test/storyexample.xml";
		Player player = StoryParser.getInstance().parser(s);
		player.getInventory().add(new Item("BlueCube", null, "", true, 2, 2));
		player.getInventory().add(new Item("GreenCube", null, "", true, 4, 4));
		player.getInventory().add(new Item("VioletCube", null, "", true, 2, 2));
        createAndShowGUI(player);
	}	

    private static void createAndShowGUI(Player pPlayer){
        // JFrame erzeugen
        AdventureFrame adventureFrame = new AdventureFrame("Adventure Game",pPlayer);

        pauseMenu = new PausePanel(adventureFrame);
        
        // MenuPanel erzeugen und Groesse/Position festlegen.       
        MenuPanel menuP = new MenuPanel(adventureFrame);       
        menuP.setBackground(Color.BLACK);
        menuP.setSize(100, 100);
        menuP.setLocation(900, 600);
        adventureFrame.add(menuP);
        
//        pauseMenu = new PausePanel();
        pauseMenu.setBackground(Color.BLACK);
        pauseMenu.setSize(500, 500);
        pauseMenu.setLocation(100, 110);
        pauseMenu.setVisible(false);
        adventureFrame.add(pauseMenu);
        
        adventureFrame.getInventoryPanel().reassignIcons();
        
        adventureFrame.setVisible(true);
    }
}
