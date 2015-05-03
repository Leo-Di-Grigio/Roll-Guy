package game.state.location.creature.struct;

import tools.Log;
import tools.Tools;

public class Struct {

	public static final int HEAD = 0;
	public static final int TORSO = 1;
	public static final int HAND_LEFT = 2;
	public static final int HAND_RIGHT = 3;
	public static final int LEG_LEFT = 4;
	public static final int LEG_RIGHT = 5;
	
	private BodyPart [] body;
	
	private boolean alive;
	
	public Struct(int stamina){
		alive = true;
		body = new BodyPart[6];
		
		int hp = 10*(10+stamina);
		
		for(int i = 0; i < body.length; ++i){
			int partHp = 0;
			
			switch (i) {
				case HEAD: // head
					partHp = (int)(0.15f * hp);
					break;
					
				case TORSO: // torso
					partHp = (int)(0.4f * hp);
					break;
					
				case HAND_LEFT: 
				case HAND_RIGHT: // hands
					partHp = (int)(0.15f * hp);
					break;
					
				case LEG_LEFT:
				case LEG_RIGHT: // legs
					partHp = (int)(0.075f * hp);
					break;
			}
			
			body[i] = new BodyPart(partHp);
		}
	}
	
	public int getHp(int part){
		return body[part].hp;
	}

	public int getHpMax(int part) {
		return body[part].hpmax;
	}

	public boolean damage(int value) { // return creature life status
		int partid = Tools.rand(0, LEG_RIGHT);
		
		BodyPart part = body[partid];
		
		part.hp -= value;
		if(part.hp < 0){
			part.hp = 0;
		}
		
		Log.debug("BodyPart id: " + partid + " hp: " + part.hp + "/" + part.hpmax);
		
		if(partid == HEAD || partid == TORSO){
			if(part.hp < part.hpmax*0.25f){
				return false;
			}
		}
		
		return true;
	}
	
	public void heal(int heal) {
		int minHp = Integer.MAX_VALUE;
		BodyPart minPart = null;
		
		for(BodyPart part: body){
			if(part.hp < minHp){
				minHp = part.hp;
				minPart = part;
			}
		}
		
		minPart.hp += heal;
		if(minPart.hp > minPart.hpmax){
			minPart.hp = minPart.hpmax;
		}
	}

	public int getHpMax() {
		int hpmax = 0;
		for(int i = 0; i < body.length; ++i){
			hpmax += body[i].hpmax;
		}
		return hpmax;
	}

	public int getHp() {
		int hp = 0;
		for(int i = 0; i < body.length; ++i){
			hp += body[i].hp;
		}
		return hp;
	}

	public boolean isAlive() {
		if(alive){
			if((this.body[TORSO].hp >= this.body[TORSO].hp*0.25f) && (this.body[HEAD].hp >= this.body[HEAD].hp*0.25f)){
				return true;
			}
			else{
				this.alive = false;
				return false;
			}
		}
		else{
			return false;
		}
	}

	public void kill() {
		alive = false;
		for(BodyPart part: body){
			part.hp = 0;
		}
	}

	public void setStructPercent(int percent) {
		if(percent == 0){
			alive = false;
		}
		else{
			alive = true;
		}
		
		for(BodyPart part: body){
			part.hp = (int)(part.hpmax * (percent/100.0f));
		}
	}
}
