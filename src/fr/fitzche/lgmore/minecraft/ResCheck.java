package fr.fitzche.lgmore.minecraft;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;

import fr.fitzche.lgmore.PlayerData;
import fr.fitzche.lgmore.Util.VoteEvent;

public interface ResCheck {
	public boolean checkRes(PlayerDeathEvent e, PlayerData killler);
	
	public boolean checkRes(PlayerDeathEvent e);
	
	public String runDeathAction(PlayerDeathEvent e, Player k);
	
	public boolean hide(PlayerDeathEvent e);
	public void beforeDie(PlayerDeathEvent e);
	public int onPlayerDamage(PlayerData attacker, PlayerData attacked); //retourne un nombre, pour une unité, 5% de force sont ajoutés (ex: 2 --> 10% de dégat sup, -3 --> -15% de dégats
	public boolean brume(PlayerDeathEvent e);
	public String getTypeName();
	
	public void onVoteEvent(VoteEvent e);
	public void onAddTragic(int before, int after, Location loc);
	public void onAddEpic(int before, int after, Location loc);
	public void onAddOrat(int before, int after, Location loc);

}
