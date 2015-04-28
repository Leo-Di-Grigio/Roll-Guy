package game.cycle.scene.game.state.location.go;

import com.badlogic.gdx.graphics.g2d.Sprite;

import game.cycle.scene.game.state.database.Database;
import game.resources.Resources;
import game.resources.tex.Tex;

public class GOFactory {

	public static GO getGo(int guid, int id, int x, int y, int param1, int param2, int param3, int param4){
		GO go = getGo(guid, id, x, y);
		go.setParam1(param1);
		go.setParam2(param2);
		go.setParam3(param3);
		go.setParam4(param4);
		go.script = go.proto.script();
		return go;
	}

	public static GO getGo(int guid, int id, float x, float y) {
		GO go = new GO(guid, Database.getGO(id));		
		go.setSprite(new Sprite(Resources.getTex(Tex.GO_NULL + go.proto.tex1())));
		go.setPosition(x, y);
		go.setLos(go.proto.los());
		
		return go;
	}
}
