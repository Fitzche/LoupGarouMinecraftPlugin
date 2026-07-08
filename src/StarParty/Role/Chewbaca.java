package StarParty.Role;

import StarParty.RoleStar;
import StarParty.RolesStar;
import fr.fitzche.lgmore.PlayerData;

public class Chewbaca implements RoleStar {

	
	public PlayerData chew;
	
	public Chewbaca(PlayerData chew) {
		this.chew = chew;
		
	}
	@Override
	public double onDamage(PlayerData attacker, PlayerData defender) {
		for (PlayerData p:chew.starParty.players) {
			if (chew != null && chew.starParty != null && defender.getName().equals(chew.getName()) && p.starParty != null && p.getLocation().distance(chew.getLocation()) < 20 && p.roleStar.equals(RolesStar.HanSolo)) {
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
