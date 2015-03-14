package game.cycle.scene.ui.list;

import java.util.ArrayList;
import java.util.HashMap;

import game.cycle.scene.game.SceneGame;
import game.cycle.scene.game.world.creature.Creature;
import game.cycle.scene.game.world.creature.CreatureProto;
import game.cycle.scene.game.world.creature.Player;
import game.cycle.scene.game.world.database.Database;
import game.cycle.scene.game.world.go.GO;
import game.cycle.scene.game.world.go.GOProto;
import game.cycle.scene.game.world.map.LocationProto;
import game.cycle.scene.game.world.map.TerrainProto;
import game.cycle.scene.ui.UI;
import game.cycle.scene.ui.Widget.Alignment;
import game.cycle.scene.ui.textfilters.TextFilterNumbers;
import game.cycle.scene.ui.widgets.Button;
import game.cycle.scene.ui.widgets.Dialog;
import game.cycle.scene.ui.widgets.Image;
import game.cycle.scene.ui.widgets.Label;
import game.cycle.scene.ui.widgets.List;
import game.cycle.scene.ui.widgets.ListItem;
import game.cycle.scene.ui.widgets.TextField;
import game.script.ui.ui_DialogClose;
import game.script.ui.app.ui_ExitGame;
import game.script.ui.app.ui_FreeCameraMode;
import game.script.ui.app.ui_GOEditorMenuCancel;
import game.script.ui.app.ui_GOEditorMenuSave;
import game.script.ui.app.ui_GameClickMode;
import game.script.ui.app.ui_LocationAdd;
import game.script.ui.app.ui_LocationAddMenuCancel;
import game.script.ui.app.ui_LocationLoad;
import game.script.ui.app.ui_LocationSave;
import game.script.ui.app.ui_LocationDel;
import game.script.ui.app.ui_NpcEditorMenuCancel;
import game.script.ui.app.ui_NpcEditorMenuSave;
import game.tools.Const;

public class UIGame extends UI {

	private SceneGame scene;
	
	// common UI
	public static final String uiMainMenu = "main-menu";
	public static final String uiFreeCamera = "free-camera";
	
	// player UI
	public static final String uiPlayerAttack = "player-attack";
	public static final String uiPlayerUse = "player-use";
	public Button playerAttack;
	public Button playerUse;
	
	// Editor Ui
	public static final String uiEditorTerrain = "editor-terrain";
	public static final String uiEditorTerrainList = "editor-terrain-list";
	public static final String uiEditorNpc = "editor-npc";
	public static final String uiEditorNpcEdit = "editor-npc-edit";
	public static final String uiEditorNpcList = "editor-npc-list";
	public static final String uiEditorGO = "editor-go";
	public static final String uiEditorGOAdd = "editor-go-add";
	public static final String uiEditorGOEdit = "editor-go-edit";
	public static final String uiEditorGOList = "editor-go-list";
	public static final String uiEditorLocation ="editor-location";
	public static final String uiEditorLocationLoad ="editor-location-load";
	public static final String uiEditorLocationAdd ="editor-location-add";
	public static final String uiEditorLocationDelete ="editor-location-delete";
	public static final String uiEditorLocationEdit ="editor-location-edit";
	public static final String uiEditorLocationList = "editor-location-list";
	public static final String uiEditorSave = "editor-save";

	public Button editorTerrain;
	public Button editorNpc;
	public Button editorNpcEdit;
	public Button editorGO;
	public Button editorGOAdd;
	public Button editorGOEdit;
	public Button editorLocation;
	public Button editorLocationLoad;
	public Button editorLocationAdd;
	public Button editorLocationDelete;
	public Button editorLocationEdit;
	public Button editorSave;
	public List   editorListTerrain;
	public List   editorListNpc;
	public List   editorListGO;
	public List   editorListLocation;
	
	// Location create
	public static final String uiCreateBackground = "create-back";
	public static final String uiCreateCancel = "create-cancel";
	public static final String uiCreateConfirm = "create-confirm";
	
	public static final String uiCreateTitle = "create-title";
	public static final String uiCreateFile = "create-file";
	public static final String uiCreateNote = "create-note";
	public static final String uiCreateSizeX = "create-sizeX";
	public static final String uiCreateSizeY = "create-sizeY";
	public static final String uiCreateStartTerrain = "create-start-terrain";
	
	public static final String uiLabelCreate = "label-create";
	public static final String uiLabelTitle = "label-title-field";
	public static final String uiLabelFile = "label-file-field";
	public static final String uiLabelNote = "label-title-note";
	public static final String uiLabelSizeX = "label-title-sizex";
	public static final String uiLabelSizeY = "label-title-sizey";
	public static final String uiLabelStartTerrain = "label-title-startterrain";
	
	public boolean locationCreateVisible;
	
	public Label labelCreate;
	public Label labelTitle;
	public Label labelFile;
	public Label labelNote;
	public Label labelSizeX;
	public Label labelSizeY;
	public Label labelStartTerrain;
	
	public Image  createLocationBackground;
	public Button createLocationCancel;
	public Button createLocationConfirm;
	
