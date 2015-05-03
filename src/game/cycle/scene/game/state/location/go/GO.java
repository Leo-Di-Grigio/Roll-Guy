package game.cycle.scene.game.state.location.go;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool.PooledEffect;

import game.cycle.scene.game.state.database.proto.GOProto;
import game.cycle.scene.game.state.event.Event;
import game.cycle.scene.game.state.location.Location;
import game.cycle.scene.game.state.location.LocationObject;
import game.cycle.scene.game.state.location.creature.Player;
import game.lua.LuaEngine;
import game.resources.Effect;
import game.resources.Resources;
import game.tools.Const;

public class GO extends LocationObject {
	
	// base data
	public GOProto proto;
	
	// flags
	public boolean passable;
	public int teleportId;

	// params
	private int param1;
	private int param2;
	private int param3;
	private int param4;

	// triggers
	public String script;
	
	// current data
	private int durability;
	private boolean use;
	private boolean los;
	
	// partical effect
	private PooledEffect effect;
	
	public GO(int guid, GOProto proto){
		super(guid, proto.fraction());
		
		this.go = true;
		this.proto = proto;
		this.passable = proto.passable();
		this.durability = proto.durabilityMax();
	}

	public void showEffect(){
		if(effect == null && proto.partical() != Effect.NULL){
			effect = Resources.getEffect(proto.partical());
			effect.setPosition(sprite.getX() + Const.TILE_SIZE/2, sprite.getY() + Const.TILE_SIZE/2);
			
			if(effect.isComplete()){
				effect.start();
			}
		}
	}
	
	public void hideEffect(){
		if(effect != null){
			effect.free();
			effect = null;
		}
	}
	
	@Override
	public void draw(SpriteBatch batch) {
		batch.draw(sprite, sprite.getX(), sprite.getY(), proto.sizeX()*Const.TILE_SIZE, proto.sizeY()*Const.TILE_SIZE);
		
		if(effect != null){
			effect.draw(batch, Gdx.graphics.getDeltaTime());
		}
	}
	
	public void event(Event event) {
		LuaEngine.execute(script, event);
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
    
    public int param1(){
    	return param1;
    }
    
    public int param2(){
    	return param2;
    }
    
    public int param3(){
    	return param3;
    }
    
    public int param4(){
    	return param4;
    }
    
    public void setParam1(int value){
    	this.param1 = value;
    }
    
    public void setParam2(int value){
    	this.param2 = value;
    }
    
    public void setParam3(int value){
    	this.param3 = value;
    }
    
    public void setParam4(int value){
    	this.param4 = value;
    }
    
    public boolean isBroken(){
    	if(durability <= 0){
    		return true;
    	}
    	else{
    		return false;
    	}
    }
    
    @Override
    public void translate(float dx, float dy) {
    	super.translate(dx, dy);
    	if(effect != null){
    		effect.setPosition(sprite.getX() + Const.TILE_SIZE/2, sprite.getY() + Const.TILE_SIZE/2);
    	}
    }
    
	@Override
	public boolean damage(int value) {
		if(durability != 0){
			durability -= value;
			
			if(durability <= 0 && effect != null){
				hideEffect();
			}
			
			return (durability > 0);
		}
		else{
			return true;
		}
	}
	
	@Override
	public void kill() {
		this.durability = 0;
		hideEffect();
	}
	
	@Override
	public void dispose() {
		
	}

	@Override
	public void update(Location loc, OrthographicCamera camera, Player player, boolean losMode) {
		if(loc.inBound(getPosition().x, getPosition().y) && durability > 0){
			showEffect();
		}
		else{
			hideEffect();
		}
	}
}