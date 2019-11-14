package dev.moltencube.mygame.ui;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import dev.moltencube.mygame.Handler;

public class UIManager {
	
	private Handler handler;
	private ArrayList<UIObject> objects;
	
	public UIManager(Handler handler) {
		this.handler = handler;
		objects = new ArrayList<UIObject>();
	}
	
	public void tick() {
		for(UIObject o : objects) {
			o.tick();
		}
	}
	
	public void render(Graphics g) {
		for(UIObject o : objects) {
			o.render(g);
		}
	}
	
	public void onMouseMove(MouseEvent e) {
		for(UIObject o : objects) {
			o.onMouseMove(e);
		}
	}
	
	public void onMouseClicked(MouseEvent e) {
		for(UIObject o : objects) {
			o.onMouseClicked(e);
		}
	}
	
	public void addObject(UIObject o) {
		o.setId(objects.size());
		objects.add(o);
	}
	
	public void removeObject(int id) {
		objects.remove(id);
	}

	public Handler getHandler() {
		return handler;
	}

	public void setHandler(Handler handler) {
		this.handler = handler;
	}

	public ArrayList<UIObject> getObjects() {
		return objects;
	}

	public void setObjects(ArrayList<UIObject> objects) {
		this.objects = objects;
	}
}
