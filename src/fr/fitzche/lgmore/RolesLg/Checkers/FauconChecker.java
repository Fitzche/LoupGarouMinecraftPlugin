package fr.fitzche.lgmore.RolesLg.Checkers;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;

import fr.fitzche.lgmore.Main;
import fr.fitzche.lgmore.PlayerData;
import fr.fitzche.lgmore.RoleInstance;
import fr.fitzche.lgmore.Lg.GameLg;
import fr.fitzche.lgmore.RolesLg.FAUCONNIER;
import fr.fitzche.lgmore.Util.LocationUtil;
import fr.fitzche.lgmore.Util.MathUtil;
import fr.fitzche.lgmore.Util.VoteEvent;
import fr.fitzche.lgmore.minecraft.ResCheck;

public class FauconChecker implements ResCheck {

	
	FAUCONNIER fauc;
	
	public FauconChecker(FAUCONNIER fauc) {
		this.fauc = fauc;
	}
	@Override
	public boolean checkRes(PlayerDeathEvent e, PlayerData killler) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean checkRes(PlayerDeathEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String runDeathAction(PlayerDeathEvent e, Player k) {
		PlayerData killer = Main.getData(k);
		PlayerData killed = Main.getData(e.getEntity());
		if (killer == null || killed == null || killer.game == null || killed.game == null) {
			return "--on player is not registred or not in game--";
		}
		for (Location loc:fauc.spottedPoint) {
			if (LocationUtil.getDistanceBetween(k, loc) < 50) {
				int n = 0;
				for (PlayerData p:((GameLg)fauc.playerWithRole.game).getPlayerAlive()) {
					if (LocationUtil.getDistanceBetween(p.player, loc) < 50) {
						n++;
					}
				}
				fauc.playerWithRole.sendMessage("Un de vos aigle a repéré un assassinat, il y a "+ n +" joueurs autours, l'aura du tueur est "+ killer.aura.getName() + ", la victime était "+ killed.getLgRole().getName());
				if (MathUtil.pourcentage(20)) {
					fauc.playerWithRole.sendMessage("Votre faucon est mort");
					fauc.spottedPoint.remove(loc);
				}
				
				return "--fauconCorrespond--";
			}
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
	public boolean brume(PlayerDeathEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getTypeName() {
		// TODO Auto-generated method stub
		return "FauconChecker";
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

	
	

}
