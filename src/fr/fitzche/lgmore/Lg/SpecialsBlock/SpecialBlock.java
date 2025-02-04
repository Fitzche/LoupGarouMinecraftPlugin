package fr.fitzche.lgmore.Lg.SpecialsBlock;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;

import fr.fitzche.lgmore.Main;
import fr.fitzche.lgmore.PlayerData;
import fr.fitzche.lgmore.Util.GameLgUtil;
import fr.fitzche.lgmore.Util.LocationUtil;
import fr.fitzche.lgmore.Util.MathUtil;
import fr.fitzche.lgmore.Util.PlayerUtil;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class SpecialBlock implements Listener{

	
	public Location loc;
	public SpecialBlockType type;
	public String id;
	public SpecialBlockData data;
	
	
	public SpecialBlockData getData() {
		return data;
	}
	public SpecialBlock(Location loc, SpecialBlockType type, SpecialBlockData data) {
		this.loc = loc;
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
		if (!e.getClickedBlock().hasMetadata("specialBlock-lgFitzche")) {
			return;
		}
		System.out.println("interact onPlayerInteract");
		
		
		if (e.getClickedBlock().getLocation().equals(this.loc)) {
			e.setCancelled(true);
			if (this.type.equals(SpecialBlockType.Vote)) {
				
				
				System.out.println("interact true onPlayerInteract");
				e.getPlayer().openInventory(GameLgUtil.getGameOfPlayer(e.getPlayer(), "interactBlockVote").invVote);
				PlayerUtil.getDataOfPlayer(e.getPlayer(), " at player interact special block").lastVoteOpen = this;
			}
			if (this.type.equals(SpecialBlockType.Accuse)) {
				e.getPlayer().sendMessage(ChatColor.RED+"Vous avez 30sec pour accuser un joueur avec la commande /lg accuse [nom du joueur], votre accusation sera rendu publique au prochain épisode.");
				PlayerData p = PlayerUtil.getDataOfPlayer(e.getPlayer(), "on player interact bloc accuse");
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
				
				if (data.type.equals(TreasureBlockType.RegisterModifier)) {
					ArrayList<PlayerData> ps = (ArrayList<PlayerData>) LocationUtil.getClassByDistance(this.loc).subList(0, Main.game.groupe-1);
					boolean ann = false;
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
								Main.game.groupInfluenceRegistre(ps, loc);
								data.used = true;
							}
						}
						e.getPlayer().sendMessage("Votre groupe a trouvé un camp, chacun sa manière de jouer la scène !! En tout cas chaque joueur reçoit un message, et doit choisir un registre (30s), le registre le + choisi gagnera 20%, cependant, les loups et surtout les solos ont une influence énorme sur ce choix. Réflechissez bien avant d'activer ce camp en clicquant une deuxième fois dessus car si un traitre se trouve parmis vous, il pourra facilement vous utiliser pour avantager le registre de son choix.");
						
						
					}
				}

				
			}
		} 
		
	}
    
}
