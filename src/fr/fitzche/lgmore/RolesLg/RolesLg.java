package fr.fitzche.lgmore.RolesLg;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.fitzche.lgmore.Camp;
import fr.fitzche.lgmore.Role;
import fr.fitzche.lgmore.Lg.GameLg;
import fr.fitzche.lgmore.Util.GameLgUtil;
import fr.fitzche.lgmore.Util.RoleUtil;
import net.md_5.bungee.api.ChatColor;

public enum RolesLg implements Role, Serializable{
	SIMPLE_VILLAGER(Camp.Villager, "Simple Villageois",100, Material.WHEAT, Aura.LUMINOUS, "Le simple villageois gagne avec les villageois sans aucun pouvoir hormis son droit de vote et son épée. Aura = lumineuse", false, true),//correspondant
	VOYANTE(Camp.Villager, "Voyante",70, Material.ENCHANTMENT_TABLE, Aura.LUMINOUS, "La voyante peut espionner un joueur à chaque épisode, mais gare à elle si elle se trompe. Aura = lumineuse", false, true),//correspondant
	INFECT_PERE_DES_LOUPS(Camp.Wolf, "Infect Père Des Loups",70, Material.FERMENTED_SPIDER_EYE, Aura.DANGEROUS, "L'infect père des loups (ou IPDL) possède force de nuit, de plus il peut choisir une fois dans la partie de réssuciter un joueur victime des loups garou en clicquant sur un message, celui-ci sera infecté et devra gagner avec les loups. Aura = Dangereuse", true, false ),//correspondant
	SWAPPER(Camp.Other, "Swapper", 0, Material.SLIME_BALL, Aura.NEUTRAL, "Role automatique du scénario teamSwapper", false, false),
	SORCIERE(Camp.Villager, "Sorcière",70, Material.POTION, Aura.NEUTRAL, "La sorcière gagne avec le village. La sorcière peut une fois dans la partie, réssuciter un joueur en clicquant sur un message, de plus elle possède une potion de instant heal, une d'instant damage et une de régénération, Aura = Neutre", true, false),//correspondant
	CUPIDON(Camp.Love, "Cupidon", 120, Material.BOW, Aura.NEUTRAL, "Le cupidon gagne avec le couple qu'il aura désigné, et il possède un arc punch 1, cependant si un membre de ce couple meure, l'autre mourra aussi. Aura neutre", false, false),
	MONTREUR(Camp.Villager, "Montreur",70, Material.CARROT_STICK, Aura.LUMINOUS, "Le montreur d'ours gagne avec le village, à chaque épisode, un ''GRRRR'' apparait dans le chat pour chaque loups dans les 50 blocs autour de lui, Aura lumineuse", false, true),//correspondant
	PETITE_FILLE(Camp.Villager, "Petite Fille",80, Material.EYE_OF_ENDER, Aura.LUMINOUS, "La petite fille doit gagner avec le village, pour cela, elle peut devenir invisible 5 minutes par nuit, et peut voir le chat des loups-garou", false, true),//correspondant
	CHASSEUR(Camp.Villager, "Chasseur",70, Material.BOW, Aura.NEUTRAL, "Le chasseur doit gagner avec les villageois, pour cela il possède un arc power 3, 20% de force contre les loups, et peut tirer sur un joueur à sa mort pour lui infliger 5 coeurs et lui faire perdre sa force s'il est loup. Aura neutre", false, true),//correspondant
	SALVATEUR(Camp.Villager, "Salvateur",80,  Material.IRON_HELMET, Aura.LUMINOUS, "Le salvateur gagne avec le village, il possède 2 potion d'instant heal et peut donner résistance à un joueur pendant 20min à chaque épisode Aura lumineuse", false, true),//correspondant
	CORBEAU(Camp.Villager, "Corbeau",70, Material.FEATHER, Aura.NEUTRAL, "Le corbeau gagne avec le village, à chaque fois que son vote correspond à la personne la plus voté, ce sera indiqué dans le chat, il recevra 2 gapple la 1ere fois, 4 la 2e,puis 2 coeur permanents, puis resistance 1. Aura neutre", false, true),
	SOEUR(Camp.Villager, "Soeur",80, Material.MELON, Aura.LUMINOUS, "Les soeurs possèdent force quand elles sont à moins de 20 blocs d'une de leurs soeurs, elle obtienne également le pseudo du tueur d'une soeur qui viendrait à mourir.  Aura lumineuse", false, true),//correspondant
	LOUP_BARBARE(Camp.Wolf, "Loup Garou Barbare",70, Material.DIAMOND_AXE, Aura.OBSCUR, "Le loup garou barbare possède force 0.5 perma, et a 5% de chance d'infliger un demi coeur en plus à chaque coup, pourcentage qui augmentera de 4% à chaque kill. A chaque kill, il gagne 2min de résistance mais perd 1 coeur permanent. Aura Obscure", true, false),
	BIENFAITEUR(Camp.Villager, "Bienfaiteur",100, Material.GOLDEN_CARROT, Aura.LUMINOUS, "Le bienfaiteur gagne avec le village, pour cela il peut donner 4 fois un coeur avec la commande /lg conferer [nomDuJoueur], de plus il possède 2 livres protection 2. Aura lumineuse", false, true),//correspondant
	IDIOT_DU_VILLAGE(Camp.Villager, "Idiot du Village",120, Material.CARROT_STICK, Aura.LUMINOUS, "L'idiot du village gagne avec le village (j'aurais pas pensé !!), si le village vient à l'éliminer, il réssucitera avec 2 coeurs en moins. Aura lumineuse", false, true),//correspondant
	LOUP_MYSTIQUE(Camp.Wolf, "Loup Mystique",80, Material.ENDER_PEARL, Aura.OBSCUR, "Le loup mystique gagne avec les loups, il possède la force des loups, et obtient un role aléatoire à chaque mort d'un loup. Aura obscur", true, false),//correspondant
	VOLEUR(Camp.Other, "Voleur",120, Material.GOLD_NUGGET, Aura.OBSCUR, "Le voleur gagne tout seul, il possède force jusqu'à 90min, et vole le role et le camp du premier joueur qu'il tuera. Aura obscur ", false, false),
	RENARD(Camp.Villager, "Renard",75, Material.LEATHER, Aura.LUMINOUS, "Le renard gagne avec le village, pour cela il peut connaitre le role d'un joueur après etre resté 10min à coté de lui, il y a cependant 15% de chance que le role ne soit pas bon, +5% par flairage déjà effectué. Aura lumineuse", false, true),//correspondant
	ANCIEN(Camp.Villager, "Ancien",70, Material.SAPLING, Aura.LUMINOUS, "L'ancien gagne avec le village, pour cela il possède une resistance permanente, s'il meure par les loups-garou, il ressucitera mais perdra sa résistance. Aura lumineuse", false, true),//correspondant
	PERFIDE(Camp.Wolf, "Loup Garou Perfide",85, Material.EYE_OF_ENDER, Aura.OBSCUR, "Le loup perfide gagne avec les loups-garou, pour cela il possède la force des loups et peut se mettre invisible 5min par nuit. Aura obscure", true, false),//correspondant
	ENFANT_SAUVAGE(Camp.Villager, "Enfant Sauvage", 90, Material.STICK, Aura.NEUTRAL, "L'enfant sauvage gagne avec le village, il choisit un modèle, si ce modèle meure, il passera loup-garou, et aura la force des loups. Aura neutre", false, true),
	SAGE(Camp.Villager, "Sage",90, Material.BANNER, Aura.LUMINOUS, "Le vieux sage gagne avec les villageois, pour cela il obtiendra le taux de présence de chaque aura autour de lui, (voir aura dans /lg help) fréquenter un joueur fera augmenter la barre de l'aura correspondant à son aura. Aura lumineuse", false, true),//correspondant
	ASSASSIN(Camp.Other, "Assassin", 140, Material.GOLD_SWORD, Aura.NEUTRAL, "L'assassin gagne tout seul, pour cela il peut fabriquer une sharpness 4, possède force de jour, et obtient 3 livres: sharpness 3, protection 3, efficacité 3. Aura neutre", false, false),
	INTERPRETE(Camp.Villager, "Interprete",95, Material.FLOWER_POT_ITEM, Aura.NEUTRAL, "L'interprète gagne avec le village, pour cela il peut interpréter 1 role entre 3 qui lui seront communiqués peu avant le début d'un épisode, il incarnera ce role pendant l'épisode, il ne peut interpréter chaque role qu'une fois. Aura neutre", false, true),
	DISCIPLE(Camp.Villager, "Disciple", 80, Material.BOOK_AND_QUILL, Aura.LUMINOUS, "Le disciple gagne avec le village, pour cela il connait l'identité du vieux sage, après 20min à ses cotés, le vieux sage obtiendra son identité, après 30min, il aura accès 2 fois à la commande /lg aura [nomDuJoueur] lui permettant d'obtenir l'aura d'un joueur, au bout de 45min, il obtiendra speed 0,5. Aura Lumineuse", false, true),//correspondant
	LOUP_METAMORPHE(Camp.Wolf, "Loup métamorphe", 70, Material.SLIME_BALL, Aura.OBSCUR, "Le loup métamorphe gagne avec les loups et possède la force des loups, il volera le role du premier joueur qu'il tuera. Aura obscure", true, false),//correspondant
	PYROMANE(Camp.Other, "Pyromane", 140, Material.TORCH, Aura.NEUTRAL, "Le pyromane gagne tout seul, il possède fire aspect et flame activable ou desactivable avec /lg fireaspect, il peut recouvrir 2 joueurs à moins de 20 blocs avec /lg recouvrir [nomDuJoueur], et tous les enflammer avec /lg enflammer pour les enflammer pendant 20 sec sans qu'ils puissent d'éteindre. Aura neutre", false, false),
	FAUCONNIER(Camp.Villager, "Fauconnier", 75,  Material.FEATHER, Aura.NEUTRAL, "description à faire, role à info aura neutre", false, true),
	ALLUMEUR(Camp.Villager, "Allumeur de Lampadaire", 110, Material.TORCH, Aura.NEUTRAL, "L'allumeur de lampadaire gagne avec le village, pour cela, il rend l'aura des joueurs autour de lui correspondant à leur objectif de victoire au bout de 10min à coté d'eux. Aura neutre ", false, true),
	ANGE(Camp.Other, "Ange",130, Material.FEATHER, Aura.LUMINOUS, "Description non fini Aura lumineuse", false, false),
	LOUP_BRUMEUX(Camp.Wolf, "Loup Brumeux", 85, Material.NAME_TAG, Aura.OBSCUR, "Le loup brumeux gagne avec les loups-garou, il peut, 2 fois dans la partie, cacher la mort d'un joueur.", true, false),
	LOUP_GRIMEUR(Camp.Wolf, "Loup Garou Grimeur", 80, Material.BANNER, Aura.OBSCUR, "Le loup garou grimeur possède la force des loups de nuit, il gagne avec les loups et peut afficher les joueurs qu'il a tués comme loup garou à l'annonce de leurs rôles malgrés leurs véritables rôles", true, false),
	PARRAIN(Camp.Villager, "Parrain", 75, Material.STONE_SWORD, Aura.DANGEROUS, "Le parrain gagne avec le village, pour cela, il peut mettre une prime sur un joueur à chaque épisode, cette prime sera envoyé à un 1 joueur du camp opposé au hasard, s'il le tue, les 2 gagneront 5% de force et 1/2 coeur permanent Aura Dangereuse", false, true),
	SERVANT_DES_LOUPS(Camp.Wolf, "Servant des loups", 60, Material.ROTTEN_FLESH, Aura.NEUTRAL, "Le servant des loups ne peut pas etre attribué à l'attribution des roles, il gagne avec les loups et possède la force des loups. Il est rattaché à un maitre (loup), si celui-ci meure, le loup servant meure à la place Aura Neutre", true, false),
	LOUP_ALCHIMISTE(Camp.Wolf, "Loup garou Alchimiste", 100, Material.POTION, Aura.OBSCUR, "Le loup garou alchimiste gagne avec les loups mais ne possède pas leur force, il peut une fois mettre un virus sur un joueur à coté de qui il est resté 5min, cette infection peut prendre 3 formes, le parasite qui prendra effet après 10min, mais qui pourra etre transmis par un kill de l'infecté, l'épidémie qui se transmet de joueur en joueur, le joueur sera avertis après une min, et le poison qui affaiblit la victime jusqu'à la mort du loup alchimiste Aura obscure", true, false),
	LOUP_MANIP(Camp.Wolf, "Loup Manipulateur", 70, Material.ENDER_CHEST, Aura.UNKNOW, "Le loup garou manipulateur gagne avec les loups garou, mais ne possède pas force, à chaque épisode, il peut aveugler un joueur, si ce joueur a un role à info, le loup manipulateur obtiendra son role et l'aveuglera, ce qui empechera le joueur de gagner des info pour l'épisode Aura ///UNKNOWN///", true, false),
	ERMITE(Camp.Villager, "Ermite", 70, Material.WOOL, Aura.UNKNOW, "L'ermite gagne avec les Villageois, il possède 30% de force le jour et 20% de résistance la nuit, mais pour chaque joueur autour de lui, il perdra 5% de force, le jour et 3% de resistance la nuit, il peut donc avoir un malus au lieu d'un bonus si trop de joueurs. Si le registre est tragique, il gagne 10% de résistance, s'il est oratoire, il perd 10% de force. Sa mort ne sera pas annoncée.  Aura Unknown", false, true),
	COMEDIEN(Camp.Villager, "Comédien", 80, Material.PAPER, Aura.LUMINOUS, ChatColor.DARK_BLUE+"Le comédien gagne avec les Villageois, pour cela, son pouvoir change en fonction du registre de la pièce. Si le registre tragique est à 20%, il aura 20% de chance de connaitre l'aura d'un joueur, 20% de chance de connaitre son nombre de kill, et 20% de chance de savoir s'il a un effet. Il possède une résistance proportionnelle à taux d'Epique, (20% d'epique = 4% de resistance, 50% = 10% et 100% = 20%). Pour un taux de oratoire à 20%, il aura 20% de chance de connaitre l'ensemble des pseudos des joueurs ayant voté pour la personne la + votée.", false, true),
	LOUP_CRAINTIF(Camp.Wolf, "Loup Garou Craintif", 70, Material.BLAZE_POWDER, Aura.UNKNOW, ChatColor.DARK_BLUE+"Le loup craintif gagne avec les Loups Garous (possède la liste de ses alliés loups), pour cela il obtient 30% de force la nuit et 20% de résistance le jour, moins 5% de résistance le jour par loup-garou proche de lui, et moins 7% de force par loup-garou proche la nuit. Si le registre est oratoire, il perd 10% de force, s'il est tragic il gagne 5% de résistance. Sa mort est cachée.   Aura Unknown", true, false),
	ANGE_THIERCE(Camp.Other, "Ange de Thiercelieux", 140, Material.FEATHER, Aura.LUMINOUS, "L'ange de thiercelieux gagne tout seul, il peut craft une sharpness 4, quand il est accusé, il gagne 20% de force contre l'accuseur et connait son role, 5% de résistance et 1 coeur permanents, et l'accuseur ne possède pas 20% de force contre lui. Si l'ange de T tue l'accuseur, sa mort ne sera pas annoncée. Aura Lumineuse", false, false),
	DEMON(Camp.Other, "Démon", 150, Material.NETHERRACK, Aura.OBSCUR, "", false, false),
	DAMNE(Camp.Other, "Damné", 60, Material.BANNER,Aura.OBSCUR, "Role utilitaire, ne pas utiliser", false, false),
	TRAQUEUR(Camp.Villager, "Traqueur", 75, Material.WEB, Aura.NEUTRAL, "Villageois, aura neutre, peut traquer un joueur avec la commande /lg traquer [nomDuJoueur] (rayon de 20 blocs + pas de changement jusqu'à sa mort). Puis le traqueur peut connaitre la position et le nombre de kill du joueur traqué à tout moment avec la commande /lg traque.", false, true),
	THANOS(Camp.Other, "Thanos", 200, Material.EMERALD,Aura.DANGEROUS, "Vous verrez", false, false),
	SORCIER(Camp.Other, "Sorcier", 80, Material.BLAZE_POWDER, Aura.NEUTRAL, "Le sorcier a 50% de gagner tout seul, et 50% de gagner avec le village, il peut créer des potions avec certains matériaux. Ces potions ont des effets divers, ce sont celles que des joueurs normaux trouverait dans des batiments bonus.", false, false),
	LOUP_SANGUINAIRE(Camp.Wolf, "Loup Garou Sanguinaire",70 , Material.REDSTONE, Aura.OBSCUR, "description à faire", true, false),
	NEGOCIATEUR(Camp.Other, "Négociateur", 110, Material.BOOK_AND_QUILL, Aura.LUMINOUS, "Le négociateur peut gagner avec les villageois et les loups à condition que les joueurs restant à la fin de la partie aient passé au moins 10min à coté du négociateur et que les joueurs tués par le négociateur du même camp que celui restant aient passé moins de 15min à coté des joueurs encore en vie. Aura lumineuse", false, false),
	LOUP_HURLEUR(Camp.Wolf, "Loup Garou Hurleur", 85, Material.BONE, Aura.OBSCUR, "Le loup hurleur gagne avec les loups, il possède une aura obscure. Deux fois dans la partie, il peut hurler avec la commande /lg hurler. Les loups aux alentours entendront le hurlement et gagneront tous un effet de régénération léger. Pour chaque loup aux alentours, le hurleur 5 secondes de régénération.", true, false),
	NECROMANCIEN(Camp.Died, "Nécromancien", 130, Material.ROTTEN_FLESH, Aura.OBSCUR, "description à faire", false, false),
	SIMPLE_WOLF(Camp.Wolf, "Simple Loup Garou", 100, Material.DIAMOND_SWORD, Aura.OBSCUR, "Le loup garou simple gagne avec les loups, et possède la force des loups. Aura obscure", true, false);//correspondant
	
