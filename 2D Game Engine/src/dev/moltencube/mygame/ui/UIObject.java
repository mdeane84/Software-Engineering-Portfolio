package dev.moltencube.mygame.ui;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

import dev.moltencube.mygame.Handler;

public abstract class UIObject {
	
	protected Handler handler;
	protected float x, y;
	protected int width, height;
	protected Rectangle bounds;
	protected boolean hovering = false;
	protected boolean active = true;
	
	protected int id;
	
	public UIObject(Handler handler, float x, float y, int width, int height) {
		this.handler = handler;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		bounds = new Rectangle((int) x, (int) y, width, height);
	}
	
	public abstract void tick();
	
	public abstract void render(Graphics g);
	
	public abstract void onClick();
	
	public void onMouseMove(MouseEvent e) {
		if(bounds.contains(e.getX(), e.getY()))
			hovering = true;
		else
			hovering = false;
	}
	
	public void onMouseClicked(MouseEvent e) {
		if(hovering && active) {
			onClick();
		}
	}
	
	// getters and setters
	
	public void setPosition(float x, float y) {
		this.x = x;
		this.y = y;
		bounds.x = (int) x;
		bounds.y = (int) y;
	}
	
	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
		bounds.x = (int) x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
		bounds.y = (int) y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public boolean isHovering() {
		return hovering;
	}

	public void setHovering(boolean hovering) {
		this.hovering = hovering;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
}
