package fr.fitzche.lgmore.RolesLg.Checkers;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;

import fr.fitzche.lgmore.Camp;
import fr.fitzche.lgmore.Main;
import fr.fitzche.lgmore.PlayerData;
import fr.fitzche.lgmore.Lg.GameLg;
import fr.fitzche.lgmore.RolesLg.IDIOT_DU_VILLAGE;
import fr.fitzche.lgmore.Util.PlayerUtil;
import fr.fitzche.lgmore.Util.VoteEvent;
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
	public boolean checkRes(PlayerDeathEvent e, PlayerData killer) {
		if (killer != null 
				&& !killer.considWolf
				&& !idv.powerUsed
				&& e.getEntity().getName().equals(idv.playerWithRole.getName())) {
			idv.playerWithRole.sendMessage("Vous avez été tué, vous bénéficiez cependant d'une 2e chance car votre assassin est un membre du village");
			for (PlayerData p: game.getPlayerAlive()) {
				p.sendMessage(ChatColor.GREEN+"Le joueur "+ idv.playerWithRole.Name + " a été tué par un villageois, il est cependant réssucité car il était Idiot Du Village");
			}
			idv.playerWithRole.setMaxHealth(idv.playerWithRole.getMaxHealth() - 4);
			idv.powerUsed= true;
			System.out.println("idv ressut");
			return true;
		} else {
			return false;
		}
		
		
	} 

	@Override
	public String runDeathAction(PlayerDeathEvent e, Player k) {
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
		return "IdvChecker";
	}

}
