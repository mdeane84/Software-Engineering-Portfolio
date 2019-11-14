package dev.moltencube.mygame.inventory;

import java.awt.event.MouseEvent;
import java.util.ArrayList;

import dev.moltencube.mygame.Handler;

public class InventoryManager {
	
	private Handler handler;
	private ArrayList<Inventory> inventories;
	
	public InventoryManager(Handler handler) {
		this.handler = handler;
		inventories = new ArrayList<Inventory>();
	}
	
	public void registerInventory(Inventory inv) {
		inventories.add(inv);
	}
	
	public void empty() {
		inventories.clear();
	}
	
	public void onMouseDragged(MouseEvent e) {
		for(Inventory inventory : inventories) {
			inventory.onMouseDragged(e);
		}
	}
	
	public void onMouseRelease(MouseEvent e) {
		for(Inventory inventory : inventories) {
			inventory.onMouseRelease(e);
		}
	}
	
	public void onMousePress(MouseEvent e) {
		for(Inventory inventory : inventories) {
			inventory.onMousePress(e);
		}
	}
	
	public void onMouseMove(MouseEvent e) {
		for(Inventory inventory : inventories) {
			inventory.onMouseMove(e);
		}
	}
	
	public void onMouseClicked(MouseEvent e) {
		for(Inventory inventory : inventories) {
			inventory.onMouseClicked(e);
		}
	}
}
