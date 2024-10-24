package fr.fitzche.lgmore.RolesLg;

import java.util.ArrayList;

import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import fr.fitzche.lgmore.GameLg;
import fr.fitzche.lgmore.PlayerData;
import fr.fitzche.lgmore.RolesLg.Checkers.SoeurChecker;
import fr.fitzche.lgmore.Util.GameLgUtil;
import fr.fitzche.lgmore.Util.LocationUtil;
import fr.fitzche.lgmore.Util.MathUtil;
import fr.fitzche.lgmore.Util.RoleUtilLg;
import net.md_5.bungee.api.ChatColor;

public class SOEUR implements RoleInstance {
	
	public ArrayList<PlayerData> sisters = new ArrayList<PlayerData>();
	public static Camp camp = Camp.Villager;
	public PlayerData playerWithRole;
	public String name ="Soeur";
	public GameLg game;
	@Override
	public String getName() {
		return camp.getColor()+name;
	}
	
	public SOEUR(PlayerData player, ArrayList<PlayerData> sisters) {
		playerWithRole = player;
		this.sisters = sisters;
		this.game = GameLgUtil.getGameOfPlayer(player, "at sister creation");
		game.resCheckers.add(new SoeurChecker(this, game));
		
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return ChatColor.DARK_BLUE+"Vous devez gagner avec les villageois, à la mort d'une de vos soeur vous obtiendrez le nom de son tueur, vous NE CONNAISSEZ PAS vo(s)(tre) soeur(s), le seul moyen de connaitre leurs/son identité(s) est la réperer grâce à l'effet force quand vous etes à moins de 20 blocs d'une de vos soeurs / de votre soeur";
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
				playerWithRole.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 100, 0, false, false));
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
		if (GameLgUtil.getGameOfPlayer(playerWithRole, "at soeur episode effect").timer.temps > 1300) {
			playerWithRole.sendMessage(ChatColor.DARK_PURPLE+"Votre soeur (ou une de vos soeur s'il y en a plus que 2)) est "+ sisters.get(MathUtil.generateAlInt(0, sisters.size() -1)).getName());
		}
		
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
		origin.sendMessage(ChatColor.GREEN + "Ce joueur n'est pas un rôle à info");
		
	}

	@Override
	public boolean isInfoRole() {
		// TODO Auto-generated method stub
		return false;
	}

}
