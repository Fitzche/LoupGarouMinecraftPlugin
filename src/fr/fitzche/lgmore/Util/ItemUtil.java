package fr.fitzche.lgmore.Util;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemUtil {
	public static void setName(ItemStack item, String name) {
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(name);
		item.setItemMeta(meta);
	}
	
	public static void setLore(ItemStack item, List<String> lore) {
		ItemMeta meta = item.getItemMeta();
		meta.setLore(lore);
		item.setItemMeta(meta);
	}
	
	public static int howManyOf(Inventory inv, Material mat) {
		int h = 0;
		for (ItemStack item:inv.getContents()) {
			if (item.getType().equals(mat)) {
				h += item.getAmount();
			}
		}
		return h;
	}
	
	public static void takeInInv(Inventory inv, Material mat, int amount) {
		int taken = 0;
		do {
			inv.remove(mat);
			taken ++;
		} while (taken < amount);
	}

}
