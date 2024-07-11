package fr.fitzche.lgmore.Love;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.scheduler.BukkitRunnable;

import fr.fitzche.lgmore.GameLg;
import fr.fitzche.lgmore.GameStatut;
import fr.fitzche.lgmore.Main;
import fr.fitzche.lgmore.PlayerData;
import fr.fitzche.lgmore.RolesLg.Camp;
import fr.fitzche.lgmore.Util.PlayerUtil;

public class Team implements Listener{
	ArrayList<PlayerData> members = new ArrayList<PlayerData>();
	ArrayList<PlayerData> targets = new ArrayList<PlayerData>();
	ArrayList<PlayerData> couples = new ArrayList<PlayerData>();
	
	GameLg game;
	Camp camp;
	String name;
	
	public boolean inGame = true;
	boolean winTarget = false;
	boolean winOnlyPlayer = false;
	boolean isCouple;
	
	public void add(PlayerData player) {
		if (members.contains(player)) {
			return;
		}
		player.team.remove(player);
		members.add(player);
	}
	
	public void remove(PlayerData player) {
		if (!members.contains(player)) {
			return;
		}
		player.team = null;
		members.remove(player);
		
	}
	
	public Team(String name, Camp camp, GameLg game, ArrayList<PlayerData> members, ArrayList<PlayerData> targets, String spec, BukkitRunnable bonusRunTargetDeath, ArrayList<PlayerData> couples,  boolean winOnlyPlayer, boolean winTarget, boolean bonusTarget, boolean inCouple ) {
		if (winTarget && (targets ==null || targets.size() == 0)) {
			System.out.println("winTarget is activated but targets list is null at Team creating "+ spec);
			return;
		}
		if (members == null || members.size() == 0) {
			System.out.println("members list is null ArrayList<PlayerData> members at Team creating  "+ spec);
			return;
		}
		
		Main.server.getPluginManager().registerEvents(this, Main.plug);
		
		if (inCouple) {
			if (couples != null && couples.size() != 0) {
				this.couples = couples;
				this.isCouple = true;
			} else {
				System.out.println("couple activated but couples list null at Team creating" + spec);
				return;
			}
		}
		
		if (isCouple) {
			System.out.println("creating couple: "+ members.get(0)+ ", "+members.get(1)+ ", " +members.get(2)+ ", "+ " couple: " + couples.get(0) + " and " + couples.get(1));
		}
		this.members = members;
		this.camp = camp;
		this.name = name;
		this.game = game;
		this.winOnlyPlayer = winOnlyPlayer;
		this.winTarget = winTarget;
		
		for (PlayerData member: members) {
			member.team = this;
		}
	}
	
	public void onPlayerDeath(PlayerData player) {
		boolean present = false;
		
		for (PlayerData ply:members) {
			if (ply.Name.equals(player.Name)) {
				present = true;
			}
		}
		if (present) {
			System.out.println("present");
			if (isCouple && player.inLove) {
				System.out.println("love member");
				for (PlayerData players: members) {
					if (players.inLove) {
						System.out.println(players+" in love");
						players.player.damage(1000);
					}
				}
			}
			members.remove(player);
			if (members.size() == 0) {
				inGame = false;
			}
		}
		
		boolean target = false;
		for (PlayerData ply1:targets) {
			if (player.Name.equals(ply1.Name)) {
				if (targets.contains(player)) {
					targets.remove(player);
					if (winTarget && targets.size() == 0) {
						win();
					}
				}
			}
		}
		
		
		
		
	}
	public PlayerData getNextCouple(PlayerData base) {
		if (base.inLove && members.contains(base)) {
			int index = members.lastIndexOf(base) + 1;
			if (index > members.size()) {
				index = 0;
			}
			PlayerData next = members.get(index);
			if (next.inLove) {
				return next;
			} else {
				return getNextCouple(next);
			}
		}
		return base;
	}
	
	public void donCouple(PlayerData giver, double give) {
		if (isCouple && giver.inLove) {
			PlayerUtil.don(giver, getNextCouple(giver), give);
		} else {
			return;
		}
		
	}
	
	
	public void win() {
		if (true) {
			return;
		}
		Bukkit.broadcastMessage("Le camp ''" + camp.getColor() + this.name + "'' a gagné la partie");
		for (PlayerData ply:game.players) {
			if (ply.inLife) {
				Bukkit.broadcastMessage(ply.camp.getColor() + ply.Name + "--"+ " Vivant"+ply.getLgRole().getCampOfRole().getColor()+ply.getLgRole().getName() + "\n");
			} else {
				Bukkit.broadcastMessage(ply.camp.getColor() + ply.Name + "--"+ ChatColor.DARK_RED+" Mort" + ChatColor.WHITE+ply.getLgRole().getCampOfRole().getColor()+ply.getLgRole().getName()+ "\n");

			}
		}
		game.stopped = true;
		game.statut = GameStatut.ENDED;
	}
}
