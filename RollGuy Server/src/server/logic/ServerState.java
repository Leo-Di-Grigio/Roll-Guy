package server.logic;

import java.util.HashMap;

import com.badlogic.gdx.math.Vector2;

import common.Const;
import common.net.Message;
import server.Client;
import server.ClientPool;

public class ServerState {
	
	// data
	private HashMap<Integer, Player> players;
	
	// system
	private ClientPool clients;
	
	// 
	private Vector2 tmpVector2;
	
	private boolean inited;
	
	public ServerState(ClientPool clients) {
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
	
	public void addPlayer(int id) {
		players.put(id, new Player(id));
		
		if(inited){
			broadAllData(id);
			clients.send(id, new Message(Message.SERVER_LOAD_GAME));
		}
	}
	
	private void broadAllData(int id) {
		for(Player player: players.values()){
			if(player.id == id){
				clients.send(id, player.getDataFull());
			}
			else{
				clients.sendToAll(player.getData());
			}
		}
	}

	public void playerMove(Message msg) {
		tmpVector2.set(msg.fx, msg.fy);
		tmpVector2.nor();
		tmpVector2.set(tmpVector2.x*Const.CHAR_SPEED, tmpVector2.y*Const.CHAR_SPEED);
		
		Player player = players.get(msg.clientId);
		if(player.move(tmpVector2)){
			clients.sendToAll(new Message(Message.SERVER_PLAYER_MOVE, msg.clientId, player.pos.x, player.pos.y));
		}
	}
}