	private Camp roleCamp;
	private String name;
	public ItemStack item;
	public Material mat;
	public Aura aura;
	public String description;
	public boolean considWolf;
	
	public boolean considVill;
	public int winValue;
	
	
	RolesLg(Camp campOfTheRole, String name, int winValue, Material  mat, Aura aura, String description, boolean considWolf, boolean considVill) {
		this.roleCamp = campOfTheRole;
		this.name = name;
		this.aura = aura;
		this.mat = mat;
		this.item = new ItemStack(mat);
		ItemMeta meta = this.item.getItemMeta();
		meta.setDisplayName(this.roleCamp.getColor()+ this.name); 
		this.item.setItemMeta(meta);
		this.description = description;
		this.considVill = considVill;
		this.considWolf = considWolf;
		
		this.winValue = winValue;
		
		
		
	}
	
	public int getWinValue() {
		return winValue;
	}
	
	public void changeCampTo(Camp camp) {
		this.roleCamp = camp;
	}
	
	 
	public Camp getCampOfRole() { 
		return this.roleCamp;
	}
	
	public String getName() {
		return this.name;
	}
	public boolean isConsidWolf() {
		return this.considWolf;
	}
	public boolean isConsidVill() {
		return this.considVill;
	}
	
	public String getDescription() {
		return this.description;
	}
	public void refresh(GameLg gameScore) {
		this.item = new ItemStack(mat);
		ItemMeta meta = this.item.getItemMeta();
		meta.setDisplayName(this.roleCamp.getColor()+ this.name);
		ArrayList<String> lores = new ArrayList<String>();
		lores.add(String.valueOf( RoleUtil.getPlayersWithRole(gameScore, this).size()));
		meta.setLore(lores);
	}
}
