package game.cycle.scene.game.world.database.proto;

import game.lua.LuaEngine;

public class GOProto {
	
	// key data
	private int id;
	private int tex;
	private String title;
	
	// params
	private int durabilityMax;
	
	// scripts
	private int fraction;
	
	// container
	private int containerSizeX;
	private int containerSizeY;
	
	// lighting
	private int lightPower;
	
	// flags
	private boolean visible;
	private boolean trigger;
	private boolean teleport;
	private boolean usable;
	private boolean container;
	private boolean passable;
	private boolean los;
	private boolean waypoint;
	private boolean dragble;
	private boolean light;
	
	private String script;
	
	public GOProto(int id, String title, int tex,
			int durabilityMax, int fraction, int containerSizeX, int containerSizeY,
			int lightPower, boolean visible, boolean trigger, boolean teleport, 
			boolean usable, boolean container, boolean passable,  boolean los, 
			boolean waypoint, boolean dragble, boolean light, String script) 
	{
		this.id = id;
		this.title = title;
		this.tex = tex;
		this.durabilityMax = durabilityMax;
		this.fraction = fraction;
		this.containerSizeX = containerSizeX;
		this.containerSizeY = containerSizeY;
		this.lightPower = lightPower;
		this.visible = visible;
		this.trigger = trigger;
		this.teleport = teleport;
		this.usable = usable;
		this.container = container;
		this.passable = passable;
		this.los = los;
		this.waypoint = waypoint;
		this.dragble = dragble;
		this.light = light;
		
		this.setScript(script);
	}

	public int id(){
		return id;
	}
	
	public String title(){
		return title;
	}
	
	public int tex1(){
		return tex;
	}
	
	public int durabilityMax(){
		return durabilityMax;
	}
	
	public int fraction(){
		return fraction;
	}
	
	public int containerSizeX(){
		return containerSizeX;
	}
	
	public int containerSizeY(){
		return containerSizeY;
	}
	
	public int lightPower(){
		return lightPower;
	}
	
	public boolean visible(){
		return visible;
	}
	
	public boolean trigger(){
		return trigger;
	}
	
	public boolean teleport(){
		return teleport;
	}
	
	public boolean usable(){
		return usable;
	}
	
	public boolean container(){
		return container;
	}
	
	public boolean passable(){
		return passable;
	}
	
	public boolean los(){
		return los;
	}
	
	public boolean waypoint(){
		return waypoint;
	}
	
	public boolean dragble(){
		return dragble;
	}
	
	public boolean light(){
		return light;
	}
	
	public String script(){
		return script;
	}
	
	public void setScript(String script){
		if(script != null && !script.equals("null")){
			this.script = script;
			LuaEngine.load(script);
		}
		else{
			this.script = null;
		}
	}
}

