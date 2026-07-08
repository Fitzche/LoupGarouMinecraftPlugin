package StarParty.Role;

import org.bukkit.Color;

import StarParty.RoleStar;
import fr.fitzche.lgmore.PlayerData;
import fr.fitzche.lgmore.Util.PlayerUtil;

public class QuiGon implements RoleStar {

	PlayerData qui;
	public QuiGon(PlayerData qui) {
		this.qui = qui;
	}
	@Override
	public double onDamage(PlayerData attacker, PlayerData defender) {
		double percentage = (defender.getHealth() / defender.getMaxHealth()) * 100;
		if (percentage < 30) {
			PlayerUtil.particleFor(qui.player, defender.getLocation(), Color.RED, 1);
			PlayerUtil.particleFor(qui.player, defender.getLocation(), Color.RED, 1);
			PlayerUtil.particleFor(qui.player, defender.getLocation(), Color.RED, 1);
		} else if (percentage < 50 && percentage > 29) {
			PlayerUtil.particleFor(qui.player, defender.getLocation(), Color.ORANGE, 1);
			PlayerUtil.particleFor(qui.player, defender.getLocation(), Color.ORANGE, 1);
			PlayerUtil.particleFor(qui.player, defender.getLocation(), Color.ORANGE, 1);
		
		} else if (percentage > 49 && percentage < 70) {
			PlayerUtil.particleFor(qui.player, defender.getLocation(), Color.YELLOW, 1);
			PlayerUtil.particleFor(qui.player, defender.getLocation(), Color.YELLOW, 1);
			PlayerUtil.particleFor(qui.player, defender.getLocation(), Color.YELLOW, 1);
			
		} else if (percentage >= 100) {
			PlayerUtil.particleFor(qui.player, defender.getLocation(), Color.GREEN, 1);
			PlayerUtil.particleFor(qui.player, defender.getLocation(), Color.GREEN, 1);
			PlayerUtil.particleFor(qui.player, defender.getLocation(), Color.GREEN, 1);
		}
		return 1;
	}

	@Override
	public void onRole() {
		// TODO Auto-generated method stub

	}

}
