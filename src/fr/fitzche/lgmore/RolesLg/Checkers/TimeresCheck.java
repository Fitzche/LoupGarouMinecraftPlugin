package fr.fitzche.lgmore.RolesLg.Checkers;

import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;

import Lg.GameLg;
import fr.fitzche.lgmore.minecraft.ResCheck;

public class TimeresCheck implements ResCheck {
	public GameLg game;

	public TimeresCheck(GameLg game) {
		this.game = game;
	}

	@Override
	public boolean checkRes(PlayerDeathEvent e) {
		if (game.timer.temps < 1200) {
			return true;
		}
		return false;
	}

	@Override
	public void runDeathAction(PlayerDeathEvent e, Player k) {
		// TODO Auto-generated method stub

	}

}
