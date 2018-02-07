package game.structure;

import java.awt.Graphics;
import java.util.LinkedList;

import game.main.Game;
import game.visual.Textures;

public class Handler {

	public LinkedList<GameObject> object = new LinkedList<GameObject>();
	
	private GameObject tempObject;
	private Textures tex;
	Game game;
	
	public Handler(Textures tex, Game game){
		this.tex = tex;
		this.game = game;
	}
	
	public void tick(){
		for(int i = 0; i < object.size(); i++){
			tempObject = object.get(i);
			tempObject.tick(object);
		}
	}
	
	public void render(Graphics g){	
		for(int i = 0; i < object.size(); i++){
			tempObject = object.get(i);
			tempObject.render(g);
		}
	}
	
	public void addObject(GameObject object){
		this.object.add(object);
	}
	
	public void removeObject(GameObject object){
		this.object.remove(object);
	}
	
	public void clearLevel(){
		object.clear();
	}
}
