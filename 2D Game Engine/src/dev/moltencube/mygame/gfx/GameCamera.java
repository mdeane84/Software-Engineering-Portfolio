package dev.moltencube.mygame.gfx;

import dev.moltencube.mygame.Handler;
import dev.moltencube.mygame.entities.Entity;
import dev.moltencube.mygame.tiles.Tile;

public class GameCamera {
	
	private Handler handler;
	private float xOffset, yOffset;
	
	public GameCamera(Handler handler, float xOffset, float yOffset) {
		this.handler = handler;
		this.xOffset = xOffset;
		this.yOffset = yOffset;
	}
	
	public void checkBlankSpace() {
		int xShift = 0;
		int yShift = 0;
		
		if(xOffset < 0) {
			xOffset = 0;
		}else if(xOffset > handler.getWorld().getWidth() * (Tile.getTileWidth() + xShift) - handler.getWidth()) {
			xOffset = handler.getWorld().getWidth() * (Tile.getTileWidth() + xShift) - handler.getWidth();
		}
		
		if(yOffset < 0) {
			yOffset = 0;
		}else if(yOffset > handler.getWorld().getHeight() * (Tile.getTileHeight() + yShift) - handler.getHeight()) {
			yOffset = handler.getWorld().getHeight() * (Tile.getTileHeight() + yShift) - handler.getHeight();
		}
	}
	
	public void centerOnEntity(Entity e) {
		xOffset = e.getX() - handler.getWidth() / 2 + e.getWidth() / 2;
		yOffset = e.getY() - handler.getHeight() /2 + e.getHeight() / 2;
		checkBlankSpace();
	}
	
	public void move(float xAmt, float yAmt) {
		xOffset += xAmt;
		yOffset += yAmt;
		checkBlankSpace();
	}

	public float getxOffset() {
		return xOffset;
	}

	public void setxOffset(float xOffset) {
		this.xOffset = xOffset;
	}

	public float getyOffset() {
		return yOffset;
	}

	public void setyOffset(float yOffset) {
		this.yOffset = yOffset;
	}
}
