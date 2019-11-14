package dev.moltencube.mygame.inventory;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import dev.moltencube.mygame.Handler;
import dev.moltencube.mygame.gfx.Assets;
import dev.moltencube.mygame.gfx.Text;
import dev.moltencube.mygame.items.Item;
import dev.moltencube.mygame.items.types.EquipType;

public class Inventory {

	// CLASS
	
	protected Handler handler;
	protected boolean active = false;
	protected Item[] inventoryItems;
	
	protected final int INVENTORY_SIZE;
	protected final int ROW_COUNT;
	protected boolean hoveringAnyInv = false;
	
	protected int slotOffsetX, slotOffsetY, slotWidth = 32, slotHeight = 32, slotSpacing = 10;
	protected int posX, posY;
	protected int ID = 0;
	
	public Inventory(Handler handler, int size, int rowCount) {
		this.handler = handler;
		this.INVENTORY_SIZE = size;
		this.ROW_COUNT = rowCount;
		inventoryItems = new Item[INVENTORY_SIZE];
		slotOffsetX = 0;
		slotOffsetY = 0;
		
		handler.getInventoryManager().registerInventory(this);
	}
	
	/* Tick method called every frame just before render method
	 * Should be used for game logic and updating states
	 * Ticks all items stored in the inventory
	 */
	public void tick() {
		
		for(Item i : inventoryItems) {
			if(i != null) {
				if(i.getCount() <= 0) {
					this.removeItemAt(i, i.getInventoryIndex());
				}
				i.tick();
			}
		}
	}
	
	/* Render method called every frame just after tick method
	 * Should be used for drawing and updating graphics
	 * Draws the background image if it exists then draws items
	 */
	public void render(Graphics g) {
		if(!active)
			return;	
		
		if(this.getImage() != null)
			g.drawImage(this.getImage(), this.getBounds().x, this.getBounds().y, null);
			
		drawItems(g);
		drawItemNames(g);
	}
	
	/* Draw Items is a helper method for render
	 * It loops through all the items currently in an
	 * inventory and draws their sprites in the appropriate
	 * inventory slot.
	 */
	protected void drawItems(Graphics g) {
		Item mouseItem = null;
		for(int i = 0; i < INVENTORY_SIZE; i++) {														// iterates through all items in the current inventory
			if(inventoryItems[i] != null) {																// checks that the slot is not null
				if(!inventoryItems[i].isFollowingMouse()) {												// checks that the item is not in a slot (not following the mouse)
					inventoryItems[i].render(g, getLocationByIndex(i).x, getLocationByIndex(i).y);			// if not following mouse, render in its given slot
				} else {
					mouseItem = inventoryItems[i];															// else, store the mouse item to be rendered last
				}
			}
		}
		if(mouseItem != null)																			// if there was an item following the mouse, render it.
			mouseItem.render(g, handler.getMouseManager().getMouseX() - Item.ITEMWIDTH / 2, handler.getMouseManager().getMouseY() - Item.ITEMHEIGHT / 2);
	}
	
	/* Draw Item Names is a helper method for render
	 * It loops through all the items currently in an
	 * inventory and draws their name over their sprite
	 * when it is being hovered by the mouse.
	 */
	protected void drawItemNames(Graphics g) {
		for(int i = 0; i < INVENTORY_SIZE; i++) {
			if(inventoryItems[i] != null) {
				if(inventoryItems[i].isHovering() && inventoryItems[i].isNameVisible()) {
					Text.drawString(g, inventoryItems[i].getName(), handler.getMouseManager().getMouseX() - 8,
							handler.getMouseManager().getMouseY(), false, false, Color.white, Assets.font12);
				}
			}
		}
	}
	
	// inventory methods
	
