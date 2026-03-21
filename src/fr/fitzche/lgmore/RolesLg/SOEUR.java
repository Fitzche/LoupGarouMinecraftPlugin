package fr.fitzche.lgmore.RolesLg;

import java.util.ArrayList;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import fr.fitzche.lgmore.Camp;
import fr.fitzche.lgmore.Main;
import fr.fitzche.lgmore.PlayerData;
import fr.fitzche.lgmore.RoleInstance;
import fr.fitzche.lgmore.Lg.GameLg;
import fr.fitzche.lgmore.RolesLg.Checkers.SoeurChecker;
import fr.fitzche.lgmore.Util.GameLgUtil;
import fr.fitzche.lgmore.Util.LocationUtil;
import fr.fitzche.lgmore.Util.MathUtil;
import fr.fitzche.lgmore.Util.RoleUtil;
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
		this.game = (GameLg)player.game;
		game.resCheckers.add(new SoeurChecker(this, game));
		
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return Main.info +ChatColor.BLUE+"Vous devez gagner avec les villageois, à la mort d'une de vos soeur vous obtiendrez le nom de son tueur, vous possédez l'effet force quand vous etes à moins de 20 blocs d'une de vos soeurs / de votre soeur, vous connaissez une de vos soeurs.";
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
			sisters = RoleUtil.getPlayersWithRole(game, RolesLg.SOEUR);
		}
		
		
	}
	
	public int R5resisBonusSister() {
		int x = 0;
		for (PlayerData ply:sisters) {
			double distance = LocationUtil.getDistanceBetween(ply, playerWithRole);
			if (distance < 21 && !ply.Name.equals(playerWithRole.Name)) {
				x++;
				
			}
		}
		return -x;
	}
	
	public void deathMessage(String name) {
		for (PlayerData player:sisters) {
			player.sendMessage(ChatColor.DARK_RED+"Le tueur de votre soeur est "+ name);
		}
	}

	@Override
	public void giveNightEffectCheck() {
		if (this.playerWithRole.infected) {
			if (!(playerWithRole.camp.equals(Camp.Wolf)&& playerWithRole.isShooted)) {
				playerWithRole.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 79, 0, false, false));
			}
		}
		
		
	}

	@Override
	public void giveNightEffect() {

		
	}

	@Override
	public void giveDayEffect() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void episodeEffect() {
		if (game.timer.temps > 1300) {
			int tries = 0;
			PlayerData sister;
			do {
				sister = sisters.get(MathUtil.generateAlInt(0, sisters.size() -1));
				tries ++;
			} while (tries <5 && sister.getName().equals(playerWithRole.getName()));
			if (sister.getName().equals(playerWithRole.getName())) {
				playerWithRole.sendMessage(ChatColor.DARK_PURPLE+"pas d'autres soeur trouvé");
			}
			playerWithRole.sendMessage("Votre soeur (ou une de vos soeur s'il y en a plus que 2)) est "+ sister.getName());
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

	@Override
	public void command(CommandSender sender, Command cmd, String msg, String[] args) {
		// TODO Auto-generated method stub
		
	}

}
