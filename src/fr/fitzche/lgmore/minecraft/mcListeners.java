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
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import com.avaje.ebeaninternal.server.deploy.BeanDescriptor.EntityType;
import com.google.common.util.concurrent.AbstractScheduledService.Scheduler;
import com.mysql.jdbc.Util;

import fr.fitzche.lgmore.GameLg;
import fr.fitzche.lgmore.Main;
import fr.fitzche.lgmore.PlayerData;
import fr.fitzche.lgmore.RolesLg.ANCIEN;
import fr.fitzche.lgmore.RolesLg.ANGE;
import fr.fitzche.lgmore.RolesLg.Camp;
import fr.fitzche.lgmore.RolesLg.ENFANT_SAUVAGE;
import fr.fitzche.lgmore.RolesLg.IDIOT_DU_VILLAGE;
import fr.fitzche.lgmore.RolesLg.LOUP_METAMORPHE;
import fr.fitzche.lgmore.RolesLg.LOUP_MYSTIQUE;
import fr.fitzche.lgmore.RolesLg.PARRAIN;
import fr.fitzche.lgmore.RolesLg.PYROMANE;
import fr.fitzche.lgmore.RolesLg.RoleDisplay;
import fr.fitzche.lgmore.RolesLg.RolesLg;
import fr.fitzche.lgmore.RolesLg.SOEUR;
import fr.fitzche.lgmore.RolesLg.VOLEUR;
import fr.fitzche.lgmore.Util.*;
import fr.fitzche.lgmore.commands.Lga;
import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_8_R1.EntityTypes;

public class mcListeners implements Listener {
	
	
	@EventHandler
	@Deprecated
	public void onPlayerDeath(PlayerDeathEvent e ) {
		
		
		
		System.out.println("death");
		
		
		
		if (GameLgUtil.getGameOfPlayer(e.getEntity().getPlayer(), " at 73 Main") != null) {
			
			
			
			Player killer = e.getEntity().getKiller();
			
			System.out.println("ffff");
			e.getEntity().getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 200, 100, false, false));

			Location loc = e.getEntity().getPlayer().getLocation();
			final ItemStack[] items = e.getEntity().getPlayer().getInventory().getContents();
			e.setKeepInventory(true);
			e.setDeathMessage("");
			
			
			
			
			
			
			
