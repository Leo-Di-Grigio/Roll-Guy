package game.script.ui.game;

import game.cycle.scene.game.world.location.creature.Player;
import game.cycle.scene.ui.list.UIGame;
import game.script.Script;

public class ui_Inventory implements Script {

	private UIGame ui;
	private Player player;

	public ui_Inventory(Player player, UIGame ui) {
		this.player = player;
		this.ui = ui;
	}

	@Override
	public void execute() {
		player.showInventory(ui);
	}
}
