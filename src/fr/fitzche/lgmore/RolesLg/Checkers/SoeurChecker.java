package fr.fitzche.lgmore.RolesLg.Checkers;

import java.util.ArrayList;

import org.bukkit.event.entity.PlayerDeathEvent;

import fr.fitzche.lgmore.GameLg;
import fr.fitzche.lgmore.PlayerData;
import fr.fitzche.lgmore.RolesLg.SOEUR;
import fr.fitzche.lgmore.minecraft.ResCheck;

public class SoeurChecker implements ResCheck {

	public SOEUR soeur;
	public GameLg game;
	public SoeurChecker(SOEUR soeur, GameLg game) {
		this.soeur = soeur;
		this.game = game;
	}
	@Override
	public boolean checkRes(PlayerDeathEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void runDeathAction(PlayerDeathEvent e) {
		ArrayList<PlayerData> sToRemove = new ArrayList<PlayerData>();
		for (PlayerData s:soeur.sisters) {
			if (e.getEntity().getName().equals(s.Name)) {
				sToRemove.add(s);
				soeur.playerWithRole.sendMessage("Votre soeur a été tué par "+ e.getEntity().getKiller().getName());
			}
		}
		for (PlayerData p:sToRemove) {
			soeur.sisters.remove(p);
		}

	}

}
