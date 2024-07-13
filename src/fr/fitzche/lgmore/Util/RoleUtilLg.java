package fr.fitzche.lgmore.Util;

import java.util.ArrayList;

import javax.management.relation.Role;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import fr.fitzche.lgmore.GameLg;
import fr.fitzche.lgmore.Main;
import fr.fitzche.lgmore.PlayerData;
import fr.fitzche.lgmore.RolesLg.ALLUMEUR;
import fr.fitzche.lgmore.RolesLg.ANCIEN;
import fr.fitzche.lgmore.RolesLg.ASSASSIN;
import fr.fitzche.lgmore.RolesLg.BIENFAITEUR;
import fr.fitzche.lgmore.RolesLg.CHASSEUR;
import fr.fitzche.lgmore.RolesLg.CORBEAU;
import fr.fitzche.lgmore.RolesLg.CUPIDON;
import fr.fitzche.lgmore.RolesLg.Camp;
import fr.fitzche.lgmore.RolesLg.DISCIPLE;
import fr.fitzche.lgmore.RolesLg.ENFANT_SAUVAGE;
import fr.fitzche.lgmore.RolesLg.IDIOT_DU_VILLAGE;
import fr.fitzche.lgmore.RolesLg.INFECT_PERE_DES_LOUPS;
import fr.fitzche.lgmore.RolesLg.INTERPRETE;
import fr.fitzche.lgmore.RolesLg.LOUP_METAMORPHE;
import fr.fitzche.lgmore.RolesLg.LOUP_MYSTIQUE;
import fr.fitzche.lgmore.RolesLg.MONTREUR;
import fr.fitzche.lgmore.RolesLg.PERFIDE;
import fr.fitzche.lgmore.RolesLg.PETITE_FILLE;
import fr.fitzche.lgmore.RolesLg.PYROMANE;
import fr.fitzche.lgmore.RolesLg.RENARD;
import fr.fitzche.lgmore.RolesLg.RoleInstance;
import fr.fitzche.lgmore.RolesLg.RolesLg;
import fr.fitzche.lgmore.RolesLg.SAGE;
import fr.fitzche.lgmore.RolesLg.SALVATEUR;
import fr.fitzche.lgmore.RolesLg.SIMPLE_VILLAGER;
import fr.fitzche.lgmore.RolesLg.SIMPLE_WOLF;
import fr.fitzche.lgmore.RolesLg.SOEUR;
import fr.fitzche.lgmore.RolesLg.SORCIERE;
import fr.fitzche.lgmore.RolesLg.VOLEUR;
import fr.fitzche.lgmore.RolesLg.VOYANTE;

