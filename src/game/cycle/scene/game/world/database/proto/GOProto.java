package game.cycle.scene.game.world.database.proto;

public class GOProto {
	
	// key data
	private int id;
	private String title;
	
	// sprite
	private int tex1;
	private int tex2;
	
	// params
	private int durabilityMax;
	
	// scripts
	private int scriptId;
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
	
	public GOProto(int id, String title, int tex1, int tex2,
			int durabilityMax, int scriptId, int fraction,
			int containerSizeX, int containerSizeY, int lightPower,
			boolean visible, boolean trigger, boolean teleport, 
			boolean usable, boolean container, boolean passable, 
			boolean los, boolean waypoint, boolean dragble, boolean light) 
	{
		this.id = id;
		this.title = title;
		this.tex1 = tex1;
		this.tex2 = tex2;
		this.durabilityMax = durabilityMax;
		this.scriptId = scriptId;
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
	}

	public int id(){
		return id;
	}
	
	public String title(){
		return title;
	}
	
	public int tex1(){
		return tex1;
	}
	
	public int tex2(){
		return tex2;
	}
	
	public int durabilityMax(){
		return durabilityMax;
	}
	
	public int scriptId(){
		return scriptId;
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
}
