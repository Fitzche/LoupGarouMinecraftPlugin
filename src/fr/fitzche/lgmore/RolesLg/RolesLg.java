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
	SIMPLE_VILLAGER(Camp.Villager, "Simple Villageois", Material.WHEAT, Aura.LUMINOUS),
	VOYANTE(Camp.Villager, "Voyante", Material.ENCHANTMENT_TABLE, Aura.LUMINOUS),
	INFECT_PERE_DES_LOUPS(Camp.Wolf, "Infect Père Des Loups", Material.FERMENTED_SPIDER_EYE, Aura.DANGEROUS),
	SORCIERE(Camp.Villager, "Sorcière", Material.POTION, Aura.NEUTRAL),
	CUPIDON(Camp.Love, "Cupidon", Material.BOW, Aura.NEUTRAL),
	MONTREUR(Camp.Villager, "Montreur", Material.CARROT_STICK, Aura.LUMINOUS),
	PETITE_FILLE(Camp.Villager, "Petite Fille", Material.EYE_OF_ENDER, Aura.LUMINOUS),
	CHASSEUR(Camp.Villager, "Chasseur", Material.BOW, Aura.LUMINOUS),
	SALVATEUR(Camp.Villager, "Salvateur", Material.IRON_HELMET, Aura.LUMINOUS),
	CORBEAU(Camp.Villager, "Corbeau", Material.FEATHER, Aura.NEUTRAL),
	SOEUR(Camp.Villager, "Soeur", Material.MELON, Aura.LUMINOUS),
	BIENFAITEUR(Camp.Villager, "Bienfaiteur", Material.GOLDEN_CARROT, Aura.LUMINOUS),
	IDIOT_DU_VILLAGE(Camp.Villager, "Idiot du Village", Material.CARROT_STICK, Aura.LUMINOUS),
	LOUP_MYSTIQUE(Camp.Wolf, "Loup Mystique", Material.ENDER_PEARL, Aura.OBSCUR),
	VOLEUR(Camp.Other, "Voleur", Material.GOLD_NUGGET, Aura.OBSCUR),
	RENARD(Camp.Villager, "Renard", Material.LEATHER, Aura.LUMINOUS),
	ANCIEN(Camp.Villager, "Ancien", Material.SAPLING, Aura.LUMINOUS),
	PERFIDE(Camp.Wolf, "Loup Garou Perfide", Material.EYE_OF_ENDER, Aura.OBSCUR),
	ENFANT_SAUVAGE(Camp.Villager, "Enfant Sauvage", Material.STICK, Aura.NEUTRAL),
	SAGE(Camp.Villager, "Sage", Material.BANNER, Aura.LUMINOUS),
	ASSASSIN(Camp.Other, "Assassin", Material.GOLD_SWORD, Aura.NEUTRAL),
	INTERPRETE(Camp.Villager, "Interprete", Material.FLOWER_POT_ITEM, Aura.NEUTRAL),
	DISCIPLE(Camp.Villager, "Disciple", Material.BOOK_AND_QUILL, Aura.LUMINOUS),
	LOUP_METAMORPHE(Camp.Wolf, "Loup métamorphe", Material.SLIME_BALL, Aura.OBSCUR),
	PYROMANE(Camp.Other, "Pyromane", Material.TORCH, Aura.NEUTRAL),
	SIMPLE_WOLF(Camp.Wolf, "Simple Loup Garou", Material.DIAMOND_SWORD, Aura.OBSCUR);
	
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
