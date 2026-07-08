package fr.fitzche.lgmore.scoreboard.Inventory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import fr.fitzche.lgmore.Main;
import fr.fitzche.lgmore.PlayerData;
import fr.fitzche.lgmore.Lg.GameLg;
import fr.fitzche.lgmore.Util.CommandUtil;
import fr.fitzche.lgmore.Util.ItemUtil;
import fr.fitzche.lgmore.bedwars.BedWarMap;
import fr.fitzche.lgmore.bedwars.Bedwars;
import fr.fitzche.lgmore.commands.SpecialItemHolder;
import fr.fitzche.lgmore.bedwars.BedLoc;
import net.md_5.bungee.api.ChatColor;

public class GeneralMenu implements Listener {

	public Inventory inv = Bukkit.createInventory(null, 36);
	
	
	public GeneralMenu(PlayerData p) {
		Bukkit.getPluginManager().registerEvents(this, Main.plug);
		
		inv.setItem(0, ItemUtil.getItem(ItemUtil.getCustomHead("Valtor91"), ChatColor.GOLD+""+ChatColor.BOLD+"Boutique", new ArrayList<String>(Arrays.asList(ChatColor.GRAY+"   ▪Boutique de grades, d'annonces de kill, et d'items"))));
		
		
		inv.setItem(11, ItemUtil.getItem(ItemUtil.getCustomHead("Elvoracitto"),ChatColor.GOLD+""+ChatColor.BOLD+ "Créer Une Partie", new ArrayList<String>(Arrays.asList(
				ChatColor.GRAY + "   ▪"+ChatColor.DARK_RED+"Loup Garou",
				ChatColor.GRAY + "   ▪"+ChatColor.DARK_BLUE+"Team Swapper"
				))));
		
		inv.setItem(13, ItemUtil.getItem(ItemUtil.getCustomHead("LeVraiFuze"), ChatColor.GOLD+""+ChatColor.BOLD+"Config", new ArrayList<String>(Arrays.asList(ChatColor.GRAY+"   ▪Configurer la partie où vous vous trouvez", ChatColor.RED+"   ✖ uniquement op ou hoster"))));
		
		inv.setItem(15, ItemUtil.getItem(ItemUtil.getCustomHead(p.getName()), ChatColor.GOLD + "" +ChatColor.BOLD+""+"Profil", new ArrayList<String>(Arrays.asList(
				ChatColor.UNDERLINE +""+ ChatColor.DARK_GREEN+"⋙  "+ ChatColor.WHITE+"Victoire / Nombre de partie Total: "+ChatColor.AQUA+""+ ChatColor.BOLD +p.getWinRate(), 
				ChatColor.DARK_GREEN+"⋙  "+ ChatColor.WHITE+p.getWinRateString(20),
				ChatColor.RED + "▪"+ ChatColor.WHITE+"xp: "+ChatColor.AQUA+ ""+ ChatColor.BOLD +p.xp , 
				ChatColor.LIGHT_PURPLE + "▪"+ChatColor.WHITE + "feather: "+ ChatColor.AQUA+""+ChatColor.BOLD +p.feathers))));

		inv.setItem(8, ItemUtil.getItem(Material.BOW, 1, ChatColor.DARK_PURPLE+ ""+ChatColor.BOLD+ "Star War Party", new ArrayList<String>(Arrays.asList(ChatColor.GRAY+"   ▪Parties courtes ) 15/16/17 joueurs", ChatColor.GRAY+"   ▪Deux camp: jedi et sith s'affrontent à roles découverts sur une petite map.", ChatColor.GRAY+"   ▪PvP pur et rapide"))));
		inv.setItem(17, ItemUtil.getItem(Material.BED, 1, ChatColor.DARK_PURPLE+ ""+ChatColor.BOLD+ "BedWar", new ArrayList<String>(Arrays.asList(ChatColor.DARK_RED+""+ChatColor.BOLD +"Bientot..."))));
		inv.setItem(34, ItemUtil.getItem(Material.ITEM_FRAME, 1, ChatColor.DARK_RED+ ""+ChatColor.BOLD+ "Configurer Inventaire Zone Pvp", new ArrayList<String>(Arrays.asList(ChatColor.DARK_RED+""+ChatColor.BOLD +"Configurer l'inventaire du monde juste pour les bagarreur,"))));
		inv.setItem(35, ItemUtil.getItem(Material.GOLD_SWORD, 1, ChatColor.DARK_RED+ ""+ChatColor.BOLD+ "Zone Pvp", new ArrayList<String>(Arrays.asList(ChatColor.DARK_RED+""+ChatColor.BOLD +"Un monde juste pour les bagarreur,",ChatColor.DARK_RED+ ""+ChatColor.BOLD+" avec quelques items et bidules pour ajouter du piment"))));
		inv.setItem(27, ItemUtil.getItem(Material.JUKEBOX, 1, ChatColor.DARK_GREEN+ ""+ChatColor.BOLD+ "Rejoindre", new ArrayList<String>(Arrays.asList(ChatColor.DARK_RED+""+ChatColor.BOLD +"Rejoindre un partie"))));

	}
	
