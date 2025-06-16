package fr.fitzche.lgmore.minecraft;

import java.rmi.server.Skeleton;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.Executors;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.swing.text.html.parser.Entity;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import com.avaje.ebeaninternal.server.deploy.BeanDescriptor.EntityType;
import com.google.common.util.concurrent.AbstractScheduledService.Scheduler;
import com.mysql.jdbc.Util;

import fr.fitzche.lgmore.Main;
import fr.fitzche.lgmore.PlayerData;
import fr.fitzche.lgmore.Lg.GameLg;
import fr.fitzche.lgmore.Camp;
import fr.fitzche.lgmore.GameStatut;
import fr.fitzche.lgmore.RolesLg.ANCIEN;
import fr.fitzche.lgmore.RolesLg.ANGE;
import fr.fitzche.lgmore.RolesLg.ENFANT_SAUVAGE;
import fr.fitzche.lgmore.RolesLg.IDIOT_DU_VILLAGE;
import fr.fitzche.lgmore.RolesLg.LOUP_BARBARE;
import fr.fitzche.lgmore.RolesLg.LOUP_METAMORPHE;
import fr.fitzche.lgmore.RolesLg.LOUP_MYSTIQUE;
import fr.fitzche.lgmore.RolesLg.PARRAIN;
import fr.fitzche.lgmore.RolesLg.PYROMANE;
import fr.fitzche.lgmore.RolesLg.RoleDisplay;
import fr.fitzche.lgmore.RolesLg.RolesLg;
import fr.fitzche.lgmore.RolesLg.SOEUR;
import fr.fitzche.lgmore.RolesLg.SWAPPER;
import fr.fitzche.lgmore.RolesLg.VOLEUR;
import fr.fitzche.lgmore.Util.*;
import fr.fitzche.lgmore.commands.Lg;
import fr.fitzche.lgmore.commands.Lga;
import fr.fitzche.lgmore.scoreboard.Inventory.VoteInv;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;


