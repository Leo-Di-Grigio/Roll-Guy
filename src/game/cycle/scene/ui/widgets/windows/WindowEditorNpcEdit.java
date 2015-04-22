package game.cycle.scene.ui.widgets.windows;

import com.badlogic.gdx.graphics.g2d.BitmapFont.HAlignment;

import game.cycle.scene.game.SceneGame;
import game.cycle.scene.game.state.location.creature.Creature;
import game.cycle.scene.ui.list.UIGame;
import game.cycle.scene.ui.textfilters.TextFilterDefault;
import game.cycle.scene.ui.textfilters.TextFilterNumbers;
import game.cycle.scene.ui.widgets.Button;
import game.cycle.scene.ui.widgets.EquipmentWidget;
import game.cycle.scene.ui.widgets.Image;
import game.cycle.scene.ui.widgets.InventoryWidget;
import game.cycle.scene.ui.widgets.Label;
import game.cycle.scene.ui.widgets.TextField;
import game.cycle.scene.ui.widgets.Window;
import game.resources.Resources;
import game.resources.tex.Tex;
import game.script.ui.editor.ui_NpcEditorMenuCancel;
import game.script.ui.editor.ui_NpcEditorMenuSave;

public class WindowEditorNpcEdit extends Window {
	
	private UIGame uigame;
	
	public static final String uiBackground = "npc-back";
	public static final String uiName = "npc-name";
	public static final String uiStrength = "npc-str";
	public static final String uiAgility = "npc-agi";
	public static final String uiStamina = "npc-stamina";
	public static final String uiPerception = "npc-perception";
	public static final String uiIntelligence = "npc-int";
	public static final String uiWillpower = "npc-willpower";
	public static final String uiTexture = "npc-texture";
	public static final String uiFraction = "npc-fraction";
	public static final String uiCancel = "npc-cancel";
	public static final String uiSave = "npc-save";
	
	public static final String uiLabelName = "label-npc-name";
	public static final String uiLabelStrength = "label-str";
	public static final String uiLabelAgility = "label-agi";
	public static final String uiLabelStamina = "label-stamina";
	public static final String uiLabelPerception = "label-perception";
	public static final String uiLabelIntelligence = "label-int";
	public static final String uiLabelWillpower = "label-willpower";
	public static final String uiLabelTexture = "label-texture";
	public static final String uiLabelFraction = "label-fraction";
	public static final String uiLabelInfo = "label-npc-info";
	
	public Label labelName;
	public Label labelStrength;
	public Label labelAgility;
	public Label labelStamina;
	public Label labelPerception;
	public Label labelIntelligence;
	public Label labelWillpower;
	public Label labelTexture;
	public Label labelInfo;
	public Label labelFraction;
	
	public Image  background;
	public Button cancel;
	public Button save;
	
	public TextField name;
	public TextField strength;
	public TextField agility;
	public TextField stamina;
	public TextField perception;
	public TextField intelligence;
	public TextField willpower;
	public TextField texture;
	public TextField fraction;

	public static final String uiInventory = "-inventory";
	public static final String uiEquipment = "-equipment";
	private InventoryWidget inventory;
	private EquipmentWidget equipment;
	
	public WindowEditorNpcEdit(String title, UIGame ui, int layer, SceneGame scene) {
		super(title, ui, Alignment.CENTER, 650, 24, 0, 0, layer);
		this.setTexNormal(Resources.getTex(Tex.UI_LIST_LINE));
		this.uigame = ui;
		this.setText("Edit NPC");
		
		loadWidgets(ui, scene);
	}
	