	public TextField createLocationTitle;
	public TextField createLocationFile;
	public TextField createLocationNote;
	public TextField createLocationSizeX;
	public TextField createLocationSizeY;
	public TextField createLocationStartTerrain;
	
	// GO editor
	public static final String uiGOEditBackground = "goedit-back";
	public static final String uiGOEditParam1 = "goedit-param1";
	public static final String uiGOEditParam2 = "goedit-param2";
	public static final String uiGOEditParam3 = "goedit-param3";
	public static final String uiGOEditParam4 = "goedit-param4";
	public static final String uiGOEditSave = "goedit-save";
	public static final String uiGOEditCancel = "goedit-cancel";
	
	public static final String uiLabelGOEdit = "label-goedit";
	public static final String uiLabelGOEditParam1 = "label-goedit-param1";
	public static final String uiLabelGOEditParam2 = "label-goedit-param2";
	public static final String uiLabelGOEditParam3 = "label-goedit-param3";
	public static final String uiLabelGOEditParam4 = "label-goedit-param4";
	public static final String uiGOEditTitle = "label-go-title";
	
	public Label labelGOEdit;
	public Label labelGOParam1;
	public Label labelGOParam2;
	public Label labelGOParam3;
	public Label labelGOParam4;
	
	public Image  goEditBackground;
	public Button goEditCancel;
	public Button goEditSave;
	
	public TextField goEditParam1;
	public TextField goEditParam2;
	public TextField goEditParam3;
	public TextField goEditParam4;
	public Label goEditTitle;
	
	// NPC Editor
	public static final String uiNpcEditBackground = "npc-back";
	public static final String uiNpcEditStrength = "npc-str";
	public static final String uiNpcEditAgility = "npc-agi";
	public static final String uiNpcEditStamina = "npc-stamina";
	public static final String uiNpcEditPerception = "npc-perception";
	public static final String uiNpcEditIntelligence = "npc-int";
	public static final String uiNpcEditWillpower = "npc-willpower";
	public static final String uiNpcEditCancel = "npc-cancel";
	public static final String uiNpcEditSave = "npc-save";
	
	public static final String uiLabelNpcEdit = "label-npc";
	public static final String uiLabelNpcStrength = "label-str";
	public static final String uiLabelNpcAgility = "label-agi";
	public static final String uiLabelNpcStamina = "label-stamina";
	public static final String uiLabelNpcPerception = "label-perception";
	public static final String uiLabelNpcIntelligence = "label-int";
	public static final String uiLabelNpcWillpower = "label-willpower";
	public static final String uiLabelNpcTitle = "label-npc-title";
	
	public Label labelNpcEdit;
	public Label labelNpcStrength;
	public Label labelNpcAgility;
	public Label labelNpcStamina;
	public Label labelNpcPerception;
	public Label labelNpcIntelligence;
	public Label labelNpcWillpower;
	
	public Image  npcEditBackground;
	public Button npcEditCancel;
	public Button npcEditSave;
	
	public TextField npcStrength;
	public TextField npcAgility;
	public TextField npcStamina;
	public TextField npcPerception;
	public TextField npcIntelligence;
	public TextField npcWillpower;
	public Label     npcEditTitle;
	
	// NPC Dialog
	public static final String uiDialog = "dialog";
	public static final String uiDialogClose ="dialog-close"; 
	public Dialog dialog;
	public Button dialogClose;
	
	public UIGame(SceneGame sceneGame) {
		super();
		this.scene = sceneGame;
		
		commonMenu();
		playerActions();
		editor();
		createLocation();
		goEdit();
		npcEdit();
		npcDialog();
	}
	
	private void commonMenu() {
		Button button = new Button(uiMainMenu, "Main menu");
		button.visible = true;
		button.setSize(128, 32);
		button.setPosition(Alignment.UPRIGTH, 0, 0);
		button.setScript(new ui_ExitGame());
		this.add(button);
		
		button = new Button(uiFreeCamera, "Free camera");
		button.visible = true;
		button.setSize(128, 32);
		button.setPosition(Alignment.UPRIGTH, 0, -34);
		button.setScript(new ui_FreeCameraMode(scene, button));
		this.add(button);
	}

