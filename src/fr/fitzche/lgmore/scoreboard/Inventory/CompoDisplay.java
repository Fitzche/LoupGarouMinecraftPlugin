package fr.fitzche.lgmore.scoreboard.Inventory;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.fitzche.lgmore.GameLg;
import fr.fitzche.lgmore.Main;
import fr.fitzche.lgmore.RolesLg.Camp;
import fr.fitzche.lgmore.RolesLg.RolesLg;
import fr.fitzche.lgmore.Util.RoleUtilLg;
import net.md_5.bungee.api.ChatColor;

public class CompoDisplay implements Listener{
	
	public Inventory inv;
	public GameLg gm;
	
	public CompoDisplay(GameLg gm) {
		Main.server.getPluginManager().registerEvents(this, Main.plug);

		
		this.gm = gm;
		Inventory compo = Bukkit.createInventory(null, 36, "Compo");
		
		ArrayList<RolesLg> loups = new ArrayList<RolesLg>();
		ArrayList<RolesLg> Villager = new ArrayList<RolesLg>();
		ArrayList<RolesLg> Other = new ArrayList<RolesLg>();

		
		for (RolesLg role: gm.dispoRoles) {
			if (role.getCampOfRole().equals(Camp.Other)) {
				Other.add(role);
			}else if (role.getCampOfRole().equals(Camp.Wolf)) {
				loups.add(role);
			} else if (role.getCampOfRole().equals(Camp.Villager)) {
				Villager.add(role);
			}
		}
		
		ItemStack sword = new ItemStack(Material.DIAMOND_SWORD) ;
		ItemMeta swordM = sword.getItemMeta();
		swordM.setDisplayName(ChatColor.DARK_RED+ "Loups-Garou");
		sword.setItemMeta(swordM);
		compo.setItem(10, sword);
		
		ItemStack ble = new ItemStack(Material.WHEAT) ;
		ItemMeta bleM = ble.getItemMeta();
		bleM.setDisplayName(ChatColor.GREEN + "Villageois");
		ble.setItemMeta(bleM);
		compo.setItem(13, ble);
		
		ItemStack feather = new ItemStack(Material.FEATHER) ;
		ItemMeta featherM = feather.getItemMeta();
		featherM.setDisplayName(ChatColor.GOLD + "Solo et Hybrides");
		feather.setItemMeta(featherM);
		compo.setItem(16, feather);
		
		ItemStack returne = new ItemStack(Material.BARRIER) ;
		ItemMeta returneM = returne.getItemMeta();
		returneM.setDisplayName(ChatColor.ITALIC + "Retour");
		returne.setItemMeta(returneM);
		compo.setItem(35, returne);
		
		this.inv = compo;
		
		
		

	}
	
	public void open(Player p) {
		p.closeInventory();
		p.openInventory(this.inv);
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
		if (e.getInventory().equals(this.inv)) {
			e.setCancelled(true);
			
			if (e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.ITALIC + "Retour")) {
				ConfigDisplay config = new ConfigDisplay(this.gm);
				config.open((Player) e.getWhoClicked());
				
				
			} else if (e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.GREEN + "Villageois")) {
				
				DisplayMenu menu = new DisplayMenu(RoleUtilLg.getItemOfCamp(gm, Camp.Villager), "Villageois", gm, inv);
				e.getWhoClicked().openInventory(menu.invs.get(0));
			} else if (e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.DARK_RED+ "Loups-Garou")) {
				DisplayMenu menu = new DisplayMenu(RoleUtilLg.getItemOfCamp(gm, Camp.Wolf), "Loups-Garou", gm, inv);
				e.getWhoClicked().openInventory(menu.invs.get(0));
			} else if (e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.GOLD + "Solo et Hybrides"))  {
				DisplayMenu menu = new DisplayMenu(RoleUtilLg.getItemOfCamp(gm, Camp.Other), "Solo et Hybrides", gm, inv);
				e.getWhoClicked().openInventory(menu.invs.get(0));
			}
			
			
			
			
		}
		
	}
	
}
