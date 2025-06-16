package fr.fitzche.lgmore.Util;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;

import fr.fitzche.lgmore.Main;
import fr.fitzche.lgmore.PlayerData;

public class HealthSyncer implements Listener{

	
	PlayerData p1;
	PlayerData p2;
	public boolean stopped = false;
	
	public HealthSyncer(PlayerData p1,PlayerData p2) {
		this.p1 = p1;
		this.p2 = p2;
		Bukkit.getPluginManager().registerEvents(this, Main.plug);
	}
	
	@EventHandler
	public void onPlayerHeal(EntityRegainHealthEvent e) {
		if (stopped) {
			return;
		}
		if (e.getEntity().getName().equals(p1.getName())) {
			double newLife = p1.getHealth() + e.getAmount();
			if (newLife > p2.getMaxHealth()) {
				newLife = p2.getMaxHealth();
			}
			p1.player.setHealth(newLife);
		} else if (e.getEntity().getName().equals(p2.getName())) {
			double newLife = p2.getHealth() + e.getAmount();
			if (newLife > p1.getMaxHealth()) {
				newLife = p1.getMaxHealth();
			}
			p1.player.setHealth(newLife);
		}
	}
	
	
	
	@EventHandler
	public void onPlayerDamage(EntityDamageEvent e) {
		if (stopped) {
			return;
		}
		if (e.getEntity().getName().equals(p1.getName())) {
			double newLife = p1.getHealth() - e.getDamage();
			if (newLife <= 0) {
				stopped = true;
				p2.player.damage(200, p2.player);
			}else {
				p1.setHealth(newLife);
			}
			p1.player.setHealth(newLife);
		} else if (e.getEntity().getName().equals(p2.getName())) {
			double newLife = p2.getHealth() - e.getDamage();
			if (newLife <= 0) {
				stopped = true;
				p1.player.damage(200, p1.player);
			} else {
				p1.setHealth(newLife);
			}
			
		}
	}
	
	
}
