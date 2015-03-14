package game.script.ui.app;

import game.cycle.scene.game.world.go.GO;
import game.cycle.scene.ui.list.UIGame;
import game.script.Script;
import game.tools.Log;

public class ui_GOEditorMenuSave implements Script {

	private UIGame ui;
	private GO go;

	public ui_GOEditorMenuSave(UIGame ui, GO go) {
		this.ui = ui;
		this.go = go;
	}

	@Override
	public void execute() {
		try{ go.param1 = Integer.parseInt(ui.goEditParam1.getText()); } catch(NumberFormatException e){Log.debug("invalid value in param1");}
		try{ go.param2 = Integer.parseInt(ui.goEditParam2.getText()); } catch(NumberFormatException e){Log.debug("invalid value in param2");}
		try{ go.param3 = Integer.parseInt(ui.goEditParam3.getText()); } catch(NumberFormatException e){Log.debug("invalid value in param3");}
		try{ go.param4 = Integer.parseInt(ui.goEditParam4.getText()); } catch(NumberFormatException e){Log.debug("invalid value in param4");}
		ui.setVisibleGOParamsEdit(null);
	}
}