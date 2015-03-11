package game.cycle.scene.game.world.creature;

import game.resources.Resources;
import game.resources.Tex;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class Player extends Creature {

	public Player() {
		super();
		sprite = new Sprite(Resources.getTex(Tex.creatureCharacter));
	}
}
