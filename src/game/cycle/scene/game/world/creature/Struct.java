package game.cycle.scene.game.world.creature;

import game.tools.Log;
import game.tools.Tools;

public class Struct {

	public static final int head = 0;
	public static final int hull = 1;
	public static final int leftHand = 2;
	public static final int rightHand = 3;
	public static final int leftLeg = 4;
	public static final int righLeg = 5;
	
	private BodyPart [] body;
	
	public Struct(int stamina){
		body = new BodyPart[6];
		
		for(int i = 0; i < body.length; ++i){
			body[i] = new BodyPart(stamina);
		}
	}
	
	public int getHp(int part){
		return body[part].hp;
	}

	public int getHpMax(int part) {
		return body[part].hpmax;
	}

	public boolean damage(int value) { // return creature life status
		int partid = Tools.rand(0, righLeg);
		
		BodyPart part = body[partid];
		
		part.hp -= value;
		if(part.hp < 0){
			part.hp = 0;
		}
		
		Log.debug("BodyPart id: " + partid + " hp: " + part.hp + "/" + part.hpmax);
		
		if(partid == head || partid == hull){
			if(part.hp < part.hpmax*0.25f){
				return false;
			}
		}
		
		return true;
	}
}
