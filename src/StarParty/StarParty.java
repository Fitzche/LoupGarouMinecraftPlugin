package StarParty;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

import WorldEditUtil.EmptyWorldGenerator;
import fr.fitzche.lgmore.Game;
import fr.fitzche.lgmore.Main;
import fr.fitzche.lgmore.PlayerData;
import fr.fitzche.lgmore.Lg.GameType;
import fr.fitzche.lgmore.Util.MathUtil;
import fr.fitzche.lgmore.minecraft.GameListener;
import fr.fitzche.lgmore.scoreboard.StarBoard;
import net.minecraft.server.v1_8_R3.WorldGenTallPlant;

public class StarParty implements Listener,Game{

	public ArrayList<PlayerData> players = new ArrayList<PlayerData>();
	public boolean started = false;
	public StarListener listener = new StarListener(this);
	public String name;
	public World world;
	
	public StarParty(String name) {
		Bukkit.getPluginManager().registerEvents(this, Main.plug);
		this.name = name;
		Bukkit.getPluginManager().registerEvents(listener, Main.plug);
		
	}
	
	
	public void broad(String message) {
		for (PlayerData p:players) {
			p.sendMessage(message);
		}
	}
	
	public void removePlayer(PlayerData p) {
		players.remove(p);
		p.starParty = null;
		p.roleStar = null;
		p.roleSta = null;
	}
	
	@Deprecated
	public void addPlayer(PlayerData p) {
		if (started || players.size() >= 17) {
			p.sendMessage("Cette party est complète ou déjà commencée");
			return;
		}
		players.add(p);
		p.sendMessage("ajouté à la game de star party: "+ name);
		
		for (PlayerData ply:players) {
			ply.sendMessage("["+players.size()+"/17]");
		}
		p.starParty = this;
		if (players.size() == 17) {
			start();
		} else if (players.size() >= 15) {
			broad("Début dans 15 secs");
			Bukkit.getScheduler().runTaskLater(Main.plug, new BukkitRunnable() {

				@Override
				public void run() {
					if (players.size() >= 15 && !started) {
						start();
						
					}
					
				}
				
			}, 300);
		}
	}
	
