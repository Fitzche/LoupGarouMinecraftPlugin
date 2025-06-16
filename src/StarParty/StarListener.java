package StarParty;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import StarParty.Role.DarkMaul;
import StarParty.Role.DarkVador;
import StarParty.Role.Dooku;
import StarParty.Role.Grievous;
import StarParty.Role.Jango;
import StarParty.Role.Obiwan;
import StarParty.Role.Palpatine;
import fr.fitzche.lgmore.Main;
import fr.fitzche.lgmore.PlayerData;
import fr.fitzche.lgmore.Util.ItemUtil;
import fr.fitzche.lgmore.Util.LineLocationHelper;
import fr.fitzche.lgmore.Util.LineRapport;
import fr.fitzche.lgmore.Util.MathUtil;
import fr.fitzche.lgmore.Util.PlayerUtil;
import fr.fitzche.lgmore.minecraft.GameListener;

public class StarListener implements GameListener, Listener {

	StarParty party;
	
	public StarListener(StarParty party) {
		this.party = party;
	}
	
	@EventHandler
	public void EntityDamageByEntityEvent(org.bukkit.event.entity.EntityDamageByEntityEvent e) {
		if (!(e.getEntity() instanceof Player) || !(e.getDamager() instanceof Player)) {
			return;
		}
		PlayerData p = Main.getData(e.getEntity());
		e.setDamage(damagePbyP(Main.getData(e.getDamager())	, Main.getData(e.getEntity()), e.getDamage(), false));
		
		if (p.starParty != null && p.roleStar.equals(RolesStar.DarkMaul)&& ((Player) e.getEntity()).getHealth() - e.getFinalDamage() <= 0 ) {
			e.setCancelled(true);
			e.getEntity().teleport(RolesStar.DarkMaul.getLoc());
			p.player.setHealth(p.player.getMaxHealth());
			p.sendMessage("Vous avez échappé de justesse à la mort");
			p.changeHealth(-4);
		}
		
		
	}
	@Override
	public double damagePbyP(PlayerData damager, PlayerData damaged, double damage, boolean isArrow) {
		if (!party.started) {
			return damage;
		}
		
		double returned = damage;
		returned *= damager.roleSta.onDamage(damager, damaged);
		returned *= damaged.roleSta.onDamage(damager, damaged);
		return returned;
	}

	@Override
	public double damagePbyEntity(PlayerData damaged, Entity damager, double damage) {
		if (!party.started) {
			return 1;
		}
		return 1;
	}

