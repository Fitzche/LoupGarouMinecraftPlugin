package fr.fitzche.lgmore.RolesLg.Checkers;

import org.bukkit.event.entity.PlayerDeathEvent;

import fr.fitzche.lgmore.GameLg;
import fr.fitzche.lgmore.RolesLg.Camp;
import fr.fitzche.lgmore.RolesLg.IDIOT_DU_VILLAGE;
import fr.fitzche.lgmore.Util.PlayerUtil;
import fr.fitzche.lgmore.minecraft.ResCheck;

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
			idv.playerWithRole.sendMessage("Vous avez été tué, vous bénéfiviez cependant d'une 2e chance car votre assassin est un membre du village");
			idv.playerWithRole.player.setMaxHealth(idv.playerWithRole.player.getMaxHealth() - 4);
			idv.powerUsed= true;
			return true;
		}
		
		
	} 

	@Override
	public void runDeathAction(PlayerDeathEvent e) {
		// TODO Auto-generated method stub
		
	}

}
