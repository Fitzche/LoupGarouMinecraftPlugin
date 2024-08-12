package fr.fitzche.lgmore.RolesLg.Checkers;

import org.bukkit.event.entity.PlayerDeathEvent;

import fr.fitzche.lgmore.GameLg;
import fr.fitzche.lgmore.RolesLg.ANCIEN;
import fr.fitzche.lgmore.RolesLg.Camp;
import fr.fitzche.lgmore.Util.PlayerUtil;
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
	public void runDeathAction(PlayerDeathEvent e) {
		// TODO Auto-generated method stub
		
	}

}
