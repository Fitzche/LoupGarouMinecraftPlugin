package StarParty.Role;

import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import StarParty.RoleStar;
import fr.fitzche.lgmore.PlayerData;
import fr.fitzche.lgmore.Util.MathUtil;

public class Yoda implements RoleStar {

	PlayerData yoda;
	public Yoda(PlayerData yoda) {
		this.yoda = yoda;
	}
	@Override
	public double onDamage(PlayerData attacker, PlayerData defender) {
		if (yoda.getName().equals(defender.getName()) && MathUtil.pourcentage(12)) {
			yoda.sendMessage("Vous avez esquivé un coup");
			return 0;
		} else if (yoda.getName().equals(attacker.getName())) {
			yoda.player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 100, 0, false, false));
		}
		return 1;
	}

	@Override
	public void onRole() {
		// TODO Auto-generated method stub

	}

}
