package fr.fitzche.lgmore.commands;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.permissions.Permission;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Scoreboard;

import com.avaje.ebeaninternal.server.persist.BindValues.Value;
import com.google.common.util.concurrent.AbstractScheduledService.Scheduler;

import org.bukkit.command.TabCompleter.*;

import fr.fitzche.lgmore.Camp;
import fr.fitzche.lgmore.Main;
import fr.fitzche.lgmore.PlayerData;
import fr.fitzche.lgmore.Lg.GameLg;

import fr.fitzche.lgmore.GameStatut;
import fr.fitzche.lgmore.Love.Team;
import fr.fitzche.lgmore.RolesLg.RoleDisplay;
import fr.fitzche.lgmore.RolesLg.RolesLg;
import fr.fitzche.lgmore.Util.CommandUtil;
import fr.fitzche.lgmore.Util.GameLgUtil;
import fr.fitzche.lgmore.Util.PlayerUtil;
import fr.fitzche.lgmore.Util.RoleUtil;
import fr.fitzche.lgmore.Util.WorldUtil;
import fr.fitzche.lgmore.scoreboard.ScoreboardLg;
import fr.fitzche.lgmore.scoreboard.Inventory.ConfigDisplay;



public class Lga implements CommandExecutor  {
	
	public static RoleDisplay INVENTAIRE;
	
	
	
	
	@Deprecated
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
		
		
		if (args[0].equals("say")) {
			
			ArrayList<String> args2 = new ArrayList<String>();
			for (String str: args) {
				args2.add(str);
			}
			args2.remove(0);
			
			String mess = String.join(" ", args2);
			GameLg game = GameLgUtil.getGameOfPlayer(((Player) sender), "at command lga say");
			
			game.broadcoast(ChatColor.ITALIC + mess);
			return true;
		} else if (args[0].equals("epic")) {
			if (args.length > 1) {
				GameLg game = GameLgUtil.getGameOfPlayer(((Player) sender), "at command lga say");
				game.addEpic(Integer.valueOf(args[1]), ((Player) sender).getLocation());
			}
			
		}else if (args[0].equals("tragic")) {
			if (args.length > 1) {
				GameLg game = GameLgUtil.getGameOfPlayer(((Player) sender), "at command lga say");
				game.addTragic(Integer.valueOf(args[1]), ((Player) sender).getLocation());
			}
		}else if (args[0].equals("oratoire")) {
			if (args.length > 1) {
				GameLg game = GameLgUtil.getGameOfPlayer(((Player) sender), "at command lga say");
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
				return true;
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
				GameLg game =GameLgUtil.getGameOfPlayer((Player) sender,	 "at group command");
				if (game == null) {
					sender.sendMessage("Vous devez etre dans une game pour effectuer cette commande");
					return false;
				}
				
				game.setGroupsTo(g);
			}
		}else if (args[0].equals("placeVote")) {
			Main.placeVoteBlock(((Player) sender).getLocation());
		}else if (args[0].equals("placeAccuse")) {
			Main.placeAccuseBlock(((Player) sender).getLocation());
		}
		
		if (args[0].equals("transfer") ) {
			PlayerData old = PlayerUtil.checkExist(args[1]);
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
				
				if (args[2] == null) {
					System.out.println("il manque le nom de la game");
					return true;
				}
				
				GameLg gm = GameLgUtil.searchGame(args[2]);
				
				Player player = (Player) sender;
				 
				
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
				GameLg game = GameLgUtil.searchGame(args[2]);
				
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
			
			
			
			//ICI
			if (args[1].equals("start")) {
				GameLg game = GameLgUtil.searchGame(args[2]);
				
				if (game == null) {
					if (sender instanceof Player) {
						Player player = (Player) sender;
						player.sendMessage("La partie " + args[2] + " n'existe pas");
						
					}return true;
				}
				
				if (game.playerAlive.size() != game.roles.size()) {
					if (sender instanceof Player || (GameLgUtil.getHowManyRole(game, RolesLg.SOEUR) == 0 || GameLgUtil.getHowManyRole(game, RolesLg.SOEUR) == 1)) {
						Player player = (Player) sender;
						player.sendMessage("Mauvais nombre de roles");
					}
					return true;
				}
				if (Main.game == null) {
					if (sender instanceof Player) {
						Player player = (Player) sender;
						player.sendMessage("Aucune partie n'existe");
						
					}
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
			
			
			
			
			
			
			
			
			
			
			if (args[1].equals("create")) {
				if (args[2] == null) {
					//System.out.println("Error manque de contenu");
				}
				
				GameLg game = new GameLg(args[2]);
				Main.game = game;
				Player playerd = (Player )sender;
				playerd.sendMessage(ChatColor.DARK_BLUE +"game created: " + ChatColor.DARK_RED+ args[2]);
				game.board.setgame(game);
				
				String[] args1 = new String[] {"Game" , "config" , args[2]};
				CommandUtil.runCommand("lga", playerd, args1);
			
			}
			
			
			
			
			if (args[1].equals("addPlayer")) {
				if (Main.game == null) {
					if (sender instanceof Player) {
						Player player = (Player) sender;
						player.sendMessage("Aucune partie n'existe");
						
					}
					return true;
				} 
				GameLg game = GameLgUtil.searchGame(args[2]);
				
				if (game == null) {
					if (sender instanceof Player) {
						Player player = (Player) sender;
						player.sendMessage("La partie " + args[2] + " n'existe pas");
						
					}return true;
				}
				
				for (PlayerData ply: Main.game.playerAlive) {
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
				Player joueur = PlayerUtil.getPlayer(args[3]);
				joueur.sendMessage("vous avez été ajouté à " +ChatColor.RED+ game.name);
				if (sender instanceof Player) {
					Player player = (Player) sender;
					player.sendMessage("joueur ajouté" + ChatColor.DARK_AQUA + args[3]);
					
				}return true;
				
				
			}
			
			
			
			
			
			if (args[1].equals("compo")) {
				
				
				GameLg game = GameLgUtil.searchGame(args[2]);
				
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
			
			
			
			
			
			
			
			if (args[1].equals("removePlayer")) {
				if (args[3]==null) {
					if (sender instanceof Player) {
						Player player = (Player) sender;
						player.sendMessage("il manque du contenue");
						
					}return true;
				}
				
				
				GameLg game = GameLgUtil.searchGame(args[2]);
				
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
				
				
				GameLg game = GameLgUtil.searchGame(args[2]);
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
							
							
							GameLg game = GameLgUtil.searchGame(args[2]);
							if (game == null) {
								if (sender instanceof Player) {
									Player player = (Player) sender;
									player.sendMessage("La partie " + args[2] + " n'existe pas");
									
								}return true;
							}
							
							
							GameLgUtil.searchGame(args[2]).roles.remove(RoleUtil.RoleofString(args[3]));

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
				GameLg game = GameLgUtil.searchGame(args[2]);
				if (game == null) {
					if (sender instanceof Player) {
						Player player = (Player) sender;
						player.sendMessage("La partie " + args[2] + " n'existe pas");
						return true;
					}
				}
				Main.game = null;
				
			}
			
			
			
			if (args[1].equals("listPlayer")) {
				GameLg game = GameLgUtil.searchGame(args[2]);
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
				GameLg game = GameLgUtil.searchGame(args[2]);

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
				GameLg game = GameLgUtil.searchGame(args[2]);
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
				
			} 
		}
		return false;
	}
	

}
