package StarParty.Role;

import org.bukkit.ChatColor;
import org.bukkit.Color;

import StarParty.RoleStar;
import fr.fitzche.lgmore.PlayerData;
import fr.fitzche.lgmore.Util.PlayerUtil;

public class Leila implements RoleStar {

	PlayerData leila;
	public Leila(PlayerData leila) {
		this.leila = leila;
	}
	@Override
	public double onDamage(PlayerData attacker, PlayerData defender) {
		if (leila == null) {
			return 1;
		}
		if (attacker.getLocation().distance(leila.getLocation()) < 10 && attacker.roleStar != null) {
			if (attacker.roleStar.color.equals(ChatColor.BLUE)) {
				PlayerUtil.particle(attacker.getLocation(), Color.BLUE, "ok", 1);
				return 1.1;
			} else {
				PlayerUtil.particle(attacker.getLocation(), Color.RED, "ok", 1);
				return 0.9;
			}
		}
		return 1;
	}

	@Override
	public void onRole() {
		// TODO Auto-generated method stub

	}

}
