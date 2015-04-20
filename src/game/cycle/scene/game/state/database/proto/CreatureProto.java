package game.cycle.scene.game.state.database.proto;

import java.util.HashSet;
import java.util.Set;

import game.cycle.scene.game.state.location.creature.struct.Stats;

public class CreatureProto {
	
	private int id;
	private int tex;
	private int fraction;
	private int race;
	private Stats stats;
	private String name;
	
	// dialogs
	private int dialogStart;
	private Set<Integer> dialogTopics;
	
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
		
		this.dialogTopics = new HashSet<Integer>();
		for(int topicId: dialogTopics){
			this.dialogTopics.add(topicId);	
		}
		
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
	
	public Integer [] dialogTopics(){
		return dialogTopics.toArray(new Integer[0]);
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

	public void addDialogTopic(int topicId) {
		this.dialogTopics.add(topicId);
	}
}