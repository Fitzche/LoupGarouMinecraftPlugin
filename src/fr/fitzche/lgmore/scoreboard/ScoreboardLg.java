package fr.fitzche.lgmore.scoreboard;

import org.bukkit.Bukkit;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

import fr.fitzche.lgmore.GameLg;
import fr.fitzche.lgmore.Main;
import fr.fitzche.lgmore.PlayerData;
import fr.fitzche.lgmore.Util.WorldUtil;
import net.md_5.bungee.api.ChatColor;

public class ScoreboardLg {
	public GameLg game;
	public boolean istimeRunned;

	public Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
	public Objective objective;

	public ScoreboardLg(GameLg game) {
		this.game = game;
		objective = board.registerNewObjective((ChatColor.RED 		+"Loup Garou UHC"   ), "dummy");
	}
	
	public void refresh() {
		System.out.println("reload score at ScoreBoard lg setgameAgain");
		for (String entry: objective.getScoreboard().getEntries()) {
			objective.getScoreboard().resetScores(entry);
		}
		
		
	    ////System.out.println("score created");
		objective.setDisplaySlot(DisplaySlot.SIDEBAR);
		
		
		
		////System.out.println("LgScoreboard 1");
	    Score score = objective.getScore(game.timer.getStringTime());
	    ////System.out.println(game.timer.getStringTime());
	    ////System.out.println(Integer.toString(game.timer.temps));
	    score.setScore(1);
	   // //System.out.println("LgScoreboard 2");
	   // //System.out.println("Episode: "+Integer.toString(game.timer.getEpisode()));
	    
	    Score score1 = objective.getScore("épisode: "+ 		Integer.toString(game.timer.getEpisode()));
	    score1.setScore(2);
	    
	    String nJoueur = Integer.toString(game.getNumberOfPlayer());
	    ////System.out.println("LgScoreboard 3");
	    Score score2 = objective.getScore("Nombres de Joueurs: " + nJoueur);
	    score2.setScore(3);
	    ////System.out.println("LgScoreboard 4");
	    
	    
	    String groupe = Integer.toString(game.groupe);
	    Score score3 = objective.getScore("Groupe de: "+ groupe);
	    score3.setScore(4);
	    ////System.out.println("LgScoreboard 5");  /**/
	    
	    
	    
	    String time = WorldUtil.getTime(Main.server.getWorld("world"));
	    Score score4 = objective.getScore("temps: " + time);
	    score4.setScore(5);
	    ////System.out.println("LgScoreboard 6");
	    
	    
	    for (PlayerData player: game.playerAlive) {
	    	if (player.player != null) {
	    		player.player.setScoreboard(board);
	    	}
	    	
	    }
	    
	    
	}
	
	
	
	public void setgame(GameLg game) {
		
		for (String entry: objective.getScoreboard().getEntries()) {
			objective.getScoreboard().resetScores(entry);
		}
		
		//System.out.println("marquage 2.1");
		istimeRunned = false;
		game = game;
		Score score = objective.getScore(game.timer.getStringTime());
	    score.setScore(1);
		//System.out.println("marquage 2.2");

	    Score score1 = objective.getScore(" Game: " + game.name + "\n" 		+"épisode: " + game.timer.getEpisode());
	    score1.setScore(0);
		//System.out.println("marquage 2.3");

	    Score score2 = objective.getScore("Nombres de Joueurs: " 		+Integer.toString(game.getNumberOfPlayer()) );
	    score2.setScore(2);
		//System.out.println("marquage 2.4");

	    Score score3 = objective.getScore("Groupe de: " + game.groupe);
	    score3.setScore(4);
		//System.out.println("marquage 2.5");

	}
	
	
}
