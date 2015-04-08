package game.cycle.scene.game.world.location;

import game.cycle.scene.game.world.location.creature.Creature;
import game.cycle.scene.game.world.location.go.GO;

public class Terrain {
	
	public TerrainProto proto;
	public Creature creature;
	public GO go;
	
	// fog
	public boolean explored;
	public boolean viewed;
	public boolean updated;
	
	// lighting
	public int lighting; // [0; 100]
	
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
