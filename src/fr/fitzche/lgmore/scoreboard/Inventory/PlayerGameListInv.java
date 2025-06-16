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

import fr.fitzche.lgmore.Main;
import fr.fitzche.lgmore.PlayerData;
import fr.fitzche.lgmore.Lg.GameLg;
import fr.fitzche.lgmore.Lg.GameNote;
import fr.fitzche.lgmore.Util.ItemUtil;
import net.md_5.bungee.api.ChatColor;

import org.bukkit.entity.Player;

public class PlayerGameListInv implements InvFunct{

	
	
	public ArrayList<GameNote> notes = new ArrayList<GameNote>();
	
	
	
	@SuppressWarnings("unchecked")
	public PlayerGameListInv(PlayerData p, Inventory backInv, Player opener) {
		
		this.notes = (ArrayList<GameNote>) p.notes.clone();
		ArrayList<ItemStack> items = new ArrayList<ItemStack>();
		
		for (GameNote note:notes) {
			ItemStack item = new ItemStack(Material.BOOK);
			String name = ChatColor.RED + note.name;
			if (note.winners.contains(p.getName())) {
				name = name + ChatColor.GOLD + " (victoire)";
			}
			ItemUtil.setName(item, name);
			String role = "";
			role = note.getStrRole(p.getName());
			ItemUtil.setLore(item, new ArrayList<String>(Arrays.asList(role)));
			items.add(item);
			
		}
		
		
		
		StringChooseInv inv = new StringChooseInv(p, items, backInv, this);
		if (inv.invs.size() > 0) {
			opener.openInventory(inv.invs.get(0));
		} else {
			opener.sendMessage("Erreur");
		}
	}
	
	
	
	@Override
	public void click(PlayerData p, GameLg game, String clickedName, ArrayList<String> lores) {
		int x = -1;
		x = Integer.valueOf(lores.get(0));
		if (lores != null && lores.size() > 0 && x >= 0 && p.isOnline) {
			this.notes.get(x).open(p.player);
		}
	}
}
