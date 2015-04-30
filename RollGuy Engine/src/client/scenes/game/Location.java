package client.scenes.game;

import java.util.HashMap;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import common.Const;
import common.net.Message;
import client.net.Network;
import client.scenes.game.data.Player;

public class Location {

	private Network net;

	// movement
	private Vector2 move;
	private Vector2 moveAcc;

	// update
	private float updateAcc;
	
	// player
	public int currentPlayerId;
	public HashMap<Integer, Player> players;
	
	public Location(Network net) {
		this.net = net;
		
		this.move = new Vector2();
		this.moveAcc = new Vector2();
		
		players = new HashMap<Integer, Player>();
	}
	
	public void moveUp() {
		move.add(0.0f, 1.0f);
	}

	public void moveDown() {
		move.add(0.0f, -1.0f);
	}

	public void moveLeft() {
		move.add(-1.0f, 0.0f);
	}

	public void moveRight() {
		move.add(1.0f, 0.0f);
	}

	public void addPlayer(Message msg) {
		players.put(msg.ix, new Player(msg));
	}

	public void addPlayerFull(Message msg) {
		this.currentPlayerId = msg.ix;
		players.put(msg.ix, new Player(msg));
	}

	public void movePlayer(Message msg) {
		if(msg.ix != currentPlayerId){
			Player player = players.get(msg.ix);
			
			if(player != null){
				player.setSpeed(msg.fx, msg.fy);
			}
		}
	}
	
	public void update(float delta, OrthographicCamera camera) {
		move.nor();
		move.set(move.x*Const.CHAR_SPEED, move.y*Const.CHAR_SPEED);
		if(players.containsKey(currentPlayerId)){
			players.get(currentPlayerId).addPos(move);
		}
		
		moveAcc.add(move);
		move.set(0.0f, 0.0f);
	
		updateAcc += delta;
		for(Player player: players.values()){
			player.update(updateAcc);
		}
		
		if(updateAcc >= Const.CLINET_UPDATE_TIME){
			updateAcc = 0.0f;
			flushBuffer();
		}
	}
	
	private void flushBuffer(){
		if(!moveAcc.isZero()){
			net.send(new Message(Message.CLIENT_PLAYER_MOVE, moveAcc.x, moveAcc.y));
			moveAcc.setZero();
		}
	}
	
	public void draw(SpriteBatch batch){
		for(Player player: players.values()){
			player.draw(batch);
		}
	}
}
