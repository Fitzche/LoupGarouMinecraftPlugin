package fr.fitzche.lgmore.bedwars;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.event.vehicle.VehicleDestroyEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import fr.fitzche.lgmore.Main;
import fr.fitzche.lgmore.PlayerData;
import fr.fitzche.lgmore.Util.PlayerUtil;
import fr.fitzche.lgmore.minecraft.GameListener;

public class BedListener implements GameListener, Listener {
	Bedwars bed;
	
	public BedListener(Bedwars bed) {
		this.bed = bed;
	}
	@Override
	public double damagePbyP(PlayerData damager, PlayerData damaged, double damage, boolean isArrow) {
		// TODO Auto-generated method stub
		return damage;
	}
	
	public boolean damageCancel(PlayerData damager, PlayerData damaged, double damage, boolean isArrow) {
		
		if (damaged.player.getHealth() < damage) {
			//MORT
			damager.numberOfKill ++;
			damaged.player.teleport(bed.spawnOfTeams.getOrDefault(damaged.bedTeam, damaged.player.getLocation()));
			bed.broadcoast(damaged.getName() + " killed !" );
			
			int timeInTick = Bedwars.timeOnDeath * 20;
			damaged.player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 10, timeInTick));
			damaged.player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 10, timeInTick));
			damaged.player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 10, timeInTick));
			damaged.player.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 10, timeInTick));
			
			if (bed.lifeOfTeams.getOrDefault(damaged.bedTeam, 100) <=0) {
				//ELIMINATION ACTIONS
				damaged.clearLgGameVar();
				PlayerUtil.spectator(damaged.player);
				bed.players.remove(damaged);
				
				BedTeam checking = null;
				for (PlayerData p:bed.players) {
					if (checking == null) {
						checking = p.bedTeam;
					} else {
						if (!p.bedTeam.equals(checking)) {
							return true;
						}
					}
				}
				
				bed.win(checking);
			}
			return true;
		}
		return false;
	}

	@Override
	public double damagePbyEntity(PlayerData damaged, Entity damager, double damage) {
		// TODO Auto-generated method stub
		return damage;
	}

	@Override
	public double kill(PlayerData killed, PlayerData killer) {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public boolean rez(PlayerData killed, PlayerData killer, Location loc, PlayerDeathEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean rez2(PlayerData killed, PlayerData killer, Location loc, PlayerDeathEvent e,
			ArrayList<ItemStack> items) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void click(PlayerData p, String name, ArrayList<String> lores, int index, Inventory inv) {
		// TODO Auto-generated method stub

	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
		if (Main.getData(e.getWhoClicked()).bedGame != null && Main.getData(e.getWhoClicked()).bedGame.name.equals(bed.name) && e.getInventory().equals(e.getWhoClicked().getInventory())) {
			if (e.getSlotType().equals(SlotType.ARMOR) && bed.started) {
				e.getWhoClicked().sendMessage("Vous ne pouvez pas retirer votre armure en partie de bedwars");
				e.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void onEntityDamage(EntityDamageEvent event) {
	    if (event.getEntity() instanceof ArmorStand && event.getEntity().getMetadata("unbreakable").get(0).equals(Main.unbreakableMeta)) {
	        if (event.getEntity().hasMetadata("bedTeam") && event.getEntity().getLocation().getWorld().equals(bed.getWorld())) {
	        	if (event.getEntity().getMetadata("bedTeam").get(0).equals(BedTeam.blueMeta)) {
	        		bed.damageBed(BedTeam.Blue);
	        	} else if (event.getEntity().getMetadata("bedTeam").get(0).equals(BedTeam.GreenMeta)) {
	        		bed.damageBed(BedTeam.GREEN);
	        	} else if (event.getEntity().getMetadata("bedTeam").get(0).equals(BedTeam.RedMeta)) {
	        		bed.damageBed(BedTeam.RED);
	        	} else if (event.getEntity().getMetadata("bedTeam").get(0).equals(BedTeam.YellowMeta)) {
	        		bed.damageBed(BedTeam.YELLOW);
	        	}
	        }
	    	
	    	event.setCancelled(true);
	    }
	    
	}

	@EventHandler
	public void onEntityBreak(VehicleDestroyEvent event) {
	    if (event.getVehicle() instanceof ArmorStand && event.getVehicle().getMetadata("unbreakable").equals("true")) {
	        event.setCancelled(true);
	    }
	}

}
