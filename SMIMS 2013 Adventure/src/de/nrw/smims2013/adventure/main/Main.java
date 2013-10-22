package de.nrw.smims2013.adventure.main;


import java.io.File;
import java.net.URISyntaxException;

import de.nrw.smims2013.adventure.model.Player;
import de.nrw.smims2013.adventure.parser.StoryParser;
import de.nrw.smims2013.adventure.view.AdventureFrame;

public class Main {
	public static final boolean DEBUG_ON = true;

	public static void main(String[] args){
//		String s = System.getProperty("user.dir") + "/src/de/nrw/smims2013/adventure/story/xml/Herbert.xml";
//		s = Main.class.getResource("/de/nrw/smims2013/adventure/story/xml/Herbert.xml").toString();
		Player player = null;
		try {
			player = StoryParser.getInstance().parser(new File(Main.class.getResource("/de/nrw/smims2013/adventure/story/xml/Herbert.xml").toURI()));
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        createAndShowGUI(player);
	}	

    private static void createAndShowGUI(Player pPlayer){
        // JFrame erzeugen
        AdventureFrame adventureFrame = new AdventureFrame("Zigarettenkaufen mit Herbert",pPlayer);
        adventureFrame.pack();
        adventureFrame.setVisible(true);
    }
}
