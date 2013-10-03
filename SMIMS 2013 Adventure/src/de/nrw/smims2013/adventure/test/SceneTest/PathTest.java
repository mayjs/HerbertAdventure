package de.nrw.smims2013.adventure.test.SceneTest;

import java.util.List;

import de.nrw.smims2013.adventure.model.Point;
import de.nrw.smims2013.adventure.model.Scene;

public class PathTest {
	private Scene scene;
	
	/**
	 * Prints field
	 */
	public PathTest() {
		this.scene = new Scene();
		List<Point> res = scene.findPath(new Point(5, 10), new Point(20, 15));
		System.out.println(res==null);
		printField(res);
	}
	
	public static void main(String[] args) {
		PathTest myTest = new PathTest();
	}
	
	/**
	 * prints field and way
	 * @param way
	 */
	void printField(List<Point> way) {
		System.out.println("===MAP===");
		boolean[][] map = scene.getTileMap();
		
		char[][] out = new char[25][25];
		for(int x=0; x<out.length; x++) {
			for(int y=0; y<out[x].length;y++)
				out[x][y] = map[x][y] ? '#' : 'O';
		}
		
		for(Point p: way) {
			out[p.getX()][p.getY()] = ' ';
		}
		
		out[20][15] = 'E';
		out[5][10] = 'S';
		
		for(char[] line : out) {
			for(char b: line) {
				System.out.print(b);
			}
			System.out.println();
		}
	}
}
