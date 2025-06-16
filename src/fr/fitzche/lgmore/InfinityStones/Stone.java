package fr.fitzche.lgmore.InfinityStones;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;

import fr.fitzche.lgmore.Main;
import fr.fitzche.lgmore.PlayerData;
import fr.fitzche.lgmore.RolesLg.THANOS;
import fr.fitzche.lgmore.Util.PlayerUtil;
import fr.fitzche.lgmore.Util.VoteEvent;
import fr.fitzche.lgmore.minecraft.ResCheck;

public class Stone implements ResCheck {
	
	public THANOS thanos;
	public PlayerData owner;
	public StonesType type;
	
	public Stone(THANOS thanos, StonesType type ) {
		this.thanos = thanos;
		this.type = type;
		thanos.game.resCheckers.add(this);
	}

	@Override
	public boolean checkRes(PlayerDeathEvent e, PlayerData killer) {
		if (e.getEntity().getName().equals(thanos.playerWithRole.getName()) && thanos.hasAll && !thanos.playerWithRole.hasRes) {
			thanos.playerWithRole.hasRes = true;
			thanos.playerWithRole.boostS5 -= 5;
			thanos.playerWithRole.sendMessage(ChatColor.DARK_PURPLE+ "Vous avez réussi à vous échapper grâce aux pierres d'infinités.");
			return true;
		}
		return false;
	}

	@Override
	public String runDeathAction(PlayerDeathEvent e, Player k) {
		String str = "";
		if (owner != null && e.getEntity().getName().equals(owner.getName())&& k.getName().equals(thanos.getName())) {
			this.thanos.playerWithRole.addStone(type);
			thanos.game.resCheckers.remove(this);
			str = str + "StoneRemoved--";
			
			
		} else if (owner != null && e.getEntity().getName().equals(owner.getName())) {
			this.owner = Main.getData(k);
			this.owner.addStone(type);
			thanos.playerWithRole.sendMessage(ChatColor.GOLD+"Le joueur "+k.getName()+ " a récupéré la pierre de "+type.getName());
			str = str + "StoneOwnerChange--";
		
		}
		return str;

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

	@Override
	public boolean checkRes(PlayerDeathEvent e) {
		if (e.getEntity().getName().equals(thanos.playerWithRole.getName()) && thanos.hasAll && !thanos.playerWithRole.hasRes) {
			thanos.playerWithRole.hasRes = true;
			thanos.playerWithRole.boostS5 -= 5;
			thanos.playerWithRole.sendMessage(ChatColor.DARK_PURPLE+ "Vous avez réussi à vous échapper grâce aux pierres d'infinités.");
			return true;
		}
		return false;
	}

	@Override
	public String getTypeName() {
		// TODO Auto-generated method stub
		return "Stone";
	}

}
