package game.script.ui.app;

import game.cycle.scene.game.world.creature.Creature;
import game.cycle.scene.game.world.creature.CreatureProto;
import game.cycle.scene.game.world.database.Database;
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
		
		if(id != Const.invalidId){
			Creature creature = new Creature(Database.getCreature(id));
			ui.editorNpcEdit.setCreature(creature);
		}
		else{
			CreatureProto proto = new CreatureProto();
			proto.id = Database.getBaseCreature().size();
			proto.name = "";
			Creature creature = new Creature(proto);
			ui.editorNpcEdit.setCreature(creature);
		}
	}
}
