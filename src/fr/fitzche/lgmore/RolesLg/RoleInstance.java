package fr.fitzche.lgmore.RolesLg;

import org.bukkit.inventory.ItemStack;

import fr.fitzche.lgmore.PlayerData;

public interface RoleInstance {
	public static ItemStack logo = null;
	
	
	public String getName();
	
	public String getDescription();
	
	public void giveRoleEffectAndItem(PlayerData player);
	
	public void giveEffectAllTime();
	public void giveNightEffectCheck();
	public void giveNightEffect();
	public void giveDayEffect();
	public void episodeEffect();
	public void changeTo(PlayerData player);
	
	
	public void setEpisodeTrue();
	
	public void startSpecialEvent();
	
	
	
	
}
