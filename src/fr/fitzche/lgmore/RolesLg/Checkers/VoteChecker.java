package fr.fitzche.lgmore.RolesLg.Checkers;

import org.bukkit.event.entity.PlayerDeathEvent;

import fr.fitzche.lgmore.GameLg;
import fr.fitzche.lgmore.PlayerData;
import fr.fitzche.lgmore.Util.GameLgUtil;
import fr.fitzche.lgmore.Util.PlayerUtil;
import fr.fitzche.lgmore.minecraft.ResCheck;

public class VoteChecker implements ResCheck {

	public GameLg gameOfVote;
	
	public VoteChecker(GameLg gameOfVote) {
		this.gameOfVote = gameOfVote;
	}
	@Override
	public boolean checkRes(PlayerDeathEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void runDeathAction(PlayerDeathEvent e) {
		if (PlayerUtil.getDataOfPlayer(e.getEntity(), "at vote checker") == null) {
			return;
		}
		PlayerData p = PlayerUtil.getDataOfPlayer(e.getEntity(), "at vote checker");
		GameLg game = GameLgUtil.getGameOfPlayer(p, "at vote checker");
		if (!game.equals(gameOfVote) ) {
			return;
		}
		for (PlayerData players:game.getPlayerAlive()) {
			if (players.canVoted.contains(p) ) {
				players.canVoted.remove(p);
			}
		}

	}

}
