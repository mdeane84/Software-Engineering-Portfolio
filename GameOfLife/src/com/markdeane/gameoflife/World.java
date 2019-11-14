package com.markdeane.gameoflife;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;
import java.util.Stack;

public class World {
	
	private final int size = 5;
	private int width, height;
	private int screenWidth, screenHeight;
	private boolean[][] tiles;
	
	private Stack<int[][]> history;
	
	public World(int width, int height) {
		this.screenWidth = width;
		this.screenHeight = height;
		this.width = width / size;
		this.height = height / size;
		
		tiles = new boolean[this.width][this.height];
		
		for(int i = 0; i < this.width; i++) {
			for(int j = 0; j < this.height; j++) {
				tiles[i][j] = false;
			}
		}
	}
	
	public void render(Graphics g) {
		g.setColor(Color.yellow);
		for(int i = 0; i < screenWidth; i += size) {
			for(int j = 0; j < screenHeight; j += size) {
				if(tiles[i/size][j/size]) {
					g.fillRect(i, j, size, size);
				}
			}
		}
	}
	
	public void randomize(int percent) {
		Random random = new Random();
		
		for(int i = 0; i < width; i++) {
			for(int j = 0; j < height; j++) {
				if(random.nextInt(100) < percent) {
					tiles[i][j] = true;
				} else {
					tiles[i][j] = false;
				}
			}
		}
			
	}
	
	public void step() {
		int count = 0;
		boolean[][] nextGen = new boolean[width][height];
		
		for(int i = 0; i < width; i++) {
			for(int j = 0; j < height; j++) {
				nextGen[i][j] = false;
			}
		}
		
		for(int i = 0; i < width; i++) {
			for(int j = 0; j < height; j++) {
				count = getAdjacent(i, j);
				
				if(tiles[i][j]) {	// if tile is alive
					if(count < 2) {
						nextGen[i][j] = false;
					} else if(count > 3){
						nextGen[i][j] = false;
					} else {
						nextGen[i][j] = true;
					}
				} else {			// if tiles is dead
					if(count == 3)
						nextGen[i][j] = true;
				}
			}
		}
		
		for(int i = 0; i < width; i++) {
			for(int j = 0; j < height; j++) {
				tiles[i][j] = nextGen[i][j];
			}
		}
	}
	
	public int getAdjacent(int i, int j) {
		int count = 0;
		
		for(int di = -1; di <= 1; di++) {
			for(int dj = -1; dj <= 1; dj++) {			
				try {
					if(tiles[i + di][j + dj]) {
						count++;
					}
				} catch(Exception e) {
				}
			}
		}
		
		if(tiles[i][j]) {
			count--;
		}
		
		return count;
	}
}
