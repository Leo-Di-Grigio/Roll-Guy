package game.script.ui.game;

import game.cycle.scene.game.world.location.creature.Player;
import game.cycle.scene.ui.list.UIGame;
import game.script.Script;

public class ui_Player implements Script {

	private Player player;
	private UIGame ui;

	public ui_Player(Player player, UIGame uigame) {
		this.player = player;
		this.ui = uigame;
	}

	@Override
	public void execute() {
		ui.player.setCretature(player);
	}
}