	public void start() {
		WorldCreator c = new WorldCreator("world"+this.name).generator(Main.world.getGenerator());
		c.generator(new EmptyWorldGenerator());
		this.world = c.createWorld();
		
		Main.placeStarMap(new Location(world, 0, 100, 0), world);
		world.getBlockAt(new Location(world, 0, 99, 0)).setType(Material.BEDROCK);
		world.getBlockAt(new Location(world, 0, 100, 0)).setType(Material.AIR);
		world.getBlockAt(new Location(world, 0, 101, 0)).setType(Material.AIR);
		world.getBlockAt(new Location(world, 0, 102, 0)).setType(Material.AIR);
		started = true;
		ArrayList<RolesStar> roles = new ArrayList<RolesStar>();
		broad("La partie commence");
		switch (players.size()) {
			case 15:
				roles = new ArrayList<RolesStar>(Arrays.asList(
						RolesStar.ObiWan,
						RolesStar.Yoda, 
						RolesStar.Windu,
						RolesStar.Luke, 
						RolesStar.HanSolo,
						RolesStar.Chewbaca, 
						RolesStar.Leila,
						
						RolesStar.DarkMaul ,
						RolesStar.DarkVador, 
						RolesStar.Palpa, 
						RolesStar.Dooku, 
						RolesStar.Grievou, 
						RolesStar.Impe, 
						RolesStar.Impe,
					
						RolesStar.Jango
						
						));
				break;
			case 16:
				roles = new ArrayList<RolesStar>(Arrays.asList(
						RolesStar.ObiWan,
						RolesStar.Yoda, 
						RolesStar.Windu,
						RolesStar.Luke, 
						RolesStar.HanSolo,
						RolesStar.Chewbaca, 
						RolesStar.Leila,
						RolesStar.QuiGon,
						RolesStar.DarkMaul ,
						RolesStar.DarkVador, 
						RolesStar.Palpa, 
						RolesStar.Dooku, 
						RolesStar.Grievou, 
						RolesStar.Impe, 
						RolesStar.Impe,
						RolesStar.Stormtrooper
						
						
						));;
				break;
			case 17:
				roles = new ArrayList<RolesStar>(Arrays.asList(
						RolesStar.ObiWan,
						RolesStar.Yoda, 
						RolesStar.Windu,
						RolesStar.Luke, 
						RolesStar.HanSolo,
						RolesStar.Chewbaca, 
						RolesStar.Leila,
						RolesStar.QuiGon,
						RolesStar.DarkMaul ,
						RolesStar.DarkVador, 
						RolesStar.Palpa, 
						RolesStar.Dooku, 
						RolesStar.Grievou, 
						RolesStar.Impe, 
						RolesStar.Impe,
						RolesStar.Stormtrooper,
						RolesStar.Jango
						
						));
				break;
			
		}
		if (players.size() < 15) {
			roles = new ArrayList<RolesStar>(Arrays.asList(
					RolesStar.ObiWan,
					RolesStar.Yoda, 
					RolesStar.Windu,
					RolesStar.Luke, 
					RolesStar.HanSolo,
					RolesStar.Chewbaca, 
					RolesStar.Leila,
					RolesStar.QuiGon,
					RolesStar.DarkMaul ,
					RolesStar.DarkVador, 
					RolesStar.Palpa, 
					RolesStar.Dooku, 
					RolesStar.Grievou, 
					RolesStar.Impe, 
					RolesStar.Impe,
					RolesStar.Stormtrooper,
					RolesStar.Jango
					
					));
		}
		if (roles.size() == 0) {
			broad("Erreur quand au lancement, vous êtes ejecté de la partie");
			this.players = new ArrayList<PlayerData>();
			return;
		}
		int trie = 0;
		ArrayList<PlayerData> copie = new ArrayList<PlayerData>();
		while (copie.size() > 0 && trie < 100) {
			int x =MathUtil.generateAlInt(0, players.size() - 1);
			copie.get(x).roleStar = roles.get(0);
			copie.get(x).sendMessage("Vous êtes "+ roles.get(0).getName() + "\n"+ roles.get(0).getDescription());

			copie.remove(x);
			roles.remove(0);
			trie ++;
		}
		
		
		
		for (PlayerData p:players) {
			Location loc = p.roleStar.getLoc();
			loc.setWorld(world);
			p.player.teleport(loc);
			p.roleSta.onRole();
			StarBoard board = new StarBoard(p);
		}
	}
	
	public void death(PlayerData p) {
		broad("Le joueur "+ p.getName() + " est mort, il était "+ p.roleStar.getName());
		p.starParty.players.remove(p);
		p.roleStar = null;
		p.starParty = null;
		p.roleSta = null;
		p.player.teleport(Main.world.getSpawnLocation());
		checkWin();
	}
	
	public void checkWin() {
		ChatColor color = null;
		for (PlayerData p:players) {
			if (color == null) {
				color = p.roleStar.getColor();
			}
			if (!p.roleStar.getColor().equals(color)) {
				return;
			}
		}
		
		if (color == null) {
			return;
		}
		String str = "";
		if (color.equals(ChatColor.RED)) {
			str = "Sith";
		} else if (color.equals(ChatColor.BLUE)) {
			str = "Jedi";
		} else if (color.equals(ChatColor.GOLD)) {
			str = "Chasseur de Prime";
		}
		
		broad(ChatColor.DARK_PURPLE + "Le camp "+ color + str + ChatColor.DARK_PURPLE+" a gagné");
		for (PlayerData p:players) {
			p.starParty = null;
			p.roleStar = null;
			p.roleSta = null;
		}
	}
	
	@EventHandler
	public void onPlayerQuit(PlayerJoinEvent e) {
		for (PlayerData p:players) {
			if (p.getName().equals(e.getPlayer().getName())) {
				death(p);
			}
		}
	}


	@Override
	public ArrayList<PlayerData> getWinners() {
		// TODO Auto-generated method stub
		return getPlayers();
	}


	@Override
	public ArrayList<PlayerData> getPlayers() {
		// TODO Auto-generated method stub
		return players;
	}


	@Override
	public GameType getType() {
		// TODO Auto-generated method stub
		return GameType.StarParty;
	}


	@Override
	public World getWorld() {
		// TODO Auto-generated method stub
		return world;
	}


	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return this.name;
	}


	@Override
	public void broadcoast(String message) {
		for (PlayerData p:players) {
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
