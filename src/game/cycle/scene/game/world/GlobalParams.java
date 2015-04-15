package game.cycle.scene.game.world;

import java.util.HashMap;

import org.luaj.vm2.LuaValue;

public class GlobalParams {

	private static HashMap<String, Integer> integers;
	private static HashMap<String, Boolean> flags;
	private static HashMap<String, String> texts;
	
	public GlobalParams() {
		integers = new HashMap<String, Integer>();
		flags = new HashMap<String, Boolean>();
		texts = new HashMap<String, String>();
	}
	
	public static void setInt(String param, int value) {
		integers.put(param, value);
	}

	public static void setFlag(String param, boolean value) {
		flags.put(param, value);
	}
	
	public static void setText(String param, String value) {
		texts.put(param, value);
	}

	public static LuaValue getInt(String param) {
		if(integers.containsKey(param)){
			return LuaValue.valueOf(integers.get(param).intValue());
		}
		else{
			return LuaValue.NIL;
		}
	}

	public static LuaValue getFlag(String param) {
		if(flags.containsKey(param)){
			return LuaValue.valueOf(flags.get(param).booleanValue());
		}
		else{
			return LuaValue.NIL;
		}
	}

	public static LuaValue getText(String param) {
		if(texts.containsKey(param)){
			return LuaValue.valueOf(texts.get(param));
		}
		else{
			return LuaValue.NIL;
		}
	}

	public static LuaValue removeInt(String param) {
		if(integers.containsKey(param)){
			return LuaValue.valueOf(integers.remove(param).intValue());
		}
		else{
			return LuaValue.NIL;
		}
	}

	public static LuaValue removeFlag(String param) {
		if(flags.containsKey(param)){
			return LuaValue.valueOf(flags.remove(param).booleanValue());
		}
		else{
			return LuaValue.NIL;
		}
	}

	public static LuaValue removeText(String param) {
		if(texts.containsKey(param)){
			return LuaValue.valueOf(texts.remove(param));
		}
		else{
			return LuaValue.NIL;
		}
	}
}
