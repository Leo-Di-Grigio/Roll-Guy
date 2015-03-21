package game.cycle.scene.game.world.go;

public class GOProto {
	
	// key data
	public int id;
	public String title;
	
	// sprite
	public int texure_1;
	public int texure_2;
	
	// flags
	public boolean visible;
	public boolean trigger;
	public boolean teleport;
	public boolean usable;
	public boolean container;
	public boolean passable;
	
	// params
	public int durabilityMax;
	
	// scripts
	public int scriptId_1;
	public int fraction;
	
	public String toString(){
		String str = "";
		str += "id: " + id;
		str += ", title: " + title;
		str += ", texId1: " + texure_1;
		str += ", texId2: " + texure_2;
		str += ", visible: " + visible;
		str += ", trigger: " + trigger;
		str += ", teleport: " + teleport;
		str += ", usable: " + usable;
		str += ", container: " + container;
		str += ", passable: " + passable;
		str += ", durability: " + durabilityMax;
		str += ", scriptId_1: " + scriptId_1;
		
		return str;
	}
}
