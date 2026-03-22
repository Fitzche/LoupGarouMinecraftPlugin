package fr.fitzche.lgmore.custom;

import java.awt.RenderingHints.Key;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import fr.fitzche.lgmore.Game;
import fr.fitzche.lgmore.Main;
import fr.fitzche.lgmore.PlayerData;
import fr.fitzche.lgmore.Lg.GameType;
import fr.fitzche.lgmore.Minage.MinageWorld;
import fr.fitzche.lgmore.Util.LocationUtil;
import fr.fitzche.lgmore.Util.MathUtil;
import fr.fitzche.lgmore.Util.WorldUtil;
import fr.fitzche.lgmore.minecraft.GameListener;
import fr.fitzche.lgmore.scoreboard.CustomGameBoard;

public interface CustomGame extends Game, Listener {

	
	public boolean canJoin(PlayerData p);
	public void addPlayer(PlayerData p);
	public ArrayList<PlayerData> getWinners();
	public ArrayList<PlayerData> getPlayers();
	public boolean hasStarted();
	public void playerQuit(String name);
	public GameType getType();
	public World getWorld();
	public String getName();
	public void broadcoast(String message);
	public void setWorld(World world);
	public GameListener getListener();
	public int getMaxNBOfPlayer();
	public int getActualNbOfPlayer();
	public void askRunFuturesActions();
	public CustomTimer getTimer();
	public void eachSecond();
	public ArrayList<RoleSet> rolesSet();
	public void setTimer(CustomTimer customTimer);
	
	
	public CustomGameDataSet getData();
	
	public void start();
	
