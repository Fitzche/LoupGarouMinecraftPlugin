package fr.fitzche.lgmore.RolesLg.Checkers;

import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;

import fr.fitzche.lgmore.Camp;
import fr.fitzche.lgmore.PlayerData;
import fr.fitzche.lgmore.Lg.GameLg;
import fr.fitzche.lgmore.RolesLg.ANCIEN;
import fr.fitzche.lgmore.Util.PlayerUtil;
import fr.fitzche.lgmore.Util.VoteEvent;
import fr.fitzche.lgmore.minecraft.ResCheck;

public class AncienChecker implements ResCheck{
	public ANCIEN ancien;
	public GameLg game;
	
	public AncienChecker(ANCIEN ancien, GameLg game) {
		this.ancien = ancien;
		this.game = game;
	}

	@Override
	public boolean checkRes(PlayerDeathEvent e) {
		if (e.getEntity().getKiller() == null) {
			return false;
		}
		if (!ancien.isRes &&PlayerUtil.getDataOfPlayer(e.getEntity().getKiller(), "at ancien checker d").camp.equals(Camp.Wolf)) {
			ancien.isRes = true;
			return true;
		}
		return false;
	}

	@Override
	public void runDeathAction(PlayerDeathEvent e, Player k) {
		// TODO Auto-generated method stub
		
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
	public void onAddTragic() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onAddEpic() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onAddOrat() {
		// TODO Auto-generated method stub
		
	}

}