			GameLg gm1 =GameLgUtil.getGameOfPlayer(e.getEntity().getPlayer(), " at Main 93");
			System.out.println("hg.1");
			if (e.getEntity().getPlayer().getKiller() != null&&GameLgUtil.getGameOfPlayer(e.getEntity().getPlayer(), "at 95 Main").timer.temps > 1200) {
				System.out.println("ask res");
				GameLgUtil.askRes(GameLgUtil.getGameOfPlayer(e.getEntity().getPlayer(), "at 96 Main 1"), GameLgUtil.getGameOfPlayer(e.getEntity().getPlayer(), "at 96 Main 2").getPlayer(e.getEntity().getPlayer().getName()), (GameLgUtil.getGameOfPlayer(e.getEntity().getKiller(),  "at 96 Main 3")).getPlayer(e.getEntity().getKiller().getName()));

			}
			
			
			int k = 300;
			
			
			System.out.println("hg.2");
			e.getEntity().getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 200, 100, false, false));

			
			
			Bukkit.getScheduler().runTaskLater(Main.plug, new BukkitRunnable() {

				@Override
				public void run() {
			
					
			    	
			    	System.out.println("revive?");
			    	
			    	PlayerData player1 = (PlayerData) GameLgUtil.getGameOfPlayer(e.getEntity().getPlayer(), " at 116 Main").getPlayer(e.getEntity().getName());
			    	if (!player1.inLife) {
			    		return;
			    	}
			    	
			    	for (ResCheck checker: gm1.resCheckers) {
		    			if (checker.checkRes(e)) {
		    				player1.relive = true;
		    			}
		    		}
			    	
			    	player1.player.getInventory().setContents(items);
			    	if (player1.relive) {
			    		if (player1.infected) {
			    			player1.player.sendMessage(ChatColor.AQUA +"Vous avez été infecté, vous devez maintenant gagner avec les loups, vous possédez également force de nuit");
			    		}
			    		Bukkit.getScheduler().runTaskLater(Main.plug, new BukkitRunnable() {

							@Override
							public void run() {
					    		player1.relive = false;

								
							}
			    			
			    		}, 1200);
			    		System.out.println("l1");
			    		e.getEntity().getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 200, 100, false, false));
			    		System.out.println("l2");
			    		GameLgUtil.tpAl(e.getEntity().getPlayer());
			    		
			    		
			    	} else { 
			    		//RUN ACTION OF DEATH
			    		for (ResCheck checker: gm1.resCheckers) {
			    			checker.runDeathAction(e);
			    		}

				    	
				    	//ANNOUNCE DEATH
				    	gm1.announceDeath(player1);
				    	//DROP STUFF
						player1.player.getInventory().setContents(items);
			    		for (ItemStack item: items) {
			    			Main.server.getWorld("world").dropItemNaturally(loc, item);
			    		}
						//REMOVE PLAYER DEAD
			    		player1.player.teleport(loc);
						player1.instantDeath = true;
						
						player1.player.setGameMode(GameMode.SPECTATOR);
						
			    		GameLgUtil.getGameOfPlayer(player1.player, " at 174 of Main").removeDiedPlayer(player1);

			    	
			    	}
			    	
			    	
			    	
			    	
			    	
					
			        
			    }
			}, k); 
			
			/*sch.schedule( new Runnable() {

				@Override
				public void run() {
					if (GameLgUtil.getGameOfPlayer(e.getEntity().getPlayer()).getPlayer(e.getEntity().getPlayer().getName()).relive) {
						//Error GameLgUtil.tpAl(e.getEntity().getPlayer());
						//setInventory
						//tp lobby pour eviter changement mode
					} else {
						//announce, drop stuff à loc , set Spec
					}
				}
				
			}, 15, TimeUnit.SECONDS);*/
			
	
			/*
			
        
		
		passer en spec, après 15s sauf si variable set sur true avec soso ou IPDL(annoncer la mort et drops)*/
		
		} else {
			e.setKeepInventory(false);

		}
	
	}
	@EventHandler
	public void onInventoryInteract(InventoryInteractEvent event) {
		//System.out.println("interact");
		if ( event.getInventory() != null && event.getInventory().equals(RoleDisplay.myInventory)) {
			event.setCancelled(true);
			System.out.println("player can't do that");
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
		if (GameLgUtil.getGameOfPlayer(e.getPlayer(), "in onPlayerChat in mcListener L.91")!= null) {
			
			if (GameLgUtil.getGameOfPlayer(e.getPlayer(), "in onPlayerChat in mcListener L.91").isInDisc == true && GameLgUtil.getGameOfPlayer(e.getPlayer(), "in onPlayerChat in mcListener L.91").getPlayer(e.getPlayer().getName()).role.getCampOfRole().equals(Camp.Wolf)) {
				
				GameLg game = GameLgUtil.getGameOfPlayer(e.getPlayer(), "in onPlayerChat in mcListener L.91");
				
				ArrayList<PlayerData> receiver = new ArrayList<PlayerData>();
				receiver.addAll(game.getRealWolfAlive());
				receiver.addAll(RoleUtilLg.getPlayersWithRole(game, RolesLg.PETITE_FILLE));
				
				for (PlayerData player: receiver) {
					player.sendMessage(ChatColor.RED+e.getMessage());
				}
				
				
			}
			e.setCancelled(true);
			
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
		
		//VERIFIE SI LA FLECHE QUI A TUé EST TIRée PAR JOUEUR, si tué par flèche, met le tireur en attacker si oui

		if (e.getDamager() instanceof Arrow) {

			//CHECK IF DAMAGER IS PLAYER BY ARROW
			Arrow arrow = (Arrow) e.getDamager();
			
			if (arrow.getShooter() instanceof Player) {
				
				attacker = (Player) arrow.getShooter();
				byPlayer = true;
			} else {
				byPlayer = false;
			}
		} else {
			attacker = (Player) e.getDamager();
			byPlayer = true;
		}
		System.out.println("base damage: " + e.getDamage());


		//CHECK IF PLAYER IG
		if (PlayerUtil.getDataOfPlayer(attacker, "at damage by entity config") == null) {
			return;
		}

		PlayerData damager = PlayerUtil.getDataOfPlayer(attacker, "at damage by entity config");
		

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
			less += (0.05*PlayerUtil.getDataOfPlayer((Player) e.getEntity(), "  in DamageByEntityEvent in mcListener, 160-170, 2 ").boostR5);

			more += (0.05*damager.boostS5);


			//CHECK STRENGHT
			boolean hasStrenght = false;
			for (PotionEffect ef:effects) {
				
				if (ef.getType().equals(PotionEffectType.INCREASE_DAMAGE)) {

					System.out.println("has strenght");
					hasStrenght = true;
					e.setDamage((e.getDamage() / 2.29));
					System.out.println(e.getDamage());
				
				} 
				
			}

			//ADD AT MORE IF STRENGHT
			if (hasStrenght) {
				more += 0.3;
			}

			

			//chasseur Strenght against wolf
			if (damager.role.equals(RolesLg.CHASSEUR) && PlayerUtil.getDataOfPlayer((Player) e.getEntity(), " at onDamageByEntity in mcListener 2").role.getCampOfRole().equals(Camp.Wolf) ) {
				more += 0.3;
				System.out.println("TEMP//augmented ");
			}


			//SET DAMAGE WITH MORE
			e.setDamage(e.getDamage() * (1+more));
			System.out.println("strenght damage: " + e.getDamage());

			//Pyromane fire
			if (damager.role.equals(RolesLg.PYROMANE)) {
				PYROMANE pyro = (PYROMANE) damager.roleIn;
				if (pyro.fireAspect) {
					if (MathUtil.pourcentage(23)) {
						e.getEntity().setFireTicks(60);
					}
				}
			}
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
				
				hasRes = true;
				System.out.println(e.getDamage());
				
			}
			
		}


		//ADD IF RESIS at More
		if (hasRes) {
			less += 0.3;
		}

		//SET DAMAGE WITH LESS
		e.setDamage(e.getDamage()/(1+less));
		System.out.println("resis damage: " + e.getDamage());
		
		
		
	}
	
	
	
}
