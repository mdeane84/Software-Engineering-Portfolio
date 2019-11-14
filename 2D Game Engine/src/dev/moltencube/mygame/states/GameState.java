package dev.moltencube.mygame.states;

import java.awt.Graphics;
import java.awt.event.KeyEvent;

import dev.moltencube.mygame.Handler;
import dev.moltencube.mygame.inventory.InventoryManager;
import dev.moltencube.mygame.items.Item;
import dev.moltencube.mygame.ui.UIManager;
import dev.moltencube.mygame.worlds.World;

public class GameState extends State {
	
	private World world;
	private UIManager uiManager;
	private InventoryManager inventoryManager;
	
	public GameState(Handler handler) {
		super(handler);
		
		uiManager = new UIManager(handler);
		world = new World(handler, "res/worlds/world2.txt");
		handler.setWorld(world);
		
	}

	@Override
	public void tick() {
		world.tick();
		uiManager.tick();
		
		// Print all items in the world to the console
		if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_U)) {
			for(Item i : handler.getWorld().getItemManager().getItems()) {
				System.out.println(i.getName());
			}
		}
		
		// set game to full screen
		setScreen();
	}

	@Override
	public void render(Graphics g) {
		world.render(g);
		renderHUD(g);
		uiManager.render(g);
	}
	
	@Override
	public UIManager getUIManager() {
		return uiManager;
	}
	
	public InventoryManager getInventoryManager() {
		return inventoryManager;
	}
	
	private void renderHUD(Graphics g) {
		handler.getWorld().getEntityManager().getPlayer().getHotbarInv().render(g);
		handler.getWorld().getEntityManager().getPlayer().getEquipInv().render(g);
		handler.getWorld().getEntityManager().getPlayer().getMouseInv().render(g);
		handler.getWorld().getEntityManager().getPlayer().getCrafting().render(g);
	}
}
