package de.herbert.parser;


import java.util.LinkedList;
import java.util.List;

import org.newdawn.slick.Color;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import de.nrw.smims2013.adventure.model.Point;

/**
 * Collection of useful functions for parsing.
 * @author Thilo
 *
 */
class ParserFunctions {
	static List<Element> getElementsByTag(NodeList list, String tag){
		List<Element> elementsByTag = new LinkedList<Element>();
		Node node;
		for(int i = 0; i < list.getLength(); i++)
		{
			if(!(node = list.item(i)).getNodeName().equals(tag) ||
					node.getNodeType() != Node.ELEMENT_NODE)
				continue;
			
			elementsByTag.add((Element)node);
		}
		return elementsByTag;
	}
	
	static List<Element> getChildElementsByTag(Element element, String tag){
		return getElementsByTag(element.getChildNodes(), tag);
	}
	
	static List<Element> getChildElements(Node node){
		return getElements(node.getChildNodes());
		
	}
	
	static List<Element> getElements(NodeList nodes){
		List<Element> elements = new LinkedList<Element>();
		Node node;
		for(int i = 0; i < nodes.getLength(); i++)
		{
			if((node = nodes.item(i)).getNodeType() != Node.ELEMENT_NODE)
				continue;
			
			elements.add((Element)node);
		}
		return elements;
	}
	
	static int makeInt(String value, int standardValue) {
		try {
			return Integer.parseInt(value);
		} catch (Exception e) {
			return standardValue;
		}
	}

	static boolean makeBoolean(String value, boolean standardValue) { 
		try {
			return Boolean.parseBoolean(value);
		} catch (Exception e) {
			return standardValue;
		}
	}
	
	static Point makePoint(Element element, String identifier, int defX, int defY){
		return new Point(makeInt(element.getAttribute(identifier + "X"), defX), makeInt(element.getAttribute(identifier + "Y"), defY));
	}
	
	static Point makePoint(Element element, String identifier){
		return makePoint(element, identifier, 15, 15);
	}
	
	static Color makeColor(String str){
		Color c = Color.decode(str);
		return c; 
	}
}
