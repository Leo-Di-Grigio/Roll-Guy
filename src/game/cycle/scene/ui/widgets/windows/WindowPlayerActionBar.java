package game.cycle.scene.ui.widgets.windows;

import game.cycle.scene.game.SceneGame;
import game.cycle.scene.game.world.creature.Player;
import game.cycle.scene.game.world.database.GameConst;
import game.cycle.scene.game.world.skill.Skill;
import game.cycle.scene.ui.list.UIGame;
import game.cycle.scene.ui.widgets.Button;
import game.cycle.scene.ui.widgets.Window;
import game.resources.Resources;
import game.resources.Tex;
import game.script.ui.game.ui_EndTurn;
import game.script.ui.game.ui_GameSkill;

public class WindowPlayerActionBar extends Window {
	
	private UIGame uigame;
	public static final String uiSlot = "player-action-";
	public static final String uiEndTurn = "player-action-endturn";
	public Button endTurn;
	public Button [] slots;
	
	public WindowPlayerActionBar(String title, UIGame ui, int layer, SceneGame scene) {
		super(title, ui, Alignment.DOWNCENTER, 24, 48, -307, 0, layer);
		this.uigame = ui;
		this.setTexNormal(Resources.getTex(Tex.uiListLine));
		loadWidgets(scene);
		this.setVisible(true);
	}

	private void loadWidgets(SceneGame scene) {
		this.lockButton(true);
		slots = new Button[GameConst.uiActionPanelSlots];
		
		for(int i = 0; i < slots.length; ++i){
			slots[i] = new Button(uiSlot+i, "" + (i + 1));
			slots[i].setVisible(true);
			slots[i].setSize(48, 48);
			slots[i].setPosition(Alignment.UPLEFT, 26 + i*50, 0);
			this.add(slots[i]);
		}
		
		endTurn = new Button(uiEndTurn, "End turn");
		endTurn.setSize(128, 32);
		endTurn.setPosition(Alignment.UPLEFT, 650, -8);
		endTurn.setScript(new ui_EndTurn(scene));
		this.add(endTurn);
	}

	public void setCreature(Player player) {
		for(int i = 0; i < slots.length; ++i){
			Skill skill = player.skillpanel[i];
			
			if(skill != null){
				slots[i].setIcon(skill.tex);
				slots[i].setScript(new ui_GameSkill(uigame, player, skill));
			}
			else{
				slots[i].setIcon(null);
				slots[i].setScript(null);
			}
		}
	}
}
