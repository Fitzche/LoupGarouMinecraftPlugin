package fr.fitzche.lgmore.Minage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map.Entry;

import org.bukkit.inventory.ItemStack;

import fr.fitzche.lgmore.Game;
import fr.fitzche.lgmore.PlayerData;
import fr.fitzche.lgmore.Util.ItemUtil;
import fr.fitzche.lgmore.scoreboard.Inventory.InvFunct;
import fr.fitzche.lgmore.scoreboard.Inventory.StringChooseInv;


public class TradeInv implements InvFunct {
	
	public ArrayList<ItemStack> items = new ArrayList<ItemStack>();
	public TradeInv(HashMap<ItemStack, ItemStack> articles, PlayerData p) {
		
		for (Entry<ItemStack, ItemStack> entry:articles.entrySet()) {
			int x = entry.getKey().getAmount();
			ItemUtil.setLore(entry.getValue(), new ArrayList<>(Arrays.asList(x + " "+entry.getKey().getItemMeta().getDisplayName())) );
			items.add(entry.getValue());
		}
		StringChooseInv choose = new StringChooseInv(p, null, null, this);
	}
	
	

	@Override
	public void click(PlayerData p, Game game, String clickedName, ArrayList<String> lores) {
		for (ItemStack item: items) {
			if (item.getItemMeta().getDisplayName().equals(clickedName)) {
				
				
				//TRADE
			}
		}

	}

}
