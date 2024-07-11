package fr.fitzche.lgmore.RolesLg;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import fr.fitzche.lgmore.GameLg;
import fr.fitzche.lgmore.Main;
import fr.fitzche.lgmore.PlayerData;
import fr.fitzche.lgmore.Util.GameLgUtil;
import fr.fitzche.lgmore.Util.LocationUtil;
import fr.fitzche.lgmore.Util.MathUtil;
import fr.fitzche.lgmore.Util.PotionUtil;
import fr.fitzche.lgmore.Util.RoleUtilLg;
import net.md_5.bungee.api.ChatColor;

public class DISCIPLE implements RoleInstance {

    public boolean firstUnlock = false;
    public boolean secondUnlock = false;
    public boolean thirdUnlock = false;

    public boolean powerUsed = false;

    public Location sageLocation;

    public int time = 0;
    public String name ="Disciple";

    public PlayerData sage;
	public PlayerData playerWithRole;
	public DISCIPLE(PlayerData player) {
		this.playerWithRole = player;
	}

	@Override
    public void changeTo(PlayerData player) {
        playerWithRole = player;
    }
	
	@Override
	public String getName() {
		return (this.camp.getColor() + name);
	}
	
	public String getDescription() {
		if (sage != null) {
			playerWithRole.sendMessage(ChatColor.GOLD + "Le vieux sage est "+ sage.Name);
		}
		return ("Vous êtes Disciple, vous devez gagner avec le village, pour cela vous connaissez le role du vieux sage, au bout de 20min avec lui, il obtiendra votre role, au bout de 30min, vous aurez accès à la commande /lg aura 2 fois, au bout de 35min vous obtiendrez speed permanent, vous pouvez retrouver le vieux sage avec la commande /lg trouver²²");
	}
	public static ItemStack logo = new ItemStack(Material.WHEAT);

	
	public void giveEffectAllTime() {
        if (thirdUnlock) {
            playerWithRole.player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 40, 0, false, false));
        } else if (LocationUtil.getDistanceBetween(playerWithRole, sage) < 30) {
            time += 100;
            if (time == 1200) {
                sage.sendMessage(ChatColor.GOLD+"Votre Disciple est "+ playerWithRole.Name);
				playerWithRole.sendMessage("Le vieux sage a reçu votre nom");
                firstUnlock = true;
            } else if (time == 2400) {
				playerWithRole.sendMessage("Vous pouvez utiliser la commande /lg aura");
                secondUnlock = true;
            } else if (time == 3600) {
				playerWithRole.sendMessage("Vous obtenez speed de manière permanente");
                thirdUnlock = true;
            }
        }

        if (sage.inLife) {
            this.sageLocation = sage.player.getLocation();
        }
        

		
	}
	
    @Deprecated
	public void giveRoleEffectAndItem(PlayerData player) {
		Bukkit.getScheduler().runTaskLater(Main.plug, new BukkitRunnable() {

            @Override
            public void run() {
                GameLg game = GameLgUtil.getGameOfPlayer(playerWithRole, "at giveRoleAndEffect of Disciple");
                ArrayList<PlayerData> sages = RoleUtilLg.getPlayersWithRole(game, RolesLg.SAGE);
                if (sages.size() != 0) {
                    sage = sages.get(MathUtil.generateAlInt(0, sages.size() -1));
                    playerWithRole.sendMessage(ChatColor.ITALIC + "Le Vieux sage est "+ sage.Name);
                } 
            }

        }, 200);
	}
	
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void giveNightEffectCheck() {
		if (!(playerWithRole.camp.equals(Camp.Wolf)&& playerWithRole.isShooted)) {
			giveNightEffect();
		}
		
	}
}
