package fr.fitzche.lgmore.clocktower;

import org.bukkit.Location;

import fr.fitzche.lgmore.Main;

public enum ClockRole {
	
	Evil("Diablotin", true, false, true, new Location(Main.world, 1, 1, 1), new EvilData(null, null)),
	/*Poisonner("Empoisonneur",true, false, true, new Location(Main.world, 1, 1, 1)),
	Espion("Espion",true, false, false, new Location(Main.world, 1, 1, 1)),
	Ecarlate("Femme Ecarlate", true, false, true, new Location(Main.world, 1, 1, 1)),
	
	Croque("Croque-Mort", false, false, false, new Location(Main.world, 1, 1, 1)), 
	Cuistot("Cuistot", false, false, false, new Location(Main.world, 1, 1, 1)),
	Empathe("Empathe", false, false, false, new Location(Main.world, 1, 1, 1)),
	Moine("Moine", false, false, false, new Location(Main.world, 1, 1, 1)),
	Voyante("Voyante", false, false, false, new Location(Main.world, 1, 1, 1)),
	Vierge("Vierge", false, false, false, new Location(Main.world, 1, 1, 1)),
	Pourfendeur("Pourfendeur", false, false, false, new Location(Main.world, 1, 1, 1)),
	Lavandière("Lavandière", false, false, false, new Location(Main.world, 1, 1, 1)),
	
	Saint("Saint", false, false, false, new Location(Main.world, 1, 1, 1)),
	Reclus("Reclus", false, false, false, new Location(Main.world, 1, 1, 1))*/;
	
	
	
	public boolean seenAsSbire;
	public String name;
	public boolean sbire;
	public boolean foreigner;
	public Location loc;
	public ClockRoleData data;
	ClockRole(String name, boolean sbire, boolean foreigner, boolean seenAsSbire, Location loc, ClockRoleData data) {
		this.name = name;
		this.seenAsSbire = seenAsSbire;
		this.sbire = sbire;
		this.foreigner = foreigner;
		this.loc = loc;
		this.data = data;
	}
	
}
