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
import fr.fitzche.lgmore.Util.GameLgUtil;
import fr.fitzche.lgmore.Util.MathUtil;
import fr.fitzche.lgmore.Util.PotionUtil;
import net.md_5.bungee.api.ChatColor;

public class SIMPLE_VILLAGER implements RoleInstance {
	public PlayerData playerWithRole;
	public String name ="Simple Villageois";
	public Camp camp = Camp.Villager;
	public GameLg game;
	
	public SIMPLE_VILLAGER(PlayerData player) {
		this.playerWithRole = player;
		this.game = (GameLg) player.game;
		
		if (MathUtil.pourcentage(50)) {
			if (MathUtil.pourcentage(50)) {
				playerWithRole.sendMessage(ChatColor.GREEN+ "Vous obtenez 2 coeurs à donner à deux joueurs de votre choix (vous exclus) avec la commande /lg conférer [nomDuJoueur]");
				playerWithRole.bienfaisance += 2;
			} else {
				playerWithRole.sendMessage(ChatColor.GREEN+ "Vous obtenez 5% de résistance");
				playerWithRole.boostR5 ++;

			}
		} else {
			if (MathUtil.pourcentage(50)) {
				playerWithRole.sendMessage(ChatColor.GREEN+ "Vous pouvez voir les mort 15s en avance");
				playerWithRole.visionDeath = true;
			} else {
				playerWithRole.sendMessage(ChatColor.GREEN+ "Vous pouvez voir les changements de registre proche de vous");
				playerWithRole.visionRegister = true;
			}
		}
	}
	
	@Override
	public String getName() {
		return (this.camp.getColor() +name);
	}
	
	public String getDescription() {
		return (ChatColor.DARK_BLUE+"Vous devez gagner avec les Villageois, vous ne possédez qu'un pouvoir aléatoire parmis 4. ");
	}
	public static ItemStack logo = new ItemStack(Material.WHEAT);

	
	public void giveEffectAllTime() {
		//null
	}
	

	@Override
    public void changeTo(PlayerData player) {
        playerWithRole = player;
    }
	public void giveRoleEffectAndItem(PlayerData player) {
		
	}
	
	
	@Override
	public void giveNightEffect() {
		
		
		
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
