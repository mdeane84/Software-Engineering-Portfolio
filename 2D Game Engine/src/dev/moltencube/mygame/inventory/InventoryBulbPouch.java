package dev.moltencube.mygame.inventory;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import dev.moltencube.mygame.Handler;
import dev.moltencube.mygame.gfx.Assets;
import dev.moltencube.mygame.items.Item;

public class InventoryBulbPouch extends Inventory {
	
	public InventoryBulbPouch(Handler handler) {
		super(handler, Item.bulbPouch.getStorageSpace(), Item.bulbPouch.getStorageWidth());
		
		this.setActive(true);
		this.slotOffsetX = 10;
		this.slotOffsetY = 34;
		this.ID = 4;
	}
	
	@Override
	public void render(Graphics g) {
		
		if(!isActive())
			return;	
		
		if(this.getImage() != null)
			g.drawImage(this.getImage(), this.getBounds().x, handler.getWorld().getEntityManager().getPlayer().getHotbarInv().posY +  this.getBounds().y, null);
			
		drawItems(g);
		drawItemNames(g);
	}
	
	@Override
	public Rectangle getBounds() {
		return new Rectangle(handler.getWorld().getEntityManager().getPlayer().getHotbarInv().getBounds().x + 592, 
				handler.getWorld().getEntityManager().getPlayer().getHotbarInv().getBounds().y + -122,
				this.getImage().getWidth(), this.getImage().getHeight());
	}
	
	@Override
	public BufferedImage getImage() {
		return Assets.bulbPouchInventory;
	}

}