	public void addItem(Item item) {
		
		// add item to any existing stacks
		for(int i = 0; i < INVENTORY_SIZE; i++) {
			if(inventoryItems[i] == null)																				// skips any null items
				continue;
			
			if(inventoryItems[i].getId() == item.getId()) {																// if they are the same item
				if(inventoryItems[i].getCount() + item.getCount() <= inventoryItems[i].getMaxCount()) {						// if there is room to combine both stacks completely
					inventoryItems[i].setCount(inventoryItems[i].getCount() + item.getCount());									// combine the stacks
					return;
				}else {																										// if there is NOT enough room to combine both stacks completely
					item.setCount(item.getCount() - (inventoryItems[i].getMaxCount() - inventoryItems[i].getCount()));			// top off the existing stack
					inventoryItems[i].setCount(inventoryItems[i].getMaxCount());												// reduce the stack to be added and continue
				}
			}
		}
		
		// add add remaining stack to first empty slot
		for(int i = 0; i < INVENTORY_SIZE; i++) {
			if(inventoryItems[i] == null) {
				if(item.getHandler() == null)															// always ensure the stack has a handler because
					item.setHandler(handler);															// some items may be created on the fly
				
				//=initialize variables for the given spot=
				item.setInventoryIndex(i);
				item.setPreviousIndex(i);
				item.setCountVisible(true);
				item.setNameVisible(true);
				item.setPickedUp(true);
				//=========================================
				
				if(item.getCount() <= item.getMaxCount()) {											// if the item is within its max count then add it
					System.out.println("add item");
					item.setToBeRemoved(true);
					inventoryItems[i] = item;
					item.setPosition(this.getLocationByIndex(i).x, this.getLocationByIndex(i).y);
					return;
				}else {																				// else, add a max count stack and continue with the remainder
					item.setCount(item.getCount() - item.getMaxCount());
					Item tempItem = item.copy();
					tempItem.setCount(tempItem.getMaxCount());
					inventoryItems[i] = tempItem;
					tempItem.setPosition(this.getLocationByIndex(i).x, this.getLocationByIndex(i).y);
				}
			}
		}
		
		// inventory full, drop remaining stack
		System.out.println("Inventory Full! dropping item.");
		dropItem(item, false);
	}
	
	/* Moves an item to a specified slot and
	 * returns whatever item was already there
	 */
	public Item tradeItemAt(Item item, int index) {
		Item tempItem = this.inventoryItems[index];
		this.inventoryItems[index] = item;
		
		if(item != null) {
			item.setInventoryIndex(index);
			item.setInventory(this);
		}
		
		return tempItem;
	}
	
	public void removeItem(Item item) {
		for(int i = 0; i < inventoryItems.length; i++) {
			if(inventoryItems[i] != null) {
				if(inventoryItems[i].getId() == item.getId()) {
					
					/* Remove if inventory stack >= amount to be removed
					 * else decrement item to be removed by the inventory item
					 * and remove the inventory item
					 */
					if(inventoryItems[i].getCount() >= item.getCount()) {
						inventoryItems[i].setCount(inventoryItems[i].getCount() - item.getCount());
						if(inventoryItems[i].getCount() == 0)
							inventoryItems[i] = null;
						return;
					}
					else {
						item.setCount(item.getCount() - inventoryItems[i].getCount()); 
						inventoryItems[i] = null;
					}
					
				}
			}
		}
	}
	
	public void removeItemAt(Item item, int index) {
		if(inventoryItems[index].getId() == item.getId()) {
			if(inventoryItems[index].getCount() > item.getCount()) {
				inventoryItems[index].setCount(inventoryItems[index].getCount() - item.getCount());
			}else if(inventoryItems[index].getCount() == item.getCount()) {
				inventoryItems[index] = null;
			}else {
				item.setCount(item.getCount() - inventoryItems[index].getCount());
				inventoryItems[index] = null;
				removeItem(item);
			}
		}
	}
	
