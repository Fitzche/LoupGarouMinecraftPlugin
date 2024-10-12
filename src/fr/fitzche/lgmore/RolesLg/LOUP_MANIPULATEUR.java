package fr.fitzche.lgmore.RolesLg;

import fr.fitzche.lgmore.PlayerData;

public class LOUP_MANIPULATEUR implements RoleInstance {

	
	public int powerUsed = 3;
	public PlayerData playerWithRole;
	
	
	
	public LOUP_MANIPULATEUR(PlayerData p) {
		this.playerWithRole = p;
	}
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Loup Manipulateur";
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void giveRoleEffectAndItem(PlayerData player) {
		// TODO Auto-generated method stub

	}

	@Override
	public void giveEffectAllTime() {
		// TODO Auto-generated method stub

	}

	@Override
	public void giveNightEffectCheck() {
		// TODO Auto-generated method stub

	}

	@Override
	public void giveNightEffect() {
		// TODO Auto-generated method stub

	}

	@Override
	public void giveDayEffect() {
		// TODO Auto-generated method stub

	}

	@Override
	public void episodeEffect() {
		// TODO Auto-generated method stub

	}

	@Override
	public void changeTo(PlayerData player) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setEpisodeTrue() {
		// TODO Auto-generated method stub

	}

	@Override
	public void startSpecialEvent() {
		// TODO Auto-generated method stub

	}

	@Override
	public void blind(PlayerData origin) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isInfoRole() {
		// TODO Auto-generated method stub
		return false;
	}

}
