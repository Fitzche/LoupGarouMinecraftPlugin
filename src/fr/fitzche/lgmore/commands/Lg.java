package fr.fitzche.lgmore.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import fr.fitzche.lgmore.GameLg;
import fr.fitzche.lgmore.Main;
import fr.fitzche.lgmore.PlayerData;
import fr.fitzche.lgmore.RolesLg.ANGE;
import fr.fitzche.lgmore.RolesLg.BIENFAITEUR;
import fr.fitzche.lgmore.RolesLg.CUPIDON;
import fr.fitzche.lgmore.RolesLg.Camp;
import fr.fitzche.lgmore.RolesLg.DISCIPLE;
import fr.fitzche.lgmore.RolesLg.ENFANT_SAUVAGE;
import fr.fitzche.lgmore.RolesLg.INFECT_PERE_DES_LOUPS;
import fr.fitzche.lgmore.RolesLg.INTERPRETE;
import fr.fitzche.lgmore.RolesLg.PARRAIN;
import fr.fitzche.lgmore.RolesLg.PYROMANE;
import fr.fitzche.lgmore.RolesLg.RENARD;
import fr.fitzche.lgmore.RolesLg.RoleInstance;
import fr.fitzche.lgmore.RolesLg.RolesLg;
import fr.fitzche.lgmore.RolesLg.SALVATEUR;
import fr.fitzche.lgmore.RolesLg.SORCIERE;
import fr.fitzche.lgmore.RolesLg.VOYANTE;
import fr.fitzche.lgmore.Util.GameLgUtil;
import fr.fitzche.lgmore.Util.PlayerUtil;
import fr.fitzche.lgmore.Util.PotionUtil;
import fr.fitzche.lgmore.Util.RoleUtilLg;
import fr.fitzche.lgmore.scoreboard.Inventory.playersDisplay;

public class Lg implements CommandExecutor {

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
		// TODO Auto-generated method stub
		if (args[0].equals("revive")) {
			if ((GameLgUtil.getGameOfPlayer((Player) sender, " at command revive of Lg, 1").getPlayer(sender.getName())).role.equals(RolesLg.INFECT_PERE_DES_LOUPS)){
				Player ply = PlayerUtil.getPlayer(args[1]);
				GameLg gm = GameLgUtil.getGameOfPlayer(ply, " at command revive of Lg 2");
				PlayerData plyD = gm.getPlayer(ply.getName());
				if (plyD.inLife==false) {
					sender.sendMessage(ChatColor.BLUE + "Ce joueur est mort, il est trop tard pour l'infecter");
					return true;
				} 
				plyD.relive = true;
				plyD.infected = true;
				plyD.camp = Camp.Wolf;
				
				if (!plyD.inLove) {
					
					gm.lgTeam.add(plyD);
				}
				
				Player plyI = PlayerUtil.getPlayer(args[2]);
				
				INFECT_PERE_DES_LOUPS role = (INFECT_PERE_DES_LOUPS) PlayerUtil.getDataOfPlayer(plyI, " at command revive of Lg, 3 ").roleIn;
				role.powerUsed = true;
				for (PlayerData loup:gm.getFalseWolfAlive()) {
					loup.sendMessage(ChatColor.GOLD+"Le joueur "+ plyI.getName() + " a rejoint votre camp");
				}
				plyI.sendMessage("Vous avez infecté "+ ply.getName());
				return true;
				
				
			} else if ((GameLgUtil.getGameOfPlayer((Player) sender, " at command revive of Lg 3").getPlayer(sender.getName())).role.equals(RolesLg.SORCIERE)) {
				Player ply = PlayerUtil.getPlayer(args[1]);
				GameLg gm = GameLgUtil.getGameOfPlayer(ply, " at command revive of Lg, 4");
				PlayerData plyD = gm.getPlayer(ply.getName());
				if (plyD.inLife==false) {
					sender.sendMessage(ChatColor.BLUE + "Ce joueur est mort, il est trop tard pour le ressuciter");
					return true;
				} 
				plyD.relive = true;
				Player plyS = PlayerUtil.getPlayer(args[2]);

				SORCIERE role = (SORCIERE) PlayerUtil.getDataOfPlayer(plyS, " at command revive of Lg, 5").roleIn;
				role.powerUsed = true;
				Player s = (Player) sender;
				s.sendMessage("Vous avez ressucité "+ plyD.Name);
				
				return true;
			} else {
				Player player = (Player) sender;
				player.sendMessage("vous n'etes pas sorcière ou infect père des loups");
				return true;
			}
			
			
			
			
		} else if (args[0].equals("voir")) {
			
			Player player = (Player) sender;
			
			if (!PlayerUtil.getDataOfPlayer(player, " at command voir of Lg, 1").role.equals(RolesLg.VOYANTE)) {
				player.sendMessage(ChatColor.BLACK + "vous n'etes pas voyante !!!");
				return true;
			}
			
			VOYANTE role = (VOYANTE) PlayerUtil.getDataOfPlayer(player, " at command voir of Lg, 2").roleIn;
			if (role.powerUsed) {
				player.sendMessage(ChatColor.BLACK + "vous avez déjà utilisé votre pouvoir !!!");
				return true;
			
			}
			PlayerData ply = PlayerUtil.getDataOfPlayer(PlayerUtil.getPlayer(args[1]), " at command voir of Lg, 3");
			
			if (ply.camp.equals(Camp.Villager)) {
				player.damage(10);
				player.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 300, 1, false, false));
		
