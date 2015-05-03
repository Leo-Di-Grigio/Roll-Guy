package game.script.ui.game;

import game.cycle.scene.ui.list.UIGame;
import game.cycle.scene.ui.widgets.windows.WindowPlayerActionBar;
import game.script.Script;
import game.script.game.event.Logic;
import game.state.skill.Skill;

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
			Logic.playerUseSelfCastSkill(skill);
		}
		else{ // target cast
			switch (skill.type) {
				case Skill.TYPE_POINT_BLANK:
					ui.setMode(UIGame.MODE_SKILL_MELEE);
					break;

				case Skill.TYPE_RANGE:
					ui.setMode(UIGame.MODE_SKILL_RANGE);
					break;
					
				case Skill.TYPE_SELFCAST:
					ui.setMode(UIGame.MODE_SKILL_SPELL);
					break;
					
				case Skill.TYPE_NULL:
				default:
					ui.setMode(UIGame.MODE_SKILL_NULL);
					break;
			}
			
			Logic.playerUseSkill(skill);
			actionBar.activateSlot(actionBarSlot);
		}
	}
}
