package game.cycle.scene.game.world.creature;

import game.cycle.scene.game.world.creature.items.Item;
import game.cycle.scene.game.world.database.Database;
import game.cycle.scene.game.world.database.GameConst;
import game.cycle.scene.game.world.map.Location;
import game.cycle.scene.game.world.skill.Skill;
import game.cycle.scene.ui.list.UIGame;

public class Player extends Creature {

	public Skill [] skillpanel;
	private Skill usedSkill;
	
	public Player() {
		super(Database.getCreature(0));
		this.player = true;
		
		skillpanel = new Skill[GameConst.uiActionPanelSlots];
		
		loadTestData();
	}
	
	private void loadTestData() {
		// load skills
		skills.put(0, Database.getSkill(0));
		skills.put(1, Database.getSkill(1));
		skillpanel[0] = skills.get(0);
		skillpanel[1] = skills.get(1);
		
		// load items
		inventory.addItem(new Item(Database.getItem(0)));
		inventory.addItem(new Item(Database.getItem(0)));
		inventory.addItem(new Item(Database.getItem(0)));
		inventory.addItem(new Item(Database.getItem(1)));
		inventory.addItem(new Item(Database.getItem(2)));
		inventory.addItem(new Item(Database.getItem(2)));
		inventory.addItem(new Item(Database.getItem(1)));
		inventory.addItem(new Item(Database.getItem(2)));
		inventory.addItem(new Item(Database.getItem(1)));
		inventory.addItem(new Item(Database.getItem(1)));
		inventory.addItem(new Item(Database.getItem(1)));
		inventory.addItem(new Item(Database.getItem(1)));
		inventory.addItem(new Item(Database.getItem(1)));
		inventory.addItem(new Item(Database.getItem(1)));
	}
	
	@Override
	public void update(Location location) {
		super.update(location);
	}

	public void setUsedSkill(Skill skill) {
		this.usedSkill = skill;
	}
	
	public Skill getUsedSkill(){
		return usedSkill;
	}

	public void showInventory(UIGame ui) {
		ui.invenotry.showInventory(this.inventory);
	}
}