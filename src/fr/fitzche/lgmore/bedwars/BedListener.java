package fr.fitzche.lgmore.bedwars;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import fr.fitzche.lgmore.Main;
import fr.fitzche.lgmore.PlayerData;
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
		return true;
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

}
