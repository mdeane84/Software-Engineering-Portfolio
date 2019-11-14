package dev.moltencube.mygame.entities.statics;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

import dev.moltencube.mygame.Handler;
import dev.moltencube.mygame.items.Item;
import dev.moltencube.mygame.items.types.ToolType;

public class Plant extends StaticEntity {
	
	public Plant(Handler handler, BufferedImage texture, float x, float y) {
		super(handler, x, y, 32, 48);
		this.texture = texture;
		this.strength = 0;
		this.solid = false;
		this.requiredTool = ToolType.ANY;
		
		bounds.x = 4;
		bounds.y = 32;
		bounds.width = 16;
		bounds.height = 16;
		
		maxHealth = 1;
		health = maxHealth;
	}
	public Plant(Handler handler, BufferedImage texture, float x, float y, int width, int height) {
		super(handler, x, y, width, height);
		
		this.texture = texture;
		this.strength = 0;
		this.solid = false;
		this.requiredTool = ToolType.ANY;
		
		bounds.x = 12;
		bounds.y = (int) (height / 1.2f);
		bounds.width = width - 22;
		bounds.height = (int) (height - height / 1.2f);
		
		maxHealth = 1;
		health = maxHealth;
	}

	@Override
	public void tick() {
		super.tick();
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(texture, (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height, null);
	}
	
	public void die() {
		Random random = new Random();
		handler.getWorld().getItemManager().addItem(Item.plantFiberItem.createNew((int) x,(int) y, random.nextInt(3) + 1));
	}
}