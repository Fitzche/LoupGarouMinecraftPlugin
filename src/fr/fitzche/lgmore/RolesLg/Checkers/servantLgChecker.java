package fr.fitzche.lgmore.RolesLg.Checkers;

import org.bukkit.entity.Damageable;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.PlayerDeathEvent;

import fr.fitzche.lgmore.PlayerData;
import fr.fitzche.lgmore.RolesLg.Servant_des_loups;
import fr.fitzche.lgmore.minecraft.ResCheck;
import net.md_5.bungee.api.ChatColor;

public class servantLgChecker implements ResCheck {
	public Servant_des_loups servant;
	
	public servantLgChecker(Servant_des_loups servant) {
		this.servant = servant;
	}

	@Override
	public boolean checkRes(PlayerDeathEvent e) {
		
		if (e.getEntity().getName().equals(servant.master.Name)) {
			servant.playerWithRole.sendMessage(ChatColor.DARK_PURPLE+"Votre maitre est mort, vous mourrez donc à sa place");
			if (servant.playerWithRole.isOnline) {	
				servant.playerWithRole.player.damage(1000, e.getEntity().getKiller());
			}
			return true;
		}
		
		return false;
	}

	@Override
	public void runDeathAction(PlayerDeathEvent e, Player k) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean hide(PlayerDeathEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void beforeDie(PlayerDeathEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int onPlayerDamage(PlayerData attacker, PlayerData attacked) {
		// TODO Auto-generated method stub
		return 0;
	}

}
