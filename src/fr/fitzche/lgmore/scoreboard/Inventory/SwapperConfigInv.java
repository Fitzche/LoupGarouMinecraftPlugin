package fr.fitzche.lgmore.scoreboard.Inventory;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import fr.fitzche.lgmore.Main;
import fr.fitzche.lgmore.PlayerData;
import fr.fitzche.lgmore.Lg.GameLg;
import fr.fitzche.lgmore.Util.ItemUtil;
import net.md_5.bungee.api.ChatColor;

public class SwapperConfigInv implements Listener{

	public GameLg game;
	public Inventory inv = Bukkit.createInventory(null, 36);
	public SwapperConfigInv(GameLg game) {
		this.game = game;
		setItems();
		Bukkit.getPluginManager().registerEvents(this, Main.plug);
	}
	
	public void setItems() {
		String duel = ChatColor.GRAY+"▪"+ ChatColor.RED+"désactivé";
		String trio = ChatColor.GRAY+"▪"+ ChatColor.RED+"désactivé";
		String quadrio = ChatColor.GRAY+"▪"+ ChatColor.RED+"désactivé";
		String pente = ChatColor.GRAY+"▪"+ ChatColor.RED+"désactivé";
		if (game.swapperDuel) {
			duel = ChatColor.GRAY+"▪"+ ChatColor.GREEN+"activé";
		} 
		if (game.swapperTrio) {
			trio = ChatColor.GRAY+"▪"+ ChatColor.GREEN+"activé";
		}
		if (game.swapperQuadrio) {
			quadrio = ChatColor.GRAY+"▪"+ ChatColor.GREEN+"activé";
		}
		if (game.swapperPente) {
			pente = ChatColor.GRAY+"▪"+ ChatColor.GREEN+"activé";
		}
		inv.setItem(10, ItemUtil.getItem(Material.PAPER, 2, ChatColor.AQUA + "Ⅱ "+ChatColor.GOLD+"DUO"+ChatColor.AQUA+ "Ⅱ", new ArrayList<String>(Arrays.asList(
				"2",
				duel
				))));
		inv.setItem(12, ItemUtil.getItem(Material.PAPER, 3, ChatColor.AQUA + "Ⅱ "+ChatColor.GOLD+"TRIO"+ChatColor.AQUA+ "Ⅱ", new ArrayList<String>(Arrays.asList(
				"3",
				trio
				))));
		inv.setItem(14, ItemUtil.getItem(Material.PAPER, 4, ChatColor.AQUA + "Ⅱ "+ChatColor.GOLD+"QUADRIO"+ChatColor.AQUA+ "Ⅱ", new ArrayList<String>(Arrays.asList(
				"4",
				quadrio
				))));
		inv.setItem(16, ItemUtil.getItem(Material.PAPER, 5, ChatColor.AQUA + "Ⅱ "+ChatColor.GOLD+"PENTA"+ChatColor.AQUA+ "Ⅱ", new ArrayList<String>(Arrays.asList(
				"5",
				pente
				))));
	}
	
	public void clearSwapChoice() {
		game.swapperDuel = false;
		game.swapperPente = false;
		game.swapperQuadrio = false;
		game.swapperTrio = false;
		
	}
	
	public void open(PlayerData p) {
		p.player.openInventory(inv);
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
		if (e.getInventory().equals(this.inv)) {
			e.setCancelled(true);
			if (e.getCurrentItem() == null || e.getCurrentItem().getItemMeta() == null || e.getCurrentItem().getItemMeta().getDisplayName() == null || e.getCurrentItem().getItemMeta().getLore() == null ||  e.getCurrentItem().getItemMeta().getLore().size() <= 0) {
				
				return; 
			}
			
			switch ( e.getCurrentItem().getItemMeta().getLore().get(0)) {
				case "2":
					clearSwapChoice();
					game.swapperDuel = true;
					break;
				case "3":
					clearSwapChoice();
					game.swapperTrio = true;
					break;
				case "4":
					clearSwapChoice();
					game.swapperQuadrio = true;
					break;
				case "5":
					clearSwapChoice();
					game.swapperPente = true;
					break;
			}
			setItems();
		}
	}
}
