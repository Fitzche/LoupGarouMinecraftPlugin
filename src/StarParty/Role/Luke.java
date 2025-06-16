package StarParty.Role;

import StarParty.RoleStar;
import StarParty.RolesStar;
import fr.fitzche.lgmore.PlayerData;
import net.md_5.bungee.api.ChatColor;

public class Luke implements RoleStar{

	public PlayerData luke;
	public PlayerData leila;
	
	public Luke(PlayerData luke) {
		this.luke = luke;
	}
	@Override
	public double onDamage(PlayerData attacker, PlayerData defender) {
		if (attacker.getName().equals(luke.getName()) && luke != null && leila != null && leila.starParty != null && luke.starParty != null &&luke.getLocation().distance(leila.getLocation()) < 15) {
			return 1.2;
		}
		return 1;
	}

	@Override
	public void onRole() {
		for (PlayerData p:luke.starParty.players) {
			if (p.roleStar != null && p.roleStar.equals(RolesStar.Leila)) {
				leila = p;
				luke.sendMessage(ChatColor.BLUE + "Leila "+ ChatColor.GOLD+ " est "+ ChatColor.BLUE + p.getName());
			}
		}
		luke.sendMessage("Leila n'est pas là");
		
	}

}
