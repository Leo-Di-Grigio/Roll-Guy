package game.cycle.scene.ui.list;

import resources.Cursors;
import ui.UI;
import game.cycle.scene.game.SceneGame;
import game.cycle.scene.game.state.location.creature.Creature;
import game.cycle.scene.game.state.location.creature.NPC;
import game.cycle.scene.game.state.location.creature.Player;
import game.cycle.scene.game.state.location.creature.items.Inventory;
import game.cycle.scene.game.state.location.creature.items.Item;
import game.cycle.scene.ui.widgets.windows.WindowCorpse;
import game.cycle.scene.ui.widgets.windows.WindowDialog;
import game.cycle.scene.ui.widgets.windows.WindowEditorItems;
import game.cycle.scene.ui.widgets.windows.WindowInventory;
import game.cycle.scene.ui.widgets.windows.WindowPlayer;
import game.cycle.scene.ui.widgets.windows.WindowPlayerActionBar;
import game.cycle.scene.ui.widgets.windows.WindowEditor;
import game.cycle.scene.ui.widgets.windows.WindowEditorGO;
import game.cycle.scene.ui.widgets.windows.WindowEditorGOEdit;
import game.cycle.scene.ui.widgets.windows.WindowEditorLocation;
import game.cycle.scene.ui.widgets.windows.WindowEditorLocationCreate;
import game.cycle.scene.ui.widgets.windows.WindowEditorNpc;
import game.cycle.scene.ui.widgets.windows.WindowEditorNpcEdit;
import game.cycle.scene.ui.widgets.windows.WindowEditorTerrain;
import game.cycle.scene.ui.widgets.windows.WindowPlayerMenu;
import game.cycle.scene.ui.widgets.windows.WindowPlayerSpellBook;
import game.cycle.scene.ui.widgets.windows.WindowPlayerStatus;
import game.cycle.scene.ui.widgets.windows.WindowTools;
import game.tools.Const;

public class UIGame extends UI {

	private SceneGame scene;
	
	// Player UI
	public static final String PLAYER = "player";
	public static final String PLAYER_MENU = "player-menu";
	public static final String PLAYER_STATUS = "player-status";
	public static final String PLAYER_ACTIONBAR = "player-actionbar";
	public static final String PLAYER_INVENTORY = "player-inventory";
	public static final String PLAYER_SPELLBOOK = "player-spellbook";
	
	// Editor
	public static final String TOOLS = "tools";
	public static final String EDITOR = "editor";
	public static final String EDITOR_TERRAIN = "editor-terrain";
	public static final String EDITOR_NPC = "editor-npc";
	public static final String EDITOR_NPC_EDIT = "editor-npc-edit";
	public static final String EDITOR_GO = "editor-go";
	public static final String EDITOR_GO_EDIT = "editor-go-edit";
	public static final String EDITOR_LOCATION = "editor-location";
	public static final String EDITRO_LOCATION_CREATE = "editor-location-create";
	public static final String EDITOR_ITEM = "editor-items";
	
	// Player menu
	public WindowPlayer player;
	public WindowPlayerMenu playermenu;
	public WindowPlayerStatus playerstatus;
	public WindowPlayerActionBar actionBar;
	public WindowInventory invenotry;
	public WindowPlayerSpellBook spellbook;
	
	// Interact
	public static final String uiDialog = "dialog";
	public static final String uiContainer = "container";
	public static final String uiCorpse = "corpse";
	public WindowDialog dialog;
	public WindowInventory container;
	public WindowCorpse corpse;
	
	public WindowTools tools;	
	public WindowEditor editor;
	public WindowEditorTerrain terrain;
	public WindowEditorNpc npc;
	public WindowEditorNpcEdit npcEdit;
	public WindowEditorGO go;
	public WindowEditorGOEdit goEdit;
	public WindowEditorLocation location;
	public WindowEditorLocationCreate locationCreate;
	public WindowEditorItems items;
	
