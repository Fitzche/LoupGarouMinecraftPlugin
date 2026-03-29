package fr.fitzche.lgmore.CharactUHC;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import fr.fitzche.lgmore.PlayerData;
import fr.fitzche.lgmore.minecraft.GameListener;

public class CharactListener implements GameListener {

	
	private CharactUHC game;

	public CharactListener(CharactUHC game) {
		this.game = game;
	}
	
	@Override
	public double damagePbyP(PlayerData damager, PlayerData damaged, double damage, boolean isArrow) {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public double damagePbyEntity(PlayerData damaged, Entity damager, double damage) {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public double kill(PlayerData killed, PlayerData killer) {
		// TODO Auto-generated method stub
		return 0;
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
		
		
		
		for (SpecialCharactItem item:this.game.items) {
			if (lores.size() > 0 && (ChatColor.RESET +lores.get(0)).equals(ChatColor.RESET + item.id)) {
				item.run(p);
			}
		}

	}

}
