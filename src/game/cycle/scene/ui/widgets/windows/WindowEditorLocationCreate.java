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
import game.script.ui.app.ui_LocationAdd;
import game.script.ui.app.ui_LocationAddMenuCancel;

public class WindowEditorLocationCreate extends Window {
	
	private UIGame uigame;
	
	public static final String uiCreateBackground = "create-back";
	public static final String uiCreateCancel = "create-cancel";
	public static final String uiCreateConfirm = "create-confirm";
	
	public static final String uiCreateTitle = "create-title";
	public static final String uiCreateFile = "create-file";
	public static final String uiCreateNote = "create-note";
	public static final String uiCreateSizeX = "create-sizeX";
	public static final String uiCreateSizeY = "create-sizeY";
	public static final String uiCreateStartTerrain = "create-start-terrain";
	
	public static final String uiLabelCreate = "label-create";
	public static final String uiLabelTitle = "label-title-field";
	public static final String uiLabelFile = "label-file-field";
	public static final String uiLabelNote = "label-title-note";
	public static final String uiLabelSizeX = "label-title-sizex";
	public static final String uiLabelSizeY = "label-title-sizey";
	public static final String uiLabelStartTerrain = "label-title-startterrain";
	
	public boolean locationCreateVisible;
	
	public Label labelCreate;
	public Label labelTitle;
	public Label labelFile;
	public Label labelNote;
	public Label labelSizeX;
	public Label labelSizeY;
	public Label labelStartTerrain;
	
	public Image  createLocationBackground;
	public Button createLocationCancel;
	public Button createLocationConfirm;
	
	public TextField createLocationTitle;
	public TextField createLocationFile;
	public TextField createLocationNote;
	public TextField createLocationSizeX;
	public TextField createLocationSizeY;
	public TextField createLocationStartTerrain;
	
	public WindowEditorLocationCreate(String title, UIGame ui, int layer, SceneGame scene) {
		super(title, ui, Alignment.CENTER, 280, 200, 0, 0, layer);
		this.setTexNormal(Resources.getTex(Tex.uiListLine));
		this.uigame = ui;
		this.closeButton(true);
		
		loadWidgets(scene);
	}

	private void loadWidgets(SceneGame scene) {
		labelCreate = new Label(uiLabelCreate, "Create Location");
		labelCreate.setSize(128, 32);
		labelCreate.setPosition(Alignment.CENTER, 0, 85);
		this.add(labelCreate);
		
		labelTitle = new Label(uiLabelTitle, "Title");
		labelTitle.setSize(35, 32);
		labelTitle.setPosition(Alignment.CENTER, -120, 65);
		this.add(labelTitle);
		
		labelFile = new Label(uiLabelFile, "File");
		labelFile.setSize(35, 32);
		labelFile.setPosition(Alignment.CENTER, -123, 45);
		this.add(labelFile);
		
		labelNote = new Label(uiLabelNote, "Note");
		labelNote.setSize(35, 32);
		labelNote.setPosition(Alignment.CENTER, -120, 25);
		this.add(labelNote);
		
		labelSizeX = new Label(uiLabelSizeX, "Size X");
		labelSizeX.setSize(45, 32);
		labelSizeX.setPosition(Alignment.CENTER, -115, 5);
		this.add(labelSizeX);
		
		labelSizeY = new Label(uiLabelSizeY, "Size Y");
		labelSizeY.setSize(45, 32);
		labelSizeY.setPosition(Alignment.CENTER, 0, 5);
		this.add(labelSizeY);
		
		labelStartTerrain = new Label(uiLabelStartTerrain, "Terrain");
		labelStartTerrain.setSize(50, 32);
		labelStartTerrain.setPosition(Alignment.CENTER, -112, -15);
		this.add(labelStartTerrain);
		
		createLocationTitle = new TextField(uiCreateTitle);
		createLocationTitle.maxTextLength = 30;
		createLocationTitle.setSize(230, 16);
		createLocationTitle.setPosition(Alignment.CENTER, 15, 65);
		this.add(createLocationTitle);
		
		createLocationFile = new TextField(uiCreateFile);
		createLocationFile.maxTextLength = 30;
		createLocationFile.setSize(230, 16);
		createLocationFile.setPosition(Alignment.CENTER, 15, 45);
		this.add(createLocationFile);
		
		createLocationNote = new TextField(uiCreateNote);
		createLocationNote.maxTextLength = 30;
		createLocationNote.setSize(230, 16);
		createLocationNote.setPosition(Alignment.CENTER, 15, 25);
		this.add(createLocationNote);
		
		createLocationSizeX = new TextField(uiCreateSizeX);
		createLocationSizeX.maxTextLength = 4;
		createLocationSizeX.setSize(60, 16);
		createLocationSizeX.setPosition(Alignment.CENTER, -55, 5);
		createLocationSizeX.setTextFilter(new TextFilterNumbers(true));
		this.add(createLocationSizeX);
		
		createLocationSizeY = new TextField(uiCreateSizeY);
		createLocationSizeY.maxTextLength = 4;
		createLocationSizeY.setSize(60, 16);
		createLocationSizeY.setPosition(Alignment.CENTER, 60, 5);
		createLocationSizeY.setTextFilter(new TextFilterNumbers(true));
		this.add(createLocationSizeY);
		
		createLocationStartTerrain = new TextField(uiCreateStartTerrain);
		createLocationStartTerrain.maxTextLength = 3;
		createLocationStartTerrain.setSize(60, 16);
		createLocationStartTerrain.setPosition(Alignment.CENTER, -55, -15);
		createLocationStartTerrain.setTextFilter(new TextFilterNumbers(true));
		this.add(createLocationStartTerrain);
				
		createLocationCancel = new Button(uiCreateCancel, "Cancel");
		createLocationCancel.setSize(128, 32);
		createLocationCancel.setPosition(Alignment.CENTER, -70, -80);
		createLocationCancel.setScript(new ui_LocationAddMenuCancel(uigame));
		this.add(createLocationCancel);
		
		createLocationConfirm = new Button(uiCreateConfirm, "Confirm");
		createLocationConfirm.setSize(128, 32);
		createLocationConfirm.setPosition(Alignment.CENTER, 70, -80);
		createLocationConfirm.setScript(new ui_LocationAdd(uigame, scene));
		this.add(createLocationConfirm);
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
