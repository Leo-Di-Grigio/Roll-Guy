package game.cycle.scene.game.world.creature.ai;

import game.cycle.scene.game.world.map.Terrain;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class PathFinding {	
	
	// key Nodes
	private static Cell startNode;
	private static Cell endNode;

	// consts
	private static final int straightCost = 10;
	private static final int diagCost = 14; // may be 14 if diagCost != strightCost
	
	// lists
	protected static ArrayList<Cell> openList;
	protected static ArrayList<Cell> closedList;
	
	protected static boolean isFinded;
	protected static ArrayList<Point> path;
	
	// map
	protected static int mapSizeX;
	protected static int mapSizeY;
	protected static Terrain [][] map;

	protected static class Cell {
		public int x;
		public int y;
		
		protected int f; // path cost
		protected int h; // heuristic value
		protected int g; // stright cost
		
		protected int costMultipiller = 1;
		
		protected Cell parent;
		
		protected Cell(int x, int y){
			this.x = x;
			this.y = y;
		}
		
		// diagonal heuristic
		protected int h() {
			int dx = Math.abs(this.x - endNode.x);
			int dy = Math.abs(this.y - endNode.y);
			return diagCost * Math.max(dx, dy);
		}
		
		protected void g(){
			this.g = straightCost;
		}
		
		protected boolean compare(Cell node){
			if(this.x == node.x && this.y == node.y){
				return true;
			}
			else{
				return false;
			}
		}
		
		protected void setValues(int g, int h, int f){
			this.g = g;
			this.h = h;
			this.f = f;
		}
	}

	public static ArrayList<Point> getPath(int x, int y, int toX, int toY, Terrain [][] map, int mapSizeX, int mapSizeY){
		// null data
		PathFinding.isFinded = false;
		
		// init data
		PathFinding.map = map;
		
		PathFinding.mapSizeX = mapSizeX;
		PathFinding.mapSizeY = mapSizeY;
		
		PathFinding.openList = new ArrayList<Cell>();
		PathFinding.closedList = new ArrayList<Cell>();
		
		PathFinding.endNode = new Cell(toX, toY);
		PathFinding.startNode = new Cell(x, y);
		
		PathFinding.path = null;
		
		if(endNode.compare(startNode)){
			return null;
		}
		
		startNode.g = 0;
		startNode.h = startNode.h();
		startNode.f = startNode.g + startNode.h;
	
		// begin A*
		search();
		
		return path;
	}
	
	private static void search(){
		Cell node = startNode;
		
		while(!node.compare(endNode)){
			int startX = Math.max(0, node.x - 1);
			int endX = Math.min(mapSizeX - 1, node.x + 1);
			
			int startY = Math.max(0, node.y - 1);
			int endY = Math.min(mapSizeY - 1, node.y + 1);
				
			for(int i = startX; i <= endX; ++i){
				for(int j = startY; j <= endY; ++j){					
					Cell test = new Cell(i, j);
					
					if(test.compare(endNode)){
						test = endNode;
					}
					
				
					if(test.compare(node) || !isPassable(map, node.x, node.y, test.x, test.y)){
						continue;
					}
					
					int cost = straightCost;
					if(!((node.x == test.x) || (node.y == test.y))){
						cost = diagCost;
					}
					
					int g = node.g + cost * test.costMultipiller;
					int h = test.h;
					int f = g + h;
					
					if(isOpen(test) || isClosed(test)){
						if(test.f > f){
							test.setValues(g, h, f);
							test.parent = node;
						}
					}
					else{
						test.setValues(g, h, f);
						test.parent = node;
						openList.add(test);
					}
				}
			}
			
			closedList.add(node);
			
			if(openList.size() == 0){
				PathFinding.isFinded = false;
				return;
			}
			
			Collections.sort(openList, new Comparator<Cell>() {
				@Override
				public int compare(Cell o1, Cell o2) {
					if(o1.f < o2.f){
				    	return -1;
				    }
				    else{
				    	if(o1.f > o2.f) 
				    		return 1;
				    	else 
				    		return 0;
				    }
				}
			});
			
			node = openList.get(0);
			openList.remove(0);
		}
		
		buildPath();
		PathFinding.isFinded = true;
	}
	
	private static void buildPath(){
		path = new ArrayList<Point>();
		Cell node = endNode;
		path.add(new Point(node.x, node.y));
		
		while(node != null && !node.compare(startNode)){
			node = node.parent;
			
			if(!node.compare(startNode)){
				path.add(0, new Point(node.x, node.y));
			}
		}
		
		// clear
		openList = null;
		closedList = null;
	}
	
	private static boolean isOpen(Cell node){
		for(Cell item: openList){
			if(item.compare(node)){
				return true;
			}
		}
		return false;
	}
	
	private static boolean isClosed(Cell node){
		for(Cell item: closedList){
			if(item.compare(node)){
				return true;
			}
		}
		return false;
	}
	
	private static boolean isPassable(Terrain [][] nodes, int fromX, int fromY, int toX, int toY){
		if(!nodes[toX][toY].proto.passable){
			return false;
		}
		if(nodes[toX][toY].creature != null){
			return false;
		}
		if(nodes[toX][toY].go != null){
			if(!nodes[toX][toY].go.proto.passable){
				return false;
			}
		}

		return true;
	}
}
