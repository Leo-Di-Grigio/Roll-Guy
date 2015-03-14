package game.script.ui.app;

import game.cycle.scene.game.world.creature.Creature;
import game.cycle.scene.game.world.creature.Struct;
import game.cycle.scene.ui.list.UIGame;
import game.script.Script;
import game.tools.Log;

public class ui_NpcEditorMenuSave implements Script {

	private UIGame ui;
	private Creature npc;

	public ui_NpcEditorMenuSave(UIGame ui, Creature npc) {
		this.ui = ui;
		this.npc = npc;
	}

	@Override
	public void execute() {
		try{ npc.stats.strength = Integer.parseInt(ui.npcStrength.getText()); } catch(NumberFormatException e){Log.debug("invalid value strength");}
		try{ npc.stats.agility = Integer.parseInt(ui.npcAgility.getText()); } catch(NumberFormatException e){Log.debug("invalid value agility");}
		try{ npc.stats.stamina = Integer.parseInt(ui.npcStamina.getText()); } catch(NumberFormatException e){Log.debug("invalid value stamina");}
		try{ npc.stats.perception = Integer.parseInt(ui.npcPerception.getText()); } catch(NumberFormatException e){Log.debug("invalid value perception");}
		try{ npc.stats.intelligence = Integer.parseInt(ui.npcIntelligence.getText()); } catch(NumberFormatException e){Log.debug("invalid value intelligence");}
		try{ npc.stats.willpower = Integer.parseInt(ui.npcWillpower.getText()); } catch(NumberFormatException e){Log.debug("invalid value willpower");}
		
		npc.struct = new Struct(npc.stats.stamina);
		ui.setVisibleNPCParamsEdit(null);
	}
}
