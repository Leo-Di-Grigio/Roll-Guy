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
import game.script.ui.app.ui_GOEditorMenuCancel;
import game.script.ui.app.ui_GOEditorMenuSave;

public class WindowEditorGOEdit extends Window {
	
	private UIGame uigame;
	
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
	
	public WindowEditorGOEdit(String title, UIGame ui, int layer, SceneGame scene) {
		super(title, ui, Alignment.CENTER, 280, 200, 0, 0, layer);
		this.setTexNormal(Resources.getTex(Tex.uiListLine));
		this.uigame = ui;
		this.closeButton(true);
		
		loadWidgets(scene);
	}
	
	public void setGO(GO go){
		if(go == null){
			setVisible(false);
			goEditSave.setScript(new ui_GOEditorMenuCancel(uigame));
		}
		else{
			setVisible(true);
			goEditSave.setScript(new ui_GOEditorMenuSave(this, go));
			goEditParam1.setText("" + go.param1);
			goEditParam2.setText("" + go.param2);
			goEditParam3.setText("" + go.param3);
			goEditParam4.setText("" + go.param4);
			goEditTitle.setText("GO: " + go.proto.title + " guid: " + go.id + " baseid: " + go.proto.id);
		}
	}
	
	private void loadWidgets(SceneGame scene) {
		labelGOEdit = new Label(uiLabelGOEdit, "Edit Game Object");
		labelGOEdit.setSize(128, 32);
		labelGOEdit.setPosition(Alignment.CENTER, 0, 85);
		this.add(labelGOEdit);
		
		labelGOParam1 = new Label(uiLabelGOEditParam1, "param 1");
		labelGOParam1.setSize(60, 32);
		labelGOParam1.setPosition(Alignment.CENTER, -110, 65);
		this.add(labelGOParam1);
		
		labelGOParam2 = new Label(uiLabelGOEditParam2, "param 2");
		labelGOParam2.setSize(60, 32);
		labelGOParam2.setPosition(Alignment.CENTER, -110, 45);
		this.add(labelGOParam2);
		
		labelGOParam3 = new Label(uiLabelGOEditParam3, "param 3");
		labelGOParam3.setSize(60, 32);
		labelGOParam3.setPosition(Alignment.CENTER, -110, 25);
		this.add(labelGOParam3);
		
		labelGOParam4 = new Label(uiLabelGOEditParam4, "param 4");
		labelGOParam4.setSize(60, 32);
		labelGOParam4.setPosition(Alignment.CENTER, -110, 5);
		this.add(labelGOParam4);
		//
		goEditParam1 = new TextField(uiGOEditParam1);
		goEditParam1.maxTextLength = 10;
		goEditParam1.setSize(210, 16);
		goEditParam1.setPosition(Alignment.CENTER, 30, 65);
		goEditParam1.setTextFilter(new TextFilterNumbers(false));
		this.add(goEditParam1);
		
		goEditParam2 = new TextField(uiGOEditParam2);
		goEditParam2.maxTextLength = 10;
		goEditParam2.setSize(210, 16);
		goEditParam2.setPosition(Alignment.CENTER, 30, 45);
		goEditParam2.setTextFilter(new TextFilterNumbers(false));
		this.add(goEditParam2);
		
		goEditParam3 = new TextField(uiGOEditParam3);
		goEditParam3.maxTextLength = 10;
		goEditParam3.setSize(210, 16);
		goEditParam3.setPosition(Alignment.CENTER, 30, 25);
		goEditParam3.setTextFilter(new TextFilterNumbers(false));
		this.add(goEditParam3);
		
		goEditParam4 = new TextField(uiGOEditParam4);
		goEditParam4.maxTextLength = 10;
		goEditParam4.setSize(210, 16);
		goEditParam4.setPosition(Alignment.CENTER, 30, 5);
		goEditParam4.setTextFilter(new TextFilterNumbers(false));
		this.add(goEditParam4);
		
		goEditTitle = new Label(uiGOEditTitle, "test");
		goEditTitle.setSize(250, 32);
		goEditTitle.setPosition(Alignment.CENTER, 0, -20);
		this.add(goEditTitle);
		
		goEditCancel = new Button(uiGOEditCancel, "Cancel");
		goEditCancel.setSize(128, 32);
		goEditCancel.setPosition(Alignment.CENTER, -70, -80);
		goEditCancel.setScript(new ui_GOEditorMenuCancel(uigame));
		this.add(goEditCancel);
		
		goEditSave = new Button(uiGOEditSave, "Save");
		goEditSave.setSize(128, 32);
		goEditSave.setPosition(Alignment.CENTER, 70, -80);
		this.add(goEditSave);
	}
}
