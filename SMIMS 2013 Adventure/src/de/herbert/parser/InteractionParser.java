package de.herbert.parser;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.script.SimpleScriptContext;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import de.herbert.model.Dialogue;
import de.herbert.model.Item;
import de.herbert.model.NPC;
import de.herbert.model.Point;
import de.herbert.model.Scene;

public class InteractionParser implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Map<String, Method> iMethods = new HashMap<String, Method>();
	private static InteractionParser instance = null;
	
	private static ScriptEngineManager scriptEngineManager = new ScriptEngineManager(); 
	private static ScriptEngine engine = scriptEngineManager.getEngineByName("JavaScript");
	
	private InteractionParser(){
		// get interaction Methods
		Method[] methods = getClass().getDeclaredMethods();
		for(Method m : methods){
			m.setAccessible(true);
			if(m.getName().contains("interaction_"))
				iMethods.put(m.getName().substring(m.getName().indexOf("_") + 1), m);
		}
		
		// prepare js-Engine
		prepareEngine();
	}
	
	public static synchronized InteractionParser getInstance(){
		if(instance == null) 
			instance = new InteractionParser();
		return instance;
	}
	
	public Object executeScript(String script){
		try {
			return engine.eval(script);
		} catch (ScriptException e) {
			System.out.println("Couldn't run script:\n" + script + "\n");
			e.printStackTrace();
			return null;
		}
	}
	
	private void prepareEngine(){
		// set variables ...
		engine.put("player", StoryParser.getInstance().getPlayer());
		engine.put("inventory", StoryParser.getInstance().getPlayer().getInventory());
	}
	
	public String getInteractionName(Element interactionElement){
		return interactionElement.getAttribute("name");
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
	
	public void parseInteraction(Element element){
		if(element == null) return;
		List<Element> childElements = ParserFunctions.getChildElements(element);
		
		for(Element e : childElements){
			Method method = iMethods.get(e.getNodeName());
			if(method == null) continue;
			try {
				method.invoke(getInstance(), e);
			} catch (IllegalAccessException e1) {
				e1.printStackTrace();
			} catch (IllegalArgumentException e1) {
				e1.printStackTrace();
			} catch (InvocationTargetException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	@SuppressWarnings("unused")
	private void interaction_add(Element e) {
		if(e.hasAttribute("item"))
			interactionAddItem(e);
		else if(e.hasAttribute("npc"))
			interactionAddNPC(e);
	}
	
	private void interactionAddItem(Element e){
		Item item = StoryParser.getInstance().getItem(e.getAttribute("item"));
		if (item == null || !e.getNodeName().equals("add"))
			return;

		String location = e.getAttribute("location");
		if (location.equals("inventory"))
			interactionAddItem(item);
		else if (location.equals("scene"))
			interactionAddItem(item, StoryParser.getInstance().getPlayer().getScene(), ParserFunctions.makePoint(e, "pos"));
		else
			interactionAddItem(item, StoryParser.getInstance().getScene(location), ParserFunctions.makePoint(e, "pos"));
	}
	
	private void interactionAddItem(Item item, Scene scene, Point pos){
		scene.add(item, pos);
	}
	
	private void interactionAddItem(Item item){
		StoryParser.getInstance().getPlayer().getInventory().add(item);
	}
	
	private void interactionAddNPC(Element e){
		Scene scene;
		if(e.getAttribute("location").equals("scene")) 
			scene = StoryParser.getInstance().getPlayer().getScene();
		else
			scene = StoryParser.getInstance().getScene(e.getAttribute("location"));
		if(scene == null)
			return;
		
		NPC npc = StoryParser.getInstance().getNPC(e.getAttribute("npc"));
		npc.setPos(ParserFunctions.makePoint(e, "pos"));
		scene.addNPC(npc);
	}
	
	@SuppressWarnings("unused")
	private void interaction_remove(Element e){
		if(e.hasAttribute("item"))
			interactionRemoveItem(e);
		else if(e.hasAttribute("npc"))
			interactionRemoveNPC(e);
	}
	
	private void interactionRemoveItem(Element e){
		Item item = StoryParser.getInstance().getItem(e.getAttribute("item"));
		if(item == null || !e.getNodeName().equals("remove"))
			return;
		
		String location = e.getAttribute("location");
		if(location.equals("inventory"))
			interactionRemove(item);
		else if(location.equals("scene"))
			interactionRemove(item, StoryParser.getInstance().getPlayer().getScene());
		else if (location.equals("")) {
			if (!interactionRemove(item))
				interactionRemove(item, StoryParser.getInstance().getPlayer().getScene());
		} else
			interactionRemove(item, StoryParser.getInstance().getScene(location));
	}
	
	private boolean interactionRemove(Item item){
		return StoryParser.getInstance().getPlayer().getInventory().remove(item);
	}

	private boolean interactionRemove(Item item, Scene scene){
		if(scene == null) return false;
		return scene.remove(item);
	}
	
	private void interactionRemoveNPC(Element e){
		Scene scene;
		if(e.getAttribute("location").equals("scene")) 
			scene = StoryParser.getInstance().getPlayer().getScene();
		else
			scene = StoryParser.getInstance().getScene(e.getAttribute("location"));
		if(scene == null)
			return;
		
		scene.removeNPC(StoryParser.getInstance().getNPC(e.getAttribute("npc")));
	}
	
	@SuppressWarnings("unused")
	private void interaction_changeScene(Element e){
		Scene scene = StoryParser.getInstance().getScene(e.getAttribute("to"));
		if(scene == null || !e.getNodeName().equals("changeScene"))
			return;
		
		StoryParser.getInstance().getPlayer().setScene(scene);
		StoryParser.getInstance().getPlayer().setPosition(getStartPos(scene, e.getAttribute("start")));
	}
	
	public void parseInteractWith(Item item1, Item item2){
		if(item1 != null && item1.canInteract(item2))
			parseInteraction((Element)item1.getNode(item2));
	}
	
	public void parseInteractWith(NPC npc, Item item){
		if(npc != null && npc.canInteract(item))
			parseInteraction((Element)npc.getNode(item));
	}
	

	public void parseUse(Item item) {
		if (item != null)
			parseInteraction((Element)item.getUseNode());
	}

	public void parseUse(Node useNode) {
		parseInteraction((Element)useNode);
	}
	
	@SuppressWarnings("unused")
	private void interaction_replace(Element e){
		Item item1 = StoryParser.getInstance().getItem(e.getAttribute("item1")), item2 = StoryParser.getInstance().getItem(e.getAttribute("item2"));
		if(item1 == null || item2 == null || !e.getNodeName().equals("replace")) return;
		
		// find out the location of item1
		String location = e.getAttribute("location");
		if (location.equals("inventory"))
			;	//TODO add method replace() in Inventory
		else if (location.equals("scene"))
			interactionReplace(item1, item2, StoryParser.getInstance().getPlayer().getScene());
		else
			interactionReplace(item1, item2, StoryParser.getInstance().getScene(location));
			
	}
	
	private void interactionReplace(Item item1, Item item2, Scene scene){
		if(scene == null){
			_err("interaction replace: unknown scene");
			return;
		}
		//Point itemPos = scene.getItemsAsMap().get(item1);
		Point itemPos = scene.getItemPosition(item1);
		scene.remove(item1);
		scene.add(item2, itemPos);
	}
	
	@SuppressWarnings("unused")
	private void interaction_openDialogue(Element interactionElement){
		if(!interactionElement.getNodeName().equals("openDialogue"))	return;
		Dialogue dialogue = DialogueParser.getInstance().getDialogue(interactionElement.getAttribute("dialogueName"));
		// TODO add code to open the dialoge
	}
	
	@SuppressWarnings("unused")
	private void interaction_closeDialogue(Element interactionElement){
		// TODO add code to close dialogue
	}
	
	@SuppressWarnings("unused")
	private void interaction_javaScript(Element interactionElement){
		executeScript(interactionElement.getTextContent());
	}
	
	private void _err(String msg){
		System.out.println(msg);
	}
}
