package fr.fitzche.lgmore.commands;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.generator.CustomChunkGenerator;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.permissions.Permission;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Scoreboard;


import com.avaje.ebeaninternal.server.persist.BindValues.Value;
import com.google.common.util.concurrent.AbstractScheduledService.Scheduler;


import com.sk89q.worldedit.CuboidClipboard;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitUtil;
import com.sk89q.worldedit.extent.clipboard.Clipboard;

import WorldEditUtil.BiomeChanger;

import WorldEditUtil.StructureLoader;
import de.inventivegames.particle.ParticleEffect;

import org.bukkit.command.TabCompleter.*;

import fr.fitzche.lgmore.Camp;
import fr.fitzche.lgmore.Game;
import fr.fitzche.lgmore.Main;
import fr.fitzche.lgmore.PlayerData;
import fr.fitzche.lgmore.InfinityStones.Stone;
import fr.fitzche.lgmore.InfinityStones.StonesType;
import fr.fitzche.lgmore.Lg.GameLg;
import fr.fitzche.lgmore.Lg.GameNote;
import fr.fitzche.lgmore.Lg.PlayerNote;
import fr.fitzche.lgmore.Lg.SpecialsBlock.SpecialBlock;
import fr.fitzche.lgmore.Lg.SpecialsBlock.SpecialBlockType;
import fr.fitzche.lgmore.Lg.SpecialsBlock.TreasureBlockData;
import fr.fitzche.lgmore.Lg.SpecialsBlock.TreasureBlockType;
import fr.fitzche.lgmore.GameStatut;
import fr.fitzche.lgmore.RolesLg.LOUP_BRUMEUX;
import fr.fitzche.lgmore.RolesLg.RoleDisplay;
import fr.fitzche.lgmore.RolesLg.RolesLg;
import fr.fitzche.lgmore.RolesLg.THANOS;
import fr.fitzche.lgmore.Util.BooksUtils;
import fr.fitzche.lgmore.Util.CommandUtil;
import fr.fitzche.lgmore.Util.GameLgUtil;
import fr.fitzche.lgmore.Util.ItemUtil;
import fr.fitzche.lgmore.Util.MathUtil;
import fr.fitzche.lgmore.Util.PlayerUtil;
import fr.fitzche.lgmore.Util.RoleUtil;
import fr.fitzche.lgmore.Util.WorldUtil;
import fr.fitzche.lgmore.scoreboard.ScoreboardLg;
import fr.fitzche.lgmore.scoreboard.Inventory.ConfigDisplay;
import fr.fitzche.lgmore.scoreboard.Inventory.SwapperConfigInv;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.minecraft.server.v1_8_R3.BiomeDecorator;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;




public class Lga implements CommandExecutor  {
	
	public static RoleDisplay INVENTAIRE;
	
	
	
	
	@Deprecated
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
		