	private void editor() {
		editorTerrain = new Button(uiEditorTerrain, "Terrain");
		editorTerrain.visible = true;
		editorTerrain.setSize(128, 32);
		editorTerrain.setPosition(Alignment.UPRIGTH, 0, -102);
		editorTerrain.setScript(new ui_GameClickMode(scene, SceneGame.clickTerrain));
		this.add(editorTerrain);
		
		editorListTerrain = new List(uiEditorTerrainList);
		editorListTerrain.setSize(260, 300);
		editorListTerrain.setVisible(16);
		editorListTerrain.setPosition(Alignment.UPRIGTH, -130, -102);
		this.add(editorListTerrain);
		loadTerrainList();
		
		editorNpc = new Button(uiEditorNpc, "NPC");
		editorNpc.visible = true;
		editorNpc.setSize(128, 32);
		editorNpc.setPosition(Alignment.UPRIGTH, 0, -136);
		editorNpc.setScript(new ui_GameClickMode(scene, SceneGame.clickEditorNpc));
		this.add(editorNpc); 

		editorNpcEdit = new Button(uiEditorNpcEdit, "Edit");
		editorNpcEdit.setSize(64, 32);
		editorNpcEdit.setPosition(Alignment.UPRIGTH, -392, -136);
		editorNpcEdit.setScript(new ui_GameClickMode(scene, SceneGame.clickEditorNpcEdit));
		this.add(editorNpcEdit);
		
		editorListNpc = new List(uiEditorNpcList);
		editorListNpc.setSize(260, 300);
		editorListNpc.setVisible(16);
		editorListNpc.setPosition(Alignment.UPRIGTH, -130, -136);
		this.add(editorListNpc);
		loadNpcList();
		
		editorGO = new Button(uiEditorGO, "Game Object");
		editorGO.visible = true;
		editorGO.setSize(128, 32);
		editorGO.setPosition(Alignment.UPRIGTH, 0, -170);
		editorGO.setScript(new ui_GameClickMode(scene, SceneGame.clickEditorGO));
		this.add(editorGO);
		
		editorGOAdd = new Button(uiEditorGOAdd, "Add");
		editorGOAdd.setSize(64, 32);
		editorGOAdd.setPosition(Alignment.UPRIGTH, -392, -170);
		editorGOAdd.setScript(new ui_GameClickMode(scene, SceneGame.clickEditorGOAdd));
		this.add(editorGOAdd);
		
		editorGOEdit = new Button(uiEditorGOEdit, "Edit");
		editorGOEdit.setSize(64, 32);
		editorGOEdit.setPosition(Alignment.UPRIGTH, -392, -204);
		editorGOEdit.setScript(new ui_GameClickMode(scene, SceneGame.clickEditorGOEdit));
		this.add(editorGOEdit);
		
		editorListGO = new List(uiEditorGOList);
		editorListGO.setSize(260, 300);
		editorListGO.setVisible(16);
		editorListGO.setPosition(Alignment.UPRIGTH, -130, -170);
		this.add(editorListGO);
		loadGOList();
		
		editorLocation = new Button(uiEditorLocation, "Locations");
		editorLocation.visible = true;
		editorLocation.setSize(128, 32);
		editorLocation.setPosition(Alignment.UPRIGTH, 0, -204);
		editorLocation.setScript(new ui_GameClickMode(scene, SceneGame.clickEditorLocation));
		this.add(editorLocation);
		
		editorLocationLoad = new Button(uiEditorLocationLoad, "Load");
		editorLocationLoad.setSize(64, 32);
		editorLocationLoad.setPosition(Alignment.UPRIGTH, -392, -204);
		editorLocationLoad.setScript(new ui_LocationLoad(this, scene));
		this.add(editorLocationLoad);
		
		editorLocationAdd = new Button(uiEditorLocationAdd, "Add");
		editorLocationAdd.setSize(64, 32);
		editorLocationAdd.setPosition(Alignment.UPRIGTH, -392, -238);
		editorLocationAdd.setScript(new ui_LocationAddMenuCancel(this));
		this.add(editorLocationAdd);
	
		editorLocationDelete = new Button(uiEditorLocationDelete, "Delete");
		editorLocationDelete.setSize(64, 32);
		editorLocationDelete.setPosition(Alignment.UPRIGTH, -392, -272);
		editorLocationDelete.setScript(new ui_LocationDel(this));
		this.add(editorLocationDelete);
		
		editorLocationEdit = new Button(uiEditorLocationEdit, "Edit");
		editorLocationEdit.setSize(64, 32);
		editorLocationEdit.setPosition(Alignment.UPRIGTH, -392, -306);
		this.add(editorLocationEdit);

		editorListLocation = new List(uiEditorLocationList);
		editorListLocation.setSize(260, 300);
		editorListLocation.setVisible(16);
		editorListLocation.setPosition(Alignment.UPRIGTH, -130, -204);
		this.add(editorListLocation);
		loadLocationList();
		
		editorSave = new Button(uiEditorSave, "Save");
		editorSave.visible = true;
		editorSave.setSize(128, 32);
		editorSave.setPosition(Alignment.UPRIGTH, 0, -272);
		editorSave.setScript(new ui_LocationSave(scene));
		this.add(editorSave);
	}

