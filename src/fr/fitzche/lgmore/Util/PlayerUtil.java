package fr.fitzche.lgmore.Util;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Skull;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.scheduler.BukkitRunnable;

import de.inventivegames.particle.ParticleEffect;
import fr.fitzche.lgmore.Main;
import fr.fitzche.lgmore.PlayerData;

public class PlayerUtil {
	public static Inventory playersInv;
	
	
	public static Player getPlayer(String name) {
		Collection<? extends Player> players = new ArrayList<Player>();
		players = Bukkit.getOnlinePlayers();
		for (Player player: players) {
			if (player.getName().equals(name)) {
				return player;
			}
		}
	
		return null;
	}
	
	public static PlayerData getDataPlayer(String name,  String spec) {
		return PlayerUtil.getDataOfPlayer(PlayerUtil.getPlayer(name), "at getDataPlayer in PlayerUtil ");
	}
	
	public static PlayerData getDataOfPlayer(Player ply, String spec) {
		return GameLgUtil.getGameOfPlayer(ply, " at 36 PlayerUtil " + spec).getPlayer(ply.getName());
	}
	
	public static PlayerData checkExist(String Name) {
		if (PlayerUtil.getDataPlayer(Name, "in checkExist of PlayerUtil") !=null) {
			return PlayerUtil.getDataPlayer(Name, "in checkExist of PlayerUtil");
		}
		
		return null;
		
	}
	public static void don(PlayerData giver, PlayerData receiver, double give) {
		double FinalGive = (give/100) * giver.getMaxHealth();
		if (!receiver.inLife) {
			giver.sendMessage("Le joueur ciblé est disparu");
			return;
		} else if (giver.getHealth() < FinalGive) {
			giver.sendMessage("Vous n'avez pas assez de vie pour faire ce don");
			return;
		} else if ((receiver.getMaxHealth() - receiver.getHealth()) < FinalGive) {
			System.out.println("le don est de "+ FinalGive+ " alors qu'il manque " +(receiver.getMaxHealth() - receiver.getHealth()));
			giver.sendMessage("La personne à qui vous envoyez de la vie ne manque pas d'autant de vie");
			return;
		} else {
			giver.player.damage(FinalGive);
			receiver.setHealth(receiver.getHealth() + FinalGive);
			giver.sendMessage("Vous avez envoyé "+ give + "% de votre vie à "+ receiver.Name);
		}
		
	}
	public static void particle(Location loc, org.bukkit.Color color) {
		try {
			for (int i = 0; i < 10; i++) { // Générer plus de particules en augmentant le nombre de répétitions
                
				
				ParticleEffect.RED_DUST.sendColor(Main.server.getOnlinePlayers(), new Location(Main.world, loc.getX() + MathUtil.betweenNegOneAndOne() , loc.getY()+1+ MathUtil.betweenNegOneAndOne(), loc.getZ()+ MathUtil.betweenNegOneAndOne()), color, true);
				
            }
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void particleFor(Player watcher, Location loc, org.bukkit.Color color) {
		try {
			for (int i = 0; i < 10; i++) { // Générer plus de particules en augmentant le nombre de répétitions
                
				
				ParticleEffect.RED_DUST.sendColor(watcher, new Location(Main.world, loc.getX() + MathUtil.betweenNegOneAndOne() , loc.getY()+1+ MathUtil.betweenNegOneAndOne(), loc.getZ()+ MathUtil.betweenNegOneAndOne()), color, true);
            }
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	
}
