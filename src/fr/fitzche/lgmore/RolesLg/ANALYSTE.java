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
import fr.fitzche.lgmore.Util.GameLgUtil;
import fr.fitzche.lgmore.Util.LocationUtil;
import fr.fitzche.lgmore.Util.MathUtil;
import fr.fitzche.lgmore.Util.PotionUtil;
import net.md_5.bungee.api.ChatColor;

public class ANALYSTE implements RoleInstance {
	public PlayerData playerWithRole;
	public String name ="Analyste";
	public Camp camp = Camp.Villager;
	public GameLg game;
	public boolean blinded = false;
	public boolean use = true;
	public boolean usePlus = true;
	
	public ANALYSTE(PlayerData player) {
		this.playerWithRole = player;
		this.game = (GameLg) player.game;
		
		
	}
	
	@Override
	public String getName() {
		return (this.camp.getColor() +name);
	}
	
	public String getDescription() {
		return (Main.info +ChatColor.BLUE+"Vous devez gagner avec les Villageois, pour cela, vous pourrez une fois par épisode connaitre la somme des pourcentage d'effet des différents joueurs dans les 20 blocs alentours à l'aide de la commande /lg analyse. Vous pourrez à une occasion connaitre exactement le pourcentage de chaque effet à l'aide de la commande /lg analysePlus. ");
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
		use = true;
		
	}

	@Override
	public void giveNightEffectCheck() {
		if (!(playerWithRole.camp.equals(Camp.Wolf)&& playerWithRole.isShooted)) {
			giveNightEffect();
		}
		
	}

	@Override
	public void blind(PlayerData origin) {
		origin.sendMessage(ChatColor.GREEN + "Ce joueur est analyste. Celui-ci recevra un effet à 0% à sa prochaine commande");
		
	}

	@Override
	public boolean isInfoRole() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void command(CommandSender sender, Command cmd, String msg, String[] args) {
		if (args[0].equals("analyse")) {
			if (use) {
				int s = 0;
				int r = 0;
				
						
				for (PlayerData p:this.game.getPlayerAlive()) {
					if (!(p.getName().equals(playerWithRole.getName())) && LocationUtil.getDistanceBetween(p, playerWithRole) < 20) {
						if (p.player.hasPotionEffect(PotionEffectType.INCREASE_DAMAGE)) {
							s = s+30;
						}
						if (p.player.hasPotionEffect(PotionEffectType.DAMAGE_RESISTANCE)) {
							s = s+30;
						}
						s += p.boostS5 * 5;
						r += p.boostR5 * 5;
						
					}
				}
				
				
				use = false;
				
				if (blinded) {
					sender.sendMessage(Main.info+" La somme des effets alentours est de 0%");

				} else {
					sender.sendMessage(Main.info+" La somme des effets alentours est de "+ (s+r) + "%");
	
				}
			
			}
			
		} else if (args[0].equals("analysePlus")) {
			if (usePlus) {
				int s = 0;
				int r = 0;
				
						
				for (PlayerData p:this.game.getPlayerAlive()) {
					if (!(p.getName().equals(playerWithRole.getName())) && LocationUtil.getDistanceBetween(p, playerWithRole) < 20) {
						if (p.player.hasPotionEffect(PotionEffectType.INCREASE_DAMAGE)) {
							s = s+30;
						}
						if (p.player.hasPotionEffect(PotionEffectType.DAMAGE_RESISTANCE)) {
							s = s+30;
						}
						s += p.boostS5 * 5;
						r += p.boostR5 * 5;
						
					}
				}
				
				
				usePlus = false;
				sender.sendMessage(Main.info+" La somme de résistance alentours est de "+ r);
				sender.sendMessage(Main.info+" La somme de force alentours est de "+ s);
			
			}
			
		}
		
	}
}
