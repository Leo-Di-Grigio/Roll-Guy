package game.tools;

public class Tools {
	
	public static boolean checkCharacter(char ch){
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
