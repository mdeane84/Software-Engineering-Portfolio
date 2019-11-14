package dev.moltencube.mygame.input;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import dev.moltencube.mygame.Handler;
import dev.moltencube.mygame.crafting.Crafting;
import dev.moltencube.mygame.entities.creatures.Player;
import dev.moltencube.mygame.inventory.InventoryManager;
import dev.moltencube.mygame.ui.UIManager;

public class MouseManager implements MouseListener, MouseWheelListener, MouseMotionListener {
	
	private boolean leftPressed, rightPressed;
	private int mouseX, mouseY;
	private UIManager uiManager;
	private InventoryManager inventoryManager;
	private Crafting crafting;
	
	public MouseManager() {
		mouseX = 0;
		mouseY = 0;
	}
	
	public void setUIManager(UIManager uiManager) {
		this.uiManager = uiManager;
	}
	
	public void setInventoryManager(InventoryManager inventoryManager) {
		this.inventoryManager = inventoryManager;
	}
	
	public void setCrafting(Crafting crafting) {
		this.crafting = crafting;
	}
	
	public boolean clickingUp(Handler handler) {
		Player player = handler.getWorld().getEntityManager().getPlayer();
		int xOff = (int) handler.getGameCamera().getxOffset();
		int yOff = (int) handler.getGameCamera().getyOffset();
		float playerX = (player.getX() + player.getWidth()/2 - xOff);
		float playerY = (player.getY() + player.getHeight()/2 - yOff);
		
		if(mouseX - playerX > mouseY - playerY && mouseY - playerY < -1 * (mouseX - playerX))
			return true;
		return false;
	}
	
	public boolean clickingDown(Handler handler) {
		Player player = handler.getWorld().getEntityManager().getPlayer();
		int xOff = (int) handler.getGameCamera().getxOffset();
		int yOff = (int) handler.getGameCamera().getyOffset();
		float playerX = (player.getX() + player.getWidth()/2 - xOff);
		float playerY = (player.getY() + player.getHeight()/2 - yOff);
		
		if(mouseX - playerX < mouseY - playerY && mouseY - playerY > -1 * (mouseX - playerX))
			return true;
		return false;
	}
	
	public boolean clickingLeft(Handler handler) {
		Player player = handler.getWorld().getEntityManager().getPlayer();
		int xOff = (int) handler.getGameCamera().getxOffset();
		int yOff = (int) handler.getGameCamera().getyOffset();
		float playerX = (player.getX() + player.getWidth()/2 - xOff);
		float playerY = (player.getY() + player.getHeight()/2 - yOff);
		
		if(mouseX - playerX < mouseY - playerY && mouseY - playerY < -1 * (mouseX - playerX))
			return true;
		return false;
	}
	
	public boolean clickingRight(Handler handler) {
		Player player = handler.getWorld().getEntityManager().getPlayer();
		int xOff = (int) handler.getGameCamera().getxOffset();
		int yOff = (int) handler.getGameCamera().getyOffset();
		float playerX = (player.getX() + player.getWidth()/2 - xOff);
		float playerY = (player.getY() + player.getHeight()/2 - yOff);
		
		if(mouseX - playerX > mouseY - playerY && mouseY - playerY > -1 * (mouseX - playerX))
			return true;
		return false;
	}
	
	// getters
	
	public boolean isLeftPressed() {
		return leftPressed;
	}
	
	public boolean isRightPressed() {
		return rightPressed;
	}
	
	public int getMouseX() {
		return mouseX;
	}
	
	public int getMouseY() {
		return mouseY;
	}
	
	// implemented methods
	
	@Override
	public void mouseDragged(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
		if(e.getButton() == MouseEvent.BUTTON1)
			leftPressed = true;
		else if(e.getButton() == MouseEvent.BUTTON3)
			rightPressed = true;
		
		if(inventoryManager != null)
			inventoryManager.onMouseDragged(e);
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
		
		if(uiManager != null)
			uiManager.onMouseMove(e);
		
		if(inventoryManager != null)
			inventoryManager.onMouseMove(e);
		
		if(crafting != null)
			crafting.onMouseMove(e);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
		if(uiManager != null) {
			uiManager.onMouseClicked(e);
		}
		
		if(inventoryManager != null)
			inventoryManager.onMouseClicked(e);
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1)
			leftPressed = true;
		else if(e.getButton() == MouseEvent.BUTTON3)
			rightPressed = true;
		
		inventoryManager.onMousePress(e);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1)
			leftPressed = false;
		else if(e.getButton() == MouseEvent.BUTTON3)
			rightPressed = false;
		
		if(inventoryManager != null)
			inventoryManager.onMouseRelease(e);
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		if(crafting != null)
			crafting.onMouseWheelMoved(e);
	}
}
