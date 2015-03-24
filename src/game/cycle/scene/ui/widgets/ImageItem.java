package game.cycle.scene.ui.widgets;

public class ImageItem extends Image {

	private int guid;

	public ImageItem(String title, int guid) {
		super(title);
		this.guid = guid;
	}

	public int getGuid(){
		return guid;
	}
}
