package fr.fitzche.lgmore.clocktower;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import fr.fitzche.lgmore.Main;
import fr.fitzche.lgmore.PlayerData;

public class ClockCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
		PlayerData player = Main.getData(sender);
		if (player == null) {
			return false;
		}
		switch (args[0]) {
		case "play":
			if (!player.isFree()) {
				player.sendMessage("Vous ne pouvez pas rejoindre une partie car vous êtes déjà en jeu, faites /ig pour connaitre vos parties en cours (non disponible)");
			} else {
				if (Main.clocks == null || Main.clocks.size() < 1) {
					
					player.sendMessage("Aucune partie n'est libre, veuillez attendre quelques secondes en attendant la création d'une partie");
				} else {
					for (ClockTower tower:Main.clocks) {
						if (tower.getActualNbOfPlayer() < 14 && !tower.started) {
							tower.addPlayer(player);
							return true;
						}
					}
					player.sendMessage("Aucune partie n'est libre, veuillez attendre quelques secondes en attendant la création d'une partie");

				}
			}
			break;
		}
		return false;
	}

}
