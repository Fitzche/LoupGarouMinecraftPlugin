package fr.fitzche.lgmore;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.craftbukkit.v1_8_R3.command.ServerCommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.player.PlayerAchievementAwardedEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.ServiceRegisterEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.RegisteredListener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.inventivetalent.particle.ParticlePlugin;


import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitUtil;
import com.sk89q.worldedit.bukkit.WorldEditAPI;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.bukkit.adapter.BukkitImplAdapter;
import com.sk89q.worldedit.world.registry.WorldData;

import StarParty.RolesStar;
import StarParty.StarParty;
import StarParty.Role.DarkVador;
import StarParty.Role.Obiwan;
import StarParty.Role.Palpatine;
import WorldEditUtil.StructureLoader;
import de.inventivegames.particle.ParticleEffect;
import fr.fitzche.flagCapture.Flag;
import fr.fitzche.flagCapture.FlagTab;
import fr.fitzche.flagCapture.Main2;
import fr.fitzche.lgmore.InfinityStones.Stone;
import fr.fitzche.lgmore.InfinityStones.StonesType;
import fr.fitzche.lgmore.Lg.GameLg;
import fr.fitzche.lgmore.Lg.SpecialsBlock.AccuseBlockData;
import fr.fitzche.lgmore.Lg.SpecialsBlock.CauldronBlockData;
import fr.fitzche.lgmore.Lg.SpecialsBlock.SpecialBlock;
import fr.fitzche.lgmore.Lg.SpecialsBlock.SpecialBlockType;
import fr.fitzche.lgmore.Lg.SpecialsBlock.StoneBlockData;
import fr.fitzche.lgmore.Lg.SpecialsBlock.TreasureBlockData;
import fr.fitzche.lgmore.Lg.SpecialsBlock.TreasureBlockType;
import fr.fitzche.lgmore.Lg.SpecialsBlock.VoteBlockData;
import fr.fitzche.lgmore.Minage.Trades;
import fr.fitzche.lgmore.RolesLg.ANCIEN;
import fr.fitzche.lgmore.RolesLg.ENFANT_SAUVAGE;
import fr.fitzche.lgmore.RolesLg.IDIOT_DU_VILLAGE;
import fr.fitzche.lgmore.RolesLg.LOUP_MYSTIQUE;
import fr.fitzche.lgmore.RolesLg.RoleDisplay;
import fr.fitzche.lgmore.RolesLg.RolesLg;
import fr.fitzche.lgmore.RolesLg.SOEUR;
import fr.fitzche.lgmore.RolesLg.THANOS;
import fr.fitzche.lgmore.Util.CommandUtil;
import fr.fitzche.lgmore.Util.GameLgUtil;
import fr.fitzche.lgmore.Util.ItemUtil;
import fr.fitzche.lgmore.Util.LineLocationHelper;
import fr.fitzche.lgmore.Util.LineRapport;
import fr.fitzche.lgmore.Util.LocationUtil;
import fr.fitzche.lgmore.Util.MathUtil;
import fr.fitzche.lgmore.Util.PlayerUtil;
import fr.fitzche.lgmore.Util.RoleUtil;
import fr.fitzche.lgmore.bedwars.Bedwars;
import fr.fitzche.lgmore.bedwars.CW;
import fr.fitzche.lgmore.clocktower.ClockCommand;
import fr.fitzche.lgmore.clocktower.ClockTower;
import fr.fitzche.lgmore.commands.Lga;
import fr.fitzche.lgmore.commands.Rejoin;
import fr.fitzche.lgmore.commands.SpecialItemHolder;
import fr.fitzche.lgmore.commands.Star;
import fr.fitzche.lgmore.custom.CustomGame;
import fr.fitzche.lgmore.custom.CustomGameType;
import fr.fitzche.lgmore.commands.Hub;
import fr.fitzche.lgmore.commands.Lg;
import fr.fitzche.lgmore.commands.LgTab;
import fr.fitzche.lgmore.minecraft.GameListener;
import fr.fitzche.lgmore.minecraft.PlayerDataLeft;
import fr.fitzche.lgmore.minecraft.mcListeners;
import fr.fitzche.lgmore.scoreboard.DefaultBoard;
import fr.fitzche.lgmore.scoreboard.ScoreboardLg;
import fr.fitzche.lgmore.scoreboard.Inventory.GameTypeChoose;
import fr.fitzche.lgmore.scoreboard.Inventory.GeneralMenu;
import net.md_5.bungee.api.ChatColor;



public class Main extends JavaPlugin implements Listener {
	public static Server server;
	
	
	public static Location loc1;
	public static Location loc2;
	public static ArrayList<SpecialBlock> specialBlocks = new ArrayList<SpecialBlock>();
	
	public static WorldData worldData;		
	
