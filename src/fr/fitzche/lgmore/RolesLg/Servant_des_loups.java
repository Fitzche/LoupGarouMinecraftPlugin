package fr.fitzche.lgmore.RolesLg;

import fr.fitzche.lgmore.GameLg;
import fr.fitzche.lgmore.PlayerData;
import fr.fitzche.lgmore.RolesLg.Checkers.servantLgChecker;
import fr.fitzche.lgmore.Util.GameLgUtil;
import net.md_5.bungee.api.ChatColor;

public class Servant_des_loups implements RoleInstance {

	public Camp camp = Camp.Wolf;
	public PlayerData master;
	public PlayerData playerWithRole;
	public GameLg game;
	
	public Servant_des_loups(PlayerData p, PlayerData playerWithRole) {
		this.master = p;
		this.playerWithRole = playerWithRole;
		this.game = GameLgUtil.getGameOfPlayer(playerWithRole, "at servant creation");
		this.game.resCheckers.add(new servantLgChecker(this));
		playerWithRole.sendMessage(ChatColor.DARK_PURPLE+"Vous êtes devenu servant des loups, si votre maitre meurt, vous mourrez à sa place, vous apparaissez comme un loup et avez la liste mais vous n'avez pas d'effet, votre maitre est "+ master.Name);
		if (!playerWithRole.inLove) {
			
			playerWithRole.team.remove(playerWithRole);
			playerWithRole.team = game.lgTeam;
		}
		playerWithRole.camp = Camp.Wolf;
		
	}
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return camp.getColor()+"Servant des Loups";
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return ChatColor.DARK_AQUA+"Vous êtes servants des loups, vous devez gagner avec les loups sans effet, et si votre maitre meure, vous mourrez à sa place, votre maitre est "+ master.Name;
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

}
