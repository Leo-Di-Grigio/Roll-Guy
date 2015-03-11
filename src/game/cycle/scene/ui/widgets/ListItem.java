package game.cycle.scene.ui.widgets;

import java.util.ArrayList;

public class ListItem {
	
	protected String text;
	protected ArrayList<Boolean> hidenMask;
	protected ArrayList<String> data;
	
	// textgen
	protected String formatter = "; ";
	
	public ListItem(ArrayList<String> data) {
		this.data = data;
		this.hidenMask = new ArrayList<Boolean>();
		compileText();
	}
	
	public ListItem(ArrayList<String> data, ArrayList<Boolean> hidenMask) {
		this.data = data;
		this.hidenMask = hidenMask;
		compileText();
	}
	
	public void set(int collumn, boolean hide, String text){
		if(collumn < data.size()){
			data.set(collumn, text);
		}
		if(collumn < hidenMask.size()){
			hidenMask.set(collumn, hide);
		}
		compileText();
	}
	
	public String get(int collumn){
		if(collumn < data.size()){
			return data.get(collumn);
		}
		else{
			return null;
		}
	}
	
	public int getCollumns(){
		return data.size(); 
	}
	
	public void setFormatter(String formatter){
		this.formatter = formatter;
		compileText();
	}
	
	private void compileText() {
		String tmp = "";
		
		for(int i = 0; i < data.size(); ++i){
			if(i < hidenMask.size()){
				if(!hidenMask.get(i)){
					tmp += data.get(i) + formatter; 
				}
			}
			else{
				tmp += data.get(i) + formatter;
			}
		}
		
		this.text = tmp;
	}

	@Override
	public String toString(){
		return text;
	}
}
