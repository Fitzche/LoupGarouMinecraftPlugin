package fr.fitzche.lgmore.bedwars;

import java.util.ArrayList;

public class BedWarMap {

	String path;
	int nbOfTeam;
	ArrayList<BedLoc> bedLocs = new ArrayList<BedLoc>();
	String name;
	int nbOfPlayerTeam;
	
	
	public BedWarMap(String path, int nbOfTeam, ArrayList<BedLoc> bedLocs, String name, int nbOfPlayer) {
			this.path = path;
			this.nbOfTeam = nbOfTeam;
			this.bedLocs = bedLocs;
			this.name = name;
			this.nbOfPlayerTeam = nbOfPlayer;
			
	}
	
	public String getName() {
		return name;
	}
}