	public static JavaPlugin plug;
	public static Permission lgop;
	public HashMap<String, Boolean> alreadyCo = new HashMap<String, Boolean>();
	public static mcListeners listeners;
	public static ArrayList<String> eventsLgNames = new ArrayList<String>();
	public static HashMap<String, String> descriptionsLgEvent = new HashMap<String, String>();
	public static World world;
	
	public static GameTypeChoose gameTypeChoseMenu = new GameTypeChoose();
	
	public static int decalageBatX = 6;
	public static int decalageBatY = 1;
	public static int decalageBatZ = 4;
	
	public final static ItemStack guillHead = ItemUtil.getCustomHead("TheGuill84");
	public final static ItemStack myHead = ItemUtil.getCustomHead("FITZCHE");


	public static final String defaultJsonRoleSTring = "{\r\n"
			+ "  \"name\": \"Joueur\",\r\n"
			+ "  \"camp\": \"Village\",\r\n"
			+ "  \"roleListName\": \"joueur\",\r\n"
			+ "  \r\n"
			+ "  \"strenght\": 1.0,\r\n"
			+ "  \"resistance\": 1.0,\r\n"
			+ "  \"boostHealth\": 0,\r\n"
			+ "\r\n"
			+ "  \"reliveTry\": 0,\r\n"
			+ "  \"lostStrenght\": 0.0,\r\n"
			+ "  \"lostResis\": 0.0,\r\n"
			+ "  \"lostHealth\": 0.0,\r\n"
			+ "\r\n"
			+ "  \"conditionKilledBy\": \"\",\r\n"
			+ "  \"conditionKilledByCamp\": \"\",\r\n"
			+ "\r\n"
			+ "  \"infoPowers\": []\r\n"
			+ "}\r\n"
			+ "";
	
	public static HashMap<String, GameLg> strToGame = new HashMap<String, GameLg>();
	
	public static HashMap<String, PlayerData> strToPlayer = new HashMap<String, PlayerData>();
	public static ArrayList<GameListener> gameListeners = new ArrayList<GameListener>();
	
	
	public static ArrayList<StarParty> parties = new ArrayList<StarParty>();
	public static ArrayList<Bedwars> bedwars = new ArrayList<Bedwars>();
	public static ArrayList<ClockTower> clocks = new ArrayList<ClockTower>();
	
	public static FixedMetadataValue unbreakableMeta = null;
	public static FixedMetadataValue makoraMeta = null;
	
	public static HashMap<CustomGameType, ArrayList<CustomGame>> games = new HashMap<CustomGameType, ArrayList<CustomGame>>();

	
	
	
	//STRING HELP
	public static String exclamation = "" +ChatColor.GOLD+"["+ChatColor.RED+ "!"+ ChatColor.GOLD+"] ";
	public static String info = "" +ChatColor.GOLD+"["+ChatColor.GREEN+ "➤➤"+ ChatColor.GOLD+"] ";
	public static String lgmoreMark = "" +ChatColor.GOLD+"["+ChatColor.GREEN+ ChatColor.BOLD+"LgMore"+ChatColor.RESET+ ChatColor.GOLD+"] ";
	
	
	
	public JavaPlugin getPlugin() {
		return this;
	}
	
	
	public static PlayerData getData(Player p) {
		return strToPlayer.getOrDefault(p.getName(), null);
	}
	public static PlayerData getData(CommandSender p) {
		return strToPlayer.getOrDefault(p.getName(), null);
	}
	public static PlayerData getData(String name) {
		return strToPlayer.getOrDefault(name, null);
	}
	
	public static String playersDataFilePath = "lgData/playersData";


	private static HashMap<String, PlayerDataLeft> playersLeft = new HashMap<String, PlayerDataLeft>();


	public static ArrayList<String> dispoGamemodes = new ArrayList<String>();


	public static String defaultJsonGamemodeString = "{\r\n"
			+ "  \"hasMinageinage\": false,\r\n"
			+ "  \"minageTime\": 10,\r\n"
			+ "  \"boostMinage\": 1,\r\n"
			+ "\r\n"
			+ "  \"roleListNames\": [\r\n"
			+ "    {\r\n"
			+ "      \"listName\": \"unreferenced\",\r\n"
			+ "      \"timeApplication\": 10\r\n"
			+ "    }\r\n"
			+ "  ]\r\n"
			+ "}\r\n"
			+ "";


