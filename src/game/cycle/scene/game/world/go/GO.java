package game.cycle.scene.game.world.go;

import game.script.Script;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class GO {
	
	public Sprite sprite;
	
	// base data
	public static int ID = 0;
	public int id;
	public GOProto proto;
	
	// flags
	public boolean passable;
	public int teleportId;
	
	// script
	public Script script1;
	
	// params
	public int param1;
	public int param2;
	public int param3;
	public int param4;

	// current
	public int durability;
	
	public GO(GOProto proto) {
		this.id = ID++;
		this.proto = proto;
		this.passable = proto.passable;
		this.durability = proto.durabilityMax;
	}
}