public class RoleUtilLg {
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
		if (player.role.equals(RolesLg.SIMPLE_VILLAGER)) {
			SIMPLE_VILLAGER role = new SIMPLE_VILLAGER(player);
			player.roleIn = role;
			return role;
		} else if (player.role.equals(RolesLg.SIMPLE_WOLF)) {
			SIMPLE_WOLF role = new SIMPLE_WOLF(player);
			player.roleIn = role;
			return role;
		} else if (player.role.equals(RolesLg.INFECT_PERE_DES_LOUPS)) {
			INFECT_PERE_DES_LOUPS role = new INFECT_PERE_DES_LOUPS(player);
			player.roleIn = role;
			return role;
		}else if (player.role.equals(RolesLg.VOYANTE)) {
			VOYANTE role = new VOYANTE(player);
			player.roleIn = role;
			return role;
		}else if (player.role.equals(RolesLg.SORCIERE)){
			SORCIERE role = new SORCIERE(player);
			player.roleIn = role;
			return role;
		} else if (player.role.equals(RolesLg.CUPIDON)) {
			CUPIDON role = new CUPIDON(player);
			player.roleIn = role;
			return role;
		}else if (player.role.equals(RolesLg.MONTREUR)) {
			MONTREUR role = new MONTREUR(player);
			player.roleIn = role;
			return role;
		}else if (player.role.equals(RolesLg.PETITE_FILLE)) {
			PETITE_FILLE role = new PETITE_FILLE(player);
			player.roleIn = role;
			return role;
		}else if (player.role.equals(RolesLg.CHASSEUR)) {
			CHASSEUR role = new CHASSEUR(player);
			player.roleIn = role;
			return role;
		}else if (player.role.equals(RolesLg.SALVATEUR)) {
			SALVATEUR role = new SALVATEUR(player);
			player.roleIn = role;
			return role;
		}else if (player.role.equals(RolesLg.CORBEAU)) {
			CORBEAU role = new CORBEAU(player);
			player.roleIn = role;
			return role;
		}else if (player.role.equals(RolesLg.SOEUR)) {
			SOEUR role = new SOEUR(player, null);
			player.roleIn = role;
			return role;
		}else if (player.role.equals(RolesLg.BIENFAITEUR)) {
			BIENFAITEUR role = new BIENFAITEUR(player);
			player.roleIn = role;
			return role;
		}else if (player.role.equals(RolesLg.IDIOT_DU_VILLAGE)) {
			IDIOT_DU_VILLAGE role = new IDIOT_DU_VILLAGE(player);
			player.roleIn = role;
			return role;
		}else if (player.role.equals(RolesLg.LOUP_MYSTIQUE)) {
			LOUP_MYSTIQUE role = new LOUP_MYSTIQUE(player);
			player.roleIn = role;
			return role;
		}else if (player.role.equals(RolesLg.VOLEUR)) {
			VOLEUR role = new VOLEUR(player);
			player.roleIn = role;
			return role;
		}else if (player.role.equals(RolesLg.RENARD)) {
			RENARD role = new RENARD(player);
			player.roleIn = role;
			return role;
		}else if (player.role.equals(RolesLg.ANCIEN)) {
			ANCIEN role = new ANCIEN(player);
			player.roleIn = role;
			return role;
		}else if (player.role.equals(RolesLg.ENFANT_SAUVAGE)) {
			ENFANT_SAUVAGE role = new ENFANT_SAUVAGE(player);
			player.roleIn = role;
			return role;
		}else if (player.role.equals(RolesLg.INTERPRETE)) {

			INTERPRETE role = new INTERPRETE(player);
			player.roleIn = role;
			return role;
		}else if (player.role.equals(RolesLg.SAGE)) {
			SAGE role = new SAGE(player);
			player.roleIn = role;
			return role;
		}else if (player.role.equals(RolesLg.DISCIPLE)) {
			DISCIPLE role = new DISCIPLE(player);
			player.roleIn = role;
			return role;
		}else if (player.role.equals(RolesLg.LOUP_METAMORPHE)) {
			LOUP_METAMORPHE role = new LOUP_METAMORPHE(player);
			player.roleIn = role;
			return role;
		}else if (player.role.equals(RolesLg.PYROMANE)) {
			PYROMANE role = new PYROMANE(player);
			player.roleIn = role;
			return role;
		}else if (player.role.equals(RolesLg.PERFIDE)) {
			PERFIDE role = new PERFIDE(player);
			player.roleIn = role;
			return role;
		}else if (player.role.equals(RolesLg.ASSASSIN)) {
			ASSASSIN role = new ASSASSIN(player);
			player.roleIn = role;
			return role;
		}else if (player.role.equals(RolesLg.ALLUMEUR)) {
			ALLUMEUR role = new ALLUMEUR(player);
			player.roleIn = role;
			return role;
		}else{
			return null;
		}
	}
	public static RoleInstance createRole(RolesLg role, Player player) {
		PlayerData temp = new PlayerData(player);
		temp.role = role;
	
		return createRoleOfPlayerRoles(temp);
	}
	
	public static RolesLg RoleofString(String str) {
		for (RolesLg role: RoleUtilLg.existingRoles) {
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
		
			if (role.getCampOfRole().equals(camp) || (camp.equals(Camp.Other) && role.getCampOfRole().equals(Camp.Love))) {
			
				ArrayList<String> lore = new ArrayList<String>();
				lore.add(String.valueOf((GameLgUtil.getHowManyRole(game, role)) ));
				lore.add("Clic gauche: AJOUTER || Clic droit: RETIRER");
				
			
				ItemStack item = role.item;
				ItemUtil.setLore(item, lore);
				items.add(item);
				
			}
			
		}
		if (items.size() == 0){
			System.out.println("items is null in getItemOfCamp in RoleUtilLg");
		}
		return items;
		
	}
	
	public static ArrayList<RolesLg> getRoleofCamp(ArrayList<RolesLg> roles, Camp camp, String spec) {
		
		ArrayList<RolesLg> roleList = new ArrayList<RolesLg>();
		
		if (roles.size() == 0) {
			System.out.println("No role in List roles at getRoleofCamp at RoleUtilLg "+ spec );
		}
		

		for (RolesLg role: roles) {
			

			if (role.getCampOfRole().equals(camp)) {
				
				roleList.add(role);
				
			}
		}
		
		
		
		return roleList;
		
	}
 	
	
}
