package de.nrw.smims2013.adventure.view;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

public class WelcomePanel extends JPanel
{
	private AdventureFrame adventureFrame;
	public WelcomePanel(AdventureFrame pAdventureFrame)
	{
		adventureFrame=pAdventureFrame;
		this.setLayout(null);
		this.setBackground(new Color(10, 10, 10, 200));
		this.setSize(1000, 600);
		this.setLocation(0, 0);
		
		JLabel header=new JLabel();
		header.setLayout(null);
		header.setText("Zigarettenkaufen mit Herbert");
		header.setBounds(-20, 0, 1000, 100);
		header.setHorizontalAlignment(SwingConstants.CENTER);
		header.setFont(new Font("DialogInput", Font.BOLD, 40));
		header.setForeground(Color.RED);
		
		JTextArea infoTextRight=new JTextArea();
		infoTextRight.setLayout(null);
		infoTextRight.setBounds(780,400,250,150);
		infoTextRight.setBackground(new Color(0,0,0,0));
		infoTextRight.setForeground(Color.RED);
		infoTextRight.setFocusable(false);
		infoTextRight.setEnabled(false);
		infoTextRight.setFont(new Font("DialogInput", Font.BOLD, 15));
		infoTextRight.setText("Bewege dich an den Rand\num in den nächsten\nRaum zu gelangen");
		
		JLabel startText=new JLabel();
		startText.setLayout(null);
		startText.setText("*Klicke um die magische Reise zu starten*");
		startText.setBounds(200, 510, 600, 100);
		startText.setHorizontalAlignment(SwingConstants.CENTER);
		startText.setFont(new Font("DialogInput", Font.BOLD, 20));
		startText.setForeground(Color.RED);
		startText.setVisible(true);
		
		
		JLabel subtitle=new JLabel();
		subtitle.setLayout(null);
		subtitle.setHorizontalAlignment(SwingConstants.CENTER);
		subtitle.setBounds(0,45,1000,100);
		subtitle.setBackground(new Color(0,0,0,0));
		subtitle.setForeground(Color.RED);
		subtitle.setFont(new Font("DialogInput", Font.BOLD, 30));
		subtitle.setText("-Ein Mann eine Geschichte-");
		
		
		JTextArea infoTextLeft=new JTextArea();
		infoTextLeft.setLayout(null);
		infoTextLeft.setBounds(20,130,400,300);
		infoTextLeft.setBackground(new Color(0,0,0,0));
		infoTextLeft.setForeground(Color.RED);
		infoTextLeft.setFocusable(false);
		infoTextLeft.setEnabled(false);
		infoTextLeft.setFont(new Font("DialogInput", Font.BOLD, 15));
		infoTextLeft.setText("Klicke auf einen Gegenstand im Inventar, \nwähle Interagieren und klicke den zweiten \nGegenstand an. Rechtsklick zum Abbrechen");
		
		JTextArea infoTextMouse=new JTextArea();
		infoTextMouse.setLayout(null);
		infoTextMouse.setBounds(20,430,400,300);
		infoTextMouse.setBackground(new Color(0,0,0,0));
		infoTextMouse.setForeground(Color.RED);
		infoTextMouse.setFocusable(false);
		infoTextMouse.setEnabled(false);
		infoTextMouse.setFont(new Font("DialogInput", Font.BOLD, 15));
		infoTextMouse.setText("Klicke auf den Boden um zu laufen,\nKlicke auf Gegenstände für mehr Informationen");
		
		JTextArea thankYouText=new JTextArea();
		thankYouText.setLayout(null);
		thankYouText.setBounds(780,130,400,300);
		thankYouText.setBackground(new Color(0,0,0,0));
		thankYouText.setForeground(Color.RED);
		thankYouText.setFocusable(false);
		thankYouText.setEnabled(false);
		thankYouText.setFont(new Font("DialogInput", Font.BOLD, 15));
		thankYouText.setText("Platzhalter für einen \nweiteren Tipp");
		
		
		this.add(header);
		this.add(startText);
		this.add(infoTextRight);
		this.add(infoTextLeft);
		this.add(subtitle);
		this.add(infoTextMouse);
		this.add(thankYouText);
	}
	
	public void dispose()
	{
		this.setVisible(false);
		this.removeAll();
	}
}
