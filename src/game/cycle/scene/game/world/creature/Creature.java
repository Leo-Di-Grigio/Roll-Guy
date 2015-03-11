package game.cycle.scene.game.world.creature;

import java.awt.Point;
import java.util.ArrayList;

import game.cycle.scene.game.world.creature.ai.PathFinding;
import game.cycle.scene.game.world.map.Location;
import game.cycle.scene.game.world.map.Node;
import game.resources.Resources;
import game.resources.Tex;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Creature {

	public int id;
	public String name;
	
	public Stats stats;
	public Inventory inventory;
	public Features features;
	
	public int mapId;
	public Vector2 pos;
	
	public Sprite sprite;
	
	// movement
	public boolean isMoved;
	public boolean isDirected;
	public ArrayList<Point> path;
	public Vector2 endPoint;
	public Vector2 direct;
	public float speed = 2.0f;
	
	public Creature() {
		pos = new Vector2();
		endPoint = new Vector2();
		direct = new Vector2();
		
		sprite = new Sprite(Resources.getTex(Tex.creatureCharacter));
	}
	
	public void update(){
		if(isMoved){
			if(isDirected){
				if(Math.abs(endPoint.x - pos.x) < speed*1.2f && Math.abs(endPoint.y - pos.y) < speed*1.2f){
					pos.set(endPoint.x, endPoint.y);
					isDirected = false;
				}
				else{
					pos.add(direct.x*speed, direct.y*speed);
				}
			}
			else{
				if(path != null){
					if(path.size() > 0){
						Point point = path.remove(0);
						if(path.size() == 0){
							path = null;
						}
						endPoint.set((float)(point.getX()*Location.tileSize), (float)(point.getY()*Location.tileSize));
				
						direct.set(endPoint.x - pos.x, endPoint.y - pos.y);
						direct.nor();
					
						isDirected = true;
					}
					else{
						path = null;
						isMoved = false;
					}
				}
				else{
					isMoved = false;
				}
			}
			
			sprite.setPosition(pos.x, pos.y);
		}
	}
	
	public void draw(SpriteBatch batch){
		sprite.draw(batch);
	}

	public void move(Node[][] map, int sizeX, int sizeY, int toX, int toY) {
		int posx = (int)(pos.x/Location.tileSize);
		int posy = (int)(pos.y/Location.tileSize);
		path = PathFinding.getPath(posx, posy, toX, toY, map, sizeX, sizeY);
		
		if(path != null){
			isMoved = true;
		}
	}
}
