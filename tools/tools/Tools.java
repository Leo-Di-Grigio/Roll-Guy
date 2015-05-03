package tools;

import game.state.location.LocationObject;

import java.awt.Point;
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

	public static float getRange(LocationObject a, LocationObject b){
		return Tools.getRange(a.getPosition(), b.getPosition());
	}
	
	public static float getRange(Point point1, Point point2){
		return getRange(point1.x, point1.y, point2.x, point2.y);
	}
	
	public static float getRange(int x1, int y1, int x2, int y2){
		return (float)Math.sqrt((x2-x1)*(x2-x1) + (y2-y1)*(y2-y1));
	}
} 