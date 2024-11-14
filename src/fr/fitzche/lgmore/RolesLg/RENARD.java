package fr.fitzche.lgmore.RolesLg;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;

import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

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
		return ChatColor.DARK_BLUE+"Vous devez gagner avec le village, pour cela, vous pouvez obtenir le rôle d'un joueur avec /lg flairer [nomDuJoueur], vous avez cependant uniquement 85% de chance d'avoir son role exact, moins 5% par personne que vous avez déjà flairé" + "\n"+ "Cependant pour flairer un joueur vous devez etre resté minimum 15min à coté de celui-ci";
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
		if (players.get(player.Name) < 900 ) {
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
				
				players.put(playerName, x+1);
			}
			
			
		}

	@Override
	public void giveNightEffectCheck() {
		giveNightEffect();
		
	}

	@Override
	public void giveNightEffect() {
		if (this.playerWithRole.infected) {
			if (!(playerWithRole.camp.equals(Camp.Wolf)&& playerWithRole.isShooted)) {
				playerWithRole.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 79, 0, false, false));
			}
		}
		
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
		origin.sendMessage(ChatColor.GOLD + "Ce joueur est Renard, tout ses flairages en court ont été remis à 0");
		this.playerWithRole.sendMessage(ChatColor.GOLD+ "Vous avez été aveuglé, vos flairage ont été remis à 0");
		for (PlayerData p:GameLgUtil.getGameOfPlayer(playerWithRole, "blind of renard").getPlayerAlive()) {
			this.players.put(p.getName(), 0);
		}
	}


	@Override
	public boolean isInfoRole() {
		// TODO Auto-generated method stub
		return false;
	}

}
