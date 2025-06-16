package fr.fitzche.lgmore.RolesLg.Checkers;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;

import fr.fitzche.lgmore.Camp;
import fr.fitzche.lgmore.Main;
import fr.fitzche.lgmore.PlayerData;
import fr.fitzche.lgmore.RolesLg.NECROMANCIEN;
import fr.fitzche.lgmore.Util.VoteEvent;
import fr.fitzche.lgmore.minecraft.ResCheck;

public class NecroChecker implements ResCheck {

	
	NECROMANCIEN necro;
	public NecroChecker(NECROMANCIEN necro) {
		this.necro = necro;
	}
	@Override
	public boolean checkRes(PlayerDeathEvent e, PlayerData killler) {
		if (killler.getName().equals(necro.playerWithRole.getName()) && necro.playerWithRole.game.necrom) {
			
			PlayerData p = Main.getData(e.getEntity());
			if (p == null) {
				return false;
			}
			e.getEntity().sendMessage("Vous avez été réssucité par le nécromancien, vous devez gagner avec celui-ci mais perdez 3 coeurs permanents");
			e.getEntity().setMaxHealth(e.getEntity().getMaxHealth() - 6);
			p.camp = Camp.Died;
			necro.playerWithRole.changeHealth(-2);
			necro.playerWithRole.sendMessage("Le joueur "+ e.getEntity().getName() + " a rejoint votre camp");
			return true;
		}
		return false;
	}

	@Override
	public boolean checkRes(PlayerDeathEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String runDeathAction(PlayerDeathEvent e, Player k) {
		if (e.getEntity().getName().equals(necro.playerWithRole.getName())) {
			k.setMaxHealth(k.getMaxHealth() - 1);
			k.sendMessage("Vous avez tué la cible du nécromancien, il vous vole donc 1 coeur, et connait votre role");
			PlayerData killer = Main.getData(k);
			if (killer != null) {
				necro.playerWithRole.sendMessage("Le tueur de votre cible est "+ killer.role.getName() + ", vous lui volez 1 coeur et vous pouvez rechoisir une cible à maudir.");
			}
			necro.playerWithRole.changeHealth(2);
			necro.powerUsed = false;
			return "necro target death --> gain 1 heart from killer";
		}
		return "";
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

	@Override
	public boolean brume(PlayerDeathEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getTypeName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onVoteEvent(VoteEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onAddTragic(int before, int after, Location loc) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onAddEpic(int before, int after, Location loc) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onAddOrat(int before, int after, Location loc) {
		// TODO Auto-generated method stub

	}

}
