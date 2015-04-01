package game.resources;

import game.cycle.scene.game.world.creature.items.Item;
import game.cycle.scene.game.world.skill.Skill;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;

public class Cursors {

	public static final int cursorDefault = 0;
	public static final int cursorTalking = 1;
	public static final int cursorCast = 2;
	
	// data
	private static HashMap<Integer, Pixmap> cursors;
	
	// selecting data
	private static Item selectedItem;
	private static Skill selectedSkill;
	
	public Cursors(HashMap<Integer, Pixmap> cursors) {
		Cursors.cursors = cursors;
	}

	public static void setCursor(int key) {
		if(cursors.get(key) != null){
			Gdx.input.setCursorImage(cursors.get(key), 0, 0);
		}
	}

	public static void selectItem(Item item) {
		selectedItem = item;
	}
	
	public static Item getSelectedItem(){
		return selectedItem;
	}

	public static void setSelectedSkill(Skill skill){
		selectedSkill = skill;
	}
	
	public static Skill getSelectedSkill() {
		return selectedSkill;
	}
}
