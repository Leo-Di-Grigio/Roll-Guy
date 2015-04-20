package game.cycle.scene.game.state.event;

import game.cycle.scene.game.state.location.LocationObject;

public class Event {

	// Misc
	public static final int EVENT_NULL = 0;
	public static final int EVENT_DAMAGE = 1;
	public static final int EVENT_DIALOG_BEGIN = 2;
	public static final int EVENT_DIALOG_END = 3;
	
	// SCRIPT
	public static final int EVENT_SCRIPT_GO_USE = 4;
	public static final int EVENT_SCRIPT_LAND = 5;
	
	// VISUAL
	public static final int EVENT_VISUAL_ATTACK = 6;
	
	// SOUND
	public static final int EVENT_SOUND_ATTACK = 7;
	public static final int EVENT_SOUND_DIALOG_BEGIN = 8;
	public static final int EVENT_SOUND_SAY = 9;
	
	// data
	public int type;
	public LocationObject source;
	public LocationObject target;
	
	public Event(int type, LocationObject source, LocationObject target) {
		this.type = type;
		this.source = source;
		this.target = target;
	}
}
