package game.cycle.scene.ui.textfilters;


public class TextFilterDefault implements TextFilter {

	@Override
	public boolean check(char ch) {
		if(ch >= 32 && ch <= 126){
			return true;
		}
		if(ch >= 1040 && ch <= 1105){
			return true;
		}
		if(ch == 1025){
			return true;
		}
		
		return false;
	}
}
