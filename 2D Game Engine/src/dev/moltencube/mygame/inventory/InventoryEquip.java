package dev.moltencube.mygame.inventory;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import dev.moltencube.mygame.Handler;
import dev.moltencube.mygame.gfx.Assets;
import dev.moltencube.mygame.items.types.EquipType;

public class InventoryEquip extends Inventory {
	public InventoryEquip(Handler handler) {
		super(handler, 2, 2);
		
		this.active = true;
		this.slotOffsetX = 64;
		this.slotOffsetY = 8;
		this.ID = 3;
	}
	
	@Override
	public void handleMouseRightClick(Inventory mouse, int index) {
		// Combine or Trade Item
		
		if(mouse.inventoryItems[0] != null) {
			if(index == 0 && mouse.inventoryItems[0].getEquipType() == EquipType.TOOL) {
				moveItemToInventoryAt(this.inventoryItems[0], mouse, index, 0);
			} else if(index == 1 && mouse.inventoryItems[0].getEquipType() == EquipType.STORAGE)  {
				moveItemToInventoryAt(this.inventoryItems[0], mouse, index, 0);
			} else if(this.inventoryItems[index] != null && handler.getWorld().getEntityManager().getPlayer().getHotbarInv().hasRoomFor(this.inventoryItems[index])) {
				this.inventoryItems[index].setCurrentInventory(handler.getWorld().getEntityManager().getPlayer().getHotbarInv());
				handler.getWorld().getEntityManager().getPlayer().getHotbarInv().addItem(this.inventoryItems[index]);
				this.inventoryItems[index] = null;
			}
		} else if(this.inventoryItems[index] != null && handler.getWorld().getEntityManager().getPlayer().getHotbarInv().hasRoomFor(this.inventoryItems[index])) {
			this.inventoryItems[index].setCurrentInventory(handler.getWorld().getEntityManager().getPlayer().getHotbarInv());
			handler.getWorld().getEntityManager().getPlayer().getHotbarInv().addItem(this.inventoryItems[index]);
			this.inventoryItems[index] = null;
		}
	}
	
	@Override
	public void handleMouseLeftClick(Inventory mouse, int index) {
			if(mouse.inventoryItems[0] == null) {
				moveItemToInventoryAt(inventoryItems[index], handler.getWorld().getEntityManager().getPlayer().getMouseInv(), index, 0);
			} else if(index == 0 && mouse.inventoryItems[0].getEquipType() == EquipType.TOOL) {
				moveItemToInventoryAt(inventoryItems[index], handler.getWorld().getEntityManager().getPlayer().getMouseInv(), index, 0);
			} else if(index == 1 && mouse.inventoryItems[0].getEquipType() == EquipType.STORAGE) {
				moveItemToInventoryAt(inventoryItems[index], handler.getWorld().getEntityManager().getPlayer().getMouseInv(), index, 0);
			}
	}
	
	@Override
	public Rectangle getBounds() {
		return new Rectangle(handler.getWorld().getEntityManager().getPlayer().getHotbarInv().getBounds().x + 600, handler.getWorld().getEntityManager().getPlayer().getHotbarInv().getBounds().y + 48, this.getImage().getWidth(), this.getImage().getHeight());
	}
	
	@Override
	public BufferedImage getImage() {
		return Assets.hotbarEquip;
	}
}
