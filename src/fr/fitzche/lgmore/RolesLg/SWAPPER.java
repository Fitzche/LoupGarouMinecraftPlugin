package fr.fitzche.lgmore.RolesLg;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import fr.fitzche.lgmore.Camp;
import fr.fitzche.lgmore.PlayerData;
import fr.fitzche.lgmore.RoleInstance;
import fr.fitzche.lgmore.Lg.GameLg;
import net.md_5.bungee.api.ChatColor;

public class SWAPPER implements RoleInstance {

	public PlayerData playerWithRole;
	public Camp camp;
	public int xp = 0;
	public int life = 3;
	public SWAPPER(PlayerData p, Camp camp) {
		this.camp = camp;
		this.playerWithRole = p;
		changeCamp(camp);
	}
	
	public void changeCamp(Camp camp) {
		xp = 0;
		playerWithRole.camp = camp;
		playerWithRole.appCamp = camp;
		playerWithRole.setDisplayName();
		((GameLg)playerWithRole.game).broadcoast(ChatColor.GOLD + "Le joueur "+ ChatColor.RED + playerWithRole.Name + ChatColor.GOLD + " a rejoint le camp "+ camp.getColor() + camp.getName());
	}
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Swapper";
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "Vous possédez une couleur attribuée, vous devez gagner avec tous les joueurs ayant cette couleur, si vous tuez un joueur, vous lui volerez une vie et celui-ci passera dans votre camp, vous gagnerez 1 xp par minute passée dans le camp où vous serez à la fin du jeu, si vous restez en vie, vous possédez 3 vies.";
	}

	@Override
	public void giveRoleEffectAndItem(PlayerData player) {
		// TODO Auto-generated method stub

	}

	@Override
	public void giveEffectAllTime() {
		xp += 1;

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
	public void changeTo(PlayerData player) {
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
		// TODO Auto-generated method stub

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
