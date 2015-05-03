package game.cycle.scene;

import game.cycle.scene.menu.SceneExit;
import game.cycle.scene.menu.SceneMenu;
import game.tools.Log;

import java.util.Collection;
import java.util.HashMap;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;

public class SceneMng implements Disposable {

	// keys
	public static final String SCENE_NULL = "scene-null";
	public static final String SCENE_MENU = "scene-menu";
	public static final String SCENE_EXIT = "scene-menu-exit";
	public static final String SCENE_GAME = "scene-game";
	
	// scenes
	private static HashMap<String, Scene> scenesList;
	private static Scene currentScene;
	
	public SceneMng() {
		scenesList = new HashMap<String, Scene>();
		
		addScene(new SceneNull(), SCENE_NULL);
		addScene(new SceneMenu(), SCENE_MENU);
		addScene(new SceneExit(), SCENE_EXIT);
		switchScene(SCENE_NULL);
	}
	
	public static void addScene(Scene scene, String key){
		scenesList.put(key, scene);
	}
	
	public static void removeScene(String key){
		Scene scene = scenesList.get(key);
		
		if(scene != null){
			scene.ui.onclose();
			scene.dispose();
			scene = null;
		}
	}
	
	public static void switchScene(String key){
		if(scenesList.containsKey(key)){
			if(currentScene != null){
				currentScene.ui.onclose();
			}
			currentScene = scenesList.get(key);
			currentScene.ui.onload();
		}
		else{
			Log.err("Scene " + key + " does not inited");
		}
	}
	
	public void update(OrthographicCamera camera) {
		if(!currentScene.pause){
			currentScene.update(camera);
		}
	}
	
	public void draw(SpriteBatch batch, OrthographicCamera camera){
		currentScene.draw(batch, camera);
	}
	
	public void drawGui(SpriteBatch batch){
		currentScene.ui.draw(batch);
		currentScene.drawGui(batch);
	}

	// Input events
	public static void mouseMoved(int x, int y) {
		currentScene.ui.mouseMoved(x, y);
	}
	
	public static void inputChar(char key) {
		currentScene.ui.inputChar(key);
	}

	public static void click(int button) {
		if(currentScene.ui.isSelected()){
			currentScene.ui.click(button);
		}
		else{
			currentScene.sceneClick(button);
		}
	}

	public static void key(int key){
		if(!currentScene.ui.isSelected()){
			currentScene.sceneKey(key);
		}
	}

	public static void scroll(int amount) {
		currentScene.ui.scroll(amount);
	}
	
	public static void pause(boolean pause){
		currentScene.pause(pause);
	}

	@Override
	public void dispose() {
		Collection<Scene> scenes = scenesList.values();
		for(Scene scene: scenes){
			scene.dispose();
			scene = null;
		}
	}
}
