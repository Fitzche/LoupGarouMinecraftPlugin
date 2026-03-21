package fr.fitzche.lgmore.settlerGame;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import fr.fitzche.lgmore.Util.MathUtil;
import fr.fitzche.lgmore.custom.CustomRole;
import fr.fitzche.lgmore.custom.RoleSet;
import net.md_5.bungee.api.ChatColor;

public class SettleGameSet implements RoleSet {

	
	public SettlerGame settlerGame;
	public HashMap<String, CustomRole> rolesOfPlayers = new HashMap<String, CustomRole>();
	public HashMap<String, String> victories = new HashMap<String, String>();
	public HashMap<String, ChatColor> colorsOfTeam = new HashMap<String, ChatColor>();
	public HashMap<String, String> teamOfPlayer = new HashMap<String, String>();
	
	
	public SettleGameSet(SettlerGame settlerGame, int actualNbOfPlayer, HashMap<String, String> teamOfPlayers) {
		this.settlerGame = settlerGame;
		
		
		
		HashMap<String, ArrayList<String>> teams = new HashMap<String, ArrayList<String>>();
		for (String player: teamOfPlayers.keySet()) {
			ArrayList<String> team = teams.getOrDefault(teamOfPlayers.getOrDefault(player, "noTeam"), new ArrayList<String>());
			team.add(player);
			teams.put(teamOfPlayers.getOrDefault(player, "noTeam"),team);
		}
		
		for (String team:teams.keySet()) {
			
			ArrayList<ChatColor> colors = new ArrayList<ChatColor>(Arrays.asList(ChatColor.BLUE, ChatColor.YELLOW, ChatColor.RED, ChatColor.GREEN, ChatColor.GOLD));
			colorsOfTeam.put(team, colors.get(MathUtil.generateAlInt(0, colors.size() - 1)));
			
			SettlerTeamData teamData = new SettlerTeamData();
			ArrayList<String> names = teams.get(team);
			teamData.players = names;
			
			for (String name:names) {
				this.teamOfPlayer.put(name, team);
			}
			
			
			for (String role:Arrays.asList("Leader", "Builder", "Explorateur", "Fighter")) {
				if (names.size() > 0) {
					int leaderIndex = MathUtil.generateAlInt(0, names.size() - 1);
					String leader = names.get(leaderIndex);
					
					this.setRolesOfPlayer(leader, new Settler(this, leader, this.settlerGame, team, role));
					teamData.playerOfSpecialRole.put(role, names.get(leaderIndex));
					names.remove(leaderIndex);
					}
			}
			
			
			
			for (String name:names) {
				this.setRolesOfPlayer(name, new Settler(this, name, this.settlerGame, team, "Citizen"));

			}
			
			
		
		
		
		}
		
		
	}

	@Override
	public HashMap<String, CustomRole> rolesOfPlayer() {
		// TODO Auto-generated method stub
		return rolesOfPlayers;
	}

	@Override
	public void setRolesOfPlayer(String name, CustomRole role) {
		rolesSettedOfPlayers().put(name, role);
		rolesOfPlayers.put(name, role);

	}

	@Override
	public ArrayList<CustomRole> roles(int n) {
		return new ArrayList<CustomRole>(rolesOfPlayers.values());
	}

	@Override
	public HashMap<String, String> victoryOfPlayers() {
		
		
		return teamOfPlayer;
	}

	@Override
	public int applicationTime() {
		// TODO Auto-generated method stub
		return 10;
	}

	@Override
	public void setVictoryOfPlayer(String name, String campName) {
		victories.put(name, campName);

	}

	@Override
	public HashMap<String, CustomRole> rolesSettedOfPlayers() {
		// TODO Auto-generated method stub
		return rolesOfPlayers;
	}

}
