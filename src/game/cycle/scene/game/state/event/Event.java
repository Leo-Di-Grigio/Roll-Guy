package game.cycle.scene.game.state.event;

import game.cycle.scene.game.state.location.LocationObject;

public class Event {

	// Misc
	public static final int EVENT_NULL = 0;
	public static final int EVENT_DAMAGE = 1;
	public static final int EVENT_DIALOG_BEGIN = 2;
	public static final int EVENT_DIALOG_END = 3;
	public static final int EVENT_PLAYER_DEAD = 4;
	public static final int EVENT_LOCATION_LOAD = 5;
	public static final int EVENT_LOCATION_CHANGE = 6;
	
	// SCRIPT
	public static final int EVENT_SCRIPT_GO_USE = 7;
	public static final int EVENT_SCRIPT_LAND = 8;
	
	// VISUAL
	public static final int EVENT_VISUAL_ATTACK = 9;
	
	// SOUND
	public static final int EVENT_SOUND_ATTACK = 10;
	public static final int EVENT_SOUND_DIALOG_BEGIN = 11;
	public static final int EVENT_SOUND_SAY = 12;
	
	// data
	public int type;
	public LocationObject source;
	public LocationObject target;
	
	// meta
	public int volume;
	
	public Event(int type, LocationObject source, LocationObject target) {
		this.type = type;
		this.source = source;
		this.target = target;
	}
	
	public Event(int type, LocationObject source, LocationObject target, int volume) {
		this.type = type;
		this.source = source;
		this.target = target;
		this.volume = volume;
	}
}
