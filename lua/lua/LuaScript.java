package lua;

import game.script.game.event.Logic;
import game.state.dialog.DialogWrapper;
import game.state.event.Event;
import game.state.location.creature.NPC;

import org.luaj.vm2.LuaError;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;

class LuaScript {
	
	private LuaValue method;
	
	protected LuaScript(LuaValue method) {
		this.method = method;
	}
	
	protected void execute(){
	    try {
	    	if(!method.isnil()){
	    		method.call();
	    	}
	    }
	    catch (LuaError e){  
	    	e.printStackTrace();
	    }
	}
	
	protected void execute(Event event){
		if(event != null){
		    try {
	    		LuaValue type = LuaValue.valueOf(event.type);
	    		LuaValue source = null;
	    		LuaValue target = null;
	    		
	    		if(event.source != null){
	    			source = CoerceJavaToLua.coerce(event.source);
	    		}
	    		
	    		if(event.target != null){
	    			target = CoerceJavaToLua.coerce(event.target);
	    		}
	    		
	    		if(!method.isnil()){
	    			method.call(type, source, target);
	    		}
		    }
		    catch (LuaError e){  
		    	e.printStackTrace();
		    } 
		}
	}

	public void execute(DialogWrapper proto, NPC npc) {
	    try {
	    	if(!method.isnil()){
	    		LuaValue dialog = CoerceJavaToLua.coerce(proto);
	    		LuaValue player = CoerceJavaToLua.coerce(Logic.getPlayer());
	    		LuaValue target = CoerceJavaToLua.coerce(npc);
	    		method.call(dialog, player, target);
	    	}
	    }
	    catch (LuaError e){  
	    	e.printStackTrace();
	    }
	}
}
