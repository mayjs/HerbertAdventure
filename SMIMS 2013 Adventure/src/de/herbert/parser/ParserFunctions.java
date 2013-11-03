package de.herbert.parser;


import java.util.LinkedList;
import java.util.List;

import org.newdawn.slick.Color;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import de.herbert.model.Point;
import de.herbert.model.Point3d;


/**
 * Collection of useful functions for parsing. 
 * @author Thilo
 *
 */
class ParserFunctions {
	
	/**
	 * 
	 * @param list 
	 * @param tag tag name to 
	 * @return List of Elements which are in list and whose tag name equals tag
	 */
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
	
	/**
	 * gets the child Elements of node.
	 * @param node 
	 * @return child Elements of node
	 */
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
	
	static int makeInt(String value, int defaultValue) {
		try {
			return Integer.parseInt(value);
		} catch (Exception e) {
			return defaultValue;
		}
	}

	static boolean makeBoolean(String value, boolean defaultValue) { 
		try {
			return Boolean.parseBoolean(value);
		} catch (Exception e) {
			return defaultValue;
		}
	}
	
	/**
	 * makes a Point out of the attributes of element: P(identifierX|identifierY)
	 * @param element
	 * @param identifier name of point
	 * @param defX default x-value
	 * @param defY default y-value
	 * @return a point
	 */
	static Point makePoint(Element element, String identifier, int defX, int defY){
		return new Point(	makeInt(element.getAttribute(identifier + "X"), defX),
							makeInt(element.getAttribute(identifier + "Y"), defY));
	}
	
	static Point makePoint(Element element, String identifier, Point defaultPoint){
		return makePoint(element, identifier, defaultPoint.getX(), defaultPoint.getY());
	}
	
	static Point makePoint(Element element, String identifier){
		return makePoint(element, identifier, 15, 15);
	}
	
	/**
	 * makes a Point3d out of the attributes of element: P(identifierX|identifierY|identifierZ)
	 * @param element
	 * @param identifier name of point
	 * @param defX default x-value
	 * @param defY default y-value
	 * @param defZ default z-value
	 * @return a Point3d
	 */
	static Point3d makePoint3d(Element element, String identifier, int defX, int defY, int defZ){
		return new Point3d(	makeInt(element.getAttribute(identifier + "X"), defX), 
							makeInt(element.getAttribute(identifier + "Y"), defY),
							makeInt(element.getAttribute(identifier + "Z"), defZ));
	}
	
	static Point3d makePoint3D(Element element, String identifier, Point3d defaultPoint){
		return makePoint3d(element, identifier, defaultPoint.getX(), defaultPoint.getY(), defaultPoint.getZ());
	}
	
	static Point3d makePoint3d(Element element, String identifier){
		return makePoint3d(element, identifier, 15, 15, 0);
	}
	
	static Color makeColor(String str){
		Color c = Color.decode(str);
		return c; 
	}
}
