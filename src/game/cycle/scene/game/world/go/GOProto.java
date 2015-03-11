package game.cycle.scene.game.world.go;

public class GOProto {
	
	// key data
	public int id;
	public String title;
	
	// sprite
	public int texure;
	
	// flags
	public boolean visible;
	public boolean trigger;
	public boolean teleport;
	public boolean usable;
	public boolean container;
	public boolean passable;
	
	// params
	public int durabilityMax;
	
	public String toString(){
		String str = "";
		str += "id: " + id;
		str += ", title: " + title;
		str += ", texId: " + texure;
		str += ", visible: " + visible;
		str += ", trigger: " + trigger;
		str += ", teleport: " + teleport;
		str += ", usable: " + usable;
		str += ", container: " + container;
		str += ", passable: " + passable;
		str += ", durability: " + durabilityMax;
		
		return str;
	}
}
