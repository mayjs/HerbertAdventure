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

	/**
	 * @param filename
	 *            the *.xml file
	 * @return a Player as it is defined in filename
	 */
	public Player parser(File file) {

		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(file);

			doc.getDocumentElement().normalize();

			parseItems(doc);
			parseNPC(doc);
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
			
			return player;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
	
	public Player parser(String fileName) {
		File file = new File(fileName);
		return parser(file);
	}

	public void parseInteractWith(Item item1, Item item2) {
		if (item1 != null && item1.canInteract(item2)) {
			parseInteraction(item1.getNode(item2));
		}
	}
	
	public void parseInteractWith(NPC npc, Item item){
		if(npc != null && npc.canInteract(item)){
			parseInteraction(npc.getNode(item));
		}
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
			parseInteraction(item.getUseNode());
	}

	public void parseUse(Node useNode) {
		parseInteraction(useNode);
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
	
	/**
	 * Parses all scenes in the story and puts them into scenes.
	 * Before a call of this function parseItems() and parseNPC() have to be called.
	 * @param doc the xml Document
	 */
	private void parseScenes(Document doc) {
		// scenes without neighbours

		List<Element> xmlScenes = getElementsByTag(doc.getElementsByTagName("scene"), "scene");

		Scene scene;
		for (Element e : xmlScenes) {
			if (e.getParentNode().getNodeName().equals("story")) {
				String sceneName = e.getAttribute("name");
				scene = new Scene(sceneName, makeInt(e.getAttribute("width"),
						50), makeInt(e.getAttribute("height"), 30));
				String lPosX = e.getAttribute("lPosX"), 
						lPosY = e.getAttribute("lPosY"), 
						rPosX = e.getAttribute("rPosX"), 
						rPosY = e.getAttribute("rPosY"), 
						tPosX = e.getAttribute("tPosX"), 
						tPosY = e.getAttribute("tPosY"), 
						bPosX = e.getAttribute("bPosX"), 
						bPosY = e.getAttribute("bPosY");

				Point startLeft = new Point(makeInt(lPosX, 15), makeInt(lPosY,15)), 
						startRight = new Point(makeInt(rPosX, 15),makeInt(rPosY, 15)), 
						startTop = new Point(makeInt(tPosX, 15), makeInt(tPosY, 15)), 
						startBottom = new Point(makeInt(bPosX, 15), makeInt(bPosY, 15));

				scene.setStartLeft(startLeft);
				scene.setStartRight(startRight);
				scene.setStartTop(startTop);
				scene.setStartBottom(startBottom);

				scene.blockWall(makeInt(e.getAttribute("blockedWallHeight"), 0));
				// add items

				//NodeList itemsInScene = e.getChildNodes();
				List<Element> itemsInScene = getChildElements(e);
				for (Element element : itemsInScene) {
						if (element.getNodeName().equals("item")) {
							Item it = items.get(element.getAttribute("name"));
							if (it != null)
								scene.add(
										it,
										new Point(
												makeInt(element
														.getAttribute("posX"),
														0), makeInt(element
														.getAttribute("posY"),
														0)));
						}else if(element.getNodeName().equals("npc")){
							NPC npc = npcs.get(element.getAttribute("name"));
							if(npc != null){
								npc.setPos(new Point(makeInt(element.getAttribute("posX"), 0), makeInt(element.getAttribute("posY"), 0)));
								scene.addNPC(npc);
							}
						}
					}
				scenes.put(scene.getName(), scene);
			}
		}

		// get neighbour scenes
		for (Element e : xmlScenes) {
			if (e.getParentNode().getNodeName().equals("story")) {
				String name = e.getAttribute("name");
				String topSceneName = e.getAttribute("topNeighbour"), bottomSceneName = e
						.getAttribute("bottomNeighbour"), rightSceneName = e
						.getAttribute("rightNeighbour"), leftSceneName = e
						.getAttribute("leftNeighbour");

				scenes.get(name).setTop(scenes.get(topSceneName));
				scenes.get(name).setBottom(scenes.get(bottomSceneName));
				scenes.get(name).setRight(scenes.get(rightSceneName));
				scenes.get(name).setLeft(scenes.get(leftSceneName));
			}

		}
	}

	private void parseItems(Document doc) {
		// items
		List<Element> xmlItems = getElementsByTag(doc.getElementsByTagName("item"), "item");
		//List<Element> xmlItems =  new LinkedList<Element>();
		Item item;
		for (Element e : xmlItems) {
			if(!e.getParentNode().getNodeName().equals("story"))
				continue;
			item = new Item();
			item.setHeight(makeInt(e.getAttribute("height"), 1));
			item.setWidth(makeInt(e.getAttribute("width"), 1));
			item.setName(e.getAttribute("name"));
			String cur = e.getAttribute("displayName");
			if(cur.equals("")) cur = item.getName();
			item.setDisplayName(cur);
			item.setDescription(e.getAttribute("description"));
			item.setGrapable(makeBoolean(e.getAttribute("grappable"), false));
			items.put(item.getName(), item);
		}

		// interactiveItems
		for (Element e : xmlItems) {
			if(!e.getParentNode().getNodeName().equals("story"))
				continue;
			List<Node> xmlInteractiveItems = getNodesByTag(e.getChildNodes(), "interaction");
			 // interactionNodes
			for (Node interactionNode : xmlInteractiveItems) {
				Element interactionElement;
				if(interactionNode.getNodeType() != Node.ELEMENT_NODE)
					continue;
				interactionElement = (Element) interactionNode;
				if (interactionElement.getAttribute("item").equals(""))
					items.get(e.getAttribute("name")).setUseNode(interactionNode);
				else
					items.get(e.getAttribute("name")).addInteractItem(
							items.get(interactionElement.getAttribute("item")),
							interactionNode);
			}

		}

	}
	
	private void parseNPC(Document doc) {
		// npcs
		List<Element> xmlNPCs = getElementsByTag(doc.getElementsByTagName("npc"), "npc");
		NPC npc;
		for (Element e : xmlNPCs) {
			if (!e.getParentNode().getNodeName().equals("story"))
				continue;
			npc = new NPC();
			npc.setHeight(makeInt(e.getAttribute("height"), 1));
			npc.setWidth(makeInt(e.getAttribute("width"), 1));
			npc.setName(e.getAttribute("name"));
			npc.setDescription(e.getAttribute("description"));
			npcs.put(npc.getName(), npc);
		}
		

		// interactiveItems and dialog
		for (Element e : xmlNPCs) {
			if (!e.getParentNode().getNodeName().equals("story"))
				continue;
			NodeList xmlInteractiveItems = e.getChildNodes(); // interactionNodes
			int interactiveLength = xmlInteractiveItems.getLength();
			for (int j = 0; j < interactiveLength; j++) {
				Node node;
				if ((node = xmlInteractiveItems.item(j)).getNodeType() != Node.ELEMENT_NODE
						|| !node.getNodeName().equals("interaction")){
					if(node != null && node.getNodeType() == Node.ELEMENT_NODE
							|| node.getNodeName().equals("dialog")){
						npcs.get(e.getAttribute("name")).setDialogNode(node);
					}
					continue;
				}

				Element interactionElement = (Element) node;
				if (interactionElement.getAttribute("item").equals(""))
					npcs.get(e.getAttribute("name")).setUseNode(node);
				else
					npcs.get(e.getAttribute("name")).addInteractItem(
							items.get(interactionElement.getAttribute("item")),
							node);
					
			}

		}

	}

	private void parsePlayer(Document doc) {
		// Player

		player = new Player();
		Element e = doc.getDocumentElement();

		Point playerPosition = new Point(makeInt(e.getAttribute("playerPosX"),
				15), makeInt(e.getAttribute("playerPosY"), 15));
		if (playerPosition != null)
			player.setPosition(playerPosition);

		String startSceneStr = e.getAttribute("startScene");

		if (startSceneStr != null && scenes.containsKey(startSceneStr))
			player.setScene(scenes.get(startSceneStr));

		player.setInventory(parseInventory(doc));
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

	private List<Node> getNodesByTag(NodeList list, String tag){
		List<Node> nodesByTag = new LinkedList<Node>();
		Node node;
		for(int i = 0; i < list.getLength(); i++)
		{
			if(!(node = list.item(i)).getNodeName().equals(tag))
				continue;
			
			nodesByTag.add(node);
		}
		return nodesByTag;
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
	
	private List<Node> getChildNodesByTag(Node parent, String tag){
		return getNodesByTag(parent.getChildNodes(), tag);
	}
	
	private List<Element> getChildElementsByTag(Node parent, String tag){
		return getElementsByTag(parent.getChildNodes(), tag);
	}
}
