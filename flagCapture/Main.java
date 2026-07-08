package fr.fitzche.flagCapture;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import de.slikey.effectlib.EffectManager;
import de.slikey.effectlib.effect.LineEffect;
import de.slikey.effectlib.util.DynamicLocation;




public class Main extends JavaPlugin implements Listener{

	
	public static HashMap<String, Color> playerToHisStealedFlagColor = new HashMap<String, Color>();
	public static HashMap<String, Color> PlayerToHisTeam = new HashMap<String, Color>();

	public static boolean isGameStarted;
	public static EffectManager man = new EffectManager(Main.plug);
	
	public static HashMap<Color, Location> locOfColor = new HashMap<Color, Location>();
	public static HashMap<Color, Boolean> isColorWithFlag = new HashMap<Color, Boolean>();
	public static HashMap<Color, Boolean> isColorAlive = new HashMap<Color, Boolean>();
	public static ArrayList<Color> colors = new ArrayList<Color>(Arrays.asList(Color.Black, Color.Blue, Color.Green, Color.Red, Color.Yellow));
	public static JavaPlugin plug;
	public static boolean autoDeath = false;
	
	

	
	
	@Override
	public void onEnable() {
		// TODO Auto-generated method stub
		super.onEnable();
		Main.plug = this;
		getCommand("flag").setExecutor(new Flag());
		getCommand("flag").setTabCompleter(new FlagTab());
	}
	
	
	
