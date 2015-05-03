package game;

import game.cycle.GameCycle;

import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Frame {

	public Frame() {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		
		config.title = Version.title + " " + Version.version + "." + Version.subversion;
		config.width = Config.frameWidth;
		config.height = Config.frameHight;
		config.resizable = Config.frameResizeble;
		config.fullscreen = Config.fullscreen;
		config.addIcon("assets/icon/icon32.png", FileType.Internal);
		
		new LwjglApplication(new GameCycle(), config);
	}
}
