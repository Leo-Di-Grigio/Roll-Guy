package game.cycle.scene.game.state.database.proto;

public class LocationProto {

	private int id;
	
	private int sizeX;
	private int sizeY;

	private int light;

	private String title;
	private String file;
	private String note;
	
	public LocationProto(int id, String title, String file, String note) {
		this.id = id;
		this.title = title;
		this.file = file;
		this.note = note;
	}
	
	public LocationProto(String title, String file, String note) {
		this.title = title;
		this.file = file;
		this.note = note;
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

	public void setSize(int sizeX, int sizeY) {
		this.sizeX = sizeX;
		this.sizeY = sizeY;
	}

	public void setLight(int light) {
		this.light = light;
	}
}
