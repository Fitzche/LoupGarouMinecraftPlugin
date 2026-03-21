package fr.fitzche.lgmore.Util;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.inventivetalent.reflection.minecraft.Minecraft;

import com.mojang.authlib.GameProfile;

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
	
	public static ItemStack getItem(Material mat, int n, String name, ArrayList<String> lores) {
		ItemStack item = new ItemStack(mat, n);
		ItemUtil.setName(item, name);
		ItemUtil.setLore(item, lores);
		return item;
		
	}
	public static ItemStack getItem(ItemStack item,  String name, ArrayList<String> lores) {
		
		ItemUtil.setName(item, name);
		ItemUtil.setLore(item, lores);
		return item;
		
	}
	
	/**
     * Récupère la tête customisée correspondant au joueur spécifié.
     * Le joueur n'a pas besoin d'être connecté.
     *
     * @param playerName Le nom du joueur dont on veut afficher la tête.
     * @return Un ItemStack représentant la tête customisée.
     */
    public static ItemStack getCustomHead(String playerName) {
        // Pour Minecraft 1.8, le type de l'item est SKULL_ITEM et la durabilité 3 correspond à la tête de joueur.
        ItemStack head = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        SkullMeta meta = (SkullMeta) head.getItemMeta();
       
     
        meta.setOwner(playerName); // renseigne le nom du joueur dont on souhaite utiliser la skin
        
        head.setItemMeta(meta);
        return head;
    }
  
	
	public static void addAppaEnchant(ItemStack item) {
		ItemMeta meta = item.getItemMeta();
		meta.addEnchant(Enchantment.DURABILITY	, 1, false);
		
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		item.setItemMeta(meta);
	}
	public static void hideAttributes(ItemStack item) {
		ItemMeta meta = item.getItemMeta();
		
		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		
		item.setItemMeta(meta);
	}
	
	public static Color getColor(ChatColor color) {
		switch (color) {
		case AQUA:
			return Color.AQUA;
		case BLACK:
			return Color.BLACK;
		case BLUE:
			return Color.BLUE;
		case BOLD:
			return Color.WHITE;
		case DARK_AQUA:
			return Color.BLUE;
		case DARK_BLUE:
			return Color.BLUE;
		case DARK_GRAY:
			return Color.GRAY;
		case DARK_GREEN:
			return Color.GREEN;
		case DARK_PURPLE:
			return Color.PURPLE;
		case DARK_RED:
			return Color.RED;
		case GOLD:
			return Color.ORANGE;
		case GRAY:
			return Color.GRAY;
		case GREEN:
			return Color.GREEN;
		case ITALIC:
			return Color.WHITE;
		case LIGHT_PURPLE:
			return Color.PURPLE;
		case MAGIC:
			return Color.PURPLE;
		case RED:
			return Color.RED;
		case RESET:
			return Color.WHITE;
		case STRIKETHROUGH:
			return Color.WHITE;
		case UNDERLINE:
			return Color.WHITE;
		case WHITE:
				return Color.WHITE;
		case YELLOW:
				return Color.YELLOW;
			
		default:
			return Color.WHITE;
		
		}
		
	}
	
	public static int howManyOf(Inventory inv, Material mat) {
		int h = 0;
		for (ItemStack item:inv.getContents()) {
			if (item == null) {
				
			} else if (item.getType().equals(mat)) {
				System.out.println("silex exist");
			}
			if (item != null &&item.getType().equals(mat)) {
				h += item.getAmount();
				System.out.println("mat +"+ item.getAmount());
			}
		}
		return h;
	}
	
	public static int howManyOf(Inventory inv, Material mat, String name) {
		int h = 0;
		for (ItemStack item:inv.getContents()) {
			
			if (item != null &&item.getType().equals(mat) && item.hasItemMeta() && item.getItemMeta().hasDisplayName() && item.getItemMeta().getDisplayName().equals(name)) {
				h += item.getAmount();
				System.out.println("mat +"+ item.getAmount());
			}
		}
		return h;
	}
	
	public static void takeInInv(Inventory inv, Material mat, int amount) {
		int taken = 0;
		for (ItemStack item:inv.getContents()) {
			if (item != null && item.getType().equals(mat)) {
				do {
					item.setAmount(item.getAmount() - 1);
					taken ++;
					
				} while (item.getAmount() > 0 && taken < amount);
				if (item.getAmount()<0) {
					inv.remove(item);
				}
			}
		}
	}
	public static void takeInInv(Inventory inv, Material mat, String name, int amount) {
		int taken = 0;
		for (ItemStack item:inv.getContents()) {
			if (item != null && item.getType().equals(mat) && item.hasItemMeta() && item.getItemMeta().hasDisplayName() && item.getItemMeta().getDisplayName().equals(name)) {
				do {
					item.setAmount(item.getAmount() - 1);
					taken ++;
					
				} while (item.getAmount() > 0 && taken < amount);
				if (item.getAmount()<0) {
					inv.remove(item);
				}
			}
		}
	}

}
