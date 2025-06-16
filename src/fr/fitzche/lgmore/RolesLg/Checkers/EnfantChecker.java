package fr.fitzche.lgmore.RolesLg.Checkers;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;

import fr.fitzche.lgmore.Camp;
import fr.fitzche.lgmore.PlayerData;
import fr.fitzche.lgmore.Lg.GameLg;
import fr.fitzche.lgmore.RolesLg.ENFANT_SAUVAGE;
import fr.fitzche.lgmore.RolesLg.RolesLg;
import fr.fitzche.lgmore.Util.RoleUtil;
import fr.fitzche.lgmore.Util.VoteEvent;
import fr.fitzche.lgmore.minecraft.ResCheck;

public class EnfantChecker implements ResCheck {
	public GameLg game;
	public ENFANT_SAUVAGE es;
	
	public EnfantChecker(ENFANT_SAUVAGE es, GameLg game) {
		this.game = game;
		this.es = es;
	}

	@Override
	public boolean checkRes(PlayerDeathEvent e, PlayerData killer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String runDeathAction(PlayerDeathEvent e, Player k) {
		if (e.getEntity().getName().equals(es.model.getName())) {
			es.playerWithRole.camp = Camp.Wolf;
			es.playerWithRole.sendMessage(ChatColor.DARK_RED+"Votre modèle est mort, vous rejoignez donc les loups garou");
			es.playerWithRole.role = RolesLg.SIMPLE_WOLF;
			es.playerWithRole.roleIn = RoleUtil.createRoleOfPlayerRoles(es.playerWithRole);
			es.playerWithRole = null;
			if (es.playerWithRole != null &&es.playerWithRole.inLove) {
				
				
				if (!es.playerWithRole.camp.equals(Camp.Love)) {
					es.playerWithRole.camp = Camp.Wolf;
				}
				es.playerWithRole.considWolf = true;
			}
			for (PlayerData p:game.getFalseWolfAlive()) {
				
				p.sendMessage(ChatColor.DARK_RED+"Un joueur a rejoint les loups-garou, faites /lg role pour voir la liste");
				
			}
			return "EsModelDeath";
		}
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
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void onVoteEvent(VoteEvent e) {
		// TODO Auto-generated method stub
		
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
	public boolean brume(PlayerDeathEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean checkRes(PlayerDeathEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getTypeName() {
		// TODO Auto-generated method stub
		return "EsChecker";
	}

}
