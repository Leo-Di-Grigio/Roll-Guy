package game.cycle.scene.ui.widgets;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import game.cycle.scene.game.world.location.creature.Player;
import game.cycle.scene.ui.interfaces.Dragged;
import game.cycle.scene.ui.widgets.windows.WindowPlayerActionBar;
import game.resources.Resources;
import game.resources.Tex;
import game.script.ui.game.ui_ActionBarPick;

public class ActionImage extends Image implements Dragged {
	
	private WindowPlayerActionBar window;
	private int actionBarSlot;
	private int clickDeltax;
	private int clickDeltay;
	
	private boolean dragged;
	private boolean active; 

	public ActionImage(String title, WindowPlayerActionBar window, int actionBarSlot, Player player) {
		super(title);
		this.draggble = true;
		this.window = window;
		this.actionBarSlot = actionBarSlot;
		this.texSelected = Resources.getTex(Tex.uiUseSkillFrame);
	}

	public void setActive(boolean active) {
		this.active = active;
	}
	
	@Override
	public void dragg(int x, int y) {	
		if(!dragged){
			clickDeltax = this.x - x;
			clickDeltay = this.y - y;
			dragged = true;
		}
		
		int dx = this.x - x - clickDeltax;
		int dy = this.y - y - clickDeltay;
		
		if(dx*dx+dy*dy > 256){ // 16px - skill pickup sensivity
			new ui_ActionBarPick(window, actionBarSlot).execute();
		}
	}
	
	@Override
	public void resetDragg() {
		dragged = false;
	}
	
	@Override
	public void draw(SpriteBatch sprites) {
		super.draw(sprites);
		
		if(this.active){
			sprites.draw(texSelected, x, y, sizeX, sizeY);
		}
	}
}
