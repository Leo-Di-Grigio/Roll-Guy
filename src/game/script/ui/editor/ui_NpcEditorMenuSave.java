package game.script.ui.editor;

import game.cycle.scene.game.state.database.Database;
import game.cycle.scene.game.state.location.creature.Creature;
import game.cycle.scene.ui.list.UIGame;
import game.cycle.scene.ui.widgets.windows.WindowEditorNpcEdit;
import game.resources.Resources;
import game.resources.tex.Tex;
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
		
		try{
			npc.proto().setName(edit.name.getText());
			npc.proto().stats().strength = Integer.parseInt(edit.strength.getText());
			npc.proto().stats().agility = Integer.parseInt(edit.agility.getText());
			npc.proto().stats().stamina = Integer.parseInt(edit.stamina.getText());
			npc.proto().stats().perception = Integer.parseInt(edit.perception.getText());
			npc.proto().stats().intelligence = Integer.parseInt(edit.intelligence.getText());
			npc.proto().stats().willpower = Integer.parseInt(edit.willpower.getText());
			npc.proto().setTex(Integer.parseInt(edit.texture.getText()));
			npc.proto().setFraction(Integer.parseInt(edit.fraction.getText())); 
		} 
		catch(NumberFormatException e){
			Log.err("Error: one of values is incorrect");
		}
		
		if(Resources.getTex(Tex.CREATURE_0 + npc.proto().tex()) == null){
			npc.proto().setTex(Tex.CREATURE_1 - Tex.CREATURE_0);
		}
		
		if(Database.getCreature(npc.proto().id()) != null){
			Database.updateCreature(npc.proto());
		}
		else{
			Database.insertCreature(npc.proto());
		}
		
		Database.loadCreatures();
		ui.loadNpcList();
		ui.npcEdit.setCreature(null);
	}
}