public class mcListeners implements Listener {
	
	
	@EventHandler
	@Deprecated
	public void onPlayerDeath(PlayerDeathEvent e ) {
		//MET EN PLACE LOG
		StringBuilder log = new StringBuilder();
		System.out.println("test death event version l.58");
		log.append("//DEATH EVENT//"+ "\n");
		
		
		//CHECK EXISTENCES KILLER/ KILLED
		if (e.getEntity().getKiller() != null) {
			log.append("Player "+ e.getEntity().getName() + " killed by "+ e.getEntity().getKiller().getName()+"\n");

		} else {
			log.append("Killer null"+"\n");
		}
		final Player killer;
		final Player killed = e.getEntity();
		if (e.getEntity().getKiller() instanceof Player) {
			killer = e.getEntity().getKiller();
		} else if (e.getEntity().getKiller() instanceof Arrow && ((Arrow) e.getEntity().getKiller()).getShooter() instanceof Player) {
			killer = (Player) ((Arrow) e.getEntity().getKiller()).getShooter();
		} else {
			killer = null;
		}
		String str = "";
		if (killer != null) {
			str = killer.getName();	
		}
		
		final PlayerData killerData = Main.strToPlayer.getOrDefault(str, null);
		
		
		final PlayerData killedData = Main.strToPlayer.getOrDefault(killed.getName(), null);
		
		
		
		
		if (killedData == null || (killer != null && killerData == null)) {
			log.append("A Player of the death isn't registred"+"\n");
			System.out.println(log);
			return;
		}
		
		
		
		if (killedData.game != null) {
			log.append("game of dead is not null" + "\n");
		}
		
		
		//RECUP INFOS
		Location loc = e.getEntity().getPlayer().getLocation();
		
		
		ArrayList<ItemStack> items = new ArrayList<ItemStack>();
		for (ItemStack item:e.getEntity().getPlayer().getInventory().getArmorContents()) {
			items.add(item);
		}
		for (ItemStack item:e.getEntity().getPlayer().getInventory().getContents()) {
			items.add(item);
		}
		//
		
		
		//VERIFIE UN CONTEXTE PAS ENCORE COMMENCE
		
		//
		
		
		//UTIL
		e.getEntity().getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 200, 100, false, false));

		e.setKeepInventory(true);
		e.setDeathMessage("");
		if (killedData.game != null && killerData.game != null && killedData.role != null && killerData.role != null) {
			log.append("killed: "+ killedData.role.getName()+ "; "+killedData.getName());
		}
		
		
		if (killer == null) {
			log.append("killer null"+ "\n");
		
		} else {
			if (killedData.game != null && killerData.game != null && killedData.role != null && killerData.role != null) {
				log.append("killer: "+ killerData.role.getName()+ "; "+killerData.getName());

			}
		}
		//
		
		
		GameLg game = killedData.game;
		if (game != null) {
			
		
		//A REMPLACER, INUTILE, UTILISER DES CHECKERS
		
		
		
		//CHECKER AV MORT
			if (killedData.game != null && killerData.game != null && killerData.game.equals(killedData.game)) {
				killedData.game.listener.rez(killedData, killerData, loc, e);
			} 
		
		
		
			int k = 200;
			System.out.println(log);
			Bukkit.getScheduler().runTaskLater(Main.plug, new BukkitRunnable() {
	
			@Override
			public void run() {
				if (killedData.game != null && killerData.game != null && killerData.game.equals(killedData.game)) {
					killedData.game.listener.rez2(killedData, killerData, loc, e, items);
				} 
					
			        
			   }
			}, k);
		}
		
	
	}
	
	
	@EventHandler 
	public void InventoryClickEvent(InventoryClickEvent event) {
	
	//	System.out.println("interact");
		if ( event.getInventory() != null && event.getInventory().equals(RoleDisplay.myInventory)||event.getInventory() != null&&event.getInventory().equals(PlayerUtil.playersInv)) {
			event.setCancelled(true);
			
			System.out.println("player can't do that");
		}
	}
	
	
	
	
	@EventHandler
	public void onPlayerRespawn(PlayerRespawnEvent e) {
		
		
	}
	
	@Deprecated
	@EventHandler
	public void onPlayerChat(PlayerChatEvent e) {
		PlayerData plyD = Main.strToPlayer.getOrDefault(e.getPlayer().getName(), null);
		if (plyD != null && plyD.game!= null && !plyD.game.statut.equals(GameStatut.NOT_STARTED)) {
			
			if (plyD.game.isInDisc == true && plyD.considWolf) {
				
				GameLg game = plyD.game;
				
				ArrayList<PlayerData> receiver = new ArrayList<PlayerData>();
				receiver.addAll(game.getRealWolfAlive());
				
				//Add de la pf au chat des loups
				receiver.addAll(RoleUtil.getPlayersWithRole(game, RolesLg.PETITE_FILLE));
				
				for (PlayerData player: receiver) {
					player.sendMessage(ChatColor.RED+e.getMessage());
				}
				
				
			}
			e.setCancelled(true);
			
		}
		
	}
	@EventHandler
	public void onPotionSplash(PotionSplashEvent e) {
		
		if (e.getPotion().getItem().hasItemMeta() && e.getPotion().getItem().getItemMeta().hasLore()&& e.getPotion().getItem().getItemMeta().getLore().contains("Révéleur d'Aura")) {
			e.setCancelled(true);
			for (LivingEntity ent:e.getAffectedEntities()) {
				if (ent instanceof Player) {
					PlayerData p = Main.strToPlayer.get(((Player) ent).getName());
					if (p == null) {
						return;
					} else {
						p.auraDiscoverEffetDuration += 60;
					}
				}
			}
		} else if (e.getPotion().getItem().hasItemMeta() && e.getPotion().getItem().getItemMeta().hasLore()&& e.getPotion().getItem().getItemMeta().getLore().contains("Potion de Téléportation ")) {
			e.setCancelled(true);
			for (LivingEntity ent:e.getAffectedEntities()) {
				if (ent instanceof Player) {
					
					GameLgUtil.tpAl((Player) ent);
				}
			}
		} else if (e.getPotion().getItem().hasItemMeta() && e.getPotion().getItem().getItemMeta().hasLore()&& e.getPotion().getItem().getItemMeta().getLore().contains("Potion De Paralysie")) {
			e.setCancelled(true);
			for (LivingEntity ent:e.getAffectedEntities()) {
				if (ent instanceof Player /*&& ( !ent.getName().equals(((Player)e.getEntity().getShooter()).getName()))*/) {
					
					ent.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 1200, 1255));
					ent.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 1200, 255));
					ent.sendMessage(ChatColor.DARK_PURPLE+"Vous êtes paralysé, vous ne pouvez plus ni mettre de dégat ni en subir (vous êtes hors de combat)");
				}
			}
		}
		
	}
	
	
	
	@EventHandler
	public void EntityDamageByEntityEvent(org.bukkit.event.entity.EntityDamageByEntityEvent e) {
		boolean byPlayer;
		Player attacker = null;
		

		//CHECK DAMAGER == PLAYER/ARROW
		if (!(e.getDamager() instanceof Arrow || e.getDamager() instanceof Player) || !(e.getEntity() instanceof Player)) {
			return;
		}
		
		if (e.getDamager() instanceof Player &&Main.strToPlayer.getOrDefault(e.getDamager().getName(), null) == null) {
			return;
		}
		
		
		//VERIFIE SI LA FLECHE QUI A TUé EST TIRée PAR JOUEUR, si tué par flèche, met le tireur en attacker si oui
		boolean isArrow = false;
		if (e.getDamager() instanceof Arrow) {

			//CHECK IF DAMAGER IS PLAYER BY ARROW
			Arrow arrow = (Arrow) e.getDamager();
			
			if (arrow.getShooter() instanceof Player) {
				
				attacker = (Player) arrow.getShooter();
				byPlayer = true;
				isArrow = true;
			} else {
				byPlayer = false;
			}
		} else {
			attacker = (Player) e.getDamager();
			byPlayer = true;
		}
		
		if (Main.strToPlayer.getOrDefault(e.getEntity().getName(), null) == null || Main.strToPlayer.getOrDefault(e.getDamager().getName(), null) == null || Main.strToPlayer.getOrDefault(e.getEntity().getName(), null).game == null || Main.strToPlayer.getOrDefault(e.getDamager().getName(), null).game == null) {
			return;
		}
		System.out.println("base damage: " + e.getDamage());


		//CHECK IF PLAYER IG
		if (attacker == null || !(e.getEntity() instanceof Player) ) {
			return;
		}

		PlayerData damager = Main.strToPlayer.getOrDefault(e.getDamager().getName(), null);
		PlayerData damaged = Main.strToPlayer.getOrDefault(e.getEntity().getName(), null);
		
		
		double finalDamage = e.getDamage();
		if (damager.game != null && damaged.game != null && !damager.game.statut.equals(GameStatut.NOT_STARTED) && damaged.game.equals(damager.game)) {
			finalDamage = damaged.game.listener.damagePbyP(damager, damaged, finalDamage, isArrow);
		}

		
		//CREATE LESS AND MORE
		double less = 0;
		double more = 0;
		if (byPlayer) {

			//LISTE POTION EFFECT OF ATTACKER
			ArrayList<PotionEffect> effects = new ArrayList<PotionEffect>();
			for (PotionEffect p:attacker.getActivePotionEffects()) {
				effects.add(p);
			}

			//CREATE BOOST R or S WITH PLAYERS BOOST
			less += (0.05*damaged.boostR5);
			System.out.println("less = "+ less);
			more += (0.05*damager.boostS5);


			//CHECK STRENGHT
			boolean hasStrenght = false;
			for (PotionEffect ef:effects) {
				
				if (ef.getType().equals(PotionEffectType.INCREASE_DAMAGE)) {

					System.out.println("has strenght: "+ e.getDamage());
					hasStrenght = true;
					finalDamage/= 1.884;
					System.out.println("has strenght annulated: "+ e.getDamage());
					System.out.println(e.getDamage());
				
				} 
				
			}

			//ADD AT MORE IF STRENGHT
			if (hasStrenght) {
				more += 0.2;
			}

			if (damager.role == null) {
				return;	
			}
			if (damager.hasStrenghtAgainst.getOrDefault(e.getEntity(), false)) {
				more += 0.2;
			}


			//SET DAMAGE WITH MORE
			finalDamage *= (1+more);
			
		}
		
		
	
		//LISTE DEFENDER EFFECT
		Player defender = (Player) e.getEntity();
		ArrayList<PotionEffect> effects2 = new ArrayList<PotionEffect>();
		for (PotionEffect p:defender.getActivePotionEffects()) {
			effects2.add(p);
		}


		//CHECK RESIS DEFENDER
		boolean hasRes = false;
		for (PotionEffect ef:effects2) {
			
			if (ef.getType().equals(PotionEffectType.DAMAGE_RESISTANCE)) {
				
				System.out.println("has resistance");
				finalDamage  *= 1.25;
				hasRes = true;
				System.out.println("correct resis"+e.getDamage());
				
			}
			
		}


		//ADD IF RESIS at More
		if (hasRes) {
			less += 0.2;
		}
	
		//SET DAMAGE WITH LESS
		finalDamage *= (1 - less);
		System.out.println("resis damage: " + e.getDamage());
		
		
		
		
		
		finalDamage *=0.89;
		e.setDamage(finalDamage);
	}
	
	
	

	@EventHandler 
	public void onCraftItem(CraftItemEvent e) {
		
		
		switch (e.getRecipe().getResult().getType()) {
		
			case DIAMOND_AXE:
				e.setCancelled(true);
				ItemStack item = new ItemStack(e.getRecipe().getResult().getType());
				item.addEnchantment(Enchantment.DIG_SPEED, 3);
				e.getWhoClicked().getInventory().addItem(item);
				clearInv(e);
				break;
			
			case DIAMOND_PICKAXE:
				e.setCancelled(true);
				ItemStack item1 = new ItemStack(e.getRecipe().getResult().getType());
				item1.addEnchantment(Enchantment.DIG_SPEED, 3);
				e.getWhoClicked().getInventory().addItem(item1);
				clearInv(e);
				break;
			
	
			
			case IRON_AXE:
				e.setCancelled(true);
				ItemStack item3 = new ItemStack(e.getRecipe().getResult().getType());
				item3.addEnchantment(Enchantment.DIG_SPEED, 3);
				e.getWhoClicked().getInventory().addItem(item3);
				clearInv(e);
				break;
			
			
			case IRON_PICKAXE:
				e.setCancelled(true);
				ItemStack item4 = new ItemStack(e.getRecipe().getResult().getType());
				item4.addEnchantment(Enchantment.DIG_SPEED, 3);
				e.getWhoClicked().getInventory().addItem(item4);
				clearInv(e);
				break;
			
			
			
			case STONE_AXE:
				e.setCancelled(true);
				ItemStack item6 = new ItemStack(e.getRecipe().getResult().getType());
				item6.addEnchantment(Enchantment.DIG_SPEED, 3);
				e.getWhoClicked().getInventory().addItem(item6);
				clearInv(e);
				break;
			
			case STONE_PICKAXE:
				e.setCancelled(true);
				ItemStack item7 = new ItemStack(e.getRecipe().getResult().getType());
				item7.addEnchantment(Enchantment.DIG_SPEED, 3);
				e.getWhoClicked().getInventory().addItem(item7);
				clearInv(e);
				break;
		
		
		
			
		
		
		
	}
		
		
	
	
	
	}
	
	public void clearInv(CraftItemEvent e) {
		CraftingInventory inventory = e.getInventory();
	    
		// Vider les 9 slots de la table de craft
		for (int i = 0; i <= 9; i++) {
			if (inventory.getItem(i) != null && inventory.getItem(i).getAmount() != 1) {
				e.getWhoClicked().getInventory().addItem(new ItemStack(inventory.getItem(i).getType(), inventory.getItem(i).getAmount() -1));
			}
			inventory.setItem(i, null);
			
		}
	}
	
	
	
	
	
	@EventHandler
	public void onInventoryOpen(InventoryOpenEvent e) {
		
		
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
		
		PlayerData plyD = Main.strToPlayer.getOrDefault(e.getWhoClicked().getName(), null);
		
		if (plyD.game != null&& e.getInventory().equals(plyD.game.invVote)) {
			
			e.setCancelled(true);
			if (e.getCurrentItem() != null && e.getCurrentItem().getItemMeta().hasLore()) {
				if (e.getCurrentItem().getItemMeta().getLore().contains("utilisé")) {
					return;
				}
			}
			if (e.getCurrentItem() != null && e.getCurrentItem().getItemMeta() != null && e.getCurrentItem().getItemMeta().getDisplayName().equals("Voter") ) {
				
				
				
				VoteInv inv = new VoteInv((Player) e.getWhoClicked(), plyD.game, e.getSlot());
				
			}
		}
	}
}
