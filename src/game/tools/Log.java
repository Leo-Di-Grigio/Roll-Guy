package game.tools;

import game.Config;
import game.cycle.scene.ui.UI;

public class Log {

	public synchronized static void msg(String text){
		System.out.println(text);
		UI.addConsoleLine(text);
	}
	
	public synchronized static void debug(String text){
		if(Config.logDebug){
			text = "DEBUG: " + text;
			
			if(Config.javaDebug){
				System.out.println(text);
			}
			UI.addConsoleLine(text);
		}
	}
	
	public synchronized static void ai(String text){
		if(Config.logDebug){
			text = "AI: " + text;
			
			if(Config.javaDebug){
				System.out.println(text);
			}
			UI.addConsoleLine(text);
		}
	}
	
	public synchronized static void err(String text){
		if(Config.logErrors){
			text = "ERR: " + text;
			
			if(Config.javaDebug){
				System.err.println();
			}
			
			UI.addConsoleLine(text);
		}
	}

	public static void luaErr(String text) {
		if(Config.logLuaErrors){
			text = "LUA-ERR: " + text;
			
			if(Config.javaDebug){
				System.err.println();
			}
			
			UI.addConsoleLine(text);
		}
	}
}
