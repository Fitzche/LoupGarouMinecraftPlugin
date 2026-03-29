package fr.fitzche.lgmore.Lg;

import java.io.Serializable;
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
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Scoreboard;

import fr.fitzche.lgmore.Camp;
import fr.fitzche.lgmore.Game;
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

import org.bukkit.WorldCreator;

import fr.fitzche.lgmore.RolesLg.Aura;
import fr.fitzche.lgmore.RolesLg.CHASSEUR;
import fr.fitzche.lgmore.RolesLg.CORBEAU;
import fr.fitzche.lgmore.RolesLg.PETITE_FILLE;
import fr.fitzche.lgmore.RolesLg.RolesLg;
import fr.fitzche.lgmore.RolesLg.SWAPPER;
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
import fr.fitzche.lgmore.commands.Hub;
import fr.fitzche.lgmore.minecraft.GameListener;
import fr.fitzche.lgmore.minecraft.PlayerDataLeft;
import fr.fitzche.lgmore.minecraft.ResCheck;
import fr.fitzche.lgmore.scoreboard.ScoreboardLg;
import fr.fitzche.lgmore.scoreboard.Inventory.CompoDisplay;
import fr.fitzche.lgmore.scoreboard.Inventory.ConfigDisplay;
import fr.fitzche.lgmore.scoreboard.Inventory.EventDisplay;
import fr.fitzche.lgmore.scoreboard.Inventory.PlayerDisplay;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;


public class GameLg implements Listener, Serializable, Game{
	public GameStatut statut;
	public Timer timer;
	public String name;
	

	
	
	private int epicTaux = 0;
	private int oratTaux = 0;
	private int tragicTaux = 0;
	
	public boolean roleBrumed = false;
	
	public boolean aleaCouple = false;
	
	//SCENARIOS
	//CHAP
	public HashMap<String, Boolean> scenarioAct = new HashMap<String, Boolean>();
	
	//1) REGISTRES
	public boolean isRegistresActivated = false;
	//2)Camps affichés
	public boolean displayedRoles = false;
	//3) Necromancie
	public boolean necrom = false;
	//4) TeamSwapper
	public boolean swapperDuel = false;
	public boolean swapperTrio = false;
	public boolean swapperQuadrio = false;
	public boolean swapperPente = false;
	public boolean swapper = false;
	//5) all people are solo
	public boolean allDifferent = false;
	
	
	private ArrayList<PlayerData> playerAlive;
	public ArrayList<PlayerData> RealwolfAlive;
	public ArrayList<PlayerData> RealvillagerAlive;
	public ArrayList<PlayerData> soloAlive;
	public ArrayList<PlayerData> FalseVillagerAlive;
	public ArrayList<PlayerData> FalseWolfAlive;
	public boolean hasLgSolo = false;
	
	public HashMap<String, Boolean> isBanned = new HashMap<String, Boolean>();
	
	public ArrayList<RolesLg> roles = new ArrayList<RolesLg>();
	
	public ScoreboardLg board;
	public boolean inGame;
	public int groupe;
	private ArrayList<PlayerData> players = new ArrayList<PlayerData>();
	public ArrayList<RoleInstance> rolesIn;
	public boolean isInVote;
	public boolean isInDisc;
	public ArrayList<FutureAction> futuresActions = new ArrayList<FutureAction>();
	public HashMap<String, PlayerData> futureAcc = new HashMap<String, PlayerData>();
	
	public HashMap<String, Integer> probasEvents = new HashMap<String, Integer>();
	public ArrayList<Location> locsBat = new ArrayList<Location>();

	
	
	
	
	public ArrayList<RolesLg> dispoRoles = new ArrayList<RolesLg>();
	
	public boolean stopped;
	
	public ArrayList<ResCheck> resCheckers = new ArrayList<ResCheck>();
	public ArrayList<PlayerData> cannotBeVoted = new ArrayList<PlayerData>();
	public CompoDisplay compo = new CompoDisplay(this);
	public EventDisplay events = new EventDisplay(this);
	public PlayerDisplay plys;
	public int voteForce = 1;
	public int maxPlayerSize = 30;
	
	
	
	
	public ConfigDisplay config = new ConfigDisplay(this);
	public GameLgListener listener;
	
	
	public boolean isMeetup = false;
	public boolean hasMoreVote = false;
	public boolean toRegister = true;
	
	
	