	public void dropItem(Item item, boolean remove) {
			item.setX((int) handler.getWorld().getEntityManager().getPlayer().getX());
			item.setY((int) handler.getWorld().getEntityManager().getPlayer().getY());
			
			item.setInventoryIndex(-1);
			item.setPreviousIndex(-1);
			item.setCountVisible(false);
			item.setNameVisible(false);
			item.setPickedUp(false);
			item.setToBeRemoved(false);
			System.out.println("Item: " + item.getName() + "Dropped at (" + item.getX() + ", " + item.getY() + ")");
			handler.getWorld().getItemManager().addItem(item.createNewWithInventory(item.getX() + 16, item.getY() + 32, item.getCount(), item.getStorageInv()));
			item.setToBeRemoved(true);
			if(remove)
				removeItem(item);
	}
	
	/* Checks if there is room for an item in an inventory
	 * returns true if there is an empty slot OR
	 * there is an item of that id with room for at least 1
	 */
	public boolean hasRoomFor(Item item) {
		for(int i = 0; i < INVENTORY_SIZE; i++) {
			if(inventoryItems[i] == null || (inventoryItems[i].getId() == item.getId() && inventoryItems[i].getCount() < inventoryItems[i].getMaxCount()))
				return true;
		}
		return false;
	}
	
	public boolean hasItem(Item item) {
		boolean hasItem = false;
		int invCount = 0;
		
		for(Item i : inventoryItems) {
			if(i != null) {
				if(i.getId() == item.getId())
					invCount += i.getCount();
			}
		}
		
		if(invCount >= item.getCount())
			hasItem = true;
		
		return hasItem;
	}
	
	public void moveItem(Item item1, int index2) {
		int index1 = item1.getPreviousIndex();
		Item item2 = inventoryItems[index2];
		
		if(canMoveItem(item1, item2, index1, index2)) {
			
			if(inventoryItems[index2] == null) {
				item1.setInventoryIndex(index2);
				item1.setPreviousIndex(index2);
				inventoryItems[index2] = item1;
				inventoryItems[index1] = null;
				System.out.println("Item moved to NULL slot: " + index2);
				
			}else if(index1 != index2 && item1.getId() == item2.getId()) {
				if(item1.getCount() + item2.getCount() <= item1.getMaxCount()) {
					inventoryItems[index2].setCount(item1.getCount() + item2.getCount());
					inventoryItems[index1] = null;
					
					System.out.println("Item moved to COMBINE SAME slot: " + index2);
				}else {
					inventoryItems[index1].setCount(item2.getCount() - (item1.getMaxCount() - item1.getCount()));
					inventoryItems[index2].setCount(item2.getMaxCount());
					
					System.out.println("Item moved to COMBINE SAME slot: " + index2);
				}
				
				
			}else if(index1 != index2){
				item1.setInventoryIndex(index2);
				item1.setPreviousIndex(index2);
				inventoryItems[index2] = item1;
				item2.setInventoryIndex(index1);
				item2.setPreviousIndex(index1);
				inventoryItems[index1] = item2;
				System.out.println("Item moved to SWAP slot: " + index2);
				
			}else {
				System.out.println("Item Not Moved");
			}
			
			if(inventoryItems[index1] != null)
				inventoryItems[index1].setPosition(this.getBounds().x + slotOffsetX + (index1 * Item.ITEMWIDTH) + (index1 * slotSpacing), this.getBounds().y + slotOffsetY);
			if(inventoryItems[index2] != null)
				inventoryItems[index2].setPosition(this.getBounds().x + slotOffsetX + (index2 * Item.ITEMWIDTH) + (index2 * slotSpacing), this.getBounds().y + slotOffsetY);
			
		}
	}
	
	public void moveItemToInventoryAt(Item item, Inventory inv, int index1, int index2) {
		this.inventoryItems[index1] = inv.tradeItemAt(item, index2);
		if(inventoryItems[index1] != null) {
			this.inventoryItems[index1].setInventoryIndex(index1);
			this.inventoryItems[index1].setInventory(this);
		}
	}
	
