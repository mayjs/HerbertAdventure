package de.herbert.view;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

import de.herbert.control.Control;
import de.herbert.model.Item;
import de.herbert.model.Player;
import de.herbert.model.Point;
import de.herbert.model.Scene;

public class SceneComponent extends Component{
	
	//ViewPos = scaleFactor * ModelPos
	private float scaleFactorX, scaleFactorY;
	
	public SceneComponent(Rectangle boundings) {
		super(boundings);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void update(GameContainer container, int delta)
			throws SlickException {
		scaleFactorX = boundings.getWidth()/Control.getInstance().getModel().getPlayer().getScene().getWidth();
		scaleFactorY = boundings.getHeight()/Control.getInstance().getModel().getPlayer().getScene().getHeight();
	}

	@Override
	public void render(GameContainer container, Graphics g)
			throws SlickException {
		Item[][][] items = Control.getInstance().getSceneItems();
		renderSceneBackground(container, g, Control.getInstance().getModel().getPlayer().getScene());
		
	}
	
	private void renderSceneBackground(GameContainer container, Graphics g, Scene scene){
		Image img = ImageLoader.getImage(scene.getName());
		g.drawImage(img,
				boundings.getX(),boundings.getY(),boundings.getMaxX(),boundings.getMaxY(),
				0,0,img.getWidth(),img.getHeight());
	}
	
	private void renderPlayerAndItemsCorrectly(Graphics g, Player p, Item[][][] items, Scene scene){
		for(int z = 0; z < items[0][0].length; z++){
			for(int y = 0; y < items[0].length; y++){
				for(int x = 0; x < items.length; x++){
					Item i = scene.getItemAt(x, y, z);
					if(i!=null && isBottomRight(i, x, y, scene)){
						Point tl = scene.getItemPosition(i);
						renderItem(g, i, tl.getX(), tl.getY());
					}
				}
				if(y == p.getPosition().getY()){
					renderPlayer(g, p);
				}
			}
		}
	}
	
	private boolean isBottomRight(Item i, int x, int y, Scene scene){
		return scene.getItemAt(new Point(x+1, y)) != i && scene.getItemAt(new Point(x,y+1)) != i;
	}
	
	private void renderPlayer(Graphics g, Player p){
		Image img = ImageLoader.getImage("player");
		float x = convertModelXtoViewX(p.getPosition().getX());
		float y = convertModelYtoViewY(p.getPosition().getY()) - img.getHeight();
		g.drawImage(img, x, y);
	}
	
	private void renderItem(Graphics g, Item i, int x, int y){
		Image img = ImageLoader.getImage(i.getName());
		float rx = convertModelXtoViewX(x);
		float ry = convertModelYtoViewY(y);
		g.drawImage(img, rx, ry);
	}
	
	public float convertModelXtoViewX(int modelX){
		return scaleFactorX * modelX;
	}
	public float convertModelYtoViewY(int modelY){
		return scaleFactorY * modelY;
	}
	public int convertViewXtoModelX(float viewX){
		return (int)(viewX/scaleFactorX);
	}
	public int convertViewYtoModelY(float viewY){
		return (int)(viewY/scaleFactorY);
	}
}
