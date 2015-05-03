package game.state.location.creature.items;

import resources.Resources;
import resources.tex.Tex;

import com.badlogic.gdx.graphics.Texture;

import game.state.database.proto.ItemProto;

public class Item {

	private static int GUID = 0;
	
	public int guid;
	public ItemProto proto;
	public Texture tex;
	
	public Item(ItemProto proto) {
		this.guid = GUID++;
		this.proto = proto;
		this.tex = Resources.getTex(Tex.ITEM_0 + proto.tex());
	}
}