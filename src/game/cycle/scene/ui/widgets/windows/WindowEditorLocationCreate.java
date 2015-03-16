package game.cycle.scene.ui.widgets.windows;

import game.cycle.scene.game.SceneGame;
import game.cycle.scene.ui.list.UIGame;
import game.cycle.scene.ui.textfilters.TextFilterNumbers;
import game.cycle.scene.ui.widgets.Button;
import game.cycle.scene.ui.widgets.Image;
import game.cycle.scene.ui.widgets.Label;
import game.cycle.scene.ui.widgets.TextField;
import game.cycle.scene.ui.widgets.Window;
import game.resources.Resources;
import game.resources.Tex;
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
	
	public static final String uiLabelTitle = "location-create-label-title";
	public static final String uiLabelFile = "location-create-label-field";
	public static final String uiLabelNote = "location-create-label-note";
	public static final String uiLabelSizeX = "location-create-label-sizex";
	public static final String uiLabelSizeY = "location-create-label-sizey";
	public static final String uiLabelTerrain = "location-create-label-startterrain";
	
	public Label labelTitle;
	public Label labelFile;
	public Label labelNote;
	public Label labelSizeX;
	public Label labelSizeY;
	public Label labelStartTerrain;
	
	public Image  background;
	public Button cancel;
	public Button confirm;
	
	public TextField title;
	public TextField file;
	public TextField note;
	public TextField sizeX;
	public TextField sizeY;
	public TextField terrain;
	
	public WindowEditorLocationCreate(String title, UIGame ui, int layer, SceneGame scene) {
		super(title, ui, Alignment.CENTER, 280, 24, 0, 0, layer);
		this.setTexNormal(Resources.getTex(Tex.uiListLine));
		this.uigame = ui;
		this.setText("Create new Location");
		loadWidgets(scene);
	}

	private void loadWidgets(SceneGame scene) {		
		this.closeButton(true);
		this.lockButton(true);
		
		background = new Image(uiBackground);
		background.setSize(280, 150);
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
		
		labelSizeX = new Label(uiLabelSizeX, "Size X");
		labelSizeX.setSize(45, 32);
		labelSizeX.setPosition(Alignment.CENTER, -115, -84);
		labelSizeX.setLayer(1);
		this.add(labelSizeX);
		
		labelSizeY = new Label(uiLabelSizeY, "Size Y");
		labelSizeY.setSize(45, 32);
		labelSizeY.setPosition(Alignment.CENTER, 0, -84);
		labelSizeY.setLayer(1);
		this.add(labelSizeY);
		
		labelStartTerrain = new Label(uiLabelTerrain, "Terrain");
		labelStartTerrain.setSize(50, 32);
		labelStartTerrain.setPosition(Alignment.CENTER, -112, -104);
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
		
		sizeX = new TextField(uiSizeX);
		sizeX.maxTextLength = 4;
		sizeX.setSize(60, 16);
		sizeX.setPosition(Alignment.CENTER, -55, -84);
		sizeX.setTextFilter(new TextFilterNumbers(true));
		sizeX.setLayer(1);
		this.add(sizeX);
		
		sizeY = new TextField(uiSizeY);
		sizeY.maxTextLength = 4;
		sizeY.setSize(60, 16);
		sizeY.setPosition(Alignment.CENTER, 60, -84);
		sizeY.setTextFilter(new TextFilterNumbers(true));
		sizeY.setLayer(1);
		this.add(sizeY);
		
		terrain = new TextField(uiTerrain);
		terrain.maxTextLength = 3;
		terrain.setSize(60, 16);
		terrain.setPosition(Alignment.CENTER, -55, -104);
		terrain.setTextFilter(new TextFilterNumbers(true));
		terrain.setLayer(1);
		this.add(terrain);
				
		cancel = new Button(uiCancel, "Cancel");
		cancel.setTexNormal(Tex.uiBackgroundLightSelected);
		cancel.setSize(128, 32);
		cancel.setPosition(Alignment.CENTER, -70, -134);
		cancel.setScript(new ui_LocationAddMenuCancel(uigame));
		cancel.setLayer(1);
		this.add(cancel);
		
		confirm = new Button(uiConfirm, "Confirm");
		confirm.setTexNormal(Tex.uiBackgroundLightSelected);
		confirm.setSize(128, 32);
		confirm.setPosition(Alignment.CENTER, 70, -134);
		confirm.setScript(new ui_LocationAdd(uigame, scene));
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
