package fr.fitzche.lgmore.RolesLg;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import fr.fitzche.lgmore.Camp;
import fr.fitzche.lgmore.Main;
import fr.fitzche.lgmore.PlayerData;
import fr.fitzche.lgmore.RoleInstance;
import fr.fitzche.lgmore.Lg.GameLg;
import fr.fitzche.lgmore.RolesLg.Checkers.VoleurChecker;
import fr.fitzche.lgmore.Util.GameLgUtil;
import fr.fitzche.lgmore.Util.RoleUtil;
import net.md_5.bungee.api.ChatColor;

public class LOUP_METAMORPHE implements RoleInstance{
	
	public PlayerData playerWithRole;
    public Camp camp = Camp.Wolf;
    public GameLg game;
    public int wait = 0;
	
	
	public LOUP_METAMORPHE(PlayerData player) {
		this.playerWithRole = player;
		this.game = (GameLg)player.game;
		this.game.resCheckers.add(new VoleurChecker(null, game, this));
		
	}
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Loup Métamorphe";
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return Main.info +ChatColor.BLUE+"Vous devez gagner avec les loups"+ "\n"+ "Pour cela vous prendrez le role du premier joueur que vous tuerez tout en restant loup garou.";
	}

	@Override
	public void giveRoleEffectAndItem(PlayerData player) {
		
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
		if (playerWithRole == null) {
			System.out.println("effect can't be gived at null player");
		}
		
		if (wait > 0) {
			wait --;
			return;
		}
		wait = 3;
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
	public void setEpisodeTrue() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void startSpecialEvent() {
		// TODO Auto-generated method stub
		
	}
	
	public void steal(PlayerData stealed) {
		if (stealed.isOnline) {
			for (PotionEffect effect:stealed.player.getActivePotionEffects()) {
				playerWithRole.addPotionEffect(effect);
			}
		}
		playerWithRole.roleIn = stealed.roleIn;
		stealed.roleIn.changeTo(playerWithRole);
		
		this.playerWithRole.role = stealed.role;
		this.playerWithRole = null;
		
		
		
	}

    @Override
    public void changeTo(PlayerData player) {
        playerWithRole = player;
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
