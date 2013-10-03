package de.nrw.smims2013.adventure.model;

import java.io.Serializable;
import java.util.List;

public class Player implements Serializable{

	private static final long serialVersionUID = 1L;
	private Inventory inventory;
	private Scene scene;
	private Point position;

	public Player() {
		this(new Inventory(), new Scene(), null);
	}
	
	public Player(Inventory inventory, Scene scene, Point position) {
		super();
		this.inventory = inventory;
		this.scene = scene;
		this.position = position;
	}

	public Inventory getInventory() {
		return inventory;
	}

	public void setInventory(Inventory pInventory) {
		inventory = pInventory;
	}

	public Scene getScene() {
		return scene;
	}

	public void setScene(Scene pScene) {
		scene = pScene;
	}

	public Point getPosition() {
		return position;
	}

	public void setPosition(Point pPosition) {
		position = pPosition;
	}

	private Point currentTarget;
	private List<Point> currentPath;
	
	public Point walkTo(Point pPosition) {
		if(currentPath==null||!currentTarget.compareTo(pPosition))
		{
			currentPath = scene.findPath(position, pPosition);
			currentTarget = pPosition;
			if(currentPath != null && !currentPath.isEmpty())
				currentPath.remove(0);
		}
		if (currentPath != null && !currentPath.isEmpty() && currentPath.get(0) != null) 
		{
			setPosition(currentPath.get(0));
			currentPath.remove(0);
		}
		if(scene.isAtBorder(position))
		{
			this.scene = scene.changeScene(pPosition, this);
		}
		return this.getPosition();
	}
}
