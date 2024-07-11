package fr.fitzche.lgmore.RolesLg;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import fr.fitzche.lgmore.PlayerData;
import fr.fitzche.lgmore.Util.PotionUtil;
import net.md_5.bungee.api.ChatColor;

public class VOYANTE implements RoleInstance{
	public PlayerData playerWithRole;
	public boolean powerUsed;
	public String name ="Voyante";

	public VOYANTE(PlayerData player) {
		this.playerWithRole = player;
		this.powerUsed = false;
		
	}
	
	
	
	@Override
	public String getName() {
		return (this.camp.getColor() + name);
	}

	@Override
    public void changeTo(PlayerData player) {
        playerWithRole = player;
    }
	
	
	
	public String getDescription() {
		return (ChatColor.DARK_BLUE+"Vous devez gagner avec les Villageois, pour cela vous pouvez connaitre un role de joueur par épisode, votre écoperez cependant d'un malus si vous faites erreur et que vous espionnez un villageois");
	}
	
	public void giveEffectAllTime() {
		//null
	}
	
	public void giveRoleEffectAndItem(PlayerData player) {
		
	}
	public static ItemStack logo = new ItemStack(Material.ENCHANTMENT_TABLE);
	public static Camp camp = Camp.Villager;
	@Override
	public void giveNightEffect() {
		if (this.playerWithRole.infected) {
			System.out.println("nk.1");
			if (playerWithRole == null) {
				System.out.println("effect can't be gived at null player");
			}
			PotionUtil.giveIncreaseDamage(playerWithRole);
			
			//VOIR SCHEDULER + EFFECT = ERROR ???
			System.out.println("nk.2");
		}
		
		
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
		this.powerUsed = false;
		this.playerWithRole.player.sendMessage(ChatColor.AQUA +"Vous pouvez à nouveau utiliser votre pouvoir");
		
	}



	@Override
	public void giveNightEffectCheck() {
		if (!(playerWithRole.camp.equals(Camp.Wolf)&& playerWithRole.isShooted)) {
			giveNightEffect();
		}
		
	}
}
