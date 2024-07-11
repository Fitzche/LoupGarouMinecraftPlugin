package fr.fitzche.lgmore.RolesLg;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import fr.fitzche.lgmore.Main;
import fr.fitzche.lgmore.PlayerData;
import fr.fitzche.lgmore.Util.GameLgUtil;
import fr.fitzche.lgmore.Util.MathUtil;
import net.md_5.bungee.api.ChatColor;

public class ENFANT_SAUVAGE implements RoleInstance{
	public Camp camp = Camp.Villager;
	public PlayerData playerWithRole;
	public PlayerData model;
	public boolean transfo = false;
	public String name ="Enfant Sauvage";
	@Deprecated
	public ENFANT_SAUVAGE(PlayerData player) {
		this.playerWithRole = player;
		playerWithRole.sendMessage(ChatColor.DARK_PURPLE+"Vous pouvez choisir un modèle avec la commande /lg choose [nomDuJoueur], si ce modèle meure vous devenez loup garou");

		Bukkit.getScheduler().runTaskLater(Main.plug, new BukkitRunnable() {

			@Override
			public void run() {
				if (model == null) {
					model = MathUtil.getAlPlayer(GameLgUtil.getGameOfPlayer(playerWithRole, "at run of ENFANT sauvage creating"));
					playerWithRole.sendMessage(ChatColor.DARK_PURPLE+"Votre modèle est "+model.Name);
				}
				
			}
			
		}, 6000);
	}
	
	
	
	public void choose(PlayerData player) {
		if (model == null) {
			this.model = player;
			playerWithRole.sendMessage(ChatColor.DARK_PURPLE+"Votre modèle est "+model.Name);

		} else {
			playerWithRole.sendMessage(ChatColor.DARK_PURPLE+ "Vous avez déjà un modèle ("+ model.Name+")");

		}
	}


	@Override
    public void changeTo(PlayerData player) {
        playerWithRole = player;
    }
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return ChatColor.DARK_PURPLE+ name;
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return ChatColor.DARK_BLUE+"Vous êtes enfant sauvage, vous devez gagner avce le village, cependant si le modèle que vous aurez choisi meure, vous deviendrez loup-garou";
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
		if (!(playerWithRole.camp.equals(Camp.Wolf)&& playerWithRole.isShooted)) {
			giveNightEffect();
		}
		
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
