package dev.moltencube.mygame.inventory;

import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import dev.moltencube.mygame.Handler;
import dev.moltencube.mygame.items.Item;

public class InventoryMouse extends Inventory {
	public InventoryMouse(Handler handler) {
		super(handler, 1, 1);
		
		this.active = true;
		this.posX = handler.getMouseManager().getMouseX() - slotWidth/2;
		this.posY = handler.getMouseManager().getMouseY() - slotHeight/2;
		this.slotOffsetX = 0;
		this.slotOffsetY = 0;
		this.ID = 1;
	}
	
	@Override
	public void tick() {
		
		this.posX = handler.getMouseManager().getMouseX() - slotWidth/2;
		this.posY = handler.getMouseManager().getMouseY() - slotHeight/2;
		if(inventoryItems[0] != null) {
			inventoryItems[0].setX(posX);
			inventoryItems[0].setY(posY);
		}
			
		for(Item i : inventoryItems) {
			if(i != null)
				i.tick();
		}		
	}
	
	@Override
	public void onMousePress(MouseEvent e) {
		
		for(Item i : inventoryItems) {
			if(i != null)
				i.onMousePress(e);
		}
	}
	
	@Override
	public void onMouseRelease(MouseEvent e) {
		if(!hoveringAnyInv && this.getInventoryItems()[0] != null) {
			this.dropItem(this.getInventoryItems()[0], true);
		}
		
		hoveringAnyInv = false;
		System.out.println(this.posX);
	}
	
	@Override
	public Rectangle getBounds() {
		return new Rectangle(posX, posY, slotWidth, slotHeight);
	}
	
	@Override
	public BufferedImage getImage() {
		return null;
	}
}