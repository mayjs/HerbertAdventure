package de.herbert.model;

import java.io.Serializable;

/**
 * Point in a 2-dimensional coordinate field (on the tile map).
 */
public class Point implements Serializable{

	private static final long serialVersionUID = 1L;
	private int x, y;

	//Constructors
	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Create Herbert Point from awt Point.
	 * @param pt
	 */
	public Point (java.awt.Point pt) {
		this.x = (int)pt.getX();
		this.y = (int)pt.getY();
	}
	
	//Getter and Setter
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	/**
	 * Compares Point instance to other Point instance.
	 * @param p	other point
	 * @return True if both are equal.
	 */
	public boolean compareTo(Point p) {
		return x == p.getX() && y == p.getY();
	}
	
	/**
	 * add another point to this one.
	 * @param p
	 */
	public void add(Point p) {
		this.x += p.getX();
		this.y += p.getY();
	}
	
	@Override
	public String toString()
	{
		return "("+x+"/"+y+")";
	}
}
