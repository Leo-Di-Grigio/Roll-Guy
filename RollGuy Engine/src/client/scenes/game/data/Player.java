package client.scenes.game.data;

import client.resources.Resources;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import common.Const;
import common.net.Message;
import common.resources.Tex;

public class Player {

	private int id;
	private Vector2 pos;
	private Vector2 speed;  // interpolated data
	private Texture tex;
	
	public Player(Message msg) {
		id = msg.ix;
		pos = new Vector2();
		speed = new Vector2();
		tex = Resources.getTex(Tex.CHAR_PLAYER);
		
		if(msg.key == Message.SERVER_ADD_PLAYER){
			//this.pos.set(msg.fx, msg.fy);
			System.out.println("ADD PLAYER");
		}
		else if(msg.key == Message.SERVER_ADD_PLAYER_FULL){
			//this.pos.set(msg.fx, msg.fy);
			System.out.println("ADD PLAYER FULL");
		}
	}
	
	public int id(){
		return id;
	}
	
	public void addPos(Vector2 move) {
		pos.add(move);
	}
		
	public void setPos(float fx, float fy) {
		pos.set(fx, fy);
	}

	public void draw(SpriteBatch batch){
		batch.draw(tex, pos.x, pos.y);
	}

	public void update(float updateAcc) {
		if(updateAcc >= Const.CLINET_UPDATE_TIME){
			speed.set(0.0f, 0.0f);
		}
		else{
			pos.add(speed);
		}
	}

	private static final int FPS_MAX = 60;
	private static final int MILISECONDS = 1000;
	private static final float UPD_DELTA = MILISECONDS/FPS_MAX * Const.CLINET_UPDATE_TIME;
	
	public void setSpeed(float fx, float fy) {
		speed.set(fx*UPD_DELTA, fy*UPD_DELTA);
	}
}
