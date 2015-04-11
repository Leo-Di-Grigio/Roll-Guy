package game.cycle.scene.game.world.database;

import game.cycle.scene.game.world.location.creature.Creature;

public class GameConst {
	
	// Graphics params
	public static final int TILE_SIZE = 32;
	
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
	public static final float AI_CALCULATE_RANGE = 100;

	// GO
	public static final int GO_TRIGGERS_COUNT = 4;
	
	public static int getMovementAP(Creature creature) {
		int str = creature.proto.stats.strength;
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