				player.sendMessage(ChatColor.BLUE + ply.Name + " est "+ ply.role);
			}  else {
				player.sendMessage(ChatColor.BLUE + ply.Name + " est "+ ply.role+ ", il se trouve en "+ply.player.getLocation().getBlockX()+ ", "+ply.player.getLocation().getBlockY() + ", " + ply.player.getLocation().getBlockZ());

			}
			
			role.powerUsed = true;
			
			
			
			
			
			
			
			
		} else if (args[0].equals("list")) {
			
			
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
			GameLg game = GameLgUtil.searchGame(args[1]);
			
			if (game == null) {
				//System.out.println("error GameNotFound");
				//System.out.println(Main.games.get(0));
				if (sender instanceof Player) {
					Player player = (Player) sender;
					player.sendMessage("La partie " + args[2] + " n'existe pas");
					
				}return true;
			}
			
			
			playersDisplay display = new playersDisplay(game.players, null);
			Player p = (Player) sender;
			display.display(p, null);
			Main.server.getPluginManager().registerEvents(display, Main.plug);
			
		} else if (args[0].equals("couple")) {
			
			
			Player cupi = (Player) sender;
			if (!PlayerUtil.getDataOfPlayer(cupi, " at command couple of Lg, 1").getLgRole().equals(RolesLg.CUPIDON)){
				cupi.sendMessage("Vous n'etes pas cupidon !!");
			}
			
			playersDisplay choose = new playersDisplay(GameLgUtil.getGameOfPlayer(cupi, " at command couple of Lg, 2").getPlayerAlive(), "/couple ");
			Main.server.getPluginManager().registerEvents(choose, Main.plug);

			choose.display(cupi, args);
			
			
			
		} else if (args[0].equals("don")) {
			Player giver = (Player) sender;
			PlayerData player = PlayerUtil.checkExist(giver.getName());
			if (player == null) {
				return true;
			}
			GameLg gm = GameLgUtil.getGameOfPlayer(giver, " at command don of Lg, 1");
			
			if (!PlayerUtil.getDataOfPlayer(giver, " at command don of Lg, 2").inLove) {
				giver.sendMessage(ChatColor.DARK_RED+"Vous n'etes pas en couple !! ");
				return true;
			}
			
			if (args[1] == null) {
				giver.sendMessage(ChatColor.DARK_RED+"Veuillez entrer un nombre ");
				return true;
			}
			
			if (Integer.valueOf(args[1]) == null) {
				giver.sendMessage(ChatColor.DARK_RED+"Veuillez entrer un nombre valide ");
				return true;

			}
			
			player.team.donCouple(player, Integer.valueOf(args[1]));
			
			System.out.println(args[1]);
			
		} else if (args[0].equals("vote")){
			
			
			PlayerData player = PlayerUtil.getDataPlayer(sender.getName(), "at command ''vote'' of Lg, 1 ");
			if (!GameLgUtil.getGameOfPlayer(player.player, "at command ''vote'' of Lg, 2 ").isInVote) {
				player.sendMessage(ChatColor.GOLD +"Ce n'est pas l'heure du vote");
			} 
			
			
			PlayerData voted = PlayerUtil.getDataPlayer(args[1], "at command ''vote'' of Lg,  3");
			
			if (!player.canVoted.contains(voted)) {
				player.sendMessage(ChatColor.GOLD +"Vous n'avez pas croisé ce joueur, vous ne pouvez donc pas voter pour celui-ci");
				return true;

			}
			
			if (player.voted != null) {
				player.voted.vote --;
			}
			
			player.voted = voted;
			player.sendMessage("Vous avez voté pour "+ voted.Name +", pour changer, faites /lg vote");
			voted.vote ++;
		} else if (args[0].equals("tirer")) {
			if (PlayerUtil.getPlayer(args[1]) != null && GameLgUtil.getGameOfPlayer(PlayerUtil.getPlayer(args[1]), "at command tirer of Lg commander") != null && PlayerUtil.getDataPlayer(args[1], "at command tirer of Lg commander 2").inLife) {
				
				GameLg gameOfTarget = GameLgUtil.getGameOfPlayer(PlayerUtil.getPlayer(args[1]), "at command tirer of Lg commander");
				PlayerData target = PlayerUtil.getDataPlayer(args[1], "at command tirer of Lg commander 2");
				
				if (gameOfTarget.hunter.equals(sender)) {
					gameOfTarget.Hunter.shoot(target);
				}
			}
		} else if (args[0].equals("proteger")) {
			if (PlayerUtil.getPlayer(args[1]) != null && GameLgUtil.getGameOfPlayer(PlayerUtil.getPlayer(args[1]), "at command tirer of Lg commander") != null && PlayerUtil.getDataPlayer(args[1], "at command tirer of Lg commander 2").inLife) {
				GameLg gameOfTarget = GameLgUtil.getGameOfPlayer(PlayerUtil.getPlayer(args[1]), "at command tirer of Lg commander");
				PlayerData target = PlayerUtil.getDataPlayer(args[1], "at command tirer of Lg commander 2");
				
				if (PlayerUtil.getDataOfPlayer((Player) sender, "at Lg command proteger").role.equals(RolesLg.SALVATEUR)) {
					SALVATEUR salvateur = (SALVATEUR) PlayerUtil.getDataOfPlayer((Player) sender, "at Lg command proteger").roleIn;
					if (!salvateur.powerUsed) {
						salvateur.proteger(target);
					}
					return true;
				}
				return true;
			}
		} else if (args[0].equals("conferer")) {
			Player bienfaiteurP = (Player) sender;
			PlayerData bienfaiteur = PlayerUtil.getDataOfPlayer(bienfaiteurP, "at /lg conferer command");
			PlayerData target = PlayerUtil.checkExist(args[1]);
			
			if (target == null && target.inLife && !target.Name.equals(bienfaiteur.Name)) {
				bienfaiteur.sendMessage("Veuillez spécifier un nom valide dans votre commande");
				return true;
			} else if (bienfaiteur.role != RolesLg.BIENFAITEUR){
				bienfaiteur.sendMessage("Vous n'etes pas bienfaiteur");
				return true;

			} else {
				BIENFAITEUR bft = (BIENFAITEUR) bienfaiteur.roleIn;
				bft.conferer(target);
				return true;
			}
		} else if (args[0].equals("choose")) {
			
			Player enfant = (Player) sender;
			PlayerData sauvage = PlayerUtil.getDataOfPlayer(enfant, "at /lg choose command");

			PlayerData target = PlayerUtil.checkExist(args[1]);
			
			if (sauvage.role == RolesLg.ENFANT_SAUVAGE) {
				if (target != null&& target.inLife) {
					ENFANT_SAUVAGE es = (ENFANT_SAUVAGE) sauvage.roleIn;
					es.choose(target);
				} else {
					sender.sendMessage(ChatColor.DARK_RED+"Choix Invalide");
				}
				
			} else {
				sender.sendMessage("Vous n'etes pas enfant sauvage");
			}
		} else if (args[0].equals("role")) {
			Player player = (Player) sender; 
			PlayerData ply = PlayerUtil.getDataOfPlayer(player, "at command /lg role");
			GameLg game = GameLgUtil.getGameOfPlayer(ply, "at /lg role command");
			
			ply.sendMessage(ply.roleIn.getDescription());
			if (ply.inLove) {
				ply.sendMessage(ChatColor.LIGHT_PURPLE+ "En couple avec: "+ ply.team.getNextCouple(ply).Name);
			}
			
			if (ply.infected) {
				ply.sendMessage(ChatColor.RED+"Infecté");
			}
			
			if (ply.camp.equals(Camp.Wolf) || ply.role.getCampOfRole().equals(Camp.Wolf)) {
				ply.sendMessage(ChatColor.RED+"Liste:" + "\n");
				if (game.timer.temps > 2699) {
					for (PlayerData loup: game.getFalseWolfAlive()) {
						ply.sendMessage(ChatColor.RED+"-"+ loup.Name+ "\n");
					}
				} else {
					ply.sendMessage(ChatColor.RED+"Attendez 45min pour avoir la liste");
				}
				
				
			}
			return true;
		} else if (args[0].equals("flairer")) {
			PlayerData target = PlayerUtil.checkExist(args[1]);
			Player player = (Player) sender;
			PlayerData renard = PlayerUtil.getDataOfPlayer(player, "at /lg flairer command");
			if (renard.role.equals(RolesLg.RENARD)) {
				RENARD renards = (RENARD) renard.roleIn;
				renards.flairer(target);
				return true;
			}
			
		}else if (args[0].equals("chooseInter")) {
			PlayerData player = PlayerUtil.getDataPlayer(args[2], "at Command interpreter/choose");
			
			INTERPRETE role = (INTERPRETE) player.roleIn;
			role.choosen = RoleUtilLg.RoleofString(args[1]);
			role.playerWithRole.sendMessage("Vous avez choisi: "+ args[1]);
		}else if (args[0].equals("aura")) {
			
			if (sender instanceof Player) {
				Player senderPlayer = (Player) sender;
				PlayerData senderPlayerData = PlayerUtil.checkExist(senderPlayer.getName());
				if (senderPlayerData != null) {
					GameLg gameOfSender = GameLgUtil.getGameOfPlayer(senderPlayerData, "at aura command check game player exist");
					if (gameOfSender != null) {
						if (senderPlayerData.role.equals(RolesLg.DISCIPLE)) {
							DISCIPLE disciple = (DISCIPLE) senderPlayerData.roleIn;
							if (disciple.secondUnlock) {
								PlayerData target = PlayerUtil.checkExist(args[1]);
								if (target == null) {
									sender.sendMessage(ChatColor.RED +"[/lg aura] veuillez choisir un joueur valide");
								} else {
									if (!disciple.powerUsed) {
										sender.sendMessage("L'aura du joueur "+ target.Name + " est "+ target.aura);
										disciple.powerUsed = true;
									} else {
										sender.sendMessage("L'aura du joueur "+ target.Name + " est "+ target.aura);
										disciple.secondUnlock = false;
									}
								}
							}
						}
					} else {
						sender.sendMessage("Vous devez etre dans une partie pour effectuer cette commande");
						return true;
					}
				} else {
					sender.sendMessage("Aucune info ne vous est associé, seul un joueur participant à une partie peut effectuer cette commande");
					return true;
				}
				
			} else {
				sender.sendMessage("Seul un joueur peut effectuer cette commande");
				return true;
			}
			
			
		
		}else if (args[0].equals("trouver")) {
			
			if (sender instanceof Player) {
				Player senderPlayer = (Player) sender;
				PlayerData senderPlayerData = PlayerUtil.checkExist(senderPlayer.getName());
				if (senderPlayerData != null) {
					GameLg gameOfSender = GameLgUtil.getGameOfPlayer(senderPlayerData, "at aura command check game player exist");
					if (gameOfSender != null) {
						if (senderPlayerData.role.equals(RolesLg.DISCIPLE)) {
							DISCIPLE disciple = (DISCIPLE) senderPlayerData.roleIn;
							sender.sendMessage(ChatColor.GOLD +"La dernière position connue du vieux sage est "+ disciple.sageLocation.getX() + "; "+ disciple.sageLocation.getY() + "; "+ disciple.sageLocation.getZ() );
						}
					} else {
						sender.sendMessage("Vous devez etre dans une partie pour effectuer cette commande");
						return true;
					}
				} else {
					sender.sendMessage("Aucune info ne vous est associé, seul un joueur participant à une partie peut effectuer cette commande");
					return true;
				}
				
			} else {
				sender.sendMessage("Seul un joueur peut effectuer cette commande");
				return true;
			}
			
			
		
		}else if (args[0].equals("recouvrir")) {
			System.out.println("covered");
			if (sender instanceof Player) {
				Player senderPlayer = (Player) sender;
				PlayerData senderPlayerData = PlayerUtil.checkExist(senderPlayer.getName());
				if (senderPlayerData != null) {
					GameLg gameOfSender = GameLgUtil.getGameOfPlayer(senderPlayerData, "at recouvrir command check game player exist");
					if (gameOfSender != null) {
						if (senderPlayerData.role.equals(RolesLg.PYROMANE)) {
							PYROMANE pyro = (PYROMANE) senderPlayerData.roleIn;
							pyro.recouvrir(PlayerUtil.getDataPlayer(args[1], "at command recouvrir check target exist"));
							return true;
						}
					} else {
						sender.sendMessage("Vous devez etre dans une partie pour effectuer cette commande");
						return true;
					}
				} else {
					sender.sendMessage("Aucune info ne vous est associé, seul un joueur participant à une partie peut effectuer cette commande");
					return true;
				}
				
			}
		}else if (args[0].equals("enflammer")) {
				System.out.println("enflammed");
				if (sender instanceof Player) {
					Player senderPlayer = (Player) sender;
					PlayerData senderPlayerData = PlayerUtil.checkExist(senderPlayer.getName());
					if (senderPlayerData != null) {
						GameLg gameOfSender = GameLgUtil.getGameOfPlayer(senderPlayerData, "at recouvrir command check game player exist");
						if (gameOfSender != null) {
							if (senderPlayerData.role.equals(RolesLg.PYROMANE)) {
								PYROMANE pyro = (PYROMANE) senderPlayerData.roleIn;
								pyro.allumer();
								return true;
							}
						} else {
							sender.sendMessage("Vous devez etre dans une partie pour effectuer cette commande");
							return true;
						}
					} else {
						sender.sendMessage("Aucune info ne vous est associé, seul un joueur participant à une partie peut effectuer cette commande");
						return true;
					}
					
				} 
		}else if (args[0].equals("switchfire")) {
					System.out.println("switch");
					if (sender instanceof Player) {
						Player senderPlayer = (Player) sender;
						PlayerData senderPlayerData = PlayerUtil.checkExist(senderPlayer.getName());
						if (senderPlayerData != null) {
							GameLg gameOfSender = GameLgUtil.getGameOfPlayer(senderPlayerData, "at recouvrir command check game player exist");
							if (gameOfSender != null) {
								if (senderPlayerData.role.equals(RolesLg.PYROMANE)) {
									PYROMANE pyro = (PYROMANE) senderPlayerData.roleIn;
									pyro.fireaspect();
									return true;
								}
							} else {
								sender.sendMessage("Vous devez etre dans une partie pour effectuer cette commande");
								return true;
							}
						} else {
							sender.sendMessage("Aucune info ne vous est associé, seul un joueur participant à une partie peut effectuer cette commande");
							return true;
						}
						
					} else{
				sender.sendMessage("Seul un joueur peut effectuer cette commande");
				return true;
			} 
		} else if (args[0].equals("angechoose")) {
			PlayerData player = PlayerUtil.getDataPlayer(args[2], "at ange command");
			if (player.role.equals(RolesLg.ANGE)) {
				ANGE ange = (ANGE) player.roleIn;
				switch (args[1]) {
				case "d":
					ange.chooseVersion(false);

				case "g":
					ange.chooseVersion(true);

			}
			}
			
		} else if (args[0].equals("prime")){
			PlayerData target = PlayerUtil.checkExist(args[1]);
			Player player = (Player) sender;
			PlayerData parrain = PlayerUtil.getDataOfPlayer(player, "at /lg cibler command");
			if (parrain.role.equals(RolesLg.PARRAIN)) {
				PARRAIN parrainR = (PARRAIN) parrain.roleIn;
				if (parrainR.powerUsed) {
					player.sendMessage("Vous devez attendre pour pouvoir mettre un prime sur un joueur");
					
				} else {
					parrainR.setNexTarget(target);
				}
				
				return true;
			}
		} else if (args[0].equals("help")) {
			sender.sendMessage(ChatColor.GOLD+"||PRESENTATION||");
			sender.sendMessage(ChatColor.AQUA+"Ceci est un plugin de loup garou uhc, il faut entre 15 et 30 joueurs, l'host peut créer un partie, y ajouter des joueurs, configurer la probabilité des events et la composition est rôles."
					+ "Au début de la partie, les joueurs vont miner pour se créer un équipement. "
					+ "A 20 minutes, tous les joueurs reçoivent un rôle appartenant soit au camp des villageois, soit au camp des loup garou soit à leur propre camp, et devant gagner en solo."
					+ "Les loup garou doivent s'infiltrer et trahir le village, et les villageois doivent trouver les loups et s'en débarasser."
					+  ChatColor.DARK_RED
					+"\n"+"\n"+ "EVENEMENTS"+ "\n"
					+  ChatColor.AQUA+"Des évènement peuvent arriver aléatoirement dans la partie, dont l'ont peut configurer la probabilité grâce au menu de configuration."
					+"\n"+ "\n"+ ChatColor.DARK_RED+"COUPLE"+ "\n"
					+ ChatColor.AQUA+"Le cupidon peut mettre 2 personnes en couple, ces deux personnes doivent gagner ensemble quoi qu'il arrive, et éliminer tout les autres membres de la partie."
					+"\n"+ "\n"+ ChatColor.DARK_RED+"VOTE"+ "\n"
					+ ChatColor.AQUA+"A chaque épisode, donc toute les 20min, chaque joueur pourra voter pour une personne qu'il a croisé durant la partie, le joueur le plus voté, s'il est voté plus de 2 fois, subira l'effet poison et prendra quelques dégats."
					+"\n"+ "\n"+ ChatColor.DARK_RED+"JOUR ET NUIT" + "\n"
					+ ChatColor.AQUA+"Chaque épisode est constitué de 10min de jour puis 10min de nuit, le moment de la journée influe certains rôles, par exemple les loup-garou possèdent force I de nuit");
			sender.sendMessage("\n"+ChatColor.GOLD+"||COMMANDE||"+ "\n"+"\n" + ChatColor.AQUA
					+"\n"+"\n"+ "/lg help --> Vous voyez bien où cela vous a conduit"+ "\n"
					+ "\n"+"\n"+"/lga Game create [nomDeLaGame] ---> crée une game et ouvre son menu de configuration"+ "\n"
					+ "\n"+"\n"+"/lga Game config [nomDeLaGame] ---> "+ChatColor.RED+"(commande op)"+ ChatColor.AQUA+ "ouvre le menu de configuration de la game [nomDeLaGame]"+ "\n"
					+ "\n"+"\n"+"/lga Game start [nomDeLaGame] ---> "+ChatColor.RED+"(commande op) "+ ChatColor.AQUA+ "lance la partie"+ "\n"
					+ "\n"+"\n"+"/lga say [message] ---> "+ChatColor.RED+"(commande op)"+ ChatColor.AQUA+" annonce un message à tout le monde"+ "\n"
					+ "\n"+"\n"+"/lg role ---> affiche le role du joueur, et d'autre infos supplémentaires comme la liste des loups s'il est loup"+ "\n"
					+ "\n"+"\n"+"/lg list ---> affiche les joueurs de la partie"+ "\n");
		}
		
		
		
		
		
		
		
		
	
	
	return false;
}
}




	
