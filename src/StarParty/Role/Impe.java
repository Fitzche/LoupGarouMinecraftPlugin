package StarParty.Role;

import StarParty.RoleStar;
import StarParty.RolesStar;
import fr.fitzche.lgmore.PlayerData;
import fr.fitzche.lgmore.Util.HealthSyncer;

public class Impe implements RoleStar {

	public PlayerData impe;
	public boolean setted = false;
	public Impe(PlayerData impe) {
		this.impe = impe;
	}
	@Override
	public double onDamage(PlayerData attacker, PlayerData defender) {
		for (PlayerData p:impe.starParty.players) {
			if (p.roleStar.equals(RolesStar.Impe)) {
				return 0.9;
			}
		}
		return 1;
	}

	@Override
	public void onRole() {
		impe.changeHealth(4);
		impe.player.setHealth(impe.player.getMaxHealth());
		if (!setted) {
			for (PlayerData p:impe.starParty.players) {
				if (p.roleStar.equals(RolesStar.Impe)) {
					HealthSyncer syncer = new HealthSyncer(impe, p);
					return;
				}
			}
			
		}
	}

}
