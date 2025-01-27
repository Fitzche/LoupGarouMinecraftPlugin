package fr.fitzche.lgmore.RolesLg;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import fr.fitzche.lgmore.Camp;
import fr.fitzche.lgmore.Main;
import fr.fitzche.lgmore.PlayerData;
import fr.fitzche.lgmore.RoleInstance;
import fr.fitzche.lgmore.Lg.GameLg;
import fr.fitzche.lgmore.Love.Team;
import fr.fitzche.lgmore.RolesLg.Checkers.AngeChecker;
import fr.fitzche.lgmore.RolesLg.Checkers.thierceAnge_Checker;
import fr.fitzche.lgmore.Util.GameLgUtil;
import fr.fitzche.lgmore.Util.LocationUtil;
import fr.fitzche.lgmore.Util.MathUtil;
import fr.fitzche.lgmore.Util.PotionUtil;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class THIERCE_ANGE implements RoleInstance {
	public PlayerData playerWithRole;
	
	
	public String name ="Ange de Thiercelieux";
	public Camp camp = Camp.Other;
	public GameLg game;
	public ArrayList<PlayerData> notAnnounced = new ArrayList<PlayerData>();
	public ArrayList<PlayerData> wantNotAnnounced = new ArrayList<PlayerData>();
	

	public THIERCE_ANGE(PlayerData player) {
		this.playerWithRole = player;
		this.game = GameLgUtil.getGameOfPlayer(player, "at T angel creating");
		game.resCheckers.add(new thierceAnge_Checker(this));
		
	}

	
	
	@Override
	public String getName() {
		return (this.camp.getColor() +name);
	}
	
	public String getDescription() {
		return (ChatColor.GOLD+"Vous gagnez seul, pour cela vous pouvez fabriquer une épée sharpness 4, de plus, si un joueur vous accuse d'etre un traitre au village via la commande /lg accuse, il n'obtiendra pas d'effet contre vous, et vous obtiendrez 20% de force contre lui ainsi que son role, ainsi que 1 coeur permanents supplémentaire et 5% de résistance. Si vous venez à le tuer, il n'y aura pas d'annonce de sa mort.");
	}
	public static ItemStack logo = new ItemStack(Material.FEATHER);

	
	public void giveEffectAllTime() {
		
		
	}


	
	
	
	@Override
    public void changeTo(PlayerData player) {
        playerWithRole = player;
    }
	@Deprecated
	public void giveRoleEffectAndItem(PlayerData player) {
		
	}

	
	
	
	@Override
	public void giveNightEffect() {
		if (this.playerWithRole.infected) {
			
			
			if (!(playerWithRole.camp.equals(Camp.Wolf)&& playerWithRole.isShooted)) {
				playerWithRole.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 79, 0, false, false));

			}
			
			//VOIR SCHEDULER + EFFECT = ERROR ???
			
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
