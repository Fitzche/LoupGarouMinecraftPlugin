package fr.fitzche.lgmore.RolesLg;

import org.bukkit.Bukkit;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import fr.fitzche.lgmore.Main;
import fr.fitzche.lgmore.PlayerData;
import fr.fitzche.lgmore.Util.PotionUtil;
import net.md_5.bungee.api.ChatColor;

public class PERFIDE implements RoleInstance{

	public Camp camp = Camp.Wolf;
	public boolean powerUsed;
	public PlayerData playerWithRole;
	public String name ="Loup Garou Perfide";

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return this.camp.getColor()+name;
	}

	@Override
    public void changeTo(PlayerData player) {
        playerWithRole = player;
    }
	
	public PERFIDE(PlayerData player) {
		
		this.playerWithRole = player;
		this.powerUsed = false;
		
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return ChatColor.DARK_BLUE+
				" Vous devez gagner avec les loups, pour cela vous pourvez devenir invisible 5 minutes par nuit en enlevant votre armure"+ "\n"+" vous possédez également force ";
	}

	@Override
	public void giveRoleEffectAndItem(PlayerData player) {
		
		
	}

	@Override
	public void giveEffectAllTime() {
		
		
	}

	@Override
	public void giveNightEffect() {
		System.out.println("nk.1");
		if (playerWithRole == null) {
			System.out.println("effect can't be gived at null player");
		}
		PotionUtil.giveIncreaseDamage(playerWithRole);
		
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
				System.out.println("TEMP at PERFIDE run setEpisodeTrue 1");
				powerUsed = true;
				
			}
			
		}, 12000);
		
	}

	@Override
	public void startSpecialEvent() {
		playerWithRole.player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 6000, 0, false, false));
		powerUsed = false;
		System.out.println("//temps at PETITE FILLE startSpecialEvent 2");
		
	}

	@Override
	public void giveNightEffectCheck() {
		if (!(playerWithRole.camp.equals(Camp.Wolf)&& playerWithRole.isShooted)) {
			giveNightEffect();
		}
		
	}

}
