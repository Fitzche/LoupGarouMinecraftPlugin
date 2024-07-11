package fr.fitzche.lgmore.scoreboard.Inventory;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.scheduler.BukkitRunnable;

import fr.fitzche.lgmore.Main;
import fr.fitzche.lgmore.PlayerData;
import fr.fitzche.lgmore.RolesLg.CUPIDON;
import fr.fitzche.lgmore.RolesLg.RolesLg;
import fr.fitzche.lgmore.Util.GameLgUtil;
import fr.fitzche.lgmore.Util.PlayerUtil;
import fr.fitzche.lgmore.commands.Lg;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent.Action;
import net.minecraft.server.v1_8_R1.CommandDispatcher;

public class playersDisplay implements Listener{
	private Inventory playersInv;
	private boolean isCommanded;
	public PlayerData Lo1;
	public PlayerData Lo2;
	public boolean openable;
	
	public playersDisplay(ArrayList<PlayerData> plys, String command) {
		this.playersInv = Bukkit.createInventory(null, 36, "Composition");
		Main.server.getPluginManager().registerEvents(this, Main.plug);
		
		int x = 0;
		for (PlayerData player: plys) {
			
			ItemStack head = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
			SkullMeta meta = (SkullMeta) head.getItemMeta();
			
			meta.setOwner(player.player.getName());
			meta.setDisplayName(player.Name);
			head.setItemMeta(meta);
			this.playersInv.setItem(x, head);
			x++;
		}
		 
		
		this.openable = true;
		
		
		
		
		
	}
	
	public void display(Player pl , String[] args) {
		
		
		
		
		
		pl.openInventory(this.playersInv);
	}
	
	
	@Deprecated
	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
		//ici clic non cancelled, suite marche ?
		
		if (e.getInventory().equals(this.playersInv)) {
			e.setCancelled(true);
		}
		
		if (!this.openable) {
			return;
		}
		this.openable = false;
		
		Bukkit.getScheduler().runTaskLater(Main.plug, new BukkitRunnable() {

			@Override
			public void run() {
			 openable = true;
				
			}
			
		}, 20);
		
		System.out.println(this.playersInv);
		
		//System.out.println(e.getInventory());

		if (e.getInventory().equals(this.playersInv)){
			//System.out.println("commande  non null???");
			if (true) {
				//System.out.println("commande  non null");
				for (ItemStack item: this.playersInv.getContents()) {
					
					if (e.getCurrentItem().equals(item)) {
						//System.out.println("equal !!");
						
						Player cupi = (Player) e.getWhoClicked();
						if (!PlayerUtil.getDataOfPlayer(cupi, " at onInventoryClick of PlayerDisplay, 1").getLgRole().equals(RolesLg.CUPIDON)){
							cupi.sendMessage("Vous n'etes pas cupidon !!");
							return;
						}
						
				
						
						if (this.Lo1 == null) {
							this.Lo1 = PlayerUtil.getDataOfPlayer(PlayerUtil.getPlayer(item.getItemMeta().getDisplayName()), " at onInventoryClick of PlayerDisplay, 2");
							cupi.sendMessage(ChatColor.LIGHT_PURPLE+"Vous avez choisi " + item.getItemMeta().getDisplayName()+ " en premier");
							return;
						}else if (this.Lo2 == null) {
							
							if (Lo1.equals(Lo2)) {
								cupi.sendMessage("Vous ne pouvez pas mettre deux fois le même joueur");
								cupi.closeInventory();
								return;
									
							}
							
							
							this.Lo2 = PlayerUtil.getDataOfPlayer(PlayerUtil.getPlayer(item.getItemMeta().getDisplayName()), " at onInventoryClick of PlayerDisplay, 2");
							cupi.sendMessage(ChatColor.LIGHT_PURPLE+"Vous avez choisi " + item.getItemMeta().getDisplayName() + " en deuxième");
							
							
							
							
							
							CUPIDON cupidon = (CUPIDON) PlayerUtil.getDataOfPlayer(cupi, " at onInventoryClick of PlayerDisplay, 3").roleIn;
							cupidon.createCouple(Lo1, Lo2, PlayerUtil.getDataOfPlayer(cupi, " at onInventoryClick of PlayerDisplay, 4"));
							cupi.sendMessage("Vous avez tiré vos flèches sur "+ Lo2.player.getName() + " et " +Lo1.player.getName());
							cupi.closeInventory();
							
							
								
						}
						
							
					} 
						
					
					
				}
			}
		}
	}
	
}
