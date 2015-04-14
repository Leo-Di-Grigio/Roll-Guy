package game;

import game.cycle.input.KeyBinds;
import game.tools.Tools;

public class Main {

	public static void main(String [] args) {
		new Config();
		new KeyBinds();
		new Tools();
		new Frame();
	}
}