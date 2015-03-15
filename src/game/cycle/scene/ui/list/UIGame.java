package game.cycle.scene.ui.list;

import game.cycle.scene.game.SceneGame;
import game.cycle.scene.game.world.creature.Creature;
import game.cycle.scene.game.world.creature.Player;
import game.cycle.scene.ui.UI;
import game.cycle.scene.ui.Widget.Alignment;
import game.cycle.scene.ui.widgets.Button;
import game.cycle.scene.ui.widgets.Dialog;
import game.cycle.scene.ui.widgets.windows.WindowEditor;
import game.cycle.scene.ui.widgets.windows.WindowEditorGO;
import game.cycle.scene.ui.widgets.windows.WindowEditorGOEdit;
import game.cycle.scene.ui.widgets.windows.WindowEditorLocation;
import game.cycle.scene.ui.widgets.windows.WindowEditorLocationCreate;
import game.cycle.scene.ui.widgets.windows.WindowEditorNpc;
import game.cycle.scene.ui.widgets.windows.WindowEditorNpcEdit;
import game.cycle.scene.ui.widgets.windows.WindowEditorTerrain;
import game.cycle.scene.ui.widgets.windows.WindowTools;
import game.script.ui.app.ui_GameClickMode;

public class UIGame extends UI {

	private SceneGame scene;
	
	// player UI
	public static final String uiPlayerAttack = "player-attack";
	public static final String uiPlayerUse = "player-use";
	public Button playerAttack;
	public Button playerUse;
	
	// New UI
	public static final String uiTools = "tools";
	public static final String uiDialog = "dialog-old";
	public static final String uiEditor = "editor";
	public static final String uiEditorTerrain = "editor-terrain";
	public static final String uiEditorNpc = "editor-npc";
	public static final String uiEditorNpcEdit = "editor-npc-edit";
	public static final String uiEditorGO = "editor-go";
	public static final String uiEditorGOEdit = "editor-go-edit";
	public static final String uiEditorLocation = "editor-location";
	public static final String uiEditorLocationCreate = "editor-location-create";
	
	public WindowTools tools;
	public Dialog dialog;
	
	public WindowEditor editor;
	public WindowEditorTerrain editorTerrain;
	public WindowEditorNpc editorNpc;
	public WindowEditorNpcEdit editorNpcEdit;
	public WindowEditorGO editorGO;
	public WindowEditorGOEdit editorGOEdit;
	public WindowEditorLocation editorLocation;
	public WindowEditorLocationCreate editorLocationCreate;
	
	public UIGame(SceneGame sceneGame) {
		super();
		this.scene = sceneGame;
		
		windows();
		playerActions();
	}
	
	private void windows() {
		tools = new WindowTools(uiTools, this, 1, scene);
		dialog = new Dialog(uiDialog, this, 2);
		
		editor = new WindowEditor(uiEditor, this, 3, scene);
		editorTerrain = new WindowEditorTerrain(uiEditorTerrain, this, 4, scene);
		editorNpc = new WindowEditorNpc(uiEditorNpc, this, 5, scene);
		editorNpcEdit = new WindowEditorNpcEdit(uiEditorNpcEdit, this, 6, scene);
		editorGO = new WindowEditorGO(uiEditorGO, this, 7, scene);
		editorGOEdit = new WindowEditorGOEdit(uiEditorGOEdit, this, 8, scene);
		editorLocation = new WindowEditorLocation(uiEditorLocation, this, 9, scene);
		editorLocationCreate = new WindowEditorLocationCreate(uiEditorLocationCreate, this, 10, scene);
	}
	
	public void showEditor() {
		boolean visible = !editor.isVisible();
		editor.setVisible(visible);
		tools.editor.setActive(visible);
		
		if(!visible){
			editorTerrain.setVisible(false);
			editorNpc.setVisible(false);
			editorNpcEdit.setVisible(false);
			editorGO.setVisible(false);
			editorGOEdit.setVisible(false);
			editorLocation.setVisible(false);
			editorLocationCreate.setVisible(false);
			
			editor.terrain.setActive(false);
			editor.npc.setActive(false);
			editor.go.setActive(false);
			editor.location.setActive(false);
		}
	}

