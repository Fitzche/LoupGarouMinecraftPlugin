package fr.fitzche.lgmore.RolesLg;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import fr.fitzche.lgmore.Camp;
import fr.fitzche.lgmore.PlayerData;
import fr.fitzche.lgmore.RoleInstance;
import fr.fitzche.lgmore.Lg.GameLg;
import fr.fitzche.lgmore.RolesLg.Checkers.craintifChecker;
import fr.fitzche.lgmore.Util.GameLgUtil;
import fr.fitzche.lgmore.Util.PotionUtil;
import net.md_5.bungee.api.ChatColor;

public class LOUP_CRAINTIF implements RoleInstance {
	public PlayerData playerWithRole;

	public static Camp camp = Camp.Wolf;
	public String name ="Loup-Garou Craintif";
	public int wait = 0;
	public GameLg game;
	public int nbOfPlayerAround = 0;
	int tempo = 0;
	
	public LOUP_CRAINTIF(PlayerData player) {
		this.playerWithRole = player;
		this.game = GameLgUtil.getGameOfPlayer(player, "at craintif creation");
		game.resCheckers.add(new craintifChecker(this));
		
		
		
	}
	
	public static ItemStack logo = new ItemStack(Material.DIAMOND_SWORD);

	
	
	@Override
	public String getName() {
		
		return (this.camp.getColor() +name);
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return (ChatColor.DARK_BLUE+"Vous devez gagner avec les Loups Garou (vous possédez la liste de vos alliés loups), pour cela vous obtenez 30% de force la nuit et 20% de résistance le jour, moins 5% de résistance le jour par loup-garou proche de vous, et moins 7% de force par loup-garou proche la nuit. Si le registre est oratoire, vous perdez 10% de force, s'il est tragic vous gagnez 5% de résistance. Votre mort est cachée");
	}

	@Override
    public void changeTo(PlayerData player) {
        playerWithRole = player;
    }

	@Override
	public void giveRoleEffectAndItem(PlayerData player) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void giveEffectAllTime() {
		if (tempo > 4) {
			tempo = 0;
			this.nbOfPlayerAround = 0;
			for (PlayerData p:game.getPlayerAlive()) {
				if (playerWithRole.getLocation().distance(p.getLocation()) <= 20 && !playerWithRole.getName().equals(p.getName()) && p.role.getCampOfRole().equals(Camp.Wolf)) {
					nbOfPlayerAround ++;
				}
			}
		} else {
			tempo ++;
		}
		
	}


	@Override
	public void giveNightEffect() {
		System.out.println("nk.1");
		if (playerWithRole == null) {
			System.out.println("effect can't be gived at null player");
		}
		
		if (wait > 0) {
			wait --;
			return;
		}
		wait = 3;
		playerWithRole.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 79, 0, false, false));
		
		//VOIR SCHEDULER + EFFECT = ERROR ???
		System.out.println("nk.2");
		
	}


	@Override
	public void giveDayEffect() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void startSpecialEvent() {
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
	public void giveNightEffectCheck() {
		if (!(playerWithRole.camp.equals(Camp.Wolf)&& playerWithRole.isShooted)) {
			giveNightEffect();
		}
		
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
