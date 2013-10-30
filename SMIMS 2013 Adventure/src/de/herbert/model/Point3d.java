package de.herbert.model;

public class Point3d extends Point {
	
	private int z;

	public Point3d (int x, int y, int z) {
		super(x, y);
		this.z = z;
	}
	
	public Point3d (java.awt.Point point, int z) {
		super(point);
		this.z = z;
	}
	
	public Point3d (Point point, int z) {
		super(point.getX(), point.getY());
		this.z = z;
	}
	
	public int getZ() {
		return z;
	}
	
	public void setZ(int z) {
		this.z = z;
	}
	
	/**
	 * Add the coordinates of another 3d-point to this point.
	 * @param p
	 */
	public void add(Point3d p) {
		this.add(new Point(p.getX(), p.getY()));
		this.z += p.getZ();
	}
	
	/**
	 * Compares to other Point Object; the comparison to a Point which is not a Point3d doesn't use the z-coordinate.
	 */
	@Override
	public boolean compareTo(Point p) {
		if(p instanceof Point3d)
			return compareTo((Point3d)p);
		else
			return super.compareTo(p);
	}
	
	/**
	 * Compares to the given Point3d.
	 */
	public boolean compareTo(Point3d p) {
		return super.compareTo(new Point(p.getX(), p.getY())) && this.getZ() == p.getZ();
	}
	
	@Override
	public String toString() {
		return "(" + this.getX() + "/" + this.getY() + "/" + z +")";
	}
}
