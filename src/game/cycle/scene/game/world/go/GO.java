package game.cycle.scene.game.world.go;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import game.cycle.scene.game.world.LocationObject;
import game.cycle.scene.game.world.map.Location;
import game.script.ScriptGame;

public class GO extends LocationObject {
	
	// base data
	public static int ID = 0;
	public int id;
	public GOProto proto;
	
	// flags
	public boolean passable;
	public int teleportId;
	
	// script
	public ScriptGame script;
	
	// params
	public int param1;
	public int param2;
	public int param3;
	public int param4;

	// current
	public int durability;
	
	public GO(GOProto proto) {		
		this.id = ID++;
		this.go = true;
		this.proto = proto;
		this.passable = proto.passable;
		this.durability = proto.durabilityMax;
	}
	
	@Override
	public void draw(SpriteBatch batch) {
		sprite.draw(batch);
	}
	
	public void use(LocationObject user){
		if(script != null){
			script.execute(user);
		}
	}

	@Override
	public boolean damage(int value) {
		if(durability != 0){
			durability -= value;
			return (durability > 0);
		}
		else{
			return true;
		}
	}
	
	@Override
	public void update(Location loc) {
		
	}

	@Override
	public void dispose() {
		
	}
}