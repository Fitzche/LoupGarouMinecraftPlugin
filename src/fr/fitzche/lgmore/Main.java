package fr.fitzche.lgmore;

import java.util.ArrayList;
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

import fr.fitzche.lgmore.RolesLg.ANCIEN;
import fr.fitzche.lgmore.RolesLg.Camp;
import fr.fitzche.lgmore.RolesLg.ENFANT_SAUVAGE;
import fr.fitzche.lgmore.RolesLg.IDIOT_DU_VILLAGE;
import fr.fitzche.lgmore.RolesLg.LOUP_MYSTIQUE;
import fr.fitzche.lgmore.RolesLg.RoleDisplay;
import fr.fitzche.lgmore.RolesLg.RolesLg;
import fr.fitzche.lgmore.RolesLg.SOEUR;
import fr.fitzche.lgmore.Util.GameLgUtil;
import fr.fitzche.lgmore.Util.LocationUtil;
import fr.fitzche.lgmore.Util.PlayerUtil;
import fr.fitzche.lgmore.Util.RoleUtilLg;
import fr.fitzche.lgmore.commands.Lga;
import fr.fitzche.lgmore.commands.Lg;
import fr.fitzche.lgmore.commands.LgTest;
import fr.fitzche.lgmore.minecraft.PlayerDataLeft;
import fr.fitzche.lgmore.minecraft.mcListeners;
import net.md_5.bungee.api.ChatColor;

public class Main extends JavaPlugin implements Listener {
	public static Server server;
	public static ArrayList<GameLg> games = new ArrayList<GameLg>();
	public static Scoreboard scoreboardLg;
	public static Objective objective;
	public static JavaPlugin plug;
	public static Permission lgop;
	public HashMap<String, Boolean> alreadyCo = new HashMap<String, Boolean>();
	public static mcListeners listeners;
	public static ArrayList<String> eventsNames = new ArrayList<String>();
	public static HashMap<String, String> descriptionsEvent = new HashMap<String, String>();
	

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
		getCommand("lgop").setExecutor(new LgTest());
		getCommand("lga").setExecutor(new Lga());
		getCommand("lga").setTabCompleter(new LgTab());
		getCommand("lg").setExecutor(new Lg());
		
		
		mcListeners listener = new mcListeners();
		this.listeners = listener;
		
		getServer().getPluginManager().registerEvents(this, this);
		getServer().getPluginManager().registerEvents(listener, this);
		
		

		
		scoreboardLg = Bukkit.getScoreboardManager().getNewScoreboard();
		objective = scoreboardLg.registerNewObjective((ChatColor.RED +"UhcM" + ChatColor.RED.ITALIC + "by Fitz"  ), "dummy");
	    System.out.println("score created");
		objective.setDisplaySlot(DisplaySlot.SIDEBAR);
		
	    
	    

	    
	    RoleUtilLg.existingRoles.add(RolesLg.ANCIEN);
	    RoleUtilLg.existingRoles.add(RolesLg.BIENFAITEUR);
	    RoleUtilLg.existingRoles.add(RolesLg.CHASSEUR);
	    RoleUtilLg.existingRoles.add(RolesLg.CORBEAU);
	    RoleUtilLg.existingRoles.add(RolesLg.CUPIDON);
	    /*ROLE INFO*/RoleUtilLg.existingRoles.add(RolesLg.DISCIPLE);
	    RoleUtilLg.existingRoles.add(RolesLg.ENFANT_SAUVAGE);
	    RoleUtilLg.existingRoles.add(RolesLg.IDIOT_DU_VILLAGE);
	    RoleUtilLg.existingRoles.add(RolesLg.INFECT_PERE_DES_LOUPS);
	    RoleUtilLg.existingRoles.add(RolesLg.INTERPRETE);
	    RoleUtilLg.existingRoles.add(RolesLg.LOUP_METAMORPHE);
	    RoleUtilLg.existingRoles.add(RolesLg.LOUP_MYSTIQUE);
	    /*ROLE INFO*/RoleUtilLg.existingRoles.add(RolesLg.MONTREUR);
	    /*ROLE INFO*/RoleUtilLg.existingRoles.add(RolesLg.PETITE_FILLE);
	    RoleUtilLg.existingRoles.add(RolesLg.PYROMANE);
	    /*ROLE INFO*/ RoleUtilLg.existingRoles.add(RolesLg.RENARD);
	    /*ROLE INFO*/RoleUtilLg.existingRoles.add(RolesLg.SAGE);
	    RoleUtilLg.existingRoles.add(RolesLg.SALVATEUR);
	    RoleUtilLg.existingRoles.add(RolesLg.SIMPLE_VILLAGER);
	    RoleUtilLg.existingRoles.add(RolesLg.SIMPLE_WOLF);
	    RoleUtilLg.existingRoles.add(RolesLg.SOEUR);
	    RoleUtilLg.existingRoles.add(RolesLg.SORCIERE);
	    RoleUtilLg.existingRoles.add(RolesLg.VOLEUR);
	    /*ROLE INFO*/ RoleUtilLg.existingRoles.add(RolesLg.VOYANTE);
	    RoleUtilLg.existingRoles.add(RolesLg.PERFIDE);
	    RoleUtilLg.existingRoles.add(RolesLg.ASSASSIN);
	    /*ROLE INFO*/RoleUtilLg.existingRoles.add(RolesLg.ALLUMEUR);
	    /*ROLE INFO*/RoleUtilLg.existingRoles.add(RolesLg.PARRAIN);
		RoleUtilLg.existingRoles.add(RolesLg.ANGE);
		RoleUtilLg.existingRoles.add(RolesLg.LOUP_ALCHIMISTE);
		RoleUtilLg.existingRoles.add(RolesLg.LOUP_BARBARE);
		RoleUtilLg.existingRoles.add(RolesLg.LOUP_MANIP);


