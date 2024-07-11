package fr.fitzche.lgmore.RolesLg;

import java.util.ArrayList;

import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import fr.fitzche.lgmore.GameLg;
import fr.fitzche.lgmore.PlayerData;
import fr.fitzche.lgmore.Love.Team;
import fr.fitzche.lgmore.Util.GameLgUtil;
import fr.fitzche.lgmore.Util.RoleUtilLg;
import net.md_5.bungee.api.ChatColor;

public class VOLEUR implements RoleInstance{
	
	public PlayerData playerWithRole;
	public String name ="Voleur";
	public Camp camp = Camp.Other;
	
	public VOLEUR(PlayerData player) {
		GameLg game = GameLgUtil.getGameOfPlayer(player, "at voleur creating");
		ArrayList<PlayerData> players = new ArrayList<PlayerData>();
		players.add(player);
		playerWithRole.team = new Team("Voleur", Camp.Other, game, players, null, "at voleur team creating", null, null, true, false, false, false);
		GameLgUtil.getGameOfPlayer(playerWithRole, "at voleur creating").teams.add(playerWithRole.team);	
		this.playerWithRole = player;
	}
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return (camp.getColor() + name);
	}

	@Override
    public void changeTo(PlayerData player) {
        playerWithRole = player;
    }


	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return ChatColor.DARK_BLUE+"Vous devez gagner tout seul"+ "\n"+ "Pour cela vous posséder force de manière permanente jusqu'à 60 minutes de jeu, et vous prendrez l'identité et le role du premier joueur que vous tuerez.";
	}

	@Override
	public void giveRoleEffectAndItem(PlayerData player) {
		playerWithRole.player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 3600, 0, false, false));
		
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
		playerWithRole.role = stealed.role;
	
		playerWithRole.player = null;


		playerWithRole.sendMessage(ChatColor.GOLD+"Vous avez volé le role de "+ stealed.Name + " qui était "+ stealed.role.getName());
		if (stealed.role.getCampOfRole().equals(Camp.Wolf)) {
			GameLg game = GameLgUtil.getGameOfPlayer(playerWithRole, "at steal of voleur");
			
			for (PlayerData p: game.getFalseWolfAlive()) {
				p.sendMessage(ChatColor.RED+"Le Joueur "+ playerWithRole.Name+ " a rejoint votre camp");
			}
		}
	}

}
