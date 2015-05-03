package game.state;

import game.state.database.Database;
import game.state.location.creature.Player;

import java.util.HashMap;
import java.util.Set;

import tools.Const;

public class Globals {

	// global LuaScript variables
	private HashMap<String, Integer> integers;
	private HashMap<String, Boolean> flags;
	private HashMap<String, String>  texts;
	
	// player
	private Player player;
	
	// dialogs
	private HashMap<Integer, Boolean> permanentDialogs;
	
	public Globals() {
		integers = new HashMap<String, Integer>();
		flags = new HashMap<String, Boolean>();
		texts = new HashMap<String, String>();
		player = new Player();
		
		loadDialogs();
	}
	
	// player
	public Player getPlayer(){
		return player;
	}
	
	// variables
	public void setInt(String param, int value) {
		integers.put(param, value);
	}

	public void setFlag(String param, boolean value) {
		flags.put(param, value);
	}
	
	public void setText(String param, String value) {
		texts.put(param, value);
	}

	public int getInt(String param) {
		if(integers.containsKey(param)){
			return integers.get(param);
		}
		else{
			return Const.INVALID_ID;
		}
	}

	public boolean getFlag(String param) {
		if(flags.containsKey(param)){
			return flags.get(param);
		}
		else{
			return false;
		}
	}

	public String getText(String param) {
		if(texts.containsKey(param)){
			return texts.get(param);
		}
		else{
			return null;
		}
	}

	public int removeInt(String param) {
		if(integers.containsKey(param)){
			return integers.remove(param);
		}
		else{
			return Const.INVALID_ID;
		}
	}

	public boolean removeFlag(String param) {
		if(flags.containsKey(param)){
			return flags.remove(param);
		}
		else{
			return false;
		}
	}

	public String removeText(String param) {
		return texts.remove(param);
	}
	
	// permanent dialogs topic
	private void loadDialogs() {
		permanentDialogs = new HashMap<Integer, Boolean>();
		
		Set<Integer> set = Database.getPermanentDialogKeys();
		
		for(Integer key: set){
			permanentDialogs.put(key, false);
		}
	}

	public void permanentTopicLock(int id) {
		permanentDialogs.put(id, false);
	}
	
	public void permanentTopicUnlock(int id) {
		permanentDialogs.put(id, true);
	}
	
	public boolean permanentTopicUnlocked(int id){
		if(permanentDialogs.containsKey(id)){
			return permanentDialogs.get(id);
		}
		else{
			return false;
		}
	}
}
