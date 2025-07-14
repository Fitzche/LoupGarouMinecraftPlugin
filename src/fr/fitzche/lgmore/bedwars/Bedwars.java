package fr.fitzche.lgmore.bedwars;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftArmorStand;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Minecart;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.sk89q.worldedit.Vector;

import WorldEditUtil.EmptyWorldGenerator;
import fr.fitzche.lgmore.Camp;
import fr.fitzche.lgmore.Game;
import fr.fitzche.lgmore.Main;
import fr.fitzche.lgmore.PlayerData;
import fr.fitzche.lgmore.Lg.GameType;
import fr.fitzche.lgmore.Minage.MinageWorld;
import fr.fitzche.lgmore.RolesLg.SWAPPER;
import fr.fitzche.lgmore.Util.MathUtil;
import fr.fitzche.lgmore.minecraft.GameListener;
import net.minecraft.server.v1_8_R3.EntityArmorStand;

public class Bedwars implements Game {

	public boolean started = false;
	public World world;
	public String name;
	
	public ArrayList<PlayerData> players = new ArrayList<PlayerData>();
	public ArrayList<PlayerData> alives = new ArrayList<PlayerData>();
	BedWarMap map;
	public int nbOfPlayers;
	
	public BedListener listener = new BedListener(this);
	
	public HashMap<BedTeam, Location> spawnOfTeams = new HashMap<BedTeam, Location>();
	public HashMap<BedTeam, Location> cartOfTeams = new HashMap<BedTeam, Location>();
	public HashMap<BedTeam, Location> generatorOfTeams = new HashMap<BedTeam, Location>();
	
	public ArrayList<Location> diamonds = new ArrayList<Location>();
	public ArrayList<Location> emeralds = new ArrayList<Location>();
	public ArrayList<Location> trader = new ArrayList<Location>();
	public ArrayList<Location> traderD = new ArrayList<Location>();
	
	final public static int timeOnDeath = 8;
	
	
	
	
	
	public HashMap<BedTeam, Integer> lifeOfTeams = new HashMap<BedTeam, Integer>();
	public Bedwars(BedWarMap map) {
		
		Bukkit.getPluginManager().registerEvents(listener, Main.plug);
		this.map = map;
		this.name = "bedwar-|map"+ map.getName()+ "|"+ MathUtil.generateAlInt(0, 10000);
		
		WorldCreator c = new WorldCreator("world"+this.name).generator(Main.world.getGenerator());
		c.generator(new EmptyWorldGenerator());
		this.world = c.createWorld();
		
		Main.placeMap(new Location(world, 0, 100, 0), world, map.path);
		world.getBlockAt(new Location(world, 0, 99, 0)).setType(Material.BEDROCK);
		world.getBlockAt(new Location(world, 0, 100, 0)).setType(Material.AIR);
		world.getBlockAt(new Location(world, 0, 101, 0)).setType(Material.AIR);
		world.getBlockAt(new Location(world, 0, 102, 0)).setType(Material.AIR);
		
		for (int x = 0; x <= 400;x++ ) {
			for (int y = 0; y <= 400;y++ ) {
				for (int z = 0; z <= 100; z++) {
					world.getBlockAt(x-200, y-200, z+50).setMetadata("unbreakable", Main.unbreakableMeta);
				}
			}
		}
		
		nbOfPlayers = map.nbOfPlayerTeam * map.nbOfTeam;
		
		
		for (BedLoc loc:map.bedLocs) {
			loc.loc.setWorld(world);
			if (loc.type.equals(BedLocType.Spawn)) {
				spawnOfTeams.put(loc.defaultTeam, loc.loc);
			}else if (loc.type.equals(BedLocType.CartPosition)) {
				cartOfTeams.put(loc.defaultTeam, loc.loc);
			} else if (loc.type.equals(BedLocType.MultiGenerator)) {
				generatorOfTeams.put(loc.defaultTeam, loc.loc);
			} else if (loc.type.equals(BedLocType.DiamondGenerator)) {
				this.diamonds.add(loc.loc);
			} else if (loc.type.equals(BedLocType.EmeraldGenerator)) {
				this.emeralds.add(loc.loc);
			}else if (loc.type.equals(BedLocType.BaseTrader)) {
				this.trader.add(loc.loc);
			}else if (loc.type.equals(BedLocType.DiamondGenerator)) {
				this.traderD.add(loc.loc);
			}
			
			
		}
		
		for (Entry<BedTeam, Integer> entry:lifeOfTeams.entrySet()) {
			lifeOfTeams.put(entry.getKey(), 10);
		}
		for (Entry<BedTeam, Location> entry:cartOfTeams.entrySet()) {
			ArmorStand stand =entry.getValue().getWorld().spawn(entry.getValue(), ArmorStand.class);
			stand.setMetadata("unbreakable", Main.unbreakableMeta);
			stand.setMetadata("bedTeam", entry.getKey().getMetaValue());
			
		}
		
	}
	
