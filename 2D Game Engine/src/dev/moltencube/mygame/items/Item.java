package dev.moltencube.mygame.items;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import dev.moltencube.mygame.Handler;
import dev.moltencube.mygame.entities.creatures.Player;
import dev.moltencube.mygame.gfx.Assets;
import dev.moltencube.mygame.gfx.Text;
import dev.moltencube.mygame.inventory.Inventory;
import dev.moltencube.mygame.items.types.EquipType;
import dev.moltencube.mygame.items.types.ToolResType;
import dev.moltencube.mygame.items.types.ToolType;

public class Item {
	
	// Handler
	
	public static Item[] items = new Item[256];
	
	// Items
		// resources
	public static Item woodItem = new Item(Assets.wood, "Wood", 20, EquipType.NONE);
	public static Item rockItem = new Item(Assets.rock, "Rock", 20, EquipType.NONE);
	public static Item stickItem = new Item(Assets.stick, "Stick", 40, EquipType.NONE);
	public static Item boneItem = new Item(Assets.bone, "Bone", 20, EquipType.NONE);
	public static Item plantFiberItem = new Item(Assets.plantFiber, "Plant Fiber", 40, EquipType.NONE);
	public static Item plantFiberRopeItem = new Item(Assets.plantFiberRope, "Fiber Rope", 20, EquipType.NONE);
	public static Item treeBulbItem = new Item(Assets.treeBulb, "Tree Bulb", 40, EquipType.NONE);
		// storage
	public static Item bulbPouch = new StorageItem(Assets.bulbPouch, "Bulb Pouch", EquipType.STORAGE, 12, 4);
		// tools
			// bone
	public static Item boneKnifeItem = new ToolItem(Assets.boneKnife, "Bone Knife", 1, EquipType.TOOL, ToolResType.BONE, ToolType.KNIFE);
	public static Item bonePickaxeItem = new ToolItem(Assets.bonePickaxe, "Bone Pickaxe", 1, EquipType.TOOL, ToolResType.BONE, ToolType.PICKAXE);
			// stone
	public static Item stoneKnifeItem = new ToolItem(Assets.stoneKnife, "Stone Knife", 1, EquipType.TOOL, ToolResType.STONE, ToolType.KNIFE);
	public static Item stonePickaxeItem = new ToolItem(Assets.stonePickaxe, "Stone Pickaxe", 1, EquipType.TOOL, ToolResType.STONE, ToolType.PICKAXE);
	public static Item stoneAxeItem = new ToolItem(Assets.stoneAxe, "Stone Axe", 1, EquipType.TOOL, ToolResType.STONE, ToolType.AXE);
	public static Item stoneShovelItem = new ToolItem(Assets.stoneShovel, "Stone Shovel", 1, EquipType.TOOL, ToolResType.STONE, ToolType.SHOVEL);
	
	
	// Class
	
	public static final int ITEMWIDTH = 32, ITEMHEIGHT = 32;
	
	protected Handler handler;
	protected BufferedImage texture;
	protected Inventory currentInventory;
	protected String name;
	protected EquipType equipType;
	protected ToolType toolType;
	protected ToolResType resourceType;
	protected int id;
	
	protected Rectangle bounds;
	
	protected int x, y, count;
	protected int maxCount;
	protected int inventoryIndex = -10, previousIndex = -10;
	protected int hungerValue, thirstValue;
	
	// tool variables
	protected int strength, durability, efficiency = 5, damage = 2;
	
	protected boolean pickedUp = false;
	protected boolean combined = false;
	protected boolean toBeRemoved = false;
	protected boolean edible = false;
	protected boolean hovering = false;
	protected boolean clickable = true;
	protected boolean followingMouse = false;
	protected boolean showCount = false, showName = false;
	
	// storage item variables
	protected Inventory storageInv = null;
	protected int storageSpace=0, rowCount;
	
	public Item(BufferedImage texture, String name, int maxCount, EquipType equipType) {						// Used to add a new static item to the game
		this.texture = texture;
		this.name = name;
		this.maxCount = maxCount;
		this.equipType = equipType;
		
		boolean addItem = false;
		for(int i = 0; i < items.length; i++) {
			if(items[i] == null) {
				id = i;
				addItem = true;
				break;
			}
		}
		
		count = 1;
		hungerValue = 0;
		thirstValue = 0;
		
		bounds = new Rectangle(x, y, ITEMWIDTH, ITEMHEIGHT);
		if(addItem)
			items[id] = this;
	}
	
