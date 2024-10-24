package fr.fitzche.lgmore;

import java.util.ArrayList;
import java.util.HashMap;
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
import fr.fitzche.lgmore.RolesLg.Checkers.TimeresCheck;
import fr.fitzche.lgmore.RolesLg.Checkers.VoteChecker;
import fr.fitzche.lgmore.Util.GameLgUtil;
import fr.fitzche.lgmore.Util.LocationUtil;
import fr.fitzche.lgmore.Util.MathUtil;
import fr.fitzche.lgmore.Util.PlayerUtil;
import fr.fitzche.lgmore.Util.RoleUtilLg;
import fr.fitzche.lgmore.commands.FutureAction;
import fr.fitzche.lgmore.minecraft.PlayerDataLeft;
import fr.fitzche.lgmore.minecraft.ResCheck;
import fr.fitzche.lgmore.scoreboard.ScoreboardLg;
import fr.fitzche.lgmore.scoreboard.Inventory.CompoDisplay;
import fr.fitzche.lgmore.scoreboard.Inventory.ConfigDisplay;
import fr.fitzche.lgmore.scoreboard.Inventory.EventDisplay;
import fr.fitzche.lgmore.scoreboard.Inventory.PlayerDisplay;


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
	public ArrayList<FutureAction> futuresActions = new ArrayList<FutureAction>();
	
	public HashMap<String, Integer> probasEvents = new HashMap<String, Integer>();
	
	public Team lgTeam;
	public Team villTeam;
	
	
	
	HashMap<String, PlayerDataLeft> playersLeft = new HashMap<String, PlayerDataLeft>();
	
	public ArrayList<RolesLg> dispoRoles = new ArrayList<RolesLg>();
	
	public boolean stopped;
	
	public ArrayList<ResCheck> resCheckers = new ArrayList<ResCheck>();
	public CompoDisplay compo = new CompoDisplay(this);
	public EventDisplay events = new EventDisplay(this);
	public PlayerDisplay plys;
	
	public ConfigDisplay config = new ConfigDisplay(this);
	
	public boolean isMeetup = false;

	
	public GameLg(String name) {
		
		this.stopped = false;
		Main.server.getPluginManager().registerEvents(events, Main.plug);
		
		for (String str: Main.eventsNames) {
			this.probasEvents.put(str, 0);
		}
		this.resCheckers.add(new TimeresCheck(this));
		this.resCheckers.add(new VoteChecker(this));
		
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
		plys = new PlayerDisplay(this);
		Main.server.getPluginManager().registerEvents(plys, Main.plug);
		}
	
	public int getGroupe() {
		return groupe;
	}
	
	
	
	public void applyInvicibility(PlayerData joueur) {
		joueur.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 200, 10, false, false));

	}
	
	
	public void askRunFuturesActions() {
		for (FutureAction fut: this.futuresActions) {
			fut.timeBeforeRun --;
			if (fut.timeBeforeRun < 1) {
				fut.action.run();
			}
		}
	}
	public PlayerData getPlayer(String name) {
		for (PlayerData ply:this.playerAlive ) {
			if (ply.Name.equals(name)) {
				return ply;
			}
		}
		
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
			
			
			ArrayList<RolesLg> exe = this.getRoles();
			this.roles = new ArrayList<RolesLg>();
			for (PlayerData player : this.playerAlive) {
				
				int number = MathUtil.generateAlInt(0, exe.size()-1);
				RolesLg role = exe.get(number);
				player.applyLgRole(role);
				
				
				
				this.roles.add(role);
				exe.remove(role);
				
			}
			
			
		}
		
		
		for (PlayerData player: this.getPlayerAlive()) {
			
			player.roleIn = RoleUtilLg.createRoleOfPlayerRoles(player);
			player.sendMessage("Vous êtes "+player.roleIn.getName());
			player.sendMessage(player.roleIn.getDescription());
			player.sendMessage(ChatColor.GOLD+"Aura: "+ player.role.aura.getName());
			this.rolesIn.add(player.roleIn);
			player.roleIn.giveRoleEffectAndItem(player);
		}
		
		this.lgTeam = new Team("Loups Garou", Camp.Wolf, this, this.getRealWolfAlive(), null, "at wolf team creating at == 1200", null, null, true, false, false, false);
		this.villTeam = new Team("Village", Camp.Villager, this, this.getRealVillagerAlive(), null, "at village team creating at == 1200", null, null, true, false, false, false);
		this.teams.add(this.lgTeam);
		this.teams.add(this.villTeam);
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
	
	@SuppressWarnings("deprecation")
	public void playEpisode() {
		System.out.println("Play new Episode");
		for (RoleInstance roles: this.rolesIn) {
			roles.episodeEffect();
			roles.setEpisodeTrue();	
		}
		
		this.setChat();
		this.startVote();
		this.decideTimeEvent();
		this.setEpisodeTime();
		
	}
	
	@Deprecated
	public void setEpisodeTime() {
		Bukkit.getScheduler().runTaskLaterAsynchronously(Main.plug, new BukkitRunnable() {
			@Override
			public void run() {
				Main.server.getWorld("world").setTime(13000);
			}
		}, 6000);
		Bukkit.getScheduler().runTaskLaterAsynchronously(Main.plug, new BukkitRunnable() {
			@Override
			public void run() {
				Main.server.getWorld("world").setTime(1000);			
			}		
		}, 12000);
		
		Main.server.getWorld("world").setTime(1000);
		Bukkit.getScheduler().runTaskLaterAsynchronously(Main.plug, new BukkitRunnable() {
			@Override
			public void run() {
				Main.server.getWorld("world").setTime(13000);	
			}
		}, 18000);
	}
	
	public void addPlayer(String name) {
		Player playerToAdd = PlayerUtil.getPlayer(name);
		if (playerToAdd != null) {
			PlayerData player = new PlayerData(playerToAdd);
			this.playerAlive.add(player);
			this.players.add(player);
			
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
			if (player.role.getCampOfRole().equals(Camp.Wolf) || player.camp.equals(Camp.Wolf)) {
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
			
			ply.sendMessage(ChatColor.GOLD+"Vous avez 25 secondes pour tirer sur un joueur de votre choix avec la commande /lg tirer [nomDuJoueur], celui perdra 3 coeurs de manière non permanente, ainsi que sa force s'il est loup");
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
		}  else {
			playerAlive.remove(ply);
			roles.remove(ply.role);
			rolesIn.remove(ply.roleIn);
			ply.inLife = false;
		}
	
		
		
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
					if (MathUtil.pourcentage(probasEvents.get("Erreur aux Urnes"))) {
						Bukkit.broadcastMessage(ChatColor.GOLD+"Une erreur s'est produite aux urnes...");
						mostVoted = getPlayerAlive().get(MathUtil.generateAlInt(0, getPlayerAlive().size()  -1));
					}
					mostVoted.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 300, 1));
					mostVoted.changeHealth(-2);
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
					if (player.voted == null) {
						return;
					}
					if ( player.role.equals(RolesLg.CORBEAU)) {
						if (player.voted.camp != Camp.Villager) {
							CORBEAU corbeau = (CORBEAU) player.roleIn;
							corbeau.addGoodVoted();
						} else {
							player.changeHealth(-2);
						
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

	@Deprecated
	public void decideTimeEvent() {
		int x = MathUtil.generateAlInt(0, 1200);
		Bukkit.getScheduler().runTaskLaterAsynchronously(Main.plug,new BukkitRunnable() {

			@Override
			public void run() {
				if (MathUtil.pourcentage(probasEvents.get("Premonition"))) {
					PlayerData player = getPlayerAlive().get(MathUtil.generateAlInt(0, getPlayerAlive().size() - 1));
					int s= 0;
					int c = 0;
					for (PlayerData players:getPlayerAlive()) {
						
						if (LocationUtil.getDistanceBetween(player, players) < 21 ) {
							switch (players.aura) {
								case DANGEROUS:
									s += 2;
								case LUMINOUS:
									c += 1;
								case NEUTRAL:
									break;
								case OBSCUR:
									s+=1;
								case UNKNOW:
									break;
								default:
									break;
								
							}

						}


					}
					if (s > c) {
						player.sendMessage(ChatColor.DARK_RED + "Vous avez un présentiment negatif envers votre entourage");
					} else {
						player.sendMessage(ChatColor.DARK_GREEN + "Vous avez un présentiment positive envers votre entourage");

					}
				}
			}
			
		} , x*20);
	}
	
	public void announceDeath(PlayerData player1) {
		
		
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
		if (MathUtil.pourcentage(probasEvents.get("Brume"))) {
			return;
		}

		Main.server.broadcastMessage(ChatColor.DARK_BLUE +"___________________________" + "\n" +
							ChatColor.RED + player1.getName() + " est mort |"+ "\n"  +
								" il était "+ player1.camp.getColor() + 
								player1.getLgRole() + ChatColor.GOLD + moreInfo + "|" + "\n"+ChatColor.DARK_BLUE +"___________________________");
		for (Team team:teams) {
			team.onPlayerDeath(player1);
		}
	}
	
	public void announceDeath(PlayerData player1, RolesLg role) {
		
		
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
		if (MathUtil.pourcentage(probasEvents.get("Brume"))) {
			return;
		}

		Main.server.broadcastMessage(ChatColor.DARK_BLUE +"___________________________" + "\n" +
							ChatColor.RED + player1.getName() + " est mort |"+ "\n"  +
								" il était "+ role.getCampOfRole().getColor() + 
								role.getName() + ChatColor.GOLD + moreInfo + "|" + "\n"+ChatColor.DARK_BLUE +"___________________________");
		for (Team team:teams) {
			team.onPlayerDeath(player1);
		}
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
