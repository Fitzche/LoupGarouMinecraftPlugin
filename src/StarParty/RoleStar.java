package StarParty;

import fr.fitzche.lgmore.PlayerData;

public interface RoleStar {

	
	public double onDamage(PlayerData attacker, PlayerData defender);
	public void onRole();
	
}
