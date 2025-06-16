package fr.fitzche.lgmore.Util;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;

import fr.fitzche.lgmore.PlayerData;

public class LineRapport {

	
	public PlayerData p;
	public List<Location> locs;
	public Location finalLoc;
	
	public LineRapport(PlayerData p, List<Location> locs) {
		this.p = p;
		this.locs = locs;
		this.finalLoc = locs.get(locs.size() - 1);
	}
}
