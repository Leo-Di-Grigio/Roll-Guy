package game.script.ui.game;

import game.cycle.scene.game.world.creature.Player;
import game.cycle.scene.game.world.skill.Skill;
import game.cycle.scene.ui.list.UIGame;
import game.resources.Cursors;
import game.script.Script;
import game.script.game.event.GameEvents;

public class ui_GameSkill implements Script {

	private UIGame ui;
	private Player player;
	private Skill skill;
	
	public ui_GameSkill(UIGame ui, Player player, Skill skill) {
		this.ui = ui;
		this.player = player;
		this.skill = skill;
	}

	@Override
	public void execute() {
		if(skill.range == 0.0f){ // selfcast skills
			GameEvents.useSkillSelfTarget(player, skill);
		}
		else{
			ui.setMode(UIGame.modeSkillUse);
			player.setUsedSkill(skill);
			
			switch (skill.type) {
				case Skill.typeSpell:
				case Skill.typeRange:	
				case Skill.typeMelee:
					Cursors.setCursor(Cursors.cursorCast);
					break;
					
				case Skill.typeNull:
				default:
					Cursors.setCursor(Cursors.cursorDefault);
					break;
			}
		}
	}
}
