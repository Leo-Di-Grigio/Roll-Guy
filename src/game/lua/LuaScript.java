package game.lua;

import game.cycle.scene.game.state.dialog.DialogWrapper;
import game.cycle.scene.game.state.event.Event;
import game.cycle.scene.game.state.location.LocationObject;
import game.cycle.scene.game.state.location.creature.NPC;
import game.cycle.scene.game.state.location.creature.Player;
import game.cycle.scene.game.state.location.go.GO;
import game.script.game.event.Logic;

import org.luaj.vm2.LuaError;
import org.luaj.vm2.LuaTable;
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
	      		LuaTable table = new LuaTable();
	    		table.set(0, LuaValue.valueOf(event.type));
	    		table.set(1, LuaValue.valueOf(event.context));
	    		
	    		if(event.source != null){
	    			LocationObject source = event.source;
	    			
	    			if(source.isGO()){
	    				GO go = (GO)source;
	    				table.set(2, CoerceJavaToLua.coerce(go));	
	    			}
	    			else if(source.isCreature()){
	    				if(source.isPlayer()){
	    					Player player = (Player)source;
	    					table.set(2, CoerceJavaToLua.coerce(player));
	    				}
	    				else if(source.isNPC()){
	    					NPC npc = (NPC)source;
	    					table.set(2, CoerceJavaToLua.coerce(npc));
	    				}
	    			}
	    		}
	    		
	    		if(event.target != null){
	    			LocationObject source = event.target;
	    			
	    			if(source.isGO()){
	    				GO go = (GO)source;
	    				table.set(3, CoerceJavaToLua.coerce(go));	
	    			}
	    			else if(source.isCreature()){
	    				if(source.isPlayer()){
	    					Player player = (Player)source;
	    					table.set(3, CoerceJavaToLua.coerce(player));
	    				}
	    				else if(source.isNPC()){
	    					NPC npc = (NPC)source;
	    					table.set(3, CoerceJavaToLua.coerce(npc));
	    				}
	    			}
	    		}
	    		
	    		if(!method.isnil()){
	    			method.call(table);
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
