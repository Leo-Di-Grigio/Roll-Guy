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
	
	public static float getRange(int x1, int y1, int x2, int y2){
		return (float)Math.sqrt((x2-x1)*(x2-x1) + (y2-y1)*(y2-y1));
	}
}
