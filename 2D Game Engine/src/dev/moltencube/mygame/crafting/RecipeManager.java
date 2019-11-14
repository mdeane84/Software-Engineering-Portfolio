package dev.moltencube.mygame.crafting;

import dev.moltencube.mygame.Handler;

public class RecipeManager {
	
	private Handler handler;

	public RecipeManager(Handler handler) {
		this.handler = handler;
		
		for(int i = 0; i < Recipe.recipes.length; i++) {
			if(Recipe.recipes[i] != null) {
				Recipe.recipes[i].setHandler(handler);
			}
		}
	}
	
	public void tick() {
		
	}
	
	public void render() {
		
	}
	
	public void addRecipe() {
		
	}
}
