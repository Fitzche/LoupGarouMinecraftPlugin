package fr.fitzche.lgmore.RolesLg;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import fr.fitzche.lgmore.Camp;
import fr.fitzche.lgmore.PlayerData;
import fr.fitzche.lgmore.RoleInstance;
import fr.fitzche.lgmore.Lg.GameLg;
import fr.fitzche.lgmore.Util.GameLgUtil;
import net.md_5.bungee.api.ChatColor;

public class SAGE implements RoleInstance {

	public Camp camp = Camp.Villager;
	public PlayerData playerWithRole;
	public GameLg game;
	public double sageBad = 0;
	public double sageNeutral = 0;
	public double sageGood = 0;
	public double jaugeur = 0.044;
	public String name ="Vieux Sage";
	public SAGE(PlayerData player) {
		this.playerWithRole = player;
		this.game = player.game;
		
	}
	
	@Override
	public String getName() {
		// 
		return camp.getColor() + name ;
	}

	@Override
	public String getDescription() {
		// 
		return ChatColor.DARK_BLUE+"Vous devez gagner avec le village, pour cela vous obtenez à chaque episode un taux de présence pour chaque aura (lumineuse, neutre, obscure) qui augmente en fonction de l'aura des joueurs que vous fréquentez, une aura dangereuse augmente très fortement le taux d'aura obscure.";
	}

	@Override
	public void giveRoleEffectAndItem(PlayerData player) {
		// 

	}

	@Override
    public void changeTo(PlayerData player) {
        playerWithRole = player;
    }

	@Override
	public void giveEffectAllTime() {
	
		for (PlayerData player: game.getPlayerAlive()) {
			if (player != null && playerWithRole != null &&!player.getName().equals(this.playerWithRole.getName()) && player.getLocation().distance(this.playerWithRole.player.getLocation()) < 20) {
				if (player.role.aura.equals(Aura.LUMINOUS) && sageGood < 100) {
					sageGood += jaugeur;
				} else if (player.aura.equals(Aura.NEUTRAL) && sageNeutral < 100) {
					sageNeutral += jaugeur;
				} else if (player.aura.equals(Aura.OBSCUR) && sageBad < 100) {
					sageBad += jaugeur;
				} else if (player.aura.equals(Aura.DANGEROUS) && sageBad < 100) {
					sageBad += (jaugeur * 18);
				}
			}
			
			
		}

	}

	@Override
	public void giveNightEffectCheck() {
		giveNightEffect();
	}

	@Override
	public void giveNightEffect() {
		

	}

	@Override
	public void giveDayEffect() {
		// 

	}

	@Override
	public void episodeEffect() {
		// 

	}

	@Override
	public void setEpisodeTrue() {
		playerWithRole.sendMessage(ChatColor.GREEN+"Votre jauge d'aura lumineux est à "+ sageGood +"%"+ "\n");
		playerWithRole.sendMessage(ChatColor.RED+"Votre jauge d'aura obscur est à "+ sageBad +"%"+ "\n");
		playerWithRole.sendMessage(ChatColor.YELLOW+"Votre jauge d'aura neutre est à "+ sageNeutral +"%"+ "\n");
		sageBad = 0;
		sageGood = 0;
		sageNeutral = 0;

		
	}

	@Override
	public void startSpecialEvent() {
		// TODO Auto-generated method stub

	}

	@Override
	public void blind(PlayerData origin) {
		origin.sendMessage(ChatColor.GOLD+ "Ce joueur est vieux sage, sa capacité à voir les auras sera perturbée lors du prochain épisode");
		this.playerWithRole.sendMessage(ChatColor.GOLD+ "Vous avez été aveuglé, les jauges d'auras seront toutes à 100% lors du prochain épisode");
		sageBad = 101;
		sageGood = 101;
		sageNeutral = 101;
	}

	@Override
	public boolean isInfoRole() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void command(CommandSender sender, Command cmd, String msg, String[] args) {
		// TODO Auto-generated method stub
		
	}

}
