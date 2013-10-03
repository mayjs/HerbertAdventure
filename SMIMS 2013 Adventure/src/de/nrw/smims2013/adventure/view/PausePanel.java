package de.nrw.smims2013.adventure.view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import de.nrw.smims2013.adventure.parser.StoryParser;

public class PausePanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private AdventureFrame adventureFrame;

	public PausePanel(AdventureFrame pAdventureFrame) {
		adventureFrame = pAdventureFrame;
		setLayout(null);
		setBackground(new Color(10, 10, 10, 200));
		setSize(1000, 600);
		setLocation(0, 0);
		createButton(400, 100, "Fortsetzen", fortsetzen, "Setze das Spiel mit dem aktuellen Stand fort.");
		createButton(400, 200, "Speichern", speichern, "Speichere den aktuellen Spielstand.");
		createButton(400, 300, "Laden", laden, "Lade den letzten gespeicherten Spielstand.");
		createButton(400, 400, "Beenden", beenden, "Speichere und beende das Spiel.");
		setVisible(false);
	}

	public void createButton(int x, int y, String name, ActionListener action, String toolTip) {
		JButton menuButton = new JButton();
		menuButton.setSize(200, 50);
		menuButton.setLocation(x, y);
		menuButton.setText(null);
		menuButton.setToolTipText(toolTip);
		menuButton.addActionListener(action);
		menuButton.setOpaque(false);
		menuButton.setContentAreaFilled(false);
		menuButton.setIcon(new ImageIcon(ImageLoader.getImage(name)));		
		this.add(menuButton);
	}
	

	public void fortsetzen() {
		setVisible(false);
	}

	private static boolean savePlayer(String fileName) {
		try {
			ObjectOutputStream out = new ObjectOutputStream(
					new FileOutputStream(fileName));
			out.writeObject(StoryParser.getInstance());
			out.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		//return true;
	}

	private static boolean loadPlayer(String fileName) {
		ObjectInputStream in;
		try {
			in = new ObjectInputStream(new FileInputStream(fileName));
			StoryParser parser = (StoryParser) in.readObject();
			StoryParser.setInstance(parser);
			in.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean speichern() {
		return savePlayer("Save");
	}


	public boolean laden() {
		return loadPlayer("Save");
	}

	ActionListener fortsetzen = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			fortsetzen();
		}
	};

	ActionListener speichern = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			if (speichern()) {
				System.out.println("Speichern erfolgreich!");
				fortsetzen();
			} else {
				System.out.println("Speichern fehlgeschlagen!");
			}
		}
	};

	ActionListener laden = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			boolean result = laden();
			if (result == true) {
				System.out.println("Laden erfolgreich!");
				adventureFrame.repaint();
				fortsetzen();
			} else {
				System.out.println("Laden fehlgeschlagen!");
			}			
		}
	};

	ActionListener beenden = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			speichern();
			System.exit(0);
		}
	};
}
