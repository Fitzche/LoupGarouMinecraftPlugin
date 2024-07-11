package fr.fitzche.lgmore.Util;

import java.util.ArrayList;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import fr.fitzche.lgmore.RolesLg.RolesLg;
import net.md_5.bungee.api.ChatColor;

public class InventoryUtil {

	//Methode qui crée un item de retour
	public static boolean IsReturnCase(ItemStack item) {
		if (item.getItemMeta().getDisplayName().equals(ChatColor.ITALIC + "Retour")) {
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean IsBackCase(ItemStack item) {
		if (item.getItemMeta().getDisplayName().equals(ChatColor.ITALIC + "Précédent")) {
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean IsNextCase(ItemStack item) {
		if (item.getItemMeta().getDisplayName().equals(ChatColor.ITALIC + "Suivant")) {
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean IsRoleCase(ArrayList<RolesLg> roles, ItemStack item) {
		for (RolesLg role:roles) {
			if ((role.getCampOfRole().getColor()+ role.getName()).equals(item.getItemMeta().getDisplayName())) {
				return true;
			}
		}
		System.out.println("not Case of role at IsRoleCase of InventoryUtil ");
		return false;
	}
	
	
}
