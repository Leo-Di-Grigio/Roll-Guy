package game.cycle.scene.game.world.event.trigger;

import game.cycle.scene.game.world.event.LocationEvent;
import game.script.ScriptGame;
import game.tools.Const;

abstract public class Trigger {

	// Trigger type
	public static final int NULL = 0;
	public static final int NULL_1 = Const.invalidId;
	public static final int GO_USE = 1;
	public static final int LAND = 2;
	public static final int SOUND = 3;
	public static final int EFFECT = 4;
	public static final int LINK = 5;
	public static final int ITEM = 6;
	public static final int DAMAGE = 7;
	
	//
	private static int ID = 0;
	public int id;

	//
	protected int type;
	protected int scriptId;
	protected int param1;
	protected int param2;
	protected int param3;
	protected int param4;
	
	protected ScriptGame script;
	
	public Trigger(int type, int scriptId, int param1, int param2, int param3, int param4) {
		this.id = ID++;
		this.type = type;
		this.scriptId = scriptId;
		this.param1 = param1;
		this.param2 = param2;
		this.param3 = param3;
		this.param4 = param4;
	}
	
	abstract public void execute(LocationEvent event);
}
