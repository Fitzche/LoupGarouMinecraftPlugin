package fr.fitzche.lgmore.RolesLg;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import fr.fitzche.lgmore.Camp;
import fr.fitzche.lgmore.PlayerData;
import fr.fitzche.lgmore.RoleInstance;


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
		
		
		return ChatColor.DARK_BLUE + "Vous êtes Chasseur, vous devez gagner avec le village, pour cela vous posseder un arc power IV ainsi que 64 flèche, de plus à votre mort vous pourrez tirer sur un joueur de votre choix avec la commande /lg tirer [nomDuJoueur], si celui-ci perdra 3 coeurs non permanents et s'il est un loup il perdra sa force de nuit, vous posséder également 30% de force contre les loups";
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
		if (this.playerWithRole.infected) {
			if (!(playerWithRole.camp.equals(Camp.Wolf)&& playerWithRole.isShooted)) {
				playerWithRole.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 79, 0, false, false));

			}
		}
		
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
		
		target.isShooted = true;
		Bukkit.broadcastMessage("Le chasseur a tiré sur " + target.getName());
		target.player.damage(6);
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

	@Override
	public void blind(PlayerData origin) {
		origin.sendMessage(ChatColor.GREEN + "Ce joueur n'est pas un rôle à info");
		
	}

	@Override
	public boolean isInfoRole() {
		// TODO Auto-generated method stub
		return false;
	}

}
