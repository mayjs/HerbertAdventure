package de.herbert.model;

import java.io.Serializable;

import de.herbert.parser.FormattedText;

public class DialogueAnswer implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private FormattedText text;
	private String id;
	
	public DialogueAnswer(FormattedText text, String id) {
		this.text = text;
		this.id = id;
	}

	public FormattedText getText() {
		return text;
	}
	
	public String getId() {
		return id;
	}
}
