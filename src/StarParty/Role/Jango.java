package StarParty.Role;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import StarParty.RoleStar;
import fr.fitzche.lgmore.Main;
import fr.fitzche.lgmore.PlayerData;
import fr.fitzche.lgmore.Util.ItemUtil;
import fr.fitzche.lgmore.Util.LineLocationHelper;
import fr.fitzche.lgmore.Util.LineRapport;
import fr.fitzche.lgmore.Util.LocationUtil;
import fr.fitzche.lgmore.Util.MathUtil;
import fr.fitzche.lgmore.Util.PlayerUtil;

public class Jango implements RoleStar {

	
	public PlayerData jango;
	public boolean inLife = true;
	public boolean used = false;
	public int tries = 0;
	boolean stopped = false;
	public boolean cancelled = false;
	public int during = 8;
	public boolean isUse = false;
	public Jango(PlayerData jango) {
		this.jango = jango;
	}
	@Override
	public double onDamage(PlayerData attacker, PlayerData defender) {
		if (attacker.getName().equals(jango.getName()) ) {
			if (MathUtil.pourcentage(10)) {
				if (attacker.player.getHealth() + 1 <= attacker.player.getMaxHealth()) {
					attacker.player.setHealth(attacker.player.getHealth() + 1);
				}
				defender.player.damage(1, attacker.player);
			}
		}
		return 1;
	}

	@Override
	public void onRole() {
		ItemStack sword = new ItemStack(Material.NETHER_STAR);
		ItemUtil.setName(sword, ChatColor.UNDERLINE+"Firethrown");
		ItemUtil.addAppaEnchant(sword);
		Player p = jango.player;
		p.getInventory().addItem(sword);

		
		jango.player.getInventory().addItem(new ItemStack(Material.GOLDEN_APPLE, 16));
		jango.changeHealth(4);
	}
	
	public void flame() {
		
		during --;
		LineRapport rapport = LineLocationHelper.getLineLocations(jango.player, 40, 0.3, 0.0, 0, 0, 0);
		LineRapport rapport1 = LineLocationHelper.getLineLocations(jango.player, 40, 0.3, 0.0, 1, 0, 0);
		LineRapport rapport2 = LineLocationHelper.getLineLocations(jango.player, 40, 0.3, 0.0, 0, 1, 0);
		LineRapport rapport3 = LineLocationHelper.getLineLocations(jango.player, 40, 0.3, 0.0, 0, 0, 1);
		LineRapport rapport4 = LineLocationHelper.getLineLocations(jango.player, 40, 0.3, 0.0, 0, -1, 0);
	
		ArrayList<Location> locs = new ArrayList<Location>();
		locs.addAll(rapport.locs);
		locs.addAll(rapport2.locs);
		locs.addAll(rapport1.locs);
		locs.addAll(rapport3.locs);
		locs.addAll(rapport4.locs);
		for (Location loca: locs) {
			PlayerUtil.particle(loca, Color.ORANGE, "ok", 0.2);
			for (Player ply:Bukkit.getOnlinePlayers()) {
				if (!ply.getName().equals(jango.getName()) && LocationUtil.getDistanceBetween(ply, loca) < 1) {
					ply.damage(1, jango.player);
					ply.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION	, 40, 3));
				}
			}
		}
	}
	
	@Deprecated
	public void fire() {
		
		used = true;
		Bukkit.getScheduler().runTaskLater(Main.plug, new BukkitRunnable() {@Override public void run() {flame();}
		}, 0);
		Bukkit.getScheduler().runTaskLater(Main.plug, new BukkitRunnable() {@Override public void run() {flame();}
		}, 10);
		Bukkit.getScheduler().runTaskLater(Main.plug, new BukkitRunnable() {@Override public void run() {flame();}
		}, 20);
		Bukkit.getScheduler().runTaskLater(Main.plug, new BukkitRunnable() {@Override public void run() {flame();}
		}, 30);
		Bukkit.getScheduler().runTaskLater(Main.plug, new BukkitRunnable() {@Override public void run() {flame();}
		}, 40);
		Bukkit.getScheduler().runTaskLater(Main.plug, new BukkitRunnable() {@Override public void run() {flame();}
		}, 50);
		Bukkit.getScheduler().runTaskLater(Main.plug, new BukkitRunnable() {@Override public void run() {flame();}
		}, 60);
		Bukkit.getScheduler().runTaskLater(Main.plug, new BukkitRunnable() {@Override public void run() {flame();}
		}, 70);
		Bukkit.getScheduler().runTaskLater(Main.plug, new BukkitRunnable() {@Override public void run() {flame();}
		}, 80);
		
		Bukkit.getScheduler().runTaskLater(Main.plug, new BukkitRunnable() {@Override public void run() {jango.player.sendMessage("Votre lance flame s'arrete");}
		}, 90);
		

		
	}

}