	public UIGame(SceneGame sceneGame) {
		super();
		this.scene = sceneGame;
		
		player();
		interact();
		editor();
	}
	
	private void interact() {
		dialog = new WindowDialog(uiDialog, this, 2);
		container = new WindowInventory(uiContainer, this, 3, Const.INVENTORY_SIZE_X, Const.INVENTORY_SIZE_Y);
		corpse = new WindowCorpse(uiCorpse, this, 3, Const.INVENTORY_SIZE_X, Const.INVENTORY_SIZE_Y);
	}

	private void player() {
		player = new WindowPlayer(PLAYER, this, 4);
		playerstatus = new WindowPlayerStatus(PLAYER_STATUS, this, 5, scene);
		playermenu = new WindowPlayerMenu(PLAYER_MENU, this, 6, scene);
		actionBar = new WindowPlayerActionBar(PLAYER_ACTIONBAR, this, 7);
		invenotry = new WindowInventory(PLAYER_INVENTORY, this, 8, Const.INVENTORY_SIZE_X, Const.INVENTORY_SIZE_Y);
		invenotry.setText("Inventory");
		
		spellbook = new WindowPlayerSpellBook(PLAYER_SPELLBOOK, this, 9);
	}
	
	private void editor() {
		tools = new WindowTools(TOOLS, this, 10, scene);		
		editor = new WindowEditor(EDITOR, this, 11, scene);
		terrain = new WindowEditorTerrain(EDITOR_TERRAIN, this, 12, scene);
		npc = new WindowEditorNpc(EDITOR_NPC, this, 13, scene);
		npcEdit = new WindowEditorNpcEdit(EDITOR_NPC_EDIT, this, 14, scene);
		go = new WindowEditorGO(EDITOR_GO, this, 15, scene);
		goEdit = new WindowEditorGOEdit(EDITOR_GO_EDIT, this, 16, scene);
		location = new WindowEditorLocation(EDITOR_LOCATION, this, 17, scene);
		locationCreate = new WindowEditorLocationCreate(EDITRO_LOCATION_CREATE, this, 18, scene);
		items = new WindowEditorItems(EDITOR_ITEM, this, 19, scene);
	}
	
	//
	public int mode = Const.INVALID_ID;
	
	// modes
	public static final int MODE_NPC_EDIT = 0;
	public static final int MODE_NPC_ADD = 1;
	public static final int MODE_GO_EDIT = 2;
	public static final int MODE_GO_ADD = 3;
	public static final int MODE_TERRAIN_BRUSH_1 = 4;
	public static final int MODE_TERRAIN_BRUSH_2 = 5;
	public static final int MODE_TERRAIN_BRUSH_3 = 6;
	public static final int MODE_TERRAIN_FILL = 7;
	public static final int MODE_SKILL_SPELL = 8;
	public static final int MODE_SKILL_RANGE = 9;
	public static final int MODE_SKILL_MELEE = 10;
	public static final int MODE_SKILL_NULL = 11;
	public static final int MODE_SELECT_ITEM = 12;
	
	public int getMode(){
		return mode;
	}

	public boolean getEditMode() {
		return tools.editor.getActive();
	}

	public boolean getSkillMode() {
		switch(mode){
			case MODE_SKILL_NULL:
			case MODE_SKILL_MELEE:
			case MODE_SKILL_RANGE:
			case MODE_SKILL_SPELL:
				return true;
				
			default:
				return false;
		}
	}
	
