package lua;

import game.script.game.event.Logic;
import game.state.dialog.DialogWrapper;
import game.state.event.Event;
import game.state.location.creature.NPC;

import java.io.File;
import java.util.HashMap;

import lua.lib.LuaLib;

import org.luaj.vm2.LuaError;
import org.luaj.vm2.LuaValue;

import tools.Log;

public class LuaEngine {
	
	private static final String FOLDER_LUA = "data/scripts/";
	private static HashMap<String, LuaScript> loadedScripts;
	
	public LuaEngine() {
		loadedScripts = new HashMap<String, LuaScript>();
	}
	
	public static void load(String title){
		if(title != null && !title.equals("null") && !loadedScripts.containsKey(title)){
			File file = new File(FOLDER_LUA + title + ".lua");
			
			if(file.exists()){
				try {
		    		LuaValue globals = LuaLib.getGlobals();
					globals.get("dofile").call(LuaValue.valueOf(FOLDER_LUA + title + ".lua"));
					LuaValue method = globals.get("execute");
				
					LuaScript script = new LuaScript(method);
					loadedScripts.put(title, script);
					
					Log.debug("load script: " + title);
		    	}
		    	catch (LuaError e){  
		    		e.printStackTrace();
		    	}
			}
		}
	}

	public static boolean isLoaded(String title) {
		return loadedScripts.containsKey(title);
	}

	public static void remove(String title){
		loadedScripts.remove(title);
	}
	
	public void clear(){
		loadedScripts.clear();
	}
	
	public static void execute(String title, Event event){
		LuaScript script = loadedScripts.get(title);
		
		if(script != null){
			script.execute(event);
		}
		
		executeLocationEvent(event);
	}

	public static void execute(DialogWrapper proto, NPC npc) {
		LuaScript script = loadedScripts.get(proto.script());
		script.execute(proto, npc);
	}

	public static void executeLocationEvent(Event event) {
		LuaScript eventScript = loadedScripts.get(Logic.getLocation().proto.eventScript());
		
		if(eventScript != null){
			eventScript.execute(event);
		}
	}
}
