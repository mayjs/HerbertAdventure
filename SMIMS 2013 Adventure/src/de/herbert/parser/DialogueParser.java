package de.herbert.parser;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import de.herbert.model.Dialogue;
import de.herbert.model.DialogueAnswer;
import de.herbert.model.DialoguePart;

public class DialogueParser {
	private static DialogueParser instance;
	
	public static DialogueParser getInstance(){
		if(instance == null) instance = new DialogueParser();
		return instance;
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
	
	private DialoguePart parseDialoguePart(Element partElement){
		List<DialogueAnswer> answers = new LinkedList<DialogueAnswer>();
		for(Element e : ParserFunctions.getChildElementsByTag(partElement, "answer")){
			answers.add(parseDialogueAnswer(e));
		}
		FormattedText text = TextParser.getInstance().parseText(partElement);
		return new DialoguePart(text, answers);
	}
	
	private DialogueAnswer parseDialogueAnswer(Element answerElement){
		FormattedText text = TextParser.getInstance().parseText(answerElement);
		String id = answerElement.getAttribute("id");
		return new DialogueAnswer(text, id);
	}
}
