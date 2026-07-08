package fr.fitzche.lgmore.Minage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import fr.fitzche.lgmore.Util.ItemUtil;

public class Trades {

	
	public static final HashMap<ItemStack, ArrayList<ItemStack>> basicMap = new HashMap<ItemStack, ArrayList<ItemStack>>();
	public static final HashMap<ItemStack, ArrayList<ItemStack>> DiamondMap = new HashMap<ItemStack, ArrayList<ItemStack>>();
	public static final HashMap<ItemStack, ArrayList<ItemStack>> EmerMap = new HashMap<ItemStack, ArrayList<ItemStack>>();
	public static final HashMap<ItemStack, ArrayList<ItemStack>> UpMap = new HashMap<ItemStack, ArrayList<ItemStack>>();

	public static void initialize() {
		
		
		ItemStack sevenDivinGold = ItemUtil.getItem(new ItemStack(Material.GOLD_INGOT, 7), "Or Divin", new ArrayList<String>());
		ItemStack tenDivinGold = ItemUtil.getItem(new ItemStack(Material.GOLD_INGOT, 10), "Or Divin", new ArrayList<String>());
		ItemStack twoDivinGold = ItemUtil.getItem(new ItemStack(Material.GOLD_INGOT, 10), "Or Divin", new ArrayList<String>());
		ItemStack oneLapisBlock = new ItemStack(Material.LAPIS_BLOCK);
		ItemStack basicStoneSword = new ItemStack(Material.STONE_SWORD);
		ItemStack twoDiamond = new ItemStack(Material.DIAMOND, 2);
		
		
		ItemStack sevenSteel = ItemUtil.getItem(new ItemStack(Material.IRON_INGOT, 7), "Acier", new ArrayList<String>());
		ItemStack fourSteel = ItemUtil.getItem(new ItemStack(Material.IRON_INGOT, 4), "Acier", new ArrayList<String>());
		
		
		
		basicMap.put(ItemUtil.hideAttributes(new ItemStack(Material.STONE_SWORD)), new ArrayList<ItemStack>(Arrays.asList(
				sevenDivinGold
				)));
		
		
		
		basicMap.put(ItemUtil.hideAttributes(new ItemStack(Material.IRON_SWORD)), new ArrayList<ItemStack>(Arrays.asList(
				ItemUtil.hideAttributes(new ItemStack(Material.STONE_SWORD)), 
				twoDiamond
				)));
		
		ItemStack legendarySword = ItemUtil.getItem(Material.IRON_SWORD, 1, "Epée mythique", new ArrayList<String>());
		legendarySword.addEnchantment(Enchantment.DAMAGE_ALL, 2);
		ItemUtil.addAppaEnchant(legendarySword );
		
		
		
		basicMap.put(ItemUtil.hideAttributes(legendarySword), new ArrayList<ItemStack>(Arrays.asList(
				ItemUtil.hideAttributes(new ItemStack(Material.IRON_SWORD)), 
				tenDivinGold,
				oneLapisBlock
				)));
		
		
		
		
		basicMap.put(ItemUtil.hideAttributes(new ItemStack(Material.GOLDEN_APPLE)), new ArrayList<ItemStack>(Arrays.asList(
				
				twoDivinGold
				)));
		
		basicMap.put(ItemUtil.hideAttributes(new ItemStack(Material.STONE, 64)), new ArrayList<ItemStack>(Arrays.asList(
				
				fourSteel
				)));
		
	}
}
