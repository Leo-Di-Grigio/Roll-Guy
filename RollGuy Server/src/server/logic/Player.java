package server.logic;

import com.badlogic.gdx.math.Vector2;
import common.net.Message;

class Player {

	public Vector2 pos;
	public int id;
	
	public Player(int id) {
		this.id = id;
		pos = new Vector2();
	}
	
	public boolean move(Vector2 tmp) {
		this.pos.add(tmp);
		return true;
	}

	public Message getData() {
		return new Message(Message.SERVER_ADD_PLAYER, id, pos.x, pos.y);
	}

	public Message getDataFull() {
		return new Message(Message.SERVER_ADD_PLAYER_FULL, id, pos.x, pos.y);
	}
}
