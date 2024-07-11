package fr.fitzche.lgmore.RolesLg;

import java.util.ArrayList;

import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import fr.fitzche.lgmore.PlayerData;
import fr.fitzche.lgmore.Util.GameLgUtil;
import fr.fitzche.lgmore.Util.LocationUtil;
import fr.fitzche.lgmore.Util.RoleUtilLg;
import net.md_5.bungee.api.ChatColor;

public class SOEUR implements RoleInstance {
	
	public ArrayList<PlayerData> sisters = new ArrayList<PlayerData>();
	public static Camp camp = Camp.Villager;
	public PlayerData playerWithRole;
	public String name ="Soeur";
	@Override
	public String getName() {
		return camp.getColor()+name;
	}
	
	public SOEUR(PlayerData player, ArrayList<PlayerData> sisters) {
		playerWithRole = player;
		sisters = sisters;
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return ChatColor.DARK_BLUE+"Vous devez gagner avec les villageois, à la mort d'une de vos soeur vous obtiendrez le nom de son tueur, et vous posséder force quand vous etes à moins de 20 blocs d'une de vos soeur";
	}

	@Override
	public void giveRoleEffectAndItem(PlayerData player) {
		
		
	}

	@Override
    public void changeTo(PlayerData player) {
        playerWithRole = player;
    }

	@Override
	public void giveEffectAllTime() {
		if (sisters == null||sisters.size()<2) {
			sisters = RoleUtilLg.getPlayersWithRole(GameLgUtil.getGameOfPlayer(playerWithRole, "at giveEffectAllTime of Soeur"), RolesLg.SOEUR);
		}
		for (PlayerData ply:sisters) {
			double distance = LocationUtil.getDistanceBetween(ply, playerWithRole);
			if (distance < 21 && !ply.Name.equals(playerWithRole.Name)) {
				playerWithRole.player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 100, 0, false, false));
				return;
			}
		}
		
	}
	
	public void deathMessage(String name) {
		for (PlayerData player:sisters) {
			player.sendMessage(ChatColor.DARK_RED+"Le tueur de votre soeur est "+ name);
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
