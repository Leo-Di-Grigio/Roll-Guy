package game.script.ui.game;

import game.cycle.scene.game.world.skill.Skill;
import game.cycle.scene.ui.list.UIGame;
import game.cycle.scene.ui.widgets.windows.WindowPlayerActionBar;
import game.script.Script;
import game.script.game.event.GameEvents;

public class ui_GameSkill implements Script {

	private UIGame ui;
	private Skill skill;
	private WindowPlayerActionBar actionBar;
	private int actionBarSlot;
	
	public ui_GameSkill(UIGame ui, Skill skill, WindowPlayerActionBar actionBar, int actionBarSlot) {
		this.ui = ui;
		this.skill = skill;
		this.actionBar = actionBar;
		this.actionBarSlot = actionBarSlot;
	}

	@Override
	public void execute() {
		actionBar.deactiveAll();
		
		if(skill.range == 0.0f){ // self cast
			GameEvents.playerUseSelfCastSkill(skill);
		}
		else{ // target cast
			switch (skill.type) {
				case Skill.typeMelee:
					ui.setMode(UIGame.modeSkillMelee);
					break;

				case Skill.typeRange:
					ui.setMode(UIGame.modeSkillRange);
					break;
					
				case Skill.typeSpell:
					ui.setMode(UIGame.modeSkillSpell);
					break;
					
				case Skill.typeNull:
				default:
					ui.setMode(UIGame.modeSkillNull);
					break;
			}
			
			GameEvents.playerUseSkill(skill);
			actionBar.activateSlot(actionBarSlot);
		}
	}
}
