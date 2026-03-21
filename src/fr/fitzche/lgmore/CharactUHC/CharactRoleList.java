package fr.fitzche.lgmore.CharactUHC;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import fr.fitzche.lgmore.Main;
import fr.fitzche.lgmore.Util.MathUtil;
import fr.fitzche.lgmore.custom.CustomRole;
import fr.fitzche.lgmore.custom.RoleSet;

public class CharactRoleList implements RoleSet{

	public HashMap<String, CustomRole> rolesOfPlayers = new HashMap<String, CustomRole>();
	public HashMap<String, String> victories = new HashMap<String, String>();
	public int nbOfRoles = 0;
	public CharactUHC game;
	public String listName = "notReferenced";
	private int appTime = 10;
	
	public CharactRoleList(CharactUHC game, String listName, int timeApp) {
		if (listName != null) {
			this.listName = listName;
		}
		this.appTime = timeApp;
		this.game = game;
	}
	@Override
	public HashMap<String, CustomRole> rolesOfPlayer() {
		
		return this.rolesOfPlayers;
	}
	@Override
	public void setRolesOfPlayer(String name , CustomRole role) {
		
		this.rolesOfPlayers.put(name, role);
	}

	@Override
	public ArrayList<CustomRole> roles(int n) {
		ArrayList<CustomRole> roles = new ArrayList<CustomRole>();
		File folder = new File(Main.plug.getDataFolder(), "roles");
		if (!folder.exists()) {
			folder.mkdirs();
			return null;
		}
		
		ArrayList<String> strs = new ArrayList<String>();
		for (File file:folder.listFiles()) {
			strs.add(file.getName());
		}
		for (int i = 0; i<n;i++) {
			
			int x = MathUtil.generateAlInt(0, strs.size() - 1);
			CharactRole role = new CharactRole(strs.get(x), this, this.game);
			if (role.roleListName.equals(this.listName)) {
				roles.add(role);
			}
			strs.remove(x);
			
			
		}
		return roles;
		
	}

	@Override
	public HashMap<String, String> victoryOfPlayers() {
		// TODO Auto-generated method stub
		return this.victories;
	}

	@Override
	public int applicationTime() {
		// TODO Auto-generated method stub
		return appTime;
	}
	@Override
	public void setVictoryOfPlayer(String name, String campName) {
		this.victories.put(name, campName);
		
	}
	@Override
	public HashMap<String, CustomRole> rolesSettedOfPlayers() {
		// TODO Auto-generated method stub
		return null;
	}

}
