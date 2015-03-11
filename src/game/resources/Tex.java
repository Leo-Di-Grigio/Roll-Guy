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
	
	// keys tiles
	public static final int tileSelect = 200;
	public static final int tileGrass = 201;
	public static final int tileWall = 202;
	
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