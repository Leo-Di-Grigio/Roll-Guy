package game.script.ui.editor;

import tools.Const;
import game.cycle.scene.ui.list.UIGame;
import game.script.Script;
import game.state.database.Database;
import game.state.database.proto.CreatureProto;
import game.state.location.creature.Creature;

public class ui_NpcEditorProto implements Script {

	private UIGame ui;

	public ui_NpcEditorProto(UIGame ui) {
		this.ui = ui;
	}

	@Override
	public void execute() {
		int id = ui.getSelectedListNpc();
		
		if(id != Const.INVALID_ID){
			Creature creature = new Creature(Const.INVALID_ID, Database.getCreature(id));
			ui.npcEdit.setCreature(creature);
		}
		else{
			CreatureProto proto = new CreatureProto(Database.getBaseCreature().size(), "");
			Creature creature = new Creature(Const.INVALID_ID, proto);
			ui.npcEdit.setCreature(creature);
		}
	}
}
