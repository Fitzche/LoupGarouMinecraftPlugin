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
import org.bukkit.entity.Firework;
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
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import fr.fitzche.lgmore.Main;
import fr.fitzche.lgmore.Util.PlayerUtil;
import net.minecraft.server.v1_8_R3.EntityFireworks;
import net.minecraft.server.v1_8_R3.World;




public class Main2 implements Listener{


	
	public static HashMap<String, Color> playerToHisStealedFlagColor = new HashMap<String, Color>();
	public static HashMap<String, Color> PlayerToHisTeam = new HashMap<String, Color>();

	public static boolean isGameStarted;
	
	
	public static HashMap<Color, Location> locOfColor = new HashMap<Color, Location>();
	public static HashMap<Color, Boolean> isColorWithFlag = new HashMap<Color, Boolean>();
	public static HashMap<Color, Boolean> isColorAlive = new HashMap<Color, Boolean>();
	public static ArrayList<Color> colors = new ArrayList<Color>(Arrays.asList(Color.Black, Color.Blue, Color.Green, Color.Red, Color.Yellow));
	public static JavaPlugin plug;
	public static boolean autoDeath = false;
	public static Scoreboard board;
	public static Team RED;
	public static Team BLUE;
	public static Team YELLOW;
	public static Team GREEN;
	public static Team ORANGE;
	public static Team WHITE;
	public static Team BLACK;
	public static Team GRAY;
	public static Team DARK_BLUE;
	public static Team AQUA;
	public static Team DARK_GREEN;
	public static Team DARK_GRAY;
	public static Team DARK_PURPLE;
	public static Team DARK_RED;
	public static Team DARK_AQUA;
	
	public Main2() {
		
	}

	
	
	
	public static void onEnable() {
		// TODO Auto-generated method stub
		
		Main2.plug = Main.plug;
		board = Bukkit.getScoreboardManager().getNewScoreboard();
		
		
		RED = board.registerNewTeam("RED");
		RED.setPrefix(ChatColor.RED.toString());
		
		BLUE = board.registerNewTeam("BLUE");
		BLUE.setPrefix(ChatColor.BLUE.toString());
		
		YELLOW = board.registerNewTeam("YELLOW");
		YELLOW.setPrefix(ChatColor.YELLOW.toString());
		
		GREEN = board.registerNewTeam("GREEN");
		GREEN.setPrefix(ChatColor.GREEN.toString());
		
	}
	
	
	
