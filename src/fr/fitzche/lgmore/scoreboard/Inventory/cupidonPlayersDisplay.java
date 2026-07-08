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

//InvFunct to choose the lovers (role cupidon), lg
public class cupidonPlayersDisplay implements Listener{
	private Inventory playersInv;
	private boolean isCommanded;
	public PlayerData Lo1;
	public PlayerData Lo2;
	public boolean openable;
	
	public cupidonPlayersDisplay(ArrayList<PlayerData> plys, String command) {
		this.playersInv = Bukkit.createInventory(null, 36, "Composition");
		Main.server.getPluginManager().registerEvents(this, Main.plug);
		
		int x = 0;
		for (PlayerData player: plys) {
			
			ItemStack head = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
			SkullMeta meta = (SkullMeta) head.getItemMeta();
			
			meta.setOwner(player.getName());
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
						if (!Main.getData(e.getWhoClicked().getName()).getLgRole().equals(RolesLg.CUPIDON)){
							cupi.sendMessage("Vous n'etes pas cupidon !!");
							return;
						}
						
				
						
						if (this.Lo1 == null) {
							if (Main.getData(item.getItemMeta().getDisplayName()) != null) {
								this.Lo1 = Main.getData(item.getItemMeta().getDisplayName());
							} else {
								return;
							}
							
							cupi.sendMessage(ChatColor.LIGHT_PURPLE+"Vous avez choisi " + item.getItemMeta().getDisplayName()+ " en premier");
							return;
						}else if (this.Lo2 == null) {
							
							
							
							if (Main.getData(item.getItemMeta().getDisplayName()) != null) {
								this.Lo2 = Main.getData(item.getItemMeta().getDisplayName());
							} else {
								return;
							}
							cupi.sendMessage(ChatColor.LIGHT_PURPLE+"Vous avez choisi " + item.getItemMeta().getDisplayName() + " en deuxième");
							
							if (Lo1.getName().equals(Lo2.getName())) {
								cupi.sendMessage("Vous ne pouvez pas mettre deux fois le même joueur");
								cupi.closeInventory();
								return;
									
							}
							if (this.Lo1 == null || this.Lo2 == null) {
								e.getWhoClicked().sendMessage("Erreur, veuillez recommencer (ligne136 de playersDisplay)");
								return;
							}
							if (Lo1.getName().equals(Lo2.getName())) {
								cupi.sendMessage("Vous ne pouvez pas mettre deux fois le même joueur, Veuillez choisir un autre couple");
								cupi.closeInventory();
								return;
							}
							
							CUPIDON cupidon = (CUPIDON) Main.getData(cupi).roleIn;
							cupidon.createCouple(Lo1, Lo2, Main.getData(cupi));
							cupi.sendMessage("Vous avez tiré vos flèches sur "+ Lo2.getName() + " et " +Lo1.getName());
							cupi.closeInventory();
							
							
								
						}
						
							
					} 
						
					
					
				}
			}
		}
	}
	
}
