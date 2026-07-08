package fr.fitzche.lgmore.bedwars;

import org.bukkit.Location;

public class BedLoc {

	
	
	public BedLoc(Location loc, BedLocType type, BedTeam defaultTeam) {
		this.loc = loc;
		this.type = type;
		this.defaultTeam = defaultTeam;
	}
	public Location loc;
	public BedLocType type;
	public BedTeam defaultTeam;
	
}
