package fr.fitzche.lgmore;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Scoreboard;


import fr.fitzche.lgmore.Love.Team;
import fr.fitzche.lgmore.RolesLg.CHASSEUR;
import fr.fitzche.lgmore.RolesLg.CORBEAU;
import fr.fitzche.lgmore.RolesLg.Camp;
import fr.fitzche.lgmore.RolesLg.PETITE_FILLE;
import fr.fitzche.lgmore.RolesLg.RoleInstance;
import fr.fitzche.lgmore.RolesLg.RolesLg;
import fr.fitzche.lgmore.RolesLg.VOYANTE;
import fr.fitzche.lgmore.Util.GameLgUtil;
import fr.fitzche.lgmore.Util.PlayerUtil;
import fr.fitzche.lgmore.Util.RoleUtilLg;
import fr.fitzche.lgmore.scoreboard.ScoreboardLg;
import net.minecraft.server.v1_8_R1.Material;

public class GameLg implements Listener{
	public GameStatut statut;
	public Timer timer;
	public String name;
	
	
	
	public Player hunter = null;
	public CHASSEUR Hunter = null;
	
	public ArrayList<PlayerData> playerAlive;
	public ArrayList<PlayerData> RealwolfAlive;
	public ArrayList<PlayerData> RealvillagerAlive;
	public ArrayList<PlayerData> soloAlive;
	public ArrayList<PlayerData> FalseVillagerAlive;
	public ArrayList<PlayerData> FalseWolfAlive;
	
	public ArrayList<Team> teams = new ArrayList<Team>();
	public ArrayList<RolesLg> roles;
	public ScoreboardLg board;
	public boolean inGame;
	public int groupe;
	public ArrayList<PlayerData> players;
	public ArrayList<RoleInstance> rolesIn;
	public boolean isInVote;
	public boolean isInDisc;
	
	public int eventTrouple;
	
	public Team lgTeam;
	public Team villTeam;
	
	
	
	public ArrayList<PlayerData> toAdd = new ArrayList<PlayerData>();
	
	public ArrayList<RolesLg> dispoRoles = new ArrayList<RolesLg>();
	
	public boolean stopped;

	
	public GameLg(String name) {
		this.stopped = false;
		
		eventTrouple = 0;
		
		Main.server.getPluginManager().registerEvents(this, Main.plug);
		
		this.isInDisc = false;
		rolesIn = new ArrayList<RoleInstance>();
		this.name = name;
		this.inGame = false;
		this.timer = new Timer();
		this.statut = GameStatut.NOT_STARTED;
		playerAlive = new ArrayList<PlayerData>();
		RealvillagerAlive = new ArrayList<PlayerData>();
		RealwolfAlive = new ArrayList<PlayerData>();
		soloAlive = new ArrayList<PlayerData>();
		FalseVillagerAlive = new ArrayList<PlayerData>();
		FalseWolfAlive = new ArrayList<PlayerData>();
		roles = new ArrayList<RolesLg>();
		groupe = 0;
		players = new ArrayList<PlayerData>();
		
		this.board = new ScoreboardLg(this);

		this.dispoRoles.addAll(RoleUtilLg.existingRoles);
		
		
		}
	
	public int getGroupe() {
		return groupe;
	}
	
	
	
