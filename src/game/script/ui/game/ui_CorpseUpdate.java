package game.script.ui.game;

import game.cycle.scene.ui.widgets.windows.WindowCorpse;
import game.script.Script;

public class ui_CorpseUpdate implements Script {

	private WindowCorpse window;

	public  ui_CorpseUpdate(WindowCorpse window) {
		this.window = window;
	}

	@Override
	public void execute() {
		this.window.showCreature(null);
	}
}
