package fr.fitzche.lgmore.commands;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.command.ServerCommandSender;

import fr.fitzche.lgmore.Game;
import fr.fitzche.lgmore.GameStatut;
import fr.fitzche.lgmore.Main;
import fr.fitzche.lgmore.PlayerData;
import fr.fitzche.lgmore.Lg.GameLg;
import fr.fitzche.lgmore.Lg.GameType;
import fr.fitzche.lgmore.Util.ItemUtil;
import fr.fitzche.lgmore.Util.PlayerUtil;
import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_8_R3.CommandDispatcher;
import net.minecraft.server.v1_8_R3.ICommandDispatcher;

public class Hub implements CommandExecutor {

	
	public static void sendHub(PlayerData p) {
		if (p == null) {
			return;
		}
		
		if (p.starParty != null) {
			p.starParty.death(p);
		}
		
		if (p.game != null ) {
			if (p.game instanceof GameLg) {
				if (((GameLg) p.game).statut.equals(GameStatut.NOT_STARTED)) {
					((GameLg) p.game).removePlayer(p, "at hub command 35");
					p.clearLgGameVar();
					
				}
			}
			if (p.player.getLocation().getWorld().equals(p.game.getWorld())) {
				p.rejoinLoc = p.player.getLocation();
			}
		}
		
		if (p.clockGame != null) {
			if (p.clockGame.started) {
				p.sendMessage("Vous ne pouvez pas quitter une partie en cours");
				return;
			} else {
				p.clockGame.broadcoast(p.getName()+ ChatColor.RED+" quit");
				p.clockGame.players.remove(p);
				p.clearLgGameVar();
				
			}
		}
		
		
		p.player.getInventory().clear();
		p.player.teleport(Main.world.getSpawnLocation());
		p.setDisplayName(p.player.getName());
		PlayerUtil.survival(p.player);
		p.player.getInventory().setItem(0, ItemUtil.getItem(Material.BOOK, 1, ChatColor.UNDERLINE+"Navigation", new ArrayList<String>(Arrays.asList("Pour accéder au menu principal"))));
		p.toji = false;
	}
	
	
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
		
		if (p.clockGame != null) {
			if (p.clockGame.started) {
				p.sendMessage("Vous ne pouvez pas quitter une partie en cours");
				return true;
			} else {
				p.clockGame.broadcoast(p.getName()+ ChatColor.RED+" quit");
				p.clockGame.players.remove(p);
				p.clearLgGameVar();
				
			}
		}
		
		
		
		p.player.teleport(Main.world.getSpawnLocation());
		PlayerUtil.survival(p.player);
		
		return false;
	}

}
