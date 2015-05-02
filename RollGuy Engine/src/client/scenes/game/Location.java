package client.scenes.game;

import java.util.HashMap;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import common.Const;
import common.net.Message;
import client.net.Network;
import client.scenes.game.data.Player;

public class Location extends Thread {

	private Network net;

	// movement
	private Vector2 move;

	// player
	public int currentPlayerId;
	public HashMap<Integer, Player> players;
	
	// update cycle
	protected long cycleTime;
	protected long LastTime = System.currentTimeMillis();
	protected long elapsedTime = 0;
	protected long LastUpdate = 0;
	
	public Location(Network net) {
		this.net = net;
		this.move = new Vector2();
		
		players = new HashMap<Integer, Player>();
	}
	
	@Override
	public void run() {
		while(true){
			synchUpdateRate();
			update();
		}
	}
	
	private void synchUpdateRate() {
		cycleTime = cycleTime + Const.CLINET_UPDATE_RATE;
		long difference = cycleTime - System.currentTimeMillis();
		try {
			Thread.sleep(Math.max(0, difference));
		}
		catch(InterruptedException e) {
			e.printStackTrace();
		}
		elapsedTime = System.currentTimeMillis() - LastTime;
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
				player.setPos(msg.fx, msg.fy);
			}
		}
	}
	
	public void update() {
		move.nor();
		move.set(move.x*Const.CHAR_SPEED, move.y*Const.CHAR_SPEED);
			
		if(players.containsKey(currentPlayerId)){
			players.get(currentPlayerId).addPos(move);
		}
		flushBuffer();
		
		for(Player player: players.values()){
			player.update();
		}
	}
	
	private void flushBuffer(){
		if(!move.isZero()){
			net.send(new Message(Message.CLIENT_PLAYER_MOVE, move.x, move.y));
			move.setZero();
		}
	}
	
	public void draw(SpriteBatch batch){
		for(Player player: players.values()){
			player.draw(batch);
		}
	}
}
