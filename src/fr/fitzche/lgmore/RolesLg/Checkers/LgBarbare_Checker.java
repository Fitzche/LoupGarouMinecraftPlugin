package fr.fitzche.lgmore.RolesLg.Checkers;

import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import fr.fitzche.lgmore.PlayerData;
import fr.fitzche.lgmore.RolesLg.LOUP_BARBARE;
import fr.fitzche.lgmore.minecraft.ResCheck;

public class LgBarbare_Checker implements ResCheck {

	
	public PlayerData owner;
	public LOUP_BARBARE lg;
	
	
	public LgBarbare_Checker(PlayerData p) {
		this.owner = p;
		this.lg = (LOUP_BARBARE) p.roleIn;
	}
	@Override
	public boolean checkRes(PlayerDeathEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void runDeathAction(PlayerDeathEvent e) {
		if (e.getEntity().getKiller().getName().equals(owner.getName())) {
			owner.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 2400, 0, false, false));
		}
		
		owner.changeHealth(-2);
		lg.sup =+ 4;
		System.out.println("run death action");
		

	}

}
