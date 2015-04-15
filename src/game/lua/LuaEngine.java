package game.lua;

import game.cycle.scene.game.world.dialog.DialogWrapper;
import game.cycle.scene.game.world.event.LocationEvent;
import game.cycle.scene.game.world.location.creature.NPC;
import game.lua.lib.LuaLib;
import game.tools.Log;

import java.io.File;
import java.util.HashMap;

import org.luaj.vm2.LuaError;
import org.luaj.vm2.LuaValue;

public class LuaEngine {
	
	private static final String folderLua = "data/scripts/";
	private static HashMap<String, LuaScript> loadedScripts;
	
	public LuaEngine() {
		loadedScripts = new HashMap<String, LuaScript>();
	}
	
	public static void load(String title){
		if(title != null && !title.equals("null") && !loadedScripts.containsKey(title)){
			File file = new File(folderLua + title + ".lua");
			
			if(file.exists()){
				try {
		    		LuaValue globals = LuaLib.getGlobals();
					globals.get("dofile").call(LuaValue.valueOf(folderLua + title + ".lua"));
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
	
	public static void clear(){
		loadedScripts.clear();
	}
	
	public static void execute(String title, LocationEvent event){
		LuaScript script = loadedScripts.get(title);
		if(script != null){
			script.execute(event);
		}
	}

	public static void execute(DialogWrapper proto, NPC npc) {
		LuaScript script = loadedScripts.get(proto.script());
		script.execute(proto, npc);
	}
}
