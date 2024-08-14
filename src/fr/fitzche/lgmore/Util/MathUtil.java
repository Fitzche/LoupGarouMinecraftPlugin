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
	public static boolean isBeetween_inclus(int min, int max, int number) {
		return (number<max+1 && number >min-1);
	}
	
	public static int probasFivePossib(int a, int b, int c, int d, int e ) {
		if (a+b+c+d+e != 100) {
			System.out.println("erreur de paramètres");
			return 0;
		} else {
			int x = MathUtil.generateAlInt(0, 100);
			if (isBeetween_inclus(0, a, x)) {
				return 1;
			} else if (isBeetween_inclus(a, a+b, x)) {
				return 2;
			} else if (isBeetween_inclus(a+b, a+b+c	, x)) {
				return 3;
			} else if (isBeetween_inclus(a+b+c, a+b+c+d, x)) {
				return 4;
			} else if (isBeetween_inclus(a+b+c+d, 100, x)) {
				return 5;
			} else {
				System.out.println("erreur de paramètres");
				return 0;
				
			}
		}
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
