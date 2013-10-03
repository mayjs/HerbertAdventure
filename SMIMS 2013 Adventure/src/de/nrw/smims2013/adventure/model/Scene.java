package de.nrw.smims2013.adventure.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Scene implements Serializable{
	

	private static final long serialVersionUID = 1L;
	private boolean[][] tileMap;
	private String name;
	private Item[][] items;
	private List<NPC> npcs = new LinkedList<NPC>();
	private Scene left, right, top, bottom;
	private Point startLeft, startRight, startTop, startBottom;
	private int blockedWallHeight;	//block so many lines of the wall from zero on
	

	public Scene () {
		//this is only for A* test
		this ("A*-Test",25,25);
		for (int y=5; y<20; y++)
			tileMap[10][y] = true;
	}
	
	public Scene(String pName, int pSizeX, int pSizeY) {
		name = pName;
		tileMap = new boolean[pSizeX][pSizeY];
		items = new Item[pSizeX][pSizeY];
		blockedWallHeight = 0;
	}
	
	//getter and setter
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Scene getLeft() {
		return left;
	}

	public void setLeft(Scene left) {
		this.left = left;
	}

	public Scene getRight() {
		return right;
	}

	public void setRight(Scene right) {
		this.right = right;
	}

	public Scene getTop() {
		return top;
	}

	public void setTop(Scene top) {
		this.top = top;
	}

	public Scene getBottom() {
		return bottom;
	}

	public void setBottom(Scene bottom) {
		this.bottom = bottom;
	}
	
	public void addNPC(NPC npc){
		this.npcs.add(npc);
	}
	
	public void removeNPC(NPC npc){
		this.npcs.remove(npc);
	}
	
	public List<NPC> getAllNPCs(){
		return npcs;
	}
	
	public NPC getNPCAt(int x, int y){
		if(npcs == null)
			return null;
		
		for(int a = 0; a < npcs.size(); a++){
			if(npcs.get(a).getPos().getX() < x &&
					npcs.get(a).getPos().getX() + npcs.get(a).getWidth() > x &&
					npcs.get(a).getPos().getY() < y &&
					npcs.get(a).getPos().getY() + npcs.get(a).getWidth() > y)
				return npcs.get(a);
		}
		return null;
	}

	public Item[][] getAllItems() {
		return items;
	}
	
	public Map<Item,Point> getItemsAsMap() {
		Map<Item,Point> result = new HashMap<Item,Point>();
		for (int i=0; i<getAllItems().length; i++) {
			for (int j=0; j<getAllItems()[i].length;j++)
			{
				Point p = new Point(i,j);
				Item item = getItemAt(p);
				if ((item != null) && (result.get(item)==null)) {
					result.put(item,p);
				}
			}
		}
		return result;
	}
	
	public Point getStartLeft() {
		return startLeft;
	}

	public Point getStartRight() {
		return startRight;
	}

	public Point getStartTop() {
		return startTop;
	}

	public Point getStartBottom() {
		return startBottom;
	}
	
	public void setStartLeft(Point startLeft) {
		this.startLeft = startLeft;
	}

	public void setStartRight(Point startRight) {
		this.startRight = startRight;
	}

	public void setStartTop(Point startTop) {
		this.startTop = startTop;
	}

	public void setStartBottom(Point startBottom) {
		this.startBottom = startBottom;
	}
	
	/**
	 * Only for testing purposes; returns the tilemap used by a*
	 * @return
	 */
	public boolean[][] getTileMap() {
		return tileMap;
	}
	
	/**
	 * Outputs Tile Map into console fur debug reasons
	 */
	public void printTileMap() {
		System.out.println("\n===== TileMap =====\n");
		for(int j=0; j<tileMap[0].length; j++) {
			for(int i=0; i<tileMap.length; i++)
				System.out.print(tileMap[i][j] == true ? 'X' : 'O');
			System.out.println();
		}
		System.out.println("\n=== End TileMap ===\n");
	}

	
	/**
	 * Block the part of the scene where the wall is
	 * @param height
	 */
	public void blockWall(int height) {
		this.blockedWallHeight = height < tileMap[0].length-1 ? height : tileMap[0].length-1;
		refreshTileMap();
	}
	
	/**
	 * Find Item at given Position in Scene.
	 * 
	 * @param pPosition
	 * @return
	 */
	public Item getItemAt(Point position) {
		for(int x=0; x<items.length; x++) {
			for(int y=0; y<items[x].length; y++) {
				if(items[x][y]!= null && 
					x <= position.getX() && position.getX() < x+items[x][y].getWidth() &&
					y <= position.getY() && position.getY() < y+items[x][y].getHeight())
					return items[x][y];
			}
		}
		return null;
	}
	public Point getItemPosition(Item i){
		for(int x=0; x<items.length; x++){
			for(int y=0; y<items[x].length; y++){
				if(items[x][y] == i)
					return new Point(x,y);
			}
		}
		return null;
	}
	
	public boolean isAtBorder(Point p) {
		return p.getX() <= 2 || p.getY() <= 2 ||
				p.getX() >= tileMap.length-3 || p.getY() >= tileMap[0].length-3;
	}
	
	public Scene changeScene(Point p,Player player) {
		int border = -1;
		
		if(p.getX() <= 2) border = 0; //Left
		else if(p.getY() <= 2) border = 1; //Top
		else if(p.getX() >= tileMap.length-3) border = 2; //Right
		else if(p.getY() >= tileMap[0].length-3) border = 3;//Bottom
		
		if(border != -1)
		{
			switch(border)
			{
			case 0: if(left!=null){player.setPosition(this.left.getStartRight()); return left;}else break;
			case 1: if(top!=null){player.setPosition(this.top.getStartBottom()); return top;}else break;
			case 2: if(right!=null){player.setPosition(this.right.getStartLeft()); return right;}else break;
			case 3: if(bottom!=null){player.setPosition(this.bottom.getStartTop()); return bottom;}else break;
			default: return null;
			}
		}
		return this;
	}
	
	/**
	 * Add Item to Scene.
	 * 
	 * @param pItem
	 * @param pPosition
	 * @param flush Should the tileMap be refreshed?
	 * @return True if added, false if not.
	 */
	public boolean add(Item pItem, Point pPosition, boolean flush) {
//		//Test all fields the item will cover
//		for(int x = pPosition.getX(); x<pPosition.getX()+pItem.getWidth() && x<items.length; x++) {
//			for(int y = pPosition.getY(); y<pPosition.getY()+pItem.getHeight() && y<items[x].length; y++) {
//				if (items[x][y]!=null)
//					return false;
//			}
//		}
		int x = pPosition.getX() < items.length ? pPosition.getX() : items.length - 1;
		int y = pPosition.getY() < items[0].length ? pPosition.getY() : items.length -1;
		items[x][y] = pItem;
		
		if (flush)
			refreshTileMap();
		
		return true;
	}
	
	/**
	 * Add Item to Scene (TileMap is refreshed)
	 * @param pItem Item to Add
	 * @param pPosition Position to add item to
	 * @return True if added, false if not
	 */
	public boolean add(Item pItem, Point pPosition) {
		return add(pItem, pPosition, true);
	}
	
	/**
	 * Remove Item from Scene and flush if true.
	 * 
	 * @param pItem
	 * @return True if removed, false if not.
	 */
	public boolean remove(Item pItem, boolean flush) {
		for(int x=0; x<items.length; x++) {
			for(int y=0; y<items[x].length; y++) {
				if (items[x][y] != null && items[x][y].equals(pItem))
					items[x][y] = null;
			}
		}
		if (flush)
			refreshTileMap();
		return true;
	}
	
	/**
	 * Flush and remove.
	 * 
	 * @param pItem
	 * @return
	 */
	public boolean remove(Item pItem) {
		return remove(pItem, true);
	}
	
	/**
	 * Refresh Tile Map
	 */
	public void refreshTileMap() {
		//reset total array to be usable
		for(int i=0; i<tileMap.length; i++) {
			for(int j=0; j<tileMap[i].length; j++) {
				tileMap[i][j] = false;
			}
		}
		
		//block wall
		for (int i = 0; i < tileMap.length; i++) {
			for(int j=0; j<this.blockedWallHeight;j++) {
				tileMap[i][j] = true;
			}
		}
		
		//block tiles
		for (int i = 0; i < items.length; i++) {
			for (int j = 0; j < items[i].length; j++) {
				if(items[i][j]!=null) {
					for(int x=i; x<i+items[i][j].getWidth(); x++)
						for(int y=j; y<j+items[i][j].getHeight(); y++)
							tileMap[x<tileMap.length ? x : items.length-1][y<tileMap[i].length ? y : tileMap[i].length-1] = true;
				}
			}
		}
	}
	
	//Only Pathfinding from here on
	
	/**
	 * 
	 * @param start
	 * @param end
	 * @return the shortest path between start and end
	 */
	public List<Point> findPath(Point start, Point end) {
		if(tileMap[end.getX()][end.getY()])
			return null;
		
		List<Node> openList = new LinkedList<Node>();
		List<Node> closedList = new LinkedList<Node>();
		
		openList.add(new Node(null,start,0,end));
		
		Node targetNode = null;
		while(!openList.isEmpty() && targetNode == null) //Search until no nodes are left oder the target is found
		{
			Node min = getMinNode(openList);
			
			List<Node> successors = min.getSuccessors(this.tileMap, end);
			for(Node n: successors)
			{
				if(!containsNodePosition(closedList, n))
				{
					if(n.getPosition().compareTo(end)){ //Path found
						targetNode = n;
						break;
					}
					
					boolean added = false;
					for(Node on : openList) //Check if the node is a shorter path to a node on the open list...
					{
						if(on.getPosition().compareTo(n.getPosition()))
						{
							if(on.getTotalCosts() >  n.getTotalCosts())
							{
								on.setKnownCost(n.getKnownCost());
								on.setPredecessor(n.getPredecessor());
							}
							added = true;
							break;
						}
					}
					if(!added) openList.add(n); //... if not add it to the OL
				}
			}
			closedList.add(min);
		}
		if(targetNode != null) //Path was found
		{
			//Recreate Path from end Node
			LinkedList<Point> path = new LinkedList<Point>();
			Node cminNode = targetNode;
			while(cminNode != null)
			{
				path.addFirst(cminNode.getPosition());
				cminNode = cminNode.getPredecessor();
			}
			return path;
		}
		
		return null;
	}
	
	/**
	 * Get the Node with minimal totalCost from a List AND REMOVE IT
	 * @param nodes
	 * @return the minimum Node
	 */
	private Node getMinNode(List<Node> nodes)
	{
		if(nodes.isEmpty()) return null;
		Node cminNode = nodes.get(0);
		for(Node n : nodes)
			if(n.getTotalCosts()<cminNode.getTotalCosts())
				cminNode = n;
		nodes.remove(cminNode);
		return cminNode;
	}
	
	private boolean containsNodePosition(List<Node> allNodes, Node n)
	{
		for(Node cn : allNodes)
			if(cn.getPosition().compareTo(n.getPosition()))
				return true;
		return false;
	}
	
	//possible directions for new Nodes (only for use in Node!!)
	private static int[][] posDirs = new int[][]{{1, 0}, {0, 1}, {-1, 0}, {0, -1},
		{1, 1}, {-1, 1}, {1, -1}, {-1, -1}};
	
	/**
	 * Only for AStar (Scene.findPath)
	 * @author Alex; Johannes
	 *
	 */
	private class Node {
		
		private Node predecessor;
		private Point position;

		private double knownCost;
		private double heuristicCost;
		
		public Node(Node predecessor, Point position, double knownCost, Point target)
		{
			this.predecessor = predecessor;
			this.position = position;
			this.knownCost = knownCost;
			this.evaluateHeuristicCost(target);
		}
		
		public Node getPredecessor() {
			return predecessor;
		}

		public Point getPosition() {
			return position;
		}

		public void setKnownCost(double knownCost) {
			this.knownCost = knownCost;
		}
		
		public double getKnownCost(){
			return knownCost;
		}

		public void setPredecessor(Node predecessor) {
			this.predecessor = predecessor;
		}
		
		public void evaluateHeuristicCost(Point target) {
			this.heuristicCost = Math.sqrt(Math.pow(position.getX()-target.getX(), 2)+Math.pow(position.getY()-target.getY(), 2));
		}
		
		public double getTotalCosts() {
			return knownCost + heuristicCost;
		}
		
		public List<Node> getSuccessors(boolean[][] map, Point target) {
			List<Node> list = new LinkedList<Node>();
			
			Point prePos = predecessor == null ? this.position : predecessor.getPosition();
			
			for (int[] nums : Scene.posDirs) {
				Point npos = new Point(position.getX()+nums[0], position.getY()+nums[1]);
				if (map.length > npos.getX() && map[0].length > npos.getY() //Check if npos is in array bounds
						&& npos.getX() >= 0 && npos.getY() >= 0 && !npos.compareTo(prePos)){ //and is not the predecessor
					if(!map[npos.getX()][npos.getY()]){ //Check if tile is not blocked
						Node n = new Node(this,npos,this.knownCost+1,target);
						list.add(n);
					}
				}
			}
			
			return list;
		}

		
	}
}
