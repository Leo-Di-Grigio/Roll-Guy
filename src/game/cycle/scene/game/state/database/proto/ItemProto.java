package game.cycle.scene.game.state.database.proto;

public class ItemProto {
	
	// proto
	private int id;
	private int sizeX;
	private int sizeY;
	private int mass;
	private int slot;
	private int tex;
	private String title;
	
	private int stackcount;
	private boolean stackble;
	
	public ItemProto(int id, int sizeX, int sizeY, int mass, int slot, int tex, String title, boolean stackble, int stackcount) {
		this.id = id;
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		this.mass = mass;
		this.slot = slot;
		this.tex = tex;
		this.title = title;
		this.stackble = stackble;
		this.stackcount = stackcount;
	}

	public int id(){
		return id;
	}
	
	public int sizeX(){
		return sizeX;
	}
	
	public int sizeY(){
		return sizeY;
	}
	
	public int mass(){
		return mass;
	}
	
	public int slot(){
		return slot;
	}
	
	public String title(){
		return title;
	}
	
	public int tex(){
		return tex;
	}
	
	public int stackcount(){
		return stackcount;
	}
	
	public boolean stackble(){
		return stackble;
	}
}
