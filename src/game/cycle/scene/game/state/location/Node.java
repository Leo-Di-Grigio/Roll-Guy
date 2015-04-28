package game.cycle.scene.game.state.location;

import game.cycle.scene.game.state.database.proto.NodeProto;

public class Node {
	
	public NodeProto proto;
	
	// fog
	public boolean explored;
	public boolean viewed;
	public boolean updated;
	
	// lighting
	public int lighting; // [0; 100]
	
	public Node() {
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
