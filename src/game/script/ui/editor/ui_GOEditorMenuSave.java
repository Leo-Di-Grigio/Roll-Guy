package game.script.ui.editor;

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
		
		try{ go.triggers[0] = Integer.parseInt(edit.trigger1.getText()); } catch(NumberFormatException e){Log.debug("invalid value");}
		try{ go.scripts[0] = Integer.parseInt(edit.script1.getText()); } catch(NumberFormatException e){Log.debug("invalid value");}
		try{ go.params1[0] = Integer.parseInt(edit.script1param1.getText()); } catch(NumberFormatException e){Log.debug("invalid value");}
		try{ go.params2[0] = Integer.parseInt(edit.script1param2.getText()); } catch(NumberFormatException e){Log.debug("invalid value");}
		try{ go.params3[0] = Integer.parseInt(edit.script1param3.getText()); } catch(NumberFormatException e){Log.debug("invalid value");}
		try{ go.params4[0] = Integer.parseInt(edit.script1param4.getText()); } catch(NumberFormatException e){Log.debug("invalid value");}

		try{ go.triggers[1] = Integer.parseInt(edit.trigger2.getText()); } catch(NumberFormatException e){Log.debug("invalid value");}
		try{ go.scripts[1] = Integer.parseInt(edit.script2.getText()); } catch(NumberFormatException e){Log.debug("invalid value");}
		try{ go.params1[1] = Integer.parseInt(edit.script2param1.getText()); } catch(NumberFormatException e){Log.debug("invalid value");}
		try{ go.params2[1] = Integer.parseInt(edit.script2param2.getText()); } catch(NumberFormatException e){Log.debug("invalid value");}
		try{ go.params3[1] = Integer.parseInt(edit.script2param3.getText()); } catch(NumberFormatException e){Log.debug("invalid value");}
		try{ go.params4[1] = Integer.parseInt(edit.script2param4.getText()); } catch(NumberFormatException e){Log.debug("invalid value");}
		
		try{ go.triggers[2] = Integer.parseInt(edit.trigger3.getText()); } catch(NumberFormatException e){Log.debug("invalid value");}
		try{ go.scripts[2] = Integer.parseInt(edit.script3.getText()); } catch(NumberFormatException e){Log.debug("invalid value");}
		try{ go.params1[2] = Integer.parseInt(edit.script3param1.getText()); } catch(NumberFormatException e){Log.debug("invalid value");}
		try{ go.params2[2] = Integer.parseInt(edit.script3param2.getText()); } catch(NumberFormatException e){Log.debug("invalid value");}
		try{ go.params3[2] = Integer.parseInt(edit.script3param3.getText()); } catch(NumberFormatException e){Log.debug("invalid value");}
		try{ go.params4[2] = Integer.parseInt(edit.script3param4.getText()); } catch(NumberFormatException e){Log.debug("invalid value");}

		try{ go.triggers[3] = Integer.parseInt(edit.trigger4.getText()); } catch(NumberFormatException e){Log.debug("invalid value");}
		try{ go.scripts[3] = Integer.parseInt(edit.script4.getText()); } catch(NumberFormatException e){Log.debug("invalid value");}
		try{ go.params1[3] = Integer.parseInt(edit.script4param1.getText()); } catch(NumberFormatException e){Log.debug("invalid value");}
		try{ go.params2[3] = Integer.parseInt(edit.script4param2.getText()); } catch(NumberFormatException e){Log.debug("invalid value");}
		try{ go.params3[3] = Integer.parseInt(edit.script4param3.getText()); } catch(NumberFormatException e){Log.debug("invalid value");}
		try{ go.params4[3] = Integer.parseInt(edit.script4param4.getText()); } catch(NumberFormatException e){Log.debug("invalid value");}
		
		edit.setGO(null);
	}
}