		//getHead
		if (args[0].equals("head")) {
			((Player) sender).getInventory().addItem(ItemUtil.getCustomHead("FITZCHE"));
			
		}else if (args[0].equals("jsonTest")) {
			
			InputStream is = getClass().getClassLoader().getResourceAsStream(args[1]);
			BooksUtils.loadRoleAndOpenBook((Player) sender, args[1], is);
			
			
			
		} if (args[0].equals("resetGames")) {
			for (Player p:Bukkit.getOnlinePlayers()) {
				Main.getData(p).clearLgGameVar();
				Hub.sendHub(Main.getData(p));
			}
		}else if (args[0].equals("export")) {
			onCommandExport(sender, cmd, msg,
		            Arrays.copyOfRange(args, 1, args.length));
			System.out.println("sended for "+ args.length);
		        return true;
		}else if (args[0].equals("addS5")) {
			if (args.length < 3) {
				sender.sendMessage("erreur de commande");
				return true;
			}
			int added = Integer.valueOf(args[2]);
			Main.getData(args[1]).boostS5 += added;
		}else if (args[0].equals("addXp")) {
			if (args.length < 3) {
				sender.sendMessage("erreur de commande");
				return true;
			}
			int added = Integer.valueOf(args[2]);
			Main.getData(args[1]).xp += added;
		}else if (args[0].equals("addFeather")) {
			if (args.length < 3) {
				sender.sendMessage("erreur de commande");
				return true;
			}
			int added = Integer.valueOf(args[2]);
			Main.getData(args[1]).feathers += added;
		}else if (args[0].equals("addR5")) {
			if (args.length < 3) {
				sender.sendMessage("erreur de commande");
				return true;
			}
			int added = Integer.valueOf(args[2]);
			Main.getData(args[1]).boostR5 += added;
		}else if (args[0].equals("remS5")) {
			if (args.length < 3) {
				sender.sendMessage("erreur de commande");
				return true;
			}
			int added = Integer.valueOf(args[2]);
			Main.getData(args[1]).boostS5 -= added;
		}else if (args[0].equals("remR5")) {
			if (args.length < 3) {
				sender.sendMessage("erreur de commande");
				return true;
			}
			int added = Integer.valueOf(args[2]);
			Main.getData(args[1]).boostR5 -= added;
		}else if (args[0].equals("infGojoBlue")) {
			ItemStack dash = new ItemStack(Material.FEATHER);
			ItemUtil.setName(dash, ChatColor.UNDERLINE+"Infinite Blue");
			ItemUtil.addAppaEnchant(dash);
			
			Player p = (Player) sender;
			p.getInventory().addItem(dash);
		}else if (args[0].equals("Jogo")) {
			System.out.println("Jogo");
			ItemStack dash = new ItemStack(Material.FEATHER);
			ItemUtil.setName(dash, ChatColor.UNDERLINE+"Jogo");
			ItemUtil.addAppaEnchant(dash);
			
			
			
			Player p = (Player) sender;
			p.getInventory().addItem(ItemUtil.getItem(Material.FIREBALL, 1, ChatColor.UNDERLINE+"Jogo", new ArrayList<String>(Arrays.asList(ChatColor.GRAY+"Fait exploser la cible après 5s"))));
			
			
		}else if (args[0].equals("itemInv")) {
			
			if (args.length > 2) {
				for (int i = 0; i < Integer.valueOf(args[2]); i++) {
					SpecialItemHolder.addInvItem(Main.getData(sender), args[1]);
				}
			}
			SpecialItemHolder.addInvItem(Main.getData(sender), args[1]);
			
		}else if (args[0].equals("item")) {
			SpecialItemHolder.giveItem((Player) sender, args[1]);
		}else if (args[0].equals("infDashGive")) {
			ItemStack dash = new ItemStack(Material.FEATHER);
			ItemUtil.setName(dash, ChatColor.UNDERLINE+"DashInf");
			ItemUtil.addAppaEnchant(dash);
			int a = 0;
			if (args.length > 1) {
				a = Integer.valueOf(args[1]);
				switch (a) {
					case 1:
						dash.addUnsafeEnchantment(Enchantment.ARROW_DAMAGE, a);
						break;
					case 2:
						dash.addUnsafeEnchantment(Enchantment.ARROW_DAMAGE, a);
						break;
					case 3:
						dash.addUnsafeEnchantment(Enchantment.ARROW_DAMAGE, a);
						break;
					case 4:
						dash.addUnsafeEnchantment(Enchantment.ARROW_INFINITE, 1);
						break;
				
					
				
				}
				if (args.length > 2) {
					dash.addUnsafeEnchantment(Enchantment.ARROW_FIRE, 1);
				}
			
			}
			Player p = (Player) sender;
			p.getInventory().addItem(dash);
		}else if (args[0].equals("dashGive")) {
			ItemStack dash = new ItemStack(Material.FEATHER);
			ItemUtil.setName(dash, ChatColor.UNDERLINE+"Dash");
			ItemUtil.addAppaEnchant(dash);
			
			int a1 = 0;
			if (args.length > 1) {
				a1 = Integer.valueOf(args[1]);
				switch (a1) {
					case 1:
						dash.addUnsafeEnchantment(Enchantment.ARROW_DAMAGE, a1);
						break;
					case 2:
						
						dash.addUnsafeEnchantment(Enchantment.ARROW_DAMAGE, a1);
						break;
					case 3:
						dash.addUnsafeEnchantment(Enchantment.ARROW_DAMAGE, a1);
						break;
					case 4:
						dash.addUnsafeEnchantment(Enchantment.ARROW_INFINITE, 1);
						break;
				
					
				
				}
				if (args.length > 2) {
					dash.addUnsafeEnchantment(Enchantment.ARROW_FIRE, 1);
				}
			
			}
			
			Player p = (Player) sender;
			p.getInventory().addItem(dash);
		}else if (args[0].equals("pvp")) {
			String name ="pvpGame-"+MathUtil.generateAlInt(0, 1000);
			GameLg game = new GameLg(name);
			Main.strToGame.put(name, game);
			Player playerd = (Player )sender;
			playerd.sendMessage(ChatColor.DARK_BLUE +"game created: " + ChatColor.DARK_RED+ name);
			game.board.setgame(game);
			game.addPlayer(playerd.getName());
			
			ArrayList<RolesLg> roles = new ArrayList<RolesLg>(Arrays.asList(
				
					RolesLg.ANCIEN,
					RolesLg.ANGE,
					RolesLg.ASSASSIN,
					RolesLg.LOUP_SANGUINAIRE,
					RolesLg.BIENFAITEUR,
					RolesLg.CHASSEUR,
				
					RolesLg.CUPIDON,
				
					RolesLg.ENFANT_SAUVAGE,
					
					RolesLg.INFECT_PERE_DES_LOUPS,
				
					RolesLg.LOUP_ALCHIMISTE,
					RolesLg.LOUP_BARBARE,
					 
					
					
					RolesLg.LOUP_HURLEUR,
					RolesLg.LOUP_METAMORPHE, 
					
					
				 
					RolesLg.PARRAIN,
					
					RolesLg.PYROMANE, 
					 
					
					RolesLg.ERMITE,
					RolesLg.LOUP_CRAINTIF,
					
					RolesLg.SALVATEUR, 
					
					RolesLg.SIMPLE_VILLAGER, 
					RolesLg.NECROMANCIEN,
					RolesLg.SIMPLE_WOLF,
					
					
					RolesLg.SORCIERE, 
					RolesLg.VOLEUR,
					
					RolesLg.LOUP_BRUMEUX,
					RolesLg.DEMON,
					RolesLg.THANOS
					
					
					
					));
			
			
			ArrayList<RolesLg> rolesFinal = new ArrayList<RolesLg>();
			while (rolesFinal.size() < Bukkit.getOnlinePlayers().size()) {
				rolesFinal.add(roles.get(MathUtil.generateAlInt(0, roles.size() - 1)));
			}
			
			for (Player p:Bukkit.getOnlinePlayers()) {
				game.addPlayer(p.getName());
			}
			game.roles = rolesFinal;
			game.isMeetup = true;
			game.necrom = MathUtil.pourcentage(50);
			game.allDifferent = true;
			game.scenarioAct.put("DirectFights", true);
			
			
			String[] args1 = new String[] {"generer"};
			CommandUtil.runCommand("lga", playerd, args1);

			String[] args2 = new String[] {"Game", "start", game.getName()};
			CommandUtil.runCommand("lga", playerd, args2);


		}else if (args[0].equals("worldId")) {
			sender.sendMessage(((Player) sender).getLocation().getWorld().getUID().toString());
		}else if (args[0].equals("worldName")) {
			sender.sendMessage(((Player) sender).getLocation().getWorld().getName());
		}else if (args[0].equals("setRole")) {
			if (args.length < 3) {
				sender.sendMessage("manque d'arguments");
				return true;
			}
			
			PlayerData target = Main.strToPlayer.getOrDefault(args[1], null);
			if (target == null) {
				sender.sendMessage("Le joueur "+args[1]+ " n'existe pas ou n'est pas dans une partie");
				return true;
				
			}
			for (RolesLg role:RoleUtil.existingRoles) {
				if (role.getName().equals(args[2])) {
					target.role = role;
					target.roleIn = RoleUtil.createRoleOfPlayerRoles(target);
					target.roleIn.giveRoleEffectAndItem(target);
				}
			}
			
		}else if (args[0].equals("say")) {
			
			ArrayList<String> args2 = new ArrayList<String>();
			for (String str: args) {
				args2.add(str);
			}
			args2.remove(0);
			
			String mess = String.join(" ", args2);
			PlayerData p = Main.strToPlayer.getOrDefault(sender.getName(), null);
			if (p == null || p.game == null) {
				return true;
			}
			
			
			p.game.broadcoast(ChatColor.ITALIC + mess);
			return true;
		} else if (args[0].equals("epic")) {
			
			if (args.length > 1) {
				PlayerData p = Main.strToPlayer.getOrDefault(sender.getName(), null);
				GameLg game = null;
				if (p.game != null && p.game instanceof GameLg) {
					game = (GameLg) p.game;
				} else {
					return false;
				}
				if (p == null || game == null) {
					return true;
				}
				
				game.addEpic(Integer.valueOf(args[1]), ((Player) sender).getLocation());
			}
			
		}else if (args[0].equals("tragic")) {
			
			if (args.length > 1) {
				PlayerData p = Main.strToPlayer.getOrDefault(sender.getName(), null);
				GameLg game = null;
				if (p.game != null && p.game instanceof GameLg) {
					game = (GameLg) p.game;
				} else {
					return false;
				}
				if (p == null || game == null) {
					return true;
				}
				game.addTragic(Integer.valueOf(args[1]), ((Player) sender).getLocation());
			}
		}else if (args[0].equals("oratoire")) {
			if (args.length > 1) {
				PlayerData p = Main.strToPlayer.getOrDefault(sender.getName(), null);
				GameLg game = null;
				if (p.game != null && p.game instanceof GameLg) {
					game = (GameLg) p.game;
				} else {
					return false;
				}
				if (p == null || game == null) {
					return true;
				}
				game.addorat(Integer.valueOf(args[1]), ((Player) sender).getLocation());
			}
		} else if (args[0].equals("loc1")) {
			Player p = (Player) sender;
			Main.loc1 = p.getLocation();
		}else if (args[0].equals("loc2")) {
			Player p = (Player) sender;
			Main.loc2 = p.getLocation();
		} else if (args[0].equals("saveStruct")) {
			if (args.length < 2) {
				System.out.println("lenght not enought big");
				
				
			} else {
				
				StructureLoader.save(Main.loc1, Main.loc2, new File("schems/"+args[1] + ".schematic"));
			}
		}else if (args[0].equals("loadStruct")) {
			if (args.length < 2) {
				System.out.println("lenght not enought big");
				
				
			} else {
				
				try {
					StructureLoader.place(((Player) sender).getLocation(), StructureLoader.load(new File("schems/"+args[1] + ".schematic")),((Player) sender).getLocation().getWorld(), BukkitUtil.getLocalWorld(((Player) sender).getLocation().getWorld()).getWorldData());				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}else if (args[0].equals("openWorld")) {
			if (args.length < 2) {
				System.out.println("lenght not enought big");
				
				
			} else {
				World world = Bukkit.getWorld(args[1]);
				
				((Player) sender).teleport(world.getSpawnLocation());
			}
		}else if (args[0].equals("addNote")) {
			Main.strToPlayer.get(sender.getName()).notes.add(new GameNote(""));
		}  else if (args[0].equals("testMath")) {
			int it = 100;
			double sum = 0;
			for (int i = 0; i < 100; i++) {
				int x =MathUtil.generateAlInt(0 , 100);
				sum += x;
				if (x > 100 || x<0) {
					System.out.println("not good");
				}
			}
			System.out.println("moyenne  = "+ Double.toString(sum/it));
		} else if (args[0].equals("tpPotion")) {
			Potion potion = new Potion(PotionType.WATER_BREATHING, 1, true);
			ItemStack item = potion.toItemStack(1);
			ArrayList<String> strs = new ArrayList<String>();
			strs.add("Potion de Téléportation ");
			ItemUtil.setLore(item, strs);
			Player p = (Player) sender;
			p.getInventory().addItem(item);
		}else if (args[0].equals("ParalysiePotion")) {
			Potion potion = new Potion(PotionType.WATER_BREATHING, 1, true);
			ItemStack item = potion.toItemStack(1);
			ArrayList<String> strs = new ArrayList<String>();
			strs.add("Potion De Paralysie");
			ItemUtil.setLore(item, strs);
			Player p = (Player) sender;
			p.getInventory().addItem(item);
		} else if (args[0].equals("cauldronStruct")) {
			PlayerData p = Main.strToPlayer.getOrDefault(sender.getName(), null);
			if (p == null || p.game == null) {
				return true;
			}
			Main.placeCauldronStruct(((Player) sender).getLocation(), p.game);
		} else if (args[0].equals("sound")) {
			Player p = (Player) sender;
			p.playSound(p.getLocation(), Sound.WOLF_HOWL, 1, 1);
		} else if (args[0].equals("thanPlace")) {
			PlayerData p = Main.strToPlayer.getOrDefault(sender.getName(), null);
			if (p == null || p.game == null) {
				return true;
			}
			THANOS than = (THANOS) p.roleIn;
			Location loc = ((Player) sender).getLocation();
			
			switch (args[1]) {
				case "t":
					Main.placeStoneStruct(loc, new Stone(than, StonesType.TIME), p.game);
					break;
				case "s":
					Main.placeStoneStruct(loc, new Stone(than, StonesType.SPACE), p.game);
					break;
				case "p":
					Main.placeStoneStruct(loc, new Stone(than, StonesType.POWER), p.game);
					break;
				case "e":
					Main.placeStoneStruct(loc, new Stone(than, StonesType.MIND), p.game);
					break;
				case "a":
					Main.placeStoneStruct(loc, new Stone(than, StonesType.SOUL), p.game);
					break;
				case "r":
					Main.placeStoneStruct(loc, new Stone(than, StonesType.REALITY), p.game);
					break;
			}
		}
		if (args[0].equals("groupe")) {
			if (args.length < 2) {
				sender.sendMessage("Veuillez indiquer un nombre valide");
				
			} else {
				int g = 5;
				try {
					g = Integer.valueOf(args[1]);
					
				}  catch  (NumberFormatException e){
					sender.sendMessage("Veuillez indiquer un nombre valide");
					return false;
				}
				PlayerData p = Main.strToPlayer.getOrDefault(sender.getName(), null);
				GameLg game = null;
				if (p.game != null && p.game instanceof GameLg) {
					game = (GameLg) p.game;
				} else {
					return false;
				}
				if (p == null || game == null) {
					return true;
				}
				if (game == null) {
					sender.sendMessage("Vous devez etre dans une game pour effectuer cette commande");
					return false;
				}
				
				game.setGroupsTo(g);
			}
		}else if (args[0].equals("placeVote")) {
			PlayerData p = Main.strToPlayer.getOrDefault(sender.getName(), null);
			if (p == null || p.game == null) {
				return true;
			}
			Main.placeVoteStruct(((Player) sender).getLocation(),p.game);
		}else if (args[0].equals("placeAccuse")) {
			PlayerData p = Main.strToPlayer.getOrDefault(sender.getName(), null);
			if (p == null || p.game == null) {
				return true;
			}
			Main.placeAccuseStruct(((Player) sender).getLocation(), p.game);
		} else if (args[0].equals("auraDisplay")) {
			
			PlayerData p = Main.strToPlayer.getOrDefault(sender.getName(), null);
			GameLg game = null;
			if (p.game != null && p.game instanceof GameLg) {
				game = (GameLg) p.game;
			} else {
				return false;
			}
			if (p == null || game == null) {
				return true;
			}
			game.futuresActions.add(new FutureAction(new BukkitRunnable() {
				
				@Override
				public void run() {
					p.auraDiscoverEffetDuration +=10;

					
				}
			}, 5));
		} else if (args[0].equals("testDescr")) {
			sender.sendMessage(new LOUP_BRUMEUX(null).getDescription());
		} else if (args[0].equals("auraDisplayPotion")) {
			Potion potion = new Potion(PotionType.WATER_BREATHING, 1, true);
			ItemStack item = potion.toItemStack(1);
			ArrayList<String> strs = new ArrayList<String>();
			strs.add("Révéleur d'Aura");
			ItemUtil.setLore(item, strs);
			Player p = (Player) sender;
			p.getInventory().addItem(item);
		} else if (args[0].equals("placeSpecial")) {
			if (args.length<2) {
				sender.sendMessage("add argument please");
				
			} else {
				PlayerData p = Main.getData(sender);
				if (p == null || p.game == null) {
					sender.sendMessage("Vous n'etes pas dans une partie");
				}
				Location locP = ((Player) sender).getLocation();
				Location loc = new Location(locP.getWorld(), locP.getBlockX(), locP.getBlockY(), locP.getBlockZ());
				switch (args[1]) {
				case "RegisterModifier":
					Main.placeTreasureBlock(loc, TreasureBlockType.RegisterModifier, p.game);
					break;
				case "AuraAnalyser":
					Main.placeTreasureBlock(loc, TreasureBlockType.AuraAnalyser, p.game);
					break;
				case "Bienfaisance":
					Main.placeTreasureBlock(loc, TreasureBlockType.Bienfaisance, p.game);
					
					break;
				case "AuraPotion":
					Main.placeTreasureBlock(loc, TreasureBlockType.AuraPotion, p.game);
					break;
				case "ParalysiePotion":
					Main.placeTreasureBlock(loc, TreasureBlockType.ParalysiePotion, p.game);
					break;
				case "TeleporterPotion":
					Main.placeTreasureBlock(loc, TreasureBlockType.TeleporterPotion, p.game);
					break;
				}
			}
		}
		if (args[0].equals("generer")) {
			PlayerData p = Main.getData(sender);
			GameLg game = null;
			if (p.game != null && p.game instanceof GameLg) {
				game = (GameLg) p.game;
			} else {
				return false;
			}
			if (p != null && p.game != null) {
				WorldCreator c = new WorldCreator("world"+p.game.getName()).generator(Main.world.getGenerator());
				
				World worldGen;
				if (game.displayedRoles && game.isMeetup) {
					World starWorld = Bukkit.getWorld("worldLgRun");
					worldGen = WorldUtil.createNewWorld(starWorld);
				} else {
					World starWorld = Bukkit.getWorld("world");
					worldGen = WorldUtil.createNewWorld(starWorld);
				}
				
				p.game.setWorld(worldGen);
				
				if (game instanceof GameLg) {
					((GameLg) game).isWorldGenerated = true;
				}
				
				
				
				sender.sendMessage("map générée");
			}
		}else if (args[0].equals("map")) {
			PlayerData p = Main.getData(sender);
			if (p == null) {
				return false;
			}
			
			Game game = p.game;
			if (game == null) {
				return false;
			} else {
				p.player.teleport(game.getWorld().getSpawnLocation());
			}
		}else if (args[0].equals("kick")) {
			if (args.length < 3) {
				return false;
			}
			PlayerData p = Main.strToPlayer.getOrDefault(args[1], null);
			if (p == null) {
				return true;
			}
			if (p.game != null && p.game.getName().equals(Main.getData(sender).game.getName())) {
				
				GameLg game = null;
				if (p.game != null && p.game instanceof GameLg) {
					game = (GameLg) p.game;
				} else {
					return false;
				}
				game.removePlayer(p, " at remove command");
			}
		}
		
		if (args[0].equals("removeXp")) {
			if (args.length < 3) {
				return false;
			}
			int x = -1;
			x = Integer.valueOf(args[2]);
			if (x < 1) {
				return true;
			}
			PlayerData p = Main.strToPlayer.getOrDefault(args[1], null);
			if (p == null) {
				return true;
			}
			p.removeXp(x);
		}else if (args[0].equals("setXp")) {
			if (args.length < 3) {
				return false;
			}
			int x = -1;
			x = Integer.valueOf(args[2]);
			if (x < 1) {
				return true;
			}
			PlayerData p = Main.strToPlayer.getOrDefault(args[1], null);
			if (p == null) {
				return true;
			}
			p.setXp(x);
		}else if (args[0].equals("invite")) {
			
			
			GameLg game = null;
			if (Main.getData(sender).game != null && Main.getData(sender).game instanceof GameLg) {
				game = (GameLg) Main.getData(sender).game;
			} else {
				return false;
			}
			
			if (game == null) {
				if (sender instanceof Player) {
					Player player = (Player) sender;
					player.sendMessage("La partie " + args[2] + " n'existe pas");
					
				}return true;
			}
			
			if (args[1].equals("@a")) {
				for (Player p:Bukkit.getOnlinePlayers()) {
					String[] args2 = {"invite", p.getName()};
					onCommand(sender, cmd, msg, args2);
				}
			}
			
			for (PlayerData ply: game.getPlayerAlive()) {
				if (ply.Name.equals(args[3])) {
					if (sender instanceof Player) {
						Player player = (Player) sender;
						player.sendMessage("ce joueur est déjà dans une partie");
						
					}return true;
				}
			}
			Player playerToAdd = PlayerUtil.getPlayer(args[3]);
			if (playerToAdd == null) {
				if (sender instanceof Player) {
					Player player = (Player) sender;
					player.sendMessage("Le joueur " + args[3] + " n'existe pas");
					
					
					
				}return true;
			}
			
			
			game.addPlayer(args[3]);
			TextComponent text = new TextComponent();
			text.setText("Vous avez été invité dans la partie "+ ChatColor.GOLD + game.name+ ChatColor.WHITE + " par "+ ChatColor.GOLD + sender.getName() + ChatColor.WHITE +
					"\n"+"Cliquez ici pour rejoindre");
			text.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, ("/lg join")+ game.name));
			Main.strToPlayer.get(args[3]).player.spigot().sendMessage(text);
			
			return true;
			
			
		}else if (args[0].equals("noscore")) {
			
			PlayerData p = Main.getData(sender);
			GameLg game = null;
			if (p.game != null && p.game instanceof GameLg) {
				game = (GameLg) p.game;
			} else {
				return false;
			}
			if (p != null && p.game != null) {
				
				game.toRegister = !game.toRegister;
				if (game.toRegister) {
					sender.sendMessage("partie à enregister");
				} else {
					sender.sendMessage("partie à ne pas enregister");
				}
				
				
			}
		}else if (args[0].equals("transfer") ) {
			PlayerData old = Main.strToPlayer.getOrDefault(args[1], null);
			if (old == null) {
				return true;
			}
			
			Player ne = Main.server.getPlayer(args[2]);
			if (ne == null) {
				return true;
				
			}
			
			if (!old.isOnline || !ne.isOnline()) {
				sender.sendMessage("Un des 2 joueur n'est pas en ligne");
				return false;
			}
			
			for (PotionEffect effet:old.player.getActivePotionEffects()) {
				ne.addPotionEffect(effet);
				ne.getInventory().setContents(old.player.getInventory().getContents());
			}
			PlayerData newPlayer = Main.getData(ne);
			newPlayer.role = old.role;
			newPlayer.roleIn = old.roleIn;
			newPlayer.infected = old.infected;
			newPlayer.boostR5 = old.boostR5;
			newPlayer.boostR5 = old.boostS5;
			newPlayer.appCamp = old.appCamp;
			newPlayer.camp = old.camp;
						
			
			
			
		}else if (args[0].equals("Game")) {
			if (args[1].equals("config")) {
				
				if (args.length < 3) {
					System.out.println("il manque le nom de la game");
					return true;
				}
				
				GameLg gm = Main.strToGame.get(args[2]);
				
				Player player = (Player) sender;
				PlayerData plyD = Main.getData(player);
				if (gm != null) {
					
				}
				if (gm.swapperPente || gm.swapperDuel || gm.swapperQuadrio || gm.swapperTrio) {
					SwapperConfigInv config = new SwapperConfigInv(gm);
					config.open(plyD);
					return true;
				}
				
				gm.config.open(player, true);
				
				
				
				
				return true;
			}
			
			if (args[1] == null) {

				if (sender instanceof Player) {
					Player player = (Player) sender;
					player.sendMessage("Il manque du contenu à la commande");
					
				}return true;
			}
			
			if (args[1].equals("addTime")) {
				GameLg game = Main.strToGame.get(args[2]);
				
				if (game == null) {
					if (sender instanceof Player) {
						Player player = (Player) sender;
						player.sendMessage("La partie " + args[2] + " n'existe pas");
						
					}return true;
				}
				for (int i = 0; i < Integer.valueOf(args[3]); i++) {
					game.timer.addOne();
				}
				
				
			}
			if (args[1].equals("addTime2")) {
				GameLg game = Main.strToGame.get(args[2]);
				
				if (game == null) {
					if (sender instanceof Player) {
						Player player = (Player) sender;
						player.sendMessage("La partie " + args[2] + " n'existe pas");
						
					}return true;
				}
				for (int i = 0; i < Integer.valueOf(args[3]); i++) {
					game.everySec();
				}
				
				
			}
			
			
			
			//ICI
			if (args[1].equals("start")) {
				GameLg game = Main.strToGame.get(args[2]);
				System.out.println("game start now");
				
				if (game == null) {
					if (sender instanceof Player) {
						Player player = (Player) sender;
						player.sendMessage("La partie " + args[2] + " n'existe pas");
						
					}return true;
				}
				
				if (game.getActualNbOfPlayer() != game.roles.size()) {
					sender.sendMessage("Mauvais nombre de roles");
					return true;
				}
				
				if (!game.isWorldGenerated) {
					sender.sendMessage("le monde n'est pas généré: faites /lga generer");
					return true;
				} 
				
				game.statut = GameStatut.NOT_STARTED;
				Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.plug, new BukkitRunnable() {
					@Override
		        	public void run() {
						game.everySec();
					}
				}, 0, 20);
				
		        
		        
		        
		        
		        return true;
			}
			
			
			
			
			if (args[1].equals("registering")) {
				PlayerData p = Main.strToPlayer.getOrDefault(sender.getName(), null);
				if (p == null || p.game == null) {
					return true;
				}
				GameLg game = null;
				if (p.game != null && p.game instanceof GameLg) {
					game = (GameLg) p.game;
				} else {
					return false;
				}
				if (game != null) {
					game.toRegister = !game.toRegister;
					if (game.toRegister) {
						sender.sendMessage("Enregistrement de la partie activé");
					} else {
						sender.sendMessage("Enregistrement de la partie désactivé");
					}
				}
			}
			
			
			
			
			
