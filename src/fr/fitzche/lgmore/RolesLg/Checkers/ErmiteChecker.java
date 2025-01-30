package fr.fitzche.lgmore.RolesLg.Checkers;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;

import fr.fitzche.lgmore.PlayerData;
import fr.fitzche.lgmore.Lg.GameLg;
import fr.fitzche.lgmore.RolesLg.ERMITE;
import fr.fitzche.lgmore.Util.GameLgUtil;
import fr.fitzche.lgmore.Util.VoteEvent;
import fr.fitzche.lgmore.minecraft.ResCheck;

public class ErmiteChecker implements ResCheck {

	public ERMITE player;
	public GameLg game;
	public ErmiteChecker(ERMITE player) {
		this.player = player;
		this.game = GameLgUtil.getGameOfPlayer(player.playerWithRole, "at ermite checker creation");
	}
	@Override
	public boolean checkRes(PlayerDeathEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void runDeathAction(PlayerDeathEvent e, Player k) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean hide(PlayerDeathEvent e) {
		if (e.getEntity().getName().equals(player.getName())) {
			System.out.println("mort ermite cachée: "+ e.getEntity().getName());
			return true;
		}
		return false;
	}

	@Override
	public void beforeDie(PlayerDeathEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public int onPlayerDamage(PlayerData attacker, PlayerData attacked) {
		int returned = 0;
		
		if (attacker.getName().equals(player.playerWithRole.getName())) {
			if (game.isDay()) {
				returned += 30;
			} 
			returned -= (player.nbOfPlayerAround * 5);
			if (game.getOrat() > 20) {
				returned -= 10;
			}
			
		} else if(attacked.getName().equals(player.playerWithRole.getName())) {
			if (!game.isDay()) {
				returned -= 20;
			}
			if (game.getTragic() > 20) {
				returned -= 10;
			}
			returned += (player.nbOfPlayerAround * 3);
		}
		return returned;
	}

	@Override
	public boolean brume(PlayerDeathEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onVoteEvent(VoteEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onAddTragic(int i, Location loc) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onAddEpic(int i, Location loc) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onAddOrat(int i, Location loc) {
		// TODO Auto-generated method stub

	}

}
