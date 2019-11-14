package dev.moltencube.mygame.items;

import java.awt.image.BufferedImage;

import dev.moltencube.mygame.items.types.EquipType;

public class StorageItem extends Item {
	
	public StorageItem(BufferedImage texture, String name, EquipType equipType, int storageSpace, int rowCount) {
		super(texture, name, 1, equipType);
		
		this.storageSpace = storageSpace;
		this.rowCount = rowCount;
	}
	
	@Override
	public void tick() {
		super.tick();
		System.out.println(count);
	}
}
