package fr.fitzche.lgmore.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftLightningStrike;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.LightningStrike;
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
import fr.fitzche.lgmore.Util.CommandUtil;
import fr.fitzche.lgmore.Util.ItemUtil;
import fr.fitzche.lgmore.Util.LineLocationHelper;
import fr.fitzche.lgmore.Util.LineRapport;
import fr.fitzche.lgmore.Util.LocationUtil;
import fr.fitzche.lgmore.Util.MathUtil;
import fr.fitzche.lgmore.Util.PlayerUtil;
import fr.fitzche.lgmore.scoreboard.Inventory.GeneralMenu;
import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_8_R3.EntityLightning;
import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.Explosion;
import net.minecraft.server.v1_8_R3.World;


public class SpecialItemHolder implements Listener {

	
	public enum SpecialItemType {
		Ataru, Lightning, Strangle, Sith;
	}
	
	public static void addInvItem(PlayerData p, String name) {
		p.specialItemsOwned.add(name);
		p.sendMessage(Main.exclamation+ " Vous avez reçu l'item: "+ ChatColor.GOLD+ name);
	}
	
	public static void giveItem(Player p, String name) {
		switch (name) {
		case "Jogo":
			p.getInventory().addItem(ItemUtil.addAppaEnchant(ItemUtil.getItem(Material.FIREBALL, 1, ChatColor.UNDERLINE+"Jogo", new ArrayList<String>(Arrays.asList(ChatColor.GRAY+"Fait exploser la cible après 5s")))));
			
			break;
		case "Sith":
			p.getInventory().addItem(ItemUtil.addAppaEnchant(ItemUtil.getItem(Material.FERMENTED_SPIDER_EYE, 1, ChatColor.UNDERLINE+"Sith", new ArrayList<String>(Arrays.asList(ChatColor.GRAY+"Donne un boost")))));
			
			break;
		case "Ataru":
			p.getInventory().addItem(ItemUtil.addAppaEnchant(ItemUtil.getItem(Material.DIAMOND_SWORD, 1, ChatColor.UNDERLINE+"Ataru", new ArrayList<String>(Arrays.asList(ChatColor.GRAY+"Repousse les ennemis")))));
			break;
		case "Dash":
			p.getInventory().addItem(ItemUtil.addAppaEnchant(ItemUtil.getItem(Material.FEATHER, 1, ChatColor.UNDERLINE+"Dash", new ArrayList<String>(Arrays.asList(ChatColor.GRAY+"Un Dash")))));
			
			break;
		case "Inventaire d'Item":
			p.getInventory().addItem(ItemUtil.addAppaEnchant(ItemUtil.getItem(Material.FEATHER, 1, ChatColor.UNDERLINE+"Inventaire d'Item", new ArrayList<String>(Arrays.asList(ChatColor.GRAY+"Ouvrir l'inventaire d'items pour en choisir un")))));

			break;
		case "Kashimo":
			p.getInventory().addItem(ItemUtil.addAppaEnchant(ItemUtil.getItem(Material.REDSTONE, 1, ChatColor.UNDERLINE+"Kashimo", new ArrayList<String>(Arrays.asList(ChatColor.GRAY+"Ouvrir l'inventaire d'items pour en choisir un")))));

			break;
		case "Yuta":
			p.getInventory().addItem(ItemUtil.addAppaEnchant(ItemUtil.getItem(Material.IRON_SWORD, 1, ChatColor.UNDERLINE+"Yuta", new ArrayList<String>(Arrays.asList(ChatColor.GRAY+"Ouvrir l'inventaire d'items pour en choisir un")))));

			break;
		case "Hakari":
			p.getInventory().addItem(ItemUtil.addAppaEnchant(ItemUtil.getItem(Material.RABBIT_FOOT, 1, ChatColor.UNDERLINE+"Hakari", new ArrayList<String>(Arrays.asList(ChatColor.GRAY+"Ouvrir l'inventaire d'items pour en choisir un")))));

			break;
		default:
			break;
		}
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
			
			if (item.getItemMeta() != null && item.getItemMeta().getDisplayName() != null && item.getItemMeta().getDisplayName().equals(ChatColor.UNDERLINE+"Kashimo")) {
            	PlayerData p = Main.getData(event.getPlayer());
            	LineRapport r = LineLocationHelper.getLineLocations(p.player, 40, 0.2, 0.1);
        		if (r.p != null) {
        			p.player.getInventory().remove(item);
                	if (item.getAmount() > 1) {
                		p.player.getInventory().addItem(ItemUtil.setName(new ItemStack(item.getType(), item.getAmount() - 1), item.getItemMeta().getDisplayName()));
                	}
        			r.p.getLocation().getWorld().strikeLightningEffect(r.p.getLocation());
        			r.p.player.damage(4, p.player);
        			r.p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW	, 30, 3));
        		}
			}else if (item.getItemMeta() != null && item.getItemMeta().getDisplayName() != null && item.getItemMeta().getDisplayName().equals(ChatColor.UNDERLINE+"LightningInf")) {
            	PlayerData p = Main.getData(event.getPlayer());
            	LineRapport r = LineLocationHelper.getLineLocations(p.player, 40, 0.2, 0.1);
        		if (r.p != null) {
        			
        			r.p.getLocation().getWorld().strikeLightningEffect(r.p.getLocation());
        			r.p.player.damage(4, p.player);
        			r.p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW	, 30, 3));
        		}
			}else if (item.getItemMeta() != null && item.getItemMeta().getDisplayName() != null && item.getItemMeta().getDisplayName().equals(ChatColor.UNDERLINE+"Yuta")) {
				PlayerData p = Main.getData(event.getPlayer());
            	
				
            	LineRapport r = LineLocationHelper.getLineLocations(p.player, 40, 0.2, 0.1);
        		if (r.p != null) {
        			if (r.p.specialItemsOwned != null &&r.p.specialItemsOwned.size() > 1) {
        				p.player.getInventory().remove(item);
        				if (item.getAmount() > 1) {
        					p.player.getInventory().addItem(ItemUtil.setName(new ItemStack(item.getType(), item.getAmount() - 1), item.getItemMeta().getDisplayName()));
        				}
        				addInvItem(p, r.p.specialItemsOwned.get(0));
        				addInvItem(p, r.p.specialItemsOwned.get(1));
        			} else {
        				p.sendMessage("Ce joueur ne possède pas 2 objets spéciaux");
        			}
        			
        			
        		}
			
			}else if (item.getItemMeta() != null && item.getItemMeta().getDisplayName() != null && item.getItemMeta().getDisplayName().equals(ChatColor.UNDERLINE+"Hakari")) {
				PlayerData p = Main.getData(event.getPlayer());
            	
				p.player.getInventory().remove(item);
            	if (item.getAmount() > 1) {
            		p.player.getInventory().addItem(ItemUtil.setName(new ItemStack(item.getType(), item.getAmount() - 1), item.getItemMeta().getDisplayName()));
            	}
            	
            	if (MathUtil.generateAlInt(0, 100) == 50) {
            		p.sendMessage(ChatColor.YELLOW+ "Jackpot !!!");
            		p.sendMessage(ChatColor.YELLOW+ "Vous recevez 100xp, 20 feathers et une énergie illimitée pendant 60s");
            		p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 1200, 10));
            		p.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 1200, 2));
            		p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 1200, 2));
            		for (int i = 0; i<=60;i++) {
            			if (i<=60) {
        					Bukkit.getScheduler().runTaskLater(Main.plug, new BukkitRunnable() {
	
								@Override
								public void run() {
									
									PlayerUtil.particle(p.getLocation(), Color.PURPLE, "at hakari jackpot", 2, 10);
									
								}
	        					
	        				}, i*5);
        				}
            		}
            		
            	} else {
            		p.sendMessage("Hé non");
            	}
			
			}else if (item.getItemMeta() != null && item.getItemMeta().getDisplayName() != null && item.getItemMeta().getDisplayName().equals(ChatColor.UNDERLINE+"oooooo")) {
				PlayerData p = Main.getData(event.getPlayer());
            	
				p.player.getInventory().remove(item);
            	if (item.getAmount() > 1) {
            		p.player.getInventory().addItem(ItemUtil.setName(new ItemStack(item.getType(), item.getAmount() - 1), item.getItemMeta().getDisplayName()));
            	}
			
			}else if (item.getItemMeta() != null && item.getItemMeta().getDisplayName() != null && item.getItemMeta().getDisplayName().equals(ChatColor.UNDERLINE+"oooooo")) {
				PlayerData p = Main.getData(event.getPlayer());
            	
				p.player.getInventory().remove(item);
            	if (item.getAmount() > 1) {
            		p.player.getInventory().addItem(ItemUtil.setName(new ItemStack(item.getType(), item.getAmount() - 1), item.getItemMeta().getDisplayName()));
            	}
			
			}else if (item.getItemMeta() != null && item.getItemMeta().getDisplayName() != null && item.getItemMeta().getDisplayName().equals(ChatColor.UNDERLINE+"oooooo")) {
				PlayerData p = Main.getData(event.getPlayer());
            	
				p.player.getInventory().remove(item);
            	if (item.getAmount() > 1) {
            		p.player.getInventory().addItem(ItemUtil.setName(new ItemStack(item.getType(), item.getAmount() - 1), item.getItemMeta().getDisplayName()));
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
        		
            }else if (item.getItemMeta() != null && item.getItemMeta().getDisplayName() != null && item.getItemMeta().getDisplayName().equals(ChatColor.UNDERLINE+"Sith")) {
            	PlayerData p = Main.getData(event.getPlayer());
            	p.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE	, 200, 0));
        		p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED	, 200, 3));
        		
            }else if (item.getItemMeta() != null && item.getItemMeta().getDisplayName() != null && item.getItemMeta().getDisplayName().equals(ChatColor.UNDERLINE+"Navigation")) {
            	PlayerData p = Main.getData(event.getPlayer());
            	if (p== null) {
            		return;
            	}
            	(new GeneralMenu(p) ).open(p);
            }else if (item.getItemMeta() != null && item.getItemMeta().getDisplayName() != null && item.getItemMeta().getDisplayName().equals(ChatColor.UNDERLINE+"Inventaire d'Item")) {
            	CommandUtil.runCommand("lg", event.getPlayer(), new String[] {"openSpecialInv"});
            }else if (item.getItemMeta() != null && item.getItemMeta().getDisplayName() != null && item.getItemMeta().getDisplayName().equals(ChatColor.UNDERLINE+"Dash")) {
            	PlayerData p = Main.getData(event.getPlayer());
            	if (p== null) {
            		return;
            	}
            	
            	
            	p.player.getInventory().remove(item);
            	if (item.getAmount() > 1) {
            		p.player.getInventory().addItem(ItemUtil.setName(new ItemStack(item.getType(), item.getAmount() - 1), item.getItemMeta().getDisplayName()));
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
		            	
						if (locs.size() > 3) {
							
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
            }else if (item.getItemMeta() != null && item.getItemMeta().getDisplayName() != null && item.getItemMeta().getDisplayName().equals(ChatColor.UNDERLINE+"Jogo")) {
            	PlayerData p = Main.getData(event.getPlayer());
            	LineRapport r = LineLocationHelper.getLineLocations(p.player, 5, 0.2, 0.1);
            	PlayerData target = r.p;
        		if (r.p != null) {
        			p.player.getInventory().remove(item);
                	if (item.getAmount() > 1) {
                		p.player.getInventory().addItem(ItemUtil.setName(new ItemStack(item.getType(), item.getAmount() - 1), item.getItemMeta().getDisplayName()));
                	}
        			int n = 30;
        			for (int i = 0; i<=n; i++) {
        				if (i<n - 1) {
        					Bukkit.getScheduler().runTaskLater(Main.plug, new BukkitRunnable() {
	
								@Override
								public void run() {
									
									PlayerUtil.particle(target.getLocation().add(new Vector(0, 1, 0)), EnumParticle.FLAME, " at jogo power ", 1.5, true, 100);
									float f = 1;
									
								}
	        					
	        				}, i*5);
        				} else {
        					Bukkit.getScheduler().runTaskLater(Main.plug, new BukkitRunnable() {
        						
								@Override
								public void run() {
									
									
									ArrayList<Player> ps = new ArrayList<Player>();
									for (Player o:target.getLocation().getWorld().getPlayers()) {
										if (LocationUtil.getDistanceBetween(o, target.getLocation()) < 10) {
											
											o.addPotionEffect(new PotionEffect( PotionEffectType.DAMAGE_RESISTANCE, 40, 255));
											ps.add(o);
											
										}
									}
									target.getLocation().getWorld().createExplosion(target.getLocation(), 10);
									for (Player o:ps) {
										Main.pvpWorldDamage(o, p.player, 6);
									}
								}
	        					
	        				}, i*5);
        				}
	        				
        				
        			}
        			
        		}
			}else if (item.getItemMeta() != null && item.getItemMeta().getDisplayName() != null && item.getItemMeta().getDisplayName().equals(ChatColor.UNDERLINE+"TargetItem")) {
            	PlayerData p = Main.getData(event.getPlayer());
            	LineRapport r = LineLocationHelper.getLineLocations(p.player, 5, 0.2, 0.1);
            	PlayerData target = r.p;
            	if (r.p != null) {
        			
        			
        		}
			}else if (item.getItemMeta() != null && item.getItemMeta().getDisplayName() != null && item.getItemMeta().getDisplayName().equals(ChatColor.UNDERLINE+"TargetItem")) {
            	PlayerData p = Main.getData(event.getPlayer());
            	LineRapport r = LineLocationHelper.getLineLocations(p.player, 5, 0.2, 0.1);
            	PlayerData target = r.p;
            	if (r.p != null) {
        			
        			
        		}
			}else if (item.getItemMeta() != null && item.getItemMeta().getDisplayName() != null && item.getItemMeta().getDisplayName().equals(ChatColor.UNDERLINE+"TargetItem")) {
            	PlayerData p = Main.getData(event.getPlayer());
            	LineRapport r = LineLocationHelper.getLineLocations(p.player, 5, 0.2, 0.1);
            	PlayerData target = r.p;
            	if (r.p != null) {
        			
        			
        		}
			}else if (item.getItemMeta() != null && item.getItemMeta().getDisplayName() != null && item.getItemMeta().getDisplayName().equals(ChatColor.UNDERLINE+"TargetItem")) {
            	PlayerData p = Main.getData(event.getPlayer());
            	LineRapport r = LineLocationHelper.getLineLocations(p.player, 5, 0.2, 0.1);
            	PlayerData target = r.p;
            	if (r.p != null) {
        			
        			
        		}
			} 
            
			
		}
	}
}
