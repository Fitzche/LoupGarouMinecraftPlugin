package fr.fitzche.lgmore.uhc_color.Gui;

import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.Wool;

import fr.fitzche.lgmore.uhc_color.MainColor;
import fr.fitzche.lgmore.uhc_color.PlayerColorboard.*;
import net.md_5.bungee.api.ChatColor;


public class chooseColorGui implements Listener {
	
	public Inventory inv;
	public Colorboard colorB;
	public Player player;
	
	@Deprecated
	public chooseColorGui(Colorboard colorB, Player player) {
		MainColor.serv.getPluginManager().registerEvents(this, MainColor.getPlugin());
		
		this.player = player;
		this.colorB = colorB;
		this.inv = Bukkit.createInventory(null, 9, "C"+ChatColor.YELLOW+"o"+ChatColor.DARK_BLUE+"l"+ChatColor.AQUA+"o"+ChatColor.GREEN+"r");
		
		Wool WHITE = new Wool(DyeColor.SILVER);
		ItemStack WHITE_ = WHITE.toItemStack(1);
		
		ItemMeta meta0 = WHITE_.getItemMeta();
		meta0.setDisplayName(ChatColor.MAGIC+"CANCEL");
		WHITE_.setItemMeta(meta0);
		inv.setItem(0, WHITE_);
		
		Wool YELLOW = new Wool(DyeColor.YELLOW);
		ItemStack YELLOW_ = YELLOW.toItemStack(1);
		ItemMeta meta4 = YELLOW_.getItemMeta();
		meta4.setDisplayName(ChatColor.YELLOW+"YELLOW");
		YELLOW_.setItemMeta(meta4);
		inv.setItem(1, YELLOW_);
		
		Wool RED = new Wool(DyeColor.RED);
		ItemStack RED_ = RED.toItemStack(1);
		ItemMeta meta14 = RED_.getItemMeta();
		meta14.setDisplayName(ChatColor.RED+"RED");
		RED_.setItemMeta(meta14);
		inv.setItem(2, RED_);
		
		Wool BLUE = new Wool(DyeColor.LIGHT_BLUE);
		ItemStack BLUE_ = BLUE.toItemStack(1);
		ItemMeta meta3 = BLUE_.getItemMeta();
		meta3.setDisplayName(ChatColor.BLUE+"BLUE");
		BLUE_.setItemMeta(meta3);
		inv.setItem(3, BLUE_);
		
		Wool GREEN = new Wool(DyeColor.GREEN);
		ItemStack GREEN_ = GREEN.toItemStack(1);
		ItemMeta meta5 = GREEN_.getItemMeta();
		meta5.setDisplayName(ChatColor.GREEN+"GREEN");
		GREEN_.setItemMeta(meta5);
		inv.setItem(4, GREEN_);
		
		Wool BLACK = new Wool(DyeColor.BLACK);
		ItemStack BLACK_ = BLACK.toItemStack(1);
		ItemMeta meta15 = BLACK_.getItemMeta();
		meta15.setDisplayName(ChatColor.BLACK+"BLACK");
		BLACK_.setItemMeta(meta15);
		inv.setItem(5, BLACK_);
		
		Wool GRAY = new Wool(DyeColor.GRAY);
		ItemStack GRAY_ = GRAY.toItemStack(1);
		ItemMeta meta8 = GRAY_.getItemMeta();
		meta8.setDisplayName(ChatColor.GRAY+"GRAY");
		GRAY_.setItemMeta(meta8);
		inv.setItem(6, GRAY_);
		
		Wool DARK_BLUE = new Wool(DyeColor.BLUE);
		ItemStack DARK_BLUE_ = DARK_BLUE.toItemStack(1);
		ItemMeta meta11 = DARK_BLUE_.getItemMeta();
		meta11.setDisplayName(ChatColor.DARK_BLUE+"DARK BLUE");
		DARK_BLUE_.setItemMeta(meta11);
		inv.setItem(7, DARK_BLUE_);
		
		Wool ORANGE = new Wool(DyeColor.ORANGE);
		ItemStack ORANGE_ = ORANGE.toItemStack(1);
		ItemMeta meta1 = ORANGE_.getItemMeta();
		meta1.setDisplayName(ChatColor.GOLD+"ORANGE");
		ORANGE_.setItemMeta(meta1);
		inv.setItem(8, ORANGE_);
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
		//System.out.println("clicked");
		
		if (!e.getInventory().equals(this.inv)) {
			System.out.println("cancelled 2++1");
			return;
		}
		System.out.println("color inv");
		
		for (ItemStack item:this.inv.getContents()) {
			
			//System.out.println("tryed");
			
			//System.out.println(e.getCurrentItem().getItemMeta().getDisplayName()+"/"+item.getItemMeta().getDisplayName());
			if (e.getCurrentItem().getItemMeta().getDisplayName() == item.getItemMeta().getDisplayName()) {
				//System.out.println("reussi");
				this.colorB.ColorPlayer(player, item.getItemMeta().getDisplayName());

				
			}
		}
		e.setCancelled(true);
		System.out.println("cancelled 2");
		
	}
}
