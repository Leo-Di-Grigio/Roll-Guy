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
import game.script.ui.app.ui_NpcEditorMenuCancel;
import game.script.ui.app.ui_NpcEditorMenuSave;

public class WindowEditorNpcEdit extends Window {
	
	private UIGame uigame;
	
	public static final String uiNpcEditBackground = "npc-back";
	public static final String uiNpcEditName = "npc-name";
	public static final String uiNpcEditStrength = "npc-str";
	public static final String uiNpcEditAgility = "npc-agi";
	public static final String uiNpcEditStamina = "npc-stamina";
	public static final String uiNpcEditPerception = "npc-perception";
	public static final String uiNpcEditIntelligence = "npc-int";
	public static final String uiNpcEditWillpower = "npc-willpower";
	public static final String uiNpcEditTexture = "npc-texture"; 
	public static final String uiNpcEditCancel = "npc-cancel";
	public static final String uiNpcEditSave = "npc-save";
	
	public static final String uiLabelNpcEdit = "label-npc";
	public static final String uiLabelNpcName = "label-npc-name";
	public static final String uiLabelNpcStrength = "label-str";
	public static final String uiLabelNpcAgility = "label-agi";
	public static final String uiLabelNpcStamina = "label-stamina";
	public static final String uiLabelNpcPerception = "label-perception";
	public static final String uiLabelNpcIntelligence = "label-int";
	public static final String uiLabelNpcWillpower = "label-willpower";
	public static final String uiLabelNpcTexture = "label-texture";
	public static final String uiLabelNpcTitle = "label-npc-title";
	
	public Label labelNpcEdit;
	public Label labelNpcName;
	public Label labelNpcStrength;
	public Label labelNpcAgility;
	public Label labelNpcStamina;
	public Label labelNpcPerception;
	public Label labelNpcIntelligence;
	public Label labelNpcWillpower;
	public Label labelNpcTexture;
	
	public Image  npcEditBackground;
	public Button npcEditCancel;
	public Button npcEditSave;
	
	public TextField npcName;
	public TextField npcStrength;
	public TextField npcAgility;
	public TextField npcStamina;
	public TextField npcPerception;
	public TextField npcIntelligence;
	public TextField npcWillpower;
	public TextField npcTexture;
	public Label     npcEditTitle;
	
	public WindowEditorNpcEdit(String title, UIGame ui, int layer, SceneGame scene) {
		super(title, ui, Alignment.CENTER, 280, 200, 0, 0, layer);
		this.setTexNormal(Resources.getTex(Tex.uiListLine));
		this.uigame = ui;
		this.closeButton(true);
		
		loadWidgets(scene);
	}
	
	public void setCreature(Creature creature){
		if(creature == null){
			setVisible(false);
			npcEditSave.setScript(new ui_NpcEditorMenuCancel(uigame));
		}
		else{
			setVisible(true);
			npcEditSave.setScript(new ui_NpcEditorMenuSave(uigame, creature));
			
			npcName.setText(creature.proto.name);
			npcStrength.setText("" + creature.proto.stats.strength);
			npcAgility.setText("" + creature.proto.stats.agility);
			npcStamina.setText("" + creature.proto.stats.stamina);
			npcPerception.setText("" + creature.proto.stats.perception);
			npcIntelligence.setText("" + creature.proto.stats.intelligence);
			npcWillpower.setText("" + creature.proto.stats.willpower);
			npcTexture.setText("" + creature.proto.texture);
			npcEditTitle.setText("GUID: " + creature.id);
		}
	}

