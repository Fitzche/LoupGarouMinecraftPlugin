package fr.fitzche.lgmore.Minage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import fr.fitzche.lgmore.Util.ItemUtil;

public class Trades {

	
	public static final HashMap<ItemStack, ArrayList<ItemStack>> basicMap = new HashMap<ItemStack, ArrayList<ItemStack>>();
	public static final HashMap<ItemStack, ArrayList<ItemStack>> DiamondMap = new HashMap<ItemStack, ArrayList<ItemStack>>();
	public static final HashMap<ItemStack, ArrayList<ItemStack>> EmerMap = new HashMap<ItemStack, ArrayList<ItemStack>>();
	public static final HashMap<ItemStack, ArrayList<ItemStack>> UpMap = new HashMap<ItemStack, ArrayList<ItemStack>>();

	public static void initialize() {
		
		
		ItemStack sevenDivinGold = ItemUtil.getItem(new ItemStack(Material.GOLD_INGOT, 7), "Or Divin", new ArrayList<String>());
		basicMap.put(new ItemStack(Material.IRON_SWORD), new ArrayList<ItemStack>(Arrays.asList(
				sevenDivinGold
				)));
		
		ItemStack basicIronSword = new ItemStack(Material.IRON_SWORD);
		ItemStack twoDiamond = new ItemStack(Material.DIAMOND, 2);
		basicMap.put(new ItemStack(Material.DIAMOND_SWORD), new ArrayList<ItemStack>(Arrays.asList(
				basicIronSword, 
				twoDiamond
				)));
		
		ItemStack legendarySword = ItemUtil.getItem(Material.DIAMOND_SWORD, 1, "Epée mythique", new ArrayList<String>());
		ItemUtil.addAppaEnchant(legendarySword );
		
		ItemStack oneLapisBlock = new ItemStack(Material.LAPIS_BLOCK);
		
		basicMap.put(legendarySword, new ArrayList<ItemStack>(Arrays.asList(
				basicIronSword, 
				twoDiamond,
				oneLapisBlock
				)));
		
	}
}
