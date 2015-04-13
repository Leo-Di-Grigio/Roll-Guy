package game.lua;

import game.tools.Log;

import java.util.HashMap;

import org.luaj.vm2.LuaError;
import org.luaj.vm2.LuaValue;

public class LuaEngine {
	
	private static final String folderLua = "data/scripts/";
	private static HashMap<String, LuaScript> loadedScripts;
	
	public LuaEngine() {
		loadedScripts = new HashMap<String, LuaScript>();
		
		// test
		load("test");
		execute("test");
	}
	
	public static void load(String title){
	    try {
	    	LuaValue globals = LuaEngineGlobals.getGlobals();
			globals.get("dofile").call(LuaValue.valueOf(folderLua + title + ".lua"));
			LuaValue method = globals.get("execute");
			
			LuaScript script = new LuaScript(title, method);
			loadedScripts.put(title, script);
	    }
	    catch (LuaError e){  
	         e.printStackTrace();
	    } 
	}
	
	public static void execute(String title){
		LuaScript script = loadedScripts.get(title);
		if(script != null){
			script.execute();
		}
		else{
			Log.luaErr("LUA script \'" + title + "\' does not inited");
		}
	}
	
	public static void remove(String title){
		loadedScripts.remove(title);
	}
	
	public static void clear(){
		loadedScripts.clear();
	}
}
