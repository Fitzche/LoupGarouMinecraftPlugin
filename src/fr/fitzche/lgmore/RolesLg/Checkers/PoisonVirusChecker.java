package fr.fitzche.lgmore.RolesLg.Checkers;

import org.bukkit.event.entity.PlayerDeathEvent;

import fr.fitzche.lgmore.RolesLg.Infections.Virus;
import fr.fitzche.lgmore.RolesLg.Infections.VirusType;
import fr.fitzche.lgmore.minecraft.ResCheck;

public class PoisonVirusChecker implements ResCheck {

	public Virus virus;
	
	public PoisonVirusChecker(Virus virus) {
		this.virus = virus;
	}
	@Override
	public boolean checkRes(PlayerDeathEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void runDeathAction(PlayerDeathEvent e) {
		if (virus.type.equals(VirusType.POISON) ) {
			if (e.getEntity().getName().equals(virus.infecter.Name)) {
				if (e.getEntity().getKiller().getName().equals(virus.owner.Name)) {
					virus.poisonGuerison = true;
				}
				virus.stopped = true;
			}
			
		}

	}

}
