package fr.fitzche.lgmore.RolesLg;

import java.awt.print.Paper;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

import fr.fitzche.lgmore.GameLg;
import fr.fitzche.lgmore.commands.Lga;
import net.minecraft.server.v1_8_R1.ItemDye;


public class RoleDisplay {
	
	public static Inventory myInventory;
	
	
	
	public RoleDisplay() {
		myInventory = Bukkit.createInventory(null, 36, "Composition");
	}
	
	
	public static void setDisplayed(GameLg game) {
		
		
		myInventory = null;
		myInventory = Bukkit.createInventory(null, 36, "Composition");
		
		
		//TODO: affichage villager non effectif, inverser jaune et rouge
		
		ArrayList<RolesLg> rolesItemWolf = new ArrayList<RolesLg>();
		for (RolesLg role: game.roles) {
			if (role.getCampOfRole().equals(Camp.Wolf)) {
				System.out.println("add de loup");
				rolesItemWolf.add(role);
				
			}
		}
		ArrayList<RolesLg> rolesItemVillager = new ArrayList<RolesLg>();
		for (RolesLg role: game.roles) {
			if (role.getCampOfRole().equals(Camp.Villager)) {
				rolesItemVillager.add(role);
				
			}
		}
		
		ArrayList<RolesLg> rolesItemOthers = new ArrayList<RolesLg>();
		for (RolesLg role: game.roles) {
			if (role.getCampOfRole().equals(Camp.Other)) {
				rolesItemOthers.add(role);
				
			}
		}
		
		
		
		
		
		
		
		
		int x = 0;
		for (RolesLg role1: rolesItemWolf) {
			System.out.println("add de loup item");
			ItemStack item = new ItemStack(Material.WOOL, 1, (short) 14);
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName(ChatColor.RED + role1.getName());
			item.setItemMeta(meta);
			myInventory.setItem(x, item);
			x++;
		}
		for (RolesLg role2: rolesItemVillager) {
			
			ItemStack item = new ItemStack(Material.WOOL, 1, (short) 13);
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName(ChatColor.GREEN + role2.getName());
			item.setItemMeta(meta);
			myInventory.setItem(x, item);
			x++;
		}
		for (RolesLg role3: rolesItemOthers) {
			ItemStack item = new ItemStack(Material.WOOL, 1, (short) 4);
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName(ChatColor.YELLOW + role3.getName());
			item.setItemMeta(meta);
			myInventory.setItem(x, item);
			x++;
		}
		
		
	}
}
