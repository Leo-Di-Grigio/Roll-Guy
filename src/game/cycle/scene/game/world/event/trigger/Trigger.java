package game.cycle.scene.game.world.event.trigger;

import game.cycle.scene.game.world.event.LocationEvent;
import game.cycle.scene.game.world.location.go.GO;
import game.tools.Const;

abstract public class Trigger {

	// Trigger type
	public static final int NULL = 0;
	public static final int NULL_1 = Const.INVALID_ID;
	public static final int GO_USE = 1;
	public static final int LAND = 2;
	public static final int SOUND = 3;
	public static final int EFFECT = 4;
	public static final int LINK = 5;
	public static final int ITEM = 6;
	public static final int DAMAGE = 7;
	
	//
	private static int ID = 0;
	protected int id;

	//
	protected GO go;
	protected int type;
	protected int param;
	protected int scriptId;
	protected int param1;
	protected int param2;
	protected int param3;
	protected int param4;
	
	public Trigger(GO go, int type, int param, int scriptId, int param1, int param2, int param3, int param4) {
		this.id = ID++;
		
		this.go = go;
		this.type = type;
		this.param = param;
		this.scriptId = scriptId;
		this.param1 = param1;
		this.param2 = param2;
		this.param3 = param3;
		this.param4 = param4;
	}
	
	public int getId(){
		return id;
	}
	
	public int getType() {
		return type;
	}

	public int getParam() {
		return param;
	}
	
	abstract public boolean execute(LocationEvent event, int param);
}
