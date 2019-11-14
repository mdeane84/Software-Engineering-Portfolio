package dev.moltencube.mygame.tiles;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import dev.moltencube.mygame.gfx.Assets;

public class Tile {
	
	// STATIC STUFF HERE
	
	public static Tile[] tiles = new Tile[256];
	public static Tile grassTile = new GrassTile(0);
	public static Tile dirtTile = new DirtTile(1);
	public static Tile rockTile = new RockTile(2);
	
	public static Tile grassTile1 = new Tile(Assets.grass1, 3);
	public static Tile grassTile2 = new Tile(Assets.grass2, 4);
	public static Tile grassTile3 = new Tile(Assets.grass3, 5);
	public static Tile grassTile4 = new Tile(Assets.grass4, 6);
	
	// CLASS
	
	private static final int TILEWIDTH = 128, TILEHEIGHT = 128;
	
	protected BufferedImage texture;
	protected final int id;
	
	public Tile(BufferedImage texture, int id) {
		this.texture = texture;
		this.id = id;
		
		tiles[id] = this;
	}
	
	public void tick() {
		
	}
	
	public void render(Graphics g, int x, int y) {
		g.drawImage(texture, x, y, TILEWIDTH, TILEHEIGHT, null);
	}
	
	public boolean isSolid() {
		return false;
	}
	
	public int getId() {
		return id;
	}

	public static int getTileWidth() {
		return TILEWIDTH;
	}

	public static int getTileHeight() {
		return TILEHEIGHT;
	}
}
