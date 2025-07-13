package fr.fitzche.lgmore.scoreboard;

import org.bukkit.Bukkit;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import fr.fitzche.lgmore.PlayerData;
import net.md_5.bungee.api.ChatColor;

public class BedBoard implements GameBoard{

	PlayerData p;
	public BedBoard(PlayerData p) {
		this.p = p;
	}
	@Override
	public void refresh() {
		if (p.bedGame == null) {
			p.board = new DefaultBoard(p);
		} else {
			Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
			Objective objective = board.registerNewObjective((ChatColor.RED 		+"Bed Wars"   ), "dummy");
			for (String entry: objective.getScoreboard().getEntries()) {
				objective.getScoreboard().resetScores(entry);
			}
			
			//FONCTIONNEMENT: Score score = obj.registe(text) puis score.setScore
			if (p.isOnline) {
				p.player.setScoreboard(board);
			}
		}
		
		
		
	}

}
