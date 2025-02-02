package fr.fitzche.lgmore.Util;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import fr.fitzche.lgmore.Main;
import fr.fitzche.lgmore.PlayerData;
import org.bukkit.Material;

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
	
	public static Location getAlLocAround(int r, int tr) {
		if (tr > 10) {
			return null;
		}
		int x = MathUtil.generateAlInt(-r, r);
		int z = MathUtil.generateAlInt(-r, r);
		
		boolean empty = true;
		int limit = 100;
		
		do {
			
			
			if (new Location(Main.server.getWorld("world"), x, limit, z).getBlock().getType().equals(Material.AIR)) {
				empty = false;
			}
			limit --;
		} while (empty && limit >49);
		if (limit < 50) {
			return getAlLocAround(r, tr+1);
		} else {
			Location loc = new Location(Main.server.getWorld("world"), x, limit, z);
			Bukkit.broadcastMessage(LocationUtil.toString(loc));
			return loc;
		}
	}
	
	public static ArrayList<PlayerData> getClassByDistance( Location loc) {
		ArrayList<PlayerData> gameP = Main.game.getPlayerAlive();
		ArrayList<PlayerData> players = new ArrayList<PlayerData>();
		
		
		for (PlayerData p:Main.game.getPlayerAlive()) {
			if (players.size() < 1) {
				players.add(p);
			} else {
				boolean added = false;
				int index = 0;
				do {
					if (gameP.get(index).getLocation().distance(loc) < p.getLocation().distance(loc)) {
						players.add(index, p);
						added = true;
					}
					if (index == players.size() -1) {
						players.add(index, p);
						added = true;
					}
					
				} while (index < players.size() && !added);
			}
		}
		
		return players;
	}
	
}
