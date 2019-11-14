package dev.moltencube.mygame;

import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferStrategy;

import dev.moltencube.mygame.display.Display;
import dev.moltencube.mygame.gfx.Assets;
import dev.moltencube.mygame.gfx.GameCamera;
import dev.moltencube.mygame.input.KeyManager;
import dev.moltencube.mygame.input.MouseManager;
import dev.moltencube.mygame.states.GameState;
import dev.moltencube.mygame.states.MenuState;
import dev.moltencube.mygame.states.SettingsState;
import dev.moltencube.mygame.states.State;

public class Game implements Runnable {
	
	private Display display;	
	private int width, height, initWidth, initHeight;
	private boolean fullscreen = false, borderless = false, windowed = false;
	public String title;
	
	private boolean running = false;
	private Thread thread;
	
	private BufferStrategy bs;
	private Graphics g;
	
	// states
	public State gameState;
	public State menuState;
	private State settingsState;
	
	// input
	private KeyManager keyManager;
	private MouseManager mouseManager;
	
	// camera
	private GameCamera gameCamera;
	
	// handler
	private Handler handler;
	
	public Game(String title, int width, int height) {
		this.width = width;
		this.height = height;
		this.initWidth = width;
		this.initHeight = height;
		this.title = title;
		this.windowed = true;
		
		keyManager = new KeyManager();
		mouseManager = new MouseManager();
	}
	
	private void init() {
		display = new Display(title, width, height);
		display.getFrame().addKeyListener(keyManager);
		display.getFrame().addMouseListener(mouseManager);
		display.getFrame().addMouseMotionListener(mouseManager);
		display.getFrame().addMouseWheelListener(mouseManager);
		display.getCanvas().addMouseListener(mouseManager);
		display.getCanvas().addMouseMotionListener(mouseManager);
		Assets.init();
		
		handler = new Handler(this);
		gameCamera = new GameCamera(handler, 0, 0);
		
		gameState = new GameState(handler);
		menuState = new MenuState(handler);
		settingsState = new SettingsState(handler);
		
		State.setState(menuState);
		mouseManager.setUIManager(State.getState().getUIManager());
		mouseManager.setInventoryManager(handler.getWorld().getInventoryManager());
		mouseManager.setCrafting(handler.getWorld().getEntityManager().getPlayer().getCrafting());
	}
	
	private void tick() {
		keyManager.tick();
		
		if(State.getState() != null) {
			State.getState().tick();
		}
	}
	
	private void render() {
		bs = display.getCanvas().getBufferStrategy();
		if(bs == null) {
			display.getCanvas().createBufferStrategy(3);
			return;
		}
		
		g = bs.getDrawGraphics();
		
		// clear screen
		g.clearRect(0, 0, display.getFrame().getWidth(), display.getFrame().getHeight());
		
		// start drawing
		
		if(State.getState() != null) {
			State.getState().render(g);
		}
		
		// end drawing
		
		bs.show();
		g.dispose();
	}
	
	public void run() {
		
		init();
		
		int fps = 60;
		double timePerTick = 1000000000 / fps;
		double delta = 0;
		long now;
		long lastTime = System.nanoTime();
		long timer = 0;
		int ticks = 0;
		
		
		while(running) {
			now = System.nanoTime();
			delta += (now - lastTime) / timePerTick;
			timer += now - lastTime;
			lastTime = now;
			
			if(delta >= 1) {
				tick();
				render();
				ticks++;
				delta--;
			}
			
			if(timer >= 1000000000) {
				//System.out.println("Ticks and Frames: " + ticks);
				ticks = 0;
				timer = 0;
			}
		}
		
		stop();
	}

	public synchronized void start() {
		if (running) {
			return;
		}
		
		running = true;
		thread = new Thread(this);
		thread.start();
	}
	
	public synchronized void stop() {
		if (!running) {
			return;
		}
		
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void setScreen(String state) {
		fullscreen = false;
		borderless = false;
		windowed = false;
		if(state == "full") {
			fullscreen = true;
			display.changeScreen("full", GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice());
			if(State.getState() == gameState)
				handler.getWorld().getEntityManager().getPlayer().getCrafting().updatePosition();
		}else if(state == "borderless") {
			borderless = true;
			display.changeScreen("borderless", GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice());
			width = display.getFrame().getWidth();
			height = display.getFrame().getHeight();
			if(State.getState() == gameState)
				handler.getWorld().getEntityManager().getPlayer().getCrafting().updatePosition();
		}else {
			windowed = true;
			display.changeScreen("windowed", GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice());
			width = initWidth;
			height = initHeight;
			if(State.getState() == gameState)
				handler.getWorld().getEntityManager().getPlayer().getCrafting().updatePosition();
		}
	}
	
	public boolean isFullscreen() {
		return fullscreen;
	}
	
	public boolean isBorderless() {
		return borderless;
	}
	
	public boolean isWindowed() {
		return windowed;
	}
	
	public KeyManager getKeyManager() {
		return keyManager;
	}
	
	public MouseManager getMouseManager() {
		return mouseManager;
	}
	
	public GameCamera getGameCamera() {
		return gameCamera;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public State getGameState() {
		return gameState;
	}
}