	public World world;
	public boolean isWorldGenerated = false;
	public GameType gameType;

	
	public GameLg(String name) {
		
		this.stopped = false;
		
		this.listener = new GameLgListener(this);
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
			p.board.refresh();
		}

		this.dispoRoles.addAll(RoleUtil.existingRoles);
		plys = new PlayerDisplay(this);
		Main.server.getPluginManager().registerEvents(plys, Main.plug);
		this.resCheckers.add(new RegisterCheck(this));
		
		this.probasEvents.put("AutomaticCheckWin", 100);
		
	}
	
	public int getGroupe() {
		return groupe;
	}
	
	
	/*
	 * applyInvicibility on a player for 10s
	 * */
	public void applyInvicibility(PlayerData joueur) {
		joueur.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 200, 10, false, false));

	}
	
	/*
	 * remove 1 to the timer of every futures actions in the list futuresActions, and run it if it arrive to 0
	 * */
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
	
	
	
	/*
	 * change the groupe size
	 * */
	public void setGroupsTo(int g) {
		this.groupe = g;
		this.broadcoast(ChatColor.ITALIC+"Groupes à "+ Integer.toString(g));
	}
	
	
	/*
	 * get the player corresponding to a name
	 * @param name the name
	 * */
	public PlayerData getPlayer(String name) {
		for (PlayerData ply:this.getPlayerAlive() ) {
			if (ply.getName().equals(name)) {
				return ply;
			}
		}
		
		return null;
	}
	

	
	/*
	 * action every second
	 * 1 if time is not runned, start: scenarion activated, tp players, place structure, 
	 * time = -10: give kit
	 * 
	 * */
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
			
			this.isRegistresActivated = scenarioAct.getOrDefault("Théâtre", false);
			this.displayedRoles = scenarioAct.getOrDefault("DirectFights", false);
			this.necrom = scenarioAct.getOrDefault("Necromancie", false);
			this.swapperDuel = scenarioAct.getOrDefault("SwapperDouble", false);
			this.swapperTrio = scenarioAct.getOrDefault("SwapperTrio", false);
			this.swapperQuadrio = scenarioAct.getOrDefault("SwapperQuatuor", false);
			this.swapperPente = scenarioAct.getOrDefault("SwapperFive", false);
			
			if (swapperDuel || swapperPente || swapperQuadrio || swapperTrio) {
				swapper = true;
				
			}
			
			
			for (PlayerData p:getPlayerAlive()) {
			
				p.board.refresh();
				if (displayedRoles) {
					p.setDisplayName();
				}
				p.boostR5 = 0;
				p.boostS5 = 0;
				if (p.isOnline) {
					p.player.teleport(Main.world.getSpawnLocation());
					p.player.removePotionEffect(PotionEffectType.REGENERATION);
					p.player.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
					p.player.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
				}
			}
			int rayon = 300;
			boolean bat = true;
			
			
			
			//si swapper
			if (swapper) {
				bat = false;
				this.displayedRoles = true;
			}
			
			//Seulement game lg
			if (bat) {
				Main.placeVoteStruct(LocationUtil.getAlLocAroundFarfrom(rayon, 2, true, this.world, this), this);
				Main.placeVoteStruct(LocationUtil.getAlLocAroundFarfrom(rayon, 2, true, this.world, this), this);
				Main.placeVoteStruct(LocationUtil.getAlLocAroundFarfrom(rayon, 2, true, this.world, this), this);
				Main.placeVoteStruct(LocationUtil.getAlLocAroundFarfrom(rayon, 2, true, this.world, this), this);
				
				int rayon2 = 100;
				Main.placeAccuseStruct(LocationUtil.getAlLocAroundFarfrom(rayon2, 5, false, this.world, this), this);
				
				
				int rayon3 = 300;
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
						//type = TreasureBlockType.RegisterModifier;
						type = TreasureBlockType.AuraAnalyser;
					} else {
						type = TreasureBlockType.Bienfaisance;
					}
					Main.placeTreasureStruct(LocationUtil.getAlLocAroundFarfrom(rayon3, 5, true, this.world, this), type, this);
				}
				for (int i = 0; i<probasEvents.getOrDefault("Nombre Batiments Leurre"	, 0); i++) {
					Main.placeBat(LocationUtil.getAlLocAroundFarfrom(rayon2, 5, false, this.world, this), this);
				}
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
				if (!swapper) {
					if (displayedRoles) {
						GameLgUtil.tpAl(ply, 100);
						isMeetup = true;
					} else {
						int r = 1000;
						if (allDifferent) {
							r = 100;
						}
						GameLgUtil.tpAl(ply, r);
					}
				} 
				
				
				ply.player.getInventory().addItem(new ItemStack(org.bukkit.Material.BOOK, 7) );
				ply.player.getInventory().addItem(new ItemStack(org.bukkit.Material.COOKED_BEEF, 64) );
				ply.player.getInventory().addItem(new ItemStack(Material.WATER_BUCKET));
			}
			if (swapper) {
				
				isMeetup = true;
				
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
					ItemStack[] blocks = {new ItemStack(Material.COBBLESTONE, 64), new ItemStack(Material.COBBLESTONE, 64), new ItemStack(Material.COBBLESTONE, 64), new ItemStack(Material.COBBLESTONE, 64), new ItemStack(Material.COBBLESTONE, 64), new ItemStack(Material.COBBLESTONE, 64)};
					
					
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
						for (ItemStack b:blocks) {
							p.player.getInventory().addItem(b);
						}
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
			
			if (swapper) {
				//r b ro v j
				ArrayList<PlayerData> rs = new ArrayList<PlayerData>();
				ArrayList<PlayerData> bs = new ArrayList<PlayerData>();
				ArrayList<PlayerData> ros = new ArrayList<PlayerData>();
				ArrayList<PlayerData> vs = new ArrayList<PlayerData>();
				ArrayList<PlayerData> ys = new ArrayList<PlayerData>();
				
				for (PlayerData p:playerAlive) {
					switch (p.camp) {
						case BLUE:
							bs.add(p);
							break;

						case GREEN:
							vs.add(p);
							break;


						case PINK:
							ros.add(p);
							break;
						case RED:
							rs.add(p);
							break;

						case YELLOW:
							ys.add(p);
							break;
						default:
							break;
					
					}
				}
				GameLgUtil.tpAl(rs, 1000);
				GameLgUtil.tpAl(bs, 1000);
				GameLgUtil.tpAl(ros, 1000);
				GameLgUtil.tpAl(vs, 1000);
				GameLgUtil.tpAl(ys, 1000);
				isMeetup = true;
			}
			if (displayedRoles) {
				for (PlayerData p:getPlayerAlive()) {
					p.setDisplayName();
				}
			}
			
		}
		
		if (game.timer.temps > 1200) {
			
			
			for (PlayerData player: game.getPlayerAlive()) {
				
				if (player == null) {
					System.out.println("temp hear in time ?> 1200 ");
					
				}else {
					player.roleIn.giveEffectAllTime();
					for (PlayerData p : getPlayerAlive()) {
						
						if (LocationUtil.getDistanceBetween(p, player) < 20) {
							
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
							PlayerUtil.particle(p.getLocation(), color, "ok", 1);
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
					player.roleIn.giveNightEffect();
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
	
	
	/*
	 * refresh the board of every player
	 * */
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
	
	
	
	/*
	 * accusation, obsolète
	 * */
	@Deprecated
	public void accusation(PlayerData accuser, PlayerData accused) {
		
		this.broadcoast("Le joueur "+ accuser.getName() + " accuse le joueur "+ accused.getName() + " publiquement, il a 10 minutes pour prouver sa culpabilité sous peine de perdre 1.5 coeurs permanents, si celui-ci se révèle innocent, il perdra 3 coeurs permanents et son droit de vote.");
		//CAUSE ORAT
		
		this.addorat(10, accuser.getLocation());
		//CONSEqUENCE orAT
		if (!isRegistresActivated|| getOrat() > 50) {
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
					broadcoast("Le joueur "+ accuser.getName() + " n'a pas pu prouver la culpabilité du joueur "+ accused.getName() + " à temps, il perd donc 1.5 coeurs permanents");
					addTragic(10, accuser.getLocation());
				} else {
					if (accused.considVill) {
						broadcoast("Le joueur "+ accuser.getName() + " a accusé à tord le joueur "+ accused.getName() + ", il perd donc 3 coeurs permanents et son droit de vote.");
						accuser.setMaxHealth(accuser.getMaxHealth() - 3);
						cannotBeVoted.add(accuser);
					} else {
						broadcoast("Le joueur "+ accuser.getName() + " a accusé à tord le joueur "+ accused.getName() + " à raison, il gagne donc 10% de force supplémentaires.");
						accuser.boostS5 += 2;
						addEpic(15, accuser.getLocation());
					}
				}
			}
			
		}, 12000);
	}
	
	
	
	
	/*
	 * remove a player from the vote list of every player*/
	public void removeFromVote(PlayerData p) {
		for (PlayerData player:this.getPlayerAlive()) {
			if (p.canVoted.contains(player)) {
				player.canVoted.remove(p);
				
			}
			
		}
		broadcoast(ChatColor.RED+"Le joueur "+ p.getName() + " a fuit la justice des villageois. Il ne pourra alors ni être voté ni voter.");
		for (PlayerData ply: this.getPlayerAlive()) {
			ply.sendMessage(ChatColor.RED+"Le joueur fuyard se trouve à " + ply.getLocation().distance(p.getLocation()) + " blocs de vous.");
		}
	}
	
	
	/*
	 * 
	 * tell a message to everyone*/
	public void broadcoast(String message ) {
		
		for (PlayerData p:this.getPlayerAlive()) {
			p.sendMessage(message);
			
		}
	}
	
	public ArrayList<RolesLg> getRoles() {
		return this.roles;
	}
	
	
	
	/*
	 * run the lg chat*/
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
	
	
	
	/*
	 * give a role to all players*/
	public void attributeRoleToAll() {
		if (this.roles.size() == this.playerAlive.size() && !swapper) {
			ArrayList<RolesLg> exe = this.getRoles();
			for (PlayerData player : this.playerAlive) {
				if (player.settedRole != null) {
					int ik = -1;
					for (RolesLg role:exe) {
						if (player.settedRole.equals(role)) {
							ik = exe.indexOf(role);
						}
					}
					if (ik > -1) {
						player.applyLgRole(exe.get(ik));
						this.roles.add(exe.get(ik));
						exe.remove(ik);
					}
					
				}
			}
			
			
			this.roles = new ArrayList<RolesLg>();
			for (PlayerData player : this.playerAlive) {
				if (player.role == null) {
					int number = MathUtil.generateAlInt(0, exe.size()-1);
					RolesLg role = exe.get(number);
					player.applyLgRole(role);
				
				
				
					this.roles.add(role);
					exe.remove(role);
				}
				
				
			}
			
			
		}
		
		if (swapper) {
			for (PlayerData p:playerAlive) {
				p.applyLgRole(RolesLg.SWAPPER);
			}
			if (swapperDuel) {
				ArrayList<PlayerData> cop = new ArrayList<PlayerData>();
				for (PlayerData p:playerAlive) {
					cop.add(p);
				}
				int r = 0;
				int b = 0;
				int tri = 0;
				while (cop.size() > 0 && tri < 100) {
					if (r<=b) {
						r++;
						cop.get(0).roleIn = new SWAPPER(cop.get(0), Camp.RED);
					} else {
						b++;
						cop.get(0).roleIn = new SWAPPER(cop.get(0), Camp.BLUE);
					}
					cop.remove(0);
					tri ++;
				}
			} else if (swapperTrio) {
				ArrayList<PlayerData> cop = new ArrayList<PlayerData>();
				for (PlayerData p:playerAlive) {
					cop.add(p);
				}
				int r = 0;
				int b = 0;
				int ro = 0;
				int tri = 0;
				while (cop.size() > 0&& tri < 100) {
					if (r <= b && r <= ro) {
						r++;
						cop.get(0).roleIn = new SWAPPER(cop.get(0), Camp.RED);
					} else if (b <= ro) {
						b++;
						cop.get(0).roleIn = new SWAPPER(cop.get(0), Camp.BLUE);
					} else {
						ro++;
						cop.get(0).roleIn = new SWAPPER(cop.get(0), Camp.PINK);
					}
					cop.remove(0);
					tri ++;
				}
				
			} else if (swapperQuadrio) {
				ArrayList<PlayerData> cop = new ArrayList<PlayerData>();
				for (PlayerData p:playerAlive) {
					cop.add(p);
				}
				int r = 0;
				int g = 0;
				int b = 0;
				int ro = 0;
				int tri = 0;
				while (cop.size() > 0&& tri < 100) {
					if (g <= r && g<=b && g<=ro) {
						g++;
						cop.get(0).roleIn = new SWAPPER(cop.get(0), Camp.GREEN);
					} else if (r <= b && r <= ro) {
						r++;
						cop.get(0).roleIn = new SWAPPER(cop.get(0), Camp.RED);
					} else if (b <= ro) {
						b++;
						cop.get(0).roleIn = new SWAPPER(cop.get(0), Camp.BLUE);
					} else {
						ro++;
						cop.get(0).roleIn = new SWAPPER(cop.get(0), Camp.PINK);
					}
					cop.remove(0);
					tri ++;
				}
				
			} else if (swapperPente) {
				ArrayList<PlayerData> cop = new ArrayList<PlayerData>();
				for (PlayerData p:playerAlive) {
					cop.add(p);
				}
				int r = 0;
				int g = 0;
				int y = 0;
				int b = 0;
				int ro = 0;
				int tri = 0;
				while (cop.size() > 0&& tri < 100) {
					if (y <= g   &&   y<=r    &&    y<=g   &&  y <= b  &&   y <= ro) {
						cop.get(0).roleIn = new SWAPPER(cop.get(0), Camp.YELLOW);
						y++;
					} else if (g <= r && g<=b && g<=ro) {
						g++;
						cop.get(0).roleIn = new SWAPPER(cop.get(0), Camp.GREEN);
					} else if (r <= b && r <= ro) {
						r++;
						cop.get(0).roleIn = new SWAPPER(cop.get(0), Camp.RED);
					} else if (b <= ro) {
						b++;
						cop.get(0).roleIn = new SWAPPER(cop.get(0), Camp.BLUE);
					} else {
						ro++;
						cop.get(0).roleIn = new SWAPPER(cop.get(0), Camp.PINK);
					}
					cop.remove(0);
					tri ++;
				}
			}
			
		}
		
		
		for (PlayerData player: this.getPlayerAlive()) {
			this.setRole(player, player.role);
			
			player.sendMessage("Vous êtes "+player.roleIn.getName());
			player.sendMessage(ChatColor.GOLD +player.roleIn.getDescription());
			player.sendMessage(ChatColor.GOLD+"Aura: "+ player.role.aura.getName());
			this.rolesIn.add(player.roleIn);
			
		}
		
		
	}
	
	
	/*
	 * 
	 * get the roles alives*/
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
		
		
		if (timer.temps>1300 && !swapper) {
			this.startVote();
			this.setChat();
			if (!hasLgSolo &&getRealWolfAlive().size() > 0 && MathUtil.pourcentage(probasEvents.getOrDefault("Loup Solitaire", 0))) {
				PlayerData p = getRealWolfAlive().get(MathUtil.generateAlInt(0, getRealWolfAlive().size() - 1));
				p.sendMessage(ChatColor.DARK_RED+"Vous devenez loup solitaire, vous devez maintenant gagner tout seul, pour cela vous gagnez 4 coeurs permanents, et 5% de résistance.");
				
				p.camp = Camp.Other;
				p.changeHealth(8);
				p.boostR5 ++;
			}
		}
		
		this.decideTimeEvent();
		this.setEpisodeTime();
		
		
		
		setGroupsTo(getNumberOfPlayer() / 5);
		
		Registre r = getRegister();
		if (r != null && r.getType() != null &&r.getType().equals(RegisterType.Tragic)) {
			if (MathUtil.pourcentage(r.getTaux()) && getGroupe() > 2) {
				setGroupsTo(getGroupe() - 1);
			}
			if (MathUtil.pourcentage(probasEvents.get("Exposed"))&&MathUtil.pourcentage(r.getTaux())) {
				exposed(GameLgUtil.getAlPlayer(this), 4);
			}
		}
		if (r != null && r.getType() != null &&r.getType().equals(RegisterType.Oratoire)) {
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
		
		
		PlayerData toAdd;
		if (isBanned.getOrDefault(name, false)) {
			return;
		}
		if (Main.strToPlayer.getOrDefault(name, null) != null) {
			toAdd=Main.strToPlayer.get(name);
			
			
		} else {
			if (PlayerUtil.getPlayer(name) != null) {
				toAdd = new PlayerData(PlayerUtil.getPlayer(name));
				Main.strToPlayer.put(name, toAdd);
			} else {
				System.out.println("joueur " + name + " non existant ou connecté");
				return;
			}
			
		}
		if (toAdd.isFree() == false) {
			toAdd.sendMessage("Vous ne pouvez pas rejoindre cette partie car vous êtes en partie, ou vous l'avez déjà rejointe");
			return;
		}
		toAdd.clearLgGameVar();
		if (toAdd.board == null) {
			toAdd.board = new ScoreboardLg(this, toAdd);
		}
		toAdd.board.refresh();
		toAdd.game = this;
		toAdd.isInLgGame = true;
		
		this.playerAlive.add(toAdd);
		this.players.add(toAdd);
		toAdd.sendMessage(ChatColor.DARK_RED+ "Ajouté à la game "+ name + " ("+ChatColor.GREEN+"type LoupGarou Uhc"+ChatColor.DARK_RED+ ")");
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
		
		
		for (PlayerData player: this.getPlayerAlive()) {
			
			if (player.camp.equals(Camp.Wolf)) {
				
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
			if (player.considWolf) {
				returneds.add(player);
			}
		}
		return returneds;
	}
	
	
	
	
	
	public void removePlayer(PlayerData ply, String spec) {
		playerAlive.remove(ply);
		players.remove(ply);
		ply.clearLgGameVar();
		ply.game = null;
		ply.isInLgGame = false;
		System.out.println(ply.getName() +" removed at "+ spec);
	}
	
	
	public void setRole(PlayerData p, RolesLg role) {
		p.setMaxHealth(20);
		if (!role.equals(RolesLg.SWAPPER)) {
			p.roleIn = RoleUtil.createRoleOfPlayerRoles(p);
		}
		
		if (p.isOnline) {
			
			p.roleIn.giveRoleEffectAndItem(p);
		}
		p.role = role;
		
		
		p.considVill = role.isConsidVill();
		p.considWolf = role.isConsidWolf();
		p.camp = role.getCampOfRole();
		p.appCamp = role.getCampOfRole();
	}
	
	
	@Deprecated
	public void removeDiedPlayer(PlayerData ply) {
		if (ply.role.equals(RolesLg.CHASSEUR)) {
			CHASSEUR hunter = (CHASSEUR) ply.roleIn;
			
			
			ply.sendMessage(ChatColor.GOLD+"Vous avez 25 secondes pour tirer sur un joueur de votre choix avec la commande /lg tirer [nomDuJoueur], celui perdra 3 coeurs de manière non permanente, ainsi que sa force s'il est loup");
			Bukkit.getScheduler().runTaskLater(Main.plug, new BukkitRunnable() {

				@Override
				public void run() {
					hunter.playerWithRole.sendMessage("Trop tard...");
					
					playerAlive.remove(ply);
					roles.remove(ply.role);
					rolesIn.remove(ply.roleIn);
					ply.player.getInventory().clear();
					ply.inLife = false;
					ply.clearLgGameVar();
					Hub.sendHub(ply);
				}
				
			}, 500);
		}  else {
			this.removePlayer(ply, "at hunter death");
			Hub.sendHub(ply);
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
		
		
	
		for (SpecialBlock bloc:Main.specialBlocks) {
			if (bloc.getData() != null && bloc.getData().getType() !=null  &&bloc.getData().getType().equals(SpecialBlockType.Vote)) {
				if (bloc.game.getName().equals(getName())) {
					
					((VoteBlockData) bloc.getData()).setVoteParam();
					((VoteBlockData) bloc.getData()).launch();
				}
					
			}
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
				if (mostVoted != null && mostVoted.vote > 2) {
					addorat(3*mostVoted.vote, mostVoted.getLocation());
				}
				
				
				boolean corb = false;
				if (mostVoted.vote > 0 && !equal) {
					
					//CONSESQUENCE EPIC
					int plus = 0;
					int exposedPlus = 0;
					if (isRegistresActivated) {
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
					
					
					broadcoast(ChatColor.GOLD + "Le joueur " + ChatColor.DARK_AQUA + mostVoted.Name +ChatColor.GOLD+ "a été le plus voté" );
					for (PlayerData player:playerAlive) {
						
						
						if (player.voted.Name.equals(mostVoted.Name)) {
							player.canVoted.remove(mostVoted);
							
							if (player.role.equals(RolesLg.CORBEAU)) {
								broadcoast(ChatColor.BLACK+"Le corbeau a voté avec le village");
								corb = true;
								addorat(5, player.getLocation());
								CORBEAU corbeau = (CORBEAU) player.roleIn;
								corbeau.addGoodVoted();
							}
						}
					}
					
					
				} else {
					broadcoast(ChatColor.GOLD +"Aucun joueur n'a été voté plus de 2 fois, ou il y a une égalité");
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
			
		}, 3600);
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

	
	/*Premonition event, à refaire mal fait*/
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
			return;
		}
		announceDeath(player1, player1.role, brumed);
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

		GameLg gm1 = (GameLg) player1.game;
		if (gm1 == null || gm1.name != this.name) {
			return;
		}
		
		if (MathUtil.pourcentage(probasEvents.get("Brume"))) {
			System.out.println("brume activated");
			return;
		}

		Main.server.broadcastMessage(ChatColor.DARK_BLUE +"___________________________" + "\n" +
							color + player1.getName() + " est mort |"+ "\n"  +
								" il était "+ role.getCampOfRole().getColor() + 
								role.getName() + ChatColor.GOLD + moreInfo + "|" + "\n"+ChatColor.DARK_BLUE +"___________________________");
		
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
	
	
	

	
/*
 * check for pf and perfide
 * */	
	@EventHandler
	public void onPlayerClick(InventoryClickEvent e) {
		
		
		
		Player sender = (Player) e.getWhoClicked();
		
		
		
		
		//CHECK FOR LITTLE GIRL
		if (Main.strToPlayer.getOrDefault(sender.getName(), null) != null && Main.strToPlayer.get(sender.getName()).game != null) {
			if (Main.getData(e.getWhoClicked()).role == RolesLg.PETITE_FILLE) {
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
				
				
				PETITE_FILLE little = (PETITE_FILLE) Main.getData(e.getWhoClicked()).roleIn;
				
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
		if (Main.strToPlayer.getOrDefault(sender.getName(), null) != null && Main.strToPlayer.get(sender.getName()).game != null) {
			if (Main.getData(e.getWhoClicked()).role == RolesLg.PETITE_FILLE) {
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
				
				
				PETITE_FILLE little = (PETITE_FILLE) Main.getData(e.getWhoClicked()).roleIn;
				
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
		
		broadcoast(ChatColor.DARK_RED+Main.exclamation+ "  Expose  " + ChatColor.WHITE+Main.info+"  Le Role du joueur "+ player.getName() + " se trouve parmi les suivant: ");
		for (String str:rolesStr) {
			broadcoast(ChatColor.GRAY+"-"+str);
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

		
		broadcoast(ChatColor.DARK_RED+ Main.exclamation+"  Expose:  "+ "\n" + ChatColor.WHITE+Main.info+"  Les Roles des joueurs suivants se trouvent parmis les suivants: ");
		for (PlayerData p:players) {
			broadcoast(ChatColor.DARK_GREEN+"-"+p.getName() );
		}
		broadcoast(ChatColor.DARK_GREEN+";   ");

		int x = MathUtil.generateAlInt(0, rolesStr.size() - 1);
		do {
			String str = rolesStr.get(x);
			broadcoast(ChatColor.GOLD+"-"+str);
			rolesStr.remove(x);
			if (rolesStr.size() > 0) {
				x = MathUtil.generateAlInt(0, rolesStr.size() - 1);
			}
			
		}while (rolesStr.size() > 0);
		
		
		
	}
	
	public void addTragic(int toAdd, Location loc) {

		if (!isRegistresActivated) {
			return;
		}
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
		if (!isRegistresActivated) {
			return;
		}
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
		if (!isRegistresActivated) {
			return;
		}
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
		if (!isRegistresActivated) {
			return 0;
		}
		return this.tragicTaux;
	}
	public int getOrat() {
		if (!isRegistresActivated) {
			return 0;
		}
		return this.oratTaux;
	}
	public int getEpic() {
		if (!isRegistresActivated) {
			return 0;
		}
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
	
	public void checkWin() {
		if (this.statut.equals(GameStatut.ENDED)) {
			return;
		}
		
		if (allDifferent) {
			if (playerAlive.size() == 1) {
				win(playerAlive.get(0).camp);
				
			}
			return;
		}
		Camp winning = null;
		for (PlayerData p:this.getPlayerAlive()) {
			if (winning == null) {
				winning = p.camp;
			} else {
				if ((!winning.equals(p.camp) && !p.camp.equals(Camp.Uneffective)) || (winning.equals(Camp.Other) && this.getPlayerAlive().size() > 1)) {
					return;
				}
			}
			
			
		}
		
		if (winning != null) {
			if (allDifferent && winning.equals(Camp.Villager) || winning.equals(Camp.Wolf))
			win(winning);
			this.statut = GameStatut.ENDED;
		}
	}
	
	public void win(Camp camp) {
		
		
		broadcoast("Le camp "+ camp.getName() + " a gagné.");
		for (PlayerData p:players) {
			String str = p.getName() + ": "+ p.getLgRole().getName();
			if (p.infected) {
				str = str + " infecté";
			}
			if (p.inLove) {
				str = str+" en couple";
			}
			broadcoast(str);
			String sup = "";
			if (necrom ) {
				sup = sup+ ChatColor.DARK_PURPLE+"-Necromancie-";
			}
			if (allDifferent) {
				sup = sup + ChatColor.GOLD+ "-PvP Game-";
			}
			if (swapper) {
				sup = sup + ChatColor.BLUE + "-Swapper-";
			}
			
			if (toRegister) {
				Main.strToPlayer.get(p.getName()).notes.add(new GameNote(this, this.name + sup));
				p.sendMessage("Partie ajoutée à votre historique");
				int gain = 0;
				
				gain += p.getLgRole().winValue;
				if (isMeetup) {
					gain /= 2;
				}
				if (allDifferent && p.camp.equals(camp)) {
					gain += 10;
				}
				if (displayedRoles ) {
					gain /= 5;
				}
				p.xp += gain;
			}
			
			
			p.clearLgGameVar();
			p.player.getInventory().clear();
			Hub.sendHub(p);
			
		}
		
		Bukkit.unloadWorld(this.world, false);
			
		this.world.getWorldFolder().delete();
		try {
			Main.deleteDirectory(world.getWorldFolder());
		} catch (Exception e) {
			// TODO: handle exception
		}
		this.stopped = true;
		this.statut = GameStatut.ENDED;
		
		
		
	}
	
	public boolean isDay() {
		if (WorldUtil.getTime(Main.server.getWorld("world")).equals("day")) {
			return true;
		} else {
			return false;
		}
	}
	
	
	//OBSOLETE
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
						if (player.considVill) {
							e++;
						} else if (player.considWolf) {
							e += 10;
						} else {
							e+=100;
						}
						
						break;
					case Oratoire:
						if (player.considVill) {
							o++;
						} else if (player.considWolf) {
							o += 10;
						} else {
							o+=100;
						}
						break;
					case Tragic:
						if (player.considVill) {
							t++;
						} else if (player.considWolf) {
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
		SpecialBlock b = new SpecialBlock(new Location(world, x, y+1, z), SpecialBlockType.Treasure, data, this, "ok");
		Main.specialBlocks.add(b);
	}

	@Override
	public ArrayList<PlayerData> getWinners() {
		// TODO Auto-generated method stub
		return getPlayerAlive();
	}

	@Override
	public ArrayList<PlayerData> getPlayers() {
		// TODO Auto-generated method stub
		return players;
	}

	@Override
	public GameType getType() {
		// TODO Auto-generated method stub
		return gameType;
	}

	@Override
	public World getWorld() {
		// TODO Auto-generated method stub
		return world;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return this.name;
	}

	@Override
	public void setWorld(World world) {
		this.world = world;
		
	}

	@Override
	public GameListener getListener() {
		// TODO Auto-generated method stub
		return listener;
	}

	@Override
	public int getMaxNBOfPlayer() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getActualNbOfPlayer() {
		if (statut.equals(GameStatut.NOT_STARTED)) {
			return this.players.size();
		}
		return this.playerAlive.size();
	}

	@Override
	public void playerQuit(String name) {
		if (!board.istimeRunned) {
			this.players.remove(Main.getData(name));
		}
		
	}

	@Override
	public void playerDefinitlyQuit(String playerName) {
		this.announceDeath(Main.getData(playerName), true, false);
		
	}
	
	
	
}
