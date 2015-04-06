package game.script.ui.editor;

import game.cycle.scene.game.world.database.Database;
import game.cycle.scene.game.world.location.creature.Creature;
import game.cycle.scene.ui.list.UIGame;
import game.cycle.scene.ui.widgets.windows.WindowEditorNpcEdit;
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
		WindowEditorNpcEdit edit = ui.npcEdit;
		
		npc.proto.name = edit.name.getText();
		try{ npc.proto.stats.strength = Integer.parseInt(edit.strength.getText()); } catch(NumberFormatException e){Log.debug("invalid value strength");}
		try{ npc.proto.stats.agility = Integer.parseInt(edit.agility.getText()); } catch(NumberFormatException e){Log.debug("invalid value agility");}
		try{ npc.proto.stats.stamina = Integer.parseInt(edit.stamina.getText()); } catch(NumberFormatException e){Log.debug("invalid value stamina");}
		try{ npc.proto.stats.perception = Integer.parseInt(edit.perception.getText()); } catch(NumberFormatException e){Log.debug("invalid value perception");}
		try{ npc.proto.stats.intelligence = Integer.parseInt(edit.intelligence.getText()); } catch(NumberFormatException e){Log.debug("invalid value intelligence");}
		try{ npc.proto.stats.willpower = Integer.parseInt(edit.willpower.getText()); } catch(NumberFormatException e){Log.debug("invalid value willpower");}
		try{ npc.proto.texture = Integer.parseInt(edit.texture.getText()); } catch(NumberFormatException e){Log.debug("invalid value texture");}
		try{ npc.proto.fraction = Integer.parseInt(edit.fraction.getText()); } catch(NumberFormatException e){Log.debug("invalid value fraction");}
		
		
		if(Resources.getTex(Tex.creaturePlayer + npc.proto.texture) == null){
			npc.proto.texture = Tex.creatureNpc - Tex.creaturePlayer;
		}
		
		if(Database.getCreature(npc.proto.id) != null){
			Database.updateCreature(npc.proto);
		}
		else{
			Database.insertCreature(npc.proto);
		}
		
		Database.updateCreatures();
		ui.loadNpcList();
		ui.npcEdit.setCreature(null);
	}
}
