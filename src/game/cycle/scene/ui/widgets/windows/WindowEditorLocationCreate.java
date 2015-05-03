package game.cycle.scene.ui.widgets.windows;

import resources.Resources;
import resources.tex.Tex;
import ui.Alignment;
import ui.Window;
import ui.widgets.used.Button;
import ui.widgets.used.Image;
import ui.widgets.used.Label;
import ui.widgets.used.TextField;
import game.cycle.scene.game.SceneGame;
import game.cycle.scene.ui.list.UIGame;
import game.cycle.scene.ui.textfilters.TextFilterNumbers;
import game.script.ui.editor.ui_LocationAdd;
import game.script.ui.editor.ui_LocationAddMenuCancel;

public class WindowEditorLocationCreate extends Window {
	
	private UIGame uigame;
	
	public static final String uiBackground = "location-create-back";
	public static final String uiCancel = "location-create-cancel";
	public static final String uiConfirm = "location-create-confirm";
	
	public static final String uiTitle = "location-create-title";
	public static final String uiFile = "location-create-file";
	public static final String uiNote = "location-create-note";
	public static final String uiSizeX = "location-create-sizeX";
	public static final String uiSizeY = "location-create-sizeY";
	public static final String uiTerrain = "location-create-start-terrain";
	public static final String uiEventScript = "location-create-event-script";
	
	public static final String uiLabelTitle = "location-create-label-title";
	public static final String uiLabelFile = "location-create-label-field";
	public static final String uiLabelNote = "location-create-label-note";
	public static final String uiLabelSizeX = "location-create-label-sizex";
	public static final String uiLabelSizeY = "location-create-label-sizey";
	public static final String uiLabelTerrain = "location-create-label-startterrain";
	public static final String uiLabelEventScript = "location-create-label-event-script";
	
	public Label labelTitle;
	public Label labelFile;
	public Label labelNote;
	public Label labelSizeX;
	public Label labelSizeY;
	public Label labelStartTerrain;
	public Label labelEventScript;
	
	public Image  background;
	public Button cancel;
	public Button confirm;
	
	public TextField title;
	public TextField file;
	public TextField note;
	public TextField sizeX;
	public TextField sizeY;
	public TextField terrain;
	public TextField eventScript;
	
	public WindowEditorLocationCreate(String title, UIGame ui, int layer, SceneGame scene) {
		super(title, ui, Alignment.CENTER, 280, 24, 0, 0, layer);
		this.setTexNormal(Resources.getTex(Tex.UI_LIST_LINE));
		this.uigame = ui;
		this.setText("Create new Location");
		loadWidgets(scene);
	}

	private void loadWidgets(SceneGame scene) {		
		this.closeButton(true);
		this.lockButton(true);
		
		background = new Image(uiBackground);
		background.setSize(280, 170);
		background.setPosition(Alignment.UPCENTER, 0, -24);
		this.add(background);
		
		labelTitle = new Label(uiLabelTitle, "Title");
		labelTitle.setSize(35, 32);
		labelTitle.setPosition(Alignment.CENTER, -120, -24);
		labelTitle.setLayer(1);
		this.add(labelTitle);
		
		labelFile = new Label(uiLabelFile, "File");
		labelFile.setSize(35, 32);
		labelFile.setPosition(Alignment.CENTER, -123, -44);
		labelFile.setLayer(1);
		this.add(labelFile);
		
		labelNote = new Label(uiLabelNote, "Note");
		labelNote.setSize(35, 32);
		labelNote.setPosition(Alignment.CENTER, -120, -64);
		labelNote.setLayer(1);
		this.add(labelNote);
		
		labelEventScript = new Label(uiLabelEventScript, "Lua");
		labelEventScript.setSize(50, 32);
		labelEventScript.setPosition(Alignment.CENTER, -130, -84);
		labelEventScript.setLayer(1);
		this.add(labelEventScript);
		
		labelSizeX = new Label(uiLabelSizeX, "Size X");
		labelSizeX.setSize(45, 32);
		labelSizeX.setPosition(Alignment.CENTER, -115, -104);
		labelSizeX.setLayer(1);
		this.add(labelSizeX);
		
		labelSizeY = new Label(uiLabelSizeY, "Size Y");
		labelSizeY.setSize(45, 32);
		labelSizeY.setPosition(Alignment.CENTER, 0, -104);
		labelSizeY.setLayer(1);
		this.add(labelSizeY);
		
		labelStartTerrain = new Label(uiLabelTerrain, "Terrain");
		labelStartTerrain.setSize(50, 32);
		labelStartTerrain.setPosition(Alignment.CENTER, -112, -124);
		labelStartTerrain.setLayer(1);
		this.add(labelStartTerrain);
		
		title = new TextField(uiTitle);
		title.maxTextLength = 30;
		title.setSize(230, 16);
		title.setPosition(Alignment.CENTER, 15, -24);
		title.setLayer(1);
		this.add(title);
		
		file = new TextField(uiFile);
		file.maxTextLength = 30;
		file.setSize(230, 16);
		file.setPosition(Alignment.CENTER, 15, -44);
		file.setLayer(1);
		this.add(file);
		
		note = new TextField(uiNote);
		note.maxTextLength = 30;
		note.setSize(230, 16);
		note.setPosition(Alignment.CENTER, 15, -64);
		note.setLayer(1);
		this.add(note);
		
		eventScript = new TextField(uiEventScript);
		eventScript.maxTextLength = 30;
		eventScript.setSize(230, 16);
		eventScript.setPosition(Alignment.CENTER, 15, -84);
		eventScript.setLayer(1);
		this.add(eventScript);
		
		sizeX = new TextField(uiSizeX);
		sizeX.maxTextLength = 4;
		sizeX.setSize(60, 16);
		sizeX.setPosition(Alignment.CENTER, -55, -104);
		sizeX.setTextFilter(new TextFilterNumbers(true));
		sizeX.setLayer(1);
		this.add(sizeX);
		
		sizeY = new TextField(uiSizeY);
		sizeY.maxTextLength = 4;
		sizeY.setSize(60, 16);
		sizeY.setPosition(Alignment.CENTER, 60, -104);
		sizeY.setTextFilter(new TextFilterNumbers(true));
		sizeY.setLayer(1);
		this.add(sizeY);
		
		terrain = new TextField(uiTerrain);
		terrain.maxTextLength = 3;
		terrain.setSize(60, 16);
		terrain.setPosition(Alignment.CENTER, -55, -124);
		terrain.setTextFilter(new TextFilterNumbers(true));
		terrain.setLayer(1);
		this.add(terrain);
				
		cancel = new Button(uiCancel, "Cancel");
		cancel.setTexNormal(Tex.UI_BACKGROUND_SELECTED_LIGHT);
		cancel.setSize(128, 32);
		cancel.setPosition(Alignment.CENTER, -70, -154);
		cancel.setScript(new ui_LocationAddMenuCancel(uigame));
		cancel.setLayer(1);
		this.add(cancel);
		
		confirm = new Button(uiConfirm, "Confirm");
		confirm.setTexNormal(Tex.UI_BACKGROUND_SELECTED_LIGHT);
		confirm.setSize(128, 32);
		confirm.setPosition(Alignment.CENTER, 70, -154);
		confirm.setScript(new ui_LocationAdd(uigame));
		confirm.setLayer(1);
		this.add(confirm);
	}
	
	public void resetFileds(){
		title.setText("");
		file.setText("");
		note.setText("");
		sizeX.setText("");
		sizeY.setText("");
		terrain.setText("");
	}
}
