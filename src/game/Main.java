package game;

import game.tools.Log;
import game.tools.Tools;

public class Main {

	public static void main(String [] args) {
		Log.msg("RollBoy v" + Version.version + "." + Version.subversion);
		new Config();
		new Tools();
		new Frame();
	}
}