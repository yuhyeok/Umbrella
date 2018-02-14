package game.helpers.collision;

import java.awt.Color;
import java.awt.Graphics;

import game.helpers.geometry.Line;
import game.helpers.geometry.Matrix;
import game.helpers.geometry.Point;
import game.helpers.geometry.Triangle;
import game.helpers.geometry.Vector;

// Make sure that the GameObject that uses a Collider updates its refPoint
public class Collider {

	private int numVertices;
	private Point[] relVertices;
	private Point refPoint;
	private Color colour;
	
	/**
	 * Constructs a collider using relative vertices to a reference point and a colour 
	 * 
	 * @param numVertices the number of vertices
	 * @param relVertices list of relative vertices
	 * @param refPoint reference point
	 * @param colour colour to render collider
	 */
	public Collider(int numVertices, Point[] relVertices, Color colour) {
		this.numVertices = numVertices;
		this.relVertices = relVertices;
		this.colour = colour;
	}
	
	/**
	 * Copies contents of collider
	 * 
	 * @param collider the collider to be copied
	 */
	public Collider(Collider collider) {
		this.numVertices = collider.numVertices;
		this.relVertices = collider.relVertices;
		this.refPoint = collider.refPoint;
		this.colour = collider.colour;
	}
	
	public void render(Graphics g) {
		Line[] lines = getLines();
		g.setColor(getColour());
		for(int i = 0; i < lines.length; i++)
			g.drawLine((int)lines[i].p1.x, (int)lines[i].p1.y, (int)lines[i].p2.x, (int)lines[i].p2.y);
	}
	
	public int getNumVertices() {
		return numVertices;
	}

	public void setNumVertices(int numVertices) {
		this.numVertices = numVertices;
	}

	public Point[] getVertices() {
		Point[] absVertices = new Point[relVertices.length];
		for(int i = 0; i < numVertices; i++) {
			absVertices[i] = new Point(relVertices[i]);
			absVertices[i].x += refPoint.x;
			absVertices[i].y += refPoint.y;
		}
		return absVertices;
	}
	
	public Point[] getRelVertices() {
		return relVertices;
	}

	public void setRelVertices(Point[] relVertices) {
		this.relVertices = relVertices;
	}
	
	public Point getRefPoint() {
		return refPoint;
	}

	public void setRefPoint(Point refPoint) {
		this.refPoint = refPoint;
	}

	public Color getColour() {
		return colour;
	}

	public void setColour(Color colour) {
		this.colour = colour;
	}

	/**
	 * Check if two colliders are colliding
	 * 
	 * @param other the other collider
	 * @return boolean indicating whether colliding or not
	 */
	public boolean isColliding(Collider other) {
		// Check if colliders are close enough to check
		if(!isClose(other))
			return false;

		// Get the lines of the colliders
		Line[] these_lines = this.getLines();
		Line[] other_lines = other.getLines();
		
		// Check if the lines intersect
		for(int i = 0; i < these_lines.length; i++)
			for(int j = 0; j < other_lines.length; j++)
				if(isIntersecting(these_lines[i], other_lines[j]))
					return true;

		// Check if any points of one collider lie within the other, but check the less complex one first
		Collider collider_less;
		Collider collider_more;
		
		// Check which collider has less vertices
		if(this.getNumVertices() <= other.getNumVertices()) {
			collider_less = new Collider(this);
			collider_more = new Collider(other);
		} else {
			collider_less = new Collider(other);
			collider_more = new Collider(this);
		}
		
		// Check if there is point within for poly_less
		Point[] poly_more_vertices = collider_more.getVertices();
		for(int i = 0; i < collider_more.getNumVertices(); i++)
			if(isPointInPolygon(poly_more_vertices[i], collider_less))
				return true;
		
		// Check if there is point within for poly_more
		Point[] poly_less_vertices = collider_less.getVertices();
		for(int i = 0; i < collider_less.getNumVertices(); i++)
			if(isPointInPolygon(poly_less_vertices[i], collider_more))
				return true;
		
		return false;
	}
	
	/**
	 * Make list of lines using vertices
	 * 
	 * @return list of lines
	 */
	public Line[] getLines() {
		Line[] lines = new Line[numVertices];
		Point[] absVertices = getVertices();
		for(int i = 0; i < numVertices; i++)
			lines[i] = new Line(absVertices[i], absVertices[(i + 1) % numVertices]);
		return lines;
	}
	
	/**
	 * Make list of triangles using vertices
	 * 
	 * @return list of triangles
	 */
	public Triangle[] getTriangles() {
		Point[] absVertices = getVertices();
		int numTriangles = numVertices - 2;
		Triangle[] triangles = new Triangle[numTriangles];
		
		for(int i = 0; i < numTriangles; i++) 
			triangles[i] = new Triangle(absVertices[0], new Vector(absVertices[0], absVertices[i + 1]), new Vector(absVertices[0], absVertices[i + 2]));
		
		return triangles;
	}
	
	/**
	 * Check if colliders are close enough to do collision check
	 * 
	 * @param other the other collider
	 * @return whether is close
	 */
	private boolean isClose(Collider other) {
		// Calculate centroids
		Point this_centroid = new Point(0, 0);
		Point other_centroid = new Point(0, 0);
		
		for(int i = 0; i < this.getNumVertices(); i++) {
			this_centroid.x += this.getVertices()[i].x;
			this_centroid.y += this.getVertices()[i].y;
		}
		
		for(int i = 0; i < this.getNumVertices(); i++) {
			other_centroid.x += this.getVertices()[i].x;
			other_centroid.y += this.getVertices()[i].y;
		}
		
		this_centroid.x /= this.getNumVertices();
		this_centroid.y /= this.getNumVertices();
		other_centroid.x /= this.getNumVertices();
		other_centroid.y /= this.getNumVertices();
		
		// Calculate farthest distance to vertex from centroid
		double r1 = 0;
		double r2 = 0;
		
		for(int i = 0; i < this.getNumVertices(); i++) {
			double distance = dist(this_centroid, this.getVertices()[i]);
			if(distance > r1)
				r1 = distance;
		}
		
		for(int i = 0; i < this.getNumVertices(); i++) {
			double distance = dist(other_centroid, this.getVertices()[i]);
			if(distance > r2)
				r2 = distance;
		}
		
		return dist(this_centroid, other_centroid) < r1 + r2;
	}
	
