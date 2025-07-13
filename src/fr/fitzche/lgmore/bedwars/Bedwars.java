package fr.fitzche.lgmore.bedwars;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;

import WorldEditUtil.EmptyWorldGenerator;
import fr.fitzche.lgmore.Camp;
import fr.fitzche.lgmore.Game;
import fr.fitzche.lgmore.Main;
import fr.fitzche.lgmore.PlayerData;
import fr.fitzche.lgmore.Lg.GameType;
import fr.fitzche.lgmore.RolesLg.SWAPPER;
import fr.fitzche.lgmore.Util.MathUtil;
import fr.fitzche.lgmore.minecraft.GameListener;

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
		
	}
	
	public boolean addPlayer(PlayerData p) {
		if (p.isFree()) {
			p.clearLgGameVar();
			players.add(p);
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
	
	public void start() {
		for (PlayerData p:players) {
			p.rejoinLoc = spawnOfTeams.get(p.bedTeam);
			if (p.isOnline) {
				p.player.teleport(spawnOfTeams.get(p.bedTeam));
			}
		}
	}
	
	public void tpSpawn() 
	{
		
		for (PlayerData p:players) {
			p.rejoinLoc = spawnOfTeams.get(p.bedTeam);
			if (p.isOnline) {
				p.player.teleport(spawnOfTeams.get(p.bedTeam));
			}
		}
	}
	
	
	ici
	/*
	 *
	 * gerer respawn
	 * gerer couche 0 = mort
	 * mort = respawn mais immobil 8 secs ( changeable )
	 * start partie auto
	 * fin = vie 0 + mort
	 * victoire
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
	

}
