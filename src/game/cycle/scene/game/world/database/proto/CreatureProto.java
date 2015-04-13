package game.cycle.scene.game.world.database.proto;

import game.cycle.scene.game.world.location.creature.struct.Stats;

public class CreatureProto {
	
	private int id;
	private int tex;
	private int fraction;
	private int race;
	private Stats stats;
	private String name;
	
	// dialogs
	private int dialogStart;
	private int [] dialogTopics;
	
	// corpse
	private boolean leaveCorpse;
	
	public CreatureProto() {
		this.name = "";
		this.stats = new Stats(5);
	}
	
	public CreatureProto(int id, String name){
		this.id = id;
		this.name = name;
		this.stats = new Stats(5);
	}
	
	public CreatureProto(int id, String name, int tex, int fraction, int dialogStart, boolean leaveCorpse, int race, int [] dialogTopics, Stats stats) {
		this.id = id;
		this.tex = tex;
		this.fraction = fraction;
		this.race = race;
		this.stats = stats;
		this.name = name;
		this.dialogStart = dialogStart;
		this.dialogTopics = dialogTopics;
		this.leaveCorpse = leaveCorpse;
	}

	public int id(){
		return id;
	}
	
	public int tex(){
		return tex;
	}
	
	public int fraction(){
		return fraction;
	}
	
	public int race(){
		return race;
	}
	
	public Stats stats(){
		return stats;
	}
	
	public String name(){
		return name;
	}
	
	public int dialogStart(){
		return dialogStart;
	}
	
	public int [] dialogTopics(){
		return dialogTopics;
	}
	
	public boolean leaveCorpse(){
		return leaveCorpse;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setTex(int tex) {
		this.tex = tex;
	}

	public void setFraction(int fraction) {
		this.fraction = fraction;
	}
}