	private void createLocation() {
		createLocationBackground = new Image(uiCreateBackground);
		createLocationBackground.setSize(280, 200);
		createLocationBackground.setPosition(Alignment.CENTER, 0, 0);
		createLocationBackground.setLayer(1);
		this.add(createLocationBackground);
		
		labelCreate = new Label(uiLabelCreate, "Create Location");
		labelCreate.setSize(128, 32);
		labelCreate.setPosition(Alignment.CENTER, 0, 85);
		labelCreate.setLayer(2);
		this.add(labelCreate);
		
		labelTitle = new Label(uiLabelTitle, "Title");
		labelTitle.setSize(35, 32);
		labelTitle.setPosition(Alignment.CENTER, -120, 65);
		labelTitle.setLayer(2);
		this.add(labelTitle);
		
		labelFile = new Label(uiLabelFile, "File");
		labelFile.setSize(35, 32);
		labelFile.setPosition(Alignment.CENTER, -123, 45);
		labelFile.setLayer(2);
		this.add(labelFile);
		
		labelNote = new Label(uiLabelNote, "Note");
		labelNote.setSize(35, 32);
		labelNote.setPosition(Alignment.CENTER, -120, 25);
		labelNote.setLayer(2);
		this.add(labelNote);
		
		labelSizeX = new Label(uiLabelSizeX, "Size X");
		labelSizeX.setSize(45, 32);
		labelSizeX.setPosition(Alignment.CENTER, -115, 5);
		labelSizeX.setLayer(2);
		this.add(labelSizeX);
		
		labelSizeY = new Label(uiLabelSizeY, "Size Y");
		labelSizeY.setSize(45, 32);
		labelSizeY.setPosition(Alignment.CENTER, 0, 5);
		labelSizeY.setLayer(2);
		this.add(labelSizeY);
		
		labelStartTerrain = new Label(uiLabelStartTerrain, "Terrain");
		labelStartTerrain.setSize(50, 32);
		labelStartTerrain.setPosition(Alignment.CENTER, -112, -15);
		labelStartTerrain.setLayer(2);
		this.add(labelStartTerrain);
		
		createLocationTitle = new TextField(uiCreateTitle);
		createLocationTitle.maxTextLength = 30;
		createLocationTitle.setSize(230, 16);
		createLocationTitle.setPosition(Alignment.CENTER, 15, 65);
		createLocationTitle.setLayer(2);
		this.add(createLocationTitle);
		
		createLocationFile = new TextField(uiCreateFile);
		createLocationFile.maxTextLength = 30;
		createLocationFile.setSize(230, 16);
		createLocationFile.setPosition(Alignment.CENTER, 15, 45);
		createLocationFile.setLayer(2);
		this.add(createLocationFile);
		
		createLocationNote = new TextField(uiCreateNote);
		createLocationNote.maxTextLength = 30;
		createLocationNote.setSize(230, 16);
		createLocationNote.setPosition(Alignment.CENTER, 15, 25);
		createLocationNote.setLayer(2);
		this.add(createLocationNote);
		
		createLocationSizeX = new TextField(uiCreateSizeX);
		createLocationSizeX.maxTextLength = 4;
		createLocationSizeX.setSize(60, 16);
		createLocationSizeX.setPosition(Alignment.CENTER, -55, 5);
		createLocationSizeX.setLayer(2);
		createLocationSizeX.setTextFilter(new TextFilterNumbers(true));
		this.add(createLocationSizeX);
		
		createLocationSizeY = new TextField(uiCreateSizeY);
		createLocationSizeY.maxTextLength = 4;
		createLocationSizeY.setSize(60, 16);
		createLocationSizeY.setPosition(Alignment.CENTER, 60, 5);
		createLocationSizeY.setLayer(2);
		createLocationSizeY.setTextFilter(new TextFilterNumbers(true));
		this.add(createLocationSizeY);
		
		createLocationStartTerrain = new TextField(uiCreateStartTerrain);
		createLocationStartTerrain.maxTextLength = 3;
		createLocationStartTerrain.setSize(60, 16);
		createLocationStartTerrain.setPosition(Alignment.CENTER, -55, -15);
		createLocationStartTerrain.setLayer(2);
		createLocationStartTerrain.setTextFilter(new TextFilterNumbers(true));
		this.add(createLocationStartTerrain);
				
		createLocationCancel = new Button(uiCreateCancel, "Cancel");
		createLocationCancel.setSize(128, 32);
		createLocationCancel.setPosition(Alignment.CENTER, -70, -80);
		createLocationCancel.setLayer(2);
		createLocationCancel.setScript(new ui_LocationAddMenuCancel(this));
		this.add(createLocationCancel);
		
		createLocationConfirm = new Button(uiCreateConfirm, "Confirm");
		createLocationConfirm.setSize(128, 32);
		createLocationConfirm.setPosition(Alignment.CENTER, 70, -80);
		createLocationConfirm.setLayer(2);
		createLocationConfirm.setScript(new ui_LocationAdd(this, scene));
		this.add(createLocationConfirm);
	}