	public void setCreature(Creature creature){
		if(creature == null){
			save.setScript(new ui_NpcEditorMenuCancel(uigame));
			equipment.setCreature(null);
			inventory.showContainer(null);
			inventory.update();
			setVisible(false);
		}
		else{
			save.setScript(new ui_NpcEditorMenuSave(uigame, creature));
			
			name.setText(creature.proto().name());
			strength.setText("" + creature.proto().stats().strength);
			agility.setText("" + creature.proto().stats().agility);
			stamina.setText("" + creature.proto().stats().stamina);
			perception.setText("" + creature.proto().stats().perception);
			intelligence.setText("" + creature.proto().stats().intelligence);
			willpower.setText("" + creature.proto().stats().willpower);
			texture.setText("" + creature.proto().tex());
			fraction.setText("" + creature.proto().fraction());
			labelInfo.setText("GUID: " + creature.getGUID());
			equipment.setCreature(creature);
			inventory.showContainer(creature.inventory);
			inventory.update();
			setVisible(true);
		}
	}

	private void loadWidgets(UIGame ui, SceneGame scene) {
		this.closeButton(true);
		this.lockButton(true);
		
		background = new Image(uiBackground);
		background.setSize(650, 250);
		background.setPosition(Alignment.UPCENTER, 0, -24);
		this.add(background);
		
		labelName = new Label(uiLabelName, "Name");
		labelName.setSize(100, 32);
		labelName.setPosition(Alignment.CENTERLEFT, 5, -24);
		labelName.setLayer(1);
		labelName.setTextAlignment(HAlignment.LEFT);
		this.add(labelName);
		
		labelStrength = new Label(uiLabelStrength, "Strength");
		labelStrength.setSize(100, 32);
		labelStrength.setPosition(Alignment.CENTERLEFT, 5, -44);
		labelStrength.setLayer(1);
		labelStrength.setTextAlignment(HAlignment.LEFT);
		this.add(labelStrength);
		
		labelAgility = new Label(uiLabelAgility, "Agility");
		labelAgility.setSize(100, 32);
		labelAgility.setPosition(Alignment.CENTERLEFT, 5, -64);
		labelAgility.setLayer(1);
		labelAgility.setTextAlignment(HAlignment.LEFT);
		this.add(labelAgility);
		
		labelStamina = new Label(uiLabelStamina, "Stamina");
		labelStamina.setSize(100, 32);
		labelStamina.setPosition(Alignment.CENTERLEFT, 5, -84);
		labelStamina.setLayer(1);
		labelStamina.setTextAlignment(HAlignment.LEFT);
		this.add(labelStamina);
		
		labelPerception = new Label(uiLabelPerception, "Perception");
		labelPerception.setSize(100, 32);
		labelPerception.setPosition(Alignment.CENTERLEFT, 5, -104);
		labelPerception.setLayer(1);
		labelPerception.setTextAlignment(HAlignment.LEFT);
		this.add(labelPerception);
		
		labelIntelligence = new Label(uiLabelIntelligence, "Intelligence");
		labelIntelligence.setSize(100, 32);
		labelIntelligence.setPosition(Alignment.CENTERLEFT, 5, -124);
		labelIntelligence.setLayer(1);
		labelIntelligence.setTextAlignment(HAlignment.LEFT);
		this.add(labelIntelligence);
		
		labelWillpower = new Label(uiLabelWillpower, "Willpower");
		labelWillpower.setSize(100, 32);
		labelWillpower.setPosition(Alignment.CENTERLEFT, 5, -144);
		labelWillpower.setLayer(1);
		labelWillpower.setTextAlignment(HAlignment.LEFT);
		this.add(labelWillpower);
		
		labelTexture = new Label(uiLabelTexture, "Texture");
		labelTexture.setSize(100, 32);
		labelTexture.setPosition(Alignment.CENTERLEFT, 5, -164);
		labelTexture.setLayer(1);
		labelTexture.setTextAlignment(HAlignment.LEFT);
		this.add(labelTexture);
		
		labelFraction = new Label(uiLabelFraction, "Fraction");
		labelFraction.setSize(100, 32);
		labelFraction.setPosition(Alignment.CENTERLEFT, 5, -184);
		labelFraction.setLayer(1);
		labelFraction.setTextAlignment(HAlignment.LEFT);
		this.add(labelFraction);
		
		name = new TextField(uiName);
		name.maxTextLength = 22;
		name.setSize(160, 16);
		name.setPosition(Alignment.CENTERLEFT, 110, -24);
		name.setTextFilter(new TextFilterDefault());
		name.setLayer(1);
		this.add(name);
		
		strength = new TextField(uiStrength);
		strength.maxTextLength = 10;
		strength.setSize(160, 16);
		strength.setPosition(Alignment.CENTERLEFT, 110, -44);
		strength.setTextFilter(new TextFilterNumbers(false));
		strength.setLayer(1);
		this.add(strength);
		
		agility = new TextField(uiAgility);
		agility.maxTextLength = 10;
		agility.setSize(160, 16);
		agility.setPosition(Alignment.CENTERLEFT, 110, -64);
		agility.setTextFilter(new TextFilterNumbers(false));
		agility.setLayer(1);
		this.add(agility);
		
		stamina = new TextField(uiStamina);
		stamina.maxTextLength = 10;
		stamina.setSize(160, 16);
		stamina.setPosition(Alignment.CENTERLEFT, 110, -84);
		stamina.setTextFilter(new TextFilterNumbers(false));
		stamina.setLayer(1);
		this.add(stamina);
		
		perception = new TextField(uiPerception);
		perception.maxTextLength = 10;
		perception.setSize(160, 16);
		perception.setPosition(Alignment.CENTERLEFT, 110, -104);
		perception.setTextFilter(new TextFilterNumbers(false));
		perception.setLayer(1);
		this.add(perception);
		
		intelligence = new TextField(uiIntelligence);
		intelligence.maxTextLength = 10;
		intelligence.setSize(160, 16);
		intelligence.setPosition(Alignment.CENTERLEFT, 110, -124);
		intelligence.setTextFilter(new TextFilterNumbers(false));
		intelligence.setLayer(1);
		this.add(intelligence);
		
		willpower = new TextField(uiWillpower);
		willpower.maxTextLength = 10;
		willpower.setSize(160, 16);
		willpower.setPosition(Alignment.CENTERLEFT, 110, -144);
		willpower.setTextFilter(new TextFilterNumbers(false));
		willpower.setLayer(1);
		this.add(willpower);
		
		texture = new TextField(uiTexture);
		texture.maxTextLength = 10;
		texture.setSize(160, 16);
		texture.setPosition(Alignment.CENTERLEFT, 110, -164);
		texture.setTextFilter(new TextFilterNumbers(false));
		texture.setLayer(1);
		this.add(texture);
		
		fraction = new TextField(uiFraction);
		fraction.maxTextLength = 10;
		fraction.setSize(160, 16);
		fraction.setPosition(Alignment.CENTERLEFT, 110, -184);
		fraction.setTextFilter(new TextFilterNumbers(true));
		fraction.setLayer(1);
		this.add(fraction);
		
		labelInfo = new Label(uiLabelInfo, "test");
		labelInfo.setSize(250, 32);
		labelInfo.setPosition(Alignment.CENTERLEFT, 10, -204);
		labelInfo.setLayer(1);
		this.add(labelInfo);
		
		cancel = new Button(uiCancel, "Cancel");
		cancel.setTexNormal(Tex.UI_BACKGROUND_SELECTED_LIGHT);
		cancel.setSize(110, 32);
		cancel.setPosition(Alignment.CENTERLEFT, 10, -235);
		cancel.setScript(new ui_NpcEditorMenuCancel(uigame));
		cancel.setLayer(1);
		this.add(cancel);
		
		save = new Button(uiSave, "Save");
		save.setTexNormal(Tex.UI_BACKGROUND_SELECTED_LIGHT);
		save.setSize(110, 32);
		save.setPosition(Alignment.CENTERLEFT, 140, -235);
		save.setLayer(1);
		this.add(save);
		
		inventory = new InventoryWidget(this.title+uiInventory, ui, 2, 30, -18);
		this.add(inventory);
		
		equipment = new EquipmentWidget(this.title+uiEquipment, ui, 2, 215, -90);
		this.add(equipment);
	}
}