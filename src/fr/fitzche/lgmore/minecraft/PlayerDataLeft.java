package fr.fitzche.lgmore.minecraft;

import fr.fitzche.lgmore.PlayerData;

public class PlayerDataLeft {
	public PlayerData playerD;
	public double life;
	
	
	public PlayerDataLeft(PlayerData p) {
		this.playerD = p;
		playerD.isOnline = false;
		this.life = p.player.getMaxHealth();
		
	}
	
	public void end() {
		this.playerD.player.setMaxHealth(life);
		playerD.isOnline = true;
		playerD.left = null;
	}
}
