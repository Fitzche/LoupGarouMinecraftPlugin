package fr.fitzche.lgmore.RolesLg.Checkers;

import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;

import fr.fitzche.lgmore.Camp;
import fr.fitzche.lgmore.PlayerData;
import fr.fitzche.lgmore.Lg.GameLg;
import fr.fitzche.lgmore.RolesLg.IDIOT_DU_VILLAGE;
import fr.fitzche.lgmore.Util.PlayerUtil;
import fr.fitzche.lgmore.minecraft.ResCheck;
import net.md_5.bungee.api.ChatColor;

public class IDV_Checker implements ResCheck{
	public IDIOT_DU_VILLAGE idv;
	public GameLg game;
	public IDV_Checker(IDIOT_DU_VILLAGE idv, GameLg game) {
		this.idv = idv;
		this.game = game;
	}
	@Override
	public boolean checkRes(PlayerDeathEvent e) {
		if (e.getEntity().getKiller() == null || PlayerUtil.getDataOfPlayer(e.getEntity().getKiller(), "at idv checker death").camp != Camp.Villager || idv.powerUsed ) {
			return false;
		}else {
			idv.playerWithRole.sendMessage("Vous avez été tué, vous bénéficiez cependant d'une 2e chance car votre assassin est un membre du village");
			for (PlayerData p: game.getPlayerAlive()) {
				p.sendMessage(ChatColor.GREEN+"Le joueur "+ idv.playerWithRole.Name + " a été tué par un villageois, il est cependant réssucité car il était Idiot Du Village");
			}
			idv.playerWithRole.setMaxHealth(idv.playerWithRole.getMaxHealth() - 4);
			idv.powerUsed= true;
			return true;
		}
		
		
	} 

	@Override
	public void runDeathAction(PlayerDeathEvent e, Player k) {
		// TODO Auto-generated method stub
		
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

}
