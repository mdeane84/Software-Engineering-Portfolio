package com.markdeane.gameoflife;

public class Launcher {
	private static GameOfLife game;
	
	public static void main(String[] args) {
		game = new GameOfLife("Game of Life", 500, 500);
		game.start();
	}
}
