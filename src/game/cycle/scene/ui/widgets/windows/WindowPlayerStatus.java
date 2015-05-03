package game.cycle.scene.ui.widgets.windows;

import resources.Resources;
import resources.tex.Tex;
import ui.Alignment;
import ui.Window;
import ui.widgets.used.Image;
import ui.widgets.used.Label;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import game.cycle.scene.game.SceneGame;
import game.cycle.scene.game.state.location.creature.Creature;
import game.cycle.scene.ui.list.UIGame;
import game.tools.Const;

public class WindowPlayerStatus extends Window {
	
	public static final String uiBackground = "player-menu-back"; 
	public static final String uiHead = "player-menu-head";
	public static final String uiHull = "player-menu-hull";
	public static final String uiHandLeft = "player-menu-HL";
	public static final String uiHandRight = "player-menu-HR";
	public static final String uiLegLeft = "player-menu-LL";
	public static final String uiLegRight = "player-menu-LR";
	public static final String uiStat = "player-stats-";
	public static final String uiAp = "player-ap";
	
	public Image background;
	public Label head;
	public Label hull;
	public Label handLeft;
	public Label handRight;
	public Label legLeft;
	public Label legRight;
	public Label ap;

	public Label [] struct;
	
	private Creature creature;
	
	public WindowPlayerStatus(String title, UIGame ui, int layer, SceneGame scene) {
		super(title, ui, Alignment.CENTERLEFT, 128, 24, 0, 0, layer);
		this.setTexNormal(Resources.getTex(Tex.UI_LIST_LINE));
		loadWidgets(scene);
		this.setVisible(true);
		setText("Player");
	}

	public void setCreature(Creature creature){
		this.creature = creature;
		update();
	}
	
	@Override
	public void draw(SpriteBatch sprites) {
		update();
		super.draw(sprites);
	}
	
	public void update(){
		for(int i = 0; i < struct.length - 1; ++i){
			struct[i].setText(creature.struct().getHp(i) + "/" + creature.struct().getHpMax(i));
		}
		
		struct[6].setText(creature.ap + "/" + Const.ACTION_POINTS_MAX);
	}
	
	private void loadWidgets(SceneGame scene) {
		this.lockButton(true);
		
		background = new Image(uiBackground);
		background.setSize(128, 150);
		background.setPosition(Alignment.UPCENTER, 0, -24);
		this.add(background);
		
		head = new Label(uiHead, "Head: ");
		head.setSize(120, 32);
		head.setPosition(Alignment.UPCENTER, 0, -24);
		head.setLayer(1);
		head.setTextAlignment(BitmapFont.HAlignment.LEFT);
		this.add(head);
		
		hull = new Label(uiHull, "Hull: ");
		hull.setSize(120, 32);
		hull.setPosition(Alignment.UPCENTER, 0, -38);
		hull.setLayer(1);
		hull.setTextAlignment(BitmapFont.HAlignment.LEFT);
		this.add(hull);
		
		handLeft = new Label(uiHandLeft, "HL: ");
		handLeft.setSize(120, 32);
		handLeft.setPosition(Alignment.UPCENTER, 0, -52);
		handLeft.setLayer(1);
		handLeft.setTextAlignment(BitmapFont.HAlignment.LEFT);
		this.add(handLeft);
		
		handRight = new Label(uiHandRight, "HR: ");
		handRight.setSize(120, 32);
		handRight.setPosition(Alignment.UPCENTER, 0, -66);
		handRight.setLayer(1);
		handRight.setTextAlignment(BitmapFont.HAlignment.LEFT);
		this.add(handRight);
		
		legLeft = new Label(uiLegLeft, "LL: ");
		legLeft.setSize(120, 32);
		legLeft.setPosition(Alignment.UPCENTER, 0, -80);
		legLeft.setLayer(1);
		legLeft.setTextAlignment(BitmapFont.HAlignment.LEFT);
		this.add(legLeft);
		
		legRight = new Label(uiLegRight, "LR: ");
		legRight.setSize(120, 32);
		legRight.setPosition(Alignment.UPCENTER, 0, -94);
		legRight.setLayer(1);
		legRight.setTextAlignment(BitmapFont.HAlignment.LEFT);
		this.add(legRight);
		
		ap = new Label(uiAp, "AP: ");
		ap.setSize(120, 32);
		ap.setPosition(Alignment.UPCENTER, 0, -108);
		ap.setLayer(1);
		ap.setTextAlignment(BitmapFont.HAlignment.LEFT);
		this.add(ap);
		
		struct = new Label[7];
		for(int i = 0; i < struct.length; ++i){
			struct[i] = new Label(uiStat + i, "");
			struct[i].setSize(80, 32);
			struct[i].setPosition(Alignment.UPCENTER, 24, -24 - i*14);
			struct[i].setLayer(1);
			struct[i].setTextAlignment(BitmapFont.HAlignment.LEFT);
			this.add(struct[i]);
		}
	}
}
