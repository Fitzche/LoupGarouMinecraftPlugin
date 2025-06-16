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
			for (Color color: Main2.colors) {
				if (Main2.locOfColor.getOrDefault(color, null) == null) {
					for (Player p:Bukkit.getOnlinePlayers()) {
						if (Main2.PlayerToHisTeam.getOrDefault(p.getName(), null) != null && Main2.PlayerToHisTeam.getOrDefault(p.getName(), null).equals(color) && !Main2.PlayerToHisTeam.getOrDefault(p.getName(), null).equals(Color.White)) {
							sender.sendMessage("la couleur "+color.getName()+ " n'a pas de localisation.");
							return true;
						}
					}
					
				}
			}
			
			Main2.start();
		}
		
		if (args[0].equals("autoDeath")) {
			if (Main2.autoDeath) {
				Main2.autoDeath = false;
				sender.sendMessage("autoDeath desactivée");
				
			} else {
				Main2.autoDeath = true;
				sender.sendMessage("autoDeath activée");
			}
			
		}
		
		if (args[0].equals("setLoc")) {
			if (args.length < 2) {
				sender.sendMessage("manque d'arg");
				return true;
			}
			
			for (Color col: Main2.colors) {
				if (col.getName().equals(args[1])) {
					Main2.locOfColor.put(col, player.getLocation());
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
				if (p.getName().equals(args[1])) {
					for (Color col:Main2.colors) {
						if (args[2].equals(col.getName())) {
							Main2.PlayerToHisTeam.put(p.getName()	, col);
							
							System.out.println(p.getName() + " added to "+ col.getName() + " result is "+ Main2.PlayerToHisTeam.getOrDefault(p.getName(), Color.White).getName());
							Main2.setNameColor(p);
						}
					}
					
				}
			}
		}
		return false;
	}

}
