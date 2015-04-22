package game.cycle.scene.game.state.location.creature;

import java.awt.Point;
import java.util.ArrayList;

import game.cycle.scene.game.state.database.Database;
import game.cycle.scene.game.state.database.GameConst;
import game.cycle.scene.game.state.database.proto.CreatureProto;
import game.cycle.scene.game.state.event.Event;
import game.cycle.scene.game.state.location.Location;
import game.cycle.scene.game.state.location.LocationObject;
import game.cycle.scene.game.state.location.Node;
import game.cycle.scene.game.state.location.creature.ai.AIPathFind;
import game.cycle.scene.game.state.location.creature.ai.Perception;
import game.cycle.scene.game.state.location.creature.items.Equipment;
import game.cycle.scene.game.state.location.creature.skills.SkillList;
import game.cycle.scene.game.state.location.creature.struct.Struct;
import game.cycle.scene.game.state.location.go.GO;
import game.resources.Fonts;
import game.resources.Resources;
import game.resources.tex.Tex;
import game.resources.tex.TexChar;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Creature extends LocationObject {
	
	protected CreatureProto proto;
	protected Struct struct;
	protected Equipment equipment;
	protected SkillList skills;
	
	// Draw
	protected Texture avatar;
	protected TexChar tex;
	protected BitmapFont font;
		
	// animation
	protected boolean animationMovement;
	protected boolean animationDamage;
	protected boolean animationIdle;
	protected int animationTimer;
	protected int animationTimerLimit = 1000;
	protected int animationDirect = TexChar.directDown;
	protected int animationDamageValue;
	protected int animationDamageTimer;
	
	// updates
	private boolean updateFogOfWar;
	
	public Creature(int guid, CreatureProto proto) {
		super(guid, proto.fraction());
		this.creature = true;
		this.endSpritePos = new Vector2();
		this.avatar = Resources.getTex(Tex.AVATAR_0);
		
		this.proto = proto;
		this.struct = new Struct(proto.stats().stamina);
		this.ap = GameConst.ACTION_POINTS_MAX;
		this.skills = new SkillList();
		this.equipment = new Equipment();
		
		this.sprite = new Sprite(Resources.getTex(Tex.CREATURE_0 + proto.tex()));
		this.tex = (TexChar)(Resources.getTexWrap(Tex.CREATURE_0 + proto.tex()));
		this.font = Resources.getFont(Fonts.fontDamage);
		
		this.loadTestSkills();
	}

	private void loadTestSkills() {
		skills.put(0, Database.getSkill(0));
		skills.put(1, Database.getSkill(1));
		skills.put(2, Database.getSkill(2));
	}

	@Override
	public void update(Location loc, OrthographicCamera camera, Player player, boolean losMode){
		movement(loc, player, losMode);
		
		if(updateFogOfWar){
			updateFogOfWar = false;
			updateLOS(loc, camera);
		}
	}
	
	private void movement(Location loc, Player player, boolean losMode) {
		if(isMoved){
			if(isDirected){
				movementContunue(loc, player, losMode);
			}
			else{
				movementNextPoint(loc);
			}
			
			loc.requestUpdate();
		}
	}
	
	private void movementContunue(Location loc, Player player, boolean losMode){
		if(isMovementInstantly(loc, player, losMode)){
			movementSetToEndPosition(loc);
		}
		else{
			if(Math.abs(endSpritePos.x - sprite.getX()) <= speed && Math.abs(endSpritePos.y - sprite.getY()) <= speed){
				movementSetToEndPosition(loc);
			}
			else{
				this.sprite.translate(direct.x*speed, direct.y*speed);
				
				if(this.draggedObject != null){
					this.draggedObject.getSprite().translate(direct.x*speed, direct.y*speed);
				}
			}	
		}
	}
	
	private void movementSetToEndPosition(Location loc){
		this.setPosition(endPos.x, endPos.y);
		this.setSpritePosition(endSpritePos.x, endSpritePos.y);
		this.isDirected = false;
		
		// drag something
		if(this.draggedObject != null){
			this.draggedObject.setPosition(endPos.x, endPos.y);
			this.draggedObject.setSpritePosition(endSpritePos.x, endSpritePos.y);
		}
		
		if(isPlayer()){
			this.updateLOS();
		}
		
		GO go = loc.map[endPos.x][endPos.y].go;
		if(go != null){
			go.event(new Event(Event.EVENT_SCRIPT_LAND, this, go));
		}
	}
	
	private void movementNextPoint(Location loc){
		if(path != null){
			if(loc.isTurnBased()){
				if(this.ap == 0){
					this.resetPath();
					return;
				}
				else if(path.size() > 0){
					if(this.ap - GameConst.getMovementAP(this) >= 0 && path.size() > 0){
						this.ap -= GameConst.getMovementAP(this);
					}
					else{
						this.resetPath();
						return;
					}
				}
			}
			
			if(path.size() > 0){
				this.endPos = path.get(0);
				
				if(loc.map[endPos.x][endPos.y].creature != null){
					movementBlocked++; // AI updating
					if(movementBlocked >= 20){ // if path blocked too long - find new path
						Point end = path.get(path.size() - 1);
						this.move(loc, end.x, end.y);
					}
					return;
				}
				else{
					movementBlocked = 0; // AI updating
					path.remove(0);
				
					Point pos = getPosition();
					if(this.path.size() == 0){
						this.path = null;
					}
					endSpritePos.set((float)(endPos.x*GameConst.TILE_SIZE), (float)(endPos.y*GameConst.TILE_SIZE));
		
					direct.set(endSpritePos.x - sprite.getX(), endSpritePos.y - sprite.getY());
					direct.nor();
				
					loc.map[pos.x][pos.y].creature = null;
					loc.map[endPos.x][endPos.y].creature = this;
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
	
	private boolean isMovementInstantly(Location loc, Player player, boolean losMode){
		return (losMode 
			 && this.isNPC()
			 && loc.isTurnBased() 
			 && !Perception.isVisible(player, loc, this) 
			 && loc.checkVisiblity(player, this) != null);
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
		if(player && location.isTurnBased() && !location.isPlayerTurn()){
			isMoved = false;
			path = null;
		}
		else{
			if(ap >= GameConst.getMovementAP(this)){
				if(path != null){
					Point point = path.get(path.size() - 1);
					if(point.x == toX && point.y == toY){
						path = null;
						return;
					}
				}
			
				if(location.map[toX][toY].proto.passable()){
					Point pos = getPosition();
					int posx = pos.x;
					int posy = pos.y;
					ArrayList<Point> path = AIPathFind.getPath(location, posx, posy, toX, toY);
			
					if(path != null){
						this.path = path;
						this.isMoved = true;
					}
					else{
						this.path = null;
					}
				}
			}
			else{
				isMoved = false;
				path = null;
			}
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

	public void kill() {
		struct.kill();
	}
	
	public void setStructPercent(int percent){
		struct.setStructPercent(percent);
	}
	
	public void updateLOS(Location loc, OrthographicCamera camera) {
		checkNode(pos, loc.map, loc.proto.sizeX(), loc.proto.sizeY(), camera);
	}

	public void updateLOS() {
		this.updateFogOfWar = true;
	}
		
	private void checkNode(Point pos, Node [][] map, int sizeX, int sizeY, OrthographicCamera camera) {
		int x = (int)(camera.position.x / GameConst.TILE_SIZE);
		int y = (int)(camera.position.y / GameConst.TILE_SIZE);
		int w = (Gdx.graphics.getWidth()/GameConst.TILE_SIZE + 4)/2;
		int h = (Gdx.graphics.getHeight()/GameConst.TILE_SIZE + 4)/2;
		
		int xmin = Math.max(0, x - w);
		int ymin = Math.max(0, y - h);
		int xmax = Math.min(sizeX, x + w);
		int ymax = Math.min(sizeY, y + h);
		
		for(int i = xmin; i < xmax; ++i){
			for(int j = ymin; j < ymax; ++j){
				map[i][j].hide();
			}
		}
		map[pos.x][pos.y].explore();
		
		Vector2 point = new Vector2();
		Vector2 direct = new Vector2();
		
		for(int i = 0; i < 360; ++i){
			point.set(pos.x, pos.y);
			direct.set((float) Math.sin(Math.toRadians(i)), (float) -Math.cos(Math.toRadians(i)));
			
			while(true){
				if(point.x >= xmin && point.x < xmax && point.y >= ymin && point.y < ymax){
					point.x += direct.x;
					point.y += direct.y;
					
					int nodex = Math.round(point.x);
					int nodey = Math.round(point.y);
					
					if(nodex >= 0 && nodex < sizeX && nodey >= 0 && nodey < sizeY){
						Node node = map[nodex][nodey];
						
						if(node.proto.los() || (node.go != null && node.go.isLos())){
							node.explore();
							break;
						}
						else{
							node.explore();
						}
					}
				}
				else{
					break;
				}
			}
		}
	}

	@Override
	public void dispose() {

	}
	
	// GET
	public int strength(){
		return proto.stats().strength;
	}
	
	public int agility(){
		return proto.stats().agility;
	}
	
	public int stamina(){
		return proto.stats().stamina;
	}
	
	public int perception(){
		return proto.stats().perception;
	}
	
	public int intelligence(){
		return proto.stats().intelligence;
	}
	
	public int willpower(){
		return proto.stats().willpower;
	}

	public CreatureProto proto(){
		return proto;
	}
	
	public Struct struct(){
		return struct;
	}
	
	public Equipment equipment(){
		return equipment;
	}
	
	public SkillList skills(){
		return skills;
	}
	
	public Texture avatar(){
		return avatar;
	}
	
	public int getMass() {
		return inventory.getTotalMass() + equipment.getTotalMass();
	}
}
