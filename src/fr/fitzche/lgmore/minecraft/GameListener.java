package fr.fitzche.lgmore.minecraft;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import fr.fitzche.lgmore.PlayerData;

public interface GameListener {

	
	public double damagePbyP(PlayerData damager, PlayerData damaged, double damage, boolean isArrow);
	public double damagePbyEntity(PlayerData damaged, Entity damager, double damage);
	public double kill(PlayerData killed, PlayerData killer);
	public boolean rez(PlayerData killed, PlayerData killer,Location loc, PlayerDeathEvent e);
	public boolean rez2(PlayerData killed, PlayerData killer,Location loc, PlayerDeathEvent e, ArrayList<ItemStack> items);
	public void click(PlayerData p, String name, ArrayList<String> lores, int index, Inventory inv);
	
}
