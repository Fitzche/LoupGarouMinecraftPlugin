package fr.fitzche.lgmore.uhc_color.PlayerColorboard;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Team;



public class Colorboard {
	public org.bukkit.scoreboard.Scoreboard board;
	
	public Player player;
	private Team RED;
	private Team BLUE;
	private Team YELLOW;
	private Team GREEN;
	private Team ORANGE;
	private Team WHITE;
	private Team BLACK;
	private Team GRAY;
	private Team DARK_BLUE;
	private Team AQUA;
	private Team DARK_GREEN;
	private Team DARK_GRAY;
	private Team DARK_PURPLE;
	private Team DARK_RED;
	private Team DARK_AQUA;
	
	
	
	public Colorboard(Player p) {
		board = Bukkit.getScoreboardManager().getNewScoreboard();
		
		
		RED = board.registerNewTeam("RED");
		RED.setPrefix(ChatColor.RED.toString());
		
		BLUE = board.registerNewTeam("BLUE");
		BLUE.setPrefix(ChatColor.BLUE.toString());
		
		YELLOW = board.registerNewTeam("YELLOW");
		YELLOW.setPrefix(ChatColor.YELLOW.toString());
		
		GREEN = board.registerNewTeam("GREEN");
		GREEN.setPrefix(ChatColor.GREEN.toString());
		
		ORANGE = board.registerNewTeam("ORANGE");
		ORANGE.setPrefix(ChatColor.GOLD.toString());
		
		WHITE = board.registerNewTeam("WHITE");
		WHITE.setPrefix(ChatColor.WHITE.toString());
		
		BLACK = board.registerNewTeam("BLACK");
		BLACK.setPrefix(ChatColor.BLACK.toString());
		
		GRAY = board.registerNewTeam("GRAY");
		GRAY.setPrefix(ChatColor.GRAY.toString());
		
		DARK_BLUE = board.registerNewTeam("DARK_BLUE");
		DARK_BLUE.setPrefix(ChatColor.DARK_BLUE.toString());
		
		
		this.player = p;
		p.setScoreboard(board);
		
	}
	
	public void ColorPlayer(Player p, String color) {
		
		for (Team t:this.board.getTeams()) {
			t.removePlayer(p);
		}
		getTeam(color).addPlayer(p);
		System.out.println(p.getName() + " ajouté à "+ color);
		this.player.sendMessage("Vous avez coloré "+ p.getName()+ " en "+color);
		player.closeInventory();
	}
	
	public void refreshScore() {
		this.player.setScoreboard(board);
	}
	
	public String getTeamOfPlayer(Player player) {
		for (Team t:board.getTeams()) {
			if (t.hasPlayer(player)) {
				return t.getDisplayName();
			}
		}
		return this.WHITE.getDisplayName();
	}


	public static String getStrFromColor(ChatColor color) {
		if (color.equals(ChatColor.RED)) {
			return "RED";
		} else if (color.equals(Color.BLUE)) {
			return "BLUE";
		} else if (color.equals(ChatColor.YELLOW)) {
			return "YELLOW";
		} else if (color.equals(ChatColor.GREEN)) {
			return "GREEN";
		} else if (color.equals(ChatColor.GOLD)) {
			return "ORANGE";
		} else if (color.equals(ChatColor.WHITE)) {
			return "WHITE";
		} else if (color.equals(ChatColor.BLACK)) {
			return "BLACK";
		} else if (color.equals(ChatColor.DARK_GRAY)) {
			return "DARK_GRAY";
		} else if (color.equals(ChatColor.DARK_BLUE)) {
			return "DARK_BLUE";
		} else if (color.equals(ChatColor.GRAY)) {
			return "GRAY";
		} else if (color.equals(ChatColor.AQUA)) {
			return "AQUA";
		} else if (color.equals(ChatColor.DARK_GREEN)) {
			return "DARK_GREEN";
		} else if (color.equals(ChatColor.DARK_RED)) {
			return "DARK_RED";
		} else if (color.equals(ChatColor.DARK_PURPLE)) {
			return "DARK_PURPLE";
		} else if (color.equals(ChatColor.DARK_AQUA)) {
			return "DARK_AQUA";
		} else if (color.equals(ChatColor.RED)) {
			return "RED";
		} else {
			return null;
		}
	}
	
	public ChatColor getColorOfTeam(String color) {
		if (color.equals("RED")) {
			return ChatColor.RED;
		} else if (color.equals("BLUE")) {
			return ChatColor.BLUE;
		} else if (color.equals("YELLOW")) {
			return ChatColor.YELLOW;
		} else if (color.equals("GREEN")) {
			return ChatColor.GREEN;
		} else if (color.equals("ORANGE")) {
			return ChatColor.GOLD;
		} else if (color.equals("WHITE")) {
			return ChatColor.WHITE;
		} else if (color.equals("BLACK")) {
			return ChatColor.BLACK;
		} else if (color.equals("DARK GRAY")) {
			return ChatColor.DARK_GRAY;
		} else if (color.equals("DARK BLUE")) {
			return ChatColor.DARK_BLUE;
		} else if (color.equals("GRAY")) {
			return ChatColor.GRAY;
		} else if (color.equals("AQUA")) {
			return ChatColor.AQUA;
		} else if (color.equals("DARK GREEN")) {
			return ChatColor.DARK_GREEN;
		} else if (color.equals("DARK RED")) {
			return ChatColor.DARK_RED;
		} else if (color.equals("DARK PURPLE")) {
			return ChatColor.DARK_PURPLE;
		} else if (color.equals("DARK AQUA")) {
			return ChatColor.DARK_AQUA;
		} else if (color.equals("RED")) {
			return ChatColor.RED;
		} else {
			return null;
		}
	}

	
	
	
	public Team getTeam(String color) {
		if (color.equals("RED")) {
			return this.RED;
		} else if (color.equals(ChatColor.BLUE+"BLUE")) {
			return this.BLUE;
		} else if (color.equals(ChatColor.YELLOW+"YELLOW")) {
			return this.YELLOW;
		} else if (color.equals(ChatColor.GREEN+"GREEN")) {
			return this.GREEN;
		} else if (color.equals(ChatColor.GOLD+"ORANGE")) {
			return this.ORANGE;
		} else if (color.equals(ChatColor.MAGIC+"WHITE")) {
			return this.WHITE;
		} else if (color.equals(ChatColor.BLACK+"BLACK")) {
			return this.BLACK;
		} else if (color.equals("DARK GRAY")) {
			return this.DARK_GRAY;
		} else if (color.equals(ChatColor.DARK_BLUE+"DARK BLUE")) {
			return this.DARK_BLUE;
		} else if (color.equals(ChatColor.GRAY+"GRAY")) {
			return this.GRAY;
		} else if (color.equals("AQUA")) {
			return this.AQUA;
		} else if (color.equals("DARK GREEN")) {
			return this.DARK_GREEN;
		} else if (color.equals("DARK RED")) {
			return this.DARK_RED;
		} else if (color.equals("DARK PURPLE")) {
			return this.DARK_PURPLE;
		} else if (color.equals("DARK AQUA")) {
			return this.DARK_AQUA;
		} else if (color.equals(ChatColor.RED+"RED")) {
			return this.RED;
		} else {
			return null;
		}
		
	}
}
