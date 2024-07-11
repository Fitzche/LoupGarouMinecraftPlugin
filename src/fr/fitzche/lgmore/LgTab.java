package fr.fitzche.lgmore;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import fr.fitzche.lgmore.Util.MyStringUtil;

public class LgTab implements TabCompleter {
	private static final ArrayList<String> arg1 = new ArrayList<String>();
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String msg, String[] args) {
		// TODO Auto-generated method stub
		ArrayList<String> completions = new ArrayList<>();
		ArrayList<String> completions1 = new ArrayList<String>();
		ArrayList<String> completions2 = new ArrayList<String>();
		ArrayList<String> completions3 = new ArrayList<String>();
		ArrayList<String> completions4 = new ArrayList<String>(); 
		if (args.length == 1) {
			completions1.add("Game");
			completions = completions1;

		}
		if (args.length == 2) {
			completions2.add("start");
			completions2.add("create");
			completions2.add("addPlayer");
			completions2.add("create");
			completions2.add("listPlayer");
			completions2.add("delete");
			completions2.add("addRole");
			completions2.add("removeRole");
			completions2.add("listRole");
			completions2.add("compo");
			completions2.add("addTime");
			completions = completions2;
		}
		if (args.length == 3) { 
			
			ArrayList<String> gamesToAdd = new ArrayList<String>();
			for (GameLg game: Main.games) {
				gamesToAdd.add(game.name);
			}
			completions3.addAll(gamesToAdd);
			completions = completions3;
		}
		if (args.length ==4) {
			
				
					ArrayList<Player> players = new ArrayList<Player>();
					players.addAll(Bukkit.getOnlinePlayers());
					for (Player player: players ) {
						completions4.add(player.getDisplayName());
					completions4.add("SIMPLE_VILLAGER");
					completions4.add("SIMPLE_WOLF");
					completions4.add("VOYANTE");
					completions = completions4;
			}
		}
		
		return completions;
	}

}
