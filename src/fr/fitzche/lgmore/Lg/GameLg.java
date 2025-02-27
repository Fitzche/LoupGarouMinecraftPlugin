package fr.fitzche.lgmore.Lg;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Scoreboard;

import fr.fitzche.lgmore.Camp;

import fr.fitzche.lgmore.GameStatut;
import fr.fitzche.lgmore.Main;
import fr.fitzche.lgmore.PlayerData;
import fr.fitzche.lgmore.RoleInstance;
import fr.fitzche.lgmore.Timer;
import fr.fitzche.lgmore.Lg.SpecialsBlock.SpecialBlock;
import fr.fitzche.lgmore.Lg.SpecialsBlock.SpecialBlockType;
import fr.fitzche.lgmore.Lg.SpecialsBlock.TreasureBlockData;
import fr.fitzche.lgmore.Lg.SpecialsBlock.TreasureBlockType;
import fr.fitzche.lgmore.Lg.SpecialsBlock.VoteBlockData;
import fr.fitzche.lgmore.Love.Team;
import fr.fitzche.lgmore.RolesLg.CHASSEUR;
import fr.fitzche.lgmore.RolesLg.CORBEAU;
import fr.fitzche.lgmore.RolesLg.PETITE_FILLE;
import fr.fitzche.lgmore.RolesLg.RolesLg;
import fr.fitzche.lgmore.RolesLg.THIERCE_ANGE;
import fr.fitzche.lgmore.RolesLg.VOYANTE;
import fr.fitzche.lgmore.RolesLg.Checkers.RegisterCheck;
import fr.fitzche.lgmore.RolesLg.Checkers.TimeresCheck;
import fr.fitzche.lgmore.RolesLg.Checkers.VoteChecker;
import fr.fitzche.lgmore.Util.GameLgUtil;
import fr.fitzche.lgmore.Util.ItemUtil;
import fr.fitzche.lgmore.Util.LocationUtil;
import fr.fitzche.lgmore.Util.MathUtil;
import fr.fitzche.lgmore.Util.PlayerUtil;
import fr.fitzche.lgmore.Util.Registre;
import fr.fitzche.lgmore.Util.RoleUtil;
import fr.fitzche.lgmore.Util.VoteEvent;
import fr.fitzche.lgmore.Util.WorldUtil;
import fr.fitzche.lgmore.commands.FutureAction;
import fr.fitzche.lgmore.minecraft.PlayerDataLeft;
import fr.fitzche.lgmore.minecraft.ResCheck;
import fr.fitzche.lgmore.scoreboard.ScoreboardLg;
import fr.fitzche.lgmore.scoreboard.Inventory.CompoDisplay;
import fr.fitzche.lgmore.scoreboard.Inventory.ConfigDisplay;
import fr.fitzche.lgmore.scoreboard.Inventory.EventDisplay;
import fr.fitzche.lgmore.scoreboard.Inventory.PlayerDisplay;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;


public class GameLg implements Listener{
	public GameStatut statut;
	public Timer timer;
	public String name;
	
	
	private int epicTaux = 0;
	private int oratTaux = 0;
	private int tragicTaux = 0;
	
	public boolean roleBrumed = false;
	
	public boolean aleaCouple = false;
	
	
	public Player hunter = null;
	public CHASSEUR Hunter = null;
	
	public ArrayList<PlayerData> playerAlive;
	public ArrayList<PlayerData> RealwolfAlive;
	public ArrayList<PlayerData> RealvillagerAlive;
	public ArrayList<PlayerData> soloAlive;
	public ArrayList<PlayerData> FalseVillagerAlive;
	public ArrayList<PlayerData> FalseWolfAlive;
	public boolean hasLgSolo = false;
	
	
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
	public HashMap<String, PlayerData> futureAcc = new HashMap<String, PlayerData>();
	
	public HashMap<String, Integer> probasEvents = new HashMap<String, Integer>();
	
	public Team lgTeam;
	public Team villTeam;
	
	
	
	public HashMap<String, PlayerDataLeft> playersLeft = new HashMap<String, PlayerDataLeft>();
	
	public ArrayList<RolesLg> dispoRoles = new ArrayList<RolesLg>();
	
	public boolean stopped;
	
	public ArrayList<ResCheck> resCheckers = new ArrayList<ResCheck>();
	public ArrayList<PlayerData> cannotBeVoted = new ArrayList<PlayerData>();
	public CompoDisplay compo = new CompoDisplay(this);
	public EventDisplay events = new EventDisplay(this);
	public PlayerDisplay plys;
	public int voteForce = 1;
	
	
	
	
	public ConfigDisplay config = new ConfigDisplay(this);
	
	public boolean isMeetup = false;
	public boolean hasMoreVote = false;
	
