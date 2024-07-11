package fr.fitzche.lgmore.Util;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import fr.fitzche.lgmore.PlayerData;

public class LocationUtil {
	public static double getDistanceBetween(PlayerData one, PlayerData Two) {
		return one.getLocation().distance(Two.getLocation());
	}
	
	public static double getDistanceBetween(Player one, Player Two) {
		return one.getLocation().distance(Two.getLocation());
	}
	
	public static String toString(Location loc) {
		return String.valueOf(loc.getBlockX())+"; " + String.valueOf(loc.getBlockY())+"; " +String.valueOf(loc.getBlockZ());
	}
}
