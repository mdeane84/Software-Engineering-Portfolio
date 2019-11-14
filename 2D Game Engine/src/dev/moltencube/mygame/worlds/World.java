package dev.moltencube.mygame.worlds;

import java.awt.Graphics;
import java.util.Random;

import dev.moltencube.mygame.Handler;
import dev.moltencube.mygame.crafting.RecipeManager;
import dev.moltencube.mygame.entities.EntityManager;
import dev.moltencube.mygame.entities.creatures.Frizard;
import dev.moltencube.mygame.entities.creatures.Player;
import dev.moltencube.mygame.entities.statics.Boulder;
import dev.moltencube.mygame.entities.statics.Plant;
import dev.moltencube.mygame.entities.statics.Tree;
import dev.moltencube.mygame.gfx.Assets;
import dev.moltencube.mygame.inventory.Inventory;
import dev.moltencube.mygame.inventory.InventoryBulbPouch;
import dev.moltencube.mygame.inventory.InventoryManager;
import dev.moltencube.mygame.items.Item;
import dev.moltencube.mygame.items.ItemManager;
import dev.moltencube.mygame.tiles.Tile;
import dev.moltencube.mygame.utils.Utils;

public class World {
	
	private Handler handler;
	private int width, height;
	private int spawnX, spawnY;
	private int[][] tiles;
	
	Random random = new Random();
	
	// entities
	private EntityManager entityManager;
	// items
	private ItemManager itemManager;
	// recipes
	private RecipeManager recipeManager;
	// inventories
	private InventoryManager inventoryManager;
	
	public World(Handler handler, String path) {
		this.handler = handler;
		
		inventoryManager = new InventoryManager(handler);
		handler.setInventoryManager(inventoryManager);
		entityManager = new EntityManager(handler, new Player(handler, 100, 100));
		itemManager = new ItemManager(handler);
		recipeManager = new RecipeManager(handler);
		
		// temporary add entities and items
		for(int i = 0; i < 12; i++) {
			entityManager.addEntity(new Tree(handler, Assets.tree1, new Item[] {Item.woodItem.createNew(random.nextInt(3)), Item.treeBulbItem.createNew(random.nextInt(2))}, random.nextInt(1800) + 50, random.nextInt(1000) + 50));
			entityManager.addEntity(new Tree(handler, Assets.tree2, null, random.nextInt(1800) + 50, random.nextInt(1000) + 50));
			entityManager.addEntity(new Tree(handler, Assets.tree3, null, random.nextInt(1800) + 50, random.nextInt(1000) + 50));
			entityManager.addEntity(new Plant(handler, Assets.plant1, random.nextInt(1400) + 50, random.nextInt(800) + 50));
			entityManager.addEntity(new Plant(handler, Assets.plant2, random.nextInt(1400) + 50, random.nextInt(800) + 50));
			entityManager.addEntity(new Plant(handler, Assets.plant3, random.nextInt(1400) + 50, random.nextInt(800) + 50));
			entityManager.addEntity(new Boulder(handler, Assets.boulder1, random.nextInt(1400) + 50, random.nextInt(800) + 50));
			entityManager.addEntity(new Boulder(handler, Assets.boulder2, random.nextInt(1400) + 50, random.nextInt(800) + 50));
			entityManager.addEntity(new Boulder(handler, Assets.boulder3, random.nextInt(1400) + 50, random.nextInt(800) + 50));
			itemManager.addItem(Item.stickItem.createNew(random.nextInt(1200), random.nextInt(800), random.nextInt(8) + 2));
		}
		itemManager.addItem(Item.boneItem.createNew(50, 100, 20));
		itemManager.addItem(Item.bulbPouch.createNewWithInventory(150, 50, new InventoryBulbPouch(handler)));
		itemManager.addItem(Item.bulbPouch.createNewWithInventory(150, 0, new InventoryBulbPouch(handler)));
		itemManager.addItem(Item.treeBulbItem.createNew(65, 65, 8));
		entityManager.addEntity(new Frizard(handler,  50, 200));
		
		
		itemManager.addItem(Item.stickItem.createNew(32, 32, 25));
		itemManager.addItem(Item.stickItem.createNew(64, 32, 30));
		itemManager.addItem(Item.plantFiberItem.createNew(40, 31, 12));
		
		loadWorld(path);
		
		entityManager.getPlayer().setX(spawnX);
		entityManager.getPlayer().setY(spawnY);
	}

	public void tick() {
		itemManager.tick();
		entityManager.tick();
	}
	
	public void render(Graphics g) {
		int xShift = 0;
		int yShift = 0;
		
		int xStart = (int) Math.max(0, handler.getGameCamera().getxOffset() / Tile.getTileWidth() + xShift);
		int xEnd = (int) Math.min(width, (handler.getGameCamera().getxOffset() + handler.getWidth()) / (Tile.getTileWidth() + xShift) + 1);
		int yStart = (int) Math.max(0, handler.getGameCamera().getyOffset() / Tile.getTileHeight() + yShift);
		int yEnd = (int) Math.min(height, (handler.getGameCamera().getyOffset() + handler.getHeight()) / (Tile.getTileHeight() + yShift) + 1);
		
		for(int y = yStart; y < yEnd; y++) {
			for(int x = xStart; x < xEnd; x++) {
				getTile(x, y).render(g, (int) (x * (Tile.getTileWidth() + xShift) - handler.getGameCamera().getxOffset()), (int) (y * (Tile.getTileHeight() + yShift) - handler.getGameCamera().getyOffset()));
			}
		}
		
		// Items
		itemManager.render(g);
		// Entities
		entityManager.render(g);
	}
	
	public Tile getTile(int x, int y) {
		if(x < 0 || y < 0 || x >= width || y >= height) {
			return Tile.grassTile;
		}
		
		Tile t = Tile.tiles[tiles[x][y]];
		if(t == null) {
			return Tile.dirtTile;
		}
		return t;
	}
	
	private void loadWorld(String path) {
		String file = Utils.loadFileAsString(path);
		String[] tokens = file.split("\\s+");
		width = Utils.parseInt(tokens[0]);
		height = Utils.parseInt(tokens[1]);
		spawnX = Utils.parseInt(tokens[2]);
		spawnY = Utils.parseInt(tokens[3]);
		
		tiles = new int[width][height];
		for(int y = 0; y < height; y++) {
			for(int x = 0; x < width; x++) {
				tiles[x][y] = Utils.parseInt(tokens[(x + y * width) + 4]);
			}
		}
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public Handler getHandler() {
		return handler;
	}

	public void setHandler(Handler handler) {
		this.handler = handler;
	}

	public ItemManager getItemManager() {
		return itemManager;
	}

	public void setItemManager(ItemManager itemManager) {
		this.itemManager = itemManager;
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public InventoryManager getInventoryManager() {
		return inventoryManager;
	}
}
