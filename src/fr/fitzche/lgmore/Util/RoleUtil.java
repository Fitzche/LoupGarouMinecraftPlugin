package fr.fitzche.lgmore.Util;

import java.util.ArrayList;

import javax.management.relation.Role;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import fr.fitzche.lgmore.Camp;
import fr.fitzche.lgmore.Main;
import fr.fitzche.lgmore.PlayerData;
import fr.fitzche.lgmore.RoleInstance;
import fr.fitzche.lgmore.Lg.GameLg;
import fr.fitzche.lgmore.RolesLg.ALLUMEUR;
import fr.fitzche.lgmore.RolesLg.ANCIEN;
import fr.fitzche.lgmore.RolesLg.ANGE;
import fr.fitzche.lgmore.RolesLg.ASSASSIN;
import fr.fitzche.lgmore.RolesLg.BIENFAITEUR;
import fr.fitzche.lgmore.RolesLg.CHASSEUR;
import fr.fitzche.lgmore.RolesLg.COMEDIEN;
import fr.fitzche.lgmore.RolesLg.CORBEAU;
import fr.fitzche.lgmore.RolesLg.CUPIDON;
import fr.fitzche.lgmore.RolesLg.DEMON;
import fr.fitzche.lgmore.RolesLg.DISCIPLE;
import fr.fitzche.lgmore.RolesLg.ENFANT_SAUVAGE;
import fr.fitzche.lgmore.RolesLg.ERMITE;
import fr.fitzche.lgmore.RolesLg.FAUCONNIER;
import fr.fitzche.lgmore.RolesLg.IDIOT_DU_VILLAGE;
import fr.fitzche.lgmore.RolesLg.INFECT_PERE_DES_LOUPS;
import fr.fitzche.lgmore.RolesLg.INTERPRETE;
import fr.fitzche.lgmore.RolesLg.LOUP_ALCHIMISTE;
import fr.fitzche.lgmore.RolesLg.LOUP_BARBARE;
import fr.fitzche.lgmore.RolesLg.LOUP_BRUMEUX;
import fr.fitzche.lgmore.RolesLg.LOUP_CRAINTIF;
import fr.fitzche.lgmore.RolesLg.LOUP_GRIMEUR;
import fr.fitzche.lgmore.RolesLg.LOUP_HURLEUR;
import fr.fitzche.lgmore.RolesLg.LOUP_MANIPULATEUR;
import fr.fitzche.lgmore.RolesLg.LOUP_METAMORPHE;
import fr.fitzche.lgmore.RolesLg.LOUP_MYSTIQUE;
import fr.fitzche.lgmore.RolesLg.LOUP_SANGUINAIRE;
import fr.fitzche.lgmore.RolesLg.MONTREUR;
import fr.fitzche.lgmore.RolesLg.NECROMANCIEN;
import fr.fitzche.lgmore.RolesLg.NEGOCIATEUR;
import fr.fitzche.lgmore.RolesLg.PARRAIN;
import fr.fitzche.lgmore.RolesLg.PERFIDE;
import fr.fitzche.lgmore.RolesLg.PETITE_FILLE;
import fr.fitzche.lgmore.RolesLg.PYROMANE;
import fr.fitzche.lgmore.RolesLg.RENARD;
import fr.fitzche.lgmore.RolesLg.RolesLg;
import fr.fitzche.lgmore.RolesLg.SAGE;
import fr.fitzche.lgmore.RolesLg.SALVATEUR;
import fr.fitzche.lgmore.RolesLg.SIMPLE_VILLAGER;
import fr.fitzche.lgmore.RolesLg.SIMPLE_WOLF;
import fr.fitzche.lgmore.RolesLg.SOEUR;
import fr.fitzche.lgmore.RolesLg.SORCIER;
import fr.fitzche.lgmore.RolesLg.SORCIERE;
import fr.fitzche.lgmore.RolesLg.THANOS;
import fr.fitzche.lgmore.RolesLg.THIERCE_ANGE;
import fr.fitzche.lgmore.RolesLg.TRAQUEUR;
import fr.fitzche.lgmore.RolesLg.VOLEUR;
import fr.fitzche.lgmore.RolesLg.VOYANTE;

public class RoleUtil {
	public static  ArrayList<RolesLg> existingRoles = new ArrayList<RolesLg>();
	
	
	
	
	
