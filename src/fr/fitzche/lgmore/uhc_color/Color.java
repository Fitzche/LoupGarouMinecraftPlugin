package fr.fitzche.lgmore.uhc_color;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.fitzche.lgmore.uhc_color.PlayerColorboard.Colorboard;
import fr.fitzche.lgmore.uhc_color.Gui.PlayerGui;
import fr.fitzche.lgmore.uhc_color.Gui.*;

public class Color implements CommandExecutor {

	@Deprecated
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
		if (args.length==0) {
			Player player = (Player) sender;
			PlayerGui gui = new PlayerGui(MainColor.players, player);
			gui.open();
			return true;
		}
		if (MainColor.getPlayer(args[0]) == null) {
			
			System.out.println("joueur null");
			return true;
		} 
		
		Colorboard board;
		
		Player player = (Player) sender;
		if (MainColor.getPlayerBoard(player)==null) {
			board = new Colorboard(player);
			MainColor.colors.add(board);
		} else {
			board = MainColor.getPlayerBoard(player);
		}
		
		chooseColorGui gui = new chooseColorGui(board, MainColor.getPlayer(args[0]));
		player.openInventory(gui.inv);
		
		
		return false;
	}

}
