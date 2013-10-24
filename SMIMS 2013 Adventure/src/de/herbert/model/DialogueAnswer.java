package de.herbert.model;

import de.herbert.parser.FormattedText;

public class DialogueAnswer {
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
