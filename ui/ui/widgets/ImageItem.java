package ui.widgets;

import ui.widgets.used.Image;

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
