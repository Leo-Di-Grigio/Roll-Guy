package common.net;

import java.io.Serializable;

public class Message implements Serializable {

	// constants
	public static final int NULL = 0;
	public static final int VERSION_CHECK  = 1;
	public static final int VERSION_CHECK_SUCCESS = 2;
	public static final int VERSION_CHECK_ERROR = 3;
	public static final int LOAD_GAME = 4;
	
	// data
	private static final long serialVersionUID = 1L;
	
	public long timestamp;
	public int key;
	public String str;
	
	public Message(int key, String str) {
		this.timestamp = System.currentTimeMillis();
		this.key = key;
		this.str = str;
	}
}