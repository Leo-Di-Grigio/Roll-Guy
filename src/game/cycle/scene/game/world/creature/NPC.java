package game.cycle.scene.game.world.creature;

import game.resources.Resources;
import game.resources.Tex;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class NPC extends Creature {

	public NPC() {
		super();

		sprite = new Sprite(Resources.getTex(Tex.creatureNpc));
	}
}
