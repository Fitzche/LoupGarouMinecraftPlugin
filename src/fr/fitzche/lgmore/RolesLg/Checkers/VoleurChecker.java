package fr.fitzche.lgmore.RolesLg.Checkers;

import org.bukkit.event.entity.PlayerDeathEvent;

import fr.fitzche.lgmore.GameLg;
import fr.fitzche.lgmore.RolesLg.LOUP_METAMORPHE;
import fr.fitzche.lgmore.RolesLg.VOLEUR;
import fr.fitzche.lgmore.Util.PlayerUtil;
import fr.fitzche.lgmore.minecraft.ResCheck;

public class VoleurChecker implements ResCheck {

	
	public GameLg game;
	public VOLEUR voleur;
	public LOUP_METAMORPHE lg;
	
	public VoleurChecker(VOLEUR voleur, GameLg game, LOUP_METAMORPHE lg) {
		if (voleur != null) {
			this.voleur = voleur;
		}
		
		this.game = game;
		
		if (lg != null) {
			this.lg = lg;
		}
		
	}
	@Override
	public boolean checkRes(PlayerDeathEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void runDeathAction(PlayerDeathEvent e) {
		if (voleur != null&&e.getEntity().getKiller().getName().equals(voleur.playerWithRole.Name)) {
			voleur.steal(PlayerUtil.getDataOfPlayer(e.getEntity(), "at voleur checker"));
		} else if (lg != null&&e.getEntity().getKiller().getName().equals(lg.playerWithRole.Name)) {
			lg.steal(PlayerUtil.getDataOfPlayer(e.getEntity(), "at voleur checker for metamorphe"));
		}

	}

}
