package dev.moltencube.mygame;

import dev.moltencube.mygame.gfx.GameCamera;
import dev.moltencube.mygame.input.KeyManager;
import dev.moltencube.mygame.input.MouseManager;
import dev.moltencube.mygame.inventory.InventoryManager;
import dev.moltencube.mygame.worlds.World;

public class Handler {
	
	private Game game;
	private World world;
	private InventoryManager inventoryManager;
	
	public Handler(Game game) {
		this.game = game;
	}
	
	public GameCamera getGameCamera() {
		return game.getGameCamera();
	}
	
	public KeyManager getKeyManager() {
		return game.getKeyManager();
	}
	
	public MouseManager getMouseManager() {
		return game.getMouseManager();
	}
	
	public int getWidth() {
		return game.getWidth();
	}
	
	public int getHeight() {
		return game.getHeight();
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public World getWorld() {
		return world;
	}

	public void setWorld(World world) {
		this.world = world;
	}
	
	public InventoryManager getInventoryManager() {
		return inventoryManager;
	}
	
	public void setInventoryManager(InventoryManager manager) {
		this.inventoryManager = manager;
	}

}
