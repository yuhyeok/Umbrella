package game.visual;

import java.awt.image.BufferedImage;

public class SpriteSheet {
	private BufferedImage image;
	private int width;
	private int height;
	
	public SpriteSheet(BufferedImage image, int width, int height){
		this.image = image;
		this.width = width;
		this.height = height;
	}
	
	// get image in sprite sheet
	public BufferedImage grabImage(int col, int row, int width, int height){
		return image.getSubimage((col * this.width) - this.width, (row * this.height) - this.height, width, height);
	}
}
