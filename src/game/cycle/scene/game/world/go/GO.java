package game.cycle.scene.game.world.go;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import game.cycle.scene.game.world.LocationObject;
import game.cycle.scene.game.world.creature.items.Inventory;
import game.cycle.scene.game.world.database.GameConst;
import game.cycle.scene.game.world.event.LocationEvent;
import game.cycle.scene.game.world.event.trigger.Trigger;
import game.cycle.scene.game.world.event.trigger.TriggersLoader;
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
	public Trigger [] triggers;
	public int [] triggerType;
	public int [] triggerParam;
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
		triggers = new Trigger[GameConst.goTriggersCount];
		triggerType = new int[GameConst.goTriggersCount];
		triggerParam = new int[GameConst.goTriggersCount];
		scripts = new int[GameConst.goTriggersCount];
		params1 = new int[GameConst.goTriggersCount];
		params2 = new int[GameConst.goTriggersCount];
		params3 = new int[GameConst.goTriggersCount];
		params4 = new int[GameConst.goTriggersCount];
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

	public void event(LocationEvent event, int param) {
		if(event.type == LocationEvent.Type.TRIGGER){
			for(int i = 0; i < GameConst.goTriggersCount; ++i){
				if(triggers[i] != null){
					triggers[i].execute(event, param);
				}
			}
		}
	}
	
	public void loadTriggers() {
		for(int i = 0; i < GameConst.goTriggersCount; ++i){
			triggers[i] = TriggersLoader.getTrigger(this, triggerType[i], triggerParam[i], scripts[i], params1[i], params2[i], params3[i], params4[i]);
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