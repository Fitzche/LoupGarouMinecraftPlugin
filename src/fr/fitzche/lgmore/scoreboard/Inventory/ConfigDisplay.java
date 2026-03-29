package fr.fitzche.lgmore.scoreboard.Inventory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.fitzche.lgmore.Main;
import fr.fitzche.lgmore.PlayerData;
import fr.fitzche.lgmore.Lg.GameLg;
import fr.fitzche.lgmore.Util.CommandUtil;
import fr.fitzche.lgmore.Util.ItemUtil;
import fr.fitzche.lgmore.commands.Lga;
import net.minecraft.server.v1_8_R3.CommandDispatcher;

public class ConfigDisplay implements Listener{
	public Inventory inv;
	public GameLg game;
	HashMap<String, Boolean> areOp = new HashMap<String, Boolean>();
	
	public ConfigDisplay(GameLg game) {
		Main.server.getPluginManager().registerEvents(this, Main.plug);

		this.game = game;
		setItems();
	}
	
	public void setItems() {
		Inventory config = Bukkit.createInventory(null, 36, "Config");
		
		
		
		
		
		
		config.setItem(10, ItemUtil.getItem(Material.ENCHANTED_BOOK, 0,ChatColor.BLUE +""+ChatColor.BOLD+ "Compo", new ArrayList<String>(Arrays.asList(ChatColor.DARK_RED+"   ▪Loup-Garou", ChatColor.GOLD+"   ▪Solo", ChatColor.DARK_GREEN+"   ▪Villageois"))));
		
		ItemStack sword = ItemUtil.getItem(Material.DIAMOND_SWORD, 1, ChatColor.DARK_BLUE + ""+ChatColor.BOLD + "Joueur", new ArrayList<String>(Arrays.asList(ChatColor.GRAY+"   ▪"+ChatColor.AQUA+"Liste des joueurs présents")));
		ItemUtil.hideAttributes(sword);
		config.setItem(13, sword);
		
		
		config.setItem(16, ItemUtil.getItem(Material.EMERALD, 0, ChatColor.BLUE + "" + ChatColor.BOLD+ "Event", new ArrayList<String>(Arrays.asList(ChatColor.GRAY+"   ▪Configurez les events"))));
		
		
		
		config.setItem(25, ItemUtil.getItem(Material.PAPER,  0, ChatColor.BLUE + ""+ ChatColor.BOLD + "Scénarios", new ArrayList<String>(Arrays.asList(ChatColor.GRAY+"   ▪Configurez les scénarios"))));
		
		int x = game.roles.size();
		int y = game.getActualNbOfPlayer();
		String l = ChatColor.DARK_GREEN + "✓ Début de la partie";
		if (x != y) {
			l = ChatColor.DARK_RED +"✖ ⋙ " + x + " roles pour "+ y + " joueurs !";
		}
		config.setItem(27, ItemUtil.getItem(Material.COMMAND_MINECART, 1,ChatColor.GOLD + ""+ChatColor.BOLD + "Start" ,new ArrayList<String>(Arrays.asList(l))));
		
		
		config.setItem(28, ItemUtil.getItem(Material.COMMAND_MINECART, 1, ChatColor.DARK_GREEN + "" + ChatColor.BOLD+"Générer", new ArrayList<String>(Arrays.asList(ChatColor.GRAY+"   ▪"+ChatColor.DARK_GREEN+ "Lancer la génération du monde pour la partie"))));
		
		config.setItem(24, ItemUtil.getItem(Material.FEATHER, 1, ChatColor.DARK_BLUE + ""+ChatColor.BOLD+ "Preset", new ArrayList<String>(Arrays.asList(ChatColor.GRAY+"⋙Presets pour faciliter la configuration de la partie", ChatColor.GRAY+"     ▪"+ChatColor.WHITE+" Classic"))));
	
		config.setItem(29, ItemUtil.getItem(Material.FEATHER, 1, ChatColor.BOLD+""+ChatColor.DARK_RED+ "Meetup", new ArrayList<String>(Arrays.asList(ChatColor.GRAY+"Un stuff est donné au départ", ChatColor.GRAY+"Le timer est avancé à 40min instantannement"))));
		
		
		
		
		config.setItem(35, ItemUtil.getItem(Material.BARRIER, -1, ChatColor.DARK_RED +"✖ Return ✖", new ArrayList<String>()));
		
		this.inv = config;
	}
	
	public void open(Player p, boolean isOp) {
		areOp.put(p.getName(), isOp);
		setItems();
		p.openInventory(this.inv);
	}
	
	
	@EventHandler
	@Deprecated
	public void onInventoryClick(InventoryClickEvent e) {
		if (e.getInventory().equals(this.inv)) {
			e.setCancelled(true);
			
			if (e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.BLUE +""+ChatColor.BOLD+ "Compo")) {
				
				this.game.compo.open((Player) e.getWhoClicked(), areOp.get(e.getWhoClicked().getName()));
			} else if (e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.DARK_BLUE + ""+ChatColor.BOLD + "Joueur")) {
				
				this.game.plys.open((Player) e.getWhoClicked(), areOp.get(e.getWhoClicked().getName()));
			} else if (e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.DARK_GREEN + "" + ChatColor.BOLD+"Générer")) {
				
				Lga lga = new Lga();
				lga.onCommand(e.getWhoClicked(), null, "génération lancée", new String[] {"generer"});
				
				
			} else if (e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.BLUE + ""+ ChatColor.BOLD + "Scénarios")) {
				
				ScenarioInv inv = new ScenarioInv(game);
				inv.setInv(Main.getData(e.getWhoClicked()), e.getInventory());
			}else if (e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.GOLD + ""+ChatColor.BOLD + "Start")) {
				if (!areOp.get(e.getWhoClicked().getName())) {
					return;
				}
				
				String[] args = new String[] {"Game", "start", game.name};
				CommandUtil.runCommand("lga", (Player) e.getWhoClicked(), args);
				e.getWhoClicked().closeInventory();
			} else if (e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.BLUE + "" + ChatColor.BOLD+ "Event")) {
				
				this.game.events.open((Player)e.getWhoClicked(), areOp.get(e.getWhoClicked().getName()));
				
			} else if (e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.DARK_GREEN+""+ChatColor.BOLD + "Meetup")) {
				this.game.isMeetup = !this.game.isMeetup;
				if (this.game.isMeetup) {
					e.getWhoClicked().sendMessage("La game "+ this.game.name + " est réglé sur: meetup activé");
				} else {
					e.getWhoClicked().sendMessage("La game "+ this.game.name + " est réglé sur: meetup désactivé");

				}
			}else if (e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.DARK_BLUE + ""+ChatColor.BOLD+ "Preset")) {
				PresetFunct pre = new PresetFunct(Main.getData(e.getWhoClicked()), e.getInventory());
			} else if (e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.DARK_RED +"✖ Return ✖")) {
				PlayerData p = Main.getData(e.getWhoClicked());
				(new GeneralMenu(p) ).open(p);
			}
			
			
			
		}
		
	}
	
	
}
