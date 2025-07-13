package fr.fitzche.lgmore;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
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

import com.onarandombox.MultiverseCore.MultiverseCore;
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
import fr.fitzche.lgmore.Lg.SpecialsBlock.SpecialBlock;
import fr.fitzche.lgmore.Lg.SpecialsBlock.SpecialBlockType;
import fr.fitzche.lgmore.Lg.SpecialsBlock.StoneBlockData;
import fr.fitzche.lgmore.Lg.SpecialsBlock.TreasureBlockData;
import fr.fitzche.lgmore.Lg.SpecialsBlock.TreasureBlockType;
import fr.fitzche.lgmore.Lg.SpecialsBlock.VoteBlockData;
import fr.fitzche.lgmore.RolesLg.ANCIEN;
import fr.fitzche.lgmore.RolesLg.ENFANT_SAUVAGE;
import fr.fitzche.lgmore.RolesLg.IDIOT_DU_VILLAGE;
import fr.fitzche.lgmore.RolesLg.LOUP_MYSTIQUE;
import fr.fitzche.lgmore.RolesLg.RoleDisplay;
import fr.fitzche.lgmore.RolesLg.RolesLg;
import fr.fitzche.lgmore.RolesLg.SOEUR;
import fr.fitzche.lgmore.RolesLg.THANOS;
import fr.fitzche.lgmore.Util.GameLgUtil;
import fr.fitzche.lgmore.Util.ItemUtil;
import fr.fitzche.lgmore.Util.LineLocationHelper;
import fr.fitzche.lgmore.Util.LineRapport;
import fr.fitzche.lgmore.Util.LocationUtil;
import fr.fitzche.lgmore.Util.MathUtil;
import fr.fitzche.lgmore.Util.PlayerUtil;
import fr.fitzche.lgmore.Util.RoleUtil;
import fr.fitzche.lgmore.bedwars.Bedwars;
import fr.fitzche.lgmore.commands.Lga;
import fr.fitzche.lgmore.commands.Rejoin;
import fr.fitzche.lgmore.commands.Star;

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
	
	public static HashMap<String, GameLg> strToGame = new HashMap<String, GameLg>();
	
	public static HashMap<String, PlayerData> strToPlayer = new HashMap<String, PlayerData>();
	public static ArrayList<GameListener> gameListeners = new ArrayList<GameListener>();
	
	
	public static ArrayList<StarParty> parties = new ArrayList<StarParty>();
	public static ArrayList<Bedwars> bedwars = new ArrayList<Bedwars>();
	

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
	@SuppressWarnings("unchecked")
	@Override
	@Deprecated
	public void onEnable() {
		// TODO Auto-generated method stub
		super.onEnable();
		
		
		
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
		
		mcListeners listener = new mcListeners();
		this.listeners = listener;
		
		Main.worldData = BukkitUtil.getLocalWorld(Main.world).getWorldData();
		
		getServer().getPluginManager().registerEvents(this, this);
		getServer().getPluginManager().registerEvents(listener, this);
		Main2 main2 = new Main2();
		getServer().getPluginManager().registerEvents(main2, this);
		
		
		Main.world = getServer().getWorld("world");
		
		
		
		
	    
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
				RolesLg.VOYANTE
				
				
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
		
		
		Main2.onEnable();
		
		Bukkit.getScheduler().runTaskTimerAsynchronously(Main.plug, new BukkitRunnable() {
			
			@Override
			public void run() {
				for (Player p:Bukkit.getOnlinePlayers()) {
					strToPlayer.get(p.getName()).board.refresh();
				}
				
			}
			
		}, 20, 20);
		
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
		
		e.getPlayer().sendMessage(ChatColor.GOLD + ""+ ChatColor.ITALIC+ "Faites /lg help et /lg info pour avoir plus d'info sur le plugin lgmore, /lg whisper [message] pour envoyer un message non anonyme aux joueurs op. Les description de roles n'indiquent pas forcement qu'il faut mettre un nom derrière la commande si la commande vise un joueur à choisir, les seules commandes ne necessitant pas de nom mais demandant un choix de joueur sont /color et /lg couple (pour cupidon). ");
		
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
	
	@Deprecated
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent e) {
		
		System.out.println("left");

		
		Bukkit.broadcastMessage(e.getPlayer().getName() +" left");
		PlayerData player = strToPlayer.get(e.getPlayer().getName());
		if (player.game == null) {
			System.out.println("game null (quit event)");
			return;
		}
		player.leftInv = e.getPlayer().getInventory();
		Game gm1 = player.game;
		
		System.out.println(gm1.getName() + " is the game ");

		if (gm1 instanceof GameLg) {
			((GameLg) gm1).playersLeft.put(player.getName(), new PlayerDataLeft(player));
			
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
	
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
	        // Vérifie que l'action est un clic droit (dans l'air ou sur un bloc)
		Action action = event.getAction();
		if (action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) {
			ItemStack item = event.getItem();
			if (item.getItemMeta() == null || item.getItemMeta().getDisplayName() == null) {
				return;
			}
			// Récupération de l'item en main
			
			if (item != null) {
				Material type = item.getType();
				// Vérifie que l'item est une épée.
				// On peut comparer directement ou utiliser endsWith("_SWORD")
				if (item.getItemMeta() != null && item.getItemMeta().getDisplayName() != null && item.getItemMeta().getDisplayName().equals(ChatColor.UNDERLINE+"AtaruInf")) {
					PlayerData p = Main.getData(event.getPlayer());
					for (Player ply:Bukkit.getOnlinePlayers()) {
						
						if (!p.getName().equals(ply.getName())&&p.getLocation().distance(ply.getLocation()) < 3) {
							ply.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE	, 250, 100));
							LineLocationHelper.applyKnockback(ply, p.player, (3 - p.getLocation().distance(ply.getLocation())) * 3);
							
						}
					}
				}
			}
			
			if (item.getItemMeta() != null && item.getItemMeta().getDisplayName() != null && item.getItemMeta().getDisplayName().equals(ChatColor.UNDERLINE+"LightningInf")) {
            	PlayerData p = Main.getData(event.getPlayer());
            	LineRapport r = LineLocationHelper.getLineLocations(p.player, 40, 0.2, 0.1);
        		if (r.p != null) {
        			
        			r.p.getLocation().getWorld().strikeLightningEffect(r.p.getLocation());
        			r.p.player.damage(4, p.player);
        			r.p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW	, 30, 3));
        		}
			}else if (item.getItemMeta() != null && item.getItemMeta().getDisplayName() != null && item.getItemMeta().getDisplayName().equals(ChatColor.UNDERLINE+"StrangleInf")) {
            	PlayerData p = Main.getData(event.getPlayer());
            	LineRapport r = LineLocationHelper.getLineLocations(p.player, 40, 0.2, 0.1);
        		if (r.p != null) {
        			p.sendMessage("Vous étrangler "+ r.p.getName());
        			p.player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 40, 1, false, false));
        			r.p.player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 60, 2, false, false));
        			r.p.player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 60, 2, false, false));
        			r.p.player.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 60, 1, false, false));
        			r.p.sendMessage("Vous êtes affecté par l'étranglement de Dark Vador");
        			PlayerUtil.particle(r.p.getLocation(), Color.BLACK, "ok", 1);
        		
        		}
            } else if (item.getItemMeta() != null && item.getItemMeta().getDisplayName() != null && item.getItemMeta().getDisplayName().equals(ChatColor.UNDERLINE+"SithInf")) {
            	PlayerData p = Main.getData(event.getPlayer());
            	p.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE	, 200, 0));
        		p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED	, 200, 3));
        	
            }else if (item.getItemMeta() != null && item.getItemMeta().getDisplayName() != null && item.getItemMeta().getDisplayName().equals(ChatColor.UNDERLINE+"Navigation")) {
            	PlayerData p = Main.getData(event.getPlayer());
            	if (p== null) {
            		return;
            	}
            	(new GeneralMenu(p) ).open(p);
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
			StructureLoader.place(loc, StructureLoader.load(new File("schems/starmap.schematic")), world, BukkitUtil.getLocalWorld(world).getWorldData());
			
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
		Main.specialBlocks.add(new SpecialBlock(loc, SpecialBlockType.Vote, new VoteBlockData(5), (GameLg) game, "ok"));
		
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
		Main.specialBlocks.add(new SpecialBlock(loc, SpecialBlockType.Cauldron, new VoteBlockData(5), (GameLg) game, "ok"));
		
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
	}




	
	

	
	