	private void goEdit() {
		goEditBackground = new Image(uiGOEditBackground);
		goEditBackground.setSize(280, 200);
		goEditBackground.setPosition(Alignment.CENTER, 0, 0);
		goEditBackground.setLayer(1);
		this.add(goEditBackground);
		
		labelGOEdit = new Label(uiLabelGOEdit, "Edit Game Object");
		labelGOEdit.setSize(128, 32);
		labelGOEdit.setPosition(Alignment.CENTER, 0, 85);
		labelGOEdit.setLayer(2);
		this.add(labelGOEdit);
		
		labelGOParam1 = new Label(uiLabelGOEditParam1, "param 1");
		labelGOParam1.setSize(60, 32);
		labelGOParam1.setPosition(Alignment.CENTER, -110, 65);
		labelGOParam1.setLayer(2);
		this.add(labelGOParam1);
		
		labelGOParam2 = new Label(uiLabelGOEditParam2, "param 2");
		labelGOParam2.setSize(60, 32);
		labelGOParam2.setPosition(Alignment.CENTER, -110, 45);
		labelGOParam2.setLayer(2);
		this.add(labelGOParam2);
		
		labelGOParam3 = new Label(uiLabelGOEditParam3, "param 3");
		labelGOParam3.setSize(60, 32);
		labelGOParam3.setPosition(Alignment.CENTER, -110, 25);
		labelGOParam3.setLayer(2);
		this.add(labelGOParam3);
		
		labelGOParam4 = new Label(uiLabelGOEditParam4, "param 4");
		labelGOParam4.setSize(60, 32);
		labelGOParam4.setPosition(Alignment.CENTER, -110, 5);
		labelGOParam4.setLayer(2);
		this.add(labelGOParam4);
		//
		goEditParam1 = new TextField(uiGOEditParam1);
		goEditParam1.maxTextLength = 10;
		goEditParam1.setSize(210, 16);
		goEditParam1.setPosition(Alignment.CENTER, 30, 65);
		goEditParam1.setLayer(2);
		goEditParam1.setTextFilter(new TextFilterNumbers(false));
		this.add(goEditParam1);
		
		goEditParam2 = new TextField(uiGOEditParam2);
		goEditParam2.maxTextLength = 10;
		goEditParam2.setSize(210, 16);
		goEditParam2.setPosition(Alignment.CENTER, 30, 45);
		goEditParam2.setLayer(2);
		goEditParam2.setTextFilter(new TextFilterNumbers(false));
		this.add(goEditParam2);
		
		goEditParam3 = new TextField(uiGOEditParam3);
		goEditParam3.maxTextLength = 10;
		goEditParam3.setSize(210, 16);
		goEditParam3.setPosition(Alignment.CENTER, 30, 25);
		goEditParam3.setLayer(2);
		goEditParam3.setTextFilter(new TextFilterNumbers(false));
		this.add(goEditParam3);
		
		goEditParam4 = new TextField(uiGOEditParam4);
		goEditParam4.maxTextLength = 10;
		goEditParam4.setSize(210, 16);
		goEditParam4.setPosition(Alignment.CENTER, 30, 5);
		goEditParam4.setLayer(2);
		goEditParam4.setTextFilter(new TextFilterNumbers(false));
		this.add(goEditParam4);
		
		goEditTitle = new Label(uiGOEditTitle, "test");
		goEditTitle.setSize(250, 32);
		goEditTitle.setPosition(Alignment.CENTER, 0, -20);
		goEditTitle.setLayer(2);
		this.add(goEditTitle);
		
		goEditCancel = new Button(uiGOEditCancel, "Cancel");
		goEditCancel.setSize(128, 32);
		goEditCancel.setPosition(Alignment.CENTER, -70, -80);
		goEditCancel.setLayer(2);
		goEditCancel.setScript(new ui_GOEditorMenuCancel(this));
		this.add(goEditCancel);
		
		goEditSave = new Button(uiGOEditSave, "Save");
		goEditSave.setSize(128, 32);
		goEditSave.setPosition(Alignment.CENTER, 70, -80);
		goEditSave.setLayer(2);
		this.add(goEditSave);
	}

