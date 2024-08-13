package fr.fitzche.lgmore.RolesLg;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import fr.fitzche.lgmore.GameLg;
import fr.fitzche.lgmore.PlayerData;
import fr.fitzche.lgmore.RolesLg.Checkers.MysticChecker;
import fr.fitzche.lgmore.Util.GameLgUtil;
import fr.fitzche.lgmore.Util.MathUtil;
import fr.fitzche.lgmore.Util.PotionUtil;
import net.md_5.bungee.api.ChatColor;

public class LOUP_MYSTIQUE implements RoleInstance {
	public PlayerData playerWithRole;
	public PotionEffect STRENGTH;
	public static Camp camp = Camp.Wolf;
	public GameLg game;
	public String name ="Loup-Garou Mystique";
	
	public LOUP_MYSTIQUE (PlayerData player) {
		this.playerWithRole = player;
		this.STRENGTH = PotionUtil.STRENGTH;
		this.game = GameLgUtil.getGameOfPlayer(player, "at mystic lg location ");
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
		PotionUtil.giveIncreaseDamage(playerWithRole);
		
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
		PlayerData playerToSee = GameLgUtil.getGameOfPlayer(playerWithRole, "at voir() of loup mystique 1").getPlayerAlive().get(MathUtil.generateAlInt(0, GameLgUtil.getGameOfPlayer(playerWithRole, "at voir() of loup mystique 2").getPlayerAlive().size() - 1));
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

}
