package fr.fitzche.lgmore.Util;

import org.bukkit.entity.Player;

import fr.fitzche.lgmore.commands.Lg;
import fr.fitzche.lgmore.commands.Lga;

public class CommandUtil {

	@Deprecated
	public static void runCommand(String commandBase, Player sender, String[] args) {
		if (commandBase.equals("lg")) {
			Lg ex = new Lg();
			ex.onCommand(sender, null, null, args);
			
		} else if (commandBase.equals("lga")) {
			Lga ex = new Lga();
			ex.onCommand(sender, null, null, args);
		}
		
		
	}
	
	
}
