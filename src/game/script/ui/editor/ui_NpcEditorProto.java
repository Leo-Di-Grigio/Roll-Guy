package game.script.ui.editor;

import game.cycle.scene.game.state.database.Database;
import game.cycle.scene.game.state.database.proto.CreatureProto;
import game.cycle.scene.game.state.location.creature.Creature;
import game.cycle.scene.ui.list.UIGame;
import game.script.Script;
import game.tools.Const;

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
