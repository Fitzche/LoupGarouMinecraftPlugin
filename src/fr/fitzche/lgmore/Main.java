package fr.fitzche.lgmore;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.command.TabCompleter;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
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

import WorldEditUtil.StructureLoader;
import de.inventivegames.particle.ParticleEffect;
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
import fr.fitzche.lgmore.Util.LocationUtil;
import fr.fitzche.lgmore.Util.PlayerUtil;
import fr.fitzche.lgmore.Util.RoleUtil;
import fr.fitzche.lgmore.commands.Lga;
import fr.fitzche.lgmore.commands.Lg;
import fr.fitzche.lgmore.commands.LgTab;

import fr.fitzche.lgmore.minecraft.PlayerDataLeft;
import fr.fitzche.lgmore.minecraft.mcListeners;
import net.md_5.bungee.api.ChatColor;


public class Main extends JavaPlugin implements Listener {
	public static Server server;
	public static GameLg game;
	
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
	
	public static int decalageBatX = 6;
	public static int decalageBatY = 1;
	public static int decalageBatZ = 4;
	
	public static ArrayList<Location> locBat = new ArrayList<Location>();

	public JavaPlugin getPlugin() {
		return this;
	}
	
	
	
	
	
	@Override
	public void onEnable() {
		// TODO Auto-generated method stub
		super.onEnable();
		
		lgop = new Permission("lgop");
		
		Main.server = this.getServer();
		Main.plug = this.getPlugin();
		
		getCommand("lga").setExecutor(new Lga());
		getCommand("lga").setTabCompleter(new LgTab());
		getCommand("lg").setExecutor(new Lg());
		
		
		mcListeners listener = new mcListeners();
		this.listeners = listener;
		
		Main.worldData = BukkitUtil.getLocalWorld(Main.world).getWorldData();
		
		getServer().getPluginManager().registerEvents(this, this);
		getServer().getPluginManager().registerEvents(listener, this);
		
		Main.world = getServer().getWorld("world");
		
		
		
		
	    
	    
		
	    
	    
	    
	    
	   
		ArrayList<RolesLg> list = new ArrayList<RolesLg>(Arrays.asList(
				RolesLg.ALLUMEUR,
				RolesLg.ANCIEN,
				RolesLg.ANGE,
				RolesLg.ASSASSIN,
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
				RolesLg.SIMPLE_WOLF,
				RolesLg.SOEUR,
				RolesLg.SORCIERE, 
				RolesLg.VOLEUR,
				RolesLg.ANGE_THIERCE,
				RolesLg.LOUP_BRUMEUX,
				RolesLg.DEMON,
				RolesLg.THANOS,
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
		
		
	}
	
	
	
	
	
	
	@Override
	public void onDisable() {
		
		super.onDisable();
	}
	
	@Deprecated
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		
		e.getPlayer().sendMessage(ChatColor.GOLD + ""+ ChatColor.ITALIC+ "Faites /lg help et /lg info pour avoir plus d'info sur le plugin lgmore, /lg whisper [message] pour envoyer un message non anonyme aux joueurs op. Les description de roles n'indiquent pas forcement qu'il faut mettre un nom derrière la commande si la commande vise un joueur à choisir, les seules commandes ne necessitant pas de nom mais demandant un choix de joueur sont /color et /lg couple (pour cupidon). ");
		
		
		
		
		Bukkit.broadcastMessage(e.getPlayer().getName() +" joined");
		if (e.getPlayer().getName().equals("TheGuill84")) {
			e.getPlayer().sendMessage("Hey, tu as rejoint un serveur avec le plugin de lg de Fitzche, fais /lga Game create [nomDeLaGame] pour créer un game, puis /lga Game config [nomDeLaGame] pour la config puis la start... ");
		}
		
		if (game.playersLeft.get(e.getPlayer().getName()) != null) {
			game.playersLeft.get(e.getPlayer().getName()).end();
		}
		
		
		
	}
	
	@Deprecated
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent e) {
		
		System.out.println("left");

		
		Bukkit.broadcastMessage(e.getPlayer().getName() +" left");
	
		if (GameLgUtil.getGameOfPlayer(e.getPlayer(), " at 300 of Main") == null) {
			System.out.println("Main -> l.301, yes");
			return;
		}
		
		PlayerData player = PlayerUtil.getDataOfPlayer(e.getPlayer(), " in Main, in onPlayerQuit ");

		GameLg gm1 = GameLgUtil.getGameOfPlayer(player, "at l.311 of Main");
		System.out.println(gm1.name + " is the game ");

		gm1.playersLeft.put(player.Name, new PlayerDataLeft(player));
		
		
		Bukkit.getScheduler().runTaskLater(Main.plug, new BukkitRunnable() {

			@Override
			public void run() {
				System.out.println("online?");
				
				
				if (!player.isOnline && gm1.timer.temps > 1199) {
					System.out.println(player.Name+ " not online");
					gm1.announceDeath(player, false, false);
				}
				
				
				
			}
			
		}, 6000);
	}
	public static void placeVoteStruct(Location loc) {
		placeBat(loc);
		Location voteB = new Location(world, loc.getX()+Main.decalageBatX, loc.getY()+Main.decalageBatY, loc.getZ()+Main.decalageBatZ);
		placeVoteBlock(voteB);
	}
	public static void placeAccuseStruct(Location loc) {
		placeBat(loc);
		Location voteB = new Location(world, loc.getX()+Main.decalageBatX, loc.getY()+Main.decalageBatY, loc.getZ()+Main.decalageBatZ);
		placeAccuseBlock(voteB);
	}
	public static void placeTreasureStruct(Location loc, TreasureBlockType type) {
		placeBat(loc);
		Location specialB = new Location(world, loc.getX()+Main.decalageBatX, loc.getY()+Main.decalageBatY, loc.getZ()+Main.decalageBatZ);
		placeTreasureBlock(specialB, type);
	}
	public static void placeCauldronStruct(Location loc) {
		placeBat(loc);
		Location specialB = new Location(world, loc.getX()+Main.decalageBatX, loc.getY()+Main.decalageBatY, loc.getZ()+Main.decalageBatZ);
		placeCauldronBlock(specialB);
	}
	public static void placeStoneStruct(Location loc, Stone stone) {
		placeBat(loc);
		Location specialB = new Location(world, loc.getX()+Main.decalageBatX, loc.getY()+Main.decalageBatY, loc.getZ()+Main.decalageBatZ);
		placeStoneBlock(stone,specialB);
	}
	
