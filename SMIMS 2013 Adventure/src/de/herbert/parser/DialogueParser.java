package de.herbert.parser;

import java.io.File;
import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import de.herbert.model.Dialogue;
import de.herbert.model.DialogueAnswer;
import de.herbert.model.DialoguePart;

public class DialogueParser implements Serializable{
	private static final long serialVersionUID = 1L;

	private static DialogueParser instance;
	
	private Map<String, Dialogue> loadedDialogues = new HashMap<String, Dialogue>();
	
	public static DialogueParser getInstance(){
		if(instance == null) instance = new DialogueParser();
		return instance;
	}
	
	/**
	 * loads Dialogues which are Child Elements of parentElement into an internal Map.
	 * @param parentElement
	 */
	public void loadDialogues(Element parentElement){
		List<Element> dialogueElements = ParserFunctions.getChildElementsByTag(parentElement, "dialoge");
		for(Element e : dialogueElements)
			loadedDialogues.put(e.getAttribute("name"), parseDialogue(e));
	}
	
	public Dialogue parseDialogue(File file){
		try{
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(file);
	
			doc.getDocumentElement().normalize();
			
			Element e = doc.getDocumentElement();
			return parseDialogue(e);
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Parses a dialogue
	 * @param dialogueElement the element of the dialogue
	 * @return the parsed Dialogue object
	 */
	public Dialogue parseDialogue(Element dialogueElement){
		if(!dialogueElement.getNodeName().equals("dialogue")) return null;
		
		Dialogue dialogue = new Dialogue();
		for(Element e : ParserFunctions.getChildElements(dialogueElement)){
			if(!e.getNodeName().equals("part")) continue;
			dialogue.addPart(parseDialoguePart(e), e.getAttribute("id"));
		}
		dialogue.setFirstPart(dialogueElement.getAttribute("startPart"));
		return dialogue;
	}
	
	/**
	 * Parses a DialoguePart
	 * @param partElement the element of the DialoguePart
	 * @return the parsed DialugePart
	 */
	private DialoguePart parseDialoguePart(Element partElement){
		// parse answers
		List<DialogueAnswer> answers = new LinkedList<DialogueAnswer>();
		for(Element e : ParserFunctions.getChildElementsByTag(partElement, "answer")){
			answers.add(parseDialogueAnswer(e));
		}
		// parse content text
		FormattedText text = TextParser.getInstance().parseText(partElement);
		// parse interactions
		List<Element> interactionNodes = ParserFunctions.getChildElementsByTag(partElement, "interaction");
		Element open = null, close = null;
		for(Element e : interactionNodes){
			if(e.getAttribute("name").equals("open"))
				open = e;
			else if(e.getAttribute("name").equals("close"))
				close = e;
		}
		
		return new DialoguePart(text, answers, open, close);	// create DialoguePart
	}
	
	/**
	 * Parses a DialogueAnswer
	 * @param answerElement the element of the DialogueAnswer
	 * @return the parsed DialogueAnswer
	 */
	private DialogueAnswer parseDialogueAnswer(Element answerElement){
		FormattedText text = TextParser.getInstance().parseText(answerElement);
		String id = answerElement.getAttribute("id");
		List<Element> interactions = ParserFunctions.getChildElementsByTag(answerElement, "interaction");
		if(interactions.size() > 0) return new DialogueAnswer(text, id, interactions.get(0));
		return new DialogueAnswer(text, id);
	}
	
	/**
	 * returns the loaded Dialogue associated with name
	 * @param name the name of the loaded Dialogue
	 * @return the Dialogue
	 */
	public Dialogue getDialogue(String name){
		return loadedDialogues.get(name);
	}
}
