package game.cycle.scene.game.state.dialog;

import game.cycle.scene.game.state.database.proto.DialogProto;

public class DialogWrapper {

	private DialogProto proto;
	
	private String textBegin;
	private String textEnd;
	
	public DialogWrapper(DialogProto proto){
		this.proto = proto;	
	}
	
	public int id(){
		return proto.id();
	}
	
	public String title(){
		return proto.title();
	}
	
	public String script(){
		return proto.script();
	}
	
	public String textBegin(){
		if(textBegin == null){
			return proto.textBegin();
		}
		else{
			return textBegin;
		}
	}
	
	public String textEnd(){
		if(textEnd == null){
			return proto.textEnd();
		}
		else{
			return textEnd;
		}
	}
	
	// set
	public void setTextBegin(String text){
		this.textBegin = text;
	}
	
	public void setTextEnd(String text){
		this.textEnd = text;
	}
}
