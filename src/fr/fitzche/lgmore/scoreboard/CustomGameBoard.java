package fr.fitzche.lgmore.scoreboard;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

import StarParty.StarParty;
import fr.fitzche.lgmore.PlayerData;
import fr.fitzche.lgmore.Lg.GameLg;
import fr.fitzche.lgmore.custom.CustomGame;

public class CustomGameBoard implements GameBoard {

	public Scoreboard board;
	public PlayerData p;
	public Objective obj;
	public CustomGame game;
	
	public CustomGameBoard(CustomGame game, PlayerData p) {
		this.p = p;
		this.game = game;
	}
	public void refresh() {
		if (p.game instanceof StarParty) {
			board = Bukkit.getScoreboardManager().getNewScoreboard();
			obj = board.registerNewObjective(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD +this.game.getName(), "dummy");
		}
		
		if (p.game instanceof CustomGame) {
			
			board = Bukkit.getScoreboardManager().getNewScoreboard();
			
			obj = board.registerNewObjective(((CustomGame) p.game).getType().name() , "dummy");
			obj.setDisplaySlot(DisplaySlot.SIDEBAR);
			Score score = obj.getScore(((CustomGame) p.game).getTimer().getStringTime());
			score.setScore(10);
			
			
			
			p.board = this;
			p.player.setScoreboard(board);
		}
		
		
		
		
	}
	

}
