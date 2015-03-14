package game.tools;

import java.util.Random;

public class Tools {

	// random
	private static Random randomizer;
	
	public Tools() {
		randomizer = new Random(System.currentTimeMillis());
	}
	
	public static int rand(int min, int max){
		return randomizer.nextInt((max - min) + 1) + min;
	}
}
