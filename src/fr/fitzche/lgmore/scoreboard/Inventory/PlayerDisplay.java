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
import org.bukkit.inventory.meta.SkullMeta;

import fr.fitzche.lgmore.GameLg;
import fr.fitzche.lgmore.Main;
import fr.fitzche.lgmore.PlayerData;
import fr.fitzche.lgmore.Util.CommandUtil;
import net.md_5.bungee.api.ChatColor;

public class PlayerDisplay implements Listener{
	public Inventory inv;
	public GameLg gm;
	
	public PlayerDisplay(GameLg gm) {
		
	this.gm = gm;
		
	ArrayList<Player> players = new ArrayList<Player>();
	for (Player pl: fr.fitzche.lgmore.Main.server.getOnlinePlayers()) {
		players.add(pl);
	}
	
	int x = 0;
	this.inv = Bukkit.createInventory(null, 36, "Joueurs");
	Main.server.getPluginManager().registerEvents(this, Main.plug);
	//System.out.println("b.1.1");

	for (Player player: players) {

		 		//System.out.println("b.1.0");
		
				ItemStack head = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
				SkullMeta meta = (SkullMeta) head.getItemMeta();
				
				meta.setOwner(player.getName());
				meta.setDisplayName(player.getName());
				ArrayList<String> l = new ArrayList<String>();
				
				//System.out.println("mm3.0");
				
				boolean present = false;
				if (gm.ListPlayer().contains(player.getName())) {
					present = true;
					//System.out.println(gm.ListPlayer() + " contain "+ player.getName());
				} else {
					//System.out.println(gm.ListPlayer() + " not contain "+ player.getName());

				}
				
				if (gm.players.size() > 0) {
					//System.out.println("aucun joueur");
				}
				//System.out.println("mm2");

				
				if (present) {
					l.add("ce joueur est présent dans cette partie");
					//System.out.println("present ici");
				} else {
					l.add("ce joueur n'est pas présent dans cette partie");
					//System.out.println("no present ici");

				}
				//System.out.println("mm1");

				meta.setLore(l);
				head.setItemMeta(meta);
				
				this.inv.setItem(x, head);
				x++;
			}
	
			ItemStack returne = new ItemStack(Material.BARRIER) ;
			ItemMeta returneM = returne.getItemMeta();
			returneM.setDisplayName(ChatColor.ITALIC + "Retour");
			returne.setItemMeta(returneM);
			this.inv.setItem(35, returne);
		}
	
	public void open(Player ply) {
		ply.closeInventory();
		ply.openInventory(inv);
	}
	
	
	public void refresh(Player ply) {
		PlayerDisplay ag = new PlayerDisplay(this.gm);
		ply.closeInventory();
		ag.open(ply);
	}
	
	@Deprecated
	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
		if (e.getInventory().equals(this.inv)) {
			e.setCancelled(true);
			
			if (e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.ITALIC + "Retour")) {
				ConfigDisplay j = new ConfigDisplay(gm);
				j.open((Player) e.getWhoClicked());
				return;
			}
			
			for (ItemStack item :this.inv.getContents()) {
				//System.out.println("testedk");
				if (item != null && item.getItemMeta().getLore() != null) {
					if (item.getItemMeta().getDisplayName().equals(e.getCurrentItem().getItemMeta().getDisplayName()) ) {
						//System.out.println("equal");
						
						if (item.getItemMeta().getLore().get(0).equals("ce joueur est présent dans cette partie")) {
							
							String[] args = new String[] {"Game", "removePlayer",this.gm.name, item.getItemMeta().getDisplayName()};
							CommandUtil.runCommand("lga", (Player) e.getWhoClicked(), args);
							//System.out.println("present");

						} else {

							String[] args = new String[] {"Game", "addPlayer",this.gm.name, item.getItemMeta().getDisplayName()};
							CommandUtil.runCommand("lga", (Player) e.getWhoClicked(), args);
							//System.out.println("no present");
						}
					}
				}
				
				
			}
			ConfigDisplay j = new ConfigDisplay(gm);
			j.open((Player) e.getWhoClicked());
			return;
			
		}
		
		
		//System.out.println("refreshed");
	}
	
	
}
