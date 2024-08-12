package fr.fitzche.lgmore.RolesLg;

import org.bukkit.ChatColor;

import fr.fitzche.lgmore.PlayerData;
import fr.fitzche.lgmore.RolesLg.Checkers.IDV_Checker;
import fr.fitzche.lgmore.Util.GameLgUtil;
import fr.fitzche.lgmore.Util.PlayerUtil;

public class IDIOT_DU_VILLAGE implements RoleInstance {
	public PlayerData playerWithRole;
	public static Camp camp = Camp.Villager;
	public boolean powerUsed = false;
	public String name ="Idiot du village";
	
	public IDIOT_DU_VILLAGE(PlayerData ply) {
		this.playerWithRole = ply;
		GameLgUtil.getGameOfPlayer(ply, "at idv creation").resCheckers.add(new IDV_Checker(this, GameLgUtil.getGameOfPlayer(ply, "at idv creation")));
	}

	@Override
    public void changeTo(PlayerData player) {
        playerWithRole = player;
    }
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return camp.getColor()+ name;
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return ChatColor.DARK_BLUE+ "Vous devez gagner avec le village, pour cela vous pourrez réssuciter une fois avec 2 coeur en moins si le village vous élimine par erreur.";
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
	
	public void respawn() {
		playerWithRole.player.setMaxHealth(playerWithRole.player.getMaxHealth()-4);
		GameLgUtil.tpAl(playerWithRole);
		powerUsed = false;
	}

}
