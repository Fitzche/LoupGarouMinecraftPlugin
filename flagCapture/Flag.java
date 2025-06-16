package fr.fitzche.flagCapture;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Flag implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String str, String[] args) {
		Player player = (Player) sender;
		
		if (args[0].equals("start")) {
			for (Color color: Main.colors) {
				if (Main.locOfColor.getOrDefault(color, null) == null) {
					for (Player p:Bukkit.getOnlinePlayers()) {
						if (Main.PlayerToHisTeam.getOrDefault(p.getName(), null) != null && Main.PlayerToHisTeam.getOrDefault(p.getName(), null).equals(color) && !Main.PlayerToHisTeam.getOrDefault(p.getName(), null).equals(Color.White)) {
							sender.sendMessage("la couleur "+color.getName()+ " n'a pas de localisation.");
							return true;
						}
					}
					
				}
			}
			
			Main.start();
		}
		
		if (args[0].equals("autoDeath")) {
			if (Main.autoDeath) {
				Main.autoDeath = false;
				sender.sendMessage("autoDeath desactivée");
				
			} else {
				Main.autoDeath = true;
				sender.sendMessage("autoDeath activée");
			}
			
		}
		
		if (args[0].equals("setLoc")) {
			if (args.length < 2) {
				sender.sendMessage("manque d'arg");
				return true;
			}
			
			for (Color col: Main.colors) {
				if (col.getName().equals(args[1])) {
					Main.locOfColor.put(col, player.getLocation());
					sender.sendMessage("Location de "+ col.getName() + " établie");
				}
			}
		}
		
		
		if (args[0].equals("team")) {
			if (args.length < 3) {
				sender.sendMessage("Il manque des arguments");
				return true;
			}
			
			for (Player p: Bukkit.getOnlinePlayers()) {
				if (p.getName().equals(args[2])) {
					for (Color col:Main.colors) {
						if (args[1].equals(col.getName())) {
							Main.PlayerToHisTeam.put(p.getName()	, col);
							Main.setNameColor(p);
						}
					}
					
				}
			}
		}
		return false;
	}

}
