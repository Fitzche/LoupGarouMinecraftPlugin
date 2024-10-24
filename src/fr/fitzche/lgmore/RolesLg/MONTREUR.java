package fr.fitzche.lgmore.RolesLg;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import fr.fitzche.lgmore.PlayerData;
import fr.fitzche.lgmore.Util.GameLgUtil;
import fr.fitzche.lgmore.Util.PotionUtil;
import net.md_5.bungee.api.ChatColor;

public class MONTREUR implements RoleInstance {

	public String name = "Montreur D'ours";
	public PlayerData playerWithRole;
	public MONTREUR(PlayerData player) {
		this.playerWithRole = player;
	}

	@Override
    public void changeTo(PlayerData player) {
        playerWithRole = player;
    }
	
	@Override
	public String getName() {
		return (this.camp.getColor() + name);
	}
	
	public String getDescription() {
		return ("Vous êtes Montreur D'ours, vous devez gagner avec les Villageois, à chaque épisode apparaitra un ''GRRRR'' pour chaque loups dans un rayon de 50 blocs autour de vous");
	}
	public static ItemStack logo = new ItemStack(Material.CARROT_ITEM);

	
	public void giveEffectAllTime() {
		//null
	}
	
	public void giveRoleEffectAndItem(PlayerData player) {
		
	}
	
	public static Camp camp = Camp.Villager;
	@Override
	public void giveNightEffect() {
		if (this.playerWithRole.infected) {
			System.out.println("nk.1");
			if (playerWithRole == null) {
				System.out.println("effect can't be gived at null player");
			}
			if (!(playerWithRole.camp.equals(Camp.Wolf)&& playerWithRole.isShooted)) {
				giveNightEffect();
			}
			
			//VOIR SCHEDULER + EFFECT = ERROR ???
			System.out.println("nk.2");
		}
		
		
	}

	@Override
	public void giveDayEffect() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void startSpecialEvent() {
		// TODO Auto-generated method stub
		
	}
	
	public void renifle() {
		Location loc = this.playerWithRole.getLocation();
		for (PlayerData target:GameLgUtil.getGameOfPlayer(playerWithRole, " at renifle() in MONTREUR, at listing of player for test them").playerAlive) {
			if (target.getLocation().distance(loc) < 50 && target.role.getCampOfRole().equals(Camp.Wolf) || target.getLocation().distance(loc) < 50 && target.infected) {
				Bukkit.broadcastMessage(ChatColor.GOLD + "Grrrrrrr" + "\n");
			}
		}
	}

	@Override
	public void episodeEffect() {
		this.renifle();
		
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
		origin.sendMessage(ChatColor.GOLD + "Ce joueur est Montreur D'ours, cependant il ne subira aucun malus car modifier cette mécanique c'est trop galère à coder (bonjour de la part du dev !), je le ferai plus tard, cependant vous avez son role et vous obtenez 5min de speed parce que heu... parce que vous fuyez l'ours");
		this.playerWithRole.sendMessage(ChatColor.GOLD+"Vous avez été aveuglé, un loup sait donc qui vous etes mais il n'a pas pu vous nuire");
		origin.addPotionEffect(new PotionEffect(PotionEffectType.SPEED	, 6000, 0, false, false));
	}

	@Override
	public boolean isInfoRole() {
		// TODO Auto-generated method stub
		return true;
	}
}
