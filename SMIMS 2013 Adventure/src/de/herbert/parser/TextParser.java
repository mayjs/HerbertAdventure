package de.herbert.parser;

import java.io.File;
import java.net.URISyntaxException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class TextParser {
	
	public void parseText(File file){
		try{
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(file);
	
			doc.getDocumentElement().normalize();
			
			Element e = doc.getDocumentElement();
			System.out.println(e.getTextContent());
		}catch(Exception e){
			
		}
	}
	
	public static void main(String[] args){
		TextParser p = new TextParser();
		try {
			p.parseText(new File(TextParser.class.getResource("/SMIMS 2013 Adventure/src/de/herbert/parser/exapmle.xml").toURI()));
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
