package dev.moltencube.mygame.entities.creatures;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

import dev.moltencube.mygame.Handler;
import dev.moltencube.mygame.gfx.Animation;
import dev.moltencube.mygame.gfx.Assets;
import dev.moltencube.mygame.items.Item;

public class Frizard extends Creature {
	
	// animations
	private final int ANIM_SPEED = 333;
	private Animation animLeft, animRight, animUp, animDown, animIdle;
	
	public Frizard(Handler handler, int x, int y) {
		super(handler, x, y, 32, 32);
		
		this.id = 1;
		
		this.xMove = 1;
		this.health = 4;
		
		bounds.x = 4;
		bounds.y = 24;
		bounds.width = 24;
		bounds.height = 8;
		
		// animations
		animLeft = new Animation(ANIM_SPEED, Assets.frizard_left);
		animRight = new Animation(ANIM_SPEED, Assets.frizard_right);
		animUp = new Animation(ANIM_SPEED, Assets.frizard_up);
		animDown = new Animation(ANIM_SPEED, Assets.frizard_down);
		animIdle = new Animation(ANIM_SPEED, Assets.frizard_idle);
	}

	@Override
	public void tick() {
		// tick animations
		animLeft.tick();
		animRight.tick();
		animUp.tick();
		animDown.tick();
		
		if(x > 500)
			xMove = -1;
		if(x < 100)
			xMove = 1;
		
		move();
		
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(getCurrentAnimationFrame(), (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height, null);
	}
	
	private BufferedImage getCurrentAnimationFrame() {
		if(xMove < 0) {
			return animLeft.getCurrentFrame();
		}else if(xMove > 0) {
			return animRight.getCurrentFrame();
		}else if(yMove < 0) {
			return animUp.getCurrentFrame();
		}else if(yMove > 0){
			return animDown.getCurrentFrame();
		}else {
			return animIdle.getCurrentFrame();
		}
	}

	@Override
	public void die() {
		Random random = new Random();
		handler.getWorld().getItemManager().addItem(Item.boneItem.createNew((int) x, (int) y, random.nextInt(3) + 1));
	}

}
