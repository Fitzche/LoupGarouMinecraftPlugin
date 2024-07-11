package fr.fitzche.lgmore.Util;

import java.util.ArrayList;
import java.util.Collection;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Skull;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.scheduler.BukkitRunnable;

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
		double FinalGive = (give/100) * giver.player.getMaxHealth();
		if (!receiver.inLife) {
			giver.sendMessage("Le joueur ciblé est disparu");
			return;
		} else if (giver.player.getHealth() < FinalGive) {
			giver.sendMessage("Vous n'avez pas assez de vie pour faire ce don");
			return;
		} else if ((receiver.player.getMaxHealth() - receiver.player.getHealth()) < FinalGive) {
			System.out.println("le don est de "+ FinalGive+ " alors qu'il manque " +(receiver.player.getMaxHealth() - receiver.player.getHealth()));
			giver.sendMessage("La personne à qui vous envoyez de la vie ne manque pas d'autant de vie");
			return;
		} else {
			giver.player.damage(FinalGive);
			receiver.player.setHealth(receiver.player.getHealth() + FinalGive);
			giver.sendMessage("Vous avez envoyé "+ give + "% de votre vie à "+ receiver.Name);
		}
		
	}
	
	
}
