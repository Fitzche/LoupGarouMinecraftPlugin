package fr.fitzche.flagCapture;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;



public class FlagTab implements TabCompleter {

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String msg, String[] args) {
		// TODO Auto-generated method stub
		ArrayList<String> completions = new ArrayList<>();
		if ((args[0].equals("team") && args.length == 3) || (args[0].equals("setLoc") && args.length == 2)) {
			completions.addAll(Arrays.asList("Bleu", "Rouge", "Jaune", "Vert", "Noir"));
		}
		
		
		return completions;
	}

}
