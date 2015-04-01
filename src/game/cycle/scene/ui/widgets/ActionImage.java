package game.cycle.scene.ui.widgets;

import game.cycle.scene.game.world.creature.Player;
import game.cycle.scene.ui.Dragged;
import game.cycle.scene.ui.widgets.windows.WindowPlayerActionBar;
import game.script.ui.game.ui_ActionBarPick;

public class ActionImage extends Image implements Dragged {
	
	private WindowPlayerActionBar window;
	private int actionBarSlot;
	private int clickDeltax;
	private int clickDeltay;
	private boolean dragged;

	public ActionImage(String title, WindowPlayerActionBar window, int actionBarSlot, Player player) {
		super(title);
		this.draggble = true;
		this.window = window;
		this.actionBarSlot = actionBarSlot;
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
}
