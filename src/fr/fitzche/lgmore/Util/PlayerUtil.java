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

	
	public static void survival(Player p) {
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "gamemode "+p.getName() + " survival");
	}
	
	public static void adventure(Player p) {
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "gamemode "+p.getName() + " adventure");
	}
	
	public static void spectator(Player p) {
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "gamemode "+p.getName() + " spectator");
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
	public static void particle(Location loc, org.bukkit.Color color, String checkWorld, double radius) {
		try {
			for (int i = 0; i < 10; i++) { // Générer plus de particules en augmentant le nombre de répétitions
                
				
				ParticleEffect.RED_DUST.sendColor(Main.server.getOnlinePlayers(), new Location(loc.getWorld(), loc.getX() + MathUtil.betweenNegOneAndOne() * radius , loc.getY()+1+ MathUtil.betweenNegOneAndOne() * radius, loc.getZ()+ MathUtil.betweenNegOneAndOne() * radius), color, true);
				
            }
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
    /**
     * emmet des particules de couleur 
     *
     * @param loc localisation d'apparition des particules.
     * @param color couleur des particules.
     * @param checkWorld Avez vous bien vérifié le monde ?
     * @param radius rayon de particules.
     * @param force nombre de particules emises
     */
	public static void particle(Location loc, org.bukkit.Color color, String checkWorld, double radius, int force) {
		try {
			for (int i = 0; i < force; i++) { // Générer plus de particules en augmentant le nombre de répétitions
                
				
				ParticleEffect.RED_DUST.sendColor(Main.server.getOnlinePlayers(), new Location(loc.getWorld(), loc.getX() + MathUtil.betweenNegOneAndOne() * radius , loc.getY()+1+ MathUtil.betweenNegOneAndOne() * radius, loc.getZ()+ MathUtil.betweenNegOneAndOne() * radius), color, true);
				
            }
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void particleFor(Player watcher, Location loc, org.bukkit.Color color, double radius) {
		try {
			for (int i = 0; i < 10; i++) { // Générer plus de particules en augmentant le nombre de répétitions
                
				
				ParticleEffect.RED_DUST.sendColor(watcher, new Location(Main.world, loc.getX() + MathUtil.betweenNegOneAndOne() * radius , loc.getY()+1+ MathUtil.betweenNegOneAndOne() * radius, loc.getZ()+ MathUtil.betweenNegOneAndOne() * radius), color, true);
            }
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	
}
