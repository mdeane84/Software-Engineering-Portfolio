package dev.moltencube.mygame.crafting;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import dev.moltencube.mygame.Handler;
import dev.moltencube.mygame.gfx.Assets;
import dev.moltencube.mygame.gfx.Text;
import dev.moltencube.mygame.items.Item;
import dev.moltencube.mygame.states.State;
import dev.moltencube.mygame.ui.ClickListener;
import dev.moltencube.mygame.ui.UIImageButton;

public class Crafting {
	
	private Handler handler;
	private ArrayList<Recipe> recipes = new ArrayList<Recipe>();
	private ArrayList<Integer> buttons = new ArrayList<Integer>();
	
	private Rectangle scrollBounds = new Rectangle();
	private Rectangle ingredBounds = new Rectangle();
	
	private int upArrowId, downArrowId, leftArrowId, rightArrowId;
	private int craftId, x10Id, x100Id;
	
	private int recipeSelection, ingredientSelection;
	private int xOffset;
	private int recipeStartX, recipeStartY, recipeSpacing;
	
	private int baseX, baseY;
	private int projectionX, projectionY;
	private int descriptionX, descriptionY;
	private int descImageX, descImageY;
	private int ingredientStartX, ingredientStartY, ingredientSpacing;
	private int upArrowX, upArrowY, downArrowX, downArrowY;
	private int leftArrowX, leftArrowY, rightArrowX, rightArrowY;
	private int craftX, craftY, x10X, x10Y, x100X, x100Y;
	
	
	private boolean active;
	private boolean needsBtns;
	private boolean hoveringRecipes, hoveringIngredients;
	
	public Crafting(Handler handler) {
		this.handler = handler;
		
		for(Recipe r : Recipe.recipes) {
			if(r != null) {
				r.setHandler(handler);
				recipes.add(r);
			}
		}
		
		recipeSpacing = 10 + Recipe.HEIGHT;
		ingredientSpacing = 10 + Item.ITEMWIDTH;
		
		recipeSelection = 0;
		ingredientSelection = 0;
		active = false;
		needsBtns = true;
		hoveringRecipes = false;
		hoveringIngredients = false;
	}
	
	public void tick() {
		if(needsBtns) {
			createButtons();
			updatePosition();
			for(int i = 0; i < recipes.size(); i++) {
				for(int j = 0; j < recipes.get(i).getIngredients().length; j++)
					recipes.get(i).getIngredients()[j].setPosition(ingredientStartX - (ingredientSelection * ingredientSpacing) + (j * ingredientSpacing), ingredientStartY);
				
				recipes.get(i).setBoundsX(recipeStartX);
				recipes.get(i).setBoundsY(recipeStartY - (recipeSelection * recipeSpacing) + (i * recipeSpacing));
			}
			needsBtns = false;
		}
		
		if(active) {
			toggleArrowButtons();
		}
	}
	
	private void toggleArrowButtons() {
		if(ingredientSelection + 3 < recipes.get(recipeSelection).getIngredients().length) {
			handler.getGame().gameState.getUIManager().getObjects().get(rightArrowId).setActive(true);
		} else {
			handler.getGame().gameState.getUIManager().getObjects().get(rightArrowId).setActive(false);
		}
		if(ingredientSelection > 0) {
			handler.getGame().gameState.getUIManager().getObjects().get(leftArrowId).setActive(true);
		} else {
			handler.getGame().gameState.getUIManager().getObjects().get(leftArrowId).setActive(false);
		}
		if(recipeSelection < recipes.size() - 1) {
			handler.getGame().gameState.getUIManager().getObjects().get(downArrowId).setActive(true);
		} else {
			handler.getGame().gameState.getUIManager().getObjects().get(downArrowId).setActive(false);
		}
		if(recipeSelection > 0) {
			handler.getGame().gameState.getUIManager().getObjects().get(upArrowId).setActive(true);
		} else {
			handler.getGame().gameState.getUIManager().getObjects().get(upArrowId).setActive(false);
		}
	}
	
