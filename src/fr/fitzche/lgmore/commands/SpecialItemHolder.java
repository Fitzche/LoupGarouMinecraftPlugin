package fr.fitzche.lgmore.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import org.inventivetalent.particle.ParticlePlugin;


import de.inventivegames.particle.ParticleEffect;
import de.slikey.effectlib.Effect;
import de.slikey.effectlib.EffectLib;
import de.slikey.effectlib.EffectManager;
import de.slikey.effectlib.EffectType;
import de.slikey.effectlib.effect.VortexEffect;
import fr.fitzche.lgmore.Main;
import fr.fitzche.lgmore.PlayerData;
import fr.fitzche.lgmore.Util.LineLocationHelper;
import fr.fitzche.lgmore.Util.LineRapport;
import fr.fitzche.lgmore.Util.LocationUtil;
import fr.fitzche.lgmore.Util.MathUtil;
import fr.fitzche.lgmore.Util.PlayerUtil;
import fr.fitzche.lgmore.scoreboard.Inventory.GeneralMenu;
import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_8_R3.EnumParticle;


public class SpecialItemHolder implements Listener {

	
	public enum SpecialItemType {
		Ataru, Lightning, Strangle, Sith;
	}
	

	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
	        // Vérifie que l'action est un clic droit (dans l'air ou sur un bloc)
		Action action = event.getAction();
		if (action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) {
			if (event == null || event.getItem() == null) {
				return;
			}
			ItemStack item = event.getItem();
			if (item == null|| !item.hasItemMeta()  || !item.getItemMeta().hasDisplayName()) {
				return;
			}
			// Récupération de l'item en main
			
			if (item != null) {
				Material type = item.getType();
				// Vérifie que l'item est une épée.
				// On peut comparer directement ou utiliser endsWith("_SWORD")
				if (item.getItemMeta() != null && item.getItemMeta().getDisplayName() != null && item.getItemMeta().getDisplayName().equals(ChatColor.UNDERLINE+"AtaruInf")) {
					PlayerData p = Main.getData(event.getPlayer());
					for (Player ply:Bukkit.getOnlinePlayers()) {
						
						if (!p.getName().equals(ply.getName())&&p.getLocation().distance(ply.getLocation()) < 3) {
							ply.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE	, 250, 100));
							LineLocationHelper.applyKnockback(ply, p.player, (3 - p.getLocation().distance(ply.getLocation())) * 3);
							
						}
					}
				}
			}
			
			if (item.getItemMeta() != null && item.getItemMeta().getDisplayName() != null && item.getItemMeta().getDisplayName().equals(ChatColor.UNDERLINE+"LightningInf")) {
            	PlayerData p = Main.getData(event.getPlayer());
            	LineRapport r = LineLocationHelper.getLineLocations(p.player, 40, 0.2, 0.1);
        		if (r.p != null) {
        			
        			r.p.getLocation().getWorld().strikeLightningEffect(r.p.getLocation());
        			r.p.player.damage(4, p.player);
        			r.p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW	, 30, 3));
        		}
			}else if (item.getItemMeta() != null && item.getItemMeta().getDisplayName() != null && item.getItemMeta().getDisplayName().equals(ChatColor.UNDERLINE+"StrangleInf")) {
            	PlayerData p = Main.getData(event.getPlayer());
            	LineRapport r = LineLocationHelper.getLineLocations(p.player, 40, 0.2, 0.1);
        		if (r.p != null) {
        			p.sendMessage("Vous étrangler "+ r.p.getName());
        			p.player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 40, 1, false, false));
        			r.p.player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 60, 2, false, false));
        			r.p.player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 60, 2, false, false));
        			r.p.player.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 60, 1, false, false));
        			r.p.sendMessage("Vous êtes affecté par l'étranglement de Dark Vador");
        			PlayerUtil.particle(r.p.getLocation(), Color.BLACK, "ok", 1);
        		
        		}
            } else if (item.getItemMeta() != null && item.getItemMeta().getDisplayName() != null && item.getItemMeta().getDisplayName().equals(ChatColor.UNDERLINE+"SithInf")) {
            	PlayerData p = Main.getData(event.getPlayer());
            	p.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE	, 200, 0));
        		p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED	, 200, 3));
        	
            }else if (item.getItemMeta() != null && item.getItemMeta().getDisplayName() != null && item.getItemMeta().getDisplayName().equals(ChatColor.UNDERLINE+"Navigation")) {
            	PlayerData p = Main.getData(event.getPlayer());
            	if (p== null) {
            		return;
            	}
            	(new GeneralMenu(p) ).open(p);
            }else if (item.getItemMeta() != null && item.getItemMeta().getDisplayName() != null && item.getItemMeta().getDisplayName().equals(ChatColor.UNDERLINE+"Dash")) {
            	PlayerData p = Main.getData(event.getPlayer());
            	if (p== null) {
            		return;
            	}
            	event.getPlayer().getInventory().remove(item);
            	
            	int force = 3;
            	if (item.getEnchantmentLevel(Enchantment.ARROW_INFINITE) > 0) {
            		force *= 250;
            	}
            	if (item.getEnchantmentLevel(Enchantment.ARROW_DAMAGE) > 0) {
            		force *= 250;
            	} else if (item.getEnchantmentLevel(Enchantment.ARROW_DAMAGE) > 1) {
            		force *= 250;
            	} else if (item.getEnchantmentLevel(Enchantment.ARROW_DAMAGE) > 2) {
            		force *= 250;
            	}
            	int nb = 30;
            	int s = force;
            	p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 300, 1));
            	
            	
            	if (item.getEnchantmentLevel(Enchantment.ARROW_FIRE) > 0) {
            		PlayerUtil.particle(p.getLocation(), Color.RED, "", 3, s);
            	}
            	
            	LineLocationHelper.applyForce(event.getPlayer(), LineLocationHelper.getLineLocations(event.getPlayer(), 10, 3, 1, 0, -0.25, 0).locs.get(1), force);
            
            	
            	for (Location l:LineLocationHelper.getLineLocations(event.getPlayer(), 10, 1, 1, 0, -0.25, 0).locs) {
            		PlayerUtil.particle(l, Color.RED, "", 0.001);
            	}
            }else if (item.getItemMeta() != null && item.getItemMeta().getDisplayName() != null && item.getItemMeta().getDisplayName().equals(ChatColor.UNDERLINE+"DashInf")) {
            	PlayerData p = Main.getData(event.getPlayer());
            	if (p== null) {
            		return;
            	}
            	
            	int force = 3;
            	if (item.getEnchantmentLevel(Enchantment.ARROW_INFINITE) > 0) {
            		force *= 250;
            	}
            	if (item.getEnchantmentLevel(Enchantment.ARROW_DAMAGE) > 0) {
            		force *= 250;
            	} else if (item.getEnchantmentLevel(Enchantment.ARROW_DAMAGE) > 1) {
            		force *= 250;
            	} else if (item.getEnchantmentLevel(Enchantment.ARROW_DAMAGE) > 2) {
            		force *= 250;
            	}
            	int nb = 30;
            	int s = force;
            	p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 300, 1));
            	
            	
            	if (item.getEnchantmentLevel(Enchantment.ARROW_FIRE) > 0) {
            		PlayerUtil.particle(p.getLocation(), Color.RED, "", 3, s);
            	}
            	
            	LineLocationHelper.applyForce(event.getPlayer(), LineLocationHelper.getLineLocations(event.getPlayer(), 10, 3, 1, 0, 0, 0).locs.get(1), force);

            	
            	for (Location l:LineLocationHelper.getLineLocations(event.getPlayer(), 10, 1, 1, 0, -0.25, 0).locs) {
            		PlayerUtil.particle(l, Color.RED, "", 0.001);
            	}
            	
            	
            }else if (item.getItemMeta() != null && item.getItemMeta().getDisplayName() != null && item.getItemMeta().getDisplayName().equals(ChatColor.UNDERLINE+"Infinite Blue")) {
            	System.out.println("Blue infinite gojo l.173 SpecialItemHolder");
            	new BukkitRunnable() {

					@Override
					public void run() {
						
						
						List<Location> locs = LineLocationHelper.getLineLocations(event.getPlayer(), 40, 10, 0, 0, -0.25, 0).locs;
		            	System.out.println(locs.size() + "; ");
						if (locs.size() > 3) {
							System.out.println("here");
		            		Location loc = locs.get(1);
							
		            		PlayerData p = Main.getData(event.getPlayer());
		            		p.gojoIt --;
		            		if (p.gojoIt < 0) {
		            			
		            			p.gojoIt = 150;
		            			try {
									this.finalize();
								} catch (Throwable e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
		            			this.cancel();
		            			
		            		} else {
		            			p.gojoIt --;
		            			if (p.gojoLoop == 0) {
		            				p.gojoLoop = 10;
		            				
		            			} else {
		            				p.gojoLoop --;
		            				
		            			}
		            			double radius = (p.gojoLoop *0.05);
		            			
		            			PlayerUtil.particle(loc, EnumParticle.DRIP_WATER, loc.getWorld().getName(), radius, true, 100);
		            			//System.out.println("particle l.203 item holder: "+ radius + "; "+ p.gojoIt);
		            			
		            			for (Player player:Bukkit.getOnlinePlayers()) {
		            				if (LocationUtil.getDistanceBetween(player, loc) < 3 && (p.gojoLoop == 10 || p.gojoLoop == 6 || p.gojoLoop == 3|| p.gojoLoop == 2)|| p.gojoLoop == 5) {
		            					
		            					player.damage(1);
		            					if (p.gojoLoop == 10) {
		            						player.teleport(loc);
		            					}
		            				}
		            				
		            			}
		            			
		            			
		            			
		            			
		            			
		            			
		            		}
		            		
		            		
		            	}
						
					}
            		
            	}.runTaskTimer(Main.plug, 3, 3);
            }
			
		}
	}
}