	public void handleMouseRightClick(Inventory mouse, int index) {
		// Some inventories must be active (open) to process mouse events
		if(!isActive())
			return;
		
		// Combine or Trade Item
		
		if(this.inventoryItems[index] != null && this.inventoryItems[index].getEquipType() == EquipType.TOOL) {
			moveItemToInventoryAt(this.inventoryItems[index], handler.getWorld().getEntityManager().getPlayer().getEquipInv(), index, 0);
			
		} else if(this.inventoryItems[index] != null && this.inventoryItems[index].getEquipType() == EquipType.STORAGE) {
			moveItemToInventoryAt(this.inventoryItems[index], handler.getWorld().getEntityManager().getPlayer().getEquipInv(), index, 1);
			
		}else if(this.inventoryItems[index] != null && mouse.inventoryItems[0] != null) {
			if(mouse.inventoryItems[0].getId() == this.inventoryItems[index].getId()) {
				if(this.inventoryItems[index].getCount() < this.inventoryItems[index].getMaxCount()) {
					mouse.inventoryItems[0].setCount(mouse.inventoryItems[0].getCount() - 1);
					if(mouse.inventoryItems[0].getCount() <= 0)
						mouse.inventoryItems[0] = null;
					this.inventoryItems[index].setCount(this.inventoryItems[index].getCount() + 1);
				}
			} else {
				moveItemToInventoryAt(this.inventoryItems[index], mouse, index, 0);
			}
		} else if(this.inventoryItems[index] == null && mouse.inventoryItems[0] != null) {
			mouse.inventoryItems[0].setCount(mouse.inventoryItems[0].getCount() - 1);
			this.tradeItemAt(mouse.inventoryItems[0].createNew(1), index);
			if(mouse.inventoryItems[0].getCount() <= 0)
				mouse.inventoryItems[0] = null;
		} else if(this.inventoryItems[index] != null && mouse.inventoryItems[0] == null) {
			int tempCount = this.inventoryItems[index].getCount();
			this.inventoryItems[index].setCount(tempCount / 2);
			mouse.inventoryItems[0] = this.inventoryItems[index].createNew(tempCount - tempCount / 2);
		}
	}
	
	public void handleMouseLeftClick(Inventory mouse, int index) {
		if(!isActive())
			return;
		
		if(this.inventoryItems[index] != null && mouse.inventoryItems[0] != null) {
			if(this.inventoryItems[index].getId() == mouse.inventoryItems[0].getId()) {
				if(this.inventoryItems[index].getCount() + mouse.inventoryItems[0].getCount() <= this.inventoryItems[index].getMaxCount()) {
					this.inventoryItems[index].setCount(this.inventoryItems[index].getCount() + mouse.inventoryItems[0].getCount());
					mouse.inventoryItems[0] = null;
				} else {
					int leftover = this.inventoryItems[index].getCount() + mouse.inventoryItems[0].getCount() - this.inventoryItems[index].getMaxCount();
					this.inventoryItems[index].setCount(this.inventoryItems[index].getMaxCount());
					mouse.inventoryItems[0].setCount(leftover);
				}
			} else {
				moveItemToInventoryAt(inventoryItems[index], handler.getWorld().getEntityManager().getPlayer().getMouseInv(), index, 0);
			}
		} else {
			moveItemToInventoryAt(inventoryItems[index], handler.getWorld().getEntityManager().getPlayer().getMouseInv(), index, 0);
		}
	}
	
	private boolean canMoveItem(Item item1, Item item2, int index1, int index2) {
		if(item2 != null)
			return (index2 < 100 || item1.getEquipType().ordinal() == index2 - 100) && (index1 < 100 || item2.getEquipType().ordinal() == index1 - 100);
		return (index2 < 100 || item1.getEquipType().ordinal() == index2 - 100);
	}
	
