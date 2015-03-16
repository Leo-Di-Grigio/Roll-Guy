package game.cycle.scene.ui.widgets.windows;

import game.cycle.scene.game.SceneGame;
import game.cycle.scene.game.world.go.GO;
import game.cycle.scene.ui.list.UIGame;
import game.cycle.scene.ui.textfilters.TextFilterNumbers;
import game.cycle.scene.ui.widgets.Button;
import game.cycle.scene.ui.widgets.Image;
import game.cycle.scene.ui.widgets.Label;
import game.cycle.scene.ui.widgets.TextField;
import game.cycle.scene.ui.widgets.Window;
import game.resources.Resources;
import game.resources.Tex;
import game.script.ui.editor.ui_GOEditorMenuCancel;
import game.script.ui.editor.ui_GOEditorMenuSave;

public class WindowEditorGOEdit extends Window {
	
	private UIGame uigame;
	
	public static final String uiBackground = "goedit-back";
	public static final String uiParam1 = "goedit-param1";
	public static final String uiParam2 = "goedit-param2";
	public static final String uiParam3 = "goedit-param3";
	public static final String uiParam4 = "goedit-param4";
	public static final String uiCancel = "goedit-cancel";
	public static final String uiSave = "goedit-save";
	
	public static final String uiLabelParam1 = "label-goedit-param1";
	public static final String uiLabelParam2 = "label-goedit-param2";
	public static final String uiLabelParam3 = "label-goedit-param3";
	public static final String uiLabelParam4 = "label-goedit-param4";
	public static final String uiLabelInfo = "label-goedit-info";
	
	public Label labelParam1;
	public Label labelParam2;
	public Label labelParam3;
	public Label labelParam4;
	public Label labelInfo;
	
	public Image  background;
	public Button cancel;
	public Button save;
	
	public TextField param1;
	public TextField param2;
	public TextField param3;
	public TextField param4;
	
	public WindowEditorGOEdit(String title, UIGame ui, int layer, SceneGame scene) {
		super(title, ui, Alignment.CENTER, 280, 24, 0, 0, layer);
		this.setTexNormal(Resources.getTex(Tex.uiListLine));
		this.uigame = ui;
		this.setText("Game Object Edit");
		
		loadWidgets(scene);
	}
	
	public void setGO(GO go){
		if(go == null){
			setVisible(false);
			save.setScript(new ui_GOEditorMenuCancel(uigame));
		}
		else{
			setVisible(true);
			save.setScript(new ui_GOEditorMenuSave(this, go));
			param1.setText("" + go.param1);
			param2.setText("" + go.param2);
			param3.setText("" + go.param3);
			param4.setText("" + go.param4);
			labelInfo.setText("GO: " + go.proto.title + " guid: " + go.id + " baseid: " + go.proto.id);
		}
	}
	
	private void loadWidgets(SceneGame scene) {
		this.closeButton(true);
		this.lockButton(true);
		
		background = new Image(uiBackground);
		background.setSize(280, 150);
		background.setPosition(Alignment.UPCENTER, 0, -24);
		this.add(background);
		
		labelParam1 = new Label(uiLabelParam1, "param 1");
		labelParam1.setSize(60, 32);
		labelParam1.setPosition(Alignment.CENTER, -110, -24);
		labelParam1.setLayer(1);
		this.add(labelParam1);
		
		labelParam2 = new Label(uiLabelParam2, "param 2");
		labelParam2.setSize(60, 32);
		labelParam2.setPosition(Alignment.CENTER, -110, -44);
		labelParam2.setLayer(1);
		this.add(labelParam2);
		
		labelParam3 = new Label(uiLabelParam3, "param 3");
		labelParam3.setSize(60, 32);
		labelParam3.setPosition(Alignment.CENTER, -110, -64);
		labelParam3.setLayer(1);
		this.add(labelParam3);
		
		labelParam4 = new Label(uiLabelParam4, "param 4");
		labelParam4.setSize(60, 32);
		labelParam4.setPosition(Alignment.CENTER, -110, -84);
		labelParam4.setLayer(1);
		this.add(labelParam4);
		//
		param1 = new TextField(uiParam1);
		param1.maxTextLength = 10;
		param1.setSize(210, 16);
		param1.setPosition(Alignment.CENTER, 30, -24);
		param1.setTextFilter(new TextFilterNumbers(false));
		param1.setLayer(1);
		this.add(param1);
		
		param2 = new TextField(uiParam2);
		param2.maxTextLength = 10;
		param2.setSize(210, 16);
		param2.setPosition(Alignment.CENTER, 30, -44);
		param2.setTextFilter(new TextFilterNumbers(false));
		param2.setLayer(1);
		this.add(param2);
		
		param3 = new TextField(uiParam3);
		param3.maxTextLength = 10;
		param3.setSize(210, 16);
		param3.setPosition(Alignment.CENTER, 30, -64);
		param3.setTextFilter(new TextFilterNumbers(false));
		param3.setLayer(1);
		this.add(param3);
		
		param4 = new TextField(uiParam4);
		param4.maxTextLength = 10;
		param4.setSize(210, 16);
		param4.setPosition(Alignment.CENTER, 30, -84);
		param4.setTextFilter(new TextFilterNumbers(false));
		param4.setLayer(1);
		this.add(param4);
		
		labelInfo = new Label(uiLabelInfo, "test");
		labelInfo.setSize(250, 32);
		labelInfo.setPosition(Alignment.CENTER, 0, -104);
		labelInfo.setLayer(1);
		this.add(labelInfo);
		
		cancel = new Button(uiCancel, "Cancel");
		cancel.setTexNormal(Tex.uiBackgroundLightSelected);
		cancel.setSize(128, 32);
		cancel.setPosition(Alignment.CENTER, -70, -134);
		cancel.setScript(new ui_GOEditorMenuCancel(uigame));
		cancel.setLayer(1);
		this.add(cancel);
		
		save = new Button(uiSave, "Save");
		save.setTexNormal(Tex.uiBackgroundLightSelected);
		save.setSize(128, 32);
		save.setPosition(Alignment.CENTER, 70, -134);
		save.setLayer(1);
		this.add(save);
	}
}