	public static HashMap<String, String> clicTexts = new HashMap<String, String>();


	
	@SuppressWarnings("unchecked")
	@Override
	@Deprecated
	public void onEnable() {
		// TODO Auto-generated method stub
		super.onEnable();
		
		WorldCreator worldStarCreator = new WorldCreator("worldStar");
		World world = Bukkit.createWorld(worldStarCreator);
		
		WorldCreator worldBedC = new WorldCreator("bedMap");
		World worldBed = Bukkit.createWorld(worldBedC);
		File file = new File(playersDataFilePath);
		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
		
		
		
		
		
		if (!file.exists()) {
			System.out.println("playersData file doesn't exist, creating it");
			try {
				file.createNewFile();
				System.out.println("creation of playersData file achieved");
			} catch (IOException e) {
				System.out.println("creation of playersData file not achieved");
				e.printStackTrace();
			}
		} else {
			try {
				System.out.println("import of playersData start");
				FileInputStream input = new FileInputStream(file);
				ObjectInputStream in = new ObjectInputStream(input);
				strToPlayer = (HashMap<String, PlayerData>) in.readObject();
				in.close();
				input.close();
				System.out.println("import of playersData achieved");
				
				for (Map.Entry<String ,PlayerData> entry:strToPlayer.entrySet()) {
					System.out.println(entry.getKey() + " for "+entry.getValue().getName());
				}
			} catch (FileNotFoundException e) {
				System.out.println("import of playersData not achieved");
				e.printStackTrace();
			} catch (IOException e) {
				System.out.println("import of playersData not achieved");
				
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				System.out.println("import of playersData not achieved");
				e.printStackTrace();
			}
		}
		
		File gamemodes = new File("Gamemodes");
		if (!gamemodes.exists()) {
			gamemodes.mkdirs();
		} else {
			for (File gamemode:gamemodes.listFiles()) {
				this.dispoGamemodes.add(gamemode.getName());
			}
		}
		
		Trades.initialize();
		lgop = new Permission("lgop");
		
		Main.server = this.getServer();
		Main.plug = this.getPlugin();
		
		getCommand("lga").setExecutor(new Lga());
		getCommand("lga").setTabCompleter(new LgTab());
		getCommand("lg").setExecutor(new Lg());
		
		getCommand("flag").setExecutor(new Flag());
		getCommand("flag").setTabCompleter(new FlagTab());
		
		getCommand("star").setExecutor(new Star());
		
		getCommand("hub").setExecutor(new Hub());
		getCommand("rejoin").setExecutor(new Rejoin());
		getCommand("cw").setExecutor(new CW());
		
		getCommand("clock").setExecutor(new ClockCommand());
		
		getCommand("color").setExecutor(new fr.fitzche.lgmore.uhc_color.Color());
		
		mcListeners listener = new mcListeners();
		this.listeners = listener;
		
		Main.worldData = BukkitUtil.getLocalWorld(Main.world).getWorldData();
		
		getServer().getPluginManager().registerEvents(this, this);
		getServer().getPluginManager().registerEvents(listener, this);
		Main2 main2 = new Main2();
		getServer().getPluginManager().registerEvents(main2, this);
		
		
		Main.world = getServer().getWorld("world");
		
		this.unbreakableMeta = new FixedMetadataValue(plug, "unbreakable");
		this.makoraMeta = new FixedMetadataValue(plug, "makora");
		
		
	    
	    for (Player p:Bukkit.getOnlinePlayers()) {
	    	if (strToPlayer.getOrDefault(p.getName(), null) == null) {
	    		strToPlayer.put(p.getName(), new PlayerData(p));
	    		System.out.println("put new PlayerData: "+ p.getName());
	    	} else {
	    		strToPlayer.get(p.getName()).player = p;
	    		
	    	}
	    }
		
	    
	
	   
		ArrayList<RolesLg> list = new ArrayList<RolesLg>(Arrays.asList(
				RolesLg.ALLUMEUR,
				RolesLg.ANCIEN,
				RolesLg.ANGE,
				RolesLg.ASSASSIN,
				RolesLg.LOUP_SANGUINAIRE,
				RolesLg.BIENFAITEUR,
				RolesLg.CHASSEUR,
				RolesLg.CORBEAU,
				RolesLg.CUPIDON,
				RolesLg.DISCIPLE,
				RolesLg.ENFANT_SAUVAGE,
				RolesLg.IDIOT_DU_VILLAGE,
				RolesLg.INFECT_PERE_DES_LOUPS,
				RolesLg.INTERPRETE,
				RolesLg.LOUP_ALCHIMISTE,
				RolesLg.LOUP_BARBARE,
				RolesLg.LOUP_GRIMEUR, 
				RolesLg.LOUP_MANIP,
				RolesLg.TRAQUEUR,
				RolesLg.LOUP_HURLEUR,
				RolesLg.LOUP_METAMORPHE, 
				RolesLg.LOUP_MYSTIQUE, 
				RolesLg.SORCIER,
				RolesLg.MONTREUR, 
				RolesLg.PARRAIN,
				RolesLg.PERFIDE, 
				RolesLg.PETITE_FILLE,
				RolesLg.PYROMANE, 
				RolesLg.RENARD, 
				RolesLg.SAGE, 
				RolesLg.ERMITE,
				RolesLg.LOUP_CRAINTIF,
				RolesLg.COMEDIEN,
				RolesLg.SALVATEUR, 
				RolesLg.SERVANT_DES_LOUPS, 
				RolesLg.SIMPLE_VILLAGER, 
				RolesLg.NECROMANCIEN,
				RolesLg.SIMPLE_WOLF,
				RolesLg.SOEUR,
				RolesLg.NEGOCIATEUR,
				RolesLg.SORCIERE, 
				RolesLg.VOLEUR,
				RolesLg.ANGE_THIERCE,
				RolesLg.LOUP_BRUMEUX,
				RolesLg.DEMON,
				RolesLg.THANOS,
				RolesLg.FAUCONNIER,
				RolesLg.SWAPPER,
				RolesLg.VOYANTE,
				RolesLg.ANALYSTE,
				RolesLg.ARAIGNEE
				
				));
		RoleUtil.existingRoles.addAll(list);

		eventsLgNames.add("Exposed");
		descriptionsLgEvent.put("Exposed","A chaque épisode, un exposed de 4 role a un pourcentagede chance de se produire en fonction du taux de tragique (exemple: 20pts de tragique = 20% de chance), ce pourcentage est la chance que l'exposed se produise ou pas, passe avant la probabilité du taux de targique (ex: event à 20%, et 60 pts de tragique = 60% de 20% = 12% de chance) L'exposed affiche 4 role dans le chat dont le nom du joueur choisi au hasard pour l'exposed.");

		eventsLgNames.add("Brume");
		descriptionsLgEvent.put("Brume", "Probabilité à la mort d'un joueur, que le message de mort soit caché au village");

		eventsLgNames.add("Premonition");
		descriptionsLgEvent.put("Premonition", "Probabilité que dans un épisode, un joueur obtienne un présentiment sur son entourage");

		eventsLgNames.add("Trouple");
		descriptionsLgEvent.put("Trouple", "Chance que le couple soit...un trouple");

		eventsLgNames.add("Mal visé");
		descriptionsLgEvent.put("Mal visé", "probabilité que le cupidon vise mal et se mette en couple avec un joueur aléatoire");

		eventsLgNames.add("Loup Solitaire");
		
		descriptionsLgEvent.put("Loup Solitaire", "Probabilité à un épisode qu'un loup devienne solitaire, ne peut apparaitre qu'une seule fois");
		
		eventsLgNames.add("Couple aléatoire");
		descriptionsLgEvent.put("Couple aléatoire", "Probabilité que le couple ne soit pas choisi par le cupidon mais de manière aléatoire");
		
		eventsLgNames.add("Nombre Batiments Leurre");
		descriptionsLgEvent.put("Nombre Batiments Leurre", "Nombres de batiments n'ayant aucun interet, ne pas trop elever");
		
		eventsLgNames.add("Nombre Batiments à Bonus");
		descriptionsLgEvent.put("Nombre Batiments à Bonus", "Nombres de batiments aynt un interet (à vous de découvrir lequel), ne pas trop elever");
		
		eventsLgNames.add("ProbaDeLaMortDeBoom");
		descriptionsLgEvent.put("ProbaDeLaMortDeBoom", "Probabilité que Boom soit assassin et meure dans les 10 min après l'obtention de son role :)");
		
		eventsLgNames.add("AutomaticCheckWin");
		descriptionsLgEvent.put("AutomaticCheckWin", "Probabilité que la victoire soit vérifiée à la mort d'un joueur (100% par défaut)");
		
		World bedWorld = Bukkit.getWorld("pvpWorld");
		Main2.onEnable();
		Bukkit.getPluginManager().registerEvents(new SpecialItemHolder(), plug);
		Bukkit.getScheduler().runTaskTimer(Main.plug, new BukkitRunnable() {
			
			@Override
			public void run() {
				for (Player p:Bukkit.getOnlinePlayers()) {
					if (strToPlayer.get(p.getName()).board != null) {
						strToPlayer.get(p.getName()).board.refresh();
					}
					
					if (p.getLocation().getY() < -1) {
						p.damage(300);
					}
				}
				
				
			}
			
		}, 20, 20);
		Bukkit.getScheduler().runTaskTimer(Main.plug, new BukkitRunnable() {
			
			@Override
			public void run() {
				Player p = null;
				CommandUtil.runCommand("lga", new ServerCommandSender() {
					
					@Override
					public void setOp(boolean arg0) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public boolean isOp() {
						// TODO Auto-generated method stub
						return true;
					}
					
					@Override
					public void sendMessage(String[] arg0) {
						for (String arg:arg0) {
							System.out.println("Rapport de Commande: " +arg);
						}
						
						
					}
					
					@Override
					public void sendMessage(String arg0) {
						System.out.println("Rapport de Commande: " +arg0);
						
					}
					
					@Override
					public String getName() {
						// TODO Auto-generated method stub
						return "console";
					}
				}, new String[] {" export"});
				
				
			}
			
		}, 20*60*10, 20*60*10);		
		
		
	}
	
	
	@EventHandler
	public void onAchievement(PlayerAchievementAwardedEvent event) {
	    event.setCancelled(true);
	}

	
	
