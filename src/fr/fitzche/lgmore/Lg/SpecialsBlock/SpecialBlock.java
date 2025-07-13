package fr.fitzche.lgmore.Lg.SpecialsBlock;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionType;
import org.bukkit.scheduler.BukkitRunnable;

import fr.fitzche.lgmore.Camp;
import fr.fitzche.lgmore.Main;
import fr.fitzche.lgmore.PlayerData;
import fr.fitzche.lgmore.Lg.GameLg;
import fr.fitzche.lgmore.RolesLg.RolesLg;
import fr.fitzche.lgmore.RolesLg.SORCIER;
import fr.fitzche.lgmore.Util.GameLgUtil;
import fr.fitzche.lgmore.Util.ItemUtil;
import fr.fitzche.lgmore.Util.LocationUtil;
import fr.fitzche.lgmore.Util.MathUtil;
import fr.fitzche.lgmore.Util.PlayerUtil;
import fr.fitzche.lgmore.scoreboard.Inventory.sorcierInv;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class SpecialBlock implements Listener, Serializable{

	
	public Location loc;
	public SpecialBlockType type;
	public String id;
	public SpecialBlockData data;
	public GameLg game;
	
	public SpecialBlockData getData() {
		return data;
	}
	public SpecialBlock(Location loc, SpecialBlockType type, SpecialBlockData data, GameLg game, String checkLoc) {
		this.loc = new Location(loc.getWorld(), loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
		this.data = data;
		this.type = type;
		Main.server.getPluginManager().registerEvents(this, Main.plug);
		this.id = Integer.toString(loc.getBlockX()) +"-"+ Integer.toString(loc.getBlockY()) +"-"+ Integer.toString(loc.getBlockZ());
	}
	
	
	
	
	
	@EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
		if (!event.getBlock().hasMetadata("specialBlock-lgFitzche")) {
			return;
		}
        // Vérifie si le bloc est du bedrock
        if (event.getBlock().getLocation().getBlockX()==this.loc.getBlockX()&&event.getBlock().getLocation().getBlockY()==this.loc.getBlockY()&&event.getBlock().getLocation().getBlockZ()==this.loc.getBlockZ()) {
            event.setCancelled(true); // Annule la destruction du bloc
            event.getPlayer().sendMessage("Ne faites pas ça voyons !!!");
            if (MathUtil.pourcentage(10)) {
            		event.getPlayer().damage(1);
            
            }
        }
    }
	
	@Deprecated
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e) {
		
		if (e.getClickedBlock() ==null || !e.getClickedBlock().hasMetadata("specialBlock-lgFitzche") || data == null) {
			
			return;
		}
		PlayerData toCheck = Main.getData(e.getPlayer());
		if (toCheck.game == null || !(toCheck.game instanceof GameLg)) {
			return;
		}
		
		
		
		if (e.getClickedBlock().getLocation().equals(this.loc)) {
			
			
			e.setCancelled(true);
			
			if (this.type.equals(SpecialBlockType.Vote)) {
				
				
				if (((GameLg)Main.getData(e.getPlayer()).game).invVote == null) {
					return;
				}
				e.getPlayer().openInventory(((GameLg)Main.getData(e.getPlayer()).game).invVote);
				Main.getData(e.getPlayer()).lastVoteOpen = this;
			}
			if (this.type.equals(SpecialBlockType.Accuse)) {
				if (true) {
					e.getPlayer().sendMessage("désactivé");
					return;
				}
				
				e.getPlayer().sendMessage(ChatColor.RED+"Vous avez 30sec pour accuser un joueur avec la commande /lg accuse [nom du joueur], votre accusation sera rendu publique au prochain épisode.");
				PlayerData p = Main.getData(e.getPlayer());
				p.canAccuse = true;
			
				Bukkit.getScheduler().runTaskLater(Main.plug, new BukkitRunnable() {

					@Override
					public void run() {
						p.canAccuse = false;
					
					}
				
				}, 600);
			}
			
			if (this.type.equals(SpecialBlockType.Treasure)) {
				TreasureBlockData data = (TreasureBlockData) this.data;
				if (data.type == null) {
					return;
				}
				System.out.println("treasure");
				if (data.used) {
					e.getPlayer().sendMessage("Déjà utilisé");
					return;
				}
				if (data.type.equals(TreasureBlockType.RegisterModifier)) {
					
					System.out.println("register modifier act in special b");
					if (true) {
						return;
					}
					
					if (game.groupe < 3) {
						e.getPlayer().sendMessage("groupes insuffisants");
					}
					List<PlayerData> psList =null;
					try {
						psList = LocationUtil.getClassByDistance(this.loc, "ok", game).subList(0, game.groupe-1);

					} catch (Exception e2) {
						e.getPlayer().sendMessage("error, essayez avec plus de monde dans les environs");
						System.out.println("erreur register modif: ");
						e2.printStackTrace();
						return;
					}

					ArrayList<PlayerData> ps = new ArrayList<PlayerData>(); 
					if (LocationUtil.getClassByDistance(this.loc, "ok", game).size() == 1) {
						ps = new ArrayList<PlayerData>(Arrays.asList(LocationUtil.getClassByDistance(this.loc, "ok" , game).get(0)));
					}
					
					
					for (PlayerData toAdd:psList) {
						ps.add(toAdd);
					}
					boolean ann = false;
					for (PlayerData p2:ps) {
						System.out.println("register modifier seek ply in special b");
						if (p2.getLocation().distance(loc) > 5) {
							e.getPlayer().sendMessage(ChatColor.DARK_PURPLE+"Il faut plus de joueurs à moins de 5 blocs de ce camp pour l'activer,");
							return;
						}
					}
					for (PlayerData p:ps) {
						for (PlayerData p2: ps) {
							if (!p2.getName().equals(p.getName()) && p.timeWithPlayers.getOrDefault(p2.getName(), 0) < 500) {
								e.getPlayer().sendMessage(ChatColor.DARK_PURPLE+"Le joueur "+ p.getName() + " et "+p2.getName()+ " n'ont pas passé assez de temps cote à cote pour actionner ce camp.");
								ann = true;
							}
						}
					}
					if (ann) {
						return;
					} else {
						for (String str:data.playersClickedOne) {
							if (e.getPlayer().getName().equals(str) && !data.used) {
								game.groupInfluenceRegistre(ps, loc);
								data.used = true;
								return;
							}
						}
						e.getPlayer().sendMessage("Votre groupe a trouvé un camp, chacun sa manière de jouer la scène !! En tout cas chaque joueur reçoit un message, et doit choisir un registre (30s), le registre le + choisi gagnera 20%, cependant, les loups et surtout les solos ont une influence énorme sur ce choix. Réflechissez bien avant d'activer ce camp en clicquant une deuxième fois dessus car si un traitre se trouve parmis vous, il pourra facilement vous utiliser pour avantager le registre de son choix.");
						data.playersClickedOne.add(e.getPlayer().getName());
						
					}
				} else if (data.type.equals(TreasureBlockType.AuraAnalyser)) {
					System.out.println("auraAnalyser found (at special block interact event)");
					List<PlayerData> psList = LocationUtil.getClassByDistance(this.loc, "ok",game).subList(0,game.groupe-1);
					ArrayList<PlayerData> ps = new ArrayList<PlayerData>(); 
					if (LocationUtil.getClassByDistance(this.loc, "ok" , game).size() == 1) {
						ps = new ArrayList<PlayerData>(Arrays.asList(LocationUtil.getClassByDistance(this.loc, "ok", game).get(0)));
					}
					for (PlayerData p2:ps) {
						if (p2.getLocation().distance(loc) > 5) {
							e.getPlayer().sendMessage(ChatColor.DARK_PURPLE+"Il faut plus de joueurs à moins de 5 blocs de ce camp pour l'activer,");
							return;
						}
					}
					
					game.groupAuraEstimation(ps);
					data.used = true;
				} else if (data.type.equals(TreasureBlockType.Bienfaisance)) {
					System.out.println("bienfaisance found (at special block interact event)");
					e.getPlayer().sendMessage(ChatColor.DARK_PURPLE+ "Vous avez trouvé un coeur à conférer à un joueur avec la commande /lg conferer [nomDuJoueur], maintenant, à vous de temporairement prendre le role du bienfaiteur au bienfaiteur !!");
					Main.getData(e.getPlayer()).bienfaisance ++;
					data.used = true;
				} else if (data.type.equals(TreasureBlockType.AuraPotion)) {
					System.out.println("auraPotion found (at special block interact event)");
					Potion potion = new Potion(PotionType.WATER_BREATHING, 1, true);
					ItemStack item = potion.toItemStack(1);
					ArrayList<String> strs = new ArrayList<String>();
					strs.add("Révéleur d'Aura");
					ItemUtil.setLore(item, strs);
					Player p = e.getPlayer();
					p.getInventory().addItem(item);
					data.used = true;
				} else if (data.type.equals(TreasureBlockType.ParalysiePotion)) {
					System.out.println("Paralysie found (at special block interact event)");
					Potion potion = new Potion(PotionType.WATER_BREATHING, 1, true);
					ItemStack item = potion.toItemStack(1);
					ArrayList<String> strs = new ArrayList<String>();
					strs.add("Potion De Paralysie");
					ItemUtil.setLore(item, strs);
					Player p = e.getPlayer();
					p.getInventory().addItem(item);
					data.used = true;
				} else if (data.type.equals(TreasureBlockType.TeleporterPotion)) {
					
					System.out.println("tpPotion found (at special block interact event)");
					
					Potion potion = new Potion(PotionType.WATER_BREATHING, 1, true);
					ItemStack item = potion.toItemStack(1);
					ArrayList<String> strs = new ArrayList<String>();
					strs.add("Potion de Téléportation ");
					ItemUtil.setLore(item, strs);
					Player p = e.getPlayer();
					p.getInventory().addItem(item);
					data.used = true;
				}

				
			} 
			if (this.type.equals(SpecialBlockType.Cauldron)) {
				Player p = e.getPlayer();
				PlayerData plyD = Main.getData(p);
				System.out.println("cauldron block");
				
				if (!plyD.role.equals(RolesLg.SORCIER)) {
					p.sendMessage("Vous ne pouvez pas utiliser ce bloc");
					return;
				} 
				sorcierInv inv = new sorcierInv(p, game);
				
			}
			
			if (this.type.equals(SpecialBlockType.Stone)) {
				StoneBlockData data = (StoneBlockData) this.data;
				if (data.taken) {
					e.getPlayer().sendMessage("Déjà Pris");
					
				} else {
					data.taken = true;
					PlayerData taker = Main.getData(e.getPlayer());
					data.stone.owner = taker;
					taker.addStone(data.stone.type);
					if (data.than.playerWithRole.isOnline) {
						data.than.playerWithRole.sendMessage(ChatColor.LIGHT_PURPLE+ "Le joueur "+ taker.getName()+ " a récupéré la pierre de "+ data.stone.type.getName());
					}
				}
			}
		} 
		
	}
    
}
