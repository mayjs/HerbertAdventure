package de.nrw.smims2013.adventure.test;


import java.awt.Color;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JPanel;

import de.nrw.smims2013.adventure.model.Item;
import de.nrw.smims2013.adventure.model.Player;
import de.nrw.smims2013.adventure.parser.StoryParser;
import de.nrw.smims2013.adventure.view.AdventureFrame;
import de.nrw.smims2013.adventure.view.MenuPanel;

public class MainParseInteractionTest {	
	
	public static void main(String[] args){
		String s = System.getProperty("user.dir") + "/src/de/nrw/smims2013/adventure/test/example2.xml";
		Player player = StoryParser.getInstance().parser(s);
		player.getInventory().get(0).use();
		Item item = player.getInventory().get(0);
		Item item2 = player.getInventory().get(0);
		StoryParser.getInstance().parseInteractWith(item, item2);
		StoryParser.getInstance().parseUse(player.getInventory().get(0));
		createAndShowGUI(player);
	}	
	
	private static boolean savePlayer(Player player, String fileName){
		try {
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName));
			out.writeObject(player);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}
	
	private static Player loadPlayer(String fileName){
		ObjectInputStream in;
		try {
			in = new ObjectInputStream(new FileInputStream(fileName));
			Player player = (Player)in.readObject();
			in.close();
			return player;
		}  catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

    private static void createAndShowGUI(Player pPlayer){
        // JFrame erzeugen
        AdventureFrame adventureFrame = new AdventureFrame("Adventure Game",pPlayer);

        // MenuPanel erzeugen und Groesse/Position festlegen.       
        MenuPanel menuP = new MenuPanel(null);       
        menuP.setBackground(Color.BLACK);
        menuP.setSize(100, 100);
        menuP.setLocation(900, 600);
        adventureFrame.add(menuP);
        
        JPanel pauseMenu = new JPanel();
        pauseMenu.setLayout(null);
        pauseMenu.setSize(90, 90);
        pauseMenu.setLocation(500, 410);
        pauseMenu.setBackground(Color.BLACK);
        pauseMenu.setVisible(true);
        adventureFrame.add(pauseMenu);
        adventureFrame.setVisible(true);
    }
}