	public static void placeBat(Location loc) {
		try {
			StructureLoader.place(loc, StructureLoader.load(new File("schems/urne.schematic")));
			Main.locBat.add(loc);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void placeVoteBlock(Location loc) {
		System.out.println("bloc vote placé");
		Main.server.getWorld("world").getBlockAt(loc).setType(Material.ENDER_CHEST);
		Main.server.getWorld("world").getBlockAt(loc).setMetadata("specialBlock-lgFitzche", new FixedMetadataValue(Main.plug, true));
		Main.specialBlocks.add(new SpecialBlock(loc, SpecialBlockType.Vote, new VoteBlockData(5)));
		
	}
	public static void placeCauldronBlock(Location loc) {
		System.out.println("bloc vote placé");
		Main.server.getWorld("world").getBlockAt(loc).setType(Material.ENDER_CHEST);
		Main.server.getWorld("world").getBlockAt(loc).setMetadata("specialBlock-lgFitzche", new FixedMetadataValue(Main.plug, true));
		Main.specialBlocks.add(new SpecialBlock(loc, SpecialBlockType.Cauldron, new VoteBlockData(5)));
		
	}
	
	public static void placeStoneBlock(Stone stone, Location loc) {
		Main.server.getWorld("world").getBlockAt(loc).setType(Material.ENDER_CHEST);
		Main.server.getWorld("world").getBlockAt(loc).setMetadata("specialBlock-lgFitzche", new FixedMetadataValue(Main.plug, true));
		Main.specialBlocks.add(new SpecialBlock(loc, SpecialBlockType.Stone, new StoneBlockData(stone)));
	}
	public static void placeAccuseBlock(Location loc) {
		Main.server.getWorld("world").getBlockAt(loc).setType(Material.ENDER_CHEST);
		Main.server.getWorld("world").getBlockAt(loc).setMetadata("specialBlock-lgFitzche", new FixedMetadataValue(Main.plug, true));

		Main.specialBlocks.add(new SpecialBlock(loc, SpecialBlockType.Accuse, new AccuseBlockData()));
		
		
	}
	public static void placeTreasureBlock(Location loc, TreasureBlockType type) {
		Main.server.getWorld("world").getBlockAt(loc).setType(Material.ENDER_CHEST);
		Main.server.getWorld("world").getBlockAt(loc).setMetadata("specialBlock-lgFitzche", new FixedMetadataValue(Main.plug, true));

		Main.specialBlocks.add(new SpecialBlock(loc, SpecialBlockType.Treasure, new TreasureBlockData(type)));
		
		
	}
	
	
	public static void setServer(Server server) {
		Main.server = server;
		}
	}




	
	

	
	
