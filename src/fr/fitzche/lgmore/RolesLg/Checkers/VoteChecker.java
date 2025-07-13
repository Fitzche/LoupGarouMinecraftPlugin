package fr.fitzche.lgmore.RolesLg.Checkers;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;

import fr.fitzche.lgmore.Main;
import fr.fitzche.lgmore.PlayerData;
import fr.fitzche.lgmore.Lg.GameLg;
import fr.fitzche.lgmore.Util.GameLgUtil;
import fr.fitzche.lgmore.Util.PlayerUtil;
import fr.fitzche.lgmore.Util.VoteEvent;
import fr.fitzche.lgmore.minecraft.ResCheck;

public class VoteChecker implements ResCheck {

	public GameLg gameOfVote;
	
	public VoteChecker(GameLg gameOfVote) {
		this.gameOfVote = gameOfVote;
	}
	@Override
	public boolean checkRes(PlayerDeathEvent e, PlayerData killer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String runDeathAction(PlayerDeathEvent e, Player k) {
		PlayerData p = Main.strToPlayer.getOrDefault(e.getEntity(), null);
		
		if (p == null) {
			return "";
		}
		GameLg game = (GameLg) p.game;
		if (game == null || !game.equals(gameOfVote) ) {
			return "";
		}
		for (PlayerData players:game.getPlayerAlive()) {
			if (players.canVoted != null && players.canVoted.contains(p) ) {
				players.canVoted.remove(p);
			}
		}
		return "";

	}
	@Override
	public boolean hide(PlayerDeathEvent e) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public void beforeDie(PlayerDeathEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public int onPlayerDamage(PlayerData attacker, PlayerData attacked) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void onVoteEvent(VoteEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public boolean brume(PlayerDeathEvent e) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public void onAddTragic(int before, int after, Location loc) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onAddEpic(int before, int after, Location loc) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onAddOrat(int before, int after, Location loc) {
		// TODO Auto-generated method stub

	}
	@Override
	public boolean checkRes(PlayerDeathEvent e) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public String getTypeName() {
		// TODO Auto-generated method stub
		return "VoteChecker";
	}

}
