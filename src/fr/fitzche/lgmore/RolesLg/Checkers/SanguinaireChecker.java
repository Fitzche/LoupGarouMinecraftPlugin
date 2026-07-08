package fr.fitzche.lgmore.RolesLg.Checkers;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import fr.fitzche.lgmore.PlayerData;
import fr.fitzche.lgmore.RolesLg.LOUP_SANGUINAIRE;
import fr.fitzche.lgmore.Util.VoteEvent;
import fr.fitzche.lgmore.minecraft.ResCheck;

public class SanguinaireChecker implements ResCheck{

	public LOUP_SANGUINAIRE loup;
	
	public SanguinaireChecker(LOUP_SANGUINAIRE lg) {
		this.loup = lg;
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
		String result = "";
		if (k.getName().equals(loup.playerWithRole.getName())) {
			loup.playerWithRole.changeHealth(2);
			result = result+ "sanguinaire gain 1 heart--- "+ "\n";
		}
		this.loup.playerWithRole.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION	, 200, 0, false, false));
		return result+"sanguinaire gain absorbtion";
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
		return null;
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
