package dev.moltencube.mygame.ui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import dev.moltencube.mygame.Handler;

public class UIImageButton extends UIObject {
	
	private BufferedImage[] images;
	private ClickListener clicker;
	
	public UIImageButton(Handler handler, float x, float y, int width, int height, BufferedImage[] images, ClickListener clicker) {
		super(handler, x, y, width, height);
		this.images = images;
		this.clicker = clicker;
	}
	public UIImageButton(Handler handler, float x, float y, int width, int height, BufferedImage image, ClickListener clicker) {
		super(handler, x, y, width, height);
		images = new BufferedImage[] {image, image};
		this.clicker = clicker;
	}
	public UIImageButton(Handler handler, float x, float y, BufferedImage image, ClickListener clicker) {
		super(handler, x, y, image.getWidth(), image.getHeight());
		images = new BufferedImage[] {image, image};
		this.clicker = clicker;
		width = image.getWidth();
		height = image.getHeight();
	}

	@Override
	public void tick() {
		
	}

	@Override
	public void render(Graphics g) {
		if(active) {
			if(hovering)
				g.drawImage(images[1], (int) x, (int) y, width, height, null);
			else
				g.drawImage(images[0], (int) x, (int) y, width, height, null);
		}
	}

	@Override
	public void onClick() {
		clicker.onClick();
	}
}
