package game.cycle.scene.game.state;

import game.cycle.scene.game.state.location.creature.Player;

import java.util.HashMap;

import org.luaj.vm2.LuaValue;

public class Globals {

	private HashMap<String, Integer> integers;
	private HashMap<String, Boolean> flags;
	private HashMap<String, String> texts;
	
	private Player player;
	
	public Globals() {
		integers = new HashMap<String, Integer>();
		flags = new HashMap<String, Boolean>();
		texts = new HashMap<String, String>();
		player = new Player();
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

	public LuaValue getInt(String param) {
		if(integers.containsKey(param)){
			return LuaValue.valueOf(integers.get(param).intValue());
		}
		else{
			return LuaValue.NIL;
		}
	}

	public LuaValue getFlag(String param) {
		if(flags.containsKey(param)){
			return LuaValue.valueOf(flags.get(param).booleanValue());
		}
		else{
			return LuaValue.NIL;
		}
	}

	public LuaValue getText(String param) {
		if(texts.containsKey(param)){
			return LuaValue.valueOf(texts.get(param));
		}
		else{
			return LuaValue.NIL;
		}
	}

	public LuaValue removeInt(String param) {
		if(integers.containsKey(param)){
			return LuaValue.valueOf(integers.remove(param).intValue());
		}
		else{
			return LuaValue.NIL;
		}
	}

	public LuaValue removeFlag(String param) {
		if(flags.containsKey(param)){
			return LuaValue.valueOf(flags.remove(param).booleanValue());
		}
		else{
			return LuaValue.NIL;
		}
	}

	public LuaValue removeText(String param) {
		if(texts.containsKey(param)){
			return LuaValue.valueOf(texts.remove(param));
		}
		else{
			return LuaValue.NIL;
		}
	}
}