	private void npcEdit() {
		npcEditBackground = new Image(uiNpcEditBackground);
		npcEditBackground.setSize(280, 290);
		npcEditBackground.setPosition(Alignment.CENTER, 0, -45);
		npcEditBackground.setLayer(1);
		this.add(npcEditBackground);
		
		labelNpcEdit = new Label(uiLabelNpcEdit, "Edit NPC");
		labelNpcEdit.setSize(128, 32);
		labelNpcEdit.setPosition(Alignment.CENTER, 0, 85);
		labelNpcEdit.setLayer(2);
		this.add(labelNpcEdit);
		
		labelNpcStrength = new Label(uiLabelNpcStrength, "Strength");
		labelNpcStrength.setSize(85, 32);
		labelNpcStrength.setPosition(Alignment.CENTER, -105, 65);
		labelNpcStrength.setLayer(2);
		this.add(labelNpcStrength);
		
		labelNpcAgility = new Label(uiLabelNpcAgility, "Agility");
		labelNpcAgility.setSize(80, 32);
		labelNpcAgility.setPosition(Alignment.CENTER, -113, 45);
		labelNpcAgility.setLayer(2);
		this.add(labelNpcAgility);
		
		labelNpcStamina = new Label(uiLabelNpcStamina, "Stamina");
		labelNpcStamina.setSize(80, 32);
		labelNpcStamina.setPosition(Alignment.CENTER, -108, 25);
		labelNpcStamina.setLayer(2);
		this.add(labelNpcStamina);
		
		labelNpcPerception = new Label(uiLabelNpcPerception, "Perception");
		labelNpcPerception.setSize(80, 32);
		labelNpcPerception.setPosition(Alignment.CENTER, -98, 5);
		labelNpcPerception.setLayer(2);
		this.add(labelNpcPerception);
		
		labelNpcIntelligence = new Label(uiLabelNpcIntelligence, "Intelligence");
		labelNpcIntelligence.setSize(100, 32);
		labelNpcIntelligence.setPosition(Alignment.CENTER, -94, -15);
		labelNpcIntelligence.setLayer(2);
		this.add(labelNpcIntelligence);
		
		labelNpcWillpower = new Label(uiLabelNpcWillpower, "Willpower");
		labelNpcWillpower.setSize(80, 32);
		labelNpcWillpower.setPosition(Alignment.CENTER, -100, -35);
		labelNpcWillpower.setLayer(2);
		this.add(labelNpcWillpower);
		
		npcStrength = new TextField(uiNpcEditStrength);
		npcStrength.maxTextLength = 10;
		npcStrength.setSize(160, 16);
		npcStrength.setPosition(Alignment.CENTER, 40, 65);
		npcStrength.setLayer(2);
		npcStrength.setTextFilter(new TextFilterNumbers(false));
		this.add(npcStrength);
		
		npcAgility = new TextField(uiNpcEditAgility);
		npcAgility.maxTextLength = 10;
		npcAgility.setSize(160, 16);
		npcAgility.setPosition(Alignment.CENTER, 40, 45);
		npcAgility.setLayer(2);
		npcAgility.setTextFilter(new TextFilterNumbers(false));
		this.add(npcAgility);
		
		npcStamina = new TextField(uiNpcEditStamina);
		npcStamina.maxTextLength = 10;
		npcStamina.setSize(160, 16);
		npcStamina.setPosition(Alignment.CENTER, 40, 25);
		npcStamina.setLayer(2);
		npcStamina.setTextFilter(new TextFilterNumbers(false));
		this.add(npcStamina);
		
		npcPerception = new TextField(uiNpcEditPerception);
		npcPerception.maxTextLength = 10;
		npcPerception.setSize(160, 16);
		npcPerception.setPosition(Alignment.CENTER, 40, 5);
		npcPerception.setLayer(2);
		npcPerception.setTextFilter(new TextFilterNumbers(false));
		this.add(npcPerception);
		
		npcIntelligence = new TextField(uiNpcEditIntelligence);
		npcIntelligence.maxTextLength = 10;
		npcIntelligence.setSize(160, 16);
		npcIntelligence.setPosition(Alignment.CENTER, 40, -15);
		npcIntelligence.setLayer(2);
		npcIntelligence.setTextFilter(new TextFilterNumbers(false));
		this.add(npcIntelligence);
		
		npcWillpower = new TextField(uiNpcEditWillpower);
		npcWillpower.maxTextLength = 10;
		npcWillpower.setSize(160, 16);
		npcWillpower.setPosition(Alignment.CENTER, 40, -35);
		npcWillpower.setLayer(2);
		npcWillpower.setTextFilter(new TextFilterNumbers(false));
		this.add(npcWillpower);
		
		npcEditTitle = new Label(uiLabelNpcTitle, "test");
		npcEditTitle.setSize(250, 32);
		npcEditTitle.setPosition(Alignment.CENTER, 0, -60);
		npcEditTitle.setLayer(2);
		this.add(npcEditTitle);
		
		npcEditCancel = new Button(uiNpcEditCancel, "Cancel");
		npcEditCancel.setSize(128, 32);
		npcEditCancel.setPosition(Alignment.CENTER, -70, -165);
		npcEditCancel.setLayer(2);
		npcEditCancel.setScript(new ui_NpcEditorMenuCancel(this));
		this.add(npcEditCancel);
		
		npcEditSave = new Button(uiNpcEditSave, "Save");
		npcEditSave.setSize(128, 32);
		npcEditSave.setPosition(Alignment.CENTER, 70, -165);
		npcEditSave.setLayer(2);
		this.add(npcEditSave);
	}
	
	public void loadLocationList() {
		editorListLocation.clear();
		HashMap<Integer, LocationProto> base = Database.getBaseLocations();
		
		ArrayList<Boolean> mask = new ArrayList<Boolean>();
		mask.add(0, true);
		mask.add(1, false);
		mask.add(2, false);
		
		for(Integer key: base.keySet()){
			ArrayList<String> data = new ArrayList<String>();
			data.add(0, "" + key);
			data.add(1, "ID: " + key);
			data.add(2, " \""+base.get(key).title+"\"");
			
			ListItem item = new ListItem(data, mask);
			item.setFormatter("");
			editorListLocation.addElement(item);
		}
	}

	private void loadTerrainList() {
		editorListTerrain.clear();
		HashMap<Integer, TerrainProto> base = Database.getBaseTerrain();
		
		ArrayList<Boolean> mask = new ArrayList<Boolean>();
		mask.add(0, true);
		mask.add(1, false);
		
		for(Integer key: base.keySet()){
			ArrayList<String> data = new ArrayList<String>();
			
			data.add(0, ""+key);
			data.add(1, base.get(key).title);
			
			ListItem item = new ListItem(data, mask);
			item.setFormatter("");
			editorListTerrain.addElement(item);
		}
	}

