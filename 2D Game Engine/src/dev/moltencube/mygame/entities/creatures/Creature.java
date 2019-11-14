package dev.moltencube.mygame.entities.creatures;

import dev.moltencube.mygame.Handler;
import dev.moltencube.mygame.entities.Entity;
import dev.moltencube.mygame.entities.types.EntityType;
import dev.moltencube.mygame.items.Item;
import dev.moltencube.mygame.tiles.Tile;

public abstract class Creature extends Entity {
	
	public static final float DEFAULT_SPEED = 3.0f;
	public static final int DEFAULT_CREATURE_WIDTH = 64, DEFAULT_CREATURE_HEIGHT = 64;
	
	protected float speed;
	protected float xMove, yMove;
	protected int hunger, thirst;
	protected int maxHunger = 100, maxThirst = 100;

	public Creature(Handler handler, float x, float y, int width, int height) {
		super(handler, x, y, width, height);
		this.entityType = EntityType.CREATURE;
		speed = DEFAULT_SPEED;
		xMove = 0;
		yMove = 0;
		
		this.hunger = 100;
		this.thirst = 100;
	}
	
	public void consume(Item item) {
		if(item.isEdible()) {
			if(item.getHungerValue() != 0) {
				if(hunger + item.getHungerValue() > maxHunger)
					hunger = maxHunger;
				else if(hunger + item.getHungerValue() < 0)
					hunger = 0;
				else
					hunger += item.getHungerValue();
			}
			if(item.getThirstValue() != 0) {
				if(thirst + item.getThirstValue() > maxThirst)
					thirst = maxThirst;
				if(thirst + item.getThirstValue() < 0)
					thirst = 0;
				else
					thirst += item.getThirstValue();
			}
		}
	}
	
	public void move() {
		if(!checkEntityCollisions(xMove, 0f))
			moveX();
		if(!checkEntityCollisions(0f, yMove))
			moveY();
	}
	
	public void moveX() {
		if(xMove > 0) {//Left
			int tx = (int) (x + xMove + bounds.x + bounds.width) / Tile.getTileWidth();
			
			if(!collisionWithTile(tx, (int) (y + bounds.y) / Tile.getTileHeight()) && 
					!collisionWithTile(tx, (int) (y + bounds.y + bounds.height) / Tile.getTileHeight())) {
				x += xMove;
			}else {
				x = tx * Tile.getTileWidth() - bounds.x - bounds.width - 1;
			}
			
		}else if(xMove < 0) {//Rightaa
			int tx = (int) (x + xMove + bounds.x) / Tile.getTileWidth();
			
			if(!collisionWithTile(tx, (int) (y + bounds.y) / Tile.getTileHeight()) && 
					!collisionWithTile(tx, (int) (y + bounds.y + bounds.height) / Tile.getTileHeight())) {
				x += xMove;
			}else {
				x = tx * Tile.getTileWidth() + Tile.getTileWidth() - bounds.x;
			}
		}
	}
	
	public void moveY() {
		if(yMove < 0) {//Up
			int ty = (int) (y + yMove + bounds.y) / Tile.getTileHeight();
			
			if(!collisionWithTile((int) (x + bounds.x) / Tile.getTileWidth(), ty) &&
					!collisionWithTile((int) (x + bounds.x + bounds.width) / Tile.getTileWidth(), ty)) {
				y += yMove;
			}else {
				y = ty * Tile.getTileHeight() + Tile.getTileHeight() - bounds.y;
			}
			
		}else if(yMove > 0) {//Down
			int ty = (int) (y + yMove + bounds.y + bounds.height) / Tile.getTileHeight();
			
			if(!collisionWithTile((int) (x + bounds.x) / Tile.getTileWidth(), ty) &&
					!collisionWithTile((int) (x + bounds.x + bounds.width) / Tile.getTileWidth(), ty)) {
				y += yMove;
			}else {
				y = ty *Tile.getTileHeight() - bounds.y - bounds.height - 1;
			}
		}
	}
	
	protected boolean collisionWithTile(int x, int y) {
		return handler.getWorld().getTile(x, y).isSolid();
	}
	
	// getters and setters
	
	public float getxMove() {
		return xMove;
	}

	public void setxMove(float xMove) {
		this.xMove = xMove;
	}

	public float getyMove() {
		return yMove;
	}

	public void setyMove(float yMove) {
		this.yMove = yMove;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}
	
	public int getHunger() {
		return hunger;
	}

	public void setHunger(int hunger) {
		this.hunger = hunger;
	}
	
	public int getMaxHunger() {
		return maxHunger;
	}

	public int getThirst() {
		return thirst;
	}

	public void setThirst(int thirst) {
		this.thirst = thirst;
	}
	
	public int getMaxThirst() {
		return maxThirst;
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}
}