	@Deprecated
	public static void start() {
		Bukkit.broadcastMessage("la partie Commence");
		isGameStarted = true;
		for (Player p : Bukkit.getOnlinePlayers()) {
			Main2.setNameColor(p);
			System.out.println("setPlayerColor Main2 71");
		}
		
		
		for (Color color: colors) {
			boolean isPresent = false;
			for (Player present:Bukkit.getOnlinePlayers()) {
				if (PlayerToHisTeam.getOrDefault(present.getName(), null) != null && PlayerToHisTeam.getOrDefault(present.getName(), null).equals(color)) {
					isPresent = true;
				}
				if (PlayerToHisTeam.getOrDefault(present.getName(), null) != null) {
					System.out.println(PlayerToHisTeam.getOrDefault(present.getName(), null).getName());
				} else {
					System.out.println("nul");
				}
			}
			isColorAlive.put(color, isPresent);
		}
		
		for (Color color:colors) {
			if (isColorAlive.getOrDefault(color, false)) {
				Bukkit.broadcastMessage("La couleur "+color.getName() + " est en lice");
				setBannerOf(color);
			}
		}
		
		isColorAlive.put(Color.White, false);
		Bukkit.getScheduler().scheduleSyncRepeatingTask(Main2.plug, new BukkitRunnable() {

			@Override
			public void run() {
				
				for (Player p: Bukkit.getOnlinePlayers()) {
					if (PlayerToHisTeam.getOrDefault(p.getName(), null) != null) {
						Color color = PlayerToHisTeam.get((p.getName()));
						if (playerToHisStealedFlagColor.getOrDefault(p.getName(), null) != null) {
							
							p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW	, 20, 0));
							
							Color stealed = playerToHisStealedFlagColor.getOrDefault(p.getName(), Color.White);
							for (Player ply: Bukkit.getOnlinePlayers()) {
								if (PlayerToHisTeam.getOrDefault(ply.getName(),  null) != null && PlayerToHisTeam.get(ply.getName()).equals(stealed)) {
									PlayerUtil.particle(p.getLocation(), org.bukkit.Color.RED , "ok", 1);
									PlayerUtil.particle(p.getLocation(), org.bukkit.Color.GREEN, "ok", 1);
									PlayerUtil.particle(p.getLocation(), org.bukkit.Color.OLIVE, "ok", 1);
									
									
								}
								
							}
							
							
							
						}
						if (p.getLocation().distance(locOfColor.getOrDefault(color, new Location(Bukkit.getWorld("world"), 0, 0, 0))) < 5 && playerToHisStealedFlagColor.get(p.getName()) != null) {
							Color eliminated = playerToHisStealedFlagColor.get(p.getName());
							if (playerToHisStealedFlagColor.getOrDefault(p.getName(), color).equals(color)) {
								return;
							}
							isColorWithFlag.put(eliminated, false);
							Bukkit.broadcastMessage("Le drapeau "+ eliminated.getName() + " a été volé par l'équipe de couleur "+ color.getName() + ", ses joueurs ne pourront plus réssuciter");
							if (autoDeath) {
								for (Player p2:Bukkit.getOnlinePlayers()) {
									if (PlayerToHisTeam.getOrDefault(p2.getName(), Color.White).equals(eliminated)) {
										p2.damage(400);
									}
								}
							}
							playerToHisStealedFlagColor.put(p.getName(), null);
						}
					}
						
				}
				
			}
			
		}, 20, 20);
	}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent e) {
		
		
		if (isGameStarted && PlayerToHisTeam.getOrDefault(e.getPlayer().getName(), null) != null)  {
			Color color = PlayerToHisTeam.get((e.getPlayer().getName()));
			
			for (Color c:colors) {
				if ( locOfColor.getOrDefault(c, new Location(Bukkit.getWorld("world"), 0, 0, 0)).distance(e.getBlock().getLocation()) < 2) {
					e.setCancelled(true);
					if (c.equals(color)) {
						return;
					}
					
					playerToHisStealedFlagColor.put(e.getPlayer().getName(), c);
					Bukkit.broadcastMessage("Le joueur "+ e.getPlayer()+ " a volé le drapeau "+ c.getName());
					
					locOfColor.get(c).getBlock().setType(Material.AIR);
				}
			}
			
			
		} 
	}
	
	@EventHandler
	public void OnPlayerRespawn(PlayerRespawnEvent e) {
		System.out.println("respawn");
		if (PlayerToHisTeam.getOrDefault(e.getPlayer().getName(), null) != null)  {
			System.out.println("respawn 2");
			Color color = PlayerToHisTeam.get((e.getPlayer().getName()));
			e.getPlayer().teleport(locOfColor.getOrDefault(color, e.getPlayer().getLocation()));
			
		}
	}
	
	public static void setNameColor(Player p) {
		if (PlayerToHisTeam.getOrDefault(p.getName(), null) != null)  {
			Color color = PlayerToHisTeam.get((p.getName()));
			ChatColor chatColor;
			switch (color) {
			case Black:
				BLACK.addPlayer(p);
				break;
			case Blue:
				BLUE.addPlayer(p);
				break;
			case Green:
				GREEN.addPlayer(p);
				break;
			case Red:
				RED.addPlayer(p);
				break;
			case White:
				WHITE.addPlayer(p);
				break;
			case Yellow:
				YELLOW.addPlayer(p);
				break;
			default:
				WHITE.addPlayer(p);
				break;
			
			}
			for (Player ply2:Bukkit.getOnlinePlayers()) {
				ply2.setScoreboard(board);
			}
		}
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		setNameColor(e.getPlayer());
	}
	
	public static void setBannerOf(Color color) {
		if (locOfColor.get(color) == null) {
			
			System.out.println("loc of color null: "+color.getName());
			return;
		}
		Location loc = locOfColor.get(color);
		Block bloc = loc.getBlock();
		
		
		
		isColorWithFlag.put(color, true);
		
		Main.server.getWorld("world").getBlockAt(new Location(loc.getWorld(), loc.getBlockX()	, loc.getBlockX() - 1, loc.getBlockX())).setType(Material.OBSIDIAN);
		Main.server.getWorld("world").getBlockAt(loc).setType(Material.STANDING_BANNER);
		System.out.println("bedrock in "+loc.getBlockX() + "; "+ (loc.getBlockY() - 1) + "; "+ loc.getBlockZ());

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
			
			
			
			if (!isColorWithFlag.getOrDefault(color, true)) {
				Bukkit.broadcastMessage("Le joueur "+ e.getEntity().getName() + " a été éliminé");
				e.getEntity().setGameMode(GameMode.SPECTATOR);
				
				for (Player p:Bukkit.getOnlinePlayers()) {
					if (PlayerToHisTeam.getOrDefault(p.getName()	, Color.White).equals(color)) {
						PlayerToHisTeam.put(e.getEntity().getName()	, null);
						return;
					}
				}
				PlayerToHisTeam.put(e.getEntity().getName()	, null);
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
