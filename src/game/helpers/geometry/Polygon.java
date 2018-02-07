package game.helpers.geometry;

import java.awt.Color;

public class Polygon {
	private String polygonName;
	private int numVertices;
	private Point[] vertices;
	private Color colour;
	
	public Polygon(String polygonName, int numVertices, Point[] vertices, Color colour) {
		this.polygonName = polygonName;
		this.numVertices = numVertices;
		this.vertices = vertices;
		this.colour = colour;
	}
	
	public void printInfo() {
		System.out.println("Polygon: " + polygonName);
		System.out.println("Number of Vertices: " + numVertices);
		for(int i = 0; i < numVertices; i++)
			System.out.println(vertices[i].x + ", " + vertices[i].y);
	}

	public String getPolygonName() {
		return polygonName;
	}

	public void setPolygonName(String polygonName) {
		this.polygonName = polygonName;
	}

	public int getNumVertices() {
		return numVertices;
	}

	public void setNumVertices(int numVertices) {
		this.numVertices = numVertices;
	}

	public Point[] getVertices() {
		return vertices;
	}

	public void setVertices(Point[] vertices) {
		this.vertices = vertices;
	}
	
	public Color getColour() {
		return colour;
	}

	public void setColour(Color colour) {
		this.colour = colour;
	}

	public Line[] getLines() {
		Line[] lines = new Line[numVertices];
		for(int i = 0; i < numVertices; i++)
			lines[i] = new Line(vertices[i], vertices[(i + 1) % numVertices]);
		return lines;
	}
}
