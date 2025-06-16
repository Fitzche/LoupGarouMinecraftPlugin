package fr.fitzche.lgmore.RolesLg.Checkers;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;

import fr.fitzche.lgmore.PlayerData;
import fr.fitzche.lgmore.Lg.GameLg;
import fr.fitzche.lgmore.Lg.RegisterType;
import fr.fitzche.lgmore.RolesLg.COMEDIEN;
import fr.fitzche.lgmore.Util.GameLgUtil;
import fr.fitzche.lgmore.Util.MathUtil;
import fr.fitzche.lgmore.Util.VoteEvent;
import fr.fitzche.lgmore.minecraft.ResCheck;

public class ComedienChecker implements ResCheck {

	public COMEDIEN comedien;
	public GameLg game;
	
	public ComedienChecker(COMEDIEN comedien) {
		this.comedien = comedien;
		this.game = comedien.game;
	}
	@Override
	public boolean checkRes(PlayerDeathEvent e, PlayerData killer) {
		// TODO Auto-generated method stub
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
		int returned = 0;
		if (game != null && attacked !=  null && attacker != null && comedien != null && comedien.playerWithRole != null) {
			if (game.getRegister().getType().equals(RegisterType.Epic) && attacked.getName().equals(comedien.playerWithRole.getName())) {
				returned -= (game.getEpic() / 5);
			}
		}
		if (game.isRegistresActivated) {
			return 5;
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
		System.out.println("on vote event checker: "+game.getOrat());
		if ((!game.isRegistresActivated && MathUtil.pourcentage(30))||(game.getRegister().getType().equals(RegisterType.Oratoire) && MathUtil.pourcentage(game.getOrat()))) {
			System.out.println("on vote event checker 2");
			comedien.playerWithRole.sendMessage("Voici les joueurs qui ont voté pour "+ e.getVoted().getName()+": ");
			for (PlayerData p:game.getPlayerAlive()) {
				if (p.voted != null && p.voted.getName().equals(e.getVoted().getName())) {
					
					comedien.playerWithRole.sendMessage("-"+ p.getName());
				}
			}
		}

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
		return "ComedienChecker";
	}

}
