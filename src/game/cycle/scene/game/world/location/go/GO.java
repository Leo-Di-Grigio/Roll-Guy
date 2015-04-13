package game.cycle.scene.game.world.location.go;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import game.cycle.scene.game.world.database.GameConst;
import game.cycle.scene.game.world.database.proto.GOProto;
import game.cycle.scene.game.world.event.LocationEvent;
import game.cycle.scene.game.world.event.trigger.Trigger;
import game.cycle.scene.game.world.location.Location;
import game.cycle.scene.game.world.location.LocationObject;
import game.cycle.scene.game.world.location.creature.Player;
import game.lua.LuaEngine;
import game.resources.Resources;

public class GO extends LocationObject {
	
	// base data
	public GOProto proto;
	
	// flags
	public boolean passable;
	public int teleportId;

	// params
	public int param1;
	public int param2;
	public int param3;
	public int param4;

	// triggers
	public Trigger [] triggers;
	public int [] triggerType;
	public int [] triggerParam;
	public int [] scripts;
	public int [] params1;
	public int [] params2;
	public int [] params3;
	public int [] params4;
	
	// current data
	private int durability;
	private boolean use;
	private boolean los;
	
	public GO(int guid, GOProto proto){
		super(guid, proto.fraction());
		
		this.go = true;
		this.proto = proto;
		this.passable = proto.passable();
		this.durability = proto.durabilityMax();
		
		// triggers
		triggers = new Trigger[GameConst.GO_TRIGGERS_COUNT];
		triggerType = new int[GameConst.GO_TRIGGERS_COUNT];
		triggerParam = new int[GameConst.GO_TRIGGERS_COUNT];
		scripts = new int[GameConst.GO_TRIGGERS_COUNT];
		params1 = new int[GameConst.GO_TRIGGERS_COUNT];
		params2 = new int[GameConst.GO_TRIGGERS_COUNT];
		params3 = new int[GameConst.GO_TRIGGERS_COUNT];
		params4 = new int[GameConst.GO_TRIGGERS_COUNT];
	}
	
	@Override
	public void draw(SpriteBatch batch) {
		sprite.draw(batch);
	}
	
	public void event(LocationEvent event) {
		LuaEngine.execute(proto.script(), event);
	}

    public void setTexture(int tex){
    	this.sprite.setTexture(Resources.getTex(tex));
    }
    
    public boolean isPassable(){
    	return this.passable;
    }
    
    public void setPassable(boolean value){
    	this.passable = value;
    }
    
    public boolean isLos(){
    	return this.los;
    }
    
    public void setLos(boolean value){
    	this.los = value;
    }
    
    public boolean isUsed(){
    	return use;
    }
    
    public void setUse(boolean value){
    	this.use = value;
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
	public void update(Location loc, OrthographicCamera camera, Player player, boolean losMode) {

	}
}