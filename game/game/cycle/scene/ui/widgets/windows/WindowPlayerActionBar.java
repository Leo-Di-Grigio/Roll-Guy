package game.cycle.scene.ui.widgets.windows;

import resources.Cursors;
import resources.Resources;
import resources.tex.Tex;
import tools.Const;
import ui.Alignment;
import ui.Tooltip;
import ui.Window;
import ui.widgets.used.ActionImage;
import ui.widgets.used.Button;
import ui.widgets.used.Image;
import game.cycle.scene.ui.list.UIGame;
import game.script.game.event.Logic;
import game.script.ui.game.ui_ActionBarDrop;
import game.script.ui.game.ui_EndTurn;
import game.script.ui.game.ui_GameSkill;
import game.script.ui.game.ui_SwitchMode;
import game.state.location.creature.Player;
import game.state.skill.Skill;

public class WindowPlayerActionBar extends Window {
	
	public static final String uiSlot = "player-action-";
	public static final String uiSkill = "player-action-skill-slot-";
	public static final String uiEndTurn = "player-action-endturn";
	public static final String uiTactics = "player-action-tactics";
	
	public Button endTurn;
	public Button switchMode;
	
	private Image [] slots;
	private ActionImage [] skills;
	private Player player;
	private UIGame ui;
	
	public WindowPlayerActionBar(String title, UIGame uigame, int layer) {
		super(title, uigame, Alignment.DOWNCENTER, 24, 48, -307, 0, layer);
		this.setTexNormal(Resources.getTex(Tex.UI_LIST_LINE));
		loadWidgets();
		this.ui = uigame;
		this.setVisible(true);
		this.endTurn.setVisible(false);
	}

	private void loadWidgets() {
		this.lockButton(true);
		slots = new Image[Const.UI_ACTION_PANEL_SLOTS];
		skills = new ActionImage[Const.UI_ACTION_PANEL_SLOTS];
		
		for(int i = 0; i < slots.length; ++i){
			slots[i] = new Image(uiSkill + i);
			slots[i].setVisible(true);
			slots[i].setSize(48, 48);
			slots[i].setPosition(Alignment.UPLEFT, 28 + i*50, 0);
			slots[i].setScript(new ui_ActionBarDrop(this, i));
			this.add(slots[i]);
		}
		
		endTurn = new Button(uiEndTurn, "End turn");
		endTurn.setSize(128, 32);
		endTurn.setPosition(Alignment.UPLEFT, 650, -8);
		endTurn.setScript(new ui_EndTurn());
		this.add(endTurn);
		
		switchMode = new Button(uiTactics, "Tactics");
		switchMode.setSize(128, 32);
		switchMode.setPosition(Alignment.UPLEFT, 650, 32);
		switchMode.setScript(new ui_SwitchMode());
		this.add(switchMode);
	}

	public void setCreature(Player player) {
		this.player = player;
		for(int i = 0; i < skills.length; ++i){
			Skill skill = player.skillpanel[i];
			player.skillpanel[i] = null;
			
			if(skill != null){
				dropSkill(skill, i);
			}
		}
	}

	public void dropSkill(Skill skill, int actionBarSlot) {
		skills[actionBarSlot] = new ActionImage(uiSlot + actionBarSlot, this, actionBarSlot, player);
		skills[actionBarSlot].setSize(48, 48);
		skills[actionBarSlot].setPosition(Alignment.UPLEFT, 28 + actionBarSlot*50, 0);
		skills[actionBarSlot].setTexNormal(skill.tex);
		skills[actionBarSlot].setLayer(1);
		skills[actionBarSlot].setTooltip(new Tooltip(skill.title, skill.tooltip));
		skills[actionBarSlot].setVisible(true);
		skills[actionBarSlot].setScript(new ui_GameSkill(ui, skill, this, actionBarSlot));
		this.add(skills[actionBarSlot]);
		
		player.skillpanel[actionBarSlot] = skill;
		player.setUsedSkill(ui, null);
		deactiveAll();
	}

	public void pickSkill(int actionBarSlot) {
		if(player != null && Cursors.getSelectedItem() == null && Cursors.getSelectedSkill() == null){
			Cursors.setSelectedSkill(player.skillpanel[actionBarSlot]);
			this.remove(skills[actionBarSlot].getTitle());
			skills[actionBarSlot] = null;
			player.skillpanel[actionBarSlot] = null;
			Logic.playerUseSkill(null);
			
			ui.setMode(ui.getMode());
		}
	}

	public void deactiveAll(){
		for(int i = 0; i < skills.length; ++i){
			if(skills[i] != null){
				skills[i].setActive(false);
			}
		}
	}
	
	public void activateSlot(int actionBarSlot) {
		if(skills[actionBarSlot] != null){
			skills[actionBarSlot].setActive(true);
		}
	}
	
	public void clickAction(int slot){
		if(slot >= 0 && slot < skills.length){
			if(skills[slot] != null){
				skills[slot].execute();
			}
		}
	}
}
