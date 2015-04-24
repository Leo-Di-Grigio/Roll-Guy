package game.resources.tex;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Disposable;

public class Tex implements Disposable {

	// keys
	public static final int NULL = 0;
	
	// keys UI
	public static final int UI_BUTTON_NORMAL = 100;
	public static final int UI_BUTTON_SELECTED = 101;
	public static final int UI_BUTTON_CLICK = 102;
	public static final int UI_BACKGROUND_NORMAL = 103;
	public static final int UI_BACKGROUND_SELECTED = 104;
	public static final int UI_BACKGROUND_SELECTED_LIGHT = 105;
	public static final int UI_LIST_LINE = 106;
	public static final int UI_INVENTORY_SLOT = 107;
	public static final int UI_INVENTORY_SLOT_HELMET = 108;
	public static final int UI_INVENTORY_SLOT_CHEST = 109;
	public static final int UI_INVENTORY_SLOT_HAND_1 = 110;
	public static final int UI_INVENTORY_SLOT_HAND_2 = 111;
	public static final int UI_SKILL_FRAME = 112;
	public static final int UI_BACKGROUND_INFORMATION = 113;
	
	// tile select
	public static final int GAMEPLAY_SELECT = 150;
	public static final int GAMEPLAY_WP = 151;

	// keys tiles
	public static final int TILE_NULL = 200;
	public static final int TILE_GRASS = 201;
	public static final int TILE_WALL = 202;
	public static final int TILE_WATER = 203;
	public static final int TILE_ALUMINA = 204;
	public static final int TILE_CRACKED_ALUMINA = 205;
	public static final int TILE_STONE = 206;
	public static final int TILE_SAND_STONE_COAST = 207;
	public static final int TILE_RIVER = 208;
	public static final int TILE_SAND = 209;
	public static final int TILE_SAND_STONE = 210;
	
	// atlases
	public static final int TEX_ATLAS_0 = 250;
	
	// lighting
	public static final int LIGHT = 300;
	
	// creature
	public static final int CREATURE_0 = 1000;
	public static final int CREATURE_1 = 1001;
	public static final int CREATURE_2 = 1002;
		
	// creature avatar
	public static final int AVATAR_0 = 2000;
		
	// go
	public static final int GO_NULL = 10000;
	public static final int GO_DOOR_OPEN = 10001;
	public static final int GO_DOOR_CLOSE = 10002;
	public static final int GO_CHEST = 10003;
	public static final int GO_BAG = 10004;
	public static final int GO_WP = 10005;
	public static final int GO_TORCH = 10006;
	public static final int GO_MINE_WALL = 10007;
	public static final int GO_BONFIRE = 10008;
	
	// skills
	public static final int SKILL_NULL = 30000;
	public static final int SKILL_MELEE = 30001;
	public static final int SKILL_HEAL = 30002;
	public static final int SKILL_DRAG = 30003;
	public static final int SKILL_FIREBALL = 30004;
	
	// creature events
	public static final int NPC_WARNING = 50000;
	
	// items
	public static final int ITEM_0 = 100000;
	public static final int ITEM_1 = 100001;
	public static final int ITEM_2 = 100002;
	public static final int ITEM_3 = 100003;
	
	// data
	public int id;
	public Texture tex;
	
	public Tex(int id, Texture tex) {
		this.id = id;
		this.tex = tex;
	}

	@Override
	public void dispose() {
		tex = null;
	}
}