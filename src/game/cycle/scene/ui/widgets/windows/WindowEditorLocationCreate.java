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
	
	public static final String uiBackground = "create-back";
	public static final String uiCancel = "create-cancel";
	public static final String uiConfirm = "create-confirm";
	
	public static final String uiTitle = "create-title";
	public static final String uiFile = "create-file";
	public static final String uiNote = "create-note";
	public static final String uiSizeX = "create-sizeX";
	public static final String uiCreateSizeY = "create-sizeY";
	public static final String uiCreateStartTerrain = "create-start-terrain";
	
	public static final String uiLabelTitle = "label-title-field";
	public static final String uiLabelFile = "label-file-field";
	public static final String uiLabelNote = "label-title-note";
	public static final String uiLabelSizeX = "label-title-sizex";
	public static final String uiLabelSizeY = "label-title-sizey";
	public static final String uiLabelStartTerrain = "label-title-startterrain";
	
	public boolean locationCreateVisible;
	
	public Label labelTitle;
	public Label labelFile;
	public Label labelNote;
	public Label labelSizeX;
	public Label labelSizeY;
	public Label labelStartTerrain;
	
	public Image  background;
	public Button cancel;
	public Button confirm;
	
	public TextField createLocationTitle;
	public TextField createLocationFile;
	public TextField createLocationNote;
	public TextField createLocationSizeX;
	public TextField createLocationSizeY;
	public TextField createLocationStartTerrain;
	
	public WindowEditorLocationCreate(String title, UIGame ui, int layer, SceneGame scene) {
		super(title, ui, Alignment.CENTER, 280, 24, 0, 0, layer);
		this.setTexNormal(Resources.getTex(Tex.uiListLine));
		this.uigame = ui;
		this.setText("Create new Location");
		loadWidgets(scene);
	}

	private void loadWidgets(SceneGame scene) {
		this.closeButton(true);
		
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
		
		labelStartTerrain = new Label(uiLabelStartTerrain, "Terrain");
		labelStartTerrain.setSize(50, 32);
		labelStartTerrain.setPosition(Alignment.CENTER, -112, -104);
		labelStartTerrain.setLayer(1);
		this.add(labelStartTerrain);
		
		createLocationTitle = new TextField(uiTitle);
		createLocationTitle.maxTextLength = 30;
		createLocationTitle.setSize(230, 16);
		createLocationTitle.setPosition(Alignment.CENTER, 15, -24);
		createLocationTitle.setLayer(1);
		this.add(createLocationTitle);
		
		createLocationFile = new TextField(uiFile);
		createLocationFile.maxTextLength = 30;
		createLocationFile.setSize(230, 16);
		createLocationFile.setPosition(Alignment.CENTER, 15, -44);
		createLocationFile.setLayer(1);
		this.add(createLocationFile);
		
		createLocationNote = new TextField(uiNote);
		createLocationNote.maxTextLength = 30;
		createLocationNote.setSize(230, 16);
		createLocationNote.setPosition(Alignment.CENTER, 15, -64);
		createLocationNote.setLayer(1);
		this.add(createLocationNote);
		
		createLocationSizeX = new TextField(uiSizeX);
		createLocationSizeX.maxTextLength = 4;
		createLocationSizeX.setSize(60, 16);
		createLocationSizeX.setPosition(Alignment.CENTER, -55, -84);
		createLocationSizeX.setTextFilter(new TextFilterNumbers(true));
		createLocationSizeX.setLayer(1);
		this.add(createLocationSizeX);
		
		createLocationSizeY = new TextField(uiCreateSizeY);
		createLocationSizeY.maxTextLength = 4;
		createLocationSizeY.setSize(60, 16);
		createLocationSizeY.setPosition(Alignment.CENTER, 60, -84);
		createLocationSizeY.setTextFilter(new TextFilterNumbers(true));
		createLocationSizeY.setLayer(1);
		this.add(createLocationSizeY);
		
		createLocationStartTerrain = new TextField(uiCreateStartTerrain);
		createLocationStartTerrain.maxTextLength = 3;
		createLocationStartTerrain.setSize(60, 16);
		createLocationStartTerrain.setPosition(Alignment.CENTER, -55, -104);
		createLocationStartTerrain.setTextFilter(new TextFilterNumbers(true));
		createLocationStartTerrain.setLayer(1);
		this.add(createLocationStartTerrain);
				
		cancel = new Button(uiCancel, "Cancel");
		cancel.setSize(128, 32);
		cancel.setPosition(Alignment.CENTER, -70, -134);
		cancel.setScript(new ui_LocationAddMenuCancel(uigame));
		cancel.setLayer(1);
		this.add(cancel);
		
		confirm = new Button(uiConfirm, "Confirm");
		confirm.setSize(128, 32);
		confirm.setPosition(Alignment.CENTER, 70, -134);
		confirm.setScript(new ui_LocationAdd(uigame, scene));
		confirm.setLayer(1);
		this.add(confirm);
	}
	
	public void resetFileds(){
		createLocationTitle.setText("");
		createLocationFile.setText("");
		createLocationNote.setText("");
		createLocationSizeX.setText("");
		createLocationSizeY.setText("");
		createLocationStartTerrain.setText("");
	}
}