	@Override
	public double kill(PlayerData killed, PlayerData killer) {
		
		killer.numberOfKill ++;
		if (killer.roleStar.equals(RolesStar.Grievou)) {
			Grievous grie = (Grievous) killer.roleSta;
			if (killed.roleStar.hasLightsaber) {
				grie.boost ++;
				grie.griev.sendMessage("Vous gagnez 5% de force");
			}
		}
		if (killer.roleStar.equals(RolesStar.Stormtrooper)) {
			Grievous grie = (Grievous) killer.roleSta;
			grie.griev.player.setHealth(grie.griev.player.getMaxHealth());
		}
		
		
		
		killed.starParty.death(killed);
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
	public void onPlayerDeath(PlayerDeathEvent e) {
		if (!party.started) {
			return;
		}
		
		
		PlayerData killed = Main.getData(e.getEntity());
		Entity kil = e.getEntity().getKiller();
		if (killed == null || killed.starParty == null || killed.starParty != this.party) {
			return;
		}
		e.setKeepLevel(true);
		e.getEntity().getInventory().clear();
		if (kil == null) {
			killed.starParty.death(killed);
			return;
		}
		PlayerData killer;
		if (kil instanceof Arrow && ((Arrow) kil).getShooter() instanceof Player) {
			killer = Main.getData((Player) ((Arrow) kil).getShooter());
		} else if (kil instanceof Player) {
			killer = Main.getData(kil);
		} else {
			killed.starParty.death(killed);
			return;
		}
		if (killer.starParty.equals(killed.starParty)) {
			if (killed.roleStar.equals(RolesStar.Leila)) {
				for (PlayerData p:party.players) {
					if (p.getLocation().distance(e.getEntity().getLocation()) < 10 && p.roleStar.color.equals(Color.BLUE)) {
						p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 40, 4));
					}
				}
			}
			
			kill(killed, killer);
		}
		
	}
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent e) {
		
		PlayerData p = Main.getData(e.getPlayer());
		if (p.starParty != null && p.starParty.name.equals(party.name)) {
			party.removePlayer(p);
		}
		
	}
	
	public void onEntityShootBow(EntityShootBowEvent e) {
		if (!party.started && false) {
			return;
		}
		if (e.getEntity() instanceof Player) {
			PlayerData p = Main.getData(e.getEntity());
			if (p.starParty != null && p.starParty.equals(party)) {
				e.setCancelled(true);
				LineRapport rapport;
				if (p.roleStar.equals(RolesStar.Stormtrooper)) {
					rapport = LineLocationHelper.getLineLocations((Player) e.getEntity(), 40 * e.getForce(), 0.25, 0.3);

				}
				
				rapport = LineLocationHelper.getLineLocations((Player) e.getEntity(), 40 * e.getForce() *e.getForce(), 0.25, 0.3);
				List<Location> locs = rapport.locs;
				locs.remove(0);
				locs.remove(0);
				locs.remove(0);
				
				if (rapport.p != null) {
					rapport.p.player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW	, 10, 3));

					if (rapport.p.roleStar.hasSaber()) {
						locs.remove(locs.size() - 1);
						for (Location loc:locs) {
							PlayerUtil.particle(loc, Color.RED, "ok", 0);
						}
						PlayerUtil.particle(rapport.finalLoc, ItemUtil.getColor(p.roleStar.getColor()), "ok", 0.1);
						PlayerUtil.particle(rapport.finalLoc, ItemUtil.getColor(p.roleStar.getColor()), "ok", 0.1);
						PlayerUtil.particle(rapport.finalLoc, ItemUtil.getColor(p.roleStar.getColor()), "ok", 0.1);
						if (MathUtil.pourcentage(30)) {
							rapport.p.player.damage(2, e.getEntity());
							
						}
						if (p.roleStar.equals(RolesStar.Chewbaca) && MathUtil.pourcentage(30)) {
							rapport.p.getLocation().getWorld().createExplosion(rapport.p.getLocation(), 0, false);
							rapport.p.player.damage(2, p.player);
						}
					} else {
						for (Location loc:locs) {
							PlayerUtil.particle(loc, Color.RED, "ok", 0);
						}
						rapport.p.player.damage(2, e.getEntity());
						if (p.roleStar.equals(RolesStar.Chewbaca) && MathUtil.pourcentage(30)) {
							rapport.p.getLocation().getWorld().createExplosion(rapport.p.getLocation(), 0, false);
							rapport.p.player.damage(2, p.player);
						}
					}
					
				} else {
					for (Location loc:locs) {
						PlayerUtil.particle(loc, Color.RED, "ok", 0);
					}
				}
			}
		}
	}

	
	@Deprecated
	 @EventHandler
	 public void onPlayerInteract(PlayerInteractEvent event) {
	        // Vérifie que l'action est un clic droit (dans l'air ou sur un bloc)
	        Action action = event.getAction();
	        if (action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) {
	            // Récupération de l'item en main
	            ItemStack item = event.getItem();
	            if (item != null) {
	                Material type = item.getType();
	                // Vérifie que l'item est une épée.
	                // On peut comparer directement ou utiliser endsWith("_SWORD")
	                if (item.getItemMeta() != null && item.getItemMeta().getDisplayName() != null && item.getItemMeta().getDisplayName().equals(ChatColor.UNDERLINE+"Ataru")) {
	                	PlayerData p = Main.getData(event.getPlayer());
	                	if (p.roleStar != null && p.roleStar.equals(RolesStar.ObiWan)) {
	                		Obiwan obi = (Obiwan) p.roleSta;
	                		if (obi.use < 1) {
	                			
	                			p.sendMessage("Vous ne pouvez plus utiliser l'ataru");
	                			return;
	                		}
	                		obi.use--;
	                		for (PlayerData ply:party.players) {
	                			if (!ply.getName().equals(p.getName())&&  p.getLocation().distance(ply.getLocation()) < 3) {
	                				LineLocationHelper.applyKnockback(ply.player, p.player, (3 - p.getLocation().distance(ply.getLocation())) * 3);
	                			}
	                		}
	                	}
	                } else if (item.getItemMeta() != null && item.getItemMeta().getDisplayName() != null && item.getItemMeta().getDisplayName().equals(ChatColor.UNDERLINE+"Lightning")) {
	                	PlayerData p = Main.getData(event.getPlayer());
	                	if (p.roleStar != null && p.roleStar.equals(RolesStar.Palpa)) {
	                		Palpatine obi = (Palpatine) p.roleSta;
	                		if (obi.use < 1) {
	                			
	                			p.sendMessage("Vous ne pouvez plus utiliser l'éclair de force");
	                			return;
	                		}
	                		
	                		LineRapport r = LineLocationHelper.getLineLocations(p.player, 40, 0.2, 0.1);
	                		if (r.p != null) {
	                			obi.use--;
	                			r.p.getLocation().getWorld().strikeLightningEffect(r.p.getLocation());
	                			r.p.player.damage(4, p.player);
	                			r.p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW	, 30, 3));
	                		}
	                	}
	                }else if (item.getItemMeta() != null && item.getItemMeta().getDisplayName() != null && item.getItemMeta().getDisplayName().equals(ChatColor.UNDERLINE+"Strangle")) {
	                	PlayerData p = Main.getData(event.getPlayer());
	                	if (p.roleStar != null && p.roleStar.equals(RolesStar.DarkVador)) {
	                		DarkVador obi = (DarkVador) p.roleSta;
	                		if (obi.strangle > 0) {
	                			obi.strangle --;
	                		}
	                		
	                		
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
	                	}
	                } else if (item.getItemMeta() != null && item.getItemMeta().getDisplayName() != null && item.getItemMeta().getDisplayName().equals(ChatColor.UNDERLINE+"Sith")) {
	                	PlayerData p = Main.getData(event.getPlayer());
	                	if (p.roleStar != null && p.roleStar.equals(RolesStar.Palpa)) {
	                		Palpatine obi = (Palpatine) p.roleSta;
	                		if (obi.transfo) {
	                			
	                			p.sendMessage("Vous ne pouvez plus utiliser le pouvoir des sith");
	                			return;
	                		}
	                		obi.transfo = true;
	                		p.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE	, 200, 0));
	                		p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED	, 200, 3));
	                	}
	                	if (p.roleStar != null && p.roleStar.equals(RolesStar.DarkMaul)) {
	                		DarkMaul obi = (DarkMaul) p.roleSta;
	                		if (obi.sith) {
	                			
	                			p.sendMessage("Vous ne pouvez plus utiliser le pouvoir des sith");
	                			return;
	                		}
	                		obi.sith = true;
	                		
	                		p.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE	, 200, 0));
	                		p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED	, 200, 3));
	                	}
	                	if (p.roleStar != null && p.roleStar.equals(RolesStar.DarkVador)) {
	                		DarkVador obi = (DarkVador) p.roleSta;
	                		if (obi.sith) {
	                			
	                			p.sendMessage("Vous ne pouvez plus utiliser le pouvoir des sith");
	                			return;
	                		}
	                		obi.sith = true;
	                		
	                		p.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE	, 200, 0));
	                		p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED	, 200, 3));
	                	}
	                }else if (item.getItemMeta() != null && item.getItemMeta().getDisplayName() != null && item.getItemMeta().getDisplayName().equals(ChatColor.UNDERLINE+"Makashi")) {
	                	PlayerData p = Main.getData(event.getPlayer());
	                	if (p.roleStar != null && p.roleStar.equals(RolesStar.Dooku)) {
	                		Dooku obi = (Dooku) p.roleSta;
	                		
	                		if (!obi.duel) {
	                			obi.duel = true;
	                			PlayerData ply = LineLocationHelper.focus(obi.dooku.player);
	                			if (ply != null) {
	                				obi.opponent = ply.getName();
	                				obi.dooku.sendMessage("Vous prenez "+ply.getName() + " en duel, celui-ci ne pourra vous taper que vous pendant 30s, et réciproquement.");
	                				ply.sendMessage("Le compte Dooku: "+ply.getName() + " vous lance un duel, celui-ci ne pourra vous taper que vous pendant 30s, et réciproquement.");

	                			}
	                		}
	                		
	                	}
	                }else if (item.getItemMeta() != null && item.getItemMeta().getDisplayName() != null && item.getItemMeta().getDisplayName().equals(ChatColor.UNDERLINE+"Firethrown")) {
	                	PlayerData p = Main.getData(event.getPlayer());
	                	if (p.roleStar != null && p.roleStar.equals(RolesStar.Jango)) {
	                		Jango obi = (Jango) p.roleSta;
	                		
	                		if (obi.used) {
	                			p.sendMessage("Vous avez déjà utilisé votre pouvoir");
	                			return;
	                		}
	                		if (obi.isUse) {
	                			return;
	                		}
	                		obi.isUse = true;
	                		Bukkit.getScheduler().runTaskLater(Main.plug, new BukkitRunnable() {@Override public void run() {
	                			if (!event.getPlayer().getItemInHand().equals(event.getItem()) || obi.tries >= 7 || !obi.inLife) {
	                				obi.cancelled = true;
	                				obi.isUse = false;
	                			}
	                			PlayerUtil.particle(LineLocationHelper.getLineLocations(p.player, 20, 0.2, 0).locs.get(1), Color.ORANGE, "ok", 0.2, 12);
	                			p.player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20, 3, false, false));

	                			}
	                		}, 10);
	                		Bukkit.getScheduler().runTaskLater(Main.plug, new BukkitRunnable() {@Override public void run() {
	                			if (!event.getPlayer().getItemInHand().equals(event.getItem()) || obi.tries >= 7 || !obi.inLife) {
	                				obi.cancelled = true;
	                				obi.isUse = false;
	                			}
	                			PlayerUtil.particle(LineLocationHelper.getLineLocations(p.player, 20, 0.2, 0).locs.get(1), Color.ORANGE, "ok", 0.2, 12);
	                			p.player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20, 3, false, false));

	                			}
	                		
	                		}, 20);
	                		Bukkit.getScheduler().runTaskLater(Main.plug, new BukkitRunnable() {@Override public void run() {
	                			if (!event.getPlayer().getItemInHand().equals(event.getItem()) || obi.tries >= 7 || !obi.inLife) {
	                				obi.cancelled = true;
	                				obi.isUse = false;
	                			}
	                			PlayerUtil.particle(LineLocationHelper.getLineLocations(p.player, 20, 0.2, 0).locs.get(1), Color.ORANGE, "ok", 0.2, 12);
	                			p.player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20, 3, false, false));

	                			}
	                		}, 30);
	                		Bukkit.getScheduler().runTaskLater(Main.plug, new BukkitRunnable() {@Override public void run() {
	                			if (!event.getPlayer().getItemInHand().equals(event.getItem()) || obi.tries >= 7 || !obi.inLife) {
	                				obi.cancelled = true;
	                				obi.isUse = false;
	                			}
	                			PlayerUtil.particle(LineLocationHelper.getLineLocations(p.player, 20, 0.2, 0).locs.get(1), Color.ORANGE, "ok", 0.2, 12);
	                			p.player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20, 3, false, false));

	                			}
	                		}, 40);
	                		Bukkit.getScheduler().runTaskLater(Main.plug, new BukkitRunnable() {@Override public void run() {
	                			if (!event.getPlayer().getItemInHand().equals(event.getItem()) || obi.tries >= 7 || !obi.inLife) {
	                				obi.cancelled = true;
	                				obi.isUse = false;
	                			}
	                			PlayerUtil.particle(LineLocationHelper.getLineLocations(p.player, 20, 0.2, 0).locs.get(1), Color.ORANGE, "ok", 0.2, 12);
	                			p.player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20, 3, false, false));

	                			}
	                		}, 50);
	                		Bukkit.getScheduler().runTaskLater(Main.plug, new BukkitRunnable() {@Override public void run() {
	                			if (!event.getPlayer().getItemInHand().equals(event.getItem()) || obi.tries >= 7 || !obi.inLife) {
	                				obi.cancelled = true;
	                				obi.isUse = false;
	                			}
	                			PlayerUtil.particle(LineLocationHelper.getLineLocations(p.player, 20, 0.2, 0).locs.get(1), Color.ORANGE, "ok", 0.2, 12);
	                			p.player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20, 3, false, false));

	                			}
	                		}, 60);
	                		Bukkit.getScheduler().runTaskLater(Main.plug, new BukkitRunnable() {@Override public void run() {
	                			if (!event.getPlayer().getItemInHand().equals(event.getItem()) || obi.tries >= 7 || !obi.inLife) {
	                				obi.cancelled = true;
	                				obi.isUse = false;
	                			}
	                			PlayerUtil.particle(LineLocationHelper.getLineLocations(p.player, 20, 0.2, 0).locs.get(1), Color.ORANGE, "ok", 0.2, 12);
	                			p.player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20, 3, false, false));

	                			}
	                		}, 70);
	                		Bukkit.getScheduler().runTaskLater(Main.plug, new BukkitRunnable() {@Override public void run() {
	                			if (!event.getPlayer().getItemInHand().equals(event.getItem()) || obi.tries >= 7 || !obi.inLife) {
	                				obi.cancelled = true;
	                				obi.isUse = false;
	                			}
	                			
	                			PlayerUtil.particle(LineLocationHelper.getLineLocations(p.player, 20, 0.2, 0).locs.get(1), Color.ORANGE, "ok", 0.2, 12);
	                			p.player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20, 3, false, false));

	                			}
	                		}, 80);
	                		
	                		Bukkit.getScheduler().runTaskLater(Main.plug, new BukkitRunnable() {@Override public void run() {
	                			if (!obi.cancelled) {
	                				obi.fire();
	                			} else {
	                				obi.isUse = false;
	                				obi.cancelled = false;
	                				
	                				
	                				
	                			}
	                			PlayerUtil.particle(LineLocationHelper.getLineLocations(p.player, 20, 0.2, 0).locs.get(1), Color.ORANGE, "ok", 0.2, 12);
	                			p.player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20, 3, false, false));
	                			}
	                		}, 90);
	                		
	                		
	                	}
	                }
	                
	            }
	        }
	    }
}
