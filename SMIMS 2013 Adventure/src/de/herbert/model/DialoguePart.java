package de.herbert.model;

import java.io.Serializable;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import de.herbert.parser.FormattedText;

public class DialoguePart implements Serializable{
	private static final long serialVersionUID = 1L;
	
	FormattedText content;
	Element interactionNodeOpen;
	Element interactionNodeClose;
	List<DialogueAnswer> answers;
	
	public DialoguePart(FormattedText content, List<DialogueAnswer> answers, Element interactionNodeOpen, Element interactionNodeClose) {
		this.content = content;
		this.answers = answers;
		this.interactionNodeOpen = interactionNodeOpen;
		this.interactionNodeClose = interactionNodeClose;
	}
	
	public FormattedText getContent() {
		return content;
	}
	public List<DialogueAnswer> getAnswers() {
		return answers;
	}
	
	public int getCountOfAnswers(){
		return answers.size();
	}
	
	public Element getInteractionNodeOpen(){
		return interactionNodeOpen;
	}
	
	public Element getInteractionNodeClose(){
		return interactionNodeClose;
	}
}
