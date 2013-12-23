package de.herbert.model;

import java.io.Serializable;

import org.w3c.dom.Element;

import de.herbert.parser.FormattedText;

public class DialogueAnswer implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private FormattedText text;
	private String id;
	private Element interaction;

	public DialogueAnswer(FormattedText text, String id) {
		this.text = text;
		this.id = id;
	}
	
	public DialogueAnswer(FormattedText text, String id, Element interaction) {
		this.text = text;
		this.id = id;
		this.interaction = interaction;
	}
	
	public Element getInteraction() {
		return interaction;
	}

	public void setInteraction(Element interaction) {
		this.interaction = interaction;
	}

	public FormattedText getText() {
		return text;
	}
	
	public String getId() {
		return id;
	}
}
