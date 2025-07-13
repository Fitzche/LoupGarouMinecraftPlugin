package fr.fitzche.lgmore.commands;

import java.util.ArrayList;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import com.sk89q.worldedit.WorldEdit;

import fr.fitzche.lgmore.Camp;
import fr.fitzche.lgmore.GameStatut;
import fr.fitzche.lgmore.Main;
import fr.fitzche.lgmore.PlayerData;
import fr.fitzche.lgmore.RoleInstance;
import fr.fitzche.lgmore.Lg.GameLg;
import fr.fitzche.lgmore.Lg.GameNote;
import fr.fitzche.lgmore.Lg.RegisterType;
import fr.fitzche.lgmore.Lg.SpecialsBlock.SpecialBlockType;
import fr.fitzche.lgmore.Lg.SpecialsBlock.VoteBlockData;
import fr.fitzche.lgmore.RolesLg.ANGE;
import fr.fitzche.lgmore.RolesLg.Aura;
import fr.fitzche.lgmore.RolesLg.BIENFAITEUR;
import fr.fitzche.lgmore.RolesLg.CHASSEUR;
import fr.fitzche.lgmore.RolesLg.CUPIDON;
import fr.fitzche.lgmore.RolesLg.DEMON;
import fr.fitzche.lgmore.RolesLg.DISCIPLE;
import fr.fitzche.lgmore.RolesLg.ENFANT_SAUVAGE;
import fr.fitzche.lgmore.RolesLg.INFECT_PERE_DES_LOUPS;
import fr.fitzche.lgmore.RolesLg.INTERPRETE;
import fr.fitzche.lgmore.RolesLg.LOUP_ALCHIMISTE;
import fr.fitzche.lgmore.RolesLg.LOUP_BRUMEUX;
import fr.fitzche.lgmore.RolesLg.LOUP_GRIMEUR;
import fr.fitzche.lgmore.RolesLg.LOUP_MANIPULATEUR;
import fr.fitzche.lgmore.RolesLg.PARRAIN;
import fr.fitzche.lgmore.RolesLg.PYROMANE;
import fr.fitzche.lgmore.RolesLg.RENARD;
import fr.fitzche.lgmore.RolesLg.RolesLg;
import fr.fitzche.lgmore.RolesLg.SALVATEUR;
import fr.fitzche.lgmore.RolesLg.SORCIERE;
import fr.fitzche.lgmore.RolesLg.THANOS;
import fr.fitzche.lgmore.RolesLg.THIERCE_ANGE;
import fr.fitzche.lgmore.RolesLg.TRAQUEUR;
import fr.fitzche.lgmore.RolesLg.VOYANTE;
import fr.fitzche.lgmore.RolesLg.Infections.Virus;
import fr.fitzche.lgmore.RolesLg.Infections.VirusType;
import fr.fitzche.lgmore.Util.GameLgUtil;
import fr.fitzche.lgmore.Util.ItemUtil;
import fr.fitzche.lgmore.Util.LocationUtil;
import fr.fitzche.lgmore.Util.MathUtil;
import fr.fitzche.lgmore.Util.PlayerUtil;
import fr.fitzche.lgmore.Util.PotionUtil;
import fr.fitzche.lgmore.Util.RoleUtil;
import fr.fitzche.lgmore.minecraft.ResCheck;
import fr.fitzche.lgmore.scoreboard.Inventory.PlayerGameListInv;
import fr.fitzche.lgmore.scoreboard.Inventory.playersDisplay;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class Lg implements CommandExecutor {

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
		PlayerData senderData = Main.strToPlayer.getOrDefault(sender.getName(), null);
		GameLg game = null;
		if (senderData.game != null && senderData.game instanceof GameLg)  {
			game = (GameLg) senderData.game;
		}
		
		
		if (args.length == 0) {
			for (Entry<String, GameLg> entry: Main.strToGame.entrySet()) {
				GameLg gameJ = entry.getValue();
				TextComponent text = new TextComponent();
				text.setText("Clicquez ici pour rejoindre "+ ChatColor.GOLD + gameJ.name);
				text.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, ("/lg join ")+ gameJ.name));
				((Player) sender).spigot().sendMessage(text);
			}
			return true;
		}
		if (senderData != null && game != null) {
			for (RoleInstance role:game.rolesIn) {
				System.out.println("command tested");
				role.command(sender, cmd, msg, args);
			}
		} 
		
		if (args[0].equals("xp")) {
			senderData.sendMessage("Vous avez "+senderData.xp+ "xp");
			return true;
		}
		if (args[0].equals("revive")) {
			if (game == null || senderData == null) {
				return true;
			}
			PlayerData plyI = Main.strToPlayer.getOrDefault(args[2], null);
			if (plyI != null && plyI.role.equals(RolesLg.INFECT_PERE_DES_LOUPS)){
				Player ply = PlayerUtil.getPlayer(args[1]);
				
				PlayerData plyD = game.getPlayer(ply.getName());
				
				INFECT_PERE_DES_LOUPS role = (INFECT_PERE_DES_LOUPS) plyI.roleIn;

				if (role.powerUsed) {
					sender.sendMessage(ChatColor.RED +"" +ChatColor.ITALIC +"Vous ne pouvez pas infecter à nouveau");
					return true;
				}
				if (plyD.inLife==false) {
					sender.sendMessage(ChatColor.BLUE + "Ce joueur est mort, il est trop tard pour l'infecter");
					return true;
				} 

				plyD.relive = true;
				plyD.infected = true;
				plyD.camp = Camp.Wolf;
				plyD.considWolf = true;
				if (!plyD.inLove) {
					
					
					plyD.camp = Camp.Wolf;
				}
				
				
				
				role.powerUsed = true;
				for (PlayerData loup:game.getFalseWolfAlive()) {
					loup.sendMessage(ChatColor.GOLD+"Le joueur "+ ply.getName() + " a rejoint votre camp");
				}
				plyI.sendMessage("Vous avez infecté "+ ply.getName());
				return true;
				
				
			} else if (game.getPlayer(sender.getName()).role.equals(RolesLg.SORCIERE)) {
				
			
				PlayerData plyD =  Main.strToPlayer.getOrDefault(args[1], null);
				
				if (plyD == null || plyD.inLife==false) {
					sender.sendMessage(ChatColor.BLUE + "Ce joueur est mort, il est trop tard pour le ressuciter");
					return true;
				} 
				plyD.relive = true;
				PlayerData soso = Main.strToPlayer.getOrDefault(args[2], null);
				if (soso == null || soso.roleIn == null) {
					sender.sendMessage("erreur quand au sender >> commande revive");
					return false;
				}

				SORCIERE role = (SORCIERE) soso.roleIn;
				role.powerUsed = true;
				Player s = (Player) sender;
				s.sendMessage("Vous avez ressucité "+ plyD.Name);
				plyD.sendMessage("La sorcière vous a réssucité");
				
				return true;
			} else {
				Player player = (Player) sender;
				player.sendMessage("vous n'etes pas sorcière ou infect père des loups");
				return true;
			}
			
			
			
			
		}  else if (args[0].equals("list")) {
			
			
			
			//System.out.println("recherche de " + args[2]);
			
			
			if (game == null || senderData == null) {
				return true;
			}
			
			
			playersDisplay display = new playersDisplay(game.players, null);
			Player p = (Player) sender;
			display.display(p, null);
			Main.server.getPluginManager().registerEvents(display, Main.plug);
			
		} else if (args[0].equals("couple")) {
			
			if (game == null || senderData == null) {
				return true;
			}
			
			if (!senderData.getLgRole().equals(RolesLg.CUPIDON)){
				sender.sendMessage("Vous n'etes pas cupidon !!");
			}
			if (game.aleaCouple) {
				sender.sendMessage("Le couple est aléatoire");
			}
			
			playersDisplay choose = new playersDisplay(game.getPlayerAlive(), "/couple ");
			Main.server.getPluginManager().registerEvents(choose, Main.plug);

			choose.display((Player) sender, args);
			
			
			
		} else if (args[0].equals("don")) {
			
			if (game == null || senderData == null) {
				return true;
			}
			if ( !(sender instanceof Player)) {
				return false;
			}
			Player giver = (Player) sender;
			PlayerData player = Main.strToPlayer.getOrDefault(sender.getName(), null);
			if (player == null) {
				return true;
			}
			
			
			if (!player.inLove) {
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
			double value = Integer.valueOf(args[1]);
			PlayerUtil.don(player, player.coupleL, value);
			
			
			System.out.println(args[1]);
			
		} else if (args[0].equals("voteCmd")){
			if (game == null || senderData == null) {
				return true;
			}
			if (args.length < 3) {
				return true;
			}
			
			
			ItemStack item = game.invVote.getItem(Integer.valueOf(args[2]));
			if (item.hasItemMeta() && item.getItemMeta().hasLore() && item.getItemMeta().getLore().contains("utilisé")) {
				sender.sendMessage(ChatColor.GOLD+"Cette enveloppe à vote est déjà utilisée");
				return true;
			}
			if (!game.isInVote) {
				sender.sendMessage(ChatColor.GOLD +"Ce n'est pas l'heure du vote");
				return true;
			} 
			
			
			PlayerData voted = Main.strToPlayer.getOrDefault(args[1], null);
			if (voted == null) {
				sender.sendMessage("erreur sur le joueur voté >> voteCmd");
			}
			if (game.cannotBeVoted.contains(sender)) {
				sender.sendMessage(ChatColor.RED+"Vous ne pouvez pas voter");
				return true;
			}
			
			if (senderData.timeWithPlayers.getOrDefault(voted.getName(), 0) < 1) {
				sender.sendMessage(ChatColor.GOLD +"Vous n'avez pas croisé ce joueur, vous ne pouvez donc pas voter pour celui-ci");
				return true;

			}
			if (senderData.lastVoteOpen != null && senderData.lastVoteOpen.data.getType().equals(SpecialBlockType.Vote)) {
				VoteBlockData data = (VoteBlockData) senderData.lastVoteOpen.data;
				if (data.nbOfVote < 1) {
					sender.sendMessage(ChatColor.GOLD +"L'urne à vote que vous avez ouverte est pleine...");
					return true;
				}
				data.hasVotedFor.put(sender.getName(), voted.getName());
				data.nbOfVote --;
			}
			
			if (senderData.voted != null) {
				senderData.voted.vote --;
			}
			
			senderData.voted = voted;
			sender.sendMessage("Vous avez voté pour "+ voted.Name +"");
			voted.vote ++;
			
			
			ArrayList<String> str = new ArrayList<String>();
			str.add("utilisé");
			ItemUtil.setLore(game.invVote.getItem(Integer.valueOf(args[2])), str);
		}  else if (args[0].equals("conferer")) {
			if (game == null || senderData == null) {
				return true;
			}
			Player bienfaiteurP = (Player) sender;
			PlayerData bienfaiteur = senderData;
			PlayerData target = Main.strToPlayer.getOrDefault(args[1],null);
			
			if (target == null && target.inLife && !target.Name.equals(bienfaiteur.Name)) {
				bienfaiteur.sendMessage("Veuillez spécifier un nom valide dans votre commande");
				return true;
			} else if (bienfaiteur.role != RolesLg.BIENFAITEUR && bienfaiteur.bienfaisance < 1){
				
				bienfaiteur.sendMessage("Vous n'etes pas bienfaiteur");
				return true;

			} else if (bienfaiteur.bienfaisance > 0) {
				bienfaiteur.sendMessage(ChatColor.DARK_PURPLE+ "Vous conférer un coeur au joueur "+ target.getName());
				bienfaiteur.bienfaisance--;
				target.changeHealth(2);
				target.sendMessage(ChatColor.DARK_PURPLE+"Un joueur se prenant pour un bienfaiteur vous accorde un coeur supplémentaire");
			} else {
				BIENFAITEUR bft = (BIENFAITEUR) bienfaiteur.roleIn;
				bft.conferer(target);
				return true;
			}
		}  else if (args[0].equals("role")) {
			if (game == null || senderData == null) {
				return true;
			}
			System.out.println("command role");
			Player player = (Player) sender; 
			PlayerData ply = senderData;
			
			
			ply.sendMessage(ChatColor.GOLD +ply.roleIn.getDescription());
			System.out.println("sended");
			if (ply.roleIn.getDescription() == null) {
				System.out.println("command role --> str null");
			}
			if (ply.roleIn == null) {
				System.out.println("command role--> role nul");
			}
			if (ply.inLove && ply.coupleL != null) {
				ply.sendMessage(ChatColor.LIGHT_PURPLE+ "En couple avec: "+ ply.coupleL.getName());
			}
			
			if (ply.infected) {
				ply.sendMessage(ChatColor.RED+"Infecté");
			}
			
			if (ply.considWolf) {
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
		}else if (args[0].equals("help")) {
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
					+ ChatColor.AQUA+"A chaque épisode, donc toute les 20min, chaque joueur pourra voter pour une personne qu'il a croisé durant la partie en clicquant sur un enderchest dans une maison de vote, le nombre de vote par maison de vote est limité à 5, et le nombre de vote total est limité à 2/3 du nombre de joueurs dans la partie. Le joueur le plus voté, s'il est voté plus de 2 fois, sera exposed et perdra de la vie de manière permanente, la force du vote augmente au fur et à mesure du temps"
							+ "\n"+ ChatColor.DARK_RED +"AURA: "
							+ "\n"+ ChatColor.AQUA+"Chaque joueur a une aura, donnée par son role, qui peut changer au cours de la partie: "
									+ "\n"+ "UNKNOW: possédé par très peu de roles comme l'ermite ou le loup craintif"
									+ "\n"+ "OBSCUR: aura commune pour les rôles loups"
									+ "\n"+ "LUMINEUSE: aura commune pour les rôles villageois"
									+ "\n"+ "NEUTRE: aura pour les rolesles plus discret et pour certains solos"
									+ "\n"+ "DANGEROUS: aura des rôles les plus mauvais comme l'infect père des loup. Agit comme une aura obscur mais de manière augmentée"
					+  ChatColor.DARK_RED
					+"\n"+"\n"+ "REGISTRES"+ "\n"
					+ ChatColor.AQUA+"!!! Le Spectacle commence, une pièce où s'affrontent les terribles loup-garou et la justice du village. Et pour une fois le développeur... heu non le dramaturge, a décidé de laisser le choix du registre aux joueurs...enfin aux personnages, pardon. Les actions des différents joueurs avec ou sans leur role influenceront donc le registre de la partie dans 3 directions possibles: Epique, tragique et Oratoire, qui influenceront la partie, et c'est aux joueurs de choisir mais aussi d'en assumer les conséquences, bonnes ou mauvaises, sans savoir pour autant ce que font chaque registres."
					+ ""
					+ ""
					+ ""
					+ "\n"+ "\n"+ ChatColor.DARK_RED+"JOUR ET NUIT" + "\n"
					+ ChatColor.AQUA+"Chaque épisode est constitué de 10min de jour puis 10min de nuit, le moment de la journée influe certains rôles, par exemple les loup-garou possèdent force I de nuit");
			sender.sendMessage("\n"+ChatColor.GOLD+"||COMMANDE||"+ "\n"+"\n" + ChatColor.AQUA
					+"\n"+"\n"+ "/lg help --> Vous voyez bien où cela vous a conduit"+ "\n"
					+ "\n"+"\n"+"/lga Game create [nomDeLaGame] ---> "+ChatColor.RED+"(commande op)+"+ ChatColor.AQUA+"crée une game et ouvre son menu de configuration"+ "\n"
					+ "\n"+"\n"+"/lga Game config [nomDeLaGame] ---> "+ChatColor.RED+"(commande op)"+ ChatColor.AQUA+ "ouvre le menu de configuration de la game [nomDeLaGame], permet de start, ajouter/retirer des joueurs et changer la compo et les évents"+ "\n"
					+ "\n"+"\n"+"/lga Game start [nomDeLaGame] ---> "+ChatColor.RED+"(commande op) "+ ChatColor.AQUA+ "lance la partie"+ "\n"
					+ "\n"+"\n"+"/lga say [message] ---> "+ChatColor.RED+"(commande op)"+ ChatColor.AQUA+" annonce un message à tout le monde"+ "\n"
					+ "\n"+"\n"+"/lga transfer [joueur 1] [joueur 2] ---> "+ChatColor.RED+"(commande op)"+ ChatColor.AQUA+" tranfère toute les info (effet, inv, role) d'un joueur vers un autre, "+ ChatColor.DARK_RED+"commande peu fiable, n'utiliser qu'en cas de vrai neccesité (risque de casser la partie en cours)"+ChatColor.AQUA+ "\n"
					+ "\n" + "/lg vote [nomDuJoueur] ---> permet de voter contre un joueur pendant la phase des votes"
					+ "\n" + "/lg accuse [nomDuJoueur] ---> permet d'accuser un joueur, l'executeur de la commande aura alors 5min pour tuer l'accusé sous peine de perdre de la vie "
					+ "\n" + "/lg escape  ---> permet d'échapper à la justice du village, cependant au su et vu de tous, le joueur ne pourra alors ni voter ni etre voté et sa position sera révélée dans le chat. Devient effectif au prochain épisode"
					+ "\n" + "/lg checkEnd ---> permet de vérifier si la partie est finie, mais coute 5% de force. Par défaut la vérification est effectuée à chaque mort/infection, mais un évènement permet de baisser le pourcentage des vérification."
					+ "\n"+"\n"+"/lg role ---> affiche le role du joueur, et d'autre infos supplémentaires comme la liste des loups s'il est loup"+ "\n"
					+ "\n"+"\n"+"/lg list ---> affiche les joueurs de la partie"+ "\n"
							+ "/color ---> permet de colorer des pseudo "+ ChatColor.RED + "(plugin externe)"+ ChatColor.AQUA + "."+ "\n"
							+ "/lg info ---> documentation roles, clicquable");
		} else if (args[0].equals("info")) {
				if (args.length == 1) {
					
					
					for (RolesLg role:RoleUtil.existingRoles) {
						TextComponent text = new TextComponent();
						text.setText("Clicquez ici pour avoir des infos sur: " + role.getCampOfRole().getColor()+ role.getName());
						text.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/lg inforole "+ role.getName()));
						((Player) sender).spigot().sendMessage(text);
					}
					
					return true;
				}
				
				
				
				Player player = (Player) sender;
				 
				if (game == null || senderData == null) {
					return true;
				}
				game.config.open(player, false);
				
				
				
				
				
			} else if (args[0].equals("inforole")) {
				String name = args[1];
				int x = 2;
				while (x != args.length) {
					x++;
					name = name + " " + args[x-1];
					
				}
				System.out.println("command runned");
				for (RolesLg role: RoleUtil.existingRoles) {
					if (name.equals(role.getName()) ) {
						sender.sendMessage(role.getCampOfRole().getColor()+ role.getName() + ChatColor.GOLD + role.getDescription() );
						System.out.println(name + " equals "+ role.getName());

						break;
					} else {
						System.out.println(name + " doesn't equal "+ role.getName());
					}
				}
			} else if (args[0].equals("whisper")) {
				ArrayList<String> args2 = new ArrayList<String>();
				for (String str: args) {
					args2.add(str);
				}
				args2.remove(0);
				
				String mess = String.join(" ", args2);
				for (Player p: Main.plug.getServer().getOnlinePlayers()) {
					if (p.isOp()) {
						p.sendMessage(ChatColor.RED + sender.getName() + ChatColor.ITALIC + " >> "+ mess);
					}
				}
			} else if (args[0].equals("escape")) {
				if (game == null || senderData == null) {
					return true;
				}
				senderData.toEscape = true;
			} else if (args[0].equals("accuse")) {
				if (game == null || senderData == null) {
					return true;
				}
				PlayerData accuser = senderData;
				PlayerData accused = Main.strToPlayer.getOrDefault(args[1], null);
				
				if (accused == null || accuser == null) {
					return true;
				}
				
				if (!accuser.canAccuse) {
					accuser.sendMessage(ChatColor.RED+"Vous ne pouvez pas accuser un joueur sans passer par la borne de justice représenté pas un jukebox");
					return true;
				}
				
				if (accuser.timeWithPlayers.getOrDefault(accused.getName(), 0) < 30) {
					sender.sendMessage(ChatColor.GOLD+ "Vous devez passer au moins 5 min à moins de 20 blocs du joueur visé");
					sender.sendMessage(Integer.toString(accuser.timeWithPlayers.getOrDefault(accused.getName(),0)));
					return true;
				}
				
				
				if (accuser.getLocation().distance(accused.getLocation()) > 50) {
					sender.sendMessage(ChatColor.GOLD+ "Vous devez être à maximum 50 blocs du joueur visé");
					return true;
				}
				if (game.cannotBeVoted.contains(accused)) {
					sender.sendMessage(ChatColor.GOLD+ "Le joueur visé a fuit la justice du village");
					return true;
				}
				
				
				
				game.futureAcc.put(accuser.getName(), accused);
				accuser.sendMessage(ChatColor.RED+"Votre accusation aura lieu au prochain épisode");
				
			} else if (args[0].equals("epicchooseevent")) {
				
				sender.sendMessage("Vous avez choisi "+ChatColor.DARK_PURPLE+"Epique");
				senderData.favRegister = RegisterType.Epic;
			}else if (args[0].equals("tragicchooseevent")) {
				sender.sendMessage("Vous avez choisi "+ChatColor.DARK_PURPLE+"Tragique");
				senderData.favRegister = RegisterType.Tragic;
			}else if (args[0].equals("oratchooseevent")) {
				sender.sendMessage("Vous avez choisi "+ChatColor.DARK_PURPLE+"Oratoire");
				senderData.favRegister = RegisterType.Oratoire;
			} else if (args[0].equals("reality")) {
				PlayerData p = senderData;
				if (p.hasReality && !p.hasRealityUsed) {
					p.hasRealityUsed = true;
					int dist = MathUtil.generateAlInt(15, 30);
					for (PlayerData ply:game.getPlayerAlive()) {
						if (ply.getLocation().distance(p.getLocation()) < dist && !ply.getName().equals(p.getName()) && ply.isOnline) {
							ply.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 3600, 2));
							ply.sendMessage("Vous êtes affecté par la pierre de réalité");
							ply.player.playSound(p.getLocation(), Sound.ARROW_HIT, 1, 1);
						}
					}
				}
			} else if (args[0].equals("esprit")) {
				PlayerData p = senderData;
				if (p.hasMind && !p.hasMindUsed) {
					
					if (args.length < 2) {
						sender.sendMessage(ChatColor.RED+"Commande Invalide, il manque un argument");
						return true;
					}
					PlayerData target = Main.strToPlayer.getOrDefault(args[1], null);
					if (target == null) {
						sender.sendMessage(ChatColor.RED+"Commande invalide, le nom du joueur est incorrect");
						return true;
					}
					if (!target.isOnline) {
						sender.sendMessage("Ce joueur est hors ligne");
						return true;
					}
					p.hasMindUsed = true;
					target.aura = Aura.OBSCUR;
					target.camp = Camp.Wolf;
					sender.sendMessage("Vous affectez le joueur "+target.getName()+ " avec la pierre de l'esprit");
				}
			} else if (args[0].equals("time")) {
				PlayerData p = senderData;
				if (p.hasTime && !p.hasTimeUsed) {
					p.hasTimeUsed = true;
					for (int i = 0; i < 120; i++) {
						game.everySec();
					}
					for (PlayerData ply:game.getPlayerAlive()) {
						ply.sendMessage(ChatColor.DARK_GREEN+"Le temps a été avancé de 2min");
					}
					
				}
			}else if (args[0].equals("ame")) {
				PlayerData p = senderData;
				if (p.hasSoul && !p.hasSoulUsed) {
					
					if (args.length < 2) {
						sender.sendMessage(ChatColor.RED+"Commande Invalide, il manque un argument");
						return true;
					}
					PlayerData target = Main.strToPlayer.getOrDefault(args[1], null);
					if (target == null) {
						sender.sendMessage(ChatColor.RED+"Commande invalide, le nom du joueur est incorrect");
						return true;
					}
					if (!target.isOnline) {
						sender.sendMessage("Ce joueur est hors ligne");
						return true;
					}
					p.hasSoulUsed = true;
					target.auraDiscoverEffetDuration += 15;
					sender.sendMessage("Vous affectez le joueur "+target.getName()+ " avec la pierre de l'Ame");
				}
			}else if (args[0].equals("space")) {
				if (game == null || senderData == null) {
					return true;
				}
				PlayerData p = senderData;
				if (p.hasSpace && !p.hasSpaceUsed) {
					p.hasSpaceUsed = true;
					p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 3600, 2));
					
				}
			} else if (args[0].equals("join"))  {
				if (args.length < 2) {
					sender.sendMessage("pas assez d'arguments");
					return true;
				} else {
					if (Main.strToGame.getOrDefault(args[1], null) == null || Main.getData(sender).game != null) {
						sender.sendMessage("Cette game n'existe pas");
						return true;
					} else {
					
						sender.sendMessage("ajouté");
						Main.strToGame.get(args[1]).addPlayer(sender.getName());
					}
					
				}
			}else if (args[0].equals("stat")) {
				if (!(sender instanceof Player)) {
					return true;
				}
				String str;
				if (args.length < 2) {
					str = sender.getName();
				} else {
					str = args[1];
				}
				if (Main.strToPlayer.getOrDefault(str, null) != null) {
					
					Player p = (Player) sender;
					PlayerGameListInv list = new PlayerGameListInv(Main.strToPlayer.get(args[1]), null, p);
					
				} else {
					sender.sendMessage("Le joueur recherché n'est pas enregistré");
					
				}
			} else if (args[0].equals("checkEnd")) {
				if (game == null || senderData == null) {
					return true;
				}
				if (Main.strToPlayer.getOrDefault(sender.getName(), null) != null) {
					PlayerData player = Main.strToPlayer.get(sender.getName());
					if (game != null && player.isInLgGame && (game.statut.equals(GameStatut.BEFORE_ROLE) || game.statut.equals(GameStatut.IN_GAME))) {
						game.checkWin();
						player.sendMessage("Vérification des conditions de victoires, vous perdez 5% de force");
						player.boostR5 --;
					} else {
						player.sendMessage("Vous n'etes pas dans une partie, ou celle si est finie ou pas encore commencée");
					}
				} else {
					sender.sendMessage("Erreur sur votre identité, vous serez enregistré comme joueur en rejoignant une partie ou en executant la commande /lg register");
				}
			} else if (args[0].equals("register")) {
				if (Main.strToPlayer.getOrDefault(sender.getName(), null) != null) {
					sender.sendMessage("Vous êtes déjà enregistré comme joueur, faites /lg stat [votrePseudo] pour voir vos parties");
					return true;
				} else if (sender instanceof Player){
					Main.strToPlayer.put(sender.getName(), new PlayerData((Player) sender));
					sender.sendMessage("enregistré comme joueur, faites /lg stat pour voir vos partie, et /profil pour voir votre profil (fonctionnalité à venir");
				} else {
					sender.sendMessage("Vous n'êtes pas un joueur");
				}
			} else if (args[0].equals("wolfy")) {
				game.playSoundWolf(senderData);
			}
		
		
	
		
		
		
		
		
		
		
		
	
	
		return false;
	}

}




	
