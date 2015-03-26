package game.cycle.scene.game.world.go;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import game.cycle.scene.game.world.LocationObject;
import game.cycle.scene.game.world.creature.items.Inventory;
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

	// triggers
	public int [] triggers;
	public int [] scripts;
	public int [] params1;
	public int [] params2;
	public int [] params3;
	public int [] params4;
	
	// current
	public int durability;
	
	// container
	public Inventory inventory;
	
	// los
	public boolean losBlock;
	
	public GO(GOProto proto) {
		super(proto.fraction);
		this.id = ID++;
		this.go = true;
		this.proto = proto;
		this.passable = proto.passable;
		this.durability = proto.durabilityMax;
		
		// triggers
		triggers = new int[4];
		scripts = new int[4];
		params1 = new int[4];
		params2 = new int[4];
		params3 = new int[4];
		params4 = new int[4];
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
	public void dispose() {
		
	}

	@Override
	public void update(Location loc, OrthographicCamera camera) {

	}
}