package game.helpers.geometry;

public class Point {
	
	public double x;
	public double y;
	
	/**
	 * Constructs a Point using two doubles
	 * 
	 * @param x x-coordinate of point
	 * @param y y-coordinate of point
	 */
	public Point(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Copies contents of point
	 * 
	 * @param point point to be copied 
	 */
	public Point(Point point) {
		this.x = point.x;
		this.y = point.y;
	}
}
