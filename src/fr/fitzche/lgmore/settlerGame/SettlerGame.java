package fr.fitzche.lgmore.settlerGame;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import org.bukkit.World;

import fr.fitzche.lgmore.Main;
import fr.fitzche.lgmore.PlayerData;
import fr.fitzche.lgmore.Lg.GameType;
import fr.fitzche.lgmore.Util.LocationUtil;
import fr.fitzche.lgmore.Util.MathUtil;
import fr.fitzche.lgmore.custom.CustomGame;
import fr.fitzche.lgmore.custom.CustomGameDataSet;
import fr.fitzche.lgmore.custom.CustomParam;
import fr.fitzche.lgmore.custom.CustomTimer;
import fr.fitzche.lgmore.custom.RoleSet;
import fr.fitzche.lgmore.minecraft.GameListener;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class SettlerGame implements CustomGame {

	
	ArrayList<PlayerData> players = new ArrayList<PlayerData>();
	ArrayList<PlayerData> basePlayers = new ArrayList<PlayerData>();
	boolean started = false;
	String name = ("Game-"+"SettlerGame"+MathUtil.generateAlInt(0, 1000));
	private CustomTimer timer;
	
	public String hoster = "";
	private ArrayList<RoleSet> sets = new ArrayList<RoleSet>();
	private World world;
	private CustomGameDataSet data;
	public HashMap<String, String> teamOfPlayers = new HashMap<String, String>();
	HashMap<String, SettlerTeamData> dataOfTeam = new HashMap<String, SettlerTeamData>();
	
	public SettlerTeamData getDataOfTeamOfPlayer(String name) {
		if (teamOfPlayers.get(name) != null) {
			return dataOfTeam.get(teamOfPlayers.get(name));
		}
		return null;
	}
	
	
	public SettlerGame() {
		
	}
	
	
	@Override
	public void playerDefinitlyQuit(String playerName) {
		if (started) {
			CustomGame.death(this, playerName);
		}

	}

	@Override
	public boolean canJoin(PlayerData p) {
		if (started) {
			return false;
		}
		
		if (hoster=="" ) {
			hoster = p.getName();
			p.sendMessage("Vous pouvez lancer la partie avec la commande /lg settlerStart");
		}
		return true;
	}

	@Override
	public void addPlayer(PlayerData p) {
		this.basePlayers.add(p);
		ArrayList<String> teams = new ArrayList<String>(Arrays.asList("1", "2", "3", "4", "5"));
		
		for (String teamName:teams) {
			TextComponent text = new TextComponent();
			text.setText("Clicquez ici pour rejoindre la team" + teamName);
			text.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, ("/lg chooseSettlerTeam "+ teamName)));
			p.player.spigot().sendMessage(text);
		}
	}

	@Override
	public ArrayList<PlayerData> getWinners() {
		
		return null;
	}

	@Override
	public ArrayList<PlayerData> getPlayers() {
		if (!started) {
			return basePlayers;
		}
		return players;
	}

	@Override
	public boolean hasStarted() {
		// TODO Auto-generated method stub
		return started;
	}

	@Override
	public void playerQuit(String name) {
		
		if (!started) {
			basePlayers.remove(Main.getData(name));
			if (name.equals(hoster)) {
				if (basePlayers.size() > 0) {
					hoster = basePlayers.get(0).getName();
					basePlayers.get(0).sendMessage("Vous pouvez lancer la partie avec la commande /lg settlerStart");

				} else {
					hoster = "";
				}
			}
		} else {
			
		}

	}

	@Override
	public GameType getType() {
		// TODO Auto-generated method stub
		return GameType.SettlerGame;
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
		for (PlayerData p:getPlayers()) {
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
		// TODO Auto-generated method stub
		return 1000;
	}

	@Override
	public int getActualNbOfPlayer() {
		// TODO Auto-generated method stub
		return players.size();
	}

	@Override
	public void askRunFuturesActions() {
		// TODO Auto-generated method stub

	}

	@Override
	public CustomTimer getTimer() {
		// TODO Auto-generated method stub
		return this.timer;
	}

	@Override
	public void eachSecond() {
		// TODO Auto-generated method stub

	}

	@Override
	public ArrayList<RoleSet> rolesSet() {
		
		// TODO Auto-generated method stub
		return sets;
	}

	@Override
	public void setTimer(CustomTimer customTimer) {
		this.timer = customTimer;

	}

	@Override
	public CustomGameDataSet getData() {
		// TODO Auto-generated method stub
		return data;
	}

	@Override
	public void start() {
		System.out.println("settle game start");
		this.started = true;
		
		for (PlayerData p:basePlayers) {
			players.add(p);
			if (teamOfPlayers.get(p.getName()) == null) {
				HashMap<String, Integer> nbOfPlyPerTeam = new HashMap<String, Integer>();
				
				for (String team:teamOfPlayers.values()) {
					nbOfPlyPerTeam.put(team, nbOfPlyPerTeam.getOrDefault(team, 0) + 1);
				}
				
				
				int less = 1000;
				String team = "noTeam";
				for (String str:nbOfPlyPerTeam.keySet()) {
					if (nbOfPlyPerTeam.getOrDefault(str, 1000) < less) {
						less = nbOfPlyPerTeam.getOrDefault(str, 1000);
						team = str;
					}
				}
				
				teamOfPlayers.put(p.getName(), team);
				System.out.println("team of "+ p.getName() + " is setted to "+ team);
				
			}
		}
		
		
		sets.add(new SettleGameSet(this, getActualNbOfPlayer(), teamOfPlayers));
		this.data = new CustomGameDataSet(
				false,
				1000,
				"", 
				false,
				1,
				1,
				1,
				10,
				true,
				sets,
				new ArrayList<CustomParam>());
		//System.out.println("run");
		CustomGame.run(this);
		for (PlayerData p:this.players) {
			LocationUtil.tpAl(p, 1000, world);
		}
		
		/*for (PlayerData p:getPlayers()) {
			System.out.println("player "+ p.getName() +" is present with role "+ rolesSet().get(0).rolesOfPlayer().get(p.getName()));
		}*/

	}

}
