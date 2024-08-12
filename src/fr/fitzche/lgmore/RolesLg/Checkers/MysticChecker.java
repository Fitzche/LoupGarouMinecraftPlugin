package fr.fitzche.lgmore.RolesLg.Checkers;

import org.bukkit.event.entity.PlayerDeathEvent;

import fr.fitzche.lgmore.GameLg;
import fr.fitzche.lgmore.PlayerData;
import fr.fitzche.lgmore.RolesLg.Camp;
import fr.fitzche.lgmore.RolesLg.LOUP_MYSTIQUE;
import fr.fitzche.lgmore.Util.PlayerUtil;
import fr.fitzche.lgmore.minecraft.ResCheck;

public class MysticChecker implements ResCheck {
	public GameLg game;
	public LOUP_MYSTIQUE mystic;
	
	public MysticChecker(LOUP_MYSTIQUE mystic, GameLg game) {
		this.mystic = mystic;
		this.game = game;
	}

	@Override
	public boolean checkRes(PlayerDeathEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void runDeathAction(PlayerDeathEvent e) {
		PlayerData p = PlayerUtil.getDataOfPlayer(e.getEntity(), "at mystic checker");
		if (p.camp.equals(Camp.Wolf)) {
			mystic.voir();
		}
		
		
	}

}
