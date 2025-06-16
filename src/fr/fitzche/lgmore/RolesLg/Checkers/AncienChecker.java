package fr.fitzche.lgmore.RolesLg.Checkers;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;

import fr.fitzche.lgmore.Camp;
import fr.fitzche.lgmore.Main;
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
	public boolean checkRes(PlayerDeathEvent e, PlayerData killer) {
		if (killer == null) {
			System.out.println("ancien killer nul");
			return false;
		}
		
		if (!ancien.isRes 
				&&(killer.considWolf 
						|| killer.role.getCampOfRole().equals(Camp.Wolf))) {
			ancien.isRes = true;
			System.out.println("ancien ressut");
			return true;
		}
		return false;
	}

	@Override
	public String runDeathAction(PlayerDeathEvent e, Player k) {
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
	public boolean brume(PlayerDeathEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean checkRes(PlayerDeathEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getTypeName() {
		// TODO Auto-generated method stub
		return "Ancien Checker";
	}

}
