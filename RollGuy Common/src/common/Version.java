package common;

public class Version {

	public static final String TITLE = "RollGuy";
	
	public static final int MAJOR = 0;
	public static final int MINOR = 1;
	public static final int REVISION = 1;
	
	private static final String TITLE_FRAME = TITLE + " v" + MAJOR + "." + MINOR + "." + REVISION;
	
	public static String getTitle() {
		return TITLE_FRAME;
	}
}
