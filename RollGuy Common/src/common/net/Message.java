package common.net;

import java.io.Serializable;

public class Message implements Serializable {

	// constants
	public static final int NULL = 0;
	public static final int CLIENT_VERSION_CHECK  = 1;
	public static final int SERVER_VERSION_CHECK_SUCCESS = 2;
	public static final int SERVER_VERSION_CHECK_ERROR = 3;
	public static final int SERVER_LOAD_GAME = 4;
	public static final int CLIENT_PLAYER_MOVE = 5;
	public static final int SERVER_PLAYER_MOVE = 6;
	public static final int SERVER_ADD_PLAYER = 7;
	public static final int SERVER_ADD_PLAYER_FULL = 8;
	
	// data
	private static final long serialVersionUID = 1L;
	
	public long timestamp;
	public int key;
	public int ix;
	public float fx;
	public float fy;
	public String str;
	
	public Message(int key) {
		this.timestamp = System.currentTimeMillis();
		this.key = key;
	}
	
	public Message(int key, String str) {
		this.timestamp = System.currentTimeMillis();
		this.key = key;
		this.str = str;
	}
	
	public Message(int key, float fx, float fy){
		this.timestamp = System.currentTimeMillis();
		this.key = key;
		this.fx = fx;
		this.fy = fy;
	}

	public Message(int key, int ix, float fx, float fy){
		this.timestamp = System.currentTimeMillis();
		this.key = key;
		this.ix = ix;
		this.fx = fx;
		this.fy = fy;
	}

	public Message(int key, int ix, String str) {
		this.timestamp = System.currentTimeMillis();
		this.key = key;
		this.ix = ix;
		this.str = str;
	}
}