package de.nrw.smims2013.adventure.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Node;

import de.nrw.smims2013.adventure.parser.StoryParser;

public class NPC implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String name;
	private Point pos;
	private Node dialogNode;
	private Map<Item, Node> interactiveItems = new HashMap<Item, Node>();
	private Node useNode;
	private int width;
	private int height;
	private String description;
	private int dialogPos = 0;
	
	public NPC(){
		
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Point getPos() {
		return pos;
	}

	public void setPos(Point pos) {
		this.pos = pos;
	}

	public Node getDialogNode() {
		return dialogNode;
	}

	public void setDialogNode(Node dialogNode) {
		this.dialogNode = dialogNode;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public Node getNode(Item pInteractiveItem) {
		return interactiveItems.get(pInteractiveItem);
	}	
	
	public Node getUseNode(){
		return useNode;
	}
	
	public void setUseNode(Node useNode){
		this.useNode = useNode;
	}
	
	public void addInteractItem(Item pItem, Node pNode) {
		interactiveItems.put(pItem, pNode);
	}
	
	public boolean canInteract(Item pItem) {
		if (interactiveItems.containsKey(pItem)) {
			return true;
		}
		return false;
	}
	
	public boolean interactWith(Item pItem) {
		if (canInteract(pItem)) {
			StoryParser.getInstance().parseInteractWith(this, pItem);
		}
		return false;
	}
	
	public String startDialog(){
		dialogPos = 0;
		return speak();
	}
	
	/**
	 * @return the next part of the dialog
	 */
	public String speak(){
		dialogPos++;	// increase dialogPos
		String out = StoryParser.getInstance().parseDialog(dialogNode, dialogPos - 1);
		
		if(out == null)
			return startDialog();
		
		return out;
	}
	
	/**
	 * Returns the NPCs name.
	 */
	@Override
	public String toString() {
		return this.getName();
	}

}
