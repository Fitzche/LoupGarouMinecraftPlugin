package fr.fitzche.lgmore.RolesLg;

import java.awt.print.Book;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;

import fr.fitzche.lgmore.PlayerData;
import net.md_5.bungee.api.ChatColor;

public class BIENFAITEUR implements RoleInstance{
	
	public static Camp camp = Camp.Villager;
	public int used = 0;
	public PlayerData playerWithRole;
	public String name = "Bienfaiteur";
	
	public BIENFAITEUR(PlayerData player) {
		this.playerWithRole = player;
	}

	@Override
    public void changeTo(PlayerData player) {
        playerWithRole = player;
    }
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return camp.getColor()+ name;
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return ChatColor.DARK_BLUE+"Vous devez gagner avec le village, pour cela vous pouvez 4 fois conférer 1 coeur à un joueur de votre choix avec la commande /conferer, de plus vous posséder 2 livre protection 2";
	}

	@Override
	public void giveRoleEffectAndItem(PlayerData player) {
		ItemStack book = new ItemStack(Material.ENCHANTED_BOOK, 2);
		EnchantmentStorageMeta meta1 = (EnchantmentStorageMeta) book.getItemMeta();
		meta1.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2, false);
		book.setItemMeta(meta1);
		player.player.getInventory().addItem(new ItemStack(book));
		
	}

	@Override
	public void giveEffectAllTime() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void giveNightEffectCheck() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void giveNightEffect() {
		// TODO Auto-generated method stub
		
	}

	public void conferer(PlayerData ply) {
		if (used > 3) {
			playerWithRole.sendMessage("Vous n'avez plus de coeur à offrir");
			return;

		}
		ply.player.setMaxHealth(ply.player.getMaxHealth()+2);
		ply.sendMessage("Le bienfaiteur vous a conféré un coeur");
		playerWithRole.sendMessage("Vous avez conférer un coeur à "+ ply.Name);
		used++;
	}
	
	@Override
	public void giveDayEffect() {
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
	public void startSpecialEvent() {
		// TODO Auto-generated method stub
		
	}

}
