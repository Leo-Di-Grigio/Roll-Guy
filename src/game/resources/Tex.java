package game.resources;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Disposable;

public class Tex implements Disposable {
	
	// keys
	public static final int texNull = 0;
	
	// keys UI
	public static final int uiButtonNormal = 100;
	public static final int uiButtonSelected = 101;
	public static final int uiButtonClick = 102;
	public static final int uiBackgroundNormal = 103;
	public static final int uiBackgroundSelected = 104;
	public static final int uiBackgroundLightSelected = 105;
	public static final int uiListLine = 106;
	
	// tile select
	public static final int tileSelect = 150;
	public static final int tileWaypoint = 151;

	// keys tiles
	public static final int tileNull = 200;
	public static final int tileGrass = 201;
	public static final int tileWall = 202;
	
	// creature
	public static final int creatureCharacter = 1000;
	public static final int creatureNpc = 1001;
	
	// creature avatar
	public static final int avatarNpc = 2000;
	
	// go
	public static final int go = 10000;
	public static final int goDoorOpen = 10001;
	public static final int goDoorClose = 10002;
	
	// data
	public int id;
	public Texture tex;
	
	public Tex(int id, Texture tex) {
		this.id = id;
		this.tex = tex;
	}

	@Override
	public void dispose() {
		tex = null;
	}
}