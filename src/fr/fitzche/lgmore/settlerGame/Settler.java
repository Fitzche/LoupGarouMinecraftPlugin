package fr.fitzche.lgmore.settlerGame;

import fr.fitzche.lgmore.Main;
import fr.fitzche.lgmore.PlayerData;
import fr.fitzche.lgmore.custom.CustomRole;

public class Settler implements CustomRole {

	String camp;
	private PlayerData playerData;
	String roleName;
	SettleGameSet settleGameSet;
	SettlerGame game;
	
	
	
	public Settler(SettleGameSet settleGameSet, String playerName, SettlerGame settlerGame, String team, String roleName) {
		this.camp = team;
		this.roleName = roleName;
		this.settleGameSet = settleGameSet;
		this.playerData = Main.getData(playerName);
		this.game = settlerGame;
		
	}

	@Override
	public String name() {
		// TODO Auto-generated method stub
		return roleName;
	}

	@Override
	public String campId() {
		// TODO Auto-generated method stub
		return camp;
	}

	@Override
	public String campName() {
		// TODO Auto-generated method stub
		return camp;
	}

	@Override
	public double attackModif(double damage) {
		// TODO Auto-generated method stub
		return damage;
	}

	@Override
	public double damageModif(double damage) {
		// TODO Auto-generated method stub
		return damage;
	}

	@Override
	public void damage(double damage) {
		

	}

	@Override
	public void death() {
		

	}

	@Override
	public boolean checkDeath(String killed, String killer) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void setPlayer(PlayerData playerData) {
		this.playerData = playerData;

	}

	@Override
	public void application() {
		// TODO Auto-generated method stub

	}

	@Override
	public void attribution() {
		playerData.setDisplayName(this.settleGameSet.colorsOfTeam.get(camp)+  playerData.getName());

	}

}