	public static boolean sameGame(PlayerData a, PlayerData b) {
		if (a.game != null && b.game != null && b.game.getName().equals(a.game.getName())) {
			return true;
		} 
		return false;
	}
	@Override
	public void onDisable() {
		
		File file = new File(playersDataFilePath);
		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
		for (Map.Entry<String ,PlayerData> entry:strToPlayer.entrySet()) {
			System.out.println(entry.getKey() + " for "+entry.getValue().getName());
		}
		for (Map.Entry<String, GameLg> entry:strToGame.entrySet()) {
			try {
				
				
				World worldD = entry.getValue().world;
				if (entry .getValue() != null && entry.getValue().world != null && worldD != null) {
					Bukkit.unloadWorld(worldD, false);
			
					worldD.getWorldFolder().delete();
					Main.deleteDirectory(worldD.getWorldFolder());
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		for (Bedwars bedwar:bedwars) {
			try {
				
				
				World worldD = bedwar.getWorld();
				if (bedwar != null && bedwar.getWorld() != null && worldD != null) {
					Bukkit.unloadWorld(worldD, false);
			
					worldD.getWorldFolder().delete();
					Main.deleteDirectory(worldD.getWorldFolder());
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		if (!file.exists()) {
			System.out.println("playersData file doesn't exist, creating it");
			try {
				file.createNewFile();
				System.out.println("creating file achieved");
			} catch (IOException e) {
				System.out.println("creating file not achieved");
				e.printStackTrace();
			}
		}
		for (Map.Entry<String, PlayerData> entry:strToPlayer.entrySet()) {
			entry.getValue().clearLgGameVar();
			entry.getValue().player = null;
			entry.getValue().deathInventory = null;
			entry.getValue().board = null;
			entry.getValue().game = null;
			System.out.println("size of note of "+ entry.getValue().getName() + ": "+ entry.getValue().notes.size() + "against "+ strToPlayer.get(entry.getKey()).notes.size());
		}
		
		try {
			System.out.println("saving players Data");
			FileOutputStream stream = new FileOutputStream(file);
			ObjectOutputStream out = new ObjectOutputStream(stream);
			out.writeObject(strToPlayer);
			out.close();
			stream.close();
			System.out.println("saving players Data achieved");
		} catch (FileNotFoundException e) {
			System.out.println("saving players Data not achieved");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("saving players Data not achieved");
			e.printStackTrace();
		}
		
		super.onDisable();
	}
	
	@Deprecated
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		
		e.getPlayer().sendMessage(
			
			
			Main.info + "Vous rejoingnez un serveur équipé de lgmore. Ce plugin possède plusieurs mods de jeu aboutis... ou non (loup-garou uhc, team swapper, starWar party, bedwars. N'hésitez pas à aider en participant aux tests qui sont indispensables pour pouvoir corriger tous les bugs."+ "\n" +ChatColor.GOLD + ""+ ChatColor.ITALIC+ "Faites /lg help et /lg info pour avoir plus d'info sur le plugin lgmore, /lg whisper [message] pour envoyer un message non anonyme aux joueurs op. Bon jeu");
		
		e.getPlayer().sendMessage(ChatColor.DARK_PURPLE + "Faites /rejoin pour rejoindre une partie que vous n'avez pas finit");
		
		e.getPlayer().getInventory().clear();
		e.getPlayer().getInventory().setItem(0, ItemUtil.getItem(Material.BOOK, 1, ChatColor.UNDERLINE+"Navigation", new ArrayList<String>(Arrays.asList("Pour accéder au menu principal"))));
		Bukkit.broadcastMessage(e.getPlayer().getName() +" joined");
		if (e.getPlayer().getName().equals("TheGuill84")) {
			e.getPlayer().sendMessage("Hey, tu as rejoint un serveur avec le plugin de lg de Fitzche, fais /lga Game create [nomDeLaGame] pour créer un game, puis /lga Game config [nomDeLaGame] pour la config puis la start... ");
		}
		if (strToPlayer.getOrDefault(e.getPlayer().getName(), null) != null) {
			PlayerData p = strToPlayer.get(e.getPlayer().getName());
			if (p.left != null) {
				p.left.end();
			}
			
		}
		
		if (strToPlayer.getOrDefault(e.getPlayer().getName(), null) == null) {
			strToPlayer.put(e.getPlayer().getName(), new PlayerData(e.getPlayer()));
		} else {
			strToPlayer.get(e.getPlayer().getName()).player = e.getPlayer();
			strToPlayer.get(e.getPlayer().getName()).setDisplayName();
			strToPlayer.get(e.getPlayer().getName()).isOnline = true;
			strToPlayer.get(e.getPlayer().getName()).board = new DefaultBoard(strToPlayer.get(e.getPlayer().getName()));
			strToPlayer.get(e.getPlayer().getName()).board.refresh();
		}
		
		
		
		
	}
	
	public HashMap<String, Integer> hasQuitSince = new HashMap<String, Integer>();
	
	@Deprecated
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent e) {
		
		System.out.println("left");

		
		Bukkit.broadcastMessage(e.getPlayer().getName() +" left");
		PlayerData player = getData(e.getPlayer().getName());
		if (player.game == null) {
			System.out.println("game of "+ player.getName() + "null");
			return;
		}
		player.leftInv = e.getPlayer().getInventory();
		Game gm1 = player.game;
		gm1.playerQuit(player.getName());
		
		
		
		

		if (gm1 instanceof GameLg) {
			Main.playersLeft.put(player.getName(), new PlayerDataLeft(player));
			Bukkit.getScheduler().runTaskLater(Main.plug, new BukkitRunnable() {
				@Override
				public void run() {
					System.out.println("online?");	
					if (!player.isOnline && ((GameLg) gm1).timer.temps > 1199) {
						System.out.println(player.Name+ " not online");
						((GameLg) gm1).announceDeath(player, false, false);
					}
				}	
			}, 6000);	
		}
		
		
		Bukkit.getScheduler().runTaskLater(Main.plug, new BukkitRunnable() {

			@Override
			public void run() {
				if (!player.isOnline) {
					if (player.game != null) {
						player.game.playerDefinitlyQuit(player.getName());
					}
				}
				
				
				
			}
			
		}, 6000);
		
		
		
		
		
		
		player.player = null;
		player.isOnline = false;
		
		
	}
	public static void placeVoteStruct(Location loc, Game game) {
		loc.setWorld(game.getWorld());
		placeBat(loc, game);
		Location voteB = new Location(game.getWorld(), loc.getX()+Main.decalageBatX, loc.getY()+Main.decalageBatY, loc.getZ()+Main.decalageBatZ);
		placeVoteBlock(voteB, game);
	}
	public static void placeAccuseStruct(Location loc, Game game) {
		loc.setWorld(game.getWorld());
		placeBat(loc, game);
		Location voteB = new Location(game.getWorld(), loc.getX()+Main.decalageBatX, loc.getY()+Main.decalageBatY, loc.getZ()+Main.decalageBatZ);
		placeAccuseBlock(voteB, game);
	}
	public static void placeTreasureStruct(Location loc, TreasureBlockType type, GameLg game) {
		loc.setWorld(game.world);
		placeBat(loc, game);
		Location specialB = new Location(game.world, loc.getX()+Main.decalageBatX, loc.getY()+Main.decalageBatY, loc.getZ()+Main.decalageBatZ);
		placeTreasureBlock(specialB, type, game);
	}
	public static void placeCauldronStruct(Location loc, Game game) {
		loc.setWorld(game.getWorld());
		placeBat(loc, game);
		Location specialB = new Location(game.getWorld(), loc.getX()+Main.decalageBatX, loc.getY()+Main.decalageBatY, loc.getZ()+Main.decalageBatZ);
		placeCauldronBlock(specialB, game);
	}
	public static void placeStoneStruct(Location loc, Stone stone, Game game) {
		loc.setWorld(game.getWorld());
		placeBat(loc, game);
		Location specialB = new Location(game.getWorld(), loc.getX()+Main.decalageBatX, loc.getY()+Main.decalageBatY, loc.getZ()+Main.decalageBatZ);
		placeStoneBlock(stone,specialB, game);
	}
	
	@EventHandler
	public void onEntityShootBow(EntityShootBowEvent e) {
		
		if (e.getEntity() instanceof Player) {
			PlayerData p = Main.getData(e.getEntity());
			if (e.getBow().getItemMeta() != null && e.getEntity() instanceof Player && e.getBow().getItemMeta().getDisplayName() != null && e.getBow().getItemMeta().getDisplayName().equals(ChatColor.UNDERLINE+"FlameThrowerInf")) {
				e.setCancelled(true);
				LineRapport rapport = LineLocationHelper.getLineLocations((Player) e.getEntity(), 40, 0.3, 0.0, 0, 0, 0);
				LineRapport rapport1 = LineLocationHelper.getLineLocations((Player) e.getEntity(), 40, 0.3, 0.0, 1, 0, 0);
				LineRapport rapport2 = LineLocationHelper.getLineLocations((Player) e.getEntity(), 40, 0.3, 0.0, 0, 1, 0);
				LineRapport rapport3 = LineLocationHelper.getLineLocations((Player) e.getEntity(), 40, 0.3, 0.0, 0, 0, 1);
				LineRapport rapport4 = LineLocationHelper.getLineLocations((Player) e.getEntity(), 40, 0.3, 0.0, 0, -1, 0);
			
				ArrayList<Location> locs = new ArrayList<Location>();
				locs.addAll(rapport.locs);
				locs.addAll(rapport2.locs);
				locs.addAll(rapport1.locs);
				locs.addAll(rapport3.locs);
				locs.addAll(rapport4.locs);
				for (Location loca: locs) {
					PlayerUtil.particle(loca, Color.ORANGE, "ok", 0.2);
					for (Player ply:Bukkit.getOnlinePlayers()) {
						if (!e.getEntity().equals(ply) && LocationUtil.getDistanceBetween(ply, loca) < 1) {
							ply.setFireTicks(100);
							ply.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION	, 40, 3));
						}
					}
				}
				
			
			
			} else if (e.getBow().getItemMeta() != null && e.getBow().getItemMeta().getDisplayName() != null && e.getBow().getItemMeta().getDisplayName().equals(ChatColor.UNDERLINE + "Blaster")) {
				e.setCancelled(true);
				LineRapport rapport = LineLocationHelper.getLineLocations((Player) e.getEntity(), 40 * e.getForce(), 0.25, 0.3);
				List<Location> locs = rapport.locs;
				locs.remove(0);
				locs.remove(0);
				locs.remove(0);
				
				if (rapport.p != null) {
					rapport.p.player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW	, 10, 3));

					if (MathUtil.pourcentage(20)) {
						locs.remove(locs.size() - 1);
						for (Location loc:locs) {
							PlayerUtil.particle(loc, Color.RED, "ok", 0);
						}
						PlayerUtil.particle(rapport.finalLoc, Color.YELLOW, "ok", 0.1);
						PlayerUtil.particle(rapport.finalLoc, Color.YELLOW, "ok", 0.1);
						PlayerUtil.particle(rapport.finalLoc, Color.YELLOW, "ok", 0.1);
						if (MathUtil.pourcentage(30)) {
							rapport.p.player.damage(2, e.getEntity());
						}
						if (MathUtil.pourcentage(30) && e.getBow().getItemMeta() != null && e.getBow().getItemMeta().getDisplayName().equals(ChatColor.UNDERLINE+"ChewbacaShooter")) {
							rapport.p.getLocation().getWorld().createExplosion(rapport.p.getLocation(), 1, false);
							rapport.p.player.damage(2, p.player);
						}
					} else {
						p.player.damage(2, e.getEntity());
					}
					
				} else {
					for (Location loc:locs) {
						PlayerUtil.particle(loc, Color.RED, "ok", 0);
					}
				}
			}
		}
	}
	
	
	
	
	
	
	
