package game.cycle.scene.ui.widgets.windows;

import game.cycle.scene.game.SceneGame;
import game.cycle.scene.game.world.creature.Player;
import game.cycle.scene.ui.list.UIGame;
import game.cycle.scene.ui.widgets.Button;
import game.cycle.scene.ui.widgets.Window;
import game.resources.Resources;
import game.resources.Tex;
import game.script.ui.game.ui_Inventory;
import game.script.ui.game.ui_Player;

public class WindowPlayerMenu extends Window {
	
	private UIGame uigame;
	
	public static final String uiPlayer = "player-menu-player";
	public static final String uiInventory = "player-menu-inventory";
	public static final String uiSkills = "player-menu-skills";
	public static final String uiJournal = "player-menu-journal";
	
	public Button player;
	public Button inventory;
	public Button skills;
	public Button journal;
	
	public WindowPlayerMenu(String title, UIGame ui, int layer, SceneGame scene) {
		super(title, ui, Alignment.DOWNRIGHT, 128, 24, 0, 160, layer);
		this.uigame = ui;
		this.setTexNormal(Resources.getTex(Tex.uiListLine));
		loadWidgets(scene);
		this.setVisible(true);
	}

	private void loadWidgets(SceneGame scene) {
		this.lockButton(true);
		
		player = new Button(uiPlayer, "Player");
		player.setSize(128, 32);
		player.setPosition(Alignment.UPCENTER, 0, -24);
		this.add(player);
		
		inventory = new Button(uiInventory, "Inventory");
		inventory.setSize(128, 32);
		inventory.setPosition(Alignment.UPCENTER, 0, -58);
		this.add(inventory);
		
		skills = new Button(uiSkills, "Skills");
		skills.setSize(128, 32);
		skills.setPosition(Alignment.UPCENTER, 0, -92);
		this.add(skills);
		
		journal = new Button(uiJournal, "Journal");
		journal.setSize(128, 32);
		journal.setPosition(Alignment.UPCENTER, 0, -126);
		this.add(journal);
	}

	public void setCreature(Player player) {
		this.inventory.setScript(new ui_Inventory(player, uigame));
		this.player.setScript(new ui_Player(player, uigame));
	}
}
