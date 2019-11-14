package dev.moltencube.mygame.crafting.recipes;

import dev.moltencube.mygame.crafting.Recipe;
import dev.moltencube.mygame.inventory.Inventory;
import dev.moltencube.mygame.inventory.InventoryBulbPouch;
import dev.moltencube.mygame.items.Item;

public class RecipeBulbPouch extends Recipe {
	
	public RecipeBulbPouch(Item[] ingredients, String name, int count, int id) {
		super(ingredients, name, count, id);
	}
	
	@Override
	protected Inventory getInv() {
		Inventory inv = new InventoryBulbPouch(handler);
		handler.getWorld().getInventoryManager().registerInventory(inv);
		
		return inv;
	}

}
