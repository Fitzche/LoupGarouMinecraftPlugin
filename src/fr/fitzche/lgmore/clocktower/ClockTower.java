package fr.fitzche.lgmore.clocktower;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.scheduler.BukkitRunnable;

import fr.fitzche.lgmore.Game;
import fr.fitzche.lgmore.Main;
import fr.fitzche.lgmore.PlayerData;
import fr.fitzche.lgmore.Lg.GameType;
import fr.fitzche.lgmore.Util.MathUtil;
import fr.fitzche.lgmore.custom.CustomGame;
import fr.fitzche.lgmore.custom.CustomGameDataSet;
import fr.fitzche.lgmore.custom.CustomGameType;
import fr.fitzche.lgmore.custom.CustomTimer;
import fr.fitzche.lgmore.minecraft.GameListener;

public class ClockTower implements Game {

	public String name;
	public ArrayList<PlayerData> players = new ArrayList<PlayerData>();
	public World world;
	public ArrayList<ClockRole> clockRoles = new ArrayList<ClockRole>();
	public boolean started = false;
	
	public boolean night = false;
	
	
	public ClockTower() {
		this.name = "clockTowerGame-"+ MathUtil.generateAlInt(0, 10000);
	}
	@Override
	public ArrayList<PlayerData> getWinners() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<PlayerData> getPlayers() {
		// TODO Auto-generated method stub
		return players;
	}
	
	public void night() {
		night = true;
		for (PlayerData p:players) {
			Location loc = new Location(world, p.clockRole.loc.getBlockX(), p.clockRole.loc.getBlockY(), p.clockRole.loc.getBlockZ());
			if (p.isOnline) {
				p.player.teleport(loc);
				
			}
			
		}
	}
	public void morning() {
		
	}
	public void discussion() {
		
	}
	public void vote() {
		
	}
	
	
	public void start() {
		this.started = true;
		ArrayList<ClockRole> roles = ClockListGenerator.getList(getActualNbOfPlayer());
		for (PlayerData p:players) {
			int x = MathUtil.generateAlInt(0, roles.size() - 1);
			p.clockRole = roles.get(x);
			roles.remove(x);
			try {
				p.dataClock = (ClockRoleData) p.clockRole.data.getClass().getConstructors()[0].newInstance(p, this);
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void sayRole(PlayerData p) {
		if (p.clockRole != null) {
			p.sendMessage("Vous êtes "+ p.clockRole.name);
			/*
			switch (p.clockRole) {
			case Croque:
				break;
			case Cuistot:
				break;
			case Ecarlate:
				break;
			case Empathe:
				break;
			case Espion:
				break;
			case Evil:
				break;
			case Lavandière:
				break;
			case Moine:
				break;
			case Poisonner:
				break;
			case Pourfendeur:
				break;
			case Reclus:
				break;
			case Saint:
				break;
			case Vierge:
				break;
			case Voyante:
				break;
			default:
				break;
			
			}*/
		}
		
	}

	@Override
	public GameType getType() {
		// TODO Auto-generated method stub
		return GameType.ClockTower;
	}

	@Override
	public World getWorld() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return name;
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
		return null;
	}
	@Override
	public int getMaxNBOfPlayer() {
		if (clockRoles.size() < 7) {
			return 7;
		} 
		return clockRoles.size();
	}
	@Override
	public int getActualNbOfPlayer() {
		// TODO Auto-generated method stub
		return players.size();
	}
	
	@Deprecated
	public void addPlayer(PlayerData player) {
		this.players.add(player);
		broadcoast("Joueur "+ player.getName()+ "ajouté à la partie de ClockTower: "+name);
		if (players.size() > 10) {
			broadcoast("La partie commencera dans 60s si le nombre de roles correspond au nombre de joueurs (10 par défaut)");
			Bukkit.getScheduler().runTaskLater(Main.plug, new BukkitRunnable() {

				@Override
				public void run() {
					if (players.size() == clockRoles.size()) {
						try {
							start();
						} catch (IllegalArgumentException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (SecurityException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					
				}
				
			}, 1200);
		}
		
	}
	@Override
	public void playerQuit(String name) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void playerDefinitlyQuit(String name) {
		// TODO Auto-generated method stub
		
	}
	
	
	
	

}