	public Item(BufferedImage texture, String name, int count, int id) {										// Used to create a new instance of an item
		this(texture, name, count, 0, 0, id);

	}
	public Item(BufferedImage texture, String name, int count, int hungerValue, int thirstValue, int id) {		// Used to create a new instance of an item
		this.texture = texture;
		this.name = name;
		this.id = id;
		this.count = count;
		this.hungerValue = hungerValue;
		this.thirstValue = thirstValue;
		
		bounds = new Rectangle(x, y, ITEMWIDTH, ITEMHEIGHT);
		
		items[id] = this;
	}
	
	public void tick() {
		clickable = true;
		
		if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_SPACE))
			checkPickup();
		
		checkCombine();
		
		if(this.storageInv != null) {
			this.storageInv.tick();
		}
	}
	
	private void checkPickup() {
		Player player = handler.getWorld().getEntityManager().getPlayer();
		if(!pickedUp && player.getCollisionBounds(0f, 0f).intersects(bounds)) {
			pickup();
		}
	}
	
	private void pickup() {
		handler.getWorld().getEntityManager().getPlayer().pickup(this);
	}
	
	private void checkCombine() {
		if(!pickedUp) {
			for(Item i : handler.getWorld().getItemManager().getItems()) {
				if(bounds.intersects(i.getBounds()) && !i.equals(this)) {
					if(i.getId() == this.getId() && i.getStorageInv() == null && this.getStorageInv() == null) {
						i.setCount(this.count + i.getCount());
						this.setCombined(true);
						this.setToBeRemoved(true);
					}
				}
			}
		}
	}
	
	public void render(Graphics g) {
		if(handler == null)
			return;
		render(g, (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()));
	}
	
	public void render(Graphics g, int x, int y) {
		render(g, x, y, ITEMWIDTH, ITEMHEIGHT);
	}
	
	public void render(Graphics g, int x, int y, int width, int height) {
		g.drawImage(texture, x, y, width, height, null);				// draw item
		// draw count
		if(showCount) {
			Text.drawString(g, Integer.toString(count), x + 32, y + 32, false, true, Color.WHITE, Assets.font12);
		}
		renderInv(g);
	}
	
	public void renderInv(Graphics g) {
		if(this.storageInv != null) {
			if(this.currentInventory == handler.getWorld().getEntityManager().getPlayer().getEquipInv() && inventoryIndex == 1) {
				this.storageInv.render(g);
			} else {
				this.storageInv.setActive(false);
			}
		}
	}
	
	public void drawShadow(Graphics g) {
		g.drawImage(Assets.itemShadow, (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset() + 4), ITEMWIDTH, ITEMHEIGHT, null);	// draw shadow
	}
	
	public Item copy() {
		Item temp = this.createNew(this.x, this.y, this.count);
		temp.setMaxCount(this.maxCount);
		temp.setEquipType(this.equipType);
		temp.setToolType(this.toolType);
		temp.setResourceType(this.resourceType);
		temp.setInventoryIndex(this.inventoryIndex);
		temp.setPickedUp(this.pickedUp);
		temp.setCountVisible(this.showCount);
		temp.setNameVisible(this.showName);
		temp.setCombined(this.combined);
		temp.setToBeRemoved(this.toBeRemoved);
		return temp;
	}
	
	public Item createNew(int x, int y) {
		return createNew(x, y, 1);
	}
	
	public Item createNew(int count) {
		return createNew(0, 0, count);
	}
	
	public Item createNew(int x, int y, int count) {
		Item i = new Item(texture, name, count, id);
		
		if(handler != null)
			i.setHandler(handler);
		i.setPosition(x, y);
		i.setMaxCount(maxCount);
		i.setEquipType(equipType);
		i.setToolType(toolType);
		i.setResourceType(resourceType);
		i.setInventoryIndex(inventoryIndex);
		
		i.setPickedUp(pickedUp);
		i.setCountVisible(showCount);
		i.setNameVisible(showName);
		i.setCombined(combined);
		i.setToBeRemoved(toBeRemoved);
		
		return i;
	}
	
	public Item createNewWithInventory(Inventory inv) {
		return createNewWithInventory(0, 0, inv);
	}
	
	public Item createNewWithInventory(int x, int y, Inventory inv) {
		return createNewWithInventory(x, y, 1, inv);
	}
	
	public Item createNewWithInventory(int x, int y, int count, Inventory inv) {
		Item i = this.createNew(x, y, count);
		i.setStorageInv(inv);
		
		return i;
	}
	
	public boolean canCombineWith(Item item) {
		if(this.getId() == item.getId() && this.getCount() + item.getCount() <= this.getMaxCount())
			return true;
		return false;
	}
	
	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
		bounds.x = x;
		bounds.y = y;
	}

	public Handler getHandler() {
		return handler;
	}

	public void setHandler(Handler handler) {
		this.handler = handler;
	}

	public boolean isPickedUp() {
		return pickedUp;
	}

	public void setPickedUp(boolean pickedUp) {
		this.pickedUp = pickedUp;
	}

	public boolean isCombined() {
		return combined;
	}
	
	public void setCombined(boolean combined) {
		this.combined = combined;
	}
	
	public boolean isToBeRemoved() {
		return toBeRemoved;
	}
	
	public void setToBeRemoved(boolean toBeRemoved) {
		this.toBeRemoved = toBeRemoved;
	}
	
	public boolean isEdible() {
		return edible;
	}
	
	public void setEdible(boolean edible) {
		this.edible = edible;
	}
	
	public BufferedImage getTexture() {
		return texture;
	}

	public void setTexture(BufferedImage texture) {
		this.texture = texture;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public EquipType getEquipType() {
		return equipType;
	}
	
	public void setEquipType(EquipType equipType) {
		this.equipType = equipType;
	}
	
	public ToolType getToolType() {
		return toolType;
	}

	public void setToolType(ToolType toolType) {
		this.toolType = toolType;
	}

	public ToolResType getResourceType() {
		return resourceType;
	}

	public void setResourceType(ToolResType resourceType) {
		this.resourceType = resourceType;
	}

	public int getStrength() {
		return strength;
	}

	public void setStrength(int strength) {
		this.strength = strength;
	}

	public int getDurability() {
		return durability;
	}

	public void setDurability(int durability) {
		this.durability = durability;
	}

	public int getEfficiency() {
		return efficiency;
	}

	public void setEfficiency(int efficiency) {
		this.efficiency = efficiency;
	}
	
	public int getDamage() {
		return damage;
	}
	
	public void setDamage(int damage) {
		this.damage = damage;
	}
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
		bounds.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
		bounds.y = y;
	}

	public Rectangle getBounds() {
		return bounds;
	}
	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getMaxCount() {
		return maxCount;
	}
	public void setMaxCount(int maxCount) {
		this.maxCount = maxCount;
	}
	public int getInventoryIndex() {
		return inventoryIndex;
	}
	
	public void setInventoryIndex(int inventoryIndex) {
		this.inventoryIndex = inventoryIndex;
	}
	
	public int getPreviousIndex() {
		return previousIndex;
	}
	
	public void setPreviousIndex(int previousIndex) {
		this.previousIndex = previousIndex;
	}
	
	public int getHungerValue() {
		return hungerValue;
	}
	
	public void setHungerValue(int hungerValue) {
		this.hungerValue = hungerValue;
	}
	
	public int getThirstValue() {
		return thirstValue;
	}
	
	public void setThirstValue(int thirstValue) {
		this.thirstValue = thirstValue;
	}
	
	public boolean isFollowingMouse() {
		return followingMouse;
	}
	
	public void setFollowingMouse(boolean followingMouse) {
		this.followingMouse = followingMouse;
		if(followingMouse) {
			if(this.getInventoryIndex() != -1)
				this.setPreviousIndex(this.getInventoryIndex());
			this.setInventoryIndex(-1);
		}else {
			this.setInventoryIndex(this.getPreviousIndex());
		}
	}
	
	public boolean isCountVisible() {
		return showCount;
	}
	
	public void setCountVisible(boolean showCount) {
		this.showCount = showCount;
	}
	
	public boolean isNameVisible() {
		return showName;
	}
	
	public void setNameVisible(boolean showName) {
		this.showName = showName;
	}
	
	public boolean isHovering() {
		return hovering;
	}
	
	public Inventory getStorageInv() {
		return storageInv;
	}
	
	public void setStorageInv(Inventory storageInv) {
		this.storageInv = storageInv;
	}
	
	public int getStorageSpace() {
		return storageSpace;
	}
	
	public void setStorageSpace(int storageSpace) {
		this.storageSpace = storageSpace;
	}
	
	public void setHovering(boolean hovering) {
		this.hovering = hovering;
	}
	
	public int getId() {
		return id;
	}
	
	public void setInventory(Inventory inv) {
		this.currentInventory = inv;
	}
	
	public Inventory getInventory() {
		return currentInventory;
	}
	
	public void setCurrentInventory(Inventory inv) {
		this.currentInventory = inv;
	}
	
	public int getStorageWidth() {
		return this.rowCount;
	}
	
	// mouse listener
	
	public void onMouseMove(MouseEvent e) {
		checkHovering(e);
	}
	
	public void onMouseDragged(MouseEvent e) {
		
	}
	
	public void onMousePress(MouseEvent e) {
		
	}
	
	public void onMouseRelease(MouseEvent e) {
		
	}
	
	public void onMouseClicked(MouseEvent e) {
		checkHovering(e);
	}
	
	private void checkHovering(MouseEvent e) {
		if(bounds.contains(e.getX(), e.getY())) {
			hovering = true;
		}else {
			hovering = false;
		}
	}
}