	public void render(Graphics g) {
		
		if(active) {
			// draw menu
			g.drawImage(Assets.craft2MenuProjection, projectionX, projectionY, null);
			g.drawImage(Assets.craft2MenuBaseOn, baseX, baseY, null);
			g.drawImage(Assets.craft2MenuDescription, descriptionX, descriptionY, null);
			
			// draw selected item in description pane
			g.drawImage(recipes.get(recipeSelection).getTexture(), descImageX, descImageY, 150, 150, null);
			Text.drawString(g, recipes.get(recipeSelection).getName(), descriptionX + 8, descriptionY, false, false, Color.WHITE, Assets.font12);
			Text.drawWrappedString(g, "TEST DESCRIPTION", new Rectangle(descriptionX, descriptionY, Assets.craft2MenuDescription.getWidth(), 120), Color.WHITE, Assets.font12);
			
			// draw recipes
			for(int i = 0; i < recipes.size(); i++) {
				if(i > recipeSelection - 5 && i < recipeSelection + 5) {
					recipes.get(i).render(g, recipeStartX, recipeStartY - (recipeSelection * recipeSpacing) + (i * recipeSpacing));
					Text.drawString(g, recipes.get(i).getName(), recipeStartX + 36, recipeStartY - (recipeSelection * recipeSpacing) + (i * recipeSpacing) + 20, false, false, Color.WHITE, Assets.font12);
				}
			}
			// draw ingredients
			for(int i = 0; i < recipes.get(recipeSelection).getIngredients().length; i++) {
				if(i >= ingredientSelection && i < ingredientSelection + 3) {
					g.drawImage(Assets.craft2MenuFrame, ingredientStartX - (ingredientSelection * ingredientSpacing) + (i * ingredientSpacing), ingredientStartY, null);
					recipes.get(recipeSelection).getIngredients()[i].render(g, recipes.get(recipeSelection).getIngredients()[i].getX(), recipes.get(recipeSelection).getIngredients()[i].getY());
				}
			}
		}else {
			g.drawImage(Assets.craft2MenuBaseOff, baseX, baseY, null);
		}
	}
	
	private void createButtons() {
		if(State.getState().getUIManager() != null) {
			UIImageButton tempBtn;
			tempBtn = new UIImageButton(handler, projectionX + 55, projectionY + 26, Assets.craft2MenuArrowUp, new ClickListener() {
				@Override
				public void onClick() {
					if(recipeSelection > 0)
						moveRecipesUp();
				}
			});
			tempBtn.setActive(false);
			State.getState().getUIManager().addObject(tempBtn);
			upArrowId = tempBtn.getId();
			buttons.add(new Integer(tempBtn.getId()));
			
			tempBtn = new UIImageButton(handler, projectionX + 58, projectionY + 430, Assets.craft2MenuArrowDown, new ClickListener() {
				@Override
				public void onClick() {
					if(recipeSelection < recipes.size() - 1)
						moveRecipesDown();
				}
			});
			tempBtn.setActive(false);
			State.getState().getUIManager().addObject(tempBtn);
			downArrowId = tempBtn.getId();
			buttons.add(new Integer(tempBtn.getId()));
			
			tempBtn = new UIImageButton(handler, 348 + xOffset + (Assets.craft2MenuBaseOn.getWidth() / 2 - Assets.craft2MenuProjection.getWidth() / 2), handler.getHeight() - 334, Assets.craft2MenuArrowRight, new ClickListener() {
				@Override
				public void onClick() {
					if(ingredientSelection < recipes.get(recipeSelection).getIngredients().length - 1 && ingredientSelection + 3 < recipes.get(recipeSelection).getIngredients().length)
						moveIngredRight();
				}
			});
			tempBtn.setActive(false);
			State.getState().getUIManager().addObject(tempBtn);
			rightArrowId = tempBtn.getId();
			buttons.add(new Integer(tempBtn.getId()));
			
			tempBtn = new UIImageButton(handler, 196 + xOffset + (Assets.craft2MenuBaseOn.getWidth() / 2 - Assets.craft2MenuProjection.getWidth() / 2), handler.getHeight() - 334, Assets.craft2MenuArrowLeft, new ClickListener() {
				@Override
				public void onClick() {
					if(ingredientSelection > 0)
						moveIngredLeft();
				}
			});
			tempBtn.setActive(false);
			State.getState().getUIManager().addObject(tempBtn);
			leftArrowId = tempBtn.getId();
			buttons.add(new Integer(tempBtn.getId()));
			
			tempBtn = new UIImageButton(handler, 48 + xOffset + (Assets.craft2MenuBaseOn.getWidth() / 2 - Assets.craft2MenuProjection.getWidth() / 2) + Assets.craft2MenuProjection.getWidth() - Assets.craft2MenuDescription.getWidth() - 11, handler.getHeight() - Assets.craft2MenuBaseOn.getHeight() - Assets.craft2MenuProjection.getHeight() + 28 + 400, Assets.craft2MenuCraft, new ClickListener() {
				@Override
				public void onClick() {
					recipes.get(recipeSelection).craft();
				}
			});
			tempBtn.setActive(false);
			State.getState().getUIManager().addObject(tempBtn);
			craftId = tempBtn.getId();
			buttons.add(new Integer(tempBtn.getId()));
			
			tempBtn = new UIImageButton(handler, 48 + xOffset + (Assets.craft2MenuBaseOn.getWidth() / 2 - Assets.craft2MenuProjection.getWidth() / 2) + Assets.craft2MenuProjection.getWidth() - Assets.craft2MenuDescription.getWidth() - 12 + 96, handler.getHeight() - Assets.craft2MenuBaseOn.getHeight() - Assets.craft2MenuProjection.getHeight() + 28 + 400, Assets.craft2MenuX10, new ClickListener() {
				@Override
				public void onClick() {
					for(int i = 0; i < 10; i++)
						recipes.get(recipeSelection).craft();
				}
			});
			tempBtn.setActive(false);
			State.getState().getUIManager().addObject(tempBtn);
			x10Id = tempBtn.getId();
			buttons.add(new Integer(tempBtn.getId()));
			
			tempBtn = new UIImageButton(handler, 48 + xOffset + (Assets.craft2MenuBaseOn.getWidth() / 2 - Assets.craft2MenuProjection.getWidth() / 2) + Assets.craft2MenuProjection.getWidth() - Assets.craft2MenuDescription.getWidth() - 12 + 140, handler.getHeight() - Assets.craft2MenuBaseOn.getHeight() - Assets.craft2MenuProjection.getHeight() + 28 + 400, Assets.craft2MenuX100, new ClickListener() {
				@Override
				public void onClick() {
					for(int i = 0; i < 100; i++)
						recipes.get(recipeSelection).craft();
				}
			});
			tempBtn.setActive(false);
			State.getState().getUIManager().addObject(tempBtn);
			x100Id = tempBtn.getId();
			buttons.add(new Integer(tempBtn.getId()));
		}
	}
	
