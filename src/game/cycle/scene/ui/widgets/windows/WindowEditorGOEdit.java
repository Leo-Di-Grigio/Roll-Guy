package game.cycle.scene.ui.widgets.windows;

import resources.Resources;
import resources.tex.Tex;
import ui.Alignment;
import ui.Window;
import ui.widgets.used.Button;
import ui.widgets.used.Image;
import ui.widgets.used.Label;
import ui.widgets.used.TextField;

import com.badlogic.gdx.graphics.g2d.BitmapFont.HAlignment;

import game.cycle.scene.game.SceneGame;
import game.cycle.scene.game.state.location.go.GO;
import game.cycle.scene.ui.list.UIGame;
import game.cycle.scene.ui.textfilters.TextFilterNumbers;
import game.script.ui.editor.ui_GOEditorMenuCancel;
import game.script.ui.editor.ui_GOEditorMenuSave;

public class WindowEditorGOEdit extends Window {
	
	private UIGame uigame;
	
	public static final String uiBackground = "goedit-back";
	public static final String uiParam1 = "goedit-param1-trigger";
	public static final String uiParam2 = "goedit-param2-trigger";
	public static final String uiParam3 = "goedit-param3-trigger";
	public static final String uiParam4 = "goedit-param4-trigger";
	
	public static final String uiTrigger = "goedit-trigger-";
	public static final String uiTriggerParam = "goedit-trigger-param-";
	public static final String uiScript = "goedit-script-";

	public static final String uiLabelTrigger = "label-goedit-trigger-";
	public static final String uiLabelTriggerParam = "label-goedit-script-param-";
	public static final String uiLabelScript = "label-goedit-script-";
	
	public static final String uiCancel = "goedit-cancel";
	public static final String uiSave = "goedit-save";
	
	public static final String uiLabelParam1 = "label-goedit-param1-trigger";
	public static final String uiLabelParam2 = "label-goedit-param2-trigger";
	public static final String uiLabelParam3 = "label-goedit-param3-trigger";
	public static final String uiLabelParam4 = "label-goedit-param4-trigger";
	
	public static final String uiLabelInfo = "label-goedit-info";
	
	public Label labelParam1;
	public Label labelParam2;
	public Label labelParam3;
	public Label labelParam4;
	public Label labelInfo;
	
	public Label labelScript;
	
	public Image  background;
	public Button cancel;
	public Button save;
	
	public TextField param1;
	public TextField param2;
	public TextField param3;
	public TextField param4;
	public TextField script;
	
	public WindowEditorGOEdit(String title, UIGame ui, int layer, SceneGame scene) {
		super(title, ui, Alignment.CENTER, 300, 24, 0, 0, layer);
		this.setTexNormal(Resources.getTex(Tex.UI_LIST_LINE));
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
			param1.setText("" + go.param1());
			param2.setText("" + go.param2());
			param3.setText("" + go.param3());
			param4.setText("" + go.param4());
			script.setText(""+ go.script);
			
			labelInfo.setText("GO: " + go.proto.title() + " guid: " + go.getGUID() + " baseid: " + go.proto.id());
		}
	}
	
	private void loadWidgets(SceneGame scene) {
		this.closeButton(true);
		this.lockButton(true);
		
		background = new Image(uiBackground);
		background.setSize(300, 220);
		background.setPosition(Alignment.UPCENTER, 0, -24);
		this.add(background);
		
		cancel = new Button(uiCancel, "Cancel");
		cancel.setTexNormal(Tex.UI_BACKGROUND_SELECTED_LIGHT);
		cancel.setSize(128, 32);
		cancel.setPosition(Alignment.CENTER, -70, -204);
		cancel.setScript(new ui_GOEditorMenuCancel(uigame));
		cancel.setLayer(1);
		this.add(cancel);
		
		save = new Button(uiSave, "Save");
		save.setTexNormal(Tex.UI_BACKGROUND_SELECTED_LIGHT);
		save.setSize(128, 32);
		save.setPosition(Alignment.CENTER, 70, -204);
		save.setLayer(1);
		this.add(save);
		
		labelInfo = new Label(uiLabelInfo, "test");
		labelInfo.setSize(250, 32);
		labelInfo.setPosition(Alignment.CENTER, 0, -170);
		labelInfo.setLayer(1);
		labelInfo.setTextAlignment(HAlignment.LEFT);
		this.add(labelInfo);
		
		useParams(scene);
	}

	private void useParams(SceneGame scene) {
		labelParam1 = new Label(uiLabelParam1, "param 1");
		labelParam1.setSize(60, 32);
		labelParam1.setPosition(Alignment.CENTERLEFT, 0, -24);
		labelParam1.setLayer(1);
		this.add(labelParam1);
		
		labelParam2 = new Label(uiLabelParam2, "param 2");
		labelParam2.setSize(60, 32);
		labelParam2.setPosition(Alignment.CENTERLEFT, 0, -44);
		labelParam2.setLayer(1);
		this.add(labelParam2);
		
		labelParam3 = new Label(uiLabelParam3, "param 3");
		labelParam3.setSize(60, 32);
		labelParam3.setPosition(Alignment.CENTERLEFT, 0, -64);
		labelParam3.setLayer(1);
		this.add(labelParam3);
		
		labelParam4 = new Label(uiLabelParam4, "param 4");
		labelParam4.setSize(60, 32);
		labelParam4.setPosition(Alignment.CENTERLEFT, 0, -84);
		labelParam4.setLayer(1);
		this.add(labelParam4);
		
		labelScript = new Label(uiLabelTrigger, "script");
		labelScript.setSize(60, 32);
		labelScript.setPosition(Alignment.CENTERLEFT, 0, -104);
		labelScript.setLayer(1);
		this.add(labelScript);
			
		//
		param1 = new TextField(uiParam1);
		param1.maxTextLength = 10;
		param1.setSize(120, 16);
		param1.setPosition(Alignment.CENTERLEFT, 65, -24);
		param1.setTextFilter(new TextFilterNumbers(false));
		param1.setLayer(1);
		this.add(param1);
		
		param2 = new TextField(uiParam2);
		param2.maxTextLength = 10;
		param2.setSize(120, 16);
		param2.setPosition(Alignment.CENTERLEFT, 65, -44);
		param2.setTextFilter(new TextFilterNumbers(false));
		param2.setLayer(1);
		this.add(param2);
		
		param3 = new TextField(uiParam3);
		param3.maxTextLength = 10;
		param3.setSize(120, 16);
		param3.setPosition(Alignment.CENTERLEFT, 65, -64);
		param3.setTextFilter(new TextFilterNumbers(false));
		param3.setLayer(1);
		this.add(param3);
		
		param4 = new TextField(uiParam4);
		param4.maxTextLength = 10;
		param4.setSize(120, 16);
		param4.setPosition(Alignment.CENTERLEFT, 65, -84);
		param4.setTextFilter(new TextFilterNumbers(false));
		param4.setLayer(1);
		this.add(param4);
		
		script = new TextField(uiTrigger);
		script.maxTextLength = 25;
		script.setSize(200, 16);
		script.setPosition(Alignment.CENTERLEFT, 65, -104);
		script.setLayer(1);
		this.add(script);
	}
}