	public Inventory invVote;
	
	
	

	
	public GameLg(String name) {
		
		this.stopped = false;
		Main.server.getPluginManager().registerEvents(events, Main.plug);
		
		for (String str: Main.eventsLgNames) {
			this.probasEvents.put(str, 0);
		}
		this.resCheckers.add(new TimeresCheck(this));
		this.resCheckers.add(new VoteChecker(this));
		
		Main.server.getPluginManager().registerEvents(this, Main.plug);
		
		
		
		this.isInDisc = false;
		rolesIn = new ArrayList<RoleInstance>();
		this.name = name;
		this.inGame = false;
		this.timer = new Timer(this);
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
		this.board = new ScoreboardLg(this, null);
		for (PlayerData p:getPlayerAlive()) {
			p.board = new ScoreboardLg(this, p);
		}

		this.dispoRoles.addAll(RoleUtil.existingRoles);
		plys = new PlayerDisplay(this);
		Main.server.getPluginManager().registerEvents(plys, Main.plug);
		this.resCheckers.add(new RegisterCheck(this));
	}
	
	public int getGroupe() {
		return groupe;
	}
	
	
	
	public void applyInvicibility(PlayerData joueur) {
		joueur.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 200, 10, false, false));

	}
	
	
	public void askRunFuturesActions() {
		ArrayList<FutureAction> toRemove = new ArrayList<FutureAction>();
		
		for (FutureAction fut: this.futuresActions) {
			fut.timeBeforeRun --;
			if (fut.timeBeforeRun < 1) {
				fut.action.run();
				toRemove.add(fut);
			}
		}
		for (FutureAction futur:toRemove) {
			this.futuresActions.remove(futur);
		}
	}
	
	public void setGroupsTo(int g) {
		this.groupe = g;
		this.broadcoast(ChatColor.ITALIC+"Groupes à "+ Integer.toString(g));
	}
	public PlayerData getPlayer(String name) {
		for (PlayerData ply:this.playerAlive ) {
			if (ply.Name.equals(name)) {
				return ply;
			}
		}
		
		return null;
	}
	

	@Deprecated
	public void everySec() {
		GameLg game = this;
		
		if (stopped) {
			
			return;
		}
		
		
		if (board.istimeRunned == false) {
			board.istimeRunned = true;
			timer.temps = -20;
			broadcoast(ChatColor.UNDERLINE + "La partie va commencer dans 20 secondes");
			for (PlayerData p:getPlayerAlive()) {
				p.board = new ScoreboardLg(game, p);
				p.board.setgame(game);
				p.board.refresh();
			}
			int rayon = 500;
			
			Main.placeVoteStruct(LocationUtil.getAlLocAroundFarfrom(rayon, 2, true));
			Main.placeVoteStruct(LocationUtil.getAlLocAroundFarfrom(rayon, 2, true));
			Main.placeVoteStruct(LocationUtil.getAlLocAroundFarfrom(rayon, 2, true));
			Main.placeVoteStruct(LocationUtil.getAlLocAroundFarfrom(rayon, 2, true));
			
			int rayon2 = 100;
			Main.placeAccuseStruct(LocationUtil.getAlLocAroundFarfrom(rayon2, 5, false));
			
			
			int rayon3 = 500;
			if (probasEvents.getOrDefault("Nombre Batiments à Bonus"	, 0) < 1) {
				System.out.println("probasEvents.getOrDefault(\"Nombre Batiments à Bonus\"	, 0) == 0 in 259 of GameLg in start");
			}
			for (int i = 0; i<=probasEvents.getOrDefault("Nombre Batiments à Bonus"	, 0); i++) {
				
				TreasureBlockType type;
				int x = MathUtil.generateAlInt(0, 100);
				if (x <=15) {
					type = TreasureBlockType.AuraAnalyser;
				} else if (x <= 30) {
					type = TreasureBlockType.AuraPotion;
				} else if (x<=45) {
					type = TreasureBlockType.TeleporterPotion;
				} else if (x<=60) {
					type = TreasureBlockType.ParalysiePotion;
				} else if (x<=80) {
					type = TreasureBlockType.RegisterModifier;
				} else {
					type = TreasureBlockType.Bienfaisance;
				}
				Main.placeTreasureStruct(LocationUtil.getAlLocAroundFarfrom(rayon3, 5, true), type);
			}
			for (int i = 0; i<probasEvents.getOrDefault("Nombre Batiments Leurre"	, 0); i++) {
				Main.placeBat(LocationUtil.getAlLocAroundFarfrom(rayon2, 5, false));
			}
			if (MathUtil.pourcentage(probasEvents.getOrDefault("Couple aléatoire"	, 0))) {
				System.out.println("couple aléatoire");
				this.aleaCouple = true;
			}
			
		}
		
		boolean x = timer.addOne();
		if (x) {
			playEpisode();
		}
		
		
		
		
		
		
		if (game.timer.temps == -10) {
			System.out.println("gived start kit Lga l.163");
			for (PlayerData ply:game.players) {
				ply.setMaxHealth(20);
				ply.player.setHealth(20);
				GameLgUtil.tpAl(ply);
				ply.player.getInventory().addItem(new ItemStack(org.bukkit.Material.BOOK, 7) );
				ply.player.getInventory().addItem(new ItemStack(org.bukkit.Material.COOKED_BEEF, 64) );
				ply.player.getInventory().addItem(new ItemStack(Material.WATER_BUCKET));
			}
		}
		if (game.timer.temps==0) {
			game.broadcoast(ChatColor.UNDERLINE + "La partie commence");
			for (PlayerData player: game.getPlayerAlive()) {
				if (player.Name.equals("FITZCHE")) {
					game.broadcoast("Le développeur est dans la partie...");
				}
			}
			
			//STATUT
			game.statut = GameStatut.BEFORE_ROLE;
			//FIN STATUT
			
			if (game.isMeetup) {
				for (PlayerData p:game.getPlayerAlive()) {
					ItemStack legging = new ItemStack(Material.IRON_LEGGINGS);
					legging.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL	, 3);
					ItemStack boots = new ItemStack(Material.IRON_BOOTS);
					boots.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL	, 3);
					ItemStack helmet = new ItemStack(Material.IRON_HELMET);
					helmet.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL	, 3);
					ItemStack chestplate = new ItemStack(Material.DIAMOND_CHESTPLATE);
					chestplate.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL	, 2);
					ItemStack sword = new ItemStack(Material.DIAMOND_SWORD);
					sword.addEnchantment(Enchantment.DAMAGE_ALL	, 3);
					ItemStack gap = new ItemStack(Material.GOLDEN_APPLE, 15);
					ItemStack bow = new ItemStack(Material.BOW);
					bow.addEnchantment(Enchantment.ARROW_DAMAGE, 2);
					ItemStack arrows = new ItemStack(Material.ARROW, 64);
					
					if (p.isOnline) {
						p.player.getInventory().addItem(legging);
						p.player.getInventory().addItem(boots);
						p.player.getInventory().addItem(helmet);
						p.player.getInventory().addItem(chestplate);
						p.player.getInventory().addItem(sword);
						p.player.getInventory().addItem(gap);
						p.player.getInventory().addItem(bow);
						p.player.getInventory().addItem(arrows);
						p.player.getInventory().addItem(new ItemStack(Material.ANVIL));
						p.player.giveExpLevels(1000);
					}
				}
				for (int i = 0; i <= 1199; i++) {
					game.timer.addOne();
				}
			}
		}
		
		
		if (game.timer.temps == 1200) {
			game.statut = GameStatut.IN_GAME;
			game.attributeRoleToAll();
			
		}
		
		if (game.timer.temps > 1200) {
			
			
			for (PlayerData player: game.getPlayerAlive()) {
				
				if (player == null) {
					System.out.println("temp hear in time ?> 1200 ");
					
				}else {
					player.roleIn.giveEffectAllTime();
					for (PlayerData p : getPlayerAlive()) {
						
						if (player.getLocation().distance(p.getLocation()) < 20) {
							
							player.timeWithPlayers.put(p.getName(), player.timeWithPlayers.getOrDefault(p.getName(), 0) + 1);
						}
						
						if (p.auraDiscoverEffetDuration > 0) {
							Effect effect = Effect.CRIT;
							Color color  = Color.YELLOW;
							switch (p.aura) {
							case DANGEROUS:
								effect = Effect.EXPLOSION_LARGE;
								color = Color.RED;
								break;
							case LUMINOUS:
								effect = Effect.HEART;
								color = Color.GREEN;
								break;
							case NEUTRAL:
								
								break;
							case OBSCUR:
								effect = Effect.EXPLOSION_LARGE;
								color = Color.RED;
								break;
							case UNKNOW:
								break;
							default:
								
								break;
							
							}
							PlayerUtil.particle(p.getLocation(), color);
							p.auraDiscoverEffetDuration --;
						}
						
					}
				}
				
			}
			if (WorldUtil.getTime(Main.server.getWorld("world")).equals("day")) {
				
				for (PlayerData player: game.getPlayerAlive()) {
					player.roleIn.giveDayEffect();
				}
			} else if (WorldUtil.getTime(Main.server.getWorld("world")).equals("night")) {
				
				for (PlayerData player: game.getPlayerAlive()) {
					player.roleIn.giveNightEffectCheck();
				}
			}
			if (game.isMeetup) {
				if (game.timer.temps == 1201) {
					for (int i = 0; i <= 1197; i++) {
						game.timer.addOne();
					}
				}
			}
		}
		
		
	}
	
	
	public void playersRefresh() {
		for (PlayerData ply:this.playerAlive) {
			for (PlayerData ply1:this.playerAlive) {
				if (!ply.equals(ply1) && !ply.canVoted.contains(ply1) && ply.getLocation().distance(ply1.getLocation()) < 20 && ply1.inLife && ply.inLife && !cannotBeVoted.contains(ply1)) {
					ply.canVoted.add(ply1);
				}
			}
		}
		 
		for (PlayerData p:this.getPlayerAlive()) {
			p.board.refresh();
		}
		
	}
	@Deprecated
	public void accusation(PlayerData accuser, PlayerData accused) {
		
		Bukkit.broadcastMessage("Le joueur "+ accuser.getName() + " accuse le joueur "+ accused.getName() + " publiquement, il a 10 minutes pour prouver sa culpabilité sous peine de perdre 1.5 coeurs permanents, si celui-ci se révèle innocent, il perdra 3 coeurs permanents et son droit de vote.");
		//CAUSE ORAT
		
		this.addorat(10, accuser.getLocation());
		//CONSEqUENCE orAT
		if (getOrat() > 50) {
			broadcoast("Ses Coordonnée sont "+String.valueOf(accused.getLocation().getBlockX())+ "; "+String.valueOf(accused.getLocation().getBlockY()) + "; "+ String.valueOf(accused.getLocation().getBlockZ()));
		}
		if (accused.role.equals(RolesLg.ANGE_THIERCE)) {
			accused.hasStrenghtAgainst.put(accuser.player, true);
			accused.setMaxHealth(accused.getMaxHealth()+2);
			accused.boostS5++;
			
			accused.sendMessage(ChatColor.GOLD+"Vous avez été accusé, le role du joueur vous accusant est "+ accuser.role.getName()+", vous gagnez 20% de force contre celui-ci ainsi que 1 coeur permanent, si vous venez à le tuer, sa mort ne sera pas annoncée.");
		} else {
			accuser.hasStrenghtAgainst.put(accused.player, true);
		}
		
		Bukkit.getScheduler().runTaskLater(Main.plug, new BukkitRunnable() {

			@Override
			public void run() {
				
				accuser.hasStrenghtAgainst.put(accused.player, false);
				if (accused.inLife) {
					accuser.setMaxHealth(accuser.getMaxHealth() - 1.5);
					Bukkit.broadcastMessage("Le joueur "+ accuser.getName() + " n'a pas pu prouver la culpabilité du joueur "+ accused.getName() + " à temps, il perd donc 1.5 coeurs permanents");
					addTragic(10, accuser.getLocation());
				} else {
					if (accused.team.equals(villTeam)) {
						Bukkit.broadcastMessage("Le joueur "+ accuser.getName() + " a accusé à tord le joueur "+ accused.getName() + ", il perd donc 3 coeurs permanents et son droit de vote.");
						accuser.setMaxHealth(accuser.getMaxHealth() - 3);
						cannotBeVoted.add(accuser);
					} else {
						Bukkit.broadcastMessage("Le joueur "+ accuser.getName() + " a accusé à tord le joueur "+ accused.getName() + " à raison, il gagne donc 10% de force supplémentaires.");
						accuser.boostS5 += 2;
						addEpic(15, accuser.getLocation());
					}
				}
			}
			
		}, 12000);
	}
	
	public void removeFromVote(PlayerData p) {
		for (PlayerData player:this.getPlayerAlive()) {
			if (p.canVoted.contains(player)) {
				player.canVoted.remove(p);
				
			}
			
		}
		Bukkit.broadcastMessage(ChatColor.RED+"Le joueur "+ p.getName() + " a fuit la justice des villageois. Il ne pourra alors ni être voté ni voter.");
		for (PlayerData ply: this.getPlayerAlive()) {
			ply.sendMessage(ChatColor.RED+"Le joueur fuyard se trouve à " + ply.getLocation().distance(p.getLocation()) + " blocs de vous.");
		}
	}
	
	public void broadcoast(String message ) {
		
		for (PlayerData p:this.getPlayerAlive()) {
			p.sendMessage(message);
			
		}
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
			
			player.roleIn = RoleUtil.createRoleOfPlayerRoles(player);
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
		
		if (!hasLgSolo &&getRealWolfAlive().size() > 0 && MathUtil.pourcentage(probasEvents.get("Loup Solitaire"))) {
			PlayerData p = getRealWolfAlive().get(MathUtil.generateAlInt(0, getRealWolfAlive().size() - 1));
			p.sendMessage(ChatColor.DARK_RED+"Vous devenez loup solitaire, vous devez maintenant gagner tout seul, pour cela vous gagnez 4 coeurs permanents, et 5% de résistance.");
			p.team = new Team("Loup Solitaire", Camp.Other, this, players, null, "at wolf team creating at == 1200", null, null, true, false, false, false);
			lgTeam.remove(p);
			p.camp = Camp.Other;
			p.changeHealth(8);
			p.boostR5 ++;
		}
		
		setGroupsTo(getNumberOfPlayer() / 5);
		
		Registre r = getRegister();
		if (r.getType().equals(RegisterType.Tragic)) {
			if (MathUtil.pourcentage(r.getTaux()) && getGroupe() > 2) {
				setGroupsTo(getGroupe() - 1);
			}
			if (MathUtil.pourcentage(probasEvents.get("Exposed"))&&MathUtil.pourcentage(r.getTaux())) {
				exposed(GameLgUtil.getAlPlayer(this), 4);
			}
		}
		if (r.getType().equals(RegisterType.Oratoire)) {
			if (MathUtil.pourcentage(r.getTaux()) && getGroupe() < 6) {
				setGroupsTo(getGroupe() + 1);
			}
		}
		
		
		
		for (PlayerData p:getPlayerAlive()) {
			if (futureAcc.getOrDefault(p.getName(), null) != null) {
				accusation(p, futureAcc.getOrDefault(p.getName(), null));
				//EFFET (et cause exceptionnelement) REGISTRE
				if (getRegister().getType().equals(RegisterType.Tragic)) {
					addTragic(10, p.getLocation());
				}
				
			}
			
			if (p.toEscape) {
				removeFromVote(p);

			}
		}
		
		
	}
	
	
	
	@Deprecated
	public void setEpisodeTime() {
		
		
		
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
	
	public void playSoundMO() {
		for (PlayerData p:getPlayerAlive()) {
			if (p.isOnline) {
				p.player.playSound(p.getLocation(), Sound.WOLF_GROWL, 1, 1);
			}
			
		}
	}
	public void playSoundWolf() {
		for (PlayerData p:getPlayerAlive()) {
			if (p.isOnline) {
				p.player.playSound(p.getLocation(), Sound.WOLF_HOWL, 1, 1);
			}
			
		}
	}
	public void playSoundMO(PlayerData p) {
		if (p.isOnline) {
			p.player.playSound(p.getLocation(), Sound.WOLF_GROWL, 1, 1);
		}
	}
	public void playSoundWolf(PlayerData p) {
		if (p.isOnline) {
			p.player.playSound(p.getLocation(), Sound.WOLF_HOWL, 1, 1);
		}
	}

	@Deprecated
	public void startVote() {
		this.invVote = Bukkit.createInventory(null, 36);
		
		int nbVoter = this.getNumberOfPlayer() * 2 / 3;
		for (SpecialBlock bloc:Main.specialBlocks) {
			if (bloc.getData() != null&&bloc.getData().getType() !=null  &&bloc.getData().getType().equals(SpecialBlockType.Vote)) {
				((VoteBlockData) bloc.getData()).nbOfVote = 5;
			}
		}
		if (getPlayerAlive().size() < 5) {
			nbVoter = getPlayerAlive().size();
		}
		for (int i = 0; i < nbVoter; i++) {
			ItemStack item = new ItemStack(Material.EMERALD);
			ItemUtil.setName(item, "Voter");
			this.invVote.setItem(i, item);
		}
		
		for (PlayerData player: this.playerAlive) {
			player.askVoted();
			
		}
		this.isInVote = true;
		GameLg game = this;
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
				
				addorat(3*mostVoted.vote, mostVoted.getLocation());
				
				boolean corb = false;
				if (mostVoted.vote > 0 && !equal) {
					
					//CONSESQUENCE EPIC
					int plus = 0;
					int exposedPlus = 0;
					if (getOrat() > 70) {
						plus++;
					}
					if (getTragic() > 40 || getOrat() > 40) {
						plus++;
					} else if (getEpic() > 90) {
						plus-= 3;
						if (MathUtil.pourcentage(70)) {
							exposedPlus ++;
						}
						
					}else if (getEpic() > 60) {
						plus-= 2;
						if (MathUtil.pourcentage(45)) {
							exposedPlus ++;
						}
						
					}else if (getEpic() > 30) {
						plus-= 1;
						if (MathUtil.pourcentage(20)) {
							exposedPlus ++;
						}
						
					}
					//CONSEQUENCE ORAT
					if (getOrat() > 20 && MathUtil.pourcentage(40)) {
						exposedPlus --;
					}
					if (plus < 0) {
						plus = 0;
					}
					
					
					
					mostVoted.changeHealth(-(voteForce + plus));
					if (6-voteForce+ exposedPlus < 1) {
						exposed(mostVoted, 1);
					} else {
						exposed(mostVoted, 6-voteForce+ exposedPlus);
					}
					
					
					if (voteForce < 5) {
						voteForce ++;
					}
					
					
					Bukkit.broadcastMessage(ChatColor.GOLD + "Le joueur " + ChatColor.DARK_AQUA + mostVoted.Name +ChatColor.GOLD+ "a été le plus voté" );
					for (PlayerData player:playerAlive) {
						
						
						if (player.voted.Name.equals(mostVoted.Name)) {
							player.canVoted.remove(mostVoted);
							
							if (player.role.equals(RolesLg.CORBEAU)) {
								Bukkit.broadcastMessage(ChatColor.BLACK+"Le corbeau a voté avec le village");
								corb = true;
								addorat(5, player.getLocation());
								CORBEAU corbeau = (CORBEAU) player.roleIn;
								corbeau.addGoodVoted();
							}
						}
					}
					
					
				} else {
					Bukkit.broadcastMessage(ChatColor.GOLD +"Aucun joueur n'a été voté plus de 2 fois, ou il y a une égalité");
				}
				VoteEvent event = new VoteEvent(game, mostVoted, mostVoted.vote, corb);
				for (ResCheck checker:game.resCheckers) {
					checker.onVoteEvent(event);
				}
				
				for (PlayerData player:playerAlive) {
					if (player.voted == null) {
						return;
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
	
	public void announceDeath(PlayerData player1, boolean brumed, boolean hidden) {
		
		
		if (hidden) {
			for (Team team:teams) {
				team.onPlayerDeath(player1);
			}
			return;
		}
		ChatColor color = ChatColor.RED;
		if (brumed) {
			color = ChatColor.MAGIC;
		}
		String moreInfo = "";
		if (player1.infected) {
			moreInfo = moreInfo+ (" (loup garou) ");
		} 
		if (player1.inLove) {
			moreInfo = moreInfo+ (" (en couple) ");
			addTragic(8, null);
		} 

		GameLg gm1 =GameLgUtil.getGameOfPlayer(player1, " at 152 Main");
		if (gm1.name != this.name) {
			return;
		}
		if (MathUtil.pourcentage(probasEvents.get("Brume"))) {
			return;
		}
		

		Main.server.broadcastMessage(ChatColor.DARK_BLUE +"___________________________" + "\n" +
							color + player1.getName() + " est mort |"+ "\n"  +
								" il était "+ player1.camp.getColor() + 
								player1.getLgRole() + ChatColor.GOLD + moreInfo + "|" + "\n"+ChatColor.DARK_BLUE +"___________________________");
		for (Team team:teams) {
			team.onPlayerDeath(player1);
		}
	}
	
	public void announceDeath(PlayerData player1, RolesLg role, boolean brumed) {
		ChatColor color = ChatColor.RED;
		if (brumed) {
			color = ChatColor.MAGIC;
		}
		
		String moreInfo = "";
		if (player1.infected) {
			moreInfo = moreInfo+ (" (loup garou) ");
		} 
		if (player1.inLove) {
			moreInfo = moreInfo+ (" (en couple) ");
		} 

		GameLg gm1 = Main.game;
		if (gm1.name != this.name) {
			return;
		}
		if (MathUtil.pourcentage(probasEvents.get("Brume"))) {
			return;
		}

		Main.server.broadcastMessage(ChatColor.DARK_BLUE +"___________________________" + "\n" +
							color + player1.getName() + " est mort |"+ "\n"  +
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
	
	public void exposed(PlayerData player, int nbOfRole) {
		
		ArrayList<String> rolesStr = new ArrayList<String>();
		int x = MathUtil.generateAlInt(1, nbOfRole);
		
		for (int i = 0; i < nbOfRole; i++) {
			if (i==x) {
				rolesStr.add(player.getLgRole().getName());
				
			} else {
				rolesStr.add(getRoles().get(MathUtil.generateAlInt(0, getRoles().size() - 1)).getName());
			}
			
			
		}
		
		Bukkit.broadcastMessage(ChatColor.DARK_RED+ "//EXPOSED//" + ChatColor.DARK_GREEN+"Le Role du joueur "+ player.getName() + " se trouve parmi les suivant: ");
		for (String str:rolesStr) {
			Bukkit.broadcastMessage(ChatColor.GOLD+"-"+str);
		}
		
		
	}
	public void exposedMulti(ArrayList<PlayerData> players, int nbOfRoleSup) {
		
		ArrayList<String> rolesStr = new ArrayList<String>();
		for (PlayerData p:players) {
			rolesStr.add(p.getLgRole().getName());
		}
		for (int i = 0; i<nbOfRoleSup; i++) {
			rolesStr.add(getRoles().get(MathUtil.generateAlInt(0, getRoles().size() - 1)).getName());

		}

		
		Bukkit.broadcastMessage(ChatColor.DARK_RED+ "//EXPOSED//" + ChatColor.DARK_GREEN+"Les Roles des joueurs:se trouvent parmis les suivants: ");
		for (PlayerData p:players) {
			Bukkit.broadcastMessage(ChatColor.DARK_GREEN+"-"+p.getName() );
		}
		Bukkit.broadcastMessage(ChatColor.DARK_GREEN+"se trouvent parmis les suivants: ");

		int x = MathUtil.generateAlInt(0, rolesStr.size() - 1);
		do {
			String str = rolesStr.get(x);
			Bukkit.broadcastMessage(ChatColor.GOLD+"-"+str);
			rolesStr.remove(x);
			if (rolesStr.size() > 0) {
				x = MathUtil.generateAlInt(0, rolesStr.size() - 1);
			}
			
		}while (rolesStr.size() > 0);
		
		
		
	}
	
	public void addTragic(int toAdd, Location loc) {
		int before = this.tragicTaux;
		if (this.oratTaux < 1 && this.epicTaux < 1) {
			this.tragicTaux += toAdd;
		} else if (this.oratTaux > 0) {
			if (this.oratTaux > toAdd) {
				this.oratTaux -= toAdd;
			} else {
				this.tragicTaux = toAdd - this.oratTaux;
				this.oratTaux = 0;
			}
		}else if (this.epicTaux > 0) {
			if (this.epicTaux > toAdd) {
				this.epicTaux -= toAdd;
			} else {
				this.tragicTaux = toAdd - this.epicTaux;
				this.epicTaux = 0;
			}
		}
		checkRegistr();
		for (ResCheck res:this.resCheckers) {
			res.onAddTragic(before, this.tragicTaux, loc);
		}
		
	}
	
	public void addorat(int toAdd, Location loc) {
		int before = this.oratTaux;
		if (this.tragicTaux < 1 && this.epicTaux < 1) {
			this.oratTaux += toAdd;
		} else if (this.tragicTaux > 0) {
			if (this.tragicTaux > toAdd) {
				this.tragicTaux -= toAdd;
			} else {
				this.oratTaux = toAdd - this.tragicTaux;
				this.tragicTaux = 0;
			}
		}else if (this.epicTaux > 0) {
			if (this.epicTaux > toAdd) {
				this.epicTaux -= toAdd;
			} else {
				this.oratTaux = toAdd - this.epicTaux;
				this.epicTaux = 0;
			}
		}
		checkRegistr();
		for (ResCheck res:this.resCheckers) {
			res.onAddOrat(before, this.oratTaux, loc);
		}
	}
	
	public void addEpic(int toAdd, Location loc) {
		int before = this.epicTaux;
		if (this.tragicTaux < 1 && this.oratTaux < 1) {
			this.epicTaux += toAdd;
		} else if (this.tragicTaux > 0) {
			if (this.tragicTaux > toAdd) {
				this.tragicTaux -= toAdd;
			} else {
				this.epicTaux = toAdd - this.tragicTaux;
				this.tragicTaux = 0;
			}
		}else if (this.oratTaux > 0) {
			if (this.oratTaux > toAdd) {
				this.oratTaux -= toAdd;
			} else {
				this.epicTaux = toAdd - this.epicTaux;
				this.oratTaux = 0;
			}
		}
		checkRegistr();
		for (ResCheck res:this.resCheckers) {
			res.onAddEpic(before, this.epicTaux, loc);
		}
		
	}
	
	public void checkRegistr() {
		if (oratTaux > 100) {
			oratTaux = 100;
		}else if (epicTaux > 100) {
			epicTaux = 100;
		}else if (tragicTaux > 100) {
			tragicTaux = 100;
		}
	}
	
	public int getTragic() {
		return this.tragicTaux;
	}
	public int getOrat() {
		return this.oratTaux;
	}
	public int getEpic() {
		return this.epicTaux;
	}
	public Registre getRegister() {
		if (epicTaux > 0) {
			
			return new Registre(epicTaux, RegisterType.Epic);
		} else if (tragicTaux > 0) {
			return new Registre(tragicTaux, RegisterType.Tragic);
		} else if (oratTaux > 0) {
			return new Registre(oratTaux, RegisterType.Oratoire);
		}
		System.out.println("taux nul");
		return new Registre(0, null);
		
	}
	
	public boolean isDay() {
		if (WorldUtil.getTime(Main.server.getWorld("world")).equals("day")) {
			return true;
		} else {
			return false;
		}
	}
	
	
	@Deprecated
	public void groupInfluenceRegistre(ArrayList<PlayerData> ps, Location loc) {
		System.out.println("groupInfluenceRegistre act");
		if (ps.size() < 1) {
			System.out.println("groupInfluenceRegistre null");
		}
		for (PlayerData player:ps) {
			System.out.println("groupInfluenceRegistre send");
			TextComponent text = new TextComponent();
			text.setText("Clickez ici pour choisir le registre tragique");
			text.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, ("/lg tragicchooseevent "+player.Name)));
			player.player.spigot().sendMessage(text);
			
			TextComponent text2 = new TextComponent();
			text2.setText("Clickez ici pour choisir le registre epique");
			text2.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, ("/lg epicchooseevent "+player.Name)));
			player.player.spigot().sendMessage(text2);
			TextComponent text3 = new TextComponent();
			text3.setText("Clickez ici pour choisir le registre oratoire");
			text3.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, ("/lg oratchooseevent "+player.Name)));
			player.player.spigot().sendMessage(text3);
		}
		
		Bukkit.getScheduler().runTaskLater(Main.plug, new BukkitRunnable() {

			@Override
			public void run() {
				int t = 0;
				int o = 0;
				int e = 0;
				for (PlayerData player:ps) {
					switch (player.favRegister) {
					case Epic:
						if (player.team.equals(villTeam)) {
							e++;
						} else if (player.team.equals(lgTeam)) {
							e += 10;
						} else {
							e+=100;
						}
						
						break;
					case Oratoire:
						if (player.team.equals(villTeam)) {
							o++;
						} else if (player.team.equals(lgTeam)) {
							o += 10;
						} else {
							o+=100;
						}
						break;
					case Tragic:
						if (player.team.equals(villTeam)) {
							t++;
						} else if (player.team.equals(lgTeam)) {
							t += 10;
						} else {
							t+=100;
						}
						break;
					
					default:
						break;
					
					}
					player.favRegister = null;
				}
				
				
				boolean nul = false;
				boolean eVict = false;
				boolean tVict = false;
				boolean oVict = false;
				if (t==o) {
					if (e>t) {
						eVict = true;
					} else {
						nul = true;
					}
				} else if (t>o) {
					if (e>t) {
						eVict = true;
					} else if (e==t) {
						nul = true;
					} else {
						tVict = true;
					}
				} else if (t<o) {
					if (e>o) {
						eVict = true;
					} else if (e < o) {
						oVict = true;
					} else {
						nul = true;
					}
				}
				String str = "Erreur, désolé jsp ce qui va pas, signalez moi le probleme et je règle ça (ce message est envoyé aux joueurs si le texte envoyé aux joueurs n'est pas correctement initialisé ?)";
				
				if (nul) {
					str = "Il y a une égalité, par conséquent il n'y aura pas de changement de registre";
					
				} else if (eVict) {
					str = "Le registre Epique l'emporte";
					addEpic(20, loc);
				}else if (oVict) {
					str = "Le registre Oratoire l'emporte";
					addorat(20, loc);
				}else if (tVict) {
					str = "Le registre Tragique l'emporte";
					addTragic(20, loc);
				}
				
				for (PlayerData p:ps) {
					p.sendMessage(ChatColor.DARK_PURPLE+str);
				}
				
				
			}
			
		}, 300);
	
	}
	
	public void groupAuraEstimation(ArrayList<PlayerData> ps) {
		double taux = 0;
		int diviseur = 0;
		for (PlayerData p:ps) {
			diviseur ++;
			switch (p.aura)	 {
			case DANGEROUS:
				taux -= 10;
				break;
			case LUMINOUS:
				taux += 100;
				break;
			case NEUTRAL:
				taux += 50;
				break;
			case OBSCUR:
				
				break;
			case UNKNOW:
				taux -= 50;
				taux *= 1.2;
				taux+=50;
				break;
			default:
				break;
			
			}	
			if (taux < 0) {
				taux = 0;
			}
			if (taux > 100) {
				taux = 100;
			}
		}
		double result = taux/diviseur;
		for (PlayerData p2:ps) {
			p2.sendMessage(ChatColor.DARK_PURPLE+ "le taux d'aura lumineuse contre obscur dans ce groupe est de "+ Double.toString(result)+ "%");
		}
	}
	
	
	public void summonTreasure(TreasureBlockType type, Location loc) {
		
		int x = loc.getBlockX();
		int y = loc.getBlockY();
		int z = loc.getBlockZ();
		World world = Main.server.getWorld("world");
		world.getBlockAt(new Location(world, x, y, z)).setType(Material.BEDROCK);
		world.getBlockAt(new Location(world, x+1, y, z+1)).setType(Material.BEDROCK);
		world.getBlockAt(new Location(world, x+1, y, z)).setType(Material.BEDROCK);
		world.getBlockAt(new Location(world, x+1, y, z-1)).setType(Material.BEDROCK);
		world.getBlockAt(new Location(world, x, y, z-1)).setType(Material.BEDROCK);
		world.getBlockAt(new Location(world, x-1, y, z-1)).setType(Material.BEDROCK);
		world.getBlockAt(new Location(world, x-1, y, z)).setType(Material.BEDROCK);
		world.getBlockAt(new Location(world, x-1, y, z+1)).setType(Material.BEDROCK);
		world.getBlockAt(new Location(world, x, y, z+1)).setType(Material.BEDROCK);
		world.getBlockAt(new Location(world, x, y+1, z)).setType(Material.ENDER_CHEST);
		TreasureBlockData data = new TreasureBlockData(type);
		SpecialBlock b = new SpecialBlock(new Location(world, x, y+1, z), SpecialBlockType.Treasure, data);
		Main.specialBlocks.add(b);
	}
	
	
	
}
