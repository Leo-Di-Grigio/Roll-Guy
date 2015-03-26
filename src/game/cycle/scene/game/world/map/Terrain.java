package game.cycle.scene.game.world.map;

import game.cycle.scene.game.world.creature.Creature;
import game.cycle.scene.game.world.go.GO;

public class Terrain {
	
	public TerrainProto proto;
	public Creature creature;
	public GO go;
	
	// fog
	public boolean explored;
	public boolean viewed;
	public boolean updated;
	
	public Terrain() {
		explored = false;
		viewed = false;
	}
	
	public void explore() {
		explored = true;
		viewed = true;
	}

	public void hide() {
		if(explored){
			viewed = false;
		}
	}
}
