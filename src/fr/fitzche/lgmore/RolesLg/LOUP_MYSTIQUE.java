package fr.fitzche.lgmore.RolesLg;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import fr.fitzche.lgmore.Camp;
import fr.fitzche.lgmore.PlayerData;
import fr.fitzche.lgmore.RoleInstance;
import fr.fitzche.lgmore.Lg.GameLg;
import fr.fitzche.lgmore.RolesLg.Checkers.MysticChecker;
import fr.fitzche.lgmore.Util.GameLgUtil;
import fr.fitzche.lgmore.Util.MathUtil;
import fr.fitzche.lgmore.Util.PotionUtil;
import net.md_5.bungee.api.ChatColor;

public class LOUP_MYSTIQUE implements RoleInstance {
	public PlayerData playerWithRole;
	
	public static Camp camp = Camp.Wolf;
	public GameLg game;
	public String name ="Loup-Garou Mystique";
	public int wait = 0;
	
	public LOUP_MYSTIQUE (PlayerData player) {
		this.playerWithRole = player;
		
		this.game = player.game;
		this.game.resCheckers.add(new MysticChecker(this, game));
		
		
	}
	
	public static ItemStack logo = new ItemStack(Material.ENDER_PEARL);

	
	
	@Override
	public String getName() {
		
		return (this.camp.getColor() +name);
	}


	@Override
    public void changeTo(PlayerData player) {
        playerWithRole = player;
    }

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return (ChatColor.DARK_BLUE+"Vous êtes Loup-Garou, vous devez gagner avec les Loups Garou, vous possédez la liste de vos alliés loups, pour cela vous obtenez Force I la Nuit ainsi que le nom et le rôle d'un joueur aléatoire de la partie à chaque fois qu'un loup-garou meure");
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
	
	public void voir() {
		PlayerData playerToSee = playerWithRole.game.getPlayerAlive().get(MathUtil.generateAlInt(0, playerWithRole.game.getPlayerAlive().size() - 1));
		playerWithRole.sendMessage(ChatColor.GOLD + "Le rôle de "+ playerToSee.Name + ", qui est " + playerToSee.role);
		if (playerToSee.inLove) {
			playerWithRole.sendMessage(ChatColor.GOLD+"Celui-ci est en couple");

		}
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


	@Override
	public void command(CommandSender sender, Command cmd, String msg, String[] args) {
		// TODO Auto-generated method stub
		
	}

}
