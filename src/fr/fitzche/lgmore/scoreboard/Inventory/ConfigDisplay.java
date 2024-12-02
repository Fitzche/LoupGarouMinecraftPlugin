package fr.fitzche.lgmore.scoreboard.Inventory;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.fitzche.lgmore.Main;
import fr.fitzche.lgmore.Lg.GameLg;
import fr.fitzche.lgmore.Util.CommandUtil;

public class ConfigDisplay implements Listener{
	public Inventory inv;
	public GameLg game;
	HashMap<String, Boolean> areOp = new HashMap<String, Boolean>();
	
	public ConfigDisplay(GameLg game) {
		Main.server.getPluginManager().registerEvents(this, Main.plug);

		Inventory config = Bukkit.createInventory(null, 36, "Config");
		
		
		this.game = game;
		
		ItemStack book = new ItemStack(org.bukkit.Material.ENCHANTED_BOOK);
		ItemMeta metaBook = book.getItemMeta();
		metaBook.setDisplayName(ChatColor.BLUE + "Compo");
		book.setItemMeta(metaBook);
		config.setItem(10, book);
		
		ItemStack sword = new ItemStack(org.bukkit.Material.DIAMOND_SWORD);
		ItemMeta metaSword = sword.getItemMeta();
		metaSword.setDisplayName(ChatColor.BLUE + "Joueur");
		sword.setItemMeta(metaSword);
		config.setItem(13, sword);
		
		ItemStack event = new ItemStack(org.bukkit.Material.EMERALD);
		ItemMeta metaEvent = event.getItemMeta();
		metaEvent.setDisplayName(ChatColor.BLUE + "Event");
		event.setItemMeta(metaEvent);
		config.setItem(16, event);
		
		ItemStack start = new ItemStack(org.bukkit.Material.COMMAND_MINECART);
		ItemMeta metaStart = start.getItemMeta();
		metaStart.setDisplayName(ChatColor.DARK_BLUE + "Start");
		start.setItemMeta(metaStart);
		config.setItem(27, start);
		
		ItemStack meetup = new ItemStack(org.bukkit.Material.FEATHER);
		ItemMeta metaMeetup = start.getItemMeta();
		metaMeetup.setDisplayName(ChatColor.DARK_BLUE + "Meetup");
		meetup.setItemMeta(metaMeetup);
		config.setItem(29, meetup);
		
		this.inv = config;
	}
	
	public void open(Player p, boolean isOp) {
		areOp.put(p.getName(), isOp);
		p.openInventory(this.inv);
	}
	
	
	@EventHandler
	@Deprecated
	public void onInventoryClick(InventoryClickEvent e) {
		if (e.getInventory().equals(this.inv)) {
			e.setCancelled(true);
			if (e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.BLUE +"Compo")) {
				
				this.game.compo.open((Player) e.getWhoClicked(), areOp.get(e.getWhoClicked().getName()));
			} else if (e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.BLUE + "Joueur")) {
				
				this.game.plys.open((Player) e.getWhoClicked(), areOp.get(e.getWhoClicked().getName()));
			} else if (e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.DARK_BLUE + "Start")) {
				if (!areOp.get(e.getWhoClicked().getName())) {
					return;
				}
				
				String[] args = new String[] {"Game", "start", game.name};
				CommandUtil.runCommand("lga", (Player) e.getWhoClicked(), args);
				e.getWhoClicked().closeInventory();
			} else if (e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.BLUE + "Event")) {
				
				this.game.events.open((Player)e.getWhoClicked(), areOp.get(e.getWhoClicked().getName()));
				
			} else if (e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.DARK_BLUE + "Meetup")) {
				this.game.isMeetup = !this.game.isMeetup;
				if (this.game.isMeetup) {
					e.getWhoClicked().sendMessage("La game "+ this.game.name + " est réglé sur: meetup activé");
				} else {
					e.getWhoClicked().sendMessage("La game "+ this.game.name + " est réglé sur: meetup désactivé");

				}
			}
			
			
			
		}
		
	}
	
	
}