	@Deprecated
	public static void run(CustomGame game) {
		//BOARD
		for (PlayerData p:game.getPlayers()) {
			
			p.board = new CustomGameBoard(game, p);	
		}
		
		//WORLD
		if (!game.getData().isMapEmpty) {
			game.setWorld(WorldUtil.createNewWorld()); 
			System.out.println("new world created for custom game ");
			game.setTimer(new CustomTimer(game));
		} else {
			//NON-EFFECTIVE
		}
		
		//MINAGE
		if (game.getData().hasMinage) {
			MinageWorld minage = new MinageWorld(new BukkitRunnable() {
				
				@Override
				public void run() {
					for (PlayerData p:game.getPlayers()) {
						LocationUtil.tpAl(p, game.getData().tpRayon, game.getWorld());
					}
					
				}
			}, new ArrayList<Material>(Arrays.asList(Material.DIAMOND, Material.GOLD_INGOT, Material.LAPIS_ORE, Material.APPLE, Material.DIAMOND_SWORD, Material.IRON_SWORD, Material.GOLDEN_APPLE, Material.GOLDEN_CARROT, Material.IRON_INGOT)), game.getData().timeMinageInSec, game.getData().boostGold 	, game.getData().boostIron, game.getData().boostDiams, false, 10000, game.getPlayers());
		}
		
		
		//EACH SECOND
		Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.plug, new BukkitRunnable() {

			@Override
			public void run() {
				
				game.eachSecond();
				game.getTimer().add(1);
				
				for (RoleSet set: game.rolesSet()) {
					if (set.applicationTime() == game.getTimer().temps) {
						ArrayList<CustomRole> roles = set.roles(game.getActualNbOfPlayer());
						for (PlayerData p:game.getPlayers()) {
							if (set.rolesSettedOfPlayers() != null && set.rolesSettedOfPlayers().get(p.getName()) != null) {
								p.setCustomRole(game, set.rolesSettedOfPlayers().get(p.getName()), set);
								if (roles.contains(set.rolesSettedOfPlayers().get(p.getName()))) {
									roles.remove(set.rolesSettedOfPlayers().get(p.getName()));
								}
							} else {
								int x = MathUtil.generateAlInt(0, roles.size() - 1);
								p.setCustomRole(game, roles.get(x), set);
								roles.remove(x);
							}
							
							
							
							
							

						}
						
						
					}
				}
			}
			
		}, 20, 20);
	}
	
	
	
	
	
	@EventHandler
	public static void onEntityDamage(EntityDamageEvent e) {
		
		if (e.getEntity() instanceof Player && Main.getData(e.getEntity()).game instanceof CustomGame) {
			CustomGame game = (CustomGame) Main.getData(e.getEntity()).game;
			
			if (e instanceof EntityDamageByEntityEvent) {
				for (RoleSet roleSet:game.rolesSet()) {
					CustomRole role = roleSet.rolesOfPlayer().getOrDefault(((EntityDamageByEntityEvent) e).getDamager().getName(), null);
					if (role != null) {
						e.setDamage(role.attackModif(e.getFinalDamage(), e.getEntity().getName()));
					
					}
				}
			}
			
			for (RoleSet roleSet:game.rolesSet()) {
				CustomRole role = roleSet.rolesOfPlayer().getOrDefault(e.getEntity().getName(), null);
				if (role != null) {
					if (e instanceof EntityDamageByEntityEvent) {
						e.setDamage(role.damageModif(e.getFinalDamage(), ((EntityDamageByEntityEvent) e).getDamager().getName()));
					}
					e.setDamage(role.damageModif(e.getFinalDamage(), "pve"));
					
				}
			}
			
			
			//checkRez
			//Par défaut quand un joueur meurt, il est éliminé et renvoyé au lobby (sans annonce)
			if (e.getFinalDamage() >= ((Player) e.getEntity()).getHealth()) {
				boolean rez;
				if (game.getData().defaultRespawn) {
					rez = true;
				}else {rez = false;}
				
				for (RoleSet roleSet:game.rolesSet()) {
					CustomRole role = roleSet.rolesOfPlayer().getOrDefault(e.getEntity().getName(), null);
					if (role != null) {
						if (e instanceof EntityDamageByEntityEvent) {
							if (role.checkDeath(e.getEntity().getName(), ((EntityDamageByEntityEvent) e).getDamager().getName())) {
								rez = true;
							}
						} else {
							if (role.checkDeath(e.getEntity().getName(), "")) {
								rez = true;
							}
						}
						
					}
				}
				if (rez) {
					e.setCancelled(true);
					((Player) e.getEntity()).setHealth(((Player) e.getEntity()).getMaxHealth());
					if (game.getData().lostStuffOnDeath) {
						
						for (ItemStack i:((Player) e.getEntity()).getInventory().getContents()) {
							e.getEntity().getLocation().getWorld().dropItem(e.getEntity().getLocation(), i);
						}
						((Player) e.getEntity()).getInventory().clear();
					}
					
					
					
					if (game.getData().respawnOfPlayers.getOrDefault(e.getEntity().getName(), null) != null) {
						
						
						
						e.getEntity().teleport(game.getData().respawnOfPlayers.get(e.getEntity().getName()));
					}
					LocationUtil.tpAl(Main.getData(e.getEntity()), game.getData().tpRayon, game.getWorld());

				} else {
					death(game, e.getEntity().getName());
				}
			}
			
		}
	}
	
	public static void death(CustomGame game, String PlayerName) {
		for (RoleSet roleSet:game.rolesSet()) {
			CustomRole role = roleSet.rolesOfPlayer().getOrDefault(PlayerName, null);
			if (role != null) {
				role.death();
				roleSet.setVictoryOfPlayer(PlayerName, "nul");
				String camp = "";
				for (Entry<String, String> entry:roleSet.victoryOfPlayers().entrySet()) {
					if (!entry.getValue().equals(camp)) {
						if (camp.equals("")) {
							if (!entry.getValue().equals("nul")) {
								camp = entry.getValue();
							}
							
						} 
						if (!entry.getValue().equals("nul")) {
							return;
						}
					}
				}
				if (camp.equals("")) {
					game.broadcoast("Partie Perdue par tout le monde (je sais pas comment c possible, si y'a quelqu'un qui voit ce message, dsl ça a bugé");
				} else {
					game.broadcoast("Le camp "+ camp + " a gagné");
					for (PlayerData p:game.getPlayers()) {
						p.clearLgGameVar();
						if (p.isOnline) {
							p.player.teleport(Main.world.getSpawnLocation());
						}
					}
				}
				
			}
		}
	}

}
