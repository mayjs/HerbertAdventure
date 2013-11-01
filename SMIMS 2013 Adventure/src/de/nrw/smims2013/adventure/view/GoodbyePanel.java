package de.nrw.smims2013.adventure.view;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

public class GoodbyePanel extends JPanel
{
	private AdventureFrame adventureFrame;
	public GoodbyePanel(AdventureFrame pAdventurePanel)
	{
	adventureFrame=pAdventurePanel;
	this.setLayout(null);
	this.setBackground(new Color(10, 10, 10, 200));
	this.setSize(1006,728);
	this.setLocation(0, 0);
	
	JLabel header=new JLabel();
	header.setLayout(null);
	header.setText(" Vielen Dank für's spielen!");
	header.setBounds(-20, 0, 1000, 100);
	header.setHorizontalAlignment(SwingConstants.CENTER);
	header.setFont(new Font("DialogInput", Font.BOLD, 40));
	header.setForeground(Color.RED);
	
	JTextArea infoTextRight=new JTextArea();
	infoTextRight.setLayout(null);
	infoTextRight.setBounds(80, 460, 900, 100);
	infoTextRight.setBackground(new Color(0,0,0,0));
	infoTextRight.setForeground(Color.RED);
	infoTextRight.setFocusable(false);
	infoTextRight.setEnabled(false);
	infoTextRight.setFont(new Font("DialogInput", Font.BOLD, 24));
	infoTextRight.setText("Dieses Abenteuer scheint gelöst doch Herbert mag auch Oreo's\n                           Also:");
	
	JLabel startText=new JLabel();
	startText.setLayout(null);
	startText.setText("*Sei gespannt auf viele weitere Abenteuer mit Herbert und Gisela*");
	startText.setBounds(100, 510, 800, 100);
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
	subtitle.setText("Kaufe jetzt die Vollversion für 59,-");
	
	
	JTextArea infoTextLeft=new JTextArea();
	infoTextLeft.setLayout(null);
	infoTextLeft.setBounds(120,150,400,400);
	infoTextLeft.setBackground(new Color(0,0,0,0));
	infoTextLeft.setForeground(Color.RED);
	infoTextLeft.setFocusable(false);
	infoTextLeft.setEnabled(false);
	infoTextLeft.setFont(new Font("DialogInput", Font.BOLD, 15));
	infoTextLeft.setText("Johannes May\nChristof Duhme\nJulia Bresien \nChristopher Schrewing\nThilo von Neumann\nAlexander Lukas\nKatharina Kemper\n\nChristian Kemmer");
	
	JTextArea infoTextMouse=new JTextArea();
	infoTextMouse.setLayout(null);
	infoTextMouse.setBounds(330,150,500,400);
	infoTextMouse.setBackground(new Color(0,0,0,0));
	infoTextMouse.setForeground(Color.RED);
	infoTextMouse.setFocusable(false);
	infoTextMouse.setEnabled(false);
	infoTextMouse.setFont(new Font("DialogInput", Font.BOLD, 15));
	infoTextMouse.setText("Wegfindung, 2D Rasterverwaltung\nOberfläche, Speicher/Ladefunktion\nGrafik, Story\nOberfläche\nXML-Parsing\nWegfindung, Szenen Implementierung\nGrafik, Story,Oberfläche\n\nProjektleiter und Tech Support");
	
//	JTextArea thankYouText=new JTextArea();
//	thankYouText.setLayout(null);
//	thankYouText.setBounds(780,130,400,300);
//	thankYouText.setBackground(new Color(0,0,0,0));
//	thankYouText.setForeground(Color.RED);
//	thankYouText.setFocusable(false);
//	thankYouText.setEnabled(false);
//	thankYouText.setFont(new Font("DialogInput", Font.BOLD, 15));
//	thankYouText.setText("Platzhalter für einen \nweiteren Tipp");
	
	
	this.add(header);
	this.add(startText);
	this.add(infoTextRight);
	this.add(infoTextLeft);
	this.add(subtitle);
	this.add(infoTextMouse);
//	this.add(thankYouText);
	this.setVisible(false);
	
	
}
	public void popUp()
	{
		adventureFrame.getScenePanel().setVisible(false);
		adventureFrame.getInventoryPanel().setVisible(false);
		adventureFrame.getMenuPanel().setVisible(false);
		this.setVisible(true);
		adventureFrame.add(this);
	}
}
