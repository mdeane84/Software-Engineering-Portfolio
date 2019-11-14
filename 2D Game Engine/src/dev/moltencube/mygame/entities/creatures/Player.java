package dev.moltencube.mygame.entities.creatures;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import dev.moltencube.mygame.Handler;
import dev.moltencube.mygame.crafting.Crafting;
import dev.moltencube.mygame.entities.Entity;
import dev.moltencube.mygame.gfx.Animation;
import dev.moltencube.mygame.gfx.Assets;
import dev.moltencube.mygame.inventory.Inventory;
import dev.moltencube.mygame.inventory.InventoryEquip;
import dev.moltencube.mygame.inventory.InventoryHotbar;
import dev.moltencube.mygame.inventory.InventoryMouse;
import dev.moltencube.mygame.items.Item;
import dev.moltencube.mygame.items.types.EquipType;

public class Player extends Creature {
	
	// animations
	private final int ANIM_SPEED = 180;
	private Animation animLeft, animRight, animUp, animDown, animIdle;
	
	// attack
	private int arSize = 40;
	private long lastAttackTimer, attackCooldown = 600, attackTimer = attackCooldown;
	private Rectangle aBounds = new Rectangle();
	
	private InventoryHotbar invHotbar;
	private InventoryEquip invEquip;
	private InventoryMouse invMouse;
	private Crafting crafting;
	
	private boolean tempShowAttack = false;
	private boolean showStorage = true;
	
	public Player(Handler handler, float x, float y) {
		super(handler, x, y, Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT);
		
		this.id = 0;
		
		bounds.x = 24;
		bounds.y = 48;
		bounds.width = 16;
		bounds.height = 15;
		
		aBounds.width = arSize;
		aBounds.height = arSize;
		
		// animations
		animLeft = new Animation(ANIM_SPEED, Assets.player_left);
		animRight = new Animation(ANIM_SPEED, Assets.player_right);
		animUp = new Animation(ANIM_SPEED, Assets.player_up);
		animDown = new Animation(ANIM_SPEED, Assets.player_down);
		animIdle = new Animation(ANIM_SPEED, Assets.player_idle);
		
		// inventories
		invHotbar = new InventoryHotbar(handler);
		invEquip = new InventoryEquip(handler);
		invMouse = new InventoryMouse(handler);
		crafting = new Crafting(handler);
	}

	@Override
	public void tick() {
		// animations
		animLeft.tick();
		animRight.tick();
		animUp.tick();
		animDown.tick();
		
		// movement
		getInput();
		move();
		handler.getGameCamera().centerOnEntity(this);
		
		// attack
		if(tempShowAttack)
			tempShowAttack = false;
		checkAttacks();
		
		//inventory
		invHotbar.tick();
		invEquip.tick();
		invMouse.tick();
		
		// crafting
		crafting.tick();
	}
	
	private void checkAttacks() {
		// attack timer
		attackTimer += System.currentTimeMillis() - lastAttackTimer;
		lastAttackTimer = System.currentTimeMillis();
		if(attackTimer < attackCooldown)
			return;
		
		// attack
		Rectangle cb = getCollisionBounds(0, 0);
		
		if(handler.getMouseManager().isLeftPressed()) {
			if(handler.getMouseManager().clickingUp(handler)) {
				aBounds.x = cb.x + cb.width / 2 - arSize / 2;
				aBounds.y = cb.y - arSize;
			}else if(handler.getMouseManager().clickingDown(handler)) {
				aBounds.x = cb.x + cb.width / 2 - arSize / 2;
				aBounds.y = cb.y + cb.height;
			}else if(handler.getMouseManager().clickingLeft(handler)) {
				aBounds.x = cb.x - arSize;
				aBounds.y = cb.y + cb.height / 2 - arSize;
			}else if(handler.getMouseManager().clickingRight(handler)) {
				aBounds.x = cb.x + cb.width;
				aBounds.y = cb.y + cb.height / 2 - arSize;
			}
		}else {
			return;
		}
		
		tempShowAttack = true;
		attackTimer = 0;
		
		for(Entity e : handler.getWorld().getEntityManager().getEntities()) {
			if(e.equals(this)) {
				continue;
			}
			
			if(e.getCollisionBounds(0f, 0f).intersects(aBounds)) {
				if(getTool() != null) {
					e.hurt(getTool().getDamage());
					return;
				}else {
					e.hurt(1);
				}
			}
		}
	}
	
