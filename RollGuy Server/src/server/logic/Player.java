package server.logic;

import com.badlogic.gdx.math.Vector2;

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

	public String getData() {
		return "just player";
	}

	public String getDataFull() {
		return "just player int";
	}
}
