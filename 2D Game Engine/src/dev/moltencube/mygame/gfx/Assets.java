package dev.moltencube.mygame.gfx;

import java.awt.Font;
import java.awt.image.BufferedImage;

public class Assets {
	
	private static final int WIDTH = 32, HEIGHT = 32;
	private static final int PLANT_WIDTH = 32, PLANT_HEIGHT = 48;
	private static final int TREE_WIDTH = 64, TREE_HEIGHT = 96;
	private static final int BOULDER_WIDTH = 64, BOULDER_HEIGHT = 64;
	private static final int PLAYER_WIDTH = 128, PLAYER_HEIGHT = 128;
	
	// temp
	public static BufferedImage grass1, grass2, grass3, grass4;
	
	// fonts
	public static Font font12;
	
	// crafting
	public static BufferedImage craftMenuBaseOn, craftMenuBaseOff, craftMenuProjection, craftMenuDescription, craftMenuFrame;
	public static BufferedImage craftMenuArrowUp, craftMenuArrowDown, craftMenuArrowLeft, craftMenuArrowRight;
	public static BufferedImage craftMenuCraft, craftMenuX10, craftMenuX100;
	// crafting2
	public static BufferedImage craft2MenuBaseOff, craft2MenuBaseOn, craft2MenuProjection, craft2MenuDescription, craft2MenuFrame;
	public static BufferedImage craft2MenuArrowUp, craft2MenuArrowDown, craft2MenuArrowLeft, craft2MenuArrowRight, craft2MenuCraft, craft2MenuX10, craft2MenuX100;
	
	// inventory
	public static BufferedImage hotbar, hotbarEquip;
	public static BufferedImage bulbPouchInventory;
	
	// tiles
	public static BufferedImage grass, dirt, stone;
	//plants
	public static BufferedImage plant1, plant2, plant3;
	// trees
	public static BufferedImage tree1, tree2, tree3;
	// boulders
	public static BufferedImage boulder1, boulder2, boulder3;
	
	// creatures
	public static BufferedImage[] player_left, player_right, player_up, player_down, player_idle;		// player
	public static BufferedImage[] frizard_left, frizard_right, frizard_up, frizard_down, frizard_idle;	// frizard
	
	// items
	public static BufferedImage itemShadow;
		// resources
	public static BufferedImage wood, rock, stick, bone, plantFiber, plantFiberRope, treeBulb, bulbPouch;
	public static BufferedImage ironOre, ironIngot;
		// tools
	public static BufferedImage boneKnife, bonePickaxe;
	public static BufferedImage stoneKnife, stonePickaxe, stoneAxe, stoneShovel;
	
	// menu
	public static BufferedImage[] btn_start;
	
