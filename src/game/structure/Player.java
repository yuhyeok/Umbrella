package game.structure;

import java.awt.Graphics;
import java.util.LinkedList;

import game.helpers.geometry.Point;
import game.helpers.geometry.Vector;

public class Player extends GameObject{

	public Player(Vector s, Vector v, Vector a, ObjectId id) {
		super(s, v, a, id);
	}

	@Override
	public void tick(LinkedList<GameObject> object) {
		s = s.add(v);
		v = v.add(a);
		collider.setRefPoint(new Point(s.x, s.y));
	}

	@Override
	public void render(Graphics g) {
		if(collider != null)
			collider.render(g);
	}
	
}