			if (args[1].equals("create")) {
				if (args[2] == null) {
					//System.out.println("Error manque de contenu");
				}
				
				GameLg game = new GameLg(args[2]);
				Main.strToGame.put(args[2], game);
				Player playerd = (Player )sender;
				playerd.sendMessage(ChatColor.DARK_BLUE +"game created: " + ChatColor.DARK_RED+ args[2]);
				game.board.setgame(game);
				game.addPlayer(playerd.getName());
				
				String[] args1 = new String[] {"Game" , "config" , args[2]};
				CommandUtil.runCommand("lga", playerd, args1);
			
			}
			
			
			
			
			
			
			
			
			
			
			if (args[1].equals("compo")) {
				
				
				GameLg game = Main.strToGame.getOrDefault(args[2], null);
				
				if (game == null) {
					if (sender instanceof Player) {
						Player player = (Player) sender;
						player.sendMessage("La partie " + args[2] + " n'existe pas");
						
					}return true;
				}
				RoleDisplay.setDisplayed(game);
				if (sender instanceof Player) {
					Player player = (Player) sender;
					player.openInventory(RoleDisplay.myInventory);
					
				}
				
				
				
				
			}
			
			
			
			
			if (args[1].equals("bl") ) {
				if (args.length > 1) {
					PlayerData p = Main.getData(sender);
					GameLg game = null;
					if (p.game != null && p.game instanceof GameLg) {
						game = (GameLg) p.game;
					} else {
						return false;
					}
					
					game.isBanned.put(args[2], true);
					sender.sendMessage("Le joueur "+args[2]+ " est blacklist");
				}
			}else if (args[1].equals("wl")) {
				if (args.length > 1) {
					PlayerData p = Main.getData(sender);
					GameLg game = null;
					if (p.game != null && p.game instanceof GameLg) {
						game = (GameLg) p.game;
					} else {
						return false;
					}
					game.isBanned.put(args[2], false);
					sender.sendMessage("Le joueur "+args[2]+ " est whitelist");
				}
			}else if (args[1].equals("removePlayer")) {
				if (args[3]==null) {
					if (sender instanceof Player) {
						Player player = (Player) sender;
						player.sendMessage("il manque du contenue");
						
					}return true;
				}
				
				
				GameLg game = Main.strToGame.getOrDefault(args[2], null);
				
				
				if (game == null) {
					if (sender instanceof Player) {
						Player player = (Player) sender;
						player.sendMessage("La partie " + args[2] + " n'existe pas");
						
					}return true;
				}
				
				Player playerToRemove = PlayerUtil.getPlayer(args[3]);
				if (playerToRemove == null) {
					//System.out.println("error playerNotFound");
					if (sender instanceof Player) {
						Player player = (Player) sender;
						player.sendMessage("Le joueur " + args[3] + " n'existe pas");
						
						
						
					}return true;
				}
				game.removePlayer(game.getPlayer(args[3]), " at cmd (lga Game removePlayer)");
				if (sender instanceof Player) {
					Player player = (Player) sender;
					player.sendMessage("Joueur removed: "+ playerToRemove.getDisplayName());
					
					
					
				}return true;
				
				
				
				
			}
			
