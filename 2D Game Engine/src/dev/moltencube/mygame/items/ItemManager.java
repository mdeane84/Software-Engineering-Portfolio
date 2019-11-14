package dev.moltencube.mygame.items;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;

import dev.moltencube.mygame.Handler;

public class ItemManager {
	
	private Handler handler;
	private ArrayList<Item> items;
	private ArrayList<Item> itemsToAdd;
	private Comparator<Item> renderSorter = new Comparator<Item>() {
		@Override
		public int compare(Item a, Item b) {
			if(a.getY() + Item.ITEMWIDTH < b.getY() + Item.ITEMHEIGHT)
				return -1;
			return 1;
		}
	};
	
	public ItemManager(Handler handler) {
		this.handler = handler;
		items = new ArrayList<Item>();
		itemsToAdd = new ArrayList<Item>();
	}
	
	public void tick() {
		if(!itemsToAdd.isEmpty()) {
			for(Item item : itemsToAdd)
				items.add(item);
			itemsToAdd.clear();
			System.out.println("Item Actually Added!");
		}

		Iterator<Item> it = items.iterator();
		while(it.hasNext()) {
			Item i = it.next();
			i.tick();
			
			if(i.isToBeRemoved()) {
				it.remove();
				System.out.println("Item: " + i.getName() + " Removed From World!");
				
				if(i.isPickedUp())
					System.out.println("ITEM: " + i.getName() + " Picked Up");
				
				if(i.isCombined()) {
					System.out.println("ITEM: " + i.getName() + " Combined!");
				}
			}
		}
		
		if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_T)) {
			Iterator<Item> iterate = items.iterator();
			while(iterate.hasNext()) {
				Item i = iterate.next();
				System.out.println("Item Location: " + i.getX() + ", " + i.getY());
				System.out.println("Item PickedUp: " + i.isPickedUp());
			}
		}
		
		items.sort(renderSorter);
	}
	
	public void render(Graphics g) {
		for(Item i : items)
			i.drawShadow(g);
		
		for(Item i : items) 
			i.render(g);
	}
	
	public void addItem(Item item) {
		if(item.getHandler() == null)
			item.setHandler(handler);
		itemsToAdd.add(item);
		System.out.println("Item Added To World!");
	}
	
	// getters and setters
	
	public Handler getHandler() {
		return handler;
	}

	public void setHandler(Handler handler) {
		this.handler = handler;
	}

	public ArrayList<Item> getItems() {
		return items;
	}

}
