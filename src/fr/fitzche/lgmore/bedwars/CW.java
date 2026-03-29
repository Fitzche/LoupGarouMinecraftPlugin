package fr.fitzche.lgmore.bedwars;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import fr.fitzche.lgmore.Main;
import fr.fitzche.lgmore.Minage.TradeInv;
import fr.fitzche.lgmore.Minage.Trades;

public class CW implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
		switch (args[0]) {
		case "startForceCW":
			((Bedwars) Main.getData(sender).game).startForce();
			break;
		case "spawnBasicTrader":
			TradeInv inv = new TradeInv(Trades.basicMap, Main.getData(sender));
			break;
		case "map":
			Main.getData(sender).player.teleport(((Bedwars)Main.getData(sender).game).getWorld().getSpawnLocation());
			break;
		}
		return false;
	}

}
