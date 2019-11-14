package dev.moltencube.mygame.crafting;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import dev.moltencube.mygame.Handler;
import dev.moltencube.mygame.crafting.recipes.RecipeBulbPouch;
import dev.moltencube.mygame.entities.creatures.Player;
import dev.moltencube.mygame.gfx.Assets;
import dev.moltencube.mygame.inventory.Inventory;
import dev.moltencube.mygame.items.Item;

public class Recipe {
	
	// Handler
	
	public static Recipe[] recipes = new Recipe[256];
	
	// recipes
		// resources
	public static Recipe plantFiberRopeRecipe = new Recipe(new Item[]{Item.plantFiberItem.createNew(3)}, "Fiber Rope", 1, Item.plantFiberRopeItem.getId());
	
		// tools
			// bone
	public static Recipe boneKnifeRecipe = new Recipe(new Item[]{Item.stickItem.createNew(1), Item.plantFiberRopeItem.createNew(1), Item.boneItem.createNew(1)}, "Bone Knife", 1, Item.boneKnifeItem.getId());
	public static Recipe bonePickaxeRecipe = new Recipe(new Item[]{Item.stickItem.createNew(2), Item.plantFiberRopeItem.createNew(1), Item.boneItem.createNew(1)}, "Bone Pickaxe", 1, Item.bonePickaxeItem.getId());
			// stone
	public static Recipe stoneKnifeRecipe = new Recipe(new Item[]{Item.stickItem.createNew(1), Item.plantFiberRopeItem.createNew(1), Item.rockItem.createNew(1)}, "Stone Knife", 1, Item.stoneKnifeItem.getId());
	public static Recipe stonePickaxeRecipe = new Recipe(new Item[]{Item.stickItem.createNew(2), Item.plantFiberRopeItem.createNew(1), Item.rockItem.createNew(2)}, "Stone Pickaxe", 1, Item.stonePickaxeItem.getId());
	public static Recipe stoneAxeRecipe = new Recipe(new Item[]{Item.stickItem.createNew(2), Item.plantFiberRopeItem.createNew(1), Item.rockItem.createNew(1)}, "Stone Axe", 1, Item.stoneAxeItem.getId());
	public static Recipe stoneShovelRecipe = new Recipe(new Item[]{Item.stickItem.createNew(2), Item.plantFiberRopeItem.createNew(1), Item.rockItem.createNew(1)}, "Stone Shovel", 1, Item.stoneShovelItem.getId());
		
		// storage
	public static RecipeBulbPouch bulbPouchRecipe = new RecipeBulbPouch(new Item[]{Item.treeBulbItem.createNew(8), Item.plantFiberItem.createNew(3)}, "Bulb Pouch", 1, Item.bulbPouch.getId());
	
	
	// Class
	
	private Item[] ingredients;
	
	protected Handler handler;
	protected String name;
	protected BufferedImage texture;
	protected final int id, count;
	
	protected Rectangle bounds;
	
	protected static final int WIDTH = 32, HEIGHT = 32;
	
	protected boolean craftable = false;
	
	public Recipe(Item[] ingredients, String name, int count, int id) {
		this.ingredients = ingredients;
		this.name = name;
		this.texture = Item.items[id].getTexture();
		this.id = id;
		this.count = count;
		
		bounds = new Rectangle(0, 0, 32, 32);
		
		for(int i = 0; i < this.ingredients.length; i++) {
			this.ingredients[i].setNameVisible(true);
			this.ingredients[i].setCountVisible(true);
		}
		
		recipes[id] = this;
	}
	
	public void tick() {
		
	}
	
	public void render(Graphics g) {
		
	}
	
	public void render(Graphics g, int x, int y) {
		g.drawImage(Assets.craft2MenuFrame, x, y, null);
		g.drawImage(texture, x, y, null);
	}
	
	public boolean isCraftable() {
		
		Inventory hotbar = handler.getWorld().getEntityManager().getPlayer().getHotbarInv();
		Inventory backpack;
		
		if(handler.getWorld().getEntityManager().getPlayer().getEquipInv().getInventoryItems()[1] != null) {
			backpack = handler.getWorld().getEntityManager().getPlayer().getEquipInv().getInventoryItems()[1].getStorageInv();
		} else {
			backpack = new Inventory(handler, 1, 1);
		}
		
		if(hotbar == null)
			return false;
		
		for(Item i : ingredients) {
			if(hotbar.hasItem(i) || backpack.hasItem(i)) {
				craftable = true;
			}else {
				return false;
			}
		}
		
		return craftable;
	}
	
	public boolean craft() {
		Player player = handler.getWorld().getEntityManager().getPlayer();
		Inventory inventory = handler.getWorld().getEntityManager().getPlayer().getHotbarInv();
		Inventory backpack;
		if(handler.getWorld().getEntityManager().getPlayer().getEquipInv().getInventoryItems()[1] != null) {
			backpack = handler.getWorld().getEntityManager().getPlayer().getEquipInv().getInventoryItems()[1].getStorageInv();
		} else {
			backpack = new Inventory(handler, 1, 1);
		}
		
		if(this.isCraftable()) {
			for(Item i : ingredients) {
				if(inventory.hasItem(i)) {
					inventory.removeItem(i);
				} else if(backpack.hasItem(i)) {
					backpack.removeItem(i);
				}
			}
			
			Inventory tempInv = this.getInv();
			if(tempInv != null) {
				Item craftedItem = Item.items[this.id].createNewWithInventory((int) player.getX(), (int) player.getY(), tempInv);
				if(inventory.hasRoomFor(craftedItem)) {
					inventory.addItem(craftedItem);
				} else if(backpack.hasRoomFor(craftedItem)) {
					backpack.addItem(craftedItem);
				} else {
					handler.getWorld().getItemManager().addItem(craftedItem);
				}
			} else {
				System.out.println("System Failed To Apply Inventory");
				Item craftedItem = Item.items[this.id].createNew((int) player.getX(), (int) player.getY(), count);
				if(inventory.hasRoomFor(craftedItem)) {
					inventory.addItem(craftedItem);
				} else if(backpack.hasRoomFor(craftedItem)) {
					backpack.addItem(craftedItem);
				} else {
					handler.getWorld().getItemManager().addItem(craftedItem);
				}
			}
			
			return true;
		}
		System.out.println("Item: " + this.getName() + " Not Craftable!");
		return false;
	}
	
	protected Inventory getInv()  {
		return null;
	}
	
	public Recipe getById(int id) {
		return recipes[id];
	}
	
	public Recipe getByName(String name) {
		for(Recipe recipe : recipes) {
			if(recipe.getName() == name) {
				return recipe;
			}
		}
		return null;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Item[] getIngredients() {
		return ingredients;
	}

	public void setIngredients(Item[] ingredients) {
		this.ingredients = ingredients;
	}

	public BufferedImage getTexture() {
		return texture;
	}

	public void setTexture(BufferedImage texture) {
		this.texture = texture;
	}

	public void setHandler(Handler handler) {
		this.handler = handler;
		for(int i = 0; i < ingredients.length; i++)
			ingredients[i].setHandler(handler);
	}

	public Rectangle getBounds() {
		return bounds;
	}

	public void setBoundsX(int x) {
		this.bounds.x = x;
	}
	
	public void setBoundsY(int y) {
		this.bounds.y = y;
	}
}