	public void applyInvicibility(PlayerData joueur) {
		joueur.player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 200, 10, false, false));

	}
	
	
	public PlayerData getPlayer(String name) {
		for (PlayerData ply:this.playerAlive ) {
			if (ply.Name.equals(name)) {
				return ply;
			}
		}
		//System.out.println("joueur non trouvé dans la partie");
		return null;
	}
	
	public ArrayList<RolesLg> getRoles() {
		return this.roles;
	}
	
	@Deprecated
	public void setChat() {
		this.isInDisc = true;
		GameLgUtil.broadcoastTargeted(this, Camp.Wolf, ChatColor.RED + "Vous pouvez à présent discuter sur le chat des loups-garous");
		Bukkit.getScheduler().runTaskLater(Main.plug, new BukkitRunnable() {

			@Override
			public void run() {
				isInDisc = false;

				
			}
			
		}, 800);
	}
	
	public void attributeRoleToAll() {
		if (this.roles.size() == this.playerAlive.size()) {
			Random random = new Random(); 
			ArrayList<RolesLg> conserv = new ArrayList<RolesLg>();
			ArrayList<RolesLg> exe = new ArrayList<RolesLg>();
			exe = this.getRoles();
			for (PlayerData player : this.playerAlive) {
				
				int number = random.nextInt(exe.size());
				RolesLg role = exe.get(number);
				player.applyLgRole(role);
				
				
				
				conserv.add(role);
				exe.remove(role);
				
			}
			
			this.roles = conserv;
		}
	}
	
	
	
	public String getRolesAlive() {
		StringBuilder builder = new StringBuilder();
		if (roles == null) {
			return ("Aucun Role n'a été ajouté, ou n'est encore en vie");
		}
		builder.append("Voici les rôles présents: "+"\n");
		for (RolesLg role: roles) {
			builder.append(role.getCampOfRole().getColor() + role.getName() + "\n");
		}
		
		return builder.toString();
		
	}
	
	public String ListPlayer() {
		StringBuilder string = new StringBuilder();
		for (PlayerData player: playerAlive) {
			string.append(player.Name + "\n");
		}
		return string.toString();
	}
	
	public void playEpisode() {
		////System.out.println("eee1");
		if (this.rolesIn == null) {
		//	//System.out.println("eeeX");
		}
		//System.out.println(this.rolesIn.get(0));
		for (RoleInstance roles: this.rolesIn) {
			////System.out.println("eee2");

			roles.episodeEffect();
			////System.out.println("eee3");

			roles.setEpisodeTrue();
			System.out.println("TEMP/// playEpisode of GameLg eee4");

		}
		
		this.setChat();
		this.startVote();
	}
	
	public void addPlayer(String name) {
		//System.out.println("le joueur va etre ajouté");
		Player playerToAdd = PlayerUtil.getPlayer(name);
		if (playerToAdd != null) {
			//System.out.println("joueur " + name + "ajoutée à " + this.name);
			PlayerData player = new PlayerData(playerToAdd);
			this.playerAlive.add(player);
			this.players.add(player);
			
			
			////System.out.println("fait");
			
		} else {
			System.out.println("joueur " + name + " non existant ou connecté");
		}
	}
	
	public ArrayList<PlayerData> getPlayerAlive() {
		return this.playerAlive;
	}
	public ArrayList<PlayerData> getRealVillagerAlive() {
		ArrayList<PlayerData> returneds = new ArrayList<PlayerData>();
		
		for (PlayerData player: playerAlive) {
			if (player.camp.equals(Camp.Villager)) {
				returneds.add(player);
			}
		}
		return returneds;
	}
	
	
	
	public ArrayList<PlayerData> getRealWolfAlive() {
		ArrayList<PlayerData> returneds = new ArrayList<PlayerData>();
		
		for (PlayerData player: playerAlive) {
			if (player.camp.equals(Camp.Wolf)||player.role.getCampOfRole().equals(Camp.Wolf)) {
				returneds.add(player);
			}
		}
		return returneds;
	}
	
	public ArrayList<PlayerData> getRealOthersAlive() {
		ArrayList<PlayerData> returneds = new ArrayList<PlayerData>();
		
		for (PlayerData player: playerAlive) {
			if (player.camp.equals(Camp.Other)) {
				returneds.add(player);
			}
		}
		return returneds;
	}
	
	public ArrayList<PlayerData> getRealLoveAlive() {
		ArrayList<PlayerData> returneds = new ArrayList<PlayerData>();
		
		for (PlayerData player: playerAlive) {
			if (player.camp.equals(Camp.Love)) {
				returneds.add(player);
			}
		}
		return returneds;
	}
	
	public ArrayList<PlayerData> getFalseWolfAlive() {
		ArrayList<PlayerData> returneds = new ArrayList<PlayerData>();
		
		for (PlayerData player: playerAlive) {
			if (player.role.getCampOfRole().equals(Camp.Wolf)) {
				returneds.add(player);
			}
		}
		return returneds;
	}
	
	public PlayerData getPlayerDataWithName(String name) {
		for (PlayerData player: playerAlive) {
			if (player.Name.equals(name)) {
				return player;
			}
		}
		//System.out.println("joueur non intégré dans la partie");
		return null;
	}
	
	public void removePlayer(PlayerData ply, String spec) {
		playerAlive.remove(ply);
		players.remove(ply);
		System.out.println(ply.Name +" removed at "+ spec);
	}
	
	@Deprecated
	public void removeDiedPlayer(PlayerData ply) {
		if (ply.role.equals(RolesLg.CHASSEUR)) {
			CHASSEUR hunter = (CHASSEUR) ply.roleIn;
			this.Hunter = hunter;
			this.hunter = ply.player;
			ply.sendMessage(ChatColor.GOLD+"Vous avez 25 secondes pour tirer sur un joueur de votre choix avec la commande lg tirer, celui perdra 5 coeurs de manière non permanente, ainsi que sa force s'il est loup");

			Bukkit.getScheduler().runTaskLater(Main.plug, new BukkitRunnable() {

				@Override
				public void run() {
					GameLg.this.hunter.sendMessage("Trop tard...");
					GameLg.this.hunter = null;
					playerAlive.remove(ply);
					roles.remove(ply.role);
					rolesIn.remove(ply.roleIn);
					ply.inLife = false;
				}
				
			}, 500);
		} 
		playerAlive.remove(ply);
		roles.remove(ply.role);
		rolesIn.remove(ply.roleIn);
		ply.inLife = false;
		
		
	}

	@Deprecated
	public void startVote() {
		for (PlayerData player: this.playerAlive) {
			player.askVoted();
			
		}
		this.isInVote = true;
		Bukkit.getScheduler().runTaskLater(Main.plug, new BukkitRunnable() {

			@Override
			public void run() {
				isInVote = false;
				
				PlayerData mostVoted = null;
				boolean equal = false;
				for (PlayerData ply:playerAlive) {
					if (mostVoted == null||ply.vote > mostVoted.vote) {
						mostVoted = ply;
						equal = false;
					}else if (mostVoted.vote == ply.vote) {
						equal = true;
					}
					
				}
				
				if (mostVoted.vote > 2 && !equal) {
					mostVoted.player.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 300, 1));
					mostVoted.player.setMaxHealth(mostVoted.player.getMaxHealth() - 4);
					Bukkit.broadcastMessage(ChatColor.GOLD + "Le joueur " + ChatColor.DARK_AQUA + mostVoted.Name +ChatColor.GOLD+ "a été le plus voté" );
					for (PlayerData player:playerAlive) {
						
						
						if (player.voted.Name.equals(mostVoted.Name)) {
							player.canVoted.remove(mostVoted);
							
							if (player.role.equals(RolesLg.CORBEAU)) {
								Bukkit.broadcastMessage(ChatColor.BLACK+"Le corbeau a voté avec le village");
							}
						}
					}
					
					
				} else {
					Bukkit.broadcastMessage(ChatColor.GOLD +"Aucun joueur n'a été voté plus de 2 fois, ou il y a une égalité");
				}
				for (PlayerData player:playerAlive) {
					if ( player.voted.camp != Camp.Villager) {
						if (player.role.equals(RolesLg.CORBEAU)) {
							CORBEAU corbeau = (CORBEAU) player.roleIn;
							corbeau.addGoodVoted();
						} else {
							player.player.setMaxHealth(player.player.getMaxHealth()-2);
						
						} 
					}
				}
			}
			
		}, 1200);
	}
	
	public ArrayList<PlayerData> getFalseVillagersAlive() {
		ArrayList<PlayerData> returneds = new ArrayList<PlayerData>();
		
		for (PlayerData player: playerAlive) {
			if (player.role.getCampOfRole().equals(Camp.Villager)) {
				returneds.add(player);
			}
		}
		return returneds;
	}
	
	public ArrayList<PlayerData> getFalseOthersAlive() {
		ArrayList<PlayerData> returneds = new ArrayList<PlayerData>();
		
		for (PlayerData player: playerAlive) {
			if (player.role.getCampOfRole().equals(Camp.Other)) {
				returneds.add(player);
			}
		}
		return returneds;
	}
	
	public int getNumberOfWolf() {
		int returned = 0;
		ArrayList<PlayerData> testeds = new ArrayList<PlayerData>();
		testeds = getRealWolfAlive();
		for (PlayerData player: testeds) {
			returned++;
		}
		return returned;
	}
	
	public int getNumberOfPlayer() {
		////System.out.println("marquage 3.1");
		int number = 0;
		for (PlayerData player: playerAlive) {
			number++;
		}
		return number;
	}
	
	public void announceDeath(PlayerData player1) {
		
		for (Team team:teams) {
			team.onPlayerDeath(player1);
		}
		String moreInfo = "";
		if (player1.infected) {
			moreInfo = moreInfo+ (" (loup garou) ");
		} 
		if (player1.inLove) {
			moreInfo = moreInfo+ (" (en couple) ");
		} 

		GameLg gm1 =GameLgUtil.getGameOfPlayer(player1, " at 152 Main");
		if (gm1.name != this.name) {
			return;
		}

		Main.server.broadcastMessage(ChatColor.DARK_BLUE +"___________________________" + "\n" +
							ChatColor.RED + player1.player.getName() + " est mort |"+ "\n"  +
								" il était "+ player1.camp.getColor() + 
								player1.getLgRole() + ChatColor.GOLD + moreInfo + "|" + "\n"+ChatColor.DARK_BLUE +"___________________________");

	}
	
	
	
	public int getNumberOfVillager() {
		int returned = 0;
		ArrayList<PlayerData> testeds = new ArrayList<PlayerData>();
		testeds = getRealVillagerAlive();
		for (PlayerData player: testeds) {
			returned++;
		}
		return returned;
	}
	
	public int getNumberOfOthers() {
		int returned = 0;
		ArrayList<PlayerData> testeds = new ArrayList<PlayerData>();
		testeds = getRealOthersAlive();
		for (PlayerData player: testeds) {
			returned++;
		}
		return returned;
	}
	
	@EventHandler
	public void onPlayerClick(InventoryClickEvent e) {
		
		
		
		Player sender = (Player) e.getWhoClicked();
		
		
		
		
		//CHECK FOR LITTLE GIRL
		if (GameLgUtil.getGameOfPlayer(sender, "at onPlayerClick at GameLg ", false) != null) {
			if (PlayerUtil.getDataOfPlayer((Player) e.getWhoClicked(), "in onPlayerClick in GameLg").role == RolesLg.PETITE_FILLE) {
				EntityEquipment armorC = ((Player) e.getWhoClicked()).getEquipment();
				ItemStack[] armor = armorC.getArmorContents();
				
				boolean isEmpty = true;
				for (ItemStack item: armor) {
					if (item.getType() != org.bukkit.Material.AIR  ) {
						isEmpty = false;
					} else {
						System.out.println(item.getType().name());
					}
				}
				
				
				PETITE_FILLE little = (PETITE_FILLE) PlayerUtil.getDataOfPlayer((Player) e.getWhoClicked(), "in onPlayerClick in GameLg").roleIn;
				
				if (isEmpty) {
					System.out.println("empty at GameLg onPlayerClick");
					if (!little.powerUsed) {
						little.startSpecialEvent();
						System.out.println("started at GameLg onPlayerClick");

					} 
					
				} else {
					System.out.println("not empty at GameLg onPlayerClick");

					sender.removePotionEffect(PotionEffectType.INVISIBILITY);
				
				}
		
		
			
			
			
			
		}
		}
		
		//CHECK FOR PERFIDE
		if (GameLgUtil.getGameOfPlayer(sender, "at onPlayerClick at GameLg ", false) != null) {
			if (PlayerUtil.getDataOfPlayer((Player) e.getWhoClicked(), "in onPlayerClick in GameLg").role == RolesLg.PETITE_FILLE) {
				EntityEquipment armorC = ((Player) e.getWhoClicked()).getEquipment();
				ItemStack[] armor = armorC.getArmorContents();
				
				boolean isEmpty = true;
				for (ItemStack item: armor) {
					if (item.getType() != org.bukkit.Material.AIR  ) {
						isEmpty = false;
					} else {
						System.out.println(item.getType().name());
					}
				}
				
				
				PETITE_FILLE little = (PETITE_FILLE) PlayerUtil.getDataOfPlayer((Player) e.getWhoClicked(), "in onPlayerClick in GameLg").roleIn;
				
				if (isEmpty) {
					System.out.println("empty at GameLg onPlayerClick");
					if (!little.powerUsed) {
						little.startSpecialEvent();
						System.out.println("started at GameLg onPlayerClick");

					} 
					
				} else {
					System.out.println("not empty at GameLg onPlayerClick");

					sender.removePotionEffect(PotionEffectType.INVISIBILITY);
				
				}
		
		
			
			
			
			
		}
		}
		
		
	}
	
	
}
