package StarParty;

import org.bukkit.ChatColor;
import org.bukkit.Location;

import fr.fitzche.lgmore.Main;

public enum RolesStar {

	Yoda("MaitreYoda", ChatColor.BLUE, "Yoda a 12% de chance d'esquiver les coups qu'il reçoit (dégats réduits), de plus, il obtiendra 10sec de speed quand il frappe un joueur.", new Location(Main.world, 0, 100, 0), true, false),
	Windu("MaitreWindu", ChatColor.BLUE, "Windu possède de la force contre les sith (plus élevé en fonction de leur utilisation du coté obscur), ainsi que 5% de force par kill.", new Location(Main.world, 0, 100, 0), true, false),
	QuiGon("QuiGon", ChatColor.BLUE, "Qui Gon voit des particules de couleurs autour des joueurs prenant des dégats indiquant leur état de santé.", new Location(Main.world, 0, 100, 0), true, false),
	Luke("LucSkywalker", ChatColor.BLUE, "Luc Skywalker possède 20% de force à proximité de Leila (15 blocs) dont il connait l'identité, il possède un sabre laser et un blaster", new Location(Main.world, 0, 100, 0), true, true), 
	HanSolo("HanSolo", ChatColor.BLUE, "Han Solo a 20% de chance de gagner 1% de force à chacune de ses attaques, il possède un blaster.", new Location(Main.world, 0, 100, 0), false, true), 
	Leila("Leila", ChatColor.BLUE, "donne 10% de force à ses alliés et 10% de faiblesse à ses ennemis dans un rayon de 15 blocs, régène légèrement ses alliés environnant à sa mort. Possède un blaster.", new Location(Main.world, 0, 100, 0), false, true), 
	Chewbaca("Chewbaca", ChatColor.BLUE, "Chewbaca possède 10% de résistance à proximité de Han Solo, son blaster a 30% de chance de générer une explosion et d'infliger 1 coeur en plus. Ne possède pas de sabre laser", new Location(Main.world, 0, 100, 0), false, true),
	DarkMaul("Dark Maul", ChatColor.RED, "Dark Maul possède un item \"Sith\" qui lui permet d'obtenir speed 3 et force pendant 10secs, de plus, il peut réssuciter une fois, en perdant 2 coeurs permanents", new Location(Main.world, 0, 100, 0), true, false), 
	Palpa("DarkSidious", ChatColor.RED, "Dark Sidious possède 2 items: le \"lighning\", un éclair de force utilisable 2 fois qui fera tomber un éclair sur le joueur visé, lui infligeant 2 coeurs; et le \"Sith\", un pouvoir des sith qui lui accordera speed 3 et force pendant 10 secs. Il possède un sabre laser mais pas de blasters", new Location(Main.world, 0, 100, 0), true, false), 
	Dooku("Dooku", ChatColor.RED, "Dooku possède 15% de force contre les manieurs de sabre laser, grâce à sa forme de combat basé sur le duel (item Makashi), il peut lancer un duel contre le joueur visé, les deux opposant de pourront se combattre que l'un l'autre pendant 30s. possède un sabre laser", new Location(Main.world, 0, 100, 0), true, false), 
	Grievou("Grievous", ChatColor.RED, "Grievous gagne 5% de force à chaque kill sur un utilisateur de sabre laser", new Location(Main.world, 0, 100, 0), false, false), 
	Jango("JangoFett", ChatColor.GOLD, "Jango possède un item flamethrower, s'il clique avec, un chargement de 4s se lancera ou celui-ci aura slowness 3, s'il change d'item dans la main, le chargement s'arretera. Si le chargement arrive à 4s, un rayon de feu apparaitra pendant 4s dans la direction ou Jango regarde, celui-ci retirera 1 demi coeur toutes les demi sec aux joueurs pris dans le rayon. Jango possède 12 coeurs et possède 10% de chance de voler un demi coeur non permanents quand il tape un joueur.", new Location(Main.world, 0, 100, 0), false, true), 
	Stormtrooper("Stormtrooper", ChatColor.RED, "Le stormtrooper possède un blaster dont le tir sera complètement chargé quelque soit la charge de l'arc (meilleure cadence de tir), de plus, à chaque kill, celui-ci régènera toute sa vie.", new Location(Main.world, 0, 100, 0), false, true), 
	Impe("GardeImpérial", ChatColor.RED, "Les deux gardes impériaux possèdent une vie commune, mais 20 coeurs au total à eux deux, ils possèdent résistance 10% à moins de 10 blocs l'un de l'autre.", new Location(Main.world, 0, 100, 0), false, false),
	DarkVador("DarkVador", ChatColor.RED, "Dark Vador possède 15% de force, il possède également un item nommé \"Strangle\" lui permettant d'étrangler un joueur (2 utilisations), celui-ci subira blindness, slowness et poison pendant 3 sec. Dark Vador possède également un item \"Sith\" lui permettant d'obtenir speed 3 et force pendant 10 secs.", new Location(Main.world, 0, 100, 0), true, false),
	ObiWan("Obiwan", ChatColor.BLUE, "Obiwan possède 20% de résistance, de plus, s'il se protège de son épée nommée \"Ataru\", tous les joueurs dans un rayon de 3 blocs seront éjecté. Il possède un sabre laser mais pas de blaster", new Location(Main.world, 0, 100, 0), true, true);
	
	
	private RolesStar(String name, ChatColor color, String description, Location loc, boolean hasLightsaber, boolean gun) {
		this.color = color;
		this.name = name;
		this.description = description;
		this.loc = loc;
		this.hasLightsaber = hasLightsaber;
		this.hasGun = gun;
	}
	
	public ChatColor color;
	public String name;
	public String description;
	public Location loc;
	public boolean hasLightsaber;
	public boolean hasGun;
	public String getName() {
		return name;
	}
	
	public boolean hasGun() {
		return this.hasGun;
	}
	
	public boolean hasSaber() {
		return hasLightsaber;
	}
	
	public String getDescription() {
		return this.description;
	}
	
	public ChatColor getColor() {
		return color;
	}
	public Location getLoc() {
		return this.loc;
	}
}
