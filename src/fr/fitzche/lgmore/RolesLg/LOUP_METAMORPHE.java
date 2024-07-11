package fr.fitzche.lgmore.RolesLg;

import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import fr.fitzche.lgmore.GameLg;
import fr.fitzche.lgmore.PlayerData;
import fr.fitzche.lgmore.Util.GameLgUtil;
import fr.fitzche.lgmore.Util.RoleUtilLg;
import net.md_5.bungee.api.ChatColor;

public class LOUP_METAMORPHE implements RoleInstance{
	
	public PlayerData playerWithRole;
    public Camp camp = Camp.Wolf;
	
	
	public LOUP_METAMORPHE(PlayerData player) {
		this.playerWithRole = player;
	}
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Loup Métamorphe";
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return ChatColor.DARK_BLUE+"Vous devez gagner avec les loups"+ "\n"+ "Pour cela vous prendrez le role du premier joueur que vous tuerez tout en restant loup garou.";
	}

	@Override
	public void giveRoleEffectAndItem(PlayerData player) {
		
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
	
	public void steal(PlayerData stealed) {
		stealed.player = playerWithRole.player;
        
		stealed.Name = playerWithRole.Name;
		stealed.infected = true;
		stealed.camp = playerWithRole.camp;
		stealed.role.changeCampTo(playerWithRole.role.getCampOfRole());
		playerWithRole.role = stealed.role;
		
		GameLg game = GameLgUtil.getGameOfPlayer(playerWithRole, "at steal of metamorph");
		game.playerAlive.remove(playerWithRole);
		playerWithRole.player = null;
	}

    @Override
    public void changeTo(PlayerData player) {
        playerWithRole = player;
    }

}
