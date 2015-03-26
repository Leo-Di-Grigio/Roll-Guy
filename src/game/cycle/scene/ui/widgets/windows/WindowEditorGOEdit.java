package game.cycle.scene.ui.widgets.windows;

import com.badlogic.gdx.graphics.g2d.BitmapFont.HAlignment;

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
	
	public static final String uiTrigger1 = "goedit-trigger1";
	public static final String uiScript1 = "goedit-script1";
	public static final String uiScript1Param1 = "goedit-script1-param1";
	public static final String uiScript1Param2 = "goedit-script1-param2";
	public static final String uiScript1Param3 = "goedit-script1-param3";
	public static final String uiScript1Param4 = "goedit-script1-param4";
	
	public static final String uiTrigger2 = "goedit-trigger2";
	public static final String uiScript2 = "goedit-script2";
	public static final String uiScript2Param1 = "goedit-script2-param1";
	public static final String uiScript2Param2 = "goedit-script2-param2";
	public static final String uiScript2Param3 = "goedit-script2-param3";
	public static final String uiScript2Param4 = "goedit-script2-param4";
	
	public static final String uiTrigger3 = "goedit-trigger3";
	public static final String uiScript3 = "goedit-script3";
	public static final String uiScript3Param1 = "goedit-script3-param1";
	public static final String uiScript3Param2 = "goedit-script3-param2";
	public static final String uiScript3Param3 = "goedit-script3-param3";
	public static final String uiScript3Param4 = "goedit-script3-param4";
	
	public static final String uiTrigger4 = "goedit-trigger4";
	public static final String uiScript4 = "goedit-script4";
	public static final String uiScript4Param1 = "goedit-script4-param1";
	public static final String uiScript4Param2 = "goedit-script4-param2";
	public static final String uiScript4Param3 = "goedit-script4-param3";
	public static final String uiScript4Param4 = "goedit-script4-param4";
	
	public static final String uiCancel = "goedit-cancel";
	public static final String uiSave = "goedit-save";
	
	public static final String uiLabelParam1 = "label-goedit-param1";
	public static final String uiLabelParam2 = "label-goedit-param2";
	public static final String uiLabelParam3 = "label-goedit-param3";
	public static final String uiLabelParam4 = "label-goedit-param4";
	
	public static final String uiLabelTrigger1 = "label-goedit-trigger1";
	public static final String uiLabelScript1 = "label-goedit-script1";
	public static final String uiLabelScript1Param1 = "label-goedit-script1-param1";
	public static final String uiLabelScript1Param2 = "label-goedit-script1-param2";
	public static final String uiLabelScript1Param3 = "label-goedit-script1-param3";
	public static final String uiLabelScript1Param4 = "label-goedit-script1-param4";
	
	public static final String uiLabelTrigger2 = "label-goedit-trigger2";
	public static final String uiLabelScript2 = "label-goedit-script2";
	public static final String uiLabelScript2Param1 = "label-goedit-script2-param1";
	public static final String uiLabelScript2Param2 = "label-goedit-script2-param2";
	public static final String uiLabelScript2Param3 = "label-goedit-script2-param3";
	public static final String uiLabelScript2Param4 = "label-goedit-script2-param4";
	
	public static final String uiLabelTrigger3 = "label-goedit-trigger3";
	public static final String uiLabelScript3 = "label-goedit-script3";
	public static final String uiLabelScript3Param1 = "label-goedit-script3-param1";
	public static final String uiLabelScript3Param2 = "label-goedit-script3-param2";
	public static final String uiLabelScript3Param3 = "label-goedit-script3-param3";
	public static final String uiLabelScript3Param4 = "label-goedit-script3-param4";
	
	public static final String uiLabelTrigger4 = "label-goedit-trigger4";
	public static final String uiLabelScript4 = "label-goedit-script4";
	public static final String uiLabelScript4Param1 = "label-goedit-script4-param1";
	public static final String uiLabelScript4Param2 = "label-goedit-script4-param2";
	public static final String uiLabelScript4Param3 = "label-goedit-script4-param3";
	public static final String uiLabelScript4Param4 = "label-goedit-script4-param4";
	
	public static final String uiLabelInfo = "label-goedit-info";
	
	public Label labelParam1;
	public Label labelParam2;
	public Label labelParam3;
	public Label labelParam4;
	
	public Label labelTrigger1;
	public Label labelScript1;
	public Label labelScript1Param1;
	public Label labelScript1Param2;
	public Label labelScript1Param3;
	public Label labelScript1Param4;
	
	public Label labelTrigger2;
	public Label labelScript2;
	public Label labelScript2Param1;
	public Label labelScript2Param2;
	public Label labelScript2Param3;
	public Label labelScript2Param4;
	
	public Label labelTrigger3;
	public Label labelScript3;
	public Label labelScript3Param1;
	public Label labelScript3Param2;
	public Label labelScript3Param3;
	public Label labelScript3Param4;
	
	public Label labelTrigger4;
	public Label labelScript4;
	public Label labelScript4Param1;
	public Label labelScript4Param2;
	public Label labelScript4Param3;
	public Label labelScript4Param4;
	
	public Label labelInfo;
	
	public Image  background;
	public Button cancel;
	public Button save;
	
	public TextField param1;
	public TextField param2;
	public TextField param3;
	public TextField param4;
	
	public TextField trigger1;
	public TextField script1;
	public TextField script1param1;
	public TextField script1param2;
	public TextField script1param3;
	public TextField script1param4;
	
	public TextField trigger2;
	public TextField script2;
	public TextField script2param1;
	public TextField script2param2;
	public TextField script2param3;
	public TextField script2param4;
	
	public TextField trigger3;
	public TextField script3;
	public TextField script3param1;
	public TextField script3param2;
	public TextField script3param3;
	public TextField script3param4;
	
	public TextField trigger4;
	public TextField script4;
	public TextField script4param1;
	public TextField script4param2;
	public TextField script4param3;
	public TextField script4param4;
	
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
			
			trigger1.setText(""+go.triggers[0]);
			script1.setText(""+go.scripts[0]);
			script1param1.setText(""+go.params1[0]);
			script1param2.setText(""+go.params2[0]);
			script1param3.setText(""+go.params3[0]);
			script1param4.setText(""+go.params4[0]);
			
			trigger2.setText(""+go.triggers[1]);
			script2.setText(""+go.scripts[1]);
			script2param1.setText(""+go.params1[1]);
			script2param2.setText(""+go.params2[1]);
			script2param3.setText(""+go.params3[1]);
			script2param4.setText(""+go.params4[1]);
			
			trigger3.setText(""+go.triggers[2]);
			script3.setText(""+go.scripts[2]);
			script3param1.setText(""+go.params1[2]);
			script3param2.setText(""+go.params2[2]);
			script3param3.setText(""+go.params3[2]);
			script3param4.setText(""+go.params4[2]);
			
			trigger4.setText(""+go.triggers[3]);
			script4.setText(""+go.scripts[3]);
			script4param1.setText(""+go.params1[3]);
			script4param2.setText(""+go.params2[3]);
			script4param3.setText(""+go.params3[3]);
			script4param4.setText(""+go.params4[3]);
			
			labelInfo.setText("GO: " + go.proto.title + " guid: " + go.id + " baseid: " + go.proto.id);
		}
	}
	
	private void loadWidgets(SceneGame scene) {
		this.closeButton(true);
		this.lockButton(true);
		
		background = new Image(uiBackground);
		background.setSize(780, 200);
		background.setPosition(Alignment.UPCENTER, 0, -24);
		this.add(background);
		
		cancel = new Button(uiCancel, "Cancel");
		cancel.setTexNormal(Tex.uiBackgroundLightSelected);
		cancel.setSize(128, 32);
		cancel.setPosition(Alignment.CENTER, -70, -184);
		cancel.setScript(new ui_GOEditorMenuCancel(uigame));
		cancel.setLayer(1);
		this.add(cancel);
		
		save = new Button(uiSave, "Save");
		save.setTexNormal(Tex.uiBackgroundLightSelected);
		save.setSize(128, 32);
		save.setPosition(Alignment.CENTER, 70, -184);
		save.setLayer(1);
		this.add(save);
		
		labelInfo = new Label(uiLabelInfo, "test");
		labelInfo.setSize(250, 32);
		labelInfo.setPosition(Alignment.CENTER, 0, -150);
		labelInfo.setLayer(1);
		labelInfo.setTextAlignment(HAlignment.LEFT);
		this.add(labelInfo);
		
		useParams(scene);
		trigger1(scene);
		trigger2(scene);
		trigger3(scene);
		trigger4(scene);
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
	
	private void trigger1(SceneGame scene) {
		labelTrigger1 = new Label(uiLabelTrigger1, "trigger 1");
		labelTrigger1.setSize(60, 32);
		labelTrigger1.setPosition(Alignment.CENTERLEFT, 150, -24);
		labelTrigger1.setLayer(1);
		this.add(labelTrigger1);
		
		labelScript1 = new Label(uiLabelScript1, "script 1");
		labelScript1.setSize(60, 32);
		labelScript1.setPosition(Alignment.CENTERLEFT, 150, -44);
		labelScript1.setLayer(1);
		this.add(labelScript1);
		
		labelScript1Param1 = new Label(uiLabelScript1Param1, "param 1");
		labelScript1Param1.setSize(60, 32);
		labelScript1Param1.setPosition(Alignment.CENTERLEFT, 150, -64);
		labelScript1Param1.setLayer(1);
		this.add(labelScript1Param1);
		
		labelScript1Param2 = new Label(uiLabelScript1Param2, "param 2");
		labelScript1Param2.setSize(60, 32);
		labelScript1Param2.setPosition(Alignment.CENTERLEFT, 150, -84);
		labelScript1Param2.setLayer(1);
		this.add(labelScript1Param2);
		
		labelScript1Param3 = new Label(uiLabelScript1Param3, "param 3");
		labelScript1Param3.setSize(60, 32);
		labelScript1Param3.setPosition(Alignment.CENTERLEFT, 150, -104);
		labelScript1Param3.setLayer(1);
		this.add(labelScript1Param3);
		
		labelScript1Param4 = new Label(uiLabelScript1Param4, "param 4");
		labelScript1Param4.setSize(60, 32);
		labelScript1Param4.setPosition(Alignment.CENTERLEFT, 150, -124);
		labelScript1Param4.setLayer(1);
		this.add(labelScript1Param4);
		
		//
		trigger1 = new TextField(uiTrigger1);
		trigger1.maxTextLength = 10;
		trigger1.setSize(80, 16);
		trigger1.setPosition(Alignment.CENTERLEFT, 215, -24);
		trigger1.setTextFilter(new TextFilterNumbers(false));
		trigger1.setLayer(1);
		this.add(trigger1);
		
		script1 = new TextField(uiScript1);
		script1.maxTextLength = 10;
		script1.setSize(80, 16);
		script1.setPosition(Alignment.CENTERLEFT, 215, -44);
		script1.setTextFilter(new TextFilterNumbers(false));
		script1.setLayer(1);
		this.add(script1);
		
		script1param1 = new TextField(uiScript1Param1);
		script1param1.maxTextLength = 10;
		script1param1.setSize(80, 16);
		script1param1.setPosition(Alignment.CENTERLEFT, 215, -64);
		script1param1.setTextFilter(new TextFilterNumbers(false));
		script1param1.setLayer(1);
		this.add(script1param1);
		
		script1param2 = new TextField(uiScript1Param2);
		script1param2.maxTextLength = 10;
		script1param2.setSize(80, 16);
		script1param2.setPosition(Alignment.CENTERLEFT, 215, -84);
		script1param2.setTextFilter(new TextFilterNumbers(false));
		script1param2.setLayer(1);
		this.add(script1param2);
		
		script1param3 = new TextField(uiScript1Param3);
		script1param3.maxTextLength = 10;
		script1param3.setSize(80, 16);
		script1param3.setPosition(Alignment.CENTERLEFT, 215, -104);
		script1param3.setTextFilter(new TextFilterNumbers(false));
		script1param3.setLayer(1);
		this.add(script1param3);
		
		script1param4 = new TextField(uiScript1Param4);
		script1param4.maxTextLength = 10;
		script1param4.setSize(80, 16);
		script1param4.setPosition(Alignment.CENTERLEFT, 215, -124);
		script1param4.setTextFilter(new TextFilterNumbers(false));
		script1param4.setLayer(1);
		this.add(script1param4);
	}

	private void trigger2(SceneGame scene) {
		labelTrigger2 = new Label(uiLabelTrigger2, "trigger 2");
		labelTrigger2.setSize(60, 32);
		labelTrigger2.setPosition(Alignment.CENTERLEFT, 300, -24);
		labelTrigger2.setLayer(1);
		this.add(labelTrigger2);
		
		labelScript2 = new Label(uiLabelScript2, "script 2");
		labelScript2.setSize(60, 32);
		labelScript2.setPosition(Alignment.CENTERLEFT, 300, -44);
		labelScript2.setLayer(1);
		this.add(labelScript2);
		
		labelScript2Param1 = new Label(uiLabelScript2Param1, "param 1");
		labelScript2Param1.setSize(60, 32);
		labelScript2Param1.setPosition(Alignment.CENTERLEFT, 300, -64);
		labelScript2Param1.setLayer(1);
		this.add(labelScript2Param1);
		
		labelScript2Param2 = new Label(uiLabelScript2Param2, "param 2");
		labelScript2Param2.setSize(60, 32);
		labelScript2Param2.setPosition(Alignment.CENTERLEFT, 300, -84);
		labelScript2Param2.setLayer(1);
		this.add(labelScript2Param2);
		
		labelScript2Param3 = new Label(uiLabelScript2Param3, "param 3");
		labelScript2Param3.setSize(60, 32);
		labelScript2Param3.setPosition(Alignment.CENTERLEFT, 300, -104);
		labelScript2Param3.setLayer(1);
		this.add(labelScript2Param3);
		
		labelScript2Param4 = new Label(uiLabelScript2Param4, "param 4");
		labelScript2Param4.setSize(60, 32);
		labelScript2Param4.setPosition(Alignment.CENTERLEFT, 300, -124);
		labelScript2Param4.setLayer(1);
		this.add(labelScript2Param4);
		
		//
		trigger2 = new TextField(uiTrigger2);
		trigger2.maxTextLength = 10;
		trigger2.setSize(80, 16);
		trigger2.setPosition(Alignment.CENTERLEFT, 365, -24);
		trigger2.setTextFilter(new TextFilterNumbers(false));
		trigger2.setLayer(1);
		this.add(trigger2);
		
		script2 = new TextField(uiScript2);
		script2.maxTextLength = 10;
		script2.setSize(80, 16);
		script2.setPosition(Alignment.CENTERLEFT, 365, -44);
		script2.setTextFilter(new TextFilterNumbers(false));
		script2.setLayer(1);
		this.add(script2);
		
		script2param1 = new TextField(uiScript2Param1);
		script2param1.maxTextLength = 10;
		script2param1.setSize(80, 16);
		script2param1.setPosition(Alignment.CENTERLEFT, 365, -64);
		script2param1.setTextFilter(new TextFilterNumbers(false));
		script2param1.setLayer(1);
		this.add(script2param1);
		
		script2param2 = new TextField(uiScript2Param2);
		script2param2.maxTextLength = 10;
		script2param2.setSize(80, 16);
		script2param2.setPosition(Alignment.CENTERLEFT, 365, -84);
		script2param2.setTextFilter(new TextFilterNumbers(false));
		script2param2.setLayer(1);
		this.add(script2param2);
		
		script2param3 = new TextField(uiScript2Param3);
		script2param3.maxTextLength = 10;
		script2param3.setSize(80, 16);
		script2param3.setPosition(Alignment.CENTERLEFT, 365, -104);
		script2param3.setTextFilter(new TextFilterNumbers(false));
		script2param3.setLayer(1);
		this.add(script2param3);
		
		script2param4 = new TextField(uiScript2Param4);
		script2param4.maxTextLength = 10;
		script2param4.setSize(80, 16);
		script2param4.setPosition(Alignment.CENTERLEFT, 365, -124);
		script2param4.setTextFilter(new TextFilterNumbers(false));
		script2param4.setLayer(1);
		this.add(script2param4);
	}

	private void trigger3(SceneGame scene) {
		labelTrigger3 = new Label(uiLabelTrigger3, "trigger 3");
		labelTrigger3.setSize(60, 32);
		labelTrigger3.setPosition(Alignment.CENTERLEFT, 450, -24);
		labelTrigger3.setLayer(1);
		this.add(labelTrigger3);
		
		labelScript3 = new Label(uiLabelScript3, "script 3");
		labelScript3.setSize(60, 32);
		labelScript3.setPosition(Alignment.CENTERLEFT, 450, -44);
		labelScript3.setLayer(1);
		this.add(labelScript3);
		
		labelScript3Param1 = new Label(uiLabelScript3Param1, "param 1");
		labelScript3Param1.setSize(60, 32);
		labelScript3Param1.setPosition(Alignment.CENTERLEFT, 450, -64);
		labelScript3Param1.setLayer(1);
		this.add(labelScript3Param1);
		
		labelScript3Param2 = new Label(uiLabelScript3Param2, "param 2");
		labelScript3Param2.setSize(60, 32);
		labelScript3Param2.setPosition(Alignment.CENTERLEFT, 450, -84);
		labelScript3Param2.setLayer(1);
		this.add(labelScript3Param2);
		
		labelScript3Param3 = new Label(uiLabelScript3Param3, "param 3");
		labelScript3Param3.setSize(60, 32);
		labelScript3Param3.setPosition(Alignment.CENTERLEFT, 450, -104);
		labelScript3Param3.setLayer(1);
		this.add(labelScript3Param3);
		
		labelScript3Param4 = new Label(uiLabelScript3Param4, "param 4");
		labelScript3Param4.setSize(60, 32);
		labelScript3Param4.setPosition(Alignment.CENTERLEFT, 450, -124);
		labelScript3Param4.setLayer(1);
		this.add(labelScript3Param4);
		
		//
		trigger3 = new TextField(uiTrigger3);
		trigger3.maxTextLength = 10;
		trigger3.setSize(80, 16);
		trigger3.setPosition(Alignment.CENTERLEFT, 515, -24);
		trigger3.setTextFilter(new TextFilterNumbers(false));
		trigger3.setLayer(1);
		this.add(trigger3);
		
		script3 = new TextField(uiScript3);
		script3.maxTextLength = 10;
		script3.setSize(80, 16);
		script3.setPosition(Alignment.CENTERLEFT, 515, -44);
		script3.setTextFilter(new TextFilterNumbers(false));
		script3.setLayer(1);
		this.add(script3);
		
		script3param1 = new TextField(uiScript3Param1);
		script3param1.maxTextLength = 10;
		script3param1.setSize(80, 16);
		script3param1.setPosition(Alignment.CENTERLEFT, 515, -64);
		script3param1.setTextFilter(new TextFilterNumbers(false));
		script3param1.setLayer(1);
		this.add(script3param1);
		
		script3param2 = new TextField(uiScript3Param2);
		script3param2.maxTextLength = 10;
		script3param2.setSize(80, 16);
		script3param2.setPosition(Alignment.CENTERLEFT, 515, -84);
		script3param2.setTextFilter(new TextFilterNumbers(false));
		script3param2.setLayer(1);
		this.add(script3param2);
		
		script3param3 = new TextField(uiScript3Param3);
		script3param3.maxTextLength = 10;
		script3param3.setSize(80, 16);
		script3param3.setPosition(Alignment.CENTERLEFT, 515, -104);
		script3param3.setTextFilter(new TextFilterNumbers(false));
		script3param3.setLayer(1);
		this.add(script3param3);
		
		script3param4 = new TextField(uiScript3Param4);
		script3param4.maxTextLength = 10;
		script3param4.setSize(80, 16);
		script3param4.setPosition(Alignment.CENTERLEFT, 515, -124);
		script3param4.setTextFilter(new TextFilterNumbers(false));
		script3param4.setLayer(1);
		this.add(script3param4);
	}

	private void trigger4(SceneGame scene) {
		labelTrigger4 = new Label(uiLabelTrigger4, "trigger 4");
		labelTrigger4.setSize(60, 32);
		labelTrigger4.setPosition(Alignment.CENTERLEFT, 600, -24);
		labelTrigger4.setLayer(1);
		this.add(labelTrigger4);
		
		labelScript4 = new Label(uiLabelScript4, "script 4");
		labelScript4.setSize(60, 32);
		labelScript4.setPosition(Alignment.CENTERLEFT, 600, -44);
		labelScript4.setLayer(1);
		this.add(labelScript4);
		
		labelScript4Param1 = new Label(uiLabelScript4Param1, "param 1");
		labelScript4Param1.setSize(60, 32);
		labelScript4Param1.setPosition(Alignment.CENTERLEFT, 600, -64);
		labelScript4Param1.setLayer(1);
		this.add(labelScript4Param1);
		
		labelScript4Param2 = new Label(uiLabelScript4Param2, "param 2");
		labelScript4Param2.setSize(60, 32);
		labelScript4Param2.setPosition(Alignment.CENTERLEFT, 600, -84);
		labelScript4Param2.setLayer(1);
		this.add(labelScript4Param2);
		
		labelScript4Param3 = new Label(uiLabelScript4Param3, "param 3");
		labelScript4Param3.setSize(60, 32);
		labelScript4Param3.setPosition(Alignment.CENTERLEFT, 600, -104);
		labelScript4Param3.setLayer(1);
		this.add(labelScript4Param3);
		
		labelScript4Param4 = new Label(uiLabelScript4Param4, "param 4");
		labelScript4Param4.setSize(60, 32);
		labelScript4Param4.setPosition(Alignment.CENTERLEFT, 600, -124);
		labelScript4Param4.setLayer(1);
		this.add(labelScript4Param4);
		
		//
		trigger4 = new TextField(uiTrigger4);
		trigger4.maxTextLength = 10;
		trigger4.setSize(80, 16);
		trigger4.setPosition(Alignment.CENTERLEFT, 665, -24);
		trigger4.setTextFilter(new TextFilterNumbers(false));
		trigger4.setLayer(1);
		this.add(trigger4);
		
		script4 = new TextField(uiScript4);
		script4.maxTextLength = 10;
		script4.setSize(80, 16);
		script4.setPosition(Alignment.CENTERLEFT, 665, -44);
		script4.setTextFilter(new TextFilterNumbers(false));
		script4.setLayer(1);
		this.add(script4);
		
		script4param1 = new TextField(uiScript4Param1);
		script4param1.maxTextLength = 10;
		script4param1.setSize(80, 16);
		script4param1.setPosition(Alignment.CENTERLEFT, 665, -64);
		script4param1.setTextFilter(new TextFilterNumbers(false));
		script4param1.setLayer(1);
		this.add(script4param1);
		
		script4param2 = new TextField(uiScript4Param2);
		script4param2.maxTextLength = 10;
		script4param2.setSize(80, 16);
		script4param2.setPosition(Alignment.CENTERLEFT, 665, -84);
		script4param2.setTextFilter(new TextFilterNumbers(false));
		script4param2.setLayer(1);
		this.add(script4param2);
		
		script4param3 = new TextField(uiScript4Param3);
		script4param3.maxTextLength = 10;
		script4param3.setSize(80, 16);
		script4param3.setPosition(Alignment.CENTERLEFT, 665, -104);
		script4param3.setTextFilter(new TextFilterNumbers(false));
		script4param3.setLayer(1);
		this.add(script4param3);
		
		script4param4 = new TextField(uiScript4Param4);
		script4param4.maxTextLength = 10;
		script4param4.setSize(80, 16);
		script4param4.setPosition(Alignment.CENTERLEFT, 665, -124);
		script4param4.setTextFilter(new TextFilterNumbers(false));
		script4param4.setLayer(1);
		this.add(script4param4);
	}
}
