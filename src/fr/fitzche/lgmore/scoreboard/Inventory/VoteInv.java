package fr.fitzche.lgmore.scoreboard.Inventory;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import fr.fitzche.lgmore.Main;
import fr.fitzche.lgmore.PlayerData;
import fr.fitzche.lgmore.Lg.GameLg;
import fr.fitzche.lgmore.Util.CommandUtil;

public class VoteInv implements Listener{

	public Inventory inv;
	public int slot;
	
	public VoteInv(Player p, GameLg game, int slot) {
		this.slot = slot;
		Main.server.getPluginManager().registerEvents(this, Main.plug);
		this.inv = Bukkit.createInventory(null, 90);
		int x = 0;
		for (PlayerData player: game.getPlayerAlive()) {
			
			ItemStack head = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
			SkullMeta meta = (SkullMeta) head.getItemMeta();
			
			meta.setOwner(player.getName());
			meta.setDisplayName(player.Name);
			head.setItemMeta(meta);
			this.inv.setItem(x, head);
			x++;
		}
		
		
		
		p.openInventory(inv);
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
		if (e.getInventory().equals(inv) && e.getCurrentItem() != null) {
			e.setCancelled(true);
			String[] strs = {"voteCmd",e.getCurrentItem().getItemMeta().getDisplayName(), Integer.toString(slot)};
			
			
			CommandUtil.runCommand("lg",(Player) e.getWhoClicked(), strs);
		}
	}
}
