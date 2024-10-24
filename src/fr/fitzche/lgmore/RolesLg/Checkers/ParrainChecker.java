package fr.fitzche.lgmore.RolesLg.Checkers;

import java.util.ArrayList;

import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;

import fr.fitzche.lgmore.GameLg;
import fr.fitzche.lgmore.PlayerData;
import fr.fitzche.lgmore.RolesLg.PARRAIN;
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
		}

	}

}
