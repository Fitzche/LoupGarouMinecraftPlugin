package fr.fitzche.lgmore.RolesLg.Checkers;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;

import fr.fitzche.lgmore.Main;
import fr.fitzche.lgmore.PlayerData;
import fr.fitzche.lgmore.RolesLg.THIERCE_ANGE;
import fr.fitzche.lgmore.Util.PlayerUtil;
import fr.fitzche.lgmore.Util.VoteEvent;
import fr.fitzche.lgmore.minecraft.ResCheck;

public class thierceAnge_Checker implements ResCheck {
	THIERCE_ANGE ange;
	
	public thierceAnge_Checker(THIERCE_ANGE ange) {
		this.ange = ange;
	}
	@Override
	public boolean checkRes(PlayerDeathEvent e, PlayerData killer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String runDeathAction(PlayerDeathEvent e, Player k) {
		if (k.getName().equals(ange.playerWithRole.getName()) && ange.wantNotAnnounced.contains(Main.getData(e.getEntity()))) {
			ange.notAnnounced.add(Main.getData(e.getEntity()));
			return "canHideAngeThierce";
		} else {
			return "";
		}
		
		

	}

	@Override
	public boolean hide(PlayerDeathEvent e) {
		if (this.ange.notAnnounced.contains(Main.getData(e.getEntity()))) {
			return true;
		} else {
			return false;
		}
		
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
		return "ThierceAngeChecker";
	}

}
