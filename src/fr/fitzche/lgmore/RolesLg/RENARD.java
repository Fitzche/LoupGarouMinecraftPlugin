package fr.fitzche.lgmore.RolesLg;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;

import fr.fitzche.lgmore.PlayerData;
import fr.fitzche.lgmore.Util.GameLgUtil;
import fr.fitzche.lgmore.Util.LocationUtil;
import fr.fitzche.lgmore.Util.MathUtil;
import net.md_5.bungee.api.ChatColor;

public class RENARD implements RoleInstance{

	
	public PlayerData playerWithRole;
	Hashtable<String, Integer> players = new Hashtable<String, Integer>();
	public int flaired = 0;
	public static Camp camp = Camp.Villager;
	public String name ="Renard";
	public RENARD(PlayerData player) {
		this.playerWithRole = player;
		for (PlayerData playered :GameLgUtil.getGameOfPlayer(player, "at Renard()").getPlayerAlive()) {
			players.put(playered.Name, 0);
		}
		
	}
	
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return camp.getColor()+name;
	}


	@Override
    public void changeTo(PlayerData player) {
        playerWithRole = player;
    }
	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return ChatColor.DARK_BLUE+"Vous devez gagner avec le village, pour cela, vous pouvez obtenir le rôle d'un joueur avec /lg flairer [nomDuJoueur], vous avez cependant uniquement 85% de chance d'avoir son role exact, moins 5% par personne que vous avez flairé" + "\n"+ "Cependant pour flairer un joueur vous devez etre resté minimum 10minutes à coté de celui-ci";
	}

	@Override
	public void giveRoleEffectAndItem(PlayerData player) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void giveEffectAllTime() {
		for (PlayerData ply:GameLgUtil.getGameOfPlayer(playerWithRole, "at giveAllTimeEffect of Renard").getPlayerAlive()) {
			if (LocationUtil.getDistanceBetween(playerWithRole, ply) < 21) {
				getPlayerFlaire(ply.Name);
			}
		}
		
	}
	
	public void flairer(PlayerData player) {
		if (players.get(player.Name) < 600) {
			playerWithRole.sendMessage("Vous ne pouvez pas encore flairer ce joueur");
			return;
		} else {
			if (MathUtil.pourcentage(80 - (5*flaired))) {
				System.out.println("80% yes");
				playerWithRole.sendMessage(player.Name + " est probablement " + player.role.name());
			} else {
				System.out.println("80% no");
				playerWithRole.sendMessage(player.Name + " est probablement " + GameLgUtil.getGameOfPlayer(player, "at flairer() of renard").getRoles().get(MathUtil.generateAlInt(0, GameLgUtil.getGameOfPlayer(player, "at flairer() of renard 2").getRoles().size() - 1)).getName());

			}
			this.flaired ++;
			
		}
	}
	
	public void getPlayerFlaire(String playerName) {
			int x = players.get(playerName);
			if (x < 600) {
				players.remove(playerName);
				players.put(playerName, x+1);
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
