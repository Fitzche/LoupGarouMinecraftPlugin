package fr.fitzche.lgmore.Util;

import java.util.ArrayList;
import fr.fitzche.lgmore.RolesLg.Camp;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import fr.fitzche.lgmore.GameLg;
import fr.fitzche.lgmore.GameStatut;
import fr.fitzche.lgmore.Main;
import fr.fitzche.lgmore.PlayerData;
import fr.fitzche.lgmore.Love.Team;
import fr.fitzche.lgmore.RolesLg.INFECT_PERE_DES_LOUPS;
import fr.fitzche.lgmore.RolesLg.RolesLg;
import fr.fitzche.lgmore.RolesLg.SORCIERE;

public class GameLgUtil {
	public static GameLg searchGame(String name) {
		for (GameLg g: Main.games) {
			if (g.name.equals(name)) {
				System.out.println("correspondance");
				return g;
			}
		}
		System.out.println("la game n'existe pas");
		return null;
	}
	
	public static void tpAl(PlayerData player) {
		player.player.addPotionEffect(PotionUtil.INVINCIBILITY);
		Location loc = new Location(Main.server.getWorld("world"), MathUtil.generateAlInt(0, 500), MathUtil.generateAlInt(100, 150), MathUtil.generateAlInt(0, 500));
		player.player.teleport(loc);
	}
	public static void tpAl(Player player) {
		player.addPotionEffect(PotionUtil.INVINCIBILITY);
		Location loc = new Location(Main.server.getWorld("world"), MathUtil.generateAlInt(0, 500), MathUtil.generateAlInt(100, 150), MathUtil.generateAlInt(0, 500));
		player.teleport(loc);
	}
	
	
	
	public static GameLg getGameOfPlayer(Player player, String location) {
		for (GameLg game :Main.games) {
			for (PlayerData ply:game.playerAlive) {
				if (player.equals(ply.player)) {
					return game;
				}
			}
		}
		System.out.println("partie du joueur " + player.getName() + " non trouvée at "+ location);
		return null;
	}
	
	
	public static void broadcoastTargeted(ArrayList<PlayerData> players, String message) {
		for (PlayerData player:players) {
			player.sendMessage(message);
		}
	}
	
	public static void broadcoastTargeted(GameLg game, Camp camp, String message) {
		ArrayList<PlayerData> players = new ArrayList<PlayerData>();
		
		if (camp.equals(Camp.Wolf)) {
			players = game.getRealWolfAlive();
		} else if (camp.equals(Camp.Villager)) {
			players = game.getFalseVillagersAlive();
		} else if (camp.equals(Camp.Other)) {
			players = game.getRealWolfAlive();
		} else if (camp.equals(Camp.Love)) {
			players = game.getRealLoveAlive();
		}
		
		
		for (PlayerData player: players) {
			player.sendMessage(message);
		}
		
		
		
		
		
	}
	
	public static GameLg getGameOfPlayer(Player player, String location, boolean x) {
		for (GameLg game :Main.games) {
			for (PlayerData ply:game.playerAlive) {
				if (player.equals(ply.player)) {
					return game;
				}
			}
		}
		return null;
	}
	
	public static GameLg getGameOfPlayer(PlayerData player, String location) {
		for (GameLg game :Main.games) {
			for (PlayerData ply:game.playerAlive) {
				if (player.player.equals(ply.player)) {
					return game;
				}
			}
		}
		System.out.println("partie du joueur " + player.player.getName() + " non trouvée at "+location);
		return null;
	}
	
	public static int getHowManyRole(GameLg gm, RolesLg rol) {
		int x = 0;
		
		for (RolesLg role:gm.roles) {
			if (role.getName().equals(rol.getName())) {
				x++;
			}
		}
		return x;
	}
	
	public static void isEnded(GameLg game) {
		Team isWinner = null;
		for (Team team:game.teams) {
			if (team.inGame) {
				if (isWinner == null) {
					isWinner = team;
				} else {
					return;
				}
			}
		}
		if (isWinner != null) {
			isWinner.win();
		}
		
	}
	
	public static void askRes(GameLg game, PlayerData ply, PlayerData killer) {
		//System.out.println("hg.1.1");
		if (RoleUtilLg.isRoleIn(game, RolesLg.INFECT_PERE_DES_LOUPS) || RoleUtilLg.isRoleIn(game, RolesLg.SORCIERE)) {
			//System.out.println("hg.1.2");
			ArrayList<PlayerData> plys = RoleUtilLg.getPlayersWithRole(game, RolesLg.INFECT_PERE_DES_LOUPS);
			//System.out.println("hg.1.3");
			for (PlayerData ply1: plys) {
				//System.out.println("hg.1.mixte.4.1");
				
				INFECT_PERE_DES_LOUPS role = (INFECT_PERE_DES_LOUPS) ply1.roleIn;
				//System.out.println("hg.1.mixte.4.2");
				role.infect(ply, killer);
				//System.out.println("hg.1.mixte.4.3");
				System.out.println("demande de résurrection 1");

			}
			ArrayList<PlayerData> plys1 = RoleUtilLg.getPlayersWithRole(game, RolesLg.SORCIERE);
			for (PlayerData ply1: plys1) {
				SORCIERE role = (SORCIERE) ply1.roleIn;
				role.lifePotion(ply);
				System.out.println("demande de résurrection 2");
			}
		}
		System.out.println("demande de résurrection");
	}
	//TODO: corriger searchGame car game pas ajoutée au games de Main quand créée
}