			if (args[1].equals("addRole")) {
				if (args[2] == null || args[3]==null) {
					if (sender instanceof Player) {
						Player player = (Player) sender;
						player.sendMessage("il manque du contenue à la commande");
						
					}return true;
				}
				
				
				GameLg game =  Main.strToGame.getOrDefault(args[2], null);
				if (game == null) {
					if (sender instanceof Player) {
						Player player = (Player) sender;
						player.sendMessage("La partie " + args[2] + " n'existe pas");
						
					}
					return true;
				}
				
				game.roles.add(RoleUtil.RoleofString(args[3]));
				if (sender instanceof Player) {
					Player player = (Player) sender;
					player.sendMessage("role ajouté: " +args[3] );
					
				}
				return true;
			}

			
			if (args[1].equals("removeRole")) {
							
							if (args[2] == null || args[3]==null) {
								if (sender instanceof Player) {
									Player player = (Player) sender;
									player.sendMessage("il manque du contenue");
									
								}return true;
							}
							
							
							GameLg game = Main.strToGame.getOrDefault(args[2], null);
							if (game == null) {
								if (sender instanceof Player) {
									Player player = (Player) sender;
									player.sendMessage("La partie " + args[2] + " n'existe pas");
									
								}return true;
							}
							
							
							game.roles.remove(RoleUtil.RoleofString(args[3]));

							System.out.println(args[3] + " retiré");
							sender.sendMessage(args[3] + " retiré");
							return true;
						}
			
			
			if (args[1].equals("delete")) {
				if (args[2] == null) {
					if (sender instanceof Player) {
						Player player = (Player) sender;
						player.sendMessage("il manque du contenue");
						return true;
					}
				}
				GameLg game = Main.strToGame.getOrDefault(args[2], null);
				if (game == null) {
					if (sender instanceof Player) {
						Player player = (Player) sender;
						player.sendMessage("La partie " + args[2] + " n'existe pas");
						return true;
					}
				}
				Bukkit.unloadWorld(game.world, false);
				
			}
			
			
			
