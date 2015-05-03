package game.state.location.sound;

import java.awt.Point;
import java.util.ArrayList;

import game.state.location.Location;

public class LocationSound {

	public static int volume(Location loc, int sourceX, int sourceY, int endX, int endY, int volume){
		ArrayList<Point> line = trace(sourceX, sourceY, endX, endY);
		
		for(Point point: line){
			volume -= loc.map[point.x][point.y].proto.soundMush();
		}
		
		return volume;
	}

	private static ArrayList<Point> trace(int sourceX, int sourceY, int endX, int endY){
		int x, y, dx, dy, incx, incy, pdx, pdy, es, el, err;
		ArrayList<Point> arr = new ArrayList<Point>();
		dx = endX - sourceX;
		dy = endY - sourceY;
	 
		incx = sign(dx);
		incy = sign(dy);
	 
		if(dx < 0){
			dx = -dx;
		}
		
		if(dy < 0){
			dy = -dy;
		}
		
		if(dx > dy){
			pdx = incx;
			pdy = 0;
			es = dy;	
			el = dx;
		}
		else{
			pdx = 0;
			pdy = incy;
			es = dx;
			el = dy;
		}
	 
		x = sourceX;
		y = sourceY;
		err = el/2;
		arr.add(new Point(x, y));
	
		for(int i = 0; i < el; i++){
			err -= es;
			
			if(err < 0){
				err += el;
				x += incx;
				y += incy;
			}
			else{
				x += pdx;
				y += pdy;
			}
	 
			arr.add(new Point(x, y));
		}
		
		return arr;
	}
	
	private static int sign(int x) {
		return (x > 0) ? 1 : (x < 0) ? -1 : 0;
	}
}
