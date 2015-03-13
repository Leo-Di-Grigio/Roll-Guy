package game.cycle.scene.ui.list;

import java.util.ArrayList;
import java.util.HashMap;

import game.cycle.scene.game.SceneGame;
import game.cycle.scene.game.world.creature.Creature;
import game.cycle.scene.game.world.creature.Player;
import game.cycle.scene.game.world.database.Database;
import game.cycle.scene.game.world.go.GOProto;
import game.cycle.scene.game.world.map.LocationProto;
import game.cycle.scene.game.world.map.TerrainProto;
import game.cycle.scene.ui.UI;
import game.cycle.scene.ui.Widget.Alignment;
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
import game.script.ui.app.ui_GameClickMode;
import game.script.ui.app.ui_LocationAddMenu;
import game.script.ui.app.ui_LocationLoad;
import game.script.ui.app.ui_LocationSave;
import game.script.ui.app.ui_LoctionDel;
import game.tools.Const;

public class UIGame extends UI {

	private SceneGame scene;
	
	// common UI
	public static final String uiMainMenu = "main-menu";
	public static final String uiFreeCamera = "free-camera";
	
	// player UI
	public static final String uiPlayerAttack = "player-attack";
	public Button playerAttack;
	
	// Editor Ui
	public static final String uiEditorTerrain = "editor-terrain";
	public static final String uiEditorTerrainList = "editor-terrain-list";
	public static final String uiEditorNpc = "editor-npc";
	public static final String uiEditorGO = "editor-go";
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
	public Button editorGO;
	public Button editorLocation;
	public Button editorLocationLoad;
	public Button editorLocationAdd;
	public Button editorLocationDelete;
	public Button editorLocationEdit;
	public Button editorSave;
	public List   editorListTerrain;
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
		npc();
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
		
		editorGO = new Button(uiEditorGO, "Game Object");
		editorGO.visible = true;
		editorGO.setSize(128, 32);
		editorGO.setPosition(Alignment.UPRIGTH, 0, -170);
		editorGO.setScript(new ui_GameClickMode(scene, SceneGame.clickEditorGO));
		this.add(editorGO);
		
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
		editorLocationAdd.setScript(new ui_LocationAddMenu(this)); //new ui_LocationAdd(this, scene)
		this.add(editorLocationAdd);
	
		editorLocationDelete = new Button(uiEditorLocationDelete, "Delete");
		editorLocationDelete.setSize(64, 32);
		editorLocationDelete.setPosition(Alignment.UPRIGTH, -392, -272);
		editorLocationDelete.setScript(new ui_LoctionDel(this));
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
		createLocationSizeX.maxTextLength = 5;
		createLocationSizeX.setSize(60, 16);
		createLocationSizeX.setPosition(Alignment.CENTER, -55, 5);
		createLocationSizeX.setLayer(2);
		this.add(createLocationSizeX);
		
		createLocationSizeY = new TextField(uiCreateSizeY);
		createLocationSizeY.maxTextLength = 5;
		createLocationSizeY.setSize(60, 16);
		createLocationSizeY.setPosition(Alignment.CENTER, 60, 5);
		createLocationSizeY.setLayer(2);
		this.add(createLocationSizeY);
		
		createLocationStartTerrain = new TextField(uiCreateStartTerrain);
		createLocationStartTerrain.maxTextLength = 3;
		createLocationStartTerrain.setSize(60, 16);
		createLocationStartTerrain.setPosition(Alignment.CENTER, -55, -15);
		createLocationStartTerrain.setLayer(2);
		this.add(createLocationStartTerrain);
				
		createLocationCancel = new Button(uiCreateCancel, "Cancel");
		createLocationCancel.setSize(128, 32);
		createLocationCancel.setPosition(Alignment.CENTER, -70, -80);
		createLocationCancel.setLayer(2);
		createLocationCancel.setScript(new ui_LocationAddMenu(this));
		this.add(createLocationCancel);
		
		createLocationConfirm = new Button(uiCreateConfirm, "Confirm");
		createLocationConfirm.setSize(128, 32);
		createLocationConfirm.setPosition(Alignment.CENTER, 70, -80);
		createLocationConfirm.setLayer(2);
		this.add(createLocationConfirm);
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
	}
	
	private void npc() {
		dialog = new Dialog(uiDialog);
		this.add(dialog);
		
		dialogClose = new Button(uiDialogClose, "x");
		dialogClose.setSize(24, 24);
		dialogClose.setPosition(Alignment.CENTERLEFT, 476, 276);
		dialogClose.setScript(new ui_DialogClose(dialog, dialogClose));
		dialogClose.setLayer(1);
		this.add(dialogClose);
	}
	
	public void setClickMode(int valuePrevious, int valueNew){
		// off
		switch (valuePrevious) {
			case SceneGame.clickPlayerAttack:
				playerAttack.setActive(false);
				break;
				
			case SceneGame.clickTerrain:
				editorTerrain.setActive(false);
				editorListTerrain.visible = false;
				break;

			case SceneGame.clickEditorNpc:
				editorNpc.setActive(false);
				break;
			
			case SceneGame.clickEditorGO:
				editorGO.setActive(false);
				editorListGO.visible = false;
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
			
			case SceneGame.clickTerrain:
				editorTerrain.setActive(true);
				editorListTerrain.visible = true;
				break;
				
			case SceneGame.clickEditorNpc:
				editorNpc.setActive(true);
				break;
				
			case SceneGame.clickEditorGO:
				editorGO.setActive(true);
				editorListGO.visible = true;
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
	
	@Override
	public void onload() {
		
	}

	@Override
	public void onclose() {

	}
}

