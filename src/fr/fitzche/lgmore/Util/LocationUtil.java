package fr.fitzche.lgmore.Util;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import fr.fitzche.lgmore.Main;
import fr.fitzche.lgmore.PlayerData;
import fr.fitzche.lgmore.Lg.GameLg;

import org.bukkit.Material;
import org.bukkit.World;

public class LocationUtil {
	public static double getDistanceBetween(PlayerData one, PlayerData Two) {
		if (!one.getLocation().getWorld().equals(Two.getLocation().getWorld())) {
			return 10000;
		}
		return one.getLocation().distance(Two.getLocation());
	}
	
	
	public static void tpAl(PlayerData player, int i) {
		if (!player.isOnline) {
			System.out.println("can't tp alea offline player: "+player.getName());
		}
		if (i < 0) {
			i *= -1;
		}
		if (i < 10) {
			i = 10;
		}
		player.addPotionEffect(PotionUtil.INVINCIBILITY);
		Location loc = new Location(player.player.getLocation().getWorld(), MathUtil.generateAlInt(-i, i), 200, MathUtil.generateAlInt(-i, i));
		player.player.teleport(loc);
	}
	public static double getDistanceBetween(Player one, Player Two) {
		if (!one.getLocation().getWorld().equals(Two.getLocation().getWorld())) {
			return 10000;
		}
		return one.getLocation().distance(Two.getLocation());
	}
	public static double getDistanceBetween(Player one, Location Two) {
		if (!one.getLocation().getWorld().equals(Two.getWorld())) {
			return 10000;
		}
		return one.getLocation().distance(Two);
	}
	
	public static String toString(Location loc) {
		return String.valueOf(loc.getBlockX())+"; " + String.valueOf(loc.getBlockY())+"; " +String.valueOf(loc.getBlockZ());
	}
	

	public static Location getAlLocAroundFarfrom(int r, int tr, boolean bol, World world, GameLg game) {
		if (tr > 10) {
			return null;
		}
		int x = MathUtil.generateAlInt(-r, r);
		int z = MathUtil.generateAlInt(-r, r);
		
		boolean empty = true;
		int limit = 100;
		
		do {
			
			
			if (!new Location(world, x, limit, z).getBlock().getType().equals(Material.AIR) && !new Location(world, x, limit, z).getBlock().getType().equals(Material.LEAVES) && !new Location(world, x, limit, z).getBlock().getType().equals(Material.LEAVES_2) && !new Location(world, x, limit, z).getBlock().getType().equals(Material.WOOD)) {
				empty = false;
			}
			limit --;
		} while (empty && limit >49);
		if (limit < 50) {
			return getAlLocAroundFarfrom(r, tr+1, false, world, game);
		} else {
			Location loc = new Location(world, x, limit, z);
			boolean retry = false;
			for (Location locT: game.locsBat) {
				if ((new Location(loc.getWorld(), loc.getX(), 0, loc.getZ()).distance(new Location(loc.getWorld(), locT.getX(), 0, locT.getZ()))) <20) {
					retry = true;
				}
			}
			if (retry) {
				return getAlLocAroundFarfrom(r, tr+1, false, world, game);
			}
			System.out.println("loc = "+ loc.getBlockX()+ "; "+loc.getBlockY()+"; "+loc.getBlockZ());
			loc.setWorld(world);
			return loc;
		}
	}
	
	public static ArrayList<PlayerData> getClassByDistance( Location loc, String CheckWorld, GameLg game) {
		ArrayList<PlayerData> gameP = game.getPlayerAlive();
		ArrayList<PlayerData> players = new ArrayList<PlayerData>();
		
		
		for (PlayerData p:game.getPlayerAlive()) {
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
					if (index == players.size() -1 && !added) {
						players.add(index, p);
						added = true;
					}
					index++;
					
				} while (index < players.size() && !added);
			}
		}
		
		return players;
	}
	
}
