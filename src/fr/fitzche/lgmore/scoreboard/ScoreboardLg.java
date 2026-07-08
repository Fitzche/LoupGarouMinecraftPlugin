package fr.fitzche.lgmore.scoreboard;

import java.io.Serializable;

import org.bukkit.Bukkit;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

import fr.fitzche.lgmore.Main;
import fr.fitzche.lgmore.PlayerData;
import fr.fitzche.lgmore.Lg.GameLg;
import fr.fitzche.lgmore.Util.WorldUtil;
import net.md_5.bungee.api.ChatColor;

public class ScoreboardLg implements GameBoard{
	public GameLg game;
	public boolean istimeRunned = false;
	public PlayerData player;

	
	

	public ScoreboardLg(GameLg game, PlayerData player) {
		this.game = game;
		Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
		Objective objective = board.registerNewObjective((ChatColor.RED 		+"LG More"   ), "dummy");
		
		if (player != null ) {
			this.player = player;
			this.refresh();
		}
		
	}
	
	public void refresh() {
		if (player.starParty != null) {
			player.board = new StarBoard(player);
		}
		Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
		Objective objective = board.registerNewObjective((ChatColor.RED 		+"Loup Garou UHC"   ), "dummy");
		for (String entry: objective.getScoreboard().getEntries()) {
			objective.getScoreboard().resetScores(entry);
		}
		
		
	    ////System.out.println("score created");
		objective.setDisplaySlot(DisplaySlot.SIDEBAR);
		
		
		
		////System.out.println("LgScoreboard 1");
	    Score score = objective.getScore(ChatColor.WHITE+ "  "+ChatColor.BOLD+game.timer.getStringTime());
	    ////System.out.println(game.timer.getStringTime());
	    ////System.out.println(Integer.toString(game.timer.temps));
	    score.setScore(4);
	   // //System.out.println("LgScoreboard 2");
	   // //System.out.println("Episode: "+Integer.toString(game.timer.getEpisode()));
	    Score scoreBase = objective.getScore(ChatColor.DARK_BLUE+ ""+ChatColor.BOLD+  "     ❶"+ChatColor.DARK_RED+" INFORMATION"+ChatColor.DARK_BLUE+" ❶:");
	    scoreBase.setScore(60);
	    Score score1 = objective.getScore(ChatColor.BOLD+"  Episode: "+ChatColor.GOLD+ 		Integer.toString(game.timer.getEpisode()));
	    score1.setScore(3);
	    
	    String nJoueur = Integer.toString(game.getNumberOfPlayer());
	    ////System.out.println("LgScoreboard 3");
	    Score score2 = objective.getScore(ChatColor.BOLD+"  Joueurs: "+ChatColor.GOLD + nJoueur);
	    score2.setScore(5);
	    ////System.out.println("LgScoreboard 4");
	    
	    
	    String groupe = Integer.toString(game.groupe);
	    Score score3 = objective.getScore(ChatColor.BOLD+"  Groupes: "+ChatColor.GOLD+ groupe);
	    score3.setScore(1);
	    ////System.out.println("LgScoreboard 5");  /**/
	    
	    
	    
	    String time = WorldUtil.getTime(Main.server.getWorld("world"));
	    Score score4 = objective.getScore(ChatColor.BOLD+"  Horaire: "+ChatColor.GOLD + time);
	    score4.setScore(2);
	    
	   
	    int registrPoint = 0;
	    String str = "nul --> ";
	    if (game.getTragic() > 0) {
	    	registrPoint = game.getTragic();
	    	str = "Tragique -- > ";
	    } else if (game.getOrat() > 0) {
	    	registrPoint = game.getOrat();
	    	str = "Oratoire -- > ";
	    } else if (game.getEpic() > 0) {
	    	registrPoint = game.getEpic();
	    	str = "Epique -- > ";
	    }
	    if (game.isRegistresActivated) {
	    	Score scoreReg = objective.getScore(ChatColor.BOLD+"  Registre: "+ChatColor.GOLD + str + Integer.toString(registrPoint));
	    	scoreReg.setScore(2);
	    }
	    
	    
	    
	    if (player!=null&&player.player != null && player.isOnline) {
	    	Score score5 = objective.getScore(ChatColor.BOLD+"  Kills: "+ ChatColor.GOLD+player.numberOfKill);
		    
		    score5.setScore(1);
		}
	    player.player.setScoreboard(board);
	    if (this.player.isOnline) {
	    	this.player.player.setScoreboard(board);
	    }
	    
	}
	
	
	
	public void setgame(GameLg game) {
		this.game = game;
		Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
		Objective objective = board.registerNewObjective((ChatColor.RED 		+"Loup Garou UHC"   ), "dummy");
		for (String entry: objective.getScoreboard().getEntries()) {
			objective.getScoreboard().resetScores(entry);
		}
		
		//System.out.println("marquage 2.1");
		istimeRunned = false;
		
		

	}
	
	
}
