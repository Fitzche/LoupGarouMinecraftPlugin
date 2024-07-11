package fr.fitzche.lgmore.RolesLg;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.fitzche.lgmore.PlayerData;
import net.minecraft.server.v1_8_R1.Item;

public class CHASSEUR implements RoleInstance{

	public PlayerData playerWithRole;
	public static Camp camp = Camp.Villager;
	public String name ="Chasseur";

	
	
	
	@Override
	public String getName() {
		
		return this.camp.getColor()+name;
	}

	@Override
    public void changeTo(PlayerData player) {
        playerWithRole = player;
    }

	@Override
	public String getDescription() {
		
		
		return ChatColor.DARK_BLUE + "Vous êtes Chasseur, vous devez gagner avec le village, pour cela vous posseder un arc power IV ainsi que 64 flèche, de plus à votre mort vous pourrez tirer sur un joueur de votre choix avec la commande /lg tirer nomDuJoueur, si celui-ci est un loup il perdra 3 coeurs ainsi que sa force de nuit, vous posséder également 30% de force contre les loups";
	}
	
	public CHASSEUR(PlayerData player) {
		this.playerWithRole = player;
		
	}

	@Override
	public void giveRoleEffectAndItem(PlayerData player) {
		ItemStack bow = new ItemStack(Material.BOW);
		ItemMeta bowMeta = bow.getItemMeta();
		bowMeta.addEnchant(Enchantment.ARROW_DAMAGE, 4, true);
		bow.setItemMeta(bowMeta);
		playerWithRole.player.getInventory().addItem(bow);
		
		playerWithRole.player.getInventory().addItem(new ItemStack(Material.ARROW, 64));
		
	}
	
	

	@Override
	public void giveEffectAllTime() {
		
		
	}

	@Override
	public void giveNightEffect() {
		
		
	}

	@Override
	public void giveDayEffect() {
		
		
	}

	@Override
	public void episodeEffect() {
		
		
	}

	@Override
	public void setEpisodeTrue() {
		
		
	}
	
	
	public void shoot(PlayerData target) {
		target.player.damage(10);
		target.isShooted = true;
		Bukkit.broadcastMessage("Le chasseur a tiré sur " + target.player.getName());
	}

	@Override
	public void startSpecialEvent() {
		
		
	}

	@Override
	public void giveNightEffectCheck() {
		if (!(playerWithRole.camp.equals(Camp.Wolf)&& playerWithRole.isShooted)) {
			giveNightEffect();
		}
		
	}

}
