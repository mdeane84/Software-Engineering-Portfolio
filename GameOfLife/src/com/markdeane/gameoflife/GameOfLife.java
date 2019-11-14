package com.markdeane.gameoflife;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.util.Stack;

public class GameOfLife implements Runnable, KeyListener {

	private Display display;
	private World world;
	private int width, height;
	private int steps = 0;
	public String title;
	
	private boolean running = false;
	private boolean play = false;
	private Thread thread;
	
	private BufferStrategy bs;
	private Graphics g;
	
	public GameOfLife(String title, int width, int height) {
		this.width = width;
		this.height = height;
		this.title = title;
	}
	
	private void init() {
		display = new Display(title, width, height);
		display.getCanvas().addKeyListener(this);
		display.getFrame().addKeyListener(this);
		
		world = new World(width, height);
		world.randomize(25);
		
	}
	
	private void tick() {
		if(play) {
			world.step();
			System.out.println("steps: " + ++steps);
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
		
		// render world
		world.render(g);
				
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
		
		
		while(running) {
			now = System.nanoTime();
			delta += (now - lastTime) / timePerTick;
			timer += now - lastTime;
			lastTime = now;
			
			if(delta >= 1) {
				tick();
				render();
				delta--;
			}
			
			if(timer >= 1000000000) {
				//System.out.println("Ticks and Frames: " + ticks);
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

	@Override
	public void keyPressed(KeyEvent e) {
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			play = !play;
		} else if(e.getKeyCode() == KeyEvent.VK_R && !play) {
			world.randomize(50);
		} else if(e.getKeyCode() == KeyEvent.VK_RIGHT && !play) {
			world.step();
			System.out.println("steps: " + ++steps);
		}
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}
}
