package dev.moltencube.mygame.states;

import java.awt.Graphics;
import java.awt.event.KeyEvent;

import dev.moltencube.mygame.Handler;
import dev.moltencube.mygame.ui.UIManager;

public abstract class State {
	
	private static State currentState = null;
	
	public static void setState(State state) {
		currentState = state;
		
	}
	
	public static State getState() {
		return currentState;
	}
	
	// Class
	
	protected Handler handler;
	
	public State(Handler handler) {
		this.handler = handler;
	}
	
	protected void setScreen() {
		if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_F11)) {
			if(handler.getGame().isBorderless()) {
				handler.getGame().setScreen("windowed");
			}else {
				handler.getGame().setScreen("borderless");
			}
		}
	}
	
	public abstract void tick();
	
	public abstract void render(Graphics g);
	
	public abstract UIManager getUIManager();
}