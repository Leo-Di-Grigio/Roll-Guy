package game.cycle.scene.ui.widgets.windows;

import ui.Alignment;
import ui.widgets.SpellBook;
import ui.widgets.SpellBookItem;
import ui.widgets.Window;
import game.cycle.scene.game.state.location.creature.Creature;
import game.cycle.scene.game.state.skill.Skill;
import game.cycle.scene.ui.list.UIGame;
import game.resources.Resources;
import game.resources.tex.Tex;

public class WindowPlayerSpellBook extends Window {

	public static final String uiSpellBook = "-spellbook";
	private SpellBook spellbook;
	
	public WindowPlayerSpellBook(String title, UIGame ui, int layer) {
		super(title, ui, Alignment.CENTER, 300, 24, 0, 0, layer);
		this.setTexNormal(Resources.getTex(Tex.UI_LIST_LINE));
		this.setText("SpellBook");
		loadWidgets();
	}

	private void loadWidgets() {
		this.closeButton(true);
		this.lockButton(true);
		
		spellbook = new SpellBook(this.title+uiSpellBook);
		spellbook.setSize(300, 400);
		spellbook.setPosition(Alignment.UPRIGTH, 0, -24);
		this.add(spellbook);
	}

	public void setCretature(Creature creature) {
		if(creature == null){
			this.setVisible(false);
		}
		else{
			if(this.visible){
				this.setVisible(false);
			}
			else{
				spellbook.clear();
				
				for(Skill skill: creature.skills().skills.values()){
					SpellBookItem item = new SpellBookItem(skill);
					spellbook.addElement(item);
				}
				
				this.setVisible(true);
			}
		}
	}
}
