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

	// Creature inventory
	public static final int INVENTORY_SIZE_X = 5;
	public static final int INVENTORY_SIZE_Y = 6;
	
	// AI
	public static final float AI_CALCULATE_RANGE = 100;

	// GO
	public static final int GO_TRIGGERS_COUNT = 4;
	
	public static int getMovementAP(Creature creature) {
		return 1; // test value
	}

	public static int getAttackAp(Creature creature) {
		return 3; // test value
	}
}
