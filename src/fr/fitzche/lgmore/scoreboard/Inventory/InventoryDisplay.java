package fr.fitzche.lgmore.scoreboard.Inventory;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.fitzche.lgmore.Util.ItemUtil;
import net.md_5.bungee.api.ChatColor;

public class InventoryDisplay {
	public Inventory inventory;
	public DisplayMenu referenceMenu;
	
	public InventoryDisplay(DisplayMenu referenceMenu, String name) {
		this.referenceMenu = referenceMenu;
		this.inventory = Bukkit.createInventory(null, 36, name);
		ItemStack back = new ItemStack(Material.BARRIER);
		ItemUtil.setName(back, ChatColor.ITALIC +"Précédant");
		
		ItemStack next = new ItemStack(Material.BARRIER);
		ItemUtil.setName(next, ChatColor.ITALIC +"Suivant");
		
		ItemStack returne = new ItemStack(Material.BARRIER);
		ItemUtil.setName(returne, ChatColor.ITALIC + "Retour");
		
		inventory.setItem(27, back);
		inventory.setItem(35, next);
		inventory.setItem(31, returne);
		
	}
	
	public void assignInv(ItemStack[] items, String name) {
		System.out.println("contents will be assigned in assignInv() in InventoryDisplay");
		ItemStack back = inventory.getItem(27);
		ItemStack next = inventory.getItem(35);
		ItemStack returne = inventory.getItem(31);
		
		
		if (items.length > 27) {
			System.out.println("DisplayInventory can't have more than 27 item at 17 of Inventory at assignInventory()");
		}
		Inventory newInventory = Bukkit.createInventory(null, 36, name);
		newInventory.setContents(items);
		if (items == null || items.length == 0 || items[0] == null) {
			System.out.println("contents are null in assignInv() in InventoryDisplay");
		}
		System.out.println("contents are assigned in assignInv() in InventoryDisplay");
		
		
		newInventory.setItem(31, returne);
		newInventory.setItem(27, back);
		newInventory.setItem(35, next);
		this.inventory = newInventory;
		
		
	}
	
	
}
