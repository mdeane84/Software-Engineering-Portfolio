package dev.moltencube.mygame.entities.statics;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import dev.moltencube.mygame.Handler;
import dev.moltencube.mygame.items.Item;
import dev.moltencube.mygame.items.types.ToolType;

public class Boulder extends StaticEntity {
	
	Handler handler;
	
	public Boulder(Handler handler, BufferedImage texture, float x, float y) {
		super(handler, x, y, 64, 64);
		this.handler = handler;
		this.texture = texture;
		this.strength = 0;
		this.requiredTool = ToolType.PICKAXE;
		
		bounds.x = 12;
		bounds.y = (int) (height / 1.2f);
		bounds.width = width - 22;
		bounds.height = (int) (height - height / 1.2f);
		
		maxHealth = 5;
		health = maxHealth;
	}
	public Boulder(Handler handler, BufferedImage texture, float x, float y, int width, int height) {
		super(handler, x, y, width, height);
		this.handler = handler;
		this.texture = texture;
		this.requiredTool = ToolType.PICKAXE;
		
		bounds.x = 12;
		bounds.y = (int) (height / 1.2f);
		bounds.width = width - 22;
		bounds.height = (int) (height - height / 1.2f);
		
		maxHealth = 5;
		health = maxHealth;
	}

	@Override
	public void tick() {
		super.tick();
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(texture, (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height, null);
		
		if(health > 0) {
			//g.setColor(Color.red);
			//g.fillRect((int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset() - 8), width * health / startingHealth, 8);
		}
	}

	@Override
	public void die() {
		handler.getWorld().getItemManager().addItem(Item.rockItem.createNew((int) (x + width / 4), (int) (y + height / 2)));
	}

}
