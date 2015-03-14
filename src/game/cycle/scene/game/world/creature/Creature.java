package game.cycle.scene.game.world.creature;

import java.awt.Point;
import java.util.ArrayList;

import game.cycle.scene.game.world.creature.ai.PathFinding;
import game.cycle.scene.game.world.map.Location;
import game.cycle.scene.game.world.map.Terrain;
import game.resources.Resources;
import game.resources.Tex;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Creature {

	private static int ID = 0;
	public int id;
	
	// personal
	public String name;
	public Texture avatar;
	
	public int energy;
	public String energyMax;
	
	public CreatureProto proto;
	public Struct struct;
	public Inventory inventory;
	public Features features;
	
	public int mapId;
	
	public Sprite sprite;
	
	// movement
	public boolean isMoved;
	public boolean isDirected;
	public ArrayList<Point> path;
	public Vector2 endPoint;
	public Vector2 direct;
	public float speed = 2.0f;
	
	public Creature(CreatureProto proto) {
		this.id = ID++;
		endPoint = new Vector2();
		direct = new Vector2();
		
		name = "Creature ID: " + id;
		avatar = Resources.getTex(Tex.avatarNpc);
		
		this.proto = proto;
		this.struct = new Struct(proto.stats.stamina);
		
		sprite = new Sprite(Resources.getTex(Tex.creatureCharacter + proto.texture));
	}
	
	public void setPosition(Terrain [][] map, int x, int y){
		int size = Location.tileSize;
		
		int oldx = (int)(sprite.getX()/size);
		int oldy = (int)(sprite.getY()/size);
		
		map[oldx][oldy].creature = null;
		map[x][y].creature = this;
		sprite.setPosition(x*size, y*size);
	}
	
	public void update(Terrain [][] map){
		if(isMoved){
			if(isDirected){
				if(Math.abs(endPoint.x - sprite.getX()) < speed*1.2f && Math.abs(endPoint.y - sprite.getY()) < speed*1.2f){
					sprite.setPosition(endPoint.x, endPoint.y);
					isDirected = false;
				}
				else{
					sprite.translate(direct.x*speed, direct.y*speed);
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
				
						direct.set(endPoint.x - sprite.getX(), endPoint.y - sprite.getY());
						direct.nor();
					
						isDirected = true;
						
						map[(int)(sprite.getX()/Location.tileSize)][(int)(sprite.getY()/Location.tileSize)].creature = null;
						map[(int)point.getX()][(int)point.getY()].creature = this;
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
		}
	}
	
	public void draw(SpriteBatch batch){
		sprite.draw(batch);
	}

	public void move(Terrain [][] map, int sizeX, int sizeY, int toX, int toY) {
		if(map[toX][toY].go != null && !map[toX][toY].go.proto.passable){
			return;
		}
		
		if(path != null){
			Point point = path.get(path.size() - 1);
			if(point.x == toX && point.y == toY){
				return;
			}
		}
		
		if(map[toX][toY].proto.passable){
			int posx = (int)(sprite.getX()/Location.tileSize);
			int posy = (int)(sprite.getY()/Location.tileSize);
			path = PathFinding.getPath(posx, posy, toX, toY, map, sizeX, sizeY);
		
			if(path != null){
				isMoved = true;
			}
			else{
				path = null;
			}
		}
	}
	
	public boolean damage(int value){ // return life status
		return struct.damage(value);
	}
}
