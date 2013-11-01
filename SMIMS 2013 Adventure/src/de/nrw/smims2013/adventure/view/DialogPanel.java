package de.nrw.smims2013.adventure.view;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class DialogPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JLabel dialog = new JLabel();
	private Thread dialogThread;

	private AdventureFrame adventureFrame;

	public DialogPanel(AdventureFrame adventureFrame) {
		this.adventureFrame = adventureFrame;
		setLayout(null);
		setBackground(Color.GRAY);
		setSize(600, 25);
		setLocation(200, 550);
		dialog.setSize(600, 25);
		dialog.setLocation(0, 0);
		dialog.setHorizontalAlignment(SwingConstants.CENTER);
		dialog.setVerticalAlignment(SwingConstants.CENTER);
		this.add(dialog);
		setVisible(false);
	}

	public void setDialog(String text) {
		if (dialogThread != null && !dialogThread.isInterrupted()) {
			dialogThread.interrupt();
			dialogThread = null;
		}
		dialogThread = new DialogThread(this, dialog, text);
		dialogThread.start();
	}
}