package fr.fitzche.lgmore.RolesLg;

import org.bukkit.ChatColor;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import fr.fitzche.lgmore.PlayerData;
import fr.fitzche.lgmore.Util.PotionUtil;

public class ANCIEN implements RoleInstance{

	public static Camp camp = Camp.Villager;
	public boolean isRes = false;
	public PlayerData playerWithRole;
	public String name = "Ancien";
	
	public ANCIEN(PlayerData player) {
		playerWithRole = player;

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
		return ChatColor.DARK_BLUE+"Vous êtes ancien "+"\n"+ "Vous devez gagner avce le village, vous possédez l'effet résistance permanent, si vous mourrez de la main des loups garou, vous réssuciterez sans votre résistance";
	}

	@Override
	public void giveRoleEffectAndItem(PlayerData player) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void giveEffectAllTime() {
		if (!isRes) {
			playerWithRole.player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 60, 0, false , false));
		}
		
	}

	@Override
	public void giveNightEffectCheck() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void giveNightEffect() {
		// TODO Auto-generated method stub
		
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
