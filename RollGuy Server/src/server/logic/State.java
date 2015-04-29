package server.logic;

import common.net.Message;
import server.ClientPool;

public class State {

	private ClientPool clients;

	public State(ClientPool clients) {
		this.clients = clients;		
		this.clients.sendToAll(new Message(Message.LOAD_GAME, null));
	}
}
