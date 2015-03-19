package game.cycle.scene.ui.widgets.windows;

import game.cycle.scene.game.SceneGame;
import game.cycle.scene.game.world.creature.Creature;
import game.cycle.scene.ui.list.UIGame;
import game.cycle.scene.ui.textfilters.TextFilterDefault;
import game.cycle.scene.ui.textfilters.TextFilterNumbers;
import game.cycle.scene.ui.widgets.Button;
import game.cycle.scene.ui.widgets.Image;
import game.cycle.scene.ui.widgets.Label;
import game.cycle.scene.ui.widgets.TextField;
import game.cycle.scene.ui.widgets.Window;
import game.resources.Resources;
import game.resources.Tex;
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
	
	public WindowEditorNpcEdit(String title, UIGame ui, int layer, SceneGame scene) {
		super(title, ui, Alignment.CENTER, 280, 24, 0, 0, layer);
		this.setTexNormal(Resources.getTex(Tex.uiListLine));
		this.uigame = ui;
		this.setText("Edit NPC");
		
		loadWidgets(scene);
	}
	
	public void setCreature(Creature creature){
		if(creature == null){
			setVisible(false);
			save.setScript(new ui_NpcEditorMenuCancel(uigame));
		}
		else{
			setVisible(true);
			save.setScript(new ui_NpcEditorMenuSave(uigame, creature));
			
			name.setText(creature.proto.name);
			strength.setText("" + creature.proto.stats.strength);
			agility.setText("" + creature.proto.stats.agility);
			stamina.setText("" + creature.proto.stats.stamina);
			perception.setText("" + creature.proto.stats.perception);
			intelligence.setText("" + creature.proto.stats.intelligence);
			willpower.setText("" + creature.proto.stats.willpower);
			texture.setText("" + creature.proto.texture);
			labelInfo.setText("GUID: " + creature.getId());
		}
	}

	private void loadWidgets(SceneGame scene) {
		this.closeButton(true);
		this.lockButton(true);
		
		background = new Image(uiBackground);
		background.setSize(280, 230);
		background.setPosition(Alignment.UPCENTER, 0, -24);
		this.add(background);
		
		labelName = new Label(uiLabelName, "Name");
		labelName.setSize(80, 32);
		labelName.setPosition(Alignment.CENTER, -116, -24);
		labelName.setLayer(1);
		this.add(labelName);
		
		labelStrength = new Label(uiLabelStrength, "Strength");
		labelStrength.setSize(85, 32);
		labelStrength.setPosition(Alignment.CENTER, -105, -44);
		labelStrength.setLayer(1);
		this.add(labelStrength);
		
		labelAgility = new Label(uiLabelAgility, "Agility");
		labelAgility.setSize(80, 32);
		labelAgility.setPosition(Alignment.CENTER, -113, -64);
		labelAgility.setLayer(1);
		this.add(labelAgility);
		
		labelStamina = new Label(uiLabelStamina, "Stamina");
		labelStamina.setSize(80, 32);
		labelStamina.setPosition(Alignment.CENTER, -108, -84);
		labelStamina.setLayer(1);
		this.add(labelStamina);
		
		labelPerception = new Label(uiLabelPerception, "Perception");
		labelPerception.setSize(80, 32);
		labelPerception.setPosition(Alignment.CENTER, -98, -104);
		labelPerception.setLayer(1);
		this.add(labelPerception);
		
		labelIntelligence = new Label(uiLabelIntelligence, "Intelligence");
		labelIntelligence.setSize(100, 32);
		labelIntelligence.setPosition(Alignment.CENTER, -94, -124);
		labelIntelligence.setLayer(1);
		this.add(labelIntelligence);
		
		labelWillpower = new Label(uiLabelWillpower, "Willpower");
		labelWillpower.setSize(80, 32);
		labelWillpower.setPosition(Alignment.CENTER, -100, -144);
		labelWillpower.setLayer(1);
		this.add(labelWillpower);
		
		labelTexture = new Label(uiLabelTexture, "Texture");
		labelTexture.setSize(80, 32);
		labelTexture.setPosition(Alignment.CENTER, -109, -164);
		labelTexture.setLayer(1);
		this.add(labelTexture);
		
		name = new TextField(uiName);
		name.maxTextLength = 22;
		name.setSize(160, 16);
		name.setPosition(Alignment.CENTER, 40, -24);
		name.setTextFilter(new TextFilterDefault());
		name.setLayer(1);
		this.add(name);
		
		strength = new TextField(uiStrength);
		strength.maxTextLength = 10;
		strength.setSize(160, 16);
		strength.setPosition(Alignment.CENTER, 40, -44);
		strength.setTextFilter(new TextFilterNumbers(false));
		strength.setLayer(1);
		this.add(strength);
		
		agility = new TextField(uiAgility);
		agility.maxTextLength = 10;
		agility.setSize(160, 16);
		agility.setPosition(Alignment.CENTER, 40, -64);
		agility.setTextFilter(new TextFilterNumbers(false));
		agility.setLayer(1);
		this.add(agility);
		
		stamina = new TextField(uiStamina);
		stamina.maxTextLength = 10;
		stamina.setSize(160, 16);
		stamina.setPosition(Alignment.CENTER, 40, -84);
		stamina.setTextFilter(new TextFilterNumbers(false));
		stamina.setLayer(1);
		this.add(stamina);
		
		perception = new TextField(uiPerception);
		perception.maxTextLength = 10;
		perception.setSize(160, 16);
		perception.setPosition(Alignment.CENTER, 40, -104);
		perception.setTextFilter(new TextFilterNumbers(false));
		perception.setLayer(1);
		this.add(perception);
		
		intelligence = new TextField(uiIntelligence);
		intelligence.maxTextLength = 10;
		intelligence.setSize(160, 16);
		intelligence.setPosition(Alignment.CENTER, 40, -124);
		intelligence.setTextFilter(new TextFilterNumbers(false));
		intelligence.setLayer(1);
		this.add(intelligence);
		
		willpower = new TextField(uiWillpower);
		willpower.maxTextLength = 10;
		willpower.setSize(160, 16);
		willpower.setPosition(Alignment.CENTER, 40, -144);
		willpower.setTextFilter(new TextFilterNumbers(false));
		willpower.setLayer(1);
		this.add(willpower);
		
		texture = new TextField(uiTexture);
		texture.maxTextLength = 10;
		texture.setSize(160, 16);
		texture.setPosition(Alignment.CENTER, 40, -164);
		texture.setTextFilter(new TextFilterNumbers(false));
		texture.setLayer(1);
		this.add(texture);
		
		labelInfo = new Label(uiLabelInfo, "test");
		labelInfo.setSize(250, 32);
		labelInfo.setPosition(Alignment.CENTER, 0, -184);
		labelInfo.setLayer(1);
		this.add(labelInfo);
		
		cancel = new Button(uiCancel, "Cancel");
		cancel.setTexNormal(Tex.uiBackgroundLightSelected);
		cancel.setSize(110, 32);
		cancel.setPosition(Alignment.CENTER, -65, -215);
		cancel.setScript(new ui_NpcEditorMenuCancel(uigame));
		cancel.setLayer(1);
		this.add(cancel);
		
		save = new Button(uiSave, "Save");
		save.setTexNormal(Tex.uiBackgroundLightSelected);
		save.setSize(110, 32);
		save.setPosition(Alignment.CENTER, 65, -215);
		save.setLayer(1);
		this.add(save);
	}
}