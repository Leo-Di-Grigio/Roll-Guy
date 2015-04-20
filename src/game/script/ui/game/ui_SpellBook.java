package game.script.ui.game;

import game.cycle.scene.game.state.location.creature.Player;
import game.cycle.scene.ui.list.UIGame;
import game.script.Script;

public class ui_SpellBook implements Script {

	private Player player;
	private UIGame ui;

	public ui_SpellBook(Player player, UIGame ui) {
		this.player = player;
		this.ui = ui;
	}

	@Override
	public void execute() {
		ui.spellbook.setCretature(player);
	}
}
