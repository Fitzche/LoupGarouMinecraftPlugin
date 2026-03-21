package fr.fitzche.lgmore.RolesLg.Checkers;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;

import fr.fitzche.lgmore.PlayerData;
import fr.fitzche.lgmore.RolesLg.ARAIGNEE;
import fr.fitzche.lgmore.Util.LocationUtil;
import fr.fitzche.lgmore.Util.VoteEvent;
import fr.fitzche.lgmore.minecraft.ResCheck;

public class AraigneeChecker implements ResCheck {

	
	public ARAIGNEE araignee;
	public AraigneeChecker(ARAIGNEE araignee) {
		this.araignee = araignee;
	}
	@Override
	public boolean checkRes(PlayerDeathEvent e, PlayerData killer) {
		if (e.getEntity().getName().equals(araignee.playerWithRole.getName())&& araignee.percentagePlayers.getOrDefault(killer.getName(), 0.0) >= 100 && !araignee.resus) {
			araignee.resus = true;
			araignee.playerWithRole.sendMessage("Vous avez réssucité car votre assassin est manipulé à 100%, cependant votre manipulation envers celui-ci repasse à 0%");
			araignee.percentagePlayers.put(killer.getName(), 0.0);
			return true;
		}
		return false;
	}

	@Override
	public boolean checkRes(PlayerDeathEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String runDeathAction(PlayerDeathEvent e, Player k) {
		if (LocationUtil.getDistanceBetween(k, araignee.playerWithRole.player) < 20 && araignee.percentagePlayers.getOrDefault(k.getName(), 0.0) < 100) {
			araignee.percentagePlayers.put(k.getName(), araignee.percentagePlayers.getOrDefault(k.getName(), 0.0) + 10.0);
			if (araignee.percentagePlayers.get(k.getName()) >= 100) {
				araignee.playerWithRole.sendMessage("Le joueur "+ k.getName() + " vous est manipulable.");
			}
		}
		return "add10ManipAraignee";
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
	public boolean brume(PlayerDeathEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getTypeName() {
		// TODO Auto-generated method stub
		return "araigneeChecker";
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

}
