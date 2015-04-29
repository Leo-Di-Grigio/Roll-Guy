package server.logic;

import java.util.HashMap;

import com.badlogic.gdx.math.Vector2;

import common.net.Message;
import server.Client;
import server.ClientPool;

public class State {

	// data
	private HashMap<Integer, Player> players;
	
	// tools
	private ClientPool clients;
	private Vector2 tmpVector2;
	
	private boolean inited;
	
	public State(ClientPool clients) {
		// clients
		this.clients = clients;		
		this.clients.sendToAll(new Message(Message.SERVER_LOAD_GAME));
		
		// data
		players = new HashMap<Integer, Player>();
		
		// tools
		tmpVector2 = new Vector2();
		
		for(Client client: clients.value()){
			addPlayer(client.id);
		}
		
		for(Integer id: players.keySet()){
			broadAllData(id);
		}
		
		this.inited = true;
	}

	private void broadAllData(int id) {
		for(Player player: players.values()){
			if(id == player.id){
				clients.send(id, new Message(Message.SERVER_ADD_PLAYER_FULL, id, player.getDataFull()));
			}
			else{
				clients.sendToAll(new Message(Message.SERVER_ADD_PLAYER, player.id, player.getData()));
			}
		}
	}

	public void addPlayer(int id) {
		players.put(id, new Player(id));
		
		if(inited){
			broadAllData(id);
			clients.send(id, new Message(Message.SERVER_LOAD_GAME));
		}
	}

	public void playerMove(int id, Message msg) {
		tmpVector2.set(msg.fx, msg.fy);
		Player player = players.get(id);
		
		if(player.move(tmpVector2)){
			clients.sendToAll(new Message(Message.SERVER_PLAYER_MOVE, id, player.pos.x, player.pos.y));
		}
	}
}
