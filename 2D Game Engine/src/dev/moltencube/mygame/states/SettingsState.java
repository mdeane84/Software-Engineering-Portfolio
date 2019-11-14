package dev.moltencube.mygame.states;

import java.awt.Graphics;

import dev.moltencube.mygame.Handler;
import dev.moltencube.mygame.ui.UIManager;

public class SettingsState extends State {
	
	UIManager uiManager;
	
	public SettingsState(Handler handler) {
		super(handler);
		
		uiManager = new UIManager(handler);
		handler.getGame().getMouseManager().setUIManager(uiManager);
	}

	@Override
	public void tick() {
		
	}

	@Override
	public void render(Graphics g) {
		
	}
	
	@Override
	public UIManager getUIManager() {
		return uiManager;
	}

}
