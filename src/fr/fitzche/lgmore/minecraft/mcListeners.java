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
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.PlayerDeathEvent;
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
		System.out.println("Player "+ e.getEntity().getName() + " killed by "+ e.getEntity().getKiller().getName());
		final Player killer;
		if (e.getEntity().getKiller() instanceof Player) {
			killer = e.getEntity().getKiller();
		} else if (e.getEntity().getKiller() instanceof Arrow && ((Arrow) e.getEntity().getKiller()).getShooter() instanceof Player) {
			killer = (Player) ((Arrow) e.getEntity().getKiller()).getShooter();
		} else {
			killer = null;
		}
		if (GameLgUtil.getGameOfPlayer(e.getEntity().getPlayer(), " at 73 Main") != null) {
			System.out.println("game not null");
			Location loc = e.getEntity().getPlayer().getLocation();
			GameLg game = GameLgUtil.getGameOfPlayer(e.getEntity().getPlayer(), " at 73 Main");
			
			
			ArrayList<ItemStack> items = new ArrayList<ItemStack>();
			for (ItemStack item:e.getEntity().getPlayer().getInventory().getArmorContents()) {
				items.add(item);
			}
			for (ItemStack item:e.getEntity().getPlayer().getInventory().getContents()) {
				items.add(item);
			}
			
			
			
			if (game.statut.equals(GameStatut.NOT_STARTED)) {
				System.out.println("game not started");
				e.setKeepInventory(true);
				Bukkit.getScheduler().runTaskLater(Main.plug, new BukkitRunnable() {
					
					@Override
					public void run() {
						e.getEntity().getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 200, 100, false, false));
						
						e.getEntity().teleport(loc);
						
					}
				}, 20);
				return;
			}
			
			
			
			e.getEntity().getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 200, 100, false, false));

			e.setKeepInventory(true);
			e.setDeathMessage("");
			
			if (killer == null) {
				System.out.println("killer null");
			}

			
			if (killer != null &&game.timer.temps > 1200) {
				System.out.println("ask res");
				GameLgUtil.askRes(game, game.getPlayer(e.getEntity().getPlayer().getName()), game.getPlayer(e.getEntity().getKiller().getName()));

			}
			
			for (ResCheck checker:game.resCheckers) {
				checker.beforeDie(e);
			}
			
			
			int k = 300;
			
			
			
			
			
			
			
			Bukkit.getScheduler().runTaskLater(Main.plug, new BukkitRunnable() {

				@Override
				public void run() {
			
					
			    	
			    	
			    	
			    	PlayerData player1 = (PlayerData) game.getPlayer(e.getEntity().getName());
			    	if (!player1.inLife) {
			    		return;
			    	}
			    	
			    	for (ResCheck checker: game.resCheckers) {
		    			if (checker.checkRes(e)) {
		    				player1.relive = true;
		    			}
		    		}
			    	
			    	
			    	if (player1.relive) {
			    		if (player1.infected) {
			    			player1.player.sendMessage(ChatColor.AQUA +"Vous avez été infecté, vous devez maintenant gagner avec les loups, vous possédez également force de nuit");
			    		}
			    		Bukkit.getScheduler().runTaskLaterAsynchronously(Main.plug, new BukkitRunnable() {

							@Override
							public void run() {
					    		player1.relive = false;

								
							}
			    			
			    		}, 1200);
			    
			    		e.getEntity().getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 200, 100, false, false));
			    		GameLgUtil.tpAl(e.getEntity().getPlayer());
			    		
			    		
			    	} else { 
			    		
			    		boolean hidden = false;
			    		if (killer != null) {
			    			PlayerData killerData = PlayerUtil.getDataOfPlayer(killer, "at death announce");
			    			killerData.numberOfKill ++;
			    			
			    			for (ResCheck checker: game.resCheckers) {
			    				checker.runDeathAction(e, killer);
			    				if (checker.hide(e)) {
			    				hidden = true;
			    				}
			    			}
			    		}
			    		

				    	if (!hidden) {
				    		//ANNOUNCE DEATH
				    		if (player1.grimed) {
				    			game.announceDeath(player1, RolesLg.SIMPLE_WOLF);
				    		} else {
				    			game.announceDeath(player1);
				    		}
				    	}
				    	
				    	
				    	//DROP STUFF
						
			    		for (ItemStack item: items) {
			    			if (item != null) {
			    				Main.server.getWorld("world").dropItemNaturally(loc, item);

			    			}
			    		}
						//REMOVE PLAYER DEAD
			    		player1.player.teleport(loc);
						player1.instantDeath = true;
						
						player1.player.setGameMode(GameMode.SPECTATOR);
						
			    		game.removeDiedPlayer(player1);

			    	
			    	}
			    	
			    	
			    	
			    	
			    	
					
			        
			    }
			}, k); 
			
			
		
		} else {
			e.setKeepInventory(false);

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
		
		if (e.getEntity() instanceof Player &&GameLgUtil.getGameOfPlayer((Player) e.getEntity(), "at damageByEntityEvent 1") == null || GameLgUtil.getGameOfPlayer(attacker, "at damageByEntityEvent 2") == null ) {
			return;
		}
		System.out.println("base damage: " + e.getDamage());


		//CHECK IF PLAYER IG
		if (PlayerUtil.getDataOfPlayer(attacker, "at damage by entity config") == null || PlayerUtil.getDataOfPlayer((Player) e.getEntity(), "at damage event") == null) {
			return;
		}

		PlayerData damager = PlayerUtil.getDataOfPlayer(attacker, "at damage by entity config");
		GameLg gameDamager = GameLgUtil.getGameOfPlayer(damager, "at damage event");
		GameLg gameDefencer = GameLgUtil.getGameOfPlayer((Player) e.getEntity(), "at damage event");

		if (gameDefencer.statut.equals(GameStatut.NOT_STARTED) && gameDamager.statut.equals(GameStatut.NOT_STARTED)) {
			e.setCancelled(true);
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
			less += (0.05*PlayerUtil.getDataOfPlayer((Player) e.getEntity(), "  in DamageByEntityEvent in mcListener, 160-170, 2 ").boostR5);
			System.out.println("less = "+ less);
			more += (0.05*damager.boostS5);


			//CHECK STRENGHT
			boolean hasStrenght = false;
			for (PotionEffect ef:effects) {
				
				if (ef.getType().equals(PotionEffectType.INCREASE_DAMAGE)) {

					System.out.println("has strenght: "+ e.getDamage());
					hasStrenght = true;
					e.setDamage((e.getDamage() / 1.884));
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

			//chasseur Strenght against wolf
			if (damager.role.equals(RolesLg.CHASSEUR) && PlayerUtil.getDataOfPlayer((Player) e.getEntity(), " at onDamageByEntity in mcListener 2").role.getCampOfRole().equals(Camp.Wolf) ) {
				more += 0.2;
				System.out.println("TEMP//augmented ");
			}

			if (damager.role.equals(RolesLg.LOUP_BARBARE)) {
				LOUP_BARBARE lg = (LOUP_BARBARE) damager.roleIn;
				System.out.println("test barbare jjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjj");
				if (MathUtil.pourcentage(lg.sup)) {
					System.out.println("DAAAAAAAAAAAAAAAAAAAAMMMMMMMMMMMMMMMMAAAAAAAAAAGGGGGGGGGEEEEEEEEEE");
					e.setDamage(e.getDamage()+1);
				}
			}

			//SET DAMAGE WITH MORE
			e.setDamage(e.getDamage() * (1+more));
			System.out.println("strenght final damage: " + e.getDamage());

			//Pyromane fire
			if (damager.role.equals(RolesLg.PYROMANE)) {
				PYROMANE pyro = (PYROMANE) damager.roleIn;
				if (pyro.fireAspect) {
					if (MathUtil.pourcentage(35)) {
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
				e.setDamage((e.getDamage() * 1.25));
				hasRes = true;
				System.out.println("correct resis"+e.getDamage());
				
			}
			
		}


		//ADD IF RESIS at More
		if (hasRes) {
			less += 0.2;
		}

		//SET DAMAGE WITH LESS
		e.setDamage(e.getDamage()/(1 +less));
		System.out.println("resis damage: " + e.getDamage());
		
		
		e.setDamage(e.getDamage() * 0.89);
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
	public void onPlayerInteract(PlayerInteractEvent e) {
		System.out.println("interact onPlayerInteract");
		if (e.getClickedBlock() != null && e.getClickedBlock().hasMetadata("interactBlockVote")) {
			System.out.println("interact true onPlayerInteract");
			e.getPlayer().openInventory(GameLgUtil.getGameOfPlayer((Player) e.getPlayer(), "interactBlockVote").invVote);
		}
		if (e.getClickedBlock() != null && e.getClickedBlock().hasMetadata("interactBlockAccuse")) {
			e.getPlayer().sendMessage(ChatColor.RED+"Vous avez 30sec pour accuser un joueur avec la commande /lg accuse [nom du joueur], votre accusation sera rendu publique au prochain épisode.");
			PlayerData p = PlayerUtil.getDataOfPlayer(e.getPlayer(), "on player interact bloc accuse");
			p.canAccuse = true;
			
			Bukkit.getScheduler().runTaskLater(Main.plug, new BukkitRunnable() {

				@Override
				public void run() {
					p.canAccuse = false;
					
				}
				
			}, 600);
		}
	}
	
	public boolean onPlayerInteract(Block bloc, Player p) {
		System.out.println("interact onPlayerInteract");
		if (bloc != null && bloc.hasMetadata("interactBlockVote")) {
			System.out.println("interact true onPlayerInteract");
			p.openInventory(GameLgUtil.getGameOfPlayer((Player) p, "interactBlockVote").invVote);
		}else if (bloc != null && bloc.hasMetadata("interactBlockAccuse")) {
			p.sendMessage(ChatColor.RED+"Vous avez 30sec pour accuser un joueur avec la commande /lg accuse [nom du joueur], votre accusation sera rendu publique au prochain épisode.");
			PlayerData ply = PlayerUtil.getDataOfPlayer(p, "on player interact bloc accuse");
			ply.canAccuse = true;
			
			Bukkit.getScheduler().runTaskLater(Main.plug, new BukkitRunnable() {

				@Override
				public void run() {
					ply.canAccuse = false;
					
				}
				
			}, 600);
		} else {
			return false;
		}
		return true;
	}
	
	@EventHandler
	public void onInventoryOpen(InventoryOpenEvent e) {
		
		
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
		System.out.println("vote1");
		if (e.getInventory().equals(GameLgUtil.getGameOfPlayer((Player) e.getWhoClicked(), "click").invVote)) {
			System.out.println("vote2");
			e.setCancelled(true);
			if (e.getCurrentItem().getItemMeta().hasLore()) {
				if (e.getCurrentItem().getItemMeta().getLore().contains("utilisé")) {
					return;
				}
			}
			if (e.getCurrentItem() != null && e.getCurrentItem().getItemMeta() != null && e.getCurrentItem().getItemMeta().getDisplayName().equals("Voter") ) {
				
				
				System.out.println("vote3");
				VoteInv inv = new VoteInv((Player) e.getWhoClicked(), GameLgUtil.getGameOfPlayer((Player) e.getWhoClicked(), ""), e.getSlot());
				
			}
		}
	}
}
