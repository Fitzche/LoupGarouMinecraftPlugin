package StarParty.Role;

import StarParty.RoleStar;
import fr.fitzche.lgmore.PlayerData;

public class Grievous implements RoleStar {

	
	public PlayerData griev;
	public double boost;
	public Grievous(PlayerData griev) {
		this.griev = griev;
	}
	@Override
	public double onDamage(PlayerData attacker, PlayerData defender) {
		if (defender.getName().equals(griev.getName())) {
			return 0.85;
		} else if (attacker.getName().equals(griev.getName())) {
			return 1 + (boost * 0.05);
		}
		return 1;
	}

	@Override
	public void onRole() {
		// TODO Auto-generated method stub

	}

}