	public void die() {
		System.out.println("Player died");
	}
	
	private void getInput() {
		xMove = 0;
		yMove = 0;
		
		if(handler.getKeyManager().up) {
			yMove = -speed;
		}
		if(handler.getKeyManager().down) {
			yMove = speed;
		}
		if(handler.getKeyManager().left) {
			xMove = -speed;
		}
		if(handler.getKeyManager().right) {
			xMove = speed;
		}
		
		// toggle inventory
		if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_E)) {
			if(invEquip.getInventoryItems()[1] != null) {
				invEquip.getInventoryItems()[1].getStorageInv().setActive(!invEquip.getInventoryItems()[1].getStorageInv().isActive());
			}
		}
		
		if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_NUMPAD1)) {
			health-=1;
			System.out.println(health);
		}
		if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_NUMPAD2)) {
			hunger-=5;
			System.out.println(hunger);
		}
		if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_NUMPAD3)) {
			thirst-=5;
			System.out.println(thirst);
		}
		if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_NUMPAD0)) {
			health = maxHealth;
			hunger = maxHunger;
			thirst = maxThirst;
		}
		
		// toggle crafting
		if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_C)) {
			crafting.setActive(!crafting.isActive());
			
			if(crafting.isActive()) {
				// set buttons as active
				for(Integer i : crafting.getButtons())
					handler.getGame().gameState.getUIManager().getObjects().get(i).setActive(true);;
			}else {
				// set buttons as not active
				for(Integer i : crafting.getButtons())
					handler.getGame().gameState.getUIManager().getObjects().get(i).setActive(false);
			}
		}
		
		// TEMP
		if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_1)) {
			invHotbar.addItem(Item.boneKnifeItem.createNew(1));
			invHotbar.addItem(Item.stoneAxeItem.createNew(1));
		}
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(getCurrentAnimationFrame(), (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height, null);
		if(tempShowAttack)
			g.fillRect((int) (aBounds.x - handler.getGameCamera().getxOffset()), (int) (aBounds.y - handler.getGameCamera().getyOffset()), aBounds.width, aBounds.height);
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
	
	public void pickup(Item item) {
		if(invHotbar.hasRoomFor(item)) {
			item.setNameVisible(true);
			item.setCountVisible(true);
			item.setToBeRemoved(true);
			item.setPickedUp(true);
			invHotbar.addItem(item);
		}else if(invEquip.getInventoryItems()[0] == null && item.getEquipType() == EquipType.TOOL) {
			System.out.println("Tool Null!");
			item.setNameVisible(true);
			item.setCountVisible(true);
			item.setToBeRemoved(true);
			item.setPickedUp(true);
			invEquip.getInventoryItems()[0] = item;
		}else if(invEquip.getInventoryItems()[1] == null && item.getEquipType() == EquipType.STORAGE) {
			System.out.println("Storage Null");
			item.setNameVisible(true);
			item.setCountVisible(true);
			item.setToBeRemoved(true);
			item.setPickedUp(true);
			invEquip.getInventoryItems()[1] = item;
		}else if(invEquip.getInventoryItems()[1] != null && invEquip.getInventoryItems()[1].getStorageInv().hasRoomFor(item)) {
			item.setNameVisible(true);
			item.setCountVisible(true);
			item.setToBeRemoved(true);
			item.setPickedUp(true);
			invEquip.getInventoryItems()[1].getStorageInv().addItem(item);
		}
	}
	
	// getters and setters

	public Inventory getHotbarInv() {
		return invHotbar;
	}
	
	public Inventory getEquipInv() {
		return invEquip;
	}
	
	public Inventory getMouseInv() {
		return invMouse;
	}

	public Crafting getCrafting() {
		return crafting;
	}

	public void setCrafting(Crafting crafting) {
		this.crafting = crafting;
	}
	
	public Item getTool() {
		return invEquip.getInventoryItems()[0];
	}

}
