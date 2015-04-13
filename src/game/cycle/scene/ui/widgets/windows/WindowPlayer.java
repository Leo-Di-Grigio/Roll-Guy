package game.cycle.scene.ui.widgets.windows;

import com.badlogic.gdx.graphics.g2d.BitmapFont.HAlignment;

import game.cycle.scene.game.world.location.creature.Creature;
import game.cycle.scene.ui.list.UIGame;
import game.cycle.scene.ui.widgets.EquipmentWidget;
import game.cycle.scene.ui.widgets.Image;
import game.cycle.scene.ui.widgets.Label;
import game.cycle.scene.ui.widgets.Window;
import game.resources.Resources;
import game.resources.tex.Tex;

public class WindowPlayer extends Window {
	
	public static final String uiBackground = "player-back";
	
	public static final String uiStrength = "player-strength";
	public static final String uiAgility = "player-alilty";
	public static final String uiStamina = "player-stamina";
	public static final String uiPerception = "player-perception";
	public static final String uiIntelligence = "player-intelligence";
	public static final String uiWillpower = "player-willpower";
	public static final String uiEquipment = "-equipment";
	
	public Image background;
	public EquipmentWidget equip;
	public Label strenght;
	public Label agility;
	public Label stamina;
	public Label perception;
	public Label intelligence;
	public Label willpower;
	
	public WindowPlayer(String title, UIGame ui, int layer) {
		super(title, ui, Alignment.CENTER, 220, 24, 0, 0, layer);
		this.setTexNormal(Resources.getTex(Tex.UI_LIST_LINE));
		this.setText("Player");
		loadWidgets(ui);
	}

	private void loadWidgets(UIGame ui) {
		this.closeButton(true);
		this.lockButton(true);
		
		background = new Image(uiBackground);
		background.setSize(220, 410);
		background.setPosition(Alignment.UPCENTER, 0, -24);
		this.add(background);
		
		equip = new EquipmentWidget(this.title+uiEquipment, ui, 1, 0, -88);
		this.add(equip);
		
		strenght = new Label(uiStrength, "Strenght 10");
		strenght.setSize(128, 16);
		strenght.setPosition(Alignment.DOWNLEFT, 10, -230);
		strenght.setLayer(1);
		strenght.setTextAlignment(HAlignment.LEFT);
		this.add(strenght);
		
		agility = new Label(uiAgility, "Agilty 10");
		agility.setSize(128, 16);
		agility.setPosition(Alignment.DOWNLEFT, 10, -250);
		agility.setLayer(1);
		agility.setTextAlignment(HAlignment.LEFT);
		this.add(agility);
		
		stamina = new Label(uiStamina, "Stamina 10");
		stamina.setSize(128, 16);
		stamina.setPosition(Alignment.DOWNLEFT, 10, -270);
		stamina.setLayer(1);
		stamina.setTextAlignment(HAlignment.LEFT);
		this.add(stamina);
		
		perception = new Label(uiPerception, "Perception 10");
		perception.setSize(128, 16);
		perception.setPosition(Alignment.DOWNLEFT, 10, -290);
		perception.setLayer(1);
		perception.setTextAlignment(HAlignment.LEFT);
		this.add(perception);
		
		intelligence = new Label(uiIntelligence, "Inelligence 10");
		intelligence.setSize(128, 16);
		intelligence.setPosition(Alignment.DOWNLEFT, 10, -310);
		intelligence.setLayer(1);
		intelligence.setTextAlignment(HAlignment.LEFT);
		this.add(intelligence);
		
		willpower = new Label(uiWillpower, "Willpower 10");
		willpower.setSize(128, 16);
		willpower.setPosition(Alignment.DOWNLEFT, 10, -330);
		willpower.setLayer(1);
		willpower.setTextAlignment(HAlignment.LEFT);
		this.add(willpower);
	}
	
	public void setCretature(Creature creature){
		this.equip.setCreature(creature);
		
		if(creature == null){
			this.setVisible(false);
		}
		else{
			strenght.setText("Strenght: " + creature.proto.stats().strength);
			agility.setText("Agility: " + creature.proto.stats().agility);
			stamina.setText("Stamina: " + creature.proto.stats().stamina);
			perception.setText("Perception: " + creature.proto.stats().perception);
			intelligence.setText("Intelligence: " + creature.proto.stats().intelligence);
			willpower.setText("Willpower: " + creature.proto.stats().willpower);
			this.setVisible(true);
		}
	}
}
