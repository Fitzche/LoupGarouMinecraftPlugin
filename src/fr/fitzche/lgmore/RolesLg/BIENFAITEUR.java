package fr.fitzche.lgmore.RolesLg;

import java.awt.print.Book;
import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import fr.fitzche.lgmore.Camp;
import fr.fitzche.lgmore.Main;
import fr.fitzche.lgmore.PlayerData;
import fr.fitzche.lgmore.RoleInstance;
import net.md_5.bungee.api.ChatColor;

public class BIENFAITEUR implements RoleInstance{
	
	public static Camp camp = Camp.Villager;
	public int used = 0;
	public PlayerData playerWithRole;
	public String name = "Bienfaiteur";
	public HashMap<String, Boolean> hasGived = new HashMap<String, Boolean>();
	
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
		return ChatColor.DARK_BLUE+"Vous devez gagner avec le village, pour cela vous pouvez 4 fois conférer 1 coeur à un joueur de votre choix avec la commande /lg conferer [nomDuJoueur], de plus vous posséder 2 livre protection 2";
	}

	@Override
	public void giveRoleEffectAndItem(PlayerData player) {
		ItemStack book = new ItemStack(Material.ENCHANTED_BOOK, 1);
		ItemStack book2 = new ItemStack(Material.ENCHANTED_BOOK, 1);
		
		EnchantmentStorageMeta meta1 = (EnchantmentStorageMeta) book.getItemMeta();
		meta1.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2, true);
		
		EnchantmentStorageMeta meta2 = meta1.clone();
		
		
		
		book.setItemMeta(meta1);
		book2.setItemMeta(meta2);
		
		player.player.getInventory().addItem(new ItemStack(book));
		player.player.getInventory().addItem(new ItemStack(book2));
	}

	@Override
	public void giveEffectAllTime() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void giveNightEffectCheck() {
		giveNightEffect();
		
	}

	@Override
	public void giveNightEffect() {
		
		
	}

	public void conferer(PlayerData ply) {
		if (used > 3 || this.hasGived.getOrDefault(ply.getName(), false)) {
			playerWithRole.sendMessage("Vous n'avez plus de coeur à offrir ou vous en avez déjà offert à ce joueur");
			return;

		}
		ply.setMaxHealth(ply.getMaxHealth()+2);
		ply.sendMessage("Le bienfaiteur vous a conféré un coeur");
		playerWithRole.sendMessage("Vous avez conférer un coeur à "+ ply.Name);
		this.hasGived.put(ply.getName(), true);
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
