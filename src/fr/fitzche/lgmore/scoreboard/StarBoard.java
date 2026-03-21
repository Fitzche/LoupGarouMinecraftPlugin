package fr.fitzche.lgmore.scoreboard;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

import fr.fitzche.lgmore.PlayerData;
import fr.fitzche.lgmore.Lg.GameLg;

public class StarBoard implements GameBoard{

	
	
	public StarBoard(PlayerData p) {
		
		this.p = p;
		refresh();
	}
	public Scoreboard board;
	public PlayerData p;
	public Objective obj;
	public void refresh() {
		if (p.starParty == null) {
			if (p.game != null && p.game instanceof GameLg) {
				p.board = new ScoreboardLg((GameLg) p.game, p);
			}
			return;
		}
		board = Bukkit.getScoreboardManager().getNewScoreboard();
		obj = board.registerNewObjective(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD +"Star Party", "dummy");
		
		if (p.roleStar != null) {
			Score score = obj.getScore(ChatColor.GOLD+""+ChatColor.BOLD+"Role: "+p.roleStar.getColor()+ p.roleStar.getName());
			score.setScore(1);
		}
		
		p.player.setScoreboard(board);
		
	}


	
}