	public void showTerrain(){
		editorTerrain.setVisible(!editorTerrain.isVisible());
		editor.terrain.setActive(editorTerrain.isVisible());
	}
	
	public void showNpc(){
		editorNpc.setVisible(!editorNpc.isVisible());
		editor.npc.setActive(editorNpc.isVisible());
	}

	public void showGO(){
		editorGO.setVisible(!editorGO.isVisible());
		editor.go.setActive(editorGO.isVisible());
	}

	public void showLocation(){
		editorLocation.setVisible(!editorLocation.isVisible());
		editor.location.setActive(editorLocation.isVisible());
	}
	
	public void loadNpcList() {
		editorNpc.loadNpcList();
	}
	
	private void playerActions() {
		playerAttack = new Button(uiPlayerAttack, "Attack");
		playerAttack.setVisible(true);
		playerAttack.setSize(128, 32);
		playerAttack.setPosition(Alignment.DOWNCENTER, 0, 0);
		playerAttack.setScript(new ui_GameClickMode(scene, SceneGame.clickPlayerAttack));
		this.add(playerAttack);
		
		playerUse = new Button(uiPlayerUse, "Use");
		playerUse.setVisible(true);
		playerUse.setSize(128, 32);
		playerUse.setPosition(Alignment.DOWNCENTER, 0, 34);
		playerUse.setScript(new ui_GameClickMode(scene, SceneGame.clickPlayerUse));
		this.add(playerUse);
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
				editorTerrain.setVisible(false);
				break;

			case SceneGame.clickEditorNpc:
				editorNpc.setVisible(false);
				editorNpcEdit.setCreature(null);
				break;
				
			case SceneGame.clickEditorNpcEdit:
				editorNpc.editorNpcEdit.setActive(false);
				editorNpc.setVisible(false);
				editorNpcEdit.setCreature(null);
				break;
			
			case SceneGame.clickEditorGO:
				editorGO.setVisible(false);
				editorGOEdit.setGO(null);
				break;
				
			case SceneGame.clickEditorGOAdd:
				editorGO.editorGOAdd.setActive(false);
				editorGO.setVisible(true);
				break;
			
			case SceneGame.clickEditorGOEdit:
				editorGO.editorGOEdit.setActive(false);
				editorGO.setVisible(true);
				break;
				
			case SceneGame.clickEditorLocation:
				editorLocation.setVisible(false);
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
				editorTerrain.setVisible(true);
				break;
				
			case SceneGame.clickEditorNpc:
				editorNpc.setVisible(true);
				break;
				
			case SceneGame.clickEditorNpcEdit:
				editorNpc.editorNpcEdit.setActive(true);
				editorNpc.setVisible(true);
				break;
				
			case SceneGame.clickEditorGO:
				editorGO.setVisible(true);
				break;
				
			case SceneGame.clickEditorGOAdd:
				editorGO.editorGOAdd.setActive(true);
				editorGO.setVisible(true);
				break;
			
			case SceneGame.clickEditorGOEdit:
				editorGO.editorGOEdit.setActive(true);
				editorGO.setVisible(true);
				break;
				
			case SceneGame.clickEditorLocation:
				editorLocation.setVisible(true);
				break;
				
			default:
				break;
		}
	}

	public void npcTalk(UIGame ui, Player player, Creature npc) {
		dialog.setCreature(npc);
		dialog.setVisible(true);
	}

	public boolean isDialog() {
		return dialog.isVisible();
	}

	public int getSelectedListTerrain() {
		return editorTerrain.getSelectedListTerrain();
	}

	public int getSelectedListNpc() {
		return editorNpc.getSelectedListNpc();
	}
	
	public int getSelectedListGO(){
		return editorGO.getSelectedListGO();
	}

	public int getSelectedListLocation() {
		return editorLocation.getSelectedListLocation();
	}
	
	public void setVisibleCreteLocation(boolean visible){
		editorLocationCreate.setVisible(visible);
		
		if(!visible){
			editorLocationCreate.resetFileds();
		}
	}
	
	@Override
	public void onload() {
		
	}

	@Override
	public void onclose() {

	}
}