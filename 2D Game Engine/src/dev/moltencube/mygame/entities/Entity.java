package dev.moltencube.mygame.entities;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import dev.moltencube.mygame.Handler;
import dev.moltencube.mygame.entities.types.EntityType;
import dev.moltencube.mygame.items.Item;
import dev.moltencube.mygame.items.types.ToolType;

public abstract class Entity {
	
	// STATIC
	public static Entity[] entities = new Entity[256];
	
	public static final int DEFAULT_HEALTH = 10;
	
	// CLASS
	
	protected Handler handler;
	protected BufferedImage texture;
	protected float x, y;
	protected int width, height;
	protected int health, maxHealth;
	protected int strength;
	protected int id;
	protected boolean solid;
	protected boolean active = true;
	protected boolean hovering = false;
	protected Rectangle bounds;
	
	protected ToolType requiredTool;
	protected EntityType entityType;
	
	public Entity(Handler handler, float x, float y, int width, int height) {
		this.handler = handler;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.solid = true;
		
		maxHealth = DEFAULT_HEALTH;
		health = maxHealth;
		
		bounds = new Rectangle(0, 0, width, height);
	}
	
	public abstract void tick();
	
	public abstract void render(Graphics g);
	
	public abstract void die();
	
	public void hurt(int amt) {
		System.out.println("Hurt Called"); 
		if(handler.getWorld().getEntityManager().getPlayer().getTool() != null) {
			Item currentTool = handler.getWorld().getEntityManager().getPlayer().getTool();
			
			if(entityType == EntityType.STATIC && currentTool.getToolType() == requiredTool) {
				System.out.println("Hitting resource for: " + currentTool.getEfficiency());
				health -= currentTool.getEfficiency();
				
				if(health <= 0) {
					active = false;
					die();
				}
				return;
				
			}else if(entityType == EntityType.CREATURE) {
				System.out.println("Hitting creature for: " + amt);
				health -= amt;
				if(health <= 0) {
					active = false;
					die();
				}
				return;
			}
		}else
			if(entityType == EntityType.CREATURE) {
				System.out.println("Hitting creature for: " + amt);
				health -= amt;
				if(health <= 0) {
					active = false;
					die();
				}
			}
				
			if(entityType == EntityType.STATIC && requiredTool == ToolType.ANY) {
				System.out.println("Hitting resource for: " + amt);
				health -= amt;
				
				if(health <= 0) {
					active = false;
					die();
				}
			return;
		}
	}
	
	public boolean checkEntityCollisions(float xOffset, float yOffset) {
		for(Entity e : handler.getWorld().getEntityManager().getEntities()) {
			if(e.equals(this)) {
				continue;
			}
			if(e.isSolid() && e.getCollisionBounds(0f, 0f).intersects(getCollisionBounds(xOffset, yOffset))){
				return true;
			}
		}
		return false;
	}
	
	public Rectangle getCollisionBounds(float xOffset, float yOffset) {
		return new Rectangle((int) (x + bounds.x + xOffset), (int) (y + bounds.y + yOffset), bounds.width, bounds.height);
	}
	
	public BufferedImage getTexture() {
		return texture;
	}
	
	public ToolType getRequiredTool() {
		return requiredTool;
	}

	public EntityType getEntityType() {
		return entityType;
	}

	public void setEntityType(EntityType entityType) {
		this.entityType = entityType;
	}

	public void setTexture(BufferedImage texture) {
		this.texture = texture;
	}
	
	public int getStrength() {
		return this.strength;
	}
	
	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}
	
	public int getMaxHealth() {
		return maxHealth;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getId() {
		return id;
	}

	public boolean isHovering() {
		return hovering;
	}

	public void setHovering(boolean hovering) {
		this.hovering = hovering;
	}

	public boolean isSolid() {
		return solid;
	}

	public void setSolid(boolean solid) {
		this.solid = solid;
	}
}
