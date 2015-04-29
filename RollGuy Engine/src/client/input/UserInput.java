package client.input;

import client.scenes.SceneMng;
import client.scenes.ui.interfaces.Dragged;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;

public class UserInput implements InputProcessor {
	
	private static boolean [] keys;
	public static int mouseX;
	public static int mouseY;
	
	public static boolean mouseRight;
	public static boolean mouseLeft;
	public static Dragged draggedElement;
	
	public static boolean pause;
	
	public UserInput() {
		keys = new boolean[256];
	}
	
	public static boolean key(int code){
		return keys[code];
	}
	
	@Override
	public boolean keyDown(int code) {
		keys[code] = true;
		return false;
	}

	@Override
	public boolean keyUp(int code) {
		keys[code] = false;
		
		SceneMng.key(code);
		return false;
	}

	@Override
	public boolean mouseMoved(int x, int y) {
		mouseX = x;
		mouseY = y;
		return false;
	}
	
	@Override
	public boolean keyTyped(char key) {
		SceneMng.inputChar(key);
		return false;
	}
	
	@Override
	public boolean scrolled(int amount) {
		SceneMng.scroll(amount);
		return false;
	}
	
	@Override
	public boolean touchDown(int x, int y, int pointer, int button) {
		if(button == Input.Buttons.LEFT) {
			UserInput.mouseLeft = true;
		}
			
		if(button == Input.Buttons.RIGHT){
			UserInput.mouseRight = true;
		}
		
		SceneMng.click(button);
		return false;
	}

	@Override
	public boolean touchUp(int x, int y, int pointer, int button) {
		if(button == Input.Buttons.LEFT){
			if(UserInput.draggedElement != null){
				UserInput.draggedElement.resetDragg();
				UserInput.draggedElement = null;
			}
			UserInput.mouseLeft = false;
			return true;
		}
			
		if(button == Input.Buttons.RIGHT){
			UserInput.mouseRight = false;
			return true;
		}
			
		return false;
	}
	
	@Override
	public boolean touchDragged(int x, int y, int pointer) {
		UserInput.mouseX = x;
		UserInput.mouseY = y;
		
		if(draggedElement != null){
			draggedElement.dragg(x, Gdx.graphics.getHeight() - y);
		}
		return false;
	}
}
