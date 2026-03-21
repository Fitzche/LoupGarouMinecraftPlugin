package fr.fitzche.lgmore.clocktower;

import fr.fitzche.lgmore.PlayerData;

public class EvilData implements ClockRoleData {
	public PlayerData p;
	public ClockTower game;
	
	public boolean use = false;
	public EvilData(PlayerData p, ClockTower game) {
		this.p = p;
		this.game = game;
	}
	@Override
	public void night() {
		use = true;
		p.sendMessage("Vous pouvez à nouveau tuer un joueur grâce à la commande /clock kill [pseudo]");
		
	}
	@Override
	public void morning() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void discussion() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void vote() {
		// TODO Auto-generated method stub
		
	}
}
