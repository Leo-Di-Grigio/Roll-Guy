package game.cycle.scene.game.world.location.creature.items;

public class ItemProto {
	
	// Constants
	// Type
	public static final int typeNull = 0;
	public static final int typeHelemt = 1;
	public static final int typeChest = 2;
	public static final int typeWeapon1H = 3;
	public static final int typeWeapon2H = 4;
	
	// proto
	public int id;
	public int sizeX;
	public int sizeY;
	public int mass;
	public int type;
	
	public String title;
	public int tex;
	
	public boolean stackble;
	public int stackcount;
}
