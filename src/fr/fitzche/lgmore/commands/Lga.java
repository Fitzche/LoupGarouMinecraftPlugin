package fr.fitzche.lgmore.commands;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
import org.mozilla.javascript.tools.shell.Environment;

import com.avaje.ebeaninternal.server.persist.BindValues.Value;
import com.google.common.util.concurrent.AbstractScheduledService.Scheduler;
import com.khorn.terraincontrol.TerrainControl;
import com.khorn.terraincontrol.TerrainControlEngine;
import com.onarandombox.MultiverseCore.MultiverseCore;
import com.onarandombox.MultiverseCore.utils.WorldManager;
import com.sk89q.worldedit.CuboidClipboard;
import com.sk89q.worldedit.WorldEdit;
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
import fr.fitzche.lgmore.Lg.SpecialsBlock.SpecialBlock;
import fr.fitzche.lgmore.Lg.SpecialsBlock.SpecialBlockType;
import fr.fitzche.lgmore.Lg.SpecialsBlock.TreasureBlockData;
import fr.fitzche.lgmore.Lg.SpecialsBlock.TreasureBlockType;
import fr.fitzche.lgmore.GameStatut;

import fr.fitzche.lgmore.RolesLg.RoleDisplay;
import fr.fitzche.lgmore.RolesLg.RolesLg;
import fr.fitzche.lgmore.RolesLg.THANOS;
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



public class Lga implements CommandExecutor  {
	
	public static RoleDisplay INVENTAIRE;
	
	
	
	
	@Deprecated
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
		
		if (args[0].equals("head")) {
			((Player) sender).getInventory().addItem(ItemUtil.getCustomHead("FITZCHE"));
			;
		}
		if (args[0].equals("setRole")) {
			if (args.length < 3) {
				sender.sendMessage("manque d'arguments");
				return true;
			}
			
			PlayerData target = Main.strToPlayer.getOrDefault(args[0], null);
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
			
		}
		if (args[0].equals("say")) {
			
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
				c.generator(Main.world.getGenerator());
				World copied = Main.plug.getServer().getWorld("world");
				copied = Main.world;
				
				
				c.copy(copied);
				
				//c.generatorSettings("{\"coordinateScale\":684.412,\"heightScale\":684.412,\"lowerLimitScale\":512.0,\"upperLimitScale\":512.0,\"depthNoiseScaleX\":200.0,\"depthNoiseScaleZ\":200.0,\"depthNoiseScaleExponent\":0.5,\"mainNoiseScaleX\":80.0,\"mainNoiseScaleY\":160.0,\"mainNoiseScaleZ\":80.0,\"baseSize\":8.5,\"stretchY\":12.0,\"biomeDepthWeight\":1.0,\"biomeDepthOffset\":0.0,\"biomeScaleWeight\":1.0,\"biomeScaleOffset\":0.0,\"seaLevel\":63,\"useCaves\":true,\"useDungeons\":true,\"dungeonChance\":8,\"useStrongholds\":true,\"useVillages\":true,\"useMineShafts\":true,\"useTemples\":true,\"useMonuments\":true,\"useRavines\":true,\"useWaterLakes\":true,\"waterLakeChance\":4,\"useLavaLakes\":true,\"lavaLakeChance\":80,\"useLavaOceans\":false,\"fixedBiome\":26,\"biomeSize\":4,\"riverSize\":4,\"dirtSize\":33,\"dirtCount\":10,\"dirtMinHeight\":0,\"dirtMaxHeight\":256,\"gravelSize\":33,\"gravelCount\":8,\"gravelMinHeight\":0,\"gravelMaxHeight\":256,\"graniteSize\":33,\"graniteCount\":0,\"graniteMinHeight\":0,\"graniteMaxHeight\":80,\"dioriteSize\":33,\"dioriteCount\":0,\"dioriteMinHeight\":0,\"dioriteMaxHeight\":80,\"andesiteSize\":33,\"andesiteCount\":0,\"andesiteMinHeight\":0,\"andesiteMaxHeight\":80,\"coalSize\":17,\"coalCount\":25,\"coalMinHeight\":0,\"coalMaxHeight\":128,\"ironSize\":12,\"ironCount\":25,\"ironMinHeight\":0,\"ironMaxHeight\":64,\"goldSize\":9,\"goldCount\":4,\"goldMinHeight\":0,\"goldMaxHeight\":32,\"redstoneSize\":15,\"redstoneCount\":13,\"redstoneMinHeight\":0,\"redstoneMaxHeight\":16,\"diamondSize\":8,\"diamondCount\":2,\"diamondMinHeight\":0,\"diamondMaxHeight\":16,\"lapisSize\":7,\"lapisCount\":3,\"lapisCenterHeight\":16,\"lapisSpread\":16}");
				Bukkit.getLogger().info(c.generatorSettings());
				World worldGen = c.createWorld();
				
				
				p.game.setWorld(worldGen);
				
				if (game instanceof GameLg) {
					((GameLg) game).isWorldGenerated = true;
				}
				
				
				
				sender.sendMessage("map générée");
			}
		}
		
		if (args[0].equals("map")) {
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
		}
		if (args[0].equals("kick")) {
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
		if (args[0].equals("addXp")) {
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
			p.addXp(x);
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
		}
		if (args[0].equals("setXp")) {
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
		}
		if (args[0].equals("invite")) {
			
			
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
			
			for (PlayerData ply: game.playerAlive) {
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
			
			
		}
		
		if (args[0].equals("noscore")) {
			
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
		}
		if (args[0].equals("transfer") ) {
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
			old.Name = ne.getName();
			old.player = ne;
						
			
			
			
		}
		
		if (args[0].equals("Game")) {
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
				
				if (game.playerAlive.size() != game.roles.size()) {
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
			}
			if (args[1].equals("wl")) {
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
			}
			
			
			if (args[1].equals("removePlayer")) {
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
				game.playerAlive.remove(game.getPlayerDataWithName(args[3]));
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
	

}
