package de.herbert.model;

import java.util.List;

import de.herbert.parser.FormattedText;

public class DialoguePart {
	FormattedText content;
	List<DialogueAnswer> answers;
	
	public DialoguePart(FormattedText content, List<DialogueAnswer> answers) {
		this.content = content;
		this.answers = answers;
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
}
