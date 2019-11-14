package dev.moltencube.mygame.entities.statics;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

import dev.moltencube.mygame.Handler;
import dev.moltencube.mygame.gfx.Assets;
import dev.moltencube.mygame.items.Item;
import dev.moltencube.mygame.items.types.ToolType;

public class Tree extends StaticEntity {
	
	private Item[] drops;
	
	public Tree(Handler handler, BufferedImage texture, Item[] drops, float x, float y) {
		super(handler, x, y, 64, 2 * 64);
		
		this.texture = texture;
		this.strength = 0;
		this.requiredTool = ToolType.AXE;
		
		this.drops = drops;
		if(drops == null)
			this.drops = new Item[] {Item.woodItem.createNew(1)};
		
		initBounds();
		
		maxHealth = 5;
		health = maxHealth;
	}
	public Tree(Handler handler, BufferedImage texture, float x, float y, int width, int height) {
		super(handler, x, y, width, height);
		
		this.texture = texture;
		this.requiredTool = ToolType.AXE;
		
		initBounds();
		
		maxHealth = 5;
		health = maxHealth;
	}
	
	private void initBounds() {
		if(texture == Assets.tree1) {
			bounds.x = 12;
			bounds.y = (int) (height / 1.2f);
			bounds.width = width - 22;
			bounds.height = (int) (height - height / 1.2f);
		}else if(texture == Assets.tree2) {
			bounds.x = 8;
			bounds.y = (int) (height / 1.2f);
			bounds.width = width - 24;
			bounds.height = (int) (height - height / 1.2f);
		}else if(texture == Assets.tree3) {
			bounds.x = 10;
			bounds.y = 102;
			bounds.width = width - 22;
			bounds.height = 16;
		}else {
			bounds.x = 12;
			bounds.y = (int) (height / 1.2f);
			bounds.width = width - 22;
			bounds.height = (int) (height - height / 1.2f);
		}
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
	
	public void die() {
		Random random = new Random();
		for(Item i : drops) {
			handler.getWorld().getItemManager().addItem(i.createNew((int) (this.x - 32 + random.nextInt(64)), (int) (this.y + 32 + random.nextInt(64)), i.getCount() + 1));
		}
	}
}