	private void loadNpcList() {
		editorListNpc.clear();
		
		HashMap<Integer, CreatureProto> base = Database.getBaseCreature();
		
		for(Integer key: base.keySet()){
			ArrayList<String> data = new ArrayList<String>();
			data.add(0, ""+key);
			data.add(1, base.get(key).name);
			
			ListItem item = new ListItem(data);
			editorListNpc.addElement(item);
		}
	}

	private void loadGOList() {
		editorListGO.clear();
		HashMap<Integer, GOProto> base = Database.getBaseGO();
		
		for(Integer key: base.keySet()){
			ArrayList<String> data = new ArrayList<String>();
			data.add(0, ""+key);
			data.add(1, base.get(key).title);
			
			ListItem item = new ListItem(data);
			editorListGO.addElement(item);
		}
	}

	private void playerActions() {
		playerAttack = new Button(uiPlayerAttack, "Attack");
		playerAttack.visible = true;
		playerAttack.setSize(128, 32);
		playerAttack.setPosition(Alignment.DOWNCENTER, 0, 0);
		playerAttack.setScript(new ui_GameClickMode(scene, SceneGame.clickPlayerAttack));
		this.add(playerAttack);
		
		playerUse = new Button(uiPlayerUse, "Use");
		playerUse.visible = true;
		playerUse.setSize(128, 32);
		playerUse.setPosition(Alignment.DOWNCENTER, 0, 34);
		playerUse.setScript(new ui_GameClickMode(scene, SceneGame.clickPlayerUse));
		this.add(playerUse);
	}
	
	private void npcDialog() {
		dialog = new Dialog(uiDialog);
		dialog.setLayer(1);
		this.add(dialog);
		
		dialogClose = new Button(uiDialogClose, "x");
		dialogClose.setSize(24, 24);
		dialogClose.setPosition(Alignment.CENTERLEFT, 476, 276);
		dialogClose.setScript(new ui_DialogClose(dialog, dialogClose));
		dialogClose.setLayer(2);
		this.add(dialogClose);
	}
	
	public void setClickMode(int valuePrevious, int valueNew){
		// off
		switch (valuePrevious) {
			case SceneGame.clickPlayerAttack:
				playerAttack.setActive(false);
				break;
				
			case SceneGame.clickPlayerUse:
				playerUse.setActive(false);
				break;
				
			case SceneGame.clickTerrain:
				editorTerrain.setActive(false);
				editorListTerrain.visible = false;
				break;

			case SceneGame.clickEditorNpc:
				editorNpc.setActive(false);
				setVisibleNPCEditor(false);
				setVisibleNPCParamsEdit(null);
				break;
				
			case SceneGame.clickEditorNpcEdit:
				editorNpcEdit.setActive(false);
				setVisibleNPCEditor(false);
				setVisibleNPCParamsEdit(null);
				break;
			
			case SceneGame.clickEditorGO:
				editorGO.setActive(false);
				setVisibleGOEditor(false);
				setVisibleGOParamsEdit(null);
				break;
				
			case SceneGame.clickEditorGOAdd:
				editorGOAdd.setActive(false);
				setVisibleGOEditor(true);
				break;
			
			case SceneGame.clickEditorGOEdit:
				editorGOEdit.setActive(false);
				setVisibleGOEditor(true);
				break;
				
			case SceneGame.clickEditorLocation:
				editorLocation.setActive(false);
				editorListLocation.visible = false;
				editorLocationLoad.visible = false;
				editorLocationAdd.visible = false;
				editorLocationDelete.visible = false;
				editorLocationEdit.visible = false;
				break;
				
			default:
				break;
		}
		
		// on
		switch (valueNew) {
			case SceneGame.clickPlayerAttack:
				playerAttack.setActive(true);
				break;

			case SceneGame.clickPlayerUse:
				playerUse.setActive(true);
				break;
				
			case SceneGame.clickTerrain:
				editorTerrain.setActive(true);
				editorListTerrain.visible = true;
				break;
				
			case SceneGame.clickEditorNpc:
				editorNpc.setActive(true);
				setVisibleNPCEditor(true);
				break;
				
			case SceneGame.clickEditorNpcEdit:
				editorNpcEdit.setActive(true);
				setVisibleNPCEditor(true);
				break;
				
			case SceneGame.clickEditorGO:
				editorGO.setActive(true);
				setVisibleGOEditor(true);
				break;
				
			case SceneGame.clickEditorGOAdd:
				editorGOAdd.setActive(true);
				setVisibleGOEditor(true);
				break;
			
			case SceneGame.clickEditorGOEdit:
				editorGOEdit.setActive(true);
				setVisibleGOEditor(true);
				break;
				
			case SceneGame.clickEditorLocation:
				editorLocation.setActive(true);
				editorListLocation.visible = true;
				editorLocationLoad.visible = true;
				editorLocationAdd.visible = true;
				editorLocationDelete.visible = true;
				editorLocationEdit.visible = true;
				break;
				
			default:
				break;
		}
	}

