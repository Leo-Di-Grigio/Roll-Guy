package game.cycle.scene.game.state.database.proto;

public class DialogProto {

	private int id;
	private int permanentId;
	private String title;
	private String textBegin;
	private String textEnd;
	private String script;
	
	public DialogProto(int id, int permanentId, String title, String textBegin, String textEnd, String script) {
		this.id = id;
		this.permanentId = permanentId;
		this.title = title;
		this.textBegin = textBegin;
		this.textEnd = textEnd;
		this.script = script;
	}

	public int id(){
		return id;
	}
	
	public int permanentId(){
		return permanentId;
	}
	
	public String title(){
		return title;
	}
	
	public String textBegin(){
		return textBegin;
	}
	
	public String textEnd(){
		return textEnd;
	}
	
	public String script(){
		return script;
	}
}
