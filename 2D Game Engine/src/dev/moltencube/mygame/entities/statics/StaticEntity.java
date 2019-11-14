package dev.moltencube.mygame.entities.statics;

import java.awt.Rectangle;

import dev.moltencube.mygame.Handler;
import dev.moltencube.mygame.entities.Entity;
import dev.moltencube.mygame.entities.types.EntityType;

public abstract class StaticEntity extends Entity {
	
	public StaticEntity(Handler handler, float x, float y, int width, int height) {
		super(handler, x, y, width, height);
		
		this.entityType = EntityType.STATIC;
	}
	
	public void tick() {
		checkHovering();
	}
	
	private void checkHovering() {
		Rectangle temp = new Rectangle((int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height);
		if(temp.contains(handler.getMouseManager().getMouseX(), handler.getMouseManager().getMouseY())) {
			hovering = true;
		}else {
			hovering = false;
		}
	}
}
