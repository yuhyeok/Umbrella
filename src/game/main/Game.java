package game.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

import game.input.KeyInput;
import game.structure.Handler;
import game.visual.BufferedImageLoader;
import game.visual.Textures;

public class Game extends Canvas implements Runnable{

	private static final long serialVersionUID = 1L;
	public static final int WIDTH = 1280;
	public static final int HEIGHT = 800;
	public static final int SCALE = 1;
	public final static String TITLE = "Umbrella";
	
	public static JFrame frame = new JFrame(TITLE);
	
	private static boolean running = false;
	private Thread thread;
	
	static Handler handler;
	Textures tex;

	public void init(){	
		BufferedImageLoader loader = new BufferedImageLoader();
		try{
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		setFocusable(true);
		
		tex = new Textures(this);
		
		handler = new Handler(tex, this);
		
		addKeyListener(new KeyInput(handler));
	}

	private synchronized void start(){
		if(running){
			return;
		}
		
		running = true;
		thread = new Thread(this);
		thread.start();
	}
	
	private synchronized void stop(){
		if(!running){
			return;
		}
		
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.exit(1);
	}
	
	public void run() {
		init();
		requestFocus();
		long lastTime = System.nanoTime();
		final double amountOfTicks = 30.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		int updates = 0;
		int frames = 0;
		long timer = System.currentTimeMillis();
		long now;
		
		while(running){
			now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			
			if(delta >= 1){
				tick();
				updates++;
				delta--;
			}
			render();
			frames++;
				
			if(System.currentTimeMillis() - timer > 1000){
				timer += 1000;
				System.out.println(updates + " Ticks, Fps " + frames);
				updates = 0;
				frames = 0;
			}
		}
		render();
		stop();
	}
	
	public void tick(){
		handler.tick();
	}
	
	public void render(){
		BufferStrategy bs = this.getBufferStrategy(); // this is Canvas since extends Canvas
		if(bs == null){
			createBufferStrategy(3); // number of buffers
			return;
		}

		Graphics2D g = (Graphics2D)bs.getDrawGraphics();
		
		RenderingHints rh = new RenderingHints(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g.setRenderingHints(rh);
		
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, WIDTH + 10, HEIGHT + 10);
		
		handler.render(g);
		
		g.dispose();
		bs.show();
	}
	
	public static void main(String args[]){		
		Game game = new Game();
		
		game.setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		game.setMaximumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		game.setMinimumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		
		// start maximized for now
		frame.add(game);
		frame.pack(); // 
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // exits when closed
		frame.setResizable(false); // disables resizing
		frame.setLocationRelativeTo(null); // sets relative location to null
//		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
//		frame.setUndecorated(true);
		frame.setVisible(true); // sets visible
		
		game.start();
	}
}