	public static void init() {
		SpriteSheet menuSheet = new SpriteSheet(ImageLoader.loadImage("/textures/ui/menubuttons.png"));
		SpriteSheet tileSheet = new SpriteSheet(ImageLoader.loadImage("/textures/environment/tiles.png"));
		SpriteSheet tileSheetNew = new SpriteSheet(ImageLoader.loadImage("/textures/environment/tilesNew.png"));
		SpriteSheet plantSheet = new SpriteSheet(ImageLoader.loadImage("/textures/environment/plants.png"));
		SpriteSheet treeSheet = new SpriteSheet(ImageLoader.loadImage("/textures/environment/trees.png"));
		SpriteSheet boulderSheet = new SpriteSheet(ImageLoader.loadImage("/textures/environment/boulders.png"));
		SpriteSheet itemSheet = new SpriteSheet(ImageLoader.loadImage("/textures/items/items.png"));
		SpriteSheet craftSheet = new SpriteSheet(ImageLoader.loadImage("/textures/ui/craftingSheet.png"));
		SpriteSheet playerSheet = new SpriteSheet(ImageLoader.loadImage("/textures/creatures/starman.png"));
		SpriteSheet frizardSheet = new SpriteSheet(ImageLoader.loadImage("/textures/creatures/frizard.png"));
		SpriteSheet craftSheet2 = new SpriteSheet(ImageLoader.loadImage("/textures/ui/craftingSheet2.png"));
		
		// fonts
		font12 = FontLoader.loadFont("res/fonts/font.ttf", 12);
		
		// menu assets
		btn_start = new BufferedImage[2];
		btn_start[0] = menuSheet.crop(0, 0, 2*WIDTH, HEIGHT);
		btn_start[1] = menuSheet.crop(2*WIDTH, 0, 2*WIDTH, HEIGHT);
		
		
		// game assets
			// TEMPORARY!!!
				grass1 = tileSheetNew.crop(0, 0, 128, 128);
				grass2 = tileSheetNew.crop(128, 0, 128, 128);
				grass3 = tileSheetNew.crop(128*2, 0, 128, 128);
				grass4 = ImageLoader.loadImage("/textures/environment/bluegrass.png");
		
			// creatures
				// player
		player_left = new BufferedImage[2];	// allocate walk left animation
		player_right = new BufferedImage[2];	// allocate walk right animation
		player_down = new BufferedImage[2];	// allocate walk down animation
		player_up = new BufferedImage[2];		// allocate walk up animation
		player_idle = new BufferedImage[1];		// allocate idle animation
		
		player_idle[0] = playerSheet.crop(0, 0, PLAYER_WIDTH, PLAYER_HEIGHT);
		for(int frame = 0; frame < 2; frame++) {	// set walk animations
			player_right[frame] = playerSheet.crop(PLAYER_WIDTH * frame, PLAYER_HEIGHT, PLAYER_WIDTH, PLAYER_HEIGHT);
			player_left[frame] = playerSheet.crop(PLAYER_WIDTH * frame, 2*PLAYER_HEIGHT, PLAYER_WIDTH, PLAYER_HEIGHT);
			player_down[frame] = playerSheet.crop(PLAYER_WIDTH * frame, 3*PLAYER_HEIGHT, PLAYER_WIDTH, PLAYER_HEIGHT);
			player_up[frame] = playerSheet.crop(PLAYER_WIDTH * frame, 4*PLAYER_HEIGHT, PLAYER_WIDTH, PLAYER_HEIGHT);
		}
		
				// frizard
		frizard_left = new BufferedImage[3];	// allocate walk left animation
		frizard_right = new BufferedImage[3];	// allocate walk right animation
		frizard_down = new BufferedImage[3];	// allocate walk down animation
		frizard_up = new BufferedImage[3];		// allocate walk up animation
		frizard_idle = new BufferedImage[1];	// allocate idle animation
		
		frizard_idle[0] = frizardSheet.crop(0, 0, 32, 32);
		for(int frame = 0; frame < 3; frame++) {
			frizard_left[frame] = frizardSheet.crop(40*frame, 64, 40, 32);
			frizard_right[frame] = frizardSheet.crop(40*frame, 32, 40, 32);
		}
		
		
			// crafting
		craftMenuBaseOff = craftSheet.crop(337, 394, 264, 51);				// Base Off
		craftMenuBaseOn = craftSheet.crop(337, 445, 264, 51);				// Base On
		craftMenuProjection = craftSheet.crop(0, 0, 336, 515);				// Projection
		craftMenuDescription = craftSheet.crop(334, 0, 183, 393);			// Description Pane
		craftMenuFrame = craftSheet.crop(520, 0, 32, 32);					// Item Frame
		craftMenuArrowUp = craftSheet.crop(520, 128, 32, 16);				// Up Arrow
		craftMenuArrowDown = craftSheet.crop(520, 144, 32, 16);				// Down Arrow
		craftMenuArrowRight = craftSheet.crop(536, 128, 16, 32);			// Right Arrow
		craftMenuArrowLeft = craftSheet.crop(520, 128, 16, 32);				// Left Arrow
		craftMenuCraft = craftSheet.crop(520, 32, 91, 32);					// Craft Button
		craftMenuX10 = craftSheet.crop(520, 64, 40, 32);					// X10 Button
		craftMenuX100 = craftSheet.crop(520, 96, 41, 32);					// X100 Button
		
			// crafting v.2
		craft2MenuBaseOff = craftSheet2.crop(0, 0, 264, 50);				// Base Off
		craft2MenuBaseOn = craftSheet2.crop(0, 53, 264, 50);				// Base On
		craft2MenuProjection = craftSheet2.crop(0, 107, 336, 512);			// Projection
		craft2MenuDescription = craftSheet2.crop(338, 109, 182, 392);		// Description Pane
		craft2MenuFrame = craftSheet2.crop(306, 33, 32, 32);				// Item Frame
		craft2MenuArrowUp = craftSheet2.crop(340, 33, 32, 16);				// Arrow Up
		craft2MenuArrowDown = craftSheet2.crop(340, 50, 32, 16);			// Arrow Down
		craft2MenuArrowLeft = craftSheet2.crop(307, 66, 16, 32);			// Arrow Left
		craft2MenuArrowRight = craftSheet2.crop(324, 66, 16, 32);			// Arrow Right
		craft2MenuCraft = craftSheet2.crop(265, 0, 91, 32);					// Craft Button
		craft2MenuX10 = craftSheet2.crop(265, 33, 40, 32);					// X10 Button
		craft2MenuX100 = craftSheet2.crop(265, 66, 41, 32);					// X100 Button
		
		
			// inventory
		hotbar = ImageLoader.loadImage("/textures/ui/hotbar.png");
		hotbarEquip = ImageLoader.loadImage("/textures/ui/hotbarequip.png");
		bulbPouchInventory = ImageLoader.loadImage("/textures/ui/bulbpouchInventory.png");
		
			// tiles
		grass = tileSheet.crop(WIDTH, 0, WIDTH, HEIGHT);
		dirt = tileSheet.crop(2*WIDTH, 0, WIDTH, HEIGHT);
		stone = tileSheet.crop(3*WIDTH, 0, WIDTH, HEIGHT);
		
			// items
		itemShadow = ImageLoader.loadImage("/textures/items/itemshadow.png");
				// resources
		stick = itemSheet.crop(0, 0, WIDTH, HEIGHT);
		wood = itemSheet.crop(WIDTH, 0, WIDTH, HEIGHT);
		rock = itemSheet.crop(2*WIDTH, 0, WIDTH, HEIGHT);
		bone = itemSheet.crop(9*WIDTH, 0, WIDTH, HEIGHT);
		plantFiber = itemSheet.crop(3*WIDTH, 0, WIDTH, HEIGHT);
		plantFiberRope = itemSheet.crop(4*WIDTH, 0, WIDTH, HEIGHT);
		treeBulb = itemSheet.crop(0, HEIGHT, WIDTH, HEIGHT);
		
				// storage
		bulbPouch = itemSheet.crop(WIDTH, HEIGHT, WIDTH, HEIGHT);
		
				// tools
					// bone
		boneKnife = itemSheet.crop(10*WIDTH, 0, WIDTH, HEIGHT);
		bonePickaxe = itemSheet.crop(11*WIDTH, 0, WIDTH, HEIGHT);
		
					// stone
		stoneAxe = itemSheet.crop(5*WIDTH, 0, WIDTH, HEIGHT);
		stoneShovel = itemSheet.crop(6*WIDTH, 0, WIDTH, HEIGHT);
		stonePickaxe = itemSheet.crop(7*WIDTH, 0, WIDTH, HEIGHT);
		stoneKnife = itemSheet.crop(8*WIDTH, 0, WIDTH, HEIGHT);
		
			// plants
		plant1 = plantSheet.crop(0, 0, PLANT_WIDTH, PLANT_HEIGHT);
		plant2 = plantSheet.crop(PLANT_WIDTH, 0, PLANT_WIDTH, PLANT_HEIGHT);
		plant3 = plantSheet.crop(2*PLANT_WIDTH, 0, PLANT_WIDTH, PLANT_HEIGHT);
		
			// trees
		tree1 = treeSheet.crop(0, 0, TREE_WIDTH, TREE_HEIGHT);
		tree2 = treeSheet.crop(TREE_WIDTH, 0, TREE_WIDTH, TREE_HEIGHT);
		tree3 = treeSheet.crop(2*TREE_WIDTH, 0, TREE_WIDTH, TREE_HEIGHT);
		
			// boulders
		boulder1 = boulderSheet.crop(0, 0, BOULDER_WIDTH, BOULDER_HEIGHT);
		boulder2 = boulderSheet.crop(BOULDER_WIDTH, 0, BOULDER_WIDTH, BOULDER_HEIGHT);
		boulder3 = boulderSheet.crop(2 * BOULDER_WIDTH, 0, BOULDER_WIDTH, BOULDER_HEIGHT);
	}
}
