package de.nrw.smims2013.adventure.parser;

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
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import de.nrw.smims2013.adventure.main.Main;
import de.nrw.smims2013.adventure.model.Inventory;
import de.nrw.smims2013.adventure.model.Item;
import de.nrw.smims2013.adventure.model.Player;
import de.nrw.smims2013.adventure.model.Point;
import de.nrw.smims2013.adventure.model.Scene;
import de.nrw.smims2013.adventure.model.NPC;

public class StoryParser implements Serializable {

	private static final long serialVersionUID = 1L;

	private static StoryParser storyParser = null;

	private Map<String, Item> items = new HashMap<String, Item>();
	private Map<String, Scene> scenes = new HashMap<String, Scene>();
	private Map<String, NPC> npcs = new HashMap<String, NPC>();
	private Player player = new Player();

	public static synchronized StoryParser getInstance() {
		if (storyParser == null) {
			storyParser = new StoryParser();
		}
		return storyParser;
	}

	public static synchronized void setInstance(StoryParser pStoryParser) {
		storyParser = pStoryParser;
	}

	private StoryParser() {

	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public Player parser(String fileName) {
		File file = new File(fileName);
		return parser(file);
	}
	
	/**
	 * Creates a Player out of the information found in file.
	 * 
	 * @param file the *.xml file to be parsed
	 * @return a player
	 */
	public Player parser(File file) {

		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			dbf.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
			
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(file);

			doc.getDocumentElement().normalize();

			parseItems(doc);
			parseNPCs(doc);
			parseScenes(doc);
			parsePlayer(doc);
			
			if(Main.DEBUG_ON){
				System.out.println("-------------Parser-------------");
				System.out.println("parsed Items:");
				for(Item item : items.values()){
					System.out.println(item.toString());
				}
				System.out.println("\nparsed Scenes:");
				for(Scene scene : scenes.values()){
					System.out.println(scene.toString());
				}
				System.out.println("\nparsed NPCs:");
				for(NPC npc: npcs.values()){
					System.out.println(npc.toString());
				}
				System.out.println("\nparsed Player:");
				System.out.println(getPlayer().toString());
				System.out.println("-----------End Parser----------\n\n");
			}
			
			return getPlayer();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
	
	private void parseItems(Document doc){
		List<Element> itemElements = getChildElementsByTag(doc.getDocumentElement(), "item");
		
		for(Element e : itemElements){
			addItem(makeItem(e));
		}
		// add interaction Items
		for(Element e : itemElements){
			addInteractionNodesToItem(e);
		}
	}

	private void parseScenes(Document doc){
		List<Element> sceneElements = getChildElementsByTag(doc.getDocumentElement(), "scene");
		
		for(Element e : sceneElements){
			addScene(makeScene(e));
		}
		// parse neighbour scenes
		for(Element e : sceneElements){
			addNeighbourScenes(e);
		}
	}
	
	private void parseNPCs(Document doc){
		List<Element> npcElements = getChildElementsByTag(doc.getDocumentElement(), "npc");
		
		for(Element e : npcElements){
			addNPC(makeNPC(e));
		}
	}
	
	private Player parsePlayer(Document doc){
		Player player = new Player();
		
		player.setPosition(makePoint(doc.getDocumentElement(), "playerPos"));
		player.setScene(getScene(doc.getDocumentElement().getAttribute("startScene")));
		player.setInventory(parseInventory(doc));
		this.player = player;
		return player;
	}

	private int makeInt(String value, int standardValue) {
		try {
			return Integer.parseInt(value);
		} catch (Exception e) {
			return standardValue;
		}
	}

	private boolean makeBoolean(String value, boolean standardValue) { 
		try {
			return Boolean.parseBoolean(value);
		} catch (Exception e) {
			return standardValue;
		}
	}
	
	private Point makePoint(Element element, String identifier, int defX, int defY){
		return new Point(makeInt(element.getAttribute(identifier + "X"), defX), makeInt(element.getAttribute(identifier + "Y"), defY));
	}
	
	private Point makePoint(Element element, String identifier){
		return makePoint(element, identifier, 15, 15);
	}

	private NPC makeNPC(Element element){
		if(!element.getNodeName().equals("npc"))
			return null;
		
		// get attributes
		NPC npc = new NPC();
		npc.setName(element.getAttribute("name"));
		npc.setDescription(element.getAttribute("descrpition"));
		npc.setHeight(makeInt(element.getAttribute("height"), 20));
		npc.setWidth(makeInt(element.getAttribute("width"), 10));
		
		// parse the rest of the content
		List<Element> childElements = getChildElements(element);
		for(Element e : childElements){
			if(e.getNodeName().equals("interaction")){
				String item = e.getAttribute("item");
				if(item.equals("")){
					npc.setUseNode(e);
				}else{
					npc.addInteractItem(getItem(item), e);
				}
			}else if(e.getNodeName().equals("dialog")){
				npc.setDialogNode(e);
			}
		}
		
		return npc;
	}
	
	private Item makeItem(Element element){
		if(!element.getNodeName().equals("item"))
			return null;
		
		// parse attributes
		Item item = new Item();
		item.setName(element.getAttribute("name"));
		item.setDisplayName(element.getAttribute("displayName"));
		if(item.getDisplayName().equals("")) item.setDisplayName(item.getName());
		item.setDescription(element.getAttribute("description"));
		item.setWidth(makeInt(element.getAttribute("width"), 1));
		item.setHeight(makeInt(element.getAttribute("height"), 1));
		item.setGrapable(makeBoolean(element.getAttribute("grappable"), false));
		
		return item;
	}

	private Scene makeScene(Element element){
		if(!element.getNodeName().equals("scene"))
			return null;
		
		// parse attributes
		Scene scene = new Scene(element.getAttribute("name"),
								makeInt(element.getAttribute("width"), 100),
								makeInt(element.getAttribute("height"), 60));
		scene.setStartBottom(makePoint(element, "bPos"));
		scene.setStartTop(makePoint(element, "tPos"));
		scene.setStartRight(makePoint(element, "rPos"));
		scene.setStartLeft(makePoint(element, "lPos"));
		scene.blockWall(makeInt(element.getAttribute("blockedWallHeight"), 0));
		// parse the rest of the content of the scene excluding neighbour scenes
		List<Element> childElements = getChildElements(element);
		for(Element e : childElements){
			if(e.getNodeName().equals("item")){
				scene.add(getItem(e.getAttribute("name")),
						makePoint(e, "pos"));
			}else if(e.getNodeName().equals("npc")){
				NPC npc = getNPC(e.getAttribute("name"));
				npc.setPos(makePoint(e, "pos"));
				scene.addNPC(npc);
			}
		}
		
		return scene;
	}

	private void addNeighbourScenes(Element element){
		Scene scene = getScene(element.getAttribute("name"));
		if(!element.getNodeName().equals("scene") || scene == null)
			return;
		
		scene.setRight(getScene(element.getAttribute("rightNeighbour")));
		scene.setLeft(getScene(element.getAttribute("leftNeighbour")));
		scene.setBottom(getScene(element.getAttribute("bottomNeighbour")));
		scene.setTop(getScene(element.getAttribute("topNeighbour")));
	}
	
	private void addInteractionNodesToItem(Element itemElement){
		Item item = getItem(itemElement.getAttribute("name"));
		if(!itemElement.getNodeName().equals("item") || item == null)
			return;
		
		List<Element> childElements = getChildElements(itemElement);
		for(Element e : childElements){
			if(e.getNodeName().equals("interaction")){
				String itemName = e.getAttribute("item");
				if(itemName.equals("")){
					item.setUseNode(e);
				}else{
					item.addInteractItem(getItem(itemName), e);
				}
			}
		}
	}
	
	private void addScene(Scene scene){
		scenes.put(scene.getName(), scene);
	}
	
	private void addNPC(NPC npc){
		npcs.put(npc.getName(), npc);
	}
	
	private void addItem(Item item){
		items.put(item.getName(), item);
	}
	
	private Item getItem(String name){
		return items.get(name);
	}
	
	private NPC getNPC(String name){
		return npcs.get(name);
	}
	
	private Scene getScene(String name){
		return scenes.get(name);
	}
	
	private List<Element> getElementsByTag(NodeList list, String tag){
		List<Element> elementsByTag = new LinkedList<Element>();
		Node node;
		for(int i = 0; i < list.getLength(); i++)
		{
			if(!(node = list.item(i)).getNodeName().equals(tag) ||
					node.getNodeType() != Node.ELEMENT_NODE)
				continue;
			
			elementsByTag.add((Element)node);
		}
		return elementsByTag;
	}
	
	private List<Element> getChildElementsByTag(Element element, String tag){
		return getElementsByTag(element.getChildNodes(), tag);
	}
	
	private List<Element> getChildElements(Node node){
		NodeList list = node.getChildNodes();
		List<Element> elements = new LinkedList<Element>();
		Node childNode;
		for(int i = 0; i < list.getLength(); i++)
		{
			if((childNode = list.item(i)).getNodeType() != Node.ELEMENT_NODE)
				continue;
			
			elements.add((Element)childNode);
		}
		return elements;
	}
	
	private Point getStartPos(Scene scene, String pos){
		if(pos.equals("top"))
			return scene.getStartTop();
		if(pos.equals("bottom"))
			return scene.getStartBottom();
		if(pos.equals("right"))
			return scene.getStartRight();
		if(pos.equals("left"))
			return scene.getStartLeft();
		return new Point(15, 15);
	}
	
	// from here on: interaction parsing:
	
//	private void parseInteraction(Element element){
//		List<Element> childElements = getChildElements(element);
//		for(Element e : childElements){
//			if(e.getNodeName().equals("add")){
//				interactionAdd(e);
//			}else if(e.getNodeName().equals("remove")){
//				interactionRemove(e);
//			}else if(e.getNodeName().equals("changeScene")){
//				interactionChangeScene(e);
//			}
//		}
//	}
	
	private void interactionAdd(Element e) {
		Item item = getItem(e.getAttribute("item"));
		if (item == null || !e.getNodeName().equals("add"))
			return;

		String location = e.getAttribute("location");
		if (location.equals("inventory"))
			interactionAdd(item);
		else if (location.equals("scene"))
			interactionAdd(item, getPlayer().getScene(), makePoint(e, "pos"));
		else
			interactionAdd(item, getScene(location), makePoint(e, "pos"));
	}
	
	private void interactionAdd(Item item, Scene scene, Point pos){
		scene.add(item, pos);
	}
	
	private void interactionAdd(Item item){
		getPlayer().getInventory().add(item);
	}
	
	private void interactionRemove(Element e){
		Item item = getItem(e.getAttribute("item"));
		if(item == null || !e.getNodeName().equals("remove"))
			return;
		
		String location = e.getAttribute("location");
		if(location.equals("inventory"))
			interactionRemove(item);
		else if(location.equals("scene"))
			interactionRemove(item, getPlayer().getScene());
		else if (location.equals("")) {
			if (!interactionRemove(item))
				interactionRemove(item, getPlayer().getScene());
		} else
			interactionRemove(item, getScene(location));
	}
	
	private boolean interactionRemove(Item item){
		return getPlayer().getInventory().remove(item);
	}

	private boolean interactionRemove(Item item, Scene scene){
		if(scene == null) return false;
		return scene.remove(item);
	}
	
	private void interactionChangeScene(Element e){
		Scene scene = getScene(e.getAttribute("to"));
		if(scene == null || !e.getNodeName().equals("changeScene"))
			return;
		
		getPlayer().setScene(scene);
		getPlayer().setPosition(getStartPos(scene, e.getAttribute("start")));
	}
	
	public void parseInteractWith(Item item1, Item item2){
		if(item1 != null && item1.canInteract(item2))
			parseInteraction((Element)item1.getNode(item2));
	}
	
	public void parseInteractWith(NPC npc, Item item){
		if(npc != null && npc.canInteract(item))
			parseInteraction((Element)npc.getNode(item));
	}
	
	public String parseDialog(Node dialogNode, int part){
		if(dialogNode == null)
			return null;
		
		NodeList xmlParts = dialogNode.getChildNodes();
		int length = xmlParts.getLength();
		Node partNode;
		int curNode = -1;
		for(int i = 0; i < length; i++){
			if(part < length && (partNode = xmlParts.item(i)).getNodeType() == Node.ELEMENT_NODE){
				curNode++;
				if(curNode == part)
					return ((Element) partNode).getAttribute("content");
			}
		}
		
			
		return null;
	}
	
	

	public void parseInteraction(Node node) {
		if (node != null && node.getNodeName().equals("interaction")) {
			List<Element> actions = getChildElements(node);
			for (Element e : actions) {
				String actionName = e.getNodeName();

				if (e.getAttribute("location").equals("inventory")) {
					if (actionName.equals("add")) {
						getPlayer().getInventory().add(
								items.get(e.getAttribute("item")));
					} else if (actionName.equals("remove")) {
						getPlayer().getInventory().remove(
								items.get(e.getAttribute("item")));
					}

				} else if (e.getAttribute("location").equals("")) {

					// <changeScene to="scene" start="right"/>
					if (actionName.equals("changeScene")) {
						getPlayer().setScene(scenes.get(e.getAttribute("to")));
						String startStr = e.getAttribute("start");
						if (startStr.equals("right")) {
							getPlayer().setPosition(
									getPlayer().getScene().getStartRight());
						} else if (startStr.equals("left")) {
							getPlayer().setPosition(
									getPlayer().getScene().getStartLeft());
						} else if (startStr.equals("top")) {
							getPlayer().setPosition(
									getPlayer().getScene().getStartTop());
						} else if (startStr.equals("bottom")) {
							getPlayer().setPosition(
									getPlayer().getScene().getStartBottom());
						}
					}else if(actionName.equals("remove")){
						if(!getPlayer().getInventory().remove(items.get(e.getAttribute("item")))){
							Scene scene = scenes.get(e.getAttribute("location"));
							if(scene==null)
								scene = getPlayer().getScene();
							if(e.hasAttribute("item"))
								scene.remove(items.get(e.getAttribute("item")));
							else
								scene.removeNPC(npcs.get(e.getAttribute("npc")));
						}
					}

				} else { // location != inventory && location != null
					Scene scene = scenes.get(e.getAttribute("location"));
					if (scene == null)
						scene = getPlayer().getScene();
					if (actionName.equals("add")) {
						Point pos = new Point(
								makeInt(e.getAttribute("posX"), 0), makeInt(
										e.getAttribute("posY"), 0));
						if(e.hasAttribute("item")){
							Item it = items.get(e.getAttribute("item"));
							scene.add(it, pos);
						}else{
							NPC npc = npcs.get(e.getAttribute("npc"));
							npc.setPos(new Point(makeInt(e.getAttribute("posX"), 0), makeInt(e.getAttribute("posY"), 0)));
							scene.addNPC(npc);
						}
					} else if (actionName.equals("remove")) {
						if(e.hasAttribute("item"))
							scene.remove(items.get(e.getAttribute("item")));
						else
							scene.removeNPC(npcs.get(e.getAttribute("npc")));
					}
				}
			}
		}
	}

	public void parseUse(Item item) {
		if (item != null)
			parseInteraction((Element)item.getUseNode());
	}

	public void parseUse(Node useNode) {
		parseInteraction((Element)useNode);
	}

	private Inventory parseInventory(Document doc) {
		// inventory
		List<Element> xmlInventory = getElementsByTag(doc.getElementsByTagName("inventory"), "inventory");
		Inventory inventory = new Inventory();

		List<Element> xmlItemsInInventory;

		for (Element inv : xmlInventory) {
			if (!inv.getParentNode().getNodeName()
					.equals("story"))
				continue;
			xmlItemsInInventory = getChildElementsByTag(inv, "item");
			for (Element e : xmlItemsInInventory) {
				inventory.add(items.get(e.getAttribute("name")));
			}
		}
		return inventory;
	}


}
