package dev.moltencube.mygame.display;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class Display {
	
	private JFrame frame;
	private Canvas canvas;
	
	private String title;
	private int width, height, minWidth, minHeight, maxWidth, maxHeight;
	
	public Display(String title, int width, int height) {
		this.title = title;
		this.width = width;
		this.height = height;
		this.minWidth = width;
		this.minHeight = height;
		this.maxWidth = width;
		this.maxHeight = height;
		
		createDisplay();
	}
	
	private void createDisplay() {
		frame = new JFrame(title);
		frame.setSize(width, height);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		canvas = new Canvas();
		canvas.setPreferredSize(new Dimension(width, height));
		canvas.setMaximumSize(new Dimension(maxWidth, maxHeight));
		canvas.setMinimumSize(new Dimension(minWidth, minHeight));
		canvas.setFocusable(false);
		
		frame.add(canvas);
		frame.pack();
	}
	
	public void changeScreen(String state, GraphicsDevice device) {
		frame.dispose();
		
		if(state == "full") {
			if(device != null) {
				device.setFullScreenWindow(frame);
			}else {
				GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().setFullScreenWindow(frame);
			}
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setResizable(false);
			frame.setUndecorated(true);
			frame.setVisible(true);
		}else if(state == "borderless") {
			if(device != null) {
				device.setFullScreenWindow(null);
			}else {
				GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().setFullScreenWindow(null); 
			}
			frame.setSize(Toolkit.getDefaultToolkit().getScreenSize());
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setResizable(false);
			frame.setUndecorated(true);
			frame.setLocationRelativeTo(null);
			frame.setVisible(true);
		}else {
			if(device != null) {
				device.setFullScreenWindow(null);
			}else {
				GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().setFullScreenWindow(null); 
			}
			frame.setUndecorated(false);
			frame.setSize(width, height);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setResizable(false);
			frame.setLocationRelativeTo(null);
			frame.setVisible(true);

			frame.pack();
		}
	}
	
	public Canvas getCanvas() {
		return canvas;
	}
	
	public JFrame getFrame() {
		return frame;
	}
}
