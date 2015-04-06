package game.cycle.scene.game.world.location.creature.items;

import com.badlogic.gdx.graphics.Texture;

import game.resources.Resources;
import game.resources.Tex;

public class Item {

	private static int GUID = 0;
	
	public int guid;
	public ItemProto proto;
	public Texture tex;
	
	public Item(ItemProto proto) {
		this.guid = GUID++;
		this.proto = proto;
		this.tex = Resources.getTex(Tex.itemNull0 + proto.tex);
	}
}