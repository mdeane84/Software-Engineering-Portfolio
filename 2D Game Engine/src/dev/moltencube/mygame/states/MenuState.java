package dev.moltencube.mygame.states;

import java.awt.Color;
import java.awt.Graphics;

import dev.moltencube.mygame.Handler;
import dev.moltencube.mygame.gfx.Assets;
import dev.moltencube.mygame.inventory.InventoryManager;
import dev.moltencube.mygame.ui.ClickListener;
import dev.moltencube.mygame.ui.UIImageButton;
import dev.moltencube.mygame.ui.UIManager;

public class MenuState extends State {
	
	private UIManager uiManager;
	
	public MenuState(Handler handler) {
		super(handler);
		uiManager = new UIManager(handler);
		
		uiManager.addObject(new UIImageButton(handler, 20, 20, 128, 64, Assets.btn_start, new ClickListener(){
			@Override
			public void onClick() {
				handler.getMouseManager().setUIManager(handler.getGame().gameState.getUIManager());
				State.setState(handler.getGame().gameState);
			}
		}));
	}

	@Override
	public void tick() {
		uiManager.tick();
		
		setScreen();
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.cyan);
		g.fillRect(0, 0, 2000, 2000);
		uiManager.render(g);
		g.drawImage(Assets.player_left[0], 200, 0, 256, 256, null);
	}
	
	@Override
	public UIManager getUIManager() {
		return uiManager;
	}

}
