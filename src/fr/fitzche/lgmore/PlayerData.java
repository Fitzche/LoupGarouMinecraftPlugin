package fr.fitzche.lgmore;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.scheduler.BukkitRunnable;

import fr.fitzche.lgmore.Love.Team;
import fr.fitzche.lgmore.RolesLg.Aura;
import fr.fitzche.lgmore.RolesLg.Camp;
import fr.fitzche.lgmore.RolesLg.RoleInstance;
import fr.fitzche.lgmore.RolesLg.RolesLg;
import fr.fitzche.lgmore.Util.GameLgUtil;
import fr.fitzche.lgmore.Util.RoleUtilLg;
import net.md_5.bungee.api.ChatColor;

public class PlayerData {
	public RoleInstance roleIn;
	public String Name;
	public ArrayList<PlayerData> canVoted;
	public int vote;
	public boolean isShooted = false;
	public PlayerData voted;
	public Player player;
	public RolesLg role;
	public Camp camp;
	public Team team;
	public boolean inLove;
	public boolean infected;
	public boolean inLife;
	public boolean relive;
	public Inventory deathInventory;
	public int boostS5;
	public int boostR5;
	public Aura aura;
	public boolean instantDeath;
	
	public PlayerData(Player player) {
		
		canVoted = new ArrayList<PlayerData>();
		this.vote = 0;
		boostR5 = 0;
		this.boostS5 = 0;
		//System.out.println("creation joueur");
		this.player = player;
		//System.out.println("1");
		this.Name = player.getName();
		//System.out.println("2");

		deathInventory = Bukkit.createInventory(null, 36, "Inventory");
		
		this.infected = false;
		//System.out.println("3");

		this.inLove = false;
		//System.out.println("4");
		
		this.relive = false;

		this.inLife = true;
		
		//System.out.println("5");

	}
	public void applyLgRole(RolesLg role) { 
		this.camp = role.getCampOfRole();
		this.role = role;
		this.aura = role.aura;
		
	}
	
	public void sendMessage(String message) {
		this.player.sendMessage(message);
	}
	public Location getLocation() {
		return this.player.getLocation();
	}
	public RolesLg getLgRole() {
		return this.role;
	}
	
	
	public void askVoted() {
		
		this.player.sendMessage(ChatColor.GOLD+"Vous pouvez voter pour le joueur de votre choix"+ "\n"+ " Le joueur le plus voté subira 15s de poison et perdra 1 coeur de façon permanente");
		this.vote = 0;
		this.voted = null;
	}
	
	public boolean isInGame(String spec) {
		if (GameLgUtil.getGameOfPlayer(this, ("at 89 of PlayerData "+spec)) == null) {
			return false;
		} else {
			return true;
		}
	}
	
	public boolean isInThisGame(GameLg gm) {
		for (PlayerData ply:gm.players) {
			if (this.equals(ply)) {
				return true;
			}
		}
		return false;
		
	}
	
}
