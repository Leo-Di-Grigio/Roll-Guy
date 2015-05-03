package game.cycle.scene.ui.textfilters;

public class TextFilterNumbers implements TextFilter {

	private boolean positiveOnly;
	
	public TextFilterNumbers(boolean positiveOnly) {
		this.positiveOnly = positiveOnly;
	}
	
	@Override
	public boolean check(char ch) {
		if(positiveOnly){
			if(ch >= 48 && ch <= 57){
				return true;
			}
			else{
				return false;
			}
		}
		else{
			if((ch >= 48 && ch <= 57) || ch == 45){
				return true;
			}
			else{
				return false;
			}
		}
	}
}
