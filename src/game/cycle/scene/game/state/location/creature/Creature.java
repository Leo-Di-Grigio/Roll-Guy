package game.cycle.scene.game.state.location.creature;

import game.cycle.scene.game.state.database.Database;
import game.cycle.scene.game.state.database.proto.CreatureProto;
import game.cycle.scene.game.state.location.Location;
import game.cycle.scene.game.state.location.LocationObject;
import game.cycle.scene.game.state.location.creature.items.Equipment;
import game.cycle.scene.game.state.location.creature.skills.SkillList;
import game.cycle.scene.game.state.location.creature.struct.Struct;
import game.resources.Fonts;
import game.resources.Resources;
import game.resources.tex.Tex;
import game.resources.tex.TexChar;

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
	protected int animationDirect = TexChar.DIRECT_DOWN;
	protected int animationDamageValue;
	protected int animationDamageTimer;
	
	public Creature(int guid, CreatureProto proto) {
		super(guid, proto.fraction());
		this.creature = true;
		this.endSpritePos = new Vector2();
		this.avatar = Resources.getTex(Tex.AVATAR_0);
		
		this.proto = proto;
		this.struct = new Struct(proto.stats().stamina);
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
		skills.put(3, Database.getSkill(3));
	}

	@Override
	public void update(Location loc, OrthographicCamera camera, Player player, boolean losMode){

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
		//checkNode(pos, loc.map, loc.proto.sizeX(), loc.proto.sizeY(), camera);
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
