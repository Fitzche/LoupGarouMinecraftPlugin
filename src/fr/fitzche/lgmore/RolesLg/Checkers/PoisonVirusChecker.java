package fr.fitzche.lgmore.RolesLg.Checkers;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;

import fr.fitzche.lgmore.PlayerData;
import fr.fitzche.lgmore.RolesLg.Infections.Virus;
import fr.fitzche.lgmore.RolesLg.Infections.VirusType;
import fr.fitzche.lgmore.Util.VoteEvent;
import fr.fitzche.lgmore.minecraft.ResCheck;

public class PoisonVirusChecker implements ResCheck {

	public Virus virus;
	
	public PoisonVirusChecker(Virus virus) {
		this.virus = virus;
	}
	@Override
	public boolean checkRes(PlayerDeathEvent e, PlayerData killer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String runDeathAction(PlayerDeathEvent e, Player k) {
		String str = "";
		if (virus.type.equals(VirusType.POISON) ) {
			if (e.getEntity().getName().equals(virus.infecter.getName())) {
				str = str + "endAlchimistPoison";
				if (k.getName().equals(virus.owner.getName())) {
					virus.poisonGuerison = true;
					str = str + " + killerIsTheInfected";
					
				}
				virus.stopped = true;
			}
			
		}
		return str;

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
		return "PoisonVirusChecker";
	}

}
