package de.nrw.smims2013.adventure.view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import javax.swing.JPanel;

import de.nrw.smims2013.adventure.main.Main;
import de.nrw.smims2013.adventure.model.Item;
import de.nrw.smims2013.adventure.model.NPC;
import de.nrw.smims2013.adventure.model.Player;
import de.nrw.smims2013.adventure.model.Point;
import de.nrw.smims2013.adventure.parser.StoryParser;

public class ScenePanel extends JPanel implements MouseListener {

	private static final long serialVersionUID = 1L;
	private AdventureFrame adventureFrame;
	private Item selectedItem;

	private Queue<WalkThread> walkThreads = new LinkedList<WalkThread>();

	public static final int SKALIERUNG_X = 10;
	public static final int SKALIERUNG_Y = 10;

	public ScenePanel(AdventureFrame pAdventureFrame) {
		adventureFrame = pAdventureFrame;
		// Groesse und Position werden festgelegt.
		setBackground(Color.WHITE);
		setSize(1000, 600);
		setLocation(0, 0);
		this.addMouseListener(this);
	}

	public Player getPlayer() {
		return StoryParser.getInstance().getPlayer();
	}

	public AdventureFrame getAdventureFrame() {
		return adventureFrame;
	}

	public void setAdventureFrame(AdventureFrame adventureFrame) {
		this.adventureFrame = adventureFrame;
	}
	
	public void setSelectedItem(Item item){
		this.selectedItem = item;
	}
	
	public Item getSelectedItem(){
		return selectedItem;
	}

	private int convertModelXToViewX(int x) {
		return x * SKALIERUNG_X;
	}

	private int convertModelYToViewY(int y) {
		return y * SKALIERUNG_Y;
	}

	private int convertViewXToModelX(int x) {
		return x / SKALIERUNG_X;
	}

	private int convertViewYToModelY(int y) {
		return y / SKALIERUNG_Y;
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		paintScene(g);
		paintItems(g);
		paintNPCs(g);
		paintPlayer(g);
//		paintPlayerAndItemsCorrectly(g);
	}
	
	/*private void paintPlayerAndItemsCorrectly(Graphics g)
	{
		Item[][] items = this.getPlayer().getScene().getAllItems();
		for(int y = 0; y < items[0].length; y++){
			if(this.getPlayer().getPosition().getY() == y){
				this.paintPlayer(g);
			}
			for(int x = 0; x < items.length; x++){
				Point p = new Point(x,y);			
				Item i = this.getPlayer().getScene().getItemAt(new Point(x,y));
				if(i!=null&&isBottomRight(i,x,y)){
					Point tl=this.getPlayer().getScene().getItemPosition(i);
					drawItem(g,i,tl.getX(),tl.getY());
				}
			}
		}
		//List<Item> collidingItems = new LinkedList<Item>();
	}
	
	private List<Item> getCollidingItems(Player p, Item[][] items){
		List<Item> res = new LinkedList<Item>();
		Rectangle playerRect = new Rectangle(p.getPosition().getX(),p.getPosition().getY(),
					convertViewXToModelX(ImageLoader.getImage("player").getWidth()),convertViewYToModelY(ImageLoader.getImage("player").getHeight()));
		for(int x = 0; x < items.length; x++){
			for(int y = 0; y < items[x].length; y++){
				Rectangle rect2 = new Rectangle(x,y,items[x][y].getWidth(),items[x][y].getHeight());
				if(rect2.intersects(playerRect))
					res.add(items[x][y]);
			}
		}
		
		return res;
	}
	
	private void drawItem(Graphics g, Item i, int x, int y){
		g.drawImage(ImageLoader.getImage(i.getName()),
				this.convertModelXToViewX(x),
				this.convertModelYToViewY(y),
				this.convertModelXToViewX(i.getWidth()),
				this.convertModelYToViewY(i.getHeight()),
				null);
	}
	
	private boolean isBottomRight(Item i, int x, int y){
		return (this.getPlayer().getScene().getItemAt(new Point(x+1,y)) != i &&
		this.getPlayer().getScene().getItemAt(new Point(x,y+1))!=i);
	}
	private Point convertBottomRightToTopLeft(Point br,Item i){
		return new Point(br.getX()-i.getWidth()+1,br.getY()-i.getHeight()+1);
	}*/

	private void paintScene(Graphics g) {
		BufferedImage scene = ImageLoader.getImage(getPlayer().getScene()
				.getName());
		g.drawImage(scene, 0, 0, this.getWidth(), this.getHeight(), null);
	}

	private void paintItems(Graphics g) {
		Item[][] items = getPlayer().getScene().getAllItems();
		for (int x = 0; x < items.length; x++)
			for (int y = 0; y < items[x].length; y++)
				if (items[x][y] != null) {
					g.drawImage(ImageLoader.getImage(items[x][y].getName()),
							this.convertModelXToViewX(x),
							this.convertModelYToViewY(y),
							this.convertModelXToViewX(items[x][y].getWidth()),
							this.convertModelYToViewY(items[x][y].getHeight()),
							null);
					//Debug: Hitbox
					if(Main.DEBUG_ON)
						g.drawRect(this.convertModelXToViewX(x),
							this.convertModelYToViewY(y),
							this.convertModelXToViewX(items[x][y].getWidth()),
							this.convertModelYToViewY(items[x][y].getHeight()));
				}
	}
	
