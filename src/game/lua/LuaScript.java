package game.lua;

import game.cycle.scene.game.world.dialog.DialogWrapper;
import game.cycle.scene.game.world.event.LocationEvent;
import game.cycle.scene.game.world.location.LocationObject;
import game.cycle.scene.game.world.location.creature.NPC;
import game.cycle.scene.game.world.location.creature.Player;
import game.cycle.scene.game.world.location.go.GO;
import game.script.game.event.GameEvents;

import org.luaj.vm2.LuaError;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;

public class LuaScript {
	
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
	
	protected void execute(LocationEvent event){
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
	    		LuaValue player = CoerceJavaToLua.coerce(GameEvents.getPlayer());
	    		LuaValue target = CoerceJavaToLua.coerce(npc);
	    		method.call(dialog, player, target);
	    	}
	    }
	    catch (LuaError e){  
	    	e.printStackTrace();
	    }
	}
}
