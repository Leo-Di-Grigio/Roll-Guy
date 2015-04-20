package game.cycle.scene.game.state.database.proto;

public class NodeProto {

	private int id;
	private int tex;
	private String title;
	
	// flags
	private boolean passable;
	private boolean los;
	
	public NodeProto(int id, int tex, String title, boolean passable, boolean los) {
		this.id = id;
		this.tex = tex;
		this.title = title;
		
		// flags
		this.passable = passable;
		this.los = los;
	}
	
	public int id(){
		return id;
	}
	
	public int tex(){
		return tex;
	}
	
	public boolean passable(){
		return passable;
	}
	
	public boolean los(){
		return los;
	}
	
	public String title(){
		return title;
	}
}
