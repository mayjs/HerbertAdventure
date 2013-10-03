package de.nrw.smims2013.adventure.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Node;

import de.nrw.smims2013.adventure.parser.StoryParser;

public class Item implements Serializable{

	private static final long serialVersionUID = 1L;
	private String name;
	private String displayName;

	private Map<Item, Node> interactiveItems = new HashMap<Item, Node>();
	private Node useNode;
	private String description;
	private boolean grapable;
	private int width;
	private int height;

	public Item(String pname, Map<Item, Node> pinteractiveItems, String pdescription, boolean pgrapable, int pwidth, int pheight)
	{
		name=pname;
		interactiveItems=pinteractiveItems;
		description=pdescription;
		grapable=pgrapable;
		width=pwidth;
		height=pheight;
	}
	public Item()
	{
		
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
	
	public Node getUseNode(){
		return useNode;
	}
	
	/**
	 * True if player may use item with himself
	 * @return
	 */
	public boolean isUsable() {
		return getUseNode()!=null;
	}
	
	public void setUseNode(Node useNode){
		this.useNode = useNode;
	}
	
	public Node getNode(Item pInteractiveItem) {
		return interactiveItems.get(pInteractiveItem);
	}

	public String getDescription() {
		return description;
	}
	
	public int getHeight() {
		return height;
	}

	public String getName() {
		return name;
	}
	
	public String getDisplayName() {
		return displayName;
	}
	
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public int getWidth() {
		return width;
	}

	public boolean interactWith(Item pItem) {
		if (canInteract(pItem)) {
			StoryParser.getInstance().parseInteractWith(this, pItem);
		}
		return false;
	}

	public boolean isGrapable() {
		return grapable;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setGrapable(boolean grapable) {
		this.grapable = grapable;
	}

	public void setHeight(int pHeight) {
		height = pHeight;
	}

	public void setName(String pName) {
		name = pName;
	}

	public void setWidth(int pWidth) {
		width = pWidth;
	}

	/**
	 * Use the item, if possible.
	 */
	public void use() {
		StoryParser.getInstance().parseUse(this);
	}
	
	public String toString() {
		return displayName;
	}
}
