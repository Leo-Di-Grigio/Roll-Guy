package game.cycle.scene.ui.list;

import game.cycle.scene.game.SceneGame;
import game.cycle.scene.game.world.creature.Creature;
import game.cycle.scene.game.world.creature.Player;
import game.cycle.scene.ui.UI;
import game.cycle.scene.ui.Widget.Alignment;
import game.cycle.scene.ui.widgets.Button;
import game.cycle.scene.ui.widgets.Dialog;
import game.script.ui.ui_DialogClose;
import game.script.ui.app.ui_ExitGame;
import game.script.ui.app.ui_GameClickMode;

public class UIGame extends UI {

	private SceneGame scene;
	
	// common UI
	public static final String uiMainMenu = "main-menu";
	
	// player UI
	public static final String uiPlayerAttack = "player-attack";
	public Button playerAttack;
	
	// editor Ui
	public static final String uiEditor = "editor";
	public static final String uiEditorNpc = "npc";
	public Button editor;
	public Button editorNpc;
	
	// NPC
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
		npc();
	}

	private void commonMenu() {
		Button button = new Button(uiMainMenu, "Main menu");
		button.visible = true;
		button.setSize(128, 32);
		button.setPosition(Alignment.UPRIGTH, 0, 0);
		button.setScript(new ui_ExitGame());
		this.add(button);
	}

	private void editor() {
		editor = new Button(uiEditor, "Editor");
		editor.visible = true;
		editor.setSize(128, 32);
		editor.setPosition(Alignment.UPRIGTH, 0, -34);
		editor.setScript(new ui_GameClickMode(scene, SceneGame.clickEditor));
		this.add(editor);
		
		editorNpc = new Button(uiEditorNpc, "NPC");
		editorNpc.visible = true;
		editorNpc.setSize(128, 32);
		editorNpc.setPosition(Alignment.UPRIGTH, 0, -68);
		editorNpc.setScript(new ui_GameClickMode(scene, SceneGame.clickEditorNpc));
		this.add(editorNpc); 
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
		
		// 	setPosition(Alignment.CENTERLEFT, 0, -300);
		// setSize(500, 600);
		
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
				
			case SceneGame.clickEditor:
				editor.setActive(false);
				break;

			case SceneGame.clickEditorNpc:
				editorNpc.setActive(false);
				break;
				
			default:
				break;
		}
		
		// on
		switch (valueNew) {
			case SceneGame.clickPlayerAttack:
				playerAttack.setActive(true);
				break;
			
			case SceneGame.clickEditor:
				editor.setActive(true);
				break;
				
			case SceneGame.clickEditorNpc:
				editorNpc.setActive(true);
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
	
	@Override
	public void onload() {
		
	}

	@Override
	public void onclose() {

	}
}
