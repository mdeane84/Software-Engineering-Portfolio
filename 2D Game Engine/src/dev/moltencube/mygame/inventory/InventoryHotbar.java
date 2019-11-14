package dev.moltencube.mygame.inventory;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import dev.moltencube.mygame.Handler;
import dev.moltencube.mygame.gfx.Assets;

public class InventoryHotbar extends Inventory {
	
	private int healthOffsetX, healthOffsetY;
	private int hungerOffsetX, hungerOffsetY;
	private int thirstOffsetX, thirstOffsetY;
	private int healthWidth, hungerWidth, thirstWidth;
	
	public InventoryHotbar(Handler handler) {
		super(handler, 12, 12);
		
		this.active = true;
		this.slotOffsetX = 73;
		this.slotOffsetY = 56;
		this.ID = 2;
		
		healthOffsetX = 318;
		healthOffsetY = 16;
		
		hungerOffsetX = 316;
		hungerOffsetY = 40;
		
		thirstOffsetX = 322;
		thirstOffsetY = 40;
	}
	
	@Override
	public void render(Graphics g) {
		if(!active)
			return;	
		
		healthWidth = (int)((float)(handler.getWorld().getEntityManager().getPlayer().getHealth()) / (float)(handler.getWorld().getEntityManager().getPlayer().getMaxHealth()) * 458);
		hungerWidth = (int)((float)(handler.getWorld().getEntityManager().getPlayer().getHunger()) / (float)(handler.getWorld().getEntityManager().getPlayer().getMaxHunger()) * 248);
		thirstWidth = (int)((float)(handler.getWorld().getEntityManager().getPlayer().getThirst()) / (float)(handler.getWorld().getEntityManager().getPlayer().getMaxThirst()) * 248);
		
		if(healthWidth < 0)
			healthWidth = 0;
		if(hungerWidth < 0)
			hungerWidth = 0;
		if(thirstWidth < 0)
			thirstWidth = 0;
		
		if(this.getImage() != null) {
			g.setColor(Color.BLACK);
			
			// Draw health|food|thirst backdrop
			g.fillRect(this.getBounds().x + healthOffsetX - 238, this.getBounds().y + healthOffsetY, 470, 8);						// health
			g.fillRect(this.getBounds().x + hungerOffsetX - 248, this.getBounds().y + 40, 248, 8);									// food
			g.fillRect(this.getBounds().x + thirstOffsetX, this.getBounds().y + 40, 258, 8);										// thirst
			
			// Draw health|food|thirst
			g.setColor(Color.RED);
			g.fillRect(this.getBounds().x + healthOffsetX - healthWidth / 2, this.getBounds().y + 16, healthWidth, 8);													// health
			g.setColor(Color.BLUE);
			g.fillRect(this.getBounds().x + hungerOffsetX - hungerWidth, this.getBounds().y + hungerOffsetY, hungerWidth, 8);		// food
			g.fillRect(this.getBounds().x + thirstOffsetX, this.getBounds().y + thirstOffsetY, thirstWidth, 8);		// thirst
			g.drawImage(this.getImage(), this.getBounds().x, this.getBounds().y, null);
		}
			
		drawItems(g);
		drawItemNames(g);
	}
	
	@Override
	public Rectangle getBounds() {
		return new Rectangle(handler.getGame().getWidth() / 2 - Assets.hotbar.getWidth() / 2, handler.getGame().getHeight() - Assets.hotbar.getHeight(), this.getImage().getWidth(), this.getImage().getHeight());
	}
	
	@Override
	public BufferedImage getImage() {
		return Assets.hotbar;
	}
}
