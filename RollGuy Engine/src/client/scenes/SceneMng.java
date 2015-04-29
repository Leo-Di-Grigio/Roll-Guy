package client.scenes;

import java.util.HashMap;

import client.scenes.list.SceneMenu;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;

import common.tools.Log;

public class SceneMng implements Disposable {

	public static final String SCENE_NULL = "null";
	public static final String SCENE_MENU = "menu";
	public static final String SCENE_MENU_SERVER = "menu-server";
	public static final String SCENE_MENU_CONNECT = "menu-connect";
	public static final String SCENE_GAME = "game";
	
	// data
	private static HashMap<String, Scene> scenes;
	private static Scene currentScene;
	
	public SceneMng() {
		scenes = new HashMap<String, Scene>();
		
		addScene(SCENE_NULL, new SceneNull(SCENE_NULL));
		addScene(SCENE_MENU, new SceneMenu(SCENE_MENU));
		switchScene(SCENE_MENU);
	}
	
	public static String getCurrentTitle(){
		return currentScene.title;
	}
	
	public boolean isNotNull() {
		return currentScene != null;
	}
	
	public static void addScene(String title, Scene scene){
		scenes.put(title, scene);
	}

	public static void remove(String title) {
		Scene scene = scenes.remove(title);
		
		if(scene != null){
			scene.dispose();
		}
	}

	public static void switchScene(String title){
		if(scenes.containsKey(title)){
			if(currentScene != null){
				currentScene.uiclose();
			}
			
			currentScene = scenes.get(title);
			currentScene.uiopen();
			
			Log.debug("Scene \"" + title + "\" loaded");
		}
		else{
			Log.err("Scene \"" + title + "\" does not inited");
		}
	}
	
	public void update(float delta, OrthographicCamera camera){
		currentScene.update(delta, camera);
	}
	
	public void draw(SpriteBatch batch, OrthographicCamera camera){
		currentScene.ui.update();
		currentScene.draw(batch, camera);
	}
	
	public void drawgui(SpriteBatch batch){
		currentScene.ui.draw(batch);
		currentScene.drawgui(batch);
	}
	
	public static void inputChar(char key) {
		currentScene.inputChar(key);
	}

	public static void click(int button) {
		if(currentScene.isUiSelected()){
			currentScene.click(button);
		}
		else{
			currentScene.sceneClick(button);
		}
	}

	public static void key(int key){
		if(!currentScene.isUiSelected()){
			currentScene.sceneKey(key);
		}
	}

	@Override
	public void dispose() {
		for(Scene scene: scenes.values()){
			scene.dispose();
		}
	}
}
