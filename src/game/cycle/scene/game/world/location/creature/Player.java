package game.cycle.scene.game.world.location.creature;

import com.badlogic.gdx.graphics.OrthographicCamera;

import game.cycle.scene.game.world.database.Database;
import game.cycle.scene.game.world.database.GameConst;
import game.cycle.scene.game.world.location.Location;
import game.cycle.scene.game.world.location.go.GO;
import game.cycle.scene.game.world.skill.Skill;
import game.cycle.scene.ui.list.UIGame;
import game.tools.Const;
import game.tools.Tools;

public class Player extends Creature {

	public Skill [] skillpanel;
	private Skill usedSkill;
	
	public Player() {
		super(Const.INVALID_ID, Database.getCreature(0));
		this.player = true;
		
		skillpanel = new Skill[GameConst.UI_ACTION_PANEL_SLOTS];
		
		loadTestData();
	}
	
	private void loadTestData() {
		// load skills
		skillpanel[0] = skills.get(0);
		skillpanel[1] = skills.get(1);
		skillpanel[2] = skills.get(2);
	}
	
	@Override
	public void update(Location location, OrthographicCamera camera) {
		super.update(location, camera);
	}

	public void setUsedSkill(UIGame ui, Skill skill) {		
		if(skill == null){
			resetUsedSkill(ui);
		}
		else{
			this.usedSkill = skill;
		}
	}
	
	public void resetUsedSkill(UIGame ui){
		this.usedSkill = null;
		ui.actionBar.deactiveAll();
		ui.setMode(ui.getMode());
	}
	
	public Skill getUsedSkill(){
		return usedSkill;
	}

	public void showInventory(UIGame ui) {
		ui.invenotry.showContainer(this.inventory);
	}
	
	public void containerGO(GO go, UIGame ui) {
		if(Tools.getRange(go, this) <= GameConst.INTERACT_RANGE){
			if(go.inventory != null){
				ui.openContainer(go.inventory);
			}
		}
	}
}