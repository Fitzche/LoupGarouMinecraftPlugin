package fr.fitzche.lgmore.minecraft;

import java.io.Serializable;

import org.bukkit.Bukkit;

import fr.fitzche.lgmore.Main;
import fr.fitzche.lgmore.PlayerData;

public class PlayerDataLeft implements Serializable {
	public PlayerData playerD;
	public double life;
	
	
	
	public PlayerDataLeft(PlayerData p) {
		this.playerD = p;
		playerD.isOnline = false;
		this.life = p.player.getMaxHealth();
		p.left = this;
		if (p.player.getLocation().getWorld() != Main.world) {
			p.rejoinLoc = p.player.getLocation();
		}
		
	}
	
	public void end() {
		this.playerD.player = Bukkit.getPlayer(playerD.getName());
		this.playerD.player.setMaxHealth(life);
		playerD.isOnline = true;
		playerD.left = null;
		this.playerD.player.teleport(Main.world.getSpawnLocation());
	}
}
