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
	SIMPLE_VILLAGER(Camp.Villager, "Simple Villageois", Material.WHEAT, Aura.LUMINOUS),//correspondant
	VOYANTE(Camp.Villager, "Voyante", Material.ENCHANTMENT_TABLE, Aura.LUMINOUS),//correspondant
	INFECT_PERE_DES_LOUPS(Camp.Wolf, "Infect Père Des Loups", Material.FERMENTED_SPIDER_EYE, Aura.DANGEROUS),//correspondant
	SORCIERE(Camp.Villager, "Sorcière", Material.POTION, Aura.NEUTRAL),//correspondant
	CUPIDON(Camp.Love, "Cupidon", Material.BOW, Aura.NEUTRAL),
	MONTREUR(Camp.Villager, "Montreur", Material.CARROT_STICK, Aura.LUMINOUS),//correspondant
	PETITE_FILLE(Camp.Villager, "Petite Fille", Material.EYE_OF_ENDER, Aura.LUMINOUS),//correspondant
	CHASSEUR(Camp.Villager, "Chasseur", Material.BOW, Aura.LUMINOUS),//correspondant
	SALVATEUR(Camp.Villager, "Salvateur", Material.IRON_HELMET, Aura.LUMINOUS),//correspondant
	CORBEAU(Camp.Villager, "Corbeau", Material.FEATHER, Aura.NEUTRAL),
	SOEUR(Camp.Villager, "Soeur", Material.MELON, Aura.LUMINOUS),//correspondant
	BIENFAITEUR(Camp.Villager, "Bienfaiteur", Material.GOLDEN_CARROT, Aura.LUMINOUS),//correspondant
	IDIOT_DU_VILLAGE(Camp.Villager, "Idiot du Village", Material.CARROT_STICK, Aura.LUMINOUS),//correspondant
	LOUP_MYSTIQUE(Camp.Wolf, "Loup Mystique", Material.ENDER_PEARL, Aura.OBSCUR),//correspondant
	VOLEUR(Camp.Other, "Voleur", Material.GOLD_NUGGET, Aura.OBSCUR),
	RENARD(Camp.Villager, "Renard", Material.LEATHER, Aura.LUMINOUS),//correspondant
	ANCIEN(Camp.Villager, "Ancien", Material.SAPLING, Aura.LUMINOUS),//correspondant
	PERFIDE(Camp.Wolf, "Loup Garou Perfide", Material.EYE_OF_ENDER, Aura.OBSCUR),//correspondant
	ENFANT_SAUVAGE(Camp.Villager, "Enfant Sauvage", Material.STICK, Aura.NEUTRAL),
	SAGE(Camp.Villager, "Sage", Material.BANNER, Aura.LUMINOUS),//correspondant
	ASSASSIN(Camp.Other, "Assassin", Material.GOLD_SWORD, Aura.NEUTRAL),
	INTERPRETE(Camp.Villager, "Interprete", Material.FLOWER_POT_ITEM, Aura.NEUTRAL),
	DISCIPLE(Camp.Villager, "Disciple", Material.BOOK_AND_QUILL, Aura.LUMINOUS),//correspondant
	LOUP_METAMORPHE(Camp.Wolf, "Loup métamorphe", Material.SLIME_BALL, Aura.OBSCUR),//correspondant
	PYROMANE(Camp.Other, "Pyromane", Material.TORCH, Aura.NEUTRAL),
	ALLUMEUR(Camp.Villager, "Allumeur de Lampadaire", Material.TORCH, Aura.NEUTRAL),
	ANGE(Camp.Other, "Ange", Material.FEATHER, Aura.LUMINOUS),
	PARRAIN(Camp.Villager, "Parrain", Material.STONE_SWORD, Aura.DANGEROUS),
	SIMPLE_WOLF(Camp.Wolf, "Simple Loup Garou", Material.DIAMOND_SWORD, Aura.OBSCUR);//correspondant
	
	private Camp roleCamp;
	private String name;
	public ItemStack item;
	public Material mat;
	public Aura aura;
	
	
	
	
	RolesLg(Camp campOfTheRole, String name, Material  mat, Aura aura) {
		this.roleCamp = campOfTheRole;
		this.name = name;
		this.aura = aura;
		this.mat = mat;
		this.item = new ItemStack(mat);
		ItemMeta meta = this.item.getItemMeta();
		meta.setDisplayName(this.roleCamp.getColor()+ this.name); 
		this.item.setItemMeta(meta);
		
		
		
		
		
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
	public void refresh(GameLg gameScore) {
		this.item = new ItemStack(mat);
		ItemMeta meta = this.item.getItemMeta();
		meta.setDisplayName(this.roleCamp.getColor()+ this.name);
		ArrayList<String> lores = new ArrayList<String>();
		lores.add(String.valueOf( RoleUtilLg.getPlayersWithRole(gameScore, this).size()));
		meta.setLore(lores);
	}
}
