package game.cycle.scene.game.world.dialog;

import org.apache.commons.lang3.text.WordUtils;

public class DialogBlock {

	private static final int wrapCharactersCount = 57;
	private static final String wraper = "\n";
	
	public DialogProto dialog;
	public String [] beginText;
	public String [] endText;
	
	public DialogBlock(DialogProto dialog) {
		this.dialog = dialog;
		
		String begin = "\n- ...";
		if(dialog.textBegin != null){
			begin = "\n- " + dialog.textBegin;
		}
		
		beginText = WordUtils.wrap(begin, wrapCharactersCount, wraper, true).split(wraper);
		
		
		String end = "- ...";
		if(dialog.textEnd != null){
			end = "- " + dialog.textEnd;
		}
		
		endText = WordUtils.wrap(end, wrapCharactersCount, wraper, true).split(wraper);
	}
}