package fr.fitzche.lgmore.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
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

import fr.fitzche.lgmore.GameLg;
import fr.fitzche.lgmore.GameStatut;
import fr.fitzche.lgmore.Main;
import fr.fitzche.lgmore.PlayerData;
import fr.fitzche.lgmore.Love.Team;
import fr.fitzche.lgmore.RolesLg.Camp;
import fr.fitzche.lgmore.RolesLg.RoleDisplay;
import fr.fitzche.lgmore.RolesLg.RolesLg;
import fr.fitzche.lgmore.Util.CommandUtil;
import fr.fitzche.lgmore.Util.GameLgUtil;
import fr.fitzche.lgmore.Util.PlayerUtil;
import fr.fitzche.lgmore.Util.RoleUtilLg;
import fr.fitzche.lgmore.Util.WorldUtil;
import fr.fitzche.lgmore.scoreboard.ScoreboardLg;
import fr.fitzche.lgmore.scoreboard.Inventory.ConfigDisplay;
import net.minecraft.server.v1_8_R1.Material;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class Lga implements CommandExecutor  {
	
	public static RoleDisplay INVENTAIRE;
	
	
	
	
	@Deprecated
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
		//System.out.println("tried");
		// TODO Auto-generated method stub
		//GameCommands
		//System.out.println(args[0] + args[1] + args[2]);
		
		
		if (!sender.isOp()) {
			sender.sendMessage(ChatColor.RED+"Vous n'avez pas cette permission");
			return true;
		}
		if (args[0].equals("say")) {
			
			ArrayList<String> args2 = new ArrayList<String>();
			for (String str: args) {
				args2.add(str);
			}
			args2.remove(0);
			
			String mess = String.join(" ", args2);
			Bukkit.broadcastMessage(ChatColor.ITALIC + mess);
			return true;
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
				 
				ConfigDisplay config = new ConfigDisplay(gm);
				config.open(player);
				
				
				
				
				return true;
			}
			
			if (args[1] == null) {
				//System.out.println("Error manque de contenu");

				if (sender instanceof Player) {
					Player player = (Player) sender;
					player.sendMessage("Il manque du contenu à la commande");
					
				}return true;
			}
			
			if (args[1].equals("addTime")) {
				
				
				//System.out.println("recherche de " + args[2]);
				GameLg game = GameLgUtil.searchGame(args[2]);
				
				if (game == null) {
					//System.out.println("error GameNotFound");
					//System.out.println(Main.games.get(0));
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
				
				//System.out.println("recherche de " + args[2]);
				GameLg game = GameLgUtil.searchGame(args[2]);
				
				if (game == null) {
					//System.out.println("error GameNotFound");
					//System.out.println(Main.games.get(0));
					if (sender instanceof Player) {
						Player player = (Player) sender;
						player.sendMessage("La partie " + args[2] + " n'existe pas");
						
					}return true;
				}
				
				if (game.playerAlive.size() != game.roles.size()) {
					//System.out.println("nombre de joueur non égal au nombre de roles");
					if (sender instanceof Player || (GameLgUtil.getHowManyRole(game, RolesLg.SOEUR) == 0 || GameLgUtil.getHowManyRole(game, RolesLg.SOEUR) == 1)) {
						Player player = (Player) sender;
						player.sendMessage("Mauvais nombre de roles");
					}
					return true;
				}
				
				
				if (Main.games == null) {
					if (sender instanceof Player) {
						Player player = (Player) sender;
						player.sendMessage("Aucune partie n'existe");
						
					}
					return true;
				} else {
					//System.out.println(Main.games.get(0).name);
				}
				
				
				
				game.statut = GameStatut.BEFORE_ROLE;
				Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.plug, new BukkitRunnable() {
					@Override
		        	public void run() {
						for (PlayerData ply:game.playerAlive) {
							for (PlayerData ply1:game.playerAlive) {
								if (!ply.equals(ply1) && !ply.canVoted.contains(ply1) && ply.getLocation().distance(ply1.getLocation()) < 20 && ply1.inLife && ply.inLife) {
									ply.canVoted.add(ply1);
								}
							}
						}
					}
				}, 0, 100);
				
		        Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.plug, new BukkitRunnable() {
		        	@Override
		        	public void run() {
		        		
		        		//System.out.println("runned");
		        		if (game.stopped) {
		        			return;
		        		}
		        		game.board.refresh();
		        		
		        		if (game.board.istimeRunned == false) {
		        			game.board.istimeRunned = true;
		        			game.timer.temps = -20;
		        			Bukkit.broadcastMessage(ChatColor.UNDERLINE + "La partie va commencer dans 20 secondes");
		        			
		        		}
		        		int x1 = game.timer.getEpisode();
		        		game.timer.addOne();
		        		int x2 = game.timer.getEpisode();
		        		
		        		if (game.timer.temps == -10) {
		        			for (PlayerData ply:game.players) {
		    					GameLgUtil.tpAl(ply);
		    					System.out.println("gived start kit Lga l.163");
		    					ply.player.getInventory().addItem(new ItemStack(org.bukkit.Material.BOOK, 7) );
		    					ply.player.getInventory().addItem(new ItemStack(org.bukkit.Material.COOKED_BEEF, 64) );
		    					
		    				}
		        		}
		        		if (game.timer.temps==0) {
		        			Bukkit.broadcastMessage(ChatColor.UNDERLINE + "La partie commence");
							for (PlayerData player: game.getPlayerAlive()) {
								if (player.Name.equals("FITZCHE")) {
									Bukkit.broadcastMessage("Le développeur est dans la partie...");
								}
							}

		        		}
		        		
		        		
		        		
		        		
		        		if (game.timer.temps == 1200) {
		        			
		        			//KIT de depart + tp aleatoire
		        			
		        			
		        			game.statut = GameStatut.IN_GAME;
		        			////System.out.println("marquage g19.1");
		        			game.attributeRoleToAll();
		        			////System.out.println("marquage g19.2");
		        			for (PlayerData player: game.getPlayerAlive()) {
		        				////System.out.println("marquage g19.2.mixte.1");
		        				player.roleIn = RoleUtilLg.createRoleOfPlayerRoles(player);
		        				player.sendMessage("Vous êtes "+player.roleIn.getName());
		        				player.player.sendMessage(player.roleIn.getDescription());
		        				game.rolesIn.add(player.roleIn);
		        				////System.out.println("marquage g19.2.mixte.2");
		        				player.roleIn.giveRoleEffectAndItem(player);
		        				////System.out.println("marquage g19.2.mixte.3");
		        			}
		        			
		        			game.lgTeam = new Team("Loups Garou", Camp.Wolf, game, game.getRealWolfAlive(), null, "at wolf team creating at == 1200", null, null, true, false, false, false);
			        		////System.out.println("marquage 1.5");
			        		game.villTeam = new Team("Village", Camp.Villager, game, game.getRealVillagerAlive(), null, "at village team creating at == 1200", null, null, true, false, false, false);

			        		
			        		game.teams.add(game.lgTeam);
			        		game.teams.add(game.villTeam);
		        			////System.out.println("marquage g19.3");
		        		}
		        		////System.out.println("marquage 1.5");
		        		game.board.refresh();
		        		
		        		////System.out.println("score updated");
		        		
		        		
		        		
		        		
		        		
		        		if (game.timer.temps > 1200) {
		        			//System.out.println("k.1");
		        			
		        			
		        			//FIN ????
		        			GameLgUtil.isEnded(game);
		        			
		        			for (PlayerData player: game.getPlayerAlive()) {
		        				
		        				if (player == null) {
		        					System.out.println("temp hear in time ?> 1200 ");
		        					return;
		        				}
		        				player.roleIn.giveEffectAllTime();
		        				//System.out.println("k.1.ser.2");
		        			}
		        			if (WorldUtil.getTime(Main.server.getWorld("world")).equals("DAY")) {
		        				//System.out.println("k.2");
		        				for (PlayerData player: game.getPlayerAlive()) {
		        					//System.out.println("k.2.ser.1");
		        					player.roleIn.giveDayEffect();
		        					//System.out.println("k.2.ser.2");
		        				}
		        			} else if (WorldUtil.getTime(Main.server.getWorld("world")).equals("NIGHT")) {
		        				//System.out.println("k.3");
		        				for (PlayerData player: game.getPlayerAlive()) {
		        					//System.out.println("k.3.ser.1");
		        					player.roleIn.giveNightEffectCheck();
		        					//System.out.println("k.3.ser.2");
		        				}
		        			}
		        			
		        		}
		        		
		        		if (x1 != x2) {
		        			game.playEpisode();
		        		//	System.out.println("nouvel episode");
		        		} else {
		        		//	System.out.println("nothing of new");
		        		}
		        		
		        			

		        		
		        	}
		        }
		        		
		        		
		        		, 0, 20);
		        
		        
		        
		        return true;
			}
			
			
			
			
			
			
			
			
			
			
			if (args[1].equals("create")) {
				if (args[2] == null) {
					//System.out.println("Error manque de contenu");
				}
				
				GameLg game = new GameLg(args[2]);
				Main.games.add(game);
				Player playerd = (Player )sender;
				playerd.sendMessage(ChatColor.DARK_BLUE +"game created: " + ChatColor.DARK_RED+ args[2]);
				game.board.setgame(game);
				String[] args1 = new String[] {"Game" , "config" , args[2]};
				CommandUtil.runCommand("lga", playerd, args1);
			
			}
			
			
			
			
			if (args[1].equals("addPlayer")) {
				if (Main.games == null) {
					if (sender instanceof Player) {
						Player player = (Player) sender;
						player.sendMessage("Aucune partie n'existe");
						
					}
					return true;
				} else {
					//System.out.println(Main.games.get(0).name);
				}
				//System.out.println("recherche de " + args[2]);
				GameLg game = GameLgUtil.searchGame(args[2]);
				
				if (game == null) {
					//System.out.println("error GameNotFound");
					//System.out.println(Main.games.get(0));
					if (sender instanceof Player) {
						Player player = (Player) sender;
						player.sendMessage("La partie " + args[2] + " n'existe pas");
						
					}return true;
				}
				
				for (GameLg game1: Main.games) {
					for (PlayerData ply: game1.playerAlive) {
						if (ply.Name.equals(args[3])) {
							//System.out.println("player already in Game");
							if (sender instanceof Player) {
								Player player = (Player) sender;
								player.sendMessage("ce joueur est déjà dans une partie");
								
							}return true;
						}
					}
				}
				Player playerToAdd = PlayerUtil.getPlayer(args[3]);
				if (playerToAdd == null) {
					//System.out.println("error playerNotFound");
					if (sender instanceof Player) {
						Player player = (Player) sender;
						player.sendMessage("Le joueur " + args[3] + " n'existe pas");
						
						
						
					}return true;
				}
				
				
				game.addPlayer(args[3]);
				Player joueur = PlayerUtil.getPlayer(args[3]);
				joueur.sendMessage("vous avez été ajouté à " +ChatColor.RED+ game.name);
				//System.out.println("done");
				
				//System.out.println("marquage 1.4");
				
				if (sender instanceof Player) {
					Player player = (Player) sender;
					player.sendMessage("joueur ajouté" + ChatColor.DARK_AQUA + args[3]);
					
				}return true;
				
				
			}
			
			
			
			
			
			if (args[1].equals("compo")) {
				
				
				GameLg game = GameLgUtil.searchGame(args[2]);
				
				if (game == null) {
					//System.out.println("error GameNotFound");
					//System.out.println(Main.games.get(0));
					if (sender instanceof Player) {
						Player player = (Player) sender;
						player.sendMessage("La partie " + args[2] + " n'existe pas");
						
					}return true;
				}
				
				//System.out.println("marquage 1.2");
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
					//System.out.println("error GameNotFound");
					//System.out.println(Main.games.get(0));
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
				//System.out.println("player removed");
				if (sender instanceof Player) {
					Player player = (Player) sender;
					player.sendMessage("Joueur removed: "+ playerToRemove.getDisplayName());
					
					
					
				}return true;
				
				
				
				
			}
			
			if (args[1].equals("addRole")) {
				//System.out.println("started to add player");
				
				if (args[2] == null || args[3]==null) {
					if (sender instanceof Player) {
						Player player = (Player) sender;
						player.sendMessage("il manque du contenue");
						
					}return true;
				}
				
				
				GameLg game = GameLgUtil.searchGame(args[2]);
				//System.out.println("marquage 1.1");
				if (game == null) {
					//System.out.println("error GameNotFound");
					//System.out.println(Main.games.get(0));
					if (sender instanceof Player) {
						Player player = (Player) sender;
						player.sendMessage("La partie " + args[2] + " n'existe pas");
						
					}
					return true;
				}
				
				game.roles.add(RoleUtilLg.RoleofString(args[3]));
				if (sender instanceof Player) {
					Player player = (Player) sender;
					player.sendMessage("role ajouté: " +args[3] );
					
				}
				
				
				//System.out.println("marquage 1.2");
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
								//System.out.println("error GameNotFound");
								//System.out.println(Main.games.get(0));
								if (sender instanceof Player) {
									Player player = (Player) sender;
									player.sendMessage("La partie " + args[2] + " n'existe pas");
									
								}return true;
							}
							
							
							GameLgUtil.searchGame(args[2]).roles.remove(RoleUtilLg.RoleofString(args[3]));

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
					//System.out.println("error GameNotFound");
					//System.out.println(Main.games.get(0));
					if (sender instanceof Player) {
						Player player = (Player) sender;
						player.sendMessage("La partie " + args[2] + " n'existe pas");
						return true;
					}
				}
				Main.games.remove(game);
				//System.out.println("game removed");
				
			}
			
			
			
			if (args[1].equals("listPlayer")) {
				
				
				
				
				//System.out.println("1");
				GameLg game = GameLgUtil.searchGame(args[2]);
				//System.out.println("2");

				if (game == null) {
					//System.out.println("error GameNotFound");
					//System.out.println(Main.games.get(0));
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
				//System.out.println("1");
				GameLg game = GameLgUtil.searchGame(args[2]);
				//System.out.println("2");

				if (game == null) {
					//System.out.println("error GameNotFound");
					//System.out.println(Main.games.get(0));
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
				
			}
		}
		
		
		
		
		
		//System.out.println("not command");
		return false;
	}
	

}
