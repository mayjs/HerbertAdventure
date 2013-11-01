package de.nrw.smims2013.adventure.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import de.nrw.smims2013.adventure.model.Item;

public class DescriptionPanel extends JPanel
{
	private static final long serialVersionUID = 1L;
	private AdventureFrame adventureFrame;
	private BufferedImage image;
	private JLabel header;
	private JTextArea infoText;
	
	public DescriptionPanel(AdventureFrame pAdventureFrame)
	{
		adventureFrame=pAdventureFrame;
		this.setVisible(false);
		this.setLayout(null);	
		this.setBackground(new Color(205, 133, 63));
//		this.setSize(600,360);
//		this.setLocation(200,120);
//		header=new JLabel();
//		header.setSize(600, 30);
//		header.setFont(new Font("DialogInput", Font.BOLD, 40));
//		header.setHorizontalAlignment(JLabel.CENTER);
//		header.setVerticalAlignment(JLabel.CENTER);
//		infoText=new JTextArea();
//		infoText.setFont(new Font("DialogInput", Font.BOLD, 20));
//		infoText.setBackground(new Color(205, 133, 63));
//		infoText.setFocusable(false);
//		infoText.setBounds(50, 50, 500, 500);
//		infoText.setEnabled(false);
	}
	
	public void popUp(Item pItem)
	{			
		this.setSize(300,180);
		this.setLocation(350,210);
		header=new JLabel();
		header.setSize(300, 30);
		header.setText(pItem.getDisplayName());
		header.setFont(new Font("DialogInput", Font.BOLD, 30));
		header.setHorizontalAlignment(SwingConstants.CENTER);
		header.setVerticalAlignment(SwingConstants.CENTER);
		header.setLocation(0, 0);
		infoText=new JTextArea();
		infoText.setFont(new Font("DialogInput", Font.BOLD, 15));
		infoText.setBackground(new Color(205, 133, 63));
		infoText.setFocusable(false);
		infoText.setBounds(0, 30, 500, 500);
		infoText.setEnabled(false);
		infoText.setText(toGoodString(pItem.getDescription(), 31));
		this.add(header);
		this.add(infoText);
		this.setVisible(true);
	}
	
	public void notification(String message)
	{
		this.setSize(320,40);
		this.setLocation(375,250);
		infoText=new JTextArea();
		infoText.setFont(new Font("DialogInput", Font.BOLD, 20));
		infoText.setBackground(new Color(205, 133, 63));
		infoText.setFocusable(false);
		infoText.setBounds(5,5,300,180);		
		message=toGoodString(message, 40);
		infoText.setText(message);
		this.add(infoText);
		this.setVisible(true);
	}
	
	public void dispose()
	{
		this.setVisible(false);
		this.removeAll();
	}


private String toGoodString(String text,int lineLength)
	{
		int i=lineLength;
		String n;
		String p=text;
		int z=i;
		while(i<p.length())
		{
			String d="  ";
			if(p.charAt(i)!=d.charAt(1))
			{
				n=p.substring(0, i) + "-\n" + p.substring(i);
			}
			else n=p.substring(0, i) + "\n" + p.substring(i+1);
			p=n;
			i=i+z;
		}
		return p;
	}

}
