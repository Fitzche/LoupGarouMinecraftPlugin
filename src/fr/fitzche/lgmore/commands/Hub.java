package fr.fitzche.lgmore.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.command.ServerCommandSender;

import fr.fitzche.lgmore.GameStatut;
import fr.fitzche.lgmore.Main;
import fr.fitzche.lgmore.PlayerData;
import fr.fitzche.lgmore.Util.PlayerUtil;
import net.minecraft.server.v1_8_R3.CommandDispatcher;
import net.minecraft.server.v1_8_R3.ICommandDispatcher;

public class Hub implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
	
		
		PlayerData p = Main.getData(sender);
		if (p == null) {
			return false;
		}
		
		if (p.starParty != null) {
			p.starParty.death(p);
		}
		
		if (p.game != null) {
			if (p.player.getLocation().getWorld().equals(p.game.getWorld())) {
				p.rejoinLoc = p.player.getLocation();
			}
		}
		
		if (p.bedGame != null && p.bedGame.getWorld().equals(p.getLocation().getWorld()) ) {
			p.rejoinLoc = p.getLocation();
		}
		
		p.player.teleport(Main.world.getSpawnLocation());
		PlayerUtil.survival(p.player);
		
		return false;
	}

}
