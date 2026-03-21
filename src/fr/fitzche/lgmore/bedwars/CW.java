package fr.fitzche.lgmore.bedwars;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import fr.fitzche.lgmore.Main;

public class CW implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
		switch (args[0]) {
		case "startForceCW":
			((Bedwars) Main.getData(sender).game).startForce();
			break;
		case "spawnBasicTrader":
			((Bedwars)Main.getData(sender).game).trader(Main.getData(sender).getLocation(), BedLocType.BaseTrader);
			break;
		case "map":
			Main.getData(sender).player.teleport(((Bedwars)Main.getData(sender).game).getWorld().getSpawnLocation());
			break;
		}
		return false;
	}

}
