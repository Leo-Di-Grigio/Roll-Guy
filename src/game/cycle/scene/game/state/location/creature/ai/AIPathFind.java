package game.cycle.scene.game.state.location.creature.ai;

import game.cycle.scene.game.state.database.GameConst;
import game.cycle.scene.game.state.location.Location;
import game.cycle.scene.game.state.location.Node;
import game.tools.Tools;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class AIPathFind {

	public static ArrayList<Point> getPath(final Location location, final int x, final int y, final int toX, final int toY){
		if(x == toX && y == toY){
			return null;
		}
		else{
			Node node = location.map[toX][toY];
			
			if(!node.proto.passable()){
				return null;
			}
			
			if(node.creature != null || (node.go != null && !node.go.passable)){
				if(Tools.getRange(x, y, toX, toY) <= GameConst.INTERACT_RANGE){
					return null;
				}
				else{
					ArrayList<Path> pathes = new ArrayList<Path>();
					
					int startX = Math.max(0, toX - 1);
					int endX = Math.min(location.proto.sizeX() - 1, toX + 1);
					int startY = Math.max(0, toY - 1);
					int endY = Math.min(location.proto.sizeY() - 1, toY + 1);
					
					for(int i = startX; i <= endX; ++i){
						for(int j = startY; j <= endY; ++j){
							Node testNode = location.map[i][j];
							
							if(testNode.proto.passable()){
								if(testNode.creature == null && (testNode.go == null || testNode.go.passable)){
									Path path = search(x, y, i, j, location);
										
									if(path != null){
										pathes.add(path);
									}
								}
							}
						}
					}
					
					if(pathes.size() > 0){
						int minCost = Integer.MAX_VALUE;
						Path minPath = null;
					
						for(Path path: pathes){
							if(path.cost < minCost){
								minCost = path.cost;
								minPath = path;
							}
						}
						
						return minPath.path;
					}
					else{
						return null;
					}
				}
			}
			else{
				Path result = search(x, y, toX, toY, location);
				
				if(result == null){
					return null;
				}
				else{
					if(result.path == null || result.path.size() == 0){
						return null;
					}
					else{
						return result.path;
					}
				}
			}
		}
	}

	private static Path search(int x, int y, int toX, int toY, Location location) {
		// begin data
		ArrayList<Cell> openList = new ArrayList<Cell>();
		ArrayList<Cell> closedList = new ArrayList<Cell>();
		Node [][] map = location.map;
		
		Cell endCell = new Cell(toX, toY);
		Cell startCell = new Cell(x, y);
		startCell.g = 0;
		startCell.h = startCell.h(endCell);
		startCell.f = startCell.g + startCell.h;
		
		// start algorithm A*
		Cell cell = startCell;
		
		// unstuck timer (костыль, но вопрос нужно было решать)
		int infinityTimer = 0;
		
		while(!cell.compare(endCell)){
			infinityTimer++;
			if(infinityTimer > 1000){
				return null;
			}
			
			int startX = Math.max(0, cell.x - 1);
			int endX = Math.min(location.proto.sizeX() - 1, cell.x + 1);
			
			int startY = Math.max(0, cell.y - 1);
			int endY = Math.min(location.proto.sizeY() - 1, cell.y + 1);
				
			for(int i = startX; i <= endX; ++i){
				for(int j = startY; j <= endY; ++j){					
					Cell test = new Cell(i, j);
					
					if(test.compare(endCell)){
						test = endCell;
					}
					
				
					if(test.compare(cell) || !isPassable(map, cell.x, cell.y, test.x, test.y)){
						continue;
					}
					
					int cost = Cell.straightCost;
					if(!((cell.x == test.x) || (cell.y == test.y))){
						cost = Cell.diagCost;
					}
					
					int g = cell.g + cost * test.costMultipiller;
					int h = test.h;
					int f = g + h;
					
					if(isOpen(closedList, test) || isClosed(openList, test)){
						if(test.f > f){
							test.setValues(g, h, f);
							test.parent = cell;
						}
					}
					else{
						test.setValues(g, h, f);
						test.parent = cell;
						openList.add(test);
					}
				}
			}
			
			closedList.add(cell);
			
			if(openList.size() == 0){
				return null;
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
			
			cell = openList.get(0);
			openList.remove(0);
		}
		
		return buildPath(startCell, endCell, map);
	}
	
	private static Path buildPath(Cell startCell, Cell endCell, Node [][] map){
		Path path = new Path();
		
		Cell node = endCell;
		
		if(map[endCell.x][endCell.y].creature == null){
			path.add(new Point(endCell.x, endCell.y), node.f);
		}
		
		while(node != null && !node.compare(startCell)){
			node = node.parent;
			
			if(!node.compare(startCell)){
				path.add(new Point(node.x, node.y), node.f);
			}
		}
		
		return path;
	}
	
	private static boolean isOpen(ArrayList<Cell> openList, Cell node){
		for(Cell item: openList){
			if(item.compare(node)){
				return true;
			}
		}
		return false;
	}
	
	private static boolean isClosed(ArrayList<Cell> closedList, Cell node){
		for(Cell item: closedList){
			if(item.compare(node)){
				return true;
			}
		}
		return false;
	}
	
	private static boolean isPassable(Node [][] map, int fromX, int fromY, int toX, int toY){
		if(!map[toX][toY].proto.passable()){
			return false;
		}
		if(map[toX][toY].creature != null){
			return false;
		}
		if(map[toX][toY].go != null){
			if(!map[toX][toY].go.passable){
				return false;
			}
		}

		return true;
	}
}

class Path {
	public ArrayList<Point> path;
	public int cost;
	
	public Path(){
		this.path = new ArrayList<Point>();
		this.cost = 0;
	}

	public void add(Point point, int cost) {
		path.add(0, new Point(point.x, point.y));
		this.cost += cost;
	}
}

class Cell {
	// constants
	protected static final int straightCost = 10;
	protected static final int diagCost = 14; // may be 14 if diagCost != strightCost
	protected int costMultipiller = 1;
	
	// cell data
	protected int x;
	protected int y;
	
	protected int f;
	protected int h;
	protected int g;
	
	protected Cell parent;
	
	protected Cell(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	// diagonal heuristic
	protected int h(Cell endNode) {
		int dx = Math.abs(this.x - endNode.x);
		int dy = Math.abs(this.y - endNode.y);
		return diagCost * Math.max(dx, dy);
	}
	
	protected void g(){
		this.g = straightCost;
	}
	
	protected void setValues(int g, int h, int f){
		this.g = g;
		this.h = h;
		this.f = f;
	}

	protected boolean compare(Cell node){
		if(this.x == node.x && this.y == node.y){
			return true;
		}
		else{
			return false;
		}
	}
}