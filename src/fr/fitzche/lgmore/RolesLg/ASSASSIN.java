package fr.fitzche.lgmore.RolesLg;

import java.awt.print.Book;
import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import fr.fitzche.lgmore.Camp;
import fr.fitzche.lgmore.Main;
import fr.fitzche.lgmore.PlayerData;
import fr.fitzche.lgmore.RoleInstance;
import fr.fitzche.lgmore.Lg.GameLg;

import fr.fitzche.lgmore.Util.GameLgUtil;
import fr.fitzche.lgmore.Util.PotionUtil;
import net.md_5.bungee.api.ChatColor;


public class ASSASSIN implements RoleInstance {
	public PlayerData playerWithRole;
	public String name ="Assassin";
	public Camp camp = Camp.Other;
	public ASSASSIN(PlayerData player) {
		this.playerWithRole = player;
		ArrayList<PlayerData> players = new ArrayList<PlayerData>();
		players.add(player);
		GameLg game = (GameLg) player.game;
		
	}
	
	@Override
	public String getName() {
		return (this.camp.getColor() +name);
	}
	
	public String getDescription() {
		return (ChatColor.DARK_BLUE+"Vous êtes ASSASSIN !!! (Royal) Vous devez gagner tout seul, vous possédez les livre efficency III, sharpness III, et protection III, vous pouvez également fabriquer une épée tranchant IV, et vous possédez force le jour");
	}
	public static ItemStack logo = new ItemStack(Material.GOLD_SWORD);

	
	public void giveEffectAllTime() {
		//null
	}
	

	@Override
    public void changeTo(PlayerData player) {
        playerWithRole = player;
    }
	public void giveRoleEffectAndItem(PlayerData player) {
		ItemStack book1 = new ItemStack(Material.ENCHANTED_BOOK, 1);
		EnchantmentStorageMeta meta1 = (EnchantmentStorageMeta) book1.getItemMeta();
		meta1.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3, false);
		book1.setItemMeta(meta1);
		player.player.getInventory().addItem(new ItemStack(book1));
		
		ItemStack book2 = new ItemStack(Material.ENCHANTED_BOOK, 1);
		EnchantmentStorageMeta meta2 = (EnchantmentStorageMeta) book2.getItemMeta();
		meta2.addEnchant(Enchantment.DAMAGE_ALL, 3, false);
		book2.setItemMeta(meta2);
		player.player.getInventory().addItem(new ItemStack(book2));
		
		ItemStack book3 = new ItemStack(Material.ENCHANTED_BOOK, 1);
		EnchantmentStorageMeta meta3 = (EnchantmentStorageMeta) book3.getItemMeta();
		meta3.addEnchant(Enchantment.DIG_SPEED, 3, false);
		book3.setItemMeta(meta3);
		player.player.getInventory().addItem(new ItemStack(book3));
	}
	
	
	
	
	@Override
	public void giveNightEffect() {
		
		
		
	}

	@Override
	public void giveDayEffect() {
		playerWithRole.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 60, 0, false, false));
		
	}

	@Override
	public void startSpecialEvent() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void episodeEffect() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setEpisodeTrue() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void giveNightEffectCheck() {
		if (!(playerWithRole.camp.equals(Camp.Wolf)&& playerWithRole.isShooted)) {
			giveNightEffect();
		}
		
	}

	@Override
	public void blind(PlayerData origin) {
		origin.sendMessage(ChatColor.GREEN + "Ce joueur n'est pas un rôle à info");
		
	}

	@Override
	public boolean isInfoRole() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void command(CommandSender sender, Command cmd, String msg, String[] args) {
		// TODO Auto-generated method stub
		
	}
}