	public void npcTalk(UIGame ui, Player player, Creature npc) {
		dialog.setCreature(npc);
		dialog.visible = true;
		dialogClose.visible = true;
	}

	public boolean isDialog() {
		return dialog.visible;
	}

	public int getSelectedListTerrain() {
		ListItem item = editorListTerrain.getSelected();
		if(item != null){
			return Integer.parseInt(item.get(0));
		}
		else{
			return Const.invalidId;
		}
	}

	public int getSelectedListNpc() {
		ListItem item = editorListNpc.getSelected();
		
		if(item != null){
			return Integer.parseInt(item.get(0));
		}
		else{
			return Const.invalidId;
		}
	}
	
	public int getSelectedListGO(){
		ListItem item = editorListGO.getSelected();
		
		if(item != null){
			return Integer.parseInt(item.get(0));
		}
		else{
			return Const.invalidId;
		}
	}

	public int getSelectedListLocation() {
		ListItem item = editorListLocation.getSelected();
		
		if(item != null){
			return Integer.parseInt(item.get(0));
		}
		else{
			return Const.invalidId;
		}
	}
	
	public void setVisibleGOEditor(boolean visible){
		editorGOAdd.visible = visible;
		editorGOEdit.visible = visible;
		editorListGO.visible = visible;
	}
		
	public void setVisibleCreteLocation(boolean visible){
		locationCreateVisible = visible;
		
		labelCreate.visible = visible;
		labelTitle.visible = visible;
		labelFile.visible = visible;
		labelNote.visible = visible;
		labelSizeX.visible = visible;
		labelSizeY.visible = visible;
		labelStartTerrain.visible = visible;
		
		createLocationBackground.visible = visible;
		createLocationCancel.visible = visible;
		createLocationConfirm.visible = visible;
		
		createLocationTitle.visible = visible;
		createLocationFile.visible = visible;
		createLocationNote.visible = visible;
		createLocationSizeX.visible = visible;
		createLocationSizeY.visible = visible;
		createLocationStartTerrain.visible = visible;
		
		if(!visible){
			createLocationTitle.setText("");
			createLocationFile.setText("");
			createLocationNote.setText("");
			createLocationSizeX.setText("");
			createLocationSizeY.setText("");
			createLocationStartTerrain.setText("");
		}
	}
	
	public void setVisibleGOParamsEdit(GO go){
		boolean visible;
		if(go == null){
			visible = false;
			goEditSave.setScript(new ui_GOEditorMenuCancel(this));
		}
		else{
			visible = true;
			goEditSave.setScript(new ui_GOEditorMenuSave(this, go));
			goEditParam1.setText("" + go.param1);
			goEditParam2.setText("" + go.param2);
			goEditParam3.setText("" + go.param3);
			goEditParam4.setText("" + go.param4);
			goEditTitle.setText("GO: " + go.proto.title + " guid: " + go.id + " baseid: " + go.proto.id);
		}
		
		labelGOEdit.visible = visible;
		labelGOParam1.visible = visible;
		labelGOParam2.visible = visible;
		labelGOParam3.visible = visible;
		labelGOParam4.visible = visible;
		
		goEditBackground.visible = visible;
		goEditCancel.visible = visible;
		goEditSave.visible = visible;
		
		goEditParam1.visible = visible;
		goEditParam2.visible = visible;
		goEditParam3.visible = visible;
		goEditParam4.visible = visible;
		goEditTitle.visible = visible;
	}

	private void setVisibleNPCEditor(boolean visible) {
		editorNpcEdit.visible = visible;
		editorListNpc.visible = visible;
	}
	
	public void setVisibleNPCParamsEdit(Creature creature){
		boolean visible;
		if(creature == null){
			visible = false;
			npcEditSave.setScript(new ui_NpcEditorMenuCancel(this));
		}
		else{
			visible = true;
			npcEditSave.setScript(new ui_NpcEditorMenuSave(this, creature));
			
			npcStrength.setText("" + creature.proto.stats.strength);
			npcAgility.setText("" + creature.proto.stats.agility);
			npcStamina.setText("" + creature.proto.stats.stamina);
			npcPerception.setText("" + creature.proto.stats.perception);
			npcIntelligence.setText("" + creature.proto.stats.intelligence);
			npcWillpower.setText("" + creature.proto.stats.willpower);
			npcEditTitle.setText("GUID: " + creature.id);
		}
		
		labelNpcEdit.visible = visible;
		labelNpcStrength.visible = visible;
		labelNpcAgility.visible = visible;
		labelNpcStamina.visible = visible;
		labelNpcPerception.visible = visible;
		labelNpcIntelligence.visible = visible;
		labelNpcWillpower.visible = visible;
		
		npcEditBackground.visible = visible;
		npcEditCancel.visible = visible;
		npcEditSave.visible = visible;
		
		npcStrength.visible = visible;
		npcAgility.visible = visible;
		npcStamina.visible = visible;
		npcPerception.visible = visible;
		npcIntelligence.visible = visible;
		npcWillpower.visible = visible;
		npcEditTitle.visible = visible;
	}
	
	@Override
	public void onload() {
		
	}

	@Override
	public void onclose() {

	}
}