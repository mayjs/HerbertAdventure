package de.herbert.parser;


import java.awt.Font;
import java.io.File;
import java.util.LinkedList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.newdawn.slick.Color;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class TextParser {
	static TextParser instance;
	List<Element> fonts = new LinkedList<Element>();
	
	public static TextParser getInstance(){
		if(instance == null) instance = new TextParser();
		return instance;
	}
	
	public List<Element> getFonts() {
		return fonts;
	}

	public void setFonts(List<Element> fonts) {
		this.fonts = fonts;
	}

	public void parse(File file){
		try{
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(file);
	
			doc.getDocumentElement().normalize();
			
			Element e = doc.getDocumentElement();
			System.out.println(e.getTextContent());
			fonts = ParserFunctions.getChildElementsByTag(e, "font");
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public List<Text> parseText(File file){
		try{
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(file);
	
			doc.getDocumentElement().normalize();
			
			Element e = doc.getDocumentElement();
			fonts = ParserFunctions.getChildElementsByTag(e, "font");
			List<Text> textList = new LinkedList<Text>();
			for(Element el : fonts){
				textList.add(makeText(el));
			}
			return textList;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	private Text makeText(Element e){
		Text text = new Text(e.getTextContent(), getFont(e), getColor(e));
		return text;
	}
	
	public String getText(Element e){
		return e.getTextContent();
	}
	
	public Color getColor(Element e){
		return ParserFunctions.makeColor(e.getAttribute("color"));
	}
	
	public Font getFont(Element e){
		Font f = new Font(e.getAttribute("name"), Font.BOLD, ParserFunctions.makeInt(e.getAttribute("size"), 12));
		return f;
	}
	
	public static void main(String[] args){
		TextParser p = new TextParser();
		try {
			//p.parseText(new File(Main.class.getResource("/de/nrw/smims2013/adventure/story/xml/Herbert.xml").toURI()));
			p.parseText(new File("C:/exapmle.xml"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
