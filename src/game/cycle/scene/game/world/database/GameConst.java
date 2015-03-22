package game.cycle.scene.game.world.database;

import game.cycle.scene.game.world.creature.Creature;

public class GameConst {

	public static final int apMax = 10;
	
	public static final int uiActionPanelSlots = 12;

	// PLAYER ACTIONS
	public static final float interactRange = 1.45f;
	
	public static int getMovementAP(Creature creature) {
		return 1; // test value
	}

	public static int getAttackAp(Creature creature) {
		return 3; // test value
	}
}
