package fr.fitzche.lgmore.RolesLg;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import fr.fitzche.lgmore.Camp;
import fr.fitzche.lgmore.Main;
import fr.fitzche.lgmore.PlayerData;
import fr.fitzche.lgmore.RoleInstance;
import fr.fitzche.lgmore.Util.WorldUtil;
import net.md_5.bungee.api.ChatColor;

public class PETITE_FILLE implements RoleInstance{

	public Camp camp;
	public boolean powerUsed;
	public PlayerData playerWithRole;
	public String name ="Petite Fille ";

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return this.camp.getColor()+name;
	}

	@Override
    public void changeTo(PlayerData player) {
        playerWithRole = player;
    }
	
	public PETITE_FILLE(PlayerData player) {
		this.camp = Camp.Villager;
		this.playerWithRole = player;
		this.powerUsed = false;
		
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return ChatColor.DARK_BLUE +"Vous devez gagner avec le village, pour cela vous pourvez devenir invisible 5 minutes par nuit en enlevant votre armure"+ "\n"+" vous pouvez également consulter le chat des loups-garou";
	}

	@Override
	public void giveRoleEffectAndItem(PlayerData player) {
	
		
	}

	@Override
	public void giveEffectAllTime() {
		
		
	}

	@Override
	public void giveNightEffect() {
		
		Player p = playerWithRole.player;
		boolean renew = false;
		
		
		if (p.getInventory().getBoots() == null && p.getInventory().getChestplate() == null && p.getInventory().getLeggings() == null && p.getInventory().getHelmet() == null) {
			for (PotionEffect effect:p.getActivePotionEffects()) {
				if (effect.getType().equals(PotionEffectType.INVISIBILITY)) {
					return;
				}
			}
			p.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 1000, 0, false, false));
			
		} else {
			
			p.removePotionEffect(PotionEffectType.INVISIBILITY);
		}
		
	}

	@Override
	public void giveDayEffect() {
		
		
	}

	@Override
	public void episodeEffect() {
		
		
	}

	@Override
	@Deprecated
	public void setEpisodeTrue() {
		powerUsed = false;
		Bukkit.getScheduler().runTaskLater(Main.plug, new BukkitRunnable() {

			@Override
			public void run() {
				System.out.println("TEMP at PETITE FILLE run setEpisodeTrue 1");
				powerUsed = true;
				
			}
			
		}, 12000);
		
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
		origin.sendMessage(ChatColor.GOLD + "Ce joueur est Petite Fille, il ne pourra pas se rendre invisible lors de la prochaine nuit");
		this.playerWithRole.sendMessage(ChatColor.GOLD+"Vous avez été aveuglé, vous ne pourrez pas vous rendre invisible à la prochaine nuit ");
		this.powerUsed = true;
		
	}

	@Override
	public boolean isInfoRole() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void command(CommandSender sender, Command cmd, String msg, String[] args) {
		// TODO Auto-generated method stub
		
	}

}
