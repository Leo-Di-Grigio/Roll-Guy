package game.cycle.scene.game.world.creature;

import java.awt.Point;

import game.cycle.scene.game.world.LocationObject;
import game.cycle.scene.game.world.creature.ai.PathFinding;
import game.cycle.scene.game.world.database.GameConst;
import game.cycle.scene.game.world.map.Location;
import game.cycle.scene.game.world.map.Terrain;
import game.resources.Fonts;
import game.resources.Resources;
import game.resources.Tex;
import game.resources.TexChar;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Creature extends LocationObject {

	private static int ID = 0;
	public int id;
	
	// personal
	public Texture avatar;
	
	public int energy;
	public String energyMax;
	
	public CreatureProto proto;
	public Struct struct;
	public Inventory inventory;
	public SkillList features;
	
	// actions
	public int ap;
	
	// Draw
	public TexChar tex;
	public BitmapFont font;
		
	// animation
	public boolean animationMovement;
	public boolean animationDamage;
	public boolean animationIdle;
	public int animationTimer;
	public int animationTimerLimit = 1000;
	public int animationDirect = TexChar.directDown;
	public int animationDamageValue;
	public int animationDamageTimer;
	
	public Creature(CreatureProto proto) {
		this.id = ID++;
		endPoint = new Vector2();
		avatar = Resources.getTex(Tex.avatarNpc);
		
		this.proto = proto;
		this.struct = new Struct(proto.stats.stamina);
		this.ap = GameConst.apMax;
		this.features = new SkillList();
		
		sprite = new Sprite(Resources.getTex(Tex.creaturePlayer + proto.texture));
		tex = (TexChar)(Resources.getTexWrap(Tex.creaturePlayer + proto.texture));
		font = Resources.getFont(Fonts.fontDamage);
	}
	
	@Override
	public void update(Location location){
		movement(location, location.isTurnBased);
	}

	private void movement(Location location, boolean isTurnBased) {
		if(isMoved){
			if(isDirected){
				if(Math.abs(endPoint.x - sprite.getX()) < speed*1.2f && Math.abs(endPoint.y - sprite.getY()) < speed*1.2f){
					sprite.setPosition(endPoint.x, endPoint.y);
					direct.set(0.0f, 0.0f);
					isDirected = false;
				}
				else{
					sprite.translate(direct.x*speed, direct.y*speed);
				}
			}
			else{
				if(path != null){
					if(isTurnBased){
						if(ap - GameConst.getMovementAP(this) >= 0 && path.size() > 0){
							ap -= GameConst.getMovementAP(this);
						}
						else{
							isMoved = false;
							path = null;
							return;
						}
					}
					
					if(path.size() > 0){
						Point point = path.remove(0);
						if(path.size() == 0){
							path = null;
						}
						endPoint.set((float)(point.getX()*Location.tileSize), (float)(point.getY()*Location.tileSize));
				
						direct.set(endPoint.x - sprite.getX(), endPoint.y - sprite.getY());
						direct.nor();
					
						isDirected = true;
						
						location.map[(int)(sprite.getX()/Location.tileSize)][(int)(sprite.getY()/Location.tileSize)].creature = null;
						location.map[(int)point.getX()][(int)point.getY()].creature = this;
						
						// animation switch
						float angle = direct.angle();
						if(angle <= 45.0f && (angle >= 0.0f || angle > 315.0f)){
							animationDirect = TexChar.directRight;
						}
						else if(angle > 45.0f && angle <= 135.0f){
							animationDirect = TexChar.directUp;
						}
						else if(angle > 135.0f && angle <= 225.0f){
							animationDirect = TexChar.directLeft;
						}
						else if(angle > 225.0f && angle <= 315.0f){
							animationDirect = TexChar.directDown;
						}
					}
					else{
						path = null;
						isMoved = false;
						return;
					}
				}
				else{
					path = null;
					isMoved = false;
					return;
				}
			}
		}
	}
	
	public void animationUpdate() {
		animationTimer++;
		
		if(animationTimer % 10 == 0){
			animationMovement = !animationMovement;
		}
		
		if(animationTimer >= animationTimerLimit){
			animationTimer = 0;
		}
		
		if(animationDamage){
			animationDamageTimer++;
			
			if(animationDamageTimer > 30){
				animationDamageTimer = 0;
				animationDamage = false;
			}
		}
	}
	
	@Override
	public void draw(SpriteBatch batch){
		if(path != null && isMoved && ap > 0){
			if(animationMovement){
				batch.draw(tex.move1[animationDirect], sprite.getX(), sprite.getY());
			}
			else{
				batch.draw(tex.move2[animationDirect], sprite.getX(), sprite.getY());
			}
		}
		else{
			batch.draw(tex.idle[animationDirect], sprite.getX(), sprite.getY());
		}
		
		if(animationDamage){
			font.draw(batch, "-" + animationDamageValue, sprite.getX(), sprite.getY() + 16 + animationDamageTimer);
		}
	}

	public void move(Terrain [][] map, int sizeX, int sizeY, int toX, int toY) {
		if(ap >= GameConst.getMovementAP(this)){
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
				Point pos = getPosition();
				int posx = pos.x;
				int posy = pos.y;
				path = PathFinding.getPath(posx, posy, toX, toY, map, sizeX, sizeY);
		
				if(path != null){
					isMoved = true;
				}
				else{
					path = null;
				}
			}
		}
		else{
			isMoved = false;
			path = null;
		}
	}
	
	public boolean damage(int value){ // return life status
		animationDamage = true;
		animationDamageValue = value;
		animationDamageTimer = 0;
		return struct.damage(value);
	}
	
	public boolean isAlive() {
		return struct.isAlive();
	}

	public void resetAp(){
		this.ap = GameConst.apMax;
	}
	
	@Override
	public void dispose() {

	}
}
