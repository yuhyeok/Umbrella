package game.helpers.geometry;

public class Triangle {
	
	public Point p0;
	public Vector v1;
	public Vector v2;
	
	/**
	 * Constructs a triangle using a point and two vectors
	 * 
	 * @param p0 the point
	 * @param v1 the first vector
	 * @param v2 the second vector
	 */
	public Triangle(Point p0, Vector v1, Vector v2) {
		this.p0 = p0;
		this.v1 = v1;
		this.v2 = v2;
	}
	
	/**
	 * Copies contents of triangle
	 * 
	 * @param triangle the triangle to be copied
	 */
	public Triangle(Triangle triangle) {
		this.p0 = triangle.p0;
		this.v1 = triangle.v1;
		this.v2 = triangle.v2;
	}
}