	public void updatePosition() {
		if(handler.getGame().isWindowed())
			xOffset = 0;
		else
			xOffset = 96;
		
		baseX = 50 + xOffset;
		baseY = handler.getHeight() - Assets.craft2MenuBaseOn.getHeight();
		
		projectionX = baseX + (Assets.craft2MenuBaseOn.getWidth() / 2 - Assets.craft2MenuProjection.getWidth() / 2);
		projectionY = baseY - Assets.craft2MenuProjection.getHeight();
		
		descriptionX = projectionX + Assets.craft2MenuProjection.getWidth() - Assets.craft2MenuDescription.getWidth() - 12;
		descriptionY = projectionY + 28;
		descImageX = descriptionX + 4;
		descImageY = descriptionY + 27;
		
		scrollBounds.x = projectionX;
		scrollBounds.y = projectionY;
		scrollBounds.width = 128;
		scrollBounds.height = Assets.craft2MenuProjection.getHeight() - 48;
		
		ingredBounds.x = descriptionX;
		ingredBounds.y = descriptionY + 184;
		ingredBounds.width = 182;
		ingredBounds.height = 38;
		
		upArrowX = projectionX + 55;
		upArrowY = projectionY + 26;
		downArrowX = projectionX + 58;
		downArrowY = projectionY + 430;
		rightArrowX = descriptionX + 162;
		rightArrowY = descriptionY + 189;
		leftArrowX = descriptionX + 8;
		leftArrowY = descriptionY + 189;
		
		craftX = descriptionX;
		craftY = descriptionY + 400;
		x10X = descriptionX + 96;
		x10Y = descriptionY + 400;
		x100X = descriptionX + 140;
		x100Y = descriptionY + 400;
		
		if(handler.getGame().gameState != null) {
			handler.getGame().gameState.getUIManager().getObjects().get(upArrowId).setPosition(upArrowX, upArrowY);
			handler.getGame().gameState.getUIManager().getObjects().get(downArrowId).setPosition(downArrowX, downArrowY);
			handler.getGame().gameState.getUIManager().getObjects().get(leftArrowId).setPosition(leftArrowX, leftArrowY);
			handler.getGame().gameState.getUIManager().getObjects().get(rightArrowId).setPosition(rightArrowX, rightArrowY);
			handler.getGame().gameState.getUIManager().getObjects().get(craftId).setPosition(craftX, craftY);
			handler.getGame().gameState.getUIManager().getObjects().get(x10Id).setPosition(x10X, x10Y);
			handler.getGame().gameState.getUIManager().getObjects().get(x100Id).setPosition(x100X, x100Y);
		}
		
		recipeStartX = 10 + projectionX;
		recipeStartY = 220 + projectionY;
		
		ingredientStartX = descriptionX + 35;
		ingredientStartY = descriptionY + 188;
		
		for(int i = 0; i < recipes.get(recipeSelection).getIngredients().length; i++)
			recipes.get(recipeSelection).getIngredients()[i].setPosition(ingredientStartX - (ingredientSelection * ingredientSpacing) + (i * ingredientSpacing), ingredientStartY);
	}
	
