package game.script.ui.app;

import game.cycle.scene.game.world.creature.Creature;
import game.cycle.scene.game.world.database.Database;
import game.cycle.scene.ui.list.UIGame;
import game.resources.Resources;
import game.resources.Tex;
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
		npc.proto.name = ui.npcName.getText();
		try{ npc.proto.stats.strength = Integer.parseInt(ui.npcStrength.getText()); } catch(NumberFormatException e){Log.debug("invalid value strength");}
		try{ npc.proto.stats.agility = Integer.parseInt(ui.npcAgility.getText()); } catch(NumberFormatException e){Log.debug("invalid value agility");}
		try{ npc.proto.stats.stamina = Integer.parseInt(ui.npcStamina.getText()); } catch(NumberFormatException e){Log.debug("invalid value stamina");}
		try{ npc.proto.stats.perception = Integer.parseInt(ui.npcPerception.getText()); } catch(NumberFormatException e){Log.debug("invalid value perception");}
		try{ npc.proto.stats.intelligence = Integer.parseInt(ui.npcIntelligence.getText()); } catch(NumberFormatException e){Log.debug("invalid value intelligence");}
		try{ npc.proto.stats.willpower = Integer.parseInt(ui.npcWillpower.getText()); } catch(NumberFormatException e){Log.debug("invalid value willpower");}
		try{ npc.proto.texture = Integer.parseInt(ui.npcTexture.getText()); } catch(NumberFormatException e){Log.debug("invalid value texture");}
		
		if(Resources.getTex(Tex.creatureCharacter + npc.proto.texture) == null){
			npc.proto.texture = Tex.creatureNpc - Tex.creatureCharacter;
		}
		
		if(Database.getCreature(npc.proto.id) != null){
			Database.updateCreature(npc.proto);
		}
		else{
			Database.insertCreature(npc.proto);
		}
		
		Database.updateCreatures();
		ui.loadNpcList();
		ui.setVisibleNPCParamsEdit(null);
	}
}