	private void loadWidgets(SceneGame scene) {
		labelNpcEdit = new Label(uiLabelNpcEdit, "Edit NPC");
		labelNpcEdit.setSize(128, 32);
		labelNpcEdit.setPosition(Alignment.CENTER, 0, 85);
		this.add(labelNpcEdit);
		
		labelNpcName = new Label(uiLabelNpcName, "Name");
		labelNpcName.setSize(80, 32);
		labelNpcName.setPosition(Alignment.CENTER, -116, 65);
		this.add(labelNpcName);
		
		labelNpcStrength = new Label(uiLabelNpcStrength, "Strength");
		labelNpcStrength.setSize(85, 32);
		labelNpcStrength.setPosition(Alignment.CENTER, -105, 45);
		this.add(labelNpcStrength);
		
		labelNpcAgility = new Label(uiLabelNpcAgility, "Agility");
		labelNpcAgility.setSize(80, 32);
		labelNpcAgility.setPosition(Alignment.CENTER, -113, 25);
		this.add(labelNpcAgility);
		
		labelNpcStamina = new Label(uiLabelNpcStamina, "Stamina");
		labelNpcStamina.setSize(80, 32);
		labelNpcStamina.setPosition(Alignment.CENTER, -108, 5);
		this.add(labelNpcStamina);
		
		labelNpcPerception = new Label(uiLabelNpcPerception, "Perception");
		labelNpcPerception.setSize(80, 32);
		labelNpcPerception.setPosition(Alignment.CENTER, -98, -15);
		this.add(labelNpcPerception);
		
		labelNpcIntelligence = new Label(uiLabelNpcIntelligence, "Intelligence");
		labelNpcIntelligence.setSize(100, 32);
		labelNpcIntelligence.setPosition(Alignment.CENTER, -94, -35);
		this.add(labelNpcIntelligence);
		
		labelNpcWillpower = new Label(uiLabelNpcWillpower, "Willpower");
		labelNpcWillpower.setSize(80, 32);
		labelNpcWillpower.setPosition(Alignment.CENTER, -100, -55);
		this.add(labelNpcWillpower);
		
		labelNpcTexture = new Label(uiLabelNpcTexture, "Texture");
		labelNpcTexture.setSize(80, 32);
		labelNpcTexture.setPosition(Alignment.CENTER, -109, -75);
		this.add(labelNpcTexture);
		
		npcName = new TextField(uiNpcEditName);
		npcName.maxTextLength = 22;
		npcName.setSize(160, 16);
		npcName.setPosition(Alignment.CENTER, 40, 65);
		npcName.setTextFilter(new TextFilterDefault());
		this.add(npcName);
		
		npcStrength = new TextField(uiNpcEditStrength);
		npcStrength.maxTextLength = 10;
		npcStrength.setSize(160, 16);
		npcStrength.setPosition(Alignment.CENTER, 40, 45);
		npcStrength.setTextFilter(new TextFilterNumbers(false));
		this.add(npcStrength);
		
		npcAgility = new TextField(uiNpcEditAgility);
		npcAgility.maxTextLength = 10;
		npcAgility.setSize(160, 16);
		npcAgility.setPosition(Alignment.CENTER, 40, 25);
		npcAgility.setTextFilter(new TextFilterNumbers(false));
		this.add(npcAgility);
		
		npcStamina = new TextField(uiNpcEditStamina);
		npcStamina.maxTextLength = 10;
		npcStamina.setSize(160, 16);
		npcStamina.setPosition(Alignment.CENTER, 40, 5);
		npcStamina.setTextFilter(new TextFilterNumbers(false));
		this.add(npcStamina);
		
		npcPerception = new TextField(uiNpcEditPerception);
		npcPerception.maxTextLength = 10;
		npcPerception.setSize(160, 16);
		npcPerception.setPosition(Alignment.CENTER, 40, -15);
		npcPerception.setTextFilter(new TextFilterNumbers(false));
		this.add(npcPerception);
		
		npcIntelligence = new TextField(uiNpcEditIntelligence);
		npcIntelligence.maxTextLength = 10;
		npcIntelligence.setSize(160, 16);
		npcIntelligence.setPosition(Alignment.CENTER, 40, -35);
		npcIntelligence.setTextFilter(new TextFilterNumbers(false));
		this.add(npcIntelligence);
		
		npcWillpower = new TextField(uiNpcEditWillpower);
		npcWillpower.maxTextLength = 10;
		npcWillpower.setSize(160, 16);
		npcWillpower.setPosition(Alignment.CENTER, 40, -55);
		npcWillpower.setTextFilter(new TextFilterNumbers(false));
		this.add(npcWillpower);
		
		npcTexture = new TextField(uiNpcEditTexture);
		npcTexture.maxTextLength = 10;
		npcTexture.setSize(160, 16);
		npcTexture.setPosition(Alignment.CENTER, 40, -75);
		npcTexture.setLayer(2);
		npcTexture.setTextFilter(new TextFilterNumbers(false));
		this.add(npcTexture);
		
		npcEditTitle = new Label(uiLabelNpcTitle, "test");
		npcEditTitle.setSize(250, 32);
		npcEditTitle.setPosition(Alignment.CENTER, 0, -100);
		this.add(npcEditTitle);
		
		npcEditCancel = new Button(uiNpcEditCancel, "Cancel");
		npcEditCancel.setSize(128, 32);
		npcEditCancel.setPosition(Alignment.CENTER, -70, -165);
		npcEditCancel.setScript(new ui_NpcEditorMenuCancel(uigame));
		this.add(npcEditCancel);
		
		npcEditSave = new Button(uiNpcEditSave, "Save");
		npcEditSave.setSize(128, 32);
		npcEditSave.setPosition(Alignment.CENTER, 70, -165);
		this.add(npcEditSave);
	}
}