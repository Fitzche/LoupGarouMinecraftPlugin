package fr.fitzche.lgmore.Lg;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import fr.fitzche.lgmore.Camp;
import fr.fitzche.lgmore.GameStatut;
import fr.fitzche.lgmore.Main;
import fr.fitzche.lgmore.PlayerData;
import fr.fitzche.lgmore.RolesLg.LOUP_BARBARE;
import fr.fitzche.lgmore.RolesLg.PYROMANE;
import fr.fitzche.lgmore.RolesLg.RolesLg;
import fr.fitzche.lgmore.RolesLg.SWAPPER;
import fr.fitzche.lgmore.Util.GameLgUtil;
import fr.fitzche.lgmore.Util.MathUtil;
import fr.fitzche.lgmore.minecraft.GameListener;
import fr.fitzche.lgmore.minecraft.ResCheck;
import net.md_5.bungee.api.ChatColor;

public class GameLgListener implements GameListener {
	public GameLgListener() {
		// TODO Auto-generated constructor stub
	}
	
	public GameLg game;
	@Override
	public double damagePbyP(PlayerData damager, PlayerData damaged, double damage, boolean isArrow) {
		
		
		
		double more = 1;
		double finalDamage = damage;
		if (damager.role.equals(RolesLg.CHASSEUR) && damaged.considWolf ) {
			more += 0.2;
			System.out.println("TEMP//augmented ");
		}

		if (damager.role.equals(RolesLg.LOUP_BARBARE)) {
			LOUP_BARBARE lg = (LOUP_BARBARE) damager.roleIn;
			if (MathUtil.pourcentage(lg.sup)) {
				finalDamage++;
			}
		}

		//SET DAMAGE WITH MORE
		finalDamage = damage *= more;
		

		//Pyromane fire
		if (damager.role.equals(RolesLg.PYROMANE)) {
			PYROMANE pyro = (PYROMANE) damager.roleIn;
			if (pyro.fireAspect) {
				if (MathUtil.pourcentage(35)) {
					damaged.player.setFireTicks(60);
				}
			}
		}
		double modifier = 0;
		for (ResCheck checker:game.resCheckers) {
			modifier += checker.onPlayerDamage(damager, damaged);
		}
		
		finalDamage *= (1+(modifier/100));
	
		return finalDamage;
	}

	@Override
	public double damagePbyEntity(PlayerData damaged, Entity damager, double damage) {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public double kill(PlayerData killed, PlayerData killer) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void click(PlayerData p, String name, ArrayList<String> lores, int index, Inventory inv) {
		// TODO Auto-generated method stub

	}

	@Override
	@Deprecated
	public boolean rez(PlayerData killed, PlayerData killer, Location loc, PlayerDeathEvent e) {
		if (game.statut.equals(GameStatut.NOT_STARTED)) {
			StringBuilder log = new StringBuilder();
			log.append("game not started");
			System.out.println(log);
			e.setKeepInventory(true);
			
			Bukkit.getScheduler().runTaskLater(Main.plug, new BukkitRunnable() {
				
				@Override
				public void run() {
					e.getEntity().getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 200, 100, false, false));
					
					e.getEntity().teleport(loc);
					
				}
			}, 20);
			System.out.println(log);
			return true;
		}
		
		if (killer != null &&game.timer.temps > 1200) {
			System.out.println("ask res");
			GameLgUtil.askRes(game, game.getPlayer(e.getEntity().getPlayer().getName()), game.getPlayer(e.getEntity().getKiller().getName()));

		}
		for (ResCheck checker:game.resCheckers) {
			checker.beforeDie(e);
		}
		
		
		
		
		
