package game.helpers.geometry;

public class Vector {
	public double x;
	public double y;
	
	public Vector(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public Vector(Point s, Point e) {
		this.x = e.x - s.x;
		this.y = e.y - s.y;
	}
	
	public double crossZ(Vector v) {
		return this.x * v.y - v.x * this.y;
	}
}
