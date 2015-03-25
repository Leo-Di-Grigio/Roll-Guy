package game.cycle.scene.game.world.creature;

import java.awt.Point;

import game.cycle.scene.game.world.LocationObject;
import game.cycle.scene.game.world.creature.ai.AIPathFind;
import game.cycle.scene.game.world.creature.items.Equipment;
import game.cycle.scene.game.world.creature.items.Inventory;
import game.cycle.scene.game.world.creature.skills.SkillList;
import game.cycle.scene.game.world.creature.struct.Struct;
import game.cycle.scene.game.world.database.Database;
import game.cycle.scene.game.world.database.GameConst;
import game.cycle.scene.game.world.map.Location;
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
	
	public CreatureProto proto;
	public Struct struct;
	public Equipment equipment;
	public Inventory inventory;
	public SkillList skills;
	
	// Draw
	public Texture avatar;
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
		super(proto.fraction);
		this.creature = true;
		endSpritePos = new Vector2();
		avatar = Resources.getTex(Tex.avatarNpc);
		
		this.proto = proto;
		this.struct = new Struct(proto.stats.stamina);
		this.ap = GameConst.apMax;
		this.skills = new SkillList();
		this.equipment = new Equipment();
		this.inventory = new Inventory(GameConst.inventorySizeX, GameConst.inventorySizeY);
		
		sprite = new Sprite(Resources.getTex(Tex.creaturePlayer + proto.texture));
		tex = (TexChar)(Resources.getTexWrap(Tex.creaturePlayer + proto.texture));
		font = Resources.getFont(Fonts.fontDamage);
		
		loadTestSkills();
	}

	private void loadTestSkills() {
		skills.put(0, Database.getSkill(0));
		skills.put(1, Database.getSkill(1));
	}

	@Override
	public void update(Location location){
		movement(location, location.isTurnBased);
	}

	private void movement(Location location, boolean isTurnBased) {
		if(isMoved){
			if(isDirected){
				if(Math.abs(endSpritePos.x - sprite.getX()) < speed*1.2f && Math.abs(endSpritePos.y - sprite.getY()) < speed*1.2f){
					this.setPosition(endPos.x, endPos.y);
					this.setSpritePosition(endSpritePos.x, endSpritePos.y);
					this.isDirected = false;
				}
				else{
					this.sprite.translate(direct.x*speed, direct.y*speed);
				}
			}
			else{
				if(path != null){
					if(isTurnBased && path.size() > 0){
						if(this.ap - GameConst.getMovementAP(this) >= 0 && path.size() > 0){
							this.ap -= GameConst.getMovementAP(this);
						}
						else{
							this.resetPath();
							return;
						}
					}
					
					if(path.size() > 0){
						this.endPos = path.remove(0);
						Point pos = getPosition();
						if(this.path.size() == 0){
							this.path = null;
						}
						endSpritePos.set((float)(endPos.x*GameConst.tileSize), (float)(endPos.y*GameConst.tileSize));
				
						direct.set(endSpritePos.x - sprite.getX(), endSpritePos.y - sprite.getY());
						direct.nor();
						
						location.map[pos.x][pos.y].creature = null;
						location.map[endPos.x][endPos.y].creature = this;
						this.setPosition(endPos.x, endPos.y);
						
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
						
						// end
						isDirected = true;
					}
					else{
						this.resetPath();
						return;
					}
				}
				else{
					this.resetPath();
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
		if(isAlive()){
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
		else{
			batch.draw(tex.dead[animationDirect], sprite.getX(), sprite.getY());
		}
	}

	public void move(Location location, int toX, int toY) {
		if(ap >= GameConst.getMovementAP(this)){
			if(path != null){
				Point point = path.get(path.size() - 1);
				if(point.x == toX && point.y == toY){
					path = null;
					return;
				}
			}
		
			if(location.map[toX][toY].proto.passable){
				Point pos = getPosition();
				int posx = pos.x;
				int posy = pos.y;
				path = AIPathFind.getPath(location, posx, posy, toX, toY);
		
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
	
	@Override
	public void dispose() {

	}
}
