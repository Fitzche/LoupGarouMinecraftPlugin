package fr.fitzche.lgmore.scoreboard.Inventory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.sk89q.util.StringUtil;

import fr.fitzche.lgmore.Main;
import fr.fitzche.lgmore.PlayerData;

import fr.fitzche.lgmore.Util.ItemUtil;
import net.md_5.bungee.api.ChatColor;


public class StringChooseInv implements Listener{

	
	
	public ArrayList<ItemStack> notes = new ArrayList<ItemStack>();
	public ArrayList<Inventory> invs = new ArrayList<Inventory>();
	public ArrayList<ItemStack> gameNotes = new ArrayList<ItemStack>();
	public Inventory backInv;
	public InvFunct functer;
	
	@SuppressWarnings("unchecked")
	public StringChooseInv(PlayerData p, ArrayList<ItemStack> list, Inventory backInv, InvFunct functer) {
		Main.server.getPluginManager().registerEvents(this, Main.plug);
		this.notes = list;
		this.backInv = backInv;
		this.functer = functer;
		if (notes.size() <1) {
			return;
		}
		int reper = 0;
		ArrayList<ItemStack> stock = new ArrayList<ItemStack>();
		do {
			if (reper == 29) {
				addInv(stock);
				stock = new ArrayList<ItemStack>();
				
			}
			
			//ItemStack item = new ItemStack(Material.PAPER);
			ItemStack item = notes.get(0);
			String s = ChatColor.MAGIC + Integer.toString(gameNotes.size());
			ArrayList<String> toSet = new ArrayList<String>();
			toSet.add(s);
			if (notes.get(0).getItemMeta().getLore() != null) {
				
				for (String str:notes.get(0).getItemMeta().getLore()) {
					toSet.add(str);
				}
				
			}
			ItemUtil.setLore(item, toSet);
			ItemUtil.setName(item, notes.get(0).getItemMeta().getDisplayName());
			stock.add(item);
			gameNotes.add(notes.get(0));
			notes.remove(0);
			reper++;
			
		} while (notes.size() > 0);
		if (stock.size() > 0) {
			addInv(stock);
			stock = new ArrayList<ItemStack>();
		}
		notes = gameNotes;
		gameNotes = new ArrayList<ItemStack>();
		if (this.invs.size() > 0 && p.isOnline) {
			p.player.openInventory(invs.get(0));
		}
	} 
	
	public void addInv(ArrayList<ItemStack> stock) {
		Inventory toAdd = Bukkit.createInventory(null, 36);
		for (ItemStack item: stock) {
			toAdd.setItem(stock.indexOf(item), item);
		}
		ItemStack itemNext = new ItemStack(Material.BARRIER);
		ItemStack itemPre = new ItemStack(Material.BARRIER);
		ItemStack itemRet = new ItemStack(Material.BARRIER);
		
		
		ItemUtil.setName(itemPre, "Precedent");
		ItemUtil.setName(itemNext, "Suivant");
		ItemUtil.setName(itemRet, "Retour");
		
		toAdd.setItem(34, itemPre);
		toAdd.setItem(35, itemNext);
		toAdd.setItem(33, itemRet);
		invs.add(toAdd);
		
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		if (invs.contains(event.getInventory())) {
			
			event.setCancelled(true);
			
			if (event.getCurrentItem() != null &&
					event.getCurrentItem().getItemMeta() != null &&
					event.getCurrentItem().getItemMeta().getDisplayName() != null &&
					event.getCurrentItem().getItemMeta().getDisplayName().equals("Precedent")) {
				
				event.getWhoClicked().openInventory(invs.get(invs.indexOf(event.getInventory()) - 1));
				return;
				
			} else if (event.getCurrentItem() != null && event.getCurrentItem().hasItemMeta() &&event.getCurrentItem().getItemMeta().hasDisplayName() && event.getCurrentItem().getItemMeta().getDisplayName().equals("Suivant") && invs.indexOf(event.getInventory()) + 1 < invs.size()) {
				event.getWhoClicked().openInventory(invs.get(invs.indexOf(event.getInventory()) + 1));
				return;
			}else if (event.getCurrentItem() != null && event.getCurrentItem().getItemMeta() != null && event.getCurrentItem().getItemMeta().hasDisplayName()&& event.getCurrentItem().getItemMeta().getDisplayName().equals("Retour")) {
				event.getWhoClicked().openInventory(backInv);
				return;
			}
			
			if (event.getCurrentItem() == null || event.getCurrentItem().getItemMeta() == null || event.getCurrentItem().getItemMeta().getLore() == null || event.getCurrentItem().getItemMeta().getLore().size() < 1) {
				System.out.println("item not performent, l.83 onInventoryClick StringChooseInv");
				return;
				
			} else {
				
				
				
				if (Integer.valueOf(event.getCurrentItem().getItemMeta().getLore().get(0).substring(2, event.getCurrentItem().getItemMeta().getLore().get(0).length())) != null){
					PlayerData p = Main.getData(event.getWhoClicked());
					
					ArrayList<String> lores = new ArrayList<String>();
					for (String s:event.getCurrentItem().getItemMeta().getLore()) {
						lores.add(s);
					}
					String name = event.getCurrentItem().toString();
					if (event.getCurrentItem().getItemMeta().hasDisplayName()) {
						name = event.getCurrentItem().getItemMeta().getDisplayName();
					}
					this.functer.click(p, p.game, name, lores, event.getCurrentItem());
					System.out.println("click of "+ p.getName() + " on "+name);
					

				}
			}
		}
	}
}
