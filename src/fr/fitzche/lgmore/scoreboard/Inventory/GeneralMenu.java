package fr.fitzche.lgmore.scoreboard.Inventory;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import fr.fitzche.lgmore.Main;
import fr.fitzche.lgmore.PlayerData;
import fr.fitzche.lgmore.Lg.GameLg;
import fr.fitzche.lgmore.Util.CommandUtil;
import fr.fitzche.lgmore.Util.ItemUtil;
import net.md_5.bungee.api.ChatColor;

public class GeneralMenu implements Listener {

	public Inventory inv = Bukkit.createInventory(null, 36);
	
	
	public GeneralMenu(PlayerData p) {
		Bukkit.getPluginManager().registerEvents(this, Main.plug);
		inv.setItem(11, ItemUtil.getItem(Main.myHead,ChatColor.GOLD+""+ChatColor.BOLD+ "Créer Une Partie", new ArrayList<String>(Arrays.asList(
				ChatColor.GRAY + "   ▪"+ChatColor.DARK_RED+"Loup Garou",
				ChatColor.GRAY + "   ▪"+ChatColor.DARK_BLUE+"Team Swapper"
				))));
		
		inv.setItem(13, ItemUtil.getItem(ItemUtil.getCustomHead("LeVraiFuze"), ChatColor.GOLD+""+ChatColor.BOLD+"Config", new ArrayList<String>(Arrays.asList(ChatColor.GRAY+"   ▪Configurer la partie où vous vous trouvez", ChatColor.RED+"   ✖ uniquement op ou hoster"))));
		
		inv.setItem(15, ItemUtil.getItem(ItemUtil.getCustomHead(p.getName()), ChatColor.GOLD + "" +ChatColor.BOLD+""+"Profil", new ArrayList<String>(Arrays.asList(
				ChatColor.UNDERLINE +""+ ChatColor.DARK_GREEN+"⋙  "+ ChatColor.WHITE+"Victoire / Nombre de partie Total: "+ChatColor.AQUA+""+ ChatColor.BOLD +p.getWinRate(), 
				ChatColor.DARK_GREEN+"⋙  "+ ChatColor.WHITE+p.getWinRateString(20),
				ChatColor.RED + "▪"+ ChatColor.WHITE+"xp: "+ChatColor.AQUA+ ""+ ChatColor.BOLD +p.xp , 
				ChatColor.LIGHT_PURPLE + "▪"+ChatColor.WHITE + "feather: "+ ChatColor.AQUA+""+ChatColor.BOLD +p.feathers))));

		
	}
	
	public void open(PlayerData p) {
		if (p.isOnline) {
			p.player.openInventory(inv);
		}
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
		if (e.getInventory().equals(this.inv)) {
			e.setCancelled(true);
			if (e.getCurrentItem().getItemMeta() == null || e.getCurrentItem().getItemMeta().getDisplayName() == null) {
				return;
				
			}
			PlayerData p = Main.getData(e.getWhoClicked());
			if (e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.GOLD+""+ChatColor.BOLD+"Créer Une Partie")) {
				GameTypeChoose chose = new GameTypeChoose();
				chose.open(p, e.getInventory());
			} else if (e.getCurrentItem().getItemMeta().getDisplayName().equals( ChatColor.GOLD+""+ChatColor.BOLD+"Config")) {
				if (p.game != null && (p.hoster || p.Name.equals("FITZCHE") || p.Name.equals("Fitzche"))) {
					CommandUtil.runCommand("lga", (Player) e.getWhoClicked(), new String[] {"Game", "config", p.game.name});
					
				}
			}
			
		}
	}
	
	
	

}
