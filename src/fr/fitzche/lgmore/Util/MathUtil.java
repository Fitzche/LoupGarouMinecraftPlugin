package fr.fitzche.lgmore.Util;

import java.util.Random;

import fr.fitzche.lgmore.GameLg;
import fr.fitzche.lgmore.PlayerData;

public class MathUtil {
	public static int generateAlInt(int min, int max) {
		Random r = new Random();
		int x = r.nextInt((max-min+1));
		return x + min;
	}
	
	public static boolean pourcentage(int pourcentage) {
		int x = generateAlInt(0, 100);
		if (x > pourcentage) {
			return false;
		} else {
			return true;
		}
	}
	
	public static PlayerData getAlPlayer(GameLg game, PlayerData withOut) {
		PlayerData player = game.getPlayerAlive().get(MathUtil.generateAlInt(0, game.getPlayerAlive().size() -1));
		
		if (player != withOut) {
			return player;
		} else {
			return MathUtil.getAlPlayer(game, withOut);
		}
	}
	
	public static PlayerData getAlPlayer(GameLg game) {
		PlayerData player = game.getPlayerAlive().get(MathUtil.generateAlInt(0, game.getPlayerAlive().size() -1));
		return player;
		
	}
}
