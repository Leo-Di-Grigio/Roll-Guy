package game.cycle.scene.game.world.go;

import game.script.Script;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class GO {
	
	public Sprite sprite;
	
	// base data
	public static int ID = 0;
	public int id;
	public GOProto proto;
	
	// teleport
	public int teleportId;
	
	// script
	public Script script1;
	
	public GO() {
		this.id = ID++;
	}
}
