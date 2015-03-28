package game.cycle.scene.ui.widgets.windows;

import com.badlogic.gdx.graphics.g2d.BitmapFont.HAlignment;

import game.cycle.scene.game.SceneGame;
import game.cycle.scene.game.world.database.GameConst;
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
	public static final String uiParam1 = "goedit-param1-trigger";
	public static final String uiParam2 = "goedit-param2-trigger";
	public static final String uiParam3 = "goedit-param3-trigger";
	public static final String uiParam4 = "goedit-param4-trigger";
	
	public static final String uiTrigger = "goedit-trigger-";
	public static final String uiTriggerParam = "goedit-trigger-param-";
	public static final String uiScript = "goedit-script-";

	public static final String uiLabelTrigger = "label-goedit-trigger-";
	public static final String uiLabelTriggerParam = "label-goedit-trigger-param-";
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
	
	public Label [] labelTrigger;
	public Label [] labelTriggerParam;
	public Label [] labelScript;
	public Label [] labelScriptParam1;
	public Label [] labelScriptParam2;
	public Label [] labelScriptParam3;
	public Label [] labelScriptParam4;
	
	public Image  background;
	public Button cancel;
	public Button save;
	
	public TextField param1;
	public TextField param2;
	public TextField param3;
	public TextField param4;

	public TextField [] trigger;
	public TextField [] triggerParam;
	public TextField [] script;
	public TextField [] scriptParam1;
	public TextField [] scriptParam2;
	public TextField [] scriptParam3;
	public TextField [] scriptParam4;
	
	public WindowEditorGOEdit(String title, UIGame ui, int layer, SceneGame scene) {
		super(title, ui, Alignment.CENTER, 780, 24, 0, 0, layer);
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
			
			for(int i = 0; i < GameConst.goTriggersCount; ++i){
				trigger[i].setText(""+go.triggerType[i]);
				triggerParam[i].setText(""+go.triggerParam[i]);
				script[i].setText(""+go.scripts[i]);
				scriptParam1[i].setText(""+go.params1[i]);
				scriptParam2[i].setText(""+go.params2[i]);
				scriptParam3[i].setText(""+go.params3[i]);
				scriptParam4[i].setText(""+go.params4[i]);
			}
			
			labelInfo.setText("GO: " + go.proto.title + " guid: " + go.getGUID() + " baseid: " + go.proto.id);
		}
	}
	
	private void loadWidgets(SceneGame scene) {
		this.closeButton(true);
		this.lockButton(true);
		
		background = new Image(uiBackground);
		background.setSize(780, 220);
		background.setPosition(Alignment.UPCENTER, 0, -24);
		this.add(background);
		
		cancel = new Button(uiCancel, "Cancel");
		cancel.setTexNormal(Tex.uiBackgroundLightSelected);
		cancel.setSize(128, 32);
		cancel.setPosition(Alignment.CENTER, -70, -204);
		cancel.setScript(new ui_GOEditorMenuCancel(uigame));
		cancel.setLayer(1);
		this.add(cancel);
		
		save = new Button(uiSave, "Save");
		save.setTexNormal(Tex.uiBackgroundLightSelected);
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
		triggers(scene);
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
		//
		param1 = new TextField(uiParam1);
		param1.maxTextLength = 10;
		param1.setSize(80, 16);
		param1.setPosition(Alignment.CENTERLEFT, 65, -24);
		param1.setTextFilter(new TextFilterNumbers(false));
		param1.setLayer(1);
		this.add(param1);
		
		param2 = new TextField(uiParam2);
		param2.maxTextLength = 10;
		param2.setSize(80, 16);
		param2.setPosition(Alignment.CENTERLEFT, 65, -44);
		param2.setTextFilter(new TextFilterNumbers(false));
		param2.setLayer(1);
		this.add(param2);
		
		param3 = new TextField(uiParam3);
		param3.maxTextLength = 10;
		param3.setSize(80, 16);
		param3.setPosition(Alignment.CENTERLEFT, 65, -64);
		param3.setTextFilter(new TextFilterNumbers(false));
		param3.setLayer(1);
		this.add(param3);
		
		param4 = new TextField(uiParam4);
		param4.maxTextLength = 10;
		param4.setSize(80, 16);
		param4.setPosition(Alignment.CENTERLEFT, 65, -84);
		param4.setTextFilter(new TextFilterNumbers(false));
		param4.setLayer(1);
		this.add(param4);
	}
	
	private void triggers(SceneGame scene) {
		int count = GameConst.goTriggersCount;

		// init
		labelTrigger = new Label[count];
		labelTriggerParam = new Label[count];
		labelScript = new Label[count];
		labelScriptParam1 = new Label[count];
		labelScriptParam2 = new Label[count];
		labelScriptParam3 = new Label[count];
		labelScriptParam4 = new Label[count];
		
		trigger = new TextField[count];
		triggerParam = new TextField[count];
		script = new TextField[count];
		scriptParam1 = new TextField[count];
		scriptParam2 = new TextField[count];
		scriptParam3 = new TextField[count];
		scriptParam4 = new TextField[count];
		
		for(int i = 0; i < count; ++i){
			labelTrigger[i] = new Label(uiLabelTrigger+i, "trigger " + i);
			labelTrigger[i].setSize(60, 32);
			labelTrigger[i].setPosition(Alignment.CENTERLEFT, 150 + i*150, -24);
			labelTrigger[i].setLayer(1);
			this.add(labelTrigger[i]);
			
			labelTriggerParam[i] = new Label(uiLabelTriggerParam+i, "param");
			labelTriggerParam[i].setSize(60, 32);
			labelTriggerParam[i].setPosition(Alignment.CENTERLEFT, 150 + i*150, -44);
			labelTriggerParam[i].setLayer(1);
			this.add(labelTriggerParam[i]);
			
			labelScript[i] = new Label(uiLabelScript+i, "script " + i);
			labelScript[i].setSize(60, 32);
			labelScript[i].setPosition(Alignment.CENTERLEFT, 150 + i*150, -64);
			labelScript[i].setLayer(1);
			this.add(labelScript[i]);
			
			labelScriptParam1[i] = new Label(uiLabelParam1+i, "param 1");
			labelScriptParam1[i].setSize(60, 32);
			labelScriptParam1[i].setPosition(Alignment.CENTERLEFT, 150 + i*150, -84);
			labelScriptParam1[i].setLayer(1);
			this.add(labelScriptParam1[i]);
			
			labelScriptParam2[i] = new Label(uiLabelParam2+i, "param 2");
			labelScriptParam2[i].setSize(60, 32);
			labelScriptParam2[i].setPosition(Alignment.CENTERLEFT, 150 + i*150, -104);
			labelScriptParam2[i].setLayer(1);
			this.add(labelScriptParam2[i]);
			
			labelScriptParam3[i] = new Label(uiLabelParam3+i, "param 3");
			labelScriptParam3[i].setSize(60, 32);
			labelScriptParam3[i].setPosition(Alignment.CENTERLEFT, 150 + i*150, -124);
			labelScriptParam3[i].setLayer(1);
			this.add(labelScriptParam3[i]);
			
			labelScriptParam4[i] = new Label(uiLabelParam4+i, "param 4");
			labelScriptParam4[i].setSize(60, 32);
			labelScriptParam4[i].setPosition(Alignment.CENTERLEFT, 150 + i*150, -144);
			labelScriptParam4[i].setLayer(1);
			this.add(labelScriptParam4[i]);
			
			//
			trigger[i] = new TextField(uiTrigger+i);
			trigger[i].maxTextLength = 10;
			trigger[i].setSize(80, 16);
			trigger[i].setPosition(Alignment.CENTERLEFT, 215 + i*150, -24);
			trigger[i].setTextFilter(new TextFilterNumbers(false));
			trigger[i].setLayer(1);
			this.add(trigger[i]);
			
			triggerParam[i] = new TextField(uiTriggerParam+i);
			triggerParam[i].maxTextLength = 10;
			triggerParam[i].setSize(80, 16);
			triggerParam[i].setPosition(Alignment.CENTERLEFT, 215 + i*150, -44);
			triggerParam[i].setTextFilter(new TextFilterNumbers(false));
			triggerParam[i].setLayer(1);
			this.add(triggerParam[i]);
			
			script[i] = new TextField(uiScript+i);
			script[i].maxTextLength = 10;
			script[i].setSize(80, 16);
			script[i].setPosition(Alignment.CENTERLEFT, 215 + i*150, -64);
			script[i].setTextFilter(new TextFilterNumbers(false));
			script[i].setLayer(1);
			this.add(script[i]);
			
			scriptParam1[i] = new TextField(uiParam1+i);
			scriptParam1[i].maxTextLength = 10;
			scriptParam1[i].setSize(80, 16);
			scriptParam1[i].setPosition(Alignment.CENTERLEFT, 215 + i*150, -84);
			scriptParam1[i].setTextFilter(new TextFilterNumbers(false));
			scriptParam1[i].setLayer(1);
			this.add(scriptParam1[i]);
			
			scriptParam2[i] = new TextField(uiParam2+i);
			scriptParam2[i].maxTextLength = 10;
			scriptParam2[i].setSize(80, 16);
			scriptParam2[i].setPosition(Alignment.CENTERLEFT, 215 + i*150, -104);
			scriptParam2[i].setTextFilter(new TextFilterNumbers(false));
			scriptParam2[i].setLayer(1);
			this.add(scriptParam2[i]);
			
			scriptParam3[i] = new TextField(uiParam3+i);
			scriptParam3[i].maxTextLength = 10;
			scriptParam3[i].setSize(80, 16);
			scriptParam3[i].setPosition(Alignment.CENTERLEFT, 215 + i*150, -124);
			scriptParam3[i].setTextFilter(new TextFilterNumbers(false));
			scriptParam3[i].setLayer(1);
			this.add(scriptParam3[i]);
			
			scriptParam4[i] = new TextField(uiParam4+i);
			scriptParam4[i].maxTextLength = 10;
			scriptParam4[i].setSize(80, 16);
			scriptParam4[i].setPosition(Alignment.CENTERLEFT, 215 + i*150, -144);
			scriptParam4[i].setTextFilter(new TextFilterNumbers(false));
			scriptParam4[i].setLayer(1);
			this.add(scriptParam4[i]);
		}
	}
}