	/**
	 * Check if two lines are intersecting
	 * 
	 * @param l1 first line
	 * @param l2 second line
	 * @return whether lines are intersecting
	 */
	private boolean isIntersecting(Line l1, Line l2) {
		// Make vectors from the given lines
		Vector l1_vector = new Vector(l1.p1, l1.p2);
		Vector l2_vector = new Vector(l2.p1, l2.p2);
		
		// Make vectors from chosen origin to point of opposite line
		Vector l1_to_p1_vector = new Vector(l1.p1, l2.p1);
		Vector l1_to_p2_vector = new Vector(l1.p1, l2.p2);
		Vector l2_to_p1_vector = new Vector(l2.p1, l1.p1);
		Vector l2_to_p2_vector = new Vector(l2.p1, l1.p2);
		
		// Calculate cross product between line 1 and line 2's endpoints
		double cross1 = l1_vector.crossZ(l1_to_p1_vector);
		double cross2 = l1_vector.crossZ(l1_to_p2_vector);
		
		// Find intersecting cases when at least one of the four cross products is 0
		if(cross1 == 0) {
			if(cross2 == 0) { // lines are collinear 
				if(isPointWithinLineSegment(l1.p1, l2) || isPointWithinLineSegment(l1.p2, l2) || isPointWithinLineSegment(l2.p1, l1) || isPointWithinLineSegment(l2.p2, l1))
					return true;
			} else if(isPointWithinLineSegment(l2.p1, l1)) // case where line 2's point 1 is collinear with line 1
					return true;
		} else {			
			if(cross2 == 0 && isPointWithinLineSegment(l2.p2, l1)) // case where line 2's point 2 is collinear with line 1
				return true;
		}
		
		// Putting these down here should save some computation time
		double cross3 = l2_vector.crossZ(l2_to_p1_vector);
		double cross4 = l2_vector.crossZ(l2_to_p2_vector);
		
		if(cross3 == 0 && cross4 != 0 && isPointWithinLineSegment(l1.p1, l2)) // case where line 1's point 1 is collinear with line 2
			return true;
		
		if(cross3 != 0 && cross4 == 0 && isPointWithinLineSegment(l1.p2, l2)) // case where line 1's point 2 is collinear with line 2  
			return true;
		
		// if each pair of cross products are opposite signs, intersection
		return !isSameSign(cross1, cross2) && !isSameSign(cross3, cross4);
	}
	
	/**
	 * Check if a point is inside a collider
	 * 
	 * @param p the point
	 * @param poly the collider
	 * @return whether point is inside collider
	 */
	private boolean isPointInPolygon(Point p, Collider poly) {
		int count = 0;
		Triangle[] triangles = poly.getTriangles();
		
		for(int i = 0; i < triangles.length; i++)
			if(isInTriangle(p, triangles[i]))
				count++;
		
		// point is in polygon if it's inside an odd number of triangles
		return count % 2 != 0;
	}
	
	/**
	 * Check if point is in triangle
	 * 
	 * @param p the point
	 * @param triangle the triangle
	 * @return whether point is in triangle
	 */
	private boolean isInTriangle(Point p, Triangle triangle) {
		// if triangle has 0 area, not possible to be in triangle
		if(triangle.v1.crossZ(triangle.v2) == 0)
			return false;
		Vector v3 = new Vector(triangle.p0, p);
		double[][] array = {{triangle.v1.x, triangle.v2.x}, {triangle.v1.y, triangle.v2.y}};
		Matrix matrix = new Matrix(array);
		double[][] rhs = {{v3.x}, {v3.y}};
		Matrix solution = matrix.solve(new Matrix(rhs));
		double[][] solutionData = solution.getData();
		double d = solutionData[0][0];
		double c = solutionData[1][0];
		return c >= 0 && c <= 1 && d >= 0 && d <= 1 && c + d <= 1;
	}
	
	/**
	 * Check if point is within line segment
	 * 
	 * @param p the point
	 * @param l the line
	 * @return whether point is within line segment
	 */
	private boolean isPointWithinLineSegment(Point p, Line l) {
		return ((l.p1.x <= p.x && p.x <= l.p2.x) || (l.p2.x <= p.x && p.x <= l.p1.x)) &&
			   ((l.p1.y <= p.y && p.y <= l.p2.y) || (l.p2.y <= p.y && p.y <= l.p1.y));
	}
	
	/**
	 * Check if two numbers have the same sign
	 * 
	 * @param num1 first number
	 * @param num2 second number
	 * @return whether numbers have same sign
	 */
	private boolean isSameSign(double num1, double num2) {
		return num1 * num2 >= 0;
	}
	
	/**
	 * Calculate the distance between two points
	 * 
	 * @param p1 first point
	 * @param p2 second point
	 * @return distance between points
	 */
	private double dist(Point p1, Point p2) {
		return Math.sqrt(Math.pow(p2.x - p1.x, 2) + Math.pow(p2.y - p1.y, 2));
	}
}