	public void setMode(int modeKey) {
		resetModes();
		
		if(modeKey == Const.INVALID_ID){
			mode = Const.INVALID_ID;
			Cursors.setCursor(Cursors.cursorDefault);
		}
		else{
			if(mode == modeKey){
				mode = Const.INVALID_ID;
			}
			else{
				mode = modeKey;
				switch (mode) {
					case MODE_NPC_ADD:
						npc.add.setActive(true);
						break;
					
					case MODE_NPC_EDIT:
						npc.edit.setActive(true);
						break;
						
					case MODE_GO_ADD:
						go.add.setActive(true);
						break;
						
					case MODE_GO_EDIT:
						go.edit.setActive(true);
						break;
						
					case MODE_TERRAIN_BRUSH_1:
						terrain.brush1.setActive(true);
						break;
						
					case MODE_TERRAIN_BRUSH_2:
						terrain.brush2.setActive(true);
						break;
						
					case MODE_TERRAIN_BRUSH_3:
						terrain.brush3.setActive(true);
						break;
						
					case MODE_TERRAIN_FILL:
						terrain.fill.setActive(true);
						break;
				}
			}
		}
	}
	
	private void resetModes(){
		npc.add.setActive(false);
		npc.edit.setActive(false);
		
		go.add.setActive(false);
		go.edit.setActive(false);
		
		terrain.brush1.setActive(false);
		terrain.brush2.setActive(false);
		terrain.brush3.setActive(false);
		terrain.fill.setActive(false);
	}
	
	public void showEditor() {
		boolean visible = !editor.isVisible();
		editor.setVisible(visible);
		tools.editor.setActive(visible);
		this.mode = Const.INVALID_ID;
		this.resetModes();
		
		if(!visible){
			terrain.setVisible(false);
			npc.setVisible(false);
			npcEdit.setVisible(false);
			go.setVisible(false);
			goEdit.setVisible(false);
			location.setVisible(false);
			locationCreate.setVisible(false);
			
			editor.terrain.setActive(false);
			editor.npc.setActive(false);
			editor.go.setActive(false);
			editor.location.setActive(false);
		}
	}

	public void showTerrain(){
		terrain.setVisible(!terrain.isVisible());
		editor.terrain.setActive(terrain.isVisible());
	}
	
	public void showNpc(){
		npc.setVisible(!npc.isVisible());
		editor.npc.setActive(npc.isVisible());
	}

	public void showGO(){
		go.setVisible(!go.isVisible());
		editor.go.setActive(go.isVisible());
	}

	public void showLocation(){
		location.setVisible(!location.isVisible());
		editor.location.setActive(location.isVisible());
	}

	public void showItems() {
		items.setVisible(!items.isVisible());
		editor.items.setActive(items.isVisible());
	}
	
	public void loadNpcList() {
		npc.loadNpcList();
	}

	public void npcTalk(NPC npc) {
		dialog.setCreature(scene.getState().getGlobals(), npc);
		dialog.setVisible(true);
	}

	public boolean isDialog() {
		return dialog.isVisible();
	}
	
	public void openContainer(Inventory inventory) {
		container.showContainer(inventory);
		corpse.showCreature(null);
	}

	public void openCorpse(Creature creature) {
		container.showContainer(null);
		corpse.showCreature(creature);
	}
	
	public int getSelectedListTerrain() {
		return terrain.getSelectedListTerrain();
	}

	public int getSelectedListNpc() {
		return npc.getSelectedListNpc();
	}
	
	public int getSelectedListGO(){
		return go.getSelectedListGO();
	}

	public int getSelectedListLocation() {
		return location.getSelectedListLocation();
	}
	
	public void setVisibleCreteLocation(boolean visible){
		locationCreate.setVisible(visible);
		
		if(!visible){
			locationCreate.resetFileds();
		}
	}

	public void setPlayer(Player player) {
		this.playerstatus.setCreature(player);
		this.actionBar.setCreature(player);
		this.playermenu.setCreature(player);
	}

	public void selectItem(Item item) {
		if(item == null){
			this.setMode(Const.INVALID_ID);
			Cursors.selectItem(null);
		}
		else{
			this.setMode(MODE_SELECT_ITEM);
			Cursors.selectItem(item);
		}
	}

	public void showInventory() {
		invenotry.setVisible(!invenotry.isVisible());
	}
	
	// updates
	public void updateDialogTopics() {
		dialog.updateTopics(scene.getState().getGlobals(), dialog.getNPC());
	}
}