			if (args[1].equals("listPlayer")) {
				GameLg game = Main.strToGame.getOrDefault(args[2], null);
				if (game == null) {
					if (sender instanceof Player) {
						Player player = (Player) sender;
						player.sendMessage("La partie " + args[2] + " n'existe pas");
						return true;
					}
				}
				
				if (sender instanceof Player) {
					Player player = (Player) sender;
					player.sendMessage(ChatColor.GREEN + "Joueurs de "+ game.name+": "+ "\n"+ChatColor.GRAY +game.ListPlayer());
					return true;
				}
			}
			
			
			if (args[1].equals("listRole")) {
				GameLg game = Main.strToGame.getOrDefault(args[2], null);

				if (game == null) {
					if (sender instanceof Player) {
						Player player = (Player) sender;
						player.sendMessage("La partie " + args[2] + " n'existe pas");
						return true;
					}
				}
				if (sender instanceof Player) {
					Player player = (Player) sender;
					player.sendMessage(game.getRolesAlive());
					return true;
				}
				
			} else if (args[1].equals("meetup")) {
				if (args.length == 2) {
					sender.sendMessage("Veuillez spécifier la game");
					return true;
				}
				GameLg game = Main.strToGame.getOrDefault(args[2], null);
				if (game == null) {
					sender.sendMessage("Veuillez indiquer un nom de partie valide");
					return true;

				}
				if (args.length == 3) {
					sender.sendMessage("Veuillez spécifier avec \"true\" ou \"false\"");
					return true;
				}
				
				if (args[3].equals("false")) {
					game.isMeetup = false;
					sender.sendMessage("La game "+ args[2] + " est réglé sur: meetup désactivé");
				} else if (args[3].equals("true")) {
					game.isMeetup = true;
					sender.sendMessage("La game "+ args[2] + " est réglé sur: meetup activé");
				} else {
					sender.sendMessage("veuillez indiquer \"true\" ou \"false\"");

				}
				
			} else if (args[1].equals("setRole")) {
				if (Main.strToPlayer.getOrDefault(args[2], null) != null) {
					PlayerData p = Main.strToPlayer.get(args[2]);
					for (RolesLg role:RoleUtil.existingRoles) {
						if (args[3].equals(role.getName())) {
							p.settedRole =  role;
						}
					}
				}
			}
		}
		return false;
	}
	
	
	public static boolean checkNbOfArg(int nb, String[] args, Player sender) {
		if (args.length< nb) {
			sender.sendMessage(Main.exclamation + " Il manque un argument à votre commande");
			
		} 
		return (!(args.length< nb));
	}
	
	
	// ── CONFIGURATION — à modifier avant déploiement ──────────────────────────
    private static final String SITE_URL  = "https://lgweb.onrender.com";       // URL du backend
    private static final String ADMIN_KEY = "lgmore-admin-key-fitzche";   // Même valeur que dans server.js
    // ──────────────────────────────────────────────────────────────────────────

    private static final Gson GSON = new GsonBuilder().serializeNulls().create();

    
    public boolean onCommandExport(CommandSender sender, Command cmd, String label, String[] args) {

        if (!sender.hasPermission("lgop") && !sender.isOp()) {
            sender.sendMessage(ChatColor.RED + "Permission insuffisante.");
            return true;
        }

        List<PlayerData> toExport = new ArrayList<>();

        if (args.length == 0) {
            // Tous les joueurs connus (connectés ou non)
            toExport.addAll(Main.strToPlayer.values());
            if (sender != null) {
            	sender.sendMessage(Main.info + ChatColor.GREEN
                    + "Export de tous les joueurs (" + toExport.size() + ")...");

            }
            
        } else if (args[0].equalsIgnoreCase("online")) {
            // Seulement les connectés
            for (PlayerData p : Main.strToPlayer.values()) {
                if (p.isOnline) toExport.add(p);
            }
            if (sender != null) {
            	 sender.sendMessage(Main.info + ChatColor.GREEN
                         + "Export des joueurs en ligne (" + toExport.size() + ")...");

            } 
           
        } else {
            // Un joueur spécifique
            String name = args[0];
            PlayerData p = Main.strToPlayer.getOrDefault(name,
                           Main.strToPlayer.getOrDefault(name.toLowerCase(), null));
            if (p == null) {
                sender.sendMessage(ChatColor.RED + "Joueur introuvable : " + name);
                return true;
            }
            toExport.add(p);
            sender.sendMessage(Main.info + ChatColor.GREEN
                    + "Export de " + p.getName() + "...");
        }

        // Envoi asynchrone pour ne pas bloquer le thread principal Spigot
        final List<PlayerData> finalList = toExport;
        new BukkitRunnable() {
            @Override
            public void run() {
                try {
                    String json   = buildPayload(finalList);
                    int    status = postToSite(json);

                    // Retour sur le thread principal pour envoyer le message
                    new BukkitRunnable() {
                        @Override public void run() {
                            if (status == 200) {
                                sender.sendMessage(Main.info + ChatColor.GREEN
                                        + "✔ Export réussi ("
                                        + finalList.size() + " joueur(s)) → " + SITE_URL);
                            } else {
                                sender.sendMessage(ChatColor.RED
                                        + "✘ Export échoué (HTTP " + status
                                        + "). Vérifiez l'URL et la clé admin dans ExportCommand.java.");
                            }
                        }
                    }.runTask(Main.plug);

                } catch (Exception e) {
                    e.printStackTrace();
                    new BukkitRunnable() {
                        @Override public void run() {
                            sender.sendMessage(ChatColor.RED
                                    + "✘ Erreur réseau : " + e.getMessage()
                                    + " — Vérifiez que le backend est lancé sur " + SITE_URL);
                        }
                    }.runTask(Main.plug);
                }
            }
        }.runTaskAsynchronously(Main.plug);

        return true;
    }

    /**
     * Sérialise une liste de PlayerData en JSON structuré pour le backend.
     * Tous les champs envoyés ici doivent correspondre à ce que le frontend attend.
     */
    private String buildPayload(List<PlayerData> players) {
        List<Map<String, Object>> list = new ArrayList<>();

        for (PlayerData p : players) {
            Map<String, Object> map = new HashMap<>();

            // ── Identité ───────────────────────────────────────────────────
            map.put("Name",     p.getName());
            map.put("xp",       p.xp);
            map.put("feathers", p.feathers);

            // ── Statistiques PvP Zone ──────────────────────────────────────
            map.put("pvpZoneKill",  p.pvpZoneKill);
            map.put("pvpZoneDeath", p.pvpZoneDeath);

            // ── Pierres d'Infinité ─────────────────────────────────────────
            map.put("hasSpaceUsed",   p.hasSpaceUsed);
            map.put("hasSoulUsed",    p.hasSoulUsed);
            map.put("hasPowerUsed",   p.hasPowerUsed);
            map.put("hasTimeUsed",    p.hasTimeUsed);
            map.put("hasRealityUsed", p.hasRealityUsed);
            map.put("hasMindUsed",    p.hasMindUsed);

            // ── Inventaire spécial ─────────────────────────────────────────
            map.put("specialItemsOwned",
                    p.specialItemsOwned != null ? new ArrayList<>(p.specialItemsOwned) : new ArrayList<>());

            // ── Grades ────────────────────────────────────────────────────
            map.put("grades",
                    p.grades != null ? new ArrayList<>(p.grades) : new ArrayList<>());

            // ── Annonces de mort personnalisées ───────────────────────────
            map.put("specialDeathAnnounces",
                    p.specialDeathAnnounces != null ? new ArrayList<>(p.specialDeathAnnounces) : new ArrayList<>());

            // ── Historique des parties ─────────────────────────────────────
            List<Map<String, Object>> notes = new ArrayList<>();
            if (p.notes != null) {
                for (GameNote note : p.notes) {
                    Map<String, Object> n = new HashMap<>();
                    n.put("name", note.name);
                    n.put("winningCamp", note.winningCamp != null
                            ? new HashMap<String, String>() {{ put("name", note.winningCamp.getName()); }}
                            : null);
                    n.put("winners",
                            note.winners != null ? new ArrayList<>(note.winners) : new ArrayList<>());

                    // Détail par joueur (PlayerNote)
                    List<Map<String, Object>> plys = new ArrayList<>();
                    if (note.plysNote != null) {
                        for (PlayerNote pn : note.plysNote) {
                            Map<String, Object> pm = new HashMap<>();
                            pm.put("name",     pn.name);
                            pm.put("role",     pn.role != null ? pn.role.getName() : null);
                            pm.put("kill",     pn.kill);
                            pm.put("inLove",   pn.inLove);
                            pm.put("infected", pn.infected);
                            pm.put("hasWin",   pn.hasWin);
                            plys.add(pm);
                        }
                    }
                    n.put("plysNote", plys);
                    notes.add(n);
                }
            }
            map.put("notes", notes);

            // ── Rôle forcé si applicable ───────────────────────────────────
            if (p.settedRole != null) {
                map.put("settedRole", p.settedRole.getName());
            }

            list.add(map);
        }

        Map<String, Object> payload = new HashMap<>();
        payload.put("players", list);
        return GSON.toJson(payload);
    }

    /**
     * Envoie le JSON au backend via HTTP POST.
     * Retourne le code de réponse HTTP (200 = succès).
     */
    private int postToSite(String jsonPayload) throws IOException {
        URL url = new URL(SITE_URL + "/api/ingest");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        conn.setRequestProperty("x-admin-key", ADMIN_KEY);
        conn.setDoOutput(true);
        conn.setConnectTimeout(5000);
        conn.setReadTimeout(10000);

        byte[] body = jsonPayload.getBytes(StandardCharsets.UTF_8);
        conn.setRequestProperty("Content-Length", String.valueOf(body.length));

        try (OutputStream os = conn.getOutputStream()) {
            os.write(body);
            os.flush();
        }

        int status = conn.getResponseCode();
        conn.disconnect();
        return status;
    }

}











