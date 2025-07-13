package fr.fitzche.lgmore.RolesLg;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import fr.fitzche.lgmore.Camp;
import fr.fitzche.lgmore.PlayerData;
import fr.fitzche.lgmore.RoleInstance;
import fr.fitzche.lgmore.Lg.GameLg;
import fr.fitzche.lgmore.RolesLg.Checkers.LgBarbare_Checker;
import fr.fitzche.lgmore.Util.GameLgUtil;
import net.md_5.bungee.api.ChatColor;

public class LOUP_BARBARE implements RoleInstance {

	
	public PlayerData playerWithRole; 
	public int sup = 5;
	public GameLg game;
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return Camp.Wolf.getColor()+"Loup Garou Barbare";
	}

	
	public LOUP_BARBARE(PlayerData p) {
		p.boostS5 += 2;
		this.playerWithRole = p;
		this.game = (GameLg)p.game;
		this.game.resCheckers.add(new LgBarbare_Checker(p, this));
		
	}
	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return ChatColor.DARK_AQUA+"Vous gagnez avec les loups garou, pour cela vous possédez force 0.5 de manière permanente, et vous avez 5% de chance d'infliger un dégat supplémentaire de 1/2 coeur à chaque coup, pourcentage qui augmentera de 4% à chaque kill,  cependant vous perdrez 1 coeur permanent à chaque kill. Vous obtenez 2min de résistance à chaque kill.";
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
		if (!playerWithRole.isShooted) {
			giveNightEffect();
		}

	}

	@Override
	public void giveNightEffect() {
		playerWithRole.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 79, 0, false, false));


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
		this.playerWithRole = player;

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
