package fr.fitzche.lgmore.RolesLg.Checkers;

import org.bukkit.ChatColor;
import org.bukkit.event.entity.PlayerDeathEvent;

import fr.fitzche.lgmore.GameLg;
import fr.fitzche.lgmore.PlayerData;
import fr.fitzche.lgmore.RolesLg.Camp;
import fr.fitzche.lgmore.RolesLg.ENFANT_SAUVAGE;
import fr.fitzche.lgmore.minecraft.ResCheck;

public class EnfantChecker implements ResCheck {
	public GameLg game;
	public ENFANT_SAUVAGE es;
	
	public EnfantChecker(ENFANT_SAUVAGE es, GameLg game) {
		this.game = game;
		this.es = es;
	}

	@Override
	public boolean checkRes(PlayerDeathEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void runDeathAction(PlayerDeathEvent e) {
		if (e.getEntity().getName().equals(es.model.Name)) {
			for (PlayerData p:game.getFalseWolfAlive()) {
				if (!es.playerWithRole.inLove) {
					
					game.lgTeam.add(es.playerWithRole);
				}
				p.sendMessage(ChatColor.DARK_RED+"Un joueur a rejoint les loups-garou, faites /lg role pour voir la liste");
				es.playerWithRole.camp = Camp.Wolf;
				es.playerWithRole.sendMessage(ChatColor.DARK_RED+"Votre modèle est mort, vous rejoignez donc les loups garou");
			}
		}

	}

}
