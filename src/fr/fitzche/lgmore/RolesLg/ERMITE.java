package fr.fitzche.lgmore.RolesLg;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import fr.fitzche.lgmore.Camp;
import fr.fitzche.lgmore.Main;
import fr.fitzche.lgmore.PlayerData;
import fr.fitzche.lgmore.RoleInstance;
import fr.fitzche.lgmore.Lg.GameLg;
import fr.fitzche.lgmore.RolesLg.Checkers.ErmiteChecker;
import fr.fitzche.lgmore.Util.GameLgUtil;
import fr.fitzche.lgmore.Util.PotionUtil;
import net.md_5.bungee.api.ChatColor;

public class ERMITE implements RoleInstance {
	public PlayerData playerWithRole;
	public String name ="Ermite";
	public Camp camp = Camp.Villager;
	public GameLg game;
	public int nbOfPlayerAround = 0;
	int tempo = 0;
	
	public ERMITE(PlayerData player) {
		this.playerWithRole = player;
		this.game = player.game;
		game.resCheckers.add(new ErmiteChecker(this));
	}
	
	@Override
	public String getName() {
		return (this.camp.getColor() +name);
	}
	
	public String getDescription() {
		return (ChatColor.DARK_BLUE+"Vous devez gagner avec les Villageois, vous possédez 30% de force le jour et 20% de résistance la nuit, mais pour chaque joueur autour de vous, vous perdez 5% de force, le jour et 3% de resistance la nuit, vous pouvez donc avoir un malus. Si le registre est tragique, vous gagnez 10% de résistance, s'il est oratoire, vous perdez 10% de force. Votre mort ne sera pas annoncée.");
	}
	public static ItemStack logo = new ItemStack(Material.WHEAT);

	
	public void giveEffectAllTime() {
		if (tempo > 4) {
			tempo = 0;
			this.nbOfPlayerAround = 0;
			for (PlayerData p:game.getPlayerAlive()) {
				if (playerWithRole.getLocation().distance(p.getLocation()) <= 20 && !playerWithRole.getName().equals(p.getName())) {
					nbOfPlayerAround ++;
				}
			}
		} else {
			tempo ++;
		}
	}
	

	@Override
    public void changeTo(PlayerData player) {
        playerWithRole = player;
    }
	public void giveRoleEffectAndItem(PlayerData player) {
		
	}
	
	
	@Override
	public void giveNightEffect() {
		if (this.playerWithRole.infected) {
			
			
			
			
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

	@Override
	public void command(CommandSender sender, Command cmd, String msg, String[] args) {
		// TODO Auto-generated method stub
		
	}
}