	public void open(PlayerData p) {
		if (p.isOnline) {
			p.player.openInventory(inv);
		}
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
		if (e.getInventory().equals(this.inv)) {
			e.setCancelled(true);
			if (e.getCurrentItem().getItemMeta() == null || e.getCurrentItem().getItemMeta().getDisplayName() == null) {
				System.out.println("nul l.66 GeneralMenuInv");
				return;
				
			}
			PlayerData p = Main.getData(e.getWhoClicked());
			if ((e.getCurrentItem().getItemMeta().getDisplayName()).equals(ChatColor.GOLD+""+ChatColor.BOLD+"Créer Une Partie")) {
				System.out.println("open 71 GeneralMenu");
				if (p.game == null && (p.hoster || p.getName().equals("FITZCHE") || p.getName().equals("Fitzche"))) {
					GameTypeChoose chose = new GameTypeChoose();
					chose.open(p, e.getInventory());
					System.out.println("open l.75 GeneralMenu");
				}
			} else if ((e.getCurrentItem().getItemMeta().getDisplayName()).equals( ChatColor.GOLD+""+ChatColor.BOLD+"Config")) {
				if (p.game != null && (p.hoster || p.Name.equals("FITZCHE") || p.Name.equals("Fitzche"))) {
					CommandUtil.runCommand("lga", (Player) e.getWhoClicked(), new String[] {"Game", "config", p.game.getName()});
					
				}
			}else if ((e.getCurrentItem().getItemMeta().getDisplayName()).equals( ChatColor.GOLD+""+ChatColor.BOLD+"Boutique")) {
				ShopInv shop = new ShopInv(p, inv);
				
			}else if (e.getCurrentItem().getItemMeta().getDisplayName().equals( ChatColor.GOLD + "" +ChatColor.BOLD+""+"Profil")) {
				CommandUtil.runCommand("lg", (Player) e.getWhoClicked(), new String[] {"stat", p.getName()});

			}else if (e.getCurrentItem().getItemMeta().getDisplayName().equals( ChatColor.DARK_PURPLE+ ""+ChatColor.BOLD+ "Star War Party")) {
				CommandUtil.runCommand("star", (Player) e.getWhoClicked(), new String[] {"play"});

			}else if (e.getCurrentItem().getItemMeta().getDisplayName().equals( ChatColor.DARK_GREEN+ ""+ChatColor.BOLD+ "Rejoindre")) {
				JoinChoose chose = new JoinChoose(Main.getData(e.getWhoClicked()), inv);

			}else if (e.getCurrentItem().getItemMeta().getDisplayName().equals( ChatColor.DARK_RED+ ""+ChatColor.BOLD+ "Configurer Inventaire Zone Pvp")) {
				InvFunctSlotPref pref = new InvFunctSlotPref();
				pref.openMainMenu(p);
				
			}else if (e.getCurrentItem().getItemMeta().getDisplayName().equals( ChatColor.DARK_RED+ ""+ChatColor.BOLD+ "Zone Pvp")) {
				
				WorldCreator c = new WorldCreator("pvpWorld");
				World worldPvP = Bukkit.createWorld(c);
				e.getWhoClicked().teleport(worldPvP.getSpawnLocation());
				
				
				PlayerData clicker = Main.getData(e.getWhoClicked());
				HashMap<String, Integer> slotPref = clicker.__slotPref__;
				
				
				
				
				if (slotPref == null) slotPref = clicker.__slotPref__ = new HashMap<>();

				// Valeurs par défaut si une clé manque
				slotPref.putIfAbsent("sword",      0);
				slotPref.putIfAbsent("bow",        1);
				slotPref.putIfAbsent("arrow",      20);
				slotPref.putIfAbsent("gap",        3);
				slotPref.putIfAbsent("beef",       4);
				slotPref.putIfAbsent("water",      5);
				slotPref.putIfAbsent("stone1",     6);
				slotPref.putIfAbsent("stone2",     7);
				slotPref.putIfAbsent("stone3",     8);
				slotPref.putIfAbsent("stone4",     9);
				slotPref.putIfAbsent("helmet",     36);
				slotPref.putIfAbsent("chestplate", 37);
				slotPref.putIfAbsent("legging",    38);
				slotPref.putIfAbsent("boots",      39);
				e.getWhoClicked().getInventory().setItem(slotPref.get("arrow"), ItemUtil.addEnchant(new ItemStack(Material.BOW, 1), Enchantment.ARROW_DAMAGE, 2));

				e.getWhoClicked().getInventory().setItem(slotPref.get("bow"), ItemUtil.addEnchant(new ItemStack(Material.BOW, 1), Enchantment.ARROW_DAMAGE, 2));
				e.getWhoClicked().getInventory().setItem(slotPref.get("helmet"), ItemUtil.addEnchant(new ItemStack(Material.DIAMOND_HELMET, 1), Enchantment.PROTECTION_ENVIRONMENTAL, 2));
				e.getWhoClicked().getInventory().setItem(slotPref.get("chestplate"), ItemUtil.addEnchant(new ItemStack(Material.DIAMOND_CHESTPLATE, 1), Enchantment.PROTECTION_ENVIRONMENTAL, 2));
				e.getWhoClicked().getInventory().setItem(slotPref.get("legging"), ItemUtil.addEnchant(new ItemStack(Material.IRON_LEGGINGS, 1), Enchantment.PROTECTION_ENVIRONMENTAL, 3));
				e.getWhoClicked().getInventory().setItem(slotPref.get("boots"), ItemUtil.addEnchant(new ItemStack(Material.IRON_BOOTS, 1), Enchantment.PROTECTION_ENVIRONMENTAL, 3));
				e.getWhoClicked().getInventory().setItem(slotPref.get("gap"), new ItemStack(Material.GOLDEN_APPLE, 16));
				e.getWhoClicked().getInventory().setItem(slotPref.get("sword"), ItemUtil.addEnchant(new ItemStack(Material.DIAMOND_SWORD, 1), Enchantment.DAMAGE_ALL, 3));
				e.getWhoClicked().getInventory().setItem(slotPref.get("water"), new ItemStack(Material.WATER_BUCKET));
				e.getWhoClicked().getInventory().setItem(slotPref.get("beef"), new ItemStack(Material.COOKED_BEEF, 64));
				e.getWhoClicked().getInventory().setItem(slotPref.get("stone1"), new ItemStack(Material.STONE, 64));
				e.getWhoClicked().getInventory().setItem(slotPref.get("stone2"), new ItemStack(Material.STONE, 64));
				e.getWhoClicked().getInventory().setItem(slotPref.get("stone3"), new ItemStack(Material.STONE, 64));
				e.getWhoClicked().getInventory().setItem(slotPref.get("stone4"), new ItemStack(Material.STONE, 64));
				CommandUtil.runCommand("lg", (Player) e.getWhoClicked(), new String[] {"openSpecialInvItem"});
				
				
			}else if (e.getCurrentItem().getItemMeta().getDisplayName().equals( ChatColor.DARK_PURPLE+ ""+ChatColor.BOLD+ "BedWar")) {
				p.sendMessage("bloqué temporairement, et si t pas content, et bah faut DEGAGER !!!");
				boolean v = true;
				if (v) {return;}
				if (p.game != null) {
					p.sendMessage("Vous êtes déjà dans une partie");
				}
				
				for (Bedwars bed:Main.bedwars) {
					if (bed.nbOfPlayers > bed.players.size()) {
						bed.addPlayer(p);
						System.out.println("player added to bedwar in GeneralMenu: //YHGRG//");
						return;
					} else {
						p.sendMessage("Plus de Place: "+ bed.players.size() + "/"+bed.nbOfPlayers);
					}
					
				}
				p.sendMessage("Aucune partie n'est libre, veuillez attendre quelque secondes en attendant la création de celle-ci");

				Bukkit.getScheduler().runTask(Main.plug, new BukkitRunnable() {

					@Override
					public void run() {
						Bedwars bed = new Bedwars(new BedWarMap("bedMap", 1, new ArrayList<BedLoc>(), "maptest", 1));
						Main.bedwars.add(bed);
						
					}
					
				});
				
				

			}
			
		}
	}
	
	
	

}