		eventsNames.add("Brume");
		descriptionsEvent.put("Brume", "Probabilité à la mort d'un joueur, que le message de mort soit caché au village");

		eventsNames.add("Premonition");
		descriptionsEvent.put("Premonition", "Probabilité que dans un épisode, un joueur obtienne un présentiment sur son entourage");

		eventsNames.add("Trouple");
		descriptionsEvent.put("Trouple", "Chance que le couple soit...un trouple");

		eventsNames.add("Erreur aux Urnes");
		descriptionsEvent.put("Erreur aux Urnes", "Probabilité qu'à un vote, le résultat ne soit pas le bon.");

		eventsNames.add("Mal visé");
		descriptionsEvent.put("Mal visé", "probabilité que le cupidon vise mal et se mette en couple avec un joueur aléatoire");

	}
	
	
	
	
	
	
	@Override
	public void onDisable() {
		
		super.onDisable();
	}
	
	@Deprecated
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		/*
		if (alreadyCo.get(e.getPlayer().getName()) == null) {
			e.getPlayer().teleport(new Location(Main.plug.getServer().getWorld("world"), 1,156,1));
			alreadyCo.put(e.getPlayer().getName(), true);
		}
		*/
		e.getPlayer().sendMessage(ChatColor.GOLD + ""+ ChatColor.ITALIC+ "Faites /lg help et /lg info pour avoir plus d'info sur le plugin lgmore, /lg whisper [message] pour envoyer un message non anonyme aux joueurs op. Les description de roles n'indiquent pas forcement qu'il faut mettre un nom derrière la commande si la commande vise un joueur à choisir, les seules commandes ne necessitant pas de nom mais demandant un choix de joueur sont /color et /lg couple (pour cupidon). ");
		
		
		//System.out.println("join");
		e.getPlayer().setScoreboard(Main.scoreboardLg);
		Bukkit.broadcastMessage(e.getPlayer().getName() +" joined");
		if (e.getPlayer().getName().equals("TheGuill84")) {
			e.getPlayer().sendMessage("Hey, tu as rejoint un serveur avec le plugin de lg de Fitzche, fais /lga Game create [nomDeLaGame] pour créer un game, puis /lga Game config [nomDeLaGame] pour la config puis la start... ");
		}
		
		for (GameLg gm: Main.games) {
			System.out.println("tested for "+ gm.name +" at l.270 of Main");
			if (gm.playersLeft.get(e.getPlayer().getName()) != null) {
				gm.playersLeft.get(e.getPlayer().getName()).end();
			}
		}
		
		
		
	}
	
	@Deprecated
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent e) {
		
		System.out.println("left");

		e.getPlayer().setScoreboard(Main.scoreboardLg);
		Bukkit.broadcastMessage(e.getPlayer().getName() +" left");
		
		Location leftLocation = e.getPlayer().getLocation();
		if (GameLgUtil.getGameOfPlayer(e.getPlayer(), " at 300 of Main") == null) {
			System.out.println("Main -> l.301, yes");
			return;
		}
		
		PlayerData player = PlayerUtil.getDataOfPlayer(e.getPlayer(), " in Main, in onPlayerQuit ");
		

		
		
		GameLg gm1 = GameLgUtil.getGameOfPlayer(player, "at l.311 of Main");
		System.out.println(gm1.name + " is the game ");
		
		
		System.out.println("removed fot left 307 of Main, and added at list");

		gm1.playersLeft.put(player.Name, new PlayerDataLeft(player));
		
		
		Bukkit.getScheduler().runTaskLater(Main.plug, new BukkitRunnable() {

			@Override
			public void run() {
				System.out.println("online?");
				
				
				if (!player.isOnline && gm1.timer.temps > 1199) {
					System.out.println(player.Name+ " not online");
					gm1.announceDeath(player);
				}
				
				
				
			}
			
		}, 6000);
	}
	
	
	public static void setServer(Server server) {
		Main.server = server;
		}
	}


	
	

	
	
