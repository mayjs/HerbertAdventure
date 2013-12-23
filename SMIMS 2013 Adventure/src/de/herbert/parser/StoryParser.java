package de.herbert.parser;

import java.io.File;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import de.herbert.model.Inventory;
import de.herbert.model.Item;
import de.herbert.model.Player;
import de.herbert.model.Point;
import de.herbert.model.Scene;
import de.herbert.model.NPC;

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
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(file);

			doc.getDocumentElement().normalize();

			parseItems(doc);
			parseNPCs(doc);
			parseScenes(doc);
			parsePlayer(doc);
			
			if(true){//TODO: Debug
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
		List<Element> itemElements = ParserFunctions.getChildElementsByTag(doc.getDocumentElement(), "item");
		
		for(Element e : itemElements){
			addItem(makeItem(e));
		}
		// add interaction Items
		for(Element e : itemElements){
			addInteractionNodesToItem(e);
		}
	}

	private void parseScenes(Document doc){
		List<Element> sceneElements = ParserFunctions.getChildElementsByTag(doc.getDocumentElement(), "scene");
		
		for(Element e : sceneElements){
			addScene(makeScene(e));
		}
		// parse neighbour scenes
		for(Element e : sceneElements){
			addNeighbourScenes(e);
		}
	}
	
	private void parseNPCs(Document doc){
		List<Element> npcElements = ParserFunctions.getChildElementsByTag(doc.getDocumentElement(), "npc");
		
		for(Element e : npcElements){
			addNPC(makeNPC(e));
		}
	}
	
	private Player parsePlayer(Document doc){
		Player player = new Player();
		
		player.setPosition(ParserFunctions.makePoint(doc.getDocumentElement(), "playerPos"));
		player.setScene(getScene(doc.getDocumentElement().getAttribute("startScene")));
		player.setInventory(parseInventory(doc));
		this.player = player;
		return player;
	}

	int makeInt(String value, int standardValue) {
		try {
			return Integer.parseInt(value);
		} catch (Exception e) {
			return standardValue;
		}
	}

	boolean makeBoolean(String value, boolean standardValue) { 
		try {
			return Boolean.parseBoolean(value);
		} catch (Exception e) {
			return standardValue;
		}
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
		List<Element> childElements = ParserFunctions.getChildElements(element);
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
		scene.setStartBottom(ParserFunctions.makePoint(element, "bPos"));
		scene.setStartTop(ParserFunctions.makePoint(element, "tPos"));
		scene.setStartRight(ParserFunctions.makePoint(element, "rPos"));
		scene.setStartLeft(ParserFunctions.makePoint(element, "lPos"));
		scene.blockWall(makeInt(element.getAttribute("blockedWallHeight"), 0));
		// parse the rest of the content of the scene excluding neighbour scenes
		List<Element> childElements = ParserFunctions.getChildElements(element);
		for(Element e : childElements){
			if(e.getNodeName().equals("item")){
				scene.add(getItem(e.getAttribute("name")),
						ParserFunctions.makePoint(e, "pos"),
						ParserFunctions.makeInt(e.getAttribute("posZ"), 0));
			}else if(e.getNodeName().equals("npc")){
				NPC npc = getNPC(e.getAttribute("name"));
				npc.setPos(	ParserFunctions.makePoint(e, "pos"));
							///ParserFunctions.makeInt(e.getAttribute("PosZ"), 0));
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
		
		List<Element> childElements = ParserFunctions.getChildElements(itemElement);
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
	
	public Item getItem(String name){
		return items.get(name);
	}
	
	public NPC getNPC(String name){
		return npcs.get(name);
	}
	
	public Scene getScene(String name){
		return scenes.get(name);
	}
	
	public boolean hasItem(String name){
		return items.containsKey(name);
	}
	
	public boolean hasNPC(String name){
		return npcs.containsKey(name);
	}
	
	public boolean hasScene(String name){
		return scenes.containsKey(name);
	}
	
	private Inventory parseInventory(Document doc) {
		// inventory
		List<Element> xmlInventory = ParserFunctions.getElementsByTag(doc.getElementsByTagName("inventory"), "inventory");
		Inventory inventory = new Inventory();

		List<Element> xmlItemsInInventory;

		for (Element inv : xmlInventory) {
			if (!inv.getParentNode().getNodeName()
					.equals("story"))
				continue;
			xmlItemsInInventory = ParserFunctions.getChildElementsByTag(inv, "item");
			for (Element e : xmlItemsInInventory) {
				inventory.add(items.get(e.getAttribute("name")));
			}
		}
		return inventory;
	}

	public void parseInteractWith(Item item1, Item item2){
		if(item1 != null && item1.canInteract(item2))
			InteractionParser.getInstance().parseInteraction((Element)item1.getNode(item2));
	}
	
	public void parseInteractWith(NPC npc, Item item){
		if(npc != null && npc.canInteract(item))
			InteractionParser.getInstance().parseInteraction((Element)npc.getNode(item));
	}
	
	public String parseDialog(Node d, int t){return "";}
	

	public void parseUse(Item item) {
		if (item != null)
			InteractionParser.getInstance().parseInteraction((Element)item.getUseNode());
	}

	public void parseUse(Node useNode) {
		InteractionParser.getInstance().parseInteraction((Element)useNode);
	}
}
