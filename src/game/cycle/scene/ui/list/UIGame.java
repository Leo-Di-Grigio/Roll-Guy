package game.cycle.scene.ui.list;

import game.cycle.scene.game.SceneGame;
import game.cycle.scene.game.world.creature.Creature;
import game.cycle.scene.game.world.creature.Player;
import game.cycle.scene.ui.UI;
import game.cycle.scene.ui.widgets.windows.WindowDialog;
import game.cycle.scene.ui.widgets.windows.WindowInventory;
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
import game.cycle.scene.ui.widgets.windows.WindowPlayerStatus;
import game.cycle.scene.ui.widgets.windows.WindowTools;
import game.tools.Const;

public class UIGame extends UI {

	private SceneGame scene;
	
	// Player UI
	public static final String uiPlayerMenu = "player-menu";
	public static final String uiPlayerStatus = "player-status";
	public static final String uiPlayerActionbar = "player-actionbar";
	
	public WindowPlayerMenu playermenu;
	public WindowPlayerStatus playerstatus;
	public WindowPlayerActionBar actionBar;
	
	// Editor
	public static final String uiTools = "tools";
	public static final String uiDialog = "dialog";
	public static final String uiInventory = "inventory";
	public static final String uiEditor = "editor";
	public static final String uiEditorTerrain = "editor-terrain";
	public static final String uiEditorNpc = "editor-npc";
	public static final String uiEditorNpcEdit = "editor-npc-edit";
	public static final String uiEditorGO = "editor-go";
	public static final String uiEditorGOEdit = "editor-go-edit";
	public static final String uiEditorLocation = "editor-location";
	public static final String uiEditorLocationCreate = "editor-location-create";
	
	public WindowTools tools;
	public WindowDialog dialog;
	public WindowInventory invenotry;
	
	public WindowEditor editor;
	public WindowEditorTerrain terrain;
	public WindowEditorNpc npc;
	public WindowEditorNpcEdit npcEdit;
	public WindowEditorGO go;
	public WindowEditorGOEdit goEdit;
	public WindowEditorLocation location;
	public WindowEditorLocationCreate locationCreate;
	
	public UIGame(SceneGame sceneGame) {
		super();
		this.scene = sceneGame;
		
		editor();
		player();
	}
	
	private void player() {
		playerstatus = new WindowPlayerStatus(uiPlayerStatus, this, 2, scene);
		playermenu = new WindowPlayerMenu(uiPlayerMenu, this, 2, scene);
		actionBar = new WindowPlayerActionBar(uiPlayerActionbar, this, 2, scene);
		actionBar.endTurn.setVisible(false);
	}
	
	private void editor() {
		tools = new WindowTools(uiTools, this, 1, scene);
		dialog = new WindowDialog(uiDialog, this, 2);
		invenotry = new WindowInventory(uiInventory, this, 3);
		
		editor = new WindowEditor(uiEditor, this, 4, scene);
		terrain = new WindowEditorTerrain(uiEditorTerrain, this, 5, scene);
		npc = new WindowEditorNpc(uiEditorNpc, this, 6, scene);
		npcEdit = new WindowEditorNpcEdit(uiEditorNpcEdit, this, 7, scene);
		go = new WindowEditorGO(uiEditorGO, this, 8, scene);
		goEdit = new WindowEditorGOEdit(uiEditorGOEdit, this, 9, scene);
		location = new WindowEditorLocation(uiEditorLocation, this, 10, scene);
		locationCreate = new WindowEditorLocationCreate(uiEditorLocationCreate, this, 11, scene);
	}
	
	public int mode = Const.invalidId;
	public static final int modeNpcEdit = 0;
	public static final int modeNpcAdd = 1;
	public static final int modeGOEdit = 2;
	public static final int modeGOAdd = 3;
	
	public static final int modeTerrainBrush1 = 4;
	public static final int modeTerrainBrush2 = 5;
	public static final int modeTerrainBrush3 = 6;
	
	public static final int modeSkillSpell = 7;
	public static final int modeSkillRange = 8;
	public static final int modeSkillMelee = 9;
	public static final int modeSkillNull = 10;
	
	public int getMode(){
		return mode;
	}
	
	public void setMode(int modeKey) {
		resetModes();
		
		if(mode == modeKey){
			mode = Const.invalidId;
		}
		else{
			mode = modeKey;
			switch (mode) {
				case modeNpcAdd:
					npc.add.setActive(true);
					break;
				
				case modeNpcEdit:
					npc.edit.setActive(true);
					break;
					
				case modeGOAdd:
					go.add.setActive(true);
					break;
					
				case modeGOEdit:
					go.edit.setActive(true);
					break;
					
				case modeTerrainBrush1:
					terrain.brush1.setActive(true);
					break;
					
				case modeTerrainBrush2:
					terrain.brush2.setActive(true);
					break;
					
				case modeTerrainBrush3:
					terrain.brush3.setActive(true);
					break;
			}
		}
	}
	
	private void resetModes(){
		scene.getWorld().resetPlayerSkill();
		
		npc.add.setActive(false);
		npc.edit.setActive(false);
		
		go.add.setActive(false);
		go.edit.setActive(false);
		
		terrain.brush1.setActive(false);
		terrain.brush2.setActive(false);
		terrain.brush3.setActive(false);
	}
	
	public void showEditor() {
		boolean visible = !editor.isVisible();
		editor.setVisible(visible);
		tools.editor.setActive(visible);
		this.mode = Const.invalidId;
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
	
	public void loadNpcList() {
		npc.loadNpcList();
	}

	public void npcTalk(UIGame ui, Player player, Creature npc) {
		dialog.setCreature(npc);
		dialog.setVisible(true);
	}

	public boolean isDialog() {
		return dialog.isVisible();
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

	public void turnBased(boolean turnbased, boolean playerTurn) {
		if(turnbased && playerTurn){
			actionBar.endTurn.setVisible(true);
		}
		else{
			actionBar.endTurn.setVisible(false);
		}
	}

	public void setPlayer(Player player) {
		this.playerstatus.setCreature(player);
		this.actionBar.setCreature(player);
		this.playermenu.setCreature(player);
	}
	
	@Override
	public void onload() {
		
	}

	@Override
	public void onclose() {

	}
}