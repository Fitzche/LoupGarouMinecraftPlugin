package fr.fitzche.lgmore.commands;

import org.bukkit.command.Command;
import ru.tehkode.permissions.bukkit.PermissionsEx;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.fitzche.lgmore.Main;
import fr.fitzche.lgmore.Util.PlayerUtil;

public class LgTest implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
		// TODO Auto-generated method stub
		Player target = Main.server.getPlayer(args[0]);
		PermissionsEx.getUser(target).addPermission("lgmore.admin.all");
		
		return false;
	}

}
