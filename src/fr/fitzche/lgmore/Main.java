package fr.fitzche.lgmore;

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

import fr.fitzche.lgmore.Lg.GameLg;
import fr.fitzche.lgmore.RolesLg.ANCIEN;
import fr.fitzche.lgmore.RolesLg.ENFANT_SAUVAGE;
import fr.fitzche.lgmore.RolesLg.IDIOT_DU_VILLAGE;
import fr.fitzche.lgmore.RolesLg.LOUP_MYSTIQUE;
import fr.fitzche.lgmore.RolesLg.RoleDisplay;
import fr.fitzche.lgmore.RolesLg.RolesLg;
import fr.fitzche.lgmore.RolesLg.SOEUR;
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
	public static ArrayList<GameLg> games = new ArrayList<GameLg>();
	
	public static JavaPlugin plug;
	public static Permission lgop;
	public HashMap<String, Boolean> alreadyCo = new HashMap<String, Boolean>();
	public static mcListeners listeners;
	public static ArrayList<String> eventsLgNames = new ArrayList<String>();
	public static HashMap<String, String> descriptionsLgEvent = new HashMap<String, String>();
	

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
		
		getServer().getPluginManager().registerEvents(this, this);
		getServer().getPluginManager().registerEvents(listener, this);
		
		

		
		
		
	    
	    

	    
	    
	   
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
				RolesLg.VOYANTE
				
				));
		RoleUtil.existingRoles.addAll(list);

		eventsLgNames.add("Brume");
		descriptionsLgEvent.put("Brume", "Probabilité à la mort d'un joueur, que le message de mort soit caché au village");

		eventsLgNames.add("Premonition");
		descriptionsLgEvent.put("Premonition", "Probabilité que dans un épisode, un joueur obtienne un présentiment sur son entourage");

		eventsLgNames.add("Trouple");
		descriptionsLgEvent.put("Trouple", "Chance que le couple soit...un trouple");

		eventsLgNames.add("Mal visé");
		descriptionsLgEvent.put("Mal visé", "probabilité que le cupidon vise mal et se mette en couple avec un joueur aléatoire");

		eventsLgNames.add("Exposed");
		descriptionsLgEvent.put("Exposed","A chaque épisode, un exposed de 4 role a un pourcentagede chance de se produire en fonction du taux de tragique (exemple: 20pts de tragique = 20% de chance), ce pourcentage est la chance que l'exposed se produise ou pas, passe avant la probabilité du taux de targique (ex: event à 20%, et 60 pts de tragique = 60% de 20% = 12% de chance) L'exposed affiche 4 role dans le chat dont le nom du joueur choisi au hasard pour l'exposed.");

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
					gm1.announceDeath(player, false);
				}
				
				
				
			}
			
		}, 6000);
	}
	
	
	public static void setServer(Server server) {
		Main.server = server;
		}
	}


	
	

	
	