	@Deprecated
	public boolean addPlayer(PlayerData p) {
		if (p.isFree()) {
			p.clearLgGameVar();
			
			if (players.size() >= nbOfPlayers) {
				p.sendMessage(ChatColor.RED+"Cette partie est pleine");
			} else {
				players.add(p);
			}
			
			
			
			
			if (players.size() == nbOfPlayers) {
				broad("Début dans 15 secs si assez de joueurs");
				Bukkit.getScheduler().runTaskLater(Main.plug, new BukkitRunnable() {

					@Override
					public void run() {
						if (players.size() == nbOfPlayers  && !started) {
							start();
							
						}
						
					}
					
				}, 300);
			}
			return true;
			
		} else {
			return false;
		}
		
	}
	public void broad(String msg) {
		for (PlayerData p:players) {
			p.sendMessage(msg);
		}
	}
	
	@Deprecated
	public void start() {
		MinageWorld min = new MinageWorld(new BukkitRunnable() {

			@Override
			public void run() {
				tpSpawn();
				
				
				
			}
			
		}, new ArrayList<Material>(Arrays.asList(
				Material.DIAMOND, 
				Material.LAPIS_ORE,
				Material.GOLD_INGOT,
				Material.IRON_INGOT,
				Material.EMERALD, 
				Material.REDSTONE, 
				Material.COAL
				
				)), 1200, 3, 4, 2.5, false, 1000, players);
	}
	
	@Deprecated
	public void tpSpawn() 
	{
		
		for (PlayerData p:players) {
			p.rejoinLoc = spawnOfTeams.get(p.bedTeam);
			if (p.isOnline) {
				p.player.teleport(spawnOfTeams.get(p.bedTeam));
				p.player.sendTitle(ChatColor.GOLD+"Début de la Phase: ", ChatColor.RED+"Combat");
			}
		}
		
		
	}
	
	
	
	/*
	 *
	 * 
	 * 
	 * 
	 * 
	 * 
	 * spawn du minecart
	 * 
	 * 
	 * gérer attaque sur minecart --> vie
	 * marchands 
	 * 
	 * */

	
	public void giveTeams() {
		if (players.size() != nbOfPlayers) {
			broad("Le nombre de joueur necessaire au début de la partie est incorrect");
			return;
		}
		if (map.nbOfTeam == 4) {
			ArrayList<BedTeam> teams = new ArrayList<BedTeam>(Arrays.asList(BedTeam.YELLOW, BedTeam.RED, BedTeam.GREEN, BedTeam.Blue));
			HashMap<BedTeam, Integer> nbInTeam = new HashMap<BedTeam, Integer>();
			
			for (PlayerData p:players) {
				if (teams.size() > 0) {
					if (p.bedTeam != null && teams.contains(p.bedTeam)) {} else {
						p.bedTeam = teams.get(MathUtil.generateAlInt(0, teams.size() - 1));
					
					}
					nbInTeam.put(p.bedTeam, nbInTeam.getOrDefault(p.bedTeam, 0) + 1);
					if (nbInTeam.get(p.bedTeam) >= map.nbOfPlayerTeam) {
						teams.remove(p.bedTeam);
					}
				}
				p.sendMessage("Votre couleur est "+ p.bedTeam.getName());
			}
		}
	}
	
	public void removePlayer(PlayerData p) {
		if (players.contains(p)) {
			players.remove(p);
			p.clearLgGameVar();
		}
	}
	
	
	@Override
	public ArrayList<PlayerData> getWinners() {
		// TODO Auto-generated method stub
		return alives;
	}

	@Override
	public ArrayList<PlayerData> getPlayers() {
		// TODO Auto-generated method stub
		return players;
	}

	@Override
	public GameType getType() {
		// TODO Auto-generated method stub
		return GameType.Bedwars;
	}

	@Override
	public World getWorld() {
		// TODO Auto-generated method stub
		return this.world;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}

	@Override
	public void broadcoast(String message) {
		for (PlayerData p:this.players) {
			p.sendMessage(message);
		}
		
	}

	@Override
	public void setWorld(World world) {
		this.world = world;
		
	}

	@Override
	public GameListener getListener() {
		// TODO Auto-generated method stub
		return this.listener;
	}
	
	public void win(BedTeam team) {
		int kills = 0;
		for (PlayerData p:players) {
			if (p.bedTeam.equals(team)) {
				kills += p.numberOfKill;
			}
		}
		broad("L'équipe "+team.getName()+ " a gagné avec "+kills+" kill;");
		
	}
	
	public void damageBed(BedTeam  team) {
		lifeOfTeams.put(team, lifeOfTeams.getOrDefault(team, 10) - 1);
		if (lifeOfTeams.get(team) <= 0) {
			broadcoast(ChatColor.GOLD + "Le point de Respawn de l'équipe "+ team.getName() + " a été détruit");
		}
	}
	

}
