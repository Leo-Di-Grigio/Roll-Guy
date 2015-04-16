package game.lua.lib;

import game.cycle.scene.game.world.location.creature.Creature;
import game.cycle.scene.game.world.location.creature.NPC;

public class LuaLibAI {

	public void addEnemy(NPC agent, Creature target){
		agent.aidata.addEnemy(target);
	}
}
