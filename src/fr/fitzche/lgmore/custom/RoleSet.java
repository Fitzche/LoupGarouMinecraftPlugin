package fr.fitzche.lgmore.custom;

import java.util.ArrayList;
import java.util.HashMap;

public interface RoleSet {

	
	public HashMap<String, CustomRole> rolesOfPlayer();
	public void setRolesOfPlayer(String name, CustomRole role);
	public ArrayList<CustomRole> roles(int n);
	public HashMap<String, String> victoryOfPlayers();
	
	public int applicationTime();
	public void setVictoryOfPlayer(String name, String campName);
	
	//retourne un dico associant le nom d'un joueur à son role (le role doit de préférence etre compris dans la liste des roles de roles() pour éviter les doublons
	public HashMap<String, CustomRole> rolesSettedOfPlayers();

}
