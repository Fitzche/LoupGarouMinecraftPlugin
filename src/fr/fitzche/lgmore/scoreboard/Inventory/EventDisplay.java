package fr.fitzche.lgmore.scoreboard.Inventory;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import fr.fitzche.lgmore.GameLg;
import fr.fitzche.lgmore.Main;
import fr.fitzche.lgmore.PlayerData;
import fr.fitzche.lgmore.Util.CommandUtil;
import fr.fitzche.lgmore.Util.ItemUtil;
import net.md_5.bungee.api.ChatColor;

public class EventDisplay implements Listener{
	ArrayList<Inventory> invs = new ArrayList<Inventory>();
	public GameLg gm;
	
	public EventDisplay(GameLg gm) {
		
	this.gm = gm;
		
	
	
	int x = 0;
	
	Main.server.getPluginManager().registerEvents(this, Main.plug);
	//System.out.println("b.1.");
	int done =Main.eventsNames.size();

    for (int i = Main.eventsNames.size(); i>0; i =- 9) {
        Inventory inv = Bukkit.createInventory(null, 36);
       
        int end = 8+(done-i);
        List<String> firstEventsNames;
        if (Main.eventsNames.size()< end +1) {
            firstEventsNames = Main.eventsNames.subList(0 + (done -i), Main.eventsNames.size() - 1);

        } else {
        	firstEventsNames = Main.eventsNames.subList(0 + (done -i), 8+(done-i));

        }
        for (String eName:firstEventsNames) {
            ItemStack item = new ItemStack(Material.NAME_TAG);
            ItemUtil.setName(item, eName);
            inv.setItem(firstEventsNames.indexOf(eName) + 1, item);
            ItemStack itemAdd1 = new ItemStack(Material.IRON_BLOCK);
            ItemUtil.setName(itemAdd1, eName);
            ArrayList<String> lores= new ArrayList<String>();
            lores.add("gauche +1/-1 droite");
            ItemUtil.setLore(itemAdd1, lores);
            inv.setItem(firstEventsNames.indexOf(eName) + 1, itemAdd1);

            ItemStack itemAdd10 = new ItemStack(Material.IRON_BLOCK);
            ItemUtil.setName(itemAdd10, eName);
            ArrayList<String> lores10 = new ArrayList<String>();
            lores10.add("gauche +10/10 droite");
            ItemUtil.setLore(itemAdd10, lores10);
            inv.setItem(firstEventsNames.indexOf(eName) + 1, itemAdd10);

        }

        ItemStack back = new ItemStack(Material.ARROW);
        ItemUtil.setName(back, "précédent");
        inv.setItem(35, back);

        ItemStack next = new ItemStack(Material.ARROW);
        ItemUtil.setName(next, "suivant");
        inv.setItem(36, next);

        ItemStack returnItem = new ItemStack(Material.ARROW);
        ItemUtil.setName(returnItem, "suivant");
        inv.setItem(28, returnItem);
    }



    }
	
	public void open(Player ply) {
		ply.closeInventory();
		ply.openInventory(invs.get(0));
	}
	
	
	public void refresh(Player ply) {
		PlayerDisplay ag = new PlayerDisplay(this.gm);
		ply.closeInventory();
		ag.open(ply);
	}
	
	@Deprecated
	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
            for (Inventory inv: this.invs) {
                if (e.getInventory().equals(inv)); {
                    e.setCancelled(true);
                    for (String str: Main.eventsNames) {
                        if (e.getCurrentItem().getItemMeta().getDisplayName().equals(str)) {
                            switch(e.getCurrentItem().getItemMeta().getLore().get(0)) {
                                case "gauche +1/-1 droite":
                                    if (e.getClick().equals(ClickType.LEFT)) {
                                        gm.probasEvents.put(str, gm.probasEvents.get(str) +1);
                                    } else if (e.getClick().equals(ClickType.RIGHT)) {
                                        gm.probasEvents.put(str, gm.probasEvents.get(str) -1);
                                    }
                                case "gauche +10/-10 droite":
                                    if (e.getClick().equals(ClickType.LEFT)) {
                                        gm.probasEvents.put(str, gm.probasEvents.get(str) +10);

                                    } else if (e.getClick().equals(ClickType.RIGHT)) {
                                        gm.probasEvents.put(str, gm.probasEvents.get(str) -10);
                                    
                                    }
                            }
                        }
                    }


                    if (e.getCurrentItem().getItemMeta().getDisplayName().equals("précédent")) {
                        if ((this.invs.indexOf(e.getInventory()) - 1) < 0) {
                            return;
                        }
                        e.getWhoClicked().openInventory(this.invs.get(this.invs.indexOf(e.getInventory()) - 1));
                    } else if (e.getCurrentItem().getItemMeta().getDisplayName().equals("suivant")) {
                        
                        if ((this.invs.indexOf(e.getInventory()) + 1) > this.invs.size()) {
                            return;
                        }
                        e.getWhoClicked().openInventory(this.invs.get(this.invs.indexOf(e.getInventory()) - 1));
                    } else if (e.getCurrentItem().getItemMeta().getDisplayName().equals("retour")) {
                        ConfigDisplay config = new ConfigDisplay(gm);
                        config.open((Player) e.getWhoClicked());
                    }
                } 
            }
			
		}
		
		
		//System.out.println("refreshed");
	}
	
	

