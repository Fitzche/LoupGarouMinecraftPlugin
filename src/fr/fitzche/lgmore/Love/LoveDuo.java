package fr.fitzche.lgmore.Love;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.scheduler.BukkitRunnable;

import fr.fitzche.lgmore.Main;
import fr.fitzche.lgmore.PlayerData;
import fr.fitzche.lgmore.RolesLg.Camp;
import fr.fitzche.lgmore.Util.GameLgUtil;
import net.md_5.bungee.api.ChatColor;

public class LoveDuo implements Listener{
	public PlayerData loverOne;
	public PlayerData loverTwo;
	public PlayerData Cupidon;
	
	public LoveDuo(PlayerData loverOne, PlayerData loverTwo, PlayerData Cupidon) {
		
		
		Main.server.getPluginManager().registerEvents(this, Main.plug);		
		this.loverOne = loverOne;
		loverOne.player.sendMessage(ChatColor.AQUA+"Vous venez de tomber amoureux de "+ loverTwo.player.getName() + "\n" + "Vous devez maintenant gagner avec votre amoureux (et votre cupidon), si l'un de vous deux vient à mourir, l'autre mourra aussi"
				+ " Vous possédez maintenant la commande /don [pourcentage de votre vie] pour envoyer des coeurs à votre âme soeur");
		loverOne.inLove = true;
		loverOne.camp = Camp.Love;
		
		this.loverTwo = loverTwo;
		loverTwo.player.sendMessage(ChatColor.AQUA+"Vous venez de tomber amoureux de "+ loverOne.player.getName() + "\n" + "Vous devez maintenant gagner avec votre amoureux (et votre cupidon), si l'un de vous deux vient à mourir, l'autre mourra aussi"
				+ " Vous possédez maintenant la commande /don [pourcentage de votre vie] pour envoyer des coeurs à votre âme soeur");
		loverTwo.inLove = true;
		loverTwo.camp = Camp.Love;
		
		this.Cupidon = Cupidon;
	}
	
	public PlayerData getOtherLover(Player player) {
		if (player == loverOne) {
			return this.loverTwo;
		} else if (player == loverTwo) {
			return this.loverOne;
		}
		return null;
	}
	
	public PlayerData getCupidon() {
		return this.Cupidon;
	}
	
	
	public void don(PlayerData giver, double give) {
		System.out.println(String.valueOf(give/100));
		System.out.println(giver.player.getHealth());

		double transfer = giver.player.getHealth() *(give/100);
		if (giver.equals(this.loverOne)|| giver.equals(this.loverTwo) ) {
			PlayerData receiver =null;
			if (giver.equals(this.loverOne) ) {
				receiver = this.loverTwo;
			} else if (giver.equals(this.loverTwo)) {
				receiver = this.loverOne;
			}
			if (receiver.player.getMaxHealth()-receiver.player.getHealth() >= transfer) {
				if (giver.player.getHealth() <= transfer) {
					giver.player.sendMessage(ChatColor.DARK_RED+"Vous etes trop affaiblis pour effectuer ce don à votre âme soeur");

					return;
				}
				if (transfer<1) {
					giver.player.sendMessage(ChatColor.DARK_RED+"vous avez donné " + String.valueOf(give) + "% de votre vie à votre âme soeur");
					receiver.player.sendMessage(ChatColor.DARK_RED+"vous avez reçu " + String.valueOf(give) + "% de la vie de votre âme soeur");
					return;
				}
				giver.player.damage(transfer);
				receiver.player.setHealth(receiver.player.getHealth() + transfer);
				giver.player.sendMessage(ChatColor.DARK_RED+"vous avez donné " + String.valueOf(give) + "% de votre vie à votre âme soeur");
				receiver.player.sendMessage(ChatColor.DARK_RED+"vous avez reçu " + String.valueOf(give) + "% de la vie de votre âme soeur");
				
			} else {
				giver.player.sendMessage(ChatColor.DARK_RED+"Votre âme soeur ne manque pas d'autant de vie");
				return;
			}
		}
		
		
	}
	
	@Deprecated
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent e) {
		
		PlayerData TwoD = this.loverTwo;
		Player Two = TwoD.player;
		PlayerData OneD = this.loverOne;
		Player One = OneD.player;
		
		
		
		if (e.getEntity().getPlayer().equals(this.loverTwo.player)) {
			One.sendMessage("Votre Âme Soeur est morte, vous vous appretez à la rejoindre dans sa tombe");
			Bukkit.getScheduler().runTaskLater(Main.plug, new BukkitRunnable() {

				@Override
				public void run() {
					
					if (TwoD.inLife) {
						One.sendMessage("Votre couple a été miraculeusement sauvé, peut etre infecté...");
					} else {
						OneD.instantDeath = true;
						loverTwo = null;
						One.damage(10000);
						loverOne = null;
						
					}
					
					
				}
				
				
				
			}, 302);
		}
		
		if (e.getEntity().getPlayer().equals(this.loverOne.player)) {
			Two.sendMessage("Votre Âme Soeur est morte, vous vous appretez à la rejoindre dans sa tombe");
			Bukkit.getScheduler().runTaskLater(Main.plug, new BukkitRunnable() {

				@Override
				public void run() {
					
					if (OneD.inLife) {
						Two.sendMessage("Votre couple a été miraculeusement sauvé, peut etre infecté...");
					} else {
						
						
						TwoD.instantDeath = true;
						loverOne = null;
						Two.damage(10000);
						
						loverTwo = null;
					}
					
					
				}
				
				
				
			}, 302);
		}
		
		
		
		
	}
	
	
}
