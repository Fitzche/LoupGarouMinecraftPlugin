package fr.fitzche.lgmore.RolesLg;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import fr.fitzche.lgmore.PlayerData;
import fr.fitzche.lgmore.Util.PotionUtil;
import net.md_5.bungee.api.ChatColor;

public class SIMPLE_WOLF implements RoleInstance {
	public PlayerData playerWithRole;
	public PotionEffect STRENGTH;
	public static Camp camp = Camp.Wolf;
	public String name ="Loup-Garou Simple";
	
	public SIMPLE_WOLF(PlayerData player) {
		this.playerWithRole = player;
		this.STRENGTH = PotionUtil.STRENGTH;
		
	}
	
	public static ItemStack logo = new ItemStack(Material.DIAMOND_SWORD);

	
	
	@Override
	public String getName() {
		
		return (this.camp.getColor() +name);
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return (ChatColor.DARK_BLUE+"Vous devez gagner avec les Loups Garou (vous possédez la liste de vos alliés loups), pour cela vous obtenez Force I la Nuit");
	}

	@Override
    public void changeTo(PlayerData player) {
        playerWithRole = player;
    }

	@Override
	public void giveRoleEffectAndItem(PlayerData player) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void giveEffectAllTime() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void giveNightEffect() {
		System.out.println("nk.1");
		if (playerWithRole == null) {
			System.out.println("effect can't be gived at null player");
		}
		PotionUtil.giveIncreaseDamage(playerWithRole);
		
		//VOIR SCHEDULER + EFFECT = ERROR ???
		System.out.println("nk.2");
		
	}


	@Override
	public void giveDayEffect() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void startSpecialEvent() {
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
	public void giveNightEffectCheck() {
		if (!(playerWithRole.camp.equals(Camp.Wolf)&& playerWithRole.isShooted)) {
			giveNightEffect();
		}
		
	}

}
