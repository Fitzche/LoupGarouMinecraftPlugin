package fr.fitzche.lgmore.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import fr.fitzche.lgmore.Main;
import fr.fitzche.lgmore.PlayerData;

public class Rejoin implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
		PlayerData p = Main.getData(sender);
		if (p == null || p.rejoinLoc == null) {
			return false;
		}
		
		
		try {
			p.player.teleport(p.rejoinLoc);
			p.player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 100, 100));
			p.player.getInventory().clear();
			p.player.getInventory().setContents(p.leftInv.getContents());
		} catch (Exception e) {
			p.player.sendMessage("La téléportation a échouée");
			System.out.println("CODE//ERROR");
			e.printStackTrace();
		}
		
		return false;
	}

}
