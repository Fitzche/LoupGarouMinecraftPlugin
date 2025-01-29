package fr.fitzche.lgmore.RolesLg.Checkers;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;

import fr.fitzche.lgmore.PlayerData;
import fr.fitzche.lgmore.Lg.GameLg;
import fr.fitzche.lgmore.RolesLg.ANGE;
import fr.fitzche.lgmore.Util.VoteEvent;
import fr.fitzche.lgmore.minecraft.ResCheck;

public class AngeChecker implements ResCheck {

	public GameLg game;
	public ANGE ange;
	public PlayerData target;
	public boolean isGuardian;
	
	public AngeChecker(GameLg game, PlayerData target, ANGE ange) {
		this.game = game;
		this.target = target;
		this.ange = ange;
		
	}
	@Override
	public boolean checkRes(PlayerDeathEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void runDeathAction(PlayerDeathEvent e, Player k) {
		if (!target.Name.equals(e.getEntity().getName())) {
			return;
		} else {
			ange.targetDeath(k.getName());
		}
		

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
	@Override
	public boolean brume(PlayerDeathEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

}
