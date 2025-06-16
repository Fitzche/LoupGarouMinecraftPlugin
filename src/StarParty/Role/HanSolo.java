package StarParty.Role;

import StarParty.RoleStar;
import fr.fitzche.lgmore.PlayerData;
import fr.fitzche.lgmore.Util.MathUtil;

public class HanSolo implements RoleStar {

	public PlayerData solo;
	public double boost = 0;
	public HanSolo(PlayerData solo) {
		this.solo = solo;
	}
	@Override
	public double onDamage(PlayerData attacker, PlayerData defender) {

		if (attacker.getName().equals(solo.getName())) {
			if (MathUtil.pourcentage(20)) {
				boost ++;
			}
			return 1 + (boost / 100);	
		}
		return 1;
	
	}

	@Override
	public void onRole() {
		// TODO Auto-generated method stub

	}

}
