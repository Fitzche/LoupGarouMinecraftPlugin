package fr.fitzche.lgmore.RolesLg;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.fitzche.lgmore.GameLg;
import fr.fitzche.lgmore.Util.GameLgUtil;
import fr.fitzche.lgmore.Util.RoleUtilLg;

public enum RolesLg {
	SIMPLE_VILLAGER(Camp.Villager, "Simple Villageois", Material.WHEAT, Aura.LUMINOUS, "Le simple villageois gagne avec les villageois sans aucun pouvoir hormis son droit de vote et son épée. Aura = lumineuse"),//correspondant
	VOYANTE(Camp.Villager, "Voyante", Material.ENCHANTMENT_TABLE, Aura.LUMINOUS, "La voyante peut espionner un joueur à chaque épisode, mais gare à elle si elle se trompe. Aura = lumineuse"),//correspondant
	INFECT_PERE_DES_LOUPS(Camp.Wolf, "Infect Père Des Loups", Material.FERMENTED_SPIDER_EYE, Aura.DANGEROUS, "L'infect père des loups (ou IPDL) possède force de nuit, de plus il peut choisir une fois dans la partie de réssuciter un joueur victime des loups garou en clicquant sur un message, celui-ci sera infecté et devra gagner avec les loups. Aura = Dangereuse" ),//correspondant
	SORCIERE(Camp.Villager, "Sorcière", Material.POTION, Aura.NEUTRAL, "La sorcière gagne avec le village. La sorcière peut une fois dans la partie, réssuciter un joueur en clicquant sur un message, de plus elle possède une potion de instant heal, une d'instant damage et une de régénération, Aura = Neutre"),//correspondant
	CUPIDON(Camp.Love, "Cupidon", Material.BOW, Aura.NEUTRAL, "Le cupidon gagne avec le couple qu'il aura désigné, et il possède un arc punch 1, cependant si un membre de ce couple meure, l'autre mourra aussi. Aura neutre"),
	MONTREUR(Camp.Villager, "Montreur", Material.CARROT_STICK, Aura.LUMINOUS, "Le montreur d'ours gagne avec le village, à chaque épisode, un ''GRRRR'' apparait dans le chat pour chaque loups dans les 50 blocs autour de lui, Aura lumineuse"),//correspondant
	PETITE_FILLE(Camp.Villager, "Petite Fille", Material.EYE_OF_ENDER, Aura.LUMINOUS, "La petite fille doit gagner avec le village, pour cela, elle peut devenir invisible 5 minutes par nuit, et peut voir le chat des loups-garou"),//correspondant
	CHASSEUR(Camp.Villager, "Chasseur", Material.BOW, Aura.NEUTRAL, "Le chasseur doit gagner avec les villageois, pour cela il possède un arc power 3, 20% de force contre les loups, et peut tirer sur un joueur à sa mort pour lui infliger 5 coeurs et lui faire perdre sa force s'il est loup. Aura neutre"),//correspondant
	SALVATEUR(Camp.Villager, "Salvateur", Material.IRON_HELMET, Aura.LUMINOUS, "Le salvateur gagne avec le village, il possède 2 potion d'instant heal et peut donner réistance à un joueur pendant 20min à chaque épisode Aura lumineuse"),//correspondant
	CORBEAU(Camp.Villager, "Corbeau", Material.FEATHER, Aura.NEUTRAL, "Le corbeau gagne avec le village, à chaque fois que son vote correspond à la personne la plus voté, ce sera indiqué dans le chat, il recevra 2 gapple la 1ere fois, 4 la 2e,puis 2 coeur permanents, puis resistance 1. Aura neutre"),
	SOEUR(Camp.Villager, "Soeur", Material.MELON, Aura.LUMINOUS, "Les soeurs possèdent force quand elles sont à moins de 20 blocs d'une de leurs soeurs, elle obtienne également le pseudo du tueur d'une soeur qui viendrait à mourir.  Aura lumineuse"),//correspondant
	LOUP_BARBARE(Camp.Wolf, "Loup Garou Barbare", Material.DIAMOND_AXE, Aura.OBSCUR, "Le loup garou barbare possède force 0.5 perma, et a 5% de chance d'infliger un demi coeur en plus à chaque coup, pourcentage qui augmentera de 4% à chaque kill. A chaque kill, il gagne 2min de résistance mais perd 1 coeur permanent. Aura Obscure"),
	BIENFAITEUR(Camp.Villager, "Bienfaiteur", Material.GOLDEN_CARROT, Aura.LUMINOUS, "Le bienfaiteur gagne avec le village, pour cela il peut donner 4 fois un coeur avec la commande /lg conferer [nomDuJoueur], de plus il possède 2 livres protection 2. Aura lumineuse"),//correspondant
	IDIOT_DU_VILLAGE(Camp.Villager, "Idiot du Village", Material.CARROT_STICK, Aura.LUMINOUS, "L'idiot du village gagne avec le village (j'aurais pas pensé !!), si le village vient à l'éliminer, il réssucitera avec 2 coeurs en moins. Aura lumineuse"),//correspondant
	LOUP_MYSTIQUE(Camp.Wolf, "Loup Mystique", Material.ENDER_PEARL, Aura.OBSCUR, "Le loup mystique gagne avec les loups, il possède la force des loups, et obtient un role aléatoire à chaque mort d'un loup. Aura obscur"),//correspondant
	VOLEUR(Camp.Other, "Voleur", Material.GOLD_NUGGET, Aura.OBSCUR, "Le voleur gagne tout seul, il possède force jusqu'à 60min, et vole le role et le camp du premier joueur qu'il tuera. Aura obscur "),
	RENARD(Camp.Villager, "Renard", Material.LEATHER, Aura.LUMINOUS, "Le renard gagne avec le village, pour cela il peut connaitre le role d'un joueur après etre resté 10min à coté de lui, il y a cependant 15% de chance que le role ne soit pas bon, +5% par flairage déjà effectué. Aura lumineuse"),//correspondant
	ANCIEN(Camp.Villager, "Ancien", Material.SAPLING, Aura.LUMINOUS, "L'ancien gagne avec le village, pour cela il possède une resistance permanente, s'il meure par les loups-garou, il ressucitera mais perdra sa résistance. Aura lumineuse"),//correspondant
	PERFIDE(Camp.Wolf, "Loup Garou Perfide", Material.EYE_OF_ENDER, Aura.OBSCUR, "Le loup perfide gagne avec les loups-garou, pour cela il possède la force des loups et peut se mettre invisible 5min par nuit. Aura obscure"),//correspondant
	ENFANT_SAUVAGE(Camp.Villager, "Enfant Sauvage", Material.STICK, Aura.NEUTRAL, "L'enfant sauvage gagne avec le village, il choisit un modèle, si ce modèle meure, il passera loup-garou, et aura la force des loups. Aura neutre"),
	SAGE(Camp.Villager, "Sage", Material.BANNER, Aura.LUMINOUS, "Le vieux sage gagne avec les villageois, pour cela il obtiendra le taux de présence de chaque aura autour de lui, (voir aura dans /lg help) fréquenter un joueur fera augmenter la barre de l'aura correspondant à son aura. Aura lumineuse"),//correspondant
	ASSASSIN(Camp.Other, "Assassin", Material.GOLD_SWORD, Aura.NEUTRAL, "L'assassin gagne tout seul, pour cela il peut fabriquer une sharpness 4, possède force de jour, et obtient 3 livres: sharpness 3, protection 3, efficacité 3. Aura neutre"),
	INTERPRETE(Camp.Villager, "Interprete", Material.FLOWER_POT_ITEM, Aura.NEUTRAL, "L'interprète gagne avec le village, pour cela il peut interpréter 1 role entre 3 qui lui seront communiqués peu avant le début d'un épisode, il incarnera ce role pendant l'épisode, il ne peut interpréter chaque role qu'une fois. Aura neutre"),
	DISCIPLE(Camp.Villager, "Disciple", Material.BOOK_AND_QUILL, Aura.LUMINOUS, "Le disciple gagne avec le village, pour cela il connait l'identité du vieux sage, après 20min à ses cotés, le disciple obtiendra votre role, après 30min, il aura accès 2 fois à la commande /lg aura [nomDuJoueur] lui permettant d'obtenir l'aura d'un joueur, au bout de 45min, il obtiendra speed 0,5. Aura Lumineuse"),//correspondant
	LOUP_METAMORPHE(Camp.Wolf, "Loup métamorphe", Material.SLIME_BALL, Aura.OBSCUR, "Le loup métamorphe gagne avec les loups et possède la force des loups, il volera le role du premier joueur qu'il tuera. Aura obscure"),//correspondant
	PYROMANE(Camp.Other, "Pyromane", Material.TORCH, Aura.NEUTRAL, "Le pyromane gagne tout seul, il possède fire aspect et flame activable ou desactivable avec /lg fireaspect, il peut recouvrir 2 joueurs à moins de 20 blocs avec /lg recouvrir [nomDuJoueur], et tous les enflammer avec /lg enflammer pour les enflammer pendant 20 sec sans qu'ils puissent d'éteindre Aura neutre"),
	ALLUMEUR(Camp.Villager, "Allumeur de Lampadaire", Material.TORCH, Aura.NEUTRAL, "L'allumeur de lampadaire gagne avec le village, pour cela, il rend l'aura des joueurs autour de lui correspondant à leur objectif de victoire au bout de 10min à coté d'eux. Aura neutre "),
	ANGE(Camp.Other, "Ange", Material.FEATHER, Aura.LUMINOUS, "Description non fini Aura lumineuse"),
	PARRAIN(Camp.Villager, "Parrain", Material.STONE_SWORD, Aura.DANGEROUS, "Le parrain gagne avec le village, pour cela, il peut mettre une prime sur un joueur à chaque épisode, cette prime sera envoyé à un 1 joueur du camp opposé au hasard, s'il le tue, les 2 gagneront 5% de force et 1/2 coeur permanent Aura Dangereuse"),
	SERVANT_DES_LOUPS(Camp.Wolf, "Servant des loups", Material.ROTTEN_FLESH, Aura.NEUTRAL, "Le servant des loups ne peut pas etre attribué à l'attribution des roles, il gagne avec les loups et possède la force des loups. Il est rattaché à un maitre (loup), si celui-ci meure, le loup servant meure à la place Aura Neutre"),
	LOUP_ALCHIMISTE(Camp.Wolf, "Loup garou Alchimiste", Material.POTION, Aura.OBSCUR, "Le loup garou gagne avec les loups mais ne possède pas leur force, il peut une fois mettre un virus sur un joueur à coté de qui il est resté 5min, cette infection peut prendre 3 formes, le parasite qui prendra effet après 10min, mais qui pourra etre transmis par un kill de l'infecté, l'épidémie qui se transmet de joueur en joueur, le joueur sera avertis après une min, et le poison qui affaiblit la victime jusqu'à la mort du loup alchimiste Aura obscure"),
	LOUP_MANIP(Camp.Wolf, "Loup Manipulateur", Material.ENDER_CHEST, Aura.UNKNOW, "Le loup garou manipulateur gagne avec les loups garou, mais ne possède pas force, à chaque épisode, il peut aveugler un joueur, si ce joueur a un role à info, le loup manipulateur obtiendra son role et l'aveuglera, ce qui empechera le joueur de gagner des info pour l'épisode Aura ///UNKNOWN///"),
	SIMPLE_WOLF(Camp.Wolf, "Simple Loup Garou", Material.DIAMOND_SWORD, Aura.OBSCUR, "Le loup garou simple gagne avec les loups, et possède la force des loups. Aura obscure");//correspondant
	
	private Camp roleCamp;
	private String name;
	public ItemStack item;
	public Material mat;
	public Aura aura;
	public String description;
	
	
	
	
	RolesLg(Camp campOfTheRole, String name, Material  mat, Aura aura, String description) {
		this.roleCamp = campOfTheRole;
		this.name = name;
		this.aura = aura;
		this.mat = mat;
		this.item = new ItemStack(mat);
		ItemMeta meta = this.item.getItemMeta();
		meta.setDisplayName(this.roleCamp.getColor()+ this.name); 
		this.item.setItemMeta(meta);
		this.description = description;
		
		
		
		
		
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
	
	public String getDescription() {
		return this.description;
	}
	public void refresh(GameLg gameScore) {
		this.item = new ItemStack(mat);
		ItemMeta meta = this.item.getItemMeta();
		meta.setDisplayName(this.roleCamp.getColor()+ this.name);
		ArrayList<String> lores = new ArrayList<String>();
		lores.add(String.valueOf( RoleUtilLg.getPlayersWithRole(gameScore, this).size()));
		meta.setLore(lores);
	}
}
