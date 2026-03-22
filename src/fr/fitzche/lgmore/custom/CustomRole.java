package fr.fitzche.lgmore.custom;

import fr.fitzche.lgmore.PlayerData;

public interface CustomRole {

	
	public String name();
	public String campId();
	public String campName();
	public double attackModif(double damage);
	public double damageModif(double damage);

	public void death();
	public boolean checkDeath(String killed, String killer);
	public void setPlayer(PlayerData playerData);
	public void application();
	public void attribution();
	
}
