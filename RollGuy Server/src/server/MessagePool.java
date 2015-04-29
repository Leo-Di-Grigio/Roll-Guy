package server;

import server.logic.ServerLogic;
import common.net.Message;
import common.tools.Log;

public class MessagePool {

	private ServerLogic logic;
	
	public MessagePool(ServerLogic logic) {
		this.logic = logic;
	}

	public void read(int id, Message msg) {
		Log.msg("<- ID:" + msg.timestamp + ":" + msg.key + ":" + msg.str);
		logic.message(id, msg);
	}
}
