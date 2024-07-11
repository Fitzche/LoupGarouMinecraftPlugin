package fr.fitzche.lgmore.scoreboard.Inventory;

import java.util.ArrayList;
import java.util.Iterator;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import fr.fitzche.lgmore.GameLg;
import fr.fitzche.lgmore.Main;
import fr.fitzche.lgmore.RolesLg.Camp;
import fr.fitzche.lgmore.Util.CommandUtil;
import fr.fitzche.lgmore.Util.InventoryUtil;
import fr.fitzche.lgmore.Util.RoleUtilLg;

public class DisplayMenu implements Listener{

	
	public ArrayList<Inventory> invs = new ArrayList<Inventory>();
	public GameLg game;
	public String name;
	public Inventory before;
	ArrayList<ItemStack> itemsOfInventory = new ArrayList<ItemStack>();
	
	public DisplayMenu(ArrayList<ItemStack> itemsOfInventory, String name, GameLg game, Inventory inventoryBefore) {
		Main.server.getPluginManager().registerEvents(this, Main.plug);
		
		
		//copie de itemsOfInventory vers this.itemsOfInventory pour éviter de supprimer this... après
		
		for (ItemStack itemToAdd: itemsOfInventory) {
			this.itemsOfInventory.add(itemToAdd);
		}
		
		
		this.name = name;
		this.game = game;
		this.before = inventoryBefore;
		
		
		if (itemsOfInventory.get(0) == null) {
			System.out.println("itemsCalled hasn't item in DisplayMenu constructor");
			return;
		} 
		int numberOfItem = 0;
		
		do {
			InventoryDisplay page = new InventoryDisplay(this, name);
			
			
			ItemStack[] items = new ItemStack[27];
			
			for (int x = 0; items[26] == null; x++) {
				if (itemsOfInventory.size() == 0) {
					break;
				}
				items[x] = itemsOfInventory.get(0);
				if (itemsOfInventory.get(0) == null) {
					System.out.println("index 0 of itemsCalled is null in DisplayMenu constructor");
				} else {
					System.out.println("index 0 of itemsCalled is not null in DisplayMenu constructor");
				}
				itemsOfInventory.remove(0);
			}
			
			
			page.assignInv(items, name);
			invs.add(page.inventory);
			
		} while (numberOfItem > 27);
	}
	
	@Deprecated
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		if (this.invs.contains(event.getInventory())) {
			event.setCancelled(true);
		} else {
			return;
		}
		
		if (InventoryUtil.IsReturnCase(event.getCurrentItem())) {
			event.getWhoClicked().closeInventory();
			event.getWhoClicked().openInventory(before);
		} else if (InventoryUtil.IsNextCase(event.getCurrentItem())) {
			if ((invs.size() -1) == invs.indexOf(event.getInventory())) {
				event.getWhoClicked().sendMessage("Il n'y a pas d'autres inventaire !");
				
			} else {
				event.getWhoClicked().closeInventory();
				event.getWhoClicked().openInventory(invs.get(invs.indexOf(event.getInventory() )+1));
			}
			return;
			
			
		} else if (InventoryUtil.IsReturnCase(event.getCurrentItem())) {
			if (invs.indexOf(event.getInventory() )== 0) {
				event.getWhoClicked().sendMessage("Il n'y a pas d'inventaire avant !");

			} else {
				event.getWhoClicked().closeInventory();
				event.getWhoClicked().openInventory(invs.get(invs.indexOf(event.getInventory() ) - 1));
			
			}
		}
		
		
		
