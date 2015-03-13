package game.cycle.scene.ui.textfilters;

public class TextFilterNumbers implements TextFilter {

	@Override
	public boolean check(char ch) {
		if(ch >= 48 && ch <= 57){
			return true;
		}
		else{
			return false;
		}
	}
}
