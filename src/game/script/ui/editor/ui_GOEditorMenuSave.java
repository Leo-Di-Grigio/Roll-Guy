package game.script.ui.editor;

import game.cycle.scene.game.world.database.GameConst;
import game.cycle.scene.game.world.go.GO;
import game.cycle.scene.ui.widgets.windows.WindowEditorGOEdit;
import game.script.Script;
import game.tools.Log;

public class ui_GOEditorMenuSave implements Script {

	private WindowEditorGOEdit edit;
	private GO go;

	public ui_GOEditorMenuSave(WindowEditorGOEdit edit, GO go) {
		this.go = go;
		this.edit = edit;
	}

	@Override
	public void execute() {
		try{ go.param1 = Integer.parseInt(edit.param1.getText()); } catch(NumberFormatException e){Log.debug("invalid value in param1");}
		try{ go.param2 = Integer.parseInt(edit.param2.getText()); } catch(NumberFormatException e){Log.debug("invalid value in param2");}
		try{ go.param3 = Integer.parseInt(edit.param3.getText()); } catch(NumberFormatException e){Log.debug("invalid value in param3");}
		try{ go.param4 = Integer.parseInt(edit.param4.getText()); } catch(NumberFormatException e){Log.debug("invalid value in param4");}
		
		for(int i = 0; i < GameConst.goTriggersCount; ++i){
			try{ go.triggerType[i] = Integer.parseInt(edit.trigger[i].getText()); } catch(NumberFormatException e){Log.debug("invalid value");}
			try{ go.triggerParam[i] = Integer.parseInt(edit.triggerParam[i].getText()); } catch(NumberFormatException e){Log.debug("invalid value");}
			try{ go.scripts[i] = Integer.parseInt(edit.script[i].getText()); } catch(NumberFormatException e){Log.debug("invalid value");}
			try{ go.params1[i] = Integer.parseInt(edit.scriptParam1[i].getText()); } catch(NumberFormatException e){Log.debug("invalid value");}
			try{ go.params2[i] = Integer.parseInt(edit.scriptParam1[i].getText()); } catch(NumberFormatException e){Log.debug("invalid value");}
			try{ go.params3[i] = Integer.parseInt(edit.scriptParam1[i].getText()); } catch(NumberFormatException e){Log.debug("invalid value");}
			try{ go.params4[i] = Integer.parseInt(edit.scriptParam1[i].getText()); } catch(NumberFormatException e){Log.debug("invalid value");}
		}
		
		edit.setGO(null);
	}
}