	private void moveIngredRight() {
		ingredientSelection += 1;
		for(int i = 0; i < recipes.get(recipeSelection).getIngredients().length; i++)
			recipes.get(recipeSelection).getIngredients()[i].setPosition(ingredientStartX - (ingredientSelection * ingredientSpacing) + (i * ingredientSpacing), ingredientStartY);
	}
	
	private void moveIngredLeft() {
		ingredientSelection -= 1;
		for(int i = 0; i < recipes.get(recipeSelection).getIngredients().length; i++)
			recipes.get(recipeSelection).getIngredients()[i].setPosition(ingredientStartX - (ingredientSelection * ingredientSpacing) + (i * ingredientSpacing), ingredientStartY);
	}
	
	private void moveRecipesUp() {
		recipeSelection -= 1;
		ingredientSelection = 0;
		
		for(int i = 0; i < recipes.size(); i++) {
			recipes.get(i).setBoundsY(recipeStartY - (recipeSelection * recipeSpacing) + (i * recipeSpacing));
		}
		
		for(int i = 0; i < recipes.get(recipeSelection).getIngredients().length; i++)
			recipes.get(recipeSelection).getIngredients()[i].setPosition(ingredientStartX - (ingredientSelection * ingredientSpacing) + (i * ingredientSpacing), ingredientStartY);
	}
	
	private void moveRecipesDown() {
		recipeSelection += 1;
		ingredientSelection = 0;
		
		for(int i = 0; i < recipes.size(); i++) {
			recipes.get(i).setBoundsY(recipeStartY - (recipeSelection * recipeSpacing) + (i * recipeSpacing));
		}
		
		for(int i = 0; i < recipes.get(recipeSelection).getIngredients().length; i++)
			recipes.get(recipeSelection).getIngredients()[i].setPosition(ingredientStartX - (ingredientSelection * ingredientSpacing) + (i * ingredientSpacing), ingredientStartY);
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
		ingredientSelection = 0;
		for(int i = 0; i < recipes.get(recipeSelection).getIngredients().length; i++)
			recipes.get(recipeSelection).getIngredients()[i].setPosition(ingredientStartX - (ingredientSelection * ingredientSpacing) + (i * ingredientSpacing), ingredientStartY);
	}
	
	public ArrayList<Integer> getButtons() {
		return buttons;
	}
	
	// mouse handler
	
	public void onMouseMove(MouseEvent e) {
		if(scrollBounds.contains(e.getX(), e.getY())) {
			hoveringRecipes = true;
		}else {
			hoveringRecipes = false;
		}
		
		if(ingredBounds.contains(e.getX(), e.getY())) {
			hoveringIngredients = true;
		}else {
			hoveringIngredients = false;
		}
	}
	
	public void onMouseWheelMoved(MouseWheelEvent e) {
		if(hoveringRecipes) {
			if(e.getWheelRotation() > 0 && recipeSelection < recipes.size() - 1) {
				moveRecipesDown();
			}else if(e.getWheelRotation() < 0 && recipeSelection > 0) {
				moveRecipesUp();
			}
		}
		
		if(hoveringIngredients) {
			if(e.getWheelRotation() < 0) {
				if(ingredientSelection + 3 < recipes.get(recipeSelection).getIngredients().length)
					moveIngredRight();
			}else if(e.getWheelRotation() > 0) {
				if(ingredientSelection > 0)
					moveIngredLeft();
			}
		}
	}
}
