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
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionType;

import fr.fitzche.lgmore.Main;
import fr.fitzche.lgmore.PlayerData;
import fr.fitzche.lgmore.Lg.GameLg;
import fr.fitzche.lgmore.Util.CommandUtil;
import fr.fitzche.lgmore.Util.ItemUtil;

public class sorcierInv implements Listener{

	public Inventory inv;
	public int slot;
	
	public sorcierInv(Player p, GameLg game) {
		
		inv = Bukkit.createInventory(null, 36);
		
		ItemStack itemTp = new ItemStack(Material.POTION);
		ItemUtil.setName(itemTp, "Potion de Téléportation");
		ArrayList<String> lores = new ArrayList<String>();
		lores.add("-1 gap");
		lores.add("-3 blocs de lapis lazuli");
		lores.add("-30 redstone");
		ItemUtil.setLore(itemTp, lores);
		inv.setItem(0, itemTp);
		
		ItemStack itemPara = new ItemStack(Material.POTION);
		ItemUtil.setName(itemPara, "Potion de Paralysie");
		ArrayList<String> lores2 = new ArrayList<String>();
		lores2.add("-2 gap");
		lores2.add("-3 fils");
		lores2.add("-5 chair putréfié");
		lores2.add("-3 silex");
		ItemUtil.setLore(itemPara, lores2);
		
		inv.setItem(1, itemPara);
		
		ItemStack itemAura = new ItemStack(Material.POTION);
		ItemUtil.setName(itemAura, "Potion de Révélation d'Auras");
		ArrayList<String> lores3 = new ArrayList<String>();
		lores3.add("-30 redstone");
		lores3.add("-1 gap");
		lores3.add("-10 plumes");
		
		ItemUtil.setLore(itemAura, lores3);
		
		inv.setItem(1, itemAura);
		
		
		
		
		p.openInventory(inv);
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
		if (e.getInventory().equals(inv) && e.getCurrentItem() != null) {
			e.setCancelled(true);
			if (e.getCurrentItem().hasItemMeta() && e.getCurrentItem().getItemMeta().hasDisplayName()) {
				switch (e.getCurrentItem().getItemMeta().getDisplayName()) {
				case "Potion de Téléportation":
					if (ItemUtil.howManyOf(	inv	, Material.GOLDEN_APPLE) >= 1 && ItemUtil.howManyOf(	inv	, Material.LAPIS_BLOCK ) >= 3 &&  ItemUtil.howManyOf(	inv	, Material.REDSTONE) >= 30) {
						ItemUtil.takeInInv(inv, Material.GOLDEN_APPLE, 1);
						ItemUtil.takeInInv(inv, Material.LAPIS_BLOCK, 3);
						ItemUtil.takeInInv(inv, Material.REDSTONE	, 30);
						
						Potion potion = new Potion(PotionType.WATER_BREATHING, 1, true);
						ItemStack item = potion.toItemStack(1);
						ArrayList<String> strs = new ArrayList<String>();
						strs.add("Potion de Téléportation ");
						ItemUtil.setLore(item, strs);
						Player p = (Player) e.getWhoClicked();
						p.getInventory().addItem(item);
					} else {
						e.getWhoClicked().sendMessage("Vous n'avez pas les ingrédients necessaires");
					}
					break;
				case "Potion de Paralysie":
					if (ItemUtil.howManyOf(	inv	, Material.FLINT) >= 3&&ItemUtil.howManyOf(	inv	, Material.GOLDEN_APPLE) >= 2 && ItemUtil.howManyOf(	inv	, Material.ROTTEN_FLESH ) >= 5 &&  ItemUtil.howManyOf(	inv	, Material.STRING) >= 3) {
						ItemUtil.takeInInv(inv, Material.GOLDEN_APPLE, 2);
						ItemUtil.takeInInv(inv, Material.ROTTEN_FLESH, 5);
						ItemUtil.takeInInv(inv, Material.STRING	, 3);
						ItemUtil.takeInInv(inv, Material.FLINT	, 3);
						
						Potion potion = new Potion(PotionType.WATER_BREATHING, 1, true);
						ItemStack item = potion.toItemStack(1);
						ArrayList<String> strs = new ArrayList<String>();
						strs.add("Potion De Paralysie");
						ItemUtil.setLore(item, strs);
						Player p = (Player) e.getWhoClicked();
						p.getInventory().addItem(item);
					} else {
						e.getWhoClicked().sendMessage("Vous n'avez pas les ingrédients necessaires");
					}
					break;
				case "Potion de Révélation d'Auras":
					if (ItemUtil.howManyOf(	inv	, Material.GOLDEN_APPLE) >= 1 && ItemUtil.howManyOf(	inv	, Material.FEATHER) >= 10 &&  ItemUtil.howManyOf(	inv	, Material.REDSTONE) >= 30) {
						ItemUtil.takeInInv(inv, Material.GOLDEN_APPLE, 1);
						ItemUtil.takeInInv(inv, Material.FEATHER, 10);
						ItemUtil.takeInInv(inv, Material.REDSTONE	, 30);
						
						Potion potion = new Potion(PotionType.WATER_BREATHING, 1, true);
						ItemStack item = potion.toItemStack(1);
						ArrayList<String> strs = new ArrayList<String>();
						strs.add("Révéleur d'Aura");
						ItemUtil.setLore(item, strs);
						Player p = (Player) e.getWhoClicked();
						p.getInventory().addItem(item);
					} else {
						e.getWhoClicked().sendMessage("Vous n'avez pas les ingrédients necessaires");
					}
					break;
				}
			}
		}
	}
}
