package game.resources;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;

public class Cursors {

	public static final int cursorDefault = 0;
	public static final int cursorTalking = 1;

	// data
	private static HashMap<Integer, Pixmap> cursors;
	
	public Cursors(HashMap<Integer, Pixmap> cursors) {
		Cursors.cursors = cursors;
	}

	public static void setCursor(int key) {
		if(cursors.get(key) != null){
			Gdx.input.setCursorImage(cursors.get(key), 0, 0);
		}
	}
}
