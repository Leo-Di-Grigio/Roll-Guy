package game.lua;

import game.tools.Log;

import org.luaj.vm2.LuaError;
import org.luaj.vm2.LuaValue;

public class LuaScript {

	private String title;
	private LuaValue method;
	
	public LuaScript(String title, LuaValue method) {
		this.title = title;
		this.method = method;
	}
	
	public void execute(){
	    try {
	    	if(!method.isnil()){
	    		method.call();
	    	}
	    	else{
	    		Log.err("script " + title + ".lua does not exist");
	    	}
	    }
	    catch (LuaError e){  
	    	e.printStackTrace();
	    } 
	}
}