	@Deprecated
	public static void start() {
		Bukkit.broadcastMessage("la partie Commence");
		isGameStarted = true;
		for (Player p : Bukkit.getOnlinePlayers()) {
			Main.setNameColor(p);
		}
		
		
		for (Color color: colors) {
			boolean isPresent = false;
			for (Player present:Bukkit.getOnlinePlayers()) {
				if (PlayerToHisTeam.getOrDefault(present, null) != null && PlayerToHisTeam.getOrDefault(present, null).equals(color)) {
					isPresent = true;
				}
			}
			isColorAlive.put(color, isPresent);
		}
		
		isColorAlive.put(Color.White, false);
		Bukkit.getScheduler().scheduleAsyncDelayedTask(Main.plug, new BukkitRunnable() {

			@Override
			public void run() {
				for (Player p: Bukkit.getOnlinePlayers()) {
					if (PlayerToHisTeam.getOrDefault(p.getName(), null) != null) {
						Color color = PlayerToHisTeam.get((p.getName()));
						if (!playerToHisStealedFlagColor.getOrDefault(p.getName(), Color.White).equals(Color.White)) {
							p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW	, 20, 0));
							
							Color stealed = playerToHisStealedFlagColor.getOrDefault(p, Color.White);
							for (Player ply: Bukkit.getOnlinePlayers()) {
								if (PlayerToHisTeam.getOrDefault(ply, Color.White).equals(stealed)) {
									LineEffect line = new LineEffect(man);
									line.setDynamicOrigin(new DynamicLocation(ply));
									line.setDynamicTarget(new DynamicLocation(p));
									line.color = org.bukkit.Color.ORANGE;
									line.iterations = 1 * 1;
									line.start();
								}
								
							}
							
							
							
						}
						if (p.getLocation().distance(locOfColor.getOrDefault(color, new Location(Bukkit.getWorld("world"), 0, 0, 0))) < 5) {
							Color eliminated = playerToHisStealedFlagColor.get(p.getName());
							isColorWithFlag.put(eliminated, false);
							Bukkit.broadcastMessage("Le drapeau "+ eliminated.getName() + " a été volé par l'équipe de couleur "+ color.getName() + ", ses joueurs ne pourront plus réssuciter");
							if (autoDeath) {
								for (Player p2:Bukkit.getOnlinePlayers()) {
									if (PlayerToHisTeam.getOrDefault(p2, Color.White).equals(eliminated)) {
										p2.damage(400);
									}
								}
							}
							playerToHisStealedFlagColor.put(p.getName(), null);
						}
					}
						
				}
				
			}
			
		}, 20);
	}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent e) {
		if (isGameStarted && PlayerToHisTeam.getOrDefault(e.getPlayer().getName(), null) != null)  {
			Color color = PlayerToHisTeam.get((e.getPlayer().getName()));
			
			for (Color c:colors) {
				if (!c.equals(color) && locOfColor.getOrDefault(c, new Location(Bukkit.getWorld("world"), 0, 0, 0)).equals(e.getBlock().getLocation())) {
					playerToHisStealedFlagColor.put(e.getPlayer().getName()	, c);
					Bukkit.broadcastMessage("Le joueur "+ e.getPlayer()+ " a volé le drapeau "+ c.getName());
					e.setCancelled(true);
					locOfColor.get(c).getBlock().setType(Material.AIR);
				}
			}
			
			
		} else {
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void OnPlayerRespawn(PlayerRespawnEvent e) {
		if (PlayerToHisTeam.getOrDefault(e.getPlayer().getName(), null) != null)  {
			Color color = PlayerToHisTeam.get((e.getPlayer().getName()));
			if (isColorAlive.getOrDefault(color, true)) {
				e.getPlayer().teleport(locOfColor.getOrDefault(color, e.getPlayer().getLocation()));
			}
			
		}
	}
	
	public static void setNameColor(Player p) {
		if (PlayerToHisTeam.getOrDefault(p.getName(), null) != null)  {
			Color color = PlayerToHisTeam.get((p.getName()));
			ChatColor chatColor;
			switch (color) {
			case Black:
				chatColor = ChatColor.BLACK;
				break;
			case Blue:
				chatColor = ChatColor.BLUE;
				break;
			case Green:
				chatColor = ChatColor.GREEN;
				break;
			case Red:
				chatColor = ChatColor.RED;
				break;
			case White:
				chatColor = ChatColor.WHITE;
				break;
			case Yellow:
				chatColor = ChatColor.YELLOW;
				break;
			default:
				chatColor = ChatColor.WHITE;
				break;
			
			}
			p.setCustomName(chatColor+p.getName());
		}
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		setNameColor(e.getPlayer());
	}
	
	public void setBannerOf(Color color) {
		if (locOfColor.get(color) == null) {
			
			System.out.println("loc of color null: "+color.getName());
			return;
		}
		Location loc = locOfColor.get(color);
		Block bloc = loc.getBlock();
		
		(new Location(loc.getWorld(), loc.getBlockX()	, loc.getBlockX() - 1, loc.getBlockX())).getBlock().setType(Material.BEDROCK);
		
		
		isColorWithFlag.put(color, true);
		
		bloc.setType(Material.BANNER);
	}
	
	
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent e) {
		if (!isGameStarted) {
			return;
		}
		
		if (PlayerToHisTeam.getOrDefault(e.getEntity().getName(), null) != null) {
			
			Color color = PlayerToHisTeam.get((e.getEntity().getName()));
			if ( !playerToHisStealedFlagColor.getOrDefault(e.getEntity().getName(), Color.White).equals(Color.White) ) {
				setBannerOf(playerToHisStealedFlagColor.get(e.getEntity().getName()));
				
			}
			
			
			
			if (isColorWithFlag.getOrDefault(color, false)) {
				Bukkit.broadcastMessage("Le joueur "+ e.getEntity().getName() + " a été éliminé");
				e.getEntity().setGameMode(GameMode.SPECTATOR);
				PlayerToHisTeam.put(e.getEntity().getName()	, null);
				for (Player p:Bukkit.getOnlinePlayers()) {
					if (PlayerToHisTeam.getOrDefault(p.getName()	, Color.White).equals(color)) {
						return;
					}
				}
				Bukkit.broadcastMessage("L'équipe de couleur "+color.getName() + " a été éliminée");
				isColorAlive.put(color, false);
				
				Color potentialWin = null;
				for (Player p:Bukkit.getOnlinePlayers()) {
					if (potentialWin == null && PlayerToHisTeam.getOrDefault(p.getName()	, null) != null) {
						potentialWin = PlayerToHisTeam.get(p.getName());
					}
					
					if (potentialWin != null && PlayerToHisTeam.getOrDefault(p.getName()	, null) != null && !PlayerToHisTeam.get(p.getName()).equals(potentialWin)) {
						return;
					}
					
					
					
				}
				
				if (potentialWin != null) {
					Bukkit.broadcastMessage("L'équipe "+ potentialWin.getName()+ " a gagné");
				}
			} 
		}
	}
}
