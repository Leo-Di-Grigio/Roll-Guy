package game.cycle.scene.game.world.creature;

import game.cycle.scene.game.world.database.Database;
import game.cycle.scene.game.world.database.GameConst;
import game.cycle.scene.game.world.map.Location;
import game.cycle.scene.game.world.skill.Skill;

public class Player extends Creature {

	public Skill [] skillpanel;
	public Skill usedSkill;
	
	public Player() {
		super(Database.getCreature(0));
		this.player = true;
		
		skillpanel = new Skill[GameConst.uiActionPanelSlots];
		skillpanel[0] = skills.get(0);
		skillpanel[1] = skills.get(1);
	}
	
	@Override
	public void update(Location location) {
		super.update(location);
	}

	public void setUsedSkill(Skill skill) {
		this.usedSkill = skill;
	}
}
