package fr.fitzche.lgmore.Minage;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.logging.log4j.core.config.plugins.convert.TypeConverters.IntegerConverter;
import org.apache.logging.log4j.core.util.Integers;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import WorldEditUtil.EmptyWorldGenerator;
import fr.fitzche.lgmore.Main;
import fr.fitzche.lgmore.PlayerData;
import fr.fitzche.lgmore.Util.GameLgUtil;
import fr.fitzche.lgmore.Util.LocationUtil;
import fr.fitzche.lgmore.Util.MathUtil;

public class MinageWorld implements Listener{

	BukkitRunnable run;
	ArrayList<Material> autorized;
	int timeInSec;
	double boostGold;
	double boostIron;
	double boostDiamond;
	boolean removeArmor;
	int limitDiamond;
	int time;
	World world;
	HashMap<String, Integer> limitD = new HashMap<String, Integer>();
	ArrayList<PlayerData> ps;
	
	/*
	 * 
	 * 
	 * */
	@Deprecated
	public MinageWorld(BukkitRunnable run, ArrayList<Material> autorized, int timeInSec,double boostGold ,double boostIron, double boostDiamond, boolean removeArmor, int limitDiamond, ArrayList<PlayerData> ps) {
		this.run = run;
		this.autorized = autorized;
		this.timeInSec = timeInSec;
		this.boostDiamond = boostDiamond;
		this.boostGold = boostGold;
		this.boostIron = boostIron;
		this.removeArmor = removeArmor;
		this.ps = ps;
		this.limitDiamond = limitDiamond;
		
		WorldCreator c = new WorldCreator("worldMinage"+MathUtil.generateAlInt(0, 99999)).generator(Main.world.getGenerator());
		c.generator(new EmptyWorldGenerator());
		this.world = c.createWorld();
		for (PlayerData p:ps) {
			if (p.game != null) {
				LocationUtil.tpAl(p, 1000);
			}
		}
		
		Bukkit.getPluginManager().registerEvents(this, Main.plug);
		
		
		Bukkit.getScheduler().runTaskTimerAsynchronously(Main.plug, new BukkitRunnable() {
			
			@Override
			public void run() {
				if (time < timeInSec) {
					time ++;
					for (PlayerData p:ps) {
						Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
						Objective obj = board.registerNewObjective("vie", "health");
						obj.setDisplayName("" + time + " / "+timeInSec + "  ||  "+ "diamond: "+ limitD.getOrDefault(p.getName(), 0) + " / "+ limitDiamond);
						if (p.isOnline) {
							p.player.setScoreboard(board);
						}
					}
					
					if (time >= timeInSec) {
						for (PlayerData p:ps) {
							
							if (p.isOnline) {
								for (ItemStack item:p.player.getInventory().getContents()) {
									boolean toRemove = false;
									for (Material mat:autorized) {
										if (!mat.equals(item.getType())) {
											toRemove = true;
										}
									}
									
									if (toRemove) {
										p.player.getInventory().remove(item);
									}
								}
							}
						}
						run.run();
					}
				}
				
				
			}
		}, 20, 20);
		
	}
	
	@EventHandler
	public void EntityDamageByEntityEvent(org.bukkit.event.entity.EntityDamageByEntityEvent e) {
		if (e.getEntity() instanceof Player && e.getEntity().getLocation().getWorld().equals(this.world)) {
			if (((Player) e.getEntity()).getHealth() < e.getDamage()) {
				e.setCancelled(true);
				
				LocationUtil.tpAl(Main.getData(e.getEntity()), 1000);
			}
		}
	}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		if (event.getBlock().getLocation().getWorld().equals(this.world)) {
			switch (event.getBlock().getType()) {
			case DIAMOND_ORE:
				int x = 0;
				
				while (this.boostDiamond >= x && boostDiamond != 0) {
					x++;
				}
				
				
				double pro = boostDiamond - 1-x;
				if (!MathUtil.pourcentage((int) pro * 100)) {
					x--;
				} 
				
				event.setCancelled(true);
				
				event.getBlock().setType(Material.AIR);
				if (this.limitD.getOrDefault(event.getPlayer().getName(), 0) >= limitDiamond) {
					event.getPlayer().getInventory().addItem(new ItemStack(Material.GOLD_INGOT, x));
					return;
				}
				
				this.limitD.put(event.getPlayer().getName(), this.limitD.getOrDefault(event.getPlayer().getName(), 0) + x);
				if (this.limitD.get(event.getPlayer().getName()) > limitDiamond) {
					x -= (this.limitD.get(event.getPlayer().getName()) - limitDiamond);
				}
				
				event.getPlayer().getInventory().addItem(new ItemStack(Material.DIAMOND, x));
				
				break;
			case GOLD_ORE:
				int x1 = 0;
				
				while (this.boostGold >= x1 && boostGold != 0) {
					x1++;
				}
				
				
				double pro1 = boostGold - 1-x1;
				if (!MathUtil.pourcentage((int) pro1 * 100)) {
					x1--;
				} 
				
				event.setCancelled(true);
				
				event.getBlock().setType(Material.AIR);
				event.getPlayer().getInventory().addItem(new ItemStack(Material.GOLD_INGOT, x1));
				break;
			case IRON_ORE:
				int x2 = 0;
				
				while (this.boostIron >= x2 && boostIron != 0) {
					x2++;
				}
				
				
				double pro2 = boostIron - 1-x2;
				if (!MathUtil.pourcentage((int) pro2 * 100)) {
					x2--;
				} 
				
				event.setCancelled(true);
				event.getBlock().setType(Material.AIR);
				event.getPlayer().getInventory().addItem(new ItemStack(Material.GOLD_INGOT, x2));
				break;
			}
		}
	}
}
