package fr.fitzche.lgmore.RolesLg.Checkers;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;

import fr.fitzche.lgmore.Main;
import fr.fitzche.lgmore.PlayerData;
import fr.fitzche.lgmore.RolesLg.RolesLg;
import fr.fitzche.lgmore.RolesLg.TRAQUEUR;
import fr.fitzche.lgmore.Util.LocationUtil;
import fr.fitzche.lgmore.Util.PlayerUtil;
import fr.fitzche.lgmore.Util.VoteEvent;
import fr.fitzche.lgmore.minecraft.ResCheck;

public class TrackRes implements ResCheck {
	public TRAQUEUR owner;
	
	public TrackRes(TRAQUEUR owner) {
		this.owner = owner;
	}

	@Override
	public boolean checkRes(PlayerDeathEvent e, PlayerData killler) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean checkRes(PlayerDeathEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String runDeathAction(PlayerDeathEvent e, Player k) {
		if (owner != null && owner.tracked != null && e.getEntity().getName().equals(owner.tracked.getName())) {
			owner.tracked = null;
			owner.playerWithRole.sendMessage(Main.info +ChatColor.GRAY  +"Vous pouvez choisir une nouvelle cible à traquer");
			return "tracked mort";
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
		return "traqueur checker";
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
