package fr.fitzche.lgmore.RolesLg;

import org.bukkit.ChatColor;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import fr.fitzche.lgmore.Camp;
import fr.fitzche.lgmore.PlayerData;
import fr.fitzche.lgmore.RoleInstance;
import fr.fitzche.lgmore.Lg.GameLg;
import fr.fitzche.lgmore.RolesLg.Checkers.AncienChecker;
import fr.fitzche.lgmore.Util.GameLgUtil;
import fr.fitzche.lgmore.Util.PotionUtil;

public class ANCIEN implements RoleInstance{

	public static Camp camp = Camp.Villager;
	public boolean isRes = false;
	public PlayerData playerWithRole;
	public String name = "Ancien";
	public GameLg game;
	public int wait = 0;
	
	public ANCIEN(PlayerData player) {
		playerWithRole = player;
		this.game = GameLgUtil.getGameOfPlayer(player, "at ancien creation");
		this.game.resCheckers.add(new AncienChecker(this, game));

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
		return ChatColor.DARK_BLUE+ "Vous devez gagner avce le village, vous possédez l'effet résistance permanent, si vous mourrez de la main des loups garou, vous réssuciterez sans votre résistance";
	}

	@Override
	public void giveRoleEffectAndItem(PlayerData player) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void giveEffectAllTime() {
		if (!isRes) {
			if (wait >0) {
				wait --;
				return;
			}
			wait = 3;
			playerWithRole.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 79, 0, false , false));
		}
		
	}

	
	
	@Override
	public void giveNightEffectCheck() {
		if (this.playerWithRole.infected) {
			if (!(playerWithRole.camp.equals(Camp.Wolf)&& playerWithRole.isShooted)) {
				giveNightEffect();
			}
		}
		
	}

	@Override
	public void giveNightEffect() {
		playerWithRole.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 79, 0, false, false));

		
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


	@Override
	public void blind(PlayerData origin) {
		origin.sendMessage("Ce joueur n'est pas un role à info...");
		
	}


	@Override
	public boolean isInfoRole() {
		// TODO Auto-generated method stub
		return false;
	}

}
