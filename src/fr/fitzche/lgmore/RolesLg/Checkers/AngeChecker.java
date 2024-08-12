package fr.fitzche.lgmore.RolesLg.Checkers;

import org.bukkit.event.entity.PlayerDeathEvent;

import fr.fitzche.lgmore.GameLg;
import fr.fitzche.lgmore.PlayerData;
import fr.fitzche.lgmore.RolesLg.ANGE;
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
	public void runDeathAction(PlayerDeathEvent e) {
		if (!target.Name.equals(e.getEntity().getName())) {
			return;
		} else {
			ange.targetDeath(e.getEntity().getKiller().getName());
		}
		

	}

}
