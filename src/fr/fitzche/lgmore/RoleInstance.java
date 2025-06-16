package fr.fitzche.lgmore;


import java.io.Serializable;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import fr.fitzche.lgmore.RolesLg.RolesLg;
import fr.fitzche.lgmore.RolesLg.VOYANTE;
import fr.fitzche.lgmore.Util.PlayerUtil;
import fr.fitzche.lgmore.commands.FutureAction;

public interface RoleInstance extends Serializable{
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
	public void blind(PlayerData origin);
	public boolean isInfoRole();
	public void command(CommandSender sender, Command cmd, String msg, String[] args);
	
	
	
	
	
	
}
