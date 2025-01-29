package fr.fitzche.lgmore.RolesLg.Checkers;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;

import fr.fitzche.lgmore.PlayerData;
import fr.fitzche.lgmore.Lg.GameLg;
import fr.fitzche.lgmore.RolesLg.PARRAIN;
import fr.fitzche.lgmore.Util.VoteEvent;
import fr.fitzche.lgmore.minecraft.ResCheck;

public class ParrainChecker implements ResCheck {
	public GameLg game;
	public PARRAIN parrain;

	public ParrainChecker(GameLg game, PARRAIN parrain) {
		this.parrain = parrain;
		this.game = game;
	}
	@Override
	public boolean checkRes(PlayerDeathEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void runDeathAction(PlayerDeathEvent e, Player k) {
		if (e.getEntity().getName().equals(parrain.target.Name) ) {
			parrain.targetDeath(k.getName());
			if (parrain.target.team.equals(game.villTeam)) {
				game.addEpic(10, e.getEntity().getLocation());
			}
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
