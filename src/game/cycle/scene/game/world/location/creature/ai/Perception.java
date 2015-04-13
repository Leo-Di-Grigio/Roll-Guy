package game.cycle.scene.game.world.location.creature.ai;

import game.cycle.scene.game.world.database.GameConst;
import game.cycle.scene.game.world.location.Location;
import game.cycle.scene.game.world.location.LocationObject;
import game.cycle.scene.game.world.location.creature.Creature;

public class Perception {

	// Visibility
	public static boolean isVisible(Creature creature, Location loc, LocationObject target){
		int x = target.getPosition().x;
		int y = target.getPosition().y;
		
		if(loc.inBound(x, y)){
			return isVisible(creature, loc.map[x][y].lighting);
		}
		else{
			return false;
		}
	}
	
	public static boolean isVisible(Creature creature, int lighting){
		int race = creature.proto.race;
		int perception = creature.proto.stats.perception;
		
		switch(race){
			case GameConst.RACE_HUMAN_MALE:
			case GameConst.RACE_HUMAN_FEM:
				return isVisibleHuman(perception, lighting);
				
			case GameConst.RACE_ELF_MALE:
			case GameConst.RACE_ELF_FEM:
				return isVisibleElf(perception, lighting);
				
			case GameConst.RACE_DWARF_MALE:
			case GameConst.RACE_DWARF_FEM:
				return isVisibleDwarf(perception, lighting);
				
			case GameConst.RACE_ORC_MALE:
			case GameConst.RACE_ORC_FEM:
				return isVisibleOrc(perception, lighting);
				
			case GameConst.RACE_GNOME_MALE:
			case GameConst.RACE_GNOME_FEM:
				return isVisibleGnome(perception, lighting);
				
			default:
				return false;
		}
	}

	private static boolean isVisibleHuman(int perception, int lighting) {
		if(lighting >= 75 - perception*5){
			return true;
		}
		else{
			return false;
		}
	}

	private static boolean isVisibleElf(int perception, int lighting) {
		if(lighting >= 65 - perception*5){
			return true;
		}
		else{
			return false;
		}
	}

	private static boolean isVisibleDwarf(int perception, int lighting) {
		if(lighting >= 75 - perception*5){
			return true;
		}
		else{
			return false;
		}
	}

	private static boolean isVisibleOrc(int perception, int lighting) {
		if(lighting >= 70 - perception*5){
			return true;
		}
		else{
			return false;
		}
	}

	private static boolean isVisibleGnome(int perception, int lighting) {
		if(lighting >= 84 - perception*4){
			return true;
		}
		else{
			return false;
		}
	}
	
	// Sound
	public static boolean isHear(Creature creature, int sound){
		int race = creature.proto.race;
		int perception = creature.proto.stats.perception;
		
		switch(race){
			case GameConst.RACE_HUMAN_MALE:
			case GameConst.RACE_HUMAN_FEM:
				return isHearHuman(perception, sound);
				
			case GameConst.RACE_ELF_MALE:
			case GameConst.RACE_ELF_FEM:
				return isHearElf(perception, sound);
				
			case GameConst.RACE_DWARF_MALE:
			case GameConst.RACE_DWARF_FEM:
				return isHearDwarf(perception, sound);
				
			case GameConst.RACE_ORC_MALE:
			case GameConst.RACE_ORC_FEM:
				return isHearOrc(perception, sound);
				
			case GameConst.RACE_GNOME_MALE:
			case GameConst.RACE_GNOME_FEM:
				return isHearGnome(perception, sound);
				
			default:
				return false;
		}
	}

	private static boolean isHearHuman(int perception, int sound) {
		if(sound >= 75 - perception*5){
			return true;
		}
		else{
			return false;
		}
	}

	private static boolean isHearElf(int perception, int sound) {
		if(sound >= 65 - perception*5){
			return true;
		}
		else{
			return false;
		}
	}

	private static boolean isHearDwarf(int perception, int sound) {
		if(sound >= 60 - perception*5){
			return true;
		}
		else{
			return false;
		}
	}

	private static boolean isHearOrc(int perception, int sound) {
		if(sound >= 70 - perception*5){
			return true;
		}
		else{
			return false;
		}
	}

	private static boolean isHearGnome(int perception, int sound) {
		if(sound >= 48 - perception*4){
			return true;
		}
		else{
			return false;
		}
	}
}