	public static ArrayList<PlayerData> getPlayersWithRole(GameLg game ,RolesLg role) {
		
		ArrayList<PlayerData> returnedPlayers = new ArrayList<PlayerData>();
		
		for (PlayerData player: game.getPlayerAlive()) {
			if (player.getLgRole().equals(role)) {
				returnedPlayers.add(player);
			}
		}
		
		return returnedPlayers;
	}
	
	public static boolean getContainsLgRole(GameLg g, RolesLg role) {
		for (RolesLg role1: g.roles) {
			if (role1.equals(role)) {
				return true;
			}
		}
		
		
		
		return false;
	}
	
	
	
	public static boolean isRoleIn(GameLg game, RolesLg rl) {
		for (RolesLg role:game.roles) {
			if (role.equals(rl)) {
				return true;
			}
		}
		return false;
	}
	
	
	public static RoleInstance createRoleOfPlayerRoles(PlayerData player) {
		
		return player.role.createRoleOfPlayerRoles(player);
		
		
	}

	


	public static RoleInstance createRole(RolesLg role, Player player) {
		PlayerData temp = new PlayerData(player);
		temp.role = role;
	
		return createRoleOfPlayerRoles(temp);
	}
	
	public static RolesLg RoleofString(String str) {
		for (RolesLg role: RoleUtil.existingRoles) {
			if (isStringRole(str, role)) {
				return role;
			}
		}
		return null;
	}
	public static boolean isStringRole(String str, RolesLg role) {
		if (str.equals(role.getName())||  str.equals(role.getCampOfRole().getColor()+ role.getName())) {
			return true;
		} else {
			return false;
		}
	}
	
	public static ArrayList<RolesLg> getSolos(GameLg gm) {
		ArrayList<RolesLg> others = new ArrayList<RolesLg>();
		
		for (RolesLg role:gm.dispoRoles) {
			if (role.getCampOfRole().equals(Camp.Other) || role.getCampOfRole().equals(Camp.Love)) {
				others.add(role);
			}
		}
		
		return others;
	}
	
	public static ArrayList<RolesLg> getVillager(GameLg gm) {
		ArrayList<RolesLg> villagers = new ArrayList<RolesLg>();
		
		for (RolesLg role:gm.dispoRoles) {
			if (role.getCampOfRole().equals(Camp.Villager) ) {
				villagers.add(role);
			}
		}
		
		return villagers;
	}
	
	public static ArrayList<RolesLg> getWolf(GameLg gm) {
		ArrayList<RolesLg> wolfs = new ArrayList<RolesLg>();
		
		for (RolesLg role:gm.dispoRoles) {
			if (role.getCampOfRole().equals(Camp.Wolf) ) {
				wolfs.add(role);
			}
		}
		
		return wolfs;
	}
	
	public static ArrayList<ItemStack> getItemOfCamp(GameLg game, Camp camp) {
		
		ArrayList<ItemStack> items = new ArrayList<ItemStack>();
		
		
		for (RolesLg role:game.dispoRoles) {
		
			if (role.getCampOfRole().equals(camp) || (camp.equals(Camp.Other) && (!role.getCampOfRole().equals(Camp.Wolf) && !role.getCampOfRole().equals(Camp.Villager)))) {
			
				ArrayList<String> lore = new ArrayList<String>();
				lore.add(String.valueOf((GameLgUtil.getHowManyRole(game, role)) ));
				lore.add("Clic gauche: AJOUTER || Clic droit: RETIRER");
				
			
				ItemStack item = role.item;
				ItemUtil.setLore(item, lore);
				items.add(item);
				
			}
			
		}
		if (items.size() == 0){
			System.out.println("items is null in getItemOfCamp in RoleUtil");
		}
		return items;
		
	}
	
	public static ArrayList<RolesLg> getRoleofCamp(ArrayList<RolesLg> roles, Camp camp, String spec) {
		
		ArrayList<RolesLg> roleList = new ArrayList<RolesLg>();
		
		if (roles.size() == 0) {
			System.out.println("No role in List roles at getRoleofCamp at RoleUtil "+ spec );
		}
		

		for (RolesLg role: roles) {
			

			if (role.getCampOfRole().equals(camp)) {
				
				roleList.add(role);
				
			}
		}
		
		
		
		return roleList;
		
	}
 	
	
}
