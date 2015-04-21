package game.cycle.scene.game.state;

import game.cycle.scene.game.state.location.creature.Player;
import game.tools.Const;

import java.util.HashMap;

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

	public int getInt(String param) {
		if(integers.containsKey(param)){
			return integers.get(param);
		}
		else{
			return Const.INVALID_ID;
		}
	}

	public boolean getFlag(String param) {
		if(flags.containsKey(param)){
			return flags.get(param);
		}
		else{
			return false;
		}
	}

	public String getText(String param) {
		if(texts.containsKey(param)){
			return texts.get(param);
		}
		else{
			return null;
		}
	}

	public int removeInt(String param) {
		if(integers.containsKey(param)){
			return integers.remove(param);
		}
		else{
			return Const.INVALID_ID;
		}
	}

	public boolean removeFlag(String param) {
		if(flags.containsKey(param)){
			return flags.remove(param);
		}
		else{
			return false;
		}
	}

	public String removeText(String param) {
		return texts.remove(param);
	}
}
