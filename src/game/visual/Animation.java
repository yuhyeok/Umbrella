package game.visual;

import java.awt.image.BufferedImage;

public class Animation {

	private BufferedImage[] sprite;
	private int totalAnimTime;
	
	private long timeStarted = -1;
	
	public Animation(BufferedImage[] sprite, int totalAnimTime){
		this.sprite = sprite;
		this.totalAnimTime = totalAnimTime;
	}
	
	// return sprite if started
	public BufferedImage getSprite(){
		if(timeStarted != -1)
			return sprite[(int)(((System.currentTimeMillis() - timeStarted) % totalAnimTime) / (totalAnimTime / (sprite.length - 1)))];
		else
			return null;
	}
	
	// Set beginning of animation time stamp
	public void start(){
		timeStarted = System.currentTimeMillis();
	}
	
	public void stop(){
		timeStarted = -1;
	}
}
