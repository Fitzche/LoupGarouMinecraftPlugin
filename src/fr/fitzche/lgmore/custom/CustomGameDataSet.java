package fr.fitzche.lgmore.custom;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Location;

public class CustomGameDataSet {

	
	

	public CustomGameDataSet(boolean isMapEmpty, int tpRayon, String structureMapPath,boolean hasMinage,
	int boostDiams,
	
	int boostGold,
	
	int boostIron,
	
	int timeMinageInSec,
	boolean damage, ArrayList<RoleSet> rolesSet,
	ArrayList<CustomParam> params
			) {
		this.isMapEmpty = isMapEmpty;
		this.tpRayon = tpRayon;
		this.structureMapPath = structureMapPath;
		this.hasMinage = hasMinage;
		this.boostDiams= boostDiams;
		
		this.boostGold = boostDiams;
		
		this.boostIron = boostIron;
		
		this.timeMinageInSec = timeMinageInSec;
		this.damage = damage;
		this.rolesSet = rolesSet;
		this.params = params;
	}
	
	//GENERAL
	boolean lostStuffOnDeath = false;
	public boolean defaultRespawn = false;
	
	//WORLD
	boolean isMapEmpty = false;
	int tpRayon = 500;
	String structureMapPath = "";
	public HashMap<String, Location> respawnOfPlayers = new HashMap<String, Location>();
	
	//MINAGE
	boolean hasMinage = false;
	int boostDiams = 1;
	int supBoostDiams = 0; //probas over 100
	int boostGold = 1;
	int supBoostGold = 0;
	int boostIron = 1;
	int supBoostIron = 0;
	int timeMinageInSec = 1200;
	boolean damage = false;
	ArrayList<CustomParam> params = new ArrayList<CustomParam>();
	
	
	
	//ROLE
	ArrayList<RoleSet> rolesSet = new ArrayList<RoleSet>();
	
	
}
