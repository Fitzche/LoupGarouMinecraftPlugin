package StarParty.Role;

import StarParty.RoleStar;
import StarParty.RolesStar;
import fr.fitzche.lgmore.PlayerData;

public class Windu implements RoleStar {
	
	PlayerData windu;
	public Windu(PlayerData windu) {
		this.windu = windu;
	}
	
	@Override
	public double onDamage(PlayerData attacker, PlayerData defender) {
		if (attacker.getName().equals(windu.getName())) {
			if (windu.starParty != null &&defender.starParty != null && defender.starParty == windu.starParty) {
				if (defender.roleStar.equals(RolesStar.Palpa)) {
					return 1.25 + (windu.numberOfKill * 0.05);
				} else if (defender.roleStar.equals(RolesStar.DarkMaul)) {
					return 1.2 + (windu.numberOfKill * 0.05);
				} else if (defender.roleStar.equals(RolesStar.DarkVador)) {
					return 1.2 + (windu.numberOfKill * 0.05);
				} else if (defender.roleStar.equals(RolesStar.Dooku)) {
					return 1.15 + (windu.numberOfKill * 0.05);
				}
				
			}
			
		}
		return 1;
	}

	@Override
	public void onRole() {
		// TODO Auto-generated method stub

	}

}
