package game.script.ui.editor;

import game.cycle.scene.game.state.location.go.GO;
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
		try{ 
			go.setParam1(Integer.parseInt(edit.param1.getText()));
			go.setParam2(Integer.parseInt(edit.param2.getText())); 
			go.setParam3(Integer.parseInt(edit.param3.getText())); 
			go.setParam4(Integer.parseInt(edit.param4.getText())); 
		} 
		catch(NumberFormatException e){ 
			Log.debug("invalid value(s)");
		}
		
		go.script = edit.script.getText();
		edit.setGO(null);
	}
}