	public int getHoveredSlot() {
		int mouseX = handler.getMouseManager().getMouseX();
		int mouseY = handler.getMouseManager().getMouseY();
		Rectangle slotBounds = new Rectangle(slotWidth, slotHeight);
		
		for(int i = 0; i < INVENTORY_SIZE; i++) {
			slotBounds.x = this.getLocationByIndex(i).x;
			slotBounds.y = this.getLocationByIndex(i).y;
			if(slotBounds.contains(mouseX, mouseY))
				return i;
		}

		return -1;
	}
	
	public Handler getHandler() {
		return handler;
	}

	public void setHandler(Handler handler) {
		this.handler = handler;
	}
	
	public BufferedImage getImage() {
		return Assets.hotbar;
	}
	
	public Rectangle getBounds() {
		return new Rectangle(0, 0, this.getImage().getWidth(), this.getImage().getHeight());
	}
	
	public Point getLocationByIndex(int index) {
		return new Point(this.getBounds().x + this.getSlotOffsetX() + ((index % ROW_COUNT) * slotSpacing) + ((index % ROW_COUNT) * slotWidth),
				this.getBounds().y + this.getSlotOffsetY() + ((index/ROW_COUNT % ROW_COUNT) * slotSpacing) + ((index/ROW_COUNT % ROW_COUNT) * slotHeight));
	}

	public int getInventorySize() {
		return INVENTORY_SIZE;
	}
	
	public int getID() {
		return ID;
	}

	public boolean isActive() {
		return active;
	}

	public int getSlotOffsetX() {
		return slotOffsetX;
	}

	public int getSlotOffsetY() {
		return slotOffsetY;
	}

	public int getSlotSpacing() {
		return slotSpacing;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Item[] getInventoryItems() {
		return inventoryItems;
	}
	
	// mouse handler
	
	public void onMouseMove(MouseEvent e) {
		if(!isActive())
			return;
		
		for(Item i : inventoryItems) {
			if(i != null)
				i.onMouseMove(e);
		}
	}
	
	public void onMouseDragged(MouseEvent e) {
		if(!isActive())
			return;
		
		for(Item i : inventoryItems) {
			if(i != null)
				i.onMouseDragged(e);
		}
	}
	
	public void onMousePress(MouseEvent e) {
		if(!isActive())
			return;
		
		if(this.getBounds().contains(new Point(handler.getMouseManager().getMouseX(), handler.getMouseManager().getMouseY()))) {
			handler.getWorld().getEntityManager().getPlayer().getMouseInv().hoveringAnyInv = true;
		}else {
			handler.getWorld().getEntityManager().getPlayer().getMouseInv().hoveringAnyInv = false;
		}
		
		for(Item i : inventoryItems) {
			if(i != null)
				i.onMousePress(e);
		}
	}
	
	public void onMouseRelease(MouseEvent e) {
		if(!isActive())
			return;
		
		for(Item i : inventoryItems) {
			if(i != null)
				i.onMouseRelease(e);
		}
		
		// if the mouse is hovering this inventory
		if(this.getBounds().contains(new Point(handler.getMouseManager().getMouseX(), handler.getMouseManager().getMouseY()))) {
			handler.getWorld().getEntityManager().getPlayer().getMouseInv().hoveringAnyInv = true;
			System.out.println("Hovering True");
			int tempHoveredSlot = this.getHoveredSlot();
			
			if(tempHoveredSlot >= 0) {
				if(e.getButton() == MouseEvent.BUTTON1) {
					handleMouseLeftClick(handler.getWorld().getEntityManager().getPlayer().getMouseInv(), tempHoveredSlot);
				} else if(e.getButton() == MouseEvent.BUTTON3) {
					handleMouseRightClick(handler.getWorld().getEntityManager().getPlayer().getMouseInv(), tempHoveredSlot);
				}
			}
			
		}
		System.out.println(this.posX);
	}
	
	public void onMouseClicked(MouseEvent e) {
		for(Item i : inventoryItems) {
			if(i != null)
				i.onMouseClicked(e);
		}
	}
}
