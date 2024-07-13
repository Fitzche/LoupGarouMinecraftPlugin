package fr.fitzche.lgmore.RolesLg;

import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import fr.fitzche.lgmore.PlayerData;
import fr.fitzche.lgmore.Util.GameLgUtil;
import fr.fitzche.lgmore.Util.LocationUtil;
import fr.fitzche.lgmore.Util.PotionUtil;
import net.md_5.bungee.api.ChatColor;

public class ALLUMEUR implements RoleInstance {
	public PlayerData playerWithRole;
    HashMap<PlayerData, Integer>  tauxConversion = new HashMap<PlayerData, Integer>();
	public String name ="Allumeur de lampadaire";
	public Camp camp = Camp.Villager;
	public ALLUMEUR(PlayerData player) {
		this.playerWithRole = player;
        for (PlayerData p: GameLgUtil.getGameOfPlayer(player, "at Allumeur creating").getPlayerAlive()) {
            this.tauxConversion.put(player, 0);
        }
	}
	
	@Override
	public String getName() {
		return (this.camp.getColor() +name);
	}
	
	public String getDescription() {
		return (ChatColor.DARK_BLUE+"Vous devez gagner avec les Villageois, pour cela, vous influencer l'aura des personnes autours de vous pour que celle-ci corresponde à leur camp (10min pour rendre une aura correct)");
	}
	public static ItemStack logo = new ItemStack(Material.TORCH);

	
	public void giveEffectAllTime() {
		for (PlayerData player:GameLgUtil.getGameOfPlayer(playerWithRole, "at Allumeur checking").getPlayerAlive()) {
            if (LocationUtil.getDistanceBetween(player, playerWithRole) < 20) {
                this.tauxConversion.put(player, this.tauxConversion.get(player) + 1);
            }
            if (this.tauxConversion.get(player) > 99) {
                switch (player.camp) {
                    case Love:
                        player.aura = Aura.DANGEROUS;
                    case Other:
                        player.aura = Aura.OBSCUR;
                    case TEAM:
                        player.aura = Aura.OBSCUR;
                    case Villager:
                        player.aura = Aura.LUMINOUS;
                    case Wolf:
                        player.aura = Aura.OBSCUR;
                    default:
                        break;
                    
                }
                this.tauxConversion.put(player, 0);
            }
        }
	}
	

	@Override
    public void changeTo(PlayerData player) {
        playerWithRole = player;
    }
	public void giveRoleEffectAndItem(PlayerData player) {
		
	}
	
	
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void giveNightEffectCheck() {
		if (!(playerWithRole.camp.equals(Camp.Wolf)&& playerWithRole.isShooted)) {
			giveNightEffect();
		}
		
	}
}