		if (event.getInventory().getName().equals("Solo et Hybrides")) {
			
			if (InventoryUtil.IsRoleCase(RoleUtilLg.getRoleofCamp(RoleUtilLg.existingRoles, Camp.Other, " at DisplayMenu check for Other"), event.getCurrentItem()) ||InventoryUtil.IsRoleCase(RoleUtilLg.getRoleofCamp(RoleUtilLg.existingRoles, Camp.Love, " at DisplayMenu check for Love"), event.getCurrentItem())) {
				if (event.getClick().equals(ClickType.LEFT)) {
					String[] list = new String[] {"Game", "addRole", this.game.name, event.getCurrentItem().getItemMeta().getDisplayName()};
					System.out.println(event.getCurrentItem().getItemMeta().getDisplayName()+ " added at onInventoryClick of DisplayMenu ");

					CommandUtil.runCommand("lga", (Player) event.getWhoClicked(), list);
					this.refresh((Player) event.getWhoClicked(), Camp.Other);
					
				} else if (event.getClick().equals(ClickType.RIGHT)) {
					String[] list = new String[] {"Game", "removeRole",this.game.name,  event.getCurrentItem().getItemMeta().getDisplayName()};
					CommandUtil.runCommand("lga", (Player) event.getWhoClicked(), list);
					System.out.println(event.getCurrentItem().getItemMeta().getDisplayName()+ " removed at onInventoryClick of DisplayMenu ");

					this.refresh((Player) event.getWhoClicked(), Camp.Other);
				}
			}
			
			
			
		} else if (event.getInventory().getName().equals("Villageois")) {
			if (InventoryUtil.IsRoleCase(RoleUtilLg.getRoleofCamp(RoleUtilLg.existingRoles, Camp.Villager, " at DisplayMenu check for Village"), event.getCurrentItem())) {
				if (event.getClick().equals(ClickType.LEFT)) {
					String[] list = new String[] {"Game", "addRole", this.game.name, event.getCurrentItem().getItemMeta().getDisplayName()};


					CommandUtil.runCommand("lga", (Player) event.getWhoClicked(), list);
					
					this.refresh((Player) event.getWhoClicked(), Camp.Villager);
				} else if (event.getClick().equals(ClickType.RIGHT)) {
					String[] list = new String[] {"Game", "removeRole",this.game.name,  event.getCurrentItem().getItemMeta().getDisplayName()};
					CommandUtil.runCommand("lga", (Player) event.getWhoClicked(), list);

					this.refresh((Player) event.getWhoClicked(), Camp.Villager);
				}
			}
			
			
		} else if (event.getInventory().getName().equals("Loups-Garou")){
			if (RoleUtilLg.existingRoles.size() == 0 || RoleUtilLg.existingRoles.get(0) == null || RoleUtilLg.existingRoles==null) {
				System.out.println("existing role is null");
			}
			
			if (InventoryUtil.IsRoleCase(RoleUtilLg.getRoleofCamp(RoleUtilLg.existingRoles, Camp.Wolf, " at DisplayMenu check for Wolf"), event.getCurrentItem())) {
				
				if (event.getClick().equals(ClickType.LEFT)) {
					
					String[] list = new String[] {"Game", "addRole", this.game.name, event.getCurrentItem().getItemMeta().getDisplayName()};


					CommandUtil.runCommand("lga", (Player) event.getWhoClicked(), list);
					
					
					this.refresh((Player) event.getWhoClicked(), Camp.Wolf);
					
				} else if (event.getClick().equals(ClickType.RIGHT)) {
					
					String[] list = new String[] {"Game", "removeRole",this.game.name,  event.getCurrentItem().getItemMeta().getDisplayName()};
					CommandUtil.runCommand("lga", (Player) event.getWhoClicked(), list);
					
					this.refresh((Player) event.getWhoClicked(), Camp.Wolf);
				}
			}
			
			
		} else {
			System.out.println(event.getInventory().getName()+ " doesn't equal ''Loups-Garou'', ''Villageois'', or ''Solo et Hybrides''");
		}
		
		
	}
	public void refresh(Player player, Camp camp) {
		//ici prendre les items à la racine,
		DisplayMenu newMenu = new DisplayMenu(RoleUtilLg.getItemOfCamp(game, camp), name, game, before);
		player.closeInventory();
		player.openInventory(newMenu.invs.get(0));
	}
	
}
