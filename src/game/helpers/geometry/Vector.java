package game.helpers.geometry;

public class Vector {
	
	public double x;
	public double y;
	
	/**
	 * Constructs a vector using two doubles
	 * 
	 * @param x magnitude of vector in x-axis
	 * @param y magnitude of vector in y-axis
	 */
	public Vector(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Constructs a vector using two points
	 * 
	 * @param s start point
	 * @param e end point
	 */
	public Vector(Point s, Point e) {
		this.x = e.x - s.x;
		this.y = e.y - s.y;
	}
	
	/**
	 * Copies contents of vector
	 * 
	 * @param vector vector to be copied
	 */
	public Vector(Vector vector) {
		this.x = vector.x;
		this.y = vector.y;
	}
	
	/**
	 * Calculates the cross product between this and another vector
	 * 
	 * @param other the other vector
	 * @return z component
	 */
	public double crossZ(Vector other) {
		return this.x * other.y - other.x * this.y;
	}
	
	/**
	 * Calculates the magnitude of the vector
	 * 
	 * @return magnitude of vector
	 */
	public double magnitude() {
		return Math.sqrt(this.x * this.x + this.y * this.y);
	}
	
	/**
	 * Adds this and another vector together
	 * 
	 * @param other the other vector
	 * @return the sum of the two vectors
	 */
	public Vector add(Vector other) {
		return new Vector(this.x + other.x, this.y + other.y);
	}
	
	/**
	 * Subtracts this with another vector
	 * 
	 * @param other the other vector
	 * @return the difference of the two vectors
	 */
	public Vector subtract(Vector other) {
		return new Vector(this.x - other.x, this.y - other.y);
	}
}
