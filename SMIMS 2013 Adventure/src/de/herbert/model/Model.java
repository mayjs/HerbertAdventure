package de.herbert.model;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;


public class Model {
	
	private Player player;
	
	public Model(){

	}
	
	public void setPlayer(Player p){
		player = p;
	}
	
	public Player getPlayer(){
		return player;
	}
}
