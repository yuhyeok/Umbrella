package game.helpers.geometry;

public class Line {
	
	public Point p1;
	public Point p2;
	
	/**
	 * Constructs a Line using two points
	 * 
	 * @param p1 start point of the line
	 * @param p2 end point of the line
	 */
	public Line(Point p1, Point p2) {
		this.p1 = p1;
		this.p2 = p2;
	}
}