		return false;
	}

	@Deprecated
	@Override
	public boolean rez2(PlayerData killed, PlayerData killer, Location loc, PlayerDeathEvent e, ArrayList<ItemStack> items) {
		StringBuilder log = new StringBuilder();
		if (!killed.inLife) {
    		return false;
    	}
    	
    	for (ResCheck checker: game.resCheckers) {
    		if (killer != null) {
    			if (checker.checkRes(e, killer)) {
    				killed.relive = true;
    				log.append(killed.getName()+ " rez by "+ checker.getTypeName()+ "\n");
    			}
    		} else {
    			if (checker.checkRes(e)) {
    				killed.relive = true;
    				log.append(killed.getName()+ " rez by "+ checker.getTypeName()+ "\n");
    			}
    		}
    			
		}
    	
    	if (game.swapper && !game.statut.equals(GameStatut.NOT_STARTED)) {
			SWAPPER swapDead = (SWAPPER)killed.roleIn;
			swapDead.life --;
			killed.sendMessage("Vous perdez une vie");
			if (killer != null) {
				SWAPPER swapKiller = (SWAPPER) killer.roleIn;
				swapKiller.life++;
				killer.sendMessage("Vous gagner une vie");
				swapDead.changeCamp(swapKiller.camp);
			}
			if (swapDead.life > 0) {
				return true;
			}
		
			killed.game.broadcoast("Le joueur "+ killed.player.getCustomName() + " est mort définitivement");
		
		
		
			//MORT
			for (ItemStack item: items) {
				if (item != null && !item.getType().equals(Material.AIR)) {
					Main.server.getWorld("world").dropItemNaturally(loc, item);

				}
			}
			if (killed.isOnline) {
				killed.player.teleport(loc);
			
		
				killed.player.setGameMode(GameMode.SPECTATOR);
			}
			//REMOVE PLAYER DEAD
			killed.instantDeath = true;
			
			game.removeDiedPlayer(killed);
			game.checkWin();
			return false;
		}
    	
    	
    	if (killed.relive) {
    		
    		//REGISTER CAUSE
    		game.addorat(10, e.getEntity().getLocation());
    		//REGISTER EFFECT EPIC
    		if (game.isRegistresActivated && MathUtil.pourcentage(game.getEpic() / 5)) {
    			game.broadcoast(ChatColor.DARK_PURPLE + "Le joueur "+ killed.getName() + " a réssucité");
    		}
    		if (killed.infected && killed.relive) {
    			killed.player.sendMessage(ChatColor.AQUA +"Vous avez été infecté, vous devez maintenant gagner avec les loups, vous possédez également force de nuit, faites /lg role pour connaitre la liste des loups garou");
    			((GameLg)killed.game).checkWin();
    		}
    		
    		Bukkit.getScheduler().runTaskLaterAsynchronously(Main.plug, new BukkitRunnable() {
    			
				@Override
				public void run() {
					killed.relive = false;

					
				}
    			
    		}, 1200);
    
    		
    		//RE TP
    		GameLgUtil.tpAl(e.getEntity().getPlayer());
    		
    		
    	} else { 
    		boolean brumed = false;
    		boolean hidden = false;
    		
    		
    		for (ResCheck checker: game.resCheckers) {
				
				
				if (killer != null) {
					String str = checker.runDeathAction(e, killer.player);
					if (!str.equals("")) {
						log.append("death action: "+str + "\n");
					}
					
				}
				
				//CHECK MORT CACHée
				if (checker.hide(e)) {
					hidden = true;
					log.append("death hidden by "+ checker.getTypeName()+"\n");
				}
				
				//EFFET TRAGIC
				if (game.isRegistresActivated && MathUtil.pourcentage((game.getTragic()/10))) {
					hidden = true;
					log.append("death hidden by tragic ("+(game.getTragic()/10)+ ")"+"\n");
				}
				
				//CHECK MORT BROUILLéE
				if (checker.brume(e)) {
					brumed = true;
					log.append("death brumed by "+ checker.getTypeName()+ "\n");
				}
				
				//EFFET EPIC
				if (game.isRegistresActivated &&MathUtil.pourcentage(game.getEpic()/5)) {
					brumed = true;
					log.append("death brumed by epic"+ "\n");
				}
			}
    		if (killer != null) {
    			
    			killer.numberOfKill ++;
    			
    			
    			
    			
    			//REGISTER CAUSES
    			if (brumed) {
    				game.addEpic(10, e.getEntity().getLocation());
    			}
    			if (hidden) {
    				game.addTragic(5, e.getEntity().getLocation());
    			}
    			if (!killed.roleIn.isInfoRole()) {
					game.addTragic(5, e.getEntity().getLocation());
				} else {
					game.addEpic(5, e.getEntity().getLocation());
				}
				
				if (killed.camp == Camp.Other || killed.camp == Camp.TEAM) {
					game.addEpic(10, e.getEntity().getLocation());
				}
				
    		}
    		

    		//ANNOUNCE DEATH
    		if (killed.grimed) {
    			game.announceDeath(killed, RolesLg.SIMPLE_WOLF, false);
    			log.append("Grimed"+ "\n");
    		} else if (hidden){
    			game.announceDeath(killed, false, true);
    		} else if (brumed){
    			game.announceDeath(killed, true, false);
    		} else {
    			game.announceDeath(killed, false, false);
    		}
	    	
	    	
	    	//DROP STUFF
			
    		for (ItemStack item: items) {
    			if (item != null && !item.getType().equals(Material.AIR)) {
    				Main.server.getWorld("world").dropItemNaturally(loc, item);

    			}
    		}
    		if (killed.isOnline) {
    			killed.player.teleport(loc);
    			
			
    			killed.player.setGameMode(GameMode.SPECTATOR);
    		}
			//REMOVE PLAYER DEAD
    		killed.instantDeath = true;
			
    		game.removeDiedPlayer(killed);
    		if (!(false)|| MathUtil.pourcentage(game.probasEvents.getOrDefault("AutomaticCheckWin", 100))) {
    			game.checkWin();
			}
    		
    	}
    	System.out.println(log);
		return false;
	}

}