	public static void placeBat(Location loc, Game game) {
		loc.setWorld(game.getWorld());
		try {
			StructureLoader.place(loc, StructureLoader.load(new File("schems/urne.schematic")), game.getWorld(), BukkitUtil.getLocalWorld(game.getWorld()).getWorldData());
			if (game instanceof GameLg) {
				((GameLg) game).locsBat.add(loc);
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void placeStarMap(Location loc, World world) {
		loc.setWorld(world);
		try {
			StructureLoader.place(loc, StructureLoader.load(new File("schems/spawn9777954.schematic")), world, BukkitUtil.getLocalWorld(world).getWorldData());
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void placeMap(Location loc, World world, String path) {
		loc.setWorld(world);
		try {
			StructureLoader.place(loc, StructureLoader.load(new File(path)), world, BukkitUtil.getLocalWorld(world).getWorldData());
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void placeVoteBlock(Location loc, Game game) {
		loc.setWorld(game.getWorld());
		System.out.println("bloc vote placé");
		game.getWorld().getBlockAt(loc).setType(Material.JUKEBOX);
		game.getWorld().getBlockAt(loc).setMetadata("specialBlock-lgFitzche", new FixedMetadataValue(Main.plug, true));
		Main.specialBlocks.add(new SpecialBlock(loc, SpecialBlockType.Vote, new VoteBlockData(((GameLg) game).groupe, (GameLg) game), (GameLg) game, ""));
		
	}
	public static void deleteDirectory(File file) {
	    for (File subFile : file.listFiles()) {
	        if (subFile.isDirectory()) {
	            deleteDirectory(subFile);
	        } else {
	            subFile.delete();
	        }
	    }
	    file.delete();
	}
	public static void placeCauldronBlock(Location loc, Game game) {
		loc.setWorld(game.getWorld());
		System.out.println("bloc vote placé");
		game.getWorld().getBlockAt(loc).setType(Material.CAULDRON);
		game.getWorld().getBlockAt(loc).setMetadata("specialBlock-lgFitzche", new FixedMetadataValue(Main.plug, true));
		Main.specialBlocks.add(new SpecialBlock(loc, SpecialBlockType.Cauldron, new CauldronBlockData(), (GameLg) game, "ok"));
		
	}
	
	public static void placeStoneBlock(Stone stone, Location loc, Game game) {
		loc.setWorld(game.getWorld());
		game.getWorld().getBlockAt(loc).setType(Material.ENDER_CHEST);
		game.getWorld().getBlockAt(loc).setMetadata("specialBlock-lgFitzche", new FixedMetadataValue(Main.plug, true));
		Main.specialBlocks.add(new SpecialBlock(loc, SpecialBlockType.Stone, new StoneBlockData(stone), (GameLg) game, "ok"));
	}
	public static void placeAccuseBlock(Location loc, Game game) {
		loc.setWorld(game.getWorld());
		game.getWorld().getBlockAt(loc).setType(Material.ANVIL);
		game.getWorld().getBlockAt(loc).setMetadata("specialBlock-lgFitzche", new FixedMetadataValue(Main.plug, true));

		Main.specialBlocks.add(new SpecialBlock(loc, SpecialBlockType.Accuse, new AccuseBlockData(), (GameLg) game, "ok"));
		
		
	}
	public static void placeTreasureBlock(Location loc, TreasureBlockType type, Game game) {
		loc.setWorld(game.getWorld());
		if (type == null) {
			System.out.println("TYPE NULL");
			return;
		}
		
		
		game.getWorld().getBlockAt(loc).setType(Material.ENDER_CHEST);
		game.getWorld().getBlockAt(loc).setMetadata("specialBlock-lgFitzche", new FixedMetadataValue(Main.plug, true));

		Main.specialBlocks.add(new SpecialBlock(loc, SpecialBlockType.Treasure, new TreasureBlockData(type), (GameLg) game, "ok"));
		
		
	}
	
	
	public static void setServer(Server server) {
			Main.server = server;
	}


	public static void pvpWorldKill(Player killed, Entity killer, World world) {
		killed.getInventory().clear();
		killed.getInventory().setArmorContents(null);
		killed.teleport(Main.world.getSpawnLocation());
		PlayerData killedD = Main.getData(killed);
		PlayerData killerD = Main.getData(killer);
		killed.setMaxHealth(20);
		killed.setHealth(killed.getMaxHealth());
		killerD.addXp(MathUtil.generateAlInt(1, 4));
		killedD.autel = false;
		killedD.sphere = false;
		killerD.toji = false;
		if (killerD.getHealth() + 6 > killerD.getMaxHealth()) {
			killerD.setHealth(killerD.getMaxHealth());
		} else {
			killerD.setHealth(killerD.getHealth()+ 6);
		}
		killerD.player.setHealth(decalageBatX);
		SpecialItemHolder.giveItem(killedD.player, "Navigation");
		killed.removePotionEffect(PotionEffectType.SPEED);
		killed.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
		killed.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
		if (MathUtil.generateAlInt(0, 100) > 70) {
			
			if (MathUtil.generateAlInt(0, 100) > 90) {
				killerD.sendMessage(Main.info + " Vous avez gagné 10 feather");
				killerD.feathers += 10;
			} else {
				killerD.feathers++;
				killerD.sendMessage(Main.info + " Vous avez gagné 1 feather");
			}
		}
		
		if (killerD != null && killedD != null) {
			killedD.pvpZoneDeath ++;
			killerD.pvpZoneKill ++;
			for (Player p:world.getPlayers()) {
				
				String message = "";
				if (killerD.specialDeathAnnounces != null&& killerD.specialDeathAnnounces.size() > 0) {
					message = killerD.specialDeathAnnounces.get(MathUtil.generateAlInt(0, killerD.specialDeathAnnounces.size() - 1));
				}
				
				
				p.sendMessage(killed.getName() + " a été éliminé par "+ killer.getName() + ", "+ message);
			}
		}
		
	}
	
	public static void pvpWorldDamage(Player damaged, Player damager, double damage) {
		if (damaged.getHealth() - damage <= 0) {
			
			
			pvpWorldKill(damaged, damager, damaged.getLocation().getWorld());
		} else {
			damaged.setHealth(damaged.getHealth() - damage);
		}
	}
}




	
	

	
	