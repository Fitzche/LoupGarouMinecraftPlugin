package fr.fitzche.lgmore.Minage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;

import fr.fitzche.lgmore.Game;
import fr.fitzche.lgmore.Main;
import fr.fitzche.lgmore.PlayerData;
import fr.fitzche.lgmore.Util.ItemUtil;
import fr.fitzche.lgmore.scoreboard.Inventory.InvFunct;
import fr.fitzche.lgmore.scoreboard.Inventory.StringChooseInv;
import net.md_5.bungee.api.ChatColor;


public class TradeInv implements InvFunct {
	
	public ArrayList<ItemStack> items = new ArrayList<ItemStack>();
	public ArrayList<ItemStack> basesItems = new ArrayList<ItemStack>();
	public ArrayList<ArrayList<ItemStack>> prices = new ArrayList<ArrayList<ItemStack>>();
	
	public final static FixedMetadataValue moneyMetaExample = new FixedMetadataValue(Main.plug, "exampleMoney");
	
	
	public TradeInv(HashMap<ItemStack, ArrayList<ItemStack>> articles, PlayerData p) {
		
		
		HashMap<ItemStack, ArrayList<ItemStack>> articlesC = new HashMap<ItemStack, ArrayList<ItemStack>>(articles);
		for (Entry<ItemStack, ArrayList<ItemStack>> entry:articlesC.entrySet()) {
			basesItems.add(new ItemStack(entry.getKey()));
			ArrayList<String> lores = new ArrayList<String>();
			for (ItemStack value:entry.getValue()) {
				int x = value.getAmount();
				
				String name = value.getType().name().toLowerCase();
				if (value.hasItemMeta() && value.getItemMeta().hasDisplayName()) {
					name = value.getItemMeta().getDisplayName();
				}
				
				lores.add(ChatColor.DARK_RED +"-" + x + " " + ChatColor.GOLD+name);
				
			
				
				
			}
			ItemUtil.setLore(entry.getKey(), lores);
			items.add(entry.getKey());
			prices.add(entry.getValue());
		
		}
		StringChooseInv choose = new StringChooseInv(p, new ArrayList<ItemStack>(items), null, this);
		System.out.println("choose is "+ items.size());
	}
	
	

	@Override
	public void click(PlayerData p, Game game, String clickedName, ArrayList<String> lores, ItemStack returnedItem) {
		System.out.println("initialize for "+ items.size());
		for (ItemStack item: items) {
			System.out.println("test for "+ item.getType().name());
			if (item.getType().equals(returnedItem.getType()) && item.getItemMeta().equals(returnedItem.getItemMeta())) {
				System.out.println("correspond for "+ item.getType().name());
				
				ArrayList<ItemStack> moneys = prices.get(items.indexOf(item));
				
				
				for (ItemStack money:moneys) {
					
					int neccess = money.getAmount();
					Material mat = money.getType();
					int have = 0;
					
					
					if (money.hasItemMeta() && money.getItemMeta().hasDisplayName() ) {
						have = ItemUtil.howManyOf(p.player.getInventory(), mat, money.getItemMeta().getDisplayName());
						if (have >= neccess) {
							
						} else {
							p.sendMessage("Vous n'avez pas les matériaux neccessaires pour acheter cet objet");
							return;
						}
					} else {
						have = ItemUtil.howManyOf(p.player.getInventory(), mat);
						if (have >= neccess) {

						} else {
							p.sendMessage("Vous n'avez pas les matériaux neccessaires pour acheter cet objet");
							return;
						}
					}
					
					
					if (money.hasItemMeta() && money.getItemMeta().hasDisplayName() ) {
						have = ItemUtil.howManyOf(p.player.getInventory(), mat, money.getItemMeta().getDisplayName());
						if (have >= neccess) {
							ItemUtil.takeInInv(p.player.getInventory(), mat, money.getItemMeta().getDisplayName(), neccess);
						} else {
							p.sendMessage("Vous n'avez pas les matériaux neccessaires pour acheter cet objet");
							return;
						}
					} else {
						have = ItemUtil.howManyOf(p.player.getInventory(), mat);
						if (have >= neccess) {
							ItemUtil.takeInInv(p.player.getInventory(), mat, neccess);

						} else {
							p.sendMessage("Vous n'avez pas les matériaux neccessaires pour acheter cet objet");
							return;
						}
					}
				}
				
				
				
				
				
				p.player.getInventory().addItem(basesItems.get(items.indexOf(item)));
				
				
				
				
			} else {
				System.out.println("don't correspond for "+ item.getType().name() + ": "+returnedItem.getType().name());
			}
			
			
		}

	}

}
