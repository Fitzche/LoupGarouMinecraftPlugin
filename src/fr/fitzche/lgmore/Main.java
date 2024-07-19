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
import org.bukkit.craftbukkit.v1_8_R1.CraftWorld;
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
import fr.fitzche.lgmore.minecraft.mcListeners;
import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_8_R1.BiomeBase;
import net.minecraft.server.v1_8_R1.BiomeDecorator;
import net.minecraft.server.v1_8_R1.BlockDataAbstract;
import net.minecraft.server.v1_8_R1.BlockPosition;
import net.minecraft.server.v1_8_R1.Blocks;
import net.minecraft.server.v1_8_R1.IBlockData;
import net.minecraft.server.v1_8_R1.WorldGenMinable;
import net.minecraft.server.v1_8_R1.WorldGenerator;

public class Main extends JavaPlugin implements Listener {
	public static Server server;
	public static ArrayList<GameLg> games = new ArrayList<GameLg>();
	public static Scoreboard scoreboardLg;
	public static Objective objective;
	public static JavaPlugin plug;
	public static Permission lgop;
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
		
		CraftWorld cW = (CraftWorld) getServer().getWorld("world");
		net.minecraft.server.v1_8_R1.World world = cW.getHandle();
		
		Random rD = new Random();
		final WorldGenerator dMore = new WorldGenMinable(Blocks.DIAMOND_ORE.getBlockData(), 80);
		dMore.generate(world, rD, new BlockPosition(0, 0, 0));
		
		Random rG = new Random();
		final WorldGenerator gMore = new WorldGenMinable(Blocks.GOLD_ORE.getBlockData(), 80);
		gMore.generate(world, rD, new BlockPosition(0, 0, 0));

		
		scoreboardLg = Bukkit.getScoreboardManager().getNewScoreboard();
		objective = scoreboardLg.registerNewObjective((ChatColor.RED +"UhcM" + ChatColor.RED.ITALIC + "by Fitz"  ), "dummy");
	    System.out.println("score created");
		objective.setDisplaySlot(DisplaySlot.SIDEBAR);
		
	    
	    

	    
	    RoleUtilLg.existingRoles.add(RolesLg.ANCIEN);
	    RoleUtilLg.existingRoles.add(RolesLg.BIENFAITEUR);
	    RoleUtilLg.existingRoles.add(RolesLg.CHASSEUR);
	    RoleUtilLg.existingRoles.add(RolesLg.CORBEAU);
	    RoleUtilLg.existingRoles.add(RolesLg.CUPIDON);
	    RoleUtilLg.existingRoles.add(RolesLg.DISCIPLE);
	    RoleUtilLg.existingRoles.add(RolesLg.ENFANT_SAUVAGE);
	    RoleUtilLg.existingRoles.add(RolesLg.IDIOT_DU_VILLAGE);
	    RoleUtilLg.existingRoles.add(RolesLg.INFECT_PERE_DES_LOUPS);
	    RoleUtilLg.existingRoles.add(RolesLg.INTERPRETE);
	    RoleUtilLg.existingRoles.add(RolesLg.LOUP_METAMORPHE);
	    RoleUtilLg.existingRoles.add(RolesLg.LOUP_MYSTIQUE);
	    RoleUtilLg.existingRoles.add(RolesLg.MONTREUR);
	    RoleUtilLg.existingRoles.add(RolesLg.PETITE_FILLE);
	    RoleUtilLg.existingRoles.add(RolesLg.PYROMANE);
	    RoleUtilLg.existingRoles.add(RolesLg.RENARD);
	    RoleUtilLg.existingRoles.add(RolesLg.SAGE);
	    RoleUtilLg.existingRoles.add(RolesLg.SALVATEUR);
	    RoleUtilLg.existingRoles.add(RolesLg.SIMPLE_VILLAGER);
	    RoleUtilLg.existingRoles.add(RolesLg.SIMPLE_WOLF);
	    RoleUtilLg.existingRoles.add(RolesLg.SOEUR);
	    RoleUtilLg.existingRoles.add(RolesLg.SORCIERE);
	    RoleUtilLg.existingRoles.add(RolesLg.VOLEUR);
	    RoleUtilLg.existingRoles.add(RolesLg.VOYANTE);
	    RoleUtilLg.existingRoles.add(RolesLg.PERFIDE);
	    RoleUtilLg.existingRoles.add(RolesLg.ASSASSIN);
		RoleUtilLg.existingRoles.add(RolesLg.ALLUMEUR);
	    RoleUtilLg.existingRoles.add(RolesLg.PARRAIN);
		RoleUtilLg.existingRoles.add(RolesLg.ANGE);


		eventsNames.add("Brume");
		descriptionsEvent.put("Brume", "Probabilité à la mort d'un joueur, que le message de mort soit caché au village");

		eventsNames.add("Premonition");
		descriptionsEvent.put("Premonition", "Probabilité que dans un épisode, un joueur obtienne un présentiment sur son entourage");

		eventsNames.add("Trouple");
		descriptionsEvent.put("Trouple", "Chance que le couple soit...un trouple");

	}
	
	
	
	
	
	
	@Override
	public void onDisable() {
		
		super.onDisable();
	}
	
	@Deprecated
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		System.out.println("join");
		e.getPlayer().setScoreboard(Main.scoreboardLg);
		Bukkit.broadcastMessage(e.getPlayer().getName() +" joined");
		
		for (GameLg gm: Main.games) {
			System.out.println("tested for "+ gm.name +" at l.270 of Main");
			if (gm.toAdd.size() == 0) {
				System.out.println("no player to add l.272 of Main");
			}
			
			ArrayList<PlayerData> example = new ArrayList<PlayerData>();
			for (PlayerData ply : gm.toAdd) {
				example.add(ply);
			}
			for (PlayerData ply: example) {
				
				if (ply.Name.equals(e.getPlayer().getName())) {
					gm.addPlayer(e.getPlayer().getName());
					gm.toAdd.remove(ply);
					System.out.println(ply.Name+" removed for disconnect at l.279 of Main");
				} else {
					System.out.println(ply.Name +" isn't "+ e.getPlayer().getName() + " at 281 of Main");
				}
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
		

		if (GameLgUtil.getGameOfPlayer(player, "at l.307 of Main") == null) {
			System.out.println("l.309 of Main");
			return;
		}
		
		GameLg gm1 = GameLgUtil.getGameOfPlayer(player, "at l.311 of Main");
		System.out.println(gm1.name + " is the game ");
		ItemStack[] inventory = e.getPlayer().getInventory().getContents();
		gm1.removePlayer(player, "at 314 of Main");
		System.out.println("removed fot left 307 of Main, and added at list");

		gm1.toAdd.add(player);
		
		
		Bukkit.getScheduler().runTaskLater(Main.plug, new BukkitRunnable() {

			@Override
			public void run() {
				System.out.println("online?");
				
				
				if (!player.player.isOnline() && gm1.timer.temps > 1199) {
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


	
	

	
	