	private void paintNPCs(Graphics g){
		List<NPC> npcs = getPlayer().getScene().getAllNPCs();
		if(npcs == null) return;
		for(int a = 0; a < npcs.size(); a++){
			if (Main.DEBUG_ON)
				System.out.println(npcs.get(a).getName());
			g.drawImage(ImageLoader.getImage(npcs.get(a).getName()),
					this.convertModelXToViewX(npcs.get(a).getPos().getX()),
					this.convertModelYToViewY(npcs.get(a).getPos().getY()),
					this.convertModelXToViewX(npcs.get(a).getWidth()),
					this.convertModelYToViewY(npcs.get(a).getHeight()),
					null);
		}
	}

	private void paintPlayer(Graphics g) {
		BufferedImage playerImg = ImageLoader.getImage("player");
		int x = this.convertModelXToViewX(getPlayer().getPosition().getX());
		int y = this.convertModelYToViewY(getPlayer().getPosition().getY())
				- playerImg.getHeight();
		g.drawImage(playerImg, x, y, null);
	}

	private synchronized void walkTo(Point p) {
		for (WalkThread thread : this.walkThreads) {
			thread.interrupt();
		}

		WalkThread lat = new WalkThread(getPlayer(), p, this);
		walkThreads.add(lat);
		lat.start();
	}

	public void repaintPlayer() {
//		BufferedImage img = ImageLoader.getImage("player");
//		int boundingWidth = img.getWidth() + this.convertModelXToViewX(2);
//		int boundingHeight = img.getHeight() + this.convertModelYToViewY(2);
//		Point pos = getPlayer().getPosition();
//		this.paintImmediately(
//				this.convertModelXToViewX(pos.getX())
//						- this.convertModelXToViewX(1),
//				this.convertModelYToViewY(pos.getY())
//						- this.convertModelYToViewY(1) - img.getHeight(),
//				boundingWidth, boundingHeight);
		this.paintImmediately(0, 0, this.getWidth(), this.getHeight());
	}

	public synchronized void removeFromThreadList(WalkThread thread) {
		thread.interrupt();
		walkThreads.remove(thread);
	}

	public synchronized void stopWalking() {
		while (! walkThreads.isEmpty()) {
			removeFromThreadList(walkThreads.element());
		}
	}
	
//	private Item getItemAtModelPosition(int x, int y) {
//		Item result = null;
//		for (Item item : getPlayer().getScene().getItemsAsMap().keySet()) {
//			Point p = getPlayer().getScene().getItemsAsMap().get(item);
//			int width = item.getWidth();
//			int height = item.getHeight();
//			if ((p.getX() <= x) && (p.getY() <= y) && (p.getX() + width >= x)
//					&& (p.getY() + height >= y)) {
//				result = item;
//			}
//		}
//		return result;
//	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		adventureFrame.getPausePanel().fortsetzen();
		adventureFrame.getDescriptionPanel().dispose();
		adventureFrame.getOptionPanel().dispose();

		adventureFrame.getWelcomePanel().dispose();

		//adventureFrame.getInventoryPanel().unselectAll();

		if(arg0.getButton() == MouseEvent.BUTTON1)
		{

			int x = this.convertViewXToModelX((int) arg0.getPoint().getX());
			int y = this.convertViewYToModelY((int) arg0.getPoint().getY());
			
			
			//Item item = getItemAtModelPosition(x, y);
			Item item = this.getPlayer().getScene().getItemAt(new Point(x,y));
			NPC npc = this.getPlayer().getScene().getNPCAt(x, y);
			if (item != null) {
				//option panel called
				stopWalking();
				if(selectedItem !=  null){
					if(item.canInteract(selectedItem)){
						item.interactWith(selectedItem);
						selectedItem = null;
						adventureFrame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
					}
					else {
						adventureFrame.notification("Das kann ich nicht machen.");
					}
				} else{
					this.adventureFrame.getOptionPanel().itemClicked(false, new Point(arg0.getPoint()), item);
				}
			} else if(npc != null){
				//npc clicked
				if(selectedItem != null && npc.canInteract(selectedItem)){
					npc.interactWith(selectedItem);
					selectedItem = null;
					adventureFrame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
					
					//TODO: Remove after presentation
					for(Item i : this.getPlayer().getInventory().getItems()){
						if(i.getName().equals("Zigaretten")){
							this.getAdventureFrame().getGoodbyePanel().popUp();
						}
					}
				}
				else
					this.adventureFrame.getDialogPanel().setDialog(npc.speak());
			} else {
				// nichts angeklickt, also laufen
				Point target = new Point(x, y);
				this.walkTo(target);
			}
			repaintPlayer();			

		}
		else{ //Abbruch
			selectedItem = null;
			getAdventureFrame().getInventoryPanel().unselectAll();
			//stop walking when right clicked
			if(arg0.getButton() == MouseEvent.BUTTON3)
				this.stopWalking();
			
			getAdventureFrame().setCursor(Cursor.getDefaultCursor());
		}
		getAdventureFrame().repaint();
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
	}

}
