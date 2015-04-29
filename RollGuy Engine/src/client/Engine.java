package client;

import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import common.Version;

public class Engine {

	public Engine() {
		LwjglApplicationConfiguration frameConf = new LwjglApplicationConfiguration();
		
		frameConf.title = Version.getTitle();
		frameConf.width = Config.frameWidth;
		frameConf.height = Config.frameHight;
		frameConf.resizable = Config.frameResizeble;
		frameConf.fullscreen = Config.fullscreen;
		frameConf.addIcon("assets/icon/icon32.png", FileType.Internal);
		
		new LwjglApplication(new Cycle(), frameConf);
	}
}
