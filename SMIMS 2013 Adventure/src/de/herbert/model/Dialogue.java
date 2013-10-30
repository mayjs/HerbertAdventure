package de.herbert.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import de.herbert.parser.FormattedText;

public class Dialogue implements Serializable{
	private static final long serialVersionUID = 1L;
	Map<String, DialoguePart> parts = new HashMap<String, DialoguePart>();
	DialoguePart curPart;
	String first;
	
	public Dialogue(){
		List<DialogueAnswer> ans = new LinkedList<DialogueAnswer>();
		ans.add(new DialogueAnswer(new FormattedText("weiter"), "part2"));
		DialoguePart p = new DialoguePart(new FormattedText("Hast du Käse für\n mich?"), ans, null, null);
		parts.put("part1", p);
		curPart = p;
		ans = new LinkedList<DialogueAnswer>();
		ans.add(new DialogueAnswer(new FormattedText("Hier, kannst ihn haben."), "part1"));
		ans.add(new DialogueAnswer(new FormattedText("Nein!"), "part2"));
		ans.add(new DialogueAnswer(new FormattedText("Nein, nein \nNEIN!"), "part2"));
		ans.add(new DialogueAnswer(new FormattedText("von mir aus ?¿"), "part1"));
		p = new DialoguePart(new FormattedText("Bitte! :( \n\nsonst kann ich keinen Auflauf machen!"), ans, null, null);
		parts.put("part2", p);
	}
	
	public void setFirstPart(String id){
		setCurPart(id);
		first = id;
	}
	public void addPart(DialoguePart part, String id){
		parts.put(id, part);
	}
	
	public DialoguePart getPart(String id){
		return parts.get(id);
	}
	
	public void setCurPart(String id){
		curPart = parts.get(id);
	}
	
	public DialoguePart getCurPart(){
		return curPart;
	}
	
	public String getFirstPartId(){
		return first;
	}
}
