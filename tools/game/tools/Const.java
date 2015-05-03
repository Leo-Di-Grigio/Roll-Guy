package game.tools;

import game.cycle.scene.game.state.location.creature.Creature;

public class Const {

	// ID
	public static final int INVALID_ID = Integer.MIN_VALUE;

	// Data size params
	public static final int INTEGER_TYPE_SIZE = Integer.BYTES;
	public static final int CHAR_TYPE_SIZE = Character.BYTES;
	
	// Graphics params
	public static final int TILE_SIZE = 40;
	
	// Map params
	public static final int MAP_CHUNK_SIZE = 16;
	
	// Turn params
	public static final int ACTION_POINTS_MAX = 10;

	// PLAYER ACTIONS
	public static final int UI_ACTION_PANEL_SLOTS = 12;
	public static final float INTERACT_RANGE = 1.45f; // ~> sqrt(2)
	
	public static final int MOVEMENT_NORMAL_LOAD_AP = 1;
	public static final int MOVEMENT_HALF_LOAD_AP = 2;
	public static final int MOVEMENT_TREE_QUARTERS_LOAD_AP = 4;
	public static final int MOVEMENT_OVERLOAD_AP = 11;

	// Creature inventory
	public static final int INVENTORY_SIZE_X = 5;
	public static final int INVENTORY_SIZE_Y = 6;
	
	// AI
	public static final float AI_CALCULATE_RANGE = 30;

	// GO
	public static final int GO_TRIGGERS_COUNT = 4;
	
	// Races
	public static final int RACE_HUMAN_MALE = 0;
	public static final int RACE_HUMAN_FEM = 1;
	public static final int RACE_ELF_MALE = 2;
	public static final int RACE_ELF_FEM = 3;
	public static final int RACE_DWARF_MALE = 4;
	public static final int RACE_DWARF_FEM = 5;
	public static final int RACE_ORC_MALE = 6;
	public static final int RACE_ORC_FEM = 7;
	public static final int RACE_GNOME_MALE = 8;
	public static final int RACE_GNOME_FEM = 9;
	
	// Items
	public static final int ITEM_SLOT_NULL = 0;
	public static final int ITEM_SLOT_HEAD = 1;
	public static final int ITEM_SLOT_CHEST = 2;
	public static final int ITEM_SLOT_WEAPON_1H = 3;
	public static final int ITEM_SLOT_WEAPIN_2H = 4;
	
	public static int getMovementAP(Creature creature) {
		int str = creature.proto().stats().strength;
		int mass = creature.getMass();
		
		if(mass <= 9*str){
			return MOVEMENT_NORMAL_LOAD_AP;
		}
		else if(mass > 9*str && mass <= 13*str){
			return MOVEMENT_HALF_LOAD_AP;
		}
		else if(mass > 13*str && mass <= 17*str){
			return MOVEMENT_TREE_QUARTERS_LOAD_AP;
		}
		else{
			return MOVEMENT_OVERLOAD_AP;
		}
	}

	public static int getAttackAp(Creature creature) {
		return 3; // test value
	}
}
