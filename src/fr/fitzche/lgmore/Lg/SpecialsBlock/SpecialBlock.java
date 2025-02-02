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
				ArrayList<PlayerData> ps = (ArrayList<PlayerData>) LocationUtil.getClassByDistance(this.loc).subList(0, Main.game.groupe-1);
				
			}
		} 
		
	}
    
}
