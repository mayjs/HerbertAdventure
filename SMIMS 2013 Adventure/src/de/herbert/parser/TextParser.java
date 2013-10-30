package de.herbert.parser;


import java.awt.Font;
import java.io.File;
import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.newdawn.slick.Color;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class TextParser implements Serializable{
	private static final long serialVersionUID = 1L;
	
	static TextParser instance;
	Map <String, Font> loadedFonts = new HashMap<String, Font>();
	
	public static TextParser getInstance(){
		if(instance == null) instance = new TextParser();
		return instance;
	}
	
	public void loadFonts(Element element){
		List<Element> fonts = ParserFunctions.getChildElementsByTag(element, "font");
		for(Element e : fonts){
			loadedFonts.put(e.getAttribute("loadedName"), getFont(e));
		}
	}
	
	public FormattedText parseText(File file){
		try{
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(file);
	
			doc.getDocumentElement().normalize();
			
			Element e = doc.getDocumentElement();
			return parseText(e);
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	public FormattedText parseText(Element e){
		List<Element> fontList = ParserFunctions.getChildElementsByTag(e, "font");
		List<Text> textList = new LinkedList<Text>();
		for(Element el : fontList){
			textList.add(makeText(el));
		}
		return new FormattedText(textList);
	}
	
	private Text makeText(Element e){
		String str = e.getTextContent();
		int ind;
		while((ind = str.indexOf("\\n"))>=0){
			str = str.substring(0, ind) + "\n" + ((ind + 2 >= str.length() - 1)?"":str.substring(ind + 2, str.length()));
		}
		Text text = new Text(str, getFont(e), getColor(e));
		return text;
	}
	
	public String getText(Element e){
		return e.getTextContent();
	}
	
	public Color getColor(Element e){
		return ParserFunctions.makeColor(e.getAttribute("color"));
	}
	
	public Font getFont(Element e){
		Font f =getLoadedFont(e.getAttribute("loadedName"));
		if(f == null) f = new Font(e.getAttribute("name"), getFontStyle(e.getAttribute("style")), ParserFunctions.makeInt(e.getAttribute("size"), 12));
		return f;
	}
	
	public Font getLoadedFont(String loadedName){
		return loadedFonts.get(loadedName);
	}
	
	public int getFontStyle(String str){
		if(str == null) return Font.PLAIN;
		int returnValue = 0;
				if(str.contains("bold"))	returnValue = Font.BOLD;
				if(str.contains("italic")) 	returnValue = returnValue | Font.ITALIC;
		else 								returnValue = Font.PLAIN;
				return returnValue;
	}
}
