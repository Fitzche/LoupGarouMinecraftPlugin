package fr.fitzche.lgmore.RolesLg;

import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import fr.fitzche.lgmore.Camp;
import fr.fitzche.lgmore.Main;
import fr.fitzche.lgmore.PlayerData;
import fr.fitzche.lgmore.RoleInstance;
import fr.fitzche.lgmore.Lg.GameLg;
import fr.fitzche.lgmore.Util.GameLgUtil;
import fr.fitzche.lgmore.Util.LocationUtil;
import fr.fitzche.lgmore.Util.PotionUtil;
import net.md_5.bungee.api.ChatColor;

public class ALLUMEUR implements RoleInstance {
	public PlayerData playerWithRole;
    HashMap<PlayerData, Integer>  tauxConversion = new HashMap<PlayerData, Integer>();
	public String name ="Allumeur de lampadaire";
	public Camp camp = Camp.Villager;
	public GameLg game;
	public ALLUMEUR(PlayerData player) {
		this.playerWithRole = player;
		this.game = (GameLg) player.game;
		
        for (PlayerData p: ((GameLg) player.game).getPlayerAlive()) {
            this.tauxConversion.put(player, 0);
        }
	}
	
	@Override
	public String getName() {
		return (this.camp.getColor() +name);
	}
	
	public String getDescription() {
		return (Main.info +ChatColor.BLUE+"Vous devez gagner avec les Villageois, pour cela, vous influencer l'aura des personnes autours de vous pour que celle-ci corresponde à leur camp (10min pour rendre une aura correcte)");
	}
	public static ItemStack logo = new ItemStack(Material.TORCH);

	
	public void giveEffectAllTime() {
		for (PlayerData player:game.getPlayerAlive()) {
            if (LocationUtil.getDistanceBetween(player, playerWithRole) < 20) {
            	int x = 0;
            	if (this.tauxConversion.get(player) != null) {
            		x = this.tauxConversion.get(player);
            	}
                this.tauxConversion.put(player, x + 1);
            }
            if (this.tauxConversion.get(player) ==null) {
            	this.tauxConversion.put(player, 0);
            }
            if (this.tauxConversion.get(player) > 600) {
            	if (player.considVill) {
            		player.aura = Aura.LUMINOUS;
            		
            	} else if (player.inLove) {
            		player.aura = Aura.UNKNOW;
            		
            	} else {
            		player.aura = Aura.OBSCUR;
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

	@Override
	public void blind(PlayerData origin) {
		origin.sendMessage(ChatColor.GOLD + "Ce joueur est Allumeur de Lampadaire, ses taux de conversion pour corriger l'aura de son entourage ont donc été remis à 0, à son insu...");
		for (PlayerData p: ((GameLg)origin.game).getPlayerAlive()) {
			this.tauxConversion.put(p, 0);
		}
		
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
