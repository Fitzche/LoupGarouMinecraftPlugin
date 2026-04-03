package fr.fitzche.lgmore.scoreboard.Inventory;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import fr.fitzche.lgmore.Game;
import fr.fitzche.lgmore.Main;
import fr.fitzche.lgmore.PlayerData;
import fr.fitzche.lgmore.Lg.GameLg;
import fr.fitzche.lgmore.Lg.GameType;
import fr.fitzche.lgmore.Util.CommandUtil;
import fr.fitzche.lgmore.Util.ItemUtil;
import fr.fitzche.lgmore.Util.MathUtil;
import fr.fitzche.lgmore.commands.SpecialItemHolder;

public class SpecialItemChoser implements InvFunct {

	ArrayList<ItemStack> items = new ArrayList<ItemStack>();
	public SpecialItemChoser(PlayerData p) {
		
		if (p.specialItemsOwned != null && p.specialItemsOwned.size() > 0) {
			for (String item:p.specialItemsOwned) {
				items.add(ItemUtil.addAppaEnchant(ItemUtil.getItem(Material.PAPER, 1, item, new ArrayList<String>())));
			}
		}
		
		
	}
	
	public void open(PlayerData p, Inventory backInv) {
		StringChooseInv inv = new StringChooseInv(p, items, backInv, this);
	}
	@Override
	public void click(PlayerData p, Game game, String clickedName, ArrayList<String> lores, ItemStack returnedItem) {
		p.player.closeInventory();
		SpecialItemHolder.giveItem(p.player, clickedName);
		for (int i = 0; i < p.specialItemsOwned.size(); i++) {
			if (p.specialItemsOwned.get(i) == clickedName) {
				p.specialItemsOwned.remove(i);
				i = p.specialItemsOwned.size();
			}
		}
		

	}

}
