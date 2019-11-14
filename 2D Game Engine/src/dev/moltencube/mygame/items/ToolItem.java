package dev.moltencube.mygame.items;

import java.awt.image.BufferedImage;

import dev.moltencube.mygame.items.types.EquipType;
import dev.moltencube.mygame.items.types.ToolResType;
import dev.moltencube.mygame.items.types.ToolType;

public class ToolItem extends Item {
	
	public ToolItem(BufferedImage texture, String name, int maxCount, EquipType equipType, ToolResType resourceType, ToolType toolType) {
		super(texture, name, maxCount, equipType);
		
		this.toolType = toolType;
		this.resourceType = resourceType;
		
		if(resourceType == ToolResType.BONE)
			initBone();
		else if(resourceType == ToolResType.STONE)
			initStone();
	}
	
	private void initBone() {
		strength = 1;
		
		if(toolType == ToolType.KNIFE) {
			
		}else if(toolType == ToolType.PICKAXE) {
			
		}else if(toolType == ToolType.AXE) {
			
		}else if(toolType == ToolType.SHOVEL) {
			
		}
	}
	
	private void initStone() {
		strength = 2;
		
		if(toolType == ToolType.KNIFE) {
			
		}else if(toolType == ToolType.PICKAXE) {
			
		}else if(toolType == ToolType.AXE) {
			
		}else if(toolType == ToolType.SHOVEL) {
			
		}
	}
}
