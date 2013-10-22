package de.herbert.parser;

import java.util.LinkedList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

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
		NodeList list = node.getChildNodes();
		List<Element> elements = new LinkedList<Element>();
		Node childNode;
		for(int i = 0; i < list.getLength(); i++)
		{
			if((childNode = list.item(i)).getNodeType() != Node.ELEMENT_NODE)
				continue;
			
			elements.add((Element)childNode);
		}
		return elements;
	}
}
