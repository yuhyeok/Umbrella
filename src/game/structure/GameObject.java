package game.structure;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.LinkedList;

import game.helpers.geometry.Vector;

public abstract class GameObject {
	
	protected Vector s;
	protected Vector v;
	protected Vector a;
	
	protected ObjectId id;
	
	public GameObject(Vector s, Vector v, Vector a, ObjectId id){
		this.s = s;
		this.v = v;
		this.a = a;
		this.id = id;
	}

	public abstract void tick(LinkedList<GameObject> object);
	public abstract void render(Graphics g);
	
	public abstract Rectangle getBounds();
	
	public Vector getS() {
		return s;
	}

	public void setS(Vector s) {
		this.s = s;
	}

	public Vector getV() {
		return v;
	}

	public void setV(Vector v) {
		this.v = v;
	}

	public Vector getA() {
		return a;
	}

	public void setA(Vector a) {
		this.a = a;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public ObjectId getId(){
		return id;
	}
}
