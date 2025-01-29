package fr.fitzche.lgmore.RolesLg;

import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

import fr.fitzche.lgmore.Camp;
import fr.fitzche.lgmore.PlayerData;
import fr.fitzche.lgmore.RoleInstance;
import fr.fitzche.lgmore.Util.GameLgUtil;
import fr.fitzche.lgmore.Util.PotionUtil;
import net.md_5.bungee.api.ChatColor;

public class SALVATEUR implements RoleInstance{

	public static Camp camp = Camp.Villager;
	public PlayerData playerWithRole;
	public boolean powerUsed = false;
	public String name ="Salvateur";
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return camp.getColor()+ name;
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return ChatColor.DARK_BLUE+ "Vous devez gagner avec le village, pour ce faire vous posséder 2 potion de instant heal, et, à chaque épisode vous pourrez protéger un joueur pendant 20 minutes avec la commande /lg proteger [nomDuJoueur], celui-ci obtiendra resistance I";
	}

	@Override
    public void changeTo(PlayerData player) {
        playerWithRole = player;
    }

	@Override
	public void giveRoleEffectAndItem(PlayerData player) {
		Potion potion = new Potion(PotionType.INSTANT_HEAL);
		ItemStack popo = potion.toItemStack(2);
		playerWithRole.player.getInventory().addItem(popo);
		
		
	}
	
	public SALVATEUR(PlayerData player) {
		this.playerWithRole = player;
	}

	@Override
	public void giveEffectAllTime() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void giveNightEffectCheck() {
		if (!(playerWithRole.camp.equals(Camp.Wolf)&& playerWithRole.isShooted)) {
			giveNightEffect();
		}
		
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void episodeEffect() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setEpisodeTrue() {
		this.powerUsed = false;
		
	}

	
	public void proteger(PlayerData target) {
		target.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 24000, 0, false, false));
		target.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 24000, 0, false, false));
		target.sendMessage(ChatColor.DARK_GREEN+ "Le Salvateur vous a protégé, vous obtenez donc résistance et no fall pendant 20 minutes");

		this.playerWithRole.sendMessage(ChatColor.DARK_GREEN+"vous avez protégé "+ target.Name);
		this.powerUsed = true;
		
		if (this.playerWithRole.getName().equals(target.Name)) {
			GameLgUtil.getGameOfPlayer(playerWithRole, " at salvateur protection").addEpic(10, playerWithRole.getLocation());
		}
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

}
