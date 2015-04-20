package game.cycle.scene.game.state.database.proto;

import game.lua.LuaEngine;

public class LocationProto {

	private int id;
	
	private int sizeX;
	private int sizeY;

	private int light;

	private String title;
	private String file;
	private String note;
	
	private String eventScript;
	
	public LocationProto(int id, String title, String file, String note, String eventScript) {
		this.id = id;
		this.title = title;
		this.file = file;
		this.note = note;
		setEventScript(eventScript);
	}
	
	public LocationProto(String title, String file, String note, String eventScript) {
		this.title = title;
		this.file = file;
		this.note = note;
		setEventScript(eventScript);
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
	
	public int light(){
		return light;
	}
	
	public String title(){
		return title;
	}
	
	public String file(){
		return file;
	}
	
	public String note(){
		return note;
	}
	
	public String eventScript(){
		return eventScript;
	}

	public void setSize(int sizeX, int sizeY) {
		this.sizeX = sizeX;
		this.sizeY = sizeY;
	}

	public void setLight(int light) {
		this.light = light;
	}
	
	public void setEventScript(String eventScript){
		this.eventScript = eventScript;
		LuaEngine.load(eventScript);
	}
}
