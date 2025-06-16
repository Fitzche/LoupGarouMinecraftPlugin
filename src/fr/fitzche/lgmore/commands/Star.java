package fr.fitzche.lgmore.commands;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import StarParty.RolesStar;
import StarParty.StarParty;
import StarParty.StarUtil;
import fr.fitzche.lgmore.Main;
import fr.fitzche.lgmore.PlayerData;
import fr.fitzche.lgmore.Util.ItemUtil;
import fr.fitzche.lgmore.Util.MathUtil;

public class Star implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
		
		
		
		if (args[0].equals("setRole")) {
			for (RolesStar role:RolesStar.values()) {
				if (args[1].equals(role.getName())) {
					PlayerData p = Main.getData(sender);
					p.roleStar = role;
					p.roleSta = StarUtil.createRole(role, p);
					p.roleSta.onRole();
				}
			}
		}
		if (args[0].equals("role")) {
			PlayerData p = Main.getData(sender);
			if (p == null) {
				return false;
			}
			if (p.roleStar == null) {
				p.sendMessage("Vous n'avez pas de role");
			} else {
				p.sendMessage("Vous êtes "+ p.roleStar.color + p.roleStar.getName() + "\n"+ ChatColor.WHITE + p.roleStar.getDescription());
			}
		}
		if (args[0].equals("play")) {
			
			
			for (StarParty party:Main.parties) {
				if (!party.started && party.players.size() < 17) {
					party.addPlayer(Main.getData(sender));
					return true;
				}
			}
			StarParty party = new StarParty("starParty-"+ MathUtil.generateAlInt(0, 10000));
			Main.parties.add(party);
			party.addPlayer(Main.getData(sender));
			
		}
		if (args[0].equals("forceStart")) {
			Main.getData(sender).starParty.started = true;
		}
		
		if (args[0].equals("ataruGive")) {
			ItemStack sword = new ItemStack(Material.DIAMOND_SWORD);
			ItemUtil.setName(sword, ChatColor.UNDERLINE+"AtaruInf");
			Player p = (Player) sender;
			p.getInventory().addItem(sword);
		}
		if (args[0].equals("lightningGive")) {
			ItemStack sword = new ItemStack(Material.NETHER_STAR);
			ItemUtil.setName(sword, ChatColor.UNDERLINE+"LightningInf");
			ItemUtil.addAppaEnchant(sword);
			Player p = (Player) sender;
			p.getInventory().addItem(sword);
		}
		if (args[0].equals("SithGive")) {
			ItemStack sword = new ItemStack(Material.NETHER_STAR);
			ItemUtil.setName(sword, ChatColor.UNDERLINE+"SithInf");
			ItemUtil.addAppaEnchant(sword);
			Player p = (Player) sender;
			p.getInventory().addItem(sword);
		}
		if (args[0].equals("StrangleGive")) {
			ItemStack sword = new ItemStack(Material.NETHER_STAR);
			ItemUtil.setName(sword, ChatColor.UNDERLINE+"StrangleInf");
			ItemUtil.addAppaEnchant(sword);
			Player p = (Player) sender;
			p.getInventory().addItem(sword);
		}
		if (args[0].equals("ChewGive")) {
			ItemStack sword = new ItemStack(Material.BOW);
			ItemUtil.setName(sword, ChatColor.UNDERLINE+"ChewbacaShooter");
			ItemUtil.addAppaEnchant(sword);
			Player p = (Player) sender;
			p.getInventory().addItem(sword);
		}
		if (args[0].equals("flameGive")) {
			ItemStack sword = new ItemStack(Material.BOW);
			ItemUtil.setName(sword, ChatColor.UNDERLINE+"FlameThrowerInf");
			ItemUtil.addAppaEnchant(sword);
			Player p = (Player) sender;
			p.getInventory().addItem(sword);
		}
		
		if (args[0].equals("flameTrueGive")) {
			ItemStack sword = new ItemStack(Material.BOW);
			ItemUtil.setName(sword, ChatColor.UNDERLINE+"Firethrown");
			ItemUtil.addAppaEnchant(sword);
			Player p = (Player) sender;
			p.getInventory().addItem(sword);
		}
		return false